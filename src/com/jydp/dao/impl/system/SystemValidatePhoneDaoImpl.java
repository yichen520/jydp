package com.jydp.dao.impl.system;

import com.iqmkj.utils.LogUtil;
import com.jydp.dao.ISystemValidatePhoneDao;
import com.jydp.entity.DO.system.SystemValidatePhoneDO;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

/**
 * 系统手机验证
 * @author whx
 *
 */
@Repository
public class SystemValidatePhoneDaoImpl implements ISystemValidatePhoneDao {

	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;

	/**
	 * 新增 系统验证
	 * @param systemValidatePhoneDO 待新增的 系统验证
	 * @return 新增成功：返回true；新增失败：返回false
	 */
	public boolean insertSystemValidatePhone(SystemValidatePhoneDO systemValidatePhoneDO) {
		int result = 0;

		try {
			result = sqlSessionTemplate.insert("SystemValidatePhone_insertSystemValidatePhone", systemValidatePhoneDO);
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
	 * 根据记录Id拿到 系统验证
	 * @param id 系统验证 记录Id
	 * @return 查询成功：返回 系统验证，查询失败，返回null
	 */
	public SystemValidatePhoneDO getSystemValidatePhoneById(long id) {
		SystemValidatePhoneDO systemValidatePhoneDO = null;

		try {
			systemValidatePhoneDO = sqlSessionTemplate.selectOne("SystemValidatePhone_getSystemValidatePhoneById", id);
		} catch (Exception e) {
			LogUtil.printErrorLog(e);
		}

		return systemValidatePhoneDO;
	}

	/**
	 * 查询今日记录的手机验证次数
	 * @param phoneNumber 手机号码
	 * @param todayTime 今天凌晨的时间戳
	 * @return 今日记录的手机验证次数
	 */
	public int getTodayValidateNumber(String phoneNumber, Timestamp todayTime) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("phoneNumber", phoneNumber);
		map.put("todayTime", todayTime);

		int resultNumber = 0;

		try {
			resultNumber = sqlSessionTemplate.selectOne("SystemValidatePhone_getTodayValidateNumber", map);
		} catch (Exception e) {
			LogUtil.printErrorLog(e);
		}

		return resultNumber;
	}

	/**
	 * 查询今日记录的IP验证次数
	 * @param ipAddress 访问者的ip地址
	 * @param todayTime 今天凌晨的时间戳
	 * @return 今日记录的邮箱验证次数
	 */
	public int getTodayValidateNumberOfIp(String ipAddress, Timestamp todayTime) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("ipAddress", ipAddress);
		map.put("todayTime", todayTime);

		int resultNumber = 0;

		try {
			resultNumber = sqlSessionTemplate.selectOne("SystemValidatePhone_getTodayValidateNumberOfIp", map);
		} catch (Exception e) {
			LogUtil.printErrorLog(e);
		}

		return resultNumber;
	}

	/**
	 * 查询最新的手机验证记录信息
	 * @param phoneNumber 手机号码
	 * @return 查询成功：返回验证记录信息， 查询失败或为查询到数据：返回null
	 */
	public SystemValidatePhoneDO getLastValidatePhone(String phoneNumber) {
		SystemValidatePhoneDO systemValidatePhoneDO = null;

		try {
			systemValidatePhoneDO = sqlSessionTemplate.selectOne("SystemValidatePhone_getLastValidatePhone", phoneNumber);
		} catch (Exception e) {
			LogUtil.printErrorLog(e);
		}

		return systemValidatePhoneDO;
	}

	/**
	 * 验证后修改验证信息
	 * @param id 验证记录Id
	 * @param validateTime 验证时的时间戳
	 * @return 操作成功：返回true，操作失败：返回false
	 */
	public boolean updateValidateTime(long id, Timestamp validateTime) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		map.put("validateTime", validateTime);

		int changeNumber = 0;

		try {
			changeNumber = sqlSessionTemplate.update("SystemValidatePhone_updateValidateTime", map);
		} catch (Exception e) {
			LogUtil.printErrorLog(e);
		}

		if (changeNumber > 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 修改 系统验证
	 * @param systemValidatePhoneDO 系统验证
	 * @return 修改成功：返回true，修改失败：返回false
	 */
	public boolean updateSystemValidatePhone(SystemValidatePhoneDO systemValidatePhoneDO) {
		int result = 0;

		try {
			result = sqlSessionTemplate.update("SystemValidatePhone_updateSystemValidatePhone", systemValidatePhoneDO);
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