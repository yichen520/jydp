package com.jydp.dao.impl.user;

import com.iqmkj.utils.LogUtil;
import com.jydp.dao.IUserIdentificationImageDao;
import com.jydp.entity.DO.user.UserIdentificationImageDO;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Description: 用户认证详情图
 * Author: hht
 * Date: 2018-02-07 17:12
 */
@Repository
public class UserIdentificationImageDaoImpl implements IUserIdentificationImageDao {

    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    /**
     * 查询用户认证详情图列表
     * @param identificationId 用户认证记录Id
     * @return 查询成功：返回用户认证详情图列表，查询失败：返回null
     */
    public List<UserIdentificationImageDO> listUserIdentificationImageByIdentificationId(long identificationId) {
        List<UserIdentificationImageDO> result = null;
        try {
            result = sqlSessionTemplate.selectList
                    ("UserIdentificationImage_listUserIdentificationImageByIdentificationId", identificationId);
        } catch (Exception e) {
            LogUtil.printErrorLog(e);
        }
        return result;
    }

}
