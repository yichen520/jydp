package com.jydp.controller.web;

import com.iqmkj.utils.DateUtil;
import com.iqmkj.utils.FileDataEntity;
import com.iqmkj.utils.FileWriteRemoteUtil;
import com.iqmkj.utils.LogUtil;
import com.iqmkj.utils.StringUtil;
import com.jydp.entity.BO.JsonObjectBO;
import com.jydp.entity.DO.user.UserDO;
import com.jydp.entity.DO.user.UserIdentificationDO;
import com.jydp.interceptor.BackerWebInterceptor;
import com.jydp.service.IUserIdentificationImageService;
import com.jydp.service.IUserIdentificationService;
import com.jydp.service.IUserService;
import config.FileUrlConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Description: 用户身份认证
 * Author: hht
 * Date: 2018-02-27 13:41
 */
@Controller
@RequestMapping("/userWeb/identificationController")
@Scope(value="prototype")
public class IdentificationController {

    /** 用户认证 */
    @Autowired
    private IUserIdentificationService userIdentificationService;

    /** 用户账号 */
    @Autowired
    private IUserService userService;

    /** 用户认证详情图 */
    @Autowired
    private IUserIdentificationImageService userIdentificationImageService;

    /** 重新认证，新增实名认证 */
    @RequestMapping("/showAdd")
    public String showAdd(HttpServletRequest request) {
        String userIdStr = StringUtil.stringNullHandle(request.getParameter("userId"));
        String userAccount = StringUtil.stringNullHandle(request.getParameter("userAccount"));
        if (!StringUtil.isNotNull(userIdStr) || !StringUtil.isNotNull(userAccount)) {
            request.setAttribute("code", 2);
            request.setAttribute("message", "参数为空");
            return "page/web/login";
        }

        int userId = Integer.parseInt(userIdStr);
        request.setAttribute("userId", userId);
        request.setAttribute("userAccount", userAccount);
        return "page/web/identification";
    }

    /** 新增实名认证 */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public @ResponseBody JsonObjectBO add(HttpServletRequest request) {
        JsonObjectBO responseJson = new JsonObjectBO();
        boolean handleFrequent = BackerWebInterceptor.handleFrequent(request);
        if (handleFrequent) {
            responseJson.setCode(6);
            responseJson.setMessage("您的操作太频繁");
            return responseJson;
        }

        String userIdStr = StringUtil.stringNullHandle(request.getParameter("userId"));
        String userAccount = StringUtil.stringNullHandle(request.getParameter("userAccount"));
        String userName = StringUtil.stringNullHandle(request.getParameter("userName"));
        String userCertNo = StringUtil.stringNullHandle(request.getParameter("userCertNo"));
        if (!StringUtil.isNotNull(userIdStr) || !StringUtil.isNotNull(userAccount)
                ||!StringUtil.isNotNull(userName) || !StringUtil.isNotNull(userCertNo)) {
            responseJson.setCode(2);
            responseJson.setMessage("参数为空！");
            return responseJson;
        }

        //姓名，身份证 格式判断
        JsonObjectBO validateJson = validateNameAndCertNo(userName, userCertNo);
        if (validateJson.getCode() != 1) {
            responseJson.setCode(validateJson.getCode());
            responseJson.setMessage(validateJson.getMessage());
            return responseJson;
        }

        List<MultipartFile> imageList = new ArrayList<>();
        // 转型为MultipartHttpRequest：
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        //获取文件名
        Iterator<String> iter = multipartRequest.getFileNames();
        while (iter.hasNext()) {
            // 获得文件：
            MultipartFile file = multipartRequest.getFile(iter.next().toString());
            if (file != null) {
                imageList.add(file);
            }
        }
        if (imageList.size() < 3) {
            responseJson.setCode(5);
            responseJson.setMessage("证件照至少上传三张");
            return responseJson;
        }
        if (imageList.size() > 9) {
            responseJson.setCode(5);
            responseJson.setMessage("证件照不能超过9张");
            return responseJson;
        }

        int userId = Integer.parseInt(userIdStr);
        UserIdentificationDO existIdentification = userIdentificationService.getUserIdentificationByUserAccountLately(userAccount);
        if (existIdentification != null) {
            if (existIdentification.getIdentificationStatus() == 1) {
                responseJson.setCode(5);
                responseJson.setMessage("已有认证信息在审核中");
                return responseJson;
            }
            if (existIdentification.getIdentificationStatus() == 2) {
                responseJson.setCode(5);
                responseJson.setMessage("已有认证信息通过");
                return responseJson;
            }
        }

        //一张身份证只能审核通过一次
        boolean identificationBoo = userIdentificationService.validateIdentification(userCertNo);
        if (identificationBoo) {
            responseJson.setCode(5);
            responseJson.setMessage("此身份证已被使用！");
            return responseJson;
        }

        UserDO userDO = userService.getUserByUserId(userId);
        if (userDO == null || userDO.getAccountStatus() != 1) {
            responseJson.setCode(5);
            responseJson.setMessage("您的账号不存在！");
            return responseJson;
        }

        //上传图片到图片服务器
        List<String> imageUrlList = new ArrayList<>();
        List<FileDataEntity> imageEntityList = new ArrayList<>();
        try {
            for(MultipartFile images : imageList){
                if (images == null || images.getSize() <= 0) {
                    continue;
                }
                FileDataEntity fileData = new FileDataEntity(images.getOriginalFilename(), images.getInputStream());
                imageEntityList.add(fileData);
            }
        } catch (IOException e) {
            LogUtil.printErrorLog(e);
        }

        if (imageEntityList.size() > 0) {
            imageUrlList = FileWriteRemoteUtil.uploadFileList(imageEntityList, FileUrlConfig.file_remote_identificationImage_url);
        }
        if (imageUrlList == null || imageUrlList.size() <= 0) {
            responseJson.setCode(5);
            responseJson.setMessage("服务器异常，图片上传失败！");
            return responseJson;
        }

        UserIdentificationDO userIdentificationDO = new UserIdentificationDO();
        userIdentificationDO.setUserId(userDO.getUserId());
        userIdentificationDO.setUserAccount(userDO.getUserAccount());  //用户账号
        userIdentificationDO.setUserName(userName);  //用户姓名
        userIdentificationDO.setUserPhone(userDO.getPhoneAreaCode() + userDO.getPhoneNumber());  //手机号
        userIdentificationDO.setUserCertNo(userCertNo);  //身份证号
        userIdentificationDO.setIdentificationStatus(1);
        userIdentificationDO.setAddTime(DateUtil.getCurrentTime());

        boolean insertBoo = userIdentificationService.insertUserIdentificationAndImage(userIdentificationDO, imageUrlList);
        if (insertBoo) {
            responseJson.setCode(1);
            responseJson.setMessage("操作成功");
            return responseJson;
        }

        responseJson.setCode(5);
        responseJson.setMessage("操作失败");
        return responseJson;
    }

    /**
     * 验证身份证姓名和身份证号码
     * @param userName 身份证姓名
     * @param userCertNo 身份证号码
     * @return 验证通过：返回code=1，验证失败：返回code!=1
     */
    private JsonObjectBO validateNameAndCertNo(String userName, String userCertNo) {
        JsonObjectBO responseJson = new JsonObjectBO();
        //姓名校验
        Pattern patternName = Pattern.compile("[\\u4e00-\\u9fa5]{2,8}");
        Matcher matcherName = patternName.matcher(userName);
        if (!matcherName.matches()) {
            responseJson.setCode(3);
            responseJson.setMessage("姓名只允许中文！");
            return responseJson;
        }

        //身份证校验
        Pattern pattern1 = Pattern.compile("^(\\d{6})(19|20)(\\d{2})(0[1-9]|1[0-2])(0[1-9]|[1-2][0-9]|3[0-1])(\\d{3})(\\d|X|x)?$"); //粗略的校验
        Matcher matcher = pattern1.matcher(userCertNo);
        if(!matcher.matches()){
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
        for (int i = 0; i < 17; i++){
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
}
