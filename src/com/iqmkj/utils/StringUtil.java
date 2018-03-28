package com.iqmkj.utils;

/**
 * 字符串操作
 * @author gql
 *
 */
public class StringUtil {
	/**
	 * 判断字符串是否为空
	 * @param paramStr 待判断字符串
	 * @return 为空返回true 不为空返回false
	 */
	public static boolean isNull(String paramStr){
		boolean result = false;
		if(paramStr == null || paramStr.isEmpty()){
			result = true;
			return result;
		}
		paramStr = paramStr.trim();
		if("".equals(paramStr) || "null".equals(paramStr) || "NULL".equals(paramStr)){
			result = true;
			return result;
		}
		return result;
	}
	/**
	 * 字符串非空判断
	 * 属于空的：（NULL，‘’， ‘null’,'NULL'）
	 * @param paramStr  待判断的字符串
	 * @return  true：非空，false：空
	 */
	public static boolean isNotNull(String paramStr){
		if(paramStr == null){
			return false;
		}
		if(paramStr.isEmpty()){
			return false;
		}
		paramStr = paramStr.trim();
		if(paramStr.equals("")){
			return false;
		}
		if(paramStr.equals("null")){
			return false;
		}
		if(paramStr.equals("NULL")){
			return false;
		}
		
		return true;
	}
	
	/**
	 * 对字符串进行处理，把'null'、'NULL'处理成''空字符串，非空的字符串会执行trim
	 * @param paramStr  待处理的字符串
	 * @return  处理后的字符串
	 */
	public static String stringNullHandle(String paramStr){
		if(isNotNull(paramStr)){
			return paramStr.trim();
		}else{
			return "";
		}
	}
	
}
