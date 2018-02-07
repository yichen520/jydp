package com.jydp.service;

import com.jydp.entity.BO.JsonObjectBO;
import com.jydp.entity.DO.system.SystemValidatePhoneDO;

/**
 * 系统手机验证
 * @author whx
 *
 */
public interface ISystemValidatePhoneService {

	/**
	 * 新增 系统验证
	 * @param systemValidatePhoneDO 待新增的 系统验证
	 * @return 新增成功：返回true，新增失败：返回false
	 */
	boolean insertSystemValidatePhone(SystemValidatePhoneDO systemValidatePhoneDO);

	/**
	 * 根据记录Id拿到 系统验证
	 * @param id 系统验证 记录Id
	 * @return 查询成功：返回系统验证，查询失败或无数据，返回null
	 */
	SystemValidatePhoneDO getSystemValidatePhoneById(long id);

	/**
	 * 添加手机验证记录
	 * @param phoneNumber 手机号码
	 * @param validateCode 验证码
	 * @param ipAddress 访问者的ip地址
	 * @return 操作成功:返回code=1, 操作失败:返回code!=1
	 */
	JsonObjectBO addValidatePhone(String phoneNumber, String validateCode, String ipAddress);

	/**
	 * 手机验证
	 * @param phoneNumber 手机号码
	 * @param validateCode 验证码
	 * @return 操作成功:返回code=1, 操作失败:返回code!=1
	 */
	JsonObjectBO validatePhone(String phoneNumber, String validateCode);

	/**
	 * 修改 系统验证
	 * @param systemValidatePhoneDO 系统验证
	 * @return 修改成功：返回true，修改失败：返回false
	 */
	boolean updateSystemValidatePhone(SystemValidatePhoneDO systemValidatePhoneDO);

}
