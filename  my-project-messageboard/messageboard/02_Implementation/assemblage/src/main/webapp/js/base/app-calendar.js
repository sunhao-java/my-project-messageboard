YAHOO.namespace("app.calendar");

YAHOO.app.calendar = function(){
	var $C = YAHOO.util.Connect,dom = YAHOO.util.Dom,event = YAHOO.util.Event,$L=YAHOO.lang;
	
	return{
		simpleInit : function(args){
			var dateFormat_ = args.dateFormat || 'yyyy-MM-dd hh:mm';		//正则表达式
			var id_ = args.id;
			var x = args.x || 0;
			var y = args.y || 0;
			var dateField = dom.get(id_);
			
			event.addListener(dateField, "click", function(){
				SelectDate(this, dateFormat_);
			});
		}
	}
}(); 