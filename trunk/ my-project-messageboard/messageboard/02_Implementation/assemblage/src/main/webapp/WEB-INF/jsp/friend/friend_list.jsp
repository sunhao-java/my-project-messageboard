<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/includes.jsp"%>
<%@ include file="/WEB-INF/jsp/common/common_js.jsp" %>

<!--
* 好友列表 页面
* Developed By: sunhao
* Mail: sunhao.java@gmail.com
* Time: 12-7-24 下午10:56
* Version:1.0
* History:
-->

<msg:css href="/css/friend.css"/>
<msg:js src="js/jquery/jquery-1.4.2.min.js"/>
<msg:js src="js/jquery/easyloader.js"/>
<msg:js src="js/base/app-dialog.js"/>

<style type="text/css">
	.il {
	    margin: 0 !important;
	    padding: 10px;
	}
	
	.l-panel-bar{
		margin-top: 0px !important;
	}
	
	.yui-dialog .yui-overlay{
		background: none repeat scroll 0 0 #FFFFFF !important;
	    padding: 0px !important;
	}
</style>

<script type="text/javascript">
	var count = '${paginationSupport.totalRow }';
	$(document).ready(function(){
		using("confirm", function(){
			var length = parseInt('${paginationSupport.totalRow }');
			for(var i = 0; i < length; i++){
				$("#deleteFriend" + i).confirm({
					confirmMessage: '好友',
                    isFormatMessage: true,
                    removeElement: $('#li' + i),
                    customSucTip: function(status){
						$("#count").html(count - 1 + "");
						count--;
						$("#msg_succ").show();
						setTimeout(function() {
	                        $('#msg_succ').hide('slow');
	                    }, 1000);
                    },
                    customErrTip: function(status){
                    	$("#msg-error").show();
                    }
				});
			}
		})
		
		$('#friend-groups-add').click(function(){
			var li = $('<li><input type="text" onblur="save(this);" style="height: 20px;font-size:8pt"></li>');
			$('#friend-groups-list li:first-child').before(li);
			li.find('input').focus();
		});
	});
	
	function save(input){
		var input = $(input);
		if(input.val() == ''){
			input.parent('li').hide();
		} else {
			$.ajax({
				type: 'post',
				url: '${contextPath}/friend/saveGroup.do',
				data: 'groupName=' + input.val(),
				dataType: 'json',
				success: function(o){
					try{
						if(o.status == '1'){
							//成功
							input.replaceWith('<a href="' + o.groupId + '"><em>' + input.val() + '</em> (0)</a>' +
								'<span action="delete" class="delete" rel="${contextPath}/friend/groupFun.do?action=delete&groupId=' +
								+ o.groupId + '"' + 'onclick="func(this, \'\', \'' + o.groupId + '\')">删除</span>' +
	                   			'<span action="edit" class="edit" rel="${contextPath}/friend/groupFun.do?action=edit&groupId=' +
	                   			o.groupId + '"' + 'onclick="func(this, \'' + input.val() + '\', \'' + o.groupId + '\')">编辑</span>');
						} else {
							alert('error');
						}
					} catch (e){
						alert(e);
					}
				}
			});
		}
	}
	
	function func(span, name, pkId){
		var span_ = $(span);
		var action = span_.attr('action');
		var url = span_.attr('rel');
		if(action == 'edit'){
			//编辑
			span_.parent('li').html('<input type="text" onblur="edit(this, \'' + url + '\', \'' + pkId + '\');" ' +
				'style="height: 20px;font-size:8pt" value="' + name + '">');
		} else {
			//删除
			edit(span, url, pkId);
		}
	}
	
	function edit(input, url, pkId){
		var groupName = '';
		var action = 'delete';
		var tagName = input.tagName.toLowerCase();
		if(tagName == 'input'){
			groupName = $(input).val();
			action = 'edit';
		}
		
		$.ajax({
			url: url,
			type: 'POST',
			dataType: 'json',
			data: 'groupName=' + groupName,
			success: function(o){
				try{
					if(o.status == '1'){
						//成功
						if(action == 'edit'){
							//编辑时 
							$(input).replaceWith('<a href="' + pkId + '"><em>' + groupName + '</em></a>' +
								'<span action="delete" class="delete" rel="${contextPath}/friend/groupFun.do?action=delete&groupId=' + pkId + '"' + 
	                   			'onclick="func(this, \'\', \'' + pkId + '\')">删除</span>' +
	                   			'<span action="edit" class="edit" rel="${contextPath}/friend/groupFun.do?action=edit&groupId=' + pkId + '"' +
	                   				'onclick="func(this, \'' + groupName + '\', \'' + pkId + '\')">编辑</span>');
						} else {
							//删除
							$(input).parent('li').fadeOut('slow');
						}
					} else {
						alert('error');
					}
				} catch (e){
					alert(e);
				}
			},
			error: function(o){
				
			}
		});
	}
	
	function groupEdit(friendId){
		YAHOO.app.dialog.pop({
		   'dialogHead': '编辑好友分组',
		   'url': '${contextPath}/friend/listGroup.do?fid=' + friendId,
		   'diaWidth': 500,
		   'diaHeight': 'auto',
		   'icon': '',
		   'formId': 'groupForm',
		   'action': '${contextPath}/friend/saveFriendGroup.do?fid=' + friendId,
           'afterRequest': function(o){
			   if(o.status == '1'){
				   window.location.reload(true);
			   } else {
				   YAHOO.app.dialog.pop({'dialogHead':'提示','cancelButton':'false','alertMsg':'设置分组失败！'});
			   }
           }                                 
		});
	}
	
	function addGroup(){
		var flag = new Date().getTime();
		var li = $('<li id="li' + flag + '">' + 
					'<input type="input" maxlength="20" size="20" class="f-text" onblur="save1(this);" id="' + flag + '" name="addGroupName">' +
				 '</li>');
		$('#friend-group-list li:last-child').after(li);
		li.find('input').focus();
	}
	
	function save1(input){
		input = $(input);
		var group = input.val();
		if(group == ''){
			$('#li' + input.attr('id')).hide();
		} else {
			$.ajax({
				type: 'post',
				url: '${contextPath}/friend/saveGroup.do',
				data: 'groupName=' + input.val(),
				dataType: 'json',
				success: function(o){
					try{
						if(o.status == '1'){
							var id = o.groupId;
							//成功
							input.parent('li').replaceWith('<li><label for="group-' + id + '">' +
								'<input type="checkbox" value="' + id + '" name="group" id="group-' + id + '"/>' +
								input.val() + '</label></li>');
						} else {
							alert('error');
						}
					} catch (e){
						alert(e);
					}
				}
			});
		}
	}
</script>

<jsp:include page="/WEB-INF/jsp/base/head.jsp">
	<jsp:param value="联系人管理" name="title"/>
</jsp:include>

<div class="content01">
	<jsp:include page="friend_head.jsp"/>
	
	<div style="display:none" class="msg-succ" id="msg_succ">
	 	删除好友成功！
	</div>
	
	<div id="msg-error" class="msg-error" style="display:none">
		删除好友失败！
	</div>
	
	<div id="wrap">
		<ol class="il" style="width: 800px;float: left;">
			<h3>
				共有 <span id="count">${paginationSupport.totalRow }</span> 个好友
			</h3>
			<c:forEach items="${paginationSupport.items}" var="friend" varStatus="status">
				<li id="li${status.index}">
					<div class="people">
						<p class="image">
							<msg:head userId="${friend.friendId}"/>
						</p>
						<table class="info">
							<caption>
								<a href="${contextPath}/user/userInfo.do?viewUserId=${friend.friendId}">
									<span title="${friend.friendUser.truename}" class="online_width">${friend.friendUser.truename }</span>
								</a>
								<c:if test="${friend.friendUser.sex eq 1}">
									<img src="${contextPath}/image/male.png" alt="男" title="男" style="vertical-align: top"/>
								</c:if>
								<c:if test="${friend.friendUser.sex eq 2}">
									<img src="${contextPath}/image/female.png" alt="女" title="女" style="vertical-align: top"/>
								</c:if>
							</caption>
							<tr class="phone-num">
								<c:choose>
									<c:when test="${not empty friend.groups && fn:length(friend.groups) > 0}">
										<th>分组：</th>
										<td>
											<c:forEach items="${friend.groups}" var="g" varStatus="status">
												${g.name} <c:if test="${status.index + 1 ne fn:length(friend.groups)}">，</c:if>
											</c:forEach>
											<br/>
											<span class="add-group">
												<a href="javascript:;" onclick="groupEdit('${friend.pkId}');" class="link">
													<img class="addicon" src="${contextPath }/image/transparent.gif">
													修改分组
												</a>
											</span>
										</td>
									</c:when>
									<c:otherwise>
										<td colspan="2">
											<span class="add-group">
												<a href="javascript:;" onclick="groupEdit('${friend.pkId}');" class="link">
													<img class="addicon" src="${contextPath }/image/transparent.gif">
													添加分组
												</a>
											</span>
										</td>
									</c:otherwise>
								</c:choose>
							</tr>
							<c:if test="${not empty friend.friendUser.phoneNum }">
								<tr class="phone-num">
									<th>手机：</th>
									<td>${friend.friendUser.phoneNum }</td>
								</tr>
							</c:if>
							<c:if test="${not empty friend.friendUser.email }">
								<tr class="phone-num">
									<th>邮箱：</th>
									<td>${friend.friendUser.email }</td>
								</tr>
							</c:if>
							<c:if test="${not empty friend.friendUser.qq }">
								<tr class="phone-num">
									<th>QQ：</th>
									<td>${friend.friendUser.qq }</td>
								</tr>
							</c:if>
							<tr>
								<td></td>
							</tr>
						</table>
						<ul class="actions">
							<li>
								<a class="agreeRequest" href="${contextPath}/user/userInfo.do?viewUserId=${friend.friendId}">查看好友</a>
							</li>
                            <li>
								<a class="denylRequest" id="deleteFriend${status.index}" href="javascript:void(0);"
									rel="${contextPath }/friend/deleteFriend.do?fid=${friend.friendId}">删除好友</a>
							</li>
						</ul>
					</div>
				</li>
			</c:forEach>
		</ol>
		
		<div class="friend-group">
			<div id="friends-filter" class="h-filter">
                <p class="current">
                    <a href="${contextPath}/friend.do">所有好友</a>
                </p>
                <h4>
                	分组
                </h4>
                <ul class="friend-groups-list" id="friend-groups-list">
                    <c:forEach items="${groups}" var="group">
                    	<li>
                    		<a href="${contextPath}/friend.do?groupId=${group.pkId}">
                    			<em>${group.name}</em> (${group.userNum})
                    		</a>
                   			<span action="delete" class="delete" rel="${contextPath}/friend/groupFun.do?action=delete&groupId=${group.pkId}" 
                   				onclick="func(this, '', '${group.pkId}')">删除</span>
                   			<span action="edit" class="edit" rel="${contextPath}/friend/groupFun.do?action=edit&groupId=${group.pkId}" 
                   				onclick="func(this, '${group.name}', '${group.pkId}')">编辑</span>
                    	</li>
                    </c:forEach>
                    <li>
                    	<a href="${contextPath}/friend.do?groupId=0">
                        	<em>未分组</em> (${noGroupFriendNum})
                        </a>
                    </li>
                </ul>
                <h5 class="add-group">
                    <a id="friend-groups-add" href="javascript:void(0);">增加分组</a>
                </h5>
            </div>
		</div>
	</div>

	<c:url var="paginationAction" value="friend.do">
	</c:url>
	<%@ include file="/WEB-INF/jsp/common/pagination.jsp"%>
</div>