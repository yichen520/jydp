package com.jydp.test;

import com.jydp.entity.DO.transaction.TransactionCurrencyDO;
import com.jydp.entity.DTO.TransactionPendOrderDTO;
import com.jydp.service.IRedisService;
import com.jydp.service.ITransactionCurrencyService;
import com.jydp.service.ITransactionPendOrderService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 测试redids
 * @author whx
 */
@RunWith(SpringJUnit4ClassRunner.class)//JUnit4进行测试
@ContextConfiguration(locations={"classpath:spring/spring-*.xml"})//加载spring配置文件
@WebAppConfiguration("src/resources")//加载resources
@Transactional(transactionManager = "transactionManager")//不加入这个注解配置，事务控制会失效
@Rollback(value = false)//true为回滚(执行成功也不会修改数据)，false不回滚(会修改数据)。若使用@Commit不会回滚。
public class RedisTest {

    /** redis服务 */
    @Autowired
    private IRedisService redisService;

    /** 挂单记录 */
    @Autowired
    private ITransactionPendOrderService transactionPendOrderService;

    /** 交易币种 */
    @Autowired
    private ITransactionCurrencyService transactionCurrencyService;

    @Test
    public void testRedis() {
        //获取所有币种
        List<TransactionCurrencyDO> transactionCurrencyList = transactionCurrencyService.getTransactionCurrencyListForWeb();
        if(transactionCurrencyList.isEmpty()){
            return;
        }

        for (TransactionCurrencyDO transactionCurrency: transactionCurrencyList) {
            //买入记录
            List<TransactionPendOrderDTO> transactionPendOrderBuyList =
                    transactionPendOrderService.listLatestRecords(1,transactionCurrency.getCurrencyId(),15);

            //设置买入挂单记录key
            String buyKey = "transactionPendOrderBuyList" + transactionCurrency.getCurrencyId();
            //设置买一价key
            String buyOneKey = "buyOne" + transactionCurrency.getCurrencyId();

            List<Object> setBuyList = new ArrayList<>();
            if(transactionPendOrderBuyList.size() > 0){
                for (TransactionPendOrderDTO transactionPendOrder: transactionPendOrderBuyList
                        ) {
                    setBuyList.add(transactionPendOrder);
                }
            }

            if(setBuyList.size() == transactionPendOrderBuyList.size() && setBuyList.size() > 0){
                //删除挂单记录key
                redisService.deleteValue(buyKey);
                //插入最新挂单记录
                redisService.addList(buyKey,setBuyList);
                //删除买一价key
                redisService.deleteValue(buyOneKey);
                //插入最新买一价
                redisService.addValue(buyOneKey,transactionPendOrderBuyList.get(0).getPendingPrice());
            }

            //卖出记录
            List<TransactionPendOrderDTO> transactionPendOrderSellList =
                    transactionPendOrderService.listLatestRecords(2,transactionCurrency.getCurrencyId(),15);

            //设置卖出挂单记录key
            String sellKey = "transactionPendOrderSellList" + transactionCurrency.getCurrencyId();
            //设置卖一价key
            String sellOneKey = "sellOne" + transactionCurrency.getCurrencyId();

            List<Object> setSellList = new ArrayList<>();
            if(transactionPendOrderSellList.size() > 0){
                for (TransactionPendOrderDTO transactionPendOrder: transactionPendOrderSellList
                        ) {
                    setSellList.add(transactionPendOrder);
                }
            }

            if(setSellList.size() == transactionPendOrderSellList.size() && setSellList.size() > 0){
                //清空该key
                redisService.deleteValue(sellKey);
                //插入最新数据
                redisService.addList(sellKey,setSellList);
                //删除卖一价key
                redisService.deleteValue(sellOneKey);
                //插入最新卖一价
                redisService.addValue(sellOneKey,transactionPendOrderSellList.get(0).getPendingPrice());
            }
        }
        /*String key = "123456789";
        String valueStr = "qazwsx123";

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("111111", "cscs");
        jsonObject.put("222222", "测试123");

        JsonObjectBO jsonObjectBO = new JsonObjectBO();
        jsonObjectBO.setCode(1);
        jsonObjectBO.setMessage("测试测试");
        jsonObjectBO.setData(jsonObject);

        String keyMap = "keymap";
        Map<Object, Object> map = new HashMap<Object, Object>();
        map.put(keyMap, jsonObjectBO);
        map.put("map测试", 123456789);
        map.put("mapDouble", 12345.0123);

        List<Object> listObject = new ArrayList<>();
        listObject.add(1);
        listObject.add("12测试");

        String listKey = "lis123123";
        List<Object> listmap = new ArrayList<>();
        listmap.add(map);

        String nullKey = "nullkey";
        System.out.println(redisService.addValue(nullKey, null));
        System.out.println(redisService.addList(listKey, listmap));
        System.out.println(redisService.addList(listKey, 123));
        System.out.println(redisService.addMap(keyMap, map, 20));
        System.out.println(redisService.deleteValue("9999999"));
        System.out.println(redisService.getList(listKey));
        System.out.println(redisService.getListSize(listKey));*/

        //存入List
//        redisService.deleteValue("tc");
//
//        List<TransactionCurrencyDO> transactionCurrencyList = transactionCurrencyService.getTransactionCurrencyListForWeb();
//        List<Object> setList = new ArrayList<>();
//        for (TransactionCurrencyDO transactionCurrency: transactionCurrencyList
//             ) {
//            setList.add(transactionCurrency);
//        }
//        redisService.addList("tc",setList);
//
//        //取出List
//        List<Object> resultList = redisService.getList("tc");
//
//        for (Object object: resultList) {
//            TransactionCurrencyDO transactionCurrency = (TransactionCurrencyDO)object;
//            List<TransactionCurrencyDO> tcList = new ArrayList<>();
//            tcList.add(transactionCurrency);
//            System.out.println(transactionCurrency.getCurrencyId());
//            System.out.println(transactionCurrency.getCurrencyName());
//            System.out.println(transactionCurrency.getCurrencyShortName());
//            System.out.println(transactionCurrency.getAddTime());
//        }
    }

}
