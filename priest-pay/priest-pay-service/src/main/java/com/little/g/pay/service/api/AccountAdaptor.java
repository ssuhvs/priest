package com.little.g.pay.service.api;

import com.little.g.pay.dto.Account;
import com.little.g.pay.dto.AccountDetailDTO;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by zhaoyao on 16/5/9.
 */
public interface AccountAdaptor<T extends Account> {

    @Transactional(propagation = Propagation.MANDATORY, isolation = Isolation.READ_COMMITTED)
    long getBalance(T account);

    @Transactional(propagation = Propagation.MANDATORY, isolation = Isolation.READ_COMMITTED)
    AccountDetailDTO getDetail(T account);

    /**
     * 转入
     * @param account
     * @param amount
     */
    @Transactional(propagation = Propagation.MANDATORY, isolation = Isolation.READ_COMMITTED)
    void credit(T account, long amount, Account to);

    /**
     * 转出
     * @param account
     * @param amount
     */
    @Transactional(propagation = Propagation.MANDATORY, isolation = Isolation.READ_COMMITTED)
    void debit(T account, long amount, Account from);

    @Transactional(propagation = Propagation.MANDATORY, isolation = Isolation.READ_COMMITTED)
    void init(T account);

    /**
     * 锁定指定帐号, 保证不同事务间, 不会并行操作账户
     * @param account
     */
    @Transactional(propagation = Propagation.MANDATORY, isolation = Isolation.READ_COMMITTED)
    void lock(T account);
}
