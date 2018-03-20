package com.jydp.service.impl.transaction;

import com.iqmkj.utils.BigDecimalUtil;
import com.iqmkj.utils.DateUtil;
import com.jydp.dao.ITransactionDealRedisDao;
import com.jydp.entity.BO.JsonObjectBO;
import com.jydp.entity.DO.transaction.TransactionDealRedisDO;
import com.jydp.entity.DTO.TransactionBottomPriceDTO;
import com.jydp.entity.DTO.TransactionDealPriceDTO;
import com.jydp.entity.VO.TransactionGraphVO;
import com.jydp.entity.DTO.TransactionDealRedisDTO;
import com.jydp.service.IRedisService;
import com.jydp.service.ITransactionDealRedisService;
import config.RedisKeyConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * redis成交记录
 * @author fk
 *
 */
@Service("transactionDealRedisService")
public class TransactionDealRedisServiceImpl implements ITransactionDealRedisService {

    /** redis成交记录 */
    @Autowired
    private ITransactionDealRedisDao transactionDealRedisDao;

    /** redis服务 */
    @Autowired
    private IRedisService redisService;

    /**
     * 查询前num条成交记录
     * @param num 查询条数
     * @param currencyId 币种Id
     * @return 查询成功：返回用户成交记录；查询失败：返回null
     */
    public List<TransactionDealRedisDO> listTransactionDealRedis(int num, int currencyId){
        return transactionDealRedisDao.listTransactionDealRedis(num, currencyId);
    }

    /**
     * 新增成交记录
     * @param orderNo  记录号
     * @param paymentType  收支类型
     * @param currencyId  币种Id
     * @param transactionPrice  成交单价
     * @param currencyNumber  成交数量
     * @param currencyTotalPrice  成交总价
     * @param addTime  添加时间
     * @return  操作成功：返回true，操作失败：返回false
     */
    public boolean insertTransactionDealRedis(String orderNo, int paymentType, int currencyId,
                                       double transactionPrice, double currencyNumber, double currencyTotalPrice,
                                       Timestamp addTime){
        TransactionDealRedisDO dealRedisDO = new TransactionDealRedisDO();
        dealRedisDO.setOrderNo(orderNo);
        dealRedisDO.setPaymentType(paymentType);
        dealRedisDO.setCurrencyId(currencyId);
        dealRedisDO.setTransactionPrice(transactionPrice);
        dealRedisDO.setCurrencyNumber(currencyNumber);
        dealRedisDO.setCurrencyTotalPrice(currencyTotalPrice);
        dealRedisDO.setAddTime(addTime);
        return transactionDealRedisDao.insertTransactionDealRedis(dealRedisDO);
    }

    /**
     * 批量新增成交记录
     * @param redisDealList  成交记录集合
     * @return  操作成功：返回true，操作失败：返回false
     */
    public boolean insertTransactionDealRedisList(List<TransactionDealRedisDO> redisDealList){
        return transactionDealRedisDao.insertTransactionDealRedisList(redisDealList);
    }

    /**
     * 查询今日总成交数量
     * @return 查询成功：返回总成交数量，查询失败或没有成交量：返回0
     */
    public List<TransactionDealPriceDTO> getNowTurnover(){
        long dateLon = DateUtil.lingchenLong();
        Timestamp date;

        //判断当前时间是否是凌晨至开盘之前
        long nowDate = DateUtil.getCurrentTimeMillis() - RedisKeyConfig.OPENING_TIME;
        if(nowDate >= dateLon){
            dateLon = dateLon + RedisKeyConfig.OPENING_TIME;
            date = DateUtil.longToTimestamp(dateLon);
        } else {
            dateLon = dateLon - RedisKeyConfig.DAY_TIME + RedisKeyConfig.OPENING_TIME;
            date = DateUtil.longToTimestamp(dateLon);

        }
        return transactionDealRedisDao.getNowTurnover(date);
    }

    /**
     * 查询今日总交易额
     * @return 查询成功：返回总成交金额，查询失败或没有成交额：返回0
     */
    public List<TransactionDealPriceDTO> getNowVolumeOfTransaction(){
        long dateLon = DateUtil.lingchenLong();
        Timestamp date;

        //判断当前时间是否是凌晨至开盘之前
        long nowDate = DateUtil.getCurrentTimeMillis() - RedisKeyConfig.OPENING_TIME;
        if(nowDate >= dateLon){
            dateLon = dateLon + RedisKeyConfig.OPENING_TIME;
            date = DateUtil.longToTimestamp(dateLon);
        } else {
            dateLon = dateLon - RedisKeyConfig.DAY_TIME + RedisKeyConfig.OPENING_TIME;
            date = DateUtil.longToTimestamp(dateLon);

        }
        return transactionDealRedisDao.getNowVolumeOfTransaction(date);
    }

    /**
     * 查询今日最高价
     * @return 查询成功：返回今日最高价，查询失败或今日最高价为0：返回0
     */
    public List<TransactionDealPriceDTO> getTodayHighestPrice(){
        long dateLon = DateUtil.lingchenLong();
        Timestamp date;

        //判断当前时间是否是凌晨至开盘之前
        long nowDate = DateUtil.getCurrentTimeMillis() - RedisKeyConfig.OPENING_TIME;
        if(nowDate >= dateLon){
            dateLon = dateLon + RedisKeyConfig.OPENING_TIME;
            date = DateUtil.longToTimestamp(dateLon);
        } else {
            dateLon = dateLon - RedisKeyConfig.DAY_TIME + RedisKeyConfig.OPENING_TIME;
            date = DateUtil.longToTimestamp(dateLon);

        }
        return transactionDealRedisDao.getTodayHighestPrice(date);
    }

    /**
     * 查询今日最低价
     * @return 查询成功：返回今日最低价，查询失败或今日最低价为0：返回0
     */
    public List<TransactionDealPriceDTO> getTodayLowestPrice(){
        long dateLon = DateUtil.lingchenLong();
        Timestamp date;

        //判断当前时间是否是凌晨至开盘之前
        long nowDate = DateUtil.getCurrentTimeMillis() - RedisKeyConfig.OPENING_TIME;
        if(nowDate >= dateLon){
            dateLon = dateLon + RedisKeyConfig.OPENING_TIME;
            date = DateUtil.longToTimestamp(dateLon);
        } else {
            dateLon = dateLon - RedisKeyConfig.DAY_TIME + RedisKeyConfig.OPENING_TIME;
            date = DateUtil.longToTimestamp(dateLon);

        }
        return transactionDealRedisDao.getTodayLowestPrice(date);
    }

    /**
     * 查询当前时间上一个成交价格
     * @param getDate 需要查询的时间节点
     * @return 查询成功：返回上一个价格，查询失败或上一个价格为0：返回0
     */
    public List<TransactionDealPriceDTO> getNowLastPrice(Timestamp getDate){
/*        long dateLon = DateUtil.lingchenLong();
        Timestamp date;

        //判断当前时间是否是凌晨至开盘之前
        long nowDate = getDate.getTime() - RedisKeyConfig.OPENING_TIME;
        if(nowDate >= dateLon){
            dateLon = dateLon + RedisKeyConfig.OPENING_TIME;
            date = DateUtil.longToTimestamp(dateLon);
        } else {
            dateLon = dateLon - RedisKeyConfig.DAY_TIME + RedisKeyConfig.OPENING_TIME;
            date = DateUtil.longToTimestamp(dateLon);
        } */
        return transactionDealRedisDao.getNowLastPrice(getDate, null);
    }

    /**
     * 验证币种是否有历史交易
     * @param currencyId  币种Id
     * @return  操作成功：返回true，操作失败：返回false
     */
    public boolean validateGuidancePrice(int currencyId){
        List<TransactionDealRedisDO> redisDO = transactionDealRedisDao.listTransactionDealRedis(1, currencyId);
        if (redisDO != null && !redisDO.isEmpty()){
            return true;
        } else {
            return false;
        }
    }

    /**
     * 根据订单号查询记录
     * @param orderNo  订单号
     * @return  操作成功：返回记录集合，操作失败：返回null
     */
    public List<TransactionDealRedisDO> listTransactionDealRedisByOrderNo(String orderNo){
        return transactionDealRedisDao.listTransactionDealRedisByOrderNo(orderNo);
    }

    /**
     * 根据订单号删除记录
     * @param orderNo 订单号
     * @return 操作成功：返回true，操作失败：返回false
     */
    public boolean deleteDealByOrderNo(String orderNo){
        return transactionDealRedisDao.deleteDealByOrderNo(orderNo);
    }

    /**
     * 获取盛源交易所 当日成交总价，当日成交总数量
     * @param currencyId 币种Id
     * @param orderNoPrefix 批次号前缀
     * @return 操作成功：返回数据集合，操作失败:返回null
     */
    public TransactionBottomPriceDTO getBottomPriceToday(int currencyId, String orderNoPrefix){
        return transactionDealRedisDao.getBottomPriceToday(currencyId, orderNoPrefix);
    }

    /**
     * 获取盛源交易所 当前价
     * @param currencyId 币种Id
     * @param orderNoPrefix 批次号前缀
     * @return 查询成功：返回当前价格，查询失败或当前价格为0：返回0
     */
    public double getCurrentPrice(int currencyId, String orderNoPrefix){
        return transactionDealRedisDao.getCurrentPrice(currencyId, orderNoPrefix);
    }

    /**
     * k线图数据拉取
     * @param currencyId 币种Id
     * @return 操作成功：返回数据集合，操作失败:返回null
     */
    public List<TransactionDealRedisDTO> listTransactionUserDealForKline(int currencyId){
        return transactionDealRedisDao.listTransactionUserDealForKline(currencyId);
    }

    /**
     * 查询未来num条redis成交记录
     * @param paymentType  交易类型
     * @param currencyId  币种Id
     * @param date  查询时间
     * @param num  查询条数
     * @return  操作成功：返回数据集合，操作失败:返回null
     */
    public List<TransactionDealRedisDO> listTransactionDealForPending(int paymentType, int currencyId, Timestamp date, int num){
        return transactionDealRedisDao.listTransactionDealForPending(paymentType, currencyId, date, num);
    }

    /**
     * k线图数据拼接
     * @param currencyId  币种Id
     * @param minute  分钟数
     * @param transactionDealRedisList  成交记录
     * @return 操作成功:返回k线图数据(前一百条), 操作失败:返回null
     */
    public List<TransactionGraphVO> jointGraphData(int currencyId, int minute, List<TransactionDealRedisDTO> transactionDealRedisList){
        JsonObjectBO responseJson = new JsonObjectBO();
        List<TransactionGraphVO> transactionGraphList = new ArrayList<>();
        long nodeDate = 1L * minute * 60 * 1000;  //节点时间
        int length = 0;  //长度值
        int subscript = 0;  //下标值
        long initialDate = 0;  //初始时间
        double openPrice = 0;  //开盘价
        double closPrice = 0;  //收盘价
        double maxPrice = 0;  //最高价
        double minPrice = 0;  //最低价
        double countPrice = 0;  //成交量

        //获取list长度
        if(transactionDealRedisList == null || transactionDealRedisList.size() <= 0){
            return transactionGraphList;
        } else {
            length = transactionDealRedisList.size();
            initialDate = transactionDealRedisList.get(0).getAddTime().getTime();
        }

        //获得截止时间点
        long nowDate = 1L * DateUtil.getCurrentTime().getTime();
        //判定当前拼接时间节点是否大于截止时间节点
        while (initialDate < nowDate){
            TransactionGraphVO transactionGraphVO = new TransactionGraphVO();

            //判断下标是否越界
            if(length > subscript){
                //时间节点判断
                TransactionDealRedisDTO transactionDealRedis =  transactionDealRedisList.get(subscript);
                if (initialDate <= transactionDealRedis.getAddTime().getTime() &&
                        transactionDealRedis.getAddTime().getTime() < (initialDate + nodeDate)){
                    //开盘价放入
                    if(openPrice == 0){
                        openPrice =  transactionDealRedis.getTransactionPrice();
                    }

                    //收盘价放入
                    closPrice = transactionDealRedis.getTransactionPrice();

                    //最高价放入
                    if(maxPrice < transactionDealRedis.getTransactionPrice()){
                        maxPrice = transactionDealRedis.getTransactionPrice();
                    }

                    //最低价放入
                    if(minPrice > transactionDealRedis.getTransactionPrice() || minPrice == 0){
                        minPrice = transactionDealRedis.getTransactionPrice();
                    }

                    //成交量计算
                    countPrice = BigDecimalUtil.add(countPrice,transactionDealRedis.getCurrencyNumber());
                    subscript++;
                } else {

                    if(openPrice != 0){
                        transactionGraphVO.setOpenPrice(openPrice);
                        transactionGraphVO.setClosPrice(closPrice);
                        transactionGraphVO.setMaxPrice(maxPrice);
                        transactionGraphVO.setMinPrice(minPrice);
                        transactionGraphVO.setCountPrice(countPrice);
                        transactionGraphVO.setDealDate(DateUtil.longToTimestamp(initialDate));
                        transactionGraphList.add(transactionGraphVO);

                        //节点参数格式化
                        openPrice = 0;  //开盘价
                        closPrice = 0;  //收盘价
                        maxPrice = 0;  //最高价
                        minPrice = 0;  //最低价
                        countPrice = 0;  //成交量
                    }

                    initialDate += nodeDate;
                }
            } else {

                if(openPrice != 0) {
                    transactionGraphVO.setOpenPrice(openPrice);
                    transactionGraphVO.setClosPrice(closPrice);
                    transactionGraphVO.setMaxPrice(maxPrice);
                    transactionGraphVO.setMinPrice(minPrice);
                    transactionGraphVO.setCountPrice(countPrice);
                    transactionGraphVO.setDealDate(DateUtil.longToTimestamp(initialDate));
                    transactionGraphList.add(transactionGraphVO);

                    //节点参数格式化
                    openPrice = 0;  //开盘价
                    closPrice = 0;  //收盘价
                    maxPrice = 0;  //最高价
                    minPrice = 0;  //最低价
                    countPrice = 0;  //成交量
                }
                initialDate += nodeDate;
            }
        }

        List<TransactionGraphVO> transactionGraph =  new ArrayList<>();
        if(transactionGraphList != null && transactionGraphList.size() > 0){
            if(transactionGraphList.size() > 100){
                transactionGraph.addAll(transactionGraphList.subList(transactionGraphList.size() - 100, transactionGraphList.size()));
            } else {
                transactionGraph = transactionGraphList;
            }
        }
        return transactionGraph;
    }

    /**
     * 从redis获取k线图数据
     * @param currencyId  币种Id
     * @param node  时间节点 ：5分钟 5m、15分钟 15m、30分钟 30m、1小时 1h、4小时 4h、1天 1d 、1周 1w
     * @return 操作成功:返回k线图数据, 操作失败:返回null
     */
    public List<TransactionGraphVO> gainGraphData(int currencyId,String node){
        List<TransactionGraphVO> transactionGraphList = new ArrayList<TransactionGraphVO>();
        //当前价
        Object graphData = redisService.getValue(RedisKeyConfig.GRAPH_DATA + currencyId + node);
        if(graphData != null){
            transactionGraphList = (List<TransactionGraphVO>) graphData;
        }
        return transactionGraphList;
    }

}
