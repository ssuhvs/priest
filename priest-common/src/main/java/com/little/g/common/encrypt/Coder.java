package com.little.g.common.encrypt;


import com.google.common.base.Strings;
import com.little.g.common.CommonConstants;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.BASE64Encoder;

import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 各种加密算法
 * 包括：URLEncode、MD5、SHA、HMAC
 */
public class Coder {
    public static final String KEY_SHA = "SHA";
    public static final String KEY_MD5 = "MD5";

    private static Logger logger = LoggerFactory.getLogger(Coder.class);

    public static final String VERSION_SECRET_KEY = "5!&were7$%&$9";



    /**
     * MAC算法可选以下多种算法
     * <p/>
     * <pre>
     * HmacMD5
     * HmacSHA1
     * HmacSHA256
     * HmacSHA384
     * HmacSHA512
     * </pre>
     */
    public static final String KEY_MAC = "HmacSHA1";

    /**
     * BASE64加密
     * @param key
     * @return
     * @throws Exception
     */
    public static String encryptBASE64(byte[] key)throws Exception{
        return (new BASE64Encoder()).encodeBuffer(key);
    }

    /**
     * MD5加密
     * @param data
     * @return
     * @throws Exception
     */
    public static byte[] encryptMD5(byte[] data)throws Exception{
        MessageDigest md5 = MessageDigest.getInstance(KEY_MD5);
        md5.update(data);
        return md5.digest();
    }

    /**
     * 将参数URLEncode为UTF-8
     */
    public static String encodeUTF8(String params) {
        try {
            String en = URLEncoder.encode(params, CommonConstants.DEFAULT_CONTENT_CHARSET);
            en = en.replace("+", "%20");
            en = en.replace("*", "%2A");
            return en;
        } catch (UnsupportedEncodingException problem) {
            throw new IllegalArgumentException(problem);
        }
    }

    /**
     * 将参数URLEncode，默认为UTF-8
     */
    public static String encode(String params, String charset) {
        try {
            String en = URLEncoder.encode(params, charset != null ? charset : CommonConstants.DEFAULT_CONTENT_CHARSET);
            en = en.replace("+", "%20");
            en = en.replace("*", "%2A");
            return en;
        } catch (UnsupportedEncodingException problem) {
            throw new IllegalArgumentException(problem);
        }
    }

    /**
     * 将参数URLDecoder为UTF-8
     */
    public static String decodeUTF8(String params) {
        try {
            String de = URLDecoder.decode(params, CommonConstants.DEFAULT_CONTENT_CHARSET);
            return de;
        } catch (UnsupportedEncodingException problem) {
            throw new IllegalArgumentException(problem);
        }
    }

    /**
     * BASE64解密
     *
     * @param key
     * @return
     * @throws Exception
     */
    public static byte[] decryptBASE64(String key) {
        return Base64.decodeBase64(key);
    }

    /**
     * 解码base64
     *
     * @param key
     * @return
     */
    public static String decodeBASE64(String key) {
        byte[] bytes = Base64.decodeBase64(key);
        return new String(bytes);
    }


    /**
     * BASE64加密
     *
     * @param key
     * @return
     * @throws Exception
     */
    public static String encryptBase64URLSafeString(byte[] key) throws Exception {
        return Base64.encodeBase64URLSafeString(key);
    }

    /**
     * BASE64加密
     *
     * @param key
     * @return
     * @throws Exception
     */
    public static String encryptBase64URLSafeString(String key) throws Exception {
        return Base64.encodeBase64URLSafeString(key.getBytes(CommonConstants.DEFAULT_CONTENT_CHARSET));
    }

    /**
     * BASE64加密
     *
     * @param key
     * @return
     * @throws Exception
     */
    public static String encryptBase64(String key) throws UnsupportedEncodingException {
        return Base64.encodeBase64String(key.getBytes(CommonConstants.DEFAULT_CONTENT_CHARSET));
    }

    /**
     * BASE64加密
     *
     * @param key
     * @return
     * @throws Exception
     */
    public static String encryptBase64(byte[] key) throws UnsupportedEncodingException {
        return Base64.encodeBase64String(key);
    }

    /**
     * MD5加密
     *
     * @param data
     * @return
     * @throws Exception
     */
    public static String encryptMD5(String data) throws Exception {

        MessageDigest md5 = MessageDigest.getInstance(KEY_MD5);
        md5.update(data.getBytes());

        return toHexString(md5.digest());

    }

    /**
     * MD5加密
     *
     * @param data
     * @return
     * @throws Exception
     */
    public static byte[] encryptMD5_Byte(String data) throws Exception {

        MessageDigest md5 = MessageDigest.getInstance(KEY_MD5);
        md5.update(data.getBytes(CommonConstants.DEFAULT_CONTENT_CHARSET));
        return md5.digest();
    }

    /**
     * MD5加密
     *
     * @param data
     * @return
     * @throws Exception
     */
    public static String encryptMD5GBK(String data) throws Exception {

        MessageDigest md5 = MessageDigest.getInstance(KEY_MD5);
        md5.update(data.getBytes("GBK"));
        return toHexString(md5.digest());

    }


    /**
     * SHA加密
     *
     * @param data
     * @return
     * @throws Exception
     */
    public static byte[] encryptSHA(byte[] data) throws Exception {

        MessageDigest sha = MessageDigest.getInstance(KEY_SHA);
        sha.update(data);

        return sha.digest();

    }

    public static String encryptSHA16(byte[] data) {
        // 用来将字节转换成 16 进制表示的字符
        char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

        try {
            MessageDigest md = MessageDigest.getInstance(KEY_SHA);
            md.update(data); // 通过使用 update 方法处理数据,使指定的 byte数组更新摘要
            byte[] encryptStr = md.digest(); // 获得密文完成哈希计算,产生128 位的长整数
            char str[] = new char[16 * 2]; // 每个字节用 16 进制表示的话，使用两个字符
            int k = 0; // 表示转换结果中对应的字符位置
            for (int i = 0; i < 16; i++) { // 从第一个字节开始，对每一个字节,转换成 16 进制字符的转换
                byte byte0 = encryptStr[i]; // 取第 i 个字节
                str[k++] = hexDigits[byte0 >>> 4 & 0xf]; // 取字节中高 4 位的数字转换, >>> 为逻辑右移，将符号位一起右移
                str[k++] = hexDigits[byte0 & 0xf]; // 取字节中低 4 位的数字转换
            }
            return new String(str); // 换后的结果转换为字符串
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 初始化HMAC密钥
     *
     * @return
     * @throws Exception
     */
    public static String initMacKey() throws Exception {
        KeyGenerator keyGenerator = KeyGenerator.getInstance(KEY_MAC);

        SecretKey secretKey = keyGenerator.generateKey();
        return encryptBase64URLSafeString(secretKey.getEncoded());
    }

    /**
     * HMAC加密
     *
     * @param data
     * @param key
     * @return
     * @throws Exception
     */
    public static byte[] encryptHMAC(String data, byte[] key) throws Exception {

        SecretKey secretKey = new SecretKeySpec(key, KEY_MAC);
        Mac mac = Mac.getInstance(secretKey.getAlgorithm());
        mac.init(secretKey);

        return mac.doFinal(data.getBytes(CommonConstants.DEFAULT_CONTENT_CHARSET));

    }

    public static String toHexString(byte[] b) {
        StringBuilder sb = new StringBuilder(b.length * 2);
        for (byte by : b) {
            sb.append(HEXCHAR[(by & 0xf0) >>> 4]);
            sb.append(HEXCHAR[by & 0x0f]);
        }
        return sb.toString();
    }

    public static byte[] toBytes(String s) {
        byte[] bytes;
        bytes = new byte[s.length() / 2];
        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = (byte) Integer.parseInt(s.substring(2 * i, 2 * i + 2), 16);
        }
        return bytes;
    }

    /**
     * 内部接口方法签名生成 MD5(mobile + deviceId + version + secretKey)
     *
     * @param mobile   手机号
     * @param deviceId 设备号
     * @return
     * @throws Exception
     */
    public static String generatorCode(String mobile, String deviceId, Long ct) {
        //计算默认的code
        String code = "";
        if (!Strings.isNullOrEmpty(mobile) && !Strings.isNullOrEmpty(deviceId) && ct != null && ct > 0) {
            try {
                code = mobile + deviceId + VERSION_SECRET_KEY + ct;
                code = Coder.encryptMD5(code);
            } catch (Exception e) {
                logger.error("calculate default code error:mobile:{},deviceId:{},ct:{}", mobile, deviceId, ct, e);
            }
        }
        return code;
    }

    private static char[] HEXCHAR = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    public static void main(String[] args) {
        String countryCode = "+86";
        String mobile = "13718378010";
        String deviceId = "CF5C58DE-D982-4169-9FFB-1D8173D5E3DA";
        Long ct = System.currentTimeMillis();
        System.out.println(ct);
        System.out.println(generatorCode(countryCode + mobile, deviceId, 1456467086228L));

//        String value = " 132-34_56678";
//        value = value.trim();
//        if (value.contains("_")) {
//            value = value.replace("_", "");
//        }
//        if (value.contains("-")) {
//            value = value.replace("-", "");
//        }
//        if (value.contains(" ")) {
//            value = value.replaceAll(" ", "");
//        }
//        System.out.println(value);
    }
}
