package com.jydp.service.impl.user;

import java.sql.Timestamp;
import java.util.List;

import com.jydp.dao.IUserSessionDao;
import com.jydp.entity.DO.user.UserSessionDO;
import com.jydp.service.IRedisService;
import com.jydp.service.IUserSessionService;
import config.SessionConfig;
import org.apache.commons.collections4.CollectionUtils;
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

	/** reids服务 */
	@Autowired
	private IRedisService redisService;

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
	 * 删除redis中用户的session
	 * @param userId 用户Id
	 * @return 操作成功：返回true，操作失败：返回false
	 */
	public boolean deleteRedisSession(int userId) {
		if (userId <= 0) {
			return false;
		}

		String userIdKey = SessionConfig.SESSION_USER_ID + String.valueOf(userId);
		List<Object> sessionList = redisService.getList(userIdKey);
		if (CollectionUtils.isEmpty(sessionList)) {
			return true;
		}

		for (Object object:sessionList
			 ) {
			if (object == null) {
				continue;
			}
			redisService.deleteMapValue(String.valueOf(object), SessionConfig.SESSION_USER_ATTR);
		}
		redisService.deleteValue(userIdKey);
		return true;
	}

}
