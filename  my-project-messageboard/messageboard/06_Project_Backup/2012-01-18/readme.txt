
此次失败原因：
	1、在页面的文本框输入一个时间(String类型的)，想controller中传递时，会报错，spring报错：不能绑定数据
		原因是因为message中的beginTime是Date类型的，而页面传来的是string类型的
		
	2、页面第一次点击查询是正确的传递中文，而第二次点击的时候(直接查询或者分页)会出现中文显示乱码的错误



解决方案：
	1、在controller中重写父类的方法：
		/**
		 * TODO :寻求更好的解决方案
		 * 提交form时，表单中的beginTime和endTime是string类型的，而message这个bean中的两个字段是date类型的<br/>
		 * spring进行字段绑定的时候会报错，无法绑定这两个字段<br/>
		 * 解决办法暂时是重写父类此方法，注册一个customerEditor，用来进行日期类型转换
		 * @param request
		 * @param binder
		 */
		protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) {
	        SimpleDateFormat dateFormat = new SimpleDateFormat(ResourceType.SIMPLE_DATE_FORMAT);
	        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	    } 
	    此方法虽然可以，但是比较麻烦，累赘！需要改进
	    
	2、暂时没有找到解决方案