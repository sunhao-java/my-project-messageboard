<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/includes.jsp"%>
<%@ include file="/WEB-INF/jsp/common/common_js.jsp" %>

<!--
* 邀请好友 页面
* Developed By: sunhao
* Mail: sunhao.java@gmail.com
* Time: 12-7-24 下午10:56
* Version:1.0
* History:
-->

<msg:js src="js/jquery/jquery-1.4.2.min.js"/>
<msg:css href="/css/friend.css"/>
<msg:js src="js/base/app-dialog.js"/>

<script type="text/javascript">
	$(document).ready(function(){
		//全选
		$("#selectAll").click(function(){
			var checks = $("input[type=checkbox][name=addFriend]");
			for(var i = 0; i < checks.length; i++){
				checks[i].checked = true;
			}
		});
		
		//取消全选
		$("#selectNone").click(function(){
			var checks = $("input[type=checkbox][name=addFriend]");
			for(var i = 0; i < checks.length; i++){
				checks[i].checked = false;
			}
		});
		
		$("#addFriendBtn").click(function(){
			var checks = $("input[type=checkbox][name=addFriend]");
			var params = "";
			for(var i = 0; i < checks.length; i++){
				if(checks[i].checked){
					params += "selectedUserId=" + checks[i].value + "&";
				}
			}
			if(params == ""){
				alert("没有选择任何用户！");
				return false;
			}
			
			var alertMsg = '<div style="padding:0px;position:relative"><div><div style="float:left">' + 
                    '<a href="${contextPath}/user/userInfo.do"><msg:head userId="${loginUser.pkId}"/> </a>' + 
                	'</div><div style="margin-left:110px;margin-top:20px">好友请求附言：<br> <textarea style="width:300px;" ' + 
                	'id="requestReason"></textarea></div></div><div class="clear"> </div></div>' + 
                	'<div style="border-bottom:1px solid #DDD;margin-top:30px"></div>' + 
                	'<p style="padding-left: 280px;">' +
                	'<label for="isEmailNotify"><input type="checkbox" value="1" checked="true" id="isEmailNotify">我想立刻发邮件通知他</p>' +
                	'</label>';
			var popWin = YAHOO.app.dialog.pop({
				'dialogHead':'好友请求附言',
				'alertMsg':alertMsg,
				'icon':'none',
				'diaHeight':230,
				'diaWidth':460,
				'confirmFunction':function(){
		            params += "&applyMessage=" + $("#requestReason").val() + "&isEmailNotify=" + $("#isEmailNotify").val();
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
									$("#msg_succ").show();
									setTimeout(function() {
				                        $('#msg_succ').hide('slow');
				                    }, 500);
								} else {
									popWin.cancel();
									$("#msg-error").show();
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
			
			
		});
	});
</script>

<jsp:include page="/WEB-INF/jsp/base/head.jsp">
	<jsp:param value="联系人管理" name="title"/>
</jsp:include>

<div class="content01">
	<jsp:include page="friend_head.jsp"/>
	
	<div style="display:none" class="msg-succ" id="msg_succ">
	 	好友申请成功，请等待用户同意！
	</div>
	
	<div id="msg-error" class="msg-error" style="display:none">
		好友申请失败！
	</div>

	<p class="bar">
		&nbsp; ${paginationSupport.totalRow }&nbsp;个用户&nbsp;
		<span><input type="radio" id="selectAll" name="selection"><label for="selectAll">全选</label></span>
		<span><input type="radio" checked="checked" id="selectNone" name="selection"><label for="selectNone">取消全选</label></span>
		<input type="button" value="添加选择的好友" id="addFriendBtn" name="addAll" class="f-button">
	</p>

	<div id="wrap">
		<ol class="il">
			<table cellspacing="0" cellpadding="0" border="0" style="width: 100%">
				<c:forEach items="${paginationSupport.items }" var="user" varStatus="status">
					<c:if test="${status.index % 2 eq 0}">
						<tr>
					</c:if>
						<td>
							&nbsp;
						</td>
						<td width="50%">
							<li>
								<div class="short2_people">
									<c:choose>
										<c:when test="${fn:contains(appliedIds, user.pkId)}">
											<img src="${contextPath}/image/checkbox_checked.png" class="addChexbox"/>
										</c:when>
										<c:otherwise>
											<input type="checkbox" value="${user.pkId }" name="addFriend" class="addChexbox"
												<c:if test="${loginUser.pkId eq user.pkId}">disabled="disabled"</c:if>/>
										</c:otherwise>
									</c:choose>
									<p class="image">
										<a href="${contextPath}/user/userInfo.do?viewUserId=${user.pkId}">
											<msg:head userId="${user.pkId}"/>
										</a>
									</p>
									<table class="info">
										<caption>
											<a href="${contextPath}/user/userInfo.do?viewUserId=${user.pkId}">
												${user.truename }<br> 
											</a>
											<br>
										</caption>
										<tr>
		                                    <th></th>
		                                    <td></td>
		                                </tr>
									</table>
									<ul class="actions">
										<li>
											<a href="javascript:void(0)" rel="#" class="addFriendLink">添加好友</a>
										</li>
									</ul>
								</div>
							</li>
						</td>
						<td>
							&nbsp;
						</td>
					<c:if test="${status.index % 2 eq 1}">
						</tr>
					</c:if>
				</c:forEach>
			</table>
		</ol>
	</div>

	<c:url var="paginationAction" value="friend/toAddFriend.do">
	</c:url>
	<%@ include file="/WEB-INF/jsp/common/pagination.jsp"%>
</div>