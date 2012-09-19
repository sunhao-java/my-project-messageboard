<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/includes.jsp"%>
<%@ include file="/WEB-INF/jsp/common/common_js.jsp" %>

<msg:css href="css/style.css"/>
<msg:js src="js/base/app-dialog.js"/>
<msg:js src="js/validate.js"/>
<msg:js src="js/jquery/jquery-1.4.2.min.js"/>
<msg:js src="js/jquery/easyloader.js"/>

<script type="text/javascript">
	var $C = YAHOO.util.Connect,dom = YAHOO.util.Dom,event = YAHOO.util.Event;
	var count = '${paginationSupport.totalRow }';
    $(document).ready(function(){
        using(['reply', 'emoticon', 'simpleTip'], function(){
            $('input.publishReply').click(function(){
                publishReply(this);
            });
        });
    });

	function onTextAreaFource(id){
		if(dom.get("reply" + id).value == '发表评论' && dom.get("title" + id).value == '输入标题'){
			dom.get("reply" + id).value = "";
			dom.get("title" + id).value = "";
		}
	}
	
	function hideTextArea(id){
		if(dom.get("reply" + id).value == '' && dom.get("title" + id).value == ''){
			dom.get("replyDiv" + id).style.display = "none";
			dom.get("toReplyDiv" + id).style.display = "";
		}
	}
	
	function onToTextAreaFource(id, index){
		var reply = $('.replyDiv');
		if(reply && reply.length > 0){
			$(reply[0]).remove();
			$('.toreplyDiv').show();
		}
		
		var reply = '<div class="replyDiv" id="replyDiv' + id + '">' +
					'	<p class="replyimage">' +
        			'		<msg:head userId="${loginUser.pkId}" headType="2"/>' +
					'	</p>' +
					'	<form action="" id="replyForm_' + index + '">' +
					'		<input type="hidden" name="replyUserId" value="${loginUser.pkId}"/>' +
					'		<div class="contentDiv">' +
					'			&nbsp;标题&nbsp;' +
					'			<input type="text" class="f_text" name="title" id="title' + id + '" tip="输入标题"/><br/>' +
					'			&nbsp;内容' +
					'			<textarea id="reply' + id + '" name="replyContent" class="replyText" tip="输入评论"></textarea>' +
					'			<div class="buttonDiv extBtn">' +
					'				<a class="emoticon" id="emot_' + index + '" messageId="${message.pkId }" href="javascript:void(0);" title="插入表情">' +
					'				<i></i>表情<span></span>' +
					'				</a>' +
					'				<input type="button" value="发表" class="publishReply" messageId="' + id + '" onclick="publishReply(this);"/>' +
					'			</div>' +
					'		</div>' +
					'	</form>' +
					'</div>';
		$("#toReplyDiv" + id).before($(reply));
		$("#toReplyDiv" + id).hide();
		
		$('#emot_' + index).emoticon({
			panel: 'reply' + id
		});
    	
    	$('#replyForm_' + index).formTip();
	}

    function publishReply(input){
        input = $(input);
        var messageId = input.attr('messageId');
        var title = $('#title' + messageId).val();
        var content = $('#reply' + messageId).val();
        
        YAHOO.util.reply({
            title: title,
            content: content,
            resourceId: messageId,
            resourceType: '${resourceType}',
            success: function(){
                window.location.reload(true);
            }
        });
    }
</script>

<jsp:include page="/WEB-INF/jsp/base/head.jsp">
	<jsp:param value="查看所有留言" name="title"/>
</jsp:include>

<div class="content01" style="min-height: 600px !important; padding-top: 10px;">
	<c:forEach items="${paginationSupport.items }" var="message" varStatus="status">
		<div class="list" style="margin-left: 10px;">
			<ul>
				<li>
					<div class="messageDetail">
						<p class="image" style="position: absolute;">
	                        <msg:head userId="${message.createUserId}" headType="2"/>
						</p>
						<div class="head">
							<h4>
								<a href="${contextPath}/message/inDetailJsp.do?pkId=${message.pkId}">
									${message.title }
								</a>
							</h4>
							<span class="author">
								<c:choose>
									<c:when test="${loginUser.pkId eq message.createUserId}">
										<a href="${contextPath}/user/userInfo.do">
											${message.createUser.truename }
										</a>
									</c:when>
									<c:otherwise>
										<a href="${contextPath}/user/userInfo.do?viewUserId=${message.createUserId}">
											${message.createUser.truename }
										</a>
									</c:otherwise>
								</c:choose>
							</span>
							<span class="info">
								<msg:formatDate value="${message.createDate }"/>
							</span>
							<span class="pipe">|</span>
							<span class="info">
								位置：${message.ip }
							</span>
							<span class="pipe">|</span>
							<span class="info">
								<c:choose>
									<c:when test="${not empty message.createUser.homePage}">
										<a href="${message.createUser.homePage }" target="_blank">
											<img class="imgLink" src="${contextPath }/image/contact/homepage.gif" title="主页"/>
										</a>
									</c:when>
									<c:otherwise>
										<img class="imgLink" src="${contextPath }/image/contact/homepage_grey.gif"/>
									</c:otherwise>
								</c:choose>
								<c:choose>
									<c:when test="${not empty message.createUser.email }">
										<a href="mailto:${message.createUser.email }" title="发送邮件">
											<img class="imgLink" src="${contextPath }/image/contact/email.gif"/>
										</a>
									</c:when>
									<c:otherwise>
										<img class="imgLink" src="${contextPath }/image/contact/email_grey.gif" alt="" />
									</c:otherwise>
								</c:choose>
								<c:choose>
									<c:when test="${not empty message.createUser.qq }">
										<a target="_blank" href="http://wpa.qq.com/msgrd?v=3&uin=${message.createUser.qq }&site=qq&menu=yes" 
											title="QQ聊天">
											<img class="imgLink" src="${contextPath }/image/contact/qq.gif"/>
										</a>
									</c:when>
									<c:otherwise>
										<img class="imgLink" src="${contextPath }/image/contact/qq_grey.gif"/>
									</c:otherwise>
								</c:choose>
							</span>
						</div>
						<div class="content">
							<p>
								<msg:text endText="..." length="100" text="${message.content }" escapeHtml="true"/>
							</p>
							<a href="${contextPath}/message/inDetailJsp.do?pkId=${message.pkId}">阅读全文</a>
						</div>
						<div class="toreplyDiv" id="toReplyDiv${message.pkId }">
							<div class="contentDiv">
								<textarea id="toreply" class="toreplyText" 
									onclick="onToTextAreaFource('${message.pkId }', '${status.index }')">发表评论</textarea>
							</div>
						</div>
					</div>
				</li>
			</ul>
		</div>
	</c:forEach>
	
	<c:url var="paginationAction" value="message/listMessage.do"/>
	<%@ include file="/WEB-INF/jsp/common/pagination.jsp"%>
</div>