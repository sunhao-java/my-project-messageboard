<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://sunhao.wiscom.com.cn/message" prefix="msg" %>
<%
	request.setAttribute("contextPath", request.getContextPath());
%>

<msg:js src="js/jquery/jquery-1.4.2.min.js"/>
<msg:js src="js/jquery/jquery.easyui.min.js"/>

<msg:css href="themes/default/easyui.css"/>
<msg:css href="themes/icon.css"/>
<msg:css href="css/renren.css"/>
<msg:css href="css/listMsg.css"/>

<div style="Xposition: relative; zoom: 1; ">
	<c:forEach items="${messages}" var="message">
		<table border="1" class="tableform" width="70%">
			<tr>
				<td class="fb_result_head" width="10%">
					发表者
			    </td>
			    <td width="10%">
			    	${message.createUser.username }
			    </td>
			    <td class="fb_result_head" width="15%">
			    	发表于
			    </td>
			    <td width="25%">
			    	<fmt:formatDate value="${message.crateDate }" pattern="yyyy-MM-dd HH:mm"/>
			    </td>
			    <td class="fb_result_head" width="10%">
			    	QQ
			    </td>
			    <td width="15%">
			    	<c:if test="${empty message.createUser.qq}">
			    		没填
			    	</c:if>
			    	<c:if test="${not empty message.createUser.qq}">
			    		${message.createUser.qq }
			    	</c:if>
			    </td>
			    <td class="fb_result_head" width="10%">
					性别
				</td>	
				<td width="5%">
			    	<c:if test="${message.createUser.sex eq 0}">
			    		不男不女
			    	</c:if>
			    	<c:if test="${message.createUser.sex eq 1}">
			    		男
			    	</c:if>
			    	<c:if test="${message.createUser.sex eq 2}">
			    		女
			    	</c:if>
			    </td>
			</tr>
			<tr>
				<td rowspan="3" colspan="2">
					<img src="${contextPath }/${message.createUser.headImage}"/>
				</td>
				<td class="fb_result_head">主 题</td>
				<td colspan="5">
					${message.title }
				</td>
			</tr>
		</table>
	</c:forEach>
</div>