<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!--
* 站内信收件箱
* Developed By: sunhao
* Mail: sunhao.java@gmail.com
* Time: 12-10-24 下午11:22
* Version:1.0
* History:
-->

<%@ include file="/WEB-INF/jsp/common/includes.jsp"%>
<msg:css href="css/letter.css"/>

<script type="text/javascript">

</script>

<jsp:include page="/WEB-INF/jsp/base/head.jsp">
	<jsp:param value="收件箱" name="title"/>
</jsp:include>

<div class="content01">
	<jsp:include page="letter_head.jsp"/>
	
	<p class="bar">
		8 封站内信
    </p>
	<div class="info" style="height: 25px; border-top: 1px solid #ccc; padding:10px 15px 0px;background: none repeat scroll 0 0 whiteSmoke" id="ctrlbar">
		<span style="float:left"> 
			<a href="javaScript:selAll(1);">全选 </a>&nbsp;|&nbsp; 
			<a href="javaScript:selAll(0);">取消全选 </a>&nbsp;|&nbsp; 
			<a id="batchDelbtn" href="javaScript:void(0);">删除</a>&nbsp;|&nbsp;
			<a href="javaScript:setRead();">设置为已读</a> &nbsp;|&nbsp;
         	<a href="javaScript:setUnRead();">设置为未读</a>
         </span>
		<span style="float:right"> 
			<a href="/message/inbox.do?r=1">已读信件 </a> &nbsp;|&nbsp; 
			<a href="/message/inbox.do?r=0">未读信件 </a> 
		</span>
	</div>

	<div id="pmsg" class="ui-t2" style="border: none;">
		<div class="pm-bd">
			<table id="pmlist">
				<tr class="new">
					<td class="sel">
						&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="checkBox" name="messageIdchk" value="6664">
					</td>
					<td class="ava">
						<a href="/euser/profile.do?uid=2000568"> 
							<msg:head userId="41" headType="2"/>
						</a>
					</td>
					<td class="per">
						来自：
						<a href="/euser/profile.do?uid=2000568"> 
							<span class="online_width" title="06303">06303</span> 
						</a> &nbsp;
						<span class="time"> 01-03 18:24 </span>
					</td>
					<td class="title">
						<a href="/message/show.do?message.id=6664">作业硕政课程论文请在2011-12-31前完成</a>
						<a href="/message/show.do?message.id=6664" class="summary">
							... 
						</a>
					</td>
					<td class="act">
						<a class="pm-del"
							rel="/message/deleteAjax.do?messageId=6664&amp;receiveFlag=true"
							href="javascript:void(0)">删除 </a>
					</td>
				</tr>
				<tr class="">
					<td class="sel">
						&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="checkBox" name="messageIdchk" value="6771">
					</td>
					<td class="ava">
						<a href="/euser/profile.do?uid=2000568">
							<msg:head userId="41" headType="2"/>
						</a>
					</td>
					<td class="per">
						来自：
						<a href="/euser/profile.do?uid=2000568"> 
							<span class="online_width" title="06303">06303</span> 
						</a> &nbsp;
						<span class="time"> 01-01 12:40 </span>
					</td>
					<td class="title">
						<a href="/message/show.do?message.id=6771">作业硕政五班课程作业请在2012-01-08前完成</a>
						<a href="/message/show.do?message.id=6771" class="summary">
							... 
						</a>
					</td>
					<td class="act">
						<a class="pm-del"
							rel="/message/deleteAjax.do?messageId=6771&amp;receiveFlag=true"
							href="javascript:void(0)">删除 </a>
					</td>
				</tr>
			</table>
		</div>
	</div>
</div>