<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/includes.jsp" %>
<%@ include file="/WEB-INF/jsp/common/common_js.jsp" %>

<msg:js src="js/jquery/jquery-1.4.2.min.js"/>
<msg:js src="js/base/app-dialog.js"/>
<msg:js src="js/base/app-swfupload.js"/>

<script type="text/javascript">
    var $C = YAHOO.util.Connect,dom = YAHOO.util.Dom,event = YAHOO.util.Event;
    var uploadDialog;
    $(document).ready(function(){
        uploadDialog = YAHOO.app.swfupload("uploadHeadImage", '', {
            title : '上传头像',
            fileTypes : '*.jpg;*.png;*.gif;*.jpeg;*.bmp',
            params : {
                userId : '${loginUser.pkId}',
                headImage : 'true'
            },
            completeFunction : 'completeFun'
        });
    });

    function completeFun(){
        uploadDialog.cancel();
        YAHOO.app.dialog.pop({'dialogHead':'提示','cancelButton':'false','alertMsg':'上传头像成功！','confirmFunction':function(){
             window.location.reload(true);
        }});
    }
</script>

<jsp:include page="/WEB-INF/jsp/base/head.jsp">
	<jsp:param value="修改我的信息" name="title"/>
</jsp:include>

<div class="content01">
    <jsp:include page="user_edit_head.jsp"/>

    <div class="uploadField">
        <input id="uploadHeadImage" type="button" class="f-button" value="上传"/>
        <msg:userHead userId="${loginUser.pkId }" headType="1"/>
    </div>
</div>