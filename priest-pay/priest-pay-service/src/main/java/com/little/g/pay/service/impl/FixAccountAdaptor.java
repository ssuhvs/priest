package com.little.g.pay.service.impl;

import com.little.g.common.exception.ServiceDataException;
import com.little.g.pay.PayErrorCodes;
import com.little.g.pay.dto.Account;
import com.little.g.pay.dto.AccountDetailDTO;
import com.little.g.pay.dto.FixAccount;
import com.little.g.pay.service.api.AccountAdaptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


/**
 * 固定帐号允许转出, 但不允许转入
 * Created by zhaoyao on 16/5/9.
 */
@Component("fixAccountAdaptor")
public class FixAccountAdaptor implements AccountAdaptor<FixAccount> {

    private static final Logger LOGGER = LoggerFactory.getLogger(FixAccountAdaptor.class);

    @Override
    public long getBalance(FixAccount account) {
        return 0;
    }

    @Override
    public AccountDetailDTO getDetail(FixAccount account) {
        // 固定账户没有各种状态, 简单处理
        AccountDetailDTO detailDTO = new AccountDetailDTO();
        detailDTO.setAccount(account);
        return detailDTO;
    }

    @Override
    public void credit(FixAccount account, long amount, Account from) {
        LOGGER.info("credit. account={} amount={} from={}",account, amount, from);
        if (!account.isAllowCredit()) {
            throw new ServiceDataException(PayErrorCodes.PAY_ERROR,"msg.fixaccount.notallowed.credit");
        }
    }

    @Override
    public void debit(FixAccount account, long amount, Account to) {
        LOGGER.info("debit.  account={} amount={} to={}", account, amount, to);
    }

    @Override
    public void init(FixAccount account) {

    }

    @Override
    public void lock(FixAccount account) {
        // do nothing
    }
}
