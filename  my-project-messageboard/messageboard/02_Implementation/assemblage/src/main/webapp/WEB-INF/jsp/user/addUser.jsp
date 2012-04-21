<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/includes.jsp"%>
<%@ include file="/WEB-INF/jsp/common/common_js.jsp" %>

<msg:css href="css/publish.css"/>
<msg:js src="js/validate.js"/>

<script type="text/javascript">
	var $C = YAHOO.util.Connect;
 	var $D = YAHOO.util.Dom;
 	var event = YAHOO.util.Event;
 	
	function checkUser(input){
		var name = input.value;
		var requestURL = "${contextPath}/guest/check.do";
		var dataFrm = $D.get('regFrm');
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

<div id="listFrm" style="width: 100%">
	<form id="regFrm" action="" method="post">
		<table width="100%" border="1" class="tableform">
			<tr>
				<td class="fb_result_head" width="50%">
					登录名<span style="color: red">*</span><img src="${contextPath }/image/register/warning.png" title="注册后不可修改"/>
				</td>
				<td>
					<input type="text" name="username" id="username" class="f_text" dataType="Limit"
						require="true" max="100" min="1" msg="登录名不能为空,且不超过50字符" onblur="checkUser(this);"/>         
					<label id="reg" style="color: green;display: none;">*<msg:message code="message.register.ok"/></label>
					<label id="regNo" style="color: red;display: none;">*<msg:message code="message.register.no"/></label> 
				</td>
			</tr>
			<tr>
				<td class="fb_result_head">
					性别<img src="${contextPath }/image/register/warning.png" title="注册后不可修改"/>
				</td>
				<td>
					<input type="radio" value="0" name="sex" id="0" checked="checked"><label for="0"><msg:message code="message.sex.no"/></label>
					<input type="radio" value="1" name="sex" id="1"><label for="1"><msg:message code="message.sex.man"/></label>
					<input type="radio" value="2" name="sex" id="2"><label for="2"><msg:message code="message.sex.woman"/></label>
					<br/>
				</td>
			</tr>
			<tr>
				<td class="fb_result_head">
					真实姓名<span style="color: red">*</span><img src="${contextPath }/image/register/warning.png" title="注册后不可修改"/>
				</td>
				<td>
					<input type="text" name="truename" id="truename" class="f_text" dataType="Limit" 
							require="true" max="100" min="1" msg="真实姓名不能为空,且不超过50字符"/>
				</td>
			</tr>
		</table>
	</form>
	<span>注意：<img src="${contextPath }/image/register/warning.png"/>表示<span style="color: red">注册后不可修改</span>，请谨慎填写</span>
</div>
