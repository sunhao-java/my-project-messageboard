/**
 * message的JS工具类
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
}