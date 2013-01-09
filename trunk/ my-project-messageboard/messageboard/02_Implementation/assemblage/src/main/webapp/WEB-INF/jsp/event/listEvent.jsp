<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/includes.jsp"%>

<msg:css href="css/tableForm.css"/>
<msg:js src="js/jquery/jquery-1.4.2.min.js"/>
<msg:js src="js/mouse-over-out.js"/>
<msg:js src="js/base/commfunction.js"/>
<msg:css href="css/colortip-1.0-jquery.css"/>
<msg:js src="js/jquery/colortip-1.0-jquery.js"/>

<jsp:include page="/WEB-INF/jsp/base/head.jsp">
	<jsp:param value="操作日志" name="title"/>
</jsp:include>

<script type="text/javascript">
	$(document).ready(function(){
	    //隔行换色
	    $('table').each(function(){
			$(this).find('tr:even').css("background","#f7f6f6");
		});
		//调用公共JS自动提示组件
		showUser('yellow','300px','wrap');
	});
	
</script>

<div id="listDiv" onmouseover="mouseOverOrOut();">
	<table width="100%" id="tbl" class="fb_result">
		<tr>
			<th width="10%" class="fb_result_head">
				操作类型
			</th>
			<th width="10%" class="fb_result_head">
				操作模块
			</th>
			<th width="15%" class="fb_result_head">
				操作者
			</th>
			<th width="15%" class="fb_result_head">
				影响者
			</th>
			<th width="18%" class="fb_result_head">
				发生时间
			</th>
			<th width="12%" class="fb_result_head">
				发生地IP
			</th>
			<th width="20%" class="fb_result_head">
				事件描述
			</th>
		</tr>
		<c:forEach items="${paginationSupport.items}" var="event">
			<tr>
				<td>
					<c:choose>
						<c:when test="${event.operationType eq '1'}">
							增加操作
						</c:when>
						<c:when test="${event.operationType eq '2'}">
							删除操作
						</c:when>
						<c:when test="${event.operationType eq '3'}">
							编辑操作
						</c:when>
					</c:choose>
				</td>
				<td>
					<c:choose>
						<c:when test="${event.resourceType eq '1'}">
							留言模块
						</c:when>
						<c:when test="${event.resourceType eq '2'}">
							用户模块
						</c:when>
						<c:when test="${event.resourceType eq '3'}">
							留言板信息模块
						</c:when>
						<c:when test="${event.resourceType eq '4'}">
							回复模块
						</c:when>
					</c:choose>
				</td>
				<td>
					${event.operator.truename}
				</td>
				<td>
					${event.owner.truename}
				</td>
				<td>
					<msg:formatDate value="${event.operationTime}"/>
				</td>
				<td>
					${event.operationIP}
				</td>
				<td>
					<msg:cutWord length="15" endString="..." cutString="${event.description}"/>
				</td>
			</tr>
		</c:forEach>
	</table>
</div>

<c:url var="paginationAction" value="event/listEvent.do"/>
<%@ include file="/WEB-INF/jsp/common/pagination.jsp"%>