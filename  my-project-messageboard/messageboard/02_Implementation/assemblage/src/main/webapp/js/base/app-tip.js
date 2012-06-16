/**
 * 文本框提示，当文本框获取焦点并且文本框无内容显示提示，否则显示提示信息
 * User: sunhao
 * Date: 12-6-16
 * Time: 下午11:00
 * @author sunhao(sunhao.java@gmail.com)
 */
(function($){
    //定义$.tip
    $.tip = $.tip || {};

    //默认属性
    $.tip.defaults = {
        title : ''                      //提示的文字
    }

    $.fn.simpleTip = function(p){
        this.each(function(){
            p = $.extend({}, $.tip.defaults, p || {});
            var input = $(this);

            var pos = input.position();

            
            var tip = $("<span>" + p.tip + "</span>");

            tip.css("color", "#545454").css("word-wrap", "break-word").css("cursor", "text")
                    .css("white-space", "nowrap").css("position", "absolute").css("font-style", "italic").css("font-size", "12px")
                    .css("display", "inline-block").css({"left": pos.left + 1, "top": pos.top + 2});
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
        });
    };
})(jQuery);