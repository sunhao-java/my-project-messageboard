<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!--
* 个人主页
* Developed By: sunhao
* Mail: sunhao.java@gmail.com
* Time: 12-8-16 下午10:04
* Version:1.0
* History:
-->

<%@ include file="/WEB-INF/jsp/common/includes.jsp"%>
<msg:js src="js/jquery/jquery-1.4.2.min.js"/>
<msg:js src="js/jquery/easyloader.js"/>
<msg:js src="js/jquery/jquery.cookie.js"/>
<msg:css href="/css/profile.css"/>
<msg:js src="js/base/app-dialog.js"/>

<script type="text/javascript">
	$(document).ready(function(){
		using(['simpleTip', 'emoticon'], function(){
			$("#tweetTxt").simpleTip({
				tip: '今天你动弹了吗？来吐槽一下吧。。。',                      
		    	color: '#545454',			
				size: '12px'	
			});
			
			$('#emoticon').emoticon({
				panel: 'tweetTxt'
			});
			
			$('div.tweetContent').displayEmoticon({
				contextPath: '${contextPath}'
			});
		});
		
		var tweetTxt = $.cookie('${loginUser.pkId}tweet');
		if(tweetTxt != null && tweetTxt != ''){
			$('#tweetTxt').val(tweetTxt);
			if(tweetTxt.length > 160){
				$('#publish').attr('disabled', 'disabled').addClass('disabled');
				$('#contentLength').parent('span').hide();
				var span = $('<span class="r">已超出<em id="outcontentLength">' + (tweetTxt.length - 160) + '</em>字</span>');
				$('#contentLength').parent('span').before(span);
			}
		}
		
		$('#tweetTxt').bind('keyup change', function(){
			$.cookie('${loginUser.pkId}tweet', $(this).val(), {expires: 7,path:'${contextPath}'});
			
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
		
		$('ul.tabs > li > a').click(function(){
			var tab = $(this);
			var tabs = $('ul.tabs > li > a');
			var tabId = tab.attr('tab-id');
			
			for(var i = 0; i < tabs.length; i++){
				var t = tabs[i];
				if(tabId != $(t).attr('tab-id')) {
					$('#' + $(t).attr('tab-id')).hide();
					$(t).parent('li').removeClass('active');
				}
			}
			$('#' + tabId).show();
			tab.parent('li').addClass('active');
		});
		
		$('#publish').click(function(){
			var content = $('#tweetTxt').val();
			if(content == '' || content.length > 160){
				alert('动弹内容不得为空或者超过160字!');
				return false;
			}
			
			$.ajax({
				url: '${contextPath}/tweet/save.do',
				type: 'post',
				dataType: 'json',
				data: 'content=' + content,
				success: function(o){
					if(o.status == 1){
						$.cookie('${loginUser.pkId}tweet', '', {expires: 7,path:'${contextPath}'});
						window.location.reload(true);
					} else {
						YAHOO.app.dialog.pop({
	                        'dialogHead':'提示',
	                        'cancelButton':'false',
	                        'alertMsg':'发布动弹失败!',
	                        'icon':'warnicon'
                     	});
					}
				},
				error: function(o){
					YAHOO.app.dialog.pop({
                       'dialogHead':'提示',
                       'cancelButton':'false',
                       'alertMsg':'发布动弹失败!',
                       'icon':'warnicon'
                    });
				}
			});
		});
	});
	
	function gotoAlbumDetail(albumId){
		window.location.href = '${contextPath}/album/listPhotos.do?albumId=' + albumId;
	}
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
				<a href="${contextPath}/user/inEditHead.do" class="head">
					<msg:head userId="${loginUser.pkId}" headType="2"/>
				</a>
    			<span class="action">
        			<a href="#" class="name" title="男">孙昊</a>
					<span class="opts">
						<img src="${contextPath }/image/male.png" align="absmiddle" title="男">
        				<a href="${contextPath}/user/editUserInfo.do">修改资料</a>
						<a href="${contextPath}/user/inEditHead.do">更换头像</a>
        			</span>
    			</span>
    			<div class="clear"></div>
			    <div class="stat">
			    	<a href="${contextPath}/friend.do">好友(${msgFun:length(friends)})</a>
			    	<a href="${contextPath}/message/inListMyMessageJsp.do">博文(0)</a>
			    	<a href="${contextPath}/album/index.do">相册(${msgFun:length(albums)})</a>
			    </div>
			</div>
			<div class="manager clearfix">
				<a class="a1 blog" href="${contextPath}/message/inPublishMessageJsp.do"><i>.</i><span>发表博文</span></a>
				<a class="a2 admin" href="${contextPath}/album/index.do"><i>.</i><span>相册管理</span></a>
			</div>
			<div id="Fellows" class="mod users">
				<strong>好友</strong>
			    <ul class="clearfix">
			    	<c:forEach items="${friends}" var="f">
			    		<li>
			    			<a title="${f.friendUser.truename}" href="#">
			    				<msg:head userId="${f.friendUser.pkId}" headType="2"/>
			    			</a>
			    		</li>
			    	</c:forEach>
		    	</ul>
			    <div class="more">
			    	<a href="${contextPath}/friend.do">共有 ${msgFun:length(friends)} 个好友</a>
			    </div>
		    </div>
		    <c:if test="${not empty loginUser.weiboUrl}">
		    	<div class="mod">
					<strong><c:if test="${loginUser.weiboType eq 1}">新浪</c:if><c:if test="${loginUser.weiboType eq 2}">腾讯</c:if>微博秀<span id="connectBtn"></span></strong>
					<iframe width="220" height="410" class="share_self" 
			 			frameborder="0" scrolling="no" src="${loginUser.weiboUrl}"></iframe>
				</div>
		    </c:if>
		</div>
		<div class="content-right">
			<div class="myblog clearfix">
				<span class="action"></span><a href="#">进入我的博客</a>
			</div>
			<div class="rmod">
				<strong>
					<a href="${contextPath}/user/editUserInfo.do">修改</a>我的信息
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
			    	<c:forEach items="${albums}" var="a">
			    		<li>
			    			<a href="javaScript:void(0);" title="${a.albumName}" onclick="gotoAlbumDetail('${a.pkId}');">
								<c:choose>
									<c:when test="${a.cover eq '/image/default.png'}">
										<img src="${contextPath}/image/a.gif"  style="opacity: 1; 
												background-image: url('${contextPath}/${a.cover}');"/>
									</c:when>
									<c:otherwise>
										<img src="${contextPath}/image/a.gif"  style="opacity: 1; 
												background-image: url('${contextPath}/photo.jpg?filePath=${a.cover}&width=54&height=54');"/>
									</c:otherwise>
								</c:choose>
								<div class="photo-num">
									${album.photoCount}
								</div> 
							</a>
			    		</li>
			    	</c:forEach>
		    	</ul>
			    <div class="more">
			    	<a href="${contextPath}/album/index.do">共有 ${msgFun:length(albums)} 个相册</a>
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
					<div class="extBtn">
						<a class="emoticon" id="emoticon" href="javascript:void(0);" title="插入表情"><i></i>表情<span></span></a>
					</div>
					<div id="tFormOpts">
						<input type="button" value="发 布" class="B" id="publish">
					</div>
					
				</form>
			</div>
			<div class="mmod" id="Logs">
    			<ul class="tabs">
			    	<li class="active"><a href="#" id="myTweetTab" tab-id="myTweet">我的动弹</a></li>
			    	<li><a href="#" id="friendTweetTab" tab-id="friendTweet">好友动弹</a></li>
			    	<li><a href="#" id="userBlogTab" tab-id="userBlog">博客</a></li>
			    	<li><a href="#" id="userAlbumsTab" tab-id="userAlbums">相册</a></li>
    	    	</ul>
    	    	<ul class="UserLogs" id="myTweet">
    	    		<msg:controller method="index" module="tweet" type="invoke"/>
    	    		<c:forEach items="${paginationSupport.items}" var="tweet" varStatus="status">
			    		<li class="Tweet" id="li_${tweet.pkId}">
			    			<table class="tab-table">
								<tr>
									<td class="TweetUser">
										<a href="${contextPath}/message/user/profile.do">
											<msg:head userId="${tweet.creator.pkId}" headType="2"/>
										</a>
									</td>
									<td class="TweetContent">
										<h5>
											<a href="${contextPath}/message/user/profile.do" class="user">${tweet.creator.truename}</a>
											<span class="action1">
												更新了动态
									    	</span>
									    	<span class="delete">
										    	<a href="javascript:void(0);" id="delete_${tweet.pkId}"
										    			rel="${contextPath}/tweet/delete.do?tweetId=${tweet.pkId}">
										    		删除
										    	</a>
										    	<script type="text/javascript">
										    		using('confirm', function(){
											    		$('#delete_${tweet.pkId}').confirm({
											    			confirmMessage: '确定要删除此条动弹吗？',
										                    isFormatMessage: false,
										                    removeElement: $('#li_${tweet.pkId}')
											    		});
										    		});
										    	</script>
									    	</span>
										</h5>
										<div class="post tweetContent">
											${tweet.content}
										</div>
										<div class="bottom">
											<%--<div class="opts">
												<a href="#" class="reply">评论<span>(<span id="">2</span>)</span></a>
											</div>--%>
											<div class="time"><msg:formatDate value="${tweet.createTime}"/></div>
										</div>
									</td>
								</tr>
							</table>
						</li>
    	    		</c:forEach>
		        </ul>
		        <ul class="UserLogs" id="friendTweet" style="display: none;">
    	    		<msg:controller method="listFriendsTweet" module="tweet" type="invoke"/>
    	    		<c:forEach items="${pagination.items}" var="tweet">
			    		<li class="Tweet">
			    			<table class="tab-table">
								<tr>
									<td class="TweetUser">
										<a href="#">
											<msg:head userId="${tweet.creator.pkId}" headType="2"/>
										</a>
									</td>
									<td class="TweetContent">
										<h5>
											<a href="#" class="user">${tweet.creator.truename}</a>
											<span class="action1">
												更新了动态
									    	</span>
										</h5>
										<div class="post tweetContent">
											${tweet.content}
										</div>
										<div class="bottom">
											<%--<div class="opts">
												<a href="#" class="reply">评论<span>(<span id="">2</span>)</span></a>
											</div>--%>
											<div class="time"><msg:formatDate value="${tweet.createTime}"/></div>
										</div>
									</td>
								</tr>
							</table>
						</li>
    	    		</c:forEach>
		        </ul>
		        <ul class="UserLogs" id="userBlog" style="display: none;">
    	    		<msg:controller method="index" module="tweet" type="invoke"/>
		    		<li id="LI_1004071" class="Tweet log_type_100">
		    			<table class="tab-table">
							<tr>
								<td class="TweetUser">
									<a href="#">
										<msg:head userId="${loginUser.pkId}" headType="2"/>
									</a>
								</td>
								<td class="TweetContent">
									<h5>
										<a href="#" class="user">博客</a>
										<span class="action1">
											更新了动态
								    	</span>
									</h5>
									<div class="post">
										南京源创会最后一个主题Git，很快结束了，今天又爆场了！
									</div>
									<div class="bottom">
										<div class="opts">
											<a href="#" class="reply">评论<span>(<span id="">2</span>)</span></a>
										</div>
										<div class="time">昨天(17:38) </div>
									</div>
								</td>
							</tr>
						</table>
					</li>
		        </ul>
		        <ul class="UserLogs" id="userAlbums" style="display: none;">
    	    		<msg:controller method="index" module="tweet" type="invoke"/>
		    		<li id="LI_1004071" class="Tweet log_type_100">
		    			<table class="tab-table">
							<tr>
								<td class="TweetUser">
									<a href="#">
										<msg:head userId="${loginUser.pkId}" headType="2"/>
									</a>
								</td>
								<td class="TweetContent">
									<h5>
										<a href="#" class="user">相册</a>
										<span class="action1">
											更新了动态
								    	</span>
									</h5>
									<div class="post">
										南京源创会最后一个主题Git，很快结束了，今天又爆场了！
									</div>
									<div class="bottom">
										<div class="opts">
											<a href="#" class="reply">评论<span>(<span id="">2</span>)</span></a>
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