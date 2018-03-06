package com.jydp.dao.impl.transaction;

import com.iqmkj.utils.LogUtil;
import com.jydp.dao.ITransactionCurrencyDao;
import com.jydp.entity.DO.transaction.TransactionCurrencyDO;
import com.jydp.entity.DTO.TransactionUserDealDTO;
import com.jydp.entity.VO.TransactionCurrencyVO;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 交易币种
 * @author fk
 *
 */
@Repository
public class TransactionCurrencyDaoImpl implements ITransactionCurrencyDao{

    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    /**
     * 新增交易币种
     * @param transactionCurrency  交易币种
     * @return  操作成功：返回true，操作失败：返回false
     */
    public boolean insertTransactionCurrency(TransactionCurrencyDO transactionCurrency){
        int result = 0;

        try {
            result = sqlSessionTemplate.insert("TransactionCurrency_insertTransactionCurrency", transactionCurrency);
        } catch (Exception e) {
            LogUtil.printErrorLog(e);
        }

        if (result > 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 根据币种Id获取交易币种
     * @param currencyId  币种Id
     * @return  操作成功：返回交易币种，操作失败：返回null
     */
    public TransactionCurrencyVO getTransactionCurrencyByCurrencyId(int currencyId){
        TransactionCurrencyVO transactionCurrency = null;

        try {
            transactionCurrency = sqlSessionTemplate.selectOne("TransactionCurrency_getTransactionCurrencyByCurrencyId", currencyId);
        } catch (Exception e) {
            LogUtil.printErrorLog(e);
        }

        return transactionCurrency;
    }

    /**
     * 修改交易币种信息
     * @param transactionCurrency  交易币种
     * @return  操作成功：返回true，操作失败：返回false
     */
    public boolean updateTransactionCurrency(TransactionCurrencyDO transactionCurrency){
        int result = 0;

        try {
            result = sqlSessionTemplate.update("TransactionCurrency_updateTransactionCurrency", transactionCurrency);
        } catch (Exception e) {
            LogUtil.printErrorLog(e);
        }

        if (result > 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 根据币种Id删除交易币种
     * @param currencyId  币种Id
     * @return  操作成功：返回true，操作失败：返回false
     */
    public boolean deleteTransactionCurrencyByCurrencyId(int currencyId){
        int result = 0;

        try {
            result = sqlSessionTemplate.delete("TransactionCurrency_deleteTransactionCurrencyByCurrencyId", currencyId);
        } catch (Exception e) {
            LogUtil.printErrorLog(e);
        }

        if (result > 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 获取所有币种信息(web端用户注册时使用)
     * @return 查询成功：返回币种信息列表；查询失败：返回null
     */
    @Override
    public List<TransactionCurrencyVO> getTransactionCurrencyListForWeb() {
        List<TransactionCurrencyVO> TransactionCurrencyVOList = null;

        try {
            TransactionCurrencyVOList = sqlSessionTemplate.selectList("TransactionCurrency_getTransactionCurrencyListForWeb");
        } catch (Exception e) {
            LogUtil.printErrorLog(e);
        }
        return TransactionCurrencyVOList;
    }

    /**
     * 根据币种名称获取交易币种
     * @param currencyName  币种名称
     * @return  操作成功：返回交易币种，操作失败：返回null
     */
    public TransactionCurrencyVO getTransactionCurrencyByCurrencyName(String currencyName){
        TransactionCurrencyVO transactionCurrency = null;

        try {
            transactionCurrency = sqlSessionTemplate.selectOne("TransactionCurrency_getTransactionCurrencyByCurrencyName", currencyName);
        } catch (Exception e) {
            LogUtil.printErrorLog(e);
        }

        return  transactionCurrency;
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
        int result = 0;

        Map<String, Object> map = new HashMap<>();
        map.put("currencyName", currencyName);
        map.put("paymentType", paymentType);
        map.put("upStatus", upStatus);
        map.put("backerAccount", backAccount);
        map.put("startAddTime", startAddTime);
        map.put("endAddTime", endAddTime);
        map.put("startUpTime", startUpTime);
        map.put("endUpTime", endUpTime);

        try {
            result = sqlSessionTemplate.selectOne("TransactionCurrency_countTransactionCurrencyForBack", map);
        } catch (Exception e) {
            LogUtil.printErrorLog(e);
        }

        return result;
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
        List<TransactionCurrencyVO> resultList = null;

        Map<String, Object> map = new HashMap<>();
        map.put("currencyName", currencyName);
        map.put("paymentType", paymentType);
        map.put("upStatus", upStatus);
        map.put("backerAccount", backAccount);
        map.put("startAddTime", startAddTime);
        map.put("endAddTime", endAddTime);
        map.put("startUpTime", startUpTime);
        map.put("endUpTime", endUpTime);
        map.put("startNumber", pageNumber * pageSize);
        map.put("pageSize", pageSize);

        try {
            resultList = sqlSessionTemplate.selectList("TransactionCurrency_listTransactionCurrencyForBack", map);
        } catch (Exception e) {
            LogUtil.printErrorLog(e);
        }

        return resultList;
    }

    /**
     * 停，复牌操作
     * @param currencyId  币种Id
     * @param paymentType  交易状态,1:正常，2:停牌
     * @return  操作成功：返回true，操作失败：返回false
     */
    public boolean updatePaymentType(int currencyId, int paymentType){
        int result = 0;

        Map<String, Object> map = new HashMap<>();
        map.put("currencyId", currencyId);
        map.put("paymentType", paymentType);

        try {
            result = sqlSessionTemplate.update("TransactionCurrency_updatePaymentType", map);
        } catch (Exception e) {
            LogUtil.printErrorLog(e);
        }

        if (result > 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 上，下线币种操作
     * @param currencyId  币种Id
     * @param upStatus  上线状态,1:待上线,2:上线中,3:停牌,4:已下线
     * @return  操作成功：返回true，操作失败：返回false
     */
    public boolean updateUpStatus(int currencyId, int upStatus){
        int result = 0;

        Map<String, Object> map = new HashMap<>();
        map.put("currencyId", currencyId);
        map.put("upStatus", upStatus);

        try {
            result = sqlSessionTemplate.update("TransactionCurrency_updateUpStatus", map);
        } catch (Exception e) {
            LogUtil.printErrorLog(e);
        }

        if (result > 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 查询全部币种信息
     * @return  操作成功：返回币种信息集合，操作失败：返回null
     */
    public List<TransactionCurrencyDO> listTransactionCurrencyAll(){
        List<TransactionCurrencyDO> resultList= null;

        try {
            resultList = sqlSessionTemplate.selectList("TransactionCurrency_listTransactionCurrencyAll");
        } catch (Exception e){
            LogUtil.printErrorLog(e);
        }

        return resultList;
    }

    /**
     * 为币种行情查询所有币种信息
     * @return 操作成功：返回币种信息；操作失败：返回null
     */
    @Override
    public List<TransactionUserDealDTO> getTransactionCurrencyMarketForWeb() {
        List<TransactionUserDealDTO> transactionUserDealDTOList = null;

        try {
            transactionUserDealDTOList = sqlSessionTemplate.selectList("TransactionCurrency_getTransactionCurrencyMarketForWeb");
        } catch (Exception e) {
            LogUtil.printErrorLog(e);
        }
        return transactionUserDealDTOList;
    }

    /**
     * 获取各币种最新价
     * @param openTime 当日开盘时间
     * @return 查询成功：返回币种信息；查询失败：返回null
     */
    @Override
    public Map<Integer,TransactionUserDealDTO> getNewPriceForWeb(Timestamp openTime) {
        Map<Integer,TransactionUserDealDTO> newPriceMap = null;

        try {
            newPriceMap = sqlSessionTemplate.selectMap("TransactionCurrency_getNewPriceForWeb",openTime,"currencyId");
        } catch (Exception e) {
            LogUtil.printErrorLog(e);
        }
        return newPriceMap;
    }

    /**
     * 查询各币种买一价信息
     * @return 查询成功：返回各币种买一价信息；查询失败：返回null
     */
    @Override
    public Map<Integer,TransactionUserDealDTO> getBuyOneForWeb() {
        Map<Integer,TransactionUserDealDTO> buyOneMap = null;

        try {
            buyOneMap = sqlSessionTemplate.selectMap("TransactionCurrency_getBuyOneForWeb","currencyId");
        } catch (Exception e) {
            LogUtil.printErrorLog(e);
        }
        return buyOneMap;
    }

    /**
     * 查询各币种卖一价信息
     * @return 查询成功：返回各币种信息；查询失败：返回null
     */
    @Override
    public Map<Integer,TransactionUserDealDTO> getSellOneForWeb() {
        Map<Integer,TransactionUserDealDTO> sellOneMap = null;

        try {
            sellOneMap = sqlSessionTemplate.selectMap("TransactionCurrency_getSellOneForWeb","currencyId");
        } catch (Exception e) {
            LogUtil.printErrorLog(e);
        }
        return sellOneMap;
    }

    /**
     * 查询各币种今日成交量
     * @param openTime 今日开盘时间
     * @return 查询成功：返回各币种信息；查询失败：返回null
     */
    @Override
    public Map<Integer,TransactionUserDealDTO> getTransactionVolumeForWeb(Timestamp openTime) {
        Map<Integer,TransactionUserDealDTO> volumeMap = null;

        try {
            volumeMap = sqlSessionTemplate.selectMap("TransactionCurrency_getTransactionVolumeForWeb",openTime,"currencyId");
        } catch (Exception e) {
            LogUtil.printErrorLog(e);
        }
        return volumeMap;
    }

    /**
     * 获取昨日最新成交价
     * @param openTime 今日开盘时间
     * @param startTime 昨日开盘时间
     * @return 查询成功：返回各币种信息；查询失败：返回null
     */
    @Override
    public Map<Integer,TransactionUserDealDTO> getYesterdayLastPriceForWeb(Timestamp openTime, Timestamp startTime) {
        Map<Integer,TransactionUserDealDTO> yesterdayPriceMap = null;

        Map<String, Object> map = new HashMap<>();
        map.put("openTime", openTime);
        map.put("startTime", startTime);

        try {
            yesterdayPriceMap = sqlSessionTemplate.selectMap("TransactionCurrency_getYesterdayLastPriceForWeb",map,"currencyId");
        } catch (Exception e) {
            LogUtil.printErrorLog(e);
        }
        return yesterdayPriceMap;
    }

}
