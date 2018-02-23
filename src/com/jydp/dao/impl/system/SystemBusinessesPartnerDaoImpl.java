package com.jydp.dao.impl.system;

import com.iqmkj.utils.LogUtil;
import com.jydp.dao.ISystemBusinessesPartnerDao;
import com.jydp.entity.DO.system.SystemBusinessesPartnerDO;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 合作商家
 * @author zym
 *
 */
@Repository
public class SystemBusinessesPartnerDaoImpl implements ISystemBusinessesPartnerDao {

    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    /**
     * web用户端查询合作商家
     * @return 查询成功：返回合作商家列表；查询失败：返回null
     */
    @Override
    public List<SystemBusinessesPartnerDO> getSystemBusinessesPartnerForWeb() {
        List<SystemBusinessesPartnerDO> systemBusinessesPartnerList = null;

        try {
            systemBusinessesPartnerList = sqlSessionTemplate.selectList("SystemBusinessesPartner_getSystemBusinessesPartnerForWeb");
        } catch (Exception e) {
            LogUtil.printErrorLog(e);
        }
        return systemBusinessesPartnerList;
    }

    /**
     * 新增 合作商家
     * @param systemBusinessesPartnerDO 待新增的 合作商家
     * @return 新增成功：返回true，新增失败：返回false
     */
    public boolean insertSystemBusinessesPartner(SystemBusinessesPartnerDO systemBusinessesPartnerDO) {
        int result = 0;

        try {
            result = sqlSessionTemplate.insert("SystemBusinessesPartner_insertSystemBusinessesPartner", systemBusinessesPartnerDO);
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
     * 根据记录Id拿到 合作商家
     * @param id 合作商家 记录Id
     * @return 查询成功：返回 合作商家，查询失败，返回null
     */
    public SystemBusinessesPartnerDO getSystemBusinessesPartnerById(int id) {
        SystemBusinessesPartnerDO systemBusinessesPartnerDO = null;

        try {
            systemBusinessesPartnerDO = sqlSessionTemplate.selectOne("SystemBusinessesPartner_getSystemBusinessesPartnerById", id);
        } catch (Exception e) {
            LogUtil.printErrorLog(e);
        }

        return systemBusinessesPartnerDO;
    }

    /**
     * 获取商家总数
     * @return 查询成功：返回总数，查询失败：返回0
     */
    public int countSystemBusinessesPartner() {
        int result = 0;

        try {
            result = sqlSessionTemplate.selectOne("SystemBusinessesPartner_countSystemBusinessesPartner");
        } catch (Exception e) {
            LogUtil.printErrorLog(e);
        }

        return result;
    }

    /**
     * 分页查询合作商家
     * @param pageNumber 查询的页面pageSize 每页的信息数量
     * @return 查询成功：返回合作商家列表，查询失败：返回 null
     */
    public List<SystemBusinessesPartnerDO> listSystemBusinessesPartnerByPage(int pageNumber, int pageSize) {
        List<SystemBusinessesPartnerDO> systemBusinessesPartnerList = null;

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("startNumber", pageNumber*pageSize);
        map.put("pageSize", pageSize);

        try {
            systemBusinessesPartnerList = sqlSessionTemplate.selectList("SystemBusinessesPartner_listSystemBusinessesPartnerByPage", map);
        } catch (Exception e) {
            LogUtil.printErrorLog(e);
        }

        return systemBusinessesPartnerList;
    }

    /**
     * 修改 合作商家
     * @param systemBusinessesPartnerDO 合作商家
     * @return 修改成功：返回true，修改失败：返回false
     */
    public boolean updateSystemBusinessesPartner(SystemBusinessesPartnerDO systemBusinessesPartnerDO) {
        int result = 0;

        try {
            result = sqlSessionTemplate.update("SystemBusinessesPartner_updateSystemBusinessesPartner", systemBusinessesPartnerDO);
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
     * 删除 合作商家
     * @param id 合作商家 的记录id
     * @return 删除成功：返回true，删除失败：返回false
     */
    public boolean deleteSystemBusinessesPartner(int id) {
        int result = 0;

        try {
            result = sqlSessionTemplate.delete("SystemBusinessesPartner_deleteSystemBusinessesPartner", id);
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
