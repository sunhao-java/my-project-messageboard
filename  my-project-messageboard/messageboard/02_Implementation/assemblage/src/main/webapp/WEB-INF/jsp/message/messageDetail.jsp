<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/includes.jsp"%>
<%@ include file="/WEB-INF/jsp/common/common_js.jsp" %>

<msg:css href="css/detail.css"/>
<msg:js src="js/base/app-dialog.js"/>
<msg:js src="js/jquery/jquery-1.4.2.min.js"/>
<msg:js src="js/jquery/easyloader.js"/>
<msg:js src="js/validate.js"/>
<msg:js src="js/base/commfunction.js"/>
<msg:js src="js/base/app-dialog.js"/>

<msg:js src="js/base/app-swfupload.js"/>

<script type="text/javascript">
    var util = YAHOO.util;
    var $C = util.Connect;
    var dom = util.Dom;
    var event = util.Event;
    
    $(document).ready(function(){
        YAHOO.app.swfupload.showAttachments('showAttachmentPanel', {
            resourceId : '${message.pkId}',
            resourceType : 1,
            uploadId: '${message.createUser.pkId}'
        });

        using('reply', function(){
            $('#showReply').displayReply({
                resourceId: '${message.pkId}',
                resourceType: '${resourceType}',
                isDelete: '${message.createUser.pkId}' == '${loginUser.pkId}'
            });

            <c:if test="${(flag ne 'audit') && (message.createUser.pkId ne loginUser.pkId)}">
                $('#blog-cmtsft').reply({
                    resourceId: '${message.pkId}',
                    resourceType: '${resourceType}',
                    title: true,
                    success: function(id){
                        window.location.reload(true);
                    }
                });
            </c:if>
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
                <msg:head userId="${message.createUser.pkId}" headType="2"/>
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

                    <c:if test="${(flag ne 'audit') && (message.createUser.pkId ne loginUser.pkId)}">
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

            <!-- 展示评论的地方 -->
            <div id="showReply">
                
            </div>
            <!-- 日志评论-->
            <div id="blog-cmtsft">

            </div>
        </div>
    </div>
</div>
<%@ include file="/WEB-INF/jsp/base/template.jsp"%>