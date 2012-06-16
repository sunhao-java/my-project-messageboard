<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/includes.jsp"%>
<%@ include file="/WEB-INF/jsp/common/common_js.jsp" %>

<msg:css href="css/photo.css"/>
<msg:js src="js/base/app-swfupload.js"/>
<msg:js src="js/jquery/jquery-1.4.2.min.js"/>
<msg:js src="js/jquery/easyloader.js"/>
<msg:js src="js/base/app-dialog.js"/>
<msg:js src="js/base/app-alertForm.js"/>
<msg:js src="js/validate.js"/>
<msg:js src="js/base/commfunction.js"/>

<script type="text/javascript">
	var $C = YAHOO.util.Connect,dom = YAHOO.util.Dom,event = YAHOO.util.Event;

	$(document).ready(function(){
		using(['fancybox'], function(){
			$("a[rel=fancyshow_group]").fancybox({
				'transitionIn'	:	'elastic',
				'transitionOut'	:	'elastic',
				'speedIn'		:	600, 
				'speedOut'		:	200, 
				'overlayShow'	:	true,
				'titlePosition' 	: 'over',
				'titleFormat'		: function(title, pkId, currentArray, currentIndex, currentOpts) {
					var returnValue = "";
					returnValue += '<span id="fancybox-title-over"> ' + (currentIndex + 1) + ' / ' 
									+ currentArray.length + (title.length ? ' &nbsp; ' + title : ' &nbsp; 没有描述！');
					returnValue += '<span class="float-right">';
					returnValue += '<a href="javascript:void(0)" class="show-bigpic" onclick="showDetail(&quot;' + pkId + '&quot;);">查看详情</a>';
					returnValue += '</span>';
					returnValue += '</span>';
					
					return returnValue;
				}
			});
		});
		
        YAHOO.app.swfupload("upload", "showUploadPanel", {
            title : '上传图片',
            fileTypes : '*.jpg;*.png;*.gif;*.jpeg;*.bmp',
            fileTypeDescription : '请选择图片',
            uploadUrl : '${contextPath}/album/uploadPhoto.do',
            params : {
                headImage : 'false',
                resourceId : '${album.pkId}',
                resourceType : ${resourceType},
                uploadId : '${loginUser.pkId}'
            },
            completeFunction : 'completeFun'
        });
        
        YAHOO.app.swfupload("upload2", "showUploadPanel", {
            title : '上传图片',
            fileTypes : '*.jpg;*.png;*.gif;*.jpeg;*.bmp',
            fileTypeDescription : '请选择图片',
            uploadUrl : '${contextPath}/album/uploadPhoto.do',
            params : {
                headImage : 'false',
                resourceId : '${album.pkId}',
                resourceType : ${resourceType},
                uploadId : '${loginUser.pkId}'
            },
            completeFunction : 'completeFun'
        });
    });
	
	function completeFun(){
        uploadDialog.cancel();
        YAHOO.app.dialog.pop({'dialogHead':'提示','cancelButton':'false','alertMsg':'上传图片成功！','confirmFunction':function(){
             window.location.reload(true);
        }});
    }
	
	function editPhoto(photoId){
		var photoLis = dom.getElementsByClassName('select');
		if(photoLis.length > 0){
			dom.removeClass(photoLis[0], 'select');
			event.removeListener(window, 'keydown');
		}
		
		var photoLi = dom.get('li#' + photoId);
		dom.addClass(photoLi, 'select');
		dom.get('summary#' + photoId).focus();
		event.addListener(window, 'keydown', function(e){
        	var keyCode = e.which;
        	if(keyCode == 27){
        		//ESC
        		cancel(photoId);
        		event.removeListener(window, 'keydown');
        	} else if(keyCode == 13){
        		//ENTRY
        		var summary = dom.get('summary#' + photoId);
        		if(summary.value == ''){
        			cancel(photoId);
        			return;
        		}
        		var requestURL = '${contextPath}/album/saveSummary.do?summary=' + summary.value + '&photoId=' + photoId;
				$C.asyncRequest('POST', requestURL, {
					success : function(o){
						var _e = eval("(" + o.responseText + ")");
						if(_e.status == '1'){
							dom.get('descript#' + photoId).innerHTML = summary.value;
							dom.get('link#' + photoId).title = summary.value;
							cancel(photoId);
						} else {
							YAHOO.app.dialog.pop({'dialogHead':'提示','cancelButton':'false','alertMsg':'保存描述失败！'});
						}
					}, 
					failure : function(o){
						YAHOO.app.dialog.pop({'dialogHead':'提示','cancelButton':'false','alertMsg':'错误代码:' + o.status});
					}
				});
        		event.removeListener(window, 'keydown');
        	}
        });
	}
	
	function cancel(photoId){
		var photoLi = dom.get('li#' + photoId);
		event.removeListener(window, 'keydown');
		dom.removeClass(photoLi, 'select');
	}
	
	function editAlbum(albumId){
		YAHOO.app.alertForm.show({
            'reqUrl':'${contextPath}/album/formbackAlbum.do?albumId=' + albumId,
            'title':'编辑相册',
            'diaWidth':'450',
            'diaHeight':'180',
            'submitUrl':'${contextPath}/album/saveAlbum.do',
            'formId': 'albumForm',
            'success' : '编辑相册成功',
            'failure' : '编辑相册失败'
        });
	}
	
	function setCover(photoId){
		var requestURL = '${contextPath}/album/setCover.do?photoId=' + photoId + '&albumId=${album.pkId}';
		var dialog = 
		YAHOO.app.dialog.pop({'dialogHead':'提示','alertMsg':'你确定设置此照片为相册封面吗？',
			'confirmFunction':function(){
				$C.asyncRequest('POST', requestURL, {
					success : function(o){
						var _e = eval("(" + o.responseText + ")");
						if(_e.status == '1'){
							YAHOO.app.dialog.pop({'dialogHead':'提示','cancelButton':'false','alertMsg':'设置封面成功 :)',
								autoClose:1});
							dialog.cancel();
						} else {
							YAHOO.app.dialog.pop({'dialogHead':'提示','cancelButton':'false','alertMsg':'设置封面失败！'});
						}
					},
					failure : function(e){
						YAHOO.app.dialog.pop({'dialogHead':'提示','cancelButton':'false','alertMsg':'错误代码:' + o.status});
					}
				});
			}});
	}
	
	function deleteAlbum(albumId){
		var requestURL = '${contextPath }/album/deleteAlbum.do?albumId=' + albumId;
		var responseURL = '${contextPath}/album/index.do';
		deleteOne(requestURL, responseURL, 'false');
	}
	
	function showAllMine(){
		var htmlCookie = YAHOO.util.Cookie.get('htmlCookie${randomTag}');
		if(htmlCookie == null){
			var requestURL = '${contextPath}/album/showAllMine.do';
			$C.asyncRequest('POST',requestURL,{
				success : function(o){
					var _e = eval('(' + o.responseText + ')');
					htmlCookie = makeHTML(_e.albums);
					YAHOO.util.Cookie.set('htmlCookie${randomTag}', htmlCookie);
					
					dom.get('showPanel').innerHTML = htmlCookie;
					dom.get('show').style.display = 'none';
					dom.get('close').style.display = '';
				}
			});
		} else {
			dom.get('showPanel').innerHTML = htmlCookie;
			dom.get('show').style.display = 'none';
			dom.get('close').style.display = '';
		}
	}
	
	function makeHTML(albums){
		var html = '';
		for(var i = 0; i < albums.length; i++){
			var album = albums[i];
			html += '<li>';
			html += '	<a class="album-name" href="${contextPath}/album/listPhotos.do?albumId=' + album.pkId + '">';
			if(album.cover == '/image/default.png'){
				html += '		<img title="' + album.albumName + '" src="${contextPath}/' + album.cover + '"/>';
			} else {
				html += '		<img title="' + album.albumName + '" src="${contextPath}/photo.jpg?filePath=' + album.cover + '"/>';
			}
			html += '	</a>';
			html += '<span class="tag">';
			html += '	<a class="album-name" href="${contextPath}/album/listPhotos.do?albumId=' + album.pkId + '">';
			html += 		album.albumName;
			html += '	</a>';
			html += '</span>';
			html += '<span class="statis">' + album.photoCount + '张</span>';
		}
		
		return html;
	}
	
	function closePanel(){
		var html = '';
		
		<c:forEach items="${albums.items}" var="album1">
			html += '<li>';
			html += '	<a class="album-name" href="${contextPath}/album/listPhotos.do?albumId=${album1.pkId}">';
			if('${album1.cover}' == '/image/default.png'){
				html += '	<img title="${album1.albumName}" src="${contextPath}/${album1.cover}"/>';
			} else {
				html += '	<img title="${album1.albumName}" src="${contextPath}/photo.jpg?filePath=${album1.cover}"/>';
			}
			html += '	</a>';
			html += '<span class="tag">';
			html += '	<a class="album-name" href="${contextPath}/album/listPhotos.do?albumId=${album1.pkId}">';
			html += 		'${album1.albumName}';
			html += '	</a>';
			html += '</span>';
			html += '<span class="statis">${album1.photoCount}张</span>';
		</c:forEach>
		
		dom.get('showPanel').innerHTML = html;
		dom.get('show').style.display = '';
		dom.get('close').style.display = 'none';
	}
	
	function showDetail(pkId){
		window.location.href = '${contextPath}/album/showDetail.do?photoId=' + pkId + '&albumId=${album.pkId}';
	}
	
	function exportAlbum(albumId){
		window.location.href = '${contextPath}/album/export.do?albumId=' + albumId;
	}
</script>

<jsp:include page="/WEB-INF/jsp/base/navigation.jsp">
	<jsp:param value="我的相册" name="title"/>
	<jsp:param value="${album.albumName}" name="title"/>
	<jsp:param value="${contextPath}/album/index.do" name="link"/>
</jsp:include>

<div class="clearfix" id="main">
	<div class="album-main">
		<div class="function-nav clearfix">
			<div class="float-left">
				<input type="button" id="upload" value="上传图片">
			</div>
			<ul class="nav-btn">
				<li class="editi-button">
					<a href="javascript:void(0);" onclick="editAlbum('${album.pkId}');">编辑</a>
				</li>
				<li class="pipe">
					|
				</li>
				<li class="delete-button">
					<a href="javascript:void(0);" onclick="deleteAlbum('${album.pkId}');">删除相册</a>
				</li>
				<li class="pipe">
					|
				</li>
				<li class="export-button">
					<a href="javascript:void(0);" onclick="exportAlbum('${album.pkId}');">导出相册</a>
				</li>
			</ul>
		</div>

		<div class="ablum-infor-box ablum-infor-fri">
			<div class="ablum-infor">
				<h1>
					${album.albumName}
					<span class="num">(${album.photoCount }张照片)</span>
				</h1>
				<p class="describe" id="describeAlbum">
					<c:choose>
						<c:when test="${not empty album.summary}">
							${album.summary}
						</c:when>
						<c:otherwise>
							还没有相册描述...
						</c:otherwise>
					</c:choose>
					<a href="javascript:void(0);" onclick="editAlbum('${album.pkId}')">编辑</a>
				</p>
			</div>
		</div>
		
		<c:choose>
			<c:when test="${empty paginationSupport.items}">
				<div class="no-content">
					当前相册中没有照片， <%--<a target="_blank" class="upload flashUploader fromAlbum" href="http://upload.renren.com/index.do?id=522521488&amp;ref=1002" style="display: ">，点这里添加 » </a>--%>
					<input type="button" id="upload2" value="上传图片">
				</div>
			</c:when>
			<c:otherwise>
				<div class="photo-list my-list" style="">
					<div class="first-page clearfix">
						<ul>
							<c:forEach items="${paginationSupport.items}" var="photo">
								<li id="li#${photo.pkId}">
									<%-- TODO --%>
									<a class="picture" href="${contextPath}/photo.jpg?fileId=${photo.attachment.pkId}" style="cursor: move" 
											title="${photo.summary}" rel="fancyshow_group" id="link#${photo.pkId}" pkId="${photo.pkId}"> 
										<img src="${contextPath}/image/a.gif"  style="opacity: 1; 
											background-image: url('${contextPath}/photo.jpg?fileId=${photo.attachment.pkId}&width=170&height=130');" alt="${photo.photoName}"/>
									</a>
									<div class="photo-oper">
										<a href="javascript:void(0);" title="设为封面" class="photo-cover" onclick="setCover('${photo.pkId}')">设为封面</a>
									</div>
									<div class="photo-oper">
										<a href="javascript:void(0);" title="编辑" class="photo-edit" onclick="editPhoto('${photo.pkId}')">编辑</a>
									</div>
									<div class="myphoto-info">
										<span class="descript" id="descript#${photo.pkId}">
											<c:choose>
												<c:when test="${empty photo.summary}">
													没有描述！
												</c:when>
												<c:otherwise>
													${photo.summary}
												</c:otherwise>
											</c:choose>
										</span>
									</div>
									<div class="edit-desc" style="" id="edit#${photo.pkId}">
										<div class="edit-content">
											<input type="text" value="${photo.summary}" id="summary#${photo.pkId}">
											<p>
												可按
												<span class="">回车</span>保存、
												<span class="">Esc</span>取消
											</p>
										</div>
									</div>
								</li>
							</c:forEach>
						</ul>
					</div>
				</div>
				<c:url var="paginationAction" value="album/listPhotos.do?albumId=${album.pkId}"/>
				<%@ include file="/WEB-INF/jsp/common/pagination.jsp"%>
			</c:otherwise>
		</c:choose>
		
		<!-- the first tabs -->
	</div>
	<!-- end of album-main -->
	<div class="album-sidebar">
		<div class="corner-body">
			<h2>
				我的相册
				<c:if test="${albums.totalRow ne fn:length(albums.items)}">
					<a id="show" class="more" href="javascript:void(0);" onclick="showAllMine();">全部</a>
					<a id="close" class="more" href="javascript:void(0);" onclick="closePanel();" style="display: none;">收起</a>
				</c:if>
			</h2>
			<ul class="album-list-me" id="showPanel">
				<c:forEach items="${albums.items}" var="a">
					<li>
						<a href="${contextPath}/album/listPhotos.do?albumId=${a.pkId}" class="album-mame"> 
							<c:choose>
								<c:when test="${a.cover eq '/image/default.png'}">
									<img src="${contextPath}/${a.cover}" title="${a.albumName}"/>
								</c:when>
								<c:otherwise>
									<img src="${contextPath}/photo.jpg?filePath=${a.cover}" title="${a.albumName}"/>
								</c:otherwise>
							</c:choose>
						</a>
						<span class="tag"> 
							<a href="${contextPath}/album/listPhotos.do?albumId=${a.pkId}" title="${a.albumName}">${a.albumName}</a> 
						</span>
						<span class="statis">${a.photoCount}张</span>
					</li>
				</c:forEach>
			</ul>
		</div>
	</div>
</div>