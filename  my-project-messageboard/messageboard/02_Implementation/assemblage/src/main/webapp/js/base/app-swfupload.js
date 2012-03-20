/**
 * 封装SWFupload,上传组件
 * User: sunhao
 * Date: 12-3-17
 * Time: 上午5:44
 * @author sunhao(sunhao.java@gmail.com)
 */
YAHOO.namespace("app.swfupload");
var uploadDialog;
var $C = YAHOO.util.Connect,dom = YAHOO.util.Dom,event = YAHOO.util.Event,$L=YAHOO.lang;
var config;
var contextPath;

YAHOO.app.swfupload = function(link, element, p){
    var location = window.location;
    contextPath = "/" + location.pathname.split("/")[1];
    
    var f = {
        init : function(args){
            p.title = args.title || '上传文件',                                      //弹框显示的标题
            p.uploadUrl = args.uploadUrl || contextPath + '/upload/upload.do',      //上传路径，不能为空
            p.close = args.close || 'true',                                         //右上角是否有关闭图标，默认是true
            p.modal = args.modal || 'true',						                    //背景是否被灰化
            p.draggable = args.draggable || 'true',      			                //窗口是否能被移动
            p.fileTypeDescription = args.fileTypeDescription || '',
            p.fileTypes = args.fileTypes || '*.*',                                  //文件类型限制，多个用半角分号隔开，如*.doc;*.jpg
            p.fileSizeLimit = args.fileSizeLimit || 100 * 1024 * 1024,              //单个文件大小上限，默认100M
            p.totalUploadSize = args.totalUploadSize || 1024 * 1024 * 1024,         //总共文件上传大小上限,默认1G
            p.completeFunction = 'attachUploadComplete',                            //上传结束后执行的函数
            p.params = args.params || {},                                           //额外参数
            p.width = args.width || 620,                                            //弹框宽
            p.height = args.height || 440                                           //弹框高

            YAHOO.app.resources = {
                'element':element,
                'resourceId':p.params.resourceId,
                'resourceType':p.params.resourceType,
                'uploadId':p.params.uploadId
            }
        }, bodyHtml:function () {
            var _bodyHtml = "";
            var maxUploadSizeString = "<div class=\"uploadTips\">提示信息:可上传的单个文件最大尺寸为：1G</div>";
                    _bodyHtml += "<div name=\"upcontent\" id=\"flashContent\" style=\"display:block;overflow:hidden;height:400px;\">"
                            + f.flashHtml() + maxUploadSizeString + "</div>";
                _bodyHtml += "<div name=\"upcontent\" id=\"templateContent\" style=\"display:none;height:400px;overflow-y: scroll;\"></div>";
            return _bodyHtml;
        }, flashHtml:function () {
            var _flashHtml = new Array();
            _flashHtml.push("<object classid=\"clsid:d27cdb6e-ae6d-11cf-96b8-444553540000\"");
            _flashHtml.push("codebase=\"http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=9,0,0,0\"");
            _flashHtml.push("	width=\"100%\" height=\"100%\" id=\"file\" align=\"middle\">");
            _flashHtml.push("	<param name=\"allowScriptAccess\" value=\"sameDomain\" />");
            _flashHtml.push("	<param name=\"movie\" value=\"" + contextPath + "/js/swfupload/FlashFileUploads.swf?ver=123484\" />");
            _flashHtml.push("   <param name=\"quality\" value=\"high\" />");
            _flashHtml.push("	<param name=\"wmode\" value=\"transparent\">");
            var flashVars = "&fileTypeDescription=" + encodeURIComponent(p.fileTypeDescription) +
                    "&fileTypes=" + encodeURIComponent(p.fileTypes) +
                    "&fileSizeLimit=" + encodeURIComponent(p.fileSizeLimit) +
                    "&totalUploadSize=" + encodeURIComponent(p.totalUploadSize) +
                    "&completeFunction=" + encodeURIComponent('attachUploadComplete');
            flashVars = f.getSubmitUrl() + flashVars;
            _flashHtml.push("    <param name=\"FlashVars\" value='" + flashVars + "'>");
            _flashHtml.push("    <embed src=\"" + contextPath + "/js/swfupload/FlashFileUpload.swf?ver=123484\" " +
                    "FlashVars='" + flashVars + "'");
            _flashHtml.push(" quality=\"high\" wmode=\"transparent\" width=\"100%\" height=\"100%\" name=\"file\"");
            _flashHtml.push(" align=\"middle\" allowscriptaccess=\"sameDomain\" type=\"application/x-shockwave-flash\"");
            _flashHtml.push(" pluginspage=\"http://www.macromedia.com/go/getflashplayer\" />");
            _flashHtml.push("</object>");
            return _flashHtml.join("");
        }, getSubmitUrl:function () {
            var page = "uploadPage=" + p.uploadUrl;
            var _p = p.params;
            page += "%3FuserId=" + (_p.userId ? _p.userId : -1);
            page += "%26headImage=" + (_p.headImage ? _p.headImage : 'false');
            page += "%26resourceId=" + (_p.resourceId ? _p.resourceId : -1);
            page += "%26resourceType=" + (_p.resourceType ? _p.resourceType : -1);
            page += "%26uploadId=" + (_p.uploadId ? _p.uploadId : -1);
            return page;
        }, show : function(){
            var id = "swfupload" + new Date().getTime();
            uploadDialog = YAHOO.app.dialog.pop({
                id : id,
                dialogHead : p.title,
                confirmButton : 'false',
                cancelBtn : '关闭',
                closeIcon : p.close,
                modal : p.modal,
                draggable : p.draggable,
                alertMsg : f.bodyHtml(),
                diaWidth : p.width,
                diaHeight : p.height,
                icon : 'none'
            });
        }
    }

    YAHOO.util.Event.on(link, 'click', function(e){
        config = p;
        f.init(config)
        f.show();
    });
}

var alertDialog;
function attachUploadComplete(x) {
    //TODO @2012-03-19 23:44 by sunhao
    var res = YAHOO.app.resources;
    var element = res.element, resourceId = res.resourceId, resourceType = res.resourceType, uploadId = res.uploadId,
            resourceId = res.resourceId;
    var action = contextPath + "/upload/showUploads.do?resourceId=" + resourceId +"&resourceType=" + resourceType + "&uploadId="
            + uploadId;

    if(!resourceId)
        return;

    if('string' == typeof element){
        element = dom.get(element);
    }

    var callback = {
        success : function(o) {
            var res = eval("(" + o.responseText + ")"),uploadFiles = res.files;
            if(res.status == '1'){
                if(uploadFiles && uploadFiles.length > 0){
                    var innerHTML = "";
                    for(var i = 0; i < uploadFiles.length; i++){
                        innerHTML += uploadFiles[i].fileName + "<br/>";
                    }
                    element.innerHTML = innerHTML;
                }
            }
        },
        failure : function(o) {
            
        }
    }

    $C.asyncRequest("GET", action, callback);

    // 提示信息
    if(x) {
        x = eval("(" + x + ")");
    }else{
        x = {};
    }
    
    var files = x.totalFiles,size = x.totalSize;
    var style = 'margin: 10px 10px 0 0;font-weight: bold;padding: 0 10px;';
    var style2 = 'background: none repeat scroll 0 0 #2782D6;height: 20px;margin: 10px 10px 0;';
    var tipMsg = "<div style='" + style2 + "'></div>" +
            "<p style='" + style + "'>已经上传100%，成功上传 " + files + " 个文件,共" + size + "</p>";

    var dialogId = "dialog" + new Date().getTime();
    alertDialog = YAHOO.app.dialog.pop({
        id : dialogId,
        dialogHead : '上传成功',
        cancelButton : false,
        alertMsg : tipMsg,
        icon : 'none',
        diaWidth : 460,
        diaHeight : 150,
        closeIcon : 'false',
        confirmBtn : '继续上传',
        cancelBtn : '结束上传',
        confirmFunction:function(){
            continueUpload();
        },
        cancelFuncion:function(){
            cancelUpload();
        }
    });

}

function continueUpload(){
    alertDialog.cancel();
}

function cancelUpload(){
    uploadDialog.cancel();
    alertDialog.cancel();
    var masks = dom.getElementsByClassName('mask', 'div');
    var panels = dom.getElementsByClassName('yui-simple-dialog', 'div');
    for(var i = 0; i < masks.length; i++){
        masks[i].style.display = 'none';
    }
    for(var i = 0; i < panels.length; i++){
        panels[i].style.visibility = 'hidden';
    }
    dom.get('_yuiResizeMonitor').style.visibility = 'hidden';
}