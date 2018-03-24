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
     * 根据用户账号查询用户币种转出记录
     * @param userAccount 用户账号
     * @return 查询成功：返回用户转出记录；查询失败：返回null
     */
    @Override
    public List<JydpUserCoinOutRecordDO> getJydpUserCoinOutRecordlist(String userAccount,int pageNumber, int pageSize) {

        List<JydpUserCoinOutRecordDO> jydpUserCoinOutRecordList = null;
        Map<String, Object> map = new HashMap<>();
        map.put("userAccount", userAccount);
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
     * @param currencyName 币种名称，没有填null
     * @param handleStatus 审核状态，没有填0
     * @param startAddTime 申请开始时间，没有填null
     * @param endAddTime 申请结束时间，没有填null
     * @param startFinishTime 完成开始时间，没有填null
     * @param endFinishTime 完成结束时间 ，没有填null
     * @return 查询成功：返回用户转出记录数；查询失败：返回0
     */
    public int countJydpUserCoinOutRecordForBack(String coinRecordNo, String userAccount, String walletAccount, String currencyName, int handleStatus,
                                          Timestamp startAddTime, Timestamp endAddTime, Timestamp startFinishTime, Timestamp endFinishTime){

        Map<String, Object> map = new HashMap<>();
        map.put("coinRecordNo", coinRecordNo);
        map.put("userAccount", userAccount);
        map.put("walletAccount", walletAccount);
        map.put("currencyName", currencyName);
        map.put("handleStatus", handleStatus);
        map.put("startAddTime", startAddTime);
        map.put("endAddTime", endAddTime);
        map.put("startFinishTime", startFinishTime);
        map.put("endFinishTime", endFinishTime);

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
     * @param currencyName 币种名称，没有填null
     * @param handleStatus 审核状态，没有填0
     * @param startAddTime 申请开始时间，没有填null
     * @param endAddTime 申请结束时间，没有填null
     * @param startFinishTime 完成开始时间，没有填null
     * @param endFinishTime 完成结束时间 ，没有填null
     * @return 查询成功：返回用户转出记录集合；查询失败：返回null
     */
    public List<JydpUserCoinOutRecordDO> listJydpUserCoinOutRecord(String coinRecordNo, String userAccount, String walletAccount, String currencyName, int handleStatus,
                                                                   Timestamp startAddTime, Timestamp endAddTime, Timestamp startFinishTime, Timestamp endFinishTime,
                                                                   int pageNumber, int pageSize){
        Map<String, Object> map = new HashMap<>();
        map.put("coinRecordNo", coinRecordNo);
        map.put("userAccount", userAccount);
        map.put("walletAccount", walletAccount);
        map.put("currencyName", currencyName);
        map.put("handleStatus", handleStatus);
        map.put("startAddTime", startAddTime);
        map.put("endAddTime", endAddTime);
        map.put("startFinishTime", startFinishTime);
        map.put("endFinishTime", endFinishTime);
        map.put("startNumber", pageNumber * pageSize);
        map.put("pageSize", pageSize);

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
     * @return 操作成功：true；查询失败：false
     */
    public boolean updateHandleStatus(List<String> coinRecordNoList, String remark){
        Map<String, Object> map = new HashMap<>();
        map.put("coinRecordNoList", coinRecordNoList);
        map.put("remark", remark);
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
     * @return 操作成功：true；查询失败：false
     */
    public boolean updateRefuseHandleStatus(List<String> coinRecordNoList, String remarks){
        Map<String, Object> map = new HashMap<>();
        map.put("coinRecordNoList", coinRecordNoList);
        map.put("remark", remarks);
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
     * @param userAccount 用户账号
     * @return 查询成功：返回记录总数；查询失败：返回0
     */
    @Override
    public int countJydpUserCoinOutRecord(String userAccount) {

        int count = 0;

        try {
            count = sqlSessionTemplate.selectOne("JydpUserCoinOutRecord_countJydpUserCoinOutRecord",userAccount);
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
}
