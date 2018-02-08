package com.jydp.service.impl.transaction;

import com.jydp.dao.ITransactionUserDealDao;
import com.jydp.entity.DO.transaction.TransactionUserDealDO;
import com.jydp.service.ITransactionUserDealService;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

/**
 * 成交记录
 * @author fk
 *
 */
@Service("transactionUserDealService")
public class TransactionUserDealServiceImpl implements ITransactionUserDealService{

    /** 成交记录 */
    @Autowired
    private ITransactionUserDealDao transactionUserDealDao;

    /**
     * 查询用户成交记录
     * @param userId 用户Id
     * @return 查询成功：返回用户成交记录；查询失败：返回null
     */
    @Override
    public List<TransactionUserDealDO> getTransactionUserDeallist(int userId) {
        return transactionUserDealDao.getTransactionUserDeallist(userId);
    }

    /**
     * 新增成交记录
     * @param orderNo  记录号,业务类型（2）+日期（6）+随机位（10）
     * @param pendingOrderNo  挂单记录号
     * @param userId  用户Id
     * @param paymentType  收支类型,1：买入，2：卖出
     * @param currencyId  币种Id
     * @param currencyName  货币名称
     * @param currencyNumber  成交数量
     * @param currencyTotalPrice  成交总价
     * @param remark  备注
     * @param addTime  添加时间
     * @return  操作成功：返回true，操作失败：返回false
     */
    public boolean insertTransactionUserDeal(String orderNo, String pendingOrderNo, int userId, int paymentType,
                                      int currencyId, String currencyName, double currencyNumber, double currencyTotalPrice,
                                      String remark, Timestamp addTime){
        TransactionUserDealDO transactionUserDealDO = new TransactionUserDealDO();
        transactionUserDealDO.setOrderNo(orderNo);
        transactionUserDealDO.setPendingOrderNo(pendingOrderNo);
        transactionUserDealDO.setUserId(userId);
        transactionUserDealDO.setPaymentType(paymentType);
        transactionUserDealDO.setCurrencyId(currencyId);
        transactionUserDealDO.setCurrencyName(currencyName);
        transactionUserDealDO.setCurrencyNumber(currencyNumber);
        transactionUserDealDO.setCurrencyTotalPrice(currencyTotalPrice);
        transactionUserDealDO.setRemark(remark);
        transactionUserDealDO.setAddTime(addTime);

        return transactionUserDealDao.insertTransactionUserDeal(transactionUserDealDO);
    }

    /**
     * 查询成交记录条数(后台)
     * @param orderNo  记录号,业务类型（2）+日期（6）+随机位（10）
     * @param userAccount  用户账号
     * @param paymentType  收支类型,1：买入，2：卖出
     * @param currencyName  货币名称
     * @param startAddTime  起始完成时间
     * @param endAddTime  结束完成时间
     * @return  操作成功：返回成交记录条数，操作失败：返回0
     */
    public int countTransactionUserDealForBack(String orderNo, String userAccount, int paymentType, String currencyName,
                                        Timestamp startAddTime, Timestamp endAddTime){
        return transactionUserDealDao.countTransactionUserDealForBack(orderNo, userAccount, paymentType, currencyName, startAddTime, endAddTime);
    }

    /**
     * 查询成交记录(后台)
     * @param orderNo  记录号,业务类型（2）+日期（6）+随机位（10）
     * @param userAccount  用户账号
     * @param paymentType  收支类型,1：买入，2：卖出
     * @param currencyName  货币名称
     * @param startAddTime  起始完成时间
     * @param endAddTime  结束完成时间
     * @param pageNumber  当前页数
     * @param pageSize  每页条数
     * @return  操作成功：返回成交记录，操作失败：返回null
     */
    public List<TransactionUserDealDO> listTransactionUserDealForBack(String orderNo, String userAccount, int paymentType, String currencyName,
                                                               Timestamp startAddTime, Timestamp endAddTime, int pageNumber, int pageSize){
        return transactionUserDealDao.listTransactionUserDealForBack(orderNo, userAccount, paymentType, currencyName, startAddTime, endAddTime, pageNumber, pageSize);
    }

}
