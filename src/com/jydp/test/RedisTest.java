package com.jydp.test;

import com.jydp.service.IRedisService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

/**
 * 测试redids
 * @author whx
 */
public class RedisTest extends BaseJunit4Test {

    @Autowired
    private IRedisService redisService;

    @Test
    public void testRedis() {
//        String key = "123456789";
//        String valueStr = "qazwsx123";
//
//        JSONObject jsonObject = new JSONObject();
//        jsonObject.put("111111", "cscs");
//        jsonObject.put("222222", "测试123");
//
//        JsonObjectBO jsonObjectBO = new JsonObjectBO();
//        jsonObjectBO.setCode(1);
//        jsonObjectBO.setMessage("测试测试");
//        jsonObjectBO.setData(jsonObject);
//
//        String keyMap = "keymap";
//        Map<Object, Object> map = new HashMap<Object, Object>();
//        map.put(keyMap, jsonObjectBO);
//        map.put("map测试", 123456789);
//        map.put("mapDouble", 12345.0123);
//
//        List<Object> listObject = new ArrayList<>();
//        listObject.add(1);
//        listObject.add("12测试");
//
//        String listKey = "lis123123";
//        List<Object> listmap = new ArrayList<>();
//        listmap.add(map);
//
//        String nullKey = "nullkey";
//        System.out.println(redisService.addValue(nullKey, null));
//        System.out.println(redisService.addList(listKey, listmap));
//        System.out.println(redisService.addList(listKey, 123));
//        System.out.println(redisService.addMap(keyMap, map, 20));
//        System.out.println(redisService.deleteValue("9999999"));
//        System.out.println(redisService.getList(listKey));
//        System.out.println(redisService.getListSize(listKey));

        double buy = 0.26 * 0.1237 * 0.001;
        BigDecimal bigDecimal = new BigDecimal(buy + "");
        System.out.println(Double.toString(buy));
        System.out.println(Double.toString(Double.valueOf(bigDecimal.setScale(8, BigDecimal.ROUND_HALF_DOWN).toString())));
        System.out.println(Double.toString(Double.valueOf(bigDecimal.setScale(8, BigDecimal.ROUND_DOWN).toString())));
        System.out.println(Double.toString(Double.valueOf(bigDecimal.setScale(8, BigDecimal.ROUND_UP).toString())));
    }

}
