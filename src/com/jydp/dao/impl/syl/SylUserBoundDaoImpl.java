package com.jydp.dao.impl.syl;

import com.iqmkj.utils.LogUtil;
import com.jydp.dao.ISylUserBoundDao;
import com.jydp.entity.DO.syl.SylUserBoundDO;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


/**
 * 盛源链账号绑定
 *
 * @author sy
 */
@Repository
public class SylUserBoundDaoImpl implements ISylUserBoundDao {
    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    /**
     * 新增盛源链账号绑定记录
     * @param sylUserBound 待新增的 盛源链账号绑定记录
     * @return 新增成功：返回true, 新增失败：返回false
     */
    public boolean insertSylUserBound(SylUserBoundDO sylUserBound){
        int result = 0;

        try {
            result = sqlSessionTemplate.insert("SylUserBound_insertSylUserBound", sylUserBound);
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
     * 查询用户盛源链账号,根据用户id
     * @param userId 用户id
     * @return 查询成功:返回用户盛源链信息, 查询失败:返回null
     */
    public SylUserBoundDO getSylUserBoundByUserId(int userId) {
        SylUserBoundDO result = null;

        try {
            result = sqlSessionTemplate.selectOne("SylUserBound_getSylUserBoundByUserId", userId);
        } catch (Exception e) {
            LogUtil.printErrorLog(e);
        }

        return result;
    }

    /**
     * 根据盛源链账号查询绑定信息
     * @param sylUserAccount 盛源链账号
     * @return 查询成功:返回用户绑定信息, 查询失败或者没有相关信息:返回null
     */
    public SylUserBoundDO getSylUserBoundBySylUserAccount(String sylUserAccount){
        SylUserBoundDO result = null;

        try {
            result = sqlSessionTemplate.selectOne("SylUserBound_getSylUserBoundBySylUserAccount", sylUserAccount);
        } catch (Exception e) {
            LogUtil.printErrorLog(e);
        }

        return result;
    }
}
