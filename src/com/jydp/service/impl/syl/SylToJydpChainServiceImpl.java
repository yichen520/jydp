package com.jydp.service.impl.syl;

import com.jydp.dao.ISylToJydpChainDao;
import com.jydp.entity.DO.syl.SylToJydpChainDO;
import com.jydp.service.ISylToJydpChainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * SYL转账盛源链记录(SYL-->JYDP)
 *
 * @author sy
 */
@Service("sylToJydpChainService")
public class SylToJydpChainServiceImpl implements ISylToJydpChainService {

    /** SYL转账盛源链记录(SYL-->JYDP)*/
    @Autowired
    private ISylToJydpChainDao sylToJydpChainDao;

    /**
     * 新增 SYL转账盛源链记录(SYL-->JYDP)
     * @param sylToJydpChain 待新增的 SYL转账盛源链记录(SYL-->JYDP)
     * @return 新增成功：返回true, 新增失败：返回false
     */
    public boolean insertSylToJydpChain(SylToJydpChainDO sylToJydpChain){
        return sylToJydpChainDao.insertSylToJydpChain(sylToJydpChain);
    }
}
