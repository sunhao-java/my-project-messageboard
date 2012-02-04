
/**
 * 封装YUI的弹窗组件（此弹窗可以放入一个页面）
 * 依赖的js包在common_js.jsp中已被引入
 * @author sunhao(sunhao.java@gmail.com)
 */
YAHOO.namespace("app.form");
YAHOO.app.form = function(){
	var $C = YAHOO.util.Connect,dom = YAHOO.util.Dom,event = YAHOO.util.Event,$L=YAHOO.lang;
	var _true = 'true';
	var _false = 'false';
	var formDialog;
	
	return {
		show : function(args){
			var id_ = args.id;										//id
			var url_ = args.reqUrl || '';							//请求页面的地址
			var submit_ = args.submitUrl || '';						//提交的地址，如果不写，则在载入页面的form中
			var formId_ = args.formId;								//需要提交的表单ID
			var name_ = args.name || "formDialog";					//iframe的name，默认是formDialog
			var responseUrl_ = args.responseUrl || "";				//点击确定按钮后要跳转的URL
			var success_ = args.success || "成功！";					//交互成功显示信息，默认显示成功
			var failure_ = args.failure || "失败！";					//交互失败显示信息，默认显示失败
			
			var closeIcon_ = args.closeIcon || _false;				//右上角是否有关闭图标，默认是true
			
			var cancelButton_ = args.cancelButton || _true;			//是否有取消按钮，默认是true
			
			var title_ = args.title || ""; 							//弹窗的head
			
			var modal_ = args.modal || _true;						//背景是否被灰化
			var draggable_ = args.draggable || _true;				//窗口是否能被移动
			
			var diaWidth_ = args.diaWidth || 300;					//弹窗宽度(默认300px)
			var diaHeight_ = args.diaHeight || 100;					//弹窗高度(默认100px)
			
			var zIndex_ = args.zIndex || 999;						//对应CSS属性值z-index,默认是4
			
			var overflow_ = args.overflow || 'no'; 				// 显示内容区域的overflow样式
			
			if($L.isString(closeIcon_)){
				closeIcon_ = (closeIcon_ == _true);
			}
			
			if($L.isString(modal_)){
                modal_ = (modal_ == _true);
            }
			
			if($L.isString(draggable_)){
				draggable_ = (draggable_ == _true);
			}
			
			/**
			 * 如果success_和failure_不是以！或!结果的，则在后面加上！
			 * @memberOf {TypeName} 
			 */
			var success_temp = success_.substring(success_.length-1, success_.length);
			var failure_temp = failure_.substring(failure_.length-1, failure_.length);
			if(!(success_temp == '!' || success_temp == '！')){
				success_ += '！';
			}
			if(!(failure_temp == '!' || failure_temp == '！')){
				failure_ += '！';
			}
			
			/**
			 * 拼弹框需要的HTML
			 * @memberOf {TypeName} 
			 */
			var str = '<div class="hd"><span style="font-size: 12px;">' + title_ + '</span></div>';
				str += '<div class="formbd">';
				
			  str += '<iframe src="' + url_ + '" id="' + name_ + '" name="' + name_ + '" frameborder="0" ' +
			  				'style="width: 100%;height:100%" scrolling="' + overflow_ + '"></iframe>';
			  
			  str += '</div>';
			  
			 //使此div的ID永远唯一，否则会出现第一次出现弹框时没有问题，但第二次第三次就会出问题
			 var divId = 'dialog1' + new Date().getTime();
			  
			 var div = document.createElement("div");
			 div.innerHTML = str;
			 div.id = divId;
			 div.setAttribute('class', 'yui-pe-content');
			 
			 document.body.appendChild(div);
			 
			 formDialog = new YAHOO.widget.Dialog(divId, 
				{ width : diaWidth_,
				  height: diaHeight_,
				  fixedcenter : true,
				  visible : false, 
				  modal : true,
				  close : closeIcon_,
				  zIndex : zIndex_,
				  constraintoviewport : false,
				  buttons : [ { text:"提交", handler:handleSubmit, isDefault:true },
					      { text:"取消", handler:handleCancel } ]
				});
			 
			function handleSubmit() {
				var formDate = frames[name_].document.forms[0];
				/**
				 * 没有提供提交URL，则提交form，使用form的action
				 * 否则使用提供的URL进行提交表单
				 * 
				 * 最好使用提供URL的方式
				 */
				if(submit_ == ''){
					formDate.submit();
					if(responseUrl_ != ''){
						window.location.href = responseUrl_;
					} else {
						window.location.reload(true);
					}
				} else {
					/**
					 * 这里有问题，以后要修改
					 */
					var flag = true;//Validator.Validate(formDate, 1);
					if(flag){
						this.cancel();
						$C.setForm(formDate);
						$C.asyncRequest("POST", submit_, {
							success : function(o){
								var _e = eval("(" + o.responseText + ")");
								if(_e.status == '1'){
									YAHOO.app.dialog.pop({'dialogHead':'提示','cancelButton':'false','alertMsg':success_,
										'confirmFunction' : function(){
											if(responseUrl_ != ''){
												window.location.href = responseUrl_;
											} else {
												window.location.reload(true);
											}
										}});
								} else {
									YAHOO.app.dialog.pop({'dialogHead':'提示','cancelButton':'false','alertMsg':failure_});
								}
							},
							failure : function(o){
								YAHOO.app.dialog.pop({'dialogHead':'提示','cancelButton':'false','alertMsg':'错误代码:' + o.status});
							}
						});
					}
				}
			};
			
			function handleCancel() {
				this.cancel();
			};
			 
			 formDialog.render();
			 formDialog.show();
            
			
		}
	}
}();