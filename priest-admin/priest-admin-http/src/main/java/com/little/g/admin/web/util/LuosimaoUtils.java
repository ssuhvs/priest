package com.little.g.admin.web.util;


import com.alibaba.fastjson.JSONObject;
import com.little.g.admin.common.utils.HttpUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Zouxf on 2017/7/4.
 */
public class LuosimaoUtils {
    private static final String VERIVY_URL = "https://captcha.luosimao.com/api/site_verify";
    private static final String API_KEY = "9c413a529df1092e0b5f96648b22e2d7";

    private static final Logger logger = LoggerFactory.getLogger(LuosimaoUtils.class);
    public static boolean verifyCode(String code) {

        Map<String, String> params = new HashMap<String, String>();
        params.put("api_key", API_KEY);
        params.put("response", code);

        String responseBody = HttpUtils.post(VERIVY_URL, params, Collections.emptyMap(), "UTF-8");
        logger.info("luosimao:req:{},resp:{}",params,responseBody);

        return org.apache.commons.lang3.StringUtils.equals(JSONObject.parseObject(responseBody, Map.class).get("res").toString(), "success");
    }
}
