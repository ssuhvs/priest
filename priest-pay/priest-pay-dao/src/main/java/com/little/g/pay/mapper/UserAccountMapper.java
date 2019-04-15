package com.little.g.pay.mapper;

import com.little.g.pay.model.UserAccount;
import com.little.g.pay.model.UserAccountExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserAccountMapper {
    long countByExample(UserAccountExample example);

    int deleteByExample(UserAccountExample example);

    int deleteByPrimaryKey(Long uid);

    int insert(UserAccount record);

    int insertSelective(UserAccount record);

    List<UserAccount> selectByExample(UserAccountExample example);

    UserAccount selectByPrimaryKey(Long uid);

    int updateByExampleSelective(@Param("record") UserAccount record, @Param("example") UserAccountExample example);

    int updateByExample(@Param("record") UserAccount record, @Param("example") UserAccountExample example);

    int updateByPrimaryKeySelective(UserAccount record);

    int updateByPrimaryKey(UserAccount record);
}