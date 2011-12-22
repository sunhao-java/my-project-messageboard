<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://sunhao.wiscom.com.cn/message" prefix="msg" %>
<html>
  <head>
    <title>留言板</title>
	
	<msg:js src="js/jquery/jquery-1.4.2.min.js"/>
	<msg:js src="js/jquery/jquery.easyui.min.js"/>
	
	<msg:js src="js/commfunction.js"/>
	
	<msg:css href="themes/default/easyui.css"/>
	<msg:css href="themes/icon.css"/>
	
	<msg:css href="css/publiccss.css"/>
	
	<!--[if lte IE 6]>
		<msg:css href="css/iecss.css"/>
	<![endif]-->
	
  </head>
  
  <body class="easyui-layout" style="">
    <div region="north" border="false" style="height:69px;overflow: hidden;">
    	<img src="../image/logo.jpg"/>
    	<div class="title" style=""></div>
    	<div class="tool">
            <a class="t3" title="个人首页" href="javaScript:linkFun('mainPage');"></a>
            <a class="t1" title="个性设置"></a>
            <a class="t2" title="收藏夹" href="javaScript:addBookmark();"></a>
            <a class="t4" title="系统帮助"></a>
            <a id="logout" class="t5" title="退出系统"></a>
        </div>
	</div>
	<div region="west" title="标题" style="width:150px;background-color: #F7F7F7;overflow-x:hidden;overflow-y:auto;">
		<div id="menu20">
        	<ul id="module">
				<li><a href="javaScript:linkFun('mainPage');">本站首页</a></li>
				<li><a href="javaScript:linkFun('introduceMe');">个人介绍 </a></li>
				<li><a href="javaScript:linkFun('showLetter');">一封信 </a></li>
				<li><a href="javaScript:linkFun('showYou');">美丽的女孩 </a></li>
				<li><a href="javaScript:linkFun('showBtn');">童话之心 </a></li>
				<li><a href="javaScript:linkFun('message/listMessage');">查看留言</a></li>
				<li><a href="javaScript:linkFun('message/inLyJsp');">发表留言</a></li>
				<li><a href="javaScript:linkFun('admin/inLoginJsp');">留言管理</a></li>
				<li><a href="javaScript:linkFun('down/afterDown');">源码共享</a></li>
			</ul>
		</div>
	</div>
	<div region="south" border="false" style="height:60px;background:#F7F7F7;overflow: hidden;">
		<div id="tailTab">
			<p>
				<span class="ipbackup">皖ICP备020540号</span><span class="STYLE8">
					版权所有 &copy; 2011 孙昊</span>
			</p>
			<p>
				<span class="STYLE8"> 地址： 安徽省淮南市舜耕中路168号 邮编 232001
					客户服务热线：15161472714（手机）</span>
				<span class="STYLE8"> 客服信箱：</span>
				<a target="_blank" href="mailto:sunhao0550@163.com"><strong><font
						color="#CC0000">sunhao0550@163.com</font>
				</strong>
				</a>
			</p>
		</div>
	</div>
	<div region="center" title="留言板" style="position:relative;">
		<%--<iframe src="mainPage" scrolling="auto" frameborder="0" height="100%" width="100%" id="main" src="" name="main"></iframe>--%>
		<tiles:insertAttribute name="content"/>
	</div>
  </body>
</html>