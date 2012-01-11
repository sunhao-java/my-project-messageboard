<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/includes.jsp"%>
<%@ include file="/WEB-INF/jsp/common/common_js.jsp" %>

<msg:css href="css/publish.css"/>
<msg:js src="js/jquery/jquery-1.4.2.min.js"/>
<msg:js src="js/jquery/jquery.easyui.min.js"/>

<msg:css href="themes/default/easyui.css"/>
<msg:css href="themes/icon.css"/>

<msg:css href="css/colortip-1.0-jquery.css"/>
<msg:js src="js/jquery/colortip-1.0-jquery.js"/>

<msg:js src="js/base/app-dialog.js"/>

<msg:js src="js/validate.js"/>

<script type="text/javascript">
	var $C = YAHOO.util.Connect,dom = YAHOO.util.Dom,event = YAHOO.util.Event;
	
	function save(){
		var dataFrm = dom.get('dataFrm');
		var flag = Validator.Validate(dataFrm, 3);
		$C.setForm(dataFrm);
		var requestURL = '${contextPath}/user/saveUser.do';
		if(flag){
			$C.asyncRequest("POST", requestURL, {
				success : function(o){
					var _e = eval("(" + o.responseText + ")");
					if(_e.status == '1'){
						YAHOO.app.dialog.pop({'dialogHead':'提示','cancelButton':'false','alertMsg':'修改信息成功！',
							'confirmFunction':function(){
								window.location.href = '${contextPath}/user/userInfo.do';
							}});
					} else {
						YAHOO.app.dialog.pop({'dialogHead':'提示','cancelButton':'false','alertMsg':'修改信息失败！'});
					}
				},
				failure : function(o){
					YAHOO.app.dialog.pop({'dialogHead':'提示','cancelButton':'false','alertMsg':'错误代码:' + o.status});
				}
			});
		}
	}
	
	function successFun(){
		window.location.href = '${contextPath}/user/userInfo.do';
	}
</script>

<jsp:include page="/WEB-INF/jsp/base/head.jsp">
	<jsp:param value="修改我的信息" name="title"/>
</jsp:include>

<div id="listDiv">
	<form id="dataFrm" action="" method="post">
		<input type="hidden" name="pkId" value="${user.pkId }"/>
		<table width="100%" border="1" class="tableform">
			<tr>
				<td class="fb_result_head" width="9%">
					姓名
				</td>
				<td width="42%">
					<c:out value="${user.username}"/><span style="color: red;visibility: hidden">（注册后不可修改）</span>              
				</td>
				<td class="fb_result_head" width="6%">
					性别
				</td>
				<td width="43%">
					<c:if test="${user.sex == 0}">
						<c:out value="不男不女"/>
					</c:if>
					<c:if test="${user.sex == 1}">
						<c:out value="男"/>
					</c:if>
					<c:if test="${user.sex == 2}">
						<c:out value="女"/>
					</c:if>
					<span style="color: red">（注册后不可修改）</span>
				</td>
			</tr>
			<tr>
				<td class="fb_result_head">
					真实姓名
				</td>
				<td>
					${user.truename}<span style="color: red">（注册后不可修改）</span>
				</td>
				<td class="fb_result_head">
					邮箱<span style="color: red">*</span>
				</td>
				<td>
					<input type="text" value="${user.email}" name="email" class="f_text"
						require="true" dataType="Email" msg="不能为空且格式要正确"/>
				</td>
			</tr>
			<tr>
				<td class="fb_result_head">
					电话号码<span style="color: red">*</span>
				</td>
				<td>
					<input type="text" value="${user.phoneNum}" name="phoneNum" class="f_text"
						require="true" dataType="Phone" msg="不能为空且格式要正确"/>
				</td>
				<td class="fb_result_head">
					QQ
				</td>
				<td>
					<input type="text" value="${user.qq}" name="qq" class="f_text"
						require="false" dataType="QQ" msg="QQ号码不存在"/>
				</td>
				
			</tr>
			<tr>
				<td class="fb_result_head">
					主页
				</td>
				<td colspan="3">
					<input type="text" value="${user.homePage}" name="homePage" class="f_text longText"
						require="false" dataType="Url" msg="非法的Url"/>
				</td>
			</tr>
			<tr>
				<td class="fb_result_head">
					地址
				</td>
				<td colspan="3">
					<textarea class="f_textarea" name="address"
						dataType="Limit" max="100" min="0" msg="不超过100字符">${user.address }</textarea>
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