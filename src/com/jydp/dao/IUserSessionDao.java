package com.jydp.dao;

import com.jydp.entity.DO.user.UserSessionDO;

import java.sql.Timestamp;

/**
 * 用户登录记录
 * @author whx
 *
 */
public interface IUserSessionDao {

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
	 * @param loginTime 登录时间
	 * @return 操作成功：返回数量，操作失败：返回0
	 */
	int countSession(Timestamp loginTime);

}
