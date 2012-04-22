<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/includes.jsp"%>
<%@ include file="/WEB-INF/jsp/common/common_js.jsp" %>

<msg:css href="css/photo.css"/>
<msg:js src="js/base/app-swfupload.js"/>
<msg:js src="js/jquery/jquery-1.4.2.min.js"/>
<msg:js src="js/base/app-dialog.js"/>
<msg:js src="js/base/app-dialog.js"/>
<msg:js src="js/base/app-alertForm.js"/>
<msg:js src="js/validate.js"/>
<msg:js src="js/base/commfunction.js"/>

<script type="text/javascript">
	var $C = YAHOO.util.Connect,dom = YAHOO.util.Dom,event = YAHOO.util.Event;

	$(document).ready(function(){
        YAHOO.app.swfupload("upload", "showUploadPanel", {
            title : '上传附件',
            fileTypes : '*.*',
            params : {
                headImage : 'false',
                resourceId : '${pkId}',
                resourceType : 1,
                uploadId : '${loginUser.pkId}'
            }
        });
        
    });
	
	function editPhoto(photoId){
		var photoLis = dom.getElementsByClassName('select');
		if(photoLis.length > 0){
			dom.removeClass(photoLis[0], 'select');
			event.removeListener(window, 'keydown');
		}
		
		var photoLi = dom.get('li#' + photoId);
		dom.addClass(photoLi, 'select');
		event.addListener(window, 'keydown', function(e){
        	var keyCode = e.which;
        	if(keyCode == 27){
        		cancel(photoId);
        		event.removeListener(window, 'keydown');
        	} else if(keyCode == 13){
        		alert('enter');
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
	
	function deleteAlbum(albumId){
		var requestURL = '${contextPath }/album/deleteAlbum.do?albumId=' + albumId;
		var responseURL = '${contextPath}/album/index.do';
		deleteOne(requestURL, responseURL, 'false');
	}
</script>

<jsp:include page="/WEB-INF/jsp/base/head.jsp">
	<jsp:param value="我的相册&nbsp;--&gt;&nbsp;${album.albumName}" name="title"/>
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
			</ul>
		</div>

		<div class="ablum-infor-box ablum-infor-fri">
			<div class="ablum-infor">
				<h1>
					${album.albumName}
					<span class="num">(27张照片)</span>
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

		<!-- the first tabs -->
		<div class="photo-list my-list" style="">
			<div class="first-page clearfix">
				<ul>
					<li id="li#1">
						<a class="picture" href="#" style="cursor: move"> 
							<img src="${contextPath}/image/a.gif" style="opacity: 1; 
									background-image: url('${contextPath}/image/default.png');">
						</a>
						<div class="photo-oper">
							<a href="javascript:void(0);" class="photo-edit" onclick="editPhoto('1')">编辑</a>
						</div>
						<div class="myphoto-info">
							<span class="descript">1111</span>
						</div>
						<div class="edit-desc" style="" id="edit#1">
							<div class="edit-content">
								<input type="text" value="">
								<p>
									可按
									<span class="">回车</span>保存、
									<span class="">Esc</span><a href="javascript:;" onclick="cancel('1')">取消</a>
								</p>
							</div>
						</div>
					</li>
					
					<li id="li#2">
						<a class="picture" href="#" style="cursor: move"> 
							<img src="${contextPath}/image/a.gif" style="opacity: 1; 
									background-image: url('${contextPath}/image/default.png');">
						</a>
						<div class="photo-oper">
							<a href="javascript:void(0);" class="photo-edit" onclick="editPhoto('2')">编辑</a>
						</div>
						<div class="myphoto-info">
							<span class="descript">1111</span>
						</div>
						<div class="edit-desc" style="" id="edit#2">
							<div class="edit-content">
								<input type="text" value="">
								<p>
									可按
									<span class="">回车</span>保存、
									<span class="">Esc</span><a href="javascript:;" onclick="cancel('2')">取消</a>
								</p>
							</div>
						</div>
					</li>
				</ul>
			</div>
		</div>
	</div>
	<!-- end of album-main -->
	<div class="album-sidebar">
		<div class="corner-body">
			<h2>
				我的相册
			</h2>
			<ul class="album-list-me">
				<li>
					<a href="javascript:void(0);" class="album-mame"> 
						<img width="50" height="50"
							src="${contextPath}/image/default.png" title="楚楚街寿命计算器"> 
					</a>
					<span class="tag"> 
						<a href="#" title="楚楚街寿命计算器">楚楚街寿命计算器</a> </span>
					<span class="statis">1张 <span> | </span></span>
				</li>
			</ul>
		</div>
	</div>
</div>