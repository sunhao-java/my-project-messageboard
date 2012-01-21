<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/includes.jsp"%>
<%
	request.setAttribute("contextPath", request.getContextPath());
%>
<html>
  <head>
    <title></title>
    <msg:js src="js/jquery/jquery-1.4.2.min.js"/>
	<msg:js src="js/jquery/jquery.easyui.min.js"/>
	
	<msg:css href="css/left.css"/>
	
	<script type="text/javascript">
		var OldOpenMenu = '';
        var OldOpenMenu3 = '';
        $(document).ready(function($){					
			InitMenu();				
		});
        function InitMenu() {

            var arrTb = document.getElementsByTagName("table");
            
            var iCount = 0;
            for (var i = 0; i < arrTb.length; i++) {
                var strFlag = arrTb[i].getAttribute("Flag");
                if (strFlag != null) {
                    if (strFlag == "1") {
                        if (iCount > 0) {
                            HideTB(arrTb[i].id);
                        }
                        else {
                            OldOpenMenu = arrTb[i].id;
                        }
                        iCount++;
                    }
                }
            }
        }
		function HideMenu3() {
            if (OldOpenMenu3 != '') {
                var obj = document.getElementById(OldOpenMenu3);
                obj.style.display = "none";
                OldOpenMenu3 = '';
            }
        }
		//隐藏以打开的第二级菜单
        function HideTB(tbID) {
            var myobj = document.getElementById(tbID);
            if (myobj.rows.length > 0) {
                for (var i = 1; i < myobj.rows.length; i++) {
                    myobj.rows[i].style.display = "none";
                }
            }
        }
		function OpenMenu3(evt,MenuCode, Flag, RowIndex) {
            //debugger;
            evt = evt?evt:(window.event ? window.event : null);
            if (OldOpenMenu3 != MenuCode) {
                HideMenu3();
            }
            var tempx = evt.clientX + document.body.scrollLeft;
            var tempy = evt.clientY + document.body.scrollTop;
            var obj = document.getElementById(MenuCode);
            if (obj == null) { return; }
            if (obj.style.display == "none") {
                obj.style.display = "";
                if (Flag == 1) {
                    obj.style.left = tempx;
                    obj.style.left = 10;
                    RowIndex = RowIndex / 2 + 1;
                }
                else {
                    RowIndex = (RowIndex + 1) / 2; //算出所点元素在第几行
                    obj.style.left = 55;
                }
                obj.style.top = GetObjTop(MenuCode, RowIndex);

                OldOpenMenu3 = MenuCode;
            }
            else {
                obj.style.display = "none";
            }
        }
		function OpenMenu(MenuCode) {
            HideMenu3();
            if (OldOpenMenu != MenuCode) {
                HideTB(OldOpenMenu);
            }

            OldOpenMenu = MenuCode;

            var myobj = document.getElementById(MenuCode);
            if (myobj.rows.length > 0) {
                for (var i = 1; i < myobj.rows.length; i++) {
                    if (myobj.rows[i].style.display == "none") {
                        myobj.rows[i].style.display = "";
                    }
                    else {
                        myobj.rows[i].style.display = "none";
                    }
                }
            }
        }
		function GetObjTop(MenuCode, RowIndex) {

            var temp = MenuCode.replace('div', 'T');
            var ParID = temp.substring(0, 4);
            var objPar = document.getElementById(ParID);
            var iTop = 0;
            iTop = iTop + objPar.offsetTop;
            if (objPar.rows.length > 0) {
                for (var i = 0; i <= RowIndex; i++) {
                    iTop = iTop + objPar.rows[i].offsetHeight;
                }
            }
            return iTop;
        }
		
		function nav(targetUrl){
	        parent.frames['main'].location.href="${contextPath}"+targetUrl;
	    }
	</script>
  </head>
  
  <body class="left">
	<div class="container" id="container">
	     <div id="trMenu" class="tabmenu">
		     <table width="120%" cellpadding="0" cellspacing="0" id="listMsg" flag="1">
			     	<tbody>
			     		<tr onclick="OpenMenu('listMsg');">
			     			<td colspan="2" style="cursor:pointer" class="tabheader">
			     				&nbsp;&nbsp;
			     				<span style="font-size: 13px;">幸福家园留言板</span>
			     			</td>
			     		</tr>
			     		<tr style="cursor:pointer;" onmouseover="javascript:this.bgColor='#f7e982'" onmouseout="javascript:this.bgColor=''">
			     			<td align="center">
			     				<table width="100%" cellpadding="0" cellspacing="4">
			     					<tbody>
			     						<tr>
			     							<td align="left" style="">
			     								<a href="javascript:nav('/message/listMessage.do')">
			     									&nbsp;&nbsp;<img src="${contextPath}/image/wiseduimg/module/251012_s.gif.png"/>
			     									<span style="font-size: 12px;">查看留言</span>
			     								</a>
			     							</td>
			     						</tr>
			     					</tbody>
			     				</table>
			     			</td>
			     		</tr>
			     		<tr style="cursor:pointer;" onmouseover="javascript:this.bgColor='#f7e982'" onmouseout="javascript:this.bgColor=''">
			     			<td align="center">
			     				<table width="100%" cellpadding="0" cellspacing="4">
			     					<tbody>
			     						<tr>
			     							<td align="left" style="">
			     								<a href="javascript:nav('/message/inPublishMessageJsp.do')">
			     									&nbsp;&nbsp;<img src="${contextPath}/image/wiseduimg/module/251012_s.gif.png"/>
			     									<span style="font-size: 12px;">发表留言</span>
			     								</a>
			     							</td>
			     						</tr>
			     					</tbody>
			     				</table>
			     			</td>
			     		</tr>
			     		<tr style="cursor:pointer;" onmouseover="javascript:this.bgColor='#f7e982'" onmouseout="javascript:this.bgColor=''">
			     			<td align="center">
			     				<table width="100%" cellpadding="0" cellspacing="4">
			     					<tbody>
			     						<tr>
			     							<td align="left" style="">
			     								<a href="javascript:nav('/message/inListMyMessageJsp.do')">
			     									&nbsp;&nbsp;<img src="${contextPath}/image/wiseduimg/module/251012_s.gif.png"/>
			     									<span style="font-size: 12px;">我的留言</span>
			     								</a>
			     							</td>
			     						</tr>
			     					</tbody>
			     				</table>
			     			</td>
			     		</tr>
			     		<c:if test="${user.isAdmin eq '1'}">
			     			<tr style="cursor:pointer;" onmouseover="javascript:this.bgColor='#f7e982'" onmouseout="javascript:this.bgColor=''">
				     			<td align="center">
				     				<table width="100%" cellpadding="0" cellspacing="4">
				     					<tbody>
				     						<tr>
				     							<td align="left" style="">
				     								<a href="javascript:nav('/message/listMessageAdmin.do')">
				     									&nbsp;&nbsp;<img src="${contextPath}/image/wiseduimg/module/251012_s.gif.png"/>
				     									<span style="font-size: 12px;">管理留言</span>
				     								</a>
				     							</td>
				     						</tr>
				     					</tbody>
				     				</table>
				     			</td>
				     		</tr>
			     		</c:if>
			     	</tbody>
		     </table>
		     <table width="120%" cellpadding="0" cellspacing="0" id="manager" flag="1">
			     	<tbody>
			     		<tr onclick="OpenMenu('manager');">
			     			<td colspan="2" style="cursor:pointer" class="tabheader">
			     				&nbsp;&nbsp;
			     				<span style="font-size: 13px;">个人信息管理</span>
			     			</td>
			     		</tr>
			     		<tr style="cursor:pointer;" onmouseover="javascript:this.bgColor='#f7e982'" onmouseout="javascript:this.bgColor=''">
			     			<td align="center">
			     				<table width="100%" cellpadding="0" cellspacing="4">
			     					<tbody>
			     						<tr>
			     							<td align="left" style="">
			     								<a href="javascript:nav('/user/userInfo.do')">
			     									&nbsp;&nbsp;<img src="${contextPath}/image/wiseduimg/module/251012_s.gif.png"/>
			     									<span style="font-size: 12px;">我的信息</span>
			     								</a>
			     							</td>
			     						</tr>
			     					</tbody>
			     				</table>
			     			</td>
			     		</tr>
			     		<tr style="cursor:pointer;" onmouseover="javascript:this.bgColor='#f7e982'" onmouseout="javascript:this.bgColor=''">
			     			<td align="center">
			     				<table width="100%" cellpadding="0" cellspacing="4">
			     					<tbody>
			     						<tr>
			     							<td align="left" style="">
			     								<a href="javascript:nav('/user/editUserInfo.do')">
			     									&nbsp;&nbsp;<img src="${contextPath}/image/wiseduimg/module/251012_s.gif.png"/>
			     									<span style="font-size: 12px;">修改信息</span>
			     								</a>
			     							</td>
			     						</tr>
			     					</tbody>
			     				</table>
			     			</td>
			     		</tr>
			     		<tr style="cursor:pointer;" onmouseover="javascript:this.bgColor='#f7e982'" onmouseout="javascript:this.bgColor=''">
			     			<td align="center">
			     				<table width="100%" cellpadding="0" cellspacing="4">
			     					<tbody>
			     						<tr>
			     							<td align="left" style="">
			     								<a href="javascript:nav('/user/changePsw.do')">
			     									&nbsp;&nbsp;<img src="${contextPath}/image/wiseduimg/module/251012_s.gif.png"/>
			     									<span style="font-size: 12px;">修改密码</span>
			     								</a>
			     							</td>
			     						</tr>
			     					</tbody>
			     				</table>
			     			</td>
			     		</tr>
			     		<tr style="cursor:pointer;" onmouseover="javascript:this.bgColor='#f7e982'" onmouseout="javascript:this.bgColor=''">
			     			<td align="center">
			     				<table width="100%" cellpadding="0" cellspacing="4">
			     					<tbody>
			     						<tr>
			     							<td align="left" style="">
			     								<a href="javascript:nav('/history/listLoginHistory.do')">
			     									&nbsp;&nbsp;<img src="${contextPath}/image/wiseduimg/module/251012_s.gif.png"/>
			     									<span style="font-size: 12px;">登录历史</span>
			     								</a>
			     							</td>
			     						</tr>
			     					</tbody>
			     				</table>
			     			</td>
			     		</tr>
			     		<tr style="cursor:pointer;" onmouseover="javascript:this.bgColor='#f7e982'" onmouseout="javascript:this.bgColor=''">
			     			<td align="center">
			     				<table width="100%" cellpadding="0" cellspacing="4">
			     					<tbody>
			     						<tr>
			     							<td align="left" style="">
			     								<a href="#">
			     									&nbsp;&nbsp;<img src="${contextPath}/image/wiseduimg/module/251012_s.gif.png"/>
			     									<span style="font-size: 12px;">隐私设置</span>
			     								</a>
			     							</td>
			     						</tr>
			     					</tbody>
			     				</table>
			     			</td>
			     		</tr>
			     	</tbody>
		     </table>
		     <c:if test="${user.isAdmin eq '1'}">
		     	<table width="120%" cellpadding="0" cellspacing="0" id="userManager" flag="1">
		     		<tr onclick="OpenMenu('userManager');">
		     			<td colspan="2" style="cursor:pointer" class="tabheader">
		     				&nbsp;&nbsp;
		     				<span style="font-size: 13px;">系统用户管理</span>
		     			</td>
		     		</tr>
		     		<tr style="cursor:pointer;" onmouseover="javascript:this.bgColor='#f7e982'" onmouseout="javascript:this.bgColor=''">
		     			<td align="center">
		     				<table width="100%" cellpadding="0" cellspacing="4">
		     					<tbody>
		     						<tr>
		     							<td align="left" style="">
		     								<a href="javascript:nav('/user/listAllUser.do')">
		     									&nbsp;&nbsp;<img src="${contextPath}/image/wiseduimg/module/251012_s.gif.png"/>
		     									<span style="font-size: 12px;">查看所有用户</span>
		     								</a>
		     							</td>
		     						</tr>
		     					</tbody>
		     				</table>
		     			</td>
		     		</tr>
		     		<tr style="cursor:pointer;" onmouseover="javascript:this.bgColor='#f7e982'" onmouseout="javascript:this.bgColor=''">
		     			<td align="center">
		     				<table width="100%" cellpadding="0" cellspacing="4">
		     					<tbody>
		     						<tr>
		     							<td align="left" style="">
		     								<a href="javascript:nav('/user/listAllUser.do?permission=true')">
		     									&nbsp;&nbsp;<img src="${contextPath}/image/wiseduimg/module/251012_s.gif.png"/>
		     									<span style="font-size: 12px;">用户权限分配</span>
		     								</a>
		     							</td>
		     						</tr>
		     					</tbody>
		     				</table>
		     			</td>
		     		</tr>
		     	</table>
		     </c:if>
		     
	     	<table width="120%" cellpadding="0" cellspacing="0" id="messageInfo" flag="1">
	     		<tr onclick="OpenMenu('messageInfo');">
	     			<td colspan="2" style="cursor:pointer" class="tabheader">
	     				&nbsp;&nbsp;
	     				<span style="font-size: 13px;">留言板管理</span>
	     			</td>
	     		</tr>
	     		<c:if test="${user.isAdmin eq '1'}">
	     		<tr style="cursor:pointer;" onmouseover="javascript:this.bgColor='#f7e982'" onmouseout="javascript:this.bgColor=''">
	     			<td align="center">
	     				<table width="100%" cellpadding="0" cellspacing="4">
	     					<tbody>
	     						<tr>
	     							<td align="left" style="">
	     								<a href="javascript:nav('/info/inViewInfoJsp.do')">
	     									&nbsp;&nbsp;<img src="${contextPath}/image/wiseduimg/module/251012_s.gif.png"/>
	     									<span style="font-size: 12px;">查看留言板信息</span>
	     								</a>
	     							</td>
	     						</tr>
	     					</tbody>
	     				</table>
	     			</td>
	     		</tr>
	     		<tr style="cursor:pointer;" onmouseover="javascript:this.bgColor='#f7e982'" onmouseout="javascript:this.bgColor=''">
	     			<td align="center">
	     				<table width="100%" cellpadding="0" cellspacing="4">
	     					<tbody>
	     						<tr>
	     							<td align="left" style="">
	     								<a href="javascript:nav('/info/inEditInfoJsp.do')">
	     									&nbsp;&nbsp;<img src="${contextPath}/image/wiseduimg/module/251012_s.gif.png"/>
	     									<span style="font-size: 12px;">编辑留言板信息</span>
	     								</a>
	     							</td>
	     						</tr>
	     					</tbody>
	     				</table>
	     			</td>
	     		</tr>
			     </c:if>
			     <tr style="cursor:pointer;" onmouseover="javascript:this.bgColor='#f7e982'" onmouseout="javascript:this.bgColor=''">
	     			<td align="center">
	     				<table width="100%" cellpadding="0" cellspacing="4">
	     					<tbody>
	     						<tr>
	     							<td align="left" style="">
	     								<a href="javascript:nav('/info/inListInfoHistoryJsp.do')">
	     									&nbsp;&nbsp;<img src="${contextPath}/image/wiseduimg/module/251012_s.gif.png"/>
	     									<span style="font-size: 12px;">描述编辑历史</span>
	     								</a>
	     							</td>
	     						</tr>
	     					</tbody>
	     				</table>
	     			</td>
	     		</tr>
	     	</table>
	     </div>       
	</div>
  </body>
</html>
