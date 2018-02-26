package com.jydp.service.impl.transaction;

import com.jydp.dao.ITransactionMakeOrderDao;
import com.jydp.entity.DO.transaction.TransactionMakeOrderDO;
import com.jydp.service.ITransactionMakeOrderService;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

/**
 * 做单记录
 * @author fk
 *
 */
@Service("transactionMakeOrderService")
public class TransactionMakeOrderServiceImpl implements ITransactionMakeOrderService{

    /** 做单记录 */
    @Autowired
    private ITransactionMakeOrderDao transactionMakeOrderDao;

    /**
     * 新增做单记录
     * @param orderNo 记录号,业务类型（2）+日期（6）+随机位（10）
     * @param paymentType 收支类型,1：买入，2：卖出
     * @param currencyId 币种Id
     * @param currencyName 货币名称
     * @param currencyNumber 成交数量
     * @param currencyTotalPrice 成交总价
     * @param backerAccount 后台管理员帐号
     * @param ipAddress 操作时的ip地址
     * @param executeStatus 执行状态,1：待执行,2:执行中,3:执行完成,4:执行失败,5:已撤回
     * @param remark 备注
     * @param executeTime 执行时间
     * @param addTime 添加时间
     * @return  操作成功：返回true，操作失败：返回false
     */
    public boolean insertMakeOrder(String orderNo, int paymentType, int currencyId, String currencyName, double currencyNumber,
                            double currencyTotalPrice, String backerAccount, String ipAddress, int executeStatus,
                            String remark, Timestamp executeTime, Timestamp addTime){
        TransactionMakeOrderDO transactionMakeOrderDO = new TransactionMakeOrderDO();
        transactionMakeOrderDO.setOrderNo(orderNo);
        transactionMakeOrderDO.setPaymentType(paymentType);
        transactionMakeOrderDO.setCurrencyId(currencyId);
        transactionMakeOrderDO.setCurrencyName(currencyName);
        transactionMakeOrderDO.setCurrencyNumber(currencyNumber);
        transactionMakeOrderDO.setCurrencyTotalPrice(currencyTotalPrice);
        transactionMakeOrderDO.setBackerAccount(backerAccount);
        transactionMakeOrderDO.setIpAddress(ipAddress);
        transactionMakeOrderDO.setExecuteStatus(1);
        transactionMakeOrderDO.setRemark(remark);
        transactionMakeOrderDO.setExecuteTime(executeTime);
        transactionMakeOrderDO.setAddTime(addTime);

        return transactionMakeOrderDao.insertMakeOrder(transactionMakeOrderDO);
    }

    /**
     * 根据记录号查询做单记录
     * @param orderNo  记录号
     * @return  操作成功：返回做单记录，操作失败：返回null
     */
    public TransactionMakeOrderDO getTransactionMakeOrderByOrderNo(String orderNo){
        return transactionMakeOrderDao.getTransactionMakeOrderByOrderNo(orderNo);
    }

    /**
     * 查询做单记录个数(后台)
     * @param currencyName 货币名称(币种)
     * @param executeStatus 执行状态,1：待执行,2:执行中,3:执行完成,4:执行失败,5:已撤回
     * @param paymentType 操作类型
     * @param backerAccount 后台管理员帐号
     * @param startAddTime 起始生成时间
     * @param endAddTime 结束生成时间
     * @param startExecuteTime 起始执行时间
     * @param endExecuteTime 结束执行时间
     * @return 操作成功：返回做单记录集合，操作失败：返回null
     */
    public int countTransactionMakeOrderForBack(String currencyName, int executeStatus, int paymentType, String backerAccount,
                                         Timestamp startAddTime, Timestamp endAddTime,
                                         Timestamp startExecuteTime, Timestamp endExecuteTime){
        return transactionMakeOrderDao.countTransactionMakeOrderForBack(currencyName, executeStatus, paymentType, backerAccount, startAddTime, endAddTime, startExecuteTime, endExecuteTime);
    }

    /**
     * 查询做单记录集合(后台)
     * @param currencyName 货币名称(币种)
     * @param executeStatus 执行状态,1：待执行,2:执行中,3:执行完成,4:执行失败,5:已撤回
     * @param paymentType 操作类型
     * @param backerAccount 后台管理员帐号
     * @param startAddTime 起始生成时间
     * @param endAddTime 结束生成时间
     * @param startExecuteTime 起始执行时间
     * @param endExecuteTime 结束执行时间
     * @param pageNumber 起始页数
     * @param pageSize 每页条数
     * @return 操作成功：返回做单记录集合，操作失败：返回null
     */
    public List<TransactionMakeOrderDO> listTransactionMakeOrderForBack(String currencyName, int executeStatus, int paymentType, String backerAccount,
                                                                 Timestamp startAddTime, Timestamp endAddTime,
                                                                 Timestamp startExecuteTime, Timestamp endExecuteTime,
                                                                 int pageNumber, int pageSize){
        return transactionMakeOrderDao.listTransactionMakeOrderForBack(currencyName, executeStatus, paymentType, backerAccount, startAddTime, endAddTime, startExecuteTime, endExecuteTime, pageNumber, pageSize);
    }

}
