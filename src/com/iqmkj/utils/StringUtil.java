package com.iqmkj.utils;

import com.jydp.entity.BO.JsonObjectBO;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    /**
     * 验证身份证姓名和身份证号码
     *
     * @param userName   身份证姓名
     * @param userCertNo 身份证号码
     * @return 验证通过：返回code=1，验证失败：返回code!=1
     */
    public static JsonObjectBO validateNameAndCertNo(String userName, String userCertNo) {
        JsonObjectBO responseJson = new JsonObjectBO();
        if (!isNotNull(userName) || !isNotNull(userCertNo)) {
            responseJson.setCode(2);
            responseJson.setMessage("空参数");
            return responseJson;
        }

        if (userName.length() < 2 || userName.length() > 16) {
            responseJson.setCode(3);
            responseJson.setMessage("请输入长度为2到16的姓名！");
            return responseJson;
        }
        //姓名校验/^[\u4e00-\u9fa5]+(·[\u4e00-\u9fa5]+)*$/
        Pattern patternName = Pattern.compile("[\\u4e00-\\u9fa5]+(·[\\u4e00-\\u9fa5]+)*");
        Matcher matcherName = patternName.matcher(userName);
        if (!matcherName.matches()) {
            responseJson.setCode(3);
            responseJson.setMessage("姓名格式不正确");
            return responseJson;
        }

        //身份证校验
        Pattern pattern1 = Pattern.compile("^(\\d{6})(19|20)(\\d{2})(0[1-9]|1[0-2])(0[1-9]|[1-2][0-9]|3[0-1])(\\d{3})(\\d|X|x)?$"); //粗略的校验
        Matcher matcher = pattern1.matcher(userCertNo);
        if (!matcher.matches()) {
            responseJson.setCode(3);
            responseJson.setMessage("身份证号码有误！");
            return responseJson;
        }

        // 1-17位相乘因子数组
        int[] factor = {7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2};
        // 18位随机码数组
        char[] random = "10X98765432".toCharArray();
        // 计算1-17位与相应因子乘积之和
        int total = 0;
        char[] userCertNoArray = userCertNo.toCharArray();
        for (int i = 0; i < 17; i++) {
            int certNoNum = Character.getNumericValue(userCertNoArray[i]);
            total += certNoNum * factor[i];
        }
        if (userCertNoArray[17] == 'x') {
            userCertNoArray[17] = 'X';
        }
        // 判断随机码是否相等
        char r = random[total % 11];
        if (r != userCertNoArray[17]) {
            responseJson.setCode(3);
            responseJson.setMessage("身份证号码错误");
            return responseJson;
        }

        responseJson.setCode(1);
        responseJson.setMessage("验证通过");
        return responseJson;
    }

    /**
     * 处理页面传递的特殊字符，将<>()&;:/\'"替换为" "
     *
     * @param source 处理前的字符串
     * @return 处理后的字符串
     */
    public static String rightfulString(String source) {
        if (source == null) {
            return "";
        }
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < source.length(); i++) {
            char c = source.charAt(i);
            switch (c) {
                case '<':
                    buffer.append(" ");
                    break;
                case '>':
                    buffer.append(" ");
                    break;
                case '(':
                    buffer.append(" ");
                    break;
                case ')':
                    buffer.append(" ");
                    break;
                case '&':
                    buffer.append(" ");
                    break;
                case ':':
                    buffer.append(" ");
                    break;
                case ';':
                    buffer.append(" ");
                    break;
                case '\'':
                    buffer.append(" ");
                    break;
                case '\"':
                    buffer.append(" ");
                    break;
                case '\\':
                    buffer.append(" ");
                    break;
                case '/':
                    buffer.append(" ");
                    break;
                case '*':
                    buffer.append(" ");
                    break;
                default:
                    buffer.append(c);
            }
        }
        return buffer.toString();
    }
}
