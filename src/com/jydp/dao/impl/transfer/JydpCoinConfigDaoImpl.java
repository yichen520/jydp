package com.jydp.dao.impl.transfer;

import com.iqmkj.utils.DateUtil;
import com.iqmkj.utils.LogUtil;
import com.jydp.dao.IJydpCoinConfigDao;
import com.jydp.entity.DO.transfer.JydpCoinConfigDO;
import com.jydp.entity.VO.UserCoinConfigVO;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * JYDP币种转出管理
 * @Author: wqq
 */
@Repository
public class JydpCoinConfigDaoImpl implements IJydpCoinConfigDao {

    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    /**
     * 根据筛选条件获取JYDP币种转出管理
     * @param backerAccount  后台管理员帐号
     * @param startAddTime  开始时间
     * @param endAddTime  结束时间
     * @param currencyId  币种id,没有填0
     * @return  操作成功：返回记录集合，操作失败：返回null
     */
    public List<JydpCoinConfigDO> getJydpCoinConfigServiceList(Timestamp startAddTime, Timestamp endAddTime, String backerAccount, int currencyId){
        Map<String, Object> map = new HashMap<>();
        map.put("startAddTime", startAddTime);
        map.put("endAddTime", endAddTime);
        map.put("backerAccount", backerAccount);
        map.put("currencyId", currencyId);

        List<JydpCoinConfigDO> result = null;
        try {
            result = sqlSessionTemplate.selectList("JydpCoinConfig_getJydpCoinConfigServiceList", map);
        } catch (Exception e) {
            LogUtil.printErrorLog(e);
        }
        return result;
    }

    /**
     * 新增JYDP币种转出管理
     * @param jydpCoinConfigDO  JYDP币种转出管理实体
     * @return  操作成功：ture，操作失败：返回false
     */
    public boolean insertJydpCoinConfig(JydpCoinConfigDO jydpCoinConfigDO){
        int result = 0;

        try {
            result = sqlSessionTemplate.insert("JydpCoinConfig_insertJydpCoinConfig", jydpCoinConfigDO);
        } catch (Exception e) {
            LogUtil.printErrorLog(e);
        }

        if(result > 0){
            return true;
        }else{
            return false;
        }
    }

    /**
     * 根据记录号获得JYDP币种转出管理记录
     * @param recordNo  币种转出管理记录号
     * @return  操作成功：返回记录，操作失败：返回null
     */
    public JydpCoinConfigDO getJydpCoinConfig(String recordNo){
        JydpCoinConfigDO result = null;

        try {
            result = sqlSessionTemplate.selectOne("JydpCoinConfig_getJydpCoinConfig", recordNo);
        } catch (Exception e) {
            LogUtil.printErrorLog(e);
        }
        return result;
    }

    /**
     * 根据记录号删除JYDP币种转出管理记录
     * @param recordNo  币种转出管理记录号
     * @return  操作成功：true，操作失败：false
     */
    public boolean deleteJydpCoinConfig(String recordNo){
        int result = 0;

        try {
            result = sqlSessionTemplate.update("JydpCoinConfig_deleteJydpCoinConfig", recordNo);
        } catch (Exception e) {
            LogUtil.printErrorLog(e);
        }

        if(result > 0){
            return true;
        }else{
            return false;
        }
    }

    /**
     * 查询用户所有币种,数量及转出管理(小于等于当前时间)
     * @param userId 用户id
     * @return 查询成功:返回所有币种信息, 查询失败:返回null
     */
    public List<UserCoinConfigVO> listUserCoinConfigByUserId(int userId) {
        List<UserCoinConfigVO> resultList = null;
        Map<String, Object> map = new HashMap<>();
        Timestamp addTime = DateUtil.getCurrentTime();
        map.put("userId", userId);
        map.put("addTime", addTime);


        try {
            resultList = sqlSessionTemplate.selectList("JydpCoinConfig_listUserCoinConfigByUserId", map);
        } catch (Exception e) {
            LogUtil.printErrorLog(e);
        }

        return resultList;
    }

    /**
     * 查询币种转出管理,根据币种id
     * @param currencyId 币种id
     * @return 查询成功:返回币种转出管理信息, 查询失败:返回null
     */
    public JydpCoinConfigDO getJydpCoinConfigByCurrencyId(int currencyId) {
        JydpCoinConfigDO result = null;

        try {
            result = sqlSessionTemplate.selectOne("JydpCoinConfig_getJydpCoinConfigByCurrencyId",currencyId );
        } catch (Exception e) {
            LogUtil.printErrorLog(e);
        }

        return result;
    }
}
