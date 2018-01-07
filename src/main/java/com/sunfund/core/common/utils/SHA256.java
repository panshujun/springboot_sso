package com.sunfund.core.common.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * SHA256 数字签名
 * @author zhangshun
 *
 */
public class SHA256 {

    
	public static String encryptText(String clearText ){
		try {
			//1.转为byte
			byte[] clearByte=clearText.getBytes("UTF-8");
			//2.加密
			MessageDigest messDigest=MessageDigest.getInstance("SHA-256");
			messDigest.update(clearByte);
			byte[] hashValue= messDigest.digest();
			//转换
			String result=encodeHexString(hashValue);
			return result;
			
		} catch (UnsupportedEncodingException | NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static String encodeHexString(byte[] data){
	        final int l = data.length;
	        final char[] out = new char[l << 1];
	        // two characters form the hex value.
	        for (int i = 0, j = 0; i < l; i++) {
	            out[j++] = DIGITS_LOWER[(0xF0 & data[i]) >>> 4];
	            out[j++] = DIGITS_LOWER[0x0F & data[i]];
	        }
	        return new String(out);
	}


private static final char[] DIGITS_LOWER =
    {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

/*private static final char[] DIGITS_UPPER =
    {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};*/
}
