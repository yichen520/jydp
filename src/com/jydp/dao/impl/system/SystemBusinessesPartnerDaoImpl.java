package com.jydp.dao.impl.system;

import com.iqmkj.utils.LogUtil;
import com.jydp.dao.ISystemBusinessesPartnerDao;
import com.jydp.entity.DO.system.SystemBusinessesPartnerDO;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 合作商家
 * @author zym
 *
 */
@Repository
public class SystemBusinessesPartnerDaoImpl implements ISystemBusinessesPartnerDao {

    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    /**
     * web用户端查询合作商家
     * @return 查询成功：返回合作商家列表；查询失败：返回null
     */
    @Override
    public List<SystemBusinessesPartnerDO> getSystemBusinessesPartnerForWeb() {
        List<SystemBusinessesPartnerDO> systemBusinessesPartnerList = null;

        try {
            systemBusinessesPartnerList = sqlSessionTemplate.selectList("SystemBusinessesPartner_getSystemBusinessesPartnerForWeb");
        } catch (Exception e) {
            LogUtil.printErrorLog(e);
        }
        return systemBusinessesPartnerList;
    }
}
