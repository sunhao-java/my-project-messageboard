<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/includes.jsp"%>
<%@ include file="/WEB-INF/jsp/common/common_js.jsp" %>

<msg:css href="css/publish.css"/>
<msg:js src="js/jquery/jquery-1.4.2.min.js"/>
<msg:js src="js/jquery/jquery.easyui.min.js"/>
<msg:css href="themes/default/easyui.css"/>
<msg:css href="themes/icon.css"/>
<msg:js src="js/mouse-over-out.js"/>

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
				dom.get(nameArr[i] + 'Span').style.display = '';
			} else {
				dom.get(flag + 'Select').style.display = '';
				dom.get(flag + 'Span').style.display = 'none';
			}
		}
	}
</script>

<jsp:include page="/WEB-INF/jsp/base/head.jsp">
	<jsp:param value="个人隐私设置" name="title"/>
</jsp:include>

<div id="listFrm" style="width: 30%" onmouseover="mouseOverOrOut();">
	<form id="dataFrm" action="" method="post">
		<input type="hidden" name="pkId" value="${user.pkId }"/>
		<table width="100%" border="0" class="tableform" id="tbl">
			<tr>
				<td class="fb_result_head" width="30%">
					用户名
				</td>
				<td>
					<span style="color: red">*用户名必须可见</span>           
				</td>
			</tr>
			<tr>
				<td class="fb_result_head" width="30%">
					性别
				</td>
				<td>
					<select style="display: none" id="sexSelect" name="sex">
						<option value="0">所有人可见</option>
						<option value="1">仅自己可见</option>
					</select>  
					<span onclick="showSelect('sex');" id="sexSpan">所有人可见</span>   
				</td>
			</tr>
			<tr>
				<td class="fb_result_head" width="30%">
					真实姓名
				</td>
				<td>
					<select style="display: none" id="truenameSelect" name="truename">
						<option value="0">所有人可见</option>
						<option value="1">仅自己可见</option>
					</select>  
					<span onclick="showSelect('truename');" id="truenameSpan">所有人可见</span>         
				</td>
			</tr>
			<tr>
				<td class="fb_result_head" width="30%">
					邮箱
				</td>
				<td>
					<select style="display: none" id="emailSelect" name="email">
						<option value="0">所有人可见</option>
						<option value="1">仅自己可见</option>
					</select>  
					<span onclick="showSelect('email');" id="emailSpan">所有人可见</span>    
				</td>
			</tr>
			<tr>
				<td class="fb_result_head" width="30%">
					电话号码
				</td>
				<td>
					<select style="display: none" id="phonenumSelect" name="phoneNum">
						<option value="0">所有人可见</option>
						<option value="1">仅自己可见</option>
					</select>  
					<span onclick="showSelect('phonenum');" id="phonenumSpan">所有人可见</span>   
				</td>
			</tr>
			<tr>
				<td class="fb_result_head" width="30%">
					QQ
				</td>
				<td>
					<select style="display: none" id="qqSelect" name="qq">
						<option value="0">所有人可见</option>
						<option value="1">仅自己可见</option>
					</select>  
					<span onclick="showSelect('qq');" id="qqSpan">所有人可见</span>     
				</td>
			</tr>
			<tr>
				<td class="fb_result_head" width="30%">
					主页
				</td>
				<td>
					<select style="display: none" id="homepageSelect" name="homePage">
						<option value="0">所有人可见</option>
						<option value="1">仅自己可见</option>
					</select>  
					<span onclick="showSelect('homepage');" id="homepageSpan">所有人可见</span>    
				</td>
			</tr>
			<tr>
				<td class="fb_result_head" width="30%">
					地址
				</td>
				<td>
					<select style="display: none" id="addressSelect" name="address">
						<option value="0">所有人可见</option>
						<option value="1">仅自己可见</option>
					</select>  
					<span onclick="showSelect('address');" id="addressSpan">所有人可见</span>    
				</td>
			</tr>
		</table>
	</form>
	<div class="formFunctiondiv">
		<jsp:include page="/WEB-INF/jsp/common/linkbutton.jsp">
			<jsp:param value="保存" name="save"/>
			<jsp:param value="返回" name="back"/>
		</jsp:include>
	</div>
</div>	