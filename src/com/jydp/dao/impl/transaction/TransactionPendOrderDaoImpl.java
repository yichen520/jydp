package com.jydp.dao.impl.transaction;

import com.iqmkj.utils.LogUtil;
import com.jydp.dao.ITransactionPendOrderDao;
import com.jydp.entity.DO.transaction.TransactionPendOrderDO;
import com.jydp.entity.DTO.TransactionPendOrderDTO;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 挂单记录
 * @author hz
 *
 */
@Repository
public class TransactionPendOrderDaoImpl implements ITransactionPendOrderDao{

    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    /**
     * 新增挂单记录
     * @param transactionPendOrderDO 待新增的挂单记录
     * @return 操作成功：返回true，操作失败：返回false
     */
    public boolean insertPendOrder(TransactionPendOrderDO transactionPendOrderDO){
        int result = 0;

        try {
            result = sqlSessionTemplate.insert("TransactionPendOrder_insertPendOrder", transactionPendOrderDO);
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
     * 修改挂单记录
     * @param transactionPendOrderDO 待修改的挂单记录
     * @return 操作成功：返回true，操作失败：返回false
     */
    public boolean updatePendOrder(TransactionPendOrderDO transactionPendOrderDO){
        int result = 0;

        try {
            result = sqlSessionTemplate.update("TransactionPendOrder_updatePendOrder", transactionPendOrderDO);
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
     * 根据挂单记录号查询挂单记录
     * @param pendingOrderNo 记录号,业务类型（2）+日期（6）+随机位（10）
     * @return 操作成功：返回挂单记录，操作失败：返回null
     */
    public TransactionPendOrderDO getPendOrderByPendingOrderNo(String pendingOrderNo){
        TransactionPendOrderDO transactionPendOrder = null;

        try {
            transactionPendOrder = sqlSessionTemplate.selectOne("TransactionPendOrder_getPendOrderByPendingOrderNo", pendingOrderNo);
        } catch (Exception e) {
            LogUtil.printErrorLog(e);
        }

        return transactionPendOrder;
    }

    /**
     * 根据用户id查询挂单记录个数
     * @param userId 用户Id
     * @return 操作成功：返回挂单记录数量，操作失败：返回0
     */
    public int countPendOrderByUserId(int userId){
        int result = 0;

        try {
            result = sqlSessionTemplate.selectOne("TransactionPendOrder_countPendOrderByUserId", userId);
        } catch (Exception e) {
            LogUtil.printErrorLog(e);
        }

        return result;
    }

    /**
     * 根据用户id分页查询挂单记录
     * @param userId 用户Id
     * @param pageNumber 起始页数
     * @param pageSize 每页条数
     * @return 操作成功：返回挂单记录列表，操作失败：返回null
     */
    public List<TransactionPendOrderDO> listPendOrderByUserId(int userId, int pageNumber, int pageSize){
        List<TransactionPendOrderDO> resultList = new ArrayList<TransactionPendOrderDO>();

        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        map.put("startNumber", pageNumber * pageSize);
        map.put("pageSize", pageSize);

        try {
            resultList = sqlSessionTemplate.selectList("TransactionPendOrder_listPendOrderByUserId", map);
        } catch (Exception e) {
            LogUtil.printErrorLog(e);
        }

        return resultList;
    }

    /**
     * 修改挂单状态
     * @param pendingOrderNo 记录号,业务类型（2）+日期（6）+随机位（10）
     * @param pendingStatus 挂单状态，1：未成交，2：部分成交，3：全部成交，4：部分撤销，5：全部撤销
     * @return 操作成功：返回true，操作失败：返回false
     */
    public boolean updatePendingStatus(String pendingOrderNo, int pendingStatus){
        int result = 0;

        Map<String, Object> map = new HashMap<>();
        map.put("pendingOrderNo", pendingOrderNo);
        map.put("pendingStatus", pendingStatus);

        try {
            result = sqlSessionTemplate.update("TransactionPendOrder_updatePendingStatus", map);
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
     * 查询最近num条挂单记录（价格相同的合并，用于交易中心显示）
     * @param paymentType 交易类型,1：买入，2：卖出
     * @param currencyId 币种Id
     * @param num 需要查询的条数
     * @return 操作成功：返回挂单记录列表，操作失败：返回null
     */
    public List<TransactionPendOrderDTO> listLatestRecords(int paymentType, int currencyId, int num){
        List<TransactionPendOrderDTO> resultList = new ArrayList<>();

        Map<String, Object> map = new HashMap<>();
        map.put("paymentType", paymentType);
        map.put("currencyId", currencyId);
        map.put("num", num);

        try {
            resultList = sqlSessionTemplate.selectList("TransactionPendOrder_listLatestRecords", map);
        } catch (Exception e) {
            LogUtil.printErrorLog(e);
        }

        return resultList;
    }

    /**
     * 查询挂单记录个数（后台）
     * @param userAccount 用户账号
     * @param currencyId 币种Id（查全部为0）
     * @param paymentType 交易类型,1：买入，2：卖出（查全部为0）
     * @param pendingStatus 挂单状态，1：未成交，2：部分成交，3：全部成交，4：部分撤销，5：全部撤销（查全部为0）
     * @param startAddTime 挂单起始时间，没有值填null
     * @param endAddTime 挂单结束时间，没有值填null
     * @param startFinishTime 完成起始时间，没有值填null
     * @param endFinishTime 完成结束时间，没有值填null
     * @return 操作成功：返回挂单记录数量，操作失败：返回0
     */
    public int countPendOrderForBack(String userAccount, int currencyId, int paymentType, int pendingStatus,
                              Timestamp startAddTime, Timestamp endAddTime,
                              Timestamp startFinishTime, Timestamp endFinishTime){
        int result = 0;

        Map<String, Object> map = new HashMap<>();
        map.put("userAccount", userAccount);
        map.put("currencyId", currencyId);
        map.put("paymentType", paymentType);
        map.put("pendingStatus", pendingStatus);
        map.put("startAddTime", startAddTime);
        map.put("endAddTime", endAddTime);
        map.put("startFinishTime", startFinishTime);
        map.put("endFinishTime", endFinishTime);

        try {
            result = sqlSessionTemplate.selectOne("TransactionPendOrder_countPendOrderForBack", map);
        } catch (Exception e) {
            LogUtil.printErrorLog(e);
        }

        return result;
    }

    /**
     * 分页查询挂单记录列表（后台）
     * @param userAccount 用户账号
     * @param currencyId 币种Id（查全部为0）
     * @param paymentType 交易类型,1：买入，2：卖出（查全部为0）
     * @param pendingStatus 挂单状态，1：未成交，2：部分成交，3：全部成交，4：部分撤销，5：全部撤销（查全部为0）
     * @param startAddTime 挂单起始时间，没有值填null
     * @param endAddTime 挂单结束时间，没有值填null
     * @param startFinishTime 完成起始时间，没有值填null
     * @param endFinishTime 完成结束时间，没有值填null
     * @param pageNumber 起始页数
     * @param pageSize 每页条数
     * @return 操作成功：返回挂单记录列表，操作失败：返回null
     */
    public List<TransactionPendOrderDO> listPendOrderForBack(String userAccount, int currencyId, int paymentType,
                                                      int pendingStatus, Timestamp startAddTime, Timestamp endAddTime,
                                                      Timestamp startFinishTime, Timestamp endFinishTime,
                                                      int pageNumber, int pageSize){
        List<TransactionPendOrderDO> resultList = new ArrayList<TransactionPendOrderDO>();

        Map<String, Object> map = new HashMap<>();
        map.put("userAccount", userAccount);
        map.put("currencyId", currencyId);
        map.put("paymentType", paymentType);
        map.put("pendingStatus", pendingStatus);
        map.put("startAddTime", startAddTime);
        map.put("endAddTime", endAddTime);
        map.put("startFinishTime", startFinishTime);
        map.put("endFinishTime", endFinishTime);
        map.put("startNumber", pageNumber * pageSize);
        map.put("pageSize", pageSize);

        try {
            resultList = sqlSessionTemplate.selectList("TransactionPendOrder_listPendOrderForBack", map);
        } catch (Exception e) {
            LogUtil.printErrorLog(e);
        }

        return resultList;
    }

    /**
     * 修改挂单状态为部分撤销（仅撤单用）
     * @param pendingOrderNo 记录号,业务类型（2）+日期（6）+随机位（10）
     * @param revokeNumber 撤销数量
     * @param endTime 撤销时间
     * @return 操作成功：返回true，操作失败：返回false
     */
    public boolean updatePartRevoke(String pendingOrderNo, double revokeNumber, Timestamp endTime){
        int result = 0;

        Map<String, Object> map = new HashMap<>();
        map.put("pendingOrderNo", pendingOrderNo);
        map.put("revokeNumber", revokeNumber);
        map.put("endTime", endTime);

        try {
            result = sqlSessionTemplate.update("TransactionPendOrder_updatePartRevoke", map);
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
     * 修改挂单状态为全部撤销（仅撤单用）
     * @param pendingOrderNo 记录号,业务类型（2）+日期（6）+随机位（10）
     * @param revokeNumber 撤销数量
     * @param endTime 撤销时间
     * @return 操作成功：返回true，操作失败：返回false
     */
    public boolean updateAllRevoke(String pendingOrderNo, double revokeNumber, Timestamp endTime){
        int result = 0;

        Map<String, Object> map = new HashMap<>();
        map.put("pendingOrderNo", pendingOrderNo);
        map.put("revokeNumber", revokeNumber);
        map.put("endTime", endTime);

        try {
            result = sqlSessionTemplate.update("TransactionPendOrder_updateAllRevoke", map);
        } catch (Exception e) {
            LogUtil.printErrorLog(e);
        }

        if (result > 0) {
            return true;
        } else {
            return false;
        }
    }

}
