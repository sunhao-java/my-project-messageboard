//单击行选中或者取消选中行前的checkbox(兼容IE.chrome.FF)
//by sunhao 2012-11-05
$(document).ready(function(){
	var t1,t2,t3,t4,t5,t6,t7,t8,t9,rows;
	t1 = $('table#listContent tr');
	t2 = $('table#tbl tr');
	t3 = $('table#typeOrderTable tr');
	t4 = $('table#scopeListT tr');
	t5 = $('table#table tr');
	t6 = $('table#adminsTable tr');
	t7 = $('table#fileListTable tr');
	t8 = $('table#datalist tr');
	t9 = $('table#filter tr');
	if(t1 != null && t1.length > 0)
		rows = t1;
	else if(t2 != null && t2.length > 0)
		rows = t2;
	else if(t3 != null && t3.length > 0)
		rows = t3;
	else if(t4 != null && t4.length > 0)
		rows = t4;
	else if(t5 != null && t5.length > 0)
		rows = t5;
	else if(t6 != null && t6.length > 0)
		rows = t6;
	else if(t7 != null && t7.length > 0)
		rows = t7;
	else if(t8 != null && t8.length > 0)
		rows = t8;
	else if(t9 != null && t9.length > 0)
		rows = t9;

	if(rows != null && rows.length > 0){
		rows.each(function(i){
			$(rows[i]).bind('click', function(e){
				var checkboxs = $(rows[i]).find('td > input[type=checkbox]');
				if(checkboxs != null && checkboxs.length == 1){
					var target = e.target;
					if(target.tagName.toLowerCase() == 'a')
						return;
					if(target.tagName.toLowerCase() == 'input' && $(target).attr('type') == 'checkbox'){
						target.checked = !target.checked;
					}
					var ck = checkboxs[0];
					ck.checked = !ck.checked;
				}
			});
		});
	}
});

function mouseOverOrOut() {
        var table1,table2,table3,table4,table5,table6,table7,table8,table9,rows;
        table1 = document.getElementById("listContent");
        table2 = document.getElementById("tbl");
        table3 = document.getElementById("typeOrderTable");
        table4 = document.getElementById("scopeListT");
        table5 = document.getElementById("table");
        table6 = document.getElementById("adminsTable");
        table7 = document.getElementById("fileListTable");
        table8 = document.getElementById("datalist");
        table9 = document.getElementById("filter");
        if(table1 != null){
           rows = table1.rows;
        }else if(table2 != null){      	
           rows = table2.rows;
        }else if(table3 != null){
           rows = table3.rows;
        }else if(table4 != null){
           rows = table4.rows;
        }else if(table5 != null){
           rows = table5.rows;
        }else if(table6 != null){
           rows = table6.rows;
        }else if(table7 != null){
           rows = table7.rows;
        }else if(table8 != null){
           rows = table8.rows;
        }else if(table9 != null){
           rows = table9.rows;
        }
        
		for (var i = 0; i < rows.length; i++) {
			if(i%2==0){
				rows[i].onmouseover = function () {       //鼠标在行上面的时候   
					this.style.background="#B9DDFE";			
				};
				rows[i].onmouseout = function () {        //鼠标离开时   
					this.style.background="#f7f6f6";					
				};
			}else{
				rows[i].onmouseover = function () {       //鼠标在行上面的时候   
					this.style.background="#B9DDFE";			
				};
				rows[i].onmouseout = function () {        //鼠标离开时   
					this.style.background="#ffffff";					
				};
			}
		}
}

function addMouseEvent(obj){
	var checkbox,atr,ath,i;
	atr=obj.getElementsByTagName("tr");
	for(i=0;i<atr.length;i++){
		atr[i].onclick=function(){
			ath=this.getElementsByTagName("th");
			checkbox=this.getElementsByTagName("input")[0];
			if(!ath.length && checkbox&& checkbox.getAttribute("type")=="checkbox"){
				if(this.className!="currenttr"){
					this.className="currenttr";
					checkbox.checked=true;
				}else{
					this.className="";
					checkbox.checked=false;
				}
			}
		}
	}
}

