<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!--
* 写站内信
* Developed By: sunhao
* Mail: sunhao.java@gmail.com
* Time: 12-10-26 下午11:24
* Version:1.0
* History:
-->

<%@ include file="/WEB-INF/jsp/common/includes.jsp"%>
<msg:css href="css/letter.css"/>
<msg:js src="js/jquery/jquery-1.4.2.min.js"/>
<msg:js src="js/jquery/easyloader.js"/>
<msg:js src="js/jquery/jquery.form.js"/>

<script type="text/javascript">
	$(document).ready(function(){
		using(['validationengine', 'simpleTip', 'newForm'], function(){
			$('#letter-frm').formTip({
				size: '13px'
			});
			
			$("#letter-frm").validationEngine({
				promptPosition:'topLeft',
				scroll: false
			});
			
			$('#send').bind('click', function(){
				var dataFrm = $('#letter-frm');
				var flag = $.validationEngine.doValidate(dataFrm);
				if(flag){
					dataFrm.ajaxSubmit({
						url: '${contextPath}/letter/send.do',
						type: 'post',
						dataType: 'json',
						success: function(o){
							if(o.status == '1'){
								window.location.href = '${contextPath}/letter/outbox.do';
							} else {
								$('#msg-error').show();
							}
						},
						error: function(o){
							$('#msg-error').show();
						}
					});
				}
			});
			
			$('#cancel').bind('click', function(){
				history.back(-1);
			});
		});
	});
</script>

<jsp:include page="/WEB-INF/jsp/base/head.jsp">
	<jsp:param value="写站内信" name="title"/>
</jsp:include>

<div class="content01">
	<jsp:include page="letter_head.jsp"/>
	
	<div id="msg-error" class="msg-error" style="display:none">
		站内信发送失败！
	</div>
	
	<div class="pmsg-comp">
		<form action="#" method="post" id="letter-frm">
			<table>
				<tr>
                	<td>收件人：</td>
                    <td>
                    	<input class="width450" type="text" id="receiverIds" name="receiverIds" tip="请输入收件人的ID,以逗号(,)隔开!"/>
                    </td>
                    </tr>
                    <tr>
                        <td>主题：</td>
                        <td>
                        	<input type="text" name="title" id="title" value="" tip="请输入站内信的主题!" 
                        		class="validate[required,length[1,100]] width450"/>
                        </td>
                    </tr>
                    <tr>
                        <td>内容：</td>
                        <td>
                            <textarea name="content" id="content" rows="10" cols="60" tip="请输入站内信的内容!"
                            	 class="validate[required] width450"></textarea>
                        </td>
                    </tr>
                    <tr class="pmsg-comp-act">
                        <th></th>
                        <td>
                            <div>
                                <input type="button" class="f-button" value="发送" id="send"/>
                                <input type="button" class="f-button f-alt" value="取消" id="cancel"/>
                            </div>
                        </td>
                    </tr>
                </table>
            </form>
        </div>
</div>