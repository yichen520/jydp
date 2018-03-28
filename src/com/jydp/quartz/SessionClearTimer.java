package com.jydp.quartz;


/**
 * 清除数据库session记录（该定时服务暂停使用）
 * @author whx
 *
 */
//@Component
public class SessionClearTimer {

	/** 用户登录记录 */
	/*@Autowired
	private IUserSessionService userSessionService;*/

	/** 系统管理员登录记录 */
	/*@Autowired
	private IBackerSessionService backerSessionService;*/
	
	/** 清除数据库session记录（每天凌晨4点执行一次） */
	/*@Scheduled(cron="0 0 4  * * ? ")
	public void executeClear(){
		Timestamp currTime = DateUtil.getCurrentTime();
		long currTimeLong = currTime.getTime() - 1L * 7 * 24 * 60 * 60 * 1000;
		Timestamp loginTime = DateUtil.longToTimestamp(currTimeLong);
		
		int pageSize = 1000;
		//查询用户session总数
		int userTotalNumber = userSessionService.countSession(loginTime);
		int userPageNumber = (int) Math.ceil(userTotalNumber / (pageSize * 1.0));
        if (userPageNumber <= 0) {
        	userPageNumber = 1;
        }

        //查询后台session总数
        int backerTotalNumber = backerSessionService.countSession(loginTime);
        int backerPageNumber = (int) Math.ceil(backerTotalNumber / (pageSize * 1.0));
        if (backerPageNumber <= 0) {
        	backerPageNumber = 1;
        }
		// 删除用户session
        executeUserSession(loginTime, userPageNumber, pageSize);
		// 删除后台session
        executeBackerSession(loginTime, backerPageNumber, pageSize);
	}*/
	
	/** 删除用户session */
	/*public void executeUserSession(Timestamp loginTime, int totalPageNumber, int pageSize) {
		for (int pageNumber = 0; pageNumber <= totalPageNumber; pageNumber++) {
			userSessionService.deleteSessionByTimeForTimer(loginTime, pageSize);
		}
	}*/

	/** 删除后台session */
	/*public void executeBackerSession(Timestamp loginTime, int totalPageNumber, int pageSize) {
		for (int pageNumber = 0; pageNumber <= totalPageNumber; pageNumber++) {
			backerSessionService.deleteSessionByTimeForTimer(loginTime, pageSize);
		}
	}*/
	
}
