package com.message.annotation;


/**
 * .
 * 
 * @author sunhao(sunhao.java@gmail.com)
 * @version V1.0
 * @createTime 2012-5-9 上午11:05:04
 */
public class TestAn {
	public static void main(String[] args) {
		Test test = Bean.class.getAnnotation(Test.class);
		System.out.println(test.init());
		System.out.println(test.value());
	}
}
