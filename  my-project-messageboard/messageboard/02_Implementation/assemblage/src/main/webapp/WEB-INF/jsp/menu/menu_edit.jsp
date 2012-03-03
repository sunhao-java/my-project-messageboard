<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/includes.jsp"%>
<%@ include file="/WEB-INF/jsp/common/common_js.jsp" %>

<msg:css href="css/menu.css"/>
<msg:css href="themes/icon.css"/>
<msg:js src="js/jquery/jquery-1.4.2.min.js"/>
<msg:js src="js/jquery/easyloader.js"/>
<msg:js src="js/base/app-alertForm.js"/>
<msg:js src="js/base/app-dialog.js"/>

<script type="text/javascript">
	var $C = YAHOO.util.Connect,dom = YAHOO.util.Dom,event = YAHOO.util.Event;
	
	$(document).ready(function(){
		using(['linkbutton','combotree'], function(){
			$('#save').linkbutton({
				'class':"easyui-linkbutton",
				'iconCls':"icon-save"
			});
			$("#refresh").linkbutton({
				'class':"easyui-linkbutton",
				'iconCls':"icon-reload"
			});
			$("#parentId").combotree({
				'url' : '${contextPath}/menu/ajaxLoadMenu.do',
				'class':'easyui-combotree',
				'width':'250',
				'value':'${menu.parentId}',
				'required':true
			});
		});
		
		if('${menu.menuStatus}' == '0'){
			dom.get('no').checked = true;
		} else {
			dom.get('ok').checked = true;
		}
 		
	});
	
	function changeIcon(){
		//使iframe的ID唯一
		var flag = new Date().getTime();
		YAHOO.app.alertForm.show({
			'reqUrl':'${contextPath}/menu/inShowIcon.do',
			'title':'菜单图标',
			'overflow':'yes',
			'diaWidth':'500',
			'diaHeight':'200', 
			'name':'iconDialog' + flag,
			'success' : '添加用户成功',
			'failure' : '添加用户失败',
			'cancelButton': 'false',
			'confirmFunction': function(){
				var icon = frames['iconDialog' + flag].icon;
				icon = icon == '' ? '01' : icon;
				dom.get('menuIcon').value = 'image/icon/icon_' + icon + '.png';
				dom.get('iconImg').src = '${contextPath}/image/icon/icon_' + icon + '.png';
				this.cancel();
			}
		});
	}
	
	function saveMenu(){
		var menuFrm = dom.get('menuFrm');
		var requestURL = '${contextPath}/menu/saveMenu.do';
		$C.setForm(menuFrm);
		$C.asyncRequest("POST", requestURL, {
			success : function(o){
				var _e = eval("(" + o.responseText + ")");
				if(_e.status == '1'){
					YAHOO.app.dialog.pop({'dialogHead':'提示','cancelButton':'false','alertMsg':'保存成功！',
						'confirmFunction':function(){
							parent.window.location.href = '${contextPath}/menu/inMenuJsp.do';
						}});
				} else {
					YAHOO.app.dialog.pop({'dialogHead':'提示','cancelButton':'false','alertMsg':'保存失败！'});
				}
			},
			failure : function(o){
				YAHOO.app.dialog.pop({'dialogHead':'提示','cancelButton':'false','alertMsg':'错误代码:' + o.status});
			}
		});
	}
</script>

<form id="menuFrm">
	<input type="hidden" name="pkId" value="${menu.pkId }">
	<input type="hidden" name="deleteStatus" value="${menu.deleteStatus }">
	<input type="hidden" name="createDate" value="${menu.createDate }">
	<input type="hidden" name="createUserId" value="${menu.createUserId }">
	
	<table width="80%" class="dialog_table">
		<tr>
			<td class="text" width="25%">
				菜单名称
			</td>
			<td>
				<input type="text" name="menuName" value="${menu.menuName }" class="width250"/>
				<img src="${contextPath }/image/register/warning.png" title="必填项"/>
			</td>
		</tr>
		<%-- <c:choose>
			<c:when test="${menu.parentId ne 0 }"> --%>
			<tr>
				<td class="text">
					菜单链接
				</td>
				<td>
					<input type="text" name="menuUrl" value="${menu.menuUrl }" class="width250"/>
					<img src="${contextPath }/image/register/warning.png" title="必填项"/>
				</td>
			</tr>
				<%-- <tr>
					<td class="text">
						上级菜单
					</td>
					<td>
						<input type="text" name="parentId" value="${menu.parentId }" class="width250"/>
						<img src="${contextPath }/image/register/warning.png" title="必填项"/>
						<select name="parentId">
							<option value="0"></option>
							<c:forEach items="${parentMenu }" var="parent">
								<option value="${parent.pkId }">${parent.menuName }</option>
							</c:forEach>
						</select>
					</td>
				</tr>
			</c:when>
			<c:otherwise>
				<input type="hidden" name="parentId" value="${menu.parentId }"/>
			</c:otherwise>
		</c:choose>
		<tr>
			<td class="text">
				菜单排序
			</td>
			<td>
				<input type="text" name="menuSort" value="${menu.menuSort }" class="width250"/>
			</td>
		</tr> --%>
		<tr>
			<td class="text">
				上级菜单
			</td>
			<td>
				<input type="text" name="parentId" id="parentId">
				<img src="${contextPath }/image/register/warning.png" title="必填项"/>
			</td>
		</tr>
		<tr>
			<td class="text">
				菜单排序
			</td>
			<td>
				<input type="text" name="menuSort" value="${menu.menuSort }" class="width250"/>
				<img src="${contextPath }/image/register/warning.png" title="必填项"/>
			</td>
		</tr>
		<tr>
			<td class="text">
				菜单状态
			</td>
			<td>
				<input type="radio" value="1" name="menuStatus" id="ok"><label for="ok">可用</label>
				<input type="radio" value="0" name="menuStatus" id="no"><label for="no">禁用</label>
			</td>
		</tr>
		<tr>
			<td class="text">
				菜单图标
			</td>
			<td>
				<span onclick="changeIcon();">
					<c:choose>
						<c:when test="${not empty menu.menuIcon }">
							<img src="${contextPath }/${menu.menuIcon}" id="iconImg"/>
						</c:when>
						<c:otherwise>
							<img src="${contextPath }/image/icon/icon_01.png" id="iconImg"/>
						</c:otherwise>
					</c:choose>
				</span>
				<input type="hidden" name="menuIcon" id="menuIcon" value="${menu.menuIcon }"/>
			</td>
		</tr>
	</table>
</form>
<div class="formFunctiondiv">
	<a id="save" href="javaScript:saveMenu();">
		<c:choose>
			<c:when test="${not empty menu.pkId }">
				修改
			</c:when>
			<c:otherwise>
				新增
			</c:otherwise>
		</c:choose>
	</a>
	<a id="refresh" href="javaScript:window.location.reload(true);">刷新</a>
</div>