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

    <title>修改登录密码</title>
</head>
<body>
    <header>
        <img src="<%=path%>/resources/image/wap/back.png" class="back"/>
        <p>修改登录密码</p>
    </header>

    <div class="registerBox">
        <div class="con">
        <!-- 原密码修改 -->
            <div class="registerContent" style="display:block">
                <div class="userPasswordTwo">
                    <input type="password" placeholder="原登录密码" maxlength="16" id="oldPwd" onkeyup="formatPwd(this)"/>
                </div>
                <div class="userPassword">
                    <input  style="width:100%" type="password" placeholder="新密码为字母、数字，6～16个字符" maxlength="16" id="newPwd" onkeyup="formatPwd(this)"/>
                </div>
                <div class="userPassword">
                        <input type="password" placeholder="再次输入新密码" maxlength="16" id="confirmPwd" onkeyup="formatPwd(this)"/>
                    </div>
                <div class="userPhone">
                    <p class="num"><span id="areaCode">${phoneAreaCode}</span>&nbsp;<input type="hidden" value="${phoneNumber}" id="phoneNumber"/><span id="phoneNumberText"></span></p>
                </div>
                <div class="userCode">
                    <input type="number" placeholder="请输入6位短信验证码" oninput="if(value.length>6)value=value.slice(0,6)" id="validCode"/>
                    <input class="code" value="获取验证码" readonly="readonly"/>
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

    var path = "<%=path%>"

    $(function () {
        var phone="<%=request.getAttribute("phoneNumber")%>";
        if (phone.length==6) {
            phone=phone.substring(0,3)+"***";
        }
        if (phone.length>6) {
            phone=phone.substring(0,3)+"****"+phone.substring(7)
        }
        $("#phoneNumberText").text(phone);
    })

    function formatPwd(obj) {
        var matchStr = /[^\a-\z\A-\Z\d]/g;
        var value = $(obj).val();
        if (matchStr.test(value)) {
            $(obj).get(0).value=$(obj).get(0).value.replace(/[^\a-\z\A-\Z\d]/g,'');
        }
    }

    $(".back").click(function () {
        window.location.href="<%=path%>/userWap/userInfo/userCenter/show.htm";
    });
    $(".confirm").click(function () {
        var regx = /^[A-Za-z0-9]*$/;
        var oldPwd=$("#oldPwd").val();
        var newPwd=$("#newPwd").val();
        var confirmPwd=$("#confirmPwd").val();
        var validCode=$("#validCode").val();
        if(oldPwd=="") {
            openTips("请输入密码");
            return;
        }
        if(newPwd=="") {
            openTips("请输入新密码");
            return;
        }
        if (!regx.test(newPwd)) {
            openTips("新密码格式不正确");
            return;
        }
        if(confirmPwd=="") {
            openTips("请输入确认密码");
            return;
        }
        if(!regx.test(confirmPwd)) {
            openTips("确认密码格式不正确");
            return;
        }
        if (newPwd!=confirmPwd) {
            openTips("两次输入密码不一致");
            return;
        }
        if (newPwd.length<6 || newPwd.length>16) {
            openTips("密码长度错误");
            return;
        }
        if (validCode=="" && oldPwd !="" && newPwd!="" && confirmPwd !="") {
            openTips("验证码错误");
            return;
        }
        if (validCode.length !=6) {
            openTips("验证码长度错误");
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