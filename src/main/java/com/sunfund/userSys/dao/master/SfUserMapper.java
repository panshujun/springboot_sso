package com.sunfund.userSys.dao.master;


import com.sunfund.userSys.entity.SfUser;
import com.sunfund.userSys.entity.SfUserExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface SfUserMapper {
    int countByExample(@Param("example") SfUserExample example);

    int deleteByExample(SfUserExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(SfUser record);

    int insertSelective(SfUser record);

    List<SfUser> selectByExample(SfUserExample example);

    SfUser selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") SfUser record, @Param("example") SfUserExample example);

    int updateByExample(@Param("record") SfUser record, @Param("example") SfUserExample example);

    int updateByPrimaryKeySelective(SfUser record);

    int updateByPrimaryKey(SfUser record);

    SfUser findByName(String userName);


}