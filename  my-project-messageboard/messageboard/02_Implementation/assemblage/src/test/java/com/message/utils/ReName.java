package com.message.utils;

import java.io.File;
import java.io.IOException;

/**
 * 批量修改文件名
 * 
 * @author sunhao(sunhao.java@gmail.com)
 * @version V1.0
 * @createTime 2012-3-3 上午04:02:38
 */
public class ReName {
	public static void main(String[] args) throws IOException {
		rename("C:\\Documents and Settings\\Administrator\\桌面\\新建1文件夹\\");
	}
	
	private static void rename(String path) throws IOException{
		File file = new File(path);
		if(file.isDirectory()){
			String newFileName = "";
			String[] fileNames = file.list();
			
			for(int i = 0; i < fileNames.length; i++) {
				File f = new File(file, fileNames[i]);
				String name = f.getCanonicalPath();
				int spilt = name.lastIndexOf(".");
				if(spilt != -1){
					String prefix = (i + 1) < 10 ? "0" + (i + 1) : i + "";
					newFileName = "icon_" + prefix + name.substring(spilt, name.length());
				} else {
					System.out.println("the file named " + name + " is not a file!!");
				}
				f.renameTo(new File(path + newFileName));
			}
		} else {
			throw new RuntimeException("this \"" + path + "\" is not a folder!!");
		}
	}
}
