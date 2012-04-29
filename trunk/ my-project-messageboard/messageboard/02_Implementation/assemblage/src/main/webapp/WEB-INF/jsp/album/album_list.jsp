<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/includes.jsp"%>
<%@ include file="/WEB-INF/jsp/common/common_js.jsp" %>

<msg:css href="css/album.css"/>
<msg:js src="js/jquery/jquery-1.4.2.min.js"/>
<msg:js src="js/base/app-dialog.js"/>
<msg:js src="js/base/app-alertForm.js"/>
<msg:js src="js/validate.js"/>

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
				<a class="upload-btn flashUploader" href="javaScript:void(0);" onclick="createNewAlbum();">
					<span class="add-ico">新建相册</span>
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