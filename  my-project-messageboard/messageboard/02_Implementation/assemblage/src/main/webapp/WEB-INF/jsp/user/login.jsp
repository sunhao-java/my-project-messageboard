<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://sunhao.wiscom.com.cn/message" prefix="msg" %>
<%
	String path = request.getContextPath();
	request.setAttribute("contextPath", path);
%>
<html>
	<head>
		<title>登录</title>

		<msg:js src="js/jquery/jquery-1.4.2.min.js"/>
		<msg:js src="js/jquery/jquery.easyui.min.js"/>
		<msg:js src="js/validate.js"/>

		<msg:css href="themes/default/easyui.css"/>
		<msg:css href="themes/icon.css"/>
		
		<script language="javascript">
			function login(){
				var flag = Validator.Validate(document.getElementById("dataFrm"), 3);
				if (flag) {
					$("#dataFrm").submit();
				}
			}
			
			function reset(){
				window.location.href = '${contextPath}/user/inLogin.do';
			}
		</script>

	</head>

	<body>
		<table width="100%" border="0" height="100%" cellpadding="0" cellspacing="0">
			<tr>
				<td bgcolor="#165286">
					<table width="1000" height="483" border="0" align="center" cellpadding="0" cellspacing="0" bordercolor="#66CC33" id="__01">
						<tr>
							<td background="${contextPath}/image/wiseduimg/wiscom_index_01.jpg" height="104" colspan="3">
								&nbsp;
							</td>
						</tr>
						<tr>
							<td width="603" height="379" rowspan="2" background="${contextPath}/image/wiseduimg/wiscom_index_02.jpg" style="background-repeat: no-repeat;">
								&nbsp;
							</td>
							<td width="311" height="309" valign="top" id="yzmtd" background="${contextPath}/image/wiseduimg/wiscom_index_03.jpg">
								<form action="admin/login" method="post" name="dataFrm" id="dataFrm">
									<table width="100%" border="0" cellspacing="0" cellpadding="0">
										<tr>
											<td height="102">
												&nbsp;
											</td>
										</tr>
										<tr>
											<td height="29" style="padding-left: 20px;">
												<input type="text" style="width: 250px; height: 25px; background-color: ececec; border: 1 solid #b2b2b2; line-height: 25px;" name="admin_name" id="admin_name" value="" tabindex="1">
											</td>
										</tr>
										<tr>
											<td height="19">
												&nbsp;
											</td>
										</tr>
										<tr>
											<td height="28" style="padding-left: 20px;">
												<input type="password" style="width: 250px; height: 25px; background-color: ececec; border: 1 solid #b2b2b2; line-height: 25px;" name="password" id="password" value="" tabindex="2">
											</td>
										</tr>
										<tr>
											<td height="21">
												&nbsp;
											</td>
										</tr>
										<tr>
											<td height="72" style="padding-left: 20px; font-size: 18px;">
												<img src="${contextPath}/image/wiseduimg/wiscom_login.PNG" width="124" height="46" onclick="login()" style="cursor: pointer" tabindex="4">
											</td>
										</tr>
									</table>
								</form>
							</td>
							<td width="86" height="377" rowspan="2" background="${contextPath}/image/wiseduimg/wiscom_index_04.jpg">
								&nbsp;
							</td>
						</tr>
						<tr>
							<td width="311" background="${contextPath}/image/wiseduimg/wiscom_index_05.jpg" style="background-repeat: no-repeat;">
								&nbsp;
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>

	</body>
</html>
