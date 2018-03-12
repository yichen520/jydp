package com.jydp.service.impl.system;

import com.iqmkj.utils.DateUtil;
import com.jydp.dao.ISystemValidatePhoneDao;
import com.jydp.entity.BO.JsonObjectBO;
import com.jydp.entity.DO.system.SystemValidatePhoneDO;
import com.jydp.service.ISystemValidatePhoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

/**
 * 系统手机验证
 * @author whx
 *
 */
@Service("systemValidatePhoneService")
public class SystemValidatePhoneServiceImpl implements ISystemValidatePhoneService {

	/**系统验证-手机号*/
	@Autowired
	private ISystemValidatePhoneDao systemValidatePhoneDao;

	/**
	 * 新增 系统验证
	 * @param systemValidatePhoneDO 待新增的 系统验证
	 * @return 新增成功：返回true，新增失败：返回false
	 */
	public boolean insertSystemValidatePhone(SystemValidatePhoneDO systemValidatePhoneDO) {
		return systemValidatePhoneDao.insertSystemValidatePhone(systemValidatePhoneDO);
	}

	/**
	 * 根据记录Id拿到 系统验证
	 * @param id 系统验证 记录Id
	 * @return 查询成功：返回 系统验证，查询失败，返回null
	 */
	public SystemValidatePhoneDO getSystemValidatePhoneById(long id) {
		return systemValidatePhoneDao.getSystemValidatePhoneById(id);
	}

	/**
	 * 添加手机验证记录
	 * @param phoneNumber 手机号码
	 * @param validateCode 验证码
	 * @param ipAddress 访问者的ip地址
	 * @return 操作成功:返回code=1, 操作失败:返回code!=1
	 */
	public JsonObjectBO addValidatePhone(String phoneNumber, String validateCode, String ipAddress) {
		JsonObjectBO jsonObjectBO = new JsonObjectBO();

		Timestamp todayTime = DateUtil.stringToTimestamp(DateUtil.longToTimeStr(DateUtil.lingchenLong(), DateUtil.dateFormat2));
		int todayNumber = systemValidatePhoneDao.getTodayValidateNumberOfIp(ipAddress, todayTime);
		// 同一IP超过发送次数限制
		if (todayNumber >= 100) {
			jsonObjectBO.setCode(103002);
			jsonObjectBO.setMessage("今日发送验证码次数已超限");
			return jsonObjectBO;
		}

		todayNumber = systemValidatePhoneDao.getTodayValidateNumber(phoneNumber, todayTime);
		// 超过发送验证码次数限制
		if (todayNumber >= 3) {
			jsonObjectBO.setCode(103002);
			jsonObjectBO.setMessage("今日发送验证码次数已超限");
			return jsonObjectBO;
		}

		// 组装数据
		SystemValidatePhoneDO systemValidatePhoneDO = new SystemValidatePhoneDO();
		systemValidatePhoneDO.setPhoneNo(phoneNumber);
		systemValidatePhoneDO.setValidateCode(validateCode);
		systemValidatePhoneDO.setIpAddress(ipAddress);
		systemValidatePhoneDO.setAddTime(DateUtil.getCurrentTime());
		systemValidatePhoneDO.setValidateStatus(1);

		boolean resultResult = systemValidatePhoneDao.insertSystemValidatePhone(systemValidatePhoneDO);
		if (resultResult) {
			jsonObjectBO.setCode(1);
			jsonObjectBO.setMessage("添加验证码成功");
			return jsonObjectBO;
		} else {
			jsonObjectBO.setCode(103002);
			jsonObjectBO.setMessage("添加验证码失败");
			return jsonObjectBO;
		}
	}

	/**
	 * 手机验证
	 * @param phoneNumber 手机号码
	 * @param validateCode 验证码
	 * @return 操作成功:返回code=1, 操作失败:返回code!=1
	 */
	public JsonObjectBO validatePhone(String phoneNumber, String validateCode) {
		JsonObjectBO jsonObjectBO = new JsonObjectBO();

		SystemValidatePhoneDO systemValidatePhoneDO = systemValidatePhoneDao.getLastValidatePhone(phoneNumber);
		if (systemValidatePhoneDO == null) {
			jsonObjectBO.setCode(2);
			jsonObjectBO.setMessage("验证码不存在");
			return jsonObjectBO;
		}

		if (systemValidatePhoneDO.getValidateStatus() != 1 || (DateUtil.getCurrentTimeMillis() - systemValidatePhoneDO.getAddTime().getTime()) > 10 * 60 * 1000) {
			jsonObjectBO.setCode(2);
			jsonObjectBO.setMessage("验证码已失效");
			return jsonObjectBO;
		}

		if (systemValidatePhoneDO.getValidateCode().equals(validateCode)) {
			systemValidatePhoneDao.updateValidateTime(systemValidatePhoneDO.getId(), DateUtil.getCurrentTime());

			jsonObjectBO.setCode(1);
			jsonObjectBO.setMessage("验证成功");
			return jsonObjectBO;
		} else {
			jsonObjectBO.setCode(2);
			jsonObjectBO.setMessage("验证码错误");
			return jsonObjectBO;
		}
	}

	/**
	 * 修改 系统验证
	 * @param systemValidatePhoneDO 系统验证
	 * @return 修改成功：返回true，修改失败：返回false
	 */
	public boolean updateSystemValidatePhone(SystemValidatePhoneDO systemValidatePhoneDO) {
		return systemValidatePhoneDao.updateSystemValidatePhone(systemValidatePhoneDO);
	}


}
