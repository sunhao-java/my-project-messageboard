<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/includes.jsp"%>
<%@ include file="/WEB-INF/jsp/common/common_js.jsp" %>
<%
	request.setAttribute("contextPath", request.getContextPath());
%>
<html>
	<head>
		<title></title>
		<msg:css href="css/top.css"/>
		
		<msg:js src="js/jquery/jquery-1.4.2.min.js"/>
		<msg:js src="js/jquery/jquery.easyui.min.js"/>
		
		<msg:css href="themes/default/easyui.css"/>
		<msg:css href="themes/icon.css"/>
				
		<msg:css href="css/colortip-1.0-jquery.css"/>
		<msg:js src="js/jquery/colortip-1.0-jquery.js"/>
		
		<msg:js src="js/base/commfunction.js"/>
		
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
			
			$(document).ready(function(){
				//调用公共JS自动提示组件
				showUser('yellow','300px','wrap');
				
				$('#layout').bind('click', function(){
					var link = $(this);
					//var main = top.window.parent.frames['mainFrameSet'];
					var main = parent.document.getElementById('mainFrameSet');
					if(link.hasClass('layout-button-up')){
						link.removeClass('layout-button-up');
						link.addClass('layout-button-down');
						//link.parent('div').addClass('left0');
						$('.welcomeuser').css('margin-top', '0px');
						link.attr('title', '向下展开');
						main.rows = '25,*,50';
					} else {
						link.removeClass('layout-button-down');
						link.addClass('layout-button-up');
						//link.parent('div').removeClass('left0');
						$('.welcomeuser').css('margin-top', '39px');
						link.attr('title', '向上收缩');
						main.rows = '70,*,50';
					}
				});
			});
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
								欢迎您，<msg:cutWord length="6" endString="..." cutString="${user.truename}"/>
							</span>
   						</td>
   						<td>
   							&nbsp;&nbsp;&nbsp;
   						</td>
   						<td>
   							<span>
   							这是您第
   								<c:if test="${not empty loginCount}">
   									${loginCount}
   								</c:if>
   								<c:if test="${empty loginCount}">
   									首
   								</c:if>
   							次登录本系统</span>
   						</td>
   						<td>
   							&nbsp;&nbsp;&nbsp;
   						</td>
   						<td>
   							<span>
   								您上次登录是
   								<c:if test="${not empty lastLoginTime}">
   									<msg:formatDate value="${lastLoginTime}"/>
   								</c:if>
   								<c:if test="${empty lastLoginTime}">
   									无
   								</c:if>
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
	            <a class="t3" title="个人首页" href="javaScript:void(0);" onclick="linkFun('${contextPath}/user/profile.do')"></a>
	            <a class="t1" title="个人设置" href="javaScript:void(0);" onclick="linkFun('${contextPath}/user/editUserInfo.do')"></a>
	            <a class="t2" title="收藏夹" href="javaScript:void(0);" onclick="addBookmark()"></a>
	            <a class="t4" title="系统帮助"></a>
	            <a class="t5" title="退出系统" href="javaScript:void(0);" onclick="logout('${contextPath}')"></a>
	        </div>
	        <div class="position">
	        	<a href="javaScript:void(0);" id="layout" class="layout-button layout-button-up" title="向上收起"></a>
	        </div>
		</div>
	</body>
</html>
