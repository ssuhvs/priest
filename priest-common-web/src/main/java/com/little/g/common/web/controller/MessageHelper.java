package com.little.g.common.web.controller;

import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.little.g.common.ResultJson;
import org.apache.dubbo.rpc.RpcException;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import javax.annotation.PostConstruct;
import java.lang.reflect.Field;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

/**
 * Created by wangzhen on 5/17/16.
 */
@Component
public class MessageHelper {
    private static Logger logger = LoggerFactory.getLogger(MessageHelper.class);

    private static final String KINSTALK_ROOT_PACKAGE = "com.kinstalk";
    private static final Map<Integer, ResultJson> RESULT_JSON_MAP = Maps.newConcurrentMap();

    @Autowired
    private MessageSource messageSource;

    private Set<Integer> codes;

    @PostConstruct
    public void init() {
        codes = initResultJsonErrorCode();

        logger.info("<<<<<<<=========================>>>>>>>>>");
        logger.info("init messageSource:{}, id:{}", messageSource, System.identityHashCode(messageSource));
        logger.info("init result json error code ,{}", codes);
        logger.info("<<<<<<<=========================>>>>>>>>>");
    }

    public ResultJson getCachedResultJson(Integer code, Locale locale) {
        return getCachedResultJson(code, null, locale);
    }

    public synchronized ResultJson getCachedResultJson(Integer code, String msg, Locale locale) {
        ResultJson resultJson = RESULT_JSON_MAP.get(code);

        if (resultJson == null && codes.contains(code)) {
            // map ENSURE unique entry
            resultJson = new ResultJson();
            resultJson.setC(code);// code must in range of result json definition
            if (msg == null
                    || Strings.isNullOrEmpty(msg.trim())) {
                String msgLocal = getMessage(code, locale);
                logger.info("messageSource get code:{} msg:{} locale:{} ", code, msgLocal, locale);
                resultJson.setM(msgLocal);
            } else {
                logger.info("set custom result code:{} msg:{} locale:{}", code, msg, locale);
                resultJson.setM(msg);
            }

            logger.info("load one error code ,code:{} old msg:{} msg:{}", code, msg, resultJson.getM());

            RESULT_JSON_MAP.put(code, resultJson);
        }

        if (resultJson != null) {
            logger.info("get one error code ,code:{} msg:{}", code, resultJson.getM());
        }

        return resultJson;
    }

    private Set<Integer> initResultJsonErrorCode() {
        final Set<Integer> codes = Sets.newTreeSet();

        getCodes(ResultJson.class, codes);
        getCodes(RpcException.class, codes);

        return codes;
    }

    /**
     * get codec ,default com.kinstalk
     *
     * @param baseClazz base Class
     * @param codes     codec
     * @param <T>       generics type
     */
    private <T> void getCodes(Class<T> baseClazz, final Set<Integer> codes) {
        getCodes(baseClazz, codes, KINSTALK_ROOT_PACKAGE);
    }

    private <T> void getCodes(Class<T> baseClazz, final Set<Integer> codes, String packageName) {
        Reflections reflections = new Reflections(packageName);

        Set<Class<? extends T>> allTypesFromBase = Sets.newHashSet();

        Set<Class<? extends T>> subTypes = reflections.getSubTypesOf(baseClazz);

        allTypesFromBase.add(baseClazz);
        allTypesFromBase.addAll(subTypes);

        if (allTypesFromBase.size() > 0) {
            for (Class clazz : allTypesFromBase) {
                ReflectionUtils.doWithFields(clazz, new ReflectionUtils.FieldCallback() {
                    @Override
                    public void doWith(Field field) throws IllegalArgumentException, IllegalAccessException {
                        field.setAccessible(true);
                        Object obj = field.get(null);
                        if (obj != null && obj instanceof Integer) {
                            codes.add((Integer) obj);
                        }
                    }
                }, new ReflectionUtils.FieldFilter() {
                    @Override
                    public boolean matches(Field field) {
                        return ReflectionUtils.isPublicStaticFinal(field);
                    }
                });
            }
        }
    }


    /**
     * adaptive "result json" class,invoke getMessage manually
     */
    public String getMessage(int code, Locale locale) {
        return messageSource.getMessage(String.valueOf(code), new Object[]{}, locale);
    }

}
