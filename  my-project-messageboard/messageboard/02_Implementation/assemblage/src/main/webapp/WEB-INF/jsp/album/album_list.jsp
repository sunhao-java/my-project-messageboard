<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/includes.jsp"%>
<%@ include file="/WEB-INF/jsp/common/common_js.jsp" %>

<msg:css href="css/album.css"/>
<msg:js src="js/jquery/jquery-1.4.2.min.js"/>
<msg:js src="js/base/app-dialog.js"/>
<msg:js src="js/base/app-alertForm.js"/>
<msg:js src="js/validate.js"/>

<msg:css href="css/menu.css"/>
<msg:js src="js/base/app-swfupload.js"/>

<script type="text/javascript">
	function createNewAlbum(){
		YAHOO.app.alertForm.show({
            'reqUrl':'${contextPath}/album/formbackAlbum.do',
            'title':'新建相册',
            'diaWidth':'450',
            'diaHeight':'180',
            'submitUrl':'${contextPath}/album/saveAlbum.do',
            'formId': 'albumForm',
            'success' : '新建相册成功',
            'failure' : '新建相册失败'
        });
	}
	
	function gotoAlbumDetail(albumId){
		window.location.href = '${contextPath}/album/listPhotos.do?albumId=' + albumId;
	}
	
	function config(){
		var name = 'configIFrame' + new Date().getTime();
		var popWin = 
		YAHOO.app.alertForm.show({
            'reqUrl':'${contextPath}/album/inConfig.do',
            'title':'图片水印（设置在图片中增加独具个性的水印） ',
            'needValidate':'false',
            'singleId':'false',
            'name':name,
            'diaWidth':'450',
            'diaHeight':'150',
            'success' : '设置水印成功',
            'failure' : '设置水印失败',
            'confirmFunction':function(){
            	var frame = frames[name];
            	var frameData = frame.getFrmData();
            	
            	var radioValue = frame.getRadio();
            	if(radioValue == 'word'){
            		//文字水印
            		var characterContent = frameData.characterContent.value;
            		if(characterContent == '' || characterContent.length > 20){
            			YAHOO.app.dialog.pop({
	                        'dialogHead':'提示',
	                        'cancelButton':'false',
	                        'alertMsg':'文字水印不能为空，并且不超过20字符！',
	                        'icon':'warnicon'
	                     });
            		} else {
            			submitFun(frame, popWin);
            		}
            	} else if(radioValue == 'image'){
            		//图片水印
            		var imagePath = frameData.imageMark.value;
            		if(imagePath == ''){
            			YAHOO.app.dialog.pop({
	                        'dialogHead':'提示',
	                        'cancelButton':'false',
	                        'alertMsg':'图片水印不能为空，并且不超过20字符！',
	                        'icon':'warnicon'
	                     });
            		} else {
            			if(validateImage(imagePath)){
	            			submitFun(frame, popWin);
            			}
            		}
            	} else {
            		YAHOO.app.dialog.pop({
                       'dialogHead':'提示',
                       'cancelButton':'false',
                       'alertMsg':'系统内部出现问题，请联系管理员！',
                       'icon':'warnicon'
                    });
            	}
            }
        });
	}
	
	function validateImage(path){
		var agreeExt = 'icon|png';
		var index = path.lastIndexOf('.');
		var ext = path.substring(index + 1);
		
		if(agreeExt.indexOf(ext) == -1){
			YAHOO.app.dialog.pop({
               'dialogHead':'提示',
               'cancelButton':'false',
               'alertMsg':'只能上传icon或者png格式的图片！',
               'icon':'warnicon'
            });
			
			return false;
		}
		
		return true;
	}
	
	function submitFun(frame, dialog){
		frame.submitFun(dialog);
	}
</script>

<jsp:include page="/WEB-INF/jsp/base/head.jsp">
	<jsp:param value="我的相册列表" name="title"/>
</jsp:include>

<div class="photo-main">
	<div class="photo-main-content">
		<div class="toolbar">
			<div class="opera-bar">
				<span class="numbers" style="">共${paginationSupport.totalRow }个相册</span>
			</div>
			<div class="left">
				<a class="upload-btn" href="javaScript:void(0);" onclick="createNewAlbum();">
					<span class="add-ico">新建相册</span>
				</a>
			</div>
			<span class="pipi left">
				|
			</span>
			<div class="left">
				<a class="upload-btn" href="javaScript:void(0);" onclick="config();">
					<span class="add-ico">水印设置</span>
				</a>
			</div>
		</div>
		
		<div class="album-home">
			<div class="album-list">
				<div class="first-page clearfix">
					<ul>
						<c:forEach items="${paginationSupport.items}" var="album">
							<li>
								<a class="album-cover" href="javaScript:void(0);" onclick="gotoAlbumDetail('${album.pkId}');" title="${album.albumName}">
									<c:choose>
										<c:when test="${album.cover eq '/image/default.png'}">
											<img src="${contextPath}/image/a.gif"  style="opacity: 1; 
													background-image: url('${contextPath}/${album.cover}');" alt="${photo.photoName}"/>
										</c:when>
										<c:otherwise>
											<img src="${contextPath}/image/a.gif"  style="opacity: 1; 
													background-image: url('${contextPath}/photo.jpg?filePath=${album.cover}&width=154&height=130');" alt="${photo.photoName}"/>
										</c:otherwise>
									</c:choose>
									<div class="photo-num">
										${album.photoCount}
									</div> 
								</a>
								<a class="album-title" href="javaScript:void(0);" onclick="gotoAlbumDetail('${album.pkId}');" title="${album.albumName}">
									<div class="infor">
										<span class="album-name">${album.albumName}</span>
									</div> 
								</a>
							</li>
						</c:forEach>
					</ul>
				</div>
			</div>
			<!-- enf of album-list -->
		</div>
		<!-- end of album-home -->
	</div>
</div>

<c:url var="paginationAction" value="album/index.do"/>
<%@ include file="/WEB-INF/jsp/common/pagination.jsp"%>