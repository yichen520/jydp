package com.jydp.dao;

import com.jydp.entity.DO.transfer.JydpCoinConfigDO;

import java.sql.Timestamp;
import java.util.List;

import com.jydp.entity.VO.UserCoinConfigVO;


/**
 * JYDP币种转出管理
 * @Author: wqq
 */
public interface IJydpCoinConfigDao {

    /**
     * 根据筛选条件获取JYDP币种转出管理
     * @param backerAccount  后台管理员帐号
     * @param startAddTime  开始时间
     * @param endAddTime  结束时间
     * @param currencyName  币种名称
     * @return  操作成功：返回记录集合，操作失败：返回null
     */
    List<JydpCoinConfigDO> getJydpCoinConfigServiceList(Timestamp startAddTime, Timestamp endAddTime, String backerAccount, String currencyName);

    /**
     * 新增JYDP币种转出管理
     * @param jydpCoinConfigDO  JYDP币种转出管理实体
     * @return  操作成功：ture，操作失败：返回false
     */
    boolean insertJydpCoinConfig(JydpCoinConfigDO jydpCoinConfigDO);

    /**
     * 根据记录号获得JYDP币种转出管理记录
     * @param recordNo  币种转出管理记录号
     * @return  操作成功：返回记录，操作失败：返回null
     */
    JydpCoinConfigDO getJydpCoinConfig(String recordNo);

    /**
     * 根据记录号删除JYDP币种转出管理记录
     * @param recordNo  币种转出管理记录号
     * @return  操作成功：true，操作失败：false
     */
    boolean deleteJydpCoinConfig(String recordNo);

    /**
     * 查询用户所有币种,数量及转出管理
     * @param userId 用户id
     * @return 查询成功:返回所有币种信息, 查询失败:返回null
     */
    List<UserCoinConfigVO> listUserCoinConfigByUserId(int userId);

    /**
     * 查询币种转出管理,根据币种id
     * @param currencyId 币种id
     * @return 查询成功:返回币种转出管理信息, 查询失败:返回null
     */
    JydpCoinConfigDO getJydpCoinConfigByCurrencyId(int currencyId);
}
