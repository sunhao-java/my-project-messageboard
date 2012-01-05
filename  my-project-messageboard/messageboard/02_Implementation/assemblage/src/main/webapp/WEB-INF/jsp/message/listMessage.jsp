<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/includes.jsp"%>

<msg:css href="css/style.css"/>

<jsp:include page="/WEB-INF/jsp/base/head.jsp">
	<jsp:param value="查看所有留言" name="title"/>
</jsp:include>

<c:forEach items="${messages }" var="message">
	<div class="msgArea">
		<div class="msgArea-left">
			<ul >
				<li class="userFace">
					<img class="face-normal" src="${contextPath }/${message.createUser.headImage}" title="${message.createUser.username }"/>
				</li>
				<li class="userName">${message.createUser.username }</li>
			</ul>
		</div>
		<div class="msgArea-right">
			<div class="msgTitle">
				标题：<h3>${message.title }</h3>
				<span style="float:right">位置：${message.ip }</span>
			</div>
			<div class="msgTime" >
				<fmt:formatDate value="${message.createDate }" pattern="yyyy-MM-dd HH:mm"/>
			</div>
			<div class="userContact">
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
			</div>
			<div class="msgContent">
				<table>
					<tr>
						<td style="text-indent: 25px;">
							${message.content }
						</td>
					</tr>
				</table>
			</div>
		</div>
		<div class="msgArea-clear"></div>
	</div>
</c:forEach>
