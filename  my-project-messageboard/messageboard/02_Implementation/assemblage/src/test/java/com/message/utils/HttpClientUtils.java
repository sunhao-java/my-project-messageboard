package com.message.utils;

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
public class HttpClientUtils {
	public static void main(String[] args) throws IOException {
		upload("http://img.baidu.com/img/baike/logo-baike.gif");
	}

	public static void upload(String url) throws IOException {
        HttpClient client = new HttpClient();
        GetMethod method = new GetMethod(url);
        client.executeMethod(method);
        
        byte[] fileByte = method.getResponseBody();

        FileOutputStream fos = new FileOutputStream("D:\\1.gif");
        fos.write(fileByte);
    }
}
