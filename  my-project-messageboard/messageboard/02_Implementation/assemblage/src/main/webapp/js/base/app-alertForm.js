
/**
 * 封装YUI的弹窗组件（此弹窗可以放入一个页面）
 * 依赖的js包在common_js.jsp中已被引入
 * @author sunhao(sunhao.java@gmail.com)
 */
YAHOO.namespace("app.form");
YAHOO.app.alertForm = function(){
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
			var singleId = args.singleId || _true;					//是否需要对iframe的ID和NAME唯一,默认true
			var responseUrl_ = args.responseUrl || "";				//点击确定按钮后要跳转的URL
			var success_ = args.success || "成功！";					//交互成功显示信息，默认显示成功
			var failure_ = args.failure || "失败！";					//交互失败显示信息，默认显示失败
			
			var closeIcon_ = args.closeIcon || _true;				//右上角是否有关闭图标，默认是true
			
			var cancelButton_ = args.cancelButton || _true;			//是否有取消按钮，默认是true
			var confirmButton_ = args.confirmButton || _true;		//是否有确定按钮，默认是true
			
			var confirmFunction_ = args.confirmFunction;			//点击确定按钮执行的函数
			var cancelFuncion_ = args.cancelFuncion; 				//关闭窗口时需要执行的函数
			
			var title_ = args.title || ""; 							//弹窗的head
			
			var modal_ = args.modal || _true;						//背景是否被灰化
			var draggable_ = args.draggable || _true;				//窗口是否能被移动
			
			var diaWidth_ = args.diaWidth || 300;					//弹窗宽度(默认300px)
			var diaHeight_ = args.diaHeight || 100;					//弹窗高度(默认100px)
			
			var zIndex_ = args.zIndex || 999;						//对应CSS属性值z-index,默认是4
			
			var overflow_ = args.overflow || 'no'; 					// 显示内容区域的overflow样式
			
			var needValidate_ = args.needValidate || 'true';		//是否需要验证表单，默认是true，需要验证

            var checkLeve_ = args.checkLeve || 3;                   //validate检验的提示级别，默认是3
            
            var extBtn_ = args.extBtn;								//自定义扩展的按钮,默认为空
			
			if($L.isString(closeIcon_)){
				closeIcon_ = (closeIcon_ == _true);
			}
			
			if($L.isString(needValidate_)){
				needValidate_ = (needValidate_ == _true);
			}
			
			if($L.isString(modal_)){
                modal_ = (modal_ == _true);
            }
			
			if($L.isString(draggable_)){
				draggable_ = (draggable_ == _true);
			}

            if($L.isString(checkLeve_)){
                checkLeve_ = parseInt(checkLeve_);
            }
            
            if($L.isString(singleId)){
				singleId = (singleId == _true);
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
			  if(singleId)
			  	name_ = name_ + new Date().getTime();
				
			  str += '<iframe src="' + url_ + '" id="' + name_ + '" name="' + name_ + '" frameborder="0" ' +
			  				'style="width: 100%;height:100%;overflow-x : hidden;" scrolling="' + overflow_ + '"></iframe>';
			  
			  str += '</div>';
			  
			 //使此div的ID永远唯一，否则会出现第一次出现弹框时没有问题，但第二次第三次就会出问题
			 var divId = 'dialog1' + new Date().getTime();
			  
			 var div = document.createElement("div");
			 div.innerHTML = str;
			 div.id = divId;
			 div.setAttribute('class', 'yui-pe-content');
			 
			 document.body.appendChild(div);
			 
			 if($L.isString(confirmButton_)){
				confirmButton_ = (confirmButton_ == _true);
			 }
			 if($L.isString(cancelButton_)){
				cancelButton_ = (cancelButton_ == _true);
			 }
			 
			 var buttons_ = [];
			 if(confirmButton_ || cancelButton_){
				if(confirmButton_){
					if($L.isFunction(confirmFunction_)){
						buttons_.push({text:'确定',handler:confirmFunction_});
					} else {
						buttons_.push({text:'确定',handler:handleSubmit});
					}
				}
				if(cancelButton_){
					if($L.isFunction(cancelFuncion_)){
						buttons_.push({text:'取消',handler:cancelFuncion_});
					} else {
						buttons_.push({text:'取消',handler:handleCancel});
					}
				}
			 }
			 
			 //如果有自定义扩展的按钮,则加进来
			 if(!$L.isUndefined(extBtn_)){
				 for(var i = 0; i < extBtn_.length; i++){
					 buttons_.push(extBtn_[i]);
				 }
			 }

             /**
             * 没有提供提交URL，则提交form，使用form的action
             * 否则使用提供的URL进行提交表单
             */
			 function handleSubmit() {
                 var formDate = frames[name_].document.getElementById(formId_);

                 if(formDate == null){
                     //this.cancel();
                     YAHOO.app.dialog.pop({
                        'dialogHead':'提示',
                        'cancelButton':'false',
                        'alertMsg':'请给出正确的formId！',
                        'icon':'warnicon'
                     });
                     return;
                 }

                 var action = '';
                 if(submit_ == ''){
                     action = formDate.action;
                 } else {
                     action = submit_;
                 }
                 
                 var flag = true;
                 if(needValidate_){
                	 flag = Validator.Validate(formDate, checkLeve_);
                 }

                 if (flag) {
                     $C.setForm(formDate);
                     $C.asyncRequest("POST", action, {
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
				};
				
				function handleCancel() {
					this.cancel();
				};
			 
			 formDialog = new YAHOO.widget.Dialog(divId, 
				{ 
				  width : diaWidth_,
				  height: diaHeight_,
				  fixedcenter : true,
				  visible : false, 
				  modal : modal_,
				  close : closeIcon_,
				  zIndex : zIndex_,
				  constraintoviewport : false,
				  buttons : buttons_
				});
			 
			 
			 formDialog.render();
			 formDialog.show();
            
			return formDialog;
		}
	}
}();