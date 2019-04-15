package com.little.g.pay.mapper;

import com.little.g.pay.model.UserAccount;
import com.little.g.pay.model.UserAccountExample;
import org.apache.ibatis.annotations.Param;


public interface UserAccountMapperExt {
    int updateMoneySelective(@Param("record") UserAccount record, @Param("example") UserAccountExample example);
}