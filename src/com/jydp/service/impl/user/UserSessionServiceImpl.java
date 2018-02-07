package com.jydp.service.impl.user;

import java.sql.Timestamp;

import com.jydp.dao.IUserSessionDao;
import com.jydp.entity.DO.user.UserSessionDO;
import com.jydp.service.IUserSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 用户登录记录
 * @author whx
 *
 */
@Service("userSessionService")
public class UserSessionServiceImpl implements IUserSessionService {

	/** 用户登录记录 */
	@Autowired
	private IUserSessionDao userSessionDao;

	/**
	 * 新增用户登录记录
	 * @param userSessionDO 用户登录记录
	 * @return 操作成功：返回true，操作失败：返回false
	 */
	public boolean insertUserSession(UserSessionDO userSessionDO) {
		return userSessionDao.insertUserSession(userSessionDO);
	}
	
	/**
	 * 查询用户登录记录
	 * @param sessionId 用户登录sessionId
	 * @return 操作成功：返回用户登录记录，操作失败：返回null
	 */
	public UserSessionDO getUserSessionBySessionId(String sessionId) {
		return userSessionDao.getUserSessionBySessionId(sessionId);
	}
	
	/**
	 * 查询session数量
	 * @param loginTime 登陆时间
	 * @return 操作成功：返回数量，操作失败：返回0
	 */
	public int countSession(Timestamp loginTime) {
		return userSessionDao.countSession(loginTime);
	}
	
    /**
     * 删除session（定时器操作）
     * @param loginTime 登陆时间
     * @return 操作成功：返回true，操作失败：返回false
     */
    public boolean deleteSessionByTimeForTimer(Timestamp loginTime, int pageSize) {
    	return userSessionDao.deleteSessionByTimeForTimer(loginTime, pageSize);
    }
	
}
