<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/includes.jsp"%>
<%@ include file="/WEB-INF/jsp/common/common_js.jsp" %>

<msg:css href="css/publish.css"/>
<msg:js src="js/jquery/jquery-1.4.2.min.js"/>
<msg:js src="js/jquery/jquery.easyui.min.js"/>

<msg:css href="themes/default/easyui.css"/>
<msg:css href="themes/icon.css"/>

<msg:css href="css/colortip-1.0-jquery.css"/>
<msg:js src="js/jquery/colortip-1.0-jquery.js"/>

<msg:js src="js/base/commfunction.js"/>

<script type="text/javascript">
	$(document).ready(function(){
		//调用公共JS自动提示组件
		showUser('yellow','300px','wrap');
	});
	
	function edit(){
		window.location.href = '${contextPath}/user/editUserInfo.do';
	}
</script>

<c:choose>
	<c:when test="${empty customer}">
		<jsp:include page="/WEB-INF/jsp/base/head.jsp">
			<jsp:param value="我的信息" name="title"/>
		</jsp:include>
	</c:when>
	<c:otherwise>
		<jsp:include page="/WEB-INF/jsp/base/head.jsp">
			<jsp:param value="${viewwhoname}的信息" name="title"/>
		</jsp:include>
	</c:otherwise>
</c:choose>

<div id="listFrm">
	<form id="dataFrm" action="" method="post">
		<table width="100%" border="1" class="tableform">
			<tr>
				<td class="fb_result_head" width="15%">
					登录名
				</td>
				<td width="40%">
					<c:out value="${user.username}"/>              
				</td>
				<td class="fb_result_head" width="15%">
					真实姓名
				</td>
				<td width="40%">
					<c:choose>
						<c:when test="${empty customer}">
							<c:out value="${user.truename}"/>
						</c:when>
						<c:otherwise>
							<c:choose>
								<c:when test="${privacy.truename eq 1}">
									<span style="color: red">*用户设置仅自己可见</span>
								</c:when>
								<c:when test="${privacy.truename eq 2}">
									<span style="color: red">*用户设置仅好友可见</span>
								</c:when>
								<c:otherwise>
									<c:out value="${user.truename}"/>
								</c:otherwise>
							</c:choose>
						</c:otherwise>
					</c:choose>  
				</td>
			</tr>
			<tr>
				<td class="fb_result_head" width="15%">
					性别
				</td>
				<td width="30%">
					<c:choose>
						<c:when test="${empty customer}">
							<c:if test="${user.sex == 0}">
								<c:out value="不男不女"/>
							</c:if>
							<c:if test="${user.sex == 1}">
								<c:out value="男"/>
							</c:if>
							<c:if test="${user.sex == 2}">
								<c:out value="女"/>
							</c:if>
						</c:when>
						<c:otherwise>
							<c:choose>
								<c:when test="${privacy.sex eq 1}">
									<span style="color: red">*用户设置仅自己可见</span>
								</c:when>
								<c:when test="${privacy.sex eq 2}">
									<span style="color: red">*用户设置仅好友可见</span>
								</c:when>
								<c:otherwise>
									<c:if test="${user.sex == 0}">
										<c:out value="不男不女"/>
									</c:if>
									<c:if test="${user.sex == 1}">
										<c:out value="男"/>
									</c:if>
									<c:if test="${user.sex == 2}">
										<c:out value="女"/>
									</c:if>
								</c:otherwise>
							</c:choose>
						</c:otherwise>
					</c:choose>  
				</td>
				<td class="fb_result_head" width="15%">
					电话号码
				</td>
				<td>
					<c:choose>
						<c:when test="${empty customer}">
							<c:out value="${user.phoneNum}"/>
						</c:when>
						<c:otherwise>
							<c:choose>
								<c:when test="${privacy.phonenum eq 1}">
									<span style="color: red">*用户设置仅自己可见</span>
								</c:when>
								<c:when test="${privacy.phonenum eq 2}">
									<span style="color: red">*用户设置仅好友可见</span>
								</c:when>
								<c:otherwise>
									<c:out value="${user.phoneNum}"/>
								</c:otherwise>
							</c:choose>
						</c:otherwise>
					</c:choose>  
				</td>
			</tr>
			<tr>
				<td class="fb_result_head" width="15%">
					邮箱
				</td>
				<td>
					<c:choose>
						<c:when test="${empty customer}">
							<c:out value="${user.email}"/>
						</c:when>
						<c:otherwise>
							<c:choose>
								<c:when test="${privacy.email eq 1}">
									<span style="color: red">*用户设置仅自己可见</span>
								</c:when>
								<c:when test="${privacy.email eq 2}">
									<span style="color: red">*用户设置仅好友可见</span>
								</c:when>
								<c:otherwise>
									<c:out value="${user.email}"/>
								</c:otherwise>
							</c:choose>
						</c:otherwise>
					</c:choose>  
				</td>
				<td class="fb_result_head" width="15%">
					QQ
				</td>
				<td>
					<c:choose>
						<c:when test="${empty customer}">
							<c:out value="${user.qq}"/>
						</c:when>
						<c:otherwise>
							<c:choose>
								<c:when test="${privacy.qq eq 1}">
									<span style="color: red">*用户设置仅自己可见</span>
								</c:when>
								<c:when test="${privacy.qq eq 2}">
									<span style="color: red">*用户设置仅好友可见</span>
								</c:when>
								<c:otherwise>
									<c:out value="${user.qq}"/>
								</c:otherwise>
							</c:choose>
						</c:otherwise>
					</c:choose>  
				</td>
			</tr>
			<tr>
				<td class="fb_result_head" width="15%">
					主页
				</td>
				<td colspan="3">
					<c:choose>
						<c:when test="${empty customer}">
							<a href="${user.homePage}" target="_blank">
								<msg:cutWord length="50" endString="..." cutString="${user.homePage}"/>
							</a>
						</c:when>
						<c:otherwise>
							<c:choose>
								<c:when test="${privacy.homepage eq 1}">
									<span style="color: red">*用户设置仅自己可见</span>
								</c:when>
								<c:when test="${privacy.homepage eq 2}">
									<span style="color: red">*用户设置仅好友可见</span>
								</c:when>
								<c:otherwise>
									<a href="${user.homePage}" target="_blank">
										<msg:cutWord length="50" endString="..." cutString="${user.homePage}"/>
									</a>
								</c:otherwise>
							</c:choose>
						</c:otherwise>
					</c:choose>  
				</td>
			</tr>
			<tr>
				<td class="fb_result_head" width="15%">
					地址
				</td>
				<td colspan="3">
					<c:choose>
						<c:when test="${empty customer}">
							<c:out value="${user.address }"/>
						</c:when>
						<c:otherwise>
							<c:choose>
								<c:when test="${privacy.address eq 1}">
									<span style="color: red">*用户设置仅自己可见</span>
								</c:when>
								<c:when test="${privacy.address eq 2}">
									<span style="color: red">*用户设置仅好友可见</span>
								</c:when>
								<c:otherwise>
									<c:out value="${user.address }"/>
								</c:otherwise>
							</c:choose>
						</c:otherwise>
					</c:choose>  
				</td>
			</tr>
			<tr>
				<td class="fb_result_head" width="15%">
					上次登录时间
				</td>
				<td>
                    <msg:formatDate value="${user.lastLoginTime}"/>
				</td>
				<td class="fb_result_head" width="15%">
					登录次数
				</td>
				<td>
					<c:out value="${user.loginCount}"/>
				</td>
			</tr>
			<tr>
				<td class="fb_result_head" width="15%">
					留言数目
				</td>
				<td>
					${user.messageCount}
				</td>
				<td class="fb_result_head" width="15%">
					积分
				</td>
				<td>
					<c:out value="${user.messageCount*2 + user.loginCount*1 }分"/>	
				</td>
			</tr>
		</table>
	</form>
	<div class="formFunctiondiv">
		<c:choose>
			<c:when test="${empty customer}">
				<jsp:include page="/WEB-INF/jsp/common/linkbutton.jsp">
					<jsp:param value="修改我的信息" name="edit"/>
				</jsp:include>
			</c:when>
			<c:otherwise>
				<jsp:include page="/WEB-INF/jsp/common/linkbutton.jsp">
					<jsp:param value="返回" name="back"/>
				</jsp:include>
			</c:otherwise>
		</c:choose>
	</div>
	<c:if test="${empty customer}">
		
	</c:if>
</div>	