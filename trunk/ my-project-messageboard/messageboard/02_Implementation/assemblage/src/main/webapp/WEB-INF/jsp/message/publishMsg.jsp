<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/includes.jsp"%>
<%@ include file="/WEB-INF/jsp/common/common_js.jsp" %>

<msg:js src="js/jquery/jquery-1.4.2.min.js"/>
<msg:js src="js/jquery/jquery.easyui.min.js"/>
<msg:js src="js/validate.js"/>

<msg:css href="themes/default/easyui.css"/>
<msg:css href="themes/icon.css"/>
<msg:js src="fckeditor/fckeditor.js"/>
<msg:js src="js/base/app-fckeditor.js"/>
<msg:css href="css/publish.css"/>

<msg:js src="js/base/commfunction.js"/>
<msg:js src="js/base/app-dialog.js"/>

<msg:css href="css/colortip-1.0-jquery.css"/>
<msg:js src="js/jquery/colortip-1.0-jquery.js"/>
<msg:js src="js/base/app-swfupload.js"/>

<script type="text/javascript">
	var $C = YAHOO.util.Connect,dom = YAHOO.util.Dom,event = YAHOO.util.Event;

	$(document).ready(function(){
		new myFCKeditor('content', {'id':'content', 'contextPath':'${contextPath }', 
				'width':'100%', 'toolBar':'Font', 'height':'600px'});
		
		//调用公共JS自动提示组件
		showUser('yellow','300px','wrap');
	});

    $(document).ready(function(){
        YAHOO.app.swfupload("upload", "showUploadPanel", {
            title : '上传附件',
            fileTypes : '*.*',
            params : {
                headImage : 'false',
                resourceId : '${pkId}',
                resourceType : 1,
                uploadId : '${loginUser.pkId}'
            }
        });
    });
	
	function reset(){
		window.location.href = '${contextPath}/message/inPublishMessageJsp.do';
	}
	
	function save(){
		var dataFrm = dom.get("dataFrm");
		var oEditor = FCKeditorAPI.GetInstance("content");
		var fckstr = oEditor.GetXHTML(true);
		if(fckstr == '<br />'){
			dataFrm.content.value = '';
		} else {
	        dataFrm.content.value = fckstr;
		}
        $C.setForm(dataFrm);
        var flag = Validator.Validate(dataFrm, 3);
        if(flag){
        	var requestURL = "${contextPath}/message/saveMessage.do";
        	$C.asyncRequest("POST", requestURL, {
        		success : function(o){
        			var _e = eval("(" + o.responseText + ")");
        			if(_e.status == "0"){
        				YAHOO.app.dialog.pop({'dialogHead':'提示','cancelButton':'false','alertMsg':'发表留言失败，请稍候再试！'});
        			} else if(_e.status == "1"){
        				YAHOO.app.dialog.pop({'dialogHead':'提示','cancelButton':'false','alertMsg':'发表留言成功！',
								'confirmFunction':function(){
									window.location.href = '${contextPath}/message/listMessage.do';
								}});
        			}
        		}
        	});
        }
	}
</script>

<jsp:include page="/WEB-INF/jsp/base/head.jsp">
	<jsp:param value="发表留言" name="title"/>
</jsp:include>

<div id="listFrm">
	<form id="dataFrm" action="" method="post">
		<input type="hidden" name="user_id" value="${loginUser.pkId}"/>
        <input type="hidden" name="pkId" value="${pkId}"/>
		<table width="100%" border="1" class="tableform">
			<tr>
				<td class="fb_result_head" width="15%">
					姓名
				</td>
				<td width="40%">
					<c:out value="${loginUser.username}"/>              
				</td>
				<td class="fb_result_head" width="15%">
					性别
				</td>
				<td width="30%">
					<c:if test="${loginUser.sex == 0}">
						<c:out value="不男不女"/>
					</c:if>
					<c:if test="${loginUser.sex == 1}">
						<c:out value="男"/>
					</c:if>
					<c:if test="${loginUser.sex == 2}">
						<c:out value="女"/>
					</c:if>
				</td>
			</tr>
			<tr>
				<td class="fb_result_head" width="15%">
					邮箱
				</td>
				<td>
					<c:out value="${loginUser.email}"/>
				</td>
				<td class="fb_result_head" width="15%">
					电话号码
				</td>
				<td>
					<c:out value="${loginUser.phoneNum}"/>
				</td>
			</tr>
			<tr>
				<td class="fb_result_head" width="15%">
					QQ
				</td>
				<td>
					<c:out value="${loginUser.qq}"/>
				</td>
				<td class="fb_result_head" width="15%">
					主页
				</td>
				<td>
					<a href="${loginUser.homePage}" target="_blank">
						<msg:cutWord length="30" endString="..." cutString="${loginUser.homePage}"/>
					</a>
				</td>
			</tr>
			<tr>
				<td class="fb_result_head" width="15%">
					标题<span style="color: red">*</span>
				</td>
				<td colspan="3">
					<input type="text" name="title" id="textfield5" dataType="Limit" require="true" max="100" min="1" 
						msg="不能为空,且不超过50字符" class="f_text"/>
				</td>
			</tr>
            <tr>
                <td class="fb_result_head">
                    <input type="button" id="upload" value="upload">
                </td>
                <td colspan="3">
                    <div id="showUploadPanel"></div>
                </td>
            </tr>
			<tr>
				<td class="fb_result_head" width="15%">
					留言内容<span style="color: red">*</span>
				</td>
				<td colspan="3">
					<textarea rows="4" cols="60" name="content" id="content"></textarea>
				</td>
			</tr>
		</table>
		<div class="formFunctiondiv">
			<jsp:include page="/WEB-INF/jsp/common/linkbutton.jsp">
				<jsp:param value="发表留言" name="save"/>
				<jsp:param value="重置留言" name="reset"/>
				<jsp:param value="返回" name="back"/>
			</jsp:include>
		</div>
	</form>
</div>