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
				if('${previous}' != ''){
					link.attr('title', '点击查看上一张');
					link.attr('class', 'pre-cur');
					link.attr('href', '${contextPath}/album/showDetail.do?photoId=${photo.pkId}&albumId=${album.pkId}&type=previous&visit=${visit}');
				} else {
					link.attr('class', '');
					link.attr('title', '当前已是第一张');
				}
			} else {
				if('${next}' != ''){
					link.attr('title', '点击查看下一张');
					link.attr('class', 'next-cur');
					link.attr('href', '${contextPath}/album/showDetail.do?photoId=${photo.pkId}&albumId=${album.pkId}&type=next&visit=${visit}');
				} else {
					link.attr('class', '');
					link.attr('title', '当前已是最后一张');
				}
			}
		});
		
		if('${previous}' == ''){
			$("#previmg").attr('href', 'javascript:void(0)');
			$('#previmg > img').attr('style', 'background: url(${contextPath}/image/pic_first.png) center center');
			$("span.b > a:first").attr('href', 'javascript:void(0)');
		} else {
			$("#previmg").attr('href', '${contextPath}/album/showDetail.do?photoId=${photo.pkId}&albumId=${album.pkId}&type=previous&visit=${visit}');
			$('#previmg > img').attr('style', 'background: url(${contextPath}/photo.jpg?' +
															'fileId=${previous.attachment.pkId}&width=50&height=50) center center');
			$("span.b > a:first").attr('href', '${contextPath}/album/showDetail.do?' +
															'photoId=${photo.pkId}&albumId=${album.pkId}&type=previous&visit=${visit}');
		}
		
		if('${next}' == ''){
			$("#nextimg").attr('href', 'javascript:void(0)');
			$('#nextimg > img').attr('style', 'background: url(${contextPath}/image/pic_last.png) center center');
			$("span.b > a:last").attr('href', 'javascript:void(0)');
		} else {
			$("#nextimg").attr('href', '${contextPath}/album/showDetail.do?photoId=${photo.pkId}&albumId=${album.pkId}&type=next&visit=${visit}');
			$('#nextimg > img').attr('style', 'background: url(${contextPath}/photo.jpg?' +
															'fileId=${next.attachment.pkId}&width=50&height=50) center center');
			$("span.b > a:last").attr('href', '${contextPath}/album/showDetail.do?' +
															'photoId=${photo.pkId}&albumId=${album.pkId}&type=next&visit=${visit}');
		}
		
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
			'diaHeight':130,
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
		deleteOne(requestURL, '${contextPath}/album/listPhotos.do?albumId=${album.pkId}', 'false');
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

<c:if test="${!visit}">
	<c:set value="我的相册" var="title1"/>
	<c:set value="${album.albumName}" var="albumUserName"/>
	<c:set value="/album/index.do" var="urllink"/>
	<c:set value="/album/listPhotos.do?albumId=${album.pkId}" var="urllink2"/>
</c:if>
<c:if test="${visit}">
	<c:set value="好友的相册" var="title1"/>
	<c:set value="${album.ower.truename }的相册--${album.albumName}" var="albumUserName"/>
	<c:set value="/album/listFriendAlbums.do" var="urllink"/>
	<c:set value="/album/listPhotos.do?albumId=${album.pkId}&visit=true" var="urllink2"/>
</c:if>
<jsp:include page="/WEB-INF/jsp/base/navigation.jsp">
	<jsp:param value="${title1}" name="title"/>
	<jsp:param value="${albumUserName}" name="title"/>
	<jsp:param value="当前照片" name="title"/>
	<jsp:param value="${contextPath}/${urllink}" name="link"/>
	<jsp:param value="${contextPath}/${urllink2}" name="link"/>
</jsp:include>

<div class="clearfix" id="main" style="border: 1px solid #CCCCCC">
	<div class="album-main">
		<div class="function-nav function-nav2 clearfix">
			<c:if test="${!visit}">
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
			</c:if>
		</div>
		<div class="photo">
			<a style="height: 450px;" id="imageLink">
				<img src="${contextPath}/photo.jpg?fileId=${photo.attachment.pkId}&width=720&height=540"  style="opacity: 1; 
					background-image: url('${contextPath}/image/a.gif');"/>
			</a>
		</div>
		
		<a target="_blank" href="${contextPath}/photo.jpg?fileId=${photo.attachment.pkId}" class="show-big">查看大图</a>
		
		<c:if test="${!visit}">
			<div style="" class="photo-desc" id="photoTitleEditorContainer">
				<p origintitle="" id="photoTitle" style="" class=" " onmouseover="$(this).attr('class', 'photo-title-hover')"
						onmouseout="$(this).attr('class', '')" onclick="dom.get('photoTitleEditor').style.display = '';$(this).hide();$('#description').focus();">
					<c:choose>
						<c:when test="${empty photo.summary}">
							单击此处添加描述
						</c:when>
						<c:otherwise>
							${photo.summary}
						</c:otherwise>
					</c:choose>
				</p>
				
				<script type="text/javascript">
					function saveSummary(){
						var summary = dom.get('description');
		        		if(summary.value == ''){
		        			return;
		        		}
		        		
		        		var requestURL = '${contextPath}/album/saveSummary.do?summary=' + summary.value + '&photoId=' + ${photo.pkId};
						$C.asyncRequest('POST', requestURL, {
							success : function(o){
								var _e = eval("(" + o.responseText + ")");
								if(_e.status == '1'){
									dom.get('photoTitle').innerHTML = summary.value;
									dom.get('photoTitle').style.display = '';
									dom.get('photoTitleEditor').style.display = 'none';
								} else {
									YAHOO.app.dialog.pop({'dialogHead':'提示','cancelButton':'false','alertMsg':'保存描述失败！'});
								}
							}, 
							failure : function(o){
								YAHOO.app.dialog.pop({'dialogHead':'提示','cancelButton':'false','alertMsg':'错误代码:' + o.status});
							}
						});
					}
				</script>
	
				<div style="display: none;" id="photoTitleEditor">
					<textarea class="textarea" id="description" name="description">${photo.summary}</textarea>
					<div class="button-holder">
						<input type="button" value="保存" class="input-button"
							id="btnSaveTitle" onclick="saveSummary()">
						<input type="button" value="取消" class="input-button gray"
							onclick="$('#photoTitleEditor').hide();$('#photoTitle').show();">
					</div>
				</div>
	
			</div>
		</c:if>
		<c:if test="${visit}">
			<div style="" class="photo-desc" id="photoTitleEditorContainer">
				<p origintitle="" id="photoTitle" style="" class=" ">
					<c:choose>
						<c:when test="${empty photo.summary}">
							还没有相册描述...
						</c:when>
						<c:otherwise>
							${photo.summary}
						</c:otherwise>
					</c:choose>
				</p>
			</div>
		</c:if>
	</div>
	
	
	<div class="album-sidebar">
		<div class="corner-body">
			<h2>
				${album.albumName}
			</h2>
			<div id="smallImgContent" class="smallImg">
				<ul class="clearfix">
					<li>
						<span class="t">
							<a href="javascript:void(0)" id="previmg">
								<img src="${contextPath}/image/transparent.gif" style="">
							</a>
						</span>
						<span class="b">
							<a href="javascript:void(0)">&lt;上一张</a>
						</span>
					</li>
					<li class="current">
						<span class="t">
							<a href="javascript:void(0)">
								<img src="${contextPath}/image/transparent.gif"
									style="background: url('${contextPath}/photo.jpg?fileId=${photo.attachment.pkId}&width=50&height=50')
									center center">
							</a>
						</span>
						<span class="b">当前</span>
					</li>
					<li>
						<span class="t">
							<a id="nextimg" href="javascript:void(0)">
								<img src="${contextPath}/image/transparent.gif" style="">
							</a>
						</span>
						<span class="b">
							<a href="javascript:void(0)">下一张&gt;</a>
						</span>
					</li>
				</ul>
			</div>
		</div>
	</div>
</div>