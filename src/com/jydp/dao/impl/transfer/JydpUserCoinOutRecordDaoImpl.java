package com.jydp.dao.impl.transfer;

import com.iqmkj.utils.LogUtil;
import com.jydp.dao.IJydpUserCoinOutRecordDao;
import com.jydp.entity.DO.transfer.JydpUserCoinOutRecordDO;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

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
    public List<JydpUserCoinOutRecordDO> getJydpUserCoinOutRecordlist(String userAccount) {

        List<JydpUserCoinOutRecordDO> jydpUserCoinOutRecordList = null;

        try {
            jydpUserCoinOutRecordList = sqlSessionTemplate.selectList("JydpUserCoinOutRecord_getJydpUserCoinOutRecordlist",userAccount);
        } catch (Exception e) {
            LogUtil.printErrorLog(e);
        }
        return jydpUserCoinOutRecordList;
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
