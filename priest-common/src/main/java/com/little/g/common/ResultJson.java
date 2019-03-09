package com.little.g.common;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.module.afterburner.AfterburnerModule;
import com.google.common.collect.Maps;
import org.apache.commons.lang.StringUtils;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;

/**
 * 通用JSON返回类
 */
public class ResultJson {
    private static final ObjectMapper objectMapper;

    static {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new AfterburnerModule());
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        objectMapper.configure(SerializationFeature.WRITE_SINGLE_ELEM_ARRAYS_UNWRAPPED, false);
        //objectMapper.configure(SerializationFeature.WRITE_NULL_MAP_VALUES, false);
    }

    public static final Integer SUCCESSFUL = 0; // 成功

    public static final Integer SYSTEM_UNKNOWN_EXCEPTION = 20000;
    public static final Map<Integer, String> msg = new HashMap<Integer, String>();

    private Integer c = SUCCESSFUL;  //code
    private String m; //message
    private Map<String, Object> d = null;  //data

    private Object data;    //优先使用，为null才使用d

    public Integer getC() {
        return c;
    }

    public void setC(Integer c) {
        this.c = c;
        //ResultCodeI18NAspect 取代这个逻辑
        /*if (m == null) {
            m = MessageHelper.getMessage(c, Locale.CHINA);
        }*/
    }

    public String getM() {
        return StringUtils.defaultIfEmpty(m, msg.get(c));
    }

    public static String getM(int code) {
        return StringUtils.defaultIfEmpty(msg.get(code), "");
    }

    public void setM(String m) {
        this.m = m;
    }

//    public Map<String, Object> getD() {
//        return d;
//    }

    public Object getD() {
        return data == null ? d : data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public void putD(String name, Object value) {
        if (this.d == null) {
            this.d = Maps.newHashMap();
        }

        if (StringUtils.isNotEmpty(name)) {
            this.d.put(name, value);
        }
    }

    public void putAllD(Map<String, Object> maps) {
        if (this.d == null) {
            this.d = Maps.newHashMap();
        }

        if (maps != null && !maps.isEmpty()) {
            this.d.putAll(maps);
        }
    }

    public void putAllArrD(Map<String, String[]> maps) {
        if (this.d == null) {
            this.d = Maps.newHashMap();
        }

        if (maps != null && !maps.isEmpty()) {
            this.d.putAll(maps);
        }
    }


    @Override
    public String toString() {
        try {
            return objectMapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            return "";
        }
    }

    public static void main(String args[]) throws IllegalAccessException, IOException {
        TreeSet<Integer> treeSet = new TreeSet<>();
        Field[] fields = ResultJson.class.getDeclaredFields();
        if (fields != null) {
            for (Field field : fields) {

                field.setAccessible(true);

                if (Modifier.isStatic(field.getModifiers())) {

                    Object o = field.get(null);

                    if (o != null && o instanceof Integer) {
                        treeSet.add((Integer) o);
                    }

                }
            }
        }

        Properties properties = new Properties();
        properties.load(ResultJson.class.getClassLoader().getResourceAsStream("i18n/result_json.properties"));
        Set<Object> keys = properties.keySet();

        for (Integer integer : treeSet) {
            boolean flag = keys.remove(String.valueOf(integer));
            if (!flag) {
                System.out.printf(String.format("%d=%s\n", integer, msg.get(integer)));
            }
        }

//        for (Map.Entry<Integer, String> entry : msg.entrySet()) {
//            if (entry.getKey() == 0) {
//                continue;
//            }
//            properties.put(String.valueOf(entry.getKey()), entry.getValue());
//        }
//
//        properties.store(new FileOutputStream(new File("priest-common/src/main/resources/i18n/result_json.properties")), "ResultJson国际化信息");
    }
}