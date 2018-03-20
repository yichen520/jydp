package com.jydp.dao.impl.transaction;

import com.iqmkj.utils.DateUtil;
import com.iqmkj.utils.LogUtil;
import com.jydp.dao.ITransactionStatisticsDao;
import com.jydp.entity.DO.transaction.TransactionStatisticsDO;
import com.jydp.entity.DTO.TransactionBottomPriceDTO;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
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
        String todayData = DateUtil.longToTimeStr(lingchenLong, DateUtil.dateFormat2);
        map.put("todayData", todayData);

        TransactionBottomPriceDTO result = null;
        try {
            result = sqlSessionTemplate.selectOne("TransactionStatistics_getBottomPricePast", map);
        } catch (Exception e) {
            LogUtil.printErrorLog(e);
        }
        return result;
    }

    /**
     * 查询交易统计记录条数(后台)
     *
     * @param currencyId 货币Id，查询全部为0
     * @param startTime  统计开始时间，可为null
     * @param endTime    统计接受时间，可为null
     * @return 操作成功：返回交易统计记录条数，操作失败：返回0
     */
    public int countTransactionStatisticsForBack(int currencyId, Timestamp startTime, Timestamp endTime) {
        Map<String, Object> map = new HashMap<>();
        map.put("currencyId", currencyId);
        map.put("startTime", startTime);
        map.put("endTime", endTime);

        int result = 0;
        try {
            result = sqlSessionTemplate.selectOne("TransactionStatistics_countTransactionStatisticsForBack", map);
        } catch (Exception e) {
            LogUtil.printErrorLog(e);
        }

        return result;
    }

    /**
     * 查询交易统计记录列表(后台)
     *
     * @param currencyId 货币Id，查询全部为0
     * @param startTime  统计开始时间，可为null
     * @param endTime    统计接受时间，可为null
     * @param pageNumber 当前页数
     * @param pageSize   每页条数
     * @return 操作成功：返回交易统计记录，操作失败：返回null
     */
    public List<TransactionStatisticsDO> listTransactionStatisticsForBack(int currencyId, Timestamp startTime,
                                                                          Timestamp endTime, int pageNumber, int pageSize) {
        List<TransactionStatisticsDO> resultList = null;

        Map<String, Object> map = new HashMap<>();
        map.put("currencyId", currencyId);
        map.put("startTime", startTime);
        map.put("endTime", endTime);
        map.put("startNumber", pageNumber * pageSize);
        map.put("pageSize", pageSize);
        try {
            resultList = sqlSessionTemplate.selectList("TransactionStatistics_listTransactionStatisticsForBack", map);
        } catch (Exception e) {
            LogUtil.printErrorLog(e);
        }

        return resultList;
    }
}
