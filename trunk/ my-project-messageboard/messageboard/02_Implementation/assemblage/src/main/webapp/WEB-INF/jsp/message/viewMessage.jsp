<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/includes.jsp"%>
<%@ include file="/WEB-INF/jsp/common/common_js.jsp" %>

<msg:js src="js/jquery/jquery-1.4.2.min.js"/>
<msg:js src="js/jquery/jquery.easyui.min.js"/>

<msg:css href="themes/default/easyui.css"/>
<msg:css href="themes/icon.css"/>
<msg:css href="css/colortip-1.0-jquery.css"/>
<msg:js src="js/jquery/colortip-1.0-jquery.js"/>
<msg:js src="js/base/commfunction.js"/>
<msg:js src="js/base/app-dialog.js"/>

<msg:css href="css/publish.css"/>

<style type="text/css">
	.tableform table tr td{
		
	}
</style>

<script type="text/javascript">
	var $C = YAHOO.util.Connect,dom = YAHOO.util.Dom,event = YAHOO.util.Event;
	
	function showReply(id, count){
		$("#replyTr").show();
		$("#image" + id).hide();
		$("#image1" + id).show();
	}
	
	function hideReply(id,count){
		$("#replyTr").hide();
		$("#image1" + id).hide();
		$("#image" + id).show();
	}
	
	$(document).ready(function(){
		//调用公共JS自动提示组件
		showUser('yellow','300px','wrap');
	});
	
	function deleteReply(pkId){
		var requestURL = '${contextPath}/reply/deleteReply.do?replyPkId=' + pkId;
		deleteOne(requestURL, '', true);
	}
</script>

<jsp:include page="/WEB-INF/jsp/base/head.jsp">
	<jsp:param value="查看留言" name="title"/>
</jsp:include>

<div id="listFrm">
	<form id="dataFrm" action="" method="post">
		<table width="100%" border="1" class="tableform">
			<tr>
				<td class="fb_result_head" width="15%">
					标题
				</td>
				<td width="40%">
					<c:out value="${message.title}"/>
				</td>
				<td class="fb_result_head" width="15%">
					留言时间
				</td>
				<td width="40%">
					<msg:formatDate value="${message.createDate }"/>
				</td>
			</tr>
			<tr>
				<td class="fb_result_head" width="15%">
					留言位置
				</td>
				<td width="40%">
					<c:out value="${message.ip}"/>
				</td>
				<td class="fb_result_head" width="15%">
					留言者
				</td>
				<td width="40%">
					<c:out value="${message.createUser.truename}"/>&nbsp;&nbsp;
					<img src="${contextPath}/image/wiseduimg/view.gif" title="查看留言者信息">
				</td>
			</tr>
			<tr>
				<td class="fb_result_head" width="15%">
					查看回复
				</td>
				<td colspan="3">
					共有${fn:length(message.replys) }条回复
					<c:if test="${not empty message.replys}">
     					<img id="image${message.pkId }" src="${contextPath}/image/open.gif" 
     									onclick="showReply('${message.pkId }', '${fn:length(message.replys) }');" class="img"/>
     					<img id="image1${message.pkId }" src="${contextPath}/image/close.gif" 
     									onclick="hideReply('${message.pkId }', '${fn:length(message.replys) }');" 
     										style="display: none;" class="img"/>
     				</c:if>
				</td>
			</tr>
				<tr id="replyTr" style="display: none;">
   					<td style="font-weight: bolder;text-align: left;color: #124F98;">
   						留言回复
   					</td>
   					<td colspan="3">
						<c:forEach items="${message.replys}" var="reply" varStatus="status">
	   						<table width="100%" border="0">
	   							<tr>
	   								<td class="fb_result_head" width="8%">
				   						<span style="font-weight: bold;">回复人:</span>
				   					</td>
				   					<td width="7%">
				   						<msg:cutWord length="2" endString="..." cutString="${reply.replyUser.truename}"/>
				   					</td>
				   					<td class="fb_result_head" width="10%">
				   						<span style="font-weight: bold;">回复内容:</span>
				   					</td>
				   					<td width="37%">
				   						<msg:cutWord length="20" endString="..." cutString="${reply.replyContent }"/>
				   					</td>
				   					<td class="fb_result_head" width="10%">
				   						<span style="font-weight: bold;">回复时间:</span>
				   					</td>
				   					<td width="25%">
				   						<msg:formatDate value="${reply.replyDate }"/>
				   					</td>
				   					<td width="3%">
				   						<a href="javaScript:deleteReply('${reply.pkId}');">
											<img src="${contextPath}/image/wiseduimg/delete.gif" title="删除">
										</a>
				   					</td>
	   							</tr>
	   						</table>
						</c:forEach>
   					</td>
				</tr>
			<tr>
				<td class="fb_result_head" width="15%">
					留言内容
				</td>
				<td colspan="3" height="100">
					${message.content }
				</td>
			</tr>
		</table>
	</form>
	<div class="formFunctiondiv">
		<jsp:include page="/WEB-INF/jsp/common/linkbutton.jsp">
			<jsp:param value="返回" name="back"/>
		</jsp:include>
	</div>
</div>