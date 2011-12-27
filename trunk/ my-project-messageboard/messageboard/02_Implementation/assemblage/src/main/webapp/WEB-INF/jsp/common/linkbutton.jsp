<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<script type="text/javascript">
	function back(){
		history.back();
	}
</script>

	<c:if test="${not empty param.view}">
		<a href="javaScript:view();" class="easyui-linkbutton" iconCls="icon-search">${param.view }</a>
	</c:if>

	<c:if test="${not empty param.edit}">
		<a href="javaScript:edit();" class="easyui-linkbutton" iconCls="icon-edit">${param.edit }</a>
	</c:if>
	
	<c:if test="${not empty param.save}">
		<a href="javaScript:save();" class="easyui-linkbutton" iconCls="icon-save">${param.save }</a>
	</c:if>
	
	<c:if test="${not empty param.reset}">
		<a href="javaScript:reset();" class="easyui-linkbutton" iconCls="icon-reload">${param.reset }</a>
	</c:if>
	
	<c:if test="${not empty param.copy}">
		<a href="javaScript:copy();" class="easyui-linkbutton" iconCls="icon-copy">${param.edit }</a>
	</c:if>
	
	<c:if test="${not empty param.back}">
		<a href="javaScript:back();" class="easyui-linkbutton" iconCls="icon-back">${param.back }</a>
	</c:if>
	
	

