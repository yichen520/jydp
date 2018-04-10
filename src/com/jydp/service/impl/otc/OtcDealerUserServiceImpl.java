package com.jydp.service.impl.otc;

import com.jydp.dao.IOtcDealerUserDao;
import com.jydp.entity.DO.otc.OtcDealerUserDO;
import com.jydp.service.IOtcDealerUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * 用户标识经销商相关操作
 * @author sy
 */
@Service("otcDealerUserService")
public class OtcDealerUserServiceImpl implements IOtcDealerUserService {
    @Autowired
    private IOtcDealerUserDao itcDealerUserDao;

    /**
     * 新增 用户标识经销商
     * @param otcDealerUser 待新增的 用户标识经销商
     * @return 新增成功：返回true, 新增失败：返回false
     */
    public boolean insertOtcDealerUser(OtcDealerUserDO otcDealerUser){
        return itcDealerUserDao.insertOtcDealerUser(otcDealerUser);
    }
}
