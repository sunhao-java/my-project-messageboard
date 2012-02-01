<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/includes.jsp"%>

<msg:css href="css/base/pagination.css"/>

<c:if test="${paginationSupport.pageSize > 1}">
	<div class="paginationDiv">
		<c:if test="${paginationSupport.currentIndex ne 1}">
			<c:url var="url" value="${paginationAction }">
				<c:param name="page" value="${paginationSupport.startIndex }"/>
				<c:param name="num" value="${paginationSupport.num}"/>
			</c:url>
			<a href="${contextPath}/${url}">首页</a>
			<c:url var="url" value="${paginationAction }">
				<c:param name="page" value="${paginationSupport.previousIndex }"/>
				<c:param name="num" value="${paginationSupport.num}"/>
			</c:url>
			<a href="${contextPath}/${url}">&lt;上一页</a>
		</c:if>
		<c:forEach begin="${paginationSupport.startIndexOnShow}" end="${paginationSupport.endIndexOnShow}" step="1" var="page" varStatus="status">
			<c:url var="url" value="${paginationAction }">
				<c:param name="page" value="${page }"/>
				<c:param name="num" value="${paginationSupport.num}"/>
			</c:url>
			<c:choose>
				<c:when test="${paginationSupport.currentIndex eq page}">
					<strong>${page}</strong>
				</c:when>
				<c:otherwise>
					<a href="${contextPath}/${url}">${page}</a>
				</c:otherwise>
			</c:choose>
		</c:forEach>
		<c:if test="${paginationSupport.currentIndex ne paginationSupport.pageSize}">
			<c:url var="url" value="${paginationAction }">
				<c:param name="page" value="${paginationSupport.nextIndex }"/>
				<c:param name="num" value="${paginationSupport.num}"/>
			</c:url>
			<a href="${contextPath}/${url}">下一页&gt;</a>
			<c:url var="url" value="${paginationAction }">
				<c:param name="page" value="${paginationSupport.endIndex }"/>
				<c:param name="num" value="${paginationSupport.num}"/>
			</c:url>
			<a href="${contextPath}/${url}">尾页</a>
		</c:if>
	</div>
</c:if>