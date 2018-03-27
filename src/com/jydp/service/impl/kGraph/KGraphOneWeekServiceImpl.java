package com.jydp.service.impl.kGraph;

import com.jydp.dao.IKGraphOneHoursDao;
import com.jydp.dao.IKGraphOneWeekDao;
import com.jydp.entity.DO.kgraph.KGraphOneWeekDO;
import com.jydp.service.IKGraphOneWeekService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

/**
 * k线图统计数据（一周节点）
 * @author whx
 */
@Service("KGraphOneWeekService")
public class KGraphOneWeekServiceImpl implements IKGraphOneWeekService {

    /** k线图统计数据（一周节点）*/
    @Autowired
    private IKGraphOneWeekDao kGraphOneWeekDao;

    /**
     * 批量新增k线图统计数据
     * @param kGraphFiveMinutesList k线图统计数据List
     * @return 操作成功：返回true，操作失败：返回false
     */
    public boolean insertKGraph(List<KGraphOneWeekDO> kGraphFiveMinutesList){
        return kGraphOneWeekDao.insertKGraph(kGraphFiveMinutesList);
    }

    /**
     * 查询最近的时间节点
     * @param currencyId 币种id
     * @return 操作成功：返回时间节点，操作失败：返回null
     */
    public Timestamp getKGraphLatelyTime(int currencyId){
        return kGraphOneWeekDao.getKGraphLatelyTime(currencyId);
    }

    /**
     * 查询最近时间节点的统计数据
     * @param currencyId 币种id
     * @return 操作成功：返回统计数据，操作失败：返回null
     */
    public KGraphOneWeekDO getKGraphLately(int currencyId){
        return kGraphOneWeekDao.getKGraphLately(currencyId);
    }

    /**
     * 查询最近时间节点的批量统计数据
     * @param currencyId 币种id
     * @param num 数量
     * @return 操作成功：返回统计数据List，操作失败：返回null
     */
    public List<KGraphOneWeekDO> listKGraphLately(int currencyId, int num){
        return kGraphOneWeekDao.listKGraphLately(currencyId, num);
    }
}
