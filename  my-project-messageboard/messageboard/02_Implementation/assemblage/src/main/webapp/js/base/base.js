/**
 * message的JS工具类
 * User: sunhao
 * Date: 12-07-16
 * Time: 下午10:30
 * @author sunhao(sunhao.java@gmail.com)
 */
//YAHOO的命名空间
YAHOO.namespace("util");
/**
 * 获取i18n的资源内容
 * 
 * @param {Object} code		properties文件中的key
 * @param {Object} args		需要替换形似{0},{1},...的真实值
 * @return {TypeName} 
 */
YAHOO.util.getMessage = function(code, args){
	if(!YAHOO.messages){
		return code;
	}
	
	var value = YAHOO.messages[code];
	
	if(args && value && value.indexOf('{') != -1){
		for(var i = 0; i < args.length; i++){
			var replace = new String('{' + i + '}');
			value = value.replace(replace, args[i]);
		}
	}
	
	return value;
};

/**
 * 文件后缀名的MAP，以后要是增加文件类型，直接管理此处即可
 */
YAHOO.util.extTypeIconMap = {
	'avi':'avi.gif',
    'bmp':'bmp.gif',
    'dll':'dll.gif',
    'doc':'doc.gif',
    'docx':'doc.gif',
    'exe':'exe.gif',
    'gif':'png.gif',
    'htm':'htm.gif',
    'html':'htm.gif',
    'jpg':'jpg.gif',
    'jpeg':'jpg.gif',
    'mdb':'mdb.gif',
    'mp3':'mp3.gif',
    'pdf':'pdf.gif',
    'png':'png.gif',
    'ppt':'ppt.gif',
    'pptx':'ppt.gif',
    'rar':'rar.gif',
    'rm':'rm.gif',
    'rmvb':'rm.gif',
    'swf':'swf.gif',
    'txt':'txt.gif',
    'wma':'wma.gif',
    'wmv':'wmv.gif',
    'xls':'xls.gif',
    'zip':'zip.gif',
    'unknow':'unknow.gif'
}

/**
 * 根据文件名获取此格式文件的图标
 * 
 * @param {Object} filename		文件名
 * @return {TypeName} 
 */
YAHOO.util.postfixImg = function(filename){
	var extmap = YAHOO.util.extTypeIconMap;
    if(!extmap){
        return "unknow.gif";
    }

    var split = '.',ext,index = filename.lastIndexOf(split),mapKey = 'unknow';
    if(filename.indexOf(split) != -1) {
        mapKey = filename.substring(index + 1, filename.length);
    }
    ext = extmap[mapKey.toLocaleLowerCase()];
    return $L.isUndefined(ext) ? "unknow.gif" : ext;
}