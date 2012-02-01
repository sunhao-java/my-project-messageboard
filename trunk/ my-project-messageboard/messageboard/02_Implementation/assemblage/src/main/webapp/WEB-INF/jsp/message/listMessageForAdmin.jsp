<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/includes.jsp"%>
<%@ include file="/WEB-INF/jsp/common/common_js.jsp" %>

<msg:css href="css/tableForm.css"/>
<msg:js src="js/jquery/jquery-1.4.2.min.js"/>
<msg:js src="js/jquery/jquery.easyui.min.js"/>
<msg:css href="themes/default/easyui.css"/>
<msg:css href="themes/icon.css"/>
<msg:css href="css/colortip-1.0-jquery.css"/>
<msg:js src="js/jquery/colortip-1.0-jquery.js"/>
<msg:js src="js/base/commfunction.js"/>
<msg:js src="js/mouse-over-out.js"/>
<msg:js src="js/base/app-dialog.js"/>
<msg:css href="css/publish.css"/>

<msg:js src="js/base/calendar-min.js"/>
<msg:js src="js/base/app-calendar.js"/>
<msg:css href="css/base/app-calendar.css"/>

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
	
	$(document).ready(function(){
		//调用公共JS自动提示组件
		showUser('yellow','300px','wrap');
	});
	
	function viewMessage(pkId){
		var requestURL = '${contextPath}/message/inDetailJsp.do?pkId=' + pkId + '&flag=admin';
		window.location.href = requestURL;
	}
	
	function deleteMessage(pkId){
		var requestURL = '${contextPath }/message/deleteMessage.do?pkIds=' + pkId;
		var responseURL = '${contextPath}/message/listMessageAdmin.do';
		deleteOne(requestURL, responseURL, '');
	}
	
	function deleteFunction(){
		var requestURL = '${contextPath }/message/deleteMessage.do';
		var responseURL = '${contextPath }/message/listMessageAdmin.do';
		deleteMore('pkId', requestURL, responseURL);
	}
	
	function search(){
		dom.get("dataFrm").submit();
	}
</script>

<jsp:include page="/WEB-INF/jsp/base/head.jsp">
	<jsp:param value="管理所有留言" name="title"/>
	<jsp:param value="true" name="delete"/>
</jsp:include>

<div id="listFrm">
	<form id="dataFrm" action="" method="post">
		<table width="100%" border="1" class="tableform">
			<tr>
				<td class="fb_result_head" width="15%">
					标题
				</td>
				<td width="40%">
					<input type="text" class="f_text" name="title" value="${param.title}">           
				</td>
				<td class="fb_result_head" width="15%">
					姓名<span style="color: red">（真实姓名）</span>
				</td>
				<td width="40%">
					<input type="text" class="f_text" name="createUsername" value="${param.createUsername}">                 
				</td>
			</tr>
			<tr>
				<td class="fb_result_head" width="15%">
					发表时间
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
				回复条数
			</th>
			<th width="7%" class="fb_result_head">
				管理
			</th>
		</tr>
		<c:forEach items="${paginationSupport.items}" var="message">
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
					<%-- 获取HashSet的size --%>
					${fn:length(message.replys)} 
				</td>
				<td>
					<a href="javaScript:viewMessage('${message.pkId}');">
						<img src="${contextPath}/image/wiseduimg/view.gif" title="查看">
					</a>
					<a href="javaScript:deleteMessage('${message.pkId}');">
						<img src="${contextPath}/image/wiseduimg/delete.gif" title="删除">
					</a>
				</td>
			</tr>
		</c:forEach>
	</table>
</div>

<c:url var="paginationAction" value="message/listMessageAdmin.do">
	<c:param name="beginTime" value="${param.beginTime}"/>
	<c:param name="endTime" value="${param.endTime}"/>
	<c:param name="title" value="${param.title}"/>
	<c:param name="createUsername" value="${param.createUsername}"/>
</c:url>
<%@ include file="/WEB-INF/jsp/common/pagination.jsp"%>