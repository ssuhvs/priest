package com.little.g.pay.dto;

import java.io.Serializable;

/**
 * 账户抽象
 * <p>
 * Created by zhaoyao on 16/5/9.
 */
public interface Account extends Serializable {

    String getId();

    String getPrefix();
}
