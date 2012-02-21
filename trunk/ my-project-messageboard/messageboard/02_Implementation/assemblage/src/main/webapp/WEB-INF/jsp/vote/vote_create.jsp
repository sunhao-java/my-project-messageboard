<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/includes.jsp"%>
<%@ include file="/WEB-INF/jsp/common/common_js.jsp" %>

<msg:css href="css/vote.css"/>

<msg:js src="js/jquery/jquery-1.4.2.min.js"/>
<msg:js src="js/jquery/jquery.easyui.min.js"/>
<msg:css href="themes/default/easyui.css"/>
<msg:css href="themes/icon.css"/>

<msg:js src="js/base/calendar-min.js"/>
<msg:js src="js/base/app-calendar.js"/>
<msg:css href="css/base/app-calendar.css"/>

<msg:js src="js/base/app-dialog.js"/>

<script type="text/javascript">
	var $C = YAHOO.util.Connect,dom = YAHOO.util.Dom,event = YAHOO.util.Event;
	var num = 5;
	$(document).ready(function(){
		//初始化时间选择组件
	    YAHOO.app.calendar.simpleInit({'id':'endTime', 'minTime': new Date()});
		
		$("#selectMore").click(function(){
			dom.get('multi-max-option').style.visibility = '';
		});
		$("#selectOne").click(function(){
			dom.get('multi-max-option').style.visibility = 'hidden';
			dom.get('maxOption').value = '';
		});
		$("#setEndTime").click(function(){
			var checked = dom.get('setEndTime').checked;
			if(checked){
				dom.get('endTimeSpan').style.visibility = '';
			} else {
				dom.get('endTimeSpan').style.visibility = 'hidden';
				dom.get('endTime').value = '';
			}
		});
		$("#vote-addmoreitem").click(function(){
			var tr = dom.getAncestorByTagName(this, "tr"),ntr = dom.getNextSibling(tr);
            for (var i = 0; i < 5; ++i) {
                num += 1;
                var _13 = tr.cloneNode(true),_14 = _13.getElementsByTagName("a")[0];
                _14.parentNode.removeChild(_14);
                _13.getElementsByTagName("th")[0].innerHTML = "选项 "+ num + " ： ";
                _13.getElementsByTagName("input")[0].value = "";
                dom.insertBefore(_13, ntr);
                if (i == 4) {
                    _13.getElementsByTagName("td")[0].appendChild(this);
                }
            }
            if (num == 20) {
                this.style.display = "none";
            }
		});
	});
	
	function createVote(){
		var flag = checkVote();
		if(flag){
			var requestURL = "${contextPath}/vote/saveVote.do";
			var responseURL = '${contextPath}/vote/listVote.do';
			var voteFrm = dom.get('voteFrm');
			$C.setForm(voteFrm);
			$C.asyncRequest("POST",requestURL,{
				success : function(o){
					var _e = eval("(" + o.responseText + ")");
					if(_e.status == 1){
						YAHOO.app.dialog.pop({'dialogHead':'提示','cancelButton':'false','alertMsg':'发起投票成功！',
								'confirmFunction':function(){
									window.location.href = responseURL;
								}});
					} else {
						YAHOO.app.dialog.pop({'dialogHead':'提示','cancelButton':'false','alertMsg':'发起投票失败！'});
					}
				},
				failure : function(o){
					YAHOO.app.dialog.pop({'dialogHead':'提示','cancelButton':'false','alertMsg':'错误代码:' + o.status});
				}
			});
		}
	}
	
	function checkVote(){
		var voteTitle = dom.get('question').value;
		voteTitle = voteTitle.replace(/\s+/g,"");  
		var warning = dom.get('warning');
		if(voteTitle.trim() == ''){
			if(warning == undefined){
				dom.addClass('question', 'f-warning');
				var warning = $('<span id="warning" style="display: block;color: #CC0000">此项必须填写</span>');
				$('#question').after(warning);
			}
			checkOption();
			return false;
		} else {
			if(warning != undefined){
				dom.removeClass('question', 'f-warning');
				dom.get('warning').style.display = 'none';
			}
			return true && checkOption();
		}
	}
	
	function checkOption(){
		var cho = document.getElementsByName("choice[]");
		var length = 0;
		for(var i = 0; i < cho.length; i++){
			if(cho[i].value.replace(/\s+/g, '') != ''){
				length += 1;
			}
		}
		if(length < 2){
			YAHOO.app.dialog.pop({'dialogHead':'出错啦','cancelButton':'false','alertMsg':'请至少填写两个选项。'});
			return false;
		} else {
			return true;
		}
	}
</script>

<jsp:include page="/WEB-INF/jsp/base/head.jsp">
	<jsp:param value="发起新投票" name="title"/>
</jsp:include>

<div class="content">
	<jsp:include page="vote_head.jsp"/>
	<div class="vote_create">
		<div class="vote">
			<div class="vote-content">
				<form class="form" id="voteFrm">
					<table id="vote-form-table" class="form-layout vote-newformtb">
	                    <tr class="vote-title-tr">
	                        <th>投票主题：</th>
	                        <td>
	                            <input type="text" maxlength="100" id="question" name="question" class="f-text"> 
	                            <p class="vote-newtip">填写问题，例如：你喜欢什么颜色？</p>
	                        </td>
	                    </tr>
						<tr>
							<th>
								选项 1：
							</th>
							<td>
								<input type="text" name="choice[]" maxlength="50" class="f-text">
							</td>
						</tr>
						<tr>
							<th>
								选项 2：
							</th>
							<td>
								<input type="text" name="choice[]" maxlength="50" class="f-text">
							</td>
						</tr>
						<tr>
							<th>
								选项 3：
							</th>
							<td>
								<input type="text" name="choice[]" maxlength="50" class="f-text">
							</td>
						</tr>
						<tr>
							<th>
								选项 4：
							</th>
							<td>
								<input type="text" name="choice[]" maxlength="50" class="f-text">
							</td>
						</tr>
						<tr>
							<th>
								选项 5：
							</th>
							<td>
								<input type="text" name="choice[]" maxlength="50" class="f-text">
								<a id="vote-addmoreitem" href="javascript:void(0);">增加选项</a>
							</td>
						</tr>
						<tr>
							<th>
								投票方式
							</th>
							<td>
								<p style="margin: 0 0 5px 0">
									<input type="radio" class="single-select-option" id="selectOne" checked="checked" value="1" name="type">
									<label class="single-select-option" for="selectOne" id="selectOne">
										单选
									</label>
									<input type="radio" class="multi-select-option" id="selectMore" value="2" name="type">
									<label class="multi-select-option" for="selectMore" id="selectMore">
										多选
									</label>
									<label style="visibility: hidden;" id="multi-max-option">
										最多可选择
										<input type="text" style="width: 30px;" id="maxOption" name="maxOption" class="f-text">
										项
									</label>
								</p>
								<p style="margin: 5px 0">
									<label for="setEndTime">
										<input type="checkbox" id="setEndTime" value="1" name="setEndTime">
										截止时间
									</label>
									<span style="visibility: hidden;" id="endTimeSpan">
										<input type="text" id="endTime" name="endTime"
											style="width: 90px;" class="f-text"/> 
									</span>
								</p>
							</td>
						</tr>
						<tr>
	                        <th></th>
	                        <td>
	                            <a href="javaScript:createVote();" class="easyui-linkbutton" iconCls="icon-save">
	                            	发布投票
	                            </a>
	                            <a href="javaScript:history.back(-1);" class="easyui-linkbutton" iconCls="icon-back">
	                            	返回
	                            </a>
	                        </td>
	                    </tr>
	                </table>
				</form>
			</div>
		</div>
	</div>
</div>