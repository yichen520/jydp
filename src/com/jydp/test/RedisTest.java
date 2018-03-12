package com.jydp.test;

import com.iqmkj.utils.DateUtil;
import com.jydp.dao.ITransactionMakeOrderDao;
import com.jydp.entity.DO.transaction.TransactionCurrencyDO;
import com.jydp.entity.DO.transaction.TransactionMakeOrderDO;
import com.jydp.entity.DO.transaction.TransactionUserDealDO;
import com.jydp.entity.DO.user.UserDO;
import com.jydp.service.IRedisService;
import com.jydp.service.ITransactionCurrencyService;
import com.jydp.service.ITransactionMakeOrderService;
import com.jydp.service.ITransactionUserDealService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;


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
public class RedisTest {

    @Autowired
    private IRedisService redisService;

    @Test
    public void testRedis() {
        UserDO user = new UserDO();
        user.setUserAccount("mmp");
        redisService.addValue("cnm",user);
        UserDO u = (UserDO) redisService.getValue("cnm");
        System.out.println(u.getUserAccount());

    }

}
