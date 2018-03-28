package com.iqmkj.utils;

import com.jydp.entity.BO.JsonObjectBO;
import org.springframework.web.multipart.MultipartFile;

/**
 * 文件格式判定
 * @author gql
 *
 */
public class FileFomat {

	/**
	 * 判断是否是图片
	 * @param fileName 图片名称
	 * @return true：是图片，false：不是图片
	 */
	public static boolean isImage(String fileName){
		if(!StringUtil.isNotNull(fileName)){
			return false;
		}
		
		try{
			int num = fileName.lastIndexOf(".");
			String suffixStr = fileName.substring(num + 1).toLowerCase();
			
			if(suffixStr.equals("bmp") || suffixStr.equals("png") || suffixStr.equals("jpg") || suffixStr.equals("jpeg")){
				return true;
			}else{
				return false;
			}
		}catch(Exception e){
			LogUtil.printErrorLog(e);
			return false;
		}
	}

    /**
     * 验证图片格式 和限制图片大小（上传文件）
     *
     * @param uploadImg 上传的文件
     * @param max       图片最大大小（单位：kb）
     * @return 验证通过：返回code=1，验证失败：返回code!=1
     */
    public static JsonObjectBO checkImageFile(MultipartFile uploadImg, int max) {
        JsonObjectBO responseJson = new JsonObjectBO();

        String fileName = uploadImg.getOriginalFilename();
        boolean isImage = FileFomat.isImage(fileName);
        if (!isImage) {
            responseJson.setCode(3);
            responseJson.setMessage("请上传jpg、jpeg、png格式的图片");
            return responseJson;
        }
        /*//根据图片内容、长宽判断是否为图片文件
        InputStream inputStream = null;
        try {
            inputStream = uploadImg.getInputStream();
            BufferedImage img = ImageIO.read(inputStream);
            if(img == null || img.getWidth(null) <= 0 || img.getHeight(null) <= 0){
                responseJson.setCode(3);
                responseJson.setMessage("请上传图片文件");
                return responseJson;
            }
        } catch (IOException e) {
            LogUtil.printErrorLog(e);
        }*/

        if (uploadImg.getSize() >= max * 1024) {
            responseJson.setCode(3);
            responseJson.setMessage("您的证件照太大了");
            return responseJson;
        }

        responseJson.setCode(1);
        responseJson.setMessage("验证通过");
        return responseJson;
    }

}
