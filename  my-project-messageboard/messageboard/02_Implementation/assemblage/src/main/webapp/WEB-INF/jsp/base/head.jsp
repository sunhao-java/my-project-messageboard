<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/includes.jsp"%>
<msg:css href="css/head.css"/>
<style type="text/css">
	.buttonlist{
		text-indent:18px;
		border:0px;
		margin:0px;
		height:23px;
		line-height:23px;
		text-align:left;
		width:55px;
	}
	
	#header h2 a{
		outline: medium none;
	}
	
	#header h2 a:link, a:hover, a:visited, a:active{
		color: #0066CC !important;
		text-decoration: none;
	}
	
</style>
<div id="header">
	<span style="float:left">
		<img alt="" src="${contextPath }/
			<c:if test="${empty menu.menuIcon }">image/wiseduimg/clock.gif.png</c:if>
			<c:if test="${not empty menu.menuIcon }">${menu.menuIcon}</c:if>" width="32" height="32">
	</span>
	<h2>&nbsp;&nbsp;<%=request.getParameter("title") %></h2>
</div>

<p class="actions" style="margin: 5px;border:0px solid">
	<c:if test="${param.add eq 'true'}">
		<input class="buttonlist" type="button" onclick="addpagefunction();" value="增加" 
			style="background:url(${contextPath}/image/wiseduimg/module/button_add.gif) no-repeat left;cursor:pointer;"/>
	</c:if>
	<c:if test="${param.delete eq 'true'}">
		<input class="buttonlist" type="button" onclick="deleteFunction();" value="删除" 
			style="background:url(${contextPath}/image/wiseduimg/module/button_del.gif) no-repeat left;cursor:pointer;"/>
	</c:if>
</p>