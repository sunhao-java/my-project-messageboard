<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	request.setAttribute("contextPath", request.getContextPath());
%>

<html>
  <head>
    <title>留言板</title>
    <link rel="shortcut icon" href="${contextPath }/favicon.ico" />
  </head>
  
	<frameset rows="70,*,50" height="100%" frameborder="0" framespacing="0"> 
		<frame name="header" src="${contextPath }/home/top.do" frameborder="0" scrolling="no">
		<frameset cols="155,*" height="100%" frameborder="0" framespacing="0" id="box" name="box">
			<frame id="menu" name="menu" src="${contextPath }/home/left.do" frameborder="0"  scrolling="no" noresize="noresize">
			<frame id="main" name="main" src="${contextPath }/home/index.do" frameborder="0" scrolling="auto" noresize="noresize">
		</frameset>
		<frame name="footer" src="${contextPath }/home/tail.do" frameborder="0" scrolling="no">
	</frameset>
  
	<noframes>
		<body>
		</body>
	</noframes>
</html>
