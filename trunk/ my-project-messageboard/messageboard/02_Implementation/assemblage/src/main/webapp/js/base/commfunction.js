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

function showUser(color,width,wrap){
	$(".showUser span").colorTip({color:color,width:width,wrap:wrap});
}
