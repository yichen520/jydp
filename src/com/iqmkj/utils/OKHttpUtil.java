package com.iqmkj.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import okhttp3.*;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * 发送HTTP请求的工具类
 * @author XEX
 *
 */
public class OKHttpUtil {

	/** json请求格式 **/
	private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

	/**
	 * 发送表单格式请求(默认超时时间)
	 * @param url 请求的URL地址
	 * @param formBody 发送的表单数据
	 * @return 请求成功：返回json； 请求失败返回null
	 */
	public static JSONObject postJson(String url, RequestBody formBody){
		//创建客户端
		OkHttpClient client = new OkHttpClient.Builder().build();
		
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
			LogUtil.printInfoLog("调用接口：" + url + " 异常，返回值非JSON格式" + "返回值为 " + responseStr);
			return null;
		}

		return responseJson;
	}


	/**
	 * 发送表单格式请求（超时时间60）
	 * @param url 请求的URL地址
	 * @param formBody 发送的表单数据
	 * @return 请求成功：返回json； 请求失败返回null
	 */
	public static JSONObject postJsonTimeLonger(String url, RequestBody formBody){
		//创建客户端
		OkHttpClient client = new OkHttpClient.Builder()
				.readTimeout(60, TimeUnit.SECONDS)
				.connectTimeout(60, TimeUnit.SECONDS).build();

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
			LogUtil.printInfoLog("调用接口：" + url + " 异常，返回值非JSON格式" + "返回值为 " + responseStr);
			return JSONObject.parseObject("{'code':-1}");
		}

		return responseJson;
	}

	/**
	 * 发送json格式请求
	 * @param url 请求的URL地址
	 * @param requestJson 发送的json格式数据
	 * @return 请求成功：返回json； 请求失败返回null
	 */
	public static JSONObject postRequestJson(String url, JSONObject requestJson){
		//创建客户端
		OkHttpClient client = new OkHttpClient.Builder()
                .readTimeout(60, TimeUnit.SECONDS)
				.connectTimeout(60, TimeUnit.SECONDS).build();

		//发送的json字符串
		String requestJsonStr = requestJson.toJSONString();

		//构建body数据
		RequestBody requestBody = RequestBody.create(JSON, requestJsonStr);

		//构建请求
		Request request = new Request.Builder().url(url).post(requestBody).build();

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

	/**
	 * get获取json格式请求
	 * @param url 请求的URL地址
	 * @return 请求成功：返回json； 请求失败返回null，返回值非json格式返回json的code为-1
	 */
	public static JSONObject getRequestJson(String url){
		//创建客户端
		OkHttpClient client = new OkHttpClient.Builder()
                .readTimeout(30, TimeUnit.SECONDS)
                .connectTimeout(30, TimeUnit.SECONDS).build();

		//构建请求
		Request request = new Request.Builder().url(url).get().build();

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
			if (responseStr.equals("404 Not Found\n")){
				return null;
			}
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
			return JSONObject.parseObject("{'code':-1}");
		}

		return responseJson;
	}

	/**
	 * get获取jsonArray格式请求
	 * @param url 请求的URL地址
	 * @return 请求成功：返回json； 请求失败返回null，返回值非jsonArray格式返回size为0
	 */
	public static JSONArray getRequestJsonArrary(String url){
		//创建客户端
		OkHttpClient client = new OkHttpClient.Builder()
				.readTimeout(30, TimeUnit.SECONDS)
				.connectTimeout(30, TimeUnit.SECONDS).build();

		//构建请求
		Request request = new Request.Builder().url(url).get().build();

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
		JSONArray responseJson = new JSONArray();
		try {
			responseJson = JSONArray.parseArray(responseStr);
		} catch (Exception e) {
			LogUtil.printErrorLog(e);
			LogUtil.printInfoLog("调用接口：" + url + " 异常，返回值非JSON格式");
			return new JSONArray();
		}

		return responseJson;
	}
}
