package com.jydp.service.impl.transfer;

import com.iqmkj.utils.DateUtil;
import com.iqmkj.utils.IpAddressUtil;
import com.iqmkj.utils.NumberUtil;
import com.jydp.dao.IJydpCoinConfigDao;
import com.jydp.entity.DO.transfer.JydpCoinConfigDO;
import com.jydp.entity.DO.user.UserCurrencyNumDO;
import com.jydp.entity.DO.user.UserDO;
import com.jydp.entity.VO.TransactionCurrencyVO;
import com.jydp.entity.VO.UserCoinConfigVO;
import com.jydp.service.IJydpCoinConfigService;
import com.jydp.service.ITransactionCurrencyService;
import com.jydp.service.IUserCurrencyNumService;
import com.jydp.service.IUserService;
import config.SystemCommonConfig;
import config.UserBalanceConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


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
    private ITransactionCurrencyService transactionCurrencyService;

    /** 用户币数量 */
    @Autowired
    private IUserCurrencyNumService userCurrencyNumService;

    /** 用户账号 */
    @Autowired
    private IUserService userService;
    /**
     * 根据筛选条件获取JYDP币种转出管理
     * @param backerAccount  后台管理员帐号
     * @param startAddTime  开始时间
     * @param endAddTime  结束时间
     * @param currencyId  币种id,没有填0
     * @return  操作成功：返回记录集合，操作失败：返回null
     */
    public List<JydpCoinConfigDO> getJydpCoinConfigServiceList(Timestamp startAddTime, Timestamp endAddTime, String backerAccount, int currencyId){
        return jydpCoinConfigDao.getJydpCoinConfigServiceList(startAddTime, endAddTime, backerAccount, currencyId);
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
        List<UserCoinConfigVO> userCoinConfigList = new ArrayList<UserCoinConfigVO>();
        Map<Integer, UserCurrencyNumDO> map = new HashMap<Integer, UserCurrencyNumDO>();

        //查询用户币数量,(上线中,停牌的状态)
        List<UserCurrencyNumDO> userCurrencyNumList = userCurrencyNumService.listUserCurrencyNumByUserId(userId);
        if (userCurrencyNumList != null && userCurrencyNumList.size() > 0) {
            for (UserCurrencyNumDO userCurrencyNum : userCurrencyNumList) {
                map.put(userCurrencyNum.getCurrencyId(), userCurrencyNum);
            }
        }

        //查询币种转出管理
        Timestamp currentTime = DateUtil.getCurrentTime();
        List<JydpCoinConfigDO> jydpCoinConfigList = jydpCoinConfigDao.listUserCoinConfig(currentTime);
        if (jydpCoinConfigList == null || jydpCoinConfigList.size() <= 0) {
            return null;
        }

        for (JydpCoinConfigDO jydpCoinConfig : jydpCoinConfigList) {
            if (map.containsKey(jydpCoinConfig.getCurrencyId())) {
                UserCoinConfigVO userCoinConfig = new UserCoinConfigVO();
                userCoinConfig.setCurrencyId(jydpCoinConfig.getCurrencyId());
                userCoinConfig.setCurrencyName(jydpCoinConfig.getCurrencyName());
                userCoinConfig.setFreeCurrencyNumber(jydpCoinConfig.getFreeCurrencyNumber());
                userCoinConfig.setMinCurrencyNumber(jydpCoinConfig.getMinCurrencyNumber());
                userCoinConfig.setCurrencyNumber(map.get(jydpCoinConfig.getCurrencyId()).getCurrencyNumber());
                userCoinConfigList.add(userCoinConfig);
            }

            //币种为XT
            if (jydpCoinConfig.getCurrencyId() == UserBalanceConfig.DOLLAR_ID) {
                UserDO user = userService.getUserByUserId(userId);
                if (user != null) {
                    UserCoinConfigVO userCoinConfig = new UserCoinConfigVO();
                    userCoinConfig.setCurrencyId(jydpCoinConfig.getCurrencyId());
                    userCoinConfig.setCurrencyName(jydpCoinConfig.getCurrencyName());
                    userCoinConfig.setFreeCurrencyNumber(jydpCoinConfig.getFreeCurrencyNumber());
                    userCoinConfig.setMinCurrencyNumber(jydpCoinConfig.getMinCurrencyNumber());
                    userCoinConfig.setCurrencyNumber( user.getUserBalance());
                    userCoinConfigList.add(userCoinConfig);
                }
            }
        }

        return userCoinConfigList;
    }

    /**
     * 查询币种转出管理
     * @param currentTime 当前时间
     * @return 查询成功:返回币种转出管理, 查询失败:返回null;
     */
    public List<JydpCoinConfigDO> listUserCoinConfig(Timestamp currentTime) {
        return jydpCoinConfigDao.listUserCoinConfig(currentTime);
    }

    /**
     * 查询币种转出管理,根据币种id
     * @param currencyId 币种id
     * @return 查询成功:返回币种转出管理信息, 查询失败:返回null
     */
    public JydpCoinConfigDO getJydpCoinConfigByCurrencyId(int currencyId) {
        return jydpCoinConfigDao.getJydpCoinConfigByCurrencyId(currencyId);
    }

    /**
     * 查询币种管理信息,根据currencyId
     * @param userId 用户id
     * @param currencyId 币种id
     * @return 查询成功:返回币种管理信息, 查询失败:返回null
     */
    public UserCoinConfigVO getUserCoinConfigByCurrencyId(int userId, int currencyId) {
        UserCoinConfigVO userCoinConfig = new UserCoinConfigVO();
        JydpCoinConfigDO jydpCoinConfig = jydpCoinConfigDao.getJydpCoinConfigByCurrencyId(currencyId);
        if (jydpCoinConfig == null) {
            return null;
        }

        double currencyNumber = 0;
        if (currencyId == UserBalanceConfig.DOLLAR_ID) {
            UserDO user = userService.getUserByUserId(userId);
            if (user == null) {
                return null;
            }
            currencyNumber = user.getUserBalance();
        } else {
            UserCurrencyNumDO userCurrencyNum = userCurrencyNumService.getUserCurrencyNumByUserIdAndCurrencyId(userId, currencyId);
            if (userCurrencyNum == null) {
                return null;
            }
            currencyNumber = userCurrencyNum.getCurrencyNumber();
        }

        userCoinConfig.setUserId(userId);
        userCoinConfig.setCurrencyId(currencyId);
        userCoinConfig.setCurrencyName(jydpCoinConfig.getCurrencyName());
        userCoinConfig.setMinCurrencyNumber(jydpCoinConfig.getMinCurrencyNumber());
        userCoinConfig.setFreeCurrencyNumber(jydpCoinConfig.getFreeCurrencyNumber());
        userCoinConfig.setCurrencyNumber(currencyNumber);

        return userCoinConfig;
 }
}
