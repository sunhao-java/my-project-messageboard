/**
 * 添加书签
 */
function addBookmark(){
	var title = "孙昊的LOVE";
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
function logout(){
	$.messager.confirm('系统提示', '你确定要退出?', function(r){
		if (r){
			$("#main").attr('src', '/love/admin/logout');
			var module = $("ul li");
			var length = module.length;
			for(var i = 9; i < length; i++) {
				//暂时是隐藏起来，后期进行处理，最好是remove掉
				$("ul li:eq(" + i + ")").attr("style","display: none;");
			}
			$("#logout").removeAttr("href");
		}
	});
}

/**
 * 关闭窗口
 */
function closeWin(){
	window.close();
}

/**
 * 设置主页
 */
function setMainPage(main){
	var url = top.location.href;
	main.style.behavior='url(#default#homepage)';
	main.setHomePage(url)
}

/**
 * 链接动作
 */
function linkFun(url){
	$("#main").attr('src', url);
}
