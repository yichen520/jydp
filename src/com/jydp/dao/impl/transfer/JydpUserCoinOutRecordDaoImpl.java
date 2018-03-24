package com.jydp.dao.impl.transfer;

import com.iqmkj.utils.LogUtil;
import com.jydp.dao.IJydpUserCoinOutRecordDao;
import com.jydp.entity.DO.transfer.JydpUserCoinOutRecordDO;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

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
}
