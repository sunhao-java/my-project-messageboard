package com.message.md5;

import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Base64;

public class EncrypyUtils {
	public static final String KEY_SHA = "SHA";
	public static final String KEY_MD5 = "MD5";
	public static final String KEY_MAC = "HmacMD5";

	// sun不推荐使用它们自己的base64,用apache的挺好
	/**
	 * BASE64解密
	 */
	public static byte[] decryptBASE64(byte[] dest) {
		if (dest == null) {
			return null;
		}
		return Base64.decodeBase64(dest);
	}

	/**
	 * BASE64加密
	 */
	public static byte[] encryptBASE64(byte[] origin) {
		if (origin == null) {
			return null;
		}
		return Base64.encodeBase64(origin);
	}

	/**
	 * MD5加密
	 * 
	 * @throws NoSuchAlgorithmException
	 */
	public static byte[] encryptMD5(byte[] data)
			throws NoSuchAlgorithmException {
		if (data == null) {
			return null;
		}
		MessageDigest md5 = MessageDigest.getInstance(KEY_MD5);
		md5.update(data);
		return md5.digest();
	}

	/**
	 * SHA加密
	 * 
	 * @throws NoSuchAlgorithmException
	 */
	public static byte[] encryptSHA(byte[] data)
			throws NoSuchAlgorithmException {
		if (data == null) {
			return null;
		}
		MessageDigest sha = MessageDigest.getInstance(KEY_SHA);
		sha.update(data);
		return sha.digest();
	}

	/**
	 * 初始化HMAC密钥
	 * 
	 * @throws NoSuchAlgorithmException
	 */
	public static String initMacKey() throws NoSuchAlgorithmException {
		KeyGenerator keyGenerator = KeyGenerator.getInstance(KEY_MAC);
		SecretKey secretKey = keyGenerator.generateKey();
		return new String(encryptBASE64(secretKey.getEncoded()));
	}

	/**
	 * HMAC加密
	 * 
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeyException
	 */
	public static byte[] encryptHMAC(byte[] data, String key)
			throws NoSuchAlgorithmException, InvalidKeyException {
		SecretKey secretKey = new SecretKeySpec(decryptBASE64(key.getBytes()),
				KEY_MAC);
		Mac mac = Mac.getInstance(secretKey.getAlgorithm());
		mac.init(secretKey);
		return mac.doFinal(data);

	}

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		String data = "简单加密";
		System.out.println(new BigInteger(encryptBASE64(data.getBytes()))
				.toString(16));
		System.out.println(new BigInteger(encryptBASE64(data.getBytes()))
				.toString(32));
		System.out.println(new String(decryptBASE64(encryptBASE64(data
				.getBytes()))));

		System.out.println(new BigInteger(encryptMD5(data.getBytes()))
				.toString());
		System.out.println(new BigInteger(encryptSHA(data.getBytes()))
				.toString());

		System.out.println(new BigInteger(encryptHMAC(data.getBytes(),
				initMacKey())).toString());
	}

}
