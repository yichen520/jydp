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
                <p class="trade">交易大盘</p>
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
                    <input type="password" class="loginInput" placeholder="登录密码" id="password" name="password"
                           onpaste="return false" oncontextmenu="return false" oncopy="return false" oncut="return false"
                           maxLength="16" onkeyup="value=value.replace(/[^\a-\z\A-\Z\d]/g,'')" onblur="value=value.replace(/[^\a-\z\A-\Z\d]/g,'')"/>
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

    //验证登陆
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

        $("#loginForm").submit();
    }

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