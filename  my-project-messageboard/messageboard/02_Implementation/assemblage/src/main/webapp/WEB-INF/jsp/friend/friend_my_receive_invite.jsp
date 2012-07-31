<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/includes.jsp"%>
<%@ include file="/WEB-INF/jsp/common/common_js.jsp" %>

<!--
* 我收到的邀请 页面
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

<jsp:include page="/WEB-INF/jsp/base/head.jsp">
	<jsp:param value="联系人管理" name="title"/>
</jsp:include>

<script type="text/javascript">
	var count = '${paginationSupport.totalRow }';
	$(document).ready(function(){
		using("confirm", function(){
			var length = parseInt('${paginationSupport.totalRow }');
			for(var i = 0; i < length; i++){
                var flag = i;
                //同意添加好友
				$("#agreeRequest" + flag).confirm({
					confirmMessage: '确定要同意该好友申请吗？',
                    isFormatMessage: false,
                    isDelFoot: false,
                    removeElement: $('#li' + flag),
                    customSucTip: function(status){
						$("#count").html(count - 1 + "");
						count--;
						$("#msg_succ_agree").show();
						setTimeout(function() {
	                        $('#msg_succ_agree').hide('slow');
	                    }, 1000);
                    },
                    customErrTip: function(status){
                    	$("#msg-error_agree").show();
                    }
				});

                //拒绝添加好友
                $("#denylRequest" + flag).confirm({
					confirmMessage: '确定要拒绝该好友申请吗？<br/>' +
                            '<span style="float:left">理由：</span>' +
                            '<textarea name="denylMsg" style="height: 50;width: 200" id="denylMsg' + flag + '"></textarea>',
                    isFormatMessage: false,
                    isDelFoot: false,
                    height: 100,
                    removeElement: $('#li' + flag),
                    customSucTip: function(status){
						$("#count").html(count - 1 + "");
						count--;
						$("#msg_succ_denyl").show();
						setTimeout(function() {
	                        $('#msg_succ_denyl').hide('slow');
	                    }, 1000);
                    },
                    customErrTip: function(status){
                    	$("#msg-error_denyl").show();
                    },
                    handleData: function(id){
                    	var index = id.indexOf('denylRequest');
                    	var flag_ = id.substring(index + 'denylRequest'.length);
                        return "denylMsg=" + $("#denylMsg" + flag_).val();
                    }
				});
			}
		});
	});
</script>

<div class="content01">
	<jsp:include page="friend_head.jsp"/>

    <div style="display:none" class="msg-succ" id="msg_succ_agree">
	 	同意好友申请成功！
	</div>

	<div id="msg-error_agree" class="msg-error" style="display:none">
		同意好友申请失败！
	</div>

    <div style="display:none" class="msg-succ" id="msg_succ_denyl">
	 	拒绝好友申请成功！
	</div>

	<div id="msg-error_denyl" class="msg-error" style="display:none">
		拒绝好友申请失败！
	</div>

    <div id="wrap">
		<ol class="il">
			<h3>
				共收到 <span id="count">${paginationSupport.totalRow }</span> 个好友邀请
			</h3>
			<c:forEach items="${paginationSupport.items}" var="friend" varStatus="status">
				<li id="li${status.index}">
					<div class="people">
						<p class="image">
							<msg:head userId="${friend.applyUserId}"/>
						</p>
						<table class="info">
							<caption>
								<a href="${contextPath}/user/userInfo.do?viewUserId=${friend.applyUserId}">
									<span title="${friend.applyUser.truename}" class="online_width">${friend.applyUser.truename }</span> </a>
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
						</table>
						<ul class="actions">
							<li>
								<a class="agreeRequest" id="agreeRequest${status.index}" href="javascript:void(0);"
									rel="${contextPath}/friend/ajaxHandleRequest.do?faid=${friend.pkId }&agreeFlag=1">同意添加好友</a>
							</li>
                            <li>
								<a class="denylRequest" id="denylRequest${status.index}" href="javascript:void(0);"
									rel="${contextPath}/friend/ajaxHandleRequest.do?faid=${friend.pkId }&agreeFlag=2">拒绝添加好友</a>
							</li>
						</ul>
					</div>
				</li>
			</c:forEach>
		</ol>
	</div>

	<c:url var="paginationAction" value="friend/listMyReceiveInvite.do">
	</c:url>
	<%@ include file="/WEB-INF/jsp/common/pagination.jsp"%>
</div>