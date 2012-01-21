<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/includes.jsp"%>
<%@ include file="/WEB-INF/jsp/common/common_js.jsp" %>

<msg:js src="js/jquery/jquery-1.4.2.min.js"/>
<msg:js src="js/jquery/jquery.easyui.min.js"/>
<msg:js src="js/validate.js"/>
<msg:css href="themes/default/easyui.css"/>
<msg:css href="themes/icon.css"/>
<msg:js src="fckeditor/fckeditor.js"/>
<msg:js src="js/fckeditor/myFCKEditor.js"/>
<msg:js src="js/base/commfunction.js"/>
<msg:js src="js/base/app-dialog.js"/>

<script type="text/javascript">
	var $C = YAHOO.util.Connect,dom = YAHOO.util.Dom,event = YAHOO.util.Event;

	$(document).ready(function(){
		new myFCKeditor('description', {'id':'description', 'contextPath':'${contextPath }', 
				'width':'100%', 'toolBar':'Font', 'height':'80%'});
	});
	
	function save(){
		var dataFrm = dom.get("dataFrm");
		var oEditor = FCKeditorAPI.GetInstance("description");
		var fckstr = oEditor.GetXHTML(true);
		if(fckstr == '<br />'){
			dataFrm.description.value = '';
		} else {
	        dataFrm.description.value = fckstr;
		}
        $C.setForm(dataFrm);
        var flag = Validator.Validate(dataFrm, 3);
        if(flag){
        	var requestURL = "${contextPath}/info/saveInfo.do";
        	$C.asyncRequest("POST", requestURL, {
        		success : function(o){
        			var _e = eval("(" + o.responseText + ")");
        			if(_e.status == "0"){
        				YAHOO.app.dialog.pop({'dialogHead':'提示','cancelButton':'false','alertMsg':'更新留言板描述失败'});
        			} else if(_e.status == "1"){
        				YAHOO.app.dialog.pop({'dialogHead':'提示','cancelButton':'false','alertMsg':'更新留言板描述成功！',
								'confirmFunction':function(){
									window.location.href = '${contextPath}/info/inViewInfoJsp.do';
								}});
        			}
        		},
        		failure : function(o){
					YAHOO.app.dialog.pop({'dialogHead':'提示','cancelButton':'false','alertMsg':'错误代码:' + o.status});
				}
        	});
        }
	}
	
	function reset(){
		window.location.reload();
	}
</script>

<style type="text/css">
	.formFunctiondiv{
		text-align: center; 
		margin: 10px 0 10px 0;
	}
</style>

<jsp:include page="/WEB-INF/jsp/base/head.jsp">
	<jsp:param value="编辑留言板信息" name="title"/>
</jsp:include>

<div>
	<form action="" id="dataFrm" method="post">
		<input type="hidden" name="modifyUserId" value="${loginUser.pkId}">
		<input type="hidden" name="modifyUserName" value="${loginUser.truename}">
		<textarea id="description" name="description" style="height: 100%"></textarea>
	</form>
	<div class="formFunctiondiv">
		<jsp:include page="/WEB-INF/jsp/common/linkbutton.jsp">
			<jsp:param value="保存" name="save"/>
			<jsp:param value="重置" name="reset"/>
			<jsp:param value="返回" name="back"/>
		</jsp:include>
	</div>
</div>