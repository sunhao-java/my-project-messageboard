<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/includes.jsp"%>
<%@ include file="/WEB-INF/jsp/common/common_js.jsp" %>

<msg:css href="css/tableForm.css"/>
<msg:js src="js/mouse-over-out.js"/>
<msg:js src="js/jquery/jquery-1.4.2.min.js"/>
<msg:js src="js/jquery/jquery.easyui.min.js"/>
<msg:css href="themes/default/easyui.css"/>
<msg:css href="themes/icon.css"/>
<msg:css href="css/publish.css"/>

<msg:js src="js/base/calendar-min.js"/>
<msg:js src="js/base/app-calendar.js"/>
<msg:css href="css/base/app-calendar.css"/>

<jsp:include page="/WEB-INF/jsp/base/head.jsp">
	<jsp:param value="我的登录历史" name="title"/>
</jsp:include>

<script type="text/javascript">
	var $C = YAHOO.util.Connect,dom = YAHOO.util.Dom,event = YAHOO.util.Event;
	
	$(document).ready(function(){
	    //隔行换色
	    $('table').each(function(){
			$(this).find('tr:even').css("background","#f7f6f6");
		});
	    
	    //初始化时间选择组件
	    YAHOO.app.calendar.simpleInit({'id':'beginTime'});
	    YAHOO.app.calendar.simpleInit({'id':'endTime'});
	});
	
	function search(){
		dom.get("dataFrm").submit();
	}
	
	function reset(){
		window.location.href = "${contextPath}/history/listLoginHistory.do";
	}
</script>

<div id="listFrm" style="width: 90%">
	<form id="dataFrm" action="" method="post">
		<table width="100%" border="1" class="tableform">
			<tr>
				<td class="fb_result_head" width="15%">
					登录地IP
				</td>
				<td colspan="3">
					<input type="text" class="f_text" name="loginIP" value="${param.loginIP}">           
				</td>
			</tr>
			<tr>
				<td class="fb_result_head" width="15%">
					登录时间
				</td>
				<td colspan="3">
					大于<input id="beginTime" value="${param.beginTime}" name="beginTime" class="f_text"/>
					小于<input id="endTime" value="${param.endTime}" name="endTime" class="f_text"/>
				</td>
			</tr>
		</table>
		<div class="formFunctiondiv">
			<jsp:include page="/WEB-INF/jsp/common/linkbutton.jsp">
				<jsp:param value="搜索" name="search"/>
				<jsp:param value="重置" name="reset"/>
			</jsp:include>
		</div>
	</form>
</div>

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

<c:url var="paginationAction" value="history/listLoginHistory.do">
	<c:param name="loginIP" value="${param.loginIP}"/>
	<c:param name="beginTime" value="${param.beginTime}"/>
	<c:param name="endTime" value="${param.endTime}"/>
</c:url>
<%@ include file="/WEB-INF/jsp/common/pagination.jsp"%>