<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://sunhao.wiscom.com.cn/message" prefix="msg" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
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
		
		<msg:css href="js/yui/css/container.css"/>
		<msg:css href="js/yui/css/fonts-min.css"/>
		<msg:css href="css/login.css"/>
		<%@ include file="/WEB-INF/jsp/common/common_js.jsp" %>
		
		<msg:js src="js/base/app-dialog.js"/>
		
		
		<script type="text/javascript">
			var $C = YAHOO.util.Connect;
   	 		var $D = YAHOO.util.Dom;
   	 		var event = YAHOO.util.Event;
   	 		
			function login(){
				$D.get("dataFrm").submit();
			}
			
			function closeDiv(){
				$("#registerDiv").hide("slow");
				reset();
			}
			
			function showRegister(){
				$("#registerDiv").show("slow"); 
			}
			
			function save(){
				var registerFrm = $D.get("registerFrm");
				var action = registerFrm.action;
				$C.setForm(registerFrm);
				var flag = Validator.Validate(registerFrm, 3);
				if(flag){
					$C.asyncRequest("POST", action, {
						success : function(o){
							var _e = eval("(" + o.responseText + ")");
							if(_e.status == '1'){
								YAHOO.app.dialog.pop({'dialogHead':'提示','cancelButton':'false','alertMsg':'注册成功！',
									'confirmFunction':function(){
										this.cancel();
										closeDiv();
										reset();
									}});
							} else {
								YAHOO.app.dialog.pop({'dialogHead':'提示','cancelButton':'false','alertMsg':'注册失败！'});
							}
						},
						failure : function(o){
							YAHOO.app.dialog.pop({'dialogHead':'提示','cancelButton':'false','alertMsg':'错误代码:' + o.status});
						}
					});
				}
			}
			
			function reset(){
				$D.get("registerFrm").reset();
			}
			
			function checkUser(input){
				var name = input.value;
				var requestURL = "${contextPath}/user/check.do";
				var dataFrm = $D.get('registerFrm');
				if(name != ''){
					$C.setForm(dataFrm);
					$C.asyncRequest("POST", requestURL, {
						success : function(o){
							var _e = eval("(" + o.responseText + ")");
							if(_e.status == '1'){
								$D.get('regNo').style.display = 'none';
								$D.get('reg').style.display = '';
								$D.get('username').fouce;
							} else {
								$D.get('reg').style.display = 'none';
								$D.get('regNo').style.display = '';
							}
						},
						failure : function(o){
							YAHOO.app.dialog.pop({'dialogHead':'提示','cancelButton':'false','alertMsg':'错误代码:' + o.status});
						}
					});
				}
			}
		</script>

	</head>

	<body class="yui-skin-sam">
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
							<c:choose>
								<c:when test="${empty status}">
									<td width="311" height="309" valign="top" id="yzmtd" background="${contextPath}/image/wiseduimg/wiscom_index_03.jpg">
										<form action="${contextPath}/user/login.do" method="post" name="dataFrm" id="dataFrm">
											<table width="100%" border="0" cellspacing="0" cellpadding="0">
												<tr>
													<td height="102">
														&nbsp;
													</td>
												</tr>
												<tr>
													<td height="29" style="padding-left: 20px;">
														<input type="text" style="width: 250px; height: 25px; background-color: ececec; border: 1 solid #b2b2b2; line-height: 25px;" name="username" id="username" value="" tabindex="1">
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
														<img src="${contextPath}/image/wiseduimg/wiscom_login.PNG" width="124" height="46" onclick="login()" style="cursor: pointer">
													</td>
												</tr>
											</table>
										</form>
									</td>
								</c:when>
								<c:otherwise>
									<td width="311" height="309" align="center" valign="middle" background="${contextPath}/image/wiseduimg/wiscom_blank.jpg">
										<div >
											<table align="center" border="0" cellpadding="0" cellspacing="0" class="AlrtTbl" title="">
								                <tr>
								                	<td valign="middle">
														<div class="AlrtErrTxt"> 
								                			<img name="Login.AlertImage" src="${contextPath}/image/wiseduimg/error_large.gif" alt="Error" 
								                					height="21" width="21" />
								               				<spring:message code="message.login.failure"/>
								               			</div>
								                		<div class="AlrtMsgTxt">
															${message }
                											<p>
                												<a href="${contextPath}">
                													<spring:message code="message.login.again"/>
                												</a>
                												<a href="javaScript:showRegister();">
                													<spring:message code="message.register"/>
                												</a>
                											</p>
														</div>
                									</td>
                								</tr>
                							</table>
                						 </div>
                					 </td>
								</c:otherwise>
							</c:choose>
							<td width="86" height="377" rowspan="2" background="${contextPath}/image/wiseduimg/wiscom_index_04.jpg">
								&nbsp;
							</td>
						</tr>
						<tr>
							<td width="311" height="70" background="${contextPath}/image/wiseduimg/wiscom_index_05.jpg" style="background-repeat: no-repeat;">
								&nbsp;
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
		<div id="registerDiv" class="registerDiv">
			<div id="titleDiv" class="titleDiv">
				<a id="turnoff" title="关闭" href="javaScript:closeDiv();">
					<img src="${contextPath }/image/register/turnoff.jpg">
				</a>
				<p><spring:message code="message.user.register"/></p>
			</div>
			<div>
				<img src="${contextPath}/image/register/register-back.jpg">
				<center>
					<form action="${contextPath}/user/register.do" id="registerFrm" method="post">
						<table width="100%" class="tableform">
							<tr>
	                 			<td class="fb_result_head" style="width: 25%">
	                 				<spring:message code="message.username"/><span style="color: red">*</span>
	                 			</td>
				                <td align="left" style="width: 80%">
									<input type="text" name="username" id="username" class="f_text" dataType="Limit" 
											require="true" max="100" min="1" msg="不能为空,且不超过50字符" onblur="checkUser(this);"/>
									<label id="reg" style="color: green;display: none;">*<spring:message code="message.register.ok"/></label>
									<label id="regNo" style="color: red;display: none;">*<spring:message code="message.register.no"/></label>
								</td>
	              			</tr>
	              			<tr>
	                 			<td class="fb_result_head" style="width: 25%">
	                 				<spring:message code="message.truename"/><span style="color: red">*</span>
	                 			</td>
				                <td align="left" style="width: 80%">
									<input type="text" name="truename" id="truename" class="f_text" dataType="Limit" 
											require="true" max="100" min="1" msg="不能为空,且不超过50字符"/>
								</td>
	              			</tr>
	              			<tr>
	                 			<td class="fb_result_head" style="width: 25%">
	                 				<spring:message code="message.password"/><span style="color: red">*</span>
	                 			</td>
				                <td align="left" style="width: 80%">
									<input type="password" name="password" id="password" class="f_text" 
										dataType="SafeString" msg="密码不符合安全规则"/>
								</td>
	              			</tr>
	              			<tr>
	                 			<td class="fb_result_head" style="width: 25%">
	                 				<spring:message code="message.password.again"/><span style="color: red">*</span>
	                 			</td>
				                <td align="left" style="width: 80%">
									<input type="password" name="password_" id="password_" class="f_text" 
										dataType="Repeat" to="password" msg="两次输入的密码不一致"/>
								</td>
	              			</tr>
	              			<tr>
	                 			<td class="fb_result_head" style="width: 25%">
	                 				<spring:message code="message.sex"/>
	                 			</td>
				                <td align="left" style="width: 80%">
									<input type="radio" value="0" name="sex" id="0" checked="checked"><label for="0"><spring:message code="message.sex.no"/></label>
									<input type="radio" value="1" name="sex" id="1"><label for="1"><spring:message code="message.sex.man"/></label>
									<input type="radio" value="2" name="sex" id="2"><label for="2"><spring:message code="message.sex.woman"/></label>
								</td>
	              			</tr>
	              			<tr>
	                 			<td class="fb_result_head" style="width: 25%">
	                 				<spring:message code="message.head.image"/>
	                 			</td>
				                <td align="left" style="width: 80%">
									<select name="headImage" id="pci"
										onChange="document.images['imgs'].src='${contextPath }/' + options[selectedIndex].value;" class="f_text">
										<option value="image/pic1.gif" selected="selected">
											pic1
										</option>
										<option value="image/pic2.gif">
											pic2
										</option>
										<option value="image/pic3.gif">
											pic3
										</option>
										<option value="image/pic4.gif">
											pic4
										</option>
										<option value="image/pic5.gif">
											pic5
										</option>
										<option value="image/pic6.gif">
											pic6
										</option>
										<option value="image/pic7.gif">
											pic7
										</option>
										<option value="image/pic8.gif">
											pic8
										</option>
										<option value="image/pic9.gif">
											pic9
										</option>
										<option value="image/pic10.gif">
											pic10
										</option>
										<option value="image/pic11.gif">
											pic11
										</option>
										<option value="image/pic12.gif">
											pic12
										</option>
										<option value="image/pic13.gif">
											pic13
										</option>
										<option value="image/pic14.gif">
											pic14
										</option>
										<option value="image/pic15.gif">
											pic15
										</option>
										<option value="image/pic16.gif">
											pic16
										</option>
										<option value="image/pic17.gif">
											pic17
										</option>
										<option value="image/pic18.gif">
											pic18
										</option>
										<option value="image/pic19.gif">
											pic19
										</option>
										<option value="image/pic20.gif">
											pic20
										</option>
									</select>
									<img src="${contextPath }/image/pic1.gif" width="90" height="90" id="imgs" />
								</td>
	              			</tr>
	              			<tr>
	                 			<td class="fb_result_head" style="width: 25%">
	                 				<spring:message code="message.email"/><span style="color: red">*</span>
	                 			</td>
				                <td align="left" style="width: 80%">
									<input type="text" name="email" id="email" class="f_text"
										require="true" dataType="Email" msg="信箱不能为空且格式要正确"/>
								</td>
	              			</tr>
	              			<tr>
	                 			<td class="fb_result_head" style="width: 25%">
	                 				<spring:message code="message.phonenum"/><span style="color: red">*</span>
	                 			</td>
				                <td align="left" style="width: 80%">
									<input type="text" name="phoneNum" id="phoneNum" class="f_text"
										require="true" dataType="Phone" msg="电话号码不能为空且格式 要正确"/>
								</td>
	              			</tr>
	              			<tr>
	                 			<td class="fb_result_head" style="width: 25%">
	                 				QQ
	                 			</td>
				                <td align="left" style="width: 80%">
									<input type="text" name="qq" id="qq" class="f_text"
										require="false" dataType="QQ" msg="QQ号码不存在"/>
								</td>
	              			</tr>
	              			<tr>
	                 			<td class="fb_result_head" style="width: 25%">
	                 				<spring:message code="message.address"/>
	                 			</td>
				                <td align="left" style="width: 80%">
									<input type="text" name="address" id="address" class="f_text" 
										 dataType="Limit" max="100" min="0" msg="不超过50字符"/>
								</td>
	              			</tr>
	              			<tr>
	                 			<td class="fb_result_head" style="width: 25%">
	                 				<spring:message code="message.homePage"/>
	                 			</td>
				                <td align="left" style="width: 80%">
									<input type="text" name="homePage" id="homePage" class="f_text"
										require="false" dataType="Url" msg="非法的Url"/>
								</td>
	              			</tr>
						</table>
						<div class="formFunctiondiv">
							<jsp:include page="/WEB-INF/jsp/common/linkbutton.jsp">
								<jsp:param value="注册" name="save"/>
								<jsp:param value="重置" name="reset"/>
							</jsp:include>
						</div>
					</form>
				</center>
			</div>
		</div>
	</body>
</html>