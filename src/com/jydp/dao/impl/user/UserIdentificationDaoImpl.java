package com.jydp.dao.impl.user;

import com.jydp.dao.IUserIdentificationDao;
import org.springframework.stereotype.Repository;

import com.iqmkj.utils.LogUtil;
import com.jydp.entity.DO.user.UserIdentificationDO;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Description: 用户认证
 * Author: hht
 * Date: 2018-02-07 16:49
 */
@Repository
public class UserIdentificationDaoImpl implements IUserIdentificationDao {

    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    /**
     * 新增用户认证
     *
     * @param userIdentificationDO 用户认证
     * @return 操作成功：返回用户认证信息，操作失败：返回null
     */
    public UserIdentificationDO insertUserIdentification(UserIdentificationDO userIdentificationDO) {
        int result = 0;
        try {
            result = sqlSessionTemplate.insert("UserIdentification_insertUserIdentification", userIdentificationDO);
        } catch (Exception e) {
            LogUtil.printErrorLog(e);
        }
        if (result > 0 ) {
            return userIdentificationDO;
        } else {
            return null;
        }
    }

    /**
     * 查询用户认证信息
     *
     * @param id 记录Id
     * @return 操作成功：返回用户认证信息，操作失败：返回null
     */
    public UserIdentificationDO getUserIdentificationById(long id) {
        UserIdentificationDO result = null;
        try {
            result = sqlSessionTemplate.selectOne("UserIdentification_getUserIdentificationById", id);
        } catch (Exception e) {
            LogUtil.printErrorLog(e);
        }
        return result;
    }

    /**
     * 验证该身份证号是否已被使用
     *
     * @param userCertNo 身份证号码
     * @return 已被使用：返回true，未被使用：返回false
     */
    public boolean validateIdentification(String userCertNo) {
        int result = 0;
        try {
            result = sqlSessionTemplate.selectOne("UserIdentification_validateIdentification", userCertNo);
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
     * 查询用户最近的认证信息
     *
     * @param userId 用户Id
     * @return 操作成功：返回用户认证信息，操作失败：返回null
     */
    public UserIdentificationDO getUserIdentificationByUserIdLately(int userId) {
        UserIdentificationDO result = null;
        try {
            result = sqlSessionTemplate.selectOne("UserIdentification_getUserIdentificationByUserIdLately", userId);
        } catch (Exception e) {
            LogUtil.printErrorLog(e);
        }
        return result;
    }

    /**
     * 查询用户最近的认证信息
     *
     * @param userAccount 用户账号
     * @return 操作成功：返回用户认证信息，操作失败：返回null
     */
    public UserIdentificationDO getUserIdentificationByUserAccountLately(String userAccount) {
        UserIdentificationDO result = null;
        try {
            result = sqlSessionTemplate.selectOne("UserIdentification_getUserIdentificationByUserAccountLately", userAccount);
        } catch (Exception e) {
            LogUtil.printErrorLog(e);
        }
        return result;
    }

    /**
     * 查询实名认证信息总数（后台）
     *
     * @param userAccount 用户账号(可为null)
     * @param userPhone   手机号(可为null)
     * @param userCertType   证件类型，1:身份证，2：护照
     * @param identificationStatus   实名认证状态，1：待审核，2：审核通过，3：审核拒绝（查全部为0）
     * @param startTime   开始时间(可为null)
     * @param endTime     结束时间(可为null)
     * @return 操作成功：返回用户信息总数，操作失败：返回0
     */
    public int countUserIdentificationForBacker(String userAccount, String userPhone, int userCertType,
                                                int identificationStatus, Timestamp startTime, Timestamp endTime) {
        Map<String, Object> map = new HashMap<>();
        map.put("userAccount", userAccount);
        map.put("userPhone", userPhone);
        map.put("userCertType", userCertType);
        map.put("identificationStatus", identificationStatus);
        map.put("startTime", startTime);
        map.put("endTime", endTime);

        int result = 0;
        try {
            result = sqlSessionTemplate.selectOne("UserIdentification_countUserIdentificationForBacker", map);
        } catch (Exception e) {
            LogUtil.printErrorLog(e);
        }
        return result;
    }

    /**
     * 查询实名认证信息列表（后台）
     *
     * @param userAccount 用户账号(可为null)
     * @param userPhone   手机号(可为null)
     * @param userCertType   证件类型，1:身份证，2：护照
     * @param identificationStatus   实名认证状态，1：待审核，2：审核通过，3：审核拒绝（查全部为0）
     * @param startTime   开始时间(可为null)
     * @param endTime     结束时间(可为null)
     * @param pageNumber  当前页数
     * @param pageSize    查询条数
     * @return 操作成功 ：返回用户认证信息，操作失败：返回null
     */
    public List<UserIdentificationDO> listUserIdentificationForBacker(String userAccount, String userPhone, int userCertType,
                                                                      int identificationStatus, Timestamp startTime,
                                                                      Timestamp endTime, int pageNumber, int pageSize) {
        Map<String, Object> map = new HashMap<>();
        map.put("userAccount", userAccount);
        map.put("userPhone", userPhone);
        map.put("userCertType", userCertType);
        map.put("identificationStatus", identificationStatus);
        map.put("startTime", startTime);
        map.put("endTime", endTime);
        map.put("startNumber", pageNumber * pageSize);
        map.put("pageSize", pageSize);

        List<UserIdentificationDO> resultList = null;
        try {
            resultList = sqlSessionTemplate.selectList("UserIdentification_listUserIdentificationForBacker", map);
        } catch (Exception e) {
            LogUtil.printErrorLog(e);
        }
        return resultList;
    }

    /**
     * 修改用户实名认证状态
     * @param id 记录Id
     * @param identificationStatus 实名认证状态，1：待审核，2：审核通过，3：审核拒绝
     * @param identiTime 审核时间
     * @param remark 备注，可为null
     * @return 修改成功：返回true，修改失败：返回false
     */
    public boolean updateUserIdentificationStatus (long id, int identificationStatus, Timestamp identiTime, String remark) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("identificationStatus", identificationStatus);
        map.put("identiTime", identiTime);
        map.put("remark", remark);

        int result = 0;
        try {
            result = sqlSessionTemplate.update("UserIdentification_updateUserIdentificationStatus", map);
        } catch (Exception e) {
            LogUtil.printErrorLog(e);
        }

        if (result > 0) {
            return true;
        } else {
            return false;
        }
    }
}
