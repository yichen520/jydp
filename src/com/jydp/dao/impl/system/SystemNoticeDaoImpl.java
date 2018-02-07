package com.jydp.dao.impl.system;

import com.jydp.dao.ISystemNoticeDao;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * 系统公告
 * @author zym
 *
 */
@Repository
public class SystemNoticeDaoImpl implements ISystemNoticeDao {

    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;
}
