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
		using(["ajaxUpload", "simpleTip", "imagepreview", "icolorpicker"], function(){
			$("#characterContent").simpleTip({
                tip: '文字水印不能为空，并且不超过20字符！'
            });

            $("span.preview").preview();
            
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
	
	function icolor(id1, id2){
        iColorShow(id1, id2);
    }
	
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
		var ret;
        var pkId = $("#pkId").val();
		var characterContent = $("#characterContent").val();
		var colorStr = $("#colorStr").val();
		var fontSize = $("#fontSize").val();
		var markType = getRadio();
		var location = getLocation();
        var action = '';

        if(pkId == ''){
            action = "${contextPath}/album/saveConfig.do";
        } else {
            action = "${contextPath}/album/saveEdit.do?pkId=" + pkId;
        }
		
		//显示和隐藏正在上传的标识
		$("#loading").ajaxStart(function(){
			$(this).show();
		}).ajaxComplete(function(){
			$(this).hide();
		});
		
		$.ajaxFileUpload({
			url : action,
			secureuri : false,
			fileElementId : 'imageMark',
			dataType : 'json',
			data : {mark:markType, characterContent:characterContent, location:location, color:colorStr, fontSize:fontSize},
			success : function(data, status){
				ret = data.status;
				if(ret == '1') {
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
				return false;
			}
		});
		
		return ret;
	}
</script>

<style type="text/css">
	table.dialog_table {
		border-width: 0px !important;
	}
	
	table.dialog_table td {
		border-width: 0px !important;
	}
	
	table.dialog_table tr {
		border-width: 0px !important;
	}
</style>

<form id="markConfig" method="POST" enctype="multipart/form-data">
	<img id="loading" src="${contextPath }/image/loading.gif" style="display:none;">
	<input type="hidden" name="pkId" id="pkId" value="${albumConfig.pkId}"/>
	<table width="100%" class="dialog_table">
		<tr>
			<td class="text" width="1%">
				<input type="radio" value="word" name="mark" id="word" 
					<c:if test="${empty albumConfig || albumConfig.maskType eq '1'}">checked="checked"</c:if>>
			</td>
			<td>
				<label for="word">文字水印</label>
			</td>
		</tr>
		<tr id="wordTr" <c:if test="${albumConfig.maskType eq '2'}">style="display: none;"</c:if>>
			<td>
				
			</td>
			<td valign="middle">
				<span style="color: #CC0000;font-weight: bolder;font-size: 13pt">*</span>文字内容：
                <input type="text" class="width250" id="characterContent"
					name="characterContent" value="${albumConfig.characterMark}"/><br/>
				<span style="color: #CC0000;font-weight: bolder;font-size: 13pt">*</span>文字颜色：
				<input type="text" class="width150 iColorPicker" id="colorStr" name="color" value="${albumConfig.color}"
					style="background: ${albumConfig.color}" readonly="readonly"/>
				<a onclick="icolor('colorStr','icp_color')" id="icp_color" href="javascript:void(null)">
					<img src="${contextPath}/image/color.png" align="absmiddle" style="border:0;margin:0 0 0 3px">
				</a>
				<br/>
				<span style="color: #CC0000;font-weight: bolder;font-size: 13pt">*</span>文字大小：
                <input type="text" class="width250" id="fontSize"
					name="fontSize" value="${albumConfig.fontSize}"/><br/>
			</td>
		</tr>
		<tr>
			<td class="text">
				<input type="radio" value="image" name="mark" id="image" 
					<c:if test="${albumConfig.maskType eq '2'}">checked="checked"</c:if>>
			</td>
			<td>
				<label for="image">图片水印</label>
			</td>
		</tr>
		<tr id="imageTr" <c:if test="${empty albumConfig || albumConfig.maskType eq '1'}">style="display: none;"</c:if>>
			<td>
				
			</td>
			<td>
				<input type="file" name="imageMark" id="imageMark"/><span class="prompt">必填，图片格式为icon,png</span><br/>
				<c:if test="${not empty albumConfig.attachmentId && albumConfig.attachmentId ne -1}">
	                <span class="preview" href="${contextPath}/photo.jpg?fileId=${albumConfig.attachmentId}"
	                        title="水印图片">
	                   	 显示水印图片
	                </span>
                </c:if>
			</td>
		</tr>
		<tr>
			<td colspan="2">
				<div style="border-top: 1px solid #EEEEEE;">
					水印位置：
					<input type="radio" name="location" id="lefttop" value="1"
						<c:if test="${albumConfig.location eq 1}">checked="checked"</c:if>><label for="lefttop">左上角</label>
					<input type="radio" name="location" id="righttop" value="2"
						<c:if test="${albumConfig.location eq 2}">checked="checked"</c:if>><label for="righttop">右上角</label>
					<input type="radio" name="location" id="rightbuttom" value="3"
						<c:if test="${empty albumConfig || albumConfig.location eq 3}">checked="checked"</c:if>><label for="rightbuttom">右下角</label>
					<input type="radio" name="location" id="leftbuttom" value="4"
						<c:if test="${albumConfig.location eq 4}">checked="checked"</c:if>><label for="leftbuttom">左下角</label>
					<input type="radio" name="location" id="center" value="5"
						<c:if test="${albumConfig.location eq 5}">checked="checked"</c:if>><label for="center">居中</label>
				</div>
			</td>
		</tr>
	</table>
</form>