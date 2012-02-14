<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/includes.jsp" %>

<msg:css href="css/vote.css"/>
<msg:css href="css/listVote.css"/>

<jsp:include page="/WEB-INF/jsp/base/head.jsp">
	<jsp:param value="我的投票" name="title"/>
</jsp:include>

<div class="content">
    <jsp:include page="vote_head.jsp"/>

    <div class="ui-t2 vote-ui-t2">
        <div class="ui-gf">
            <div class="vote-wrapper">
                <div id="vote-instant-wrapper" class="vote-block">
                    <h3 class="vote-heading">我发起了${fn:length(myCreateVote)}个投票</h3>
                    <ul class="vote-list">
                        <c:forEach items="${myCreateVote}" var="myVote" varStatus="status">
                            <li class="chart${status.index}">
                                <h4>
                                    <a href="#">${myVote.question}</a>
                                </h4>
                                <div class="vote-answer">
                                    <h5>我的选择：</h5>
                                    <div class="content">
                                        <c:forEach items="${myVote.myAnswer}" var="answer" varStatus="status">
                                            <p style="margin-bottom: 0px; margin-top: 0px;"
                                               class="vote-color-${status.index}">${answer}</p>
                                        </c:forEach>
                                    </div>
                                </div>
                                <%--<p class="vote-viewmore">
                                    <a name="delVote" class="delVote" rel="del.do?ajax=ajax&amp;id=163" href="javascript:;">
                                        删除
                                    </a>
                                </p>--%>
                            </li>
                        </c:forEach>
                    </ul>
                </div>
                <div class="vote-block">
                    <h3 class="vote-heading">我参与了8个投票</h3>
                    <ul class="vote-list">
                        <li class="chart0">
                            <p class="vote-digg">384</p>
                            <h4>
                                <a href="http://portal.wiscom.com.cn:8282/vote/viewVote.do?id=174">2012年金智春晚你最喜欢的节目是哪个？</a>
                            </h4>
                            <div class="vote-answer"><h5>我的选择：</h5>
                                <div class="content">
                                    <p>歌伴舞-《CCS-青苹果乐园》</p>
                                </div>
                            </div>
                            <p class="vote-viewmore"><a
                                    href="http://portal.wiscom.com.cn:8282/vote/viewVote.do?type=result&amp;id=174">»查看投票结果</a>
                            </p>
                        </li>
                    </ul>
                </div>
            </div>
        </div>
    </div>
</div>