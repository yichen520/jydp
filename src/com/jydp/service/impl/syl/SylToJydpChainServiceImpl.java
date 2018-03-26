package com.jydp.service.impl.syl;

import com.iqmkj.utils.DateUtil;
import com.iqmkj.utils.NumberUtil;
import com.jydp.dao.ISylToJydpChainDao;
import com.jydp.entity.DO.syl.SylToJydpChainDO;
import com.jydp.entity.DO.user.UserBalanceDO;
import com.jydp.entity.VO.TransactionCurrencyVO;
import com.jydp.service.ISylToJydpChainService;
import com.jydp.service.ITransactionCurrencyService;
import com.jydp.service.IUserBalanceService;
import com.jydp.service.IUserCurrencyNumService;
import config.SystemCommonConfig;
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

    /**
     * 新增 SYL转账盛源链记录(SYL-->JYDP)
     * @param sylToJydpChain 待新增的 SYL转账盛源链记录(SYL-->JYDP)
     * @return 新增成功：返回true, 新增失败：返回false
     */
    public boolean insertSylToJydpChain(SylToJydpChainDO sylToJydpChain){
        return sylToJydpChainDao.insertSylToJydpChain(sylToJydpChain);
    }

    /**
     * SYL转账盛源链执行(SYL-->JYDP)
     * @param orderNo syl单号
     * @param userId 用户Id
     * @param userAccount 用户账号
     * @param coin 转账币数量
     * @param coinType 转账币类型
     * @return 新增成功：返回true, 新增失败：返回false
     */
    @Transactional
    public boolean operationSylToJydpChain(String orderNo,int userId, String userAccount, double coin, String coinType){
        boolean executeSuccess = false;
        SylToJydpChainDO sylToJydpChain = new SylToJydpChainDO();

        TransactionCurrencyVO transactionCurrency = transactionCurrencyService.getTransactionCurrencyByCurrencyShortName(coinType);//查询币种信息
        Timestamp date = DateUtil.getCurrentTime();
        if(transactionCurrency != null){
            return false;
        }

        //转账记录添加
        sylToJydpChain.setSylRecordNo(orderNo);
        sylToJydpChain.setUserId(userId);
        sylToJydpChain.setShengyuanCoin(coin);
        sylToJydpChain.setCoinType(coinType);
        sylToJydpChain.setUserAccount(userAccount);
        sylToJydpChain.setHandleMark("盛源链app充值");
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
        userBalance.setCurrencyName(transactionCurrency.getCurrencyName());
        userBalance.setCurrencyId(transactionCurrency.getCurrencyId());
        userBalance.setRemark("充值订单号：" + orderNo);
        userBalance.setBalanceNumber(coin);
        userBalance.setAddTime(date);
        executeSuccess = userBalanceService.insertUserBalance(userBalance);

        //用户币增加
        if(executeSuccess){
            executeSuccess = userCurrencyNumService.increaseCurrencyNumber(userId, transactionCurrency.getCurrencyId(), coin);
        }

        // 数据回滚
        if (!executeSuccess) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return executeSuccess;
    }
}
