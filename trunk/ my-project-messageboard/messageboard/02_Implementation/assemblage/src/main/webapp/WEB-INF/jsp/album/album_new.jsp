<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/includes.jsp"%>
<%@ include file="/WEB-INF/jsp/common/common_js.jsp" %>

<msg:css href="css/menu.css"/>

<form id="albumForm">
	<input type="hidden" name="pkId" value="${menu.pkId }">
	<input type="hidden" name="deleteStatus" value="${menu.deleteStatus }">
	<input type="hidden" name="createDate" value='<fmt:formatDate value="${menu.createDate }" pattern="yyyy-MM-dd HH:mm"/>'>
	<input type="hidden" name="createUserId" value="${menu.createUserId }">
	
	<table width="100%" height="100%" class="dialog_table">
		<tr>
			<td class="text" width="25%">
				相册名称&nbsp;<span style="color: #CC0000;font-weight: bolder;">*</span>
			</td>
			<td>
				<input type="text" name="albumName" class="width300" id="albumName" dataType="Limit"
						require="true" max="100" min="1" msg="不能为空,且不超过50字符"/>
			</td>
		</tr>
		<tr>
			<td class="text" width="25%">
				相册描述
			</td>
			<td>
				<textarea style="height: 58px;background: none repeat scroll 0 0 #FFFFFF;" 
					id="summary" name="summary" class="width300" dataType="Limit"
						max="200" min="0" msg="不超过200字符"></textarea>
			</td>
		</tr>
		<tr>
			<td class="text" width="25%">
				相册权限
			</td>
			<td>
				<select name="viewFlag" id="viewFlag">
					<option value="1">所有人可见</option>
					<option value="2">本人可见</option>
					<option value="3">会员可见</option>
				</select>
			</td>
		</tr>
	</table>
</form>