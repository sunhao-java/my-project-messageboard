<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/includes.jsp"%>
<%@ include file="/WEB-INF/jsp/common/common_js.jsp" %>

<!--
* 好友列表 页面
* Developed By: sunhao
* Mail: sunhao.java@gmail.com
* Time: 12-7-24 下午10:56
* Version:1.0
* History:
-->

<msg:css href="/css/friend.css"/>

<style type="text/css">
	.il {
	    margin: 0 !important;
	    padding: 10px;
	}
</style>

<jsp:include page="/WEB-INF/jsp/base/head.jsp">
	<jsp:param value="联系人管理" name="title"/>
</jsp:include>

<div class="content01">
	<jsp:include page="friend_head.jsp"/>
	
	<div id="wrap">
		<ol class="il">
			<h3>
				共有 <span id="count">${paginationSupport.totalRow }</span> 个好友
			</h3>
			<c:forEach items="${paginationSupport.items}" var="friend" varStatus="status">
				<li id="li${status.index}">
					<div class="people">
						<p class="image">
							<msg:head userId="${friend.friendId}"/>
						</p>
						<table class="info">
							<caption>
								<a href="${contextPath}/user/userInfo.do?viewUserId=${friend.friendId}">
									<span title="${friend.friendUser.truename}" class="online_width">${friend.friendUser.truename }</span> </a>
							</caption>
							<tr>
								<!-- TODO -->
								<td>手机</td>
								<td>${friend.friendUser.phoneNum }</td>
							</tr>
							<tr>
								<td></td>
							</tr>
						</table>
						<ul class="actions">
							<li>
								<a class="agreeRequest" href="${contextPath}/user/userInfo.do?viewUserId=${friend.friendId}">查看好友</a>
							</li>
                            <li>
								<a class="denylRequest" id="deleteFriend${status.index}" href="javascript:void(0);"
									rel="#">删除好友</a>
							</li>
						</ul>
					</div>
				</li>
			</c:forEach>
		</ol>
	</div>

	<c:url var="paginationAction" value="friend/listMyReceiveInvite.do">
	</c:url>
	<%@ include file="/WEB-INF/jsp/common/pagination.jsp"%>
</div>