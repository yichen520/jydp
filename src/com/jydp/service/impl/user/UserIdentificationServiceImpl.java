package com.jydp.service.impl.user;

import com.jydp.dao.IUserIdentificationDao;
import com.jydp.entity.DO.user.UserIdentificationDO;
import com.jydp.service.IUserIdentificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Description: 用户认证
 * Author: hht
 * Date: 2018-02-07 16:56
 */
@Service("userIdentificationService")
public class UserIdentificationServiceImpl implements IUserIdentificationService {

    /** 用户认证 */
    @Autowired
    private IUserIdentificationDao userIdentificationService;

    /**
     * 新增用户认证
     * @param userIdentificationDO 用户认证
     * @return 操作成功：返回true，操作失败：返回false
     */
    public boolean insertUserIdentification (UserIdentificationDO userIdentificationDO) {
        return userIdentificationService.insertUserIdentification(userIdentificationDO);
    }

    /**
     * 查询用户认证信息
     * @param id 记录Id
     * @return 操作成功：返回用户认证信息，操作失败：返回null
     */
    public UserIdentificationDO getUserIdentificationById(long id) {
        return userIdentificationService.getUserIdentificationById(id);
    }

}
