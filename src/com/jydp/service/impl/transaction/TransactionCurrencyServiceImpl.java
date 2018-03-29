package com.jydp.service.impl.transaction;

import com.iqmkj.utils.DateUtil;
import com.iqmkj.utils.NumberUtil;
import com.jydp.dao.ITransactionCurrencyDao;
import com.jydp.entity.DO.transaction.TransactionCurrencyDO;
import com.jydp.entity.DTO.KGraphCurrencyDTO;
import com.jydp.entity.DTO.TransactionCurrencyBasicDTO;
import com.jydp.entity.DTO.TransactionDealPriceDTO;
import com.jydp.entity.DTO.TransactionUserDealDTO;
import com.jydp.entity.VO.StandardParameterVO;
import com.jydp.entity.VO.TransactionCurrencyVO;
import com.jydp.service.IRedisService;
import com.jydp.service.ITransactionCurrencyService;
import com.jydp.service.ITransactionDealRedisService;
import config.RedisKeyConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    /** redis成交记录 */
    @Autowired
    private ITransactionDealRedisService transactionDealRedisService;
    /**
     * 新增交易币种
     * @param currencyShortName 货币简称
     * @param currencyName 货币名称
     * @param currencyImg  币种徽标
     * @param buyFee  买入手续费
     * @param sellFee  卖出手续费
     * @param paymentType  交易状态,1:正常，2:停牌
     * @param upStatus  上线状态,1:待上线,2:上线中,3:停牌,4:已下线
     * @param backerAccount  管理员账号
     * @param ipAddresse  操作时的ip地址
     * @param guidancePrice  上市指导价
     * @param upTime  上线时间
     * @param addTime 添加时间
     * @return  操作成功：返回true，操作失败：返回false
     */
    public boolean insertTransactionCurrency(String currencyShortName, String currencyName, String currencyImg,
                                             double buyFee, double sellFee, int paymentType, int upStatus,
                                             String backerAccount, String ipAddresse, double guidancePrice,
                                             Timestamp upTime,Timestamp addTime){
        TransactionCurrencyDO transactionCurrency = new TransactionCurrencyDO();
        transactionCurrency.setCurrencyName(currencyName);
        transactionCurrency.setCurrencyShortName(currencyShortName);
        transactionCurrency.setCurrencyImg(currencyImg);
        transactionCurrency.setBuyFee(buyFee);
        transactionCurrency.setSellFee(sellFee);
        transactionCurrency.setGuidancePrice(guidancePrice);
        transactionCurrency.setPaymentType(paymentType);
        transactionCurrency.setUpStatus(upStatus);
        transactionCurrency.setBackerAccount(backerAccount);
        transactionCurrency.setIpAddress(ipAddresse);
        transactionCurrency.setUpTime(upTime);
        transactionCurrency.setAddTime(addTime);

        int currencyId = transactionCurrencyDao.insertTransactionCurrency(transactionCurrency);
        if (currencyId > 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 新增交易币种(后台)
     * @param currencyShortName 货币简称
     * @param currencyName 货币名称
     * @param currencyImg  币种徽标
     * @param buyFee  买入手续费
     * @param sellFee  卖出手续费
     * @param paymentType  交易状态,1:正常，2:停牌
     * @param upStatus  上线状态,1:待上线,2:上线中,3:停牌,4:已下线
     * @param backerAccount  管理员账号
     * @param ipAddresse  操作时的ip地址
     * @param guidancePrice  上市指导价
     * @param upTime  上线时间
     * @param addTime 添加时间
     * @return  操作成功：返回true，操作失败：返回false
     */
    @Transactional
    public boolean addTransactionCurrency(String currencyShortName, String currencyName, String currencyImg,
                                   double buyFee, double sellFee,int paymentType, int upStatus, String backerAccount, String ipAddresse,
                                   double guidancePrice, Timestamp upTime,Timestamp addTime){
        TransactionCurrencyDO transactionCurrency = new TransactionCurrencyDO();
        transactionCurrency.setCurrencyName(currencyName);
        transactionCurrency.setCurrencyShortName(currencyShortName);
        transactionCurrency.setCurrencyImg(currencyImg);
        transactionCurrency.setBuyFee(buyFee);
        transactionCurrency.setSellFee(sellFee);
        transactionCurrency.setGuidancePrice(guidancePrice);
        transactionCurrency.setPaymentType(paymentType);
        transactionCurrency.setUpStatus(upStatus);
        transactionCurrency.setRankNumber(1);
        transactionCurrency.setBackerAccount(backerAccount);
        transactionCurrency.setIpAddress(ipAddresse);
        transactionCurrency.setAddTime(addTime);

        boolean addBoo = true;
        int currencyId = 0;
        if (upTime == null) {
            transactionCurrency.setUpTime(DateUtil.getCurrentTime());
            transactionCurrency.setPaymentType(1);
            transactionCurrency.setUpStatus(2);
        } else {
            transactionCurrency.setUpTime(upTime);
        }

        int maxNum = transactionCurrencyDao.countTransactionCurrencyForBack(0,0, 0, null, null, null,null, null);
        //降位
        if (maxNum > 0) {
            addBoo = transactionCurrencyDao.updateCurrencyRankNumber(maxNum);
        }
        currencyId = transactionCurrencyDao.insertTransactionCurrency(transactionCurrency);
        if (maxNum > 0 && !addBoo) {
            addBoo = false;
        }
        if (currencyId <= 0){
            addBoo = false;
        }
        if (upTime == null && addBoo) {
            addBoo = redisService.addValue(RedisKeyConfig.YESTERDAY_PRICE + transactionCurrency.getCurrencyId(),
                    transactionCurrency.getGuidancePrice());
        }

        // 数据回滚
        if (!addBoo) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
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
     * 根据币种简称获取交易币种
     * @param currencyShortName  货币简称
     * @return  操作成功：返回交易币种，操作失败：返回null
     */
    public TransactionCurrencyVO getTransactionCurrencyByCurrencyShortName(String currencyShortName){
        return transactionCurrencyDao.getTransactionCurrencyByCurrencyShortName(currencyShortName);
    }

    /**
     * 获取所有币种行情信息(web端)
     * @return 查询成功：返回所有币种行情信息；查询失败：返回null
     */
    @Override
    public List<TransactionUserDealDTO> getTransactionCurrencyMarketForWeb() {

        //查询所有币种
        List<TransactionUserDealDTO> transactionUserDealList = transactionCurrencyDao.getTransactionCurrencyMarketForWeb();

        if (transactionUserDealList != null) {
            for (TransactionUserDealDTO transactionUserDeal:transactionUserDealList) {
                int currencyId = transactionUserDeal.getCurrencyId();
                StandardParameterVO standardParameterVO = listTransactionCurrencyAll(currencyId);

                transactionUserDeal.setLatestPrice(standardParameterVO.getNowPrice());
                transactionUserDeal.setBuyOnePrice(standardParameterVO.getBuyOne());
                transactionUserDeal.setSellOnePrice(standardParameterVO.getSellOne());
                transactionUserDeal.setVolume(standardParameterVO.getDayTurnove());
                transactionUserDeal.setChange(standardParameterVO.getTodayRange());
            }
        }
        long endTime = System.currentTimeMillis();
        return transactionUserDealList;
    }

    /**
     * 获取所有币种行情信息(wap端)
     * @return 查询成功：返回所有币种行情信息；查询失败：返回null
     */
    @Override
    public List<TransactionUserDealDTO> getTransactionCurrencyMarketForWap() {
        //查询所有币种
        List<TransactionUserDealDTO> transactionUserDealList = transactionCurrencyDao.getTransactionCurrencyMarketForWap();
        if (transactionUserDealList != null) {
            for (TransactionUserDealDTO transactionUserDeal:transactionUserDealList) {
                int currencyId = transactionUserDeal.getCurrencyId();
                StandardParameterVO standardParameterVO = listTransactionCurrencyAll(currencyId);

                transactionUserDeal.setLatestPrice(standardParameterVO.getNowPrice());
                transactionUserDeal.setBuyOnePrice(standardParameterVO.getBuyOne());
                transactionUserDeal.setSellOnePrice(standardParameterVO.getSellOne());
                transactionUserDeal.setVolume(standardParameterVO.getDayTurnove());
                transactionUserDeal.setChange(standardParameterVO.getTodayRange());
            }
        }
        long endTime = System.currentTimeMillis();
        return transactionUserDealList;
    }

    /**
     * 查询币种个数（后台）
     * @param currencyId  币种Id,查询全部填0
     * @param paymentType  交易状态,1:正常，2:涨停，3:跌停，4:停牌
     * @param upStatus  上线状态,1:待上线,2:上线中,3:禁用,4:已下线
     * @param backAccount  管理员账号
     * @param startAddTime  开始添加时间
     * @param endAddTime  结束添加时间
     * @param startUpTime  开始上线时间
     * @param endUpTime  结束上线时间
     * @return  操作成功：返回交易币种条数，操作失败：返回0
     */
    public int countTransactionCurrencyForBack(int currencyId, int paymentType, int upStatus, String backAccount,
                                               Timestamp startAddTime, Timestamp endAddTime, Timestamp startUpTime, Timestamp endUpTime){
        return transactionCurrencyDao.countTransactionCurrencyForBack(currencyId, paymentType, upStatus,
                backAccount, startAddTime, endAddTime, startUpTime, endUpTime);
    }

    /**
     * 查询币种集合（后台）
     * @param currencyId  币种Id,查询全部填0
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
    public List<TransactionCurrencyVO> listTransactionCurrencyForBack(int currencyId, int paymentType, int upStatus, String backAccount,
                                                                      Timestamp startAddTime, Timestamp endAddTime, Timestamp startUpTime, Timestamp endUpTime, int pageNumber, int pageSize){

        List<TransactionCurrencyVO> transactionCurrencyVOS = transactionCurrencyDao.listTransactionCurrencyForBack(currencyId, paymentType, upStatus, backAccount,
                startAddTime, endAddTime, startUpTime, endUpTime, pageNumber, pageSize);

        for (TransactionCurrencyVO currency: transactionCurrencyVOS) {
            boolean resultBoo = transactionDealRedisService.validateGuidancePrice(currency.getCurrencyId());
            if (resultBoo){
                currency.setReCode(2);
            } else {
                currency.setReCode(1);
            }
        }

        return transactionCurrencyVOS;
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
        TransactionCurrencyVO currency = transactionCurrencyDao.getTransactionCurrencyByCurrencyId(currencyId);
        if (currency == null) {
            return false;
        }

        //修改上线状态
        boolean upBoo = transactionCurrencyDao.updateUpStatus(currencyId, upStatus, backerAccount, ipAddress, upTime);

        //修改交易状态
        if (upStatus == 2) {
            upBoo = transactionCurrencyDao.updatePaymentType(currencyId, 1, backerAccount, ipAddress);
        }
        if (upStatus == 4) {
            upBoo = transactionCurrencyDao.updatePaymentType(currencyId, 2, backerAccount, ipAddress);
        }
        if (upStatus == 2 && upBoo) {
            //判断当前时间是否是凌晨至开盘之前
            long dateLon = DateUtil.lingchenLong();
            long nowDate = DateUtil.getCurrentTime().getTime() - RedisKeyConfig.OPENING_TIME;
            if(nowDate >= dateLon){
                //八点至凌晨
                dateLon = dateLon + RedisKeyConfig.OPENING_TIME;
            } else {
                //凌晨至八点
                dateLon = dateLon - RedisKeyConfig.DAY_TIME + RedisKeyConfig.OPENING_TIME;
            }

            //昨日收盘价计算
            dateLon = dateLon - 1;
            Timestamp date = DateUtil.longToTimestamp(dateLon);

            List<TransactionDealPriceDTO> nowLastPrice = transactionDealRedisService.getNowLastPrice(date);
            TransactionCurrencyDO transactionCurrency = transactionCurrencyDao.getTransactionCurrencyByCurrencyId(currencyId);
            if (nowLastPrice != null && !nowLastPrice.isEmpty()  && transactionCurrency != null) {
                List<Integer> lowInt = new ArrayList<>();
                for (TransactionDealPriceDTO currDTO: nowLastPrice) {
                    lowInt.add(currDTO.getCurrencyId());
                }

                if (!lowInt.contains(currencyId)) {
                    upBoo = redisService.addValue(RedisKeyConfig.YESTERDAY_PRICE + currencyId, currency.getGuidancePrice());
                }
            }
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
        Object nowPriceStr = redisService.getValue(RedisKeyConfig.NOW_PRICE + currencyId);
        if(nowPriceStr != null){
            double nowPrice = Double.parseDouble(nowPriceStr.toString());
            nowPrice = NumberUtil.doubleFormat(nowPrice, 2);
            standardParameter.setNowPrice(nowPrice);
        }

        //买一价
        Object buyOneKeyStr = redisService.getValue(RedisKeyConfig.BUY_ONE_KEY + currencyId);
        if(buyOneKeyStr != null){
            double buyOne = Double.parseDouble(buyOneKeyStr.toString());
            buyOne = NumberUtil.doubleFormat(buyOne, 2);
            standardParameter.setBuyOne(buyOne);

        }

        //卖一价
        Object sellOneKeyStr = redisService.getValue(RedisKeyConfig.SELL_ONE_KEY + currencyId);
        if(sellOneKeyStr != null){
            double sellOne = Double.parseDouble(sellOneKeyStr.toString());
            sellOne = NumberUtil.doubleFormat(sellOne, 2);
            standardParameter.setSellOne(sellOne);

        }

        //今日最高价
        Object todayMaxPriceStr = redisService.getValue(RedisKeyConfig.TODAY_MAX_PRICE + currencyId);
        if(todayMaxPriceStr != null){
            double todayMax = Double.parseDouble(todayMaxPriceStr.toString());
            todayMax = NumberUtil.doubleFormat(todayMax, 2);
            standardParameter.setTodayMax(todayMax);

        }

        //今日最低价
        Object todayMinpriceStr = redisService.getValue(RedisKeyConfig.TODAY_MIN_PRICE + currencyId);
        if(todayMinpriceStr != null){
            double todayMin = Double.parseDouble(todayMinpriceStr.toString());
            todayMin = NumberUtil.doubleFormat(todayMin, 2);
            standardParameter.setTodayMin(todayMin);
        }

        //今日涨幅
        Object todayRangeSre = redisService.getValue(RedisKeyConfig.TODAY_RANGE + currencyId);
        if(todayRangeSre != null){
            double todayRange = Double.parseDouble(todayRangeSre.toString());
            standardParameter.setTodayRange(todayRange);

        }

        //昨日收盘价
        Object yesterdayPriceStr = redisService.getValue(RedisKeyConfig.YESTERDAY_PRICE + currencyId);
        if(yesterdayPriceStr != null){
            double yesterdayPrice = Double.parseDouble(yesterdayPriceStr.toString());
            standardParameter.setYesterdayPrice(yesterdayPrice);
        }

        //今日小时成交量
        Object dayTurnoverStr = redisService.getValue(RedisKeyConfig.DAY_TURNOVER + currencyId);
        if(dayTurnoverStr != null){
            double dayTurnove = Double.parseDouble(dayTurnoverStr.toString());
            dayTurnove = NumberUtil.doubleFormat(dayTurnove, 4);
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

    /**
     * 获取所有上线和停牌币种信息
     * @return 操作成功：返回币种信息集合，操作失败：返回null
     */
    @Override
    public List<TransactionCurrencyVO> getOnlineAndSuspensionCurrencyForWap() {
        return transactionCurrencyDao.getOnlineAndSuspensionCurrencyForWap();
    }

    /**
     * 上移币种
     * @param currencyId  币种Id
     * @return  操作成功：返回true，操作失败：返回false
     */
    @Transactional
    public boolean upCurrencyRankNumber(int currencyId){
        boolean executeSuccess = false;

        //查询验空
        TransactionCurrencyVO currency = transactionCurrencyDao.getTransactionCurrencyByCurrencyId(currencyId);
        if (currency == null) {
            return false;
        }

        int rankNumber = currency.getRankNumber() - 1;
        int currId = transactionCurrencyDao.getTransactionCurrencyByRankNumber(rankNumber);

        if (currId == 0){
            return false;
        }

        //上移此币种
        executeSuccess = transactionCurrencyDao.upCurrencyRankNumber(currencyId);

        //下移之前排名靠前一位的币种
        if (executeSuccess) {
            executeSuccess = transactionCurrencyDao.downCurrencyRankNumber(currId);
        }

        // 数据回滚
        if (!executeSuccess) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return executeSuccess;
    }

    /**
     * 下移币种
     * @param currencyId  币种Id
     * @return  操作成功：返回true，操作失败：返回false
     */
    @Transactional
    public boolean downCurrencyRankNumber(int currencyId){
        boolean executeSuccess = false;

        //查询验空
        TransactionCurrencyVO currency = transactionCurrencyDao.getTransactionCurrencyByCurrencyId(currencyId);
        if (currency == null) {
            return false;
        }

        int rankNumber = currency.getRankNumber() + 1;
        int currId = transactionCurrencyDao.getTransactionCurrencyByRankNumber(rankNumber);

        if (currId == 0){
            return false;
        }

        //下移此币种
        executeSuccess = transactionCurrencyDao.downCurrencyRankNumber(currencyId);

        //上移之前排名靠后一位的币种
        if (executeSuccess) {
            executeSuccess = transactionCurrencyDao.upCurrencyRankNumber(currId);
        }

        // 数据回滚
        if (!executeSuccess) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }

        return executeSuccess;
    }

    /**
     * 根据币种排名位置获取币种信息
     * @param rankNumber   排名位置
     * @return  操作成功：返回币种Id，操作失败：返回0
     */
    public int getTransactionCurrencyByRankNumber(int rankNumber){
        return transactionCurrencyDao.getTransactionCurrencyByRankNumber(rankNumber);
    }

    /**
     * 置顶币种
     * @param currencyId  币种Id
     * @return  操作成功：返回true，操作失败：返回false
     */
    @Transactional
    public boolean topCurrencyRankNumber(int currencyId){
        boolean executeSuccess = false;

        TransactionCurrencyVO currency = transactionCurrencyDao.getTransactionCurrencyByCurrencyId(currencyId);
        if (currency == null) {
            return false;
        }

        int rankNumber = currency.getRankNumber() - 1;
        int currId = transactionCurrencyDao.getTransactionCurrencyByRankNumber(rankNumber);

        if (currId == 0){
            return false;
        }

        //修改之前的币种排名靠后一位
        executeSuccess = transactionCurrencyDao.updateCurrencyRankNumber(currency.getRankNumber());

        //置顶此币种
        if (executeSuccess) {
            executeSuccess = transactionCurrencyDao.topCurrencyRankNumber(currencyId);
        }

        // 数据回滚
        if (!executeSuccess) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }

        return executeSuccess;
    }

    /**
     * 查询所有交易币种基本信息
     * @return 操作成功：返回币种信息，操作失败：返回null
     */
    public List<TransactionCurrencyBasicDTO> listAllTransactionCurrencyBasicInfor() {
        return transactionCurrencyDao.listAllTransactionCurrencyBasicInfor();
    }

    /**
     * 获取所有上线中和停牌的币种id集合
     * @return 查询成功:返回币种id集合, 查询失败:返回null
     */
    public List<Integer> listcurrencyId() {
        return transactionCurrencyDao.listcurrencyId();
    }

    /**
     * 查询所有交易币种id,和上线状态（k线图统计操作）
     * @return 操作成功：返回币种信息，操作失败：返回null
     */
    public List<KGraphCurrencyDTO> listKGraphCurrency() {
        return transactionCurrencyDao.listKGraphCurrency();
    }

}
