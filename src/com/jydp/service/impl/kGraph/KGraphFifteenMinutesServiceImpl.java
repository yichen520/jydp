package com.jydp.service.impl.kGraph;

import com.jydp.dao.IKGraphFifteenMinutesDao;
import com.jydp.entity.DO.kgraph.KGraphFifteenMinutesDO;
import com.jydp.service.IKGraphFifteenMinutesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

/**
 * k线图统计数据（十五分钟节点）
 * @author whx
 */
@Service("kGraphFifteenMinutesService")
public class KGraphFifteenMinutesServiceImpl implements IKGraphFifteenMinutesService {

    /** k线图统计数据（十五分钟节点）*/
    @Autowired
    private IKGraphFifteenMinutesDao kGraphFifteenMinutesDao;

    /**
     * 批量新增k线图统计数据
     * @param kGraphFiveMinutesList k线图统计数据List
     * @return 操作成功：返回true，操作失败：返回false
     */
    public boolean insertKGraph(List<KGraphFifteenMinutesDO> kGraphFiveMinutesList){
        return kGraphFifteenMinutesDao.insertKGraph(kGraphFiveMinutesList);
    }

    /**
     * 查询最近的时间节点
     * @param currencyId 币种id
     * @return 操作成功：返回时间节点，操作失败：返回null
     */
    public Timestamp getKGraphLatelyTime(int currencyId){
        return kGraphFifteenMinutesDao.getKGraphLatelyTime(currencyId);
    }

    /**
     * 查询最近时间节点的统计数据
     * @param currencyId 币种id
     * @return 操作成功：返回统计数据，操作失败：返回null
     */
    public KGraphFifteenMinutesDO getKGraphLately(int currencyId){
        return kGraphFifteenMinutesDao.getKGraphLately(currencyId);
    }

    /**
     * 查询最近时间节点的批量统计数据
     * @param currencyId 币种id
     * @param num 数量
     * @return 操作成功：返回统计数据List，操作失败：返回null
     */
    public List<KGraphFifteenMinutesDO> listKGraphLately(int currencyId, int num){
        return kGraphFifteenMinutesDao.listKGraphLately(currencyId, num);
    }

}
