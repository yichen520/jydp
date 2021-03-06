package com.jydp.service.impl.user;

import com.jydp.dao.IUserPaymentTypeDao;
import com.jydp.entity.DO.otc.UserPaymentTypeDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jydp.service.IUserPaymentTypeService;

import java.util.List;

/**
 * 用户收款方式
 * @author lgx
 */
@Service("userPaymentTypeService")
public class UserPaymenTypeServiceImpl implements IUserPaymentTypeService {
    @Autowired
    private IUserPaymentTypeDao userPaymentTypeDao;

    /**
     * 新增 收款方式
     * @param userPaymentType 待新增的 收款方式
     * @return 新增成功：返回true, 新增失败：返回false
     */
    public UserPaymentTypeDO insertUserPaymentType(UserPaymentTypeDO userPaymentType){
        return userPaymentTypeDao.insertUserPaymentType(userPaymentType);
    }

    /**
     * 根据用户id、挂单号、支付方式查询 收款记录
     * @param userId  用户id
     * @param otcPendingOrderNo 挂单号
     * @param paymentType 支付方式
     * @return 查询成功：返回用户收款记录, 查询失败：返回null
     */
    public UserPaymentTypeDO getUserPaymentType(int userId, String otcPendingOrderNo, int paymentType){
       return userPaymentTypeDao.getUserPaymentType(userId, otcPendingOrderNo, paymentType);
    }

    /**
     * 根据用户id、挂单号查询 收款记录列表
     * @param userId  用户id
     * @param otcPendingOrderNo 挂单号
     * @return 查询成功：返回用户收款记录, 查询失败：返回null
     */
    public List<UserPaymentTypeDO> listUserPaymentType(int userId, String otcPendingOrderNo){
        return userPaymentTypeDao.listUserPaymentType(userId, otcPendingOrderNo);
    }
}
