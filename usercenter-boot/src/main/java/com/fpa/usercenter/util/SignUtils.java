package com.fpa.usercenter.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SignUtils {

	protected static final Logger log = LoggerFactory.getLogger(SignUtils.class);

	public static String sha256(final String appId, final String appKey, final String clientId) {
		MessageDigest md = null;
		final StringBuffer sb = new StringBuffer();
		sb.append(appId).append(appKey).append(clientId);
		try {
			md = MessageDigest.getInstance("SHA-256");
			byte[] bytes = null;
			bytes = md.digest(sb.toString().getBytes("utf-8"));
			final String sign = BinaryToHexString(bytes);
			return sign;
		} catch (final NoSuchAlgorithmException e) {
			log.error("MessageDigest error!");
		} catch (final UnsupportedEncodingException e) {
			log.error("digest error! ");
		}

		return null;

	}

	public static String sha256Uws(final String appId, final String appKey, final String body, final long timestamp, final String url) {
		Objects.requireNonNull(appId, "Application ID cannot be null");
		Objects.requireNonNull(appKey, "Application Key cannot be null");
		Objects.requireNonNull(body, "Message body cannot be null");
		Objects.requireNonNull(url, "Url cannot be null");

		final StringBuffer sb = new StringBuffer();
		sb.append(url);
		log.info("body={}" + body);
		if (StringUtils.isNotBlank(body)) {
			sb.append(StringUtils.trim(body).replaceAll(" ", "").replaceAll("\t", "").replaceAll("\r", "").replaceAll("\n", ""));
		}
		sb.append(appId).append(StringUtils.trim(appKey).replaceAll("\"", "")).append(timestamp);
		log.info("body:" + sb.toString());
		MessageDigest md = null;
		byte[] bytes = null;
		try {
			md = MessageDigest.getInstance("sha-256");
			bytes = md.digest(sb.toString().getBytes("utf-8"));
		} catch (final Exception e) {
			e.printStackTrace();
		}

		return BinaryToHexString(bytes);

	}

	private static String BinaryToHexString(final byte[] bytes) {
		final StringBuilder hex = new StringBuilder();
		final String hexStr = "0123456789abcdef";
		for (final byte b : bytes) {
			hex.append(String.valueOf(hexStr.charAt((b & 0xF0) >> 4)));
			hex.append(String.valueOf(hexStr.charAt(b & 0x0F)));
		}
		return hex.toString();
	}

	public static void main(final String[] args) {
		try {
			System.out.println(sha256("MB-RSQCSAPP-0000", "62207fa2b7b705b7630c84a836f5dca4", "123456"));
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}
}
