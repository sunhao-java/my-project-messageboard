<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/includes.jsp" %>
<%@ include file="/WEB-INF/jsp/common/common_js.jsp" %>

<msg:css href="css/vote.css"/>
<msg:css href="css/listVote.css"/>

<msg:js src="js/base/app-dialog.js"/>

<script type="text/javascript">
    var $C = YAHOO.util.Connect,dom = YAHOO.util.Dom,event = YAHOO.util.Event;

    function saveVoteResult(pkId, type, maxOption){
		var flag = checkForm(pkId, type, maxOption);
		if(flag){
			var voteForm = dom.get('voteForm' + pkId);
			var requestURL = '${contextPath}/vote/saveVoteResult.do?voteId=' + pkId;
			$C.setForm(voteForm);
			$C.asyncRequest("POST",requestURL,{
				success : function(o){
					var _e = eval("(" + o.responseText + ")");
					if(_e.status == 1){
						YAHOO.app.dialog.pop({'dialogHead':'提示','cancelButton':'false','alertMsg':'投票成功！',
								'confirmFunction':function(){
									window.location.reload(true);
								}});
					} else {
						YAHOO.app.dialog.pop({'dialogHead':'提示','cancelButton':'false','alertMsg':'投票失败！'});
					}
				},
				failure : function(o){
					YAHOO.app.dialog.pop({'dialogHead':'提示','cancelButton':'false','alertMsg':'错误代码:' + o.status});
				}
			});
		}
	}

    function checkForm(pkId, type, maxOption){
		if(type == '1'){
			var results = document.getElementsByName(pkId + "result[]");
			var length = 0;
			for(var i = 0; i < results.length; i++){
				if(results[i].checked){
					length += 1;
				}
			}
			if(length == 0){
				YAHOO.app.dialog.pop({'dialogHead':'出错啦','cancelButton':'false','alertMsg':'请选择投票项'});
				return false;
			}

			return true;

		} else if(type == '2') {
			var results = document.getElementsByName(pkId + "result[]");
			var length = 0;
			for(var i = 0; i < results.length; i++){
				if(results[i].checked){
					length += 1;
				}
			}
			if(length == 0){
				YAHOO.app.dialog.pop({'dialogHead':'出错啦','cancelButton':'false','alertMsg':'请选择投票项'});
				return false;
			} else if(length > maxOption) {
				YAHOO.app.dialog.pop({'dialogHead':'出错啦','cancelButton':'false','alertMsg':'注意：最多只能选择' + maxOption + '项'});
				return false;
			}
		}

		return true;
	}
</script>

<jsp:include page="/WEB-INF/jsp/base/head.jsp">
	<jsp:param value="我的投票" name="title"/>
</jsp:include>

<div class="content">
    <jsp:include page="vote_head.jsp"/>

    <div class="ui-t2 vote-ui-t2">
        <div class="ui-gf">
            <div class="vote-wrapper">
                <div id="vote-instant-wrapper" class="vote-block">
                    <h3 class="vote-heading">我发起了${myCreateVote.totalRow}个投票</h3>
                    <ul class="vote-list">
                        <c:forEach items="${myCreateVote.items}" var="myVote" varStatus="status">
                            <li class="chart${status.index}"
                                    <c:if test="${(status.index + 1) eq fn:length(myCreateVote.items)}">
                                        style="border-bottom-width: 0px"
                                    </c:if>>
                                <h4>
                                	<c:choose>
										<c:when test="${myVote.isOverTime eq '1' }">
											<a href="${contextPath}/vote/viewVoteResult.do?voteId=${myVote.pkId}">${myVote.question}</a>
										</c:when>
										<c:otherwise>
											<a href="${contextPath}/vote/viewVote.do?voteId=${myVote.pkId}">${myVote.question}</a>
										</c:otherwise>
									</c:choose>
									<c:if test="${myVote.isOverTime eq '1' }">(该投票已过期)</c:if>
                                </h4>
                                <c:choose>
									<c:when test="${myVote.isVote eq '1'}">
										<div class="vote-answer">
                                            <h5>我的选择：</h5>
                                            <div class="content">
                                                <c:forEach items="${myVote.myAnswer}" var="answer" varStatus="status">
                                                    <p style="margin-bottom: 0px; margin-top: 0px;"
                                                       class="vote-color-${status.index % 10}">${answer}</p>
                                                </c:forEach>
                                            </div>
                                        </div>
									</c:when>
									<c:when test="${myVote.isVote ne '0' }">
                                        <form method="post" id="voteForm${myVote.pkId}">
                                            <ul class="vote-oplist">
                                                <c:set value="${myVote.voteOptions}" var="options"/>
                                                <c:forEach items="${options}" var="option" varStatus="status">
                                                    <li>
                                                        <label for="vote_op_${option.pkId}">
                                                            <input value="${option.pkId}" name="${myVote.pkId }result[]" 
                                                            	id="vote_op_${option.pkId}"
                                                                <c:if test="${myVote.type eq 1}">type="radio"</c:if>
                                                                <c:if test="${myVote.type eq 2}">type="checkbox"</c:if>
                                                            >
                                                            <%-- ${option.optionContent} --%>
                                                            <p class="vote-color-${status.index % 10}" style="margin-bottom: 0px; margin-top: 0px;">
																${option.optionContent}
															</p>
                                                        </label>
                                                    </li>
                                                </c:forEach>
                                            </ul>
                                            <p class="vote-instant-comment">
                                                <textarea name="comment" maxlength="500"></textarea>
                                            </p>
                                            <c:if test="${vote.type eq 2}">
                                                <p class="warning" style="margin-bottom: 0px;color:#FF0000">提示:最多可选择${vote.maxOption }个投票项 </p>
                                            </c:if>
                                            <p style="margin-top: 0px; margin-bottom: 0px;">
                                                <input type="button" value="确定" class="f-button"
                                                        onclick="saveVoteResult('${myVote.pkId}', '${myVote.type}',
                                                                '${myVote.maxOption}')">
                                            </p>
                                        </form>
									</c:when>
								</c:choose>
                                <%--<p class="vote-viewmore">
                                    <a name="delVote" class="delVote" rel="del.do?ajax=ajax&amp;id=163" href="javascript:;">
                                        删除
                                    </a>
                                </p>--%>
                            </li>
                        </c:forEach>
                    </ul>
                    <c:url var="paginationAction" value="vote/listMyVote.do"/>
                    <c:set var="paginationSupport" value="${myCreateVote}"/>
                    <%@ include file="/WEB-INF/jsp/common/pagination.jsp"%>
                </div>
                <div class="vote-block">
                    <h3 class="vote-heading">我参与了${myAttendVote.totalRow}个投票</h3>
                    <ul class="vote-list">
                        <c:forEach var="attendVote" items="${myAttendVote.items}" varStatus="status">
                            <li class="chart${status.index}"
                                    <c:if test="${(status.index + 1) eq fn:length(myAttendVote.items)}">
                                        style="border-bottom-width: 0px"
                                    </c:if>>
                                <p class="vote-digg">${attendVote.participantNum}</p>
                                <h4>
                                    <a href="${contextPath}/vote/viewVote.do?voteId=${attendVote.pkId}">${attendVote.question}</a>
                                </h4>
                                <div class="vote-answer">
                                    <h5>我的选择：</h5>
                                    <div class="content">
                                        <c:forEach items="${attendVote.myAnswer}" var="answer" varStatus="status">
                                            <p style="margin-bottom: 0px; margin-top: 0px;"
                                               class="vote-color-${status.index % 10}">${answer}</p>
                                        </c:forEach>
                                    </div>
                                </div>
                                <p class="vote-viewmore">
                                    <a href="${contextPath}/vote/viewVoteResult.do?voteId=${attendVote.pkId}">»查看投票结果</a>
                                </p>
                            </li>
                        </c:forEach>
                    </ul>
                    <c:url var="paginationAction_" value="vote/listMyVote.do"/>
                    <c:set var="paginationSupport_" value="${myAttendVote}"/>
                    <%@ include file="/WEB-INF/jsp/common/pagination_.jsp"%>
                </div>
            </div>
        </div>
    </div>
</div>