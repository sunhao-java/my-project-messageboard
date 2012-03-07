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

    <!--[if lte IE 6]>
        <msg:js src="/js/base/ie-pngbug.js"/>
    <![endif]-->
	
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
            <c:forEach items="${menus}" var="menu">
                <c:set value="${menu.children}" var="children"/>
                
                <table width="120%" cellpadding="0" cellspacing="0" id="menu${menu.pkId}" flag="1">
                    <tr onclick="OpenMenu('menu${menu.pkId}');">
                        <td colspan="2" style="cursor:pointer" class="tabheader">
                            &nbsp;&nbsp;<span style="font-size: 13px;">${menu.menuName}</span>
                        </td>
                    </tr>
                    <c:forEach items="${children}" var="child">
                        <tr style="cursor:pointer;" onmouseover="javascript:this.bgColor='#f7e982'"
                                onmouseout="javascript:this.bgColor=''">
			     			<td align="center">
			     				<table width="100%" cellpadding="0" cellspacing="4">
                                    <tr>
                                        <td align="left" style="" onclick="javascript:nav('${child.menuUrl}');">
                                            <a href="#">
                                                &nbsp;&nbsp;<img src="${contextPath}/${child.menuIcon}"/>
                                                <span style="font-size: 12px;">${child.menuName}</span>
                                            </a>
                                        </td>
                                    </tr>
			     				</table>
			     			</td>
			     		</tr>
                    </c:forEach>
                </table>
            </c:forEach>
	     </div>       
	</div>
  </body>
</html>
