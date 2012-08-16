<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/includes.jsp" %>

<msg:css href="css/publish.css"/>

<div class="top">
	<ul>
		<li <c:if test="${current eq 'base' }">class="current"</c:if><c:if test="${current ne 'base' }">class="alt"</c:if>>
			<a href="${contextPath}/user/editUserInfo.do">修改基本信息</a>
		</li>
		<li <c:if test="${current eq 'head' }">class="current"</c:if><c:if test="${current ne 'head' }">class="alt"</c:if>>
			<a href="${contextPath}/user/inEditHead.do">修改头像</a>
		</li>
		<li <c:if test="${current eq 'privacy' }">class="current"</c:if><c:if test="${current ne 'privacy' }">class="alt"</c:if>>
			<a href="${contextPath}/privacy/inPrivacySetting.do">隐私设置</a>
		</li>
	</ul>
</div>