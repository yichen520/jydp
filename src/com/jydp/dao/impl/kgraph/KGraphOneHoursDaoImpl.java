package com.jydp.dao.impl.kgraph;

import com.iqmkj.utils.LogUtil;
import com.jydp.dao.IKGraphOneHoursDao;
import com.jydp.entity.DO.kgraph.KGraphOneHoursDO;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * k线图统计数据（一小时节点）
 * @author whx
 */
@Repository
public class KGraphOneHoursDaoImpl implements IKGraphOneHoursDao {

    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    /**
     * 批量新增k线图统计数据
     * @param kGraphFiveMinutesList k线图统计数据List
     * @return 操作成功：返回true，操作失败：返回false
     */
    public boolean insertKGraph(List<KGraphOneHoursDO> kGraphFiveMinutesList){
        int result = 0;

        try {
            result = sqlSessionTemplate.insert("KGraphOneHoursDao_insertKGraph", kGraphFiveMinutesList);
        } catch (Exception e) {
            LogUtil.printErrorLog(e);
        }

        if(result == kGraphFiveMinutesList.size()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 查询最近的时间节点
     * @param currencyId 币种id
     * @return 操作成功：返回时间节点，操作失败：返回null
     */
    public Timestamp getKGraphLatelyTime(int currencyId){
        Timestamp nodeTime = null;

        try {
            nodeTime = sqlSessionTemplate.selectOne("KGraphOneHoursDao_getKGraphLatelyTime", currencyId);
        } catch (Exception e) {
            LogUtil.printErrorLog(e);
        }

        return nodeTime;
    }

    /**
     * 查询最近时间节点的统计数据
     * @param currencyId 币种id
     * @return 操作成功：返回统计数据，操作失败：返回null
     */
    public KGraphOneHoursDO getKGraphLately(int currencyId){
        KGraphOneHoursDO kGraphOneHoursDO = new KGraphOneHoursDO();

        try {
            kGraphOneHoursDO = sqlSessionTemplate.selectOne("KGraphOneHoursDao_getKGraphLately", currencyId);
        } catch (Exception e) {
            LogUtil.printErrorLog(e);
        }

        return kGraphOneHoursDO;
    }

    /**
     * 查询最近时间节点的批量统计数据
     * @param currencyId 币种id
     * @param num 数量
     * @return 操作成功：返回统计数据List，操作失败：返回null
     */
    public List<KGraphOneHoursDO> listKGraphLately(int currencyId, int num){
        Map<String, Object> map = new HashMap<>();
        map.put("currencyId", currencyId);
        map.put("num", num);
        List<KGraphOneHoursDO> resultList = null;

        try {
            resultList = sqlSessionTemplate.selectList("KGraphOneHoursDao_listKGraphLately", map);
        } catch (Exception e) {
            LogUtil.printErrorLog(e);
        }

        return resultList;
    }
}
