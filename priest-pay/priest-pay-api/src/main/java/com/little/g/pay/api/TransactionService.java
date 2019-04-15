package com.little.g.pay.api;


import com.little.g.pay.dto.Account;
import com.little.g.pay.dto.AccountDetailDTO;
import com.little.g.pay.dto.CheckBalanceResultDTO;
import com.little.g.pay.dto.TransactionRecordDTO;
import com.little.g.pay.enums.BusinessType;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * account内部使用, 不export
 * Created by zhaoyao on 16/5/6.
 */
public interface TransactionService {

    void createAccount(Account account);

    List<TransactionRecordDTO> findTransaction(String transNum, Account from, Account to);

    List<TransactionRecordDTO> transfer(Account from, Account to, long amount, String transNum, BusinessType btype, String desc);

    List<TransactionRecordDTO> transferAll(Account from, Account to, String transNum, BusinessType btype, String desc);

    List<TransactionRecordDTO> strikeBalance(Account account);

    CheckBalanceResultDTO checkBalance(Account account, boolean useCheckpoint, boolean saveCheckpoint);

    long getBalance(Account account);

    AccountDetailDTO getAccountDetail(Account account);

    List<TransactionRecordDTO> queryTransactionDetailByTransNum(String transNum);

    List<TransactionRecordDTO> listTransactions(Account account, Long startTime, Long endTime, Integer limit);


}
