package com.jydp.service.impl.transaction;

import com.iqmkj.utils.DateUtil;
import com.iqmkj.utils.LogUtil;
import com.iqmkj.utils.NumberUtil;
import com.jydp.dao.ITransactionCurrencyDao;
import com.jydp.entity.DO.transaction.TransactionCurrencyDO;
import com.jydp.entity.DTO.TransactionUserDealDTO;
import com.jydp.entity.VO.StandardParameterVO;
import com.jydp.entity.VO.TransactionCurrencyVO;
import com.jydp.service.IRedisService;
import com.jydp.service.ITransactionCurrencyService;
import config.RedisKeyConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 交易币种
 * @author fk
 *
 */
@Service("transactionCurrencyService")
public class TransactionCurrencyServiceImpl implements ITransactionCurrencyService{

    /** 交易币种 */
    @Autowired
    private ITransactionCurrencyDao transactionCurrencyDao;

    /** redis服务 */
    @Autowired
    private IRedisService redisService;

    /** 日期格式 **/
    private SimpleDateFormat sdf = new SimpleDateFormat(DateUtil.dateFormat2);
    private SimpleDateFormat sdf2 = new SimpleDateFormat(DateUtil.dateFormat4);

    /**
     * 新增交易币种
     * @param currencyShortName 货币简称
     * @param currencyName 货币名称
     * @param currencyImg  币种徽标
     * @param buyFee  买入手续费
     * @param sellFee  卖出手续费
     * @param upRange  涨停幅度
     * @param downRange  跌停幅度
     * @param paymentType  交易状态,1:正常，2:停牌
     * @param upStatus  上线状态,1:待上线,2:上线中,3:停牌,4:已下线
     * @param backerAccount  管理员账号
     * @param ipAddresse  操作时的ip地址
     * @param upTime  上线时间
     * @param addTime 添加时间
     * @return  操作成功：返回true，操作失败：返回false
     */
    public boolean insertTransactionCurrency(String currencyShortName, String currencyName, String currencyImg,
                                             double buyFee, double sellFee, double upRange, double downRange,
                                             int paymentType, int upStatus, String backerAccount, String ipAddresse,
                                             Timestamp upTime,Timestamp addTime){
        TransactionCurrencyDO transactionCurrency = new TransactionCurrencyDO();
        transactionCurrency.setCurrencyName(currencyName);
        transactionCurrency.setCurrencyShortName(currencyShortName);
        transactionCurrency.setCurrencyImg(currencyImg);
        transactionCurrency.setBuyFee(buyFee);
        transactionCurrency.setSellFee(sellFee);
        transactionCurrency.setUpRange(upRange);
        transactionCurrency.setDownRange(downRange);
        transactionCurrency.setPaymentType(paymentType);
        transactionCurrency.setUpStatus(upStatus);
        transactionCurrency.setBackerAccount(backerAccount);
        transactionCurrency.setIpAddress(ipAddresse);
        transactionCurrency.setUpTime(upTime);
        transactionCurrency.setAddTime(addTime);
        return transactionCurrencyDao.insertTransactionCurrency(transactionCurrency);
    }

    /**
     * 新增交易币种(后台)
     * @param currencyShortName 货币简称
     * @param currencyName 货币名称
     * @param currencyImg  币种徽标
     * @param buyFee  买入手续费
     * @param sellFee  卖出手续费
     * @param upRange  涨停幅度
     * @param downRange  跌停幅度
     * @param paymentType  交易状态,1:正常，2:停牌
     * @param upStatus  上线状态,1:待上线,2:上线中,3:停牌,4:已下线
     * @param backerAccount  管理员账号
     * @param ipAddresse  操作时的ip地址
     * @param upTime  上线时间
     * @param addTime 添加时间
     * @return  操作成功：返回true，操作失败：返回false
     */
    @Transactional
    public boolean addTransactionCurrency(String currencyShortName, String currencyName, String currencyImg,
                                   double buyFee, double sellFee, double upRange, double downRange,
                                   int paymentType, int upStatus, String backerAccount, String ipAddresse,
                                          Timestamp upTime,Timestamp addTime){
        TransactionCurrencyDO transactionCurrency = new TransactionCurrencyDO();
        transactionCurrency.setCurrencyName(currencyName);
        transactionCurrency.setCurrencyShortName(currencyShortName);
        transactionCurrency.setCurrencyImg(currencyImg);
        transactionCurrency.setBuyFee(buyFee);
        transactionCurrency.setSellFee(sellFee);
        transactionCurrency.setUpRange(upRange);
        transactionCurrency.setDownRange(downRange);
        transactionCurrency.setPaymentType(paymentType);
        transactionCurrency.setUpStatus(upStatus);
        transactionCurrency.setBackerAccount(backerAccount);
        transactionCurrency.setIpAddress(ipAddresse);
        transactionCurrency.setAddTime(addTime);

        boolean addBoo = false;

        if (upTime == null) {
            transactionCurrency.setUpTime(DateUtil.getCurrentTime());
            transactionCurrency.setPaymentType(1);
            transactionCurrency.setUpStatus(2);
            addBoo = transactionCurrencyDao.insertTransactionCurrency(transactionCurrency);
        } else {
            transactionCurrency.setUpTime(upTime);
            addBoo = transactionCurrencyDao.insertTransactionCurrency(transactionCurrency);
        }

        return addBoo;
    }

    /**
     * 根据币种Id获取交易币种
     * @param currencyId  币种Id
     * @return  操作成功：返回交易币种，操作失败：返回null
     */
    public TransactionCurrencyVO getTransactionCurrencyByCurrencyId(int currencyId){
        return transactionCurrencyDao.getTransactionCurrencyByCurrencyId(currencyId);
    }

    /**
     * 修改交易币种信息
     * @param transactionCurrency  交易币种
     * @return  操作成功：返回true，操作失败：返回false
     */
    public boolean updateTransactionCurrency(TransactionCurrencyDO transactionCurrency){
        return transactionCurrencyDao.updateTransactionCurrency(transactionCurrency);
    }

    /**
     * 根据币种Id删除交易币种
     * @param currencyId  币种Id
     * @return  操作成功：返回true，操作失败：返回false
     */
    public boolean deleteTransactionCurrencyByCurrencyId(int currencyId){
        return transactionCurrencyDao.deleteTransactionCurrencyByCurrencyId(currencyId);
    }

    /**
     * 获取所有币种信息(web端用户注册时使用)
     * @return 查询成功：返回币种信息列表；查询失败：返回null
     */
    @Override
    public List<TransactionCurrencyVO> getTransactionCurrencyListForWeb() {
        return transactionCurrencyDao.getTransactionCurrencyListForWeb();
    }

    /**
     * 根据币种名称获取交易币种
     * @param currencyName  币种名称
     * @return  操作成功：返回交易币种，操作失败：返回null
     */
    public TransactionCurrencyVO getTransactionCurrencyByCurrencyName(String currencyName){
        return transactionCurrencyDao.getTransactionCurrencyByCurrencyName(currencyName);
    }

    /**
     * 获取所有币种行情信息(web端)
     * @return 查询成功：返回所有币种行情信息；查询失败：返回null
     */
    @Override
    public List<TransactionUserDealDTO> getTransactionCurrencyMarketForWeb() {

        //当日开盘时间
        Timestamp openTime = null;
        try {
            Date date = sdf.parse(sdf2.format(new Date()) +" 08:00:00");
            openTime = new Timestamp(date.getTime());
        } catch (ParseException e) {
            LogUtil.printErrorLog(e);
        }

        Date todayOpenDate = openTime;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(todayOpenDate);
        //减一天求得昨日日期
        calendar.add(Calendar.DAY_OF_YEAR,-1);
        Date yesterdayOpenDate = calendar.getTime();
        String time = sdf.format(yesterdayOpenDate);
        //昨日开盘时间
        Timestamp startTime = null;
        try {
            startTime = new Timestamp(sdf.parse(time).getTime());
        } catch (ParseException e) {
            LogUtil.printErrorLog(e);
        }

        long beginTime = System.currentTimeMillis();
        //查询所有币种
        List<TransactionUserDealDTO> transactionUserDealDTOList = transactionCurrencyDao.getTransactionCurrencyMarketForWeb();

        if (transactionUserDealDTOList != null) {
            //查询各币种最新价信息
            Map<Integer,TransactionUserDealDTO> newPriceMap = transactionCurrencyDao.getNewPriceForWeb();
            //查询各币种买一价信息
            Map<Integer,TransactionUserDealDTO> buyOneMap = transactionCurrencyDao.getBuyOneForWeb();
            //查询各币种卖一价信息
            Map<Integer,TransactionUserDealDTO> sellOneMap = transactionCurrencyDao.getSellOneForWeb();
            //查询各币种今日成交量信息
            Map<Integer,TransactionUserDealDTO> volumeMap = transactionCurrencyDao.getTransactionVolumeForWeb(openTime);
            //查询各币种昨日收盘价信息
            Map<Integer,TransactionUserDealDTO> yesterdayPriceMap = transactionCurrencyDao.getYesterdayLastPriceForWeb(openTime,startTime);

            for (TransactionUserDealDTO transactionUserDeal:transactionUserDealDTOList) {
                int currencyId = transactionUserDeal.getCurrencyId();
                double latestPrice = 0.0, buyOnePrice = 0.0, sellOnePrice = 0.0, volume = 0.0, yesterdayLastPrice = 0.0;

                if (redisService.getValue(RedisKeyConfig.NOW_PRICE + currencyId) != null) {
                    latestPrice = Double.parseDouble(redisService.getValue(RedisKeyConfig.NOW_PRICE + currencyId).toString());
                }
                if (redisService.getValue(RedisKeyConfig.YESTERDAY_PRICE+currencyId) != null) {
                    yesterdayLastPrice = Double.parseDouble(redisService.getValue(RedisKeyConfig.YESTERDAY_PRICE+currencyId).toString());
                }

                if (newPriceMap != null && newPriceMap.get(currencyId) != null) {
                    latestPrice = newPriceMap.get(currencyId).getLatestPrice();
                }

                if (buyOneMap != null && buyOneMap.get(currencyId) != null) {
                    buyOnePrice = buyOneMap.get(currencyId).getBuyOnePrice();
                }

                if (sellOneMap != null && sellOneMap.get(currencyId) != null) {
                    sellOnePrice = sellOneMap.get(currencyId).getSellOnePrice();
                }

                if (volumeMap != null && volumeMap.get(currencyId) != null) {
                    volume = volumeMap.get(currencyId).getVolume();
                }

                transactionUserDeal.setLatestPrice(NumberUtil.doubleFormat(latestPrice,2));
                transactionUserDeal.setBuyOnePrice(NumberUtil.doubleFormat(buyOnePrice,2));
                transactionUserDeal.setSellOnePrice(NumberUtil.doubleFormat(sellOnePrice,2));
                transactionUserDeal.setVolume(NumberUtil.doubleFormat(volume,2));
                transactionUserDeal.setYesterdayLastPrice(NumberUtil.doubleFormat(yesterdayLastPrice,2));

                //24小时涨幅 eg:24小时涨跌为24.31%,change = 24.31
                double change = 0;
                if (yesterdayLastPrice != 0) {
                    change = NumberUtil.doubleFormat(((transactionUserDeal.getLatestPrice() - yesterdayLastPrice)/yesterdayLastPrice)*100,2);
                }
                transactionUserDeal.setChange(change);
            }
        }
        long endTime = System.currentTimeMillis();
        return transactionUserDealDTOList;
    }

    /**
     * 查询币种个数（后台）
     * @param currencyName  货币名称(币种)
     * @param paymentType  交易状态,1:正常，2:涨停，3:跌停，4:停牌
     * @param upStatus  上线状态,1:待上线,2:上线中,3:禁用,4:已下线
     * @param backAccount  管理员账号
     * @param startAddTime  开始添加时间
     * @param endAddTime  结束添加时间
     * @param startUpTime  开始上线时间
     * @param endUpTime  结束上线时间
     * @return  操作成功：返回交易币种条数，操作失败：返回0
     */
    public int countTransactionCurrencyForBack(String currencyName, int paymentType, int upStatus, String backAccount,
                                               Timestamp startAddTime, Timestamp endAddTime, Timestamp startUpTime, Timestamp endUpTime){
        return transactionCurrencyDao.countTransactionCurrencyForBack(currencyName, paymentType, upStatus,
                backAccount, startAddTime, endAddTime, startUpTime, endUpTime);
    }

    /**
     * 查询币种集合（后台）
     * @param currencyName  货币名称(币种)
     * @param paymentType  交易状态,1:正常，2:涨停，3:跌停，4:停牌
     * @param upStatus  上线状态,1:待上线,2:上线中,3:禁用,4:已下线
     * @param backAccount  管理员账号
     * @param startAddTime  开始添加时间
     * @param endAddTime  结束添加时间
     * @param startUpTime  开始上线时间
     * @param endUpTime  结束上线时间
     * @param pageNumber 起始页数
     * @param pageSize 每页条数
     * @return  操作成功：返回交易币种条数，操作失败：返回0
     */
    public List<TransactionCurrencyVO> listTransactionCurrencyForBack(String currencyName, int paymentType, int upStatus, String backAccount,
                                                                      Timestamp startAddTime, Timestamp endAddTime, Timestamp startUpTime, Timestamp endUpTime, int pageNumber, int pageSize){
        return transactionCurrencyDao.listTransactionCurrencyForBack(currencyName, paymentType, upStatus, backAccount,
                startAddTime, endAddTime, startUpTime, endUpTime, pageNumber, pageSize);
    }

    /**
     * 停，复牌操作
     * @param currencyId  币种Id
     * @param paymentType  交易状态,1:正常，2:停牌
     * @param backerAccount  后台管理员账号
     * @param ipAddress  操作时的ip地址
     * @return  操作成功：返回true，操作失败：返回false
     */
    @Transactional
    public boolean updatePaymentType(int currencyId, int paymentType, String backerAccount, String ipAddress){
        //修改交易状态
        boolean excBoo = transactionCurrencyDao.updatePaymentType(currencyId, paymentType, backerAccount, ipAddress);

        //修改上线状态
        if (excBoo && paymentType == 2) {
            excBoo = transactionCurrencyDao.updateUpStatus(currencyId, 3, backerAccount, ipAddress, null);
        }
        if (excBoo && paymentType == 1) {
            excBoo = transactionCurrencyDao.updateUpStatus(currencyId, 2, backerAccount, ipAddress, DateUtil.getCurrentTime());
        }

        //数据回滚
        if(!excBoo){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }

        return excBoo;
    }

    /**
     * 上，下线币种操作
     * @param currencyId  币种Id
     * @param upStatus  上线状态,,2:上线中,4:已下线
     * @param backerAccount  后台管理员账号
     * @param ipAddress  操作时的ip地址
     * @param upTime  上线时间   下线填空
     * @return  操作成功：返回true，操作失败：返回false
     */
    @Transactional
    public boolean updateUpStatus(int currencyId, int upStatus,  String backerAccount, String ipAddress, Timestamp upTime){
        //修改上线状态
        boolean upBoo = transactionCurrencyDao.updateUpStatus(currencyId, upStatus, backerAccount, ipAddress, upTime);

        //修改交易状态
        if (upStatus == 2) {
            upBoo = transactionCurrencyDao.updatePaymentType(currencyId, 1, backerAccount, ipAddress);
        }
        if (upStatus == 4) {
            upBoo = transactionCurrencyDao.updatePaymentType(currencyId, 2, backerAccount, ipAddress);
        }

        //数据回滚
        if(!upBoo){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return upBoo;
    }

    /**
     * 查询全部币种信息
     * @return  操作成功：返回币种信息集合，操作失败：返回null
     */
    public List<TransactionCurrencyDO> listTransactionCurrencyAll(){
        return transactionCurrencyDao.listTransactionCurrencyAll();
    }

    /**
     * 根据币种id获取redis中相关币种基准参数
     * @param currencyId  币种Id
     * @return  操作成功：返回基准信息，操作失败：返回null
     */
    public StandardParameterVO listTransactionCurrencyAll(int currencyId){
        StandardParameterVO standardParameter = new StandardParameterVO();
        //当前价
        if(redisService.getValue(RedisKeyConfig.NOW_PRICE + currencyId) != null){
            double nowPrice = Double.parseDouble(redisService.getValue(RedisKeyConfig.NOW_PRICE + currencyId).toString());
            standardParameter.setNowPrice(nowPrice);

        }
        //买一价
        if(redisService.getValue(RedisKeyConfig.BUY_ONE_KEY + currencyId) != null){
            double buyOne = Double.parseDouble(redisService.getValue(RedisKeyConfig.BUY_ONE_KEY + currencyId).toString());
            standardParameter.setBuyOne(buyOne);

        }
        //卖一价
        if(redisService.getValue(RedisKeyConfig.SELL_ONE_KEY + currencyId) != null){
            double sellOne = Double.parseDouble(redisService.getValue(RedisKeyConfig.SELL_ONE_KEY + currencyId).toString());
            standardParameter.setSellOne(sellOne);

        }
        //今日最高价
        if(redisService.getValue(RedisKeyConfig.TODAY_MAX_PRICE + currencyId) != null){
            double todayMax = Double.parseDouble(redisService.getValue(RedisKeyConfig.TODAY_MAX_PRICE + currencyId).toString());
            standardParameter.setTodayMax(todayMax);

        }
        //今日最低价
        if(redisService.getValue(RedisKeyConfig.TODAY_MIN_PRICE + currencyId) != null){
            double todayMin = Double.parseDouble(redisService.getValue(RedisKeyConfig.TODAY_MIN_PRICE + currencyId).toString());
            standardParameter.setTodayMin(todayMin);
        }
        //今日涨幅
        if(redisService.getValue(RedisKeyConfig.TODAY_RANGE + currencyId) != null){
            double todayRange = Double.parseDouble(redisService.getValue(RedisKeyConfig.TODAY_RANGE + currencyId).toString());
            standardParameter.setTodayRange(todayRange);

        }
        //昨日收盘价
        if(redisService.getValue(RedisKeyConfig.YESTERDAY_PRICE + currencyId) != null){
            double yesterdayPrice = Double.parseDouble(redisService.getValue(RedisKeyConfig.YESTERDAY_PRICE + currencyId).toString());
            standardParameter.setYesterdayPrice(yesterdayPrice);
        }
        //24小时成交量
        if(redisService.getValue(RedisKeyConfig.DAY_TURNOVER + currencyId) != null){
            double dayTurnove = Double.parseDouble(redisService.getValue(RedisKeyConfig.DAY_TURNOVER + currencyId).toString());
            standardParameter.setDayTurnove(dayTurnove);
        }

        return standardParameter;
    }

    /**
     * 查询全部币种信息(web端用户注册时使用)
     * @return 操作成功：返回币种信息集合，操作失败：返回null
     */
    @Override
    public List<TransactionCurrencyVO> getAllCurrencylistForWeb() {
        return transactionCurrencyDao.getAllCurrencylistForWeb();
    }

    /**
     * 获取所有上线和停牌币种信息
     * @return 操作成功：返回币种信息集合，操作失败：返回null
     */
    @Override
    public List<TransactionCurrencyVO> getOnlineAndSuspensionCurrencyForWeb() {
        return transactionCurrencyDao.getOnlineAndSuspensionCurrencyForWeb();
    }
}
