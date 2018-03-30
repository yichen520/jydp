<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/resources/page/common/path.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <link rel="icon" href="<%=path %>/resources/image/web/icon.ico" type="image/x-ico" />
    <link rel="stylesheet" type="text/css" href="<%=path %>/resources/css/web/login.css" />
    <link rel="stylesheet" type="text/css" href="<%=path %>/resources/css/web/simpleTips.css" />
    <script type="text/javascript" src="http://libs.baidu.com/jquery/2.1.4/jquery.min.js"></script>
    <script type="text/javascript" src="<%=path %>/resources/js/simpleTips.js"></script>
    <title>登录</title>
</head>
<body>
<div class="content">
    <div class="main">
        <div class="logo">
            <a href="<%=path %>/userWeb/homePage/show">
                <p class="trade">盛源交易所</p>
                <img src="<%=path %>/resources/image/web/loginLogo.png" class="loginLogo" />
            </a>
        </div>

        <div class="login">
            <form id="loginForm" action="<%=path %>/userWeb/userLogin/login" method="post">
                <p class="loginTitle">用户登录</p>
                <p class="loginInfo">
                    <img src="<%=path %>/resources/image/web/username.png" class="loginImg" />
                    <input type="text" class="loginInput" placeholder="登录账号" id="userAccount" name="userAccount" value="${userAccount}"
                           onpaste="return false" oncontextmenu="return false" oncopy="return false" oncut="return false"
                           maxLength="16" onkeyup="value=value.replace(/[^\a-\z\A-\Z\d]/g,'')" onblur="value=value.replace(/[^\a-\z\A-\Z\d]/g,'')"/>
                </p>

                <p class="loginInfo">
                    <img src="<%=path %>/resources/image/web/password.png" class="loginImg" />
                    <input type="password" class="loginInput" placeholder="登录密码" id="password"
                           onpaste="return false" oncontextmenu="return false" oncopy="return false" oncut="return false"
                           maxLength="16" onkeyup="value=value.replace(/[^\a-\z\A-\Z\d]/g,'')" onblur="value=value.replace(/[^\a-\z\A-\Z\d]/g,'')"/>
                    <input type="hidden" id="encodePwd" name="password"/>
                </p>
                <p class="loginCode">
                    <input type="text" id="validateCode" name="validateCode" class="codeInput" placeholder="验证码"
                           maxLength="4" onkeyup="value=value.replace(/[^\a-\z\A-\Z\d]/g,'')" onblur="value=value.replace(/[^\a-\z\A-\Z\d]/g,'')"
                           onkeypress="keypressHandle(event);"/>
                    <span class="codeImg"><img src="<%=path%>/kaptcha/getKaptchaImage" id="kaptchaImage" class="codeImg" /></span>
                </p>

                <p class="operate">
                    <a href="<%=path %>/userWeb/forgetPassword/show" class="forget">忘记密码？</a>
                    <span>没有账号？<a href="<%=path %>/userWeb/userRegister/show" class="register">点击注册</a></span>
                </p>
                <input type="text" value="登&nbsp;录" class="loginBtn" onfocus="this.blur()" onclick="loginSubmit();" />
            </form>
        </div>
    </div>
</div>

<script type="text/javascript">
    window.onload = function() {
        var code = '${code}';
        var message = '${message}';

        if(code != 1 && message != "") {
            openTips(message);
            return false;
        }
    }

    //验证登录
    function loginSubmit() {
        var userAccount = $("#userAccount").val();
        var password = $("#password").val();
        var validateCode = $("#validateCode").val();

        if (isEmpty(userAccount) || userAccount.length < 6 || userAccount.length > 16) {
            openTips("请输入账号，6~16个字符");
            return;
        }
        if (isEmpty(password) || password.length < 6 || password.length > 16) {
            openTips("请输入密码，6~16个字符");
            return;
        }
        if (isEmpty(validateCode) || validateCode.length != 4) {
            openTips("请输入验证码");
            return;
        }

        var pwd = encode64($("#password").val());
        $("#encodePwd").val(pwd);
        $("#loginForm").submit();
    }

    // base64加密开始
    var keyStr = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=";

    function encode64(input) {
        var output = "";
        var chr1, chr2, chr3 = "";
        var enc1, enc2, enc3, enc4 = "";
        var i = 0;
        do {
            chr1 = input.charCodeAt(i++);
            chr2 = input.charCodeAt(i++);
            chr3 = input.charCodeAt(i++);
            enc1 = chr1 >> 2;
            enc2 = ((chr1 & 3) << 4) | (chr2 >> 4);
            enc3 = ((chr2 & 15) << 2) | (chr3 >> 6);
            enc4 = chr3 & 63;
            if (isNaN(chr2)) {
                enc3 = enc4 = 64;
            } else if (isNaN(chr3)) {
                enc4 = 64;
            }
            output = output + keyStr.charAt(enc1) + keyStr.charAt(enc2)
                + keyStr.charAt(enc3) + keyStr.charAt(enc4);
            chr1 = chr2 = chr3 = "";
            enc1 = enc2 = enc3 = enc4 = "";
        } while (i < input.length);

        return output;
    }
    // base64加密结束

    //字符串判空，为空返回true，非空返回false
    function isEmpty(validateStr) {
        var map = {};
        map[null] = true;
        map[""] = true;
        map[" "] = true;
        map["null"] = true;
        map["NULL"] = true;
        map["undefined"] = true;
        map[undefined] = true;
        return map[validateStr] ? true : false;
    }

    //生成验证码
    $(function() {
        $('#kaptchaImage').click(function () {
            $(this).hide().attr('src', '<%=path%>/kaptcha/getKaptchaImage?' + Math.floor(Math.random()*100) ).fadeIn();
            event.cancelBubble=true;
        });
    });

    //回车执行
    function keypressHandle(event) {
        if (event.keyCode == "13") {
            loginSubmit();
        }
    }
</script>
</body>
</html>