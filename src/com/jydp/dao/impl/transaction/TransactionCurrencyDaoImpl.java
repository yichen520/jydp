package com.jydp.dao.impl.transaction;

import com.iqmkj.utils.LogUtil;
import com.jydp.dao.ITransactionCurrencyDao;
import com.jydp.entity.DO.transaction.TransactionCurrencyDO;
import com.jydp.entity.DTO.KGraphCurrencyDTO;
import com.jydp.entity.DTO.TransactionCurrencyBasicDTO;
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
     * @return  操作成功：返回币种Id，操作失败：返回0
     */
    public int insertTransactionCurrency(TransactionCurrencyDO transactionCurrency){
        int currencyId = 0;

        try {
            currencyId = sqlSessionTemplate.insert("TransactionCurrency_insertTransactionCurrency", transactionCurrency);
        } catch (Exception e) {
            LogUtil.printErrorLog(e);
        }

        return currencyId;
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
        List<TransactionCurrencyVO> TransactionCurrencyList = null;

        try {
            TransactionCurrencyList = sqlSessionTemplate.selectList("TransactionCurrency_getTransactionCurrencyListForWeb");
        } catch (Exception e) {
            LogUtil.printErrorLog(e);
        }
        return TransactionCurrencyList;
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
     * 根据币种简称获取交易币种
     * @param currencyShortName  货币简称
     * @return  操作成功：返回交易币种，操作失败：返回null
     */
    public TransactionCurrencyVO getTransactionCurrencyByCurrencyShortName(String currencyShortName){
        TransactionCurrencyVO transactionCurrency = null;

        try {
            transactionCurrency = sqlSessionTemplate.selectOne("TransactionCurrency_getTransactionCurrencyByCurrencyShortName", currencyShortName);
        } catch (Exception e) {
            LogUtil.printErrorLog(e);
        }

        return  transactionCurrency;
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
        int result = 0;

        Map<String, Object> map = new HashMap<>();
        map.put("currencyId", currencyId);
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
        List<TransactionCurrencyVO> resultList = null;

        Map<String, Object> map = new HashMap<>();
        map.put("currencyId", currencyId);
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
     * @param backerAccount  后台管理员账号
     * @param ipAddress  操作时的ip地址
     * @return  操作成功：返回true，操作失败：返回false
     */
    public boolean updatePaymentType(int currencyId, int paymentType, String backerAccount, String ipAddress){
        int result = 0;

        Map<String, Object> map = new HashMap<>();
        map.put("currencyId", currencyId);
        map.put("paymentType", paymentType);
        map.put("backerAccount", backerAccount);
        map.put("ipAddress", ipAddress);

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
     * @param backerAccount  后台管理员账号
     * @param ipAddress  操作时的ip地址
     * @param upTime  上线时间   下线填空
     * @return  操作成功：返回true，操作失败：返回false
     */
    public boolean updateUpStatus(int currencyId, int upStatus, String backerAccount, String ipAddress, Timestamp upTime){
        int result = 0;

        Map<String, Object> map = new HashMap<>();
        map.put("currencyId", currencyId);
        map.put("upStatus", upStatus);
        map.put("backerAccount", backerAccount);
        map.put("ipAddress", ipAddress);
        map.put("upTime", upTime);

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
        List<TransactionUserDealDTO> transactionUserDealList = null;

        try {
            transactionUserDealList = sqlSessionTemplate.selectList("TransactionCurrency_getTransactionCurrencyMarketForWeb");
        } catch (Exception e) {
            LogUtil.printErrorLog(e);
        }
        return transactionUserDealList;
    }

    /**
     * 为币种行情查询所有币种信息
     * @return 操作成功：返回币种信息；操作失败：返回null (wap)
     */
    @Override
    public List<TransactionUserDealDTO> getTransactionCurrencyMarketForWap() {
        List<TransactionUserDealDTO> transactionUserDealList = null;
        try {
            transactionUserDealList = sqlSessionTemplate.selectList("TransactionCurrency_getTransactionCurrencyMarketForWap");
        } catch (Exception e) {
            LogUtil.printErrorLog(e);
        }
        return transactionUserDealList;
    }

    /**
     * 查询全部币种信息(web端用户注册时使用)
     * @return 操作成功：返回币种信息集合，操作失败：返回null
     */
    @Override
    public List<TransactionCurrencyVO> getAllCurrencylistForWeb() {
        List<TransactionCurrencyVO> transactionCurrencyList = null;

        try {
            transactionCurrencyList = sqlSessionTemplate.selectList("TransactionCurrency_getAllCurrencylistForWeb");
        } catch (Exception e) {
            LogUtil.printErrorLog(e);
        }
        return transactionCurrencyList;
    }

    /**
     * 获取所有上线和停牌币种信息
     * @return 操作成功：返回币种信息集合，操作失败：返回null
     */
    @Override
    public List<TransactionCurrencyVO> getOnlineAndSuspensionCurrencyForWeb() {
        List<TransactionCurrencyVO> transactionCurrencyList = null;

        try {
            transactionCurrencyList = sqlSessionTemplate.selectList("TransactionCurrency_getOnlineAndSuspensionCurrencyForWeb");
        } catch (Exception e) {
            LogUtil.printErrorLog(e);
        }
        return transactionCurrencyList;
    }

    /**
     * 获取所有上线和停牌币种信息
     * @return 操作成功：返回币种信息集合，操作失败：返回null
     */
    @Override
    public List<TransactionCurrencyVO> getOnlineAndSuspensionCurrencyForWap() {
        List<TransactionCurrencyVO> transactionCurrencyList = null;

        try {
            transactionCurrencyList = sqlSessionTemplate.selectList("TransactionCurrency_getOnlineAndSuspensionCurrencyForWap");
        } catch (Exception e) {
            LogUtil.printErrorLog(e);
        }
        return transactionCurrencyList;
    }

    /**
     * 根据币种排名位置获取币种信息id
     * @param rankNumber   排名位置
     * @return  操作成功：返回币种Id，操作失败：返回0
     */
    public int getTransactionCurrencyByRankNumber(int rankNumber){
        int result = 0;

        try {
            result = sqlSessionTemplate.selectOne("TransactionCurrency_getTransactionCurrencyByRankNumber", rankNumber);
        } catch (Exception e) {
            LogUtil.printErrorLog(e);
        }

        return result;
    }

    /**
     * 修改币种信息排名（大于该排名的所有币种排名-1）
     * @param rankNumber 排名位置
     * @return 操作成功：返回true，操作失败：返回false
     */
    public boolean updateCurrencyRankNumber(int rankNumber){
        int result = 0;

        try {
            result = sqlSessionTemplate.update("TransactionCurrency_updateCurrencyRankNumber", rankNumber);
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
     * 上移币种
     * @param currencyId  币种Id
     * @return  操作成功：返回true，操作失败：返回false
     */
    public boolean upCurrencyRankNumber(int currencyId){
        int result = 0;

        try {
            result = sqlSessionTemplate.update("TransactionCurrency_upCurrencyRankNumber", currencyId);
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
     * 下移币种
     * @param currencyId  币种Id
     * @return  操作成功：返回true，操作失败：返回false
     */
    public boolean downCurrencyRankNumber(int currencyId){
        int result = 0;

        try {
            result = sqlSessionTemplate.update("TransactionCurrency_downCurrencyRankNumber", currencyId);
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
     * 置顶币种
     * @param currencyId  币种Id
     * @return  操作成功：返回true，操作失败：返回false
     */
    public boolean topCurrencyRankNumber(int currencyId){
        int result = 0;

        try {
            result = sqlSessionTemplate.update("TransactionCurrency_topCurrencyRankNumber", currencyId);
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
     * 查询所有交易币种基本信息
     * @return 操作成功：返回币种信息，操作失败：返回null
     */
    public List<TransactionCurrencyBasicDTO> listAllTransactionCurrencyBasicInfor() {
        List<TransactionCurrencyBasicDTO> result = null;
        try {
            result = sqlSessionTemplate.selectList("TransactionCurrency_listAllTransactionCurrencyBasicInfor");
        } catch (Exception e) {
            LogUtil.printErrorLog(e);
        }
        return result;
    }

    /**
     * 查询所有交易币种id,和上线状态（k线图统计操作）
     * @return 操作成功：返回币种信息，操作失败：返回null
     */
    public List<KGraphCurrencyDTO> listKGraphCurrency() {
        List<KGraphCurrencyDTO> result = null;
        try {
            result = sqlSessionTemplate.selectList("TransactionCurrency_listKGraphCurrency");
        } catch (Exception e) {
            LogUtil.printErrorLog(e);
        }
        return result;
    }

    /**
     * 获取所有上线中和停牌的币种id集合
     * @return 查询成功:返回币种id集合, 查询失败:返回null
     */
    public List<Integer> listcurrencyId() {
        List<Integer> resultList = null;

        try {
            resultList = sqlSessionTemplate.selectList("TransactionCurrency_listcurrencyId");
        } catch (Exception e) {
            LogUtil.printErrorLog(e);
        }

        return resultList;
    }


}
