package com.jydp.service.impl.user;

import com.jydp.dao.IUserIdentificationImageDao;
import com.jydp.entity.DO.user.UserIdentificationImageDO;
import com.jydp.service.IUserIdentificationImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    /**
     * 批量新增用户认证详情图列表
     * @param userIdentificationImageList 用户认证记录列表
     * @return 查询成功：返回用户认证详情图列表，查询失败：返回null
     */
    public boolean insertUserIdentificationImageList(List<UserIdentificationImageDO> userIdentificationImageList) {
        return userIdentificationImageDao.insertUserIdentificationImageList(userIdentificationImageList);
    }

    /**
     * 查询用户认证详情图列表
     * @param identificationId 用户认证记录Id
     * @return 查询成功：返回用户认证详情图列表，查询失败：返回null
     */
    public List<UserIdentificationImageDO> listUserIdentificationImageByIdentificationId(long identificationId) {
        return userIdentificationImageDao.listUserIdentificationImageByIdentificationId(identificationId);
    }
}
