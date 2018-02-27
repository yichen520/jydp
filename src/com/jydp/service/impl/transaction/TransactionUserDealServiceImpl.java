package com.jydp.service.impl.transaction;

import com.jydp.dao.ITransactionUserDealDao;
import com.jydp.entity.DO.transaction.TransactionUserDealDO;
import com.jydp.entity.VO.TransactionUserDealVO;
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
     * @param pageNumber  当前页数
     * @param pageSize  每页条数
     * @return 查询成功：返回用户成交记录；查询失败：返回null
     */
    @Override
    public List<TransactionUserDealDO> getTransactionUserDeallist(int userId, int pageNumber, int pageSize) {
        return transactionUserDealDao.getTransactionUserDeallist(userId, pageNumber, pageSize);
    }

    /**
     * 新增成交记录
     * @param orderNo  记录号,业务类型（2）+日期（6）+随机位（10）
     * @param pendingOrderNo  挂单记录号
     * @param userId  用户Id
     * @param paymentType  收支类型,1：买入，2：卖出
     * @param currencyId  币种Id
     * @param currencyName  货币名称
     * @param transactionPrice 成交单价
     * @param currencyNumber  成交数量
     * @param currencyTotalPrice  成交总价
     * @param remark  备注
     * @param addTime  添加时间
     * @return  操作成功：返回true，操作失败：返回false
     */
    public boolean insertTransactionUserDeal(String orderNo, String pendingOrderNo, int userId, int paymentType,
                                      int currencyId, String currencyName,double transactionPrice, double currencyNumber, double currencyTotalPrice,
                                      String remark, Timestamp addTime){
        TransactionUserDealDO transactionUserDealDO = new TransactionUserDealDO();
        transactionUserDealDO.setOrderNo(orderNo);
        transactionUserDealDO.setPendingOrderNo(pendingOrderNo);
        transactionUserDealDO.setUserId(userId);
        transactionUserDealDO.setPaymentType(paymentType);
        transactionUserDealDO.setCurrencyId(currencyId);
        transactionUserDealDO.setCurrencyName(currencyName);
        transactionUserDealDO.setTransactionPrice(transactionPrice);
        transactionUserDealDO.setCurrencyNumber(currencyNumber);
        transactionUserDealDO.setCurrencyTotalPrice(currencyTotalPrice);
        transactionUserDealDO.setRemark(remark);
        transactionUserDealDO.setAddTime(addTime);

        return transactionUserDealDao.insertTransactionUserDeal(transactionUserDealDO);
    }

    /**
     * 查询成交记录条数(后台)
     * @param userAccount  用户账号
     * @param paymentType  收支类型,1：买入，2：卖出
     * @param currencyName  货币名称
     * @param startAddTime  起始完成时间
     * @param endAddTime  结束完成时间
     * @param startPendTime  起始挂单时间
     * @param endPendTime  结束挂单时间
     * @return  操作成功：返回成交记录条数，操作失败：返回0
     */
    public int countTransactionUserDealForBack(String userAccount, int paymentType, String currencyName,
                                        Timestamp startAddTime, Timestamp endAddTime, Timestamp startPendTime, Timestamp endPendTime){
        return transactionUserDealDao.countTransactionUserDealForBack(userAccount, paymentType, currencyName, startAddTime, endAddTime, startPendTime, endPendTime);
    }

    /**
     * 查询成交记录(后台)
     * @param userAccount  用户账号
     * @param paymentType  收支类型,1：买入，2：卖出
     * @param currencyName  货币名称
     * @param startAddTime  起始完成时间
     * @param endAddTime  结束完成时间
     * @param startPendTime  起始挂单时间
     * @param endPendTime  结束挂单时间
     * @param pageNumber  当前页数
     * @param pageSize  每页条数
     * @return  操作成功：返回成交记录，操作失败：返回null
     */
    public List<TransactionUserDealVO> listTransactionUserDealForBack(String userAccount, int paymentType, String currencyName,
                                                                      Timestamp startAddTime, Timestamp endAddTime, Timestamp startPendTime, Timestamp endPendTime,
                                                                      int pageNumber, int pageSize){
        return transactionUserDealDao.listTransactionUserDealForBack(userAccount, paymentType, currencyName, startAddTime, endAddTime, startPendTime, endPendTime, pageNumber, pageSize);
    }

}
