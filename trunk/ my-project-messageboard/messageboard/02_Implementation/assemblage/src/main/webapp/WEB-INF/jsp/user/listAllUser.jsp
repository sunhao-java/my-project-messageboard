<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/includes.jsp"%>
<%@ include file="/WEB-INF/jsp/common/common_js.jsp" %>

<msg:css href="css/tableForm.css"/>
<msg:js src="js/base/commfunction.js"/>
<msg:js src="js/mouse-over-out.js"/>
<msg:js src="js/jquery/jquery-1.4.2.min.js"/>
<msg:js src="js/base/app-dialog.js"/>

<script type="text/javascript">
	var $C = YAHOO.util.Connect,dom = YAHOO.util.Dom,event = YAHOO.util.Event;
	
	$(document).ready(function(){
	    //隔行换色
	    $('table').each(function(){
			$(this).find('tr:even').css("background","#f7f6f6");
		});
	});
	
	function deleteUser(pkId){
		var requestURL = '${contextPath }/user/deleteUser.do?pkIds=' + pkId;
		deleteOne(requestURL, '', 'true');
	}
	
	function deleteFunction(){
		var requestURL = '${contextPath }/user/deleteUser.do';
		var responseURL = '${contextPath }/user/listAllUser.do';
		deleteMore('pkId', requestURL, responseURL);
	}
</script>

<jsp:include page="/WEB-INF/jsp/base/head.jsp">
	<jsp:param value="所有用户" name="title"/>
	<jsp:param value="true" name="delete"/>
</jsp:include>

<div id="listDiv" onmouseover="mouseOverOrOut();">
	<table width="100%" id="tbl" class="fb_result">
		<tr>
			<th width="3%" class="fb_result_head">
				<input type="checkbox" id="selAll" name="selAll" onclick="selectAll('selAll','pkId');">
			</th>
			<th class="fb_result_head" width="25%">
				登录名
			</th>
			<th class="fb_result_head" width="25%">
				姓名
			</th>
			<th class="fb_result_head" width="15%">
				性别
			</th>
			<th class="fb_result_head" width="25%">
				注册时间
			</th>
			<th class="fb_result_head" width="7%">
				操作
			</th>
		</tr>
		<c:forEach items="${paginationSupport.items}" var="user">
			<tr>
				<td>
					<input type="checkbox" name="pkId" id="pkId" value="${user.pkId}">
				</td>
				<td>
					${user.username}
				</td>
				<td>
					${user.truename}
				</td>
				<td>
					<c:if test="${user.sex == 0}">
						<c:out value="不男不女"/>
					</c:if>
					<c:if test="${user.sex == 1}">
						<c:out value="男"/>
					</c:if>
					<c:if test="${user.sex == 2}">
						<c:out value="女"/>
					</c:if>
				</td>
				<td>
					<msg:formatDate value="${user.createDate}"/>
				</td>
				<td>
					<a href="javaScript:deleteUser('${user.pkId}');">
						<img src="${contextPath}/image/wiseduimg/delete.gif" title="删除">
					</a>
				</td>
			</tr>
		</c:forEach>
	</table>
</div>

<c:url var="paginationAction" value="user/listAllUser.do"/>
<%@ include file="/WEB-INF/jsp/common/pagination.jsp"%>