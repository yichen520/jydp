package com.jydp.service;

import com.jydp.entity.VO.WapUserCurrencyAssetsVO;

import java.util.List;

/**
 * 币种信息
 * Created by xushilong3623 on 2018/4/19.
 */

public interface IWebUserCurrencyNumService {

    /**
     * 查询用户币种资产
     *
     * @param userId 用户id
     * @return 查询成功，返回列表，失败返回null
     */
    List<WapUserCurrencyAssetsVO> listWebUserCurrencyAssets(int userId);
}
