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
    <link rel="stylesheet" href="<%=path%>/resources/css/wap/revise.css">

    <title>修改密码</title>
</head>
<body>
    <header>
        <img src="<%=path%>/resources/image/wap/back.png" class="back"/>
        <p>修改密码</p>
    </header>

    <div class="registerBox">
        <div class="con">
        <!-- 原密码修改 -->
            <div class="registerContent" style="display:block">
                <div class="userPasswordTwo">
                    <input type="password" placeholder="原登录密码" maxlength="16" id="oldPwd"/>
                </div>
                <div class="userPassword">
                    <input  style="width:100%" type="password" placeholder="新密码为字母、数字，6～16个字符" maxlength="16" id="newPwd"/>
                </div>
                <div class="userPassword">
                        <input type="password" placeholder="再次输入新密码" maxlength="16" id="confirmPwd"/>
                    </div>
                <div class="userPhone">
                    <p class="num">${phoneAreaCode} ${phoneNumber}</p>
                </div>
                <div class="userCode">
                    <input type="number" placeholder="请输入6位短信验证码" oninput="if(value.length>6)value=value.slice(0,6)" id="validCode"/>
                    <input class="code" value="获取验证码"/>
                </div>  
            </div>
        </div>
        <div class="confirm">提 交</div>
    </div>
    <!-- loading图 -->
    <div id="loading">
        <i></i>
    </div>
</body>

<script type="text/javascript" src="<%=path%>/resources/js/wap/common.js"></script>
<script type="text/javascript" src="<%=path%>/resources/js/wap/zepto.min.js"></script>
<script type="text/javascript" src="<%=path%>/resources/js/wap/jquery-2.1.4.min.js"></script>
<script type="text/javascript" src="<%=path%>/resources/js/wap/reviseZf.js"></script>
<script type="text/javascript" src="<%=path%>/resources/js/wap/simpleTips_wap.js"></script>

<script>
    $(".back").click(function () {
        window.location.href="<%=path%>/userWap/userInfo/userCenter/show.htm";
    });
    $(".confirm").click(function () {
        var regx = /^[A-Za-z0-9]*$/;
        var oldPwd=$("#oldPwd").val();
        var newPwd=$("#newPwd").val();
        var confirmPwd=$("#confirmPwd").val();
        var validCode=$("#validCode").val();
        if (oldPwd=="" || newPwd== ""|| confirmPwd=="" || validCode=="") {
            openTips("全部为必填项");
            return;
        }
        if (!regx.test(newPwd) || !regx.test(confirmPwd) || newPwd!=confirmPwd) {
            openTips("密码格式不正确或者两次输入密码不一致");
            return;
        }
        if (validCode.length !=6 || newPwd.length<6 || newPwd.length>16) {
            openTips("验证码或者密码长度错误");
            return;
        }
        $.ajax({
            url: "<%=path%>/userWap/userInfo/password/modify",
            type:'post',
            dataType:'json',
            async:true,
            contentType: "application/json",
            data: JSON.stringify({
                oldPassword: oldPwd,
                newPassword: newPwd,
                confirmPassword: confirmPwd,
                validCode:validCode
            }),
            success:function(result){
                openTips(result.message);
                if (result.code==1) {
                    setTimeout("skipToLoginHtml()",1000);
                }
            },
            error:function(){
                return openTips("服务器错误");
            }
        });
    });
    function skipToLoginHtml() {
        window.location.href="<%=path%>/wapLogin";
    }
</script>

</html>