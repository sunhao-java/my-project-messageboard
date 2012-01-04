<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/includes.jsp"%>
<%@ include file="/WEB-INF/jsp/common/common_js.jsp" %>
<%
	request.setAttribute("contextPath", request.getContextPath());
%>

<msg:css href="css/publish.css"/>
<msg:js src="js/jquery/jquery-1.4.2.min.js"/>
<msg:js src="js/jquery/jquery.easyui.min.js"/>

<msg:css href="themes/default/easyui.css"/>
<msg:css href="themes/icon.css"/>

<msg:css href="css/colortip-1.0-jquery.css"/>
<msg:js src="js/jquery/colortip-1.0-jquery.js"/>

<script type="text/javascript">
	$(document).ready(function(){
		//调用公共JS自动提示组件
		showUser('yellow','300px','wrap');
	});
</script>

<jsp:include page="/WEB-INF/jsp/base/head.jsp">
	<jsp:param value="我的信息" name="title"/>
</jsp:include>

<div id="listDiv">
	<form id="dataFrm" action="" method="post">
		<table width="100%" border="1" class="tableform">
			<tr>
				<td class="fb_result_head" width="15%">
					姓名
				</td>
				<td width="40%">
					<c:out value="${user.username}"/>              
				</td>
				<td class="fb_result_head" width="15%">
					性别
				</td>
				<td width="30%">
					<c:if test="${user.sex == 0}">
						<c:out value="不男不女"/>
					</c:if>
					<c:if test="${user.sex == 1}">
						<c:out value="男"/>
					</c:if>
					<c:if test="${user.sex == 2}">
						<c:out value="女"/>
					</c:if>
				</td>
			</tr>
			<tr>
				<td class="fb_result_head" width="15%">
					邮箱
				</td>
				<td>
					<c:out value="${user.email}"/>
				</td>
				<td class="fb_result_head" width="15%">
					电话号码
				</td>
				<td>
					<c:out value="${user.phoneNum}"/>
				</td>
			</tr>
			<tr>
				<td class="fb_result_head" width="15%">
					QQ
				</td>
				<td>
					<c:out value="${user.qq}"/>
				</td>
				<td class="fb_result_head" width="15%">
					主页
				</td>
				<td>
					<a href="${user.homePage}" target="_blank">
						<msg:cutWord length="30" endString="..." cutString="${user.homePage}"/>
					</a>
				</td>
			</tr>
			<tr>
				<td class="fb_result_head" width="15%">
					上次登录时间
				</td>
				<td>
					<%=new Date() %>
				</td>
				<td class="fb_result_head" width="15%">
					登录次数
				</td>
				<td>
					55555
				</td>
			</tr>
			<tr>
				<td class="fb_result_head" width="15%">
					留言数目
				</td>
				<td>
					555
				</td>
				<td class="fb_result_head" width="15%">
					积分
				</td>
				<td>
					55555
				</td>
			</tr>
		</table>
	</form>
	<div class="formFunctiondiv">
		<jsp:include page="/WEB-INF/jsp/common/linkbutton.jsp">
			<jsp:param value="修改我的信息" name="edit"/>
		</jsp:include>
	</div>
</div>	