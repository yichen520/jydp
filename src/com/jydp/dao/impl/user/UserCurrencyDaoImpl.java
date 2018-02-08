package com.jydp.dao.impl.user;

import com.jydp.dao.IUserCurrencyDao;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Description: 用户货币记录
 * Author: hht
 * Date: 2018-02-07 17:17
 */
@Repository
public class UserCurrencyDaoImpl implements IUserCurrencyDao {

    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

}
