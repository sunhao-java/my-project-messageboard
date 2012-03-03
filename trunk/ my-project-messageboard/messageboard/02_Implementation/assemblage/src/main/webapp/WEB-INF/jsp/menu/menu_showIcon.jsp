<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/includes.jsp"%>
<%@ include file="/WEB-INF/jsp/common/common_js.jsp" %>

<msg:css href="css/menu.css"/>

<script type="text/javascript">
	var $C = YAHOO.util.Connect,dom = YAHOO.util.Dom,event = YAHOO.util.Event;
	var icon = '';
	
	function selectIcon(num){
		for(var i = 1; i < 98; i++){
			if(dom.hasClass('a' + i, 'selected')){
				dom.removeClass('a' + i, 'selected');
				break;
			}
		}
		dom.addClass('a' + num, 'selected');
		
		if(num.length < 2){
			icon = '0' + num;
		} else {
			icon = num;
		}
	}
</script>

<div class="icon_list">
	<c:forEach begin="1" end="97" var="num">
		<c:choose>
			<c:when test="${num lt 10 }">
				<c:set value="0${num }" var="file"/>
			</c:when>
			<c:otherwise>
				<c:set value="${num }" var="file"/>
			</c:otherwise>
		</c:choose>
		<a href="javaScript:selectIcon('${num }');" id="a${num }">
			<img src="${contextPath }/image/icon/icon_${file}.png" height="20" width="20" id="img${num }">
		</a>
	</c:forEach>
</div>