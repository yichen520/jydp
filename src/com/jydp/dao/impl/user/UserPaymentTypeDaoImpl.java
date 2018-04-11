package com.jydp.dao.impl.user;

import com.iqmkj.utils.LogUtil;
import com.jydp.dao.IUserPaymentTypeDao;
import com.jydp.entity.DO.otc.OtcDealerUserDO;
import com.jydp.entity.DO.otc.UserPaymentTypeDO;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户标识经销商相关操作
 *
 * @author lgx
 */
@Repository
public class UserPaymentTypeDaoImpl implements IUserPaymentTypeDao {
    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    /**
     * 新增 收款方式
     *
     * @param userPaymentType 待新增的 收款方式
     * @return 新增成功：返回true, 新增失败：返回false
     */
    public UserPaymentTypeDO insertUserPaymentType(UserPaymentTypeDO userPaymentType) {
        int result = 0;

        try {
            result = sqlSessionTemplate.insert("UserPaymentType_insertUserPaymentType", userPaymentType);
        } catch (Exception e) {
            LogUtil.printErrorLog(e);
        }

        if (result > 0) {
            return userPaymentType;
        } else {
            return null;
        }
    }

    /**
     * 根据用户id、挂单号、支付方式查询 收款记录
     * @param userId  用户id
     * @param otcPendingOrderNo 挂单号
     * @param paymentType 支付方式
     * @return 查询成功：返回用户收款记录, 查询失败：返回null
     */
    public UserPaymentTypeDO getUserPaymentType(int userId, String otcPendingOrderNo, int paymentType){

        UserPaymentTypeDO userPaymentType = null;
        Map<String,Object> map = new HashMap<>();
        map.put("userId", userId);
        map.put("otcPendingOrderNo", otcPendingOrderNo);
        map.put("paymentType", paymentType);

        try {
            userPaymentType = sqlSessionTemplate.selectOne("UserPaymentType_getUserPaymentType",map);
        } catch (Exception e) {
            LogUtil.printErrorLog(e);
        }

        return userPaymentType;
    }

    /**
     * 根据用户id、挂单号查询 收款记录列表
     * @param userId  用户id
     * @param otcPendingOrderNo 挂单号
     * @return 查询成功：返回用户收款记录, 查询失败：返回null
     */
    public List<UserPaymentTypeDO> listUserPaymentType(int userId, String otcPendingOrderNo){
        List<UserPaymentTypeDO> list = null;
        Map<String,Object> map = new HashMap<>();
        map.put("userId", userId);
        map.put("otcPendingOrderNo", otcPendingOrderNo);

        try {
            list = sqlSessionTemplate.selectList("UserPaymentType_listUserPaymentType",map);
        } catch (Exception e) {
            LogUtil.printErrorLog(e);
        }

        return list;
    }

}
