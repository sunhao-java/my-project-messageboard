<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/includes.jsp"%>
<%@ include file="/WEB-INF/jsp/common/common_js.jsp" %>
<%
	request.setAttribute("contextPath", request.getContextPath());
%>
<html>
	<head>
		<title></title>
		<msg:js src="js/base/base.js"/>
		<msg:css href="js/yui/css/container.css"/>
		<msg:css href="js/yui/css/fonts-min.css"/>
		<msg:css href="js/yui/css/button.css"/>
        <msg:css href="css/main.css"/>
        <msg:js src="resource/index.do"/>
		<!--[if lte IE 6]>
			<msg:js src="/js/base/ie-pngbug.js"/>
		<![endif]-->
		
		<style type="text/css">
			.no-scroll{
				overflow-x: hidden;
				overflow-y: auto; 
			}
		</style>
	</head>
	<body class="no-scroll yui-skin-sam">
		<tiles:insertAttribute name="content"/>
	</body>
</html>