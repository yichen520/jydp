package com.jydp.service.impl.user;

import com.iqmkj.utils.DateUtil;
import com.iqmkj.utils.NumberUtil;
import com.jydp.dao.IUserDao;
import com.jydp.entity.BO.JsonObjectBO;
import com.jydp.entity.DO.back.BackerHandleUserRecordBalanceDO;
import com.jydp.entity.DO.transaction.TransactionCurrencyDO;
import com.jydp.entity.DO.user.UserBalanceDO;
import com.jydp.entity.DO.user.UserCurrencyNumDO;
import com.jydp.entity.DO.user.UserDO;
import com.jydp.entity.DTO.UserAmountCheckDTO;
import com.jydp.entity.DTO.UserDTO;
import com.jydp.entity.VO.TransactionCurrencyVO;
import com.jydp.service.IBackerHandleUserRecordBalanceService;
import com.jydp.service.ITransactionCurrencyService;
import com.jydp.service.IUserBalanceService;
import com.jydp.service.IUserCurrencyNumService;
import com.jydp.service.IUserService;
import config.SystemCommonConfig;
import config.UserBalanceConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Description: 用户账户
 * Author: hht
 * Date: 2018-02-07 16:24
 */
@Service("userService")
public class UserServiceImpl implements IUserService {

    /** 用户账户 */
    @Autowired
    private IUserDao userDao;

    /** 用户币数量 */
    @Autowired
    private IUserCurrencyNumService userCurrencyNumService;

    /**  交易币种 */
    @Autowired
    private ITransactionCurrencyService transactionCurrencyService;

    /**  用户账户记录 */
    @Autowired
    private IUserBalanceService userBalanceService;

    /**  后台管理员增减用户余额记录 */
    @Autowired
    private IBackerHandleUserRecordBalanceService backerHandleUserRecordBalanceService;

    /**
     * 新增用户账号
     * @param userDO 用户账号
     * @return 新增成功：返回true，新增失败：返回false
     */
    public boolean insertUser (UserDO userDO) {
        return userDao.insertUser(userDO);
    }

    /**
     * 查询用户账号
     * @param userId 用户Id
     * @return 查询成功：返回用户信息，查询失败或无数据：返回null
     */
    public UserDO getUserByUserId (int userId) {
        return userDao.getUserByUserId(userId);
    }

    /**
     * 查询用户账号总数（后台）
     * @param userAccount 用户账号（可为null）
     * @param phoneAreaCode 手机号区号（可为null）
     * @param phoneNumber 手机号（可为null）
     * @param accountStatus 账号状态，1：启用，2：禁用，查询全部填0
     * @param startTime   开始时间(可为null)
     * @param endTime     结束时间(可为null)
     * @return 查询成功：返回用户账户总数，查询失败：返回0
     */
    public int countUserForBacker (String userAccount, String phoneAreaCode, String phoneNumber, int accountStatus, Timestamp startTime, Timestamp endTime) {
        return userDao.countUserForBacker(userAccount, phoneAreaCode, phoneNumber, accountStatus, startTime, endTime);
    }

    /**
     * 查询用户账号列表（后台）
     * @param userAccount 用户账号（可为null）
     * @param phoneAreaCode 手机号区号（可为null）
     * @param phoneNumber 手机号（可为null）
     * @param accountStatus 账号状态，1：启用，2：禁用，查询全部填0
     * @param startTime   开始时间(可为null)
     * @param endTime     结束时间(可为null)
     * @param pageNumber  当前页数
     * @param pageSize    查询条数
     * @return 查询成功：返回用户账户列表，查询失败：返回null
     */
    public List<UserDO> listUserForBacker (String userAccount, String phoneAreaCode, String phoneNumber, int accountStatus,
                                    Timestamp startTime, Timestamp endTime, int pageNumber, int pageSize) {
        return userDao.listUserForBacker(userAccount, phoneAreaCode, phoneNumber, accountStatus, startTime, endTime, pageNumber, pageSize);
    }

    /**
     * 修改用户账号状态
     * @param userId 用户Id
     * @param accountStatus 用户状态，1：启用，2：禁用，-1：删除
     * @param oldAccountStatus 用户原来的状态，1：启用，2：禁用，-1：删除
     * @return 修改成功：返回true，修改失败：返回false
     */
    public boolean updateUserAccountStatus (int userId, int accountStatus, int oldAccountStatus) {
        return userDao.updateUserAccountStatus(userId, accountStatus, oldAccountStatus);
    }

    /**
     * 忘记密码
     * @param userAccount 用户账户
     * @param password 新密码
     * @return  修改成功：返回true，修改失败：返回false
     */
    @Override
    public boolean forgetPwd(String userAccount, String password) {
        UserDTO user = new UserDTO();
        user.setUserAccount(userAccount);
        user.setPassword(password);
        return userDao.updateUser(user);
    }

    /**
     * 忘记支付密码
     * @param userId 用户id
     * @param payPassword 新密码（密文）
     * @return  修改成功：返回true，修改失败：返回false
     */
    public boolean forgetPayPwd(int userId, String payPassword){

        return userDao.forgetPayPwd(userId, payPassword);
    }

    /**
     * 修改绑定手机号
     * @param userId 用户id
     * @param areaCode  手机区号
     * @param phone  手机号
     * @return  修改成功：返回true，修改失败：返回false
     */
    public boolean updatePhone(int userId, String areaCode, String phone){
        return userDao.updatePhone(userId, areaCode, phone);
    }

    /**
     * 验证用户登录
     * @param userAccount 用户账号
     * @param password 账号密码（密文）
     * @return 验证成功：返回用户信息，验证失败：返回null
     */
    @Override
    public UserDO validateUserLogin(String userAccount, String password) {
        return userDao.validateUserLogin(userAccount,password);
    }

    /**
     * 验证用户支付密码
     * @param userAccount 用户账号
     * @param payPassword 支付密码（密文）
     * @return 验证成功：返回true，验证失败：返回false
     */
    public boolean validateUserPay(String userAccount, String payPassword){
        return userDao.validateUserPay(userAccount,payPassword);
    }

    /**
     * 根据用户账号查询用户信息
     * @param userAccount 用户账号
     * @return 查询成功：返回用户信息；查询失败：返回null
     */
    @Override
    public UserDO getUserByUserAccount(String userAccount) {
        return userDao.getUserByUserAccount(userAccount);
    }

    /**
     * 根据手机号查询用户信息
     * @param phoneNumber 用户手机号
     * @return 查询成功：返回用户信息；查询失败：返回null
     */
    @Override
    public UserDO getUserByPhone(String phoneNumber) {
        return userDao.getUserByPhone(phoneNumber);
    }

    /**
     *  验证用户信息合法性
     * @param userAccount 用户名
     * @param password 密码
     * @param userPhone 用户手机号
     * @return 查询成功：返回验证结果; 查询失败：返回null
     */
    @Override
    public JsonObjectBO validateUserInfo(String userAccount, String password, String userPhone) {
        JsonObjectBO jsonObjectBO = new JsonObjectBO();
        String accountReg ="^[A-Za-z0-9]{6,16}$";
        String passwordReg = "^[A-Za-z0-9]{6,16}$";

        if (!Pattern.matches(accountReg,userAccount)) {
            jsonObjectBO.setCode(2);
            jsonObjectBO.setMessage("账户必须是字母或者数字,长度6~16位");
            return jsonObjectBO;
        }

        if (!Pattern.matches(passwordReg,password)) {
            jsonObjectBO.setCode(2);
            jsonObjectBO.setMessage("密码必须是字母或者数字,长度6~16位");
            return jsonObjectBO;
        }

        jsonObjectBO.setCode(1);
        jsonObjectBO.setMessage("用户信息合法");
        return jsonObjectBO;
    }

    /**
     * 用户注册
     * @param userDO 用户信息
     * @return 操作成功：返回true；操作失败：返回false
     */
    @Override
    @Transactional
    public boolean register(UserDO userDO){
        //用戶信息新增
        boolean result = userDao.insertUser(userDO);

        if (result) {
            //查询出新增用户userId，给用户币数量表添加记录
            userDO = userDao.getUserByUserAccount(userDO.getUserAccount());
            if (userDO == null) {
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return false;
            }
            //查询所有币种
            List<TransactionCurrencyDO> transactionCurrencyDOList = transactionCurrencyService.getTransactionCurrencyListForWeb();

            if (transactionCurrencyDOList != null) {
                List<UserCurrencyNumDO> userCurrencyNumDOList = new ArrayList<UserCurrencyNumDO>();
                for (int i = 0; i < transactionCurrencyDOList.size(); i ++) {
                    //注册时添加用户所有币种默认数量为0的记录
                    UserCurrencyNumDO userCurrencyNum =  new UserCurrencyNumDO();
                    userCurrencyNum.setUserId(userDO.getUserId());
                    userCurrencyNum.setCurrencyId(transactionCurrencyDOList.get(i).getCurrencyId());
                    userCurrencyNum.setAddTime(DateUtil.getCurrentTime());
                    userCurrencyNumDOList.add(userCurrencyNum);
                }
                //新增用户币数量记录
                result = userCurrencyNumService.insertUserCurrencyForWeb(userCurrencyNumDOList);

                if (!result) {
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                }
            }
        }
        return result;
    }

    /**
     * 增加用户余额（后台操作）
     * @param userId 用户Id
     * @param userAccount 用户账号
     * @param balanceNumber 增加的账户金额
     * @param backerId 后台管理员Id
     * @param backerAccount 后台管理员帐号
     * @param remarks 备注
     * @param ipAddress 操作时的ip地址
     * @return 操作成功：返回true，操作失败：返回false
     */
    @Transactional
    public boolean addBalanceNumberForBack(int userId, String userAccount, double balanceNumber,
                                             int backerId, String backerAccount, String remarks, String ipAddress) {
        Timestamp currentTime = DateUtil.getCurrentTime();
        String orderNo = SystemCommonConfig.AMOUNT_BALANCE_USER + DateUtil.longToTimeStr(currentTime.getTime(), DateUtil.dateFormat10) + NumberUtil.createNumberStr(10);
        UserBalanceDO userBalanceDO = new UserBalanceDO();
        userBalanceDO.setOrderNo(orderNo);  //记录号：业务类型（2）+日期（6）+随机位（10）
        userBalanceDO.setUserId(userId);
        userBalanceDO.setFromType("系统操作");
        userBalanceDO.setCurrencyId(UserBalanceConfig.DOLLAR_ID);  //币种Id,美元id=999
        userBalanceDO.setCurrencyName(UserBalanceConfig.DOLLAR);  //货币名称
        userBalanceDO.setBalanceNumber(balanceNumber);  //交易数量
        userBalanceDO.setFrozenNumber(0);  //冻结数量
        userBalanceDO.setRemark(remarks);
        userBalanceDO.setAddTime(currentTime);

        //业务执行状态
        boolean executeSuccess = true;

        //添加用户账户记录
        executeSuccess = userBalanceService.insertUserBalance(userBalanceDO);

        //添加后台操作记录
        if (executeSuccess) {
            BackerHandleUserRecordBalanceDO backerHandleUserBalance = new BackerHandleUserRecordBalanceDO();
            backerHandleUserBalance.setAddTime(currentTime);
            backerHandleUserBalance.setBackerAccount(backerAccount);
            backerHandleUserBalance.setBackerId(backerId);
            backerHandleUserBalance.setIpAddress(ipAddress);
            backerHandleUserBalance.setRemarks(remarks);
            backerHandleUserBalance.setTypeHandle(1);  //操作类型，1：增加，2：减少
            backerHandleUserBalance.setUserAccount(userAccount);
            backerHandleUserBalance.setUserId(userId);
            backerHandleUserBalance.setUserBalance(balanceNumber);  //可用资产

            executeSuccess = backerHandleUserRecordBalanceService.insertBackerHandleUserRecordBalance(backerHandleUserBalance);
        }

        // 增加账户余额
        if (executeSuccess) {
            executeSuccess = updateAddUserAmount(userId, balanceNumber, 0);
        }

        //数据回滚
        if(!executeSuccess){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return executeSuccess;
    }

    /**
     * 减少用户余额（后台操作）
     * @param userId 用户Id
     * @param userAccount 用户账号
     * @param balanceNumber 减少的账户金额
     * @param backerId 后台管理员Id
     * @param backerAccount 后台管理员帐号
     * @param remarks 备注
     * @param ipAddress 操作时的ip地址
     * @return 操作成功：返回true，操作失败：返回false
     */
    @Transactional
    public boolean reduceBalanceNumberForBack(int userId, String userAccount, double balanceNumber,
                                           int backerId, String backerAccount, String remarks, String ipAddress) {
        Timestamp currentTime = DateUtil.getCurrentTime();
        //业务类型（2）+日期（6）+随机位（10）
        String orderNo = SystemCommonConfig.AMOUNT_BALANCE_USER + DateUtil.longToTimeStr(currentTime.getTime(), DateUtil.dateFormat10) + NumberUtil.createNumberStr(10);
        UserBalanceDO userBalanceDO = new UserBalanceDO();
        userBalanceDO.setOrderNo(orderNo);  //记录号：业务类型（2）+日期（6）+随机位（10）
        userBalanceDO.setUserId(userId);
        userBalanceDO.setFromType("系统操作");
        userBalanceDO.setCurrencyId(UserBalanceConfig.DOLLAR_ID);  //币种Id,美元id=999
        userBalanceDO.setCurrencyName(UserBalanceConfig.DOLLAR);  //货币名称
        userBalanceDO.setBalanceNumber(-balanceNumber);  //交易数量
        userBalanceDO.setFrozenNumber(0);  //冻结数量
        userBalanceDO.setRemark(remarks);
        userBalanceDO.setAddTime(currentTime);

        //业务执行状态
        boolean executeSuccess = true;

        //添加用户账户记录
        executeSuccess = userBalanceService.insertUserBalance(userBalanceDO);

        //添加后台操作记录
        if (executeSuccess) {
            BackerHandleUserRecordBalanceDO backerHandleUserBalance = new BackerHandleUserRecordBalanceDO();
            backerHandleUserBalance.setAddTime(currentTime);
            backerHandleUserBalance.setBackerAccount(backerAccount);
            backerHandleUserBalance.setBackerId(backerId);
            backerHandleUserBalance.setIpAddress(ipAddress);
            backerHandleUserBalance.setRemarks(remarks);
            backerHandleUserBalance.setTypeHandle(2);  //操作类型，1：增加，2：减少
            backerHandleUserBalance.setUserAccount(userAccount);
            backerHandleUserBalance.setUserId(userId);
            backerHandleUserBalance.setUserBalance(balanceNumber);  //可用资产

            executeSuccess = backerHandleUserRecordBalanceService.insertBackerHandleUserRecordBalance(backerHandleUserBalance);
        }

        // 减少账户余额
        if (executeSuccess) {
            executeSuccess = updateReduceUserBalance(userId, balanceNumber);
        }

        //数据回滚
        if(!executeSuccess){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return executeSuccess;
    }

    /**
     * 增加用户账户金额
     * @param userId 用户Id
     * @param userBalance 可用资产（增加的值）
     * @param userBalanceLock 锁定资产（增加的值）
     * @return 操作成功：返回true，操作失败：返回false
     */
    public boolean updateAddUserAmount(int userId, double userBalance, double userBalanceLock){
        return userDao.updateAddUserAmount(userId, userBalance, userBalanceLock);
    }

    /**
     * 减少用户的可用资产
     * @param userId 用户Id
     * @param userBalance 可用资产(减少的值)
     * @return 操作成功：返回true，操作失败：返回false
     */
    public boolean updateReduceUserBalance(int userId, double userBalance){
        return userDao.updateReduceUserBalance(userId, userBalance);
    }

    /**
     * 减少用户的锁定资产
     * @param userId 用户Id
     * @param userBalanceLock 锁定资产(减少的值)
     * @return 操作成功：返回true，操作失败：返回false
     */
    public boolean updateReduceUserBalanceLock(int userId, double userBalanceLock){
        return userDao.updateReduceUserBalanceLock(userId, userBalanceLock);
    }

    /**
     * 查询用户账户错误总数（定时器对账操作）
     * @param currencyId 币种id（美元）
     * @param checkAmount 可用资产最大差额（美元）
     * @param checkAmountLock 锁定资产最大差额（美元）
     * @return 查询成功：返回用户账户错误总数，查询失败：返回0
     */
    public int countCheckUserAmountForTimer(int currencyId, double checkAmount, double checkAmountLock) {
        return userDao.countCheckUserAmountForTimer(currencyId, checkAmount, checkAmountLock);
    }

    /**
     * 查询用户账户错误列表信息（定时器对账操作）
     * @param currencyId 币种id（美元）
     * @param checkAmount 可用资产最大差额（美元）
     * @param checkAmountLock 锁定资产最大差额（美元）
     * @param pageNumber 当前页数
     * @param pageSize 每页大小
     * @return 查询成功：返回用户账户错误列表信息，查询失败：返回null
     */
    public List<UserAmountCheckDTO> listCheckUserAmountForTimer(int currencyId, double checkAmount, double checkAmountLock,
                                                         int pageNumber, int pageSize) {
        return userDao.listCheckUserAmountForTimer(currencyId, checkAmount, checkAmountLock, pageNumber, pageSize);
    }


}
