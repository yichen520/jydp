package com.jydp.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.iqmkj.config.ApplicationContextHandle;
import com.iqmkj.utils.DateUtil;
import com.iqmkj.utils.IpAddressUtil;
import com.iqmkj.utils.NumberUtil;
import com.iqmkj.utils.StringUtil;
import com.jydp.entity.BO.BackerSessionBO;
import com.jydp.entity.DO.back.BackerRolePowerDO;
import com.jydp.entity.DO.back.BackerSessionDO;
import com.jydp.service.IBackerRolePowerService;
import com.jydp.service.IBackerSessionService;
import com.jydp.service.IRedisService;
import config.SessionConfig;
import config.SystemCommonConfig;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.sql.Timestamp;

/**
 * 后台拦截器(WEB端访问)
 * @author sy
 *
 */
public class BackerWebInterceptor implements HandlerInterceptor {
    /** 后台管理员登录记录 */
    private static IBackerSessionService backerSessionService;

    /** 后台管理员登录记录 */
    private static IBackerSessionService getSessionService(HttpServletRequest request) {
        if (backerSessionService == null) {
            backerSessionService = (IBackerSessionService) ApplicationContextHandle.getBean("backerSessionService");
        }
        return backerSessionService;
    }

    /** 后台管理员的角色权限服务层 */
    private static IBackerRolePowerService backerRolePowerService;

    /** 后台管理员的角色权限服务层 */
    private static IBackerRolePowerService getRolePowerService(HttpServletRequest request) {
        if (backerRolePowerService == null) {
            backerRolePowerService = (IBackerRolePowerService) ApplicationContextHandle.getBean("backerRolePowerService");
        }
        return backerRolePowerService;
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
        BackerSessionBO backerSessionBO = (BackerSessionBO)request.getSession().getAttribute("backerSession");
        if (backerSessionBO == null) {
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
                request.setAttribute("message", "登录过期，请重新登录");
                request.getRequestDispatcher("/backLogin").forward(request, response);
                return false;
            }
        }
        return true;
    }

    /**
     * 记录登录成功
     * @param request 当前管理员请求
     * @param backerSessionBO 后台管理员信息
     * @param roleId 角色Id
     * @return 操作成功：返回sessionId，操作失败：返回null
     */
    public static void loginSuccess(HttpServletRequest request, BackerSessionBO backerSessionBO, int roleId) {
        Timestamp curTime = DateUtil.getCurrentTime();
        String sessionId = SystemCommonConfig.LOGIN_BACKER + DateUtil.longToTimeStr(curTime.getTime(), DateUtil.dateFormat10)
                + NumberUtil.createNumberStr(12);
        String ipAddress = IpAddressUtil.getIpAddress(request);

        //登录信息
        BackerSessionDO backerSessionDO = new BackerSessionDO();
        backerSessionDO.setSessionId(sessionId);
        backerSessionDO.setBackerId(backerSessionBO.getBackerId());
        backerSessionDO.setBackerAccount(backerSessionBO.getBackerAccount());
        backerSessionDO.setIpAddress(ipAddress);
        backerSessionDO.setLoginTime(curTime);

        //添加登录记录
        boolean addBoo = getSessionService(request).insertBackerSession(backerSessionDO);
        if (!addBoo) {
            return;
        }

        //session缓存outTime
        backerSessionBO.setOutTime(curTime.getTime());

        //获取角色权限
        BackerRolePowerDO isocRolePower = getRolePowerService(request).getRolePower(roleId);
        if (isocRolePower == null || !StringUtil.isNotNull(isocRolePower.getPowerJson())) {
            return;
        }

        JSONObject powerJson = JSONObject.parseObject(isocRolePower.getPowerJson());
        if (powerJson == null || powerJson.isEmpty()) {
            return;
        }

        request.getSession().setAttribute("backer_rolePower", powerJson);
        request.getSession().setAttribute("backerSession", backerSessionBO);
        //记录管理员id与sessionid的映射关系
        String springSessionId = request.getSession().getId();
        String SESSION_BACKER_KEY = SessionConfig.SESSION_BACKER_ID + String.valueOf(backerSessionBO.getBackerId());
        String SESSION_SESSIONS_KEY = SessionConfig.SESSION_SESSIONS + springSessionId;
        getRedisService(request).addList(SESSION_BACKER_KEY, SESSION_SESSIONS_KEY, SessionConfig.SESSION_TIME_OUT);
    }

    /**
     * 获取管理员信息
     * @param request 当前管理员请求
     * @return 查询成功：返回数据，未查询到数据或查询失败：返回NULL
     */
    public static BackerSessionBO getBacker(HttpServletRequest request) {
        return (BackerSessionBO) request.getSession().getAttribute("backerSession");
    }

    /**
     * 验证是否有该权限
     * @param request 当前管理员请求
     * @param powerId 权限id
     * @return 有该权限：返回true，没有该权限：返回false
     */
    public static boolean validatePower(HttpServletRequest request, int powerId) {
        JSONObject rolePowerJson = (JSONObject) request.getSession().getAttribute("backer_rolePower");
        if (rolePowerJson == null || rolePowerJson.isEmpty()) {
            return false;
        }
        if (rolePowerJson.containsKey(String.valueOf(powerId))) {
            return true;
        }
        return false;
    }

    /**
     * 后台管理员操作频繁
     * @param request
     * @return 操作频繁：返回true，否则：返回false
     */
    public static boolean handleFrequent(HttpServletRequest request) {
        BackerSessionBO backerSession = (BackerSessionBO) request.getSession().getAttribute("backerSession");
        if (backerSession == null) {
            return false;
        }

        long outTime = backerSession.getOutTime();
        long currentTime = DateUtil.getCurrentTimeMillis();
        backerSession.setOutTime(currentTime);
        request.getSession().setAttribute("backerSession", backerSession);

        if ((currentTime - outTime) <= 1000) {
            return true;
        }
        return false;
    }

    /**
     * 退出登录
     * @param request 当前管理员请求
     */
    public static void loginOut(HttpServletRequest request) {
        request.getSession().removeAttribute("backer_rolePower");
        request.getSession().removeAttribute("backerSession");
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
