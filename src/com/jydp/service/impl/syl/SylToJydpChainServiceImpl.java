package com.jydp.service.impl.syl;

import com.iqmkj.utils.DateUtil;
import com.iqmkj.utils.NumberUtil;
import com.jydp.dao.ISylToJydpChainDao;
import com.jydp.entity.DO.syl.SylToJydpChainDO;
import com.jydp.entity.DO.user.UserBalanceDO;
import com.jydp.entity.DO.user.UserCurrencyNumDO;
import com.jydp.entity.VO.TransactionCurrencyVO;
import com.jydp.service.*;
import config.SylConfig;
import config.SystemCommonConfig;
import config.UserBalanceConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.sql.Timestamp;

/**
 * SYL转账盛源链记录(SYL-->JYDP)
 *
 * @author sy
 */
@Service("sylToJydpChainService")
public class SylToJydpChainServiceImpl implements ISylToJydpChainService {

    /** SYL转账盛源链记录(SYL-->JYDP)*/
    @Autowired
    private ISylToJydpChainDao sylToJydpChainDao;

    /** 用户币数量管理*/
    @Autowired
    private IUserCurrencyNumService userCurrencyNumService;

    /** 用户账户记录*/
    @Autowired
    private IUserBalanceService userBalanceService;

    /** 交易币种管理*/
    @Autowired
    private ITransactionCurrencyService transactionCurrencyService;

    /** 用户账号 */
    @Autowired
    private IUserService userService;

    /**
     * 新增 SYL转账盛源链记录(SYL-->JYDP)
     * @param walletOrderNo 待新增的 SYL转账盛源链记录(SYL-->JYDP)
     * @return 新增成功：返回true, 新增失败：返回false
     */
    public boolean insertSylToJydpChain(SylToJydpChainDO walletOrderNo){
        return sylToJydpChainDao.insertSylToJydpChain(walletOrderNo);
    }

    /**
     * SYL转账盛源链执行(SYL-->JYDP)
     * @param walletOrderNo syl单号
     * @param userId 用户Id
     * @param userAccount 用户账号
     * @param walletUserAccount 钱包账号
     * @param coin 转账币数量
     * @param coinType 转账币类型
     * @param orderTime 订单时间
     * @param finishTime 完成时间
     * @param currencyId 货币id
     * @param currencyName 货币名称
     * @return 新增成功：返回true, 新增失败：返回false
     */
    @Transactional
    public boolean operationSylToJydpChain(String walletOrderNo,int userId, String userAccount, String walletUserAccount, double coin, String coinType,
                                           Timestamp orderTime, Timestamp finishTime, int currencyId, String currencyName){
        boolean executeSuccess;
        SylToJydpChainDO sylToJydpChain = new SylToJydpChainDO();



        //转账记录添加
        Timestamp date = DateUtil.getCurrentTime();
        String orderNo = SystemCommonConfig.COIN_GIT +
                DateUtil.longToTimeStr(date.getTime(), DateUtil.dateFormat10) +
                NumberUtil.createNumberStr(7);
        sylToJydpChain.setOrderNo(orderNo);
        sylToJydpChain.setWalletOrderNo(walletOrderNo);
        sylToJydpChain.setWalletUserAccount(walletUserAccount);
        sylToJydpChain.setUserId(userId);
        sylToJydpChain.setUserAccount(userAccount);
        sylToJydpChain.setCurrencyNumber(coin);
        sylToJydpChain.setCurrencyId(currencyId);
        sylToJydpChain.setCoinType(coinType);
        sylToJydpChain.setCurrencyName(currencyName);
        sylToJydpChain.setRemark("盛源链app充值");
        sylToJydpChain.setOrderTime(orderTime);
        sylToJydpChain.setFinishTime(finishTime);
        sylToJydpChain.setAddTime(date);
        executeSuccess = insertSylToJydpChain(sylToJydpChain);

        if(!executeSuccess){
            return executeSuccess;
        }

        //添加账户记录
        String userOrderNo = SystemCommonConfig.USER_BALANCE + DateUtil.longToTimeStr(date.getTime(), DateUtil.dateFormat10) + NumberUtil.createNumberStr(10);
        UserBalanceDO userBalance = new UserBalanceDO();

        userBalance.setUserId(userId);
        userBalance.setOrderNo(userOrderNo);
        userBalance.setFrozenNumber(0);
        userBalance.setFromType("盛源链app充值");
        userBalance.setCurrencyName(currencyName);
        userBalance.setCurrencyId(currencyId);
        userBalance.setRemark("充值订单号：" + orderNo);
        userBalance.setBalanceNumber(coin);
        userBalance.setAddTime(date);
        executeSuccess = userBalanceService.insertUserBalance(userBalance);

        if(executeSuccess){
            if(UserBalanceConfig.DOLLAR.equals(coinType)){//用户资金增加
                executeSuccess = userService.updateAddUserAmount(userId, coin, 0);
            } else {//用户币增加
                executeSuccess = userCurrencyNumService.increaseCurrencyNumber(userId, currencyId, coin);
                if (!executeSuccess) {
                    UserCurrencyNumDO userCurrencyNum = new UserCurrencyNumDO();
                    userCurrencyNum.setCurrencyId(currencyId);
                    userCurrencyNum.setUserId(userId);
                    userCurrencyNum.setCurrencyNumber(coin);
                    userCurrencyNum.setCurrencyNumberLock(0);
                    userCurrencyNum.setAddTime(date);
                    executeSuccess = userCurrencyNumService.insertUserCurrencyNum(userCurrencyNum);
                }
            }
        }
        if(executeSuccess){
            //增加用户账户记录
            orderNo = SystemCommonConfig.USER_BALANCE + DateUtil.longToTimeStr(date.getTime(), DateUtil.dateFormat10) + NumberUtil.createNumberStr(10);
            UserBalanceDO userBalanceDO = new UserBalanceDO();
            userBalanceDO.setOrderNo(orderNo);  //记录号：业务类型（2）+日期（6）+随机位（10）
            userBalanceDO.setUserId(userId);
            userBalanceDO.setFromType("电子钱包转入");
            userBalanceDO.setCurrencyId(currencyId);  //币种Id,id=999
            userBalanceDO.setCurrencyName(currencyName);  //货币名称
            userBalanceDO.setBalanceNumber(coin);  //交易数量
            userBalanceDO.setFrozenNumber(0);  //冻结数量
            userBalanceDO.setRemark("电子钱包转入订单号：" + walletOrderNo);
            userBalanceDO.setAddTime(date);
            //添加用户账户记录
            executeSuccess = userBalanceService.insertUserBalance(userBalanceDO);
        }

        // 数据回滚
        if (!executeSuccess) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return executeSuccess;
    }

    /**
     * 根据订单号查询订单信息
     * @param walletOrderNo 订单号
     * @param currencyId 币种
     * @return 查询成功：返回订单信息, 查询失败或者没有相关信息：返回null
     */
    public SylToJydpChainDO getSylToJydpChainBysylRecordNo(String walletOrderNo, int currencyId){
        return sylToJydpChainDao.getSylToJydpChainBysylRecordNo(walletOrderNo, currencyId);
    }
}
