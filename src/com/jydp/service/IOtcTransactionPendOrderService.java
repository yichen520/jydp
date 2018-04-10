package com.jydp.service;

import com.jydp.entity.BO.JsonObjectBO;
import com.jydp.entity.DO.otc.OtcTransactionPendOrderDO;
import com.jydp.entity.DTO.OtcTransactionPendOrderDTO;

import java.sql.Timestamp;
import java.util.List;

/**
 * 场外交易挂单记录
 * @author yk
 */
public interface IOtcTransactionPendOrderService {
    /**
     * 新增挂单记录
     * @param otcOrderVO 挂单数据
     * @return 操作成功：返回true，操作失败：返回false
     */
    JsonObjectBO insertPendOrder(OtcTransactionPendOrderDTO otcOrderVO);

    /**
     * 根据记录号查询挂单记录信息
     * @param orderNo 记录号
     * @return 查询成功：返回记录信息, 查询失败或者没有相应记录：返回null
     */
    OtcTransactionPendOrderDO getOtcTransactionPendOrderByOrderNo(String orderNo);

    /**
     * 按条件查询全部场外交易挂单总数
     * @param currencyId 币种Id
     * @param orderType 挂单类型:1：出售，2：回购
     * @param area 地区
     * @return 查询成功：返回记录总条数，查询失败：返回0
     */
    int countOtcTransactionPendOrder(int currencyId, int orderType, String area);

    /**
     * 查询全部场外交易挂单列表
     * @param currencyId 币种Id
     * @param orderType 挂单类型:1：出售，2：回购
     * @param area 地区
     * @param pageNumber 当前页数
     * @param pageSize 每页条数
     * @return 查询成功：返回记录信息，查询失败：返回null
     */
    List<OtcTransactionPendOrderDO> getOtcTransactionPendOrderlist(int currencyId, int orderType, String area, int pageNumber,int pageSize);

    /**
     * 根据用户id查询场外可用交易挂单列表
     * @param userId 用户id
     * @return 查询成功：返回记录信息，查询失败：返回null
     */
    List<OtcTransactionPendOrderDO> getOtcTransactionPendOrderByUserId(int userId);

    /**
     * 根据订单号删除该用户订单
     * @param userId 用户id
     * @param otcPendingOrderNo 订单id
     * @param updateTime 更新时间
     * @return 新增成功：返回true, 新增失败：返回false
     */
    boolean deleteOtcTransactionPendOrderByOtcPendingOrderNo(int userId, String otcPendingOrderNo, Timestamp updateTime);

}
