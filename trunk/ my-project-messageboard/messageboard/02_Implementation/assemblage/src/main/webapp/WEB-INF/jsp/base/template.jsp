<!--
* 回复组件的jsp模板.
*
* Developed By: sunhao
* Mail: sunhao.java@gmail.com
* Time: 12-9-7 下午9:42
* Version:1.0
* History:
-->
<style type="text/css">
    .template{
        display: none;
        visibility: hidden;
    }
</style>

<textarea id="toReply" class="template">
    <div id="reply-div" class="blog-cmts" style="width: ${'$'}{width}">
        <form action="" method="post" id="reply-form">
            {if title}
            <p style="margin-bottom: 0px;">
                <msg:message code="reply.template.title"/>&nbsp;&nbsp;
                <input type="text" msg="<msg:message code="reply.template.notitle.warn"/>" min="1" max="200" require="true" datatype="Limit"
                       name="title" class="f_text">
            </p>
            {/if}
            <p style="margin-bottom: 0px;">
                <msg:message code="reply.template.content"/>&nbsp;&nbsp;
                &lt;textarea msg="<msg:message code="reply.template.nocontent.warn"/>" min="1" max="1300" datatype="Limit" name="content"
                          id="content" style="width: 400px;"&gt;&lt;/textarea&gt;
            </p>
            <p class="ac1t">
                <input type="button" class="f-button" id="replyBtn" value="<msg:message code="reply.template.btn.reply"/>">
            </p>
        </form>
    </div>
</textarea>

<textarea id="displayReply" class="template">
    <div class="blog-cmts" id="showReply" style="width: ${'$'}{width}">
        <ol style="padding-left: 0px;overflow: hidden;">
            {for re in reply}
                {var canDelete = isDelete && true}
                <li class="blog-li" id="replyLi${'$'}{re.pkId}">
                    <div class="post">
                        <p class="image">
                            <a href="#">
                                <img src="${contextPath}/head.jpg?userId=${'$'}{re.creatorId}" title="${'$'}{re.creator.truename}">
                            </a>
                        </p>
                        <div class="info">
                            <span class="author">
                                <a href="#">
                                    <span class="visitor_online">${'$'}{re.creator.truename}</span>
                                </a>
                            </span>
                            <span class="time">
                                ${'$'}{re.replyDate}
                            </span>
                            <span class="pipe time">|</span>
                            <span class="time">
                                ${'$'}{re.title}
                            </span>
                            {if canDelete}
                                <span>
                                    <a class="deleteLink" href="javaScript:void(0);" replyId="${'$'}{re.pkId}">
                                        <img src="${contextPath}/image/wiseduimg/delete.gif" title="<msg:message code="reply.delete.title"/>">
                                    </a>
                                </span>
                            {/if}
                        </div>
                        <div style="margin: 0 0 0 0;" class="content">
                            ${'$'}{re.content}
                        </div>
                    </div>
                </li>
            {/for}
        </ol>
    </div>
</textarea>