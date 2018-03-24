package com.jydp.service.impl.transfer;

import com.jydp.dao.IJydpUserCoinOutRecordDao;
import com.jydp.entity.DO.transfer.JydpUserCoinOutRecordDO;
import com.jydp.service.IJydpUserCoinOutRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * JYDP用户币种转出记录
 * @Author: wqq
 */
@Service("jydpUserCoinOutRecordService")
public class JydpUserCoinOutRecordServiceImpl implements IJydpUserCoinOutRecordService {

    /**  JYDP用户币种转出记录 */
    @Autowired
    private IJydpUserCoinOutRecordDao jydpUserCoinOutRecordDao;

    /**
     * 根据用户账号查询用户币种转出记录
     * @param userAccount 用户账号
     * @return 查询成功：返回用户转出记录；查询失败：返回null
     */
    @Override
    public List<JydpUserCoinOutRecordDO> getJydpUserCoinOutRecordlist(String userAccount) {
        return jydpUserCoinOutRecordDao.getJydpUserCoinOutRecordlist(userAccount);
    }

    /**
     * 查询用户币种转出记录总数
     * @param userAccount 用户账号
     * @return 查询成功：返回记录总数；查询失败：返回0
     */
    @Override
    public int countJydpUserCoinOutRecord(String userAccount) {
        return jydpUserCoinOutRecordDao.countJydpUserCoinOutRecord(userAccount);
    }
}
