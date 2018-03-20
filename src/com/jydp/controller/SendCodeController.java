package com.jydp.controller;

import com.alibaba.fastjson.JSONObject;
import com.iqmkj.utils.IpAddressUtil;
import com.iqmkj.utils.StringUtil;
import com.jydp.entity.BO.JsonObjectBO;
import com.jydp.other.SendMessage;
import com.jydp.service.ISystemValidatePhoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;


/**
 * 发送验证码
 * @author whx
 *
 */
@Controller
@RequestMapping("/sendCode")
@Scope(value="prototype")
public class SendCodeController {
	
	/** 手机验证 */
	@Autowired
	public ISystemValidatePhoneService validatePhoneService;
	
	/**
     * 发送手机验证码
     */
	@RequestMapping(value="/sendPhoneCode", method= RequestMethod.POST)
	public @ResponseBody
    JSONObject sendPhoneCode(HttpServletRequest request, @RequestBody String requestJsonString){
		JSONObject resultJson = new JSONObject();

		String phoneNumber = StringUtil.stringNullHandle(request.getParameter("phoneNumber"));
		String ipAddress = IpAddressUtil.getIpAddress(request);
		if(!StringUtil.isNotNull(phoneNumber) || !StringUtil.isNotNull(ipAddress)){
			resultJson.put("code", 3);
			resultJson.put("message", "参数为空！");
			return resultJson;
		}
		
		String messageCode = SendMessage.createMessageCode();
		
		JsonObjectBO addValidatePhone = validatePhoneService.addValidatePhone(phoneNumber, messageCode, ipAddress);
		if(addValidatePhone.getCode() == 1){
            boolean sendBoo = false;

            if (phoneNumber.substring(0, 3).equals("+86")) {
                sendBoo = SendMessage.sendChina(phoneNumber, SendMessage.getMessageCodeContent(messageCode, 1));
            } else {
                sendBoo = SendMessage.sendInternational(phoneNumber, SendMessage.getEnMessageCodeContent(messageCode, 1));
            }

			if(sendBoo == false){
				resultJson.put("code", 5);
				resultJson.put("message", "短信验证码发送失败！");
				return resultJson;
			}else{
				resultJson.put("code", 1);
				resultJson.put("message", "短信验证码发送成功！");
				return resultJson;
			}
		}else{
			resultJson.put("code", addValidatePhone.getCode());
			resultJson.put("message", addValidatePhone.getMessage());
			return resultJson;
		}
	}
	
}
