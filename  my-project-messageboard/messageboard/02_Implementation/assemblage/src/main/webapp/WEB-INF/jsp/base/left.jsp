<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/base/includes.jsp"%>
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
			     							<td align="left" onclick="javascript:nav('')" style="">
			     								<a href="javascript:nav('/message/listMessage.do')">
			     									&nbsp;&nbsp;<img alt="" 
			     									src="${contextPath}/image/wiseduimg/module/251012_s.gif.png"/>
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
			     							<td align="left" onclick="javascript:nav('')" style="">
			     								<a href="javascript:nav('')">
			     									&nbsp;&nbsp;<img alt="" 
			     									src="${contextPath}/image/wiseduimg/module/251012_s.gif.png"/>
			     									<span style="font-size: 12px;">发表留言</span>
			     								</a>
			     							</td>
			     						</tr>
			     					</tbody>
			     				</table>
			     			</td>
			     		</tr>
			     	</tbody>
		     </table>
		     <table width="120%" cellpadding="0" cellspacing="0" id="manager" flag="1">
			     	<tbody>
			     		<tr onclick="OpenMenu('manager');">
			     			<td colspan="2" style="cursor:pointer" class="tabheader">
			     				&nbsp;&nbsp;
			     				<span style="font-size: 13px;">用户信息管理</span>
			     			</td>
			     		</tr>
			     		<tr style="cursor:pointer;" onmouseover="javascript:this.bgColor='#f7e982'" onmouseout="javascript:this.bgColor=''">
			     			<td align="center">
			     				<table width="100%" cellpadding="0" cellspacing="4">
			     					<tbody>
			     						<tr>
			     							<td align="left" onclick="javascript:nav('')" style="">
			     								<a href="javascript:nav('')">
			     									&nbsp;&nbsp;<img alt="" 
			     									src="${contextPath}/image/wiseduimg/module/251012_s.gif.png"/>
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
			     							<td align="left" onclick="javascript:nav('')" style="">
			     								<a href="javascript:nav('')">
			     									&nbsp;&nbsp;<img alt="" 
			     									src="${contextPath}/image/wiseduimg/module/251012_s.gif.png"/>
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
			     							<td align="left" onclick="javascript:nav('')" style="">
			     								<a href="javascript:nav('')">
			     									&nbsp;&nbsp;<img alt="" 
			     									src="${contextPath}/image/wiseduimg/module/251012_s.gif.png"/>
			     									<span style="font-size: 12px;">修改密码</span>
			     								</a>
			     							</td>
			     						</tr>
			     					</tbody>
			     				</table>
			     			</td>
			     		</tr>
			     	</tbody>
		     </table>
	     </div>       
	</div>
  </body>
</html>
