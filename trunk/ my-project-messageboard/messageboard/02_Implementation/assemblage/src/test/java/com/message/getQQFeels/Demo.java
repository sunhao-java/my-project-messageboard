package com.message.getQQFeels;

import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;

/**
 * 
 * 
 * @author sunhao(sunhao.java@gmail.com)
 * @version V1.0
 * @createTime 2012-3-15 下午10:07:17
 */
public class Demo {
	public static void main(String[] args) throws IOException {
		download("http://cm.qzonestyle.gtimg.cn/qzone/em/e");
	}

	public static void download(String url) throws IOException {
        HttpClient client = new HttpClient();
        
        for(int i = 7420; i < 7450; i++){
        	GetMethod method = new GetMethod(url + i + ".gif");
        	client.executeMethod(method);
        	
        	byte[] fileByte = method.getResponseBody();
        	
        	FileOutputStream fos = new FileOutputStream("E:\\qq\\6\\e" + i + ".gif");
        	fos.write(fileByte);
        }

    }
}
