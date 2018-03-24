package com.jydp.dao.impl.transfer;

import com.iqmkj.utils.LogUtil;
import com.jydp.dao.IJydpCoinConfigDao;
import com.jydp.entity.DO.transfer.JydpCoinConfigDO;
import com.jydp.entity.VO.UserCoinConfigVO;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * JYDP币种转出管理
 * @Author: wqq
 */
@Repository
public class JydpCoinConfigDaoImpl implements IJydpCoinConfigDao {

    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    /**
     * 查询用户所有币种,数量及转出管理
     * @param userId 用户id
     * @return 查询成功:返回所有币种信息, 查询失败:返回null
     */
    public List<UserCoinConfigVO> listUserCoinConfigByUserId(int userId) {
        List<UserCoinConfigVO> resultList = null;

        try {
            resultList = sqlSessionTemplate.selectList("JydpCoinConfig_listUserCoinConfigByUserId", userId);
        } catch (Exception e) {
            LogUtil.printErrorLog(e);
        }

        return resultList;
    }

    /**
     * 查询币种转出管理,根据币种id
     * @param currencyId 币种id
     * @return 查询成功:返回币种转出管理信息, 查询失败:返回null
     */
    public JydpCoinConfigDO getJydpCoinConfigByCurrencyId(int currencyId) {
        JydpCoinConfigDO result = null;

        try {
            result = sqlSessionTemplate.selectOne("JydpCoinConfig_getJydpCoinConfigByCurrencyId",currencyId );
        } catch (Exception e) {
            LogUtil.printErrorLog(e);
        }

        return result;
    }
}
