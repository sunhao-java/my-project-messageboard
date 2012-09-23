<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/includes.jsp"%>

<msg:css href="css/base/pagination.css"/>

<%--
<c:if test="${paginationSupport.pageSize > 1}">
	<div class="paginationDiv">
		<c:if test="${paginationSupport.currentIndex ne 1}">
			<c:url var="url" value="${paginationAction }">
				<c:param name="page" value="${paginationSupport.startIndex }"/>
				<c:param name="num" value="${paginationSupport.num}"/>
			</c:url>
			<a href="${contextPath}/${url}">首页</a>
			<c:url var="url" value="${paginationAction }">
				<c:param name="page" value="${paginationSupport.previousIndex }"/>
				<c:param name="num" value="${paginationSupport.num}"/>
			</c:url>
			<a href="${contextPath}/${url}">&lt;上一页</a>
		</c:if>
		<c:forEach begin="${paginationSupport.startIndexOnShow}" end="${paginationSupport.endIndexOnShow}" step="1" var="page" varStatus="status">
			<c:url var="url" value="${paginationAction }">
				<c:param name="page" value="${page }"/>
				<c:param name="num" value="${paginationSupport.num}"/>
			</c:url>
			<c:choose>
				<c:when test="${paginationSupport.currentIndex eq page}">
					<strong>${page}</strong>
				</c:when>
				<c:otherwise>
					<a href="${contextPath}/${url}">${page}</a>
				</c:otherwise>
			</c:choose>
		</c:forEach>
		<c:if test="${paginationSupport.currentIndex ne paginationSupport.pageSize}">
			<c:url var="url" value="${paginationAction }">
				<c:param name="page" value="${paginationSupport.nextIndex }"/>
				<c:param name="num" value="${paginationSupport.num}"/>
			</c:url>
			<a href="${contextPath}/${url}">下一页&gt;</a>
			<c:url var="url" value="${paginationAction }">
				<c:param name="page" value="${paginationSupport.endIndex }"/>
				<c:param name="num" value="${paginationSupport.num}"/>
			</c:url>
			<a href="${contextPath}/${url}">尾页</a>
		</c:if>
	</div>
</c:if>
--%>

<script type="text/javascript">
    function jump(url){
        window.location.href = '${contextPath}/' + encodeURI(url);
    }

    function reload(){
        window.location.reload(true);
    }

    function changeNum(num){
        if('${paginationSupport.num}' == num){
            return false;
        }
        var url = '${paginationAction}';
        var page = '${paginationSupport.startIndex }';
        if(url.lastIndexOf('?') < 0){
            url = url + '?a=1';
        }
        url = url + '&page=' + page + '&num=' + num;
        jump(url);
    }
</script>


    <div class="l-panel-bar">
        <div class="l-panel-bbar-inner">
            <div class="l-bar-group l-bar-selectpagesize">
            	每页显示条数：
                <select name="rp" class="l-hidden" onchange="changeNum(this.value);">
                    <option value="5" <c:if test="${paginationSupport.num eq 5}">selected="selected"</c:if>>5</option>
                    <option value="10" <c:if test="${paginationSupport.num eq 10}">selected="selected"</c:if>>10</option>
                    <option value="20" <c:if test="${paginationSupport.num eq 20}">selected="selected"</c:if>>20</option>
                    <option value="30" <c:if test="${paginationSupport.num eq 30}">selected="selected"</c:if>>30</option>
                    <option value="40" <c:if test="${paginationSupport.num eq 40}">selected="selected"</c:if>>40</option>
                    <option value="50" <c:if test="${paginationSupport.num eq 50}">selected="selected"</c:if>>50</option>
                </select>
                <div class="l-text l-text-combobox" style="width: 45px;">
                    <div class="l-text-l"></div>
                    <div class="l-text-r"></div>
                    <div class="l-trigger">
                        <div class="l-trigger-icon"></div>
                    </div>
                </div>
                <div class="l-resizable"></div>
                <div direction="se" class="l-resizable-f-r"></div>
                <div direction="s" class="l-resizable-f-c" style="width: 45px;"></div>
                <div class="l-btn-nw-drop"></div>
                </div>
            </div>
            <div class="l-bar-separator"></div>
            <div class="l-bar-group">
                <div class="l-bar-button l-bar-btnfirst" title="首页">
                    <c:if test="${paginationSupport.currentIndex ne 1}">
                        <c:url var="url" value="${paginationAction }">
                            <c:param name="page" value="${paginationSupport.startIndex }"/>
                            <c:param name="num" value="${paginationSupport.num}"/>
                        </c:url>
                    </c:if>
                    <span <c:if test="${paginationSupport.currentIndex eq 1}">class="l-disabled"</c:if>
                            <c:if test="${paginationSupport.currentIndex ne 1}"> onclick="jump('${url}');return false;" </c:if>></span>
                </div>
                <div class="l-bar-button l-bar-btnprev" title="上一页">
                    <c:if test="${paginationSupport.currentIndex ne 1}">
                        <c:url var="url" value="${paginationAction }">
                            <c:param name="page" value="${paginationSupport.previousIndex }"/>
                            <c:param name="num" value="${paginationSupport.num}"/>
                        </c:url>
                    </c:if>
                    <span <c:if test="${paginationSupport.currentIndex eq 1}">class="l-disabled"</c:if>
                            <c:if test="${paginationSupport.currentIndex ne 1}"> onclick="jump('${url}');return false;" </c:if>></span>
                </div>
            </div>
            <div class="l-bar-separator"></div>
            <div class="l-bar-group">
                <span class="pcontrol">
                    <c:forEach begin="${paginationSupport.startIndexOnShow}" end="${paginationSupport.endIndexOnShow}"
                               step="1" var="page" varStatus="status">
                        <c:url var="url" value="${paginationAction }">
                            <c:param name="page" value="${page }"/>
                            <c:param name="num" value="${paginationSupport.num}"/>
                        </c:url>
                        <c:choose>
                            <c:when test="${paginationSupport.currentIndex eq page}">
                                <strong>${page}</strong>
                            </c:when>
                            <c:otherwise>
                                <a href="${contextPath}/${url}">${page}</a>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                </span>
            </div>
            <div class="l-bar-separator"></div>
            <div class="l-bar-group">
                <div class="l-bar-button l-bar-btnnext" title="下一页">
                    <c:if test="${paginationSupport.currentIndex ne paginationSupport.pageSize}">
                        <c:url var="url" value="${paginationAction }">
                            <c:param name="page" value="${paginationSupport.nextIndex }"/>
                            <c:param name="num" value="${paginationSupport.num}"/>
                        </c:url>
                    </c:if>
                    <span <c:if test="${paginationSupport.currentIndex eq paginationSupport.pageSize}">class="l-disabled"</c:if>
                            <c:if test="${paginationSupport.currentIndex ne paginationSupport.pageSize}">
                                onclick="jump('${url}');return false;" </c:if>></span>
                </div>
                <div class="l-bar-button l-bar-btnlast" title="尾页">
                    <c:if test="${paginationSupport.currentIndex ne paginationSupport.pageSize}">
                        <c:url var="url" value="${paginationAction }">
                            <c:param name="page" value="${paginationSupport.endIndex }"/>
                            <c:param name="num" value="${paginationSupport.num}"/>
                        </c:url>
                    </c:if>
                    <span <c:if test="${paginationSupport.currentIndex eq paginationSupport.pageSize}">class="l-disabled"</c:if>
                            <c:if test="${paginationSupport.currentIndex ne paginationSupport.pageSize}">
                                onclick="jump('${url}');return false;" </c:if>></span>
                </div>
            </div>
            <div class="l-bar-separator"></div>
            <div class="l-bar-group">
                <div class="l-bar-button l-bar-btnload" title="刷新">
                    <span class="" onclick="reload();return false;"></span>
                </div>
            </div>
            <div class="l-bar-separator"></div>
            <div class="l-bar-group l-bar-right">
                <span class="l-bar-text">总共 ${paginationSupport.pageSize} 页 ，共 ${paginationSupport.totalRow} 条记录 ，每页显示记录数：${paginationSupport.num}条</span>
            </div>
            <div class="l-clear"></div>
        </div>
    </div>
