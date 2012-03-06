<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/includes.jsp" %>
<%
    String contextPath = request.getContextPath();
    response.sendRedirect(contextPath + "/guest/error.do");
%>