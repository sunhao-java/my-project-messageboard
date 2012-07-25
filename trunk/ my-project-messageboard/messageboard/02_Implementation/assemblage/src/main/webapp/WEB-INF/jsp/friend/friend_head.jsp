<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/includes.jsp"%>

<div class="top">
	<ul>
		<li <c:if test="${current eq 'all' }">class="current"</c:if><c:if test="${current ne 'all' }">class="alt"</c:if>>
			<a href="${contextPath}/friend.do">全部好友</a>
		</li>
		<li <c:if test="${current eq 'invite' }">class="current"</c:if><c:if test="${current ne 'invite' }">class="alt"</c:if>>
			<a href="${contextPath}/friend/listInvitePrompt.do">邀请提示</a>
		</li>
		
		<li class="create<c:if test="${current eq 'add' }"> current</c:if>"<c:if test="${current ne 'add' }">class="alt"</c:if>>
			<a href="${contextPath}/friend/toAddFriend.do">添加好友</a>
		</li>
	</ul>
</div>