
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

