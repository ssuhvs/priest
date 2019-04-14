package com.little.g.pay.service.impl;

import com.google.common.base.Preconditions;
import com.little.g.pay.api.AccountService;
import com.little.g.pay.api.TransactionService;
import com.little.g.pay.mapper.FrozenRecordMapper;
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
    public void createUserAccount(String appid, long uid) {
        transactionService.createAccount(appid, new NormalUserAccount(uid));
    }

    @Override
    public long getBalance(String appid, Account account, long timeStart, long timeEnd) {
        List<TransactionDetailDTO> allTransactions = transactionService.listTransactions(appid, account, timeStart, timeEnd, null);
        long balance = 0;
        for (TransactionDetailDTO td : allTransactions) {
            balance += td.getMoney();
        }
        return balance;
    }

    @Override
    public List<TransactionDetailDTO> findTransactions(String appid, Account account, Long startTime, Long endTime, Integer limit) {
        return transactionService.listTransactions(appid, account, startTime, endTime, limit);
    }

    @Override
    public UserAccountDTO get(String appid, Account account) {
        AccountDetailDTO detail = transactionService.getAccountDetail(appid, account);
        if (detail == null) {
            return null;
        }

        UserAccountDTO ret = new UserAccountDTO();
        ret.setAppid(appid);
        ret.setMoney(detail.getBalance());
        ret.setStatus((byte) detail.getStatus());
        ret.setCreateTime(detail.getCreateTime());
        ret.setUpdateTime(detail.getUpdateTime());
        if (account instanceof AccountHasUserId) {
            AccountHasUserId accountHasUserId = (AccountHasUserId) account;
            ret.setUid(accountHasUserId.getUid());
        }
        ret.setType(account.getPrefix());

        PaymentBindDTO paymentChannel = payBindService.findByPaymentChannel(appid, account, PaymentChannel.WXPAY);
        if (paymentChannel != null) {
            ret.setBindId(paymentChannel.getId());
            ret.setBindAccount(paymentChannel.getPayAccount());
            ret.setPayType(paymentChannel.getPayType());
            ret.setBindStatus(BindStatus.BIND.getValue());
        } else {
            ret.setBindStatus(BindStatus.UNBIND.getValue());
        }

        return ret;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
    public List<TransactionDetailDTO> transfer(String appid, long fromUid, long toUid, long amount, String transNum, BusinessType btype, String desc) {
        return transactionService.transfer(appid, new NormalUserAccount(fromUid), new NormalUserAccount(toUid), amount, transNum, btype, desc);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
    public List<TransactionDetailDTO> froze(String appid, long uid, long amount, String transNum, BusinessType btype, String desc) {
        return transactionService.transfer(appid, new NormalUserAccount(uid), new FrozenAccount(transNum), amount, transNum, btype, desc);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
    public List<TransactionDetailDTO> unfroze(String appid, String srcTransNum, long amount, long toUid, String transNum, BusinessType btype, String desc) {
        return transactionService.transfer(appid, new FrozenAccount(srcTransNum), new NormalUserAccount(toUid), amount, transNum, btype, desc);
    }

    @Override
    public Page<FrozenRecordDTO> listFrozenRecords(String appid, long uid, int page, int pageSize) {
        Preconditions.checkArgument(uid > 0);
        Preconditions.checkArgument(page >= 0);
        Preconditions.checkArgument(pageSize > 0 && pageSize < 200);

        Page<FrozenRecordDTO> ret = new Page<>(page, pageSize);

        FrozenRecordExample example = new FrozenRecordExample();
        example.or().andAppidEqualTo(appid).andAccountIdEqualTo(new NormalUserAccount(uid).getId());

        List<FrozenRecord> records = frozenRecordMapper.selectByExampleWithRowbounds(
                example, new RowBounds(ret.getStartIndex(), pageSize));
        int total = frozenRecordMapper.countByExample(example);

        List<FrozenRecordDTO> list = new ArrayList<>(records.size());
        for (FrozenRecord record : records) {
            FrozenRecordDTO dto = new FrozenRecordDTO();
            dto.setAppid(appid);
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
    public void createAccount(String appid, Account account) {
        transactionService.createAccount(appid, account);
    }

    @Override
    public List<TransactionDetailDTO> transfer(String appid, Account from, Account to, long amount, String transNum, BusinessType btype, String desc) {
        return transactionService.transfer(appid, from, to, amount, transNum, btype, desc);
    }

    @Override
    public Page<FrozenRecordDTO> listFrozenRecords(String appid, Account account, int page, int pageSize) {
        Preconditions.checkArgument(account!=null);
        Preconditions.checkArgument(page >= 0);
        Preconditions.checkArgument(pageSize > 0 && pageSize < 200);

        Page<FrozenRecordDTO> ret = new Page<>(page, pageSize);

        FrozenRecordExample example = new FrozenRecordExample();
        example.or().andAppidEqualTo(appid).andAccountIdEqualTo(account.getId());

        List<FrozenRecord> records = frozenRecordMapper.selectByExampleWithRowbounds(
                example, new RowBounds(ret.getStartIndex(), pageSize));
        int total = frozenRecordMapper.countByExample(example);

        List<FrozenRecordDTO> list = new ArrayList<>(records.size());
        for (FrozenRecord record : records) {
            FrozenRecordDTO dto = new FrozenRecordDTO();
            dto.setAppid(appid);
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
