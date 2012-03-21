package com.message.utils;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/**
 * 批量修改文件名
 * 
 * @author sunhao(sunhao.java@gmail.com)
 * @version V1.0
 * @createTime 2012-3-3 上午04:02:38
 */
public class ReName {
	public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("please input the file path what files you want to rename in:");
        String filePath = scanner.next();
        System.out.println("please input a string, means that renamed file name like '景甜-00.jpg':");
        String fileName = scanner.next();

        if("".equals(filePath) || "".equals(fileName)){
            System.out.println("you must given me this two params!");
            return;
        } else {
            rename(filePath, fileName);
        }
	}
    
    private static void rename(String path, String fileName) throws IOException{
		if("".equals(path) || "".equals(fileName)){
            System.out.println("you must given me this two params!");
            return;
        }

        File file = new File(path);
        if(!file.isDirectory()){
            System.out.println("you given path is not a folder!");
            return;
        }

        File[] files = file.listFiles();
        if(files == null || files.length < 0){
            System.out.println("there is no file under this folder '" + path + "' you given!");
            return;
        }
        for(int i = 1; i < files.length + 1; i++){
            File f = files[i - 1];
            String fName = f.getName();
            System.out.println("the " + i + " file name is '" + fName + "'!");
            int index = fName.lastIndexOf(".");
            String newFileName = "";
            if(index != -1){
                String prefix = i < 10 ? ("0" + i) : ("" + i);
                newFileName = fileName + "_" + prefix + fName.substring(index, fName.length());
                System.out.println("the " + i + " new file name is '" + newFileName + "'!");
            } else {
                System.out.println("the file named " + fName + " is not a file!!");
            }
            f.renameTo(new File(path + newFileName));
        }
	}
}
