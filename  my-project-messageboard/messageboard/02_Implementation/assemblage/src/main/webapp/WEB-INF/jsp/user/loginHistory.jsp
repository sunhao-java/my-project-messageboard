<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/includes.jsp"%>

<msg:css href="css/tableForm.css"/>
<msg:js src="js/mouse-over-out.js"/>
<msg:js src="js/jquery/jquery-1.4.2.min.js"/>

<jsp:include page="/WEB-INF/jsp/base/head.jsp">
	<jsp:param value="我的登录历史" name="title"/>
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
			<th width="33%" class="fb_result_head">
				登录IP
			</th>
			<th width="33%" class="fb_result_head">
				登录时间
			</th>
			<th width="34%" class="fb_result_head">
				登录浏览器
			</th>
		</tr>
		<c:forEach items="${paginationSupport.items}" var="history">
			<tr>
				<td>
					${history.loginIP}
				</td>
				<td>
					<msg:formatDate value="${history.loginTime}"/>
				</td>
				<td>
					${history.browserStr}
				</td>
			</tr>
		</c:forEach>
	</table>
</div>

<c:url var="paginationAction" value="history/listLoginHistory.do"/>
<%@ include file="/WEB-INF/jsp/common/pagination.jsp"%>