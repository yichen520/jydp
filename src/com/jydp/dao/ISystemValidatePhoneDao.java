package com.jydp.dao;


import com.jydp.entity.DO.system.SystemValidatePhoneDO;

import java.sql.Timestamp;

/**
 * 系统手机验证
 * @author whx
 *
 */
public interface ISystemValidatePhoneDao {

	/**
	 * 新增 系统验证
	 * @param systemValidatePhoneDO 待新增的 系统验证
	 * @return 新增成功：返回true；新增失败：返回false
	 */
	boolean insertSystemValidatePhone(SystemValidatePhoneDO systemValidatePhoneDO);

	/**
	 * 根据记录Id拿到 系统验证
	 * @param id 系统验证 记录Id
	 * @return 查询成功：返回系统验证，查询失败，返回null
	 */
	SystemValidatePhoneDO getSystemValidatePhoneById(long id);

	/**
	 * 查询今日记录的手机验证次数
	 * @param phoneNumber 手机号码
	 * @param todayTime 今天凌晨的时间戳
	 * @return 今日记录的手机验证次数
	 */
	int getTodayValidateNumber(String phoneNumber, Timestamp todayTime);

	/**
	 * 查询今日记录的IP验证次数
	 * @param ipAddress 访问者的ip地址
	 * @param todayTime 今天凌晨的时间戳
	 * @return 今日记录的邮箱验证次数
	 */
	int getTodayValidateNumberOfIp(String ipAddress, Timestamp todayTime);

	/**
	 * 查询最新的手机验证记录信息
	 * @param phoneNumber 手机号码
	 * @return 查询成功：返回验证记录信息， 查询失败或为查询到数据：返回null
	 */
	SystemValidatePhoneDO getLastValidatePhone(String phoneNumber);

	/**
	 * 验证后修改验证信息
	 * @param id 验证记录Id
	 * @param validateTime 验证时的时间戳
	 * @return 操作成功：返回true，操作失败：返回false
	 */
	boolean updateValidateTime(long id, Timestamp validateTime);

	/**
	 * 修改 系统验证
	 * @param systemValidatePhoneDO 系统验证
	 * @return 修改成功：返回true，修改失败：返回false
	 */
	boolean updateSystemValidatePhone(SystemValidatePhoneDO systemValidatePhoneDO);
}
