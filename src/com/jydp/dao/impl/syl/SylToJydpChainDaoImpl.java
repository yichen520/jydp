package com.jydp.dao.impl.syl;

import com.iqmkj.utils.LogUtil;
import com.jydp.dao.ISylToJydpChainDao;
import com.jydp.entity.DO.syl.SylToJydpChainDO;
import com.jydp.entity.VO.UserRechargeCoinRecordVO;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
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

    /**
     * 查询用户充币成功记录总数(用户展示数据)
     *
     * @param userId 用户id
     * @return 查询成功：返回用户充币成功记录总数，查询失败或无数据：返回0
     */
    public int countUserRechargeCoinRecordForUser(int userId) {
        int result = 0;
        try {
            result = sqlSessionTemplate.selectOne("SylToJydpChain_countUserRechargeCoinRecordForUser", userId);
        } catch (Exception e) {
            LogUtil.printErrorLog(e);
        }
        return result;
    }

    /**
     * 查询用户充币成功记录列表信息(用户展示数据)
     *
     * @param userId     用户id
     * @param pageNumber 当前页数
     * @param pageSize   每页条数
     * @return 查询成功：返回用户充币成功记录列表信息，查询失败或无数据：返回null
     */
    public List<UserRechargeCoinRecordVO> listUserRechargeCoinRecordForUser(int userId, int pageNumber, int pageSize) {
        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        map.put("startNumber", pageNumber * pageSize);
        map.put("pageSize", pageSize);

        List<UserRechargeCoinRecordVO> result = null;
        try {
            result = sqlSessionTemplate.selectList("SylToJydpChain_listUserRechargeCoinRecordForUser", map);
        } catch (Exception e) {
            LogUtil.printErrorLog(e);
        }
        return result;
    }

    /**
     * 查询用户充币成功记录总数(后台)
     * @param userAccount 用户账号,没有填null
     * @param orderNo 订单号,没有填null
     * @param walletOrderNo 钱包订单号,没有填null
     * @param currencyId 币种Id,查询全部填0
     * @param startTime 订单起始时间
     * @param endTime 订单结束时间
     * @return 查询成功:返回用户充币成功记录总数, 查询失败:返回0
     */
    public int countSylToJydpChainForBack(String userAccount, String orderNo, String walletOrderNo, int currencyId, Timestamp startTime, Timestamp endTime) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("userAccount", userAccount);
        map.put("orderNo", orderNo);
        map.put("walletOrderNo", walletOrderNo);
        map.put("currencyId", currencyId);
        map.put("startTime", startTime);
        map.put("endTime", endTime);

        int result = 0;

        try {
            result = sqlSessionTemplate.selectOne("SylToJydpChain_countSylToJydpChainForBack", map);
        } catch (Exception e) {
            LogUtil.printErrorLog(e);
        }

        return result;
    }

    /**
     * 查询用户充币成功记录(后台)
     * @param userAccount 用户账号,没有填null
     * @param orderNo 订单号,没有填null
     * @param walletOrderNo 钱包订单号,没有填null
     * @param currencyId 币种Id,查询全部填0
     * @param startTime 订单起始时间
     * @param endTime 订单结束时间
     * @param pageNumber 当前页
     * @param pageSize 页面大小
     * @return 操作成功:返回用户充币成功记录集合, 操作失败:返回null
     */
    public List<SylToJydpChainDO> listSylToJydpChainForBack(String userAccount, String orderNo, String walletOrderNo, int currencyId, Timestamp startTime, Timestamp endTime, int pageNumber, int pageSize) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("userAccount", userAccount);
        map.put("orderNo", orderNo);
        map.put("walletOrderNo", walletOrderNo);
        map.put("currencyId", currencyId);
        map.put("startTime", startTime);
        map.put("endTime", endTime);
        map.put("startNumber", pageNumber * pageSize);
        map.put("pageSize", pageSize);

        List<SylToJydpChainDO> resultList = null;

        try {
            resultList = sqlSessionTemplate.selectList("SylToJydpChain_listSylToJydpChainForBack", map);
        } catch (Exception e) {
            LogUtil.printErrorLog(e);
        }

        return resultList;
    }
}
