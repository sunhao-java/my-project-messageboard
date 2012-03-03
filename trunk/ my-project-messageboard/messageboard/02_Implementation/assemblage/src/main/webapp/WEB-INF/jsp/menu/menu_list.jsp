<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/includes.jsp"%>
<%@ include file="/WEB-INF/jsp/common/common_js.jsp" %>

<msg:js src="js/jquery/jquery-1.4.2.min.js"/>
<msg:js src="js/jquery/easyloader.js"/>
<msg:js src="js/base/app-alertForm.js"/>
<msg:js src="js/base/app-dialog.js"/>

<msg:css href="themes/icon.css"/>
<msg:css href="css/menu.css"/>

<script type="text/javascript">
	var $C = YAHOO.util.Connect,dom = YAHOO.util.Dom,event = YAHOO.util.Event;

	$(document).ready(function(){
		using(['tree', 'linkbutton'], function(){
			showTree();
			
			$("#addMenu").linkbutton({
				'class':"easyui-linkbutton",
				'iconCls':"icon-save"
			});
			$("#delete").linkbutton({
				'class':"easyui-linkbutton",
				'iconCls':"icon-remove"
			});
		});
	});
	
	function showTree(){
		$('#menuTree').tree({
			checkbox: false,
			url: '${contextPath}/menu/ajaxLoadMenu.do',
			onClick:function(node){
				dom.get('menuIframe').src = '${contextPath}/menu/inEditJsp.do?menuId=' + node.id;
			},
			onBeforeLoad : function(){
				$("#onLoading").show();
			},
			onLoadSuccess : function(){
				$("#onLoading").hide();
			}
		});
	}
	
	function addMenu(){
		//新增菜单
		dom.get('menuIframe').src = '${contextPath}/menu/inEditJsp.do';
	}
	
	function deleteMenu(){
		//删除菜单
	}
</script>

<jsp:include page="/WEB-INF/jsp/base/head.jsp">
	<jsp:param value="菜单管理" name="title"/>
</jsp:include>

<div class="lm_tree_bg" style="height: 85%">
	<div class="lm_tree_bg_left" style="width:200px;height:100%;">
		<div id="onLoading" class="loading">加载中，请稍候……</div>
		<ul id="menuTree" class="lm_tree_bg_center" style="height:97%;overflow-y:auto;overflow-x: hidden">
		</ul>
	</div>
	<div style="height:100%;" class="lm_tree_bg_right"></div>
</div>

<div class="float_left" style="margin-left: 230px;height: 85px;">
	<a id="addMenu" href="javaScript:addMenu();">新增菜单</a>
	<a id="delete" href="javaScript:deleteMenu();">删除</a>
	
	<iframe src="${contextPath}/menu/inEditJsp.do" id="menuIframe" name="menuIframe" frameborder="0" 
			width="100%" height="400px" scrolling="no"></iframe>
</div>
