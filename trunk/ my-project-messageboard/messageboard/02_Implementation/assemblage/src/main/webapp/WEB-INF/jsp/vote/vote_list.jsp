<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/includes.jsp"%>
<%@ include file="/WEB-INF/jsp/common/common_js.jsp" %>

<msg:css href="css/vote.css"/>
<msg:css href="css/listVote.css"/>

<msg:js src="js/jquery/jquery-1.4.2.min.js"/>

<jsp:include page="/WEB-INF/jsp/base/head.jsp">
	<jsp:param value="所有投票" name="title"/>
</jsp:include>

<div class="content">
	<jsp:include page="vote_head.jsp"/>
	
	<div class="vote-wrapper">
		<div id="vote-instant-wrapper">
			<ul class="vote-list">
				<c:forEach items="${pagination.items}" var="vote">
					<c:set value="${vote.voteOptions}" var="options"/>
					<c:set value="${vote.createUser}" var="user"/>
					<li class="wp">
						<form method="post" id="voteForm${vote.pkId}">
							<div class="vote-instant-singleitem">
								<p class="vote-digg">
									6
								</p>
								<h4>
									<a href="#">${vote.question}</a>
								</h4>
								<p class="image">
									<a href="#"> <img alt="${user.truename}"
											src="${contextPath }/${user.headImage}"> <span>
											<span title="${user.truename}" class="online_width">${user.truename}</span> </span> </a>
								</p>
								<ul class="vote-oplist">
									<c:forEach items="${options}" var="option">
										<li>
											<label for="vote_op_${option.pkId}">
												<input <c:if test="${vote.type eq 1}">type="radio"</c:if><c:if test="${vote.type eq 2}">type="checkbox"</c:if> value="${option.pkId}" name="result[]"
													id="vote_op_${option.pkId}">
												${option.optionContent}
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
									<input type="button" value="确定" class="f-button" onclick="">
								</p>
								<p class="vote-instant-act">
									<a href="#">»查看投票结果</a>
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
