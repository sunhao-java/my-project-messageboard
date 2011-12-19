<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:forEach items="${admins}" var="admin">
	<c:out value="${admin.pkId}"></c:out> ||
	<c:out value="${admin.username}"></c:out> ||
	<c:out value="${admin.password}"></c:out>
</c:forEach>