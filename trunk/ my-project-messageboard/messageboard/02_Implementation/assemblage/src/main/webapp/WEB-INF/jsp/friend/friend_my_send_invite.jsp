<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/includes.jsp"%>
<%@ include file="/WEB-INF/jsp/common/common_js.jsp" %>

<!--
* 我发出的邀请 页面
* Developed By: sunhao
* Mail: sunhao.java@gmail.com
* Time: 12-7-24 下午10:56
* Version:1.0
* History:
-->

<msg:css href="/css/friend.css"/>
<msg:js src="js/jquery/jquery-1.4.2.min.js"/>
<msg:js src="js/jquery/easyloader.js"/>
<msg:js src="js/base/app-dialog.js"/>

<style type="text/css">
	.il {
	    margin: 0 !important;
	    padding: 10px;
	}
	
	.yui-dialog .yui-overlay{
		background: none repeat scroll 0 0 #FFFFFF !important;
	    padding: 0px !important;
	}
</style>

<script type="text/javascript">
	var count = '${paginationSupport.totalRow }';
	$(document).ready(function(){
		using("confirm", function(){
			var length = parseInt('${paginationSupport.totalRow }');
			for(var i = 0; i < length; i++){
				$("#cancelRequest" + i).confirm({
					confirmMessage: '好友申请',
                    isFormatMessage: true,
                    removeElement: $('#li' + i),
                    customSucTip: function(status){
						$("#count").html(count - 1 + "");
						count--;
						$("#msg_succ").show();
						setTimeout(function() {
	                        $('#msg_succ').hide('slow');
	                    }, 1000);
                    },
                    customErrTip: function(status){
                    	$("#msg-error").show();
                    }
				});
			}
		});
	});
	
	function apply(faid, name, uid){
		var alertMsg = '<div style="padding:0px;position:relative"><div><div style="float:left">' + 
                '<a href="${contextPath}/user/userInfo.do?viewUserId=' + uid + '">' +
                '<img title="' + name + '" src="/message/head.jpg?userId=' + uid + '&amp;headType=0"> </a>' + 
               	'</div><div style="margin-left:110px;margin-top:20px">好友请求附言：<br> <textarea style="width:300px;" ' + 
               	'id="requestReason"></textarea></div></div><div class="clear"> </div></div>' + 
               	'<div style="border-bottom:1px solid #DDD;margin-top:30px"></div>' + 
               	'<p style="padding-left: 280px;">' +
               	'<label for="isEmailNotify"><input type="checkbox" value="1" checked="true" id="isEmailNotify">我想立刻发邮件通知他</p>' +
               	'</label>';
        
		var popWin = YAHOO.app.dialog.pop({
			'dialogHead':'再次添加 ' + name + ' 为好友',
			'alertMsg':alertMsg,
			'icon':'none',
			'diaHeight':230,
			'diaWidth':460,
			'confirmFunction':function(){
	            var params = "faid=" + faid + "&applyMessage=" + $("#requestReason").val() + 
	            					"&isEmailNotify=" + $("#isEmailNotify").val();
				$.ajax({
					type: 'POST',
					url: '${contextPath}/friend/applyFriends.do',
					data: params,
					dataType: 'json',
					success: function(o){
						try{
							var status = o.status;
							if(status == 1){
								popWin.cancel();
								$("#msg_succ1").show();
								setTimeout(function() {
			                        $('#msg_succ1').hide('slow');
			                    }, 1000);
							} else {
								popWin.cancel();
								$("#msg-error1").show();
							}
						}catch(e){
							alert(e);
						}
					},
					error: function(o){
						alert("系统内部错误，请联系管理员！");
					}
				});
			}
		});
	}
</script>

<jsp:include page="/WEB-INF/jsp/base/head.jsp">
	<jsp:param value="联系人管理" name="title"/>
</jsp:include>

<div class="content01">
	<jsp:include page="friend_head.jsp"/>
	
	<div style="display:none" class="msg-succ" id="msg_succ">
	 	取消好友申请成功！
	</div>
	
	<div id="msg-error" class="msg-error" style="display:none">
		取消好友申请失败！
	</div>
	
	<div style="display:none" class="msg-succ" id="msg_succ1">
	 	好友申请成功！
	</div>
	
	<div id="msg-error" class="msg-error1" style="display:none">
		好友申请失败！
	</div>

	<div id="wrap">
		<ol class="il">
			<h3>
				共发出 <span id="count">${paginationSupport.totalRow }</span> 个好友邀请
			</h3>
			<c:forEach items="${paginationSupport.items}" var="friend" varStatus="status">
				<li id="li${status.index}">
					<div class="people">
						<p class="image">
							<msg:head userId="${friend.inviteUserId}"/>
						</p>
						<table class="info">
							<caption>
								<a href="${contextPath}/user/userInfo.do?viewUserId=${friend.inviteUserId}">
									<span title="${friend.inviteUser.truename}" class="online_width">${friend.inviteUser.truename }</span> </a>
							</caption>
							<tr>
								<td></td>
							</tr>
							<tr>
								<td></td>
							</tr>
							<tr>
								<td class="title">请求附言:</td>
							</tr>
                            <tr>
                                <td>${friend.message }</td>
                            </tr>
                            <c:choose>
                                <c:when test="${friend.result eq 2}">
                                    <tr>
                                        <td>&nbsp;</td>
                                    </tr>
                                    <tr>
                                        <td class="title" style="color: #c00">拒绝附言:</td>
                                    </tr>
                                    <tr>
                                        <td>${friend.remark }</td>
                                    </tr>
                                </c:when>
                            </c:choose>
						</table>
						<ul class="actions">
							<li>
                                <c:choose>
                                    <c:when test="${friend.result eq 2}">
                                        对方已拒绝
                                    </c:when>
                                    <c:when test="${friend.result eq 1}">
                                        对方已同意
                                    </c:when>
                                    <c:otherwise>
                                        等待回应
                                    </c:otherwise>
                                </c:choose>
							</li>
                            <c:if test="${friend.result ne 1}">
                                <c:if test="${friend.result eq 2}">
                                    <li>
                                        <a class="requestAjax" id="requestAjax${status.index}" href="javascript:void(0);"
                                            rel="${contextPath}/friend/ajaxCancelRequest.do?faid=${friend.pkId }"
                                            onclick="apply('${friend.pkId}', '${friend.inviteUser.truename}', '${friend.inviteUserId}');">再次请求</a>
                                    </li>
                                </c:if>
                                <li>
                                    <a class="cancelRequest" id="cancelRequest${status.index}" href="javascript:void(0);"
                                        rel="${contextPath}/friend/ajaxCancelRequest.do?faid=${friend.pkId }">取消请求</a>
                                </li>
                            </c:if>
						</ul>
					</div>
				</li>
			</c:forEach>
		</ol>
	</div>
	
	<c:url var="paginationAction" value="friend/listMySendInvite.do">
	</c:url>
	<%@ include file="/WEB-INF/jsp/common/pagination.jsp"%>
</div>