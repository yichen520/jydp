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
     * @param pageNumber 起始页数
     * @param pageSize 每页条数
     * @return 查询成功：返回用户转出记录；查询失败：返回null
     */
    List<JydpUserCoinOutRecordDO> getJydpUserCoinOutRecordlist(String userAccount, int pageNumber, int pageSize);

    /**
     * 查询用户币种转出记录总数
     * @param userAccount 用户账号
     * @return 查询成功：返回记录总数；查询失败：返回0
     */
    int countJydpUserCoinOutRecord(String userAccount);

    /**
     * 撤销用户币种转出记录
     * @param coinRecordNo 转出记录流水号
     * @return 操作成功：返回true；操作失败：返回false
     */
    boolean withdrawUserCoinOutRecord(String coinRecordNo);

    /**
     * 根据记录号查询记录
     * @param coinRecordNo 转出记录流水号
     * @return 查询成功：返回记录信息；查询失败：返回null
     */
    JydpUserCoinOutRecordDO getJydpUserCoinOutRecordByRecordNo(String coinRecordNo);

    /**
     * 添加用户币种转出记录
     * @param jydpUserCoinOutRecord 币种转出记录
     * @return 操作成功:返回true, 操作失败:返回false
     */
    boolean inesertJydpUserCoinOutRecord(JydpUserCoinOutRecordDO jydpUserCoinOutRecord);
}
