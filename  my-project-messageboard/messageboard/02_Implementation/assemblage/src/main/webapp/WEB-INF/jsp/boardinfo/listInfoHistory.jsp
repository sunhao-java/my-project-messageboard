<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/includes.jsp"%>

<msg:css href="css/tableForm.css"/>
<msg:js src="js/mouse-over-out.js"/>
<msg:js src="js/jquery/jquery-1.4.2.min.js"/>

<jsp:include page="/WEB-INF/jsp/base/head.jsp">
	<jsp:param value="留言板描述修改历史" name="title"/>
</jsp:include>

<script type="text/javascript">
	$(document).ready(function(){
	    //隔行换色
	    $('table').each(function(){
			$(this).find('tr:even').css("background","#f7f6f6");
		});
	});
</script>

<div id="listDiv" onmouseover="mouseOverOrOut();">
	<table width="100%" id="tbl" class="fb_result">
		<tr>
			<th width="10%" class="fb_result_head">
				修改人
			</th>
			<th width="73%" class="fb_result_head">
				描述内容
			</th>
			<th width="17%" class="fb_result_head">
				修改时间
			</th>
		</tr>
		<c:forEach items="${paginationSupport.items}" var="info">
			<tr>
				<td>
					${info.modifyUserName}
				</td>
				<td>
					<msg:text endText="..." length="60" text="${info.description}" escapeHtml="true"/>
				</td>
				<td>
					<msg:formatDate value="${info.modifyDate}"/>
				</td>
			</tr>
		</c:forEach>
	</table>
</div>