<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/includes.jsp"%>
<%@ include file="/WEB-INF/jsp/common/common_js.jsp" %>

<msg:css href="css/style.css"/>
<msg:js src="js/base/app-dialog.js"/>
<msg:js src="js/validate.js"/>

<script type="text/javascript">
	var $C = YAHOO.util.Connect,dom = YAHOO.util.Dom,event = YAHOO.util.Event;
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
	
	function onToTextAreaFource(id){
		dom.get("replyDiv" + id).style.display = "";
		dom.get("toReplyDiv" + id).style.display = "none";
		dom.get("reply" + id).value = '发表评论';
		dom.get("title" + id).value = '输入标题';
	}
	
	function publishReply(){
		var replyForm = dom.get('replyForm');
		var action = "${contextPath}/reply/replyMessage.do";
		var flag = Validator.Validate(replyForm, 3);
		if(flag){
			$C.setForm(replyForm);
			$C.asyncRequest("POST", action, {
				success : function(o){
					var _e = eval("(" + o.responseText + ")");
					if(_e.status == '1'){
						YAHOO.app.dialog.pop({'dialogHead':'提示','cancelButton':'false','alertMsg':'发表成功！',
								'confirmFunction':function(){
									window.location.reload(true);
								}});
					} else {
						YAHOO.app.dialog.pop({'dialogHead':'提示','cancelButton':'false','alertMsg':'发表失败！'});
					}
				},
				failure : function(o){
					YAHOO.app.dialog.pop({'dialogHead':'提示','cancelButton':'false','alertMsg':'错误代码:' + o.status});
				}
			});
		}
	}
</script>

<jsp:include page="/WEB-INF/jsp/base/head.jsp">
	<jsp:param value="查看所有留言" name="title"/>
</jsp:include>

<c:forEach items="${paginationSupport.items }" var="message">
	<div class="list">
		<ul>
			<li>
				<div class="messageDetail">
					<p class="image" style="position: absolute;">
						<img src="${contextPath }/${message.createUser.headImage}" title="${message.createUser.truename }">
					</p>
					<div class="head">
						<h4>
							<a href="http://www.baidu.com">
								${message.title }
							</a>
						</h4>
						<span class="author">
							<a href="#">
								${message.createUser.truename }
							</a>
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
						<a href="#">阅读全文</a>
					</div>
					<div class="replyDiv" id="replyDiv${message.pkId }" style="display: none;">
						<p class="replyimage">
							<img src="${contextPath }/${loginUser.headImage}">
						</p>
						<form action="" id="replyForm">
							<input type="hidden" name="replyUserId" value="${loginUser.pkId}"/>
							<input type="hidden" name="messageId" value="${message.pkId}"/>
							<div class="contentDiv">
								&nbsp;标题&nbsp;
								<input type="text" class="f_text" name="title" id="title${message.pkId }" value="输入标题"
									onclick="onTextAreaFource('${message.pkId }')" onblur="hideTextArea('${message.pkId }')"/>
								&nbsp;内容
								<textarea id="reply${message.pkId }" name="replyContent" class="replyText" 
									onclick="onTextAreaFource('${message.pkId }')" onblur="hideTextArea('${message.pkId }')"
									dataType="Limit" max="1300" min="2" msg="不能为空,且不超过1300字符">
									发表评论
								</textarea>
								<div class="buttonDiv">
									<input type="button" value="发表" onclick="publishReply()"/>
								</div>
							</div>
						</form>
					</div>
					<div class="toreplyDiv" id="toReplyDiv${message.pkId }">
						<div class="contentDiv">
							<textarea id="toreply" class="toreplyText" onclick="onToTextAreaFource('${message.pkId }')">发表评论</textarea>
						</div>
					</div>
				</div>
			</li>
		</ul>
	</div>
</c:forEach>

<c:url var="paginationAction" value="message/listMessage.do"/>
<%@ include file="/WEB-INF/jsp/common/pagination.jsp"%>