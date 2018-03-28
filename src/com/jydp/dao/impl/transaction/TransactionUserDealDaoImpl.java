package com.jydp.dao.impl.transaction;

import com.iqmkj.utils.LogUtil;
import com.jydp.dao.ITransactionUserDealDao;
import com.jydp.entity.DO.transaction.TransactionUserDealDO;
import com.jydp.entity.VO.TransactionUserDealVO;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 成交记录
 * @author fk
 *
 */
@Repository
public class TransactionUserDealDaoImpl implements ITransactionUserDealDao{

    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    /**
     * 查询用户成交记录(web端)
     * @param userId 用户Id
     * @param pageNumber  当前页数
     * @param pageSize  每页条数
     * @return 查询成功：返回用户成交记录；查询失败：返回null
     */
    @Override
    public List<TransactionUserDealVO> getTransactionUserDeallist(int userId, int pageNumber, int pageSize) {
        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        map.put("startNumber", pageNumber * pageSize);
        map.put("pageSize", pageSize);

        List<TransactionUserDealVO> transactionUserDealList = null;
        try {
            transactionUserDealList = sqlSessionTemplate.selectList("TransactionUserDeal_getTransactionUserDeallist",map);
        } catch (Exception e) {
            LogUtil.printErrorLog(e);
        }
        return transactionUserDealList;
    }

    /**
     * 新增成交记录
     * @param transactionUserDeal  成交记录
     * @return  操作成功：返回true，操作失败：返回false
     */
    public boolean insertTransactionUserDeal(TransactionUserDealDO transactionUserDeal){
        int result = 0;

        try {
            result = sqlSessionTemplate.insert("TransactionUserDeal_insertTransactionUserDeal", transactionUserDeal);
        } catch (Exception e){
            LogUtil.printErrorLog(e);
        }

        if (result > 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 查询成交记录条数(后台)
     * @param userAccount  用户账号
     * @param paymentType  收支类型,1：买入，2：卖出
     * @param currencyId  币种id,查询全部填0
     * @param startAddTime  起始完成时间
     * @param endAddTime  结束完成时间
     * @param startPendTime  起始挂单时间
     * @param endPendTime  结束挂单时间
     * @return  操作成功：返回成交记录条数，操作失败：返回0
     */
    public int countTransactionUserDealForBack(String userAccount, int paymentType, int currencyId,
                                        Timestamp startAddTime, Timestamp endAddTime, Timestamp startPendTime, Timestamp endPendTime){
        int result = 0;

        Map<String, Object> map = new HashMap<>();
        map.put("userAccount", userAccount);
        map.put("paymentType", paymentType);
        map.put("currencyId", currencyId);
        map.put("startAddTime", startAddTime);
        map.put("endAddTime", endAddTime);
        map.put("startPendTime", startPendTime);
        map.put("endPendTime", endPendTime);

        try {
            result = sqlSessionTemplate.selectOne("TransactionUserDeal_countTransactionUserDealForBack", map);
        } catch (Exception e) {
            LogUtil.printErrorLog(e);
        }

        return result;
    }

    /**
     * 查询成交记录(后台)
     * @param userAccount  用户账号
     * @param paymentType  收支类型,1：买入，2：卖出
     * @param currencyId  币种id,查询全部填0
     * @param startAddTime  起始完成时间
     * @param endAddTime  结束完成时间
     * @param startPendTime  起始挂单时间
     * @param endPendTime  结束挂单时间
     * @param pageNumber  当前页数
     * @param pageSize  每页条数
     * @return  操作成功：返回成交记录，操作失败：返回null
     */
    public List<TransactionUserDealVO> listTransactionUserDealForBack(String userAccount, int paymentType, int currencyId,
                                                                      Timestamp startAddTime, Timestamp endAddTime, Timestamp startPendTime, Timestamp endPendTime,
                                                                      int pageNumber, int pageSize){
        List<TransactionUserDealVO> resultList = null;

        Map<String, Object> map = new HashMap<>();
        map.put("userAccount", userAccount);
        map.put("paymentType", paymentType);
        map.put("currencyId", currencyId);
        map.put("startAddTime", startAddTime);
        map.put("endAddTime", endAddTime);
        map.put("startPendTime", startPendTime);
        map.put("endPendTime", endPendTime);
        map.put("startNumber", pageNumber * pageSize);
        map.put("pageSize", pageSize);

        try {
            resultList = sqlSessionTemplate.selectList("TransactionUserDeal_listTransactionUserDealForBack", map);
        } catch (Exception e) {
            LogUtil.printErrorLog(e);
        }

        return resultList;
    }

    /**
     * 根据挂单记录号查询成交记录条数
     * @param pendNo  挂单记录号
     * @param userId  用户Id
     * @return  操作成功：返回成交记录条数，操作失败:返回0
     */
    public int countTransactionUserDealByPendNo(String pendNo, int userId){
        int result = 0;

        Map<String, Object> map = new HashMap<>();
        map.put("pendNo", pendNo);
        map.put("userId", userId);

        try {
            result = sqlSessionTemplate.selectOne("TransactionUserDeal_countTransactionUserDealByPendNo", map);
        } catch (Exception e) {
            LogUtil.printErrorLog(e);
        }

        return  result;
    }

    /**
     * 根据挂单记录号查询成交记录
     * @param pendNo  挂单记录号
     * @param userId  用户Id
     * @param pageNumber  当前页数
     * @param pageSize  每页条数
     * @return  操作成功：返回成交记录集合，操作失败:返回null
     */
    public List<TransactionUserDealVO> listTransactionUserDealByPendNo(String pendNo, int userId, int pageNumber, int pageSize){
        List<TransactionUserDealVO> resultList = null;

        Map<String, Object> map = new HashMap<>();
        map.put("pendNo", pendNo);
        map.put("userId", userId);
        map.put("startNumber", pageNumber * pageSize);
        map.put("pageSize", pageSize);

        try {
            resultList = sqlSessionTemplate.selectList("TransactionUserDeal_listTransactionUserDealByPendNo", map);
        } catch (Exception e) {
            LogUtil.printErrorLog(e);
        }

        return resultList;
    }

    /**
     * 查询用户成交记录总数
     * @param userId 用户Id
     * @return 查询成功：返回记录总数，查询失败：返回0
     */
    @Override
    public int countUserDealForWeb(int userId) {

        int result = 0;

        try {
            result = sqlSessionTemplate.selectOne("TransactionUserDeal_countUserDealForWeb", userId);
        } catch (Exception e) {
            LogUtil.printErrorLog(e);
        }

        return result;
    }
}
