package com.message.base.utils;

import com.message.base.json.JSONSerializer;

public class JsonUtils {

	public static String toString(Object obj)
	{
		return toString(null, obj);
	}
	
	public static String toString(String rootName, Object obj)
	{
		JSONSerializer serializer = new JSONSerializer(); 
		//serializer.addExclude("*.class");
		serializer.exclude(new String[]{"class"});
		if(rootName==null)
		{
			return serializer.deepSerialize(obj);
		}
		return serializer.deepSerialize(rootName, obj);		
	}
}
