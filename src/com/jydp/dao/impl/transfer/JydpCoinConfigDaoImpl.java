package com.jydp.dao.impl.transfer;

import com.jydp.dao.IJydpCoinConfigDao;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * JYDP币种转出管理
 * @Author: wqq
 */
@Repository
public class JydpCoinConfigDaoImpl implements IJydpCoinConfigDao {

    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;
}
