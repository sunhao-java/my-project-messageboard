<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/includes.jsp"%>
<%@ include file="/WEB-INF/jsp/common/common_js.jsp" %>

<msg:css href="css/tableForm.css"/>
<msg:js src="js/mouse-over-out.js"/>
<msg:js src="js/jquery/jquery-1.4.2.min.js"/>

<script type="text/javascript">
	var $C = YAHOO.util.Connect,dom = YAHOO.util.Dom,event = YAHOO.util.Event;
	
	$(document).ready(function(){
	    //隔行换色
	    $('table').each(function(){
			$(this).find('tr:even').css("background","#f7f6f6");
		});
	});
	
	function viewMessage(pkId){
		var requestURL = '${contextPath}/message/inDetailJsp.do?pkId=' + pkId + '&flag=audit';
		window.location.href = requestURL;
	}
</script>

<jsp:include page="/WEB-INF/jsp/base/head.jsp">
	<jsp:param value="审核不通过留言" name="title"/>
</jsp:include>

<div id="listDiv" onmouseover="mouseOverOrOut();">
	<table width="100%" id="tbl" class="fb_result">
		<tr>
			<th width="3%" class="fb_result_head">
				<input type="checkbox" id="selAll" name="selAll" onclick="selectAll('selAll','pkId');">
			</th>
			<th width="32%" class="fb_result_head">
				标题
			</th>
			<th width="10%" class="fb_result_head">
				留言者
			</th>
			<th width="23%" class="fb_result_head">
				留言时间
			</th>
			<th width="15%" class="fb_result_head">
				留言地址
			</th>
			<th width="10%" class="fb_result_head">
				状态
			</th>
		</tr>
		<c:forEach items="${pagination.items}" var="message">
			<tr>
				<td>
					<input type="checkbox" name="pkId" id="pkId" value="${message.pkId}">
				</td>
				<td>
					<a href="javaScript:viewMessage('${message.pkId}');">
						<msg:cutWord length="30" endString="..." cutString="${message.title }"/>
					</a>
				</td>
				<td>
					${message.createUser.truename }
				</td>
				<td>
					<msg:formatDate value="${message.createDate }"/>
				</td>
				<td>
					${message.ip }
				</td>
				<td>
					审核不通过
				</td>
			</tr>
		</c:forEach>
	</table>
</div>