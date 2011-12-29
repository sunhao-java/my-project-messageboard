var myFCKeditor = function( id, params ){
	var id = params.id || '';
	var contextPath = params.contextPath || '';
	var width = params.width || '100%';
	var toolBar = params.toolBar || 'Default';
	
	var basePath = contextPath + '/fckeditor/';
	var configPath = contextPath + '/js/fckeditor/fckconfig.js';
	
	var myFCKeditor = new FCKeditor(id);
	myFCKeditor.BasePath = basePath;
	myFCKeditor.Width = width;
	myFCKeditor.Config["CustomConfigurationsPath"] = configPath;
	myFCKeditor.ToolbarSet = toolBar;
	
	myFCKeditor.ReplaceTextarea();
}