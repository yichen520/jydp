package com.jydp.dao.impl.system;

import com.jydp.dao.ISystemBusinessesPartnerDao;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * 合作商家
 * @author zym
 *
 */
@Repository
public class SystemBusinessesPartnerDaoImpl implements ISystemBusinessesPartnerDao {

    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;
}
