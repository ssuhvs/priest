package com.little.g.pay.service.impl;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.little.g.common.exception.ServiceDataException;
import com.little.g.pay.PayErrorCodes;
import com.little.g.pay.api.TransactionService;
import com.little.g.pay.dto.*;
import com.little.g.pay.enums.BusinessType;
import com.little.g.pay.enums.FixAccount;
import com.little.g.pay.enums.TransactionType;
import com.little.g.pay.mapper.BalanceCheckpointMapper;
import com.little.g.pay.mapper.TransactionRecordMapper;
import com.little.g.pay.model.BalanceCheckpoint;
import com.little.g.pay.model.BalanceCheckpointExample;
import com.little.g.pay.model.TransactionRecord;
import com.little.g.pay.model.TransactionRecordExample;
import com.little.g.pay.service.api.AccountAdaptor;
import com.little.g.pay.utils.Accounts;
import com.little.g.pay.utils.TransactionNumUtil;
import com.little.g.user.api.UserService;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service("transactionService")
public class TransactionServiceImpl implements TransactionService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TransactionServiceImpl.class);

    @Resource
    private TransactionRecordMapper transactionRecordMapper;

    @Resource
    private BalanceCheckpointMapper balanceCheckpointMapper;

    @Resource
    private AccountAdaptor userAccountAdaptor;

    @Resource
    private AccountAdaptor frozenAccountAdaptor;

    @Resource
    private AccountAdaptor fixAccountAdaptor;


    @Resource
    private UserService userService;


    @Override
    public void createAccount(Account account) {
        AccountAdaptor adaptor = getAccountAdaptor(account);
        adaptor.init(account);
    }

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
    public List<TransactionRecordDTO> findTransaction(String transNum, Account from, Account to) {
        TransactionRecordExample example = new TransactionRecordExample();
        example.or().andTradeNumEqualTo(transNum);

        List<TransactionRecord> existsTransDetails = transactionRecordMapper.selectByExample(example);
        if (existsTransDetails.size() != 2) {
            throw new ServiceDataException(PayErrorCodes.PAY_ERROR,"msg.account.transaction.record.error");
        }

        // 校验交易是否发生在指定账户间
        TransactionRecord td = existsTransDetails.get(0);
        if ((td.getAccountId().equals(from.getId())
                && td.getType() == TransactionType.OUTCOME.getValue().byteValue()
                && td.getOpposite().equals(to.getId())) ||
                (td.getAccountId().equals(to.getId())
                        && td.getType() == TransactionType.INCOME.getValue().byteValue()
                        && td.getOpposite().equals(from.getId()))) {
            return createTransactionDTO(existsTransDetails);
        }

        return null;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, noRollbackFor = DuplicateKeyException.class)
    public List<TransactionRecordDTO> transfer(Account from, Account to, long amount, String transNum, BusinessType btype, String desc) {
        AccountAdaptor<Account> fromAdaptor = getAccountAdaptor(from);
        AccountAdaptor<Account> toAdaptor = getAccountAdaptor(to);

        String fromId = from.getId();
        String toId = to.getId();

        List<TransactionRecordDTO> ret;
        try {
            ret = logTransaction(fromId, toId, transNum, amount, btype, desc);
        } catch (DuplicateKeyException e) {
            LOGGER.debug("Duplicated tradeNum detected", e);

            List<TransactionRecordDTO> v = findTransaction(transNum, from, to);
            if (v != null) {
                return v;
            }
            throw new ServiceDataException(PayErrorCodes.PAY_ERROR);
        }

        // avoid deadlock
        if (fromId.compareTo(toId) > 0) {
            fromAdaptor.debit(from, amount, to);
            toAdaptor.credit(to, amount, from);
        } else {
            toAdaptor.credit(to, amount, from);
            fromAdaptor.debit(from, amount, to);
        }

        return ret;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
    public List<TransactionRecordDTO> transferAll(Account from, Account to, String transNum, BusinessType btype, String desc) {
        AccountAdaptor fromAdaptor = getAccountAdaptor(from);
        fromAdaptor.lock(from);

        long all = fromAdaptor.getBalance(from);
        if (all <= 0) {
            // 如果是业务重试，直接返回会导致业务出错, 因为该transferAll可能已经执行过了，这里需要检查一下指定交易是否存在
            return findTransaction(transNum, from, to);
        }

        return transfer(from, to, all, transNum, btype, desc);
    }

    private List<TransactionRecordDTO> logTransaction(String fromId, String toId, String transNum, long amount, BusinessType btype, String desc) {
        // log transaction
        TransactionRecord a = new TransactionRecord();
        a.setAccountId(fromId);
        a.setOpposite(toId);
        a.setType(TransactionType.OUTCOME.getValue());
        a.setMoney(-amount);
        a.setTranNum(TransactionNumUtil.generateTranNum());
        a.setTradeNum(transNum);
        a.setBtype(btype.getValue());
        a.setComment(desc);
        a.setCreateTime(System.currentTimeMillis());

        TransactionRecord b = new TransactionRecord();
        b.setAccountId(toId);
        b.setOpposite(fromId);
        b.setType(TransactionType.INCOME.getValue());
        b.setMoney(amount);
        b.setTranNum(TransactionNumUtil.generateTranNum());
        b.setTradeNum(transNum);
        b.setBtype(btype.getValue());
        b.setComment(desc);
        b.setCreateTime(System.currentTimeMillis());

        transactionRecordMapper.insert(a);
        transactionRecordMapper.insert(b);
        return createTransactionDTO(Lists.newArrayList(a, b));
    }

    @Override
    @Transactional
    public long getBalance(Account account) {
        AccountAdaptor<Account> accountAdaptor = getAccountAdaptor(account);
        return accountAdaptor.getBalance(account);
    }

    @Override
    public AccountDetailDTO getAccountDetail(Account account) {
        AccountAdaptor<Account> accountAdaptor = getAccountAdaptor(account);
        return accountAdaptor.getDetail(account);
    }

    @Transactional
    public CheckBalanceResultDTO checkBalance(Account account, boolean useCheckpoint, boolean saveCheckpoint) {
        AccountAdaptor<Account> accountAdaptor = getAccountAdaptor(account);

        long balance = accountAdaptor.getBalance(account);

        CheckBalanceResultDTO ret = new CheckBalanceResultDTO();

        BalanceCheckpoint checkpoint = useCheckpoint ? getBalanceCheckpoint(account.getId()) : null;
        LOGGER.info("Using checkpoint. checkpoint={}", checkpoint);

        TransactionRecordExample example = new TransactionRecordExample();
        TransactionRecordExample.Criteria criteria = example.or();
        criteria.andAccountIdEqualTo(account.getId());
        if (checkpoint != null) {
            criteria.andIdGreaterThan(checkpoint.getMaxId());
        }
        example.setOrderByClause("id asc");

        List<TransactionRecord> logs = transactionRecordMapper.selectByExample(example);
        long expectedBalance = 0;
        if (checkpoint != null) {
            expectedBalance = checkpoint.getBalance();
        }
        LOGGER.info("Check balance start. uid={} expectedBalance={}", account.getId(), expectedBalance);

        long maxId = 0;
        for (TransactionRecord log : logs) {
            TransactionType t = TransactionType.valueOf(log.getType());
            maxId = log.getId();
            switch (t) {
                case INCOME:
                case OUTCOME:
                    expectedBalance += log.getMoney();
                    break;
                default:
                    throw new ServiceDataException(1);
            }
        }

        ret.setMoneyDifference(balance - expectedBalance);
        LOGGER.info("Check balance end. account={} expectedBalance={} balance={} logs={}",
                 account, expectedBalance, balance, logs.size());

        if (expectedBalance < 0) {
            LOGGER.warn("Check balance found -. account={} expectedBalance={} balance={} logs={}", account, expectedBalance, balance, logs.size());
        }

        if (saveCheckpoint && !logs.isEmpty() && (ret.getMoneyDifference() == 0)) {
            //save checkpoint
            if (checkpoint == null) {
                checkpoint = new BalanceCheckpoint();
                checkpoint.setAccountId(account.getId());
            }

            checkpoint.setMaxId(maxId);
            checkpoint.setBalance(expectedBalance);
            checkpoint.setUpdateTime(System.currentTimeMillis());
            if (checkpoint.getId() == null) {
                balanceCheckpointMapper.insert(checkpoint);
            } else {
                balanceCheckpointMapper.updateByPrimaryKey(checkpoint);
            }
            LOGGER.info("Check balance updated checkpoint. checkpoint={}", checkpoint);
        }

        LOGGER.info("Check balance. account={} expectedMoney={} checkPoint={}", account, expectedBalance, checkpoint);
        return ret;
    }

    private BalanceCheckpoint getBalanceCheckpoint(String accountId) {
        // lookup checkpoint
        BalanceCheckpointExample example = new BalanceCheckpointExample();
        example.or()
                .andAccountIdEqualTo(accountId);
        List<BalanceCheckpoint> list = balanceCheckpointMapper.selectByExample(example);
        if (!list.isEmpty()) {
            return list.get(0);

        }

        return null;
    }

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
    public List<TransactionRecordDTO> strikeBalance(Account account) {
        Preconditions.checkState(account instanceof NormalUserAccount);

        CheckBalanceResultDTO result = checkBalance(account, true, true);

        if (result.getMoneyDifference() == 0) {
            return Collections.emptyList();
        }

        // 补充流水, 平帐
        List<TransactionRecordDTO> ret = new ArrayList<>();
        if (result.getMoneyDifference() > 0) { // 余额大于流水
            String trans1 = TransactionNumUtil.generate(TransactionNumUtil.PREFIX_STRIKE_A_BALANCE);
            ret.addAll(logTransaction(FixAccount.LITTLE_G.getAccount().getId(), account.getId(), trans1, result.getMoneyDifference(), BusinessType.STRIKE_BALANCE, "冲帐"));

            String trans2 = TransactionNumUtil.generate(TransactionNumUtil.PREFIX_STRIKE_A_BALANCE);
            ret.addAll(transfer(account, FixAccount.LITTLE_G.getAccount(), result.getMoneyDifference(), trans2, BusinessType.STRIKE_BALANCE, "冲帐"));


        } else {  // 余额小于流水
            String trans1 = TransactionNumUtil.generate(TransactionNumUtil.PREFIX_STRIKE_A_BALANCE);
            ret.addAll(logTransaction(account.getId(), FixAccount.LITTLE_G.getAccount().getId(), trans1, -result.getMoneyDifference(), BusinessType.STRIKE_BALANCE, "冲帐"));

            String trans2 = TransactionNumUtil.generate(TransactionNumUtil.PREFIX_STRIKE_A_BALANCE);
            ret.addAll(transfer(FixAccount.LITTLE_G.getAccount(), account, result.getMoneyDifference(), trans2, BusinessType.STRIKE_BALANCE, "冲帐"));
        }

        return ret;
    }

    private AccountAdaptor getAccountAdaptor(Account account) {
        if (account.getClass() == NormalUserAccount.class) {
            return userAccountAdaptor;
        } else if (account.getClass() == FrozenAccount.class) {
            return frozenAccountAdaptor;
        } else if (account.getClass() == com.little.g.pay.dto.FixAccount.class) {
            return fixAccountAdaptor;
        }

        LOGGER.error("Unable to find AccountAdaptor for account={}", account);
        throw new ServiceDataException(PayErrorCodes.PAY_ERROR);
    }


    private List<TransactionRecordDTO> createTransactionDTO(List<TransactionRecord> modelList) {
        List<TransactionRecordDTO> ret = new ArrayList<>();
        for (TransactionRecord td : modelList) {
            TransactionRecordDTO dto = new TransactionRecordDTO();
            BeanUtils.copyProperties(td, dto);
            ret.add(dto);
        }
        return ret;
    }

    @Override
    public List<TransactionRecordDTO> queryTransactionDetailByTransNum(String transNum) {
        TransactionRecordExample example = new TransactionRecordExample();
        TransactionRecordExample.Criteria criteria = example.createCriteria();
        criteria.andTradeNumEqualTo(transNum);
        List<TransactionRecord> transactionDetailList = transactionRecordMapper.selectByExample(example);
        return copyTransactionDetailProperties(transactionDetailList);
    }

    @Override
    public List<TransactionRecordDTO> listTransactions(Account account, Long startTime, Long endTime, Integer limit) {
        if (!(account instanceof AccountHasUserId)) {
            return Collections.emptyList();
        }

        TransactionRecordExample example = new TransactionRecordExample();
        TransactionRecordExample.Criteria criteria = example.createCriteria();

        criteria.andAccountIdEqualTo(account.getId());

        if (startTime != null && startTime > 0) {
            criteria.andCreateTimeGreaterThan(startTime);
        }
        if (endTime != null && endTime > 0) {
            criteria.andCreateTimeLessThan(endTime);
        }

        List<TransactionRecord> transactionDetailList;
        if (limit != null) {
            transactionDetailList = transactionRecordMapper.selectByExampleWithRowbounds(example, new RowBounds(0, limit));
        } else {
            transactionDetailList = transactionRecordMapper.selectByExample(example);
        }
        return copyTransactionDetailProperties(transactionDetailList);
    }




    private List<TransactionRecordDTO> copyTransactionDetailProperties(List<TransactionRecord> transactionDetailList) {
        List<TransactionRecordDTO> transactionDetailDTOList = new ArrayList<>();
        for (TransactionRecord transactionDetail : transactionDetailList) {
            transactionDetailDTOList.add(copyTransactionDetailProperties(transactionDetail));
        }
        return transactionDetailDTOList;
    }

    private TransactionRecordDTO copyTransactionDetailProperties(TransactionRecord td) {
        TransactionRecordDTO transactionDetailDTO = new TransactionRecordDTO();
        BeanUtils.copyProperties(td, transactionDetailDTO);

        Account account = Accounts.parse(td.getAccountId());
        if (account instanceof AccountHasUserId) {
            AccountHasUserId hasUserId = (AccountHasUserId) account;
            //UserDTO userInfoDTO = userService.getUserById(hasUserId.getUid());
        }

        return transactionDetailDTO;
    }

}
