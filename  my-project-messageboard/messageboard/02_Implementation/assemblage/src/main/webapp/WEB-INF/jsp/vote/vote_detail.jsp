<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/includes.jsp"%>
<%@ include file="/WEB-INF/jsp/common/common_js.jsp" %>

<msg:css href="css/vote.css"/>
<msg:css href="css/listVote.css"/>

<msg:js src="js/jquery/jquery-1.4.2.min.js"/>

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
	<jsp:param value="投票" name="title"/>
</jsp:include>

<div class="content">
	<jsp:include page="vote_head.jsp"/>
	<div class="ui-t2">
		<div class="ui-gf">
			<div class="vote-owner">
				<p class="image">
					<a href="${contextPath }/user/userInfo.do?viewUserId=${vote.createUser.pkId}"> 
                        <msg:userHead userId="${vote.createUser.pkId}" headType="2"/>
                    </a>
				</p>
				<div class="txt">
					<h3 style="margin-top: 0px; margin-bottom: 5px;">
						${vote.createUsername}的投票
					</h3>
					<%--<p class="actions" style="margin-top: 0px; margin-bottom: 5px;padding-left: 0px;">
						<a href="userVote.do?userId=110094">返回赵云的所有投票</a>
					</p>--%>
				</div>
				<p class="vote-info">
					发起于<msg:formatDate value="${vote.createTime}" pattern="yyyy-MM-dd"/>，
					<span class="vote-member">${vote.participantNum}</span>人参与
				</p>
			</div>
			<div id="vote-wrapper" class="vote-wrapper">
				<div class="vote-singlevoteform">
					<div class="vote-itemsform">
						<h3 style="margin-left: 0px; width: 75%;" class="vote-singleheading">
							${vote.question}
						</h3>
						<p class="vote-desc"></p>
						<c:if test="${show eq 'detail'}">
							<c:choose>
								<c:when test="${vote.isVote eq '1'}">
									<div id="vote-rap">
				                        <h3 style=" margin-left: 0px; width: 75%;" class="vote-heading">我的评论</h3>
										<ul style="margin-left: 0px; width: 75%;" class="pl vote-pl">
											<li>
												<p class="u-image">
													<a href="${contextPath }/user/userInfo.do?viewUserId=${loginUser.pkId}"> 
                                                        <msg:userHead userId="${loginUser.pkId}" headType="2"/>
														<span>${loginUser.truename }</span>
													</a>
												</p>
												<h5 style="margin-top: 0px;">
													选择：
												</h5>
												<ul class="vote-multichoice vote-multichoice-checked">
													<c:forEach items="${vote.myAnswer}" var="answer" varStatus="status">
														<li class="vote-color-${status.index % 10}"
															style="margin-top: 2px; padding-top: 0px; border-bottom-width: 0px; height: 18px;">
															${answer}
														</li>
													</c:forEach>
												</ul>
												<p class="review">
													<q>${vote.comment.commentContent }</q>
												</p>
											</li>
										</ul>
									</div>
								</c:when>
								<c:otherwise>
									<form class="vote-voteform" method="post" id="voteForm${vote.pkId}">
										<ul class="vote-oplist">
											<c:set value="${vote.voteOptions}" var="options"/>
											<c:forEach items="${options}" var="option" varStatus="status">
												<li class="vote-color-${status.index % 10}">
													<label for="vote_op_${option.pkId}"> 
														<input value="${option.pkId}" name="${vote.pkId }result[]" id="vote_op_${option.pkId}"
															<c:if test="${vote.type eq 1}">type="radio"</c:if>
															<c:if test="${vote.type eq 2}">type="checkbox"</c:if> 
														>
														${option.optionContent}
													</label>
												</li>
											</c:forEach>
										</ul>
										<div class="vote-addcmt">
											<p>
												<textarea class="unsharp" name="comment" maxlength="500"></textarea>
											</p>
											<p class="warning">
											</p>
											<p>
												<input type="button" value="确定" class="f-button" 
													onclick="saveVoteResult('${vote.pkId}', '${vote.type}', '${vote.maxOption}')">
											</p>
										</div>
									</form>
								</c:otherwise>
							</c:choose>
							<p style="float: right;">
								<a href="${contextPath}/vote/viewVoteResult.do?voteId=${vote.pkId}"
									id="vote-viewresult">»»查看投票结果</a>
							</p>
						</c:if>
						<c:if test="${show eq 'result'}">
							<div id="vote-rap">
			                    <table id="vote-chart" class="vote-chart">
									<c:set value="${vote.voteOptions}" var="options"/>
									<c:forEach items="${options}" var="option" varStatus="status">
										<tr class="vote-color-${status.index % 10}">
			                                <td class="vote-option"><span>${option.optionContent}</span></td>
			                                <td class="vote-chart-percentage"><span><i style="width:${option.selectPercent}%"><b></b></i></span></td>
			                                <td class="vote-percentage"><span class="num-precent">${option.selectPercent}% </span> (${option.selectNum}人)</td>
			                            </tr>
									</c:forEach>
			                    </table>
			                </div>
			                <c:if test="${not empty vote.myAnswer}">
				                <div id="vote-rap">
			                        <h3 style=" margin-left: 0px; width: 75%;" class="vote-heading">我的评论</h3>
									<ul style="margin-left: 0px; width: 75%;" class="pl vote-pl">
										<li>
											<p class="u-image">
												<a href="#"> 
                                                    <msg:userHead userId="${loginUser.pkId}" headType="2"/>
													<span>${loginUser.truename }</span>
												</a>
											</p>
											<h5 style="margin-top: 0px;">
												选择：
											</h5>
											<ul class="vote-multichoice vote-multichoice-checked">
												<c:forEach items="${vote.myAnswer}" var="answer" varStatus="status">
													<li class="vote-color-${status.index % 10}"
														style="margin-top: 2px; padding-top: 0px; border-bottom-width: 0px; height: 18px;">
														${answer}
													</li>
												</c:forEach>
											</ul>
											<p class="review">
												<q>${vote.comment.commentContent }</q>
											</p>
										</li>
									</ul>
								</div>
			                </c:if>
			                <c:if test="${not empty vote.answers}">
				                <div id="vote-rap">
			                        <h3 style=" margin-left: 0px; width: 75%;" class="vote-heading">其他评论</h3>
									<ul style="margin-left: 0px; width: 75%;" class="pl vote-pl">
										<c:set value="${vote.comments }" var="voteComments"/>
										<c:forEach items="${vote.answers }" var="answer_">
											<c:if test="${answer_.answerUserId ne loginUser.pkId }">
												<li>
													<p class="u-image">
														<a href="#"> 
                                                            <msg:userHead userId="${answer_.answerUser.pkId}" headType="2"/>
															<span>${answer_.answerUser.truename }</span>
														</a>
													</p>
													<h5 style="margin-top: 0px;">
														选择：
													</h5>
													<ul class="vote-multichoice vote-multichoice-checked">
														<msg:formatAnswer voteAnswer="${answer_.optionName }"/>
													</ul>
													<p class="review">
														<q>
															<c:forEach items="${voteComments }" var="vc">
																<c:if test="${vc.commentUserId eq answer_.answerUserId }">
																	${vc.commentContent }
																</c:if>
															</c:forEach>
														</q>
													</p>
												</li>
											</c:if>
										</c:forEach>
									</ul>
								</div>
			                </c:if>
						</c:if>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>