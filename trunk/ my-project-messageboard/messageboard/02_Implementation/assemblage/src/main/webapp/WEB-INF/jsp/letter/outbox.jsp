<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/includes.jsp"%>

<!--
* 站内信发件箱
* Developed By: sunhao
* Mail: sunhao.java@gmail.com
* Time: 12-11-07 下午10:58
* Version:1.0
* History:
-->
<msg:js src="js/jquery/jquery-1.4.2.min.js"/>
<msg:js src="js/jquery/easyloader.js"/>
<msg:js src="js/base/commfunction.js"/>
<msg:js src="js/base/app-dialog.js"/>
<msg:css href="css/letter.css"/>

<script type="text/javascript">
	$(document).ready(function(){
		using("confirm", function(){
			$('.pm-del').each(function(){
				var del = $(this);
				del.confirm({
					confirmMessage: '站内信',
                    removeElement: $('#letter_' + del.attr('lids')),
                    customSucTip: function(status){
						$('#msg_succ').show();
						setTimeout(function() {
	                        $('#msg_succ').hide('slow');
	                    }, 1000);
                    },
                    customErrTip: function(status){
                    	$('#msg-error').show();
                    }
				});
			});
		});
		
		$('#batchDelbtn').bind('click', function(){
			var letterIds = getSelectedRows('letterId', false);
			if(letterIds == null || letterIds.length == 0){
				return;
			}
			YAHOO.app.dialog.pop({
				'dialogHead':'提示',
				'alertMsg':'确定要删除选中的站内信?',
				'confirmFunction':function(){
					$.ajax({
						url: '${contextPath}/letter/deleteOutBox.do?lids=' + letterIds,
                        type: 'post',
                        dataType: 'json',
                        success: function(o){
							if(o.status == '1'){
								window.location.reload(true);
							} else {
								alert('删除失败!');
							}
                        },
                        error: function(o){
                        	alert('删除失败!');
                        }
					});
				}
			});
		});
	});

	function selAll(type){
		var selects = $('input[name=letterId][type=checkBox]');
		selects.each(function(){
			var s = this;
			if('1' == type)
				s.checked = true;
			else
				s.checked = false;
		});
	}
</script>

<jsp:include page="/WEB-INF/jsp/base/head.jsp">
	<jsp:param value="收件箱" name="title"/>
</jsp:include>

<div class="content01">
	<jsp:include page="letter_head.jsp"/>
	
	<p class="bar">
		${paginationSupport.totalRow } 封站内信
    </p>
	<div class="info" style="height: 25px; border-top: 1px solid #ccc; padding:10px 15px 0px;background: none repeat scroll 0 0 whiteSmoke" id="ctrlbar">
		<span style="float:left"> 
			<a href="javaScript:selAll(1);">全选 </a>&nbsp;|&nbsp; 
			<a href="javaScript:selAll(0);">取消全选 </a>&nbsp;|&nbsp; 
			<a id="batchDelbtn" href="javaScript:void(0);">删除</a>
         </span>
	</div>

	<div id="pmsg" class="ui-t2" style="border: none;">
		<div class="pm-bd">
			<table id="pmlist">
				<c:forEach items="${paginationSupport.items }" var="l">
					<c:set var="firstLur" value="${l.relations[0]}"/>
					<c:set var="size" value="${msgFun:length(l.relations)}"/>
					<tr id="letter_${l.pkId}">
						<td class="sel">
							&nbsp;&nbsp;&nbsp;&nbsp;
							<input type="checkBox" name="letterId" value="${l.pkId}">
						</td>
						<td class="ava" align="justify">
							给：
							<a href="${contextPath}/user/profile.do?uid=${l.creatorId}"> 
								<msg:head userId="${l.creatorId}" headType="2"/>
							</a>
						</td>
						<td class="per">
							<a href="${contextPath}/user/profile.do?uid=${firstLur.receiverId}"> 
								<span class="online_width" title="${firstLur.receiver.truename}">
									${firstLur.receiver.truename}
								</span> 
							</a> &nbsp;
							<c:if test="${size gt 1}">
								等
							</c:if>
							<span class="time"> 
								<msg:formatDate value="${l.sendTime}" dateType="1"/>
							</span>
						</td>
						<td class="title">
							<a href="${contextPath}/letter/show.do?lid=${l.pkId}">
								<c:out value="${l.title}"/>
							</a>
							<a href="${contextPath}/letter/show.do?lid=${l.pkId}" class="summary">
								<msg:text endText="..." length="100" text="${l.content}" 
									escapeHtml="true" default="..."/>
							</a>
						</td>
						<td class="act">
							<a class="pm-del" rel="${contextPath}/letter/deleteOutBox.do?lids=${l.pkId}" 
								href="javascript:void(0)" lids="${l.pkId}">删除 </a>
						</td>
					</tr>
				</c:forEach>
			</table>
		</div>
	</div>
	<c:url var="paginationAction" value="letter/outbox.do"/>
	<%@ include file="/WEB-INF/jsp/common/pagination.jsp"%>
</div>