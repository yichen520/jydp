package com.jydp.service.impl.transaction;

import com.iqmkj.utils.DateUtil;
import com.iqmkj.utils.NumberUtil;
import com.jydp.dao.ITransactionCurrencyDao;
import com.jydp.entity.DO.transaction.TransactionCurrencyDO;
import com.jydp.entity.DTO.TransactionUserDealDTO;
import com.jydp.entity.VO.TransactionCurrencyVO;
import com.jydp.service.ITransactionCurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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

        List<TransactionUserDealDTO> transactionUserDealDTOList = null;
        //当日开盘时间
        Timestamp openTime = DateUtil.stringToTimestamp(DateUtil.longToTimeStr(DateUtil.lingchenLong(),DateUtil.dateFormat4) + " 08:00:00");

        Date todayOpenDate = openTime;
        Calendar calendar = Calendar.getInstance();
        Calendar oldCalendar = Calendar.getInstance();
        calendar.setTime(todayOpenDate);
        oldCalendar.setTime(todayOpenDate);
        calendar.add(Calendar.DAY_OF_YEAR,-1);//昨天开盘时间
        Date yesterdayOpenDate = calendar.getTime();
        String time = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        time = sdf.format(yesterdayOpenDate);
        //昨日开盘时间
        Timestamp beginTime = Timestamp.valueOf(time);

        transactionUserDealDTOList = transactionCurrencyDao.getTransactionCurrencyMarketForWeb(openTime,beginTime,openTime);
        if (transactionUserDealDTOList != null) {
            for (TransactionUserDealDTO transactionUserDeal:transactionUserDealDTOList) {
                double yesterdayLastPrice = transactionUserDeal.getYesterdayLastPrice();
                double change = 0;//24小时涨幅 eg:24小时涨跌为24.31%,change = 24.31

                if (yesterdayLastPrice != 0) {
                    change = NumberUtil.doubleFormat(((transactionUserDeal.getLatestPrice() - yesterdayLastPrice)/yesterdayLastPrice)*100,2);
                }
                transactionUserDeal.setChange(change);
                transactionUserDeal.setBuyOnePrice(NumberUtil.doubleFormat(transactionUserDeal.getBuyOnePrice(),2));
                transactionUserDeal.setSellOnePrice(NumberUtil.doubleFormat(transactionUserDeal.getSellOnePrice(),2));
                transactionUserDeal.setLatestPrice(NumberUtil.doubleFormat(transactionUserDeal.getLatestPrice(),2));
                transactionUserDeal.setVolume(NumberUtil.doubleFormat(transactionUserDeal.getVolume(),2));
            }
        }
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
     * @return  操作成功：返回true，操作失败：返回false
     */
    @Transactional
    public boolean updatePaymentType(int currencyId, int paymentType){
        boolean excBoo = transactionCurrencyDao.updatePaymentType(currencyId, paymentType);

        //停牌同时修改上线状态
        if (excBoo && paymentType == 2) {
            excBoo = transactionCurrencyDao.updateUpStatus(currencyId, 3);
        }
        if (excBoo && paymentType == 1) {
            excBoo = transactionCurrencyDao.updateUpStatus(currencyId, 2);
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
     * @return  操作成功：返回true，操作失败：返回false
     */
    @Transactional
    public boolean updateUpStatus(int currencyId, int upStatus){

        boolean upBoo = transactionCurrencyDao.updateUpStatus(currencyId, upStatus);

        if (upStatus == 2) {
            upBoo = transactionCurrencyDao.updatePaymentType(currencyId, 1);
        }
        if (upStatus == 4) {
            upBoo = transactionCurrencyDao.updatePaymentType(currencyId, 2);
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
}
