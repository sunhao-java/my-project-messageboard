<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/includes.jsp"%>
<msg:css href="css/head.css"/>
<div id="header">
	<span style="float:left">
		<img alt="" src="${contextPath }/image/wiseduimg/clock.gif.png" width="32" height="32">
	</span>
	<h2>&nbsp;&nbsp;<%=request.getParameter("title") %></h2>
</div>