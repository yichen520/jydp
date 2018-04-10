package com.jydp.dao;

import com.jydp.entity.DO.otc.OtcTransactionPendOrderDO;
import com.jydp.entity.VO.OtcTransactionPendOrderVO;

import java.sql.Timestamp;
import java.util.List;

/**
 * 用户标识经销商相关操作
 *
 * @author lgx
 */
public interface IOtcTransactionPendOrderDao {

    /**
     * 新增 场外交易挂单记录
     * @param otcTransactionPendOrderDO 待新增的 场外交易挂单记录
     * @return 新增成功：返回true, 新增失败：返回false
     */
    boolean insertOtcTransactionPendOrder(OtcTransactionPendOrderDO otcTransactionPendOrderDO);

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
    List<OtcTransactionPendOrderVO> getOtcTransactionPendOrderlist(int currencyId, int orderType, String area, int pageNumber, int pageSize);

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
