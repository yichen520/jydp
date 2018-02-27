package com.jydp.service.impl.transaction;

import com.iqmkj.utils.BigDecimalUtil;
import com.iqmkj.utils.DateUtil;
import com.iqmkj.utils.NumberUtil;
import com.iqmkj.utils.StringUtil;
import com.jydp.dao.ITransactionMakeOrderDao;
import com.jydp.entity.DO.transaction.TransactionMakeOrderDO;
import com.jydp.entity.VO.TransactionMakeOrderVO;
import com.jydp.service.ITransactionDealRedisService;
import com.jydp.service.ITransactionMakeOrderService;
import com.jydp.service.ITransactionUserDealService;
import config.SystemCommonConfig;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.sql.Timestamp;
import java.util.Date;
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

    /** 用户成交记录 */
    @Autowired
    private ITransactionUserDealService transactionUserDealService;

    /** redis成交记录 */
    @Autowired
    private ITransactionDealRedisService transactionDealRedisService;

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
        return transactionMakeOrderDao.countTransactionMakeOrderForBack(currencyName, executeStatus, paymentType, backerAccount,
                startAddTime, endAddTime, startExecuteTime, endExecuteTime);
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
    public List<TransactionMakeOrderVO> listTransactionMakeOrderForBack(String currencyName, int executeStatus, int paymentType, String backerAccount,
                                                                        Timestamp startAddTime, Timestamp endAddTime,
                                                                        Timestamp startExecuteTime, Timestamp endExecuteTime,
                                                                        int pageNumber, int pageSize){
        return transactionMakeOrderDao.listTransactionMakeOrderForBack(currencyName, executeStatus, paymentType, backerAccount, startAddTime, endAddTime,
                startExecuteTime, endExecuteTime, pageNumber, pageSize);
    }

    /**
     * 批量新增做单记录
     * @param transactionMakeOrderList  做单记录集合
     * @return  操作成功，返回true，操作失败，返回false
     */
    public boolean insertTransactionMakeOrderList(List<TransactionMakeOrderDO> transactionMakeOrderList){
        return transactionMakeOrderDao.insertTransactionMakeOrderList(transactionMakeOrderList);
    }

    /**
     * 修改记录执行状态
     * @param orderNo  记录号
     * @param executeStatus  执行状态,1：待执行,2:执行中,3:执行完成,4:执行失败,5:已撤回
     * @return  操作成功，返回true，操作失败，返回false
     */
    public boolean updateOrderExecuteStatusByOrderNo(String orderNo, int executeStatus){
        return transactionMakeOrderDao.updateOrderExecuteStatusByOrderNo(orderNo, executeStatus);
    }

    /**
     * 执行做单
     * @param orderNo 记录号
     * @return  操作成功，返回true，操作失败，返回false
     */
    @Transactional
    public boolean executeMakeOrder(String orderNo){
        boolean executeBoo = false;

        TransactionMakeOrderDO order = transactionMakeOrderDao.getTransactionMakeOrderByOrderNo(orderNo);
        if (order == null || order.getExecuteStatus() != 1) {
            return false;
        }

        //计算成交单价
        String transactionPriceStr = BigDecimalUtil.div(order.getCurrencyTotalPrice(), order.getCurrencyNumber(), 8);
        if (!StringUtil.isNotNull(transactionPriceStr)) {
            executeBoo = false;
        }
        double transactionPrice = Double.parseDouble(transactionPriceStr);
        if (transactionPrice <= 0) {
            executeBoo = false;
        }

        Timestamp curTime = DateUtil.getCurrentTime();
        executeBoo = transactionMakeOrderDao.updateOrderExecuteStatusByOrderNo(orderNo, 2);
        //添加成交记录
        /*if (executeBoo) {
            String orderDealNo = SystemCommonConfig.TRANSACTION_USER_DEAL
                    + DateUtil.longToTimeStr(curTime.getTime(), DateUtil.dateFormat10)
                    + NumberUtil.createNumberStr(10);

            executeBoo = transactionUserDealService.insertTransactionUserDeal(orderDealNo, orderNo, 0, order.getPaymentType(),
                    order.getCurrencyId(), order.getCurrencyName(), transactionPrice, order.getCurrencyNumber(),
                    order.getCurrencyTotalPrice(), "做单记录", curTime);
        }*/

        //添加redis成交记录
        if (executeBoo) {
            String orderDealNo = SystemCommonConfig.TRANSACTION_MAKE_ORDER_PENDNO
                    + DateUtil.longToTimeStr(curTime.getTime(), DateUtil.dateFormat10)
                    + NumberUtil.createNumberStr(10);

            executeBoo = transactionDealRedisService.insertTransactionDealRedis(orderNo, order.getPaymentType(),
                    order.getCurrencyId(), transactionPrice, order.getCurrencyNumber(), order.getCurrencyTotalPrice(), curTime);
        }
        //修改做单记录执行状态
        if (executeBoo) {
            executeBoo = transactionMakeOrderDao.updateOrderExecuteStatusByOrderNo(orderNo, 3);
        }

        //数据回滚
        if(!executeBoo){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return executeBoo;
    }

    /**
     * 执行多条做单
     * @param orderNoList 记录号集合
     * @return  操作成功，返回true，操作失败，返回false
     */
    @Transactional
    public boolean executeMakeOrderMore(List<String> orderNoList){
        boolean executeBoo = false;

        for (String orderNo: orderNoList) {
            TransactionMakeOrderDO order = transactionMakeOrderDao.getTransactionMakeOrderByOrderNo(orderNo);
            if (order == null || order.getExecuteStatus() != 1) {
                continue;
            }

            //计算成交单价
            String transactionPriceStr = BigDecimalUtil.div(order.getCurrencyTotalPrice(), order.getCurrencyNumber(), 8);
            if (!StringUtil.isNotNull(transactionPriceStr)) {
                executeBoo = false;
            }
            double transactionPrice = Double.parseDouble(transactionPriceStr);
            if (transactionPrice <= 0) {
                executeBoo = false;
            }

            Timestamp curTime = DateUtil.getCurrentTime();
            executeBoo = transactionMakeOrderDao.updateOrderExecuteStatusByOrderNo(orderNo, 2);
            //添加成交记录
            /*if (executeBoo) {
                String orderDealNo = SystemCommonConfig.TRANSACTION_USER_DEAL
                        + DateUtil.longToTimeStr(curTime.getTime(), DateUtil.dateFormat10)
                        + NumberUtil.createNumberStr(10);

                executeBoo = transactionUserDealService.insertTransactionUserDeal(orderDealNo, orderNo, 0, order.getPaymentType(),
                        order.getCurrencyId(), order.getCurrencyName(), transactionPrice, order.getCurrencyNumber(),
                        order.getCurrencyTotalPrice(), "做单记录", curTime);
            }*/

            //添加redis成交记录
            if (executeBoo) {
                String orderDealNo = SystemCommonConfig.TRANSACTION_MAKE_ORDER_PENDNO
                        + DateUtil.longToTimeStr(curTime.getTime(), DateUtil.dateFormat10)
                        + NumberUtil.createNumberStr(10);

                executeBoo = transactionDealRedisService.insertTransactionDealRedis(orderNo, order.getPaymentType(),
                        order.getCurrencyId(), transactionPrice, order.getCurrencyNumber(), order.getCurrencyTotalPrice(), curTime);
            }
            //修改做单记录执行状态
            if (executeBoo) {
                executeBoo = transactionMakeOrderDao.updateOrderExecuteStatusByOrderNo(orderNo, 3);
            }

            //数据回滚
            if(!executeBoo){
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                break;
            }
        }

        return executeBoo;
    }

    /**
     * 批量修改记录号状态
     * @param orderNoList  记录号集合
     * @param executeStatus  执行状态,1：待执行,2:执行中,3:执行完成,4:执行失败,5:已撤回
     * @return  操作成功：true，操作失败：返回false
     */
    public boolean updateMakeOrderExecuteStatusByOrderNoList(List<String> orderNoList, int executeStatus){
        return transactionMakeOrderDao.updateMakeOrderExecuteStatusByOrderNoList(orderNoList, executeStatus);
    }
}
