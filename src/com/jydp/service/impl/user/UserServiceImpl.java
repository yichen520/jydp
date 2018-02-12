package com.jydp.service.impl.user;

import com.iqmkj.utils.DateUtil;
import com.jydp.dao.IUserDao;
import com.jydp.entity.BO.JsonObjectBO;
import com.jydp.entity.DO.transaction.TransactionCurrencyDO;
import com.jydp.entity.DO.user.UserCurrencyNumDO;
import com.jydp.entity.DO.user.UserDO;
import com.jydp.service.ITransactionCurrencyService;
import com.jydp.service.IUserCurrencyNumService;
import com.jydp.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

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
     * 修改用户账号状态
     * @param userId 用户Id
     * @param accountStatus 用户状态
     * @param oldAccountStatus 用户原来的状态
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
        UserDO user = new UserDO();
        user.setUserAccount(userAccount);
        user.setPassword(password);
        return userDao.updateUser(user);
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
     * @param refereeAccount 推荐人账号
     * @return 查询成功：返回验证结果; 查询失败：返回null
     */
    @Override
    public JsonObjectBO validateUserInfo(String userAccount, String password, String userPhone, String refereeAccount) {
        JsonObjectBO jsonObjectBO = new JsonObjectBO();
        String phoneReg = "^[1][3,4,5,7,8][0-9]{9}$";
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

        if (!Pattern.matches(phoneReg,userPhone)) {
            jsonObjectBO.setCode(2);
            jsonObjectBO.setMessage("请填写正确的手机号");
            return jsonObjectBO;
        }

        if (refereeAccount != null && !"".equals(refereeAccount)) {
            if (!Pattern.matches(accountReg,userAccount)) {
                jsonObjectBO.setCode(2);
                jsonObjectBO.setMessage("推荐人账户必须是字母或者数字,长度6~16位");
                return jsonObjectBO;
            }
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
            //查询所有币种
            List<TransactionCurrencyDO> transactionCurrencyDOList = transactionCurrencyService.getTransactionCurrencyListForWeb();

            if (transactionCurrencyDOList != null) {
                List<UserCurrencyNumDO> userCurrencyNumDOList = new ArrayList<UserCurrencyNumDO>();
                for (int i = 0; i < transactionCurrencyDOList.size(); i ++) {
                    UserCurrencyNumDO userCurrencyNum =  new UserCurrencyNumDO();
                    userCurrencyNum.setUserId(userDO.getUserId());
                    userCurrencyNum.setCurrencyId(transactionCurrencyDOList.get(i).getCurrencyId());
                    userCurrencyNum.setAddTime(DateUtil.getCurrentTime());
                }
                //新增用户币数量记录
                result = userCurrencyNumService.insertUserCurrencyForWeb(userCurrencyNumDOList);

                if (!result) {
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                    return false;
                }
            }
        } else {
            return false;
        }
        return result;
    }
}
