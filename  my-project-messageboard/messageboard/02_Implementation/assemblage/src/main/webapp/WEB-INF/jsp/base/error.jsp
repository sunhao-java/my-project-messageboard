<%@ page language="java" import="java.util.*,java.io.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/includes.jsp"%>
<%@ include file="/WEB-INF/jsp/common/common_js.jsp"%>

<msg:css href="css/errorpage.css" />

<div class="package" id="page-body">
	<div class="package" id="hatrix-nav">
		<div class="results-container">
			<table cellspacing="0" cellpadding="0" border="0"
				style="z-index: 1350; visibility: visible; margin-left: 12px; margin-top: 12px"
				class="boxy-wrapper">
				<tbody>
					<tr>
						<td class="top-left"></td>
						<td class="top"></td>
						<td class="top-right"></td>
					</tr>
					<tr>
						<td class="left"></td>
						<td class="boxy-inner">
							<div style="background: #BB0000; color: #FFF;" class="title-bar">
								<h2>呃哦！出错啦！</h2>
							</div>
							<div class="content">
								<div class="boxy-content" style="display: block;">
									<p class="note">
										<strong>Error:</strong>
									</p>
									<p class="note tip">
										错误详细信息描述
									</p>
									<p class="note error">
										${exception }
									</p>
								</div>
							</div>
							<div style="background: #F2F2F2; padding: 8px; border-top: 1px solid #CCC" class="title-bar">
								<a style="color: #FFF; text-decoration: none" class="button" 
										href="javaScript:parent.window.location.href='${contextPath }'">
									返回首页
								</a>
								<a style="color: #FFF; text-decoration: none" class="button" 
										href="javaScript:history.back(-1)">
									返回上一页
								</a>
							</div>
						</td>
						<td class="right"></td>
					</tr>
					<tr>
						<td class="bottom-left"></td>
						<td class="bottom-center"></td>
						<td class="bottom-right"></td>
					</tr>
				</tbody>
			</table>
		</div>

	</div>
</div>

<p style="display: none">
    <%
        Exception ex = (Exception) request.getAttribute("ex");
        ex.printStackTrace(new java.io.PrintWriter(out));
    %>
</p>