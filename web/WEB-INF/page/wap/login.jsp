<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd"> <html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <meta http-equiv="Cache-Control" content="no-cache" />

    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/wap/common.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/wap/login.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/wap/simpleTips_wap.css" />
    <title>登录</title>
</head>
<body>
    <!-- 内容区域 -->
    <div class="wrapper">
        <div class="logoBox">
            <img src="${pageContext.request.contextPath}/resources/image/wap/login-logo.png" id="logo"/>
            <p>交易大盘</p>
        </div>
        <div class="loginBox">
            <form id="loginForm" action="${pageContext.request.contextPath}/userWap/userLogin/login" method="post">
                <p class="title">用户登录</p>
                <div class="usernameBox">
                    <div class="box">
                        <img src="${pageContext.request.contextPath}/resources/image/wap/iconUser.png" />
                        <input type="text" placeholder="您的登录账号" id="userAccount" name="userAccount" value="${userAccount}"
                               onpaste="return false" oncontextmenu="return false" oncopy="return false" oncut="return false" maxLength="16"
                               onkeyup="checkoutValue(this)" onblur="checkoutValue(this)"/>
                    </div>
                </div>
                <div class="passwordBox">
                    <div class="box">
                        <img src="${pageContext.request.contextPath}/resources/image/wap/iconPassword.png" />
                        <input type="password" placeholder="您的登录密码" id="password"
                               onpaste="return false" oncontextmenu="return false" oncopy="return false" oncut="return false" maxLength="16" autocomplete="new-password"
                               onkeyup="checkoutValue(this)" onblur="checkoutValue(this)"
                               onkeypress="keypressHandle(event);"/>
                        <input type="hidden" id="encodePwd" name="password"/>
                    </div>
                </div>
                <div class="button" onclick="loginSubmit();" >登 录</div>
                <div class="footer">
                    <a href="${pageContext.request.contextPath}/userWap/forgetPassword/show">忘记密码</a>
                    <a href="${pageContext.request.contextPath}/userWap/userRegister/show">注册</a>
                    <div class="clear"></div>
                </div>
            </form>
        </div>
        <div class="sstr"></div>
        <div class="bstr"></div>

    </div>
    <!-- loading图 -->
    <div id="loading">
        <i></i>
    </div>
</body>

<script src="${pageContext.request.contextPath}/resources/js/wap/common.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/wap/zepto.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/wap/jquery-2.1.4.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/wap/simpleTips_wap.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/wap/checkout.js"></script>

<script type="text/javascript">
    window.onload = function() {
        var code = '${code}';
        var message = '${message}';

        if(code != 1 && message != "") {
            openTips(message);
            return false;
        }
    }
    $(function(){
        $("#logo").click(function () {
            window.location.replace("${pageContext.request.contextPath}/userWap/homePage/show");
        })
    });

    //验证登录
    function loginSubmit() {
        var userAccount = $("#userAccount").val();
        var password = $("#password").val();

        if (isEmpty(userAccount) || userAccount.length < 6 || userAccount.length > 16) {
            openTips("请输入账号，6~16个字符");
            return;
        }
        if (isEmpty(password) || password.length < 6 || password.length > 16) {
            openTips("请输入密码，6~16个字符");
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

    //回车执行
    function keypressHandle(event) {
        if (event.keyCode == "13") {
            loginSubmit();
        }
    }

</script>
</html>