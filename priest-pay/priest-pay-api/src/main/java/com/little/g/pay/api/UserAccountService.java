package com.little.g.pay.api;


import com.little.g.pay.dto.UserAccountDTO;
import com.little.g.pay.enums.AccountStatus;

/**
 * Created by lengligang on 16/1/18.
 * user account service for money change;
 */
public interface UserAccountService {
    /**
     * create user account
     *
     * @param uid uid
     * @return if success
     */
    boolean createUserAccount(Long uid);

    /**
     * update user account status
     * @param  uid uid
     * @param accountStatus user status enum;
     * @return
     */
    boolean updateAccountStatus(Long uid, AccountStatus accountStatus, String reason);

    /**
     * query user account
     * @param uid userId
     * @return userAccountStatus
     */
    UserAccountDTO queryUserAccount(Long uid);

}
