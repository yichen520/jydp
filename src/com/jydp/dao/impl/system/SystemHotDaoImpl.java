package com.jydp.dao.impl.system;

import com.jydp.dao.ISystemHotDao;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * 热门话题
 * @author zym
 *
 */
@Repository
public class SystemHotDaoImpl implements ISystemHotDao {

    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;
}
