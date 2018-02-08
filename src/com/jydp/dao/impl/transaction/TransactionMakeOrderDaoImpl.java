package com.jydp.dao.impl.transaction;

import com.iqmkj.utils.LogUtil;
import com.jydp.dao.ITransactionMakeOrderDao;
import com.jydp.entity.DO.transaction.TransactionMakeOrderDO;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 做单记录
 * @author fk
 *
 */
@Repository
public class TransactionMakeOrderDaoImpl implements ITransactionMakeOrderDao{

    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    /**
     * 新增做单记录
     * @param transactionMakeOrder 做单记录
     * @return  操作成功：返回true，操作失败：返回false
     */
    public boolean insertMakeOrder(TransactionMakeOrderDO transactionMakeOrder){
        int result = 0;

        try {
            result = sqlSessionTemplate.insert("TransactionMakeOrder_insertMakeOrder", transactionMakeOrder);
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
     * 根据记录号查询做单记录
     * @param orderNo  记录号
     * @return  操作成功：返回做单记录，操作失败：返回null
     */
    public TransactionMakeOrderDO getTransactionMakeOrderByOrderNo(String orderNo){
        TransactionMakeOrderDO result = null;

        try {
            result = sqlSessionTemplate.selectOne("TransactionMakeOrder_getTransactionMakeOrderByOrderNo", orderNo);
        } catch (Exception e) {
            LogUtil.printErrorLog(e);
        }

        return result;
    }

    /**
     * 查询做单记录个数(后台)
     * @param currencyName 货币名称(币种)
     * @param executeStatus 执行状态,1：待执行,2:执行中,3:执行完成,4:执行失败,5:已撤回
     * @param backerAccount 后台管理员帐号
     * @param startAddTime 起始生成时间
     * @param endAddTime 结束生成时间
     * @param startExecuteTime 起始执行时间
     * @param endExecuteTime 结束执行时间
     * @return 操作成功：返回做单记录集合，操作失败：返回null
     */
    public int countTransactionMakeOrderForBack(String currencyName, int executeStatus, String backerAccount,
                                         Timestamp startAddTime, Timestamp endAddTime,
                                         Timestamp startExecuteTime, Timestamp endExecuteTime){
        int result = 0;

        Map<String, Object> map = new HashMap<>();
        map.put("currencyName", currencyName);
        map.put("executeStatus", executeStatus);
        map.put("backerAccount", backerAccount);
        map.put("startAddTime", startAddTime);
        map.put("endAddTime", endAddTime);
        map.put("startExecuteTime", startExecuteTime);
        map.put("endExecuteTime", endExecuteTime);

        try {
            result = sqlSessionTemplate.insert("TransactionMakeOrder_countTransactionMakeOrderForBack", map);
        } catch (Exception e) {
            LogUtil.printErrorLog(e);
        }

        return result;
    }

    /**
     * 查询做单记录集合(后台)
     * @param currencyName 货币名称(币种)
     * @param executeStatus 执行状态,1：待执行,2:执行中,3:执行完成,4:执行失败,5:已撤回
     * @param backerAccount 后台管理员帐号
     * @param startAddTime 起始生成时间
     * @param endAddTime 结束生成时间
     * @param startExecuteTime 起始执行时间
     * @param endExecuteTime 结束执行时间
     * @param pageNumber 起始页数
     * @param pageSize 每页条数
     * @return 操作成功：返回做单记录集合，操作失败：返回null
     */
    public List<TransactionMakeOrderDO> listTransactionMakeOrderForBack(String currencyName, int executeStatus, String backerAccount,
                                                                 Timestamp startAddTime, Timestamp endAddTime,
                                                                 Timestamp startExecuteTime, Timestamp endExecuteTime,
                                                                 int pageNumber, int pageSize){
        List<TransactionMakeOrderDO> resultList = new ArrayList<TransactionMakeOrderDO>();

        Map<String, Object> map = new HashMap<>();
        map.put("currencyName", currencyName);
        map.put("executeStatus", executeStatus);
        map.put("backerAccount", backerAccount);
        map.put("startAddTime", startAddTime);
        map.put("endAddTime", endAddTime);
        map.put("startExecuteTime", startExecuteTime);
        map.put("endExecuteTime", endExecuteTime);
        map.put("startNumber", pageNumber * pageSize);
        map.put("pageSize", pageSize);

        try {
            resultList = sqlSessionTemplate.selectList("TransactionMakeOrder_listTransactionMakeOrderForBack", map);
        } catch (Exception e) {
            LogUtil.printErrorLog(e);
        }

        return resultList;
    }

}
