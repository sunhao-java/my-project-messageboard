<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/includes.jsp"%>
<%
	request.setAttribute("contextPath", request.getContextPath());
%>
<html>
	<head>
		<title></title>
		<msg:css href="js/yui/css/container.css"/>
		<msg:css href="js/yui/css/fonts-min.css"/>
		<!--[if lte IE 6]>
			<msg:js src="/js/base/ie-pngbug.js"/>
		<![endif]-->
		<style type="text/css">
			img{
				border-width: 0px !important;
			}
		</style>
	</head>
	<body class="yui-skin-sam">
		<tiles:insertAttribute name="content"/>
	</body>
</html>