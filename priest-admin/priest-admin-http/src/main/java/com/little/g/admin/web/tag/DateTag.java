package com.little.g.admin.web.tag;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DateTag extends TagSupport {

    private static final long serialVersionUID = 6464168398214506236L;

    private String value;

    private String pattern;

    @Override
    public int doStartTag() throws JspException {
        String vv = "" + value;
        if(StringUtils.isEmpty(pattern)){
            pattern="yyyy-MM-dd HH:mm:ss";
        }
        try {
            if(StringUtils.isEmpty(vv)){
                pageContext.getOut().write("");
                return super.doStartTag();
            }
            long time = Long.valueOf(vv.trim());
            Calendar c = Calendar.getInstance();
            c.setTimeInMillis(time);
            SimpleDateFormat dateformat = new SimpleDateFormat(pattern);
            String s = dateformat.format(c.getTime());
            pageContext.getOut().write(s);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return super.doStartTag();
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }
}