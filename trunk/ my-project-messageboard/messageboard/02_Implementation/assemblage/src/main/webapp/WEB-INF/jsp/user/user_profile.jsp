<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/includes.jsp"%>

<!--
* 个人主页
* Developed By: sunhao
* Mail: sunhao.java@gmail.com
* Time: 12-8-16 下午10:04
* Version:1.0
* History:
-->

<msg:js src="js/jquery/jquery-1.4.2.min.js"/>
<msg:js src="js/jquery/easyloader.js"/>
<msg:css href="/css/profile.css"/>

<script type="text/javascript">
	$(document).ready(function(){
		using('simpleTip', function(){
			$("#tweetTxt").simpleTip({
				tip: '今天你动弹了吗？来吐槽一下吧。。。',                      
		    	color: '#545454',			
				size: '12px'	
			});
		});
		
		$('#tweetTxt').bind('keyup change', function(){
			var length = $(this).val().length;
			var num = 160 - length;
			
			if(num < 0) {
				var out = length - 160;
				$('#contentLength').parent('span').hide();

				if($('#outcontentLength').length != 0) {
					$('#outcontentLength').parent('span').show();
					$('#outcontentLength').html(out);
				} else {
					var span = $('<span class="r">已超出<em id="outcontentLength">' + out + '</em>字</span>');
					$('#contentLength').parent('span').before(span);
				}
				
				$('#publish').attr('disabled', 'disabled').addClass('disabled');
			} else {
				if($('#outcontentLength').length != 0) {
					$('#outcontentLength').parent('span').hide();
					$('#contentLength').parent('span').show();
				}
				
				$('#contentLength').html(num);
				$('#publish').attr('disabled', '').removeClass('disabled');
			}
		});
	});
</script>

<style type="text/css">
	
</style>

<div class="profile-main">
	<%--<div id="topbar">
		<div id="visitorInfo">
			<span id="message">			
				<a href="#" class="msgbox" title="进入我的站内信">
					你有<em>0</em>封站内信
				</a>			
			</span>
		</div>
		<div class="clear"></div>
	</div>--%>
	
	<div class="profile-content">
		<div class="content-left">
			<div class="owner">
				<a href="http://my.oschina.net/sunhaojava" class="head">
					<msg:head userId="${loginUser.pkId}" headType="2"/>
				</a>
    			<span class="action">
        			<a href="#" class="name" title="男">孙昊</a>
					<span class="opts">
						<img src="${contextPath }/image/male.png" align="absmiddle" title="男">
        				<a href="#">修改资料</a>
						<a href="#">更换头像</a>
        			</span>
    			</span>
    			<div class="clear"></div>
			    <div class="stat">
			    	<a href="#">好友(1)</a>
			    	<a href="#">博文(0)</a>
			    	<a href="#">积分(0)</a>
			    </div>
			</div>
			<div class="manager clearfix">
				<a class="a1 blog" href="#"><i>.</i><span>发表博文</span></a>
				<a class="a2 admin" href="#"><i>.</i><span>相册管理</span></a>
			</div>
			<div id="Fellows" class="mod users">
				<strong>好友</strong>
			    <ul class="clearfix">
		    		<li>
		    			<a title="${loginUser.truename}" href="#">
		    				<msg:head userId="${loginUser.pkId}" headType="2"/>
		    			</a>
		    		</li>
		    	</ul>
			    <div class="more">
			    	<a href="#">显示所有好友(1)</a>
			    </div>
		    </div>
		    <div class="mod">
				<strong>新浪微博秀<span id="connectBtn"></span></strong>
				<iframe width="220" height="400" class="share_self"  frameborder="0" scrolling="no" 
					src="http://widget.weibo.com/weiboshow/index.php?language=&width=220&height=400&fansRow=2&ptype=1&speed=0&skin=1&isTitle=0&noborder=0&isWeibo=1&isFans=0&dpc=1&uid=1895775795&verifier=e7e57dcb"></iframe>
			</div>
		</div>
		<div class="content-right">
			<div class="myblog clearfix">
				<span class="action"></span><a href="#">进入我的博客</a>
			</div>
			<div class="rmod">
				<strong>
					<a href="#">修改</a>我的信息
				</strong>
				<ul>
					<li><b class="inline">加入时间：</b><msg:formatDate value="${loginUser.createDate}" dateType="1"/></li>
			    	<li><b class="">地址：</b>${loginUser.address}</li>
			    	<li><b class="inline">电话：</b>${loginUser.phoneNum}</li>
			    	<li><b class="inline">QQ：</b>${loginUser.qq}</li>
			    	<li><b class="inline">Email：</b>${loginUser.email}</li>
			    	<li><b>主页：</b>${loginUser.homePage}</li>
			    </ul>
			</div>
			<div id="albums" class="mod albums">
				<strong>相册</strong>
			    <ul class="clearfix">
		    		<li>
		    			<a title="${loginUser.truename}" href="#">
		    				<msg:head userId="${loginUser.pkId}" headType="2"/>
		    			</a>
		    		</li>
		    	</ul>
			    <div class="more">
			    	<a href="#">显示所有相册(1)</a>
			    </div>
		    </div>
		</div>
		<div class="content-main">
			<div id="tweetForm">
				<div id="formTitle"><span class="r">还可以输入<em id="contentLength">160</em>字</span>今天你动弹了吗？</div>	
				<form id="tForm" action="#" method="POST">
					<input type="hidden" name="user" value="151849">
					<div id="tFormEditor">
						<textarea name="msg" id="tweetTxt"></textarea>
					</div>
					<div id="tFormOpts">
						<input type="submit" value="发 布" class="B" id="publish">
						<div style="clear:right;"></div>
					</div>
					
				</form>
			</div>
			<div class="mmod" id="Logs">
    			<ul class="tabs"> 
			    	<li class="active"><a href="#">好友动弹</a></li>
			    	<li><a href="#">我的动弹</a></li>
			    	<li><a href="#">博客</a></li>
			    	<li><a href="#">相册</a></li>
    	    	</ul>
    	    	<ul class="UserLogs" id="MyTweet">
		    		<li id="LI_1004071" class="Tweet log_type_100">
		    			<table class="ostable">
							<tr>
								<td class="TweetUser">
									<a href="#">
										<msg:head userId="${loginUser.pkId}" headType="2"/>
									</a>
								</td>
								<td class="TweetContent">
									<h5>
										<a href="#" class="user">红薯</a>
										<span class="action1">
											更新了动态
								    	</span>
									</h5>
									<div class="post">
										南京源创会最后一个主题Git，很快结束了，今天又爆场了！
									</div>
									<div class="bottom">
										<div class="opts">
											<a href="javascript:tweet_reply(1004071)" class="TweetReplyLnk">评论<span>(<span id="log_reply_count_1004071">2</span>)</span></a>
										</div>
										<div class="time">昨天(17:38) </div>
									</div>
								</td>
							</tr>
						</table>
					</li>
		        </ul>
		    </div>
		</div>
	</div>
</div>