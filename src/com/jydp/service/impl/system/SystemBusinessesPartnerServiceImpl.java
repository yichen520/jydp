package com.jydp.service.impl.system;

import com.jydp.dao.ISystemBusinessesPartnerDao;
import com.jydp.entity.DO.system.SystemBusinessesPartnerDO;
import com.jydp.service.ISystemBusinessesPartnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 合作商家
 * @author zym
 *
 */
@Service("systemBusinessesPartnerService")
public class SystemBusinessesPartnerServiceImpl implements ISystemBusinessesPartnerService {

    /** 合作商家 */
    @Autowired
    private ISystemBusinessesPartnerDao systemBusinessesPartnerDao;

    /**
     * web用户端查询合作商家
     * @return 查询成功：返回合作商家列表；查询失败：返回null
     */
    @Override
    public List<SystemBusinessesPartnerDO> getSystemBusinessesPartnerForWeb() {
        return systemBusinessesPartnerDao.getSystemBusinessesPartnerForWeb();
    }
}
