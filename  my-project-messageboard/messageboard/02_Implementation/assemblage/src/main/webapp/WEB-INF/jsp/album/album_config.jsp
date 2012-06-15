<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/includes.jsp"%>
<%@ include file="/WEB-INF/jsp/common/common_js.jsp" %>

<msg:css href="css/menu.css"/>
<msg:js src="js/jquery/easyloader.js"/>
<msg:js src="js/jquery/jquery-1.4.2.min.js"/>
<msg:js src="js/base/app-dialog.js"/>
<msg:js src="js/base/app-alertForm.js"/>
<msg:js src="js/base/app-swfupload.js"/>

<script type="text/javascript">
	var $C = YAHOO.util.Connect,dom = YAHOO.util.Dom,event = YAHOO.util.Event,$L=YAHOO.lang;
	
	$(document).ready(function(){
		using("ajaxUpload", function(){
			
		});
		
		$("#word").bind('click', function(){
			$("#wordTr").show();
			$("#imageTr").hide();
		});
		$("#image").bind('click', function(){
			$("#wordTr").hide();
			$("#imageTr").show();
		});
	});
	
	function getFrmData(){
		return dom.get('markConfig');
	}
	
	function getRadio(){
		var radios = document.getElementsByName("mark");
		for(var i = 0; i < radios.length; i++){
			if(radios[i].checked){
				return radios[i].value;
			}
		}
	}
	
	function getLocation(){
		var locations = document.getElementsByName("location");
		for(var i = 0; i < locations.length; i++){
			if(locations[i].checked){
				return locations[i].value;
			}
		}
	}
	
	function submitFun(dialog){
		var characterContent = $("#characterContent").val();
		var markType = getRadio();
		var location = getLocation();
		
		
		//显示和隐藏正在上传的标识
		$("#loading").ajaxStart(function(){
			$(this).show();
		}).ajaxComplete(function(){
			$(this).hide();
		});
		
		$.ajaxFileUpload({
			url : '${contextPath}/album/saveConfig.do',
			secureuri : false,
			fileElementId : 'imageMark',
			dataType : 'json',
			data : {mark:markType, characterContent:characterContent, location:location},
			success : function(ret, status){
				var status = ret.status;
				if(status == '1') {
					YAHOO.app.dialog.pop({
                       'dialogHead':'提示',
                       'cancelButton':'false',
                       'alertMsg':'配置水印成功！',
                       'icon':'warnicon',
                       'confirmFunction':function(){
							dialog.cancel();
                       }
                    });
				} else {
					YAHOO.app.dialog.pop({
                       'dialogHead':'提示',
                       'cancelButton':'false',
                       'alertMsg':'配置水印失败！',
                       'icon':'warnicon'
                    });
				}
			},
			error : function(data, status, e){
				alert(e);
			}
		});
		
		return false;
	}
</script>

<style type="text/css">
	table {
		border-width: 0px !important;
	}
	
	table td {
		border-width: 0px !important;
	}
	
	table tr {
		border-width: 0px !important;
	}
</style>

<form id="markConfig" method="POST" enctype="multipart/form-data">
	<img id="loading" src="${contextPath }/image/loading.gif" style="display:none;">
	<table width="100%" class="dialog_table">
		<tr>
			<td class="text" width="1%">
				<input type="radio" value="word" name="mark" id="word" checked="checked">
			</td>
			<td>
				<label for="word">文字水印</label>
			</td>
		</tr>
		<tr id="wordTr">
			<td>
				
			</td>
			<td>
				文字内容：
				<input type="text" class="width250" id="characterContent" name="characterContent"/>&nbsp;
				<span style="color: #CC0000;font-weight: bolder;">*</span><br/>
			</td>
		</tr>
		<tr>
			<td class="text">
				<input type="radio" value="image" name="mark" id="image">
			</td>
			<td>
				<label for="image">图片水印</label>
			</td>
		</tr>
		<tr id="imageTr" style="display: none;">
			<td>
				
			</td>
			<td>
				图片：<input type="file" name="imageMark" id="imageMark"/>
			</td>
		</tr>
		<tr>
			<td colspan="2">
				<div style="border-top: 1px solid #EEEEEE;">
					水印位置：
					<input type="radio" name="location" id="lefttop" value="1"><label for="lefttop">左上角</label>
					<input type="radio" name="location" id="righttop" value="2"><label for="righttop">右上角</label>
					<input type="radio" name="location" id="rightbuttom" value="3" checked="checked"><label for="rightbuttom">右下角</label>
					<input type="radio" name="location" id="leftbuttom" value="4"><label for="leftbuttom">左下角</label>
					<input type="radio" name="location" id="center" value="5"><label for="center">居中</label>
				</div>
			</td>
		</tr>
	</table>
</form>