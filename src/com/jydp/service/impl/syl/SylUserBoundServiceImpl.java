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
}
