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
}
