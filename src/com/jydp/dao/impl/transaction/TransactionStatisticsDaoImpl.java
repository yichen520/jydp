package com.jydp.dao.impl.transaction;

import com.iqmkj.utils.DateUtil;
import com.iqmkj.utils.LogUtil;
import com.jydp.dao.ITransactionStatisticsDao;
import com.jydp.entity.DTO.TransactionBottomPriceDTO;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

/**
 * Description:交易统计记录表
 * Author: hht
 * Date: 2018-03-16 9:23
 */
@Repository
public class TransactionStatisticsDaoImpl implements ITransactionStatisticsDao {

    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;


    /**
     * 获取盛源交易所 历史当日成交总价*历史当日系数，历史当日成交总数量
     * @param currencyId 币种Id
     * @return 操作成功：返回数据集合，操作失败:返回null
     */
    public TransactionBottomPriceDTO getBottomPricePast(int currencyId){
        Map<String,Object> map = new HashMap<>();
        map.put("currencyId", currencyId);
        long lingchenLong = DateUtil.lingchenLong();
        String todayData = DateUtil.longToTimeStr(lingchenLong, DateUtil.dateFormat10);
        map.put("todayData", todayData);

        TransactionBottomPriceDTO result = null;
        try {
            result = sqlSessionTemplate.selectOne("TransactionStatistics_getBottomPricePast", map);
        } catch (Exception e) {
            LogUtil.printErrorLog(e);
        }
        return result;
    }
}
