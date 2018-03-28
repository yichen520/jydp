package com.jydp.service;

import java.sql.Timestamp;

import com.jydp.entity.DO.user.UserSessionDO;

/**
 * 用户登录记录
 * @author whx
 *
 */
public interface IUserSessionService {

	/**
	 * 新增用户登录记录
	 * @param userSessionDO 用户登录记录
	 * @return 操作成功：返回true，操作失败：返回false
	 */
	boolean insertUserSession(UserSessionDO userSessionDO);
	
	/**
	 * 查询用户登录记录
	 * @param sessionId 用户登录sessionId
	 * @return 操作成功：返回用户登录记录，操作失败：返回null
	 */
	UserSessionDO getUserSessionBySessionId(String sessionId);
	
	/**
	 * 查询session数量
	 * @param loginTime 登陆时间
	 * @return 操作成功：返回数量，操作失败：返回0
	 */
	int countSession(Timestamp loginTime);
	
    /**
     * 删除session（定时器操作,该方法暂停使用）
     * @param loginTime 登陆时间
     * @param pageSize 删除数量
     * @return 操作成功：返回true，操作失败：返回false
     */
    boolean deleteSessionByTimeForTimer(Timestamp loginTime, int pageSize);

	/**
	 * 删除用户的session(该方法暂停使用)
	 * @param userId 用户Id
	 * @return 操作成功：返回true，操作失败：返回false
	 */
	boolean deleteSessionByUserId(int userId);

	/**
	 * 删除redis中用户的session
	 * @param userId 用户Id
	 * @return 操作成功：返回true，操作失败：返回false
	 */
	boolean deleteRedisSession(int userId);

}
