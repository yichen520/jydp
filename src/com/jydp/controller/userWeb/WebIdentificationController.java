package com.jydp.controller.userWeb;


import com.iqmkj.utils.*;
import com.jydp.entity.BO.JsonObjectBO;
import com.jydp.entity.DO.user.UserDO;
import com.jydp.entity.DO.user.UserIdentificationDO;
import com.jydp.interceptor.UserWapInterceptor;
import com.jydp.service.IUserIdentificationImageService;
import com.jydp.service.IUserIdentificationService;
import com.jydp.service.IUserService;
import config.FileUrlConfig;
import config.SystemMessageConfig;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author yqz
 * 实名认证
 */
@RestController
@RequestMapping("/web/identification")
public class WebIdentificationController {

    /** 用户认证 */
    @Autowired
    private IUserIdentificationService userIdentificationService;

    /** 用户账号 */
    @Autowired
    private IUserService userService;

    /** 用户认证详情图 */
    @Autowired
    private IUserIdentificationImageService userIdentificationImageService;

    /**
     * 获取用户状态
     */
    @RequestMapping(value = "/getState")
    public JsonObjectBO forgetPassword(@Param("userAccount") String userAccount) {
        JsonObjectBO responseJson = new JsonObjectBO();
        if(!checkValue(userAccount)){
            responseJson.setCode(SystemMessageConfig.SYSTEM_CODE_PARAM_ERROR);
            responseJson.setMessage(SystemMessageConfig.SYSTEM_MESSAGE_PARAM_ERROR);
            return responseJson;
        }

        UserDO userDO = userService.getUserByUserAccount(userAccount);

        // 用户不存在
        if(userDO == null){
            responseJson.setCode(SystemMessageConfig.USER_ISEXIST_CODE);
            responseJson.setMessage(SystemMessageConfig.USER_ISEXIST_MESSAGE);
            return responseJson;
        }

        // 待审核
        if (userDO.getAuthenticationStatus() == 1) {
            responseJson.setCode(SystemMessageConfig.NOADOPT_CODE);
            responseJson.setMessage(SystemMessageConfig.NOADOPT_MESSAGE);
            return responseJson;
        }

        // 审核拒绝，进入查看实名认证信息页
        if (userDO.getAuthenticationStatus() == 3) {
            responseJson.setCode(SystemMessageConfig.REFUE_CODE);
            responseJson.setMessage(SystemMessageConfig.REFUE_MESSAGE);
            return responseJson;
        }
        // 参数错误，进入登录页面
        responseJson.setCode(SystemMessageConfig.SYSTEM_CODE_PARAM_ERROR);
        responseJson.setMessage(SystemMessageConfig.SYSTEM_MESSAGE_PARAM_ERROR);
        return responseJson;
    }

    /**
     * 添加认证
     */
    @RequestMapping(value = "/addIdentification")
    @ResponseBody
    public JsonObjectBO forgetPassword(HttpServletRequest request) {
        JsonObjectBO responseJson = new JsonObjectBO();
        boolean handleFrequent = UserWapInterceptor.handleFrequent(request);
        if (handleFrequent) {
            responseJson.setCode(SystemMessageConfig.FREQUENT_OPERATION_CODE);
            responseJson.setMessage(SystemMessageConfig.FREQUENT_OPERATION_MESSAGE);
            return responseJson;
        }

        //参数处理
        String userAccount = StringUtil.stringNullHandle(request.getParameter("userAccount"));
        String userName = StringUtil.stringNullHandle(request.getParameter("userName"));
        String userCertTypeStr = StringUtil.stringNullHandle(request.getParameter("userCertType"));
        String userCertNo = StringUtil.stringNullHandle(request.getParameter("userCertNo"));
        // 判断是否为空
        if (!StringUtil.isNotNull(userAccount) || !StringUtil.isNotNull(userCertTypeStr)
                ||!StringUtil.isNotNull(userName) || !StringUtil.isNotNull(userCertNo)) {
            responseJson.setCode(SystemMessageConfig.PARAMETER_ISNULL_CODE);
            responseJson.setMessage(SystemMessageConfig.PARAMETER_ISNULL_MESSAGE);
            return responseJson;
        }
        // 验证证件类型
        if(!userCertTypeStr.equals("1") && !userCertTypeStr.equals("2")){
            responseJson.setCode(SystemMessageConfig.SYSTEM_CODE_PARAM_ERROR);
            responseJson.setMessage(SystemMessageConfig.SYSTEM_MESSAGE_PARAM_ERROR);
            return responseJson;
        }
        // 验证姓名是否合法
        if(userCertTypeStr.equals("1")){
            if(!checkName(userName)){
                responseJson.setCode(SystemMessageConfig.SYSTEM_CODE_PARAM_ERROR);
                responseJson.setMessage(SystemMessageConfig.SYSTEM_MESSAGE_PARAM_ERROR);
                return responseJson;
            }
        }
        if(userCertTypeStr.equals("2")){
            if(!checkStr(userName)){
                responseJson.setCode(SystemMessageConfig.SYSTEM_CODE_PARAM_ERROR);
                responseJson.setMessage(SystemMessageConfig.SYSTEM_MESSAGE_PARAM_ERROR);
                return responseJson;
            }
        }
        // 验证账号和证件号是否合法
        if(!checkValue(userAccount) || !checkValue(userCertNo) ){
            responseJson.setCode(SystemMessageConfig.SYSTEM_CODE_PARAM_ERROR);
            responseJson.setMessage(SystemMessageConfig.SYSTEM_MESSAGE_PARAM_ERROR);
            return responseJson;
        }
        // 验证长度
        if (userName.length() > 16 || userCertNo.length() < 6 || userCertNo.length() > 18) {
            responseJson.setCode(SystemMessageConfig.SYSTEM_CODE_PARAM_ERROR);
            responseJson.setMessage(SystemMessageConfig.SYSTEM_MESSAGE_PARAM_ERROR);
            return responseJson;
        }

        int userCertType = Integer.parseInt(userCertTypeStr);
        if (userCertType == 1) {
            //证件类型为身份证：姓名，身份证 格式判断
            JsonObjectBO validateJson = StringUtil.validateNameAndCertNo(userName, userCertNo);
            if (validateJson.getCode() != 1) {
                responseJson.setCode(validateJson.getCode());
                responseJson.setMessage(validateJson.getMessage());
                return responseJson;
            }
        } else if (userCertType == 2) {
            //证件类型为护照：非法字符过滤
            userName = StringUtil.rightfulString(userName);
            userCertNo = StringUtil.rightfulString(userCertNo);
        } else {
            responseJson.setCode(SystemMessageConfig.IFICATION_FAIL_CODE);
            responseJson.setMessage(SystemMessageConfig.IFICATION_FAIL_MESSAGE);
            return responseJson;
        }

        // 转型为MultipartHttpRequest：图片处理
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile frontImg = multipartRequest.getFile("frontImg");
        MultipartFile backImg = multipartRequest.getFile("backImg");
        if (frontImg == null || frontImg.isEmpty() || frontImg.getSize() <= 0) {
            responseJson.setCode(SystemMessageConfig.PARAMETER_ISNULL_CODE);
            responseJson.setMessage(SystemMessageConfig.PARAMETER_ISNULL_MESSAGE);
            return responseJson;
        }
        if (backImg == null || backImg.isEmpty() || backImg.getSize() <= 0) {
            responseJson.setCode(SystemMessageConfig.PARAMETER_ISNULL_CODE);
            responseJson.setMessage(SystemMessageConfig.PARAMETER_ISNULL_MESSAGE);
            return responseJson;
        }
        //判断图片格式和大小
        JsonObjectBO frontImgJson = FileFomat.checkImageFile(frontImg, 10 * 1024);
        if (frontImgJson.getCode() != 1) {
            responseJson.setCode(frontImgJson.getCode());
            responseJson.setMessage(frontImgJson.getMessage());
            return responseJson;
        }
        JsonObjectBO backImgJson = FileFomat.checkImageFile(backImg, 10 * 1024);
        if (backImgJson.getCode() != 1) {
            responseJson.setCode(backImgJson.getCode());
            responseJson.setMessage(backImgJson.getMessage());
            return responseJson;
        }
        UserDO userDO = userService.getUserByUserAccount(userAccount);
        UserIdentificationDO existIdentification = userIdentificationService.getUserIdentificationByUserAccountLately(userAccount);

        if (userDO == null) {
            responseJson.setCode(SystemMessageConfig.USER_ISEXIST_CODE);
            responseJson.setMessage(SystemMessageConfig.USER_ISEXIST_MESSAGE);
            return responseJson;
        }
        if (existIdentification != null) {
            if (existIdentification.getIdentificationStatus() == 1 || userDO.getAuthenticationStatus() == 1) {
                responseJson.setCode(SystemMessageConfig.IFICATION_CONDUCT_CODE);
                responseJson.setMessage(SystemMessageConfig.IFICATION_CONDUCT_MESSAGE);
                return responseJson;
            }
            if (existIdentification.getIdentificationStatus() == 2 && userDO.getAuthenticationStatus() == 2) {
                responseJson.setCode(SystemMessageConfig.IFICATION_ISEXIST_CODE);
                responseJson.setMessage(SystemMessageConfig.IFICATION_ISEXIST_MESSAGE);
                return responseJson;
            }
        }

        //上传图片到图片服务器
        List<String> imageUrlList = new ArrayList<>();
        List<FileDataEntity> imageEntityList = new ArrayList<>();
        InputStream frontInputStream = null;
        InputStream backInputStream = null;
        try {
            FileDataEntity frontImgFileData = null;  //正面照
            frontInputStream = frontImg.getInputStream();
            frontImgFileData = new FileDataEntity(frontImg.getOriginalFilename(), frontInputStream);
            imageEntityList.add(frontImgFileData);

            FileDataEntity backImgFileData = null;  //背面照
            backInputStream = backImg.getInputStream();
            backImgFileData = new FileDataEntity(frontImg.getOriginalFilename(), backInputStream);
            imageEntityList.add(backImgFileData);
        } catch (IOException e) {
            LogUtil.printErrorLog(e);
        }
        // 进行上传
        if (imageEntityList.size() == 2) {
            imageUrlList = FileWriteRemoteUtil.uploadFileList(imageEntityList, FileUrlConfig.file_remote_identificationImage_url);
        }

        try {
            frontInputStream.close();
            backInputStream.close();
        } catch (IOException e) {
            LogUtil.printErrorLog(e);
        }
        if (imageUrlList == null || imageUrlList.size() <= 0) {
            responseJson.setCode(SystemMessageConfig.IFICATION_FAIL_CODE);
            responseJson.setMessage(SystemMessageConfig.IFICATION_FAIL_MESSAGE);
            return responseJson;
        }

        UserIdentificationDO userIdentificationDO = new UserIdentificationDO();
        userIdentificationDO.setUserId(userDO.getUserId());
        userIdentificationDO.setUserAccount(userDO.getUserAccount());  //用户账号
        userIdentificationDO.setUserName(userName);  //用户姓名
        userIdentificationDO.setPhoneAreaCode(userDO.getPhoneAreaCode());  //手机号区号
        userIdentificationDO.setUserPhone(userDO.getPhoneNumber());  //手机号
        userIdentificationDO.setUserCertType(userCertType);  //证件类型
        userIdentificationDO.setUserCertNo(userCertNo);  //证件号
        userIdentificationDO.setIdentificationStatus(1);
        userIdentificationDO.setAddTime(DateUtil.getCurrentTime());

        boolean insertBoo = userIdentificationService.insertUserIdentificationAndImage(userIdentificationDO, imageUrlList, userDO.getAuthenticationStatus());
        if (!insertBoo) {
            // 删除文件
            FileWriteRemoteUtil.deleteFileList(imageUrlList);
            responseJson.setCode(SystemMessageConfig.IFICATION_FAIL_CODE);
            responseJson.setMessage(SystemMessageConfig.IFICATION_FAIL_MESSAGE);
            return responseJson;
        }

        responseJson.setCode(SystemMessageConfig.SYSTEM_CODE_SUCCESS);
        responseJson.setMessage(SystemMessageConfig.SYSTEM_MESSAGE_SUCCESS);
        return responseJson;
    }

    /**
     * 验证字符串
     * @param str
     * @return
     */
    private boolean checkValue(String str){
        String matchStr = "^[0-9a-zA-Z]{6,16}$";
        return str.matches(matchStr);
    }

    /**
     * 验证姓名
     */
    private boolean checkName(String str){
        String matchStr = "[\\u4e00-\\u9fa5]{6,16}+";
        return str.matches(matchStr);
    }

    /**
     * 验证字母
     */
    private boolean checkStr(String str){
        String matchStr = "[a-zA-Z]{6,16}+";
        return str.matches(matchStr);
    }
}
