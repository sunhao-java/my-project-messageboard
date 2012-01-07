<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/includes.jsp"%>
<%@ include file="/WEB-INF/jsp/common/common_js.jsp" %>
<msg:css href="css/publish.css"/>

<msg:js src="js/jquery/jquery-1.4.2.min.js"/>
<msg:js src="js/jquery/jquery.easyui.min.js"/>

<msg:css href="themes/default/easyui.css"/>
<msg:css href="themes/icon.css"/>

<msg:js src="js/validate.js"/>

<script type="text/javascript">
	var $C = YAHOO.util.Connect,dom = YAHOO.util.Dom,event = YAHOO.util.Event;
	function checkPsw(input){
		dom.get("wrongSpan").style.display = 'none';
		dom.get("rightSpan").style.display = 'none';
		if(dom.get("__ErrorMessagePanel") != null){
			dom.get("__ErrorMessagePanel").style.display = 'none';
		}
		var oldpassword = input.value;
		var requestURL = '${contextPath}/user/checkPsw.do?oldPassword=' + oldpassword;
		if(oldpassword != ''){
			$C.asyncRequest("POST", requestURL, {
				success : function(o){
					var _e = eval("(" + o.responseText + ")");
					if(_e.status == '1'){
						dom.get("rightSpan").style.display = '';
						dom.get("wrongSpan").style.display = 'none';
					} else {
						dom.get("wrongSpan").style.display = '';
						dom.get("rightSpan").style.display = 'none';
						input.focus();
					}
				},
				failure : function(o){
					alert('错误代码:' + o.status);
				}
			});
		}
	}
	
	function save(){
		var dataFrm = dom.get('dataFrm');
		var flag = Validator.Validate(dataFrm, 3);
		var requestURL = '${contextPath }/user/savePassword.do';
		if(flag){
			$C.setForm(dataFrm);
			$C.asyncRequest("POST", requestURL, {
				success : function(o){
					var _e = eval("(" + o.responseText + ")");
					if(_e.status == '1'){
						$.messager.alert('提示','修改成功！','info', function(){
							window.location.href = '${contextPath}/user/userInfo.do';
						});
					} else {
						$.messager.alert('提示','修改失败！','info');
					}
				},
				failure : function(o){
					alert('错误代码:' + o.status);
				}
			});
		}
	}
</script>

<jsp:include page="/WEB-INF/jsp/base/head.jsp">
	<jsp:param value="修改我的密码" name="title"/>
</jsp:include>

<div id="listDiv">
	<form id="dataFrm" action="" method="post">
		<input type="hidden" name="pkId" value="${user.pkId }"/>
		<table width="70%" class="tableform">
			<tr>
				<td class="fb_result_head" width="12%">
					您的用户名
				</td>
				<td width="42%">
					${user.username }     
				</td>
			</tr>
			<tr>
				<td class="fb_result_head" width="12%">
					您的真实姓名
				</td>
				<td width="42%">
					${user.truename }     
				</td>
			</tr>
			<tr>
				<td class="fb_result_head" width="12%">
					原密码
				</td>
				<td width="42%">
					<input type="text" value="" class="f_text" name="oldpassword" id="oldpassword" onblur="checkPsw(this);"
						require="true" dataType="Limit" max="100" min="1" msg="原密码必填"/>     
					<span id="rightSpan" style="color: green;display: none;"><img src="${contextPath}/image/register/check_right.gif">原密码正确</span>
					<span id="wrongSpan" style="color: red;display: none;"><img src="${contextPath}/image/register/check_error.gif">原密码错误</span>       
				</td>
			</tr>
			<tr>
				<td class="fb_result_head" width="12%">
					新密码
				</td>
				<td width="42%">
					<input type="text" name="password" id="password" class="f_text" 
										dataType="SafeString" msg="密码不符合安全规则"/>        
				</td>
			</tr>
			<tr>
				<td class="fb_result_head" width="12%">
					确认新密码
				</td>
				<td width="42%">
					<input type="text" name="password_" id="password_" class="f_text" 
										dataType="Repeat" to="password" msg="两次输入的密码不一致"/>        
				</td>
			</tr>
		</table>
	</form>
	<div class="formFunctiondiv">
		<jsp:include page="/WEB-INF/jsp/common/linkbutton.jsp">
			<jsp:param value="保存" name="save"/>
			<jsp:param value="返回" name="back"/>
		</jsp:include>
	</div>
</div>	