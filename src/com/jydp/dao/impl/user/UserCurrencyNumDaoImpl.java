package com.jydp.dao.impl.user;

import com.jydp.dao.IUserCurrencyNumDao;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Description: 用户币数量
 * Author: hht
 * Date: 2018-02-07 17:25
 */
@Repository
public class UserCurrencyNumDaoImpl implements IUserCurrencyNumDao {

    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;
}
