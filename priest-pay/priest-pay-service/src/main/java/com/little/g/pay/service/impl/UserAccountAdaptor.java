package com.little.g.pay.service.impl;

import com.google.common.base.Preconditions;
import com.little.g.common.ResultJson;
import com.little.g.common.exception.ServiceDataException;
import com.little.g.pay.PayErrorCodes;
import com.little.g.pay.dto.Account;
import com.little.g.pay.dto.AccountDetailDTO;
import com.little.g.pay.dto.NormalUserAccount;
import com.little.g.pay.enums.AccountStatus;
import com.little.g.pay.mapper.UserAccountMapper;
import com.little.g.pay.mapper.UserAccountMapperExt;
import com.little.g.pay.model.UserAccount;
import com.little.g.pay.model.UserAccountExample;
import com.little.g.pay.service.api.AccountAdaptor;
import com.little.g.user.UserErrorCodes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by zhaoyao on 16/5/9.
 */
@Component("userAccountAdaptor")
public class UserAccountAdaptor implements AccountAdaptor<NormalUserAccount> {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserAccountAdaptor.class);

    @Resource
    private UserAccountMapper userAccountMapper;

    @Resource
    private UserAccountMapperExt userAccountMapperExt;

    @Override
    public long getBalance(NormalUserAccount account) {
        UserAccount model = findModel(account);
        return model.getMoney();
    }

    @Override
    public AccountDetailDTO getDetail(NormalUserAccount account) {
        UserAccount model = findModel(account);
        AccountDetailDTO detailDTO = new AccountDetailDTO();
        detailDTO.setAccount(account);
        detailDTO.setBalance(model.getMoney());
        detailDTO.setStatus(model.getStatus());
        detailDTO.setCreateTime(model.getCreateTime());
        detailDTO.setUpdateTime(model.getUpdateTime());
        return detailDTO;
    }

    private UserAccount findModel(NormalUserAccount account) {
        UserAccountExample example = new UserAccountExample();
        example.or().andUidEqualTo(account.getUid());

        List<UserAccount> accounts = userAccountMapper.selectByExample(example);
        if (accounts.isEmpty()) {
            return initAccount(account.getUid());
        }
        return accounts.get(0);
    }

    @Override
    public void credit(NormalUserAccount account, long amount, Account to) {
        doCredit(account, amount, to, false);
    }

    private void doCredit(NormalUserAccount account, long amount, Account to, boolean failOnAccountNotFound) {
        Preconditions.checkState(amount > 0, "credit amount must > 0");

        UserAccount update = new UserAccount();
        update.setMoney(amount);
        update.setUpdateTime(System.currentTimeMillis());

        UserAccountExample example = new UserAccountExample();
        example.or().andUidEqualTo(account.getUid());

        if (userAccountMapperExt.updateMoneySelective(update, example) > 0) {
            return;
        }
        // try init account
        if (failOnAccountNotFound) {
            throw new ServiceDataException(UserErrorCodes.USER_NOT_EXIST,"msg.user.not.exist");
        }

        initAccount(account.getUid());
        doCredit(account, amount, to, true);
    }

    @Override
    public void debit(NormalUserAccount account, long amount, Account from) {
        Preconditions.checkState(amount > 0, "debit amount must > 0");

        UserAccount update = new UserAccount();
        update.setMoney(-amount);
        update.setUpdateTime(System.currentTimeMillis());

        UserAccountExample example = new UserAccountExample();
        example.or()
                .andUidEqualTo(account.getUid())
                .andStatusEqualTo(AccountStatus.INIT.getValue())
                .andMoneyGreaterThanOrEqualTo(amount);

        if (userAccountMapperExt.updateMoneySelective(update, example) > 0) {
            return;
        }

        UserAccountExample exists = new UserAccountExample();
        exists.or().andUidEqualTo(account.getUid());
        List<UserAccount> accounts = userAccountMapper.selectByExample(exists);
        if (accounts.isEmpty()) {
            throw new ServiceDataException(PayErrorCodes.PAY_ERROR,"msg.account.not.exist");
        }

        UserAccount userAccount = accounts.get(0);
        if (userAccount.getMoney() < amount) {
            throw new ServiceDataException(PayErrorCodes.PAY_ERROR,"msg.account.balance.not.enough");
        }

        if (userAccount.getStatus() == AccountStatus.LOCKED.getValue().byteValue()) {
            throw new ServiceDataException(PayErrorCodes.PAY_ERROR,"msg.account.locked");
        }

        throw new ServiceDataException(ResultJson.SYSTEM_UNKNOWN_EXCEPTION);
    }

    @Override
    public void lock(NormalUserAccount account) {
        UserAccountExample example = new UserAccountExample();
        example.or().andUidEqualTo(account.getUid());

        UserAccount update = new UserAccount();
        update.setUpdateTime(System.currentTimeMillis());

        if(userAccountMapper.updateByExampleSelective(update, example) <= 0) {
            throw new ServiceDataException(ResultJson.SYSTEM_UNKNOWN_EXCEPTION);
        }
    }

    @Override
    public void init(NormalUserAccount account) {
        try {
            initAccount(account.getUid());
        } catch (DataIntegrityViolationException e) {
            LOGGER.info("reinit user account. acount={}", account);
        }
    }

    private UserAccount initAccount(long uid) {
        UserAccount account = new UserAccount();
        account.setUid(uid);
        account.setUpdateTime(System.currentTimeMillis());
        account.setCreateTime(System.currentTimeMillis());
        account.setMoney(0L);
        account.setFrozonMoney(0L);
        account.setStatus(AccountStatus.INIT.getValue());
        userAccountMapper.insert(account);
        return account;
    }
}
