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
}
