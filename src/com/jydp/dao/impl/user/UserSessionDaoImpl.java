package com.jydp.dao.impl.user;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import com.jydp.dao.IUserSessionDao;
import com.jydp.entity.DO.user.UserSessionDO;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.iqmkj.utils.LogUtil;

/**
 * 用户登录记录
 * @author whx
 *
 */
@Repository
public class UserSessionDaoImpl implements IUserSessionDao {

	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;
	
	/**
	 * 新增用户登录记录
	 * @param userSessionDO 用户登录记录
	 * @return 操作成功：返回true，操作失败：返回false
	 */
	public boolean insertUserSession(UserSessionDO userSessionDO) {
		int result = 0;
		
		try {
			result = sqlSessionTemplate.insert("UserSession_insertUserSession", userSessionDO);
		} catch (Exception e) {
			LogUtil.printErrorLog(e);
		}
		
		if (result > 0) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * 查询用户登录记录
	 * @param sessionId 用户登录sessionId
	 * @return 操作成功：返回用户登录记录，操作失败：返回null
	 */
	public UserSessionDO getUserSessionBySessionId(String sessionId) {
		UserSessionDO userSessionDO = null;
		
		try {
			userSessionDO = sqlSessionTemplate.selectOne("UserSession_getUserSessionBySessionId", sessionId);
		} catch (Exception e) {
			LogUtil.printErrorLog(e);
		}
		
		return userSessionDO;
	}
	
	/**
	 * 查询session数量
	 * @param loginTime 登录时间
	 * @return 操作成功：返回数量，操作失败：返回0
	 */
	public int countSession(Timestamp loginTime) {
        int resultNumber = 0;
        try {
            resultNumber = sqlSessionTemplate.selectOne("UserSession_countSession", loginTime);
        } catch (Exception e) {
            LogUtil.printErrorLog(e);
        }
        
        return resultNumber;
    }

}
