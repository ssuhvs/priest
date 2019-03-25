package com.little.g.common.utils;

import com.google.common.base.Strings;
import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 手机号工具类
 * User: erin
 * Date: 14-10-16
 * Time: 下午2:50
 */
public class PhoneUtil {

    //  TODO 121号码段是留给内部测试使用
    public static final String PHONE_FORMAT = "(^[0-9]{3,4}-[0-9]{3,8}$)|^(198|121|122|123|13[0-9]|14[5|7]|15[0-9]|166|18[0-9]|199|17[0|1|3|5|6|7|8])\\d{8}$";
    public final static String DEFAULT_COUNTRY_CODE = "86";
    public final static Pattern PHONE_TEST=Pattern.compile("2[89]\\d{9}");


    /**
     * 验证手机号码、电话号码是否有效
     * 新联通 （中国联通+中国网通）手机号码开头数字 130、131、132、145、155、156、185、186
     * 新移动 （中国移动+中国铁通）手机号码开头数字 134、135、136、137、138、139、147、150、151、152、157、158、159、182、183、187、188
     * 新电信 （中国电信 <http: baike.baidu.com/view/3214.htm>+中国卫通）手机号码开头数字 133、153、189、180、181
     */
    public static boolean verifyPhoneNumberFormat(String phone) {
        String countryCode = DEFAULT_COUNTRY_CODE;
        return verifyNationalNumber(phone, countryCode);
    }

    public static boolean isTestMobile(String phone){
        Matcher matcher=PHONE_TEST.matcher(phone);
        if(matcher.matches()){
            return true;
        }
        return false;
    }

    public static String getTestPassword(String phone){
        Long pthoneNum=Long.parseLong(phone);
        return String.valueOf((pthoneNum%8999)+1000);
    }

    /**
     * 校验国际号码
     *
     * @param phone       手机号码
     * @param countryCode 国际城市代码
     * @return 是否合格
     */
    public static boolean verifyNationalNumber(String phone, String countryCode) {
        //过滤掉非数字的字符
        if (Strings.isNullOrEmpty(countryCode) || Strings.isNullOrEmpty(phone)) {
            return false;
        }
        countryCode = preCheck(countryCode);
        if(isTestMobile(phone)){
            return true;
        }

        int countryCodeInt = Integer.valueOf(countryCode);
        PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
        //根据城市编码获取国际域名缩写
        String region = phoneUtil.getRegionCodeForCountryCode(countryCodeInt);
        //国际号码校验且内部测试号码及云测号码放行
        return formatNationalMobile(phone, region) || ("CN".equals(region) && !Strings.isNullOrEmpty(phone) && phone.matches(PHONE_FORMAT));
    }

    public static String preCheck(String s) {
        if (Strings.isNullOrEmpty(s)) {
            return null;
        }
        // 过滤掉空格和中划线
        s = s.trim();
        s = s.replaceAll("[^0-9]", "");
        return s;
    }

    public static String mergeCountryCodePhone(String countryCode, String mobile) {
        if (Strings.isNullOrEmpty(countryCode)) {
            countryCode = DEFAULT_COUNTRY_CODE;
        } else {
            countryCode = preCheck(countryCode);
        }
        mobile = preCheck(mobile);
        return countryCode + "_" + mobile;
    }

    public static String formatMobile(String mobile) {
        mobile = preCheck(mobile);
        // 过滤出+086等前缀
        if (!Strings.isNullOrEmpty(mobile) && mobile.length() > 11) {
            int length = mobile.length();
            mobile = mobile.substring(length - 11, length);
        }
        return mobile;
    }

    private static boolean formatNationalMobile(String mobile, String region) {
        PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
        try {
            Phonenumber.PhoneNumber swissNumberProto = phoneUtil.parse(mobile, region);
            if (phoneUtil.isValidNumber(swissNumberProto)) {
                System.out.println("is Valid phone number");
                System.err.println(swissNumberProto.getCountryCode());
                System.err.println(swissNumberProto.getNationalNumber());

            } else {
                System.out.println("is not Valid phone number");
                return false;
            }
        } catch (NumberParseException e) {
            System.err.println("NumberParseException was thrown: " + e.toString());
        }
        return true;
    }

    public static void main(String[] args) {

        String countryCode = "+86";
        String phone = "16619915916";
        System.out.println(verifyNationalNumber(phone, countryCode));

//        String mobile = "+41446681800";
//        String formatMobile = PhoneUtil.formatMobile(mobile);
//        if (!PhoneUtil.verifyPhoneNumberFormat(formatMobile)) {
//            System.out.println("error");
//        } else {
//            System.out.println("success");
//        }
//
//        String MOBILE_FORMAT_MATCHER = "(?:\\(?[0\\+]?\\d{1,3}\\)?)[\\s-]?(?:0|\\d{1,4})[\\s-]?(?:(?:13\\d{9})|(?:\\d{7,8}))";
//
//        //String mobile = "003-626-2287211";
//        System.out.println(mobile.matches(MOBILE_FORMAT_MATCHER));
//
//        String[] phones = new String[]{"1523620111", "11011363254", "15811363254", "15811364216", "15811364216",
//                "13011111111", "15811364216", "022-6232903-22", "022-6232903", "+8615811364216", "8615811224181", "17043123221", "007-007972", "17701450908"};
//        for (String phone : phones) {
//            System.out.print(phone + "  ");
//            System.out.println(PhoneUtil.verifyPhoneNumberFormat(phone));
//        }
//
//        String phone = "17701450908";
//        if (phone.length() > PhoneUtil.PHONE_LENTH) {
//            phone = phone.substring(phone.length() - PhoneUtil.PHONE_LENTH);
//            System.out.println(phone);
//        }
//
//        // String mobile = "1";
//        mobile = formatMobile(mobile);
//        System.out.println(mobile + "---> " + mobile.length());
//
//        String swissNumberStr = "13718293537";
        PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
//        Phonenumber.PhoneNumber p = phoneUtil.getExampleNumber("IQ");
//        System.out.println(p.toString());
        // 根据国家代码获取相应的国际域名缩写
//        String US = phoneUtil.getRegionCodeForCountryCode(+1);
//        System.out.println(US);
//        String usPhone = "4168716969";
//        formatNationalMobile(usPhone, US);

//        //根据国际域名缩写获取国家代码
//        int cc = phoneUtil.getCountryCodeForRegion("IQ");
//        Phonenumber.PhoneNumber phoneNumber1 = phoneUtil.getExampleNumber("CG");
//        System.out.println(phoneNumber1.toString());
//
//        Phonenumber.PhoneNumber phoneNumber2 = phoneUtil.getExampleNumber("AU");
//        System.out.println(phoneNumber2.toString());
//
//        Phonenumber.PhoneNumber phoneNumber3 = phoneUtil.getExampleNumber("CN");
//        System.out.println(phoneNumber3.toString());
//
//        Phonenumber.PhoneNumber phoneNumber4 = phoneUtil.getExampleNumber("CH");
//        System.out.println(phoneNumber4.toString());
//
//
//
//        System.out.println("countryCode:" + cc);
//        System.out.println(rc);
//        System.out.println(formatNationalMobile(mobile));
        System.out.print("this is mobile:"+isTestMobile("28000000000"));
    }

}
