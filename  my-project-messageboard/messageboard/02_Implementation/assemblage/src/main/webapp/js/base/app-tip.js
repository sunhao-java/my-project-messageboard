/**
 * 文本框提示，当文本框获取焦点并且文本框无内容显示提示，否则显示提示信息
 * User: sunhao
 * Date: 12-6-16
 * Time: 下午11:00
 * @author sunhao(sunhao.java@gmail.com)
 */
(function($){
    //定义$.tip和$.formTip(单个input框,整个form)
    $.tip = $.tip || {};
    $.formTip = $.formTip || {};

    //$.tip默认属性
    $.tip.defaults = {
        tip: '',                      //提示的文字
    	color: '#545454',				//提示文字颜色,默认是#545454
		size: '12px'					//提示文字大小,默认是12px
    }
    
    //$.formTip默认属性
    $.formTip.defaults = {
		color: '#545454',				//提示文字颜色,默认是#545454
		size: '12px'					//提示文字大小,默认是12px
    }

    /**
     * 对于单个input框的提示
     */
    $.fn.simpleTip = function(p){
        this.each(function(){
            p = $.extend({}, $.tip.defaults, p || {});
            var input = $(this);
            var pos = input.position();
            
            var tip = $("<span>" + p.tip + "</span>");
            tip.css("color", p.color).css("word-wrap", "break-word").css("cursor", "text")
                    .css("white-space", "nowrap").css("position", "absolute").css("font-style", "italic").css("font-size", p.size)
                    .css("display", "inline-block").css({"left": pos.left + 4, "top": pos.top + 4});
            tip.height(0);

            tip.bind('click', function(){
                $(tip).hide();
                input.focus();
            });

            input.bind('blur', function(){
                if(input.val() == '') {
                    $(tip).show();
                }
            });

            input.bind('focus', function(){
                if(input.val() == ''){
                    $(tip).hide();
                }
            });

            input.after(tip);
            //当文本框中有初始值时，隐藏提示信息
            if(input.val() != ''){
                tip.hide();
            }
        });
    };
    
    /**
     * 对于整个form的提示
     */
    $.fn.formTip = function(p){
    	p = $.extend({}, $.formTip.defaults, p || {});
    	var form = $(this);										//取得整个form表单
    	var fields = f.getTipFields(form);						//获取整个表单中所有的input或者textarea
    	fields.each(function(){
    		var tagName = this.tagName.toLowerCase();			//每个标签的tagName
    		if(tagName == 'input'){								//如果是input,则进行判断
    			var type = $(this).attr('type');				//input的type
    															//只有type是text或者file的input才进行tip提示
    			if(type == 'text' || type == 'file' || type == 'password'){			
    				f.runTip($(this), p);
    			}
    		}
    		if(tagName == 'textarea'){							//textarea都提示
    			f.runTip($(this), p);
    		}
    	});
    }
    
    var f = {
		getTipFields: function(form){
    		return form.find("input,textarea");					//遍历获取整个表单中所有的input或者textarea
    	},
    	runTip: function(field, p){
    		var tip = field.attr('tip');						//获得提示的文字
			if(tip){
				field.simpleTip({								//开始提示
					tip: tip,
					color: p.color,
					size: p.size
				});		
			}
    	}
    };
})(jQuery);