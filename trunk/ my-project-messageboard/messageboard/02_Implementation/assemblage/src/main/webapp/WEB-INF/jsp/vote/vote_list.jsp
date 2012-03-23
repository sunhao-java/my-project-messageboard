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
	<jsp:param value="所有投票" name="title"/>
</jsp:include>

<div class="content01">
	<jsp:include page="vote_head.jsp"/>
	
	<div class="vote-wrapper">
		<div id="vote-instant-wrapper">
			<ul class="vote-list">
				<c:forEach items="${pagination.items}" var="vote">
					<c:set value="${vote.voteOptions}" var="options"/>
					<c:set value="${vote.createUser}" var="user"/>
					<c:set value="${vote.myAnswer}" var="myAnswer"/>
					<li class="wp">
						<form method="post" id="voteForm${vote.pkId}">
							<div class="vote-instant-singleitem">
								<p class="vote-digg">
									${vote.participantNum}
								</p>
								<h4>
									<c:choose>
										<c:when test="${vote.isOverTime eq '1' }">
											<a href="${contextPath}/vote/viewVoteResult.do?voteId=${vote.pkId}">${vote.question}</a>
										</c:when>
										<c:otherwise>
											<a href="${contextPath}/vote/viewVote.do?voteId=${vote.pkId}">${vote.question}</a>
										</c:otherwise>
									</c:choose>
									<c:if test="${vote.isOverTime eq '1' }">(该投票已过期)</c:if>
								</h4>
								<p class="image">
									<a href="${contextPath }/user/userInfo.do?viewUserId=${user.pkId}"> 
                                        <msg:userHead userId="${user.pkId}" headType="2"/>
										<span>
											<span title="${user.truename}" class="online_width">${user.truename}</span> 
										</span> 
									</a>
								</p>
								<c:choose>
									<c:when test="${vote.isVote eq '1'}">
										<div class="vote-answer">
											<h5>我的选择：</h5>
                                            <div class="content">
                                            	<c:forEach items="${myAnswer}" var="answer" varStatus="status">
													<p class="vote-color-${status.index % 10}" style="margin-bottom: 0px; margin-top: 0px;">
														${answer}
													</p>
												</c:forEach>
                                            </div>
											<p class="review">
												<q>${vote.comment.commentContent }</q>
											</p>
                                        </div>
									</c:when>
									<c:when test="${vote.isOverTime ne '1' }">
										<ul class="vote-oplist">
											<c:forEach items="${options}" var="option" varStatus="status">
												<li>
													<label for="vote_op_${option.pkId}"> 
														<input value="${option.pkId}" name="${vote.pkId }result[]" id="vote_op_${option.pkId}"
															<c:if test="${vote.type eq 1}">type="radio"</c:if>
															<c:if test="${vote.type eq 2}">type="checkbox"</c:if> 
														>
														
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
											<p class="warning" style="margin-bottom: 0px;color:#FF0000">
												提示:最多可选择${vote.maxOption }个投票项 
											</p>
										</c:if>
										<p style="margin-top: 0px; margin-bottom: 0px;">
											<input type="button" value="确定" class="f-button" 
													onclick="saveVoteResult('${vote.pkId}', '${vote.type}', '${vote.maxOption}')">
										</p>
									</c:when>
								</c:choose>
								<p class="vote-instant-act">
									<a href="${contextPath}/vote/viewVoteResult.do?voteId=${vote.pkId}">»查看投票结果</a>
								</p>
							</div>
						</form>
		
		
					</li>
				</c:forEach>
			</ul>
		</div>
	</div>
	
	<c:url var="paginationAction" value="vote/listVote.do">
	</c:url>
	<%@ include file="/WEB-INF/jsp/common/pagination.jsp"%>
</div>
