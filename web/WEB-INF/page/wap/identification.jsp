<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <meta http-equiv="Cache-Control" content="no-cache"/>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/wap/common.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/wap/simpleTips_wap.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/wap/aut.css">

    <title>实名认证</title>
</head>
<body>
<!-- 头部导航 -->
<header>
    <p>实名认证</p>
</header>
<!-- 内容区域 -->
<div class="wrapper">
    <div class="list-box">
        <p>帐号</p>
        <input type="test" id="userAccount" disabled="disabled" value="${userAccount}"/>
    </div>
    <div class="list-box">
        <p>姓名</p>
        <input type="text" id="userName" maxlength="16" placeholder="您的真实姓名"/>
    </div>
    <div class="list-box">
        <p>证件类型</p>
        <select id="userCertType">
            <option value="1">身份证</option>
            <option value="2">护照</option>
        </select>
    </div>
    <div class="list-box">
        <p>证件号</p>
        <input type="text" id="userCertNo" placeholder="您的证件号" maxlength="18"/>
    </div>
    <div class="idcard-box">

        <p class="title">证件照</p>
        <div class="file">

            <input type="file" id="icardone" name="avatar" class="icardone"/>
            <div id="blah" class="autor"></div>
            <div class="imgone">
                <img src="${pageContext.request.contextPath}/resources/image/wap/tianjia.png" id="img1"/>
                <p>证件正面照</p>
            </div>

            <input type="file" id="icardtwo" name="avatar" class="icardtwo"/>
            <div id="blahtwo"></div>
            <div class="imgtwo">
                <img src="${pageContext.request.contextPath}/resources/image/wap/tianjia.png" id="img2"/>
                <p>证件反面照</p>
            </div>
            <div class="clear"></div>
        </div>
    </div>
    <a href="#" class="submit" onclick="submit()">提 交</a>
</div>
<!-- loading图 -->
<div id="loading">
    <i></i>
</div>
</body>

<script src="${pageContext.request.contextPath}/resources/js/wap/jquery-2.1.4.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/wap/common.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/wap/zepto.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/wap/simpleTips_wap.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/localResizeIMG.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/mobileBUGFix.mini.js"></script>

<script>
    var formData = new FormData();
    //记录上传图片状态的
    var a = false;
    var b = false;
    $('#icardone').localResizeIMG({
        width: 800,
        quality: 0.2,
        success: function (result) {
            document.getElementById('blah').style.backgroundImage='url('+result.base64+')';
            var blob = dataURLtoBlob(result.base64);
            formData.append("frontImg", blob, "file_frontImg.jpg");
            a = true;
        }
    });
    $('#icardtwo').localResizeIMG({
        width: 800,
        quality: 0.2,
        success: function (result) {
            document.getElementById('blahtwo').style.backgroundImage='url('+result.base64+')';
            var blob = dataURLtoBlob(result.base64);
            formData.append("backImg", blob, "file_backImg.jpg");
            b = true;
        }
    });
    function dataURLtoBlob(dataurl) {
        var arr = dataurl.split(','), mime = arr[0].match(/:(.*?);/)[1],
            bstr = atob(arr[1]), n = bstr.length, u8arr = new Uint8Array(n);
        while (n--) {
            u8arr[n] = bstr.charCodeAt(n);
        }
        return new Blob([u8arr], { type: mime });
    }
    function submit() {
        var userAccount = $("#userAccount").val();
        var userName = $("#userName").val();
        var userCertType = $("#userCertType").val();
        var userCertNo = $("#userCertNo").val();
        var frontImg = $("#icardone").val();
        var backImg = $("#icardtwo").val();

        if (!userName || userName == null || userName == "") {
            return openTips("请输入真实姓名");
        }

        if (userName.length <= 1) {
            return openTips("您的姓名过短");
        }

        if (!userCertNo) {
            return openTips("请输入您的证件号");
        }

        if (userCertType == 1) {
            var reg = /[^\u4e00-\u9fa5]/.test(userName);
            if (!userName || reg) {
                return openTips("身份证的姓名必须是全中文");
            }
            if (!IdentityCodeValid(userCertNo)) {
                return openTips("请输入正确的身份证号码");
            }
        } else {
            if (userCertNo.length < 6) {
                return openTips("护照号长度必须大于6位");
            }
        }
        if (!a) {
            return openTips("请上传您的证件正面照");
        }
        if (!b) {
            return openTips("请上传您的证件反面照");
        }
        if(!(a&&b)){
            return;
        }
        formData.append("userAccount", userAccount);
        formData.append("userName", userName);
        formData.append("userCertType", userCertType);
        formData.append("userCertNo", userCertNo);
        var url = "${pageContext.request.contextPath}/userWap/identificationController/add"

        $.ajax({
            url: url,
            type: 'post',
            dataType: 'json',
            async: true,
            processData: false,
            contentType: false,
            data: formData,
            success: function (result) {
                if (result.code == 1) {
                    openTips(result.message);
                    setTimeout(function (){
                        window.location.replace("${pageContext.request.contextPath}/wapLogin");
                    }, 1000);
                } else {
                    openTips(result.message);
                }
            }, error: function () {
                openTips("服务器异常，请稍后再试！");
            }
        });
    }

    //判断图片格式
    function checkFileImage(file) {
        if (!/.(gif|jpg|jpeg|png|gif|jpg|png)$/.test(file.val())) {
            openTips("请上传图片格式文件");
            return false;
        }
        return true;
    }

    //身份证校验
    function IdentityCodeValid(code) {
        var city = {
            11: "北京",
            12: "天津",
            13: "河北",
            14: "山西",
            15: "内蒙古",
            21: "辽宁",
            22: "吉林",
            23: "黑龙江 ",
            31: "上海",
            32: "江苏",
            33: "浙江",
            34: "安徽",
            35: "福建",
            36: "江西",
            37: "山东",
            41: "河南",
            42: "湖北 ",
            43: "湖南",
            44: "广东",
            45: "广西",
            46: "海南",
            50: "重庆",
            51: "四川",
            52: "贵州",
            53: "云南",
            54: "西藏 ",
            61: "陕西",
            62: "甘肃",
            63: "青海",
            64: "宁夏",
            65: "新疆",
            71: "台湾",
            81: "香港",
            82: "澳门",
            91: "国外"
        };
        var tip = "";
        var pass = true;
        if (!code || !/^\d{6}(19|20)?\d{2}(0[1-9]|1[0-2])(0[1-9]|[1-2][0-9]|3[0-1])\d{3}(\d|X)$/i.test(code)) {
            tip = "身份证号码有误";
            pass = false;
        } else if (!city[code.substr(0, 2)]) {
            tip = "身份证地址编码错误";
            pass = false;
        } else {
            //18位身份证需要验证最后一位校验位
            if (code.length == 18) {
                code = code.split('');
                //∑(ai×Wi)(mod 11)
                //加权因子
                var factor = [7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2];
                //校验位
                var parity = [1, 0, 'X', 9, 8, 7, 6, 5, 4, 3, 2];
                var sum = 0;
                var ai = 0;
                var wi = 0;
                for (var i = 0; i < 17; i++) {
                    ai = code[i];
                    wi = factor[i];
                    sum += ai * wi;
                }
                var last = parity[sum % 11];
                if (code[17] == 'x') {
                    code[17] = 'X';
                }
                if (last != code[17]) {
                    tip = "身份证号码错误";
                    pass = false;
                }
            }
        }
        if (!pass) openTips(tip);
        return pass;
    }
</script>
</html>