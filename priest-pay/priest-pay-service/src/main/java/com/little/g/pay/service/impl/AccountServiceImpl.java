package com.little.g.pay.service.impl;

import com.google.common.base.Preconditions;
import com.little.g.common.dto.Page;
import com.little.g.pay.api.AccountService;
import com.little.g.pay.api.TransactionService;
import com.little.g.pay.dto.*;
import com.little.g.pay.enums.BusinessType;
import com.little.g.pay.mapper.FrozenRecordMapper;
import com.little.g.pay.model.FrozenRecord;
import com.little.g.pay.model.FrozenRecordExample;
import com.little.g.pay.utils.Accounts;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhaoyao on 16/5/9.
 */
@Service("accountService")
public class AccountServiceImpl implements AccountService {

    @Resource
    private TransactionService transactionService;


    @Resource
    private FrozenRecordMapper frozenRecordMapper;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
    public void createUserAccount(long uid) {
        transactionService.createAccount(new NormalUserAccount(uid));
    }

    @Override
    public long getBalance(Account account, long timeStart, long timeEnd) {
        List<TransactionRecordDTO> allTransactions = transactionService.listTransactions(account, timeStart, timeEnd, null);
        long balance = 0;
        for (TransactionRecordDTO td : allTransactions) {
            balance += td.getMoney();
        }
        return balance;
    }

    @Override
    public List<TransactionRecordDTO> findTransactions(Account account, Long startTime, Long endTime, Integer limit) {
        return transactionService.listTransactions(account, startTime, endTime, limit);
    }

    @Override
    public UserAccountDTO get(Account account) {
        AccountDetailDTO detail = transactionService.getAccountDetail(account);
        if (detail == null) {
            return null;
        }

        UserAccountDTO ret = new UserAccountDTO();
        ret.setMoney(detail.getBalance());
        ret.setStatus((byte) detail.getStatus());
        ret.setCreateTime(detail.getCreateTime());
        ret.setUpdateTime(detail.getUpdateTime());
        if (account instanceof AccountHasUserId) {
            AccountHasUserId accountHasUserId = (AccountHasUserId) account;
            ret.setUid(accountHasUserId.getUid());
        }
        return ret;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
    public List<TransactionRecordDTO> transfer(long fromUid, long toUid, long amount, String transNum, BusinessType btype, String desc) {
        return transactionService.transfer(new NormalUserAccount(fromUid), new NormalUserAccount(toUid), amount, transNum, btype, desc);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
    public List<TransactionRecordDTO> froze(long uid, long amount, String transNum, BusinessType btype, String desc) {
        return transactionService.transfer(new NormalUserAccount(uid), new FrozenAccount(transNum), amount, transNum, btype, desc);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
    public List<TransactionRecordDTO> unfroze(String srcTransNum, long amount, long toUid, String transNum, BusinessType btype, String desc) {
        return transactionService.transfer(new FrozenAccount(srcTransNum), new NormalUserAccount(toUid), amount, transNum, btype, desc);
    }

    @Override
    public Page<FrozenRecordDTO> listFrozenRecords(long uid, int page, int pageSize) {
        Preconditions.checkArgument(uid > 0);
        Preconditions.checkArgument(page >= 0);
        Preconditions.checkArgument(pageSize > 0 && pageSize < 200);

        Page<FrozenRecordDTO> ret = new Page<>(page, pageSize);

        FrozenRecordExample example = new FrozenRecordExample();
        example.or().andAccountIdEqualTo(new NormalUserAccount(uid).getId());

        List<FrozenRecord> records = frozenRecordMapper.selectByExampleWithRowbounds(
                example, new RowBounds(ret.getStartIndex(), pageSize));
        Long total = frozenRecordMapper.countByExample(example);

        List<FrozenRecordDTO> list = new ArrayList<>(records.size());
        for (FrozenRecord record : records) {
            FrozenRecordDTO dto = new FrozenRecordDTO();
            dto.setUid(Accounts.parseUserAccount(record.getAccountId()).getUid());
            dto.setAmount(record.getAmount());
            dto.setBalance(record.getBalance());
            dto.setTradeNum(record.getTradeNum());
            dto.setCreateTime(record.getCreateTime());
            list.add(dto);
        }

        ret.setResult(list);
        ret.setTotalCount(total);
        return ret;
    }

    @Override
    public void createAccount(Account account) {
        transactionService.createAccount(account);
    }

    @Override
    public List<TransactionRecordDTO> transfer(Account from, Account to, long amount, String transNum, BusinessType btype, String desc) {
        return transactionService.transfer(from, to, amount, transNum, btype, desc);
    }

    @Override
    public Page<FrozenRecordDTO> listFrozenRecords(Account account, int page, int pageSize) {
        Preconditions.checkArgument(account!=null);
        Preconditions.checkArgument(page >= 0);
        Preconditions.checkArgument(pageSize > 0 && pageSize < 200);

        Page<FrozenRecordDTO> ret = new Page<>(page, pageSize);

        FrozenRecordExample example = new FrozenRecordExample();
        example.or().andAccountIdEqualTo(account.getId());

        List<FrozenRecord> records = frozenRecordMapper.selectByExampleWithRowbounds(
                example, new RowBounds(ret.getStartIndex(), pageSize));
        long total = frozenRecordMapper.countByExample(example);

        List<FrozenRecordDTO> list = new ArrayList<>(records.size());
        for (FrozenRecord record : records) {
            FrozenRecordDTO dto = new FrozenRecordDTO();
            dto.setAccountId(account.getId());
            if (account instanceof AccountHasUserId) {
                AccountHasUserId userAccount = (AccountHasUserId) account;
                dto.setUid(userAccount.getUid());
            }
            dto.setAmount(record.getAmount());
            dto.setBalance(record.getBalance());
            dto.setTradeNum(record.getTradeNum());
            dto.setCreateTime(record.getCreateTime());
            list.add(dto);
        }

        ret.setResult(list);
        ret.setTotalCount(total);
        return ret;
    }
}
