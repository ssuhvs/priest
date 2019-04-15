package com.little.g.pay.utils;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.little.g.pay.dto.Account;
import com.little.g.pay.dto.AccountHasUserId;
import com.little.g.pay.dto.NormalUserAccount;

/**
 * Created by zhaoyao on 16/5/12.
 */
public class Accounts {

    public static final String NORMAL_USER_PREFIX = "UNR";

    public static NormalUserAccount parseUserAccount(String accountId) {
        Preconditions.checkArgument(accountId.startsWith(NORMAL_USER_PREFIX));
        return new NormalUserAccount(Long.parseLong(accountId.substring(3)));
    }

    public static Account parse(String accountId) {
        if (accountId.startsWith(NORMAL_USER_PREFIX)) {
            return new NormalUserAccount(Long.parseLong(accountId.substring(3)));
        }
        return null;
    }

    public static AccountHasUserId newAccount(String type, long uid) {
        switch (Strings.nullToEmpty(type)) {
            case NORMAL_USER_PREFIX:
                return new NormalUserAccount(uid);
            default: // 兼容老接口
                return new NormalUserAccount(uid);
        }
    }
}
