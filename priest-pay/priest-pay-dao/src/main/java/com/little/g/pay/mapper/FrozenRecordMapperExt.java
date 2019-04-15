package com.little.g.pay.mapper;

import org.apache.ibatis.annotations.Param;

public interface FrozenRecordMapperExt {

    int unfroze(@Param("trade_num") String tradeNum, @Param("money") long balance);

}