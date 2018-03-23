package com.jydp.controller.web;

import com.iqmkj.utils.DateUtil;
import com.iqmkj.utils.FileDataEntity;
import com.iqmkj.utils.FileFomat;
import com.iqmkj.utils.FileWriteLocalUtil;
import com.iqmkj.utils.FileWriteRemoteUtil;
import com.iqmkj.utils.ImageReduceUtil;
import com.iqmkj.utils.LogUtil;
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
import java.io.InputStream;
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

        int userId = Integer.parseInt(userIdStr);
        UserDO userDO = userService.getUserByUserId(userId);
        if (userDO == null) {
            return "page/web/login";
        }

        //待审核，审核拒绝，进入查看实名认证信息页
        if (userDO.getAuthenticationStatus() == 1 || userDO.getAuthenticationStatus() == 3) {
            UserIdentificationDO existIdentification = userIdentificationService.getUserIdentificationByUserIdLately(userId);
            if (existIdentification == null) {
                return "page/web/identification";
            }

            List<UserIdentificationImageDO> userIdentificationImageList =
                    userIdentificationImageService.listUserIdentificationImageByIdentificationId(existIdentification.getId());

            request.setAttribute("identification", existIdentification);
            request.setAttribute("identificationImageList", userIdentificationImageList);
            return "page/web/identificationAfresh";
        }
        //审核通过，进入登录页
        if (userDO.getAuthenticationStatus() == 2) {
            return "page/web/login";
        }
        //未提交，进入提交实名认证信息页
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
        if (userName.length() > 16 || userCertNo.length() < 6 || userCertNo.length() > 18) {
            responseJson.setCode(3);
            responseJson.setMessage("参数错误！");
            return responseJson;
        }

        int userCertType = Integer.parseInt(userCertTypeStr);
        if (userCertType == 1) {
            //证件类型为身份证：姓名，身份证 格式判断
            JsonObjectBO validateJson = validateNameAndCertNo(userName, userCertNo);
            if (validateJson.getCode() != 1) {
                responseJson.setCode(validateJson.getCode());
                responseJson.setMessage(validateJson.getMessage());
                return responseJson;
            }
        } else if (userCertType == 2) {
            //证件类型为护照：非法字符过滤
            userName = rightfulString(userName);
            userCertNo = rightfulString(userCertNo);
        } else {
            responseJson.setCode(3);
            responseJson.setMessage("参数错误！");
            return responseJson;
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
        UserIdentificationDO existIdentification = userIdentificationService.getUserIdentificationByUserIdLately(userId);
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
        if (userDO.getAuthenticationStatus() == 1) {
            responseJson.setCode(5);
            responseJson.setMessage("您已有认证信息在审核中");
            return responseJson;
        }
        if (userDO.getAuthenticationStatus() == 2) {
            responseJson.setCode(5);
            responseJson.setMessage("您已有认证信息通过");
            return responseJson;
        }

        //本地缓存目录
        String path = request.getServletContext().getRealPath("/upload") + "/tempReduceImage/";
        //图片大于400k，压缩图片到本地缓存目录，再上传至图片服务器，最后删除缓存文件
        String frontImgSrc = "";
        if (frontImg.getSize() > 400*1024 ) {
            frontImgSrc = ImageReduceUtil.reducePicForScale(frontImg, path, 400, 0.3);
        }
        String backImgSrc = "";
        if (backImg.getSize() > 400*1024 ) {
            backImgSrc = ImageReduceUtil.reducePicForScale(backImg, path, 400, 0.3);
        }

        //上传图片到图片服务器
        List<String> imageUrlList = new ArrayList<>();
        List<FileDataEntity> imageEntityList = new ArrayList<>();
        InputStream frontInputStream = null;
        InputStream backInputStream = null;
        try {
            FileDataEntity frontImgFileData = null;  //正面照
            if (StringUtil.isNotNull(frontImgSrc)) {
                frontInputStream = new FileInputStream(frontImgSrc);
            } else {
                frontInputStream = frontImg.getInputStream();
            }
            frontImgFileData = new FileDataEntity(frontImg.getOriginalFilename(), frontInputStream);
            imageEntityList.add(frontImgFileData);

            FileDataEntity backImgFileData = null;  //背面照
            if (StringUtil.isNotNull(backImgSrc)) {
                backInputStream = new FileInputStream(backImgSrc);
            } else {
                backInputStream = backImg.getInputStream();
            }
            backImgFileData = new FileDataEntity(frontImg.getOriginalFilename(), backInputStream);
            imageEntityList.add(backImgFileData);
        } catch (IOException e) {
            LogUtil.printErrorLog(e);
        }

        if (imageEntityList.size() == 2) {
            imageUrlList = FileWriteRemoteUtil.uploadFileList(imageEntityList, FileUrlConfig.file_remote_identificationImage_url);
        }

        try {
            frontInputStream.close();
            backInputStream.close();
        } catch (IOException e) {
            LogUtil.printErrorLog(e);
        }
        //删除本地缓存文件
        if (StringUtil.isNotNull(frontImgSrc)) {
            FileWriteLocalUtil.deleteFileRealPath(frontImgSrc);
        }
        if (StringUtil.isNotNull(backImgSrc)) {
            FileWriteLocalUtil.deleteFileRealPath(backImgSrc);
        }
        if (imageUrlList == null || imageUrlList.size() < 2) {
            responseJson.setCode(5);
            responseJson.setMessage("服务器异常，图片上传失败！");
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
        if (insertBoo) {
            responseJson.setCode(1);
            responseJson.setMessage("提交成功");
        } else {
            responseJson.setCode(5);
            responseJson.setMessage("提交失败");
        }
        return responseJson;
    }

    /**
     * 验证图片格式 和限制图片大小
     * @param uploadImg 上传的文件
     * @return 验证通过：返回code=1，验证失败：返回code!=1
     */
    private JsonObjectBO checkImageFile(MultipartFile uploadImg) {
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
        Pattern patternName = Pattern.compile("[\\u4e00-\\u9fa5]{2,16}");
        Matcher matcherName = patternName.matcher(userName);
        if (!matcherName.matches()) {
            responseJson.setCode(3);
            responseJson.setMessage("姓名只允许中文，长度为2到16");
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

    /**
     * 处理页面传递的特殊字符，将<>()&;:/\'"替换为" "
     * @param source 处理前的字符串
     * @return 处理后的字符串
     */
    private String rightfulString(String source) {
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
