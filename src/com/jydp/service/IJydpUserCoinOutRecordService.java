package com.jydp.service;

import com.jydp.entity.DO.transfer.JydpUserCoinOutRecordDO;

import java.sql.Timestamp;
import java.util.List;

/**
 * JYDP用户币种转出记录
 * @Author: wqq
 */
public interface IJydpUserCoinOutRecordService {

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
     * @param userId 用户Id
     * @param coinRecordNo 转出记录流水号
     * @return 操作成功：返回true；操作失败：返回false；
     */
    boolean withdrawUserCoinOutRecord(int userId,String coinRecordNo);

    /**
     * 用户币种提现记录(提币数量小于免审数量)
     * @param currencyId 币种id
     * @param currencyName 币种名称
     * @param userId 用户id
     * @param userAccount 用户帐号
     * @param userSylAccount 电子钱包帐户
     * @param number 提币数量
     * @return 操作成功:返回true, 操作失败:返回false
     */
    boolean insertJydpUserCoinOutRecord(int currencyId, String currencyName, int userId, String userAccount, String userSylAccount, double number);

    /**
     * 根据条件查询币种转出记录数
     * @param coinRecordNo 记录号，没有填null
     * @param userAccount 用户账号，没有填null
     * @param walletAccount 转入账号，没有填null
     * @param currencyName 币种名称，没有填null
     * @param handleStatus 审核状态，没有填0
     * @param startAddTime 申请开始时间，没有填null
     * @param endAddTime 申请结束时间，没有填null
     * @param startFinishTime 完成开始时间，没有填null
     * @param endFinishTime 完成结束时间 ，没有填null
     * @return 查询成功：返回用户转出记录数；查询失败：返回0
     */
    int countJydpUserCoinOutRecord(String coinRecordNo, String userAccount, String walletAccount, String currencyName, int handleStatus,
                                   Timestamp startAddTime, Timestamp endAddTime, Timestamp startFinishTime, Timestamp endFinishTime);

    /**
     * 根据条件查询币种转出记录
     * @param coinRecordNo 记录号，没有填null
     * @param userAccount 用户账号，没有填null
     * @param walletAccount 转入账号，没有填null
     * @param currencyName 币种名称，没有填null
     * @param handleStatus 审核状态，没有填0
     * @param startAddTime 申请开始时间，没有填null
     * @param endAddTime 申请结束时间，没有填null
     * @param startFinishTime 完成开始时间，没有填null
     * @param endFinishTime 完成结束时间 ，没有填null
     * @return 查询成功：返回用户转出记录集合；查询失败：返回null
     */
    List<JydpUserCoinOutRecordDO> listJydpUserCoinOutRecord(String coinRecordNo, String userAccount, String walletAccount, String currencyName, int handleStatus,
                                                            Timestamp startAddTime, Timestamp endAddTime, Timestamp startFinishTime, Timestamp endFinishTime,
                                                            int pageNumber, int pageSize);

    /**
     * 批量审核通过用户用户币种转出记录
     * @param coinRecordNoList 记录号集合
     * @return 操作成功：true；查询失败：false
     */
    boolean updateHandleStatus(List<String> coinRecordNoList, String remark);

    /**
     * 批量审核拒绝用户用户币种转出记录
     * @param coinRecordNoList 记录号集合
     * @return 操作成功：true；查询失败：false
     */
    boolean updateRefuseHandleStatus(List<String> coinRecordNoList, String remarks);

}
