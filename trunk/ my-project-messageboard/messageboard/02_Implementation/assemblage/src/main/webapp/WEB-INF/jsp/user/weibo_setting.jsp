<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/includes.jsp"%>

<!--
* 个人主页
* Developed By: sunhao
* Mail: sunhao.java@gmail.com
* Time: 12-8-16 下午10:04
* Version:1.0
* History:
-->

<msg:js src="js/jquery/jquery-1.4.2.min.js"/>
<msg:js src="js/jquery/easyloader.js"/>
<msg:js src="js/base/base.js"/>
<msg:js src="js/base/app-dialog.js"/>

<script type="text/javascript">
	$(document).ready(function(){
		$('#submitCode').click(function(){
			var weiboType = $('#weiboType').val();
			var weiboCode = $('#weiboCode').val();
			
			if(weiboCode == '' || weiboType == '') {
				YAHOO.app.dialog.pop({
					dialogHead: '提示',
					cancelButton: false,
					alertMsg: '微博秀链接不可为空！'
				});
				return false;
			} else {
				var weiboUrl = $(weiboCode).attr('src');
				if(!weiboUrl){
					YAHOO.app.dialog.pop({
						dialogHead: '提示',
						cancelButton: false,
						alertMsg: '微博秀链接参数不合法！'
					});
					return false;
				}
				
				var data = '';
				if(weiboType == 1) {
					var uid = YAHOO.util.getParam(weiboUrl, 'uid'), verifier = YAHOO.util.getParam(weiboUrl, 'verifier');
					data = 'weiboType=' + weiboType + '&uid=' + uid + '&verifier=' + verifier;
				} else {
					var uid = YAHOO.util.getParam(weiboUrl, 'n');
					data = 'weiboType=' + weiboType + '&uid=' + uid;
				}
					
				
				var url = '${contextPath}/user/saveWeibo.do';
				$.ajax({
					url: url,
					type: 'post',
					dataType: 'json',
					data: data,
					success: function(o){
						if(o.status == 1){
							YAHOO.app.dialog.pop({
								dialogHead: '提示',
								cancelButton: false,
								alertMsg: '微博秀设置成功！',
								autoClose: 2,
								icon: 'infoicon'
							});
						} else {
							YAHOO.app.dialog.pop({
								dialogHead: '提示',
								cancelButton: false,
								alertMsg: '微博秀设置失败！',
								autoClose: 2,
								icon: 'warnicon'
							});
						}
					},
					error: function(o){
						YAHOO.app.dialog.pop({
							dialogHead: '提示',
							cancelButton: false,
							alertMsg: '微博秀设置失败！可能是网络问题！',
							autoClose: 2,
							icon: 'warnicon'
						});
					}
				});
			}
		});
	});
	
	function sinaShow(){
		var link = $('#weibo-link');
		link.html('新浪微博秀');
		link.attr('href', 'http://weibo.com/plugins/WeiboShow.php#input_copyhtml');
		$('#weibo-tip').attr('src', '${contextPath}/image/sina_show_tip.png');
		$('#weiboType').val('1');
		$('#weibo').show();
	}
	
	function tencentShow(){
		var link = $('#weibo-link');
		link.html('腾讯微博秀');
		link.attr('href', 'http://dev.open.t.qq.com/websites/show?explain=3');
		$('#weibo-tip').attr('src', '${contextPath}/image/tencent_show_tip.png');
		$('#weiboType').val('2');
		$('#weibo').show();
	}
</script>

<style type="text/css">
	.content01{
		min-height: 400px;
	}
	
	.weibo{
		padding: 10px 15px;
		margin: 10px 0 10px 30px;
		border-width: 0px;
	}
	
	.weibo-type{
		background-color: #F2FFF4;
	}
	
	.weibo-setting{
		background-color: #EEEEEE;
	}
	
	.weibo-show{
		padding: 0 15px;
		margin: 10px 0 10px 30px;
		line-height: 50px;
		background-color: #EEEEEE;
	}
</style>

<jsp:include page="/WEB-INF/jsp/base/head.jsp">
	<jsp:param value="修改我的信息" name="title"/>
</jsp:include>

<div class="content01">
	<jsp:include page="user_edit_head.jsp"/>
	
	<div class="weibo weibo-type">
		<table style="width: 100%">
			<tr>
				<th width="10%">
					<span style="float:left;font-weight:bold;font-size: 16px;color: #333;">选择微博秀:</span>
				</th>
				<th align="left">
					<a href="javascript:sinaShow();" id="add_sina_show" style="text-decoration:none;">
						<img src="${contextPath}/image/sina_weibo.png">新浪微博秀
					</a>
					&nbsp;&nbsp;&nbsp;
					<a href="javascript:tencentShow();" id="add_tencent_show" style="text-decoration:none;">
						<img src="${contextPath}/image/tencent_weibo.png">腾讯微博秀
					</a>
				</th>
			</tr>
		</table>
	</div>
	
	<div id="weibo" class="weibo weibo-setting" style="display: none;">
		<div>
			<span style="float:left;font-weight:bold;font-size: 16px;color: #333;">
				添加微博秀：
			</span>
			&nbsp;&nbsp;&nbsp;添加“微博秀”嵌入代码：去“
			<a target="_blank" id="weibo-link" href="" style="font-weight:bold;"></a>
			”复制嵌入代码(如下图)
            <form id="weibo_show_form">
            	<input type="hidden" name="weiboType" id="weiboType"/>
				<div style="margin: 10px 0px;">
					<img id="weibo-tip" style="float:right;margin-right: 300px;" src="">
					<textarea id="weiboCode" name="code" style="height: 160px;width: 400px;padding: 3px;"></textarea>
					<input type="button" id="submitCode" value="提交" style="margin-top: 5px;" class="f-button">
            		<div class="clear"></div>
				</div>
            </form>
			<span style="margin-top: 5px;">提示：每个用户只能展示一种微博秀，添加新的微博秀会将原来的微博秀覆盖。</span>
		</div>
	</div>
	
	<div class="weibo weibo-show">
		<span style="float:left;font-weight:bold;font-size: 16px;color: #333;">
			我的微博秀：
		</span>
		 显示效果
		<c:if test="${not empty loginUser.weiboUrl}">
		 	(<span style="color: green">已启用<c:if test="${loginUser.weiboType eq 1}">新浪</c:if><c:if test="${loginUser.weiboType eq 2}">腾讯</c:if>微博秀</span>)
		 	<span>&nbsp;&nbsp;<input id="useBtn" type="button" onclick="useWeiboshow();" 
		 		value="移除“<c:if test="${loginUser.weiboType eq 1}">新浪</c:if><c:if test="${loginUser.weiboType eq 2}">腾讯</c:if>微博秀”"></span>
		 	<div class="" id="weibo_show_display">
		 		<iframe width="220" height="410" class="share_self" 
		 			frameborder="0" scrolling="no" src="${loginUser.weiboUrl}"></iframe>
			</div>
		</c:if>
	</div>
</div>