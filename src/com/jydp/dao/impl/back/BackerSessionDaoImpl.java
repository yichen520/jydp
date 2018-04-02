package com.jydp.dao.impl.back;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jydp.dao.IBackerSessionDao;
import com.jydp.entity.DO.back.BackerSessionDO;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.iqmkj.utils.DateUtil;
import com.iqmkj.utils.LogUtil;

/**
 * 系统管理员登录记录
 * @author haotian
 *
 */
@Repository
public class BackerSessionDaoImpl implements IBackerSessionDao {

    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;
    
    /**
     * 添加登录记录
     * @param backerSession 登录记录
     * @return 操作成功：返回true，操作失败：返回false
     */
    public boolean insertBackerSession(BackerSessionDO backerSession) {
        int result = 0;
        
        try {
            result = sqlSessionTemplate.insert("BackerSession_insertBackerSession", backerSession);
        } catch (Exception e) {
            LogUtil.printErrorLog(e);
        }
        
        if(result > 0){
            return true;
        }else{
            return false;
        }
    }
    
    /**
     * 查询登录记录
     * @param sessionId sessionId
     * @return 查询成功：返回登录记录，查询失败：返回null
     */
    public BackerSessionDO queryBackerSessionById(String sessionId) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("sessionId", sessionId);
        
        BackerSessionDO backerSession = null;
        
        try {
            backerSession = sqlSessionTemplate.selectOne("BackerSession_queryBackerSessionById", map);
        } catch (Exception e) {
            LogUtil.printErrorLog(e);
        }
        
        return backerSession;
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
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("startNumber", pageSize * pageNumber);
        map.put("pageSize", pageSize);
        map.put("backerAccount", backerAccount);
        map.put("startTime", startTime);
        map.put("endTime", endTime);
        
        List<BackerSessionDO> list = null;
        try {
            list = sqlSessionTemplate.selectList("BackerSession_listBackerSessionByPage", map);
        } catch (Exception e) {
            LogUtil.printErrorLog(e);
        }
        
        return list;
    }
    
    /**
     * 查询登录记录总数
     * @param backerAccount 后台账号，无值填null
     * @param startTime 查询开始时间，没有值填null
     * @param endTime 查询结束时间，没有值填null
     * @return 成功：totalNumber，失败或未查询到数据：0
     */
    public int countBackerSession(String backerAccount, Timestamp startTime, Timestamp endTime) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("backerAccount", backerAccount);
        map.put("startTime", startTime);
        map.put("endTime", endTime);
        
        int totalNumber = 0;
        try {
            totalNumber = sqlSessionTemplate.selectOne("BackerSession_countBackerSession", map);
        } catch (Exception e) {
            LogUtil.printErrorLog(e);
        }
        
        return totalNumber;
    }

    /**
     * 更新session
     * @param sessionId sessionId
     * @return 操作成功：返回true，操作失败：返回false
     */
    public boolean updateSessionById(int sessionId) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("sessionId", sessionId);
        map.put("loginTime", DateUtil.getCurrentTimeMillis());
        
        int resultNumber = 0;
        try {
            resultNumber = sqlSessionTemplate.selectOne("BackerSession_updateSessionById", map);
        } catch (Exception e) {
            LogUtil.printErrorLog(e);
        }
        
        if(resultNumber > 0){
            return true;
        }else{
            return false;
        }
    }

	/**
	 * 查询session数量
	 * @param loginTime 登录时间
	 * @return 操作成功：返回数量，操作失败：返回0
	 */
    public int countSession(Timestamp loginTime) {
		int resultNumber = 0;
        try {
            resultNumber = sqlSessionTemplate.selectOne("BackerSession_countSession", loginTime);
        } catch (Exception e) {
            LogUtil.printErrorLog(e);
        }
        return resultNumber;
	}

}
