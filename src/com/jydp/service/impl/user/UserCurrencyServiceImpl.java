package com.jydp.service.impl.user;

import com.jydp.dao.IUserCurrencyDao;
import com.jydp.service.IUserCurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Description: 用户货币记录
 * Author: hht
 * Date: 2018-02-07 17:19
 */
@Service("userCurrencyService")
public class UserCurrencyServiceImpl implements IUserCurrencyService {

    /** 用户货币记录 */
    @Autowired
    private IUserCurrencyDao userCurrencyDao;
}
