package com.little.g;

import java.io.Serializable;

/**
 * Created by lengligang on 2019/4/4.
 */
public class Attribute implements Serializable {
    /**
     * 属性名
     */
    private String name;
    /**
     * 属性值
     */
    private Object value;
    /**
     * 是否必填
     */
    private boolean required;
    /**
     * 描述
     */
    private String comment;
    /**
     * 是否显示
     */
    private boolean display=true;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public boolean isDisplay() {
        return display;
    }

    public void setDisplay(boolean display) {
        this.display = display;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Attibute{");
        sb.append("name='").append(name).append('\'');
        sb.append(", value=").append(value);
        sb.append(", required=").append(required);
        sb.append(", comment='").append(comment).append('\'');
        sb.append(", display=").append(display);
        sb.append('}');
        return sb.toString();
    }
}
