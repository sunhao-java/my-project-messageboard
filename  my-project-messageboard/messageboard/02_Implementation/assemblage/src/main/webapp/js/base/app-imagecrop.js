/**
 * 封装YUI imagecropper,图片裁剪组件
 * User: sunhao
 * Date: 2012-3-23
 * Time: 下午6:39
 * @author sunhao(sunhao.java@gmail.com)
 */
YAHOO.namespace("app.imagecrop");
var contextPath;

YAHOO.app.imagecrop = function(p){
    var location = window.location;
    contextPath = "/" + location.pathname.split("/")[1];

    var f = {
        init : function(args){
            p.title = args.title || '上传头像',
            p.handles = args.handles || [all],                      //控制哪些方向出现可以移动的方框,default:[all]
            p.status = args.status || false,                        //用来决定右下角输出当前resize区域的大小和坐标,default:false
            p.minWidth = args.minWidth || 50,                       //裁减框最小宽度,default:50
            p.minHeight = args.minHeight || 50,                     //裁减框最小高度,default:50
            p.ratio = args.ratio || false,                          //是否按比例,default:false
            p.initHeight  = args.initHeight  || 100,                //初始裁减框高度,default:100
            p.initWidth = args.initWidth || 100,                    //初始裁减框宽度,default:100
            p.initialXY = args.initialXY  || [0, 0],                //裁减框初始位置，default:[0, 0]
            p.keyTick = args.keyTick
        }
    }
}