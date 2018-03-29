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
                <p>原手机号：<span id="oldAreaCode">${phoneAreaCode}</span>&nbsp;<span id="oldPhone">${phoneNumber}</span></p>
            </div>
            <div class="oldPhoneCode">
                <input type="number" placeholder="请输入6位短信验证码" maxlength="6" class="oldCode" id="oldValidCode"/>
                <input class="code" id="oldPhoneCode" value="获取验证码"/>
            </div>
            <div class="newPhone">
                <div class="choseNumber">
                    <p class="num" id="newAreaCode">+86</p>
                    <img src="<%=path%>/resources/image/wap/iconDown.png" />
                </div>
                <input type="number" placeholder="您的新手机号" maxlength="11" id="newPhone"/>
            </div>
            <div class="newPhoneCode">
                <input type="number" placeholder="请输入6位短信验证码" maxlength="6" class="oldCode" id="newValidCode"/>
                <input class="code" id="newPhoneCode" value="获取验证码"/>
            </div>
            <div class="userPassword">
                <input type="password" placeholder="登录密码" maxlength="16" id="password"/>
            </div>
        </div>
        <div class="confirm">提 交</div>
    </div>
    <!-- 选择手机号弹窗 -->
    <div class="chosePhone">
        <div class="search">
            <img src="<%=path%>/resources/image/wap/searchIcon.png" />
            <input type="type" placeholder="请选择国家或区号"/>
            <p>取消</p>
        </div>
        <div class="searchList">
            <ul id="phoneAreaContainer">
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
<script type="text/javascript" src="<%=path %>/resources/js/wap/handlebars-v4.0.11.js"></script>
<script type="text/javascript" src="<%=path%>/resources/js/wap/simpleTips_wap.js"></script>

<script type="text/x-handlebars-template" id="getPhoneArea">
    {{#each phoneAreaMap}}
    <li>
        <p class="city">{{city}}</p>
        <p class="cityNum">{{cityNum}}</p>
        <p class="clear"></p>
    </li>
    {{/each}}
</script>

<script>
    $(".back").click(function () {
        window.location.href="<%=path%>/userWap/userInfo/userCenter/show.htm";
    })
    $(".confirm").click(function () {
        var password=$("#password").val();
        var validCode=$("#oldValidCode").val();
        var newValidCode=$("#newValidCode").val();
        var areaCode=$("#newAreaCode").text();
        var phone=$("#newPhone").val();
        if (password=="" || validCode=="" || newValidCode=="" || areaCode=="" || phone=="") {
            openTips("全部为必填项");
            return;
        }
        $.ajax({
            url: "<%=path%>/userWap/userInfo/phone/modify",
            type:'post',
            dataType:'json',
            async: true,
            contentType: "application/json",
            data: JSON.stringify({
                oldValidCode: validCode,
                newValidCode: newValidCode,
                password: password,
                areaCode: areaCode,
                phone:phone
            }),
            success:function(result){
                openTips(result.message);
                if (result.code==1) {
                    setTimeout("skipToUserInfoHtml()",1000);
                }
            },
            error:function(){
                return openTips("服务器错误");
            }
        });
    });
    function skipToUserInfoHtml() {
        window.location.href="<%=path%>/userWap/userInfo/show.htm";
    }
</script>

</html>