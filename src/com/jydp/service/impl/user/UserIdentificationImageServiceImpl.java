package com.jydp.service.impl.user;

import com.jydp.dao.IUserIdentificationImageDao;
import com.jydp.service.IUserIdentificationImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Description: 用户认证详情图
 * Author: hht
 * Date: 2018-02-07 17:14
 */
@Service("userIdentificationImageService")
public class UserIdentificationImageServiceImpl implements IUserIdentificationImageService {

    /** 用户认证详情图 */
    @Autowired
    private IUserIdentificationImageDao userIdentificationImageDao;

}
