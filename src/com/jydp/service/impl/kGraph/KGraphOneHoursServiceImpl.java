package com.jydp.service.impl.kGraph;

import com.jydp.dao.IKGraphOneHoursDao;
import com.jydp.entity.DO.kgraph.KGraphOneHoursDO;
import com.jydp.service.IKGraphOneHoursService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

/**
 * k线图统计数据（一小时节点）
 * @author whx
 */
@Service("kGraphOneHoursService")
public class KGraphOneHoursServiceImpl implements IKGraphOneHoursService {

    /** k线图统计数据（一小时节点）*/
    @Autowired
    private IKGraphOneHoursDao kGraphOneHoursDao;

    /**
     * 批量新增k线图统计数据
     * @param kGraphFiveMinutesList k线图统计数据List
     * @return 操作成功：返回true，操作失败：返回false
     */
    public boolean insertKGraph(List<KGraphOneHoursDO> kGraphFiveMinutesList){
        return kGraphOneHoursDao.insertKGraph(kGraphFiveMinutesList);
    }

    /**
     * 查询最近的时间节点
     * @param currencyId 币种id
     * @return 操作成功：返回时间节点，操作失败：返回null
     */
    public Timestamp getKGraphLatelyTime(int currencyId){
        return kGraphOneHoursDao.getKGraphLatelyTime(currencyId);
    }

    /**
     * 查询最近时间节点的统计数据
     * @param currencyId 币种id
     * @return 操作成功：返回统计数据，操作失败：返回null
     */
    public KGraphOneHoursDO getKGraphLately(int currencyId){
        return kGraphOneHoursDao.getKGraphLately(currencyId);
    }

    /**
     * 查询最近时间节点的批量统计数据
     * @param currencyId 币种id
     * @param num 数量
     * @return 操作成功：返回统计数据List，操作失败：返回null
     */
    public List<KGraphOneHoursDO> listKGraphLately(int currencyId, int num){
        return kGraphOneHoursDao.listKGraphLately(currencyId, num);
    }
}
