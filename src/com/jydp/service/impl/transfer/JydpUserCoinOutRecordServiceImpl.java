package com.jydp.service.impl.transfer;

import com.jydp.dao.IJydpUserCoinOutRecordDao;
import com.jydp.service.IJydpUserCoinOutRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * JYDP用户币种转出记录
 * @Author: wqq
 */
@Service("IJydpUserCoinOutRecordService")
public class JydpUserCoinOutRecordServiceImpl implements IJydpUserCoinOutRecordService {

    /**  JYDP用户币种转出记录 */
    @Autowired
    private IJydpUserCoinOutRecordDao jydpUserCoinOutRecordDao;
}
