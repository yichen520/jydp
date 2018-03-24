package com.jydp.service.impl.transfer;

import com.jydp.dao.IJydpCoinConfigDao;
import com.jydp.entity.DO.transfer.JydpCoinConfigDO;
import com.jydp.entity.VO.UserCoinConfigVO;
import com.jydp.service.IJydpCoinConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * JYDP币种转出管理
 * @Author: wqq
 */
@Service("IJydpCoinConfigService")
public class JydpCoinConfigServiceImpl implements IJydpCoinConfigService {

    /**  JYDP币种转出管理 */
    @Autowired
    private IJydpCoinConfigDao jydpCoinConfigDao;

    /**
     * 查询用户所有币种,数量及转出管理
     * @param userId 用户id
     * @return 查询成功:返回所有币种信息, 查询失败:返回null
     */
    public List<UserCoinConfigVO> listUserCoinConfigByUserId(int userId) {
        return jydpCoinConfigDao.listUserCoinConfigByUserId(userId);
    }

    /**
     * 查询币种转出管理,根据币种id
     * @param currencyId 币种id
     * @return 查询成功:返回币种转出管理信息, 查询失败:返回null
     */
    public JydpCoinConfigDO getJydpCoinConfigByCurrencyId(int currencyId) {
        return jydpCoinConfigDao.getJydpCoinConfigByCurrencyId(currencyId);
    }
}
