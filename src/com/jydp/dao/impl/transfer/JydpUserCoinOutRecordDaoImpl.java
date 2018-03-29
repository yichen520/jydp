package com.jydp.dao.impl.transfer;

import com.iqmkj.utils.LogUtil;
import com.jydp.dao.IJydpUserCoinOutRecordDao;
import com.jydp.entity.DO.transfer.JydpUserCoinOutRecordDO;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * JYDP用户币种转出记录
 * @Author: wqq
 */
@Repository
public class JydpUserCoinOutRecordDaoImpl implements IJydpUserCoinOutRecordDao {

    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    /**
     * 根据用户Id查询用户币种转出记录
     * @param userId 用户账号
     * @return 查询成功：返回用户转出记录；查询失败：返回null
     */
    @Override
    public List<JydpUserCoinOutRecordDO> getJydpUserCoinOutRecordlist(int userId,int pageNumber, int pageSize) {

        List<JydpUserCoinOutRecordDO> jydpUserCoinOutRecordList = null;
        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        map.put("startNumber", pageNumber * pageSize);
        map.put("pageSize", pageSize);

        try {
            jydpUserCoinOutRecordList = sqlSessionTemplate.selectList("JydpUserCoinOutRecord_getJydpUserCoinOutRecordlist",map);
        } catch (Exception e) {
            LogUtil.printErrorLog(e);
        }
        return jydpUserCoinOutRecordList;
    }

    /**
     * 根据条件查询币种转出记录数
     * @param coinRecordNo 记录号，没有填null
     * @param userAccount 用户账号，没有填null
     * @param walletAccount 转入账号，没有填null
     * @param currencyId 币种id，没有填0
     * @param handleStatus 审核状态，没有填0
     * @param startAddTime 申请开始时间，没有填null
     * @param endAddTime 申请结束时间，没有填null
     * @param startFinishTime 完成开始时间，没有填null
     * @param endFinishTime 完成结束时间 ，没有填null
     * @return 查询成功：返回用户转出记录数；查询失败：返回0
     */
    public int countJydpUserCoinOutRecordForBack(String coinRecordNo, String userAccount, String walletAccount, int currencyId, int handleStatus, int outStatus,
                                          Timestamp startAddTime, Timestamp endAddTime, Timestamp startFinishTime, Timestamp endFinishTime){

        Map<String, Object> map = new HashMap<>();
        map.put("coinRecordNo", coinRecordNo);
        map.put("userAccount", userAccount);
        map.put("walletAccount", walletAccount);
        map.put("currencyId", currencyId);
        map.put("handleStatus", handleStatus);
        map.put("startAddTime", startAddTime);
        map.put("endAddTime", endAddTime);
        map.put("startFinishTime", startFinishTime);
        map.put("endFinishTime", endFinishTime);
        map.put("outStatus", outStatus);

        int result = 0;
        try {
            result = sqlSessionTemplate.selectOne("JydpUserCoinOutRecord_countJydpUserCoinOutRecordForBack", map);
        } catch (Exception e) {
            LogUtil.printErrorLog(e);
        }

        return result;
    }

    /**
     * 根据条件查询币种转出记录
     * @param coinRecordNo 记录号，没有填null
     * @param userAccount 用户账号，没有填null
     * @param walletAccount 转入账号，没有填null
     * @param currencyId 币种id，没有填0
     * @param handleStatus 审核状态，没有填0
     * @param startAddTime 申请开始时间，没有填null
     * @param endAddTime 申请结束时间，没有填null
     * @param startFinishTime 完成开始时间，没有填null
     * @param endFinishTime 完成结束时间 ，没有填null
     * @return 查询成功：返回用户转出记录集合；查询失败：返回null
     */
    public List<JydpUserCoinOutRecordDO> listJydpUserCoinOutRecord(String coinRecordNo, String userAccount, String walletAccount, int currencyId, int handleStatus, int outStatus,
                                                                   Timestamp startAddTime, Timestamp endAddTime, Timestamp startFinishTime, Timestamp endFinishTime,
                                                                   int pageNumber, int pageSize){
        Map<String, Object> map = new HashMap<>();
        map.put("coinRecordNo", coinRecordNo);
        map.put("userAccount", userAccount);
        map.put("walletAccount", walletAccount);
        map.put("currencyId", currencyId);
        map.put("handleStatus", handleStatus);
        map.put("startAddTime", startAddTime);
        map.put("endAddTime", endAddTime);
        map.put("startFinishTime", startFinishTime);
        map.put("endFinishTime", endFinishTime);
        map.put("startNumber", pageNumber * pageSize);
        map.put("pageSize", pageSize);
        map.put("outStatus", outStatus);

        List<JydpUserCoinOutRecordDO> result = null;

        try {
            result = sqlSessionTemplate.selectList("JydpUserCoinOutRecord_listJydpUserCoinOutRecord", map);
        } catch (Exception e) {
            LogUtil.printErrorLog(e);
        }
        return result;
    }

    /**
     * 批量审核通过用户用户币种转出记录
     * @param coinRecordNoList 记录号集合
     * @param remark 备注
     * @param handleTime 审核时间
     * @return 操作成功：true；查询失败：false
     */
    public boolean updateHandleStatus(List<String> coinRecordNoList, String remark, Timestamp handleTime){
        Map<String, Object> map = new HashMap<>();
        map.put("coinRecordNoList", coinRecordNoList);
        map.put("remark", remark);
        map.put("handleTime", handleTime);
        int result = 0;
        try {
            result = sqlSessionTemplate.update("JydpUserCoinOutRecord_updateHandleStatus", map);
        } catch (Exception e) {
            LogUtil.printErrorLog(e);
        }

        if (result == coinRecordNoList.size()) {
            return true;
        } else {
            return false;
        }

    }

    /**
     * 批量审核拒绝用户用户币种转出记录
     * @param coinRecordNoList 记录号集合
     * @param remarks 备注
     * @param handleTime 审核时间
     * @return 操作成功：true；查询失败：false
     */
    public boolean updateRefuseHandleStatus(List<String> coinRecordNoList, String remarks, Timestamp handleTime){
        Map<String, Object> map = new HashMap<>();
        map.put("coinRecordNoList", coinRecordNoList);
        map.put("remark", remarks);
        map.put("handleTime", handleTime);
        int result = 0;
        try {
            result = sqlSessionTemplate.update("JydpUserCoinOutRecord_updateRefuseHandleStatus", map);
        } catch (Exception e) {
            LogUtil.printErrorLog(e);
        }

        if (result == coinRecordNoList.size()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 批量查询用户币种转出记录
     * @param coinRecordNoList 记录号集合
     * @return 操作成功：用户转出记录集合；查询失败：null
     */
    public List<JydpUserCoinOutRecordDO> listJydpUserCoinOutRecordByCoinRecordNo(List<String> coinRecordNoList){
        Map<String, Object> map = new HashMap<>();
        map.put("coinRecordNoList", coinRecordNoList);
        List<JydpUserCoinOutRecordDO> resultList = null;

        try {
            resultList = sqlSessionTemplate.selectList("JydpUserCoinOutRecord_listJydpUserCoinOutRecordByCoinRecordNo", map);
        } catch (Exception e) {
            LogUtil.printErrorLog(e);
        }

        return resultList;
    }

    /**
     * 查询用户币种转出记录总数
     * @param userId 用户Id
     * @return 查询成功：返回记录总数；查询失败：返回0
     */
    @Override
    public int countJydpUserCoinOutRecord(int userId) {

        int count = 0;

        try {
            count = sqlSessionTemplate.selectOne("JydpUserCoinOutRecord_countJydpUserCoinOutRecord",userId);
        } catch (Exception e) {
            LogUtil.printErrorLog(e);
        }
        return count;
    }

    /**
     * 撤销用户币种转出记录
     * @param coinRecordNo 转出记录流水号
     * @return 操作成功：返回true；操作失败：返回false；
     */
    @Override
    public boolean withdrawUserCoinOutRecord(String coinRecordNo) {

        int result = 0;

        try {
            result = sqlSessionTemplate.update("JydpUserCoinOutRecord_withdrawUserCoinOutRecord",coinRecordNo);
        } catch (Exception e) {
            LogUtil.printErrorLog(e);
        }

        if (result > 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 根据记录号查询记录
     * @param coinRecordNo 转出记录流水号
     * @return 查询成功：返回记录信息；查询失败：返回null
     */
    @Override
    public JydpUserCoinOutRecordDO getJydpUserCoinOutRecordByRecordNo(String coinRecordNo) {

        JydpUserCoinOutRecordDO jydpUserCoinOutRecord = null;

        try {
            jydpUserCoinOutRecord = sqlSessionTemplate.selectOne("JydpUserCoinOutRecord_getJydpUserCoinOutRecordByRecordNo",coinRecordNo);
        } catch (Exception e) {
            LogUtil.printErrorLog(e);
        }
        return jydpUserCoinOutRecord;
    }

    /**
     * 添加用户币种转出记录
     * @param jydpUserCoinOutRecord 币种转出记录
     * @return 操作成功:返回true, 操作失败:返回false
     */
    public boolean inesertJydpUserCoinOutRecord(JydpUserCoinOutRecordDO jydpUserCoinOutRecord) {
        int result = 0;

        try {
            result = sqlSessionTemplate.insert("JydpUserCoinOutRecord_inesertJydpUserCoinOutRecord", jydpUserCoinOutRecord);
        } catch (Exception e) {
            LogUtil.printErrorLog(e);
        }

        if (result > 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 根据记录号查询记录批量修改转出状态
     * @param coinRecordNoList 转出记录流水号集合
     * @param sendStatus 转出状态，1：未转出，2：转出中，3：转出成功，4：转出失败
     * @return 修改成功：true；修改失败：false
     */
    public boolean updateJydpUserCoinOutRecordOutStatus(List<String> coinRecordNoList, int sendStatus){
        Map<String, Object> map = new HashMap<>();
        map.put("coinRecordNoList", coinRecordNoList);
        map.put("sendStatus", sendStatus);
        int result = 0;

        try {
            result = sqlSessionTemplate.update("JydpUserCoinOutRecord_updateJydpUserCoinOutRecordOutStatus", map);
        } catch (Exception e) {
            LogUtil.printErrorLog(e);
        }

        if(result > 0){
            return true;
        }else{
            return false;
        }
    }

    /**
     * 查询审核通过且未推送的列表
     * @return 查询成功：返回列表；查询失败，返回null
     */
    public List<JydpUserCoinOutRecordDO> listNotPushRecord(){
        List<JydpUserCoinOutRecordDO> resultList = null;

        try {
            resultList = sqlSessionTemplate.selectList("JydpUserCoinOutRecord_listNotPushRecord");
        } catch (Exception e) {
            LogUtil.printErrorLog(e);
        }

        return resultList;
    }

    /**
     * 根据币种及电子钱包操作记录号查询记录
     * @param recordNo 电子钱包操作记录号
     * @param coinId 币种Id
     * @return 查询成功：返回记录信息；查询失败或者没有相关记录：返回null
     */
    public JydpUserCoinOutRecordDO getJydpUserCoinOutRecordByRecordNoAndCoinType(String recordNo, int coinId){
        JydpUserCoinOutRecordDO jydpUserCoinOutRecord = null;
        Map<String, Object> map = new HashMap<>();
        map.put("coinId", coinId);
        map.put("sylRecordNo", recordNo);


        try {
            jydpUserCoinOutRecord = sqlSessionTemplate.selectOne("JydpUserCoinOutRecord_getJydpUserCoinOutRecordByRecordNoAndCoinType",map);
        } catch (Exception e) {
            LogUtil.printErrorLog(e);
        }
        return jydpUserCoinOutRecord;
    }

    /**
     * Syl回调接收参数修改
     * @param orderNo 转出记录流水号
     * @param recordNo 盛源链记录号
     * @param coinId 币种Id
     * @param code 状态 （3表示交易成功，4表示交易失败）
     * @param receiveTime 完成时间
     * @return 查询成功：返回记录信息；查询失败：返回null
     */
    public boolean updateJydpUserCoinOutRecordBySyl(String orderNo, String recordNo, int coinId, int code, Timestamp receiveTime){
        Map<String, Object> map = new HashMap<>();

        map.put("orderNo", orderNo);
        map.put("sylRecordNo", recordNo);
        map.put("coinId", coinId);
        map.put("code", code);
        map.put("receiveTime", receiveTime);

        int result = 0;
        try {
            result = sqlSessionTemplate.update("JydpUserCoinOutRecord_updateJydpUserCoinOutRecordBySyl", map);
        } catch (Exception e) {
            LogUtil.printErrorLog(e);
        }

        if (result > 0) {
            return true;
        } else {
            return false;
        }
    }
}
