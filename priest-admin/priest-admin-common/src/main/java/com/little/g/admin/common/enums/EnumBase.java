package com.little.g.admin.common.enums;

/**
 * Enum基础接口
 *
 * @param <O>
 */
public interface EnumBase<O> {

    /**
     * 获取枚举代码
     *
     * @return
     */
    public O getCode();

    /**
     * 获取枚举信息
     *
     * @return
     */
    public String getMessage();

}
