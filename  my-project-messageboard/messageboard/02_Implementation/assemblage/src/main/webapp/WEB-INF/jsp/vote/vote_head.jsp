<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/includes.jsp"%>

<div class="top">
	<ul>
		<li <c:if test="${current eq 'list' }">class="current"</c:if><c:if test="${current ne 'list' }">class="alt"</c:if>>
			<a href="${contextPath}/vote/listVote.do">全部投票列表</a>
		</li>
		<li <c:if test="${current eq 'myVote' }">class="current"</c:if><c:if test="${current ne 'myVote' }">class="alt"</c:if>>
			<a href="${contextPath}/vote/listMyVote.do">我的投票</a>
		</li>
		<%--<li <c:if test="${current eq 'friendVote' }">class="current"</c:if><c:if test="${current ne 'friendVote' }">class="alt"</c:if>>
			<a href="${contextPath}/vote/listMyVote.do">好友的投票</a>
		</li>--%>
		
		<li class="create<c:if test="${current eq 'create' }"> current</c:if>"<c:if test="${current ne 'create' }">class="alt"</c:if>>
			<a href="${contextPath}/vote/createVote.do">发起新投票</a>
		</li>
	</ul>
</div>