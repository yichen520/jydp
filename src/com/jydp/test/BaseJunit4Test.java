package com.jydp.test;

import com.jydp.entity.DO.user.UserDO;
import com.jydp.service.IRedisService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

/**
 * JUnit4测试
 * @author whx
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)//JUnit4进行测试
@ContextConfiguration(locations={"classpath:spring/spring-*.xml"})//加载spring配置文件
@WebAppConfiguration("src/resources")//加载resources
@Transactional(transactionManager = "transactionManager")//不加入这个注解配置，事务控制会失效
@Rollback(value = false)//true为回滚(执行成功也不会修改数据)，false不回滚(会修改数据)。若使用@Commit不会回滚。
public class BaseJunit4Test {

    /** 用户登录记录 */
    /*@Autowired
    private IUserSessionService userSessionService;*/

    @Autowired
    private IRedisService redisService;

    /**
     * 测试示例
     */
    /*@Test
    public void testUser() {
        Timestamp curTime = DateUtil.getCurrentTime();
        String sessionId = SystemCommonConfig.LOGIN_USER + DateUtil.longToTimeStr(curTime.getTime(), DateUtil.dateFormat10) +
                NumberUtil.createNumberStr(12);

        UserSessionDO userSessionDO = new UserSessionDO();
        userSessionDO.setSessionId(sessionId);
        userSessionDO.setUserId(1);
        userSessionDO.setLoginForm(1);
        userSessionDO.setIpAddress("127.0.0.1");
        userSessionDO.setLoginTime(curTime);

        userSessionService.insertUserSession(userSessionDO);
    }*/

/*    @Autowired
    private ITransactionPendOrderService transactionPendOrderService;

    @Test
    public void testInsertPendOrder() {
        Timestamp curTime = DateUtil.getCurrentTime();

        transactionPendOrderService.insertPendOrder("12201802081234567890",1, 1, 1,
        "btc", 8288.36, 12.35,
        0, 1, "挂单", curTime);
    }

    @Test
    public void testUpdatePendOrder() {
        Timestamp curTime = DateUtil.getCurrentTime();

        transactionPendOrderService.updatePendOrder("12201802081234567890",1, 1, "修改", curTime);
    }

    @Test
    public void testGetPendOrderByPendingOrderNo() {
        transactionPendOrderService.getPendOrderByPendingOrderNo("12201802081234567890");
    }

    @Test
    public void testCountPendOrderByUserId() {
        transactionPendOrderService.countPendOrderByUserId(1);
    }

    @Test
    public void testListPendOrderByUserId() {
        transactionPendOrderService.listPendOrderByUserId(1, 1,20);
    }

    @Test
    public void testUpdatePendingStatus() {
        transactionPendOrderService.updatePendingStatus("12201802081234567890", 1);
    }

    @Test
    public void testListLatestRecords() {
        transactionPendOrderService.listLatestRecords(1,1,6);
    }

    @Test
    public void testCountPendOrderForBack() {
        Timestamp curTime = DateUtil.getCurrentTime();

        transactionPendOrderService.countPendOrderForBack("hz",1,1,1, curTime,
                curTime, curTime, curTime);
    }

    @Test
    public void testListPendOrderForBack() {
        Timestamp curTime = DateUtil.getCurrentTime();

        transactionPendOrderService.listPendOrderForBack("hz",1,1,1, curTime,
                curTime, curTime, curTime,1,20);
    }*/

    @Test
    public void testRedis() {
        UserDO user = new UserDO();
        user.setUserAccount("mmp");
        redisService.addValue("123",user);
        UserDO u = (UserDO) redisService.getValue("cnm");
        System.out.println(u.getUserAccount());
    }

}
