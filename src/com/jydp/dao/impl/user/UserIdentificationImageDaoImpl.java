package com.jydp.dao.impl.user;

import com.jydp.dao.IUserIdentificationImageDao;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Description: 用户认证详情图
 * Author: hht
 * Date: 2018-02-07 17:12
 */
@Repository
public class UserIdentificationImageDaoImpl implements IUserIdentificationImageDao {

    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

}
