package com.jydp.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.iqmkj.config.ApplicationContextHandle;
import com.iqmkj.utils.DateUtil;
import com.iqmkj.utils.IpAddressUtil;
import com.iqmkj.utils.NumberUtil;
import com.jydp.entity.BO.UserSessionBO;
import com.jydp.entity.DO.user.UserSessionDO;
import com.jydp.service.IRedisService;
import com.jydp.service.IUserSessionService;
import config.SessionConfig;
import config.SystemCommonConfig;
import config.SystemMessageConfig;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.sql.Timestamp;

public class WebInterceptor implements HandlerInterceptor {

    /** 用户登录记录 */
    private static IUserSessionService userSessionService;
    /** 用户登录记录 */
    private static IUserSessionService getSessionService() {
        if (userSessionService == null) {
            userSessionService = (IUserSessionService) ApplicationContextHandle.getBean("userSessionService");
        }
        return userSessionService;
    }

    /** redis服务 */
    private static IRedisService redisService;

    /** redis服务 */
    private static IRedisService getRedisService() {
        if (redisService == null) {
            redisService = (IRedisService) ApplicationContextHandle.getBean("redisService");
        }
        return redisService;
    }

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        httpServletResponse.setHeader("Access-Control-Allow-Origin", "*");
        httpServletResponse.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        httpServletResponse.setHeader("Access-Control-Max-Age", "3600");
        httpServletResponse.setHeader("Access-Control-Allow-Headers", "x-requested-with");
        UserSessionBO userSession = (UserSessionBO) httpServletRequest.getSession().getAttribute("userSession");

        if (userSession != null) {
            return true;
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("message", SystemMessageConfig.SYSTEM_MESSAGE_LOGIN_EXPIRED);
        jsonObject.put("code", SystemMessageConfig.SYSTEM_CODE_LOGIN_EXPIRED);
        httpServletResponse.setCharacterEncoding("UTF-8");
        httpServletResponse.setContentType("text/html;charset=utf-8");
        PrintWriter out = httpServletResponse.getWriter();
        out.append(jsonObject.toString());
        out.close();
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }

    /**
     * 记录登录成功
     * @param request 当前用户请求
     * @param userSession 用户信息
     * @return 操作成功：返回sessionId，操作失败：返回null
     */
    public static void loginSuccess(HttpServletRequest request ,UserSessionBO userSession) {
        Timestamp curTime = DateUtil.getCurrentTime();
        String sessionId = SystemCommonConfig.LOGIN_USER + DateUtil.longToTimeStr(curTime.getTime(), DateUtil.dateFormat10) +
                NumberUtil.createNumberStr(12);
        String ipAddress = IpAddressUtil.getIpAddress(request);

        UserSessionDO userSessionDO = new UserSessionDO();
        userSessionDO.setSessionId(sessionId);
        userSessionDO.setUserId(userSession.getUserId());
        userSessionDO.setLoginForm(1);
        userSessionDO.setIpAddress(ipAddress);
        userSessionDO.setLoginTime(curTime);
        boolean insertResult = getSessionService().insertUserSession(userSessionDO);
        if (!insertResult) {
            return;
        }
        //session缓存信息
        userSession.setOutTime(curTime.getTime());
        request.getSession().setAttribute("userSession", userSession);
        //记录用户id与sessionid的映射关系
        String springSessionId = request.getSession().getId();
        String SESSION_USER_KEY = SessionConfig.SESSION_USER_ID + String.valueOf(userSession.getUserId());
        String SESSION_SESSIONS_KEY = SessionConfig.SESSION_SESSIONS + springSessionId;
        getRedisService().addList(SESSION_USER_KEY, SESSION_SESSIONS_KEY, SessionConfig.SESSION_TIME_OUT);
        return;
    }

    /**
     * 用户操作频繁
     * @param request
     * @return 操作频繁：返回true，否则：返回false
     */
    public static boolean handleFrequent(HttpServletRequest request) {
        UserSessionBO userSession = (UserSessionBO) request.getSession().getAttribute("userSession");
        if (userSession == null) {
            return false;
        }

        long currentTime = DateUtil.getCurrentTimeMillis();
        long outTime = userSession.getOutTime();
        userSession.setOutTime(currentTime);
        request.getSession().setAttribute("userSession", userSession);

        if ((currentTime - outTime) <= 1000) {
            return true;
        }
        return false;
    }
    /**
     * 获取用户信息
     * @param request 当前用户请求
     * @return 查询成功：返回数据，未查询到数据或查询失败：返回NULL
     */
    public static UserSessionBO getUser(HttpServletRequest request) {
        return (UserSessionBO) request.getSession().getAttribute("userSession");
    }
    /**
     * 退出登录
     * @param request 当前用户请求
     */
    public static void loginOut(HttpServletRequest request) {
        UserSessionBO userSession = (UserSessionBO) request.getSession().getAttribute("userSession");
        if(userSession != null) {
            String SESSION_USER_KEY = SessionConfig.SESSION_USER_ID + String.valueOf(userSession.getUserId());
            getRedisService().deleteValue(SESSION_USER_KEY);
        }
        request.getSession().removeAttribute("userSession");
        request.getSession().invalidate();
    }
}
