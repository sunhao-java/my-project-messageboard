<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/includes.jsp"%>
<%@ include file="/WEB-INF/jsp/common/common_js.jsp" %>

<msg:css href="css/photo.css"/>
<msg:js src="js/jquery/jquery-1.4.2.min.js"/>
<msg:js src="js/base/app-dialog.js"/>
<msg:js src="js/base/commfunction.js"/>

<script type="text/javascript">
	var $C = YAHOO.util.Connect,dom = YAHOO.util.Dom,event = YAHOO.util.Event;
	
	$(document).ready(function(){
		var link = $('#imageLink');
		link.mousemove(function(e){
			var e = e || window.event;
			var X = e.clientX;
			var thisX = $(this).offset().left + $(this).width() / 2;

			if (X <= thisX) {
				if('${previous}' != 'true'){
					link.attr('title', '点击查看上一张');
					link.attr('class', 'pre-cur');
					link.attr('href', '${contextPath}/album/showDetail.do?photoId=${photo.pkId}&albumId=${album.pkId}&type=previous');
				} else {
					link.attr('class', '');
					link.attr('title', '当前已是第一张');
				}
			} else {
				if('${next}' != 'true'){
					link.attr('title', '点击查看下一张');
					link.attr('class', 'next-cur');
					link.attr('href', '${contextPath}/album/showDetail.do?photoId=${photo.pkId}&albumId=${album.pkId}&type=next');
				} else {
					link.attr('class', '');
					link.attr('title', '当前已是最后一张');
				}
			}
		});
	});
	
	function setCover(photoId){
		var requestURL = '${contextPath}/album/setCover.do?photoId=' + photoId + '&albumId=${album.pkId}';
		YAHOO.app.dialog.pop({'dialogHead':'提示','alertMsg':'你确定设置此照片为相册封面吗？',
			'confirmFunction':function(){
				$C.asyncRequest('POST', requestURL, {
					success : function(o){
						var _e = eval("(" + o.responseText + ")");
						if(_e.status == '1'){
							YAHOO.app.dialog.pop({'dialogHead':'提示','cancelButton':'false','alertMsg':'设置封面成功 :)',
								autoClose:1});
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
	
	function move(pkId){
		var alertMsg = "<div class='move-body'>";
		alertMsg += "<p>转移至相册:</p>";
		alertMsg += '<select id="changeAlbum2" class="photo-move-opts">';
		<c:forEach items="${albums}" var="aa">
			alertMsg += "<option value='${aa.pkId}'>${aa.albumName}(${aa.photoCount})</option>";
		</c:forEach>
		alertMsg += "</select>";
					
		alertMsg += "</div>";
		YAHOO.app.dialog.pop({
			'dialogHead':'照片转移',
			'alertMsg':alertMsg,
			'icon':'none',
			diaHeight:130,
			'confirmFunction':function(){
					var toAlbumId = dom.get('changeAlbum2').value;
					var requestURL = '${contextPath}/album/movePhoto.do?toAlbumId=' + toAlbumId + '&photoId=' + pkId
					                                                   + '&fromAlbumId=${album.pkId}';
					$C.asyncRequest('POST', requestURL, {
						success : function(o){
							var _e = eval("(" + o.responseText + ")");
							if(_e.status == '1'){
								YAHOO.app.dialog.pop({'dialogHead':'提示','cancelButton':'false','alertMsg':'转移成功 :)',
									autoClose:1});
							} else {
								YAHOO.app.dialog.pop({'dialogHead':'提示','cancelButton':'false','alertMsg':'转移失败！'});
							}
						},
						failure : function(e){
							YAHOO.app.dialog.pop({'dialogHead':'提示','cancelButton':'false','alertMsg':'错误代码:' + o.status});
						}
					});
			}
		});
	}
	
	function deletePhoto(pkId){
		var requestURL = '${contextPath }/album/delete.do?photoId=' + pkId + '&albumId=${album.pkId}';
		//TODO 删除最后一张时要处理
		deleteOne(requestURL, '', 'true');
	}
</script>

<style type="text/css">
	.move-body {
	    padding: 0px;
	    position: relative;
	}
	
	.move-body p {
	    color: #333333;
	    margin-bottom: 1ex;
	    line-height: 1.4em;
	    margin-top: 0px;
	    margin-bottom: 0px;
	}
	
	.move-body select {
	    vertical-align: middle;
	    margin-top: 10px;
	    width: 230px;
	    font-family: "lucida grande",tahoma,verdana,arial,STHeiTi,simsun,sans-serif;
	    font-size: 12px;
	    border: 1px solid #BDC7D8;
	    height: 22px;
	    padding: 2px;
	    line-height: 1.1;
	}
	
	.move-body select option {
	    padding-left: 5px;
	}
	
	.move-body select, .move-body select option {
	    font-family: inherit;
	    font-size: inherit;
	    font-style: inherit;
	    font-weight: inherit;
	}
</style>

<jsp:include page="/WEB-INF/jsp/base/navigation.jsp">
	<jsp:param value="我的相册" name="title"/>
	<jsp:param value="${album.albumName}" name="title"/>
	<jsp:param value="当前照片" name="title"/>
	<jsp:param value="${contextPath}/album/index.do" name="link"/>
	<jsp:param value="${contextPath}/album/listPhotos.do?albumId=${album.pkId}" name="link"/>
</jsp:include>

<div class="clearfix" id="main">
	<div class="album-main">
		<div class="function-nav function-nav2 clearfix">
			<ul class="nav-btn">
				<li class="move">
					<a href="javascript:void(0);" onclick="move('${photo.pkId}');">转移</a>
				</li>
				<li class="pipe">
					|
				</li>
				<li class="cover">
					<a href="javascript:void(0);" onclick="setCover('${photo.pkId}');">设为封面</a>
				</li>
				<li class="pipe">
					|
				</li>
				<li class="delete">
					<a href="javascript:void(0);" onclick="deletePhoto('${photo.pkId}');">删除</a>
				</li>
			</ul>
		</div>
		<div class="photo">
			<a style="height: 450px;" id="imageLink">
				<img src="${contextPath}/photo.jpg?fileId=${photo.attachment.pkId}&check=true"  style="opacity: 1; 
					background-image: url('${contextPath}/image/a.gif');"/>
			</a>
		</div>
	</div>
	
	
	<div class="album-sidebar">
		<div class="corner-body">
			<h2>
				我的相册
				
					<a onclick="showAllMine();" href="javascript:void(0);" class="more" id="show">全部</a>
					<a style="display: none;" onclick="closePanel();" href="javascript:void(0);" class="more" id="close">收起</a>
				
			</h2>
			<ul id="showPanel" class="album-list-me">
				
					<li>
						<a class="album-mame" href="/message/album/listPhotos.do?albumId=82"> 
							
								
									<img title="测试测试" src="/message//image/default.png">
								
								
							
						</a>
						<span class="tag"> 
							<a title="测试测试" href="/message/album/listPhotos.do?albumId=82">测试测试</a> 
						</span>
						<span class="statis">0张</span>
					</li>
				
					<li>
						<a class="album-mame" href="/message/album/listPhotos.do?albumId=81"> 
							
								
									<img title="再建一个相册" src="/message//image/default.png">
								
								
							
						</a>
						<span class="tag"> 
							<a title="再建一个相册" href="/message/album/listPhotos.do?albumId=81">再建一个相册</a> 
						</span>
						<span class="statis">0张</span>
					</li>
				
					<li>
						<a class="album-mame" href="/message/album/listPhotos.do?albumId=61"> 
							
								
								
									<img title="哈哈的相册" src="/message/photo.jpg?filePath=/opt/application/data///2012/04/23//27de798e1465caadcf893fb623b46cea.jpg">
								
							
						</a>
						<span class="tag"> 
							<a title="哈哈的相册" href="/message/album/listPhotos.do?albumId=61">哈哈的相册</a> 
						</span>
						<span class="statis">2张</span>
					</li>
				
					<li>
						<a class="album-mame" href="/message/album/listPhotos.do?albumId=41"> 
							
								
								
									<img title="孙昊的相册" src="/message/photo.jpg?filePath=/opt/application/data///2012/04/23//d1c272e701d4e87f2f08d7bb4b80ae26.jpg">
								
							
						</a>
						<span class="tag"> 
							<a title="孙昊的相册" href="/message/album/listPhotos.do?albumId=41">孙昊的相册</a> 
						</span>
						<span class="statis">13张</span>
					</li>
				
			</ul>
		</div>
	</div>
</div>