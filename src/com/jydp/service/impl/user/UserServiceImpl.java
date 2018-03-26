package com.jydp.service.impl.user;

import com.iqmkj.utils.BigDecimalUtil;
import com.iqmkj.utils.DateUtil;
import com.iqmkj.utils.NumberUtil;
import com.jydp.dao.IUserDao;
import com.jydp.entity.BO.JsonObjectBO;
import com.jydp.entity.DO.back.BackerHandleUserBalanceFreezeMoneyDO;
import com.jydp.entity.DO.back.BackerHandleUserBalanceMoneyDO;
import com.jydp.entity.DO.back.BackerHandleUserRecordBalanceDO;
import com.jydp.entity.DO.back.BackerHandleUserRecordBalanceFreezeDO;
import com.jydp.entity.DO.user.UserBalanceDO;
import com.jydp.entity.DO.user.UserCurrencyNumDO;
import com.jydp.entity.DO.user.UserDO;
import com.jydp.entity.DTO.SylUserDO;
import com.jydp.entity.DTO.UserAmountCheckDTO;
import com.jydp.entity.DTO.UserDTO;
import com.jydp.entity.VO.TransactionCurrencyVO;
import com.jydp.entity.VO.UserDealCapitalMessageVO;
import com.jydp.service.IBackerHandleUserBalanceFreezeMoneyService;
import com.jydp.service.IBackerHandleUserBalanceMoneyService;
import com.jydp.service.IBackerHandleUserRecordBalanceFreezeService;
import com.jydp.service.IBackerHandleUserRecordBalanceService;
import com.jydp.service.IRedisService;
import com.jydp.service.ITransactionCurrencyService;
import com.jydp.service.IUserBalanceService;
import com.jydp.service.IUserCurrencyNumService;
import com.jydp.service.IUserService;
import config.RedisKeyConfig;
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

    /**  后台管理员增减用户冻结余额记录 */
    @Autowired
    private IBackerHandleUserRecordBalanceFreezeService backerHandleUserRecordBalanceFreezeService;

    /**  后台管理员增减用户可用币记录 */
    @Autowired
    private IBackerHandleUserBalanceMoneyService backerHandleUserBalanceMoneyService;

    /**  后台管理员增减用户冻结币记录 */
    @Autowired
    private IBackerHandleUserBalanceFreezeMoneyService backerHandleUserBalanceFreezeMoneyService;

    /** redis服务 */
    @Autowired
    private IRedisService redisService;

    /**
     * 新增用户账号
     * @param userDO 用户账号
     * @return 新增成功：返回true，新增失败：返回false
     */
    public UserDO insertUser (UserDO userDO) {
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
     * @param authenticationStatus 实名认证状态，1：待审核，2：审核通过，3：审核拒绝， 4：未提交，查询全部填0
     * @param startTime   开始时间(可为null)
     * @param endTime     结束时间(可为null)
     * @return 查询成功：返回用户账户总数，查询失败：返回0
     */
    public int countUserForBacker (String userAccount, String phoneAreaCode, String phoneNumber, int accountStatus,
                                   int authenticationStatus, Timestamp startTime, Timestamp endTime) {
        return userDao.countUserForBacker(userAccount, phoneAreaCode, phoneNumber, accountStatus, authenticationStatus, startTime, endTime);
    }

    /**
     * 查询用户账号列表（后台）
     * @param userAccount 用户账号（可为null）
     * @param phoneAreaCode 手机号区号（可为null）
     * @param phoneNumber 手机号（可为null）
     * @param accountStatus 账号状态，1：启用，2：禁用，查询全部填0
     * @param authenticationStatus 实名认证状态，1：待审核，2：审核通过，3：审核拒绝， 4：未提交，查询全部填0
     * @param startTime   开始时间(可为null)
     * @param endTime     结束时间(可为null)
     * @param pageNumber  当前页数
     * @param pageSize    查询条数
     * @return 查询成功：返回用户账户列表，查询失败：返回null
     */
    public List<UserDO> listUserForBacker (String userAccount, String phoneAreaCode, String phoneNumber, int accountStatus, int authenticationStatus,
                                           Timestamp startTime, Timestamp endTime, int pageNumber, int pageSize) {
        return userDao.listUserForBacker(userAccount, phoneAreaCode, phoneNumber, accountStatus, authenticationStatus, startTime, endTime, pageNumber, pageSize);
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
     * 修改用户账号实名认证状态
     * @param userId 用户Id
     * @param authenticationStatus 实名认证状态：1：待审核，2：审核通过，3：审核拒绝，4：未提交
     * @param oldAuthenticationStatus 原实名认证状态：1：待审核，2：审核通过，3：审核拒绝，4：未提交
     * @return 修改成功：返回true，修改失败：返回false
     */
    public boolean updateUserAuthenticationStatus (int userId, int authenticationStatus, int oldAuthenticationStatus) {
        return userDao.updateUserAuthenticationStatus(userId, authenticationStatus, oldAuthenticationStatus);
    }

    /**
     * 忘记密码
     * @param userAccount 用户账户
     * @param password 新密码
     * @return  修改成功：返回true，修改失败：返回false
     */
    @Override
    public boolean forgetPwd(String userAccount, String password) {
        return userDao.updateUser(userAccount, password);
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
     * 根据手机号及密码查询用户信息
     * @param phoneAreaCode 用户手机区号
     * @param phoneNumber 用户手机号
     * @param password 用户手机号
     * @return 查询成功：返回用户信息；查询失败：返回null
     */
    public SylUserDO getUserByPhoneAndPassword(String phoneAreaCode, String phoneNumber, String password){
        return userDao.getUserByPhoneAndPassword(phoneAreaCode, phoneNumber, password);
    }
    /**
     *  验证用户信息合法性
     * @param userAccount 用户名
     * @param password 密码
     * @param payPassword 支付密码
     * @return 查询成功：返回验证结果; 查询失败：返回null
     */
    @Override
    public JsonObjectBO validateUserInfo(String userAccount, String password, String payPassword) {
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

        if (payPassword.equals(password)) {
            jsonObjectBO.setCode(2);
            jsonObjectBO.setMessage("支付密码不能与登录密码一样");
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
    public UserDO register(UserDO userDO){
        //用戶信息新增
        UserDO user = userDao.insertUser(userDO);
        boolean excuteSuccess = true;

        if (user != null) {
            //查询所有币种
            List<TransactionCurrencyVO> transactionCurrencyList = transactionCurrencyService.getAllCurrencylistForWeb();

            if (transactionCurrencyList != null && transactionCurrencyList.size() > 0) {
                List<UserCurrencyNumDO> userCurrencyNumList = new ArrayList<>();
                for (int i = 0; i < transactionCurrencyList.size(); i ++) {
                    //注册时添加用户所有币种默认数量为0的记录
                    UserCurrencyNumDO userCurrencyNum =  new UserCurrencyNumDO();
                    userCurrencyNum.setUserId(user.getUserId());
                    userCurrencyNum.setCurrencyId(transactionCurrencyList.get(i).getCurrencyId());
                    userCurrencyNum.setAddTime(DateUtil.getCurrentTime());
                    userCurrencyNumList.add(userCurrencyNum);
                }
                //新增用户币数量记录
                excuteSuccess = userCurrencyNumService.insertUserCurrencyForWeb(userCurrencyNumList);
            }

            if (!excuteSuccess) {
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return null;
            }
        }
        return user;
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
        String orderNo = SystemCommonConfig.USER_BALANCE + DateUtil.longToTimeStr(currentTime.getTime(), DateUtil.dateFormat10) + NumberUtil.createNumberStr(10);
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
        String orderNo = SystemCommonConfig.USER_BALANCE + DateUtil.longToTimeStr(currentTime.getTime(), DateUtil.dateFormat10) + NumberUtil.createNumberStr(10);
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
     * 增加用户冻结金额（后台操作）
     * @param userId 用户Id
     * @param userAccount 用户账号
     * @param balanceNumber 增加的账户锁定金额
     * @param backerId 后台管理员Id
     * @param backerAccount 后台管理员帐号
     * @param remarks 备注
     * @param ipAddress 操作时的ip地址
     * @return 操作成功：返回true，操作失败：返回false
     */
    @Transactional
    public boolean addBalanceNumberLockForBack(int userId, String userAccount, double balanceNumber,
                                           int backerId, String backerAccount, String remarks, String ipAddress) {
        Timestamp currentTime = DateUtil.getCurrentTime();
        String orderNo = SystemCommonConfig.USER_BALANCE + DateUtil.longToTimeStr(currentTime.getTime(), DateUtil.dateFormat10) + NumberUtil.createNumberStr(10);
        UserBalanceDO userBalanceDO = new UserBalanceDO();
        userBalanceDO.setOrderNo(orderNo);  //记录号：业务类型（2）+日期（6）+随机位（10）
        userBalanceDO.setUserId(userId);
        userBalanceDO.setFromType("系统操作");
        userBalanceDO.setCurrencyId(UserBalanceConfig.DOLLAR_ID);  //币种Id,美元id=999
        userBalanceDO.setCurrencyName(UserBalanceConfig.DOLLAR);  //货币名称
        userBalanceDO.setBalanceNumber(0);  //交易数量
        userBalanceDO.setFrozenNumber(balanceNumber);  //冻结数量
        userBalanceDO.setRemark(remarks);
        userBalanceDO.setAddTime(currentTime);

        //业务执行状态
        boolean executeSuccess = true;

        //添加用户账户记录
        executeSuccess = userBalanceService.insertUserBalance(userBalanceDO);

        //添加后台操作记录
        if (executeSuccess) {
            BackerHandleUserRecordBalanceFreezeDO backerHandleUserBalanceFreeze = new BackerHandleUserRecordBalanceFreezeDO();
            backerHandleUserBalanceFreeze.setAddTime(currentTime);
            backerHandleUserBalanceFreeze.setBackerAccount(backerAccount);
            backerHandleUserBalanceFreeze.setBackerId(backerId);
            backerHandleUserBalanceFreeze.setIpAddress(ipAddress);
            backerHandleUserBalanceFreeze.setRemarks(remarks);
            backerHandleUserBalanceFreeze.setTypeHandle(1);  //操作类型，1：增加，2：减少
            backerHandleUserBalanceFreeze.setUserAccount(userAccount);
            backerHandleUserBalanceFreeze.setUserId(userId);
            backerHandleUserBalanceFreeze.setUserBalance(balanceNumber);  //冻结资产

            executeSuccess = backerHandleUserRecordBalanceFreezeService.insertBackerHandleUserRecordBalanceFreeze(backerHandleUserBalanceFreeze);
        }

        // 增加冻结金额
        if (executeSuccess) {
            executeSuccess = updateAddUserAmount(userId, 0, balanceNumber);
        }

        //数据回滚
        if(!executeSuccess){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return executeSuccess;
    }

    /**
     * 减少用户冻结金额（后台操作）
     * @param userId 用户Id
     * @param userAccount 用户账号
     * @param balanceNumber 减少的账户锁定金额
     * @param backerId 后台管理员Id
     * @param backerAccount 后台管理员帐号
     * @param remarks 备注
     * @param ipAddress 操作时的ip地址
     * @return 操作成功：返回true，操作失败：返回false
     */
    @Transactional
    public boolean reduceBalanceNumberLockForBack(int userId, String userAccount, double balanceNumber,
                                               int backerId, String backerAccount, String remarks, String ipAddress) {
        Timestamp currentTime = DateUtil.getCurrentTime();
        String orderNo = SystemCommonConfig.USER_BALANCE + DateUtil.longToTimeStr(currentTime.getTime(), DateUtil.dateFormat10) + NumberUtil.createNumberStr(10);
        UserBalanceDO userBalanceDO = new UserBalanceDO();
        userBalanceDO.setOrderNo(orderNo);  //记录号：业务类型（2）+日期（6）+随机位（10）
        userBalanceDO.setUserId(userId);
        userBalanceDO.setFromType("系统操作");
        userBalanceDO.setCurrencyId(UserBalanceConfig.DOLLAR_ID);  //币种Id,美元id=999
        userBalanceDO.setCurrencyName(UserBalanceConfig.DOLLAR);  //货币名称
        userBalanceDO.setBalanceNumber(0);  //交易数量
        userBalanceDO.setFrozenNumber(-balanceNumber);  //冻结数量
        userBalanceDO.setRemark(remarks);
        userBalanceDO.setAddTime(currentTime);

        //业务执行状态
        boolean executeSuccess = true;

        //添加用户账户记录
        executeSuccess = userBalanceService.insertUserBalance(userBalanceDO);

        //添加后台操作记录
        if (executeSuccess) {
            BackerHandleUserRecordBalanceFreezeDO backerHandleUserBalanceFreeze = new BackerHandleUserRecordBalanceFreezeDO();
            backerHandleUserBalanceFreeze.setAddTime(currentTime);
            backerHandleUserBalanceFreeze.setBackerAccount(backerAccount);
            backerHandleUserBalanceFreeze.setBackerId(backerId);
            backerHandleUserBalanceFreeze.setIpAddress(ipAddress);
            backerHandleUserBalanceFreeze.setRemarks(remarks);
            backerHandleUserBalanceFreeze.setTypeHandle(2);  //操作类型，1：增加，2：减少
            backerHandleUserBalanceFreeze.setUserAccount(userAccount);
            backerHandleUserBalanceFreeze.setUserId(userId);
            backerHandleUserBalanceFreeze.setUserBalance(balanceNumber);  //冻结资产

            executeSuccess = backerHandleUserRecordBalanceFreezeService.insertBackerHandleUserRecordBalanceFreeze(backerHandleUserBalanceFreeze);
        }

        // 减少冻结金额
        if (executeSuccess) {
            executeSuccess = updateReduceUserBalanceLock(userId, balanceNumber);
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

    /**
     * 查询用户交易中心相关资产信息
     * @param userId 用户id
     * @param currencyId 币种id
     * @return 查询成功：返回用户账户错误总数，查询失败：返回0
     */
    public UserDealCapitalMessageVO countCheckUserAmountForTimer(int userId, int currencyId){
        UserDealCapitalMessageVO userDealCapitalMessage = new UserDealCapitalMessageVO();
        UserDO user = userDao.getUserByUserId(userId);
        if(user != null){
            double userBalance = NumberUtil.doubleFormat(user.getUserBalance(), 6);
            userDealCapitalMessage.setUserBalance(userBalance);
            double userBalanceLock = NumberUtil.doubleFormat(user.getUserBalanceLock(), 6);
            userDealCapitalMessage.setUserBalanceLock(userBalanceLock);
            double currencyNumberSum = BigDecimalUtil.add(userBalance, userBalanceLock);
            userDealCapitalMessage.setCurrencyNumberSum(currencyNumberSum);
        }

        //查询用户币数量
        List<UserCurrencyNumDO> userCurrencyNumList = userCurrencyNumService.getUserCurrencyNumByUserId(userId);
        if(userCurrencyNumList != null && userCurrencyNumList.size()>0){
            //根据币种id查询币种当前交易价格
            double nowPrice = 0;
            if(redisService.getValue(RedisKeyConfig.NOW_PRICE + currencyId) != null){
                nowPrice = Double.parseDouble(redisService.getValue(RedisKeyConfig.NOW_PRICE + currencyId).toString());
            }

            for(UserCurrencyNumDO userCurrencyNum : userCurrencyNumList){
                if(userCurrencyNum.getCurrencyId() == currencyId){
                    //账户总资产计算
                    double currencyNumberSum = BigDecimalUtil.add(userCurrencyNum.getCurrencyNumber() , userCurrencyNum.getCurrencyNumberLock());
                    currencyNumberSum = BigDecimalUtil.mul(currencyNumberSum, nowPrice);
                    currencyNumberSum = NumberUtil.doubleFormat(currencyNumberSum, 6);
                    currencyNumberSum = BigDecimalUtil.add(currencyNumberSum, userDealCapitalMessage.getCurrencyNumberSum());

                    userDealCapitalMessage.setCurrencyNumber(NumberUtil.doubleFormat(userCurrencyNum.getCurrencyNumber(), 4));
                    userDealCapitalMessage.setCurrencyNumberLock(NumberUtil.doubleFormat(userCurrencyNum.getCurrencyNumberLock(), 4));
                    userDealCapitalMessage.setCurrencyNumberSum(currencyNumberSum);
                }
            }
        }
        return userDealCapitalMessage;
    }

    /**
     * 增加用户货币数量（后台操作）
     * @param userId 用户Id
     * @param userAccount 用户账号
     * @param currencyId 货币Id
     * @param currencyName 货币名称
     * @param currencyNumber 增加的货币数量
     * @param backerId 后台管理员Id
     * @param backerAccount 后台管理员帐号
     * @param remarks 备注
     * @param ipAddress 操作时的ip地址
     * @return 操作成功：返回true，操作失败：返回false
     */
    @Transactional
    public boolean addUserCurrencyNumberForBack(int userId, String userAccount, int currencyId, String currencyName, double currencyNumber,
                                                int backerId, String backerAccount, String remarks, String ipAddress) {
        Timestamp currentTime = DateUtil.getCurrentTime();
        String orderNo = SystemCommonConfig.USER_BALANCE + DateUtil.longToTimeStr(currentTime.getTime(), DateUtil.dateFormat10) + NumberUtil.createNumberStr(10);
        UserBalanceDO userBalanceDO = new UserBalanceDO();
        userBalanceDO.setOrderNo(orderNo);  //记录号：业务类型（2）+日期（6）+随机位（10）
        userBalanceDO.setUserId(userId);
        userBalanceDO.setFromType("系统操作");
        userBalanceDO.setCurrencyId(currencyId);  //币种Id,美元id=999
        userBalanceDO.setCurrencyName(currencyName);  //货币名称
        userBalanceDO.setBalanceNumber(currencyNumber);  //交易数量
        userBalanceDO.setFrozenNumber(0);  //冻结数量
        userBalanceDO.setRemark(remarks);
        userBalanceDO.setAddTime(currentTime);

        //业务执行状态
        boolean executeSuccess = true;

        //添加用户账户记录
        executeSuccess = userBalanceService.insertUserBalance(userBalanceDO);

        //新增管理员操作记录
        if (executeSuccess) {
            BackerHandleUserBalanceMoneyDO backerHandleUserBalanceMoney = new BackerHandleUserBalanceMoneyDO();
            backerHandleUserBalanceMoney.setAddTime(currentTime);
            backerHandleUserBalanceMoney.setBackerAccount(backerAccount);
            backerHandleUserBalanceMoney.setBackerId(backerId);
            backerHandleUserBalanceMoney.setIpAddress(ipAddress);
            backerHandleUserBalanceMoney.setRemarks(remarks);
            backerHandleUserBalanceMoney.setTypeHandle(1);  //操作类型，1：增加，2：减少
            backerHandleUserBalanceMoney.setUserAccount(userAccount);
            backerHandleUserBalanceMoney.setUserId(userId);
            backerHandleUserBalanceMoney.setCurrencyId(currencyId);
            backerHandleUserBalanceMoney.setUserBalance(currencyNumber);  //可用币

            executeSuccess = backerHandleUserBalanceMoneyService.insertBackerHandleUserBalanceMoney(backerHandleUserBalanceMoney);
        }
        //增加用户货币数量
        if (executeSuccess) {
            executeSuccess = userCurrencyNumService.increaseCurrencyNumber(userId, currencyId, currencyNumber);
            //用户货币账户未创建时，创建该货币账户
            if (!executeSuccess) {
                UserCurrencyNumDO userCurrencyNum = new UserCurrencyNumDO();
                userCurrencyNum.setUserId(userId);
                userCurrencyNum.setCurrencyId(currencyId);
                userCurrencyNum.setCurrencyNumber(currencyNumber);
                userCurrencyNum.setCurrencyNumberLock(0);
                userCurrencyNum.setAddTime(currentTime);

                executeSuccess = userCurrencyNumService.insertUserCurrencyNum(userCurrencyNum);
            }
        }

        //数据回滚
        if(!executeSuccess){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return executeSuccess;
    }

    /**
     * 减少用户货币数量（后台操作）
     * @param userId 用户Id
     * @param userAccount 用户账号
     * @param currencyId 货币Id
     * @param currencyName 货币名称
     * @param currencyNumber 减少的货币数量
     * @param backerId 后台管理员Id
     * @param backerAccount 后台管理员帐号
     * @param remarks 备注
     * @param ipAddress 操作时的ip地址
     * @return 操作成功：返回true，操作失败：返回false
     */
    @Transactional
    public boolean reduceUserCurrencyNumberForBack(int userId, String userAccount, int currencyId, String currencyName, double currencyNumber,
                                            int backerId, String backerAccount, String remarks, String ipAddress) {
        Timestamp currentTime = DateUtil.getCurrentTime();
        String orderNo = SystemCommonConfig.USER_BALANCE + DateUtil.longToTimeStr(currentTime.getTime(), DateUtil.dateFormat10) + NumberUtil.createNumberStr(10);
        UserBalanceDO userBalanceDO = new UserBalanceDO();
        userBalanceDO.setOrderNo(orderNo);  //记录号：业务类型（2）+日期（6）+随机位（10）
        userBalanceDO.setUserId(userId);
        userBalanceDO.setFromType("系统操作");
        userBalanceDO.setCurrencyId(currencyId);  //币种Id,美元id=999
        userBalanceDO.setCurrencyName(currencyName);  //货币名称
        userBalanceDO.setBalanceNumber(-currencyNumber);  //交易数量
        userBalanceDO.setFrozenNumber(0);  //冻结数量
        userBalanceDO.setRemark(remarks);
        userBalanceDO.setAddTime(currentTime);

        //业务执行状态
        boolean executeSuccess = true;

        //添加用户账户记录
        executeSuccess = userBalanceService.insertUserBalance(userBalanceDO);

        //新增管理员操作记录
        if (executeSuccess) {
            BackerHandleUserBalanceMoneyDO backerHandleUserBalanceMoney = new BackerHandleUserBalanceMoneyDO();
            backerHandleUserBalanceMoney.setAddTime(currentTime);
            backerHandleUserBalanceMoney.setBackerAccount(backerAccount);
            backerHandleUserBalanceMoney.setBackerId(backerId);
            backerHandleUserBalanceMoney.setIpAddress(ipAddress);
            backerHandleUserBalanceMoney.setRemarks(remarks);
            backerHandleUserBalanceMoney.setTypeHandle(2);  //操作类型，1：增加，2：减少
            backerHandleUserBalanceMoney.setUserAccount(userAccount);
            backerHandleUserBalanceMoney.setUserId(userId);
            backerHandleUserBalanceMoney.setCurrencyId(currencyId);
            backerHandleUserBalanceMoney.setUserBalance(currencyNumber);  //可用币

            executeSuccess = backerHandleUserBalanceMoneyService.insertBackerHandleUserBalanceMoney(backerHandleUserBalanceMoney);
        }
        //减少用户货币数量
        if (executeSuccess) {
            executeSuccess = userCurrencyNumService.reduceCurrencyNumber(userId, currencyId, currencyNumber);
        }

        //数据回滚
        if(!executeSuccess){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return executeSuccess;
    }

    /**
     * 增加用户货币冻结数量（后台操作）
     * @param userId 用户Id
     * @param userAccount 用户账号
     * @param currencyId 货币Id
     * @param currencyName 货币名称
     * @param currencyNumberLock 增加的货币冻结数量
     * @param backerId 后台管理员Id
     * @param backerAccount 后台管理员帐号
     * @param remarks 备注
     * @param ipAddress 操作时的ip地址
     * @return 操作成功：返回true，操作失败：返回false
     */
    @Transactional
    public boolean addUserCurrencyNumberLockForBack(int userId, String userAccount, int currencyId, String currencyName, double currencyNumberLock,
                                                int backerId, String backerAccount, String remarks, String ipAddress) {
        Timestamp currentTime = DateUtil.getCurrentTime();
        String orderNo = SystemCommonConfig.USER_BALANCE + DateUtil.longToTimeStr(currentTime.getTime(), DateUtil.dateFormat10) + NumberUtil.createNumberStr(10);
        UserBalanceDO userBalanceDO = new UserBalanceDO();
        userBalanceDO.setOrderNo(orderNo);  //记录号：业务类型（2）+日期（6）+随机位（10）
        userBalanceDO.setUserId(userId);
        userBalanceDO.setFromType("系统操作");
        userBalanceDO.setCurrencyId(currencyId);  //币种Id,美元id=999
        userBalanceDO.setCurrencyName(currencyName);  //货币名称
        userBalanceDO.setBalanceNumber(0);  //交易数量
        userBalanceDO.setFrozenNumber(currencyNumberLock);  //冻结数量
        userBalanceDO.setRemark(remarks);
        userBalanceDO.setAddTime(currentTime);

        //业务执行状态
        boolean executeSuccess = true;

        //添加用户账户记录
        executeSuccess = userBalanceService.insertUserBalance(userBalanceDO);

        //新增管理员操作记录
        if (executeSuccess) {
            BackerHandleUserBalanceFreezeMoneyDO backerHandleUserBalanceFreezeMoney = new BackerHandleUserBalanceFreezeMoneyDO();
            backerHandleUserBalanceFreezeMoney.setAddTime(currentTime);
            backerHandleUserBalanceFreezeMoney.setBackerAccount(backerAccount);
            backerHandleUserBalanceFreezeMoney.setBackerId(backerId);
            backerHandleUserBalanceFreezeMoney.setIpAddress(ipAddress);
            backerHandleUserBalanceFreezeMoney.setRemarks(remarks);
            backerHandleUserBalanceFreezeMoney.setTypeHandle(1);  //操作类型，1：增加，2：减少
            backerHandleUserBalanceFreezeMoney.setUserAccount(userAccount);
            backerHandleUserBalanceFreezeMoney.setUserId(userId);
            backerHandleUserBalanceFreezeMoney.setCurrencyId(currencyId);
            backerHandleUserBalanceFreezeMoney.setUserBalance(currencyNumberLock);  //冻结币（个）

            executeSuccess = backerHandleUserBalanceFreezeMoneyService.insertBackerHandleUserBalanceFreezeMoney(backerHandleUserBalanceFreezeMoney);
        }
        //增加用户货币冻结数量
        if (executeSuccess) {
            executeSuccess = userCurrencyNumService.increaseCurrencyNumberLock(userId, currencyId, currencyNumberLock);
            //用户货币账户未创建时，创建该货币账户
            if (!executeSuccess) {
                UserCurrencyNumDO userCurrencyNum = new UserCurrencyNumDO();
                userCurrencyNum.setUserId(userId);
                userCurrencyNum.setCurrencyId(currencyId);
                userCurrencyNum.setCurrencyNumber(0);
                userCurrencyNum.setCurrencyNumberLock(currencyNumberLock);
                userCurrencyNum.setAddTime(currentTime);

                executeSuccess = userCurrencyNumService.insertUserCurrencyNum(userCurrencyNum);
            }
        }

        //数据回滚
        if(!executeSuccess){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return executeSuccess;
    }

    /**
     * 减少用户货币冻结数量（后台操作）
     * @param userId 用户Id
     * @param userAccount 用户账号
     * @param currencyId 货币Id
     * @param currencyName 货币名称
     * @param currencyNumber 减少的货币冻结数量
     * @param backerId 后台管理员Id
     * @param backerAccount 后台管理员帐号
     * @param remarks 备注
     * @param ipAddress 操作时的ip地址
     * @return 操作成功：返回true，操作失败：返回false
     */
    @Transactional
    public boolean reduceUserCurrencyNumberLockForBack(int userId, String userAccount, int currencyId, String currencyName, double currencyNumber,
                                                   int backerId, String backerAccount, String remarks, String ipAddress) {
        Timestamp currentTime = DateUtil.getCurrentTime();
        String orderNo = SystemCommonConfig.USER_BALANCE + DateUtil.longToTimeStr(currentTime.getTime(), DateUtil.dateFormat10) + NumberUtil.createNumberStr(10);
        UserBalanceDO userBalanceDO = new UserBalanceDO();
        userBalanceDO.setOrderNo(orderNo);  //记录号：业务类型（2）+日期（6）+随机位（10）
        userBalanceDO.setUserId(userId);
        userBalanceDO.setFromType("系统操作");
        userBalanceDO.setCurrencyId(currencyId);  //币种Id,美元id=999
        userBalanceDO.setCurrencyName(currencyName);  //货币名称
        userBalanceDO.setBalanceNumber(0);  //交易数量
        userBalanceDO.setFrozenNumber(-currencyNumber);  //冻结数量
        userBalanceDO.setRemark(remarks);
        userBalanceDO.setAddTime(currentTime);

        //业务执行状态
        boolean executeSuccess = true;

        //添加用户账户记录
        executeSuccess = userBalanceService.insertUserBalance(userBalanceDO);

        //新增管理员操作记录
        if (executeSuccess) {
            BackerHandleUserBalanceFreezeMoneyDO backerHandleUserBalanceFreezeMoney = new BackerHandleUserBalanceFreezeMoneyDO();
            backerHandleUserBalanceFreezeMoney.setAddTime(currentTime);
            backerHandleUserBalanceFreezeMoney.setBackerAccount(backerAccount);
            backerHandleUserBalanceFreezeMoney.setBackerId(backerId);
            backerHandleUserBalanceFreezeMoney.setIpAddress(ipAddress);
            backerHandleUserBalanceFreezeMoney.setRemarks(remarks);
            backerHandleUserBalanceFreezeMoney.setTypeHandle(2);  //操作类型，1：增加，2：减少
            backerHandleUserBalanceFreezeMoney.setUserAccount(userAccount);
            backerHandleUserBalanceFreezeMoney.setUserId(userId);
            backerHandleUserBalanceFreezeMoney.setCurrencyId(currencyId);
            backerHandleUserBalanceFreezeMoney.setUserBalance(currencyNumber);  //冻结币（个）

            executeSuccess = backerHandleUserBalanceFreezeMoneyService.insertBackerHandleUserBalanceFreezeMoney(backerHandleUserBalanceFreezeMoney);
        }
        //减少用户货币冻结数量
        if (executeSuccess) {
            executeSuccess = userCurrencyNumService.reduceCurrencyNumberLock(userId, currencyId, currencyNumber);
        }

        //数据回滚
        if(!executeSuccess){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return executeSuccess;
    }

    /**
     * 修改用户账号支付密码状态
     * @param userId 用户Id
     * @param payPasswordStatus 支付密码状态：1：每笔交易都输入交易密码，2：每次登录只输入一次交易密码
     * @return 修改成功：返回true，修改失败：返回false
     */
    public boolean updateUserPayPasswordStatus (int userId, int payPasswordStatus){
        return userDao.updateUserPayPasswordStatus(userId, payPasswordStatus);
    }

    /**
     * 验证用户账号和手机号是否匹配
     * @param userAccount 用户账号
     * @param phoneAreaCode 区域号
     * @param phoneNumber 手机号
     * @return 查询成功：返回用户信息，查询失败：返回null
     */
    @Override
    public UserDO validateUserPhoneNumber(String userAccount, String phoneAreaCode, String phoneNumber) {
        return userDao.validateUserPhoneNumber(userAccount,phoneAreaCode,phoneNumber);
    }

}
