<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <meta http-equiv="Cache-Control" content="no-cache" />

    <link rel="stylesheet" href="<%=path%>/resources/css/wap/common.css">
    <link rel="stylesheet" href="<%=path%>/resources/css/wap/simpleTips_wap.css">
    <link rel="stylesheet" href="<%=path%>/resources/css/wap/modifyPhone.css">

    <title>修改手机号</title>
</head>
<body>
    <header>
        <img src="<%=path%>/resources/image/wap/back.png" class="back"/>
        <p>修改手机号</p>
    </header>

    <div class="registerBox">
        <div class="title">修改手机号</div>
        <div class="registerContent">
            <div class="oldPhone">
                <p>原手机号：<span>${phoneAreaCode} ${phoneNumber}</span></p>
            </div>
            <div class="oldPhoneCode">
                <input type="number" placeholder="请输入6位短信验证码" maxlength="6" class="oldCode"/>
                <p class="code">获取验证码</p>
            </div>
            <div class="newPhone">
                <div class="choseNumber">
                    <p class="num">+86</p>
                    <img src="<%=path%>/resources/image/wap/iconDown.png" />
                </div>
                <input type="number" placeholder="您的新手机号" maxlength="11"/>
            </div>
            <div class="newPhoneCode">
                <input type="number" placeholder="请输入6位短信验证码" maxlength="6" class="oldCode"/>
                <p class="code">获取验证码</p>
            </div>
            <div class="userPassword">
                <input type="password" placeholder="登录密码" maxlength="16"/>
            </div>
        </div>
        <div class="confirm" onclick="openTip()" >提 交</div>
    </div>
    <!-- 选择手机号弹窗 -->
    <div class="chosePhone">
        <div class="search">
            <img src="<%=path%>/resources/image/wap/searchIcon.png" />
            <input type="type" placeholder="请选择国家或区号"/>
            <p>取消</p>
        </div>
        <div class="searchList">
            <ul>
                <li>
                    <p class="city">阿尔及利亚</p>
                    <p class="cityNum">+35</p>
                    <p class="clear"></p>
                </li>
                <li>
                    <p class="city">安哥拉</p>
                    <p class="cityNum">+23</p>
                    <p class="clear"></p>
                </li>
                <li>
                    <p class="city">阿根廷</p>
                    <p class="cityNum">+54</p>
                    <p class="clear"></p>
                </li>
                <li>
                    <p class="city">亚美尼亚</p>
                    <p class="cityNum">+375</p>
                    <p class="clear"></p>
                </li>
                <li>
                    <p class="city">安道尔共和国</p>
                    <p class="cityNum">+376</p>
                    <p class="clear"></p>
                </li>
            </ul>
        </div>
    </div>
    <!-- loading图 -->
    <div id="loading">
        <i></i>
    </div>
</body>

<script type="text/javascript" src="<%=path%>/resources/js/wap/common.js"></script>
<script type="text/javascript" src="<%=path%>/resources/js/wap/zepto.min.js"></script>
<script type="text/javascript" src="<%=path%>/resources/js/wap/jquery-2.1.4.min.js"></script>
<script type="text/javascript" src="<%=path%>/resources/js/wap/modify.js"></script>
<script type="text/javascript" src="<%=path%>/resources/js/wap/simpleTips_wap.js"></script>

<script>
    $(".back").click(function () {
        window.location.href="<%=path%>/userWap/userInfo/userCenter/show.htm";
    })
</script>

</html>