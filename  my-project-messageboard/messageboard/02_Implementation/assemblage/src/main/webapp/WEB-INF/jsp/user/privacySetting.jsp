<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/includes.jsp"%>
<%@ include file="/WEB-INF/jsp/common/common_js.jsp" %>

<msg:css href="css/publish.css"/>
<msg:js src="js/jquery/jquery-1.4.2.min.js"/>
<msg:js src="js/jquery/jquery.easyui.min.js"/>
<msg:css href="themes/default/easyui.css"/>
<msg:css href="themes/icon.css"/>
<msg:js src="js/mouse-over-out.js"/>
<msg:js src="js/base/app-dialog.js"/>

<script type="text/javascript">
	var $C = YAHOO.util.Connect,dom = YAHOO.util.Dom,event = YAHOO.util.Event;
	
	var nameArr = ['sex', 'truename', 'email', 'phonenum', 'qq', 'homepage', 'address'];
	
	$(document).ready(function(){
	    //隔行换色
	    $('table').each(function(){
			$(this).find('tr:even').css("background","#f7f6f6");
		});
	});
	
	function showSelect(flag){
		for(var i = 0; i < nameArr.length; i++){
			if(nameArr[i] != flag){
				dom.get(nameArr[i] + 'Select').style.display = 'none';
				var select = $('#' + nameArr[i] + 'Select option:selected').text();
				$('#' + nameArr[i] + 'Span').text(select);
				dom.get(nameArr[i] + 'Span').style.display = '';
			} else {
				var select = $('#' + nameArr[i] + 'Select option:selected').text();
				$('#' + nameArr[i] + 'Span').text(select);
				dom.get(flag + 'Select').style.display = '';
				dom.get(flag + 'Span').style.display = 'none';
			}
		}
	}
	
	function save(){
		var requestURL = '${contextPath}/privacy/savePrivacy.do';
		var dataFrm = dom.get('dataFrm');
		$C.setForm(dataFrm);
		$C.asyncRequest('POST',requestURL,{
			success : function(o){
				var _e = eval("(" + o.responseText + ")");
				if(_e.status == '1'){
					YAHOO.app.dialog.pop({'dialogHead':'提示','cancelButton':'false','alertMsg':'隐私设置成功！',
						'confirmFunction':function(){
							window.location.reload(true);
						}});
				} else {
					YAHOO.app.dialog.pop({'dialogHead':'提示','cancelButton':'false','alertMsg':'隐私设置失败！'});
				}
			},
			failure : function(o){
				YAHOO.app.dialog.pop({'dialogHead':'提示','cancelButton':'false','alertMsg':'错误代码:' + o.status});
			}
		});
	}
</script>

<style type="text/css">
	.content01{
		min-height: 400px;
	}
</style>

<jsp:include page="/WEB-INF/jsp/base/head.jsp">
	<jsp:param value="个人隐私设置" name="title"/>
</jsp:include>

<div class="content01">
	<jsp:include page="user_edit_head.jsp"/>
	
	<div id="listFrm" style="width: 30%" onmouseover="mouseOverOrOut();">
		<form id="dataFrm" action="" method="post">
			<input type="hidden" name="pkId" value="${user.pkId }"/>
			<table width="100%" border="0" class="tableform" id="tbl">
				<tr>
					<td class="fb_result_head" width="30%">
						用户名
					</td>
					<td>
						<span style="color: red">*用户名必须所有人可见</span>           
					</td>
				</tr>
				<tr>
					<td class="fb_result_head" width="30%">
						性别
					</td>
					<td>
						<select style="display: none" id="sexSelect" name="sex">
							<option value="0" <c:if test="${userPrivacy.sex eq 0}">selected="selected"</c:if>>所有人可见</option>
							<option value="1" <c:if test="${userPrivacy.sex eq 1}">selected="selected"</c:if>>仅自己可见</option>
							<option value="2" <c:if test="${userPrivacy.sex eq 2}">selected="selected"</c:if>>仅好友可见</option>
						</select>  
						<span onclick="showSelect('sex');" id="sexSpan">
							<c:choose>
								<c:when test="${userPrivacy.sex eq 1}">
									仅自己可见
								</c:when>
								<c:when test="${userPrivacy.sex eq 2}">
									仅好友可见
								</c:when>
								<c:otherwise>
									所有人可见
								</c:otherwise>
							</c:choose>
						</span>   
					</td>
				</tr>
				<tr>
					<td class="fb_result_head" width="30%">
						真实姓名
					</td>
					<td>
						<select style="display: none" id="truenameSelect" name="truename">
							<option value="0" <c:if test="${userPrivacy.truename eq 0}">selected="selected"</c:if>>所有人可见</option>
							<option value="1" <c:if test="${userPrivacy.truename eq 1}">selected="selected"</c:if>>仅自己可见</option>
							<option value="2" <c:if test="${userPrivacy.truename eq 2}">selected="selected"</c:if>>仅好友可见</option>
						</select>  
						<span onclick="showSelect('truename');" id="truenameSpan">
							<c:choose>
								<c:when test="${userPrivacy.truename eq 1}">
									仅自己可见
								</c:when>
								<c:when test="${userPrivacy.truename eq 2}">
									仅好友可见
								</c:when>
								<c:otherwise>
									所有人可见
								</c:otherwise>
							</c:choose>
						</span>         
					</td>
				</tr>
				<tr>
					<td class="fb_result_head" width="30%">
						邮箱
					</td>
					<td>
						<select style="display: none" id="emailSelect" name="email">
							<option value="0" <c:if test="${userPrivacy.email eq 0}">selected="selected"</c:if>>所有人可见</option>
							<option value="1" <c:if test="${userPrivacy.email eq 1}">selected="selected"</c:if>>仅自己可见</option>
							<option value="2" <c:if test="${userPrivacy.email eq 2}">selected="selected"</c:if>>仅好友可见</option>
						</select>  
						<span onclick="showSelect('email');" id="emailSpan">
							<c:choose>
								<c:when test="${userPrivacy.email eq 1}">
									仅自己可见
								</c:when>
								<c:when test="${userPrivacy.email eq 2}">
									仅好友可见
								</c:when>
								<c:otherwise>
									所有人可见
								</c:otherwise>
							</c:choose>
						</span>    
					</td>
				</tr>
				<tr>
					<td class="fb_result_head" width="30%">
						电话号码
					</td>
					<td>
						<select style="display: none" id="phonenumSelect" name="phonenum">
							<option value="0" <c:if test="${userPrivacy.phonenum eq 0}">selected="selected"</c:if>>所有人可见</option>
							<option value="1" <c:if test="${userPrivacy.phonenum eq 1}">selected="selected"</c:if>>仅自己可见</option>
							<option value="2" <c:if test="${userPrivacy.phonenum eq 2}">selected="selected"</c:if>>仅好友可见</option>
						</select>  
						<span onclick="showSelect('phonenum');" id="phonenumSpan">
							<c:choose>
								<c:when test="${userPrivacy.phonenum eq 1}">
									仅自己可见
								</c:when>
								<c:when test="${userPrivacy.phonenum eq 2}">
									仅好友可见
								</c:when>
								<c:otherwise>
									所有人可见
								</c:otherwise>
							</c:choose>
						</span>   
					</td>
				</tr>
				<tr>
					<td class="fb_result_head" width="30%">
						QQ
					</td>
					<td>
						<select style="display: none" id="qqSelect" name="qq">
							<option value="0" <c:if test="${userPrivacy.qq eq 0}">selected="selected"</c:if>>所有人可见</option>
							<option value="1" <c:if test="${userPrivacy.qq eq 1}">selected="selected"</c:if>>仅自己可见</option>
							<option value="2" <c:if test="${userPrivacy.qq eq 2}">selected="selected"</c:if>>仅好友可见</option>
						</select>  
						<span onclick="showSelect('qq');" id="qqSpan">
							<c:choose>
								<c:when test="${userPrivacy.qq eq 1}">
									仅自己可见
								</c:when>
								<c:when test="${userPrivacy.qq eq 2}">
									仅好友可见
								</c:when>
								<c:otherwise>
									所有人可见
								</c:otherwise>
							</c:choose>
						</span>     
					</td>
				</tr>
				<tr>
					<td class="fb_result_head" width="30%">
						主页
					</td>
					<td>
						<select style="display: none" id="homepageSelect" name="homepage">
							<option value="0" <c:if test="${userPrivacy.homepage eq 0}">selected="selected"</c:if>>所有人可见</option>
							<option value="1" <c:if test="${userPrivacy.homepage eq 1}">selected="selected"</c:if>>仅自己可见</option>
							<option value="2" <c:if test="${userPrivacy.homepage eq 2}">selected="selected"</c:if>>仅好友可见</option>
						</select>  
						<span onclick="showSelect('homepage');" id="homepageSpan">
							<c:choose>
								<c:when test="${userPrivacy.homepage eq 1}">
									仅自己可见
								</c:when>
								<c:when test="${userPrivacy.homepage eq 2}">
									仅好友可见
								</c:when>
								<c:otherwise>
									所有人可见
								</c:otherwise>
							</c:choose>
						</span>    
					</td>
				</tr>
				<tr>
					<td class="fb_result_head" width="30%">
						地址
					</td>
					<td>
						<select style="display: none" id="addressSelect" name="address">
							<option value="0" <c:if test="${userPrivacy.address eq 0}">selected="selected"</c:if>>所有人可见</option>
							<option value="1" <c:if test="${userPrivacy.address eq 1}">selected="selected"</c:if>>仅自己可见</option>
							<option value="2" <c:if test="${userPrivacy.address eq 2}">selected="selected"</c:if>>仅好友可见</option>
						</select>  
						<span onclick="showSelect('address');" id="addressSpan">
							<c:choose>
								<c:when test="${userPrivacy.address eq 1}">
									仅自己可见
								</c:when>
								<c:when test="${userPrivacy.address eq 2}">
									仅好友可见
								</c:when>
								<c:otherwise>
									所有人可见
								</c:otherwise>
							</c:choose>
						</span>    
					</td>
				</tr>
			</table>
		</form>
	</div>	
	
	<div class="formFunctiondiv" style="position: relative;top: 80px;">
		<jsp:include page="/WEB-INF/jsp/common/linkbutton.jsp">
			<jsp:param value="保存" name="save"/>
			<jsp:param value="返回" name="back"/>
		</jsp:include>
	</div>
</div>