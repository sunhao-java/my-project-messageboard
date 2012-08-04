<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/includes.jsp"%>
<%@ include file="/WEB-INF/jsp/common/common_js.jsp" %>

<!--
* 好友分组管理 页面
* Developed By: sunhao
* Mail: sunhao.java@gmail.com
* Time: 12-7-24 下午10:56
* Version:1.0
* History:
-->

<msg:js src="js/jquery/jquery-1.4.2.min.js"/>

<script type="text/javascript">
	
</script>

<form action="" id="groupForm" method="post">
	<div class="friend-groups-setting-dialog">
		<h3>
			请选择分组：
		</h3>
		<ul id="friend-group-list">
			<c:forEach items="${groups}" var="g">
				<li>
					<label for="group-${g.pkId}">
						<input type="checkbox" value="${g.pkId}" name="group" id="group-${g.pkId}"
							<c:if test="${msgFun:contains(friendGroups, g.pkId)}">
								checked="checked"
							</c:if>
						/>
						${g.name}
					</label>
				</li>
			</c:forEach>
		</ul>
		<h5>
			<a href="javascript:void(0);" id="add-group-link" onclick="addGroup();">增加分组</a>
		</h5>
		<p>
			好友分组不公开，只有你自己能看到。
		</p>
	</div>
</form>