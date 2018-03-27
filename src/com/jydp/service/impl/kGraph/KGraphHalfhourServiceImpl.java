package com.jydp.service.impl.kGraph;

import com.jydp.dao.IKGraphHalfhourDao;
import com.jydp.entity.DO.kgraph.KGraphHalfhourDO;
import com.jydp.service.IKGraphHalfhourService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

/**
 * k线图统计数据（三十分钟节点）
 * @author whx
 */
@Service("kGraphHalfhourService")
public class KGraphHalfhourServiceImpl implements IKGraphHalfhourService {

    /** k线图统计数据（三十分钟节点）*/
    @Autowired
    private IKGraphHalfhourDao kGraphHalfhourDao;

    /**
     * 批量新增k线图统计数据
     * @param kGraphFiveMinutesList k线图统计数据List
     * @return 操作成功：返回true，操作失败：返回false
     */
    public boolean insertKGraph(List<KGraphHalfhourDO> kGraphFiveMinutesList){
        return kGraphHalfhourDao.insertKGraph(kGraphFiveMinutesList);
    }

    /**
     * 查询最近的时间节点
     * @param currencyId 币种id
     * @return 操作成功：返回时间节点，操作失败：返回null
     */
    public Timestamp getKGraphLatelyTime(int currencyId){
        return kGraphHalfhourDao.getKGraphLatelyTime(currencyId);
    }

    /**
     * 查询最近时间节点的统计数据
     * @param currencyId 币种id
     * @return 操作成功：返回统计数据，操作失败：返回null
     */
    public KGraphHalfhourDO getKGraphLately(int currencyId){
        return kGraphHalfhourDao.getKGraphLately(currencyId);
    }

    /**
     * 查询最近时间节点的批量统计数据
     * @param currencyId 币种id
     * @param num 数量
     * @return 操作成功：返回统计数据List，操作失败：返回null
     */
    public List<KGraphHalfhourDO> listKGraphLately(int currencyId, int num){
        return kGraphHalfhourDao.listKGraphLately(currencyId, num);
    }
}
