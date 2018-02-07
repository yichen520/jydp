package com.iqmkj.utils;

import com.alibaba.fastjson.JSONObject;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import java.io.IOException;

/**
 * 发送HTTP请求的工具类
 * @author XEX
 *
 */
public class OKHttpUtil {

	/**
	 * 发送请求
	 * @param url 请求的URL地址
	 * @param formBody 发送的表单数据
	 * @return 请求成功：返回json； 请求失败返回null
	 */
	public static JSONObject postJson(String url, RequestBody formBody){
		//创建客户端
		OkHttpClient client = new OkHttpClient();
		
		//构建请求
		Request request = new Request.Builder().url(url).post(formBody).build();
		
		//设置返回值
		Response response = null;
		
		//发送请求
		try {
			response = client.newCall(request).execute();
		} catch (IOException e) {
			LogUtil.printErrorLog(e);
			LogUtil.printInfoLog("调用接口：" + url + " 异常，发送请求失败");
			return null;
		}
		if (response == null) {
			LogUtil.printInfoLog("调用接口：" + url + " 异常，未收到返回值");
			return null;
		}
		
		//返回值字符串
		String responseStr = null;
		//获取返回值
		try {
			responseStr = response.body().string();
		} catch (IOException e) {
			LogUtil.printErrorLog(e);
			LogUtil.printInfoLog("调用接口：" + url + " 异常，未收到返回值");
			return null;
		}
		
		//转换JSON格式
		JSONObject responseJson = new JSONObject();
		try {
			responseJson = JSONObject.parseObject(responseStr);
		} catch (Exception e) {
			LogUtil.printErrorLog(e);
			LogUtil.printInfoLog("调用接口：" + url + " 异常，返回值非JSON格式");
			return null;
		}
		
		return responseJson;
	}
}
