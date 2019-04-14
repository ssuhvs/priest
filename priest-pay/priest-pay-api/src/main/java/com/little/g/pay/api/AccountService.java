package com.little.g.pay.api;

import com.little.g.pay.dto.Account;
import com.little.g.pay.dto.FrozenRecordDTO;
import com.little.g.pay.dto.TransactionRecordDTO;
import com.little.g.pay.dto.UserAccountDTO;
import com.little.g.pay.enums.BusinessType;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Created by zhaoyao on 16/5/6.
 */
public interface AccountService {

    void createUserAccount(long uid);

    UserAccountDTO get(Account account);

    long getBalance(Account account, long timeStart, long timeEnd);

    List<TransactionRecordDTO> findTransactions(Account account, Long startTime, Long endTime, Integer limit);

    List<TransactionRecordDTO> transfer(long fromUid, long toUid, long amount, String transNum, BusinessType btype, String desc);

    List<TransactionRecordDTO> froze(long uid, long amount, String transNum, BusinessType btype, String desc);

    List<TransactionRecordDTO> unfroze(String srcTransNum, long amount, long toUid, String transNum, BusinessType btype, String desc);

    Page<FrozenRecordDTO> listFrozenRecords(long uid, int page, int pageSize);

    void createAccount(Account account);

    List<TransactionRecordDTO> transfer(Account from, Account to, long amount, String transNum, BusinessType btype, String desc);

    Page<FrozenRecordDTO> listFrozenRecords(Account account, int page, int pageSize);
}
