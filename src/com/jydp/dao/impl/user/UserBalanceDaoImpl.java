package com.jydp.dao.impl.user;

import com.iqmkj.utils.LogUtil;
import com.jydp.dao.IUserBalanceDao;
import com.jydp.entity.DO.user.UserBalanceDO;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Description:用户账户记录
 * Author: hht
 * Date: 2018-02-07 15:33
 */
@Repository
public class UserBalanceDaoImpl implements IUserBalanceDao {

    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    /**
     * 新增用户账户记录
     * @param userBalanceDO 用户账户记录
     * @return 新增成功：返回true，新增失败：返回false
     */
    public boolean insertUserBalance(UserBalanceDO userBalanceDO) {
        int result = 0;
        try {
            result = sqlSessionTemplate.insert("UserBalance_insertUserBalance", userBalanceDO);
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
     * 查询用户账户记录
     * @param orderNo 记录号
     * @return 查询成功：返回用户账户记录，查询失败或无数据：返回null
     */
    public UserBalanceDO getUserBalanceByOrderNo(String orderNo) {
        UserBalanceDO result = null;
        try {
            result = sqlSessionTemplate.selectOne("UserBalance_getUserBalanceByOrderNo", orderNo);
        } catch (Exception e) {
            LogUtil.printErrorLog(e);
        }
        return result;
    }

    /**
     * 查询用户账户记录列表(web端)
     * @param userId 用户Id
     * @param pageNumber  当前页数
     * @param pageSize  每页条数
     * @return 查询成功：返回用户账户记录列表；查询失败：返回null
     */
    @Override
    public List<UserBalanceDO> getUserBalancelistForWeb(int userId, int pageNumber, int pageSize) {
        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        map.put("startNumber", pageNumber * pageSize);
        map.put("pageSize", pageSize);

        List<UserBalanceDO> userBalanceList = null;
        try {
            userBalanceList = sqlSessionTemplate.selectList("UserBalance_getUserBalancelistForWeb",map);
        } catch (Exception e) {
            LogUtil.printErrorLog(e);
        }
        return userBalanceList;
    }

    /**
     * 根据userId查询用户记录总数(web端)
     * @param userId 用户Id
     * @return 查询成功：返回用户账户记录总数；查询失败：返回0
     */
    @Override
    public int countUserBalanceForWeb(int userId) {

        int result = 0;

        try {
            result = sqlSessionTemplate.selectOne("UserBalance_countUserBalanceForWeb",userId);
        }catch (Exception e) {
            LogUtil.printErrorLog(e);
        }
        return result;
    }

    /**
     * 根据userBalanceList批量插入用户记录
     * @param userBalanceList 用户记录集合
     * @return 成功：true；查询失败：false
     */
    public boolean insertUserBalanceList(List<UserBalanceDO>userBalanceList){
        Map<String, Object> map = new HashMap<>();
        map.put("userBalanceList", userBalanceList);
        int result = 0;

        try {
            result = sqlSessionTemplate.insert("UserBalance_insertUserBalanceList",map);
        }catch (Exception e) {
            LogUtil.printErrorLog(e);
        }

        if (result == userBalanceList.size()) {
            return true;
        } else {
            return false;
        }

    }
}
