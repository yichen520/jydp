package com.jydp.service.impl.syl;

import com.jydp.dao.ISylUserBoundDao;
import com.jydp.entity.DO.syl.SylUserBoundDO;
import com.jydp.service.ISylUserBoundService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * 盛源链账号绑定
 *
 * @author sy
 */
@Service("sylUserBoundService")
public class SylUserBoundServiceImpl implements ISylUserBoundService {

    /** 盛源链账号绑定 */
    @Autowired
    private ISylUserBoundDao sylUserBoundDao;

    /**
     * 新增盛源链账号绑定记录
     * @param sylUserBound 待新增的 盛源链账号绑定记录
     * @return 新增成功：返回true, 新增失败：返回false
     */
    public boolean insertSylUserBound(SylUserBoundDO sylUserBound){
        return sylUserBoundDao.insertSylUserBound(sylUserBound);
    }

    /**
     * 查询用户盛源链账号,根据用户id
     * @param userId 用户id
     * @return 查询成功:返回用户盛源链信息, 查询失败:返回null
     */
    public SylUserBoundDO getSylUserBoundByUserId(int userId) {
        return sylUserBoundDao.getSylUserBoundByUserId(userId);
    }

    /**
     * 根据盛源链账号查询绑定信息
     * @param sylUserAccount 盛源链账号
     * @return 查询成功:返回用户绑定信息, 查询失败或者没有相关信息:返回null
     */
    public SylUserBoundDO getSylUserBoundBySylUserAccount(String sylUserAccount){
        return sylUserBoundDao.getSylUserBoundBySylUserAccount(sylUserAccount);
    }
}
