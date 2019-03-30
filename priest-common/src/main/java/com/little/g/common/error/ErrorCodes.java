package com.little.g.common.error;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lengligang on 2019/3/12.
 */
public  abstract class ErrorCodes {

    protected static Integer start;
    protected static Integer end;

    private static Map<Integer,String> codeMsgs=new HashMap<>();

    public ErrorCodes(ErrorCodeDiv.CodeBorder border) {
        if(border == null || border.getStart() == null || border.getEnd() == null
                || border.getEnd() < border.getStart()){
            throw new Error("ErrorCodes init failed border:"+border);
        }
        this.start = border.getStart();
        this.end = border.getEnd();
    }

    protected abstract void addCodes();


    public  static void  addCode2Map(Integer code,String msg){
        if(code == null || code <start || code > end){
            throw new Error("error code invalid code:"+code);
        }
        codeMsgs.put(code, msg);
    }
}
