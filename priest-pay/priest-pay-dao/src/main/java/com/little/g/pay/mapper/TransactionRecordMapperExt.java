package com.little.g.pay.mapper;


import com.little.g.pay.model.TransactionRecordExample;

public interface TransactionRecordMapperExt {
    Long sumMoneyByExample(TransactionRecordExample example);
}