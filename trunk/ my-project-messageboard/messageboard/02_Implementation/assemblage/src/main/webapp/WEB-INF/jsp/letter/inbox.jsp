<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/includes.jsp"%>

<!--
* 站内信收件箱
* Developed By: sunhao
* Mail: sunhao.java@gmail.com
* Time: 12-10-24 下午11:22
* Version:1.0
* History:
-->

<msg:js src="js/jquery/jquery-1.4.2.min.js"/>
<msg:js src="js/base/commfunction.js"/>
<msg:js src="js/base/app-dialog.js"/>
<msg:css href="css/letter.css"/>

<script type="text/javascript">
	$(document).ready(function(){
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
						url: '${contextPath}/letter/delete.do?letterIds=' + letterIds,
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
	
	function setRead(type){
		var letterIds = getSelectedRows('letterId', false);
		if(letterIds == null || letterIds.length == 0){
			return;
		}
		var tip = '未读';
		if('1' == type){
			tip = '已读';
		}
		
		$.ajax({
			url: '${contextPath}/letter/setRead.do?letterIds=' + letterIds + '&type=' + type,
            type: 'post',
            dataType: 'json',
            success: function(o){
				if(o.status == '1'){
					window.location.reload(true);
				} else {
					alert('设置' + tip + '失败!');
				}
            },
            error: function(o){
            	alert('设置' + tip + '失败!');
            }
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
			<a id="batchDelbtn" href="javaScript:void(0);">删除</a>&nbsp;|&nbsp;
			<a href="javaScript:setRead(1);">设置为已读</a> &nbsp;|&nbsp;
         	<a href="javaScript:setRead(0);">设置为未读</a>
         </span>
		<span style="float:right"> 
			<a href="/message/inbox.do?r=1">已读信件 </a> &nbsp;|&nbsp; 
			<a href="/message/inbox.do?r=0">未读信件 </a> 
		</span>
	</div>

	<div id="pmsg" class="ui-t2" style="border: none;">
		<div class="pm-bd">
			<table id="pmlist">
				<c:forEach items="${paginationSupport.items }" var="lr">
					<tr <c:if test="${lr.read eq 0}">class="new"</c:if> id="letter_${lr.letter.pkId}">
						<td class="sel">
							&nbsp;&nbsp;&nbsp;&nbsp;
							<input type="checkBox" name="letterId" value="${lr.letter.pkId}">
						</td>
						<td class="ava">
							<a href="${contextPath}/user/profile.do?uid=${lr.letter.creatorId}"> 
								<msg:head userId="${lr.letter.creatorId}" headType="2"/>
							</a>
						</td>
						<td class="per">
							来自：
							<a href="${contextPath}/user/profile.do?uid=${lr.letter.creatorId}"> 
								<span class="online_width" title="${lr.letter.creator.truename}">
									${lr.letter.creator.truename}
								</span> 
							</a> &nbsp;
							<span class="time"> 
								<msg:formatDate value="${lr.letter.sendTime}" dateType="1"/>
							</span>
						</td>
						<td class="title">
							<a href="${contextPath}/letter/show.do?lid=${lr.letter.pkId}">
								<c:out value="${lr.letter.title}"/>
							</a>
							<a href="${contextPath}/letter/show.do?lid=${lr.letter.pkId}" class="summary">
								<msg:text endText="..." length="100" text="${lr.letter.content}" 
									escapeHtml="true" default="..."/>
							</a>
						</td>
						<td class="act">
							<a class="pm-del" rel="#" href="javascript:void(0)">删除 </a>
						</td>
					</tr>
				</c:forEach>
			</table>
		</div>
	</div>
	<c:url var="paginationAction" value="letter/inbox.do"/>
	<%@ include file="/WEB-INF/jsp/common/pagination.jsp"%>
</div>