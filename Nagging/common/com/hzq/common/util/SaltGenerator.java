/**
 * @(#)SaltGenerator.java
 *
 * @author huangzhiqian
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2015年9月2日 huangzhiqian 创建版本
 */
package com.hzq.common.util;

import java.security.SecureRandom;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.Validate;

/**
 * 
 * 
 * @author huangzhiqian
 */
public class SaltGenerator {
	private static SecureRandom random = new SecureRandom();
	/**
	 * 生成随机的byte[]作为salt
	 * @param numBytes
	 * @return
	 * @author huangzhiqian
	 * @date 2015年9月2日
	 */
	private static byte[] generateSalt(int numBytes) {
		Validate.isTrue(numBytes > 0,
				"numBytes argument must be a positive integer (1 or larger)",
				numBytes);

		byte[] bytes = new byte[numBytes];
		random.nextBytes(bytes);
		return bytes;
	}
	
	/**
	 * Hex编码
	 * @param input
	 * @return
	 * @author huangzhiqian
	 * @date 2015年9月2日
	 */
	private  static String encodeHex(byte[] input) {
		return Hex.encodeHexString(input);
	}
	/**
	 * 生产制定长度*2的盐 
	 * @param saltlength
	 * @return
	 * @author huangzhiqian
	 * @date 2015年9月2日
	 */
	public static String getSalt(int saltlength){
		
		return encodeHex(generateSalt(saltlength));
	}
	
	
}

