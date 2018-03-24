package com.jydp.dao;

import com.jydp.entity.DO.transfer.JydpCoinConfigDO;
import com.jydp.entity.VO.UserCoinConfigVO;

import java.util.List;

/**
 * JYDP币种转出管理
 * @Author: wqq
 */
public interface IJydpCoinConfigDao {

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
