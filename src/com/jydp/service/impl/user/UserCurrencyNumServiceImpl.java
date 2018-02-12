package com.jydp.service.impl.user;

import com.jydp.dao.IUserCurrencyNumDao;
import com.jydp.entity.DO.user.UserCurrencyDO;
import com.jydp.entity.DO.user.UserCurrencyNumDO;
import com.jydp.service.IUserCurrencyNumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Description: 用户币数量
 * Author: hht
 * Date: 2018-02-07 17:26
 */
@Service("userCurrencyNumService")
public class UserCurrencyNumServiceImpl implements IUserCurrencyNumService {

    /** 用户币数量 */
    @Autowired
    private IUserCurrencyNumDao userCurrencyNumDao;

    /**
     * 根据用户id查询币种记录
     * @param userId 用户Id
     * @return 查询成功：返回用户币种记录信息，查询失败：返回null
     */
    public List<UserCurrencyDO> getUserCurrencyByUserId (int userId){
        return userCurrencyNumDao.getUserCurrencyByUserId(userId);
    }

    /**
     * web端添加用户币数量(用户注册时添加记录，默认各币种数量为0)
     * @return 操作成功：返回true;操作失败：返回false
     */
    @Override
    public boolean insertUserCurrencyForWeb(List<UserCurrencyNumDO> userCurrencyNumDOList) {
        return userCurrencyNumDao.insertUserCurrencyForWeb(userCurrencyNumDOList);
    }


}
