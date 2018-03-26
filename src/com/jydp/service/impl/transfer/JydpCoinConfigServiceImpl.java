package com.jydp.service.impl.transfer;

import com.iqmkj.utils.DateUtil;
import com.iqmkj.utils.IpAddressUtil;
import com.iqmkj.utils.NumberUtil;
import com.jydp.dao.IJydpCoinConfigDao;
import com.jydp.entity.DO.transfer.JydpCoinConfigDO;
import com.jydp.entity.VO.TransactionCurrencyVO;
import com.jydp.entity.VO.UserCoinConfigVO;
import com.jydp.service.IJydpCoinConfigService;
import com.jydp.service.ITransactionCurrencyService;
import config.SystemCommonConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;


/**
 * JYDP币种转出管理
 * @Author: wqq
 */
@Service("jydpCoinConfigService")
public class JydpCoinConfigServiceImpl implements IJydpCoinConfigService {

    /**  JYDP币种转出管理 */
    @Autowired
    private IJydpCoinConfigDao jydpCoinConfigDao;

    /** 交易币种 */
    @Autowired
    ITransactionCurrencyService transactionCurrencyService;

    /**
     * 根据筛选条件获取JYDP币种转出管理
     * @param backerAccount  后台管理员帐号
     * @param startAddTime  开始时间
     * @param endAddTime  结束时间
     * @param currencyName  币种名称
     * @return  操作成功：返回记录集合，操作失败：返回null
     */
    public List<JydpCoinConfigDO> getJydpCoinConfigServiceList(Timestamp startAddTime, Timestamp endAddTime, String backerAccount, String currencyName){
        return jydpCoinConfigDao.getJydpCoinConfigServiceList(startAddTime, endAddTime, backerAccount, currencyName);
    }

    /**
     * 新增JYDP币种转出管理
     * @param jydpCoinConfigDO  JYDP币种转出管理实体
     * @return  操作成功：ture，操作失败：返回false
     */
    public boolean insertJydpCoinConfig(JydpCoinConfigDO jydpCoinConfigDO){
        return jydpCoinConfigDao.insertJydpCoinConfig(jydpCoinConfigDO);
    }

    /**
     * 根据记录号获得JYDP币种转出管理记录
     * @param recordNo  币种转出管理记录号
     * @return  操作成功：返回记录，操作失败：返回null
     */
    public JydpCoinConfigDO getJydpCoinConfig(String recordNo){
        return jydpCoinConfigDao.getJydpCoinConfig(recordNo);
    }

    /**
     * 根据记录号删除JYDP币种转出管理记录
     * @param recordNo  币种转出管理记录号
     * @return  操作成功：true，操作失败：false
     */
    public boolean deleteJydpCoinConfig(String recordNo){
        return jydpCoinConfigDao.deleteJydpCoinConfig(recordNo);
    }

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
