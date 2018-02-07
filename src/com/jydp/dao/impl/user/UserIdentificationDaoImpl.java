package com.jydp.dao.impl.user;

import com.iqmkj.utils.LogUtil;
import com.jydp.dao.IUserIdentificationDao;
import com.jydp.entity.DO.user.UserIdentificationDO;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Description: 用户认证
 * Author: hht
 * Date: 2018-02-07 16:49
 */
@Repository
public class UserIdentificationDaoImpl implements IUserIdentificationDao {

    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    /**
     * 新增用户认证
     * @param userIdentificationDO 用户认证
     * @return 操作成功：返回true，操作失败：返回false
     */
    public boolean insertUserIdentification (UserIdentificationDO userIdentificationDO) {
        int result = 0;
        try {
            result = sqlSessionTemplate.insert("UserIdentification_insertUserIdentification", userIdentificationDO);
        } catch (Exception e) {
            LogUtil.printErrorLog(e);
        }

        if (result > 0) {
            return true;
        } else {
            return false;
        }

    }

    /**
     * 查询用户认证信息
     * @param id 记录Id
     * @return 操作成功：返回用户认证信息，操作失败：返回null
     */
    public UserIdentificationDO getUserIdentificationById(long id) {
        UserIdentificationDO result = null;
        try {
            result = sqlSessionTemplate.selectOne("UserIdentification_getUserIdentificationById", id);
        } catch (Exception e) {
            LogUtil.printErrorLog(e);
        }
        return result;
    }
}
