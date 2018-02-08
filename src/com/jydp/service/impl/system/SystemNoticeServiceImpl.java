package com.jydp.service.impl.system;

import com.jydp.dao.ISystemNoticeDao;
import com.jydp.entity.DO.system.SystemNoticeDO;
import com.jydp.service.ISystemNoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 系统公告
 * @author zym
 *
 */
@Service("systemNoticeService")
public class SystemNoticeServiceImpl implements ISystemNoticeService {

    /** 系统公告 */
    @Autowired
    private ISystemNoticeDao systemNoticeDao;

    /**
     * web用户端查询系统公告列表
     * @return 查询成功：返回系统公告列表；查询失败：返回null
     */
    @Override
    public List<SystemNoticeDO> getSystemNoticlistForWeb() {
        return systemNoticeDao.getSystemNoticlistForWeb();
    }
}

