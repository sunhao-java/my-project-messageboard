<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/includes.jsp"%>
<%@ include file="/WEB-INF/jsp/common/common_js.jsp" %>

<msg:css href="css/detail.css"/>
<msg:js src="js/base/app-dialog.js"/>
<msg:js src="js/jquery/jquery-1.4.2.min.js"/>
<msg:js src="js/validate.js"/>
<msg:js src="js/base/commfunction.js"/>
<msg:js src="js/base/app-dialog.js"/>

<msg:js src="js/base/app-swfupload.js"/>

<script type="text/javascript">
	var $C = YAHOO.util.Connect;
 	var dom = YAHOO.util.Dom;
 	var event = YAHOO.util.Event;

    $(document).ready(function(){
        YAHOO.app.swfupload.showAttachments('showAttachmentPanel', {
            resourceId : ${message.pkId},
            resourceType : 1,
            uploadId: ${message.createUser.pkId}
        });
    });
 	
	function reply(){
		var replyForm = dom.get("reply-form");
		var flag = Validator.Validate(replyForm, 3);
		if(flag){
			var action = "${contextPath}/reply/replyMessage.do";
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
	
	function deleteReply(pkId){
		var requestURL = '${contextPath}/reply/deleteReply.do?replyPkId=' + pkId;
		deleteOne(requestURL, '', true);
	}
</script>

<jsp:include page="/WEB-INF/jsp/base/head.jsp">
	<jsp:param value="查看留言详情" name="title"/>
</jsp:include>

<div class="content">
	<div class="header">
		<p class="image">
			<a href="#">
                <msg:userHead userId="${message.createUser.pkId}" headType="2"/>
			</a>
		</p>
		<h2>
			${message.createUser.truename }
			<span class="pipe">-</span><em>${message.title }</em>
		</h2>
		<div class="toc">
			<p class="bar">
				共${messageCount }篇记事
				<span class="pipe">|</span>
				<c:choose>
					<c:when test="${loginUser.pkId eq message.createUserId}">
						<a href="${contextPath}/message/inListMyMessageJsp.do">
							去我的所有留言列表
						</a>
					</c:when>
					<c:otherwise>
						<a href="${contextPath}/message/inListMyMessageJsp.do?viewWhoId=${message.createUserId}">
							去${message.createUser.truename }的所有留言列表
						</a>
					</c:otherwise>
				</c:choose>
			</p>
		</div>
	</div>
	<div class="ui-t2">
		<div class="ui-gf">
			<div class="blog-entry na">
				<div class="info">
					<h4>
						${message.title }
					</h4>
					<span class="time"><msg:formatDate value="${message.createDate}"/></span>

					<c:if test="${flag ne 'audit'}">
						<span class="pipe">|</span>
						<a href="#reply-div">回复本条留言</a>
					</c:if>
				</div>
				<div id="blogContent" class="content orig-content">
					<div>
						<div style="width: 100%;" class="divCla">
							<span class="SubjectMFontBlod">
								<span>
									<strong>${message.title }</strong>
								</span>
							</span>
							<br>
							<br>
							<div style="width: 100%">
								${message.content }
							</div>
						</div>
					</div>
				</div>
			</div>

            <!-- 展示附件的地方 -->
            <div id="showAttachmentPanel">
                
            </div>

            <%--<div class="attachment">
                <div class="post-attachments-div">
                    <div class="post-attachments-title">
                        附件：
                    </div>
                    <div class="post-attachments-files">
                        <p>
                            <img src="${contextPath}/image/file/ppt.gif" alt="常州信息职业技术学院--曹贵婷--乔木开发.pptx">
                            <a href="/downloadattachment/3513/常州信息职业技术学院--曹贵婷--乔木开发.pptx">
                                常州信息职业技术学院--曹贵婷--乔木开发.pptx
                            </a>
                        </p>

                        <p>
                            <img src="${contextPath}/image/file/ppt.gif" alt="钟山学院--严丹--测试.ppt">
                            <a href="/downloadattachment/3518/钟山学院--严丹--测试.ppt">
                                钟山学院--严丹--测试.ppt
                            </a>
                        </p>

                        <p>
                            <img src="${contextPath}/image/file/ppt.gif" alt="安徽理工大学--孙昊--产品中心开发.pptx">
                            <a href="/downloadattachment/3522/安徽理工大学--孙昊--产品中心开发.pptx">
                                安徽理工大学--孙昊--产品中心开发.pptx
                            </a>
                        </p>
                    </div>
                </div>
            </div>--%>
            
			<!-- 日志评论-->
			<div class="blog-cmts" id="blog-cmtsft">
				<ol style="padding-left: 0px;overflow: hidden;">
					<c:set var="replys" value="${message.replys}"/>
					<c:forEach var="reply" items="${replys}">
						<li class="blog-li">
							<div class="post">
								<p class="image">
									<a href="#">
                                        <msg:userHead userId="${reply.replyUser.pkId}" headType="2"/>
									</a>
								</p>
								<div class="info">
									<span class="author">
										<a href="#">
											<span class="visitor_online">${reply.replyUser.truename}</span>
										</a> 
									</span>
									<span class="time">
										<msg:formatDate value="${reply.replyDate}"/>
									</span>
									<span class="pipe time">|</span>
									<span class="time">
										<c:out value="${reply.title}"/>
									</span>
									<c:if test="${flag eq 'admin'}">
										<span>
											<a href="javaScript:deleteReply('${reply.pkId}');">
												<img src="${contextPath}/image/wiseduimg/delete.gif" title="删除">
											</a>
										</span>
									</c:if>
								</div>
								<div style="margin: 0 0 0 0;" class="content">
									${reply.replyContent}
								</div>
							</div>
						</li>
					</c:forEach>
				</ol>
				<c:if test="${flag ne 'audit'}">
					<!-- 回复 -->
					<div class="comment-post" id="reply-div">
						<form id="reply-form" method="post" action="">
							<input type="hidden" value="${loginUser.pkId}" name="replyUserId"/>
							<input type="hidden" value="${message.pkId}" name="messageId"/>
							<p style="margin-bottom: 0px;">
								标题&nbsp;&nbsp;
								<input type="text" class="f_text" name="title" 
									dataType="Limit" require="true" max="200" min="1" msg="不能为空,且不超过100字符"/>
							</p>
							<p style="margin-bottom: 0px;">
								内容&nbsp;&nbsp;
								<textarea style="width: 400px;" id="replyContent" name="replyContent"
									dataType="Limit" max="1300" min="1" msg="不能为空,且不超过1300字符"></textarea>
							</p>
							<div class="act">
					            <div style="float:right;">
					            	<input type="button" value="回复" id="submitBtn" class="f-button" onclick="reply()">
					            </div>
					        </div>
						</form>
					</div>
				</c:if>
			</div>
		</div>
	</div>
</div>