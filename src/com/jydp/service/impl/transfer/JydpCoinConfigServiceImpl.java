package com.jydp.service.impl.transfer;

import com.jydp.dao.IJydpCoinConfigDao;
import com.jydp.service.IJydpCoinConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * JYDP币种转出管理
 * @Author: wqq
 */
@Service("IJydpCoinConfigService")
public class JydpCoinConfigServiceImpl implements IJydpCoinConfigService {

    /**  JYDP币种转出管理 */
    @Autowired
    private IJydpCoinConfigDao jydpCoinConfigDao;
}
