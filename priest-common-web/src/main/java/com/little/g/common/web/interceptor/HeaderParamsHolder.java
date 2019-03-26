package com.little.g.common.web.interceptor;


import com.little.g.common.params.HeaderParams;

/**
 * Created by keysilence on 14-10-24.
 */
public class HeaderParamsHolder {


    public static ThreadLocal<HeaderParams> context = new ThreadLocal<HeaderParams>();



    public static void setHeader(HeaderParams headerParams) {
        synchronized (HeaderParamsHolder.class) {
            context.set(headerParams);
        }
    }

    public static HeaderParams getHeader() {
        HeaderParams header=context.get();
        if (header == null) {
            synchronized (HeaderParamsHolder.class){
                header=context.get();
                if(header!=null){
                    return header;
                }
                header=new HeaderParams();
                context.set(new HeaderParams());
            }
        }
        return header;
    }
}
