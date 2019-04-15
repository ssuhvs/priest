package com.little.g.pay.mapper;

import com.little.g.pay.model.TransactionRecord;
import com.little.g.pay.model.TransactionRecordExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface TransactionRecordMapper {
    long countByExample(TransactionRecordExample example);

    int deleteByExample(TransactionRecordExample example);

    int insert(TransactionRecord record);

    int insertSelective(TransactionRecord record);

    List<TransactionRecord> selectByExampleWithRowbounds(TransactionRecordExample example, RowBounds rowBounds);

    List<TransactionRecord> selectByExample(TransactionRecordExample example);

    int updateByExampleSelective(@Param("record") TransactionRecord record, @Param("example") TransactionRecordExample example);

    int updateByExample(@Param("record") TransactionRecord record, @Param("example") TransactionRecordExample example);
}