package com.message.utils;

public class JsonUtils {
	
	public static String toString(Object obj){
		return toString(null, obj);
	}
	
	public static String toString(String rootName, Object obj){
		/**
		 * TODO:need to create class JSONSerializer
		 * 
		 * JSONSerializer serializer = new JSONSerializer();
		 * serializer.exclude(new String[] { "class" });
		 * if (rootName == null) {
		 * 	  return serializer.deepSerialize(obj);
		 * }
		 * return serializer.deepSerialize(rootName, obj);
		 * */
		return obj.toString();
	}
}
