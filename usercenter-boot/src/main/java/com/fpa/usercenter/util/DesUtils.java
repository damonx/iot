package com.fpa.usercenter.util;

import java.security.Key;

import javax.crypto.Cipher;

public class DesUtils {
	private static String defaultSecretKey = "UC_UHOME_combination";
	private static Cipher encryptCipher = null;
	private static Cipher decryptCipher = null;

	public DesUtils() {
		this(defaultSecretKey);
	}

	public DesUtils(final String secretKey) {
		Key key;
		try {
			key = getKey(secretKey.getBytes());
			encryptCipher = Cipher.getInstance("DES");
			encryptCipher.init(Cipher.ENCRYPT_MODE, key);
			decryptCipher = Cipher.getInstance("DES");
			decryptCipher.init(Cipher.DECRYPT_MODE, key);
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

	public String encrypt(final String strIn) throws Exception {
		return byteArr2HexStr(encrypt(strIn.getBytes()));
	}

	public byte[] encrypt(final byte[] arrB) throws Exception {
		return encryptCipher.doFinal(arrB);
	}

	public String decrypt(final String strIn) throws Exception {
		return new String(decrypt(hexStr2ByteArr(strIn)));
	}

	public byte[] decrypt(final byte[] arrB) throws Exception {
		return decryptCipher.doFinal(arrB);
	}

	public static String byteArr2HexStr(final byte[] arrB) {
		final int iLen = arrB.length;
		final StringBuffer sb = new StringBuffer(iLen * 2);
		for (int i = 0; i < iLen; i++) {
			int intTmp = arrB[i];
			while (intTmp < 0) {
				intTmp = intTmp + 256;
			}
			if (intTmp < 16) {
				sb.append("0");
			}
			sb.append(Integer.toString(intTmp, 16));
		}
		return sb.toString();
	}

	public static String changIdtoLong(final String id) {
		final char[] ids = id.toCharArray();
		final StringBuffer sb = new StringBuffer();

		for (final char d : ids) {
			if (d != '0') {
				final int s = d;
				sb.append(s);
			}
		}
		String strid = sb.toString();
		if (strid.length() > 15) {
			strid = strid.substring(0, 15);
		}

		return strid;
	}

	public static byte[] hexStr2ByteArr(final String strIn) throws Exception {
		final byte[] arrB = strIn.getBytes();
		final int iLen = arrB.length;
		final byte[] arrOut = new byte[iLen / 2];
		for (int i = 0; i < iLen; i = i + 2) {
			final String strTmp = new String(arrB, i, 2);
			arrOut[i / 2] = (byte) Integer.parseInt(strTmp, 16);
		}
		return arrOut;
	}

	private Key getKey(final byte[] arrBTmp) {
		final byte[] arrB = new byte[8];
		for (int i = 0; i < arrBTmp.length && i < arrB.length; i++) {
			arrB[i] = arrBTmp[i];
		}
		final Key key = new javax.crypto.spec.SecretKeySpec(arrB, "DES");
		return key;
	}
}