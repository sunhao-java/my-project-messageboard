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

/**
 * 获取一个链接中参数指定key的值
 * 
 * @param {Object} href			链接
 * @param {Object} key			key
 * @return {TypeName} 
 */
YAHOO.util.getParam = function(href, key){
	if(!href)
		return '';
	
	var arrays = href.split('?');
	if(arrays.length != 2)
		return '';
	
	var params = arrays[1];
	var param = params.split('&');
	for(var p in param){
		var k = param[parseInt(p)].split('=');
		if(k[0] == key)
			return k[1];
	}
}

/**
 * 表情文件名和表情名称的MAP
 */
YAHOO.util.titles0 = ['微笑','撇嘴','色','发呆','得意','流泪','害羞','闭嘴','睡','大哭','尴尬','发怒','调皮','呲牙','惊讶','难过','酷','冷汗','抓狂','吐','偷笑','可爱','白眼','傲慢','饥饿','困','惊恐','流汗','憨笑','大兵','奋斗','咒骂','疑问','嘘','晕','折磨','衰','骷髅','敲打','再见','擦汗','抠鼻','鼓掌','糗大了','坏笑','左哼哼','右哼哼','哈欠','鄙视','委屈','快哭了','阴险','亲亲','吓','可怜','菜刀','西瓜','啤酒','篮球','乒乓','咖啡','饭','猪头','玫瑰','凋谢','示爱','爱心','心碎','蛋糕','闪电','炸弹','刀','足球','瓢虫','便便','月亮','太阳','礼物','拥抱','强','弱','握手','胜利','抱拳','勾引','拳头','差劲','爱你','NO','OK','爱情','飞吻','跳跳','发抖','怄火','转圈','磕头','回头','跳绳','挥手','激动','街舞','献吻','左太极','右太极'];
YAHOO.util.titles1 = ['可爱','开心','害羞','挤眼','色','飞吻','亲亲','发呆','思考','呲牙','调皮','赖皮','无感','奸笑','汗','惆怅','伤心','崩溃','流汗','冷汗','衰','晕','流泪','大哭','笑抽','混乱','恐怖','生气','发怒','瞌睡','感冒','魔鬼','外星人','黄心','篮心','紫心','粉心','绿心','爱心','心碎','喜欢','爱神箭','星星','金星','青筋','叹号','问号','睡觉','喷气','水滴','音符','音乐','火焰','便便','强','鄙视','OK','出拳','拳头','胜利','手掌','停','双手','向上','向下','向右','向左','不是我','拜佛','第一','鼓掌','肌肉','走路','跑步','情侣','美女','跳舞','抱头','交叉','摆手','考试','接吻','爱情','洗头','剪发','美甲','男孩','女孩','妈妈','爸爸','奶奶','爷爷','学生','小贩','农民','工人','警察','天使','公主','士兵','骷髅','脚印','吻','嘴唇','耳朵','眼睛','鼻子','太阳','下雨','多云','雪人','月亮','闪电','晕','浪花','猫咪','狗狗','灰鼠','花鼠','兔子','狼狗','青蛙','老虎','考拉','灰熊','猪头','牛','野猪','小猴','小马','骏马','骆驼','绵羊','大象','蛇','灰鸟','黄鸟','小鸡','啄木鸟','毛毛虫','章鱼','猴子','热带鱼','鱼儿','鲸','海豚','康乃馨','桃花','郁金香','四叶草','玫瑰','向日葵','鲜花','枫叶','绿叶','黄叶','椰树','仙人掌','兰花','贝壳','盆景','爱的礼物','婚礼','箱子','套装','鲤鱼旗','烟花','焰火','风铃','夜色','南瓜灯','小鬼','圣诞老人','圣诞树','礼物','铃铛','礼花','气球','光盘','CD','摄像机','电脑','电视','手机','传真','电话','唱片','磁带','声音','大喇叭','小喇叭','收音机','广播','眼镜','放大镜','开锁','锁头','钥匙','剪刀','榔头','灯泡','来电','来信','收件箱','信箱','浴缸','马桶','座椅','金钱','金冠','香烟','炸弹','手枪','药丸','针管','橄榄球','篮球','足球','棒球','网球','高尔夫','台球','游泳','冲浪','滑雪','黑桃','红心','梅花','方块','奖杯','打怪','箭靶','麻将','电影','写字','书','绘画','唱歌','听歌','喇叭','萨克斯','吉他','道具','男鞋','女鞋','高跟鞋','靴子','上衣','衬衫','连衣裙','和服','内衣','蝴蝶结','礼貌','皇冠','草帽','雨伞','男包','女包','口红','戒指','钻石','咖啡','绿茶','啤酒','对饮','鸡尾酒','米酒','刀叉','汉堡','意面','盖浇饭','盒饭','寿司','饭团','点心','米饭','面条','汤','面包','鸡蛋','关东煮','丸子','冰淇淋','刨冰','生日蛋糕','蛋糕','苹果','橙子','西瓜','草莓','茄子','番茄','房子','学校','大厦','楼房','医院','银行','便利店','爱心酒店','酒店','爱心教堂','教堂','警局','黄昏','傍晚','油罐','亭子','城堡','帐篷','烟囱','东京塔','富士山','朝阳','夕阳','流星','自由女神','彩虹','摩天轮','喷泉','过山车','游轮','快艇','帆船','飞机','火箭','单车','汽车','小车','出租车','公交车','警车','消防车','救护车','卡车','电车','火车','动车','高铁','钞票','加油站','红绿灯','警告','路障','盾牌','取款机','密码锁','路牌','理发店','温泉','黑白格旗','日本旗','日本','韩国','中国','美国','法国','西班牙','意大利','英国','德国','一','二','三','四','五','六','七','八','九','零','井号','向上','向下','向左','向右','右上','左上','右下','左下','左箭头','右箭头','快退','快进','OK','新','顶','向上','酷','摄影','这里','信号','满','空','获取','转让','手指','营业中','是','无','本月','报名','平假名','厕所','男厕','女厕','婴儿','禁烟','停车','轮椅','列车','厕所','保密','祝福','十八禁','ID','星号','标记','心','对战','手机','关机','股价','汇率','白羊','金牛','双子','巨蟹','狮子','处女','天秤','天蝎','射手','摩羯','水瓶','双鱼','图标','星座','A型','B型','AB型','O型','绿','红','紫','十二点','一点','二点','三点'];

YAHOO.util.icons = [];
	
for(var i=100, j = i + YAHOO.util.titles0.length; i < j; i++){
	YAHOO.util.icons.push({
		id: i,
		title: YAHOO.util.titles0[i-100],
		index: YAHOO.util.icons.length
	});
}
for(var i=7000, j = i + YAHOO.util.titles1.length; i < j; i++){
	YAHOO.util.icons.push({
		id: i,
		title: YAHOO.util.titles1[i-7000],
		index: YAHOO.util.icons.length
	});
}

/**
 * 根据表情的名字获取表情所在页码和表情的中文名
 * [所在页码, 中文名]
 * 
 * @param {Object} emoticonName		表情的名字
 * @return {TypeName} 
 */
YAHOO.util.getEmoticonTitle = function(emoticonName){
	var emoticons = YAHOO.util.icons;
	if(!emoticonName)
		return '';
	
	if(emoticonName.indexOf('e') == -1)
		return '';
	
	var emoticonId = emoticonName.substring(1);
	var title = '';
	var index = 1;
	
	if(parseInt(emoticonId) > 7449)
		return null;
	
	for(var i in emoticons){
		var icon = emoticons[i];
		
		if(icon.id == emoticonId){
			title = icon.title;
			index = icon.index;
			break;
		}
	}
	
	return [parseInt(index / 105) + 1, title];
}

/**
 * 判断一个对象是否在一个数组中
 * 
 * @param {Object} value		对象
 * @param {Object} array		数组
 * @return {TypeName} 
 */
YAHOO.util.contain = function(value, array){
	if(typeof(value) == 'undefined' || value == '')
		return true;
	
	if(typeof(array) == 'undefined' || array == null)
		return false;
	
	for(var a in array){
		if(value == array[a])
			return true;
	}
	
	return false;
}