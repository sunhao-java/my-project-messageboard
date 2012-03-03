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
		<msg:css href="js/yui/css/button.css"/>
		<!--[if lte IE 6]>
			<msg:js src="/js/base/ie-pngbug.js"/>
		<![endif]-->
		<style type="text/css">
			img{
				border-width: 0px !important;
			}
			
			input[type="text"], 
			input[type="password"], 
			input[type="url"], 
			input[type="email"], 
			input.text, 
			input.title, 
			textarea {
			    -moz-border-bottom-colors: none;
			    -moz-border-image: none;
			    -moz-border-left-colors: none;
			    -moz-border-right-colors: none;
			    -moz-border-top-colors: none;
			    background: url("${contextPath}/image/wiseduimg/input_bg.gif") repeat-x scroll center top #FFFFFF;
			    border-color: #778899 #AABBCC #AABBCC #8899AA;
			    border-radius: 1px 1px 1px 1px;
			    border-style: solid;
			    border-width: 1px;
			    color: #000000;
			    padding: 4px 1px 3px 2px;
			}
			
			input[type="text"]:focus, 
			input[type="password"]:focus, 
			input[type="url"]:focus, 
			input[type="email"]:focus, 
			input.text:focus, 
			input.title:focus, 
			textarea:focus, 
			input:focus {
			    border-color: #FF8800;
			}
			
			.width200{
				width: 200px;
			}
			
			.width250{
				width: 250px;
			}
		</style>
	</head>
	<body class="yui-skin-sam">
		<tiles:insertAttribute name="content"/>
	</body>
</html>