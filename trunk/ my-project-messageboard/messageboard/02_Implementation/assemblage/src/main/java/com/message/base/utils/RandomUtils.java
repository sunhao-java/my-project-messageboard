package com.message.base.utils;

import java.util.Random;

/**
 * 随机生成数字或者字符串的工具类.
 * 
 * @author sunhao(sunhao.java@gmail.com)
 * @version V1.0
 * @createTime 2012-4-24 下午08:25:57
 */
public class RandomUtils {
    //获取随机数
    public static String getRandomNum(int len) {
        String RandomNum = "";
        long lRand = 0;
        long val_1 = 1;
        long val_2 = 1;

        for (int i = 0; i < len - 1; i++) {
            val_1 *= 10;
        }
        for (int j = 0; j < len; j++) {
            val_2 *= 10;
        }

        while (lRand <= val_1) {
            lRand = (long) (Math.random() * val_2);
        }

        RandomNum = Long.toString(lRand);

        return RandomNum;
    }

    /**
     * 生成随即密码
     *
     * @param lenght 生成的密码的总长度
     * @return 密码的字符串
     */
    public static String genRandomNum(int lenght) {
        // 35是因为数组是从0开始的，26个字母+10个数字
        final int maxNum = 10;
        int i; // 生成的随机数
        int count = 0; // 生成的密码的长度
        /*char[] str = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k',
                'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w',
                'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };*/
        char[] str = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};

        StringBuffer tempString = new StringBuffer(100);
        Random random = new Random();
        while (count < lenght) {
            // 生成随机数，取绝对值，防止生成负数，
            i = Math.abs(random.nextInt(maxNum)); // 生成的数最大为36-1
            if (i >= 0 && i < str.length) {
                tempString.append(str[i]);
                count++;
            }
        }

        return tempString.toString();
    }

    /**
     * 生成随即密码
     *
     * @param lenght 生成的密码的总长度
     * @return 密码的字符串
     */
    public static String genRandomString(int lenght) {
        // 35是因为数组是从0开始的，26个字母+10个数字
        final int maxNum = 35;
        int i; // 生成的随机数
        int count = 0; // 生成的密码的长度
        char[] str = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k',
                'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w',
                'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K',
                'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W',
                'X', 'Y', 'Z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};

        StringBuffer tempString = new StringBuffer(100);
        Random random = new Random();
        while (count < lenght) {
            // 生成随机数，取绝对值，防止生成负数，
            i = Math.abs(random.nextInt(maxNum)); // 生成的数最大为36-1
            if (i >= 0 && i < str.length) {
                tempString.append(str[i]);
                count++;
            }
        }

        return tempString.toString();
    }

    public static synchronized String randomString(int length) {
        if (length < 1) {
            return null;
        }

        Random randGen = new Random();
        char[] numbersAndLetters = ("0123456789abcdefghijklmnopqrstuvwxyz"
                + "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ").toCharArray();

        char[] randBuffer = new char[length];
        for (int i = 0; i < randBuffer.length; i++) {
            randBuffer[i] = numbersAndLetters[randGen.nextInt(71)];
        }
        return new String(randBuffer);
    }

    public static void main(String[] args) {
        System.out.println(genRandomNum(3));
    }

}
