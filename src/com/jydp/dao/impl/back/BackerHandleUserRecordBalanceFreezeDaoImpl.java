package com.jydp.dao.impl.back;

import com.iqmkj.utils.LogUtil;
import com.jydp.dao.IBackerHandleUserRecordBalanceFreezeDao;
import com.jydp.entity.DO.back.BackerHandleUserRecordBalanceFreezeDO;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 后台管理员增减用户冻结余额记录
 * @author sy
 */
@Repository
public class BackerHandleUserRecordBalanceFreezeDaoImpl implements IBackerHandleUserRecordBalanceFreezeDao {
    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    /**
     * 新增 后台管理员增减用户冻结余额记录
     * @param backerHandleUserRecordBalanceFreeze 待新增的 后台管理员增减用户冻结余额记录
     * @return 新增成功：true，新增失败：返回false
     */
    public boolean insertBackerHandleUserRecordBalanceFreeze(BackerHandleUserRecordBalanceFreezeDO backerHandleUserRecordBalanceFreeze){
        int result = 0;

        try {
            result = sqlSessionTemplate.insert("BackerHandleUserRecordBalanceFreeze_insertBackerHandleUserRecordBalanceFreeze",
                    backerHandleUserRecordBalanceFreeze);
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
     * 根据筛选条件获取后台增减用户余额的记录
     * @param userAccount  用户账号
     * @param typeHandle  操作类型
     * @param backerAccount  操作管理员账号
     * @param startAddTime  开始添加时间
     * @param endAddTime  结束添加时间
     * @param pageNumber 查询的页码
     * @param pageSize 每页的信息数量
     * @return  操作成功：返回记录集合，操作失败：返回null
     */
    public List<BackerHandleUserRecordBalanceFreezeDO> getUserRecordBalanceFreezeList(String userAccount, int typeHandle, String backerAccount,
                                                                          Timestamp startAddTime, Timestamp endAddTime, int pageNumber, int pageSize){
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("userAccount", userAccount);
        map.put("typeHandle", typeHandle);
        map.put("backerAccount", backerAccount);
        map.put("startAddTime", startAddTime);
        map.put("endAddTime", endAddTime);
        map.put("startNumber", pageSize*pageNumber);
        map.put("pageSize", pageSize);
        List<BackerHandleUserRecordBalanceFreezeDO> resultList = null;

        try {
            resultList = sqlSessionTemplate.selectList("BackerHandleUserRecordBalanceFreeze_getUserRecordBalanceFreezeList", map);
        } catch (Exception e) {
            LogUtil.printErrorLog(e);
        }

        return resultList;
    }

    /**
     * 根据筛选条件获取后台增减用户余额的记录数量
     * @param userAccount  用户账号
     * @param typeHandle  操作类型
     * @param backerAccount  操作管理员账号
     * @param startAddTime  开始添加时间
     * @param endAddTime  结束添加时间
     * @return  操作成功：返回记录集合数量，操作失败：返回0
     */
    public int getUserRecordBalanceFreezeNumber(String userAccount, int typeHandle, String backerAccount,
                                          Timestamp startAddTime, Timestamp endAddTime){
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("userAccount", userAccount);
        map.put("typeHandle", typeHandle);
        map.put("backerAccount", backerAccount);
        map.put("startAddTime", startAddTime);
        map.put("endAddTime", endAddTime);

        int result = 0;

        try {
            result = sqlSessionTemplate.selectOne("BackerHandleUserRecordBalanceFreeze_getUserRecordBalanceFreezeNumber", map);
        } catch (Exception e) {
            LogUtil.printErrorLog(e);
        }

        return result;
    }
}