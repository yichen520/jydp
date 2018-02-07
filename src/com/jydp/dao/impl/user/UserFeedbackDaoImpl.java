package com.jydp.dao.impl.user;

import com.jydp.dao.IUserFeedbackDao;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Description: 意见反馈
 * Author: hht
 * Date: 2018-02-07 17:22
 */
@Repository
public class UserFeedbackDaoImpl implements IUserFeedbackDao {

    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;
}
