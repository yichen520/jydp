package com.jydp.dao.impl.kgraph;

import com.iqmkj.utils.LogUtil;
import com.jydp.dao.IKGraphOneDayDao;
import com.jydp.entity.DO.kgraph.KGraphOneDayDO;
import com.jydp.entity.VO.TransactionGraphVO;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * k线图统计数据（一天节点）
 * @author whx
 */
@Repository
public class KGraphOneDayDaoImpl implements IKGraphOneDayDao {

    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    /**
     * 批量新增k线图统计数据
     * @param kGraphFiveMinutesList k线图统计数据List
     * @return 操作成功：返回true，操作失败：返回false
     */
    public boolean insertKGraph(List<KGraphOneDayDO> kGraphFiveMinutesList){
        int result = 0;

        try {
            result = sqlSessionTemplate.insert("KGraphOneDayDao_insertKGraph", kGraphFiveMinutesList);
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
            nodeTime = sqlSessionTemplate.selectOne("KGraphOneDayDao_getKGraphLatelyTime", currencyId);
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
    public KGraphOneDayDO getKGraphLately(int currencyId){
        KGraphOneDayDO kGraphOneDayDO = new KGraphOneDayDO();

        try {
            kGraphOneDayDO = sqlSessionTemplate.selectOne("KGraphOneDayDao_getKGraphLately", currencyId);
        } catch (Exception e) {
            LogUtil.printErrorLog(e);
        }

        return kGraphOneDayDO;
    }

    /**
     * 查询最近时间节点的批量统计数据
     * @param currencyId 币种id
     * @param num 数量
     * @return 操作成功：返回统计数据List，操作失败：返回null
     */
    public List<TransactionGraphVO> listKGraphLately(int currencyId, int num){
        Map<String, Object> map = new HashMap<>();
        map.put("currencyId", currencyId);
        map.put("num", num);
        List<TransactionGraphVO> resultList = null;

        try {
            resultList = sqlSessionTemplate.selectList("KGraphOneDayDao_listKGraphLately", map);
        } catch (Exception e) {
            LogUtil.printErrorLog(e);
        }

        return resultList;
    }

    /**
     * 更新节点数据
     * @param kGraph 节点数据
     * @return 操作成功：返回true，操作失败：返回false
     */
    public boolean updateKGraph(KGraphOneDayDO kGraph) {
        int result = 0;
        try {
            result = sqlSessionTemplate.update("KGraphOneDay_updateKGraph", kGraph);
        } catch (Exception e) {
            LogUtil.printErrorLog(e);
        }

        if (result > 0) {
            return true;
        }
        return false;
    }

}
