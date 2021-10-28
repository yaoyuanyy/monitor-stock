package com.skyler.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/**
 * Description:
 * <pre>
 *
 * </pre>
 * NB.
 *
 * @author skyler_11@163.com
 * Created by on 2021-10-24 at 15:08
 */
public class Decript {

    public static String SHA1(String decript) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-1");
            digest.update(decript.getBytes());
            byte messageDigest[] = digest.digest();
            // Create Hex String
            StringBuffer hexString = new StringBuffer();
            // 字节数组转换为 十六进制 数
            for (int i = 0; i < messageDigest.length; i++) {
                String shaHex = Integer.toHexString(messageDigest[i] & 0xFF);
                if (shaHex.length() < 2) {
                    hexString.append(0);
                }
                hexString.append(shaHex);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String sort(String token, String timestamp, String nonce) {
        String[] strArray = {token, timestamp, nonce};
        Arrays.sort(strArray);

        StringBuilder sbuilder = new StringBuilder();
        for (String str : strArray) {
            sbuilder.append(str);
        }

        return sbuilder.toString();
    }
}
