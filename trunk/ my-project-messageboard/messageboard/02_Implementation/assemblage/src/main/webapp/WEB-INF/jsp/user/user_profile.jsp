<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!--
* 好友的个人主页
* Developed By: sunhao
* Mail: sunhao.java@gmail.com
* Time: 12-8-16 下午10:04
* Version:1.0
* History:
-->
<%@ include file="/WEB-INF/jsp/common/includes.jsp"%>
<msg:js src="js/jquery/jquery-1.4.2.min.js"/>
<msg:js src="js/jquery/easyloader.js"/>
<msg:css href="/css/profile.css"/>
<msg:js src="js/base/app-dialog.js"/>

<script type="text/javascript">
	$(document).ready(function(){
		using(['reply'], function(){
			$('div.tweetContent').displayEmoticon();
		});
	});

	function gotoAlbumDetail(albumId){
		window.location.href = '${contextPath}/album/listPhotos.do?albumId=' + albumId + '&visit=true&uid=${user.pkId}';
	}
	
	function showOrHideReply(tweetId, reply){
        var replyDiv = YAHOO.util.Dom.get('replyList_' + tweetId);
        if(replyDiv.style.display != 'none'){
            replyDiv.innerHTML = '';
            replyDiv.style.display = 'none';
            return;
        }
        var action = "${contextPath}/reply/list.do?resourceId=" + tweetId + "&resourceType=4&reply=" + reply;
        YAHOO.util.Connect.asyncRequest('POST', action, {
            success: function(o){
                if(o.responseText){
                    replyDiv.innerHTML = o.responseText;
                    replyDiv.style.display = '';
                }
            },
            failure: function(o){
                alert('网络异常!');
            }
        });
    }
	
	function reply(panel){
        YAHOO.util.reply({
            title: '',
            content: panel.val(),
            resourceId: panel.attr('tweetId'),
            resourceType: '4',
            success: function(){
                window.location.reload(true);
            }
        });
    }
</script>

<div class="profile-main">
	<div id="topbar">
		<div id="visitorInfo">
			当前访客身份： ${loginUser.truename } [ <a href="${contextPath}/user/profile.do">我的主页</a> ]
		</div>
		<div class="clear"></div>
	</div>
	
	<div class="profile-content">
		<div class="content-left">
			<div class="owner">
				<a href="#" class="head">
					<msg:head userId="${user.pkId}" headType="2"/>
				</a>
    			<span class="action">
        			<a href="#" class="name">${user.truename }</a>
					<span class="opts">
						<c:if test="${user.sex == 0}">
							不男不女
						</c:if>
						<c:if test="${user.sex == 1}">
							<img src="${contextPath }/image/male.png" align="absmiddle" title="男">
						</c:if>
						<c:if test="${user.sex == 2}">
							<img src="${contextPath }/image/female.png" align="absmiddle" title="女">
						</c:if>
        			</span>
    			</span>
    			<div class="clear"></div>
			    <div class="stat">
			    	<a href="javaScript:void(0)">好友(${msgFun:length(friendIds)})</a>
			    	<a href="${contextPath}/message/inListMyMessageJsp.do?viewWhoId=${user.pkId}">博文(${blogNum})</a>
			    	<a href="${contextPath}/album/inUserAlbum.do?uid=${user.pkId}">相册(${msgFun:length(albums)})</a>
			    </div>
			</div>
			<div class="manager clearfix">
				<a class="a1 sendmsg" href="#"><i>.</i><span>发站内信</span></a>
				<a class="a2 blog" href="${contextPath}/message/inListMyMessageJsp.do?viewWhoId=${user.pkId}"><i>.</i><span>进入博客</span></a>
			</div>
			<div id="Fellows" class="mod users">
				<strong>好友</strong>
			    <ul class="clearfix">
			    	<c:forEach items="${friendIds}" var="f">
			    		<li>
			    			<c:choose>
			    				<c:when test="${f eq loginUser.pkId }">
			    					<a href="${contextPath }/user/profile.do">
					    				<msg:head userId="${f}" headType="2"/>
					    			</a>
			    				</c:when>
			    				<c:otherwise>
			    					<a href="${contextPath }/user/userProfile.do?uid=${f}">
					    				<msg:head userId="${f}" headType="2"/>
					    			</a>
			    				</c:otherwise>
			    			</c:choose>
			    		</li>
			    	</c:forEach>
		    	</ul>
			    <div class="more">
			    	<a href="javaScript:void(0)" class="disable">共有 ${msgFun:length(friendIds)} 个好友</a>
			    </div>
		    </div>
		    <c:if test="${not empty user.weiboUrl}">
		    	<div class="mod">
					<strong><c:if test="${user.weiboType eq 1}">新浪</c:if><c:if test="${user.weiboType eq 2}">腾讯</c:if>微博秀<span id="connectBtn"></span></strong>
					<iframe width="220" height="410" class="share_self" 
			 			frameborder="0" scrolling="no" src="${user.weiboUrl}"></iframe>
				</div>
		    </c:if>
		</div>
		<div class="content-right">
			<div class="myblog clearfix">
				<span class="action"></span>
				<a href="${contextPath}/message/inListMyMessageJsp.do?viewWhoId=${user.pkId}">
					进入<c:if test="${user.sex == 1}">他</c:if><c:if test="${user.sex == 2}">她</c:if>的博客
				</a>
			</div>
			<div class="rmod">
				<strong>
					<c:if test="${user.sex == 1}">他</c:if><c:if test="${user.sex == 2}">她</c:if>的信息
				</strong>
				<ul>
					<li><b class="inline">加入时间：</b><msg:formatDate value="${user.createDate}" dateType="1"/></li>
			    	<li><b class="">地址：</b>${user.address}</li>
			    	<li><b class="inline">电话：</b>${user.phoneNum}</li>
			    	<li><b class="inline">QQ：</b>${user.qq}</li>
			    	<li><b class="inline">Email：</b>${user.email}</li>
			    	<li><b>主页：</b>${user.homePage}</li>
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
			    	<a href="${contextPath}/album/inUserAlbum.do?uid=${user.pkId}">共有 ${msgFun:length(albums)} 个相册</a>
			    </div>
		    </div>
		</div>
		<div class="content-main">
			<div class="topBlogs">
				<msg:controller method="inListMyMessageJsp" module="message" type="invoke"/>
				<strong>
					<a href="${contextPath}/message/inListMyMessageJsp.do?viewWhoId=${user.pkId}">进入博客»</a>最新博文 (共${blogNum }篇)
				</strong>
				<ul>
					<c:forEach items="${blogs.items }" var="b">
						<li>
							<a href="${contextPath }/message/inDetailJsp.do?pkId=${b.pkId}">${b.title }</a>
							<span class="s">${b.replyNum }评</span>
							<span class="d">
								<msg:formatDate value="${b.createDate }" dateType="1"/>
							</span>
						</li>
					</c:forEach>
				</ul>
			</div>
			<div class="mmod" id="Logs">
    			<ul class="tabs">
			    	<li class="active">
			    		<a href="#" id="myTweetTab" tab-id="myTweet">
			    			<c:if test="${user.sex == 1}">他</c:if><c:if test="${user.sex == 2}">她</c:if>的动弹
			    		</a>
			    	</li>
    	    	</ul>
    	    	<ul class="UserLogs">
    	    		<c:forEach items="${tweets.items}" var="tweet">
			    		<li class="Tweet">
			    			<table class="tab-table">
								<tr>
									<td class="TweetUser">
										<a href="${contextPath }/user/profile.do?uid=${tweet.creator.pkId}">
											<msg:head userId="${tweet.creator.pkId}" headType="2"/>
										</a>
									</td>
									<td class="TweetContent">
										<h5>
											<a href="${contextPath }/user/profile.do?uid=${tweet.creator.pkId}" class="user">
												${tweet.creator.truename}
											</a>
											<span class="action1">
												更新了动态
									    	</span>
										</h5>
										<div class="post tweetContent">
											${tweet.content}
										</div>
										<div class="bottom">
											<div class="opts">
												<a href="#" class="reply" <c:if test="${tweet.replyNum gt 0 }">onclick="showOrHideReply('${tweet.pkId}', true)"</c:if>>
													评论
													<span>
														(<span>${tweet.replyNum}</span>)
													</span>
                                                </a>
											</div>
											<div class="time"><msg:formatDate value="${tweet.createTime}"/></div>
										</div>
                                        <div class="replyList" id="replyList_${tweet.pkId}" style="display: none"></div>
									</td>
								</tr>
							</table>
						</li>
    	    		</c:forEach>
    	    	</ul>
   	    	</div>
		</div>
	</div>
</div>