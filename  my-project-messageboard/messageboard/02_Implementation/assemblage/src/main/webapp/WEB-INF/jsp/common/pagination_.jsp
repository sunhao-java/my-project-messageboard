<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/includes.jsp"%>

<msg:css href="css/base/pagination.css"/>

<c:if test="${paginationSupport_.pageSize > 1}">
	<div class="paginationDiv">
		<c:if test="${paginationSupport_.currentIndex ne 1}">
			<c:url var="url_" value="${paginationAction_ }">
				<c:param name="page_" value="${paginationSupport_.startIndex }"/>
				<c:param name="num_" value="${paginationSupport_.num}"/>
			</c:url>
			<a href="${contextPath}/${url_}">首页</a>
			<c:url var="url_" value="${paginationAction_ }">
				<c:param name="page_" value="${paginationSupport_.previousIndex }"/>
				<c:param name="num_" value="${paginationSupport_.num}"/>
			</c:url>
			<a href="${contextPath}/${url_}">&lt;上一页</a>
		</c:if>
		<c:forEach begin="${paginationSupport_.startIndexOnShow}" end="${paginationSupport_.endIndexOnShow}" step="1" var="page_" varStatus="status_">
			<c:url var="url_" value="${paginationAction_ }">
				<c:param name="page_" value="${page_ }"/>
				<c:param name="num_" value="${paginationSupport_.num}"/>
			</c:url>
			<c:choose>
				<c:when test="${paginationSupport_.currentIndex eq page_}">
					<strong>${page_}</strong>
				</c:when>
				<c:otherwise>
					<a href="${contextPath}/${url_}">${page_}</a>
				</c:otherwise>
			</c:choose>
		</c:forEach>
		<c:if test="${paginationSupport_.currentIndex ne paginationSupport_.pageSize}">
			<c:url var="url_" value="${paginationAction_ }">
				<c:param name="page_" value="${paginationSupport_.nextIndex }"/>
				<c:param name="num_" value="${paginationSupport_.num}"/>
			</c:url>
			<a href="${contextPath}/${url_}">下一页&gt;</a>
			<c:url var="url_" value="${paginationAction_ }">
				<c:param name="page_" value="${paginationSupport_.endIndex }"/>
				<c:param name="num_" value="${paginationSupport_.num}"/>
			</c:url>
			<a href="${contextPath}/${url_}">尾页</a>
		</c:if>
	</div>
</c:if>