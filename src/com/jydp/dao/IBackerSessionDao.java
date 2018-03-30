package com.jydp.dao;

import com.jydp.entity.DO.back.BackerSessionDO;

import java.sql.Timestamp;
import java.util.List;


/**
 * 系统管理员登录记录
 * @author whx
 *
 */
public interface IBackerSessionDao {

    /**
     * 添加登录记录
     * @param backerSession 登录记录
     * @return 操作成功：返回true，操作失败：返回false
     */
    boolean insertBackerSession(BackerSessionDO backerSession);
    
    /**
     * 查询登录记录
     * @param sessionId sessionId
     * @return 查询成功：返回登录记录，查询失败：返回null
     */
    BackerSessionDO queryBackerSessionById(String sessionId);
    
    /**
     * 分页查询登录记录
     * @param pageNumber 当前页数
     * @param pageSize 每页大小
     * @param backerAccount 后台账号，无值填null
     * @param startTime 查询开始时间，没有值填null
     * @param endTime 查询结束时间，没有值填null
     * @return 成功：list，失败或未查询到数据：null
     */
    List<BackerSessionDO> listBackerSessionByPage(int pageNumber, int pageSize, String backerAccount,
                                                  Timestamp startTime, Timestamp endTime);
    
    /**
     * 查询登录记录总数
     * @param backerAccount 后台账号，无值填null
     * @param startTime 查询开始时间，没有值填null
     * @param endTime 查询结束时间，没有值填null
     * @return 成功：totalNumber，失败或未查询到数据：0
     */
    int countBackerSession(String backerAccount, Timestamp startTime, Timestamp endTime);

    /**
     * 更新session
     * @param sessionId sessionId
     * @return 操作成功：返回true，操作失败：返回false
     */
    boolean updateSessionById(int sessionId);

	/**
	 * 查询session数量
	 * @param loginTime 登录时间
	 * @return 操作成功：返回数量，操作失败：返回0
	 */
	int countSession(Timestamp loginTime);

}
