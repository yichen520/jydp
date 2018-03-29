package com.jydp.service.impl.kGraph;

import com.jydp.dao.IKGraphFourHoursDao;
import com.jydp.entity.DO.kgraph.KGraphFourHoursDO;
import com.jydp.entity.VO.TransactionGraphVO;
import com.jydp.service.IKGraphFourHoursService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

/**
 * k线图统计数据（四小时节点）
 * @author whx
 */
@Service("kGraphFourHoursService")
public class KGraphFourHoursServiceImpl implements IKGraphFourHoursService {

    /** k线图统计数据（四小时节点）*/
    @Autowired
    private IKGraphFourHoursDao kGraphFourHoursDao;

    /**
     * 批量新增k线图统计数据
     * @param kGraphFiveMinutesList k线图统计数据List
     * @return 操作成功：返回true，操作失败：返回false
     */
    public boolean insertKGraph(List<KGraphFourHoursDO> kGraphFiveMinutesList){
        return kGraphFourHoursDao.insertKGraph(kGraphFiveMinutesList);
        }

    /**
     * 查询最近的时间节点
     * @param currencyId 币种id
     * @return 操作成功：返回时间节点，操作失败：返回null
     */
    public Timestamp getKGraphLatelyTime(int currencyId){
        return kGraphFourHoursDao.getKGraphLatelyTime(currencyId);
    }

    /**
     * 查询最近时间节点的统计数据
     * @param currencyId 币种id
     * @return 操作成功：返回统计数据，操作失败：返回null
     */
    public KGraphFourHoursDO getKGraphLately(int currencyId){
        return kGraphFourHoursDao.getKGraphLately(currencyId);
    }

    /**
     * 查询最近时间节点的批量统计数据
     * @param currencyId 币种id
     * @param num 数量
     * @return 操作成功：返回统计数据List，操作失败：返回null
     */
    public List<TransactionGraphVO> listKGraphLately(int currencyId, int num){
        return kGraphFourHoursDao.listKGraphLately(currencyId, num);
    }

    /**
     * 更新节点数据
     * @param kGraph 节点数据
     * @return 操作成功：返回true，操作失败：返回false
     */
    public boolean updateKGraph(KGraphFourHoursDO kGraph) {
        return kGraphFourHoursDao.updateKGraph(kGraph);
    }

}
