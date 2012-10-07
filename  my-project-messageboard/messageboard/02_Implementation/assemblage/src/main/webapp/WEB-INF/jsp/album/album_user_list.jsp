<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/includes.jsp"%>
<!--
* 好友的相册列表(具体到人)
* Developed By: sunhao
* Mail: sunhao.java@gmail.com
* Time: 12-10-07 下午07:30
* Version:1.0
* History:
-->

<msg:css href="css/album.css"/>

<script type="text/javascript">
	function gotoAlbumDetail(albumId){
		window.location.href = '${contextPath}/album/listPhotos.do?albumId=' + albumId + '&visit=true&uid=${user.pkId}';
	}
</script>

<jsp:include page="/WEB-INF/jsp/base/head.jsp">
	<jsp:param value="${user.truename}的相册列表" name="title"/>
</jsp:include>

<div class="photo-main">
	<div class="photo-main-content">
		<div class="toolbar">
			<div class="opera-bar">
				<span class="numbers" style="">共${paginationSupport.totalRow }个相册</span>
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
										<span class="album-name">${album.albumName}(${album.ower.truename})</span>
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
	<c:url var="paginationAction" value="album/listFriendAlbums.do"/>
	<%@ include file="/WEB-INF/jsp/common/pagination.jsp"%>
</div>