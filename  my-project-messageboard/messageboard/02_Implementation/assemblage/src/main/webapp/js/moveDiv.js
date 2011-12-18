var dragapproved = false
var minrestore = 0
//该变量表示窗口目前的状态，0表示初始化状态，1表示最大化状态
var initialwidth, initialheight
//若Client浏览器为IE5.0以上版本的
var ie5 = document.all && document.getElementById
//若Client浏览器为NetsCape6。0版本以上的
var ns6 = document.getElementById && !document.all

function iecompattest() {
	return (!window.opera && document.compatMode && document.compatMode != "BackCompat") ? document.documentElement
			: document.body
}

function drag_drop(e) {
	if (ie5 && dragapproved && event.button == 1) {
		document.getElementById("reply").style.left = tempx + event.clientX
				- offsetx + "px"
		document.getElementById("reply").style.top = tempy + event.clientY
				- offsety + "px"
	} else if (ns6 && dragapproved) {
		document.getElementById("reply").style.left = tempx + e.clientX
				- offsetx + "px"
		document.getElementById("reply").style.top = tempy + e.clientY
				- offsety + "px"
	}
}

function initializedrag(e) {
	offsetx = ie5 ? event.clientX : e.clientX
	offsety = ie5 ? event.clientY : e.clientY
	//document.getElementById("dwindowcontent").style.display = "none" //此句代码可不要
	tempx = parseInt(document.getElementById("reply").style.left)
	tempy = parseInt(document.getElementById("reply").style.top)

	dragapproved = true
	document.getElementById("reply").onmousemove = drag_drop
}

function loadwindow(width, height) {
	if (!ie5 && !ns6) //若不为IE或Netscpae浏览器，则使用一般的Window.open进行弹出窗口处理
	//window.open(url,"","width=width,height=height,scrollbars=1")
	{

	} else {
		document.getElementById("reply").style.display = '';
		document.getElementById("reply").style.width = initialwidth = width
				+ "px";
		document.getElementById("reply").style.height = initialheight = height
				+ "px";
		document.getElementById("reply").style.left = "300px";
		document.getElementById("reply").style.top = ns6 ? window.pageYOffset
				* 1 + 30 + "px" : iecompattest().scrollTop * 1 + 30 + "px";
		//document.getElementById("cframe").src=url
	}
}

function maximize() {
	if (minrestore == 0) {
		minrestore = 1 //maximize window
		//document.getElementById("maxname").setAttribute("src", "layout.png")
		document.getElementById("reply").style.width = ns6 ? window.innerWidth
				- 20 + "px"
				: iecompattest().clientWidth + "px"
		document.getElementById("reply").style.height = ns6 ? window.innerHeight
				- 20 + "px"
				: iecompattest().clientHeight + "px"
	} else {
		minrestore = 0 //restore window
		//document.getElementById("maxname").setAttribute("src", "layout.png")
		document.getElementById("reply").style.width = initialwidth
		document.getElementById("reply").style.height = initialheight
	}
	document.getElementById("reply").style.left = ns6 ? window.pageXOffset
			+ "px" : iecompattest().scrollLeft + "px"
	document.getElementById("reply").style.top = ns6 ? window.pageYOffset
			+ "px" : iecompattest().scrollTop + "px"
}

function closeit() {
	document.getElementById("reply").style.display = "none"

}

function stopdrag() {
	dragapproved = false;
	document.getElementById("reply").onmousemove = null;
	//document.getElementById("dwindowcontent").style.display = "" //extra
}