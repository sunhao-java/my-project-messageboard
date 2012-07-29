<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<html>
  <head><title>EHCache</title></head>
  <body>
  	<%
    	net.sf.ehcache.CacheManager.getInstance().clearAll();
	%>
  	缓存已清除！
  </body>
</html>