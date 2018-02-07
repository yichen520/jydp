package com.jydp.other;

import com.iqmkj.utils.LogUtil;
import com.iqmkj.utils.StringUtil;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;

/**
 * 短信验证码工具类
 * @author gql
 *
 */
public class SendMessage {

	/**
	 * 创建短信验证码
	 * @return 生成的验证码字符串
	 */
	public static String createMessageCode(){
		// 生成随机类
		Random random = new Random();
		String sRand = "";
		for (int i = 0; i < 6; i++) {
			String rand = String.valueOf(random.nextInt(10));
			sRand += rand;
		}
		return sRand;
	}
	
	/**
	 * 获取短信验证码下发内容
	 * @param messageCode 验证码
	 * @param messageType 消息类型，1：其他
	 * @return 返回组装后的文本
	 */
	public static String getMessageCodeContent(String messageCode, int messageType){
		String messageContent = null;
		if(messageType == 1){
			messageContent = "短信验证码：" + messageCode +"，10分钟内有效。如非本人操作，请忽略。";
		}
		
		return messageContent;
	}
	
	/**
	 * 向手机发送短信
	 * @param phoneNumber 接收的手机号码
	 * @param messageContent 短信实际下发内容
	 * @return 发送成功：返回true，发送失败：返回false
	 */
    public static boolean send(String phoneNumber, String messageContent) {
        if (!StringUtil.isNotNull(phoneNumber) || !StringUtil.isNotNull(messageContent)) {
    		return false;
    	}
    	
    	try {
    		//短信服务商的短信发送的请求地址
    		String messageUrl = "http://www.gxt106.com/sms.aspx";
    		
        	String userid = "199";
        	String account = "SNCZK01";
        	String password = "sinan123";
        	
        	String content = "userid=" + userid + "&account=" + account + "&password=" + password
        							  + "&mobile=" + phoneNumber + "&content=" + messageContent + "&sendTime&action=send&extro=";
    		
			URL url=new URL(messageUrl);  
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream(),"UTF-8");
			osw.write(content);
			osw.flush();
			osw.close();
			
			//服务端反馈的数据
	        String strLine = "";
	        String strResponse = "";
	        InputStream in = conn.getInputStream();
	        BufferedReader reader = new  BufferedReader(new InputStreamReader(in));	
	        while((strLine = reader.readLine()) != null){
	        	strResponse += strLine + "\n";
	        }
	        
            if (StringUtil.isNotNull(strResponse)) {
	        	int startPostion = strResponse.indexOf("<returnstatus>");
	        	int endPostion = strResponse.indexOf("</returnstatus>");
	        	
	        	String subStr = strResponse.substring(startPostion, endPostion);
	        	if(subStr.indexOf("Success") != -1){
	        		StringBuffer logOut = new StringBuffer();
	    			logOut = new StringBuffer();
	        		logOut.append("\n");
	        		logOut.append("【发送短信】");
	        		logOut.append("\n");
	        		logOut.append("【收信人手机号】" + phoneNumber);
	        		logOut.append("\n");
	        		logOut.append("【短信内容】" + messageContent);
	        		logOut.append("\n");
	        		logOut.append("【发送结果】发送成功");
	        		logOut.append("\n");
                    LogUtil.printInfoLog(logOut.toString());
	        		
	        		return true;
	        	}
	        }
		}catch(Exception e){
            LogUtil.printErrorLog(e);
		}
    	
    	StringBuffer logOut = new StringBuffer();
		logOut = new StringBuffer();
		logOut.append("\n");
		logOut.append("【发送短信】");
		logOut.append("\n");
		logOut.append("【收信人手机号】" + phoneNumber);
		logOut.append("\n");
		logOut.append("【短信内容】" + messageContent);
		logOut.append("\n");
		logOut.append("【发送结果】发送失败");
		logOut.append("\n");
        LogUtil.printInfoLog(logOut.toString());
    	
    	return false;
    }
    
}
