package com.kevin.onlinetest.util;


import org.springframework.util.DigestUtils;

/**
 * @author herokilito
 */
public class SecurityUtil {

    public static String getEncodedString(String plainString) {
        // 双重md5加密
        return md5(md5(plainString));
    }

    public static boolean check(String plainString, String cypherString) {
        return getEncodedString(plainString).equals(cypherString);
    }

    private static String md5(String plainText) {
        return DigestUtils.md5DigestAsHex(plainText.getBytes());
    }

    private static boolean md5Compare(String plainText,String cypherText) {
        return cypherText.equals(md5(plainText));
    }

}
