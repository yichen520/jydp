package com.jydp.service.impl.system;

import com.jydp.dao.ISystemHotDao;
import com.jydp.entity.DO.system.SystemHotDO;
import com.jydp.service.ISystemHotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 热门话题
 * @author zym
 *
 */
@Service("systemHotService")
public class SystemHotServiceImpl implements ISystemHotService {

    /** 热门话题 */
    @Autowired
    private ISystemHotDao systemHotDao;
    /**
     * web用户端热门话题列表查询
     * @return
     */
    @Override
    public List<SystemHotDO> getSystemHotlistForWeb() {
        return systemHotDao.getSystemHotlistForWeb();
    }
}
