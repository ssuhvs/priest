package com.little.g.common;

import com.google.common.collect.Maps;
import org.apache.commons.lang.StringUtils;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 通用JSON返回类
 */
public class ResultJson implements Serializable {

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
    }

    public String getM() {
        return StringUtils.defaultIfEmpty(m, msg.get(c));
    }

    public static String getM(int code) {
        return StringUtils.defaultIfEmpty(msg.get(code), "");
    }

    public ResultJson setM(String m) {
        this.m = m;
        return this;
    }


    public Object getD() {
        return data == null ? d : data;
    }

    public ResultJson setData(Object data) {
        this.data = data;
        return this;
    }

    public ResultJson putD(String name, Object value) {
        if (this.d == null) {
            this.d = Maps.newHashMap();
        }

        if (StringUtils.isNotEmpty(name)) {
            this.d.put(name, value);
        }
        return this;
    }

    public ResultJson putAllD(Map<String, Object> maps) {
        if (this.d == null) {
            this.d = Maps.newHashMap();
        }

        if (maps != null && !maps.isEmpty()) {
            this.d.putAll(maps);
        }
        return this;
    }

    public ResultJson putAllArrD(Map<String, String[]> maps) {
        if (this.d == null) {
            this.d = Maps.newHashMap();
        }

        if (maps != null && !maps.isEmpty()) {
            this.d.putAll(maps);
        }
        return this;
    }


    public static ResultJson newInstance(){
        return new ResultJson();
    }
}