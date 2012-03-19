
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
			var id_ = args.id || '';								//dialog框的id
			
			var closeIcon_ = args.closeIcon || _true;				//右上角是否有关闭图标，默认是true
			
			var confirmButton_ = args.confirmButton || _true;		//是否有确定按钮，默认是true
			var confirmFunction_ = args.confirmFunction;			//点击确定按钮执行的函数
            var confirmBtn_ = args.confirmBtn || '确定';              //确定按钮的文字
			
			var cancelButton_ = args.cancelButton || _true;			//是否有取消按钮，默认是true
			var cancelFuncion_ = args.cancelFuncion; 				//关闭窗口时需要执行的函数
            var cancelBtn_ = args.cancelBtn || '取消';                //取消按钮的文字
			
			var dialogHead_ = args.dialogHead || "提示"; 		    //弹窗的head
			
			var modal_ = args.modal || _true;						//背景是否被灰化
			var draggable_ = args.draggable || _true;				//窗口是否能被移动
			
			var diaWidth_ = args.diaWidth || 300;					//弹窗宽度(默认300px)
			var diaHeight_ = args.diaHeight || 100;					//弹窗高度(默认100px)
			
			var alertMsg_ = args.alertMsg;							//弹窗显示内容（与reqUrl不能同时为空）
			var reqUrl_ = args.reqUrl;								//请求的URL（与alertMsg不能同时为空）
			
			var zIndex_ = args.zIndex || 999;						//对应CSS属性值z-index,默认是4
			
			var autoClose_ = args.autoClose;						//是否自动关闭，为空表示手动关闭对话框
			
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
			var icon_ = args.icon || "alrticon";					//dialog中的图标,默认是alrticon
			
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
				}
			}
			
			function defaultConfirmFunction(){
				alertDialog.cancel();
				var masks = dom.getElementsByClassName('mask', 'div');
				var panels = dom.getElementsByClassName('yui-simple-dialog', 'div');
				for(var i = 0; i < masks.length; i++){
					masks[i].style.display = 'none';
				}
				for(var i = 0; i < panels.length; i++){
					panels[i].style.visibility = 'hidden';
				}
				dom.get('_yuiResizeMonitor').style.visibility = 'hidden';
			}
			
			function defaultCancelFunction(){
				alertDialog.cancel();
				var masks = dom.getElementsByClassName('mask', 'div');
				var panels = dom.getElementsByClassName('yui-simple-dialog', 'div');
				for(var i = 0; i < masks.length; i++){
					masks[i].style.display = 'none';
				}
				for(var i = 0; i < panels.length; i++){
					panels[i].style.visibility = 'hidden';
				}
				dom.get('_yuiResizeMonitor').style.visibility = 'hidden';
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
			}
			
			alertDialog.setHeader(dialogHead_);	//头上显示文字
			alertDialog.render(document.body);
			alertDialog.show();

            return alertDialog;
		}
	};
}();