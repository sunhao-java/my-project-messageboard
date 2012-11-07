<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/includes.jsp"%>

<!--
* 站内信发件箱
* Developed By: sunhao
* Mail: sunhao.java@gmail.com
* Time: 12-11-07 下午10:58
* Version:1.0
* History:
-->

<msg:css href="css/letter.css"/>

<script type="text/javascript">
</script>

<jsp:include page="/WEB-INF/jsp/base/head.jsp">
	<jsp:param value="收件箱" name="title"/>
</jsp:include>

<div class="content01">
	<jsp:include page="letter_head.jsp"/>
	
</div>