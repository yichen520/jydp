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
     * @param userId 用户Id
     * @param pageNumber 起始页数
     * @param pageSize 每页条数
     * @return 查询成功：返回用户转出记录；查询失败：返回null
     */
    List<JydpUserCoinOutRecordDO> getJydpUserCoinOutRecordlist(int userId, int pageNumber, int pageSize);

    /**
     * 查询用户币种转出记录总数
     * @param userId 用户Id
     * @return 查询成功：返回记录总数；查询失败：返回0
     */
    int countJydpUserCoinOutRecord(int userId);

    /**
     * 撤销用户币种转出记录
     * @param userId 用户Id
     * @param coinRecordNo 转出记录流水号
     * @return 操作成功：返回true；操作失败：返回false；
     */
    boolean withdrawUserCoinOutRecord(int userId,String coinRecordNo);

    /**
     * 用户币种提现记录
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
     * @param currencyId 币种id，没有填0
     * @param handleStatus 审核状态，没有填0
     *
     * @param startAddTime 申请开始时间，没有填null
     * @param endAddTime 申请结束时间，没有填null
     * @param startFinishTime 完成开始时间，没有填null
     * @param endFinishTime 完成结束时间 ，没有填null
     * @return 查询成功：返回用户转出记录数；查询失败：返回0
     */
    int countJydpUserCoinOutRecordForBack(String coinRecordNo, String userAccount, String walletAccount, int currencyId, int handleStatus,int outStatus,
                                   Timestamp startAddTime, Timestamp endAddTime, Timestamp startFinishTime, Timestamp endFinishTime);

    /**
     * 根据条件查询币种转出记录
     * @param coinRecordNo 记录号，没有填null
     * @param userAccount 用户账号，没有填null
     * @param walletAccount 转入账号，没有填null
     * @param currencyId 币种id，没有填0
     * @param handleStatus 审核状态，没有填0
     *
     * @param startAddTime 申请开始时间，没有填null
     * @param endAddTime 申请结束时间，没有填null
     * @param startFinishTime 完成开始时间，没有填null
     * @param endFinishTime 完成结束时间 ，没有填null
     * @return 查询成功：返回用户转出记录集合；查询失败：返回null
     */
    List<JydpUserCoinOutRecordDO> listJydpUserCoinOutRecord(String coinRecordNo, String userAccount, String walletAccount, int currencyId, int handleStatus,int outStatus,
                                                            Timestamp startAddTime, Timestamp endAddTime, Timestamp startFinishTime, Timestamp endFinishTime,
                                                            int pageNumber, int pageSize);

    /**
     * 批量审核通过用户币种转出记录
     * @param coinRecordNoList 记录号集合
     * @param remark 备注
     * @param handleTime 审核时间
     * @return 操作成功：true；查询失败：false
     */
    boolean updateHandleStatus(List<String> coinRecordNoList, String remark, Timestamp handleTime);

    /**
     * 批量审核拒绝用户币种转出记录
     * @param coinRecordNoList 记录号集合
     * @param remarks 备注
     * @param handleTime 审核时间
     * @return 操作成功：true；查询失败：false
     */
    boolean updateRefuseHandleStatus(List<String> coinRecordNoList, String remarks, Timestamp handleTime);

    /**
     * 根据记录号查询记录
     * @param coinRecordNo 转出记录流水号
     * @return 查询成功：返回记录信息；查询失败：返回null
     */
    JydpUserCoinOutRecordDO getJydpUserCoinOutRecordByRecordNo(String coinRecordNo);

    /**
     * 根据记录号查询记录批量修改转出状态(jydp向syl提币申请)
     * @param coinRecordNoList 转出记录流水号集合
     * @param sendStatus 转出状态，1：未转出，2：转出中，3：转出成功，4：转出失败
     * @return 修改成功：true；修改失败：false
     */
    boolean updateJydpUserCoinOutRecordOutStatus(List<String> coinRecordNoList, int sendStatus);

    /**
     * 查询审核通过且未推送的列表
     * @return 查询成功：返回列表；查询失败，返回null
     */
    List<JydpUserCoinOutRecordDO> listNotPushRecord();

    /**
     * 根据币种及电子钱包操作记录号查询记录
     * @param recordNo 电子钱包操作记录号
     * @param coinId 币种Id
     * @return 查询成功：返回记录信息；查询失败或者没有相关记录：返回null
     */
    JydpUserCoinOutRecordDO getJydpUserCoinOutRecordByRecordNoAndCoinType(String recordNo, int coinId);

    /**
     * Syl回调接收参数修改
     * @param orderNo 转出记录流水号
     * @param recordNo 盛源链记录号
     * @param coinId 币种Id
     * @param code 状态 （3表示交易成功，4表示交易失败）
     * @param receiveTime 完成时间
     * @return 查询成功：返回记录信息；查询失败：返回null
     */
    boolean updateJydpUserCoinOutRecordBySyl(String orderNo, String recordNo, int coinId, int code,Timestamp receiveTime);
}
