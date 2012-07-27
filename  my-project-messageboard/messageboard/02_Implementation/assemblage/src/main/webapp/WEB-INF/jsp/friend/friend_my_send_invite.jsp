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

<style type="text/css">
	.il {
	    margin: 0 !important;
	    padding: 10px;
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

	<div id="wrap">
		<ol class="il">
			<h3>
				共发出 <span id="count">${paginationSupport.totalRow }</span> 个好友邀请
			</h3>
			<c:forEach items="${paginationSupport.items}" var="friend" varStatus="status">
				<li id="li${status.index}">
					<div class="people">
						<p class="image">
							<msg:head userId="${friend.descUser.pkId}"/>
						</p>
						<table class="info">
							<caption>
								<a href="${contextPath}/user/userInfo.do?viewUserId=${friend.descUser.pkId}"> 
									<span title="${friend.descUser.truename}" class="online_width">${friend.descUser.truename }</span> </a>
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
								<td>${friend.applyMessage }</td>
							</tr>
						</table>
						<ul class="actions">
							<li>
								等待回应
							</li>
							<li>
								<a class="cancelRequest" id="cancelRequest${status.index}" href="javascript:void(0);"
									rel="${contextPath}/friend/ajaxCancelRequest.do?fid=${friend.pkId }">取消请求</a>
							</li>
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