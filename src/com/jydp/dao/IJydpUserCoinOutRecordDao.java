package com.jydp.dao;

import com.jydp.entity.DO.transfer.JydpUserCoinOutRecordDO;

import java.util.List;

/**
 * JYDP用户币种转出记录
 * @Author: wqq
 */
public interface IJydpUserCoinOutRecordDao {

    /**
     * 根据用户账号查询用户币种转出记录
     * @param userAccount 用户账号
     * @return 查询成功：返回用户转出记录；查询失败：返回null
     */
    List<JydpUserCoinOutRecordDO> getJydpUserCoinOutRecordlist(String userAccount);

    /**
     * 查询用户币种转出记录总数
     * @param userAccount 用户账号
     * @return 查询成功：返回记录总数；查询失败：返回0
     */
    int countJydpUserCoinOutRecord(String userAccount);
}
