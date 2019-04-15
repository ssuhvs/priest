package com.little.g.pay.service.impl;

import com.google.common.base.Preconditions;
import com.little.g.common.exception.ServiceDataException;
import com.little.g.pay.PayErrorCodes;
import com.little.g.pay.dto.Account;
import com.little.g.pay.dto.AccountDetailDTO;
import com.little.g.pay.dto.FrozenAccount;
import com.little.g.pay.enums.AccountStatus;
import com.little.g.pay.enums.TransactionType;
import com.little.g.pay.mapper.FrozenRecordMapper;
import com.little.g.pay.mapper.FrozenRecordMapperExt;
import com.little.g.pay.mapper.TransactionRecordMapper;
import com.little.g.pay.mapper.TransactionRecordMapperExt;
import com.little.g.pay.model.FrozenRecord;
import com.little.g.pay.model.FrozenRecordExample;
import com.little.g.pay.model.TransactionRecord;
import com.little.g.pay.model.TransactionRecordExample;
import com.little.g.pay.service.api.AccountAdaptor;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;


/**
 * Created by zhaoyao on 16/5/9.
 */
@Component("frozenAccountAdaptor")
public class FrozenAccountAdaptor implements AccountAdaptor<FrozenAccount> {

    private static final Logger log = LoggerFactory.getLogger(FrozenAccountAdaptor.class);

    private static final boolean VALIDATE_UNFROZEN_TRANS_LOG = true;

    @Resource
    private FrozenRecordMapper frozenRecordMapper;

    @Resource
    private FrozenRecordMapperExt frozenRecordMapperExt;

    @Resource
    private TransactionRecordMapper transactionRecordMapper;

    @Resource
    private TransactionRecordMapperExt transactionRecordMapperExt;

    @Override
    public void lock(FrozenAccount account) {
        FrozenRecordExample example = new FrozenRecordExample();
        example.or().andTradeNumEqualTo(account.getTransNum());
        FrozenRecord record = new FrozenRecord();
        record.setUpdateTime(System.currentTimeMillis());

        if (frozenRecordMapper.updateByExampleSelective(record, example) <= 0) {
            throw new ServiceDataException(PayErrorCodes.PAY_ERROR,"msg.account.frozen.transaction.not.exist");
        }
    }

    @Override
    public long getBalance(FrozenAccount account) {
        // 校验解冻流水
        TransactionRecordExample example = new TransactionRecordExample();
        example.or()
                .andAccountIdEqualTo(account.getId())
                .andTypeEqualTo(TransactionType.INCOME.getValue());

        List<TransactionRecord> details = transactionRecordMapper.selectByExample(example);
        if (details.isEmpty() || details.size() != 1) {
            throw new ServiceDataException(PayErrorCodes.PAY_ERROR,"msg.account.frozen.transaction.not.exist");
        }
        TransactionRecord originFrozenTransDetail = details.get(0);

        // 校验已解冻流水
        Long totalFrozen = originFrozenTransDetail.getMoney();

        TransactionRecordExample unfrozenExample = new TransactionRecordExample();
        unfrozenExample.or()
                .andAccountIdEqualTo(account.getId())
                .andTypeEqualTo(TransactionType.OUTCOME.getValue());

        Long totalUnfrozen = transactionRecordMapperExt.sumMoneyByExample(unfrozenExample);
        if (totalUnfrozen == null) {
            totalUnfrozen = 0L;
        }

        return totalFrozen + totalUnfrozen;
    }

    @Override
    public AccountDetailDTO getDetail(FrozenAccount account) {
        FrozenRecordExample example = new FrozenRecordExample();
        example.or()
                .andTradeNumEqualTo(account.getTransNum());

        List<FrozenRecord> frozenRecords = frozenRecordMapper.selectByExampleWithRowbounds(example, new RowBounds(0, 1));
        if (frozenRecords == null || frozenRecords.isEmpty()) {
            return null;
        }
        FrozenRecord record = frozenRecords.get(0);

        AccountDetailDTO detailDTO = new AccountDetailDTO();
        detailDTO.setAccount(account);
        detailDTO.setStatus(AccountStatus.INIT.getValue()); // NOTE: 冻结帐号目前没有状态，全部是激活状态
        detailDTO.setCreateTime(record.getCreateTime());
        detailDTO.setUpdateTime(record.getUpdateTime());
        return detailDTO;
    }

    /**
     * 冻结
     *
     * @param account
     * @param amount
     */
    @Override
    public void credit(FrozenAccount account, long amount, Account from) {
        Preconditions.checkState(amount > 0, "amount must > 0 ");

        FrozenRecord frozenRecord = new FrozenRecord();
        frozenRecord.setAccountId(from.getId());
        frozenRecord.setTradeNum(account.getTransNum());
        frozenRecord.setCreateTime(System.currentTimeMillis());
        frozenRecord.setAmount(amount);
        frozenRecord.setBalance(amount);

        try {
            frozenRecordMapper.insert(frozenRecord);
        } catch (DuplicateKeyException e) {
            FrozenRecordExample example = new FrozenRecordExample();
            example.or()
                    .andTradeNumEqualTo(account.getTransNum());
            List<FrozenRecord> records = frozenRecordMapper.selectByExample(example);

            if (records.get(0).getAccountId().equals(account.getId())) {
                return;
            }

            throw new ServiceDataException(PayErrorCodes.PAY_ERROR,"msg.account.duplicate.transaction");
        }

    }

    /**
     * 解冻
     *
     * @param account
     * @param amount
     */
    @Override
    public void debit(FrozenAccount account, long amount, Account to) {
        Preconditions.checkState(amount > 0, "amount must > 0 ");
        if (frozenRecordMapperExt.unfroze(account.getTransNum(), amount) <= 0) {
            log.error("transfer money exception params[account:{},amount:{},to:{}]", account, amount, to);
            throw new ServiceDataException(PayErrorCodes.PAY_ERROR,"msg.account.frozen.money.not.enough");
        }
    }

    @Override
    public void init(FrozenAccount account) {

    }
}
