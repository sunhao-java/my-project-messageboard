/**
 * 添加书签
 */
function addBookmark(){
	var title = "孙昊的幸福家园留言板";
    var url = top.location.href;
    addFavorite(url,title);
}

function addFavorite(url,title) {
    try{
        window.external.addFavorite(url, title);
    }catch (e){
        try{
            window.sidebar.addPanel(title, url, "");
        }catch (e){
            alert("加入收藏失败，请使用Ctrl+D进行添加");
        }
    }
}

/**
 * 登出系统
 */
function logout(contextPath){
	var $C = YAHOO.util.Connect,dom = YAHOO.util.Dom,event = YAHOO.util.Event;
	var requestURL = contextPath + '/user/logout.do';
	$C.asyncRequest("POST", requestURL, {
		success : function(o){
			var _e = eval("(" + o.responseText + ")");
			if(_e.status == 1){
				parent.location.href = contextPath + '/user/inLogin.do';
			}
		}
	});
}

/**
 * 链接动作
 */
function linkFun(targetUrl){
	parent.frames['main'].location.href = targetUrl;
}

/**
 * 自动提示组件用
 * @param {Object} color
 * @param {Object} width
 * @param {Object} wrap
 */
function showUser(color,width,wrap){
	$(".showUser span").colorTip({color:color,width:width,wrap:wrap});
}

/**
 * 全部选中的方法
 * @param {Object} selallid
 * @param {Object} listcheckboxname
 */
function selectAll(selallid, listcheckboxname){
    var selAll = document.getElementById(selallid);
    var PIds = document.getElementsByName(listcheckboxname);
    var PIdLength = PIds.length;
    if(selAll.checked){
        for(var i=0;i<PIdLength;i++){
            PIds[i].checked = true;
        }
    }else{
        for(var i=0;i<PIdLength;i++){
            PIds[i].checked = false;
        }
    }
}

/**
 * 批量删除的方法
 * @param {Object} boxId		ckechBox框的ID
 * @param {Object} requestURL	请求URL
 * @param {Object} responseURL	响应URL
 */
function deleteMore(boxId, requestURL, responseURL){
	var dataString = '';
	var boxes = document.getElementsByName(boxId);
	var boxesLength = boxes.length;
	for(var i = 0; i < boxesLength; i++){
		if(boxes[i].checked){
			dataString += boxes[i].value + ',';
		}
	}
	
	if(dataString == '' || dataString.length == 0){
		YAHOO.app.dialog.pop({'dialogHead':'提示','cancelButton':'false','alertMsg':'请选择需要删除的行！'});
		return;
	} else {
		YAHOO.app.dialog.pop({'dialogHead':'提示','alertMsg':'你确定要删除所选中的项吗？',
			'confirmFunction':function(){
				if(requestURL.indexOf('?') == -1){
					requestURL += '?';
				}
				requestURL += 'pkIds=' + dataString;
				$C.asyncRequest('POST', requestURL, {
					success : function(o){
						var _e = eval("(" + o.responseText + ")");
						if(_e.status == '1'){
							YAHOO.app.dialog.pop({'dialogHead':'提示','cancelButton':'false','alertMsg':'删除成功！',
								'confirmFunction':function(){
									window.location.href = responseURL;
								}});
						} else if(_e.status == '0'){
							YAHOO.app.dialog.pop({'dialogHead':'提示','cancelButton':'false','alertMsg':'删除失败！'});
						}
					},
					failure : function(o){
						YAHOO.app.dialog.pop({'dialogHead':'提示','cancelButton':'false','alertMsg':'错误代码:' + o.status});
					}
				});
			}});
	}
}

/**
 * 删除单条
 * @param {Object} requestURL 	请求URL
 * @param {Object} responseURL	响应URL
 * @param {Object} refresh		是否刷新
 */
function deleteOne(requestURL, responseURL, refresh){
	if(refresh == 'true'){
		refresh = true;
	} else if(refresh == 'false') {
		refresh = false;
	}
	YAHOO.app.dialog.pop({'dialogHead':'提示','alertMsg':'你确定要要删除选中的项吗？','confirmFunction':function(){
		$C.asyncRequest('POST', requestURL, {
			success : function(o){
				var _e = eval("(" + o.responseText + ")");
				if(_e.status == '1'){
					YAHOO.app.dialog.pop({'dialogHead':'提示','cancelButton':'false','alertMsg':'删除成功！',
						'confirmFunction':function(){
							if(refresh){
								window.location.reload(true);
							} else {
								window.location.href = responseURL;
							}
						}});
				} else {
					YAHOO.app.dialog.pop({'dialogHead':'提示','cancelButton':'false','alertMsg':'删除失败！'});
				}
			}, 
			failure : function(o){
				YAHOO.app.dialog.pop({'dialogHead':'提示','cancelButton':'false','alertMsg':'错误代码:' + o.status});
			}
		});
	}});
}