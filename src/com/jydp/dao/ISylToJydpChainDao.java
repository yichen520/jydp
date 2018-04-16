package com.jydp.dao;

import com.jydp.entity.DO.syl.SylToJydpChainDO;
import com.jydp.entity.VO.UserRechargeCoinRecordVO;

import java.util.List;

import java.sql.Timestamp;
import java.util.List;

/**
 * SYL转账盛源链记录(SYL-->JYDP)
 *
 * @author sy
 */
public interface ISylToJydpChainDao {

    /**
     * 新增 SYL转账盛源链记录(SYL-->JYDP)
     * @param walletOrderNo 待新增的 SYL转账盛源链记录(SYL-->JYDP)
     * @return 新增成功：返回true, 新增失败：返回false
     */
    boolean insertSylToJydpChain(SylToJydpChainDO walletOrderNo);

    /**
     * 根据订单号查询订单信息
     * @param walletOrderNo 订单号
     * @param currencyId 币种
     * @return 查询成功：返回订单信息, 查询失败或者没有相关信息：返回null
     */
    SylToJydpChainDO getSylToJydpChainBysylRecordNo(String walletOrderNo, int currencyId);

    /**
     * 查询用户充币成功记录总数
     *
     * @param userId 用户id
     * @return 查询成功：返回用户账户错误总数，查询失败：返回0
     */
    int countUserRechargeCoinRecordForWap(int userId);

    /**
     * 查询用户充币成功记录列表信息
     *
     * @param userId     用户id
     * @param pageNumber 当前页数
     * @param pageSize   每页条数
     * @return 查询成功：返回用户账户错误总数，查询失败：返回0
     */
    List<UserRechargeCoinRecordVO> listUserRechargeCoinRecordForWap(int userId, int pageNumber, int pageSize);

    /**
     * 查询用户充币成功记录总数(后台)
     * @param userAccount 用户账号,没有填null
     * @param orderNo 订单号,没有填null
     * @param walletOrderNo 钱包订单号,没有填null
     * @param currencyId 币种Id,查询全部填0
     * @param startTime 订单起始时间
     * @param endTime 订单结束时间
     * @return 查询成功:返回用户充币成功记录总数, 查询失败:返回0
     */
    int countSylToJydpChainForBack(String userAccount, String orderNo, String walletOrderNo, int currencyId, Timestamp startTime, Timestamp endTime);

    /**
     * 查询用户充币成功记录(后台)
     * @param userAccount 用户账号,没有填null
     * @param orderNo 订单号,没有填null
     * @param walletOrderNo 钱包订单号,没有填null
     * @param currencyId 币种Id,查询全部填0
     * @param startTime 订单起始时间
     * @param endTime 订单结束时间
     * @param pageNumber 当前页
     * @param pageSize 页面大小
     * @return 操作成功:返回用户充币成功记录集合, 操作失败:返回null
     */
    List<SylToJydpChainDO> listSylToJydpChainForBack(String userAccount, String orderNo, String walletOrderNo, int currencyId, Timestamp startTime, Timestamp endTime, int pageNumber, int pageSize);
}
