package com.jydp.dao.impl.syl;

import com.iqmkj.utils.LogUtil;
import com.jydp.dao.ISylToJydpChainDao;
import com.jydp.entity.DO.syl.SylToJydpChainDO;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

/**
 * SYL转账盛源链记录(SYL-->JYDP)
 *
 * @author sy
 */
@Repository
public class SylToJydpChainDaoImpl implements ISylToJydpChainDao {
    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    /**
     * 新增 SYL转账盛源链记录(SYL-->JYDP)
     * @param walletOrderNo 待新增的 SYL转账盛源链记录(SYL-->JYDP)
     * @return 新增成功：返回true, 新增失败：返回false
     */
    public boolean insertSylToJydpChain(SylToJydpChainDO walletOrderNo){
        int result = 0;

        try {
            result = sqlSessionTemplate.insert("SylToJydpChain_insertSylToJydpChain", walletOrderNo);
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
     * 根据订单号查询订单信息
     * @param walletOrderNo 订单号
     * @param currencyId 币种
     * @return 查询成功：返回订单信息, 查询失败或者没有相关信息：返回null
     */
    public SylToJydpChainDO getSylToJydpChainBysylRecordNo(String walletOrderNo, int currencyId){
        SylToJydpChainDO result = null;
        Map<String,Object> map = new HashMap<>();
        map.put("currencyId", currencyId);
        map.put("walletOrderNo", walletOrderNo);

        try {
            result = sqlSessionTemplate.selectOne("SylToJydpChain_getSylToJydpChainBysylRecordNo", map);
        } catch (Exception e) {
            LogUtil.printErrorLog(e);
        }

        return result;
    }
}
