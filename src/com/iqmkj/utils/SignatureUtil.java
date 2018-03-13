package com.iqmkj.utils;

import java.util.Map;
import java.util.TreeMap;

/**
 * 盛源链接口参数加密
 * 
 */
public class SignatureUtil {
	
	/**
	 * 对盛源链接口参数进行签名加密
	 * @param map 需要加密的参数
	 * @param key 秘钥
	 * @return 加密签名 
	 */
	public static String getSign(TreeMap<String, String> map, String key){
		StringBuilder resultKey = new StringBuilder();
		
		//map遍历参数拼接
        for(Map.Entry<String,String> entry : map.entrySet()){
            if(StringUtil.isNotNull(entry.getValue())){
            	resultKey.append(entry.getKey() + "=" + entry.getValue() + "&");
            }
        }
        
        //秘钥拼接
        resultKey.append(key);
        //MD5加密
        return MD5Util.md5Str(resultKey.toString());
    }
}
