package com.jydp.service.impl.transaction;

import com.iqmkj.utils.BigDecimalUtil;
import com.iqmkj.utils.DateUtil;
import com.iqmkj.utils.NumberUtil;
import com.jydp.dao.ITransactionMakeOrderDao;
import com.jydp.entity.DO.transaction.TransactionDealRedisDO;
import com.jydp.entity.DO.transaction.TransactionMakeOrderDO;
import com.jydp.service.ITransactionDealRedisService;
import com.jydp.service.ITransactionMakeOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.sql.Timestamp;
import java.util.*;

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

    /** redis成交记录 */
    @Autowired
    private ITransactionDealRedisService transactionDealRedisService;

    /**
     * 新增做单记录
     * @param orderNo 记录号,业务类型（2）+日期（6）+随机位（10）
     * @param currencyId 币种Id
     * @param currencyName 货币名称
     * @param currencyNumber 成交数量
     * @param currencyPrice 成交价格
     * @param backerAccount 后台管理员帐号
     * @param ipAddress 操作时的ip地址
     * @param executeStatus 执行状态,1：待执行,2:执行中,3:执行完成,4:执行失败
     * @param remark 备注
     * @param executeTime 执行时间
     * @param addTime 添加时间
     * @return  操作成功：返回true，操作失败：返回false
     */
    public boolean insertMakeOrder(String orderNo, int currencyId, String currencyName, double currencyNumber,
                            double currencyPrice, String backerAccount, String ipAddress, int executeStatus,
                            String remark, Timestamp executeTime, Timestamp addTime){
        TransactionMakeOrderDO transactionMakeOrderDO = new TransactionMakeOrderDO();
        transactionMakeOrderDO.setOrderNo(orderNo);
        transactionMakeOrderDO.setCurrencyId(currencyId);
        transactionMakeOrderDO.setCurrencyName(currencyName);
        transactionMakeOrderDO.setCurrencyNumber(currencyNumber);
        transactionMakeOrderDO.setCurrencyPrice(currencyPrice);
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
     * @param orderNo 批次号
     * @param currencyName 货币名称(币种)
     * @param executeStatus 执行状态,1：待执行,2:执行中,3:执行完成,4:执行失败
     * @param startExecuteTime 起始执行时间
     * @param endExecuteTime 结束执行时间
     * @return 操作成功：返回做单记录集合，操作失败：返回null
     */
    public int countTransactionMakeOrderForBack(String orderNo, String currencyName, int executeStatus, Timestamp startExecuteTime, Timestamp endExecuteTime){
        return transactionMakeOrderDao.countTransactionMakeOrderForBack(orderNo, currencyName, executeStatus, startExecuteTime, endExecuteTime);
    }

    /**
     * 查询做单记录集合(后台)
     * @param orderNo 批次号
     * @param currencyName 货币名称(币种)
     * @param executeStatus 执行状态,1：待执行,2:执行中,3:执行完成,4:执行失败
     * @param startExecuteTime 起始执行时间
     * @param endExecuteTime 结束执行时间
     * @param pageNumber 起始页数
     * @param pageSize 每页条数
     * @return 操作成功：返回做单记录集合，操作失败：返回null
     */
    public List<TransactionMakeOrderDO> listTransactionMakeOrderForBack(String orderNo, String currencyName, int executeStatus,
                                                                 Timestamp startExecuteTime, Timestamp endExecuteTime, int pageNumber, int pageSize){
        return transactionMakeOrderDao.listTransactionMakeOrderForBack(orderNo, currencyName, executeStatus, startExecuteTime, endExecuteTime, pageNumber, pageSize);
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
     * @param executeStatus  执行状态,1：待执行,2:执行中,3:执行完成,4:执行失败
     * @param olExecuteStatus  执行状态,1：待执行,2:执行中,3:执行完成,4:执行失败
     * @return  操作成功，返回true，操作失败，返回false
     */
    public boolean updateOrderExecuteStatusByOrderNo(String orderNo, int executeStatus, int olExecuteStatus){
        return transactionMakeOrderDao.updateOrderExecuteStatusByOrderNo(orderNo, executeStatus, olExecuteStatus);
    }


    /**
     * 执行多条做单
     * @param orderNoList 记录号集合
     * @return  操作成功，返回true，操作失败，返回false
     */
    @Transactional
    public boolean executeMakeOrderMore(List<String> orderNoList){
        boolean executeBoo = false;

        List<TransactionMakeOrderDO> resultList = transactionMakeOrderDao.listTransactionMakeOrderByOrderNoList(orderNoList);
        if (resultList == null || resultList.isEmpty()) {
            return false;
        }

        //批量修改为执行中
        executeBoo = transactionMakeOrderDao.updateMakeOrderExecuteStatusByOrderNoList(orderNoList, 2, 1);
        List<TransactionDealRedisDO> transactionDealRedisDOS = new ArrayList<TransactionDealRedisDO>();

        for (TransactionMakeOrderDO order : resultList) {
            //得到随机条数
            int num = (int) (Math.random() * 15) + 5;
            //总数量
            double currencyNumber = order.getCurrencyNumber();
            //子数量集合
            List<Double> numList = new ArrayList<Double>();
            //数量，时间集合
            Map<Double, Long> doubleTimestampHashMap = new HashMap<Double, Long>();

            List<Double> nList = new ArrayList<Double>();
            int i = 0;
            while (i<num){
                double random = Math.random();
                if (nList.contains(random)){
                    continue;
                }
                nList.add(random);
                i++;
            }

            Collections.sort(nList);
            double currNumForLast = order.getCurrencyNumber();
            for (int a = 0; a < nList.size(); a++) {
                double currNum = 0;
                if (a == 0) {
                    currNum = NumberUtil.doubleFormat(BigDecimalUtil.mul(nList.get(a) - 0, order.getCurrencyNumber()), 4);
                } else if (a == nList.size() - 1){
                    currNum = currNumForLast;
                } else {
                    currNum = NumberUtil.doubleFormat(BigDecimalUtil.mul(nList.get(a+1) - nList.get(a), order.getCurrencyNumber()), 4);
                }
                currNumForLast -= currNum;
                numList.add(currNum);
            }

            int j = 0;
            while (j<num){
                Double number = numList.get(j);
                int random = (int)(Math.random() * 100);
                Long timeL = (random * 5 * 6) * 100L;
                boolean result = doubleTimestampHashMap.containsValue(timeL);
                if (result){
                    continue;
                }
                doubleTimestampHashMap.put(number, timeL);
                j++;
            }

            for (Map.Entry<Double, Long> entry : doubleTimestampHashMap.entrySet()){
                TransactionDealRedisDO deal = new TransactionDealRedisDO();
                deal.setOrderNo(order.getOrderNo());
                deal.setPaymentType((int) Math.round(Math.random() + 1));
                deal.setCurrencyId(order.getCurrencyId());
                deal.setTransactionPrice(order.getCurrencyPrice());
                deal.setCurrencyNumber(entry.getKey());
                deal.setCurrencyTotalPrice(NumberUtil.doubleFormat(BigDecimalUtil.mul(entry.getKey(), order.getCurrencyPrice()), 8));
                deal.setAddTime(DateUtil.longToTimestamp(order.getExecuteTime().getTime() + entry.getValue()));

                transactionDealRedisDOS.add(deal);
            }
        }
        //添加redis成交记录
        if (executeBoo) {
            executeBoo = transactionDealRedisService.insertTransactionDealRedisList(transactionDealRedisDOS);
        }
        //修改做单记录状态
        if (executeBoo) {
            executeBoo = transactionMakeOrderDao.updateMakeOrderExecuteStatusByOrderNoList(orderNoList, 3, 2);
        }

        //数据回滚
        if(!executeBoo){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }

        return executeBoo;
    }

    /**
     * 批量修改记录号状态
     * @param orderNoList  记录号集合
     * @param executeStatus  执行状态,1：待执行,2:执行中,3:执行完成,4:执行失败
     * @param olExecuteStatus  执行状态,1：待执行,2:执行中,3:执行完成,4:执行失败
     * @return  操作成功：true，操作失败：返回false
     */
    public boolean updateMakeOrderExecuteStatusByOrderNoList(List<String> orderNoList, int executeStatus, int olExecuteStatus){
        return transactionMakeOrderDao.updateMakeOrderExecuteStatusByOrderNoList(orderNoList, executeStatus, olExecuteStatus);
    }

    /**
     * 根据记录号删除做单记录
     * @param orderNo 记录号
     * @return  操作成功：返回true，操作失败：返回false
     */
    @Transactional
    public boolean deleteMakeOrderByOrderNo(String orderNo){
        TransactionMakeOrderDO order = transactionMakeOrderDao.getTransactionMakeOrderByOrderNo(orderNo);
        if (order == null) {
            return false;
        }

        boolean resultBoo = transactionMakeOrderDao.deleteMakeOrderByOrderNo(orderNo);
        if (order.getExecuteStatus() > 2 && resultBoo) {
            resultBoo = transactionDealRedisService.deleteDealByOrderNo(orderNo);
        }

        //数据回滚
        if(!resultBoo){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return resultBoo;
    }

    /**
     * 根据执行状态查询做单记录
     * @param executeStatus  执行状态
     * @param size  查询条数
     * @return  操作成功：返回做单记录集合，操作失败：返回null
     */
    public List<TransactionMakeOrderDO> listMakeOrderByExecuteStatus(int executeStatus, int size){
        return transactionMakeOrderDao.listMakeOrderByExecuteStatus(executeStatus, size);
    }
}
