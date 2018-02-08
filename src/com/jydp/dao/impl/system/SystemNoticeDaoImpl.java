package com.jydp.dao.impl.system;

import com.iqmkj.utils.LogUtil;
import com.jydp.dao.ISystemNoticeDao;
import com.jydp.entity.DO.system.SystemNoticeDO;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 系统公告
 * @author zym
 *
 */
@Repository
public class SystemNoticeDaoImpl implements ISystemNoticeDao {

    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    /**
     * web用户端查询系统公告列表
     * @return 查询成功：返回系统公告列表；查询失败：返回null
     */
    @Override
    public List<SystemNoticeDO> getSystemNoticlistForWeb() {
        List<SystemNoticeDO> systemNoticeList = null;

        try {
            systemNoticeList = sqlSessionTemplate.selectList("SystemNotice_getSystemNoticlistForWeb");
        } catch (Exception e) {
            LogUtil.printErrorLog(e);
        }
        return systemNoticeList;
    }
}
