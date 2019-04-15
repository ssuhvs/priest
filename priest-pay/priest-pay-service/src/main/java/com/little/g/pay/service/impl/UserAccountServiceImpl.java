package com.little.g.pay.service.impl;

import com.little.g.common.exception.ServiceDataException;
import com.little.g.pay.api.UserAccountService;
import com.little.g.pay.dto.UserAccountDTO;
import com.little.g.pay.enums.AccountStatus;
import com.little.g.pay.mapper.UserAccountMapper;
import com.little.g.pay.model.UserAccount;
import com.little.g.pay.model.UserAccountExample;
import com.little.g.user.UserErrorCodes;
import com.little.g.user.api.UserService;
import com.little.g.user.dto.UserDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * Created by llg on 2019/4/14.
 */
@Service("userAccountService")
public class UserAccountServiceImpl implements UserAccountService {

    private  static final Logger logger = LoggerFactory.getLogger(UserAccountServiceImpl.class);

    @Resource
    private UserAccountMapper userAccountMapper;
    @Resource
    private UserService userService;


    @Override
    public boolean createUserAccount(Long uid) {
        UserAccount userAccount = new UserAccount();
        userAccount.setUid(uid);
        userAccount.setUpdateTime(System.currentTimeMillis());
        userAccount.setCreateTime(System.currentTimeMillis());
        userAccount.setStatus(AccountStatus.INIT.getValue());
        return userAccountMapper.insertSelective(userAccount) > 0;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
    public boolean updateAccountStatus(Long uid, AccountStatus accountStatus, String reason) {
        UserAccountExample example = new UserAccountExample();
        example.or()
                .andUidEqualTo(uid)
                .andStatusNotEqualTo(accountStatus.getValue());
        UserAccount userAccount = new UserAccount();
        userAccount.setStatus(accountStatus.getValue());
        userAccount.setUpdateTime(System.currentTimeMillis());
        int ret = userAccountMapper.updateByExampleSelective(userAccount, example);
        logger.info("updateAccountStatus. uid={} status={} reason={}", uid, accountStatus, reason);
        return ret > 0;
    }

    @Transactional
    @Override
    public UserAccountDTO queryUserAccount(Long uid) {

        UserAccount useraccount = userAccountMapper.selectByPrimaryKey(uid);
        if (useraccount == null) {
            useraccount = initAccount(uid);
        }

        UserAccountDTO account = new UserAccountDTO();
        BeanUtils.copyProperties(useraccount, account);
        return account;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_COMMITTED)
    private UserAccount initAccount(Long uid) {
        UserDTO user = userService.getUserById(uid);
        if (user == null) {
            throw new ServiceDataException(UserErrorCodes.USER_NOT_EXIST,"msg.user.not.exist");
        }

        UserAccount useraccount = new UserAccount();
        useraccount.setUid(uid);
        useraccount.setMoney(0l);
        useraccount.setFrozonMoney(0l);
        useraccount.setUpdateTime(System.currentTimeMillis());
        useraccount.setCreateTime(System.currentTimeMillis());
        useraccount.setStatus(AccountStatus.INIT.getValue());
        userAccountMapper.insertSelective(useraccount);
        return useraccount;
    }
}
