<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/includes.jsp"%>
<%@ include file="/WEB-INF/jsp/common/common_js.jsp" %>

<msg:css href="css/photo.css"/>
<msg:js src="js/jquery/jquery-1.4.2.min.js"/>

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
</script>

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
					<a href="javascript:void(0);" onclick="editAlbum('${album.pkId}');">转移</a>
				</li>
				<li class="pipe">
					|
				</li>
				<li class="cover">
					<a href="javascript:void(0);" onclick="deleteAlbum('${album.pkId}');">设为封面</a>
				</li>
				<li class="pipe">
					|
				</li>
				<li class="delete">
					<a href="javascript:void(0);" onclick="deleteAlbum('${album.pkId}');">删除</a>
				</li>
			</ul>
		</div>
		<div class="photo">
			<a style="height: 450px;" id="imageLink">
				<img src="${contextPath}/photo.jpg?fileId=${photo.file.pkId}&check=true"  style="opacity: 1; 
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