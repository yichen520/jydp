package com.jydp.dao.impl.otc;

import com.iqmkj.utils.LogUtil;
import com.jydp.dao.IOtcTransactionPendOrderDao;
import com.jydp.entity.DO.otc.OtcTransactionPendOrderDO;
import com.jydp.entity.VO.OtcTransactionPendOrderVO;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 场外交易挂单记录相关操作
 *
 * @author lgx
 */
@Repository
public class otcTransactionPendOrderDaoImpl implements IOtcTransactionPendOrderDao {
    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    /**
     * 新增 场外交易挂单记录
     * @param otcTransactionPendOrderDO 待新增的 场外交易挂单记录
     * @return 新增成功：返回true, 新增失败：返回false
     */
    public boolean insertOtcTransactionPendOrder(OtcTransactionPendOrderDO otcTransactionPendOrderDO) {
        int result = 0;

        try {
            result = sqlSessionTemplate.insert("OtcTransactionPendOrde_insertOtcTransactionPendOrder", otcTransactionPendOrderDO);
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
     * 根据记录号查询挂单记录信息
     * @param orderNo 记录号
     * @return 查询成功：返回记录信息, 查询失败或者没有相应记录：返回null
     */
    public OtcTransactionPendOrderDO getOtcTransactionPendOrderByOrderNo(String orderNo){
        OtcTransactionPendOrderDO result = null;

        try {
            result = sqlSessionTemplate.selectOne("OtcTransactionPendOrde_getOtcTransactionPendOrderByOrderNo", orderNo);
        } catch (Exception e) {
            LogUtil.printErrorLog(e);
        }

        return result;
    }

    /**
     * 按条件查询全部场外交易挂单总数
     * @param currencyId 币种Id
     * @param orderType 挂单类型:1：出售，2：回购
     * @param area 地区
     * @return 查询成功：返回记录总条数，查询失败：返回0
     */
    @Override
    public int countOtcTransactionPendOrder(int currencyId, int orderType, String area){

        int count = 0;
        Map<String,Object> map = new HashMap<>();
        map.put("currencyId", currencyId);
        map.put("orderType", orderType);
        map.put("area", area);

        try {
            count = sqlSessionTemplate.selectOne("OtcTransactionPendOrde_countOtcTransactionPendOrder",map);
        } catch (Exception e) {
            LogUtil.printErrorLog(e);
        }
        return count;
    }

    /**
     * 查询全部场外交易挂单列表
     * @param currencyId 币种Id
     * @param orderType 挂单类型:1：出售，2：回购
     * @param area 地区
     * @param pageNumber 当前页数
     * @param pageSize 每页条数
     * @return 查询成功：返回记录信息，查询失败：返回null
     */
    @Override
    public List<OtcTransactionPendOrderVO> getOtcTransactionPendOrderlist(int currencyId, int orderType, String area, int pageNumber, int pageSize) {

        List<OtcTransactionPendOrderVO> otcTransactionPendOrderList = null;
        Map<String,Object> map = new HashMap<>();
        map.put("currencyId", currencyId);
        map.put("orderType", orderType);
        map.put("area", area);
        map.put("startNumber", pageNumber * pageSize);
        map.put("pageSize", pageSize);

        try {
            otcTransactionPendOrderList = sqlSessionTemplate.selectList("OtcTransactionPendOrde_getOtcTransactionPendOrderlist",map);
        } catch (Exception e) {
            LogUtil.printErrorLog(e);
        }
        return otcTransactionPendOrderList;
    }

    /**
     * 根据用户id查询场外可用交易挂单列表
     * @param userId 用户id
     * @return 查询成功：返回记录信息，查询失败：返回null
     */
    public List<OtcTransactionPendOrderDO> getOtcTransactionPendOrderByUserId(int userId){
        List<OtcTransactionPendOrderDO> result = null;

        try {
            result = sqlSessionTemplate.selectList("OtcTransactionPendOrde_getOtcTransactionPendOrderByUserId", userId);
        } catch (Exception e) {
            LogUtil.printErrorLog(e);
        }

        return result;
    }

    /**
     * 根据订单号删除该用户订单
     * @param userId 用户id
     * @param otcPendingOrderNo 订单id
     * @param updateTime 更新时间
     * @return 新增成功：返回true, 新增失败：返回false
     */
    public boolean deleteOtcTransactionPendOrderByOtcPendingOrderNo(int userId, String otcPendingOrderNo, Timestamp updateTime){
        int result = 0;
        Map<String,Object> map = new HashMap<>();
        map.put("userId", userId);
        map.put("otcPendingOrderNo", otcPendingOrderNo);
        map.put("updateTime", updateTime);

        try {
            result = sqlSessionTemplate.delete("OtcTransactionPendOrde_deleteOtcTransactionPendOrderByOtcPendingOrderNo", map);
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
