package com.jydp.dao.impl.system;

import com.iqmkj.utils.LogUtil;
import com.jydp.dao.ISystemHotDao;
import com.jydp.entity.DO.system.SystemHotDO;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 热门话题
 * @author zym
 *
 */
@Repository
public class SystemHotDaoImpl implements ISystemHotDao {

    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;


    /**
     * web用户端热门话题列表查询
     * @return 查询成功：返回热门话题列表；查询失败：返回null
     */
    @Override
    public List<SystemHotDO> getSystemHotlistForWeb() {
        List<SystemHotDO> systemHotList = null;

        try {
            systemHotList = sqlSessionTemplate.selectList("SystemHot_getSystemHotlistForWeb");
        } catch (Exception e){
            LogUtil.printErrorLog(e);
        }
        return systemHotList;
    }
}
