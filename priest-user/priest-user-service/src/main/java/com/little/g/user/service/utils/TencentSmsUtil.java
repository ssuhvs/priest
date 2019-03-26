package com.little.g.user.service.utils;


import com.github.qcloudsms.SmsSingleSender;
import com.github.qcloudsms.SmsSingleSenderResult;
import com.github.qcloudsms.httpclient.PoolingHTTPClient;
import com.little.g.common.ResultJson;
import com.little.g.common.exception.ServiceDataException;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by lengligang on 2019/1/10.
 */
public class TencentSmsUtil {
    private static  final Logger log = LoggerFactory.getLogger(TencentSmsUtil.class);
    private static int appid=1400178231;
    private static String appkey="3629394d9c68978225d41104dc2d8737";
    private   static int templateId =262634;
    private  static String smsSign="popo泡泡";

    static PoolingHTTPClient httpclient = new PoolingHTTPClient(10);

    private  static SmsSingleSender ssender = new SmsSingleSender(appid, appkey,httpclient);


    public static String sendSms(String mobile,String[]params){
        return sendSms(null,mobile,null, params);
    }

    public static String sendSms(String nationCode,String mobile,Integer templateId,String[]params){
        if(StringUtils.isEmpty(nationCode)){
            nationCode="86";
        }
        if(StringUtils.isEmpty(mobile)){
            throw new ServiceDataException(ResultJson.INVALID_PARAM);
        }
        if(templateId == null || templateId <= 0){
            templateId=TencentSmsUtil.templateId;
        }

        try {
            SmsSingleSenderResult result = ssender.sendWithParam(nationCode, mobile,
                    templateId, params, smsSign, "", "");  // 签名参数未提供或者为空时，会使用默认签名发送短信
            if(StringUtils.isNotEmpty(result.errMsg)){
                log.error("send msg error result:{}", ToStringBuilder.reflectionToString(result));
                return "";
            }
            return  result.sid;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
