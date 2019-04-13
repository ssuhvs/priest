package com.little.g.pay.mapper;

import com.little.g.pay.model.TransactionRecord;
import com.little.g.pay.model.TransactionRecordExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TransactionRecordMapper {
    long countByExample(TransactionRecordExample example);

    int deleteByExample(TransactionRecordExample example);

    int deleteByPrimaryKey(String tranNum);

    int insert(TransactionRecord record);

    int insertSelective(TransactionRecord record);

    List<TransactionRecord> selectByExample(TransactionRecordExample example);

    TransactionRecord selectByPrimaryKey(String tranNum);

    int updateByExampleSelective(@Param("record") TransactionRecord record, @Param("example") TransactionRecordExample example);

    int updateByExample(@Param("record") TransactionRecord record, @Param("example") TransactionRecordExample example);

    int updateByPrimaryKeySelective(TransactionRecord record);

    int updateByPrimaryKey(TransactionRecord record);
}