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
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.sql.Timestamp;

/**
 * 用户拦截器(WEB端访问)
 * @author whx
 *
 */
public class UserWebInterceptor implements HandlerInterceptor {

    /** 用户登录记录 */
    private static IUserSessionService userSessionService;

    /** 用户登录记录 */
    private static IUserSessionService getSessionService(HttpServletRequest request) {
        if (userSessionService == null) {
            userSessionService = (IUserSessionService) ApplicationContextHandle.getBean("userSessionService");
        }
        return userSessionService;
    }

    /** redis服务 */
    private static IRedisService redisService;

    /** redis服务 */
    private static IRedisService getRedisService(HttpServletRequest request) {
        if (redisService == null) {
            redisService = (IRedisService) ApplicationContextHandle.getBean("redisService");
        }
        return redisService;
    }

    /**
     * 在请求到达运行的方法之前，用于拦截非法请求
     * 在controlller之前
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object controlller) throws Exception {
        UserSessionBO userSession = (UserSessionBO) request.getSession().getAttribute("userSession");
        if (userSession == null) {
            //如果是ajax请求
            if (request.getHeader("x-requested-with")!= null
                    && request.getHeader("x-requested-with").equalsIgnoreCase("XMLHttpRequest")) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("code", -1);
                jsonObject.put("message", "登录过期，请重新登录");

                PrintWriter out = null ;
                out = response.getWriter();
                out.append(jsonObject.toString());
                out.close();
                return false;
            } else {
                request.setAttribute("code", -1);
                request.setAttribute("message", "未登录");
                request.getRequestDispatcher("/webLogin").forward(request, response);
                return false;
            }
        }
        return true;
    }

    /**
     * 记录登录成功
     * @param request 当前用户请求
     * @param userSession 用户信息
     * @return 操作成功：返回sessionId，操作失败：返回null
     */
    public static void loginSuccess(HttpServletRequest request, UserSessionBO userSession) {
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
        boolean insertResult = getSessionService(request).insertUserSession(userSessionDO);
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
        getRedisService(request).addList(SESSION_USER_KEY, SESSION_SESSIONS_KEY, SessionConfig.SESSION_TIME_OUT);
        return;
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
     * 用户操作频繁
     * @param request
     * @return 操作频繁：返回true，否则：返回false
     */
    public static boolean handleFrequent(HttpServletRequest request) {
        UserSessionBO userSession = (UserSessionBO) request.getSession().getAttribute("userSession");
        if (userSession == null) {
            return false;
        }

        long outTime = userSession.getOutTime();
        long currentTime = DateUtil.getCurrentTimeMillis();
        userSession.setOutTime(currentTime);
        request.getSession().setAttribute("userSession", userSession);

        if ((currentTime - outTime) <= 1000) {
            return true;
        }
        return false;
    }

    /**
     * 退出登录
     * @param request 当前用户请求
     */
    public static void loginOut(HttpServletRequest request) {
        request.getSession().removeAttribute("userSession");
        request.getSession().invalidate();
    }

    /**
     * 用于重定向方法，这个方法可以重新返回一个新的页面，进行新的数据展示
     * 在controller之后
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object controlller, ModelAndView modelAndView) throws Exception {
    }

    /**
     * 所有程序完成之后最终会执行的方法，一般用于销毁对象IO等操作
     * 在postHandle之后
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object controlller, Exception exception) throws Exception {
    }

}
