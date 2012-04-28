package com.message.base.utils;

/**
 * 对于数字操作类.
 * 
 * @author sunhao(sunhao.java@gmail.com)
 * @version V1.0
 * @createTime 2012-4-28 上午10:36:12
 */
public class NumberUtils extends org.apache.commons.lang.math.NumberUtils {
	
	public static int getMaxCommonDivisor(int firstNum, int secondNum){
		int commonDivisor = 0;
		
		while(secondNum != 0){
			commonDivisor = firstNum % secondNum;
			firstNum = secondNum;
			secondNum = commonDivisor;
		}
		
		return commonDivisor;
	}
	
	public static int[] reduction(int numerator, int denominator){
		int commonDivisor = getMaxCommonDivisor(numerator, denominator);
		
		return new int[]{(int) numerator / commonDivisor, (int) denominator / commonDivisor};
	}

}
