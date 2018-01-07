package com.sunfund.core.common.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

@Component
public class TokenProcessor {
	private static TokenProcessor instance = new TokenProcessor();

	private long previous;
	private static final String DEFAULT_SALT = "tutwm42nolbpgkaabhh85qivopc1kzwmbyni27faviiqxfqwajnlwkb54uj01zwe";

	private TokenProcessor() {
	}

	public static TokenProcessor getInstance() {
		return instance;
	}

	public boolean isTokenValid(String token, boolean reset) {
		Lock lock = new ReentrantLock();
		try {

		} finally {
			lock.unlock();// 释放锁
		}
		return true;
	}

	public void resetToken(String token) {
		Lock lock = new ReentrantLock();
		try {

		} finally {
			lock.unlock();// 释放锁
		}
	}

	public void saveToken(String salt) {
		Lock lock = new ReentrantLock();
		try {

		} finally {
			lock.unlock();// 释放锁
		}
	}

	public String generateToken(String salt) {
		Lock lock = new ReentrantLock();
		try {
			lock.lock();

			long current = System.currentTimeMillis()
					+ ThreadLocalRandom.current().nextInt(10);
			if (current == previous)
				current++;
			previous = current;

			byte now[] = (new Long(current)).toString().getBytes();
			MessageDigest md = MessageDigest.getInstance("MD5");

			if (StringUtils.isBlank(salt)) {
				salt = DEFAULT_SALT;
			}
			md.update(salt.getBytes());
			md.update(now);

			return toHex(md.digest());
		} catch (NoSuchAlgorithmException e) {
			return null;
		} finally {
			lock.unlock();// 释放锁
		}
	}

	private String toHex(byte buffer[]) {
		StringBuffer sb = new StringBuffer(buffer.length * 2);
		for (int i = 0; i < buffer.length; i++) {
			sb.append(Character.forDigit((buffer[i] & 240) >> 4, 16));
			sb.append(Character.forDigit(buffer[i] & 15, 16));
		}

		return sb.toString();
	}

	public static void main(String args[]) {
		TokenProcessor processor = TokenProcessor.getInstance();
		String token = processor.generateToken("2011cj7067");

		System.err.println(token);

		String token2 = processor.generateToken("2011cj7067");

		System.err.println(token2);
	}

}
