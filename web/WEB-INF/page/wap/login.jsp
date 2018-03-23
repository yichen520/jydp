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
    <%--
    到时候把这里换成wap的
    --%>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/web/simpleTips.css" />
    <title>登录</title>
</head>
<body>
    <!-- 内容区域 -->
    <div class="wrapper">
        <div class="logoBox">
            <img src="${pageContext.request.contextPath}/resources/image/wap/login-logo.png" />
            <p>交易大盘</p>
        </div>
        <div class="loginBox">
            <form id="loginForm" action="${pageContext.request.contextPath}/userWap/userLogin/login" method="post">
                <p class="title">用户登录</p>
                <div class="usernameBox">
                    <div class="box">
                        <img src="${pageContext.request.contextPath}/resources/image/wap/iconUser.png" />
                        <input type="text" placeholder="您的登录帐号" id="userAccount" name="userAccount" value="${userAccount}"
                               onpaste="return false" oncontextmenu="return false" oncopy="return false" oncut="return false"
                               maxLength="16" onkeyup="value=value.replace(/[^\a-\z\A-\Z\d]/g,'')" onblur="value=value.replace(/[^\a-\z\A-\Z\d]/g,'')"/>
                    </div>
                </div>
                <div class="passwordBox">
                    <div class="box">
                        <img src="${pageContext.request.contextPath}/resources/image/wap/iconPassword.png" />
                        <input type="password" placeholder="您的登录密码" id="password" name="password"
                               onpaste="return false" oncontextmenu="return false" oncopy="return false" oncut="return false"
                               maxLength="16" onkeyup="value=value.replace(/[^\a-\z\A-\Z\d]/g,'')" onblur="value=value.replace(/[^\a-\z\A-\Z\d]/g,'')"
                               onkeypress="keypressHandle(event);"/>
                    </div>
                </div>
                <div class="button" onclick="loginSubmit();" >登录</div>
                <div class="footer">
                    <a>忘记密码</a>
                    <a href="register.html">注册</a>
                    <div class="clear"></div>
                </div>
            </form>
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

        if (isEmpty(userAccount) || userAccount.length < 6 || userAccount.length > 16) {
            openTips("请输入账号，6~16个字符");
            return;
        }
        if (isEmpty(password) || password.length < 6 || password.length > 16) {
            openTips("请输入密码，6~16个字符");
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

    //回车执行
    function keypressHandle(event) {
        if (event.keyCode == "13") {
            loginSubmit();
        }
    }
</script>
</body>

<script src="${pageContext.request.contextPath}/resources/js/wap/common.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/wap/zepto.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/wap/jquery-2.1.4.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/simpleTips.js"></script>
</html>