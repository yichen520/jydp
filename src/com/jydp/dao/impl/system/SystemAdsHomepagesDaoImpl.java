package com.jydp.dao.impl.system;

import com.jydp.dao.ISystemAdsHomepagesDao;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * 首页广告
 * @author zym
 *
 */
@Repository
public class SystemAdsHomepagesDaoImpl implements ISystemAdsHomepagesDao {

    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;
}
