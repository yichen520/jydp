package com.jydp.controller.web;

import com.iqmkj.utils.DateUtil;
import com.iqmkj.utils.FileDataEntity;
import com.iqmkj.utils.FileWriteLocalUtil;
import com.iqmkj.utils.FileWriteRemoteUtil;
import com.iqmkj.utils.LogUtil;
import com.iqmkj.utils.NumberUtil;
import com.iqmkj.utils.StringUtil;
import com.jydp.entity.BO.JsonObjectBO;
import com.jydp.entity.DO.user.UserDO;
import com.jydp.entity.DO.user.UserIdentificationDO;
import com.jydp.entity.DO.user.UserIdentificationImageDO;
import com.jydp.interceptor.BackerWebInterceptor;
import com.jydp.service.IUserIdentificationImageService;
import com.jydp.service.IUserIdentificationService;
import com.jydp.service.IUserService;
import config.FileUrlConfig;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
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

    /** 实名认证页面入口 */
    @RequestMapping("/show")
    public String show(HttpServletRequest request) {
        String userIdStr = StringUtil.stringNullHandle(request.getParameter("userId"));
        String userAccount = StringUtil.stringNullHandle(request.getParameter("userAccount"));
        if (!StringUtil.isNotNull(userIdStr) || !StringUtil.isNotNull(userAccount)) {
            request.setAttribute("code", 2);
            request.setAttribute("message", "参数为空");
            return "page/web/login";
        }
        request.setAttribute("userId", userIdStr);
        request.setAttribute("userAccount", userAccount);

        UserIdentificationDO existIdentification = userIdentificationService.getUserIdentificationByUserAccountLately(userAccount);
        if (existIdentification != null) {
            List<UserIdentificationImageDO> userIdentificationImageList =
                    userIdentificationImageService.listUserIdentificationImageByIdentificationId(existIdentification.getId());

            request.setAttribute("identification", existIdentification);
            request.setAttribute("identificationImageList", userIdentificationImageList);
            return "page/web/identificationAfresh";
        }
        return "page/web/identification";
    }

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

        request.setAttribute("userId", userIdStr);
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

        //参数处理
        String userIdStr = StringUtil.stringNullHandle(request.getParameter("userId"));
        String userAccount = StringUtil.stringNullHandle(request.getParameter("userAccount"));
        String userName = StringUtil.stringNullHandle(request.getParameter("userName"));
        String userCertTypeStr = StringUtil.stringNullHandle(request.getParameter("userCertType"));
        String userCertNo = StringUtil.stringNullHandle(request.getParameter("userCertNo"));
        if (!StringUtil.isNotNull(userIdStr) || !StringUtil.isNotNull(userAccount) || !StringUtil.isNotNull(userCertTypeStr)
                ||!StringUtil.isNotNull(userName) || !StringUtil.isNotNull(userCertNo)) {
            responseJson.setCode(2);
            responseJson.setMessage("参数为空！");
            return responseJson;
        }

        int userCertType = Integer.parseInt(userCertTypeStr);
        if (userCertType == 1) {
            //姓名，身份证 格式判断
            JsonObjectBO validateJson = validateNameAndCertNo(userName, userCertNo);
            if (validateJson.getCode() != 1) {
                responseJson.setCode(validateJson.getCode());
                responseJson.setMessage(validateJson.getMessage());
                return responseJson;
            }
        }

        // 转型为MultipartHttpRequest：图片处理
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile frontImg = multipartRequest.getFile("frontImg");
        MultipartFile backImg = multipartRequest.getFile("backImg");
        if (frontImg == null || frontImg.isEmpty() || frontImg.getSize() <= 0) {
            responseJson.setCode(2);
            responseJson.setMessage("请上传您的证件正面照");
            return responseJson;
        }
        if (backImg == null || backImg.isEmpty() || backImg.getSize() <= 0) {
            responseJson.setCode(2);
            responseJson.setMessage("请上传您的证件背面照");
            return responseJson;
        }
        //判断图片格式和大小
        JsonObjectBO frontImgJson = checkImageFile(frontImg);
        if (frontImgJson.getCode() != 1) {
            responseJson.setCode(frontImgJson.getCode());
            responseJson.setMessage(frontImgJson.getMessage());
            return responseJson;
        }
        JsonObjectBO backImgJson = checkImageFile(backImg);
        if (backImgJson.getCode() != 1) {
            responseJson.setCode(backImgJson.getCode());
            responseJson.setMessage(backImgJson.getMessage());
            return responseJson;
        }

        /*//一张身份证只能审核通过一次
        boolean identificationBoo = userIdentificationService.validateIdentification(userCertNo);
        if (identificationBoo) {
            responseJson.setCode(5);
            responseJson.setMessage("此身份证已被使用！");
            return responseJson;
        }*/

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
        UserDO userDO = userService.getUserByUserId(userId);
        if (userDO == null) {
            responseJson.setCode(5);
            responseJson.setMessage("您的账号不存在！");
            return responseJson;
        }

        //本地缓存目录
        String path = request.getServletContext().getRealPath("/upload") + "/tempReduceImage/";
        //压缩图片到本地缓存目录，再上传至图片服务器，删除缓存文件
        String frontImgSrc = "";
        if (frontImg.getSize() > 400*1024 ) {
            frontImgSrc = reduceImage(frontImg, path);
        }
        String backImgSrc = "";
        if (backImg.getSize() > 400*1024 ) {
            backImgSrc = reduceImage(backImg, path);
        }

        //上传图片到图片服务器
        List<String> imageUrlList = new ArrayList<>();
        List<FileDataEntity> imageEntityList = new ArrayList<>();
        try {
            FileDataEntity frontImgFileData = null;  //正面照
            if (StringUtil.isNotNull(frontImgSrc)) {
                frontImgFileData = new FileDataEntity(frontImg.getOriginalFilename(), new FileInputStream(frontImgSrc));
            } else {
                frontImgFileData = new FileDataEntity(frontImg.getOriginalFilename(), frontImg.getInputStream());
            }
            imageEntityList.add(frontImgFileData);

            FileDataEntity backImgFileData = null;  //背面照
            if (StringUtil.isNotNull(backImgSrc)) {
                backImgFileData = new FileDataEntity(frontImg.getOriginalFilename(), new FileInputStream(backImgSrc));
            } else {
                backImgFileData = new FileDataEntity(backImg.getOriginalFilename(), backImg.getInputStream());
            }
            imageEntityList.add(backImgFileData);
        } catch (IOException e) {
            LogUtil.printErrorLog(e);
        }

        if (imageEntityList.size() == 2) {
            imageUrlList = FileWriteRemoteUtil.uploadFileList(imageEntityList, FileUrlConfig.file_remote_identificationImage_url);
        }

        //删除本地缓存文件
        if (StringUtil.isNotNull(frontImgSrc)) {
            FileWriteLocalUtil.deleteFileRealPath(frontImgSrc);
        }
        if (StringUtil.isNotNull(backImgSrc)) {
            FileWriteLocalUtil.deleteFileRealPath(backImgSrc);
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
        userIdentificationDO.setUserCertType(userCertType);  //证件类型
        userIdentificationDO.setUserCertNo(userCertNo);  //证件号
        userIdentificationDO.setIdentificationStatus(1);
        userIdentificationDO.setAddTime(DateUtil.getCurrentTime());

        boolean insertBoo = userIdentificationService.insertUserIdentificationAndImage(userIdentificationDO, imageUrlList);
        if (insertBoo) {
            responseJson.setCode(1);
            responseJson.setMessage("操作成功");
        } else {
            responseJson.setCode(5);
            responseJson.setMessage("操作失败");
        }
        return responseJson;
    }

    /**
     * 压缩图片，400K到5M以内的图片压缩至400K以内，5M-10M压缩到700k以内
     * @param img 图片文件
     * @param path 缓存文件夹
     * @return 压缩成功：返回图片路径，压缩失败：返回null
     */
    private String reduceImage(MultipartFile img, String path) {
        StringBuffer url = new StringBuffer();
        long size = img.getSize()/1024; //单位KB
        if (size < 400 || size > 10000) {
            return null;
        }

        try {
            url.append(path);
            url.append(NumberUtil.createNumberStr(6));
            url.append(".jpg");

            //400K-1M 0.4
            if (400 >= size || size < 1000) {
                Thumbnails.of(img.getInputStream())
                        .scale(1)
                        .outputQuality(0.5)
                        .outputFormat("jpg")
                        .toFile(url.toString());
            }
            //1M-5M  0.2
            if (1000 >= size || size < 5000) {
                Thumbnails.of(img.getInputStream())
                        .scale(1)
                        .outputQuality(0.2)
                        .outputFormat("jpg")
                        .toFile(url.toString());
            }
            //5M-10M  0.1
            if (5000 >= size || size < 10000) {
                Thumbnails.of(img.getInputStream())
                        .scale(1)
                        .outputQuality(0.1)
                        .outputFormat("jpg")
                        .toFile(url.toString());
            }
        } catch (Exception ex) {
            LogUtil.printErrorLog(ex);
            FileWriteLocalUtil.deleteFileRealPath(url.toString());
            return null;
        }
        return url.toString();
    }

    /**
     * 验证图片格式 和限制图片大小
     * @param uploadImg 上传的文件
     * @return 验证通过：返回code=1，验证失败：返回code!=1
     */
    private JsonObjectBO checkImageFile(MultipartFile uploadImg) {
        JsonObjectBO responseJson = new JsonObjectBO();

        String fileName = uploadImg.getOriginalFilename();
        String extUpp = fileName.substring(fileName.lastIndexOf(".") + 1);

        //根据扩展名判断是否为要求的图片
        if (!extUpp.matches("^[(jpg)|(jpeg)|(png)|(JPG)|(JPEG)|(PNG)]+$")) {
            responseJson.setCode(3);
            responseJson.setMessage("请上传jpg、jpeg、png格式的图片");
            return responseJson;
        }
        /*//根据图片内容、长宽判断是否为图片文件,ps:影响性能
        InputStream inputStream = null;
        try {
            inputStream = uploadImg.getInputStream();
            Image img = ImageIO.read(inputStream);
            if(img == null || img.getWidth(null) <= 0 || img.getHeight(null) <= 0){
                responseJson.setCode(3);
                responseJson.setMessage("请上传图片文件");
                return responseJson;
            }
        } catch (IOException e) {
            LogUtil.printErrorLog(e);
        }*/

        if (uploadImg.getSize() >= 10*1024*1024) {
            responseJson.setCode(3);
            responseJson.setMessage("您的证件照太大了");
            return responseJson;
        }

        responseJson.setCode(1);
        responseJson.setMessage("验证通过");
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
