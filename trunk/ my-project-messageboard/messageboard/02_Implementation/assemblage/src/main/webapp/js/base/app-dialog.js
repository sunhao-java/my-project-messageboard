
/**
 * 封装YUI的弹窗组件
 * 依赖的js包在common_js.jsp中已被引入
 * @author sunhao(sunhao.java@gmail.com)
 */
YAHOO.namespace("app.dialog");
YAHOO.app.dialog = function(){
	var $C = YAHOO.util.Connect,dom = YAHOO.util.Dom,event = YAHOO.util.Event,$L=YAHOO.lang;
	var alertDialog;
	var _true = 'true';
	var _false = 'false';
	return{
		pop : function(args){
			var id_ = args.id || 'dialog' + new Date().getTime();	//dialog框的id
			
			var closeIcon_ = args.closeIcon || _true;				//右上角是否有关闭图标，默认是true
			
			var confirmButton_ = args.confirmButton || _true;		//是否有确定按钮，默认是true
			var confirmFunction_ = args.confirmFunction;			//点击确定按钮执行的函数
            var confirmBtn_ = args.confirmBtn || '确定';              //确定按钮的文字
			
			var cancelButton_ = args.cancelButton || _true;			//是否有取消按钮，默认是true
			var cancelFuncion_ = args.cancelFuncion; 				//关闭窗口时需要执行的函数
            var cancelBtn_ = args.cancelBtn || '取消';              //取消按钮的文字
			
			var dialogHead_ = args.dialogHead || "提示"; 		    //弹窗的head
			
			var modal_ = args.modal || _true;						//背景是否被灰化
			var draggable_ = args.draggable || _true;				//窗口是否能被移动
			
			var diaWidth_ = args.diaWidth || 300;					//弹窗宽度(默认300px)
			var diaHeight_ = args.diaHeight || 100;					//弹窗高度(默认100px)
			
			var alertMsg_ = args.alertMsg;							//弹窗显示内容（与reqUrl不能同时为空）
			var url_ = args.url;									//请求的URL（与alertMsg不能同时为空）
			var formId_ = args.formId || '';						//需要提交的表单ID
			var action_ = args.action;								//表单提交到的action，如果为空，则使用form中的action
			var afterRequest_ = args.afterRequest;					//表单提交之后执行的函数
			
			var zIndex_ = args.zIndex || 999;						//对应CSS属性值z-index,默认是4
			
			var autoClose_ = args.autoClose;						//是否自动关闭，为空表示手动关闭对话框
			
			var extBtn_ = args.extBtn;								//自定义扩展的按钮,默认为空
			
			/**
			 * B.ICON_BLOCK = "blckicon"; 
			 * B.ICON_ALARM = "alrticon"; 
			 * B.ICON_HELP = "hlpicon"; 
			 * B.ICON_INFO = "infoicon"; 
			 * B.ICON_WARN = "warnicon"; 
			 * B.ICON_TIP = "tipicon"; 
			 * B.ICON_CSS_CLASSNAME = "yui-icon"; 
			 * B.CSS_SIMPLEDIALOG = "yui-simple-dialog";
			 */
			var icon_ = args.icon;									//dialog中的图标,默认是alrticon
			
			if($L.isString(modal_)){
                modal_ = (modal_ == _true);
            }
			
			if($L.isString(closeIcon_)){
				closeIcon_ = (closeIcon_ == _true);
			}
			
			if($L.isString(confirmButton_)){
				confirmButton_ = (confirmButton_ == _true);
			}
			if($L.isString(cancelButton_)){
				cancelButton_ = (cancelButton_ == _true);
			}
			
			if($L.isString(draggable_)){
				draggable_ = (draggable_ == _true);
			}
			
			var buttons_ = [];
			if(confirmButton_ || cancelButton_){
				if(confirmButton_){
					if($L.isFunction(confirmFunction_)){
						buttons_.push({text:confirmBtn_,handler:confirmFunction_});
					} else {
						buttons_.push({text:confirmBtn_,handler:defaultConfirmFunction});
					}
				}
				if(cancelButton_){
					if($L.isFunction(cancelFuncion_)){
						buttons_.push({text:cancelBtn_,handler:cancelFuncion_});
					} else {
						buttons_.push({text:cancelBtn_,handler:defaultCancelFunction});
					}
				}
			}
			
			//如果有自定义扩展的按钮,则加进来
			if(!$L.isUndefined(extBtn_)){
				for(var i = 0; i < extBtn_.length; i++){
					buttons_.push(extBtn_[i]);
				}
			}
			
			if(icon_ == undefined){
				icon_ = "alrticon";
			}
			
			if(autoClose_ != undefined){
				var timeOut;
				if($L.isNumber(autoClose_)){
					timeOut = autoClose_;
				} else {
					timeOut = parseInt(autoClose_);
				}
				
				window.setTimeout(function(){
					if($L.isFunction(confirmFunction_)){
						confirmFunction_();
					} else {
						defaultConfirmFunction();
					}
				}, timeOut * 1000);
			}
			
			if(diaWidth_){
				if($L.isNumber(diaWidth_)){
					diaWidth_ = diaWidth_ + 'px';
				}
			}
			
			if(diaHeight_){
				if($L.isNumber(diaHeight_)){
					diaHeight_ = diaHeight_ + 'px';
				} else if($L.isString(diaHeight_) && diaHeight_ == 'auto'){
					diaHeight_ = null;
				}
			}
			
			function defaultConfirmFunction(){
				if(url_){
					if(formId_){
						var form = dom.get(formId_);
						if(!form){
							alert('formId配置错误，请检查！');
							return;
						}
						if(!action_)
							action_ = form.action;
						
						$C.setForm(form);
						$C.asyncRequest("POST", action_, {
							success: function(o){
								var _e = eval('(' + o.responseText + ')');
								if($L.isFunction(afterRequest_)){
									return afterRequest_(_e);
								} else {
									//不做
								}
							},
							failure: function(o){
								alert('网络错误！');
							}
						});
					} else {
						alert('缺少参数，请配置formId！');
					}
				} else {
					alertDialog.cancel();
				}
			}
			
			function defaultCancelFunction(){
				alertDialog.cancel();
			}
			
			if(url_){
				$C.asyncRequest("POST", url_, {
                    success : function(o){
						//begin
						if(o.responseText){
							alertDialog = new YAHOO.widget.SimpleDialog(id_, {
								modal: modal_,
								icon : icon_,
								visible: false,
								fixedcenter: true,
								constraintoviewport: true,
								width: diaWidth_,
								height: diaHeight_,
								role: "alertdialog",
								close : closeIcon_,
			                    closeIcon : 'none',
								buttons: buttons_,	
								text: o.responseText,
								draggable : draggable_,
								zIndex : zIndex_
							});
						}
			
			            alertDialog.render(document.body);
						alertDialog.setHeader(dialogHead_);	//头上显示文字
						alertDialog.show();
			
			            return alertDialog;
                    },
                    failure : function(o){
                        YAHOO.app.dialog.pop({'dialogHead':'提示','cancelButton':'false','alertMsg':'错误代码:' + o.status});
                    }
                 });
			}
			
			//begin
			if(alertMsg_){
				alertDialog = new YAHOO.widget.SimpleDialog(id_, {
					modal: modal_,
					icon : icon_,
					visible: false,
					fixedcenter: true,
					constraintoviewport: true,
					width: diaWidth_,
					height: diaHeight_,
					role: "alertdialog",
					close : closeIcon_,
                    closeIcon : 'none',
					buttons: buttons_,	
					text: alertMsg_,
					draggable : draggable_,
					zIndex : zIndex_
				});
	            alertDialog.render(document.body);
				alertDialog.setHeader(dialogHead_);	//头上显示文字
				alertDialog.show();
				
				dom.addClass(id_, 'dialog');
				
	            return alertDialog;
			}


		}
	};
}();