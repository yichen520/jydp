package com.jydp.service.impl.user;

import com.jydp.dao.IUserPaymentTypeDao;
import com.jydp.entity.DO.otc.UserPaymentTypeDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jydp.service.IUserPaymentTypeService;

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
    public boolean insertUserPaymentType(UserPaymentTypeDO userPaymentType){
        return userPaymentTypeDao.insertUserPaymentType(userPaymentType);
    }

    @Override
    public UserPaymentTypeDO getUserPaymentType(int userId, String otcPendingOrderNo, int paymentType) {
        return null;
    }
}
