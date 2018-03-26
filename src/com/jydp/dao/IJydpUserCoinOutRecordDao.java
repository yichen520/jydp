package com.jydp.dao;

import com.jydp.entity.DO.transfer.JydpUserCoinOutRecordDO;

import java.sql.Timestamp;
import java.util.List;

/**
 * JYDP用户币种转出记录
 * @Author: wqq
 */
public interface IJydpUserCoinOutRecordDao {

    /**
     * 根据用户Id查询用户币种转出记录
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
    int countJydpUserCoinOutRecordForBack(String coinRecordNo, String userAccount, String walletAccount, String currencyName, int handleStatus,
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
    boolean updateHandleStatus(List<String> coinRecordNoList, String remark, Timestamp handleTime);

    /**
     * 批量审核拒绝用户用户币种转出记录
     * @param coinRecordNoList 记录号集合
     * @return 操作成功：true；查询失败：false
     */
    boolean updateRefuseHandleStatus(List<String> coinRecordNoList, String remarks, Timestamp handleTime);

    /**
     * 批量查询用户币种转出记录
     * @param coinRecordNoList 记录号集合
     * @return 操作成功：用户转出记录集合；查询失败：null
     */
    List<JydpUserCoinOutRecordDO> listJydpUserCoinOutRecordByCoinRecordNo(List<String> coinRecordNoList);

}
