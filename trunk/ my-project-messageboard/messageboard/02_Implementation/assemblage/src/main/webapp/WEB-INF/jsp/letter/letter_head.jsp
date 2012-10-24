<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/includes.jsp"%>

<div class="top">
	<ul>
		<li <c:if test="${current eq 'inbox' }">class="current"</c:if><c:if test="${current ne 'inbox' }">class="alt"</c:if>>
			<a href="${contextPath}/letter/inbox.do">收件箱</a>
		</li>
		<li <c:if test="${current eq 'outbox' }">class="current"</c:if><c:if test="${current ne 'outbox' }">class="alt"</c:if>>
			<a href="${contextPath}/letter/outbox.do">发件箱</a>
		</li>
		
		<li class="create<c:if test="${current eq 'compose' }"> current</c:if>"<c:if test="${current ne 'compose' }">class="alt"</c:if>>
			<a href="${contextPath}/letter/compose.do">写站内信</a>
		</li>
	</ul>
</div>