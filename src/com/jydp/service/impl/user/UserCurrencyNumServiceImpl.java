package com.jydp.service.impl.user;

import com.jydp.dao.IUserCurrencyNumDao;
import com.jydp.service.IUserCurrencyNumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
