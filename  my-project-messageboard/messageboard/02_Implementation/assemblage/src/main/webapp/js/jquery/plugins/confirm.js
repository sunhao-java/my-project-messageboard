/**
 * 删除时的确认提示.
 * User: sunhao
 * Date: 12-7-22
 * Time: 下午2:09
 */
(function($){
    $.confirm = $.confirm || {};

    $.confirm.defaults = {
        header: "",                         // 弹出提示框的头显示文字，默认为空字串
        confirmMessage: "确定删除该%s吗？",// 提示框显示内容，格式："确定删除该%s吗？",默认替换为'项'
        isFormatMessage: true,             // 是否格式化显示的信息，默认为true，格式化显示的信息
        isDelFoot: true,                    // 是否是删除操作（包含“删除”和“取消”），默认为true，false则只为确认提示（“确认”和“取消”）
        removeElement: null,                // 删除操作后，动态删除列表元素的node name，一般为li或者tr, 为空则表示不需要删除页面元素
        confirmFunc: null,                  // 点击“确认”后，需要执行的js function
        cancelFunc: null,                    // 点击“取消”后，需要执行的js function
        customSucTip: null,					//自定义的成功提示消息
        customErrTip: null,					//自定义的失败提示消息
        handleData: null,                    //自定义处理一些参数的函数,为组件提供一些无法从外部获取的数据，返回形式如：message=hello&name=sunhao
        beforeSubmit: null,					//提交操作之前做的事(比如说一些数据合法性的验证)
        width: 250,         				//宽度
        height: 52							//高度
    }

    $.confirm.linkConfirm = $.confirm.linkConfirm || null;

    $.fn.confirm = function(p){
        this.each(function(){
            p = $.extend({}, $.confirm.defaults, p || {});
            var element = $(this);

            var f = {
                getBody: function(){
                    if(!p.isFormatMessage){
                        return '<h3>' + p.confirmMessage + '</h3>';
                    } else {
                        return '<h3>' + "确定删除该%s吗？".replace(/%s/, p.confirmMessage) + '</h3>';
                    }
                },
                getDelFoot: function(){
                    var button = ""
                    if(p.isDelFoot){
                        button = "删除";
                    } else {
                        button = "确定";
                    }

                    return "<p class=\"act\">" +
                            "   <input type=\"button\" class=\"f-button f-confirm confirm\" value=\"" + button + "\" id=\"buttonDel\"/>" +
                            "   <input type=\"button\" class=\"f-button f-cancel close\" value=\"取消\"/>" +
                            "</p>" +
                            "<div id=\"decor\" class=\"decor\"></div>";
                },
                getOffset: function(){
                    var offset = element.offset();
                    var top = offset.top - (p.height + 33);
                    var left = offset.left - (p.width * 0.8);
                    var width = $(window).width();
                    var height = $(window).height();
                    if(left < p.width){
                        left = 0;
                        $("#decor").css("left", offset.left + 5);
                    }
                    if(width - left < p.width){
                        left = width - 275;
                        $("#decor").css("left", 240);
                    }
                    if(top < p.height){
                        top = offset.top + 32;
                        $("#decor").removeClass("decor").addClass("decor1").css("bottom", p.height + 20 + "px");
                    }
                    
                    return [left, top];
                },
                getLinkConfirm: function(){
                    $.confirm.linkConfirm = new YAHOO.widget.Overlay("confirm",
                    {   xy: this.getOffset(),
                        visible:false,
                        width:p.width,
                        height:p.height
                    });
                    $.confirm.linkConfirm.setHeader(p.header);
                    $.confirm.linkConfirm.setBody(this.getBody());
                    $.confirm.linkConfirm.setFooter(this.getDelFoot());
                    $.confirm.linkConfirm.render(document.body);
                    this.getOffset();
                    $.confirm.linkConfirm.show();
                    this.addCancelEvent();
                    this.addConfirmEvent();
                },
                addCancelEvent: function(){
                    $('input[type=button].close').click(function(){
                        $.confirm.linkConfirm.hide();
                        if(p.cancelFunc){
                            return p.cancelFunc($.confirm.linkConfirm);
                        }
                    });
                },
                addConfirmEvent: function(){
                    $('input[type=button].confirm').click(function(){
                        if(p.confirmFunc){
                            return p.confirmFunc($.confirm.linkConfirm);
                        } else {
                            var url = element.attr('rel');
                            var flag = true;
                            if(p.beforeSubmit){
                            	flag = p.beforeSubmit(element.attr('id'));
                            }
                            if(url && flag){
                                f.asyncPost(url);
	                            $.confirm.linkConfirm.hide();
                            }
                        }
                    });
                },
                asyncPost: function(url){
                    $.ajax({
                        type: 'post',
                        url: url,
                        dataType: 'json',
                        data: f.handleData(),
                        success: function(data){
                            try{
                                if(data.status == 1){
                                    if(p.removeElement){
                                        //是删除操作，并且要移出元素不为空
                                        p.removeElement.hide();
                                        if(p.customSucTip){
                                        	return p.customSucTip(data.status);
                                        } else {
                                            if(p.isDelFoot)
	                                            f.showTip('suc', p.removeElement);
                                        }
                                    }
                                } else {
                                    if(p.customErrTip){
                                    	return p.customErrTip(data.status);
                                    } else {
                                        f.showTip('err', p.removeElement);
                                    }
                                }
                            } catch(e){
                                
                            }
                        },
                        error: function(data){
                            alert('错误信息:' + data.status);
                        }
                    });
                },
                showTip: function(type, element){
                    var tip = window.document.createElement('span');
                    tip.id = 'tip' + element.attr('id');
                    tip.innerHTML = type == 'suc' ? '删除成功' : '删除失败';
                    tip.className = type == 'suc' ? 'sucTip' : 'errTip';
                    element.before(tip);
                    setTimeout(function() {
                        $('#tip' + element.attr('id')).hide();
                    }, 500);
                },
                handleData: function(){
                    if(p.handleData){
                        return p.handleData(element.attr('id'));
                    } else {
                        return "";
                    }
                }
            }

            //元素添加点击事件
            element.click(function(){
                f.getLinkConfirm();
            });
            
            //添加鼠标点击事件,如果鼠标不是在表情弹框中点击,则弹框隐藏
//			$(window).bind('mousedown', function(e){
//				var x = e.pageX;			//left
//				var y = e.pageY;			//top
//				var offset = f.getOffset();
//				
//				if(!((x > offset[0] && x < offset[0] + p.width) && (y > offset[1] && y < offset[1] + p.height))){
//					if($.confirm.linkConfirm)
//						$.confirm.linkConfirm.hide();
//				}
//			});
        });
    }
})(jQuery)

/**
 * 显示提示信息
 *
 * @param type      suc:成功信息;err:失败信息
 * @param pkId      附件pkId
 */
function showTip(type, element, elementId){

}