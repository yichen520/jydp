package com.jydp.dao.impl.system;

import com.iqmkj.utils.LogUtil;
import com.jydp.dao.ISystemAdsHomepagesDao;
import com.jydp.entity.DO.system.SystemAdsHomepagesDO;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 首页广告
 * @author zym
 *
 */
@Repository
public class SystemAdsHomepagesDaoImpl implements ISystemAdsHomepagesDao {

    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    /**
     * web端用户查询首页广告列表
     * @return 查询成功：返回首页广告列表；查询失败：返回null
     */
    @Override
    public List<SystemAdsHomepagesDO> getSystemAdsHomepageslistForWeb() {
        List<SystemAdsHomepagesDO> systemAdsHomepagesList = null;

        try {
            systemAdsHomepagesList = sqlSessionTemplate.selectList("SystemAdsHomepages_getSystemAdsHomepageslistForWeb");
        } catch (Exception e) {
            LogUtil.printErrorLog(e);
        }
        return systemAdsHomepagesList;
    }
}
