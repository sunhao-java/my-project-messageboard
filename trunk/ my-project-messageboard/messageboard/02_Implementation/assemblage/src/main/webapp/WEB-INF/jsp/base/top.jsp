<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/includes.jsp"%>
<%
	request.setAttribute("contextPath", request.getContextPath());
%>
<html>
	<head>
		<title></title>
		<msg:css href="css/top.css"/>
		
		<script type="text/javascript">
			//获取当前时间
			function refresh_timer(){
			  var now = new Date();
			  var y = now.getFullYear();
			  var m = now.getMonth() + 1; 
			   m = m < 10 ? '0'+m : m;
			  var d = now.getDate();
			   d = d < 10 ? '0'+d : d;   
			  var H = now.getHours();
			   H = H < 10 ? '0'+H : H;
			  var i = now.getMinutes();
			   i = i < 10 ? '0'+i : i;
			  var s = now.getSeconds();
			   s = s < 10 ? '0'+s : s;
			   
			   var show_obj = document.getElementById('timer');
			   var show_str =  '' + y + '-' + m + '-' + d +" "+ H +':' + i +':' + s;
			   show_obj.innerHTML = "<b>" + show_str + "</b>"; 
			 }
			
			function get_time(){
			  setInterval(refresh_timer,1000);
			}
			
			get_time();
		</script>
	</head>
	<body>
		<div style="height:69px;overflow: hidden;">
			<div class="welcomeUser">
   				<table>
   					<tr>
   						<td>
							<span>
	   							<img src="../image/wiseduimg/module/clock.png">
								欢迎您，孙昊
							</span>
   						</td>
   						<td>
   							&nbsp;&nbsp;&nbsp;
   						</td>
   						<td>
   							<span>
   							这是您第10次登录本系统</span>
   						</td>
   						<td>
   							&nbsp;&nbsp;&nbsp;
   						</td>
   						<td>
   							<span>
   								您上次登录是2011年12月13日 19:24
   							</span>
   						</td>
   						<td>
   							&nbsp;&nbsp;&nbsp;
   						</td>
   						<td>
   							<span>
   								<span>■&nbsp;</span>系统时间：<span id="timer"></span>
   							</span>
   						</td>
   					</tr>
   				</table>
			</div>
	    	<div class="title" style=""></div>
	    	<div class="tool">
	            <a class="t3" title="个人首页" href="javaScript:linkFun('mainPage');"></a>
	            <a class="t1" title="个性设置"></a>
	            <a class="t2" title="收藏夹" href="javaScript:addBookmark();"></a>
	            <a class="t4" title="系统帮助"></a>
	            <a id="logout" class="t5" title="退出系统"></a>
	        </div>
		</div>
	</body>
</html>
