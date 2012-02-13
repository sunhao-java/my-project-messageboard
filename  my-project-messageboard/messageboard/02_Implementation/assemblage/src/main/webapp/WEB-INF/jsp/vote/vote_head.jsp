<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/includes.jsp"%>
<style type="text/css">
	a:link, a:active, a:visited {
		text-decoration: none;
	}
	
	a:hover {
		text-decoration: underline;
	}
	
	.top li.current a {
	    background: none repeat scroll 0 0 #FFFFFF;
	    border-top: 1px solid #EEEEEE;
	    color: #333333 !important;
	    padding: 2px 15px;
	    float: left;
	    line-height: 120%;
	}
	
	.top li a {
	    background: none repeat scroll 0 0 #999999;
	    border-top: 1px solid #B5B5B5;
	    color: #FFFFFF !important;
	    float: left;
	    line-height: 120%;
	    padding: 2px 15px;
	}
</style>
<div class="top">
	<ul>
		<li <c:if test="${current eq 'create' }">class="current"</c:if><c:if test="${current ne 'create' }">class="alt"</c:if>>
			<a href="${contextPath}/vote/createVote.do">发起新投票</a>
		</li>
		<li <c:if test="${current eq 'list' }">class="current"</c:if><c:if test="${current ne 'list' }">class="alt"</c:if>>
			<a href="${contextPath}/vote/listVote.do">返回投票列表</a>
		</li>
	</ul>
</div>