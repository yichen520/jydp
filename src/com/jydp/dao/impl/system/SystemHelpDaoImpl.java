package com.jydp.dao.impl.system;

import com.jydp.dao.ISystemHelpDao;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * 帮助中心
 * @author zym
 *
 */
@Repository
public class SystemHelpDaoImpl implements ISystemHelpDao {

    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;
}
