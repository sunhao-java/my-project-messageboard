/**
 * 表情组件
 * 
 * User: sunhao
 * Date: 12-08-22
 * Time: 下午07:43
 * @author sunhao(sunhao.java@gmail.com)
 */
(function($){
	//定义组件
	$.emoticon = $.emoticon || {};
	//定义展示时的组件
	$.displayEmoticon = $.displayEmoticon || {};
	
	//定义组件的默认值
	$.emoticon.defaults = {
		id: 'emoticon1',							//定义组件的ID
		width: 390,									//组件宽度,默认390
		height: 220,								//组件高度,默认220
		preview: true,								//是否预览表情,默认为true
		panel: ''									//目标容器ID
	}
	
	//定义展示组件的默认值
	$.displayEmoticon.defaults = {
		allowArea: 'p|span|div'
	}
	
	$.emoticon.panel = $.emoticon.panel || null;	//表情面板
	
	$.fn.emoticon = function(p){
		this.each(function(){
			p = $.extend({}, $.emoticon.defaults, p || {});
			var element = $(this);
			
			var f = {
				getBody: function(start, num, page){
					//start 开始条数
					//num	偏移量
					var end = start + num + 1;
					var html = '';
					html += '<div>';
					html += '	<div class="qz_emotion_bd" style="">';
					html += '		<div class="qzfl_emotion_page_' + page + '" style="height: 190px;">';
					html += '			<ul class="qzfl_emotion_default" style="margin-top: 0px; margin-bottom: 0px; padding-left: 0px;">';
					
					for(var i = start; i < end; i++){
						var link = '<a data-id="' + i + '" href="javascript:void(0);" id="e' + i + '"><div class="icon"></div></a>';
						html += '			<li>';
						html += '				<a data-id="e' + i + '" href="javascript:void(0);" id="e' + i + '" class="singleEmoticon">';
						html += '					<div class="icon"></div>';
						html += '				</a>';
						html += '			</li>';
					}
					
					html += '			</ul>';
					html += '		</div>';
					html += '	</div>';
					html += '</div>';
				
					return html;
				},
				getOffset: function(){
                    var offset = element.offset();
                    var top = offset.top - (p.height + 33);
                    var left = offset.left - (p.width * 0.8);
                    var width = $(window).width();
                    var height = $(window).height();
                    
                    if(top < p.height){
                    	top = offset.top + 25;
                        $("#decor").removeClass("decor").addClass("decor1").css("bottom", p.height + 10 + "px");
                    }
                    if(left < p.width * 0.8){
                    	left = 0;
                    	$("#decor").css("left", offset.left + 5);
                    }
                    if(width - left < p.width){
                    	left = width - 275;
                    	$("#decor").css("left", 240);
                    }
                    
                    return [left, top];
                },
                getDelFoot: function(){
                	var html = '';
                	html += '		<div class="qz_emotion_magic_ft">';
					html += '			<div class="qz_page_nav">';
					html += '				<input type="hidden" id="page" value="1"/>';
					html += '				<a href="javascript:void(0);" class="ui_mr5 prePage">上一页</a>';
					html += '					<span class="page_info ui_mr5"><span id="nowPage">1</span>/6</span>';
					html += '				<a href="javascript:void(0);" class="ui_mr5 nextPage">下一页</a>';
					html == '			</div>';
					html += '		</div>';
					html += '<div id=\"decor\" class=\"decor\"></div>';
                    return html;
                },
                insertIntoPanel: function(show){
                	var dataId = show.attr('data-id');
                	var show = '[' + dataId + ']';
                	
                	if(!p.panel){
                		alert('目标容器ID不能为空!');
                		return;
                	}
                	var panel_ = $('#' + p.panel);
                	if(panel_ == null || panel_.length == 0){
                		alert('目标容器ID错误!');
                		return;
                	}
                	var oldTxt = panel_.val();
                	panel_.focus();
                	panel_.val(oldTxt + show);
                },
                addEmoticonEvent: function(){
                	//为每个表情添加点击事件
                	$('a.singleEmoticon').click(function(){
                		f.insertIntoPanel($(this));
                	});
                },
                addPageEvent: function(link){
                	//上一页的事件
                	$('a.prePage').bind('click', function(){
                		//上一页事件
                		var page = parseInt($('#page').val());
                		if(page != 1){
                			//将"下一页"启用
            				$('a.nextPage').removeClass('disable');
                			if(page == 2){
                				//显示1页
                				$.emoticon.panel.setBody(f.getBody(100, 104, 1));
                				//将"上一页"禁用
                				$('a.prePage').addClass('disable');
                			} else if(page != 1){
                				//显示page-1页
                				$.emoticon.panel.setBody(f.getBody(7000 + (page - 3) * 105, 104, page - 1));
                			} else {
                				
                			}
                			
	                		$('#page').val(page - 1);
	                		$('#nowPage').html(page - 1);
	                		f.addEmoticonEvent();
                		}
                	});
                	
                	//下一页事件
                	$('a.nextPage').bind('click', function(){
                		//下一页事件
                		var page = parseInt($('#page').val());
                		if(page != 6){
                			//将"上一页"启用
            				$('a.prePage').removeClass('disable');
	                		if(page != 5){
	                			//显示page+1页
	                			$.emoticon.panel.setBody(f.getBody(7000 + (page - 1) * 105, 104, page + 1));
	                		} else if(page == 5){
	                			//显示6页
	                			$.emoticon.panel.setBody(f.getBody(7420, 30, 6));
	                			//将"下一页"禁用
            					$('a.nextPage').addClass('disable');
	                		} else {
	                			
	                		}
	                		
	                		$('#page').val(page + 1);
	                		$('#nowPage').html(page + 1);
	                		f.addEmoticonEvent();
                		} else {
                			$('a.nextPage').addClass('disable');
                		}
                	});
                },
                hidePanel: function(){
                	if($.emoticon.panel){
                		$.emoticon.panel.hide();
                	}
                },
                show: function(){
                	$.emoticon.panel = new YAHOO.widget.Overlay(p.id,
                    {   xy: f.getOffset(),
                        visible:false,
                        width:p.width,
                        height:p.height
                    });
                    $.emoticon.panel.setHeader('');
                    $.emoticon.panel.setBody(f.getBody(100, 104, 1));
                    $.emoticon.panel.setFooter(f.getDelFoot());
                    $.emoticon.panel.render(document.body);
                    
                    this.getOffset();
                    $.emoticon.panel.show();
                    //将"上一页"禁用
    				$('a.prePage').addClass('disable');
                    f.addEmoticonEvent();
                    f.addPageEvent();
                }
			}
			
			//元素添加点击事件
			element.bind('click', function(){
				f.show();
			});
			
			//添加鼠标点击事件,如果鼠标不是在表情弹框中点击,则弹框隐藏
			$(window).bind('mousedown', function(e){
				var x = e.pageX;			//left
				var y = e.pageY;			//top
				var offset = f.getOffset();
				if($('#' + p.id).css('visibility') || $('#' + p.id).css('visibility') == 'visible'){
					if(!((x > offset[0] && x < offset[0] + p.width) && (y > offset[1] && y < offset[1] + p.height))){
						if($.emoticon.panel)
							$.emoticon.panel.hide();
					}
				}
			});
		});
	}
	
	$.fn.displayEmoticon = function(p){
		this.each(function(){
			p = $.extend({}, $.displayEmoticon.defaults, p || {});
			
			//文字所在区域
			var element = $(this);
			//这个区域的标签
			var tagName = this.tagName.toLowerCase();
			
			
			var g = {
				display: function(){
					var areas = p.allowArea.split('|');
					var isContain = YAHOO.util.contain(tagName, areas);
					
					if(!isContain)
						return false;
					//取出域中的内容
					var html = element.html();
					//取出表情的正则表达式
					var reg = /\[e[0-9]{3,4}\]/g;
					
					var emoticons = html.trim().match(reg);
					html = g.makeImage(emoticons, html);
					
					element.html(html);
				},
				makeImage: function(emoticons, html){
					if(!emoticons){
						return;
					}
					
					for(var i = 0; i < emoticons.length; i++){
						var icon = emoticons[i];
						
						var reg = /e[0-9]{3,4}/g;
						var name = icon.match(reg)[0];
						var icon = YAHOO.util.getEmoticonTitle(name);
						
						if(!icon)
							continue;
						
						var img = '<img src="' + YAHOO.util.getContextPath() + '/js/jquery/css/emoticon/images/' +
                                icon[0] + '/' + name + '.gif" title="' + icon[1] + '">';
						
						html = html.replace('[' + name + ']', img);
					}
					
					return html;
				}
			}
			
			g.display();
		});
	}
})(jQuery)