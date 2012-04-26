<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/includes.jsp"%>
<!-- 导航 -->
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
		<img alt="" src="${contextPath }/image/wiseduimg/clock.gif.png" width="32" height="32">
	</span>
	<ul class="navigation">&nbsp;&nbsp;
	<%
		String[] titles = request.getParameterValues("title");
		String[] links = request.getParameterValues("link");
		for(int i = 0; i < links.length; i++){
			out.print("<li>");
			out.print("<a href=\"" + links[i] + "\">" + titles[i] + "</a>");
			out.print("</li>");
			out.print("<li class=\"split\"></li>");
		}
		out.print("<li class=\"light\">" + titles[titles.length - 1] + "</li>");
	%>
	</ul>
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