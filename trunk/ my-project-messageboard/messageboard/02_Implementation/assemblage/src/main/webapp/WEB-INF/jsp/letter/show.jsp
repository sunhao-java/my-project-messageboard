<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!--
* 站内信详情页
* Developed By: sunhao
* Mail: sunhao.java@gmail.com
* Time: 12-11-04 下午19:22
* Version:1.0
* History:
-->

<%@ include file="/WEB-INF/jsp/common/includes.jsp"%>
<msg:css href="css/letter.css"/>

<script type="text/javascript">

</script>

<jsp:include page="/WEB-INF/jsp/base/head.jsp">
	<jsp:param value="收件箱" name="title"/>
</jsp:include>

<div class="content01">
	<jsp:include page="letter_head.jsp"/>
	
	<div>
		<div class="letter-content">
			<div class="hd">
	            <h2>
	                <c:out value="${letter.title}"/>
	            </h2>
				收件人：
				<c:forEach items="${lurs}" var="lur">
					${lur.receiver.truename}
				</c:forEach>
            </div>
            <div class="bd">
	            <table>
	            	<tr>
						<td class="ava">
						    <a href="${contextPath}/user/profile.do?uid=${letter.creatorId}">
						    	<msg:head userId="${letter.creatorId}" headType="2"/>
						    </a>
						</td>
	                    <td class="per">
	                        <a href="${contextPath}/user/profile.do?uid=${letter.creatorId}">
	                            <span class="visitor_online">${letter.creator.truename}</span>
	                        </a>
	                        <span class="time">
	                        	<msg:formatDate value="${letter.sendTime}" dateType="1"/>
	                        </span>
	                    </td>
	                    <td class="cnt">
							<c:out value="${letter.content}"/>
	                    </td>
	                    </tr>
	            </table>
        	</div>
		</div>
	</div>
</div>