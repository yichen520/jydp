package com.jydp.service.impl.back;

import java.sql.Timestamp;
import java.util.List;

import com.jydp.dao.IBackerSessionDao;
import com.jydp.entity.DO.back.BackerSessionDO;
import com.jydp.service.IBackerSessionService;
import com.jydp.service.IRedisService;
import config.SessionConfig;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * 系统管理员登录记录
 * @author whx
 *
 */
@Service("backerSessionService")
public class BackerSessionServiceImpl implements IBackerSessionService {

    /**系统管理员登录记录*/
    @Autowired
    private IBackerSessionDao backerSessionDao;

    /** reids服务 */
    @Autowired
    private IRedisService redisService;

    /**
     * 添加登录记录
     * @param backerSession 登录记录
     * @return 操作成功：返回true，操作失败：返回false
     */
    public boolean insertBackerSession(BackerSessionDO backerSession) {
        return backerSessionDao.insertBackerSession(backerSession);
    }
    
    /**
     * 查询登录记录
     * @param sessionId sessionId
     * @return 查询成功：返回登录记录，查询失败：返回null
     */
    public BackerSessionDO queryBackerSessionById(String sessionId) {
        return backerSessionDao.queryBackerSessionById(sessionId);
    }
    
    /**
     * 分页查询登录记录
     * @param pageNumber 当前页数
     * @param pageSize 每页大小
     * @param backerAccount 后台账号，无值填null
     * @param startTime 查询开始时间，没有值填null
     * @param endTime 查询结束时间，没有值填null
     * @return 成功：list，失败或未查询到数据：null
     */
    public List<BackerSessionDO> listBackerSessionByPage(int pageNumber, int pageSize, String backerAccount, 
            Timestamp startTime, Timestamp endTime) {
        return backerSessionDao.listBackerSessionByPage(pageNumber, pageSize, backerAccount, startTime, endTime);
    }
    
    /**
     * 查询登录记录总数
     * @param backerAccount 后台账号，无值填null
     * @param startTime 查询开始时间，没有值填null
     * @param endTime 查询结束时间，没有值填null
     * @return 成功：totalNumber，失败或未查询到数据：0
     */
    public int countBackerSession(String backerAccount, Timestamp startTime, Timestamp endTime) {
        return backerSessionDao.countBackerSession(backerAccount, startTime, endTime);
    }

    /**
     * 更新session
     * @param sessionId sessionId
     * @return 操作成功：返回true，操作失败：返回false
     */
    public boolean updateSessionById(int sessionId) {
        return backerSessionDao.updateSessionById(sessionId);
    }

	/**
	 * 查询session数量
	 * @param loginTime 登陆时间
	 * @return 操作成功：返回数量，操作失败：返回0
	 */
    public int countSession(Timestamp loginTime) {
    	return backerSessionDao.countSession(loginTime);
    }

    /**
     * 删除redis中管理员的session
     * @param backerId 管理员Id
     * @return 操作成功：返回true，操作失败：返回false
     */
    public boolean deleteRedisSession(int backerId) {
        if (backerId <= 0) {
            return false;
        }

        String backerIdKey = SessionConfig.SESSION_BACKER_ID + String.valueOf(backerId);
        List<Object> sessionList = redisService.getList(backerIdKey);
        if (CollectionUtils.isEmpty(sessionList)) {
            return true;
        }

        for (Object object:sessionList
                ) {
            if (object == null) {
                continue;
            }
            redisService.deleteMapValue(String.valueOf(object), SessionConfig.SESSION_BACKER_ATTR, SessionConfig.SESSION_BACKER_POWER);
        }
        redisService.deleteValue(backerIdKey);
        return true;
    }

}