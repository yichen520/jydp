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
	 * @param loginTime 登陆时间
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
	
    /**
     * 删除session（定时器操作,该方法暂停使用）
     * @param loginTime 登陆时间
     * @param pageSize 删除数量
     * @return 操作成功：返回true，操作失败：返回false
     */
    public boolean deleteSessionByTimeForTimer(Timestamp loginTime, int pageSize) {
    	Map<String, Object> map = new HashMap<String, Object>();
    	map.put("loginTime", loginTime);
    	map.put("pageSize", pageSize);
    	
        int resultNumber = 0;
        try {
            resultNumber = sqlSessionTemplate.delete("UserSession_deleteSessionByTimeForTimer", map);
        } catch (Exception e) {
            LogUtil.printErrorLog(e);
        }
        
        if(resultNumber > 0 && resultNumber == pageSize){
            return true;
        }else{
            return false;
        }
    }

	/**
	 * 删除用户的session（该方法暂停使用）
	 * @param userId 用户Id
	 * @return 操作成功：返回true，操作失败：返回false
	 */
	public boolean deleteSessionByUserId(int userId) {
		int resultNumber = 0;
		try {
			resultNumber = sqlSessionTemplate.delete("UserSession_deleteSessionByUserId", userId);
		} catch (Exception e) {
			LogUtil.printErrorLog(e);
		}

		if(resultNumber > 0){
			return true;
		}else{
			return false;
		}
	}
	
}
