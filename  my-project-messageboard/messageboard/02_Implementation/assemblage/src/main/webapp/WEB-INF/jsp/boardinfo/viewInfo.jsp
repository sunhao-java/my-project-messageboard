<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/includes.jsp"%>
<%@ include file="/WEB-INF/jsp/common/common_js.jsp" %>

<msg:js src="js/jquery/jquery-1.4.2.min.js"/>
<msg:js src="js/jquery/jquery.easyui.min.js"/>
<msg:css href="themes/default/easyui.css"/>
<msg:css href="themes/icon.css"/>
<msg:css href="css/colortip-1.0-jquery.css"/>
<msg:js src="js/jquery/colortip-1.0-jquery.js"/>
<msg:js src="js/base/commfunction.js"/>

<style type="text/css">
	.content01{
		border-left: 1px solid #dad9d9;
		border-bottom: 1px solid #dad9d9;
		border-top: 1px solid #dad9d9;
		border-right: 1px solid #dad9d9;
		padding: 10px 5px 10px 5px;
	}
	
	.formFunctiondiv{
		text-align: center; 
		margin: 10px 0 10px 0;
	}
</style>

<script type="text/javascript">
	function edit(){
		window.location.href = '${contextPath }/info/inEditInfoJsp.do';
	}
</script>

<jsp:include page="/WEB-INF/jsp/base/head.jsp">
	<jsp:param value="查看留言板信息" name="title"/>
</jsp:include>

<div class="content01">
	${info.description }
</div>

<div class="formFunctiondiv">
	<c:choose>
		<c:when test="${isAdmin eq '1'}">
			<jsp:include page="/WEB-INF/jsp/common/linkbutton.jsp">
				<jsp:param value="编辑" name="edit"/>
				<jsp:param value="返回" name="back"/>
			</jsp:include>
		</c:when>
		<c:otherwise>
			<jsp:include page="/WEB-INF/jsp/common/linkbutton.jsp">
				<jsp:param value="返回" name="back"/>
			</jsp:include>
		</c:otherwise>
	</c:choose>
</div>