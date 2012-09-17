<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/common/includes.jsp" %>
<!--
* .
*
* Developed By: sunhao
* Mail: sunhao.java@gmail.com
* Time: 12-9-10 下午9:31
* Version:1.0
* History:
-->
<style>
    form.TweetReplyForm {
        border-bottom: 1px dashed #ccc;
        padding-bottom: 10px;
    }

    form.TweetReplyForm .input {
        height: 22px;
    }

    form.TweetReplyForm .input input {
        margin-left: 3px;
    }

    form.TweetReplyForm .zf {
        font-size: 9pt;
        margin: 5px 0 0 26px;
    }

    input.replyPanel{
        width:250px;
        font-size:9pt;
        padding:2px;
    }
</style>

<script type="text/javascript">

</script>

<div class="TweetRplsWrapper">
    <a id="closeTweetRpls" href="javaScript:void(0)" onclick="$(this).parent('div').parent('div').hide();$(this).parent('div').parent('div').html('')">x</a>
    <c:if test="${param.reply eq 'true'}">
        <form method="POST" action="" id="TweetReplyForm" class="TweetReplyForm">
            <div class="input">
                <msg:head userId="41" headType="2"/>
                <input type="text" style="" name="msg" class="replyPanel" id="${param.resourceId}_replyPanel" tweetId="${param.resourceId}">
                <input type="button" value="评论" class="f-button" onclick="reply($('#${param.resourceId}_replyPanel'));">
            </div>
            <div class="zf">
                &nbsp;
            </div>
        </form>
    </c:if>
    <div id="replys" class="TweetRpls">
        <ul>
            <c:forEach items="${ps.items}" var="reply">
                <li>
                    <table class="tab-table">
                        <tr>
                            <td class="portrait">
                                <a target="_blank" href="#">
                                    <msg:head userId="${reply.creatorId}" headType="2"/>
                                </a>
                            </td>
                            <td class="TweetReplyBody">
                                <div class="post">
                                    <a target="_blank" href="#">${reply.creator.truename}</a>
                                    <br>
                                    ${reply.content}
                                </div>
                                <div class="opts">
                                    <msg:formatDate value="${reply.replyTime}"/> 发布
                                </div>
                            </td>
                        </tr>
                    </table>
                </li>
            </c:forEach>
        </ul>
        <c:if test="${ps.totalRow > 5}">
            <a class="all" href="">查看所有评论 »</a>
        </c:if>
    </div>
</div>