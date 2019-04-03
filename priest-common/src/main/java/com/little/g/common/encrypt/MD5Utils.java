package com.little.g.common.encrypt;

import com.google.common.base.Strings;
import org.apache.commons.codec.digest.DigestUtils;

/**
 * DATE:9/27/15 14:29
 * AUTHOR:wangzhen
 */
public class MD5Utils {
    private static final String SALT = "littleg.salt";

    public static String encode(String raw) {
        return DigestUtils.md5Hex(raw + SALT);
    }

    public static boolean check(String raw,String cipher) {
        String encrypt = MD5Utils.encode(raw);

        return !Strings.isNullOrEmpty(encrypt)
                && encrypt.equals(cipher);

    }

    public static void main(String[] args) {

        String encrypt = MD5Utils.encode("littleg");
        System.out.println(encrypt);

        System.out.println(check("little",encrypt));
    }
}
