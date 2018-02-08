package com.jydp.service.impl.system;

import com.jydp.dao.ISystemAdsHomepagesDao;
import com.jydp.entity.DO.system.SystemAdsHomepagesDO;
import com.jydp.service.ISystemAdsHomepagesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 首页广告
 * @author zym
 *
 */
@Service("systemAdsHomepagesService")
public class SystemAdsHomepagesServiceImpl implements ISystemAdsHomepagesService {

    /** 首页广告 */
    @Autowired
    private ISystemAdsHomepagesDao systemAdsHomepagesDao;

    /**
     * web端用户查询首页广告列表
     * @return 查询成功：返回首页广告列表；查询失败：返回null
     */
    @Override
    public List<SystemAdsHomepagesDO> getSystemAdsHomepageslistForWeb() {
        return systemAdsHomepagesDao.getSystemAdsHomepageslistForWeb();
    }
}
