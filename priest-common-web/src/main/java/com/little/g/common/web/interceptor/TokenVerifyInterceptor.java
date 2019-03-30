package com.little.g.common.web.interceptor;

import com.google.common.base.Strings;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.little.g.common.CommonConstants;
import com.little.g.common.CommonErrorCodes;
import com.little.g.common.dto.TokenInfo;
import com.little.g.common.enums.TokenErrorType;
import com.little.g.common.enums.TokenVersion;
import com.little.g.common.exception.ServiceDataException;
import com.little.g.common.utils.Base62Util;
import com.little.g.user.dto.TokenCache;
import com.little.g.user.api.TokenService;
import com.little.g.user.token.TokenCommonUtil;
import com.little.g.user.token.TokenVersionConfig;
import com.little.g.user.token.TokenVersionFactory;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by lengligang on 2019/3/30.
 */
public class TokenVerifyInterceptor  extends HandlerInterceptorAdapter {

    @Resource
    private TokenService tokenService;

    private static final Logger log = LoggerFactory.getLogger(TokenVerifyInterceptor.class);

    private LoadingCache<String,TokenCache> cache = CacheBuilder.newBuilder()
            .maximumSize(100000)
            .expireAfterWrite(10, TimeUnit.MINUTES)
            .build(new CacheLoader<String, TokenCache>() {
                @Override
                public TokenCache load(String s) throws Exception {
                    if(StringUtils.isEmpty(s)) return null;
                    String[] credentials=s.split("_");
                    String token=credentials[0];
                    String deviceId=credentials[1];

                    return tokenService.verify(deviceId,token);
                }
            });

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        /*用户Token */
        String token = request.getHeader("token");

        /* 设备ID */
        String deviceId = request.getHeader("deviceId");

        if(StringUtils.isEmpty(token) || StringUtils.isEmpty(deviceId)){
            throw new ServiceDataException(CommonErrorCodes.NOT_LOGIN,"msg.user.not.login");
        }

        TokenInfo tokenInfo=checkToken(token,deviceId);
        if(tokenInfo == null || !tokenInfo.isOK()){
            throw new ServiceDataException(CommonErrorCodes.NOT_LOGIN,"msg.user.not.login");
        }
        String localKey = String.format("%s_%s",token,deviceId);

        TokenCache tokenCache=cache.get(localKey);
        if(tokenCache != null && tokenCache.isLogin()){
            return true;
        }


        throw new ServiceDataException(CommonErrorCodes.NOT_LOGIN,"msg.user.not.login");
    }


    /**
     * 检测token是否正确
     * 1.校验版本
     * 2.自校验是否正确
     * 3.是否过有效期
     *
     * @param token    token
     * @param deviceId deviceId
     * @return token的有效性对象
     */
    public static TokenInfo checkToken(String token, String deviceId) throws ServiceDataException {
        //检测token是否为空
        if (Strings.isNullOrEmpty(token) || token.length() > 30) {
            log.warn("[checkToken]param is error,token:{},deviceId:{}", token, deviceId);
            return null;
        }
        TokenInfo ti = new TokenInfo();
        //校验版本是否是支持的版本
        if (!checkVersion(token)) {
            log.warn("checkToken,version error,token:{},deviceId:{}", token, deviceId);
            ti.setOK(false);
            ti.setErrorStatus(TokenErrorType.VERSION.getValue());
            return ti;
        }
        //token自校验
        try {
            if (!checkTokenSelf(token)) {
                log.warn("checkToken,token self error,token:{},deviceId:{}", token, deviceId);
                ti.setOK(false);
                ti.setErrorStatus(TokenErrorType.SELF_CHECK.getValue());
                return ti;
            }
        } catch (IOException e) {
            log.error("checkToken IOException exception.token:{},deviceId:{},error:{}", token, deviceId, e);
            return null;
        }
        //token是否过有效期
        if (!checkTokenExpDate(token)) {
            ti.setOK(false);
            ti.setErrorStatus(TokenErrorType.EXPIRED.getValue());
            log.warn("checkToken,token expired error,token:{},deviceId:{}", token, deviceId);
            return ti;
        }

        ti.setOK(true);
        ti.setUid(calculateUid(token));
        ti.setVersion(TokenVersion.VERSION_2.getValue());

        return ti;
    }

    private static Long calculateUid(String token) {
        byte[] sidBytes = Base62Util.base62Decode(token.toCharArray());
        //取token中间8字节的组合串
        byte[] uByte = new byte[8];
        System.arraycopy(sidBytes, 5, uByte, 0, uByte.length);
        //取第一个字节为长度
        int length = (int) uByte[0];
        byte[] iByte = new byte[length];
        System.arraycopy(uByte, 1, iByte, 0, length);
        return TokenCommonUtil.bytes2long(iByte, length);
    }


    /**
     * 检测token是否还在有效期内
     *
     * @param token accessToken
     * @return 有效期校验是否通过
     */
    public static boolean checkTokenExpDate(String token) {
        Date createDate = getDate(token);
        long createDateTime = createDate.getTime();
        long nowTime = new Date().getTime();
        return createDateTime + CommonConstants.ACCESS_TOKEN_EXPIRSED > nowTime;
    }

    /**
     * 从token中取出创建时间
     *
     * @param token token
     * @return token的创建时间
     */
    public static Date getDate(String token) {
        byte[] sidBytes = Base62Util.base62Decode(token.toCharArray());
        byte[] timeBytes = new byte[4];
        System.arraycopy(sidBytes, 1, timeBytes, 0, timeBytes.length);
        int time = TokenCommonUtil.bytes2Int(timeBytes);
        return TokenCommonUtil.secondTimeToDate(time);
    }


    /**
     * 校验token的版本是否是支持的版本
     *
     * @param token token
     * @return 版本校验是否通过
     */
    private static boolean checkVersion(String token) {
        byte[] sidBytes = Base62Util.base62Decode(token.toCharArray());
        int v = (int) sidBytes[0];
        return TokenVersion.checkKeyIsExist(String.valueOf(v));
    }


    /**
     * 检测token的校验位是否正确
     *
     * @param token token
     * @return 校验位校验是否通过
     */
    private static boolean checkTokenSelf(String token) throws IOException {
        byte[] sidBytes = Base62Util.base62Decode(token.toCharArray());
        //取token中后4字节的自校验位
        byte[] checkByte = new byte[4];
        System.arraycopy(sidBytes, sidBytes.length - 4, checkByte, 0, checkByte.length);
        int sidCheckInt = TokenCommonUtil.bytes2Int(checkByte);

        //取前面剩余字节并根据不同版本的配置对应的密钥做MD5
        byte[] sidCheckBytes = new byte[sidBytes.length - 4];
        System.arraycopy(sidBytes, 0, sidCheckBytes, 0, sidCheckBytes.length);

        int version = (int) sidBytes[0];
        TokenVersionConfig tokenVersionConfig = TokenVersionFactory.getTokenConfig(version);
        String check_token_key = tokenVersionConfig.getCheck_token_key();
        String check = tokenVersionConfig.getCheck();
        String[] c = check.split(",");
        int i1 = Integer.valueOf(c[0]);
        int i2 = Integer.valueOf(c[1]);
        int i3 = Integer.valueOf(c[2]);
        int i4 = Integer.valueOf(c[3]);

        byte[] sidMd5Bytes = TokenCommonUtil.byteMerger(sidCheckBytes, check_token_key.getBytes());
        sidMd5Bytes = TokenCommonUtil.calculateMD5(sidMd5Bytes);
        //根据配置读MD5后的相应元素组成自校验位
        byte[] calculateCheckByte = new byte[]{sidMd5Bytes[i1], sidMd5Bytes[i2], sidMd5Bytes[i3], sidMd5Bytes[i4]};
        int calculateCheckInt = TokenCommonUtil.bytes2Int(calculateCheckByte);
        //比较是否相等
        return calculateCheckInt == sidCheckInt;
    }
}
