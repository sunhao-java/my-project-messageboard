
/**
 *  封装日期时间选择组件，以下是dateFormat的格式
 * 	<ul>
		<li><code>%a</code> &mdash; abbreviated weekday name</li>
		<li><code>%A</code> &mdash; full weekday name</li>
		<li><code>%b</code> &mdash; abbreviated month name</li>
		<li><code>%B</code> &mdash; full month name</li>
		<li><code>%C</code> &mdash; the century number</li>
		<li><code>%d</code> &mdash; the day of the month (range 01 to 31)</li>
		<li><code>%e</code> &mdash; the day of the month (range 1 to 31)</li>
		<li><code>%H</code> &mdash; hour, range 00 to 23 (24h format)</li>
		<li><code>%I</code> &mdash; hour, range 01 to 12 (12h format)</li>
		<li><code>%j</code> &mdash; day of the year (range 001 to 366)</li>
		<li><code>%k</code> &mdash; hour, range 0 to 23 (24h format)</li>
		<li><code>%l</code> &mdash; hour, range 1 to 12 (12h format)</li>
		<li><code>%m</code> &mdash; month, range 01 to 12</li>
		<li><code>%o</code> &mdash; month, range 1 to 12</li>
		<li><code>%M</code> &mdash; minute, range 00 to 59</li>
		<li><code>%n</code> &mdash; a newline character</li>
		<li><code>%p</code> &mdash; <strong>PM</strong> or <strong>AM</strong></li>
		<li><code>%P</code> &mdash; <strong>pm</strong> or <strong>am</strong></li>
		<li><code>%s</code> &mdash; UNIX time (number of seconds since 1970-01-01)</li>
		<li><code>%S</code> &mdash; seconds, range 00 to 59</li>
		<li><code>%t</code> &mdash; a tab character</li>
		<li><code>%W</code> &mdash; week number</li>
		<li><code>%u</code> &mdash; the day of the week (range 1 to 7, 1 = MON)</li>
		<li><code>%w</code> &mdash; the day of the week (range 0 to 6, 0 = SUN)</li>
		<li><code>%y</code> &mdash; year without the century (range 00 to 99)</li>
		<li><code>%Y</code> &mdash; year with the century</li>
		<li><code>%%</code> &mdash; a literal '%' character</li>
	</ul>
 *	@author sunhao(sunhao.java@gmail.com)
 */
YAHOO.namespace("app.calendar");
YAHOO.app.calendar = function(){
	var $C = YAHOO.util.Connect,dom = YAHOO.util.Dom,event = YAHOO.util.Event,$L=YAHOO.lang;
	
	return{
		simpleInit : function(args){
			var dateFormat_ = args.dateFormat || 'yyyy-MM-dd hh:mm';		//正则表达式
			var id_ = args.id;
			
			var style = "background: none repeat scroll 0 0 #2782D6;border-color: #DDDDDD #264F6E #264F6E #DDDDDD;border-style: solid;" +
						"border-width: 1px;color: #FFFFFF;cursor: pointer;height: 21px;letter-spacing: 1px;line-height: 17px;padding: 1px 10px;" +
						"text-align: center;margin-left: 5px;";
			var button = $("<input type='button' id='" + id_ + "SelectBtn' value='选择' style='" + style + "'>");
			var dateField = $("#" + id_);
			dateField.after(button);
			dateField.css("width", 150);
			dateField.attr("readonly", "readonly");
			
			var calendar_dateFormat = '%Y-%m-%d %l:%M';
			
			if(dateFormat_ == 'yyyy-MM-dd hh:mm'){
				calendar_dateFormat = '%Y-%m-%d %l:%M';
			} else if(dateFormat_ == 'yyyy-MM-dd'){
				calendar_dateFormat = '%Y-%m-%d';
			}
			
			NEW_CALENDAR = new Calendar({
				inputField 		: id_,
				dateFormat 		: calendar_dateFormat,
				trigger 		: id_ + 'SelectBtn',
				selectionType	: Calendar.SEL_MULTIPLE,
				showTime		: 12,
				onSelect		: function(){
						 this.hide();
				}
			});
		
		}
	}
}(); 