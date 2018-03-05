<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/resources/page/common/path.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <link rel="icon" href="<%=path %>/resources/image/web/icon.ico" type="image/x-ico" />
    <link rel="stylesheet" type="text/css" href="<%=path %>/resources/css/web/forget.css" />
    <link rel="stylesheet" type="text/css" href="<%=path %>/resources/css/web/simpleTips.css" />
    <script type="text/javascript" src="http://libs.baidu.com/jquery/2.1.4/jquery.min.js"></script>
    <script type="text/javascript" src="<%=path %>/resources/js/simpleTips.js"></script>
    <title>忘记密码</title>
</head>
<body>
<div class="fHeader">
    <div class="headerInfo">
        <a href="<%=path %>/userWeb/homePage/show">
            <img src="<%=path %>/resources/image/web/trade_logo.png" class="logo"/>
            交易大盘
        </a>
    </div>
</div>

<div class="content">
    <p class="forgetTitle">
        <span>忘记密码</span>
    </p>
    <form id="forgetForm" method="post">
        <div class="forget">
            <p class="phoneInput">
                <label class="popName">登录账号<span class="star">*</span></label>
                <input type="text" class="entry" placeholder="登录账号" id="userAccount" name="userAccount"
                       onpaste="return false" oncontextmenu="return false" oncopy="return false" oncut="return false"
                       maxLength="16" onkeyup="value=value.replace(/[^\a-\z\A-\Z\d]/g,'')" onblur="value=value.replace(/[^\a-\z\A-\Z\d]/g,'')"/>
            </p>

            <p class="phoneInput">
                <label class="popName">手机号<span class="star">*</span></label>
                <span class="popCode">
                    <select class="select" id="phoneAreaCode" name="phoneAreaCode">
                        <option value="+86">中国&nbsp;+86</option>
                        <option value="+001">美国&nbsp;+001</option>
                        <option value="+0061">澳大利亚&nbsp;+0061</option>
                    </select>
                <input type="text" class="phone" id="phone" name="phoneNumber" placeholder="绑定的手机号码" maxLength="11"
                       onkeyup="value=value.replace(/[^\d]/g,'')" onblur="value=value.replace(/[^\d]/g,'')"/>
               </span>
            </p>

            <p class="phoneInput">
                <label class="popName">短信验证码<span class="star">*</span></label>

                    <span class="popCode">
                        <input type="text" class="code" id="validateCode" name="validateCode" placeholder="6位短信验证码" maxLength="6"
                               onkeyup="value=value.replace(/[^\d]/g,'')" onblur="value=value.replace(/[^\d]/g,'')"/>
                        <input type="text" value="获取验证码" onfocus="this.blur()" class="message" id="btn" autocomplete="off"/>
                    </span>
            </p>

            <p class="phoneInput">
                <label class="popName">新密码<span class="star">*</span></label>
                <input type="password" class="entry" id="password" name="password" placeholder="新密码，6~16个字符，字母和数字" maxLength="16"
                       onkeyup="value=value.replace(/[^\a-\z\A-\Z\d]/g,'')" onblur="value=value.replace(/[^\a-\z\A-\Z\d]/g,'')"/>
            </p>

            <p class="phoneInput">
                <label class="popName">重复密码<span class="star">*</span></label>
                <input type="password" class="entry" id="repeatPassword"  placeholder="再次输入新密码" maxLength="16"
                       onkeyup="value=value.replace(/[^\a-\z\A-\Z\d]/g,'')" onblur="value=value.replace(/[^\a-\z\A-\Z\d]/g,'')"/>
            </p>
            <input type="text" value="提&nbsp;交" class="submit" onfocus="this.blur()" onclick="forgetPwd()"/>
        </div>
    </form>
</div>


<div class="forgetFoot">盛临九洲版权所有</div>

<script type="text/javascript">
    $(function () {
        $(".pChoose").click(function () {
            $(".phone").show();
            $(".artificial").hide();
        });
        $(".aChoose").click(function () {
            $(".phone").hide();
            $(".artificial").show();
        })
    });

    $(function () {
        $(".mode span").click(function () {
            $(".mode span").removeClass("chooseState");
            $(this).addClass("chooseState");
        })
    });

    var wait = 60;
    var waitBoo = false;
    function time(o) {
        if (wait == 0) {
            o.removeAttribute("disabled");
            o.value = "获取验证码";
            wait = 60;
            waitBoo = false;
        } else {
            o.setAttribute("disabled", true);
            o.value = "重新发送(" + wait + ")";
            wait--;
            waitBoo = true;
            setTimeout(function () {
                        time(o)
                    },
                    1000)
        }
    }

    //发送短信验证码
    var phoneBoo = false;
    document.getElementById("btn").onclick = function() {
        if(phoneBoo || waitBoo){
            return false;
        }else{
            phoneBoo = true;
        }

        var phone = $("#phone").val();
        if (!phone) {
            phoneBoo = false;
            return openTips("请输入您的手机号");
        }

        time(this);
        $.ajax({
            url: '<%=path %>' + "/sendCode/sendPhoneCode",
            type:'post',
            dataType:'json',
            async:true,
            data:{
                phoneNumber : phone
            },
            success:function(result){
                phoneBoo = false;
                openTips(result.message);
            },
            error:function(){
                phoneBoo = false;
                openTips("服务器错误");
            }
        });
    };

    //忘记密码
    var forgetPwdBoo = false;
    function forgetPwd() {
        var userAccountEle = $("#userAccount");
        var passwordEle = $("#password");
        var repeatPasswordEle = $("#repeatPassword");
        var phoneEle = $("#phone");
        var validateCodeEle = $("#validateCode");

        var userAccount = userAccountEle.val();
        var password = passwordEle.val();
        var repeatPassword = repeatPasswordEle.val();
        var phone = phoneEle.val();
        var validateCode = validateCodeEle.val();

        if (!userAccount || userAccount.length<6 || userAccount.length>16) {
            changeValue(userAccountEle, "请输入账号，字母、数字，6~16个字符");
            return ;
        }
        if (!password || password.length<6 || password.length>16) {
            changeValue(passwordEle, "请输入登录密码，字母、数字，6~16个字符");
            return ;
        }
        if (!repeatPassword || repeatPassword.length<6 || repeatPassword.length>16) {
            changeValue(repeatPasswordEle, "请输入重复密码，字母、数字，6~16个字符");
            return ;
        }
        if (repeatPassword != password) {
            changeValue(repeatPasswordEle, "两次密码不匹配");
            changeValue(passwordEle, "两次密码不匹配");
            return ;
        }
        if (!phone) {
            changeValue(phoneEle, "请输入您的手机号");
            return ;
        }
        if (!validateCode || validateCode.length!=6) {
            changeValue(validateCodeEle, "请输入6位短信验证码");
            return ;
        }

        if(forgetPwdBoo){
            openTips("正在操作，请稍后！");
            return;
        }else{
            forgetPwdBoo = true;
        }

        var formData = new FormData(document.getElementById("forgetForm"));
        $.ajax({
            url: '<%=path %>' + "/userWeb/forgetPassword/forgetPassword",
            type:'post',
            data:formData,
            dataType:'json',
            processData:false,
            contentType:false,
            success:function (result) {
                if (result.code == 1) {
                    openTips(result.message);
                    window.location.href = "<%=path%>" + "/userWeb/userLogin/show";
                } else {
                    openTips(result.message);
                }
                forgetPwdBoo = false;
            },
            error:function () {
                forgetPwdBoo = false;
                openTips("数据加载出错，请稍候重试");
            }
        });
    }

    function changeValue(o, str) {
        $(".delete").remove();
        $(o).addClass("error");
        $(o).attr("type","text");
        $(o).val(str);

        var str = "<img class='delete' src='<%=path %>/resources/image/web/register.png' />";
        var id = $(o).attr("id");
        if ('validateCode' == id) {
            str = "<img class='delete' id='delete_code' style='right: 100px;' src='<%=path %>/resources/image/web/register.png' />";
        } else if ('password' == id || 'repeatPassword' == id) {
            str = "<img class='delete' id='delete_password' src='<%=path %>/resources/image/web/register.png' />";
        }

        $(o).parent().append(str);

        $(".delete").bind("click",function(){
            var id = $(this).attr("id");
            if ('delete_code' == id) {
                $(this).parent().find(".code").val("");
                $(this).parent().find(".code").removeClass("error");
            } else if ('delete_password' == id || 'repeatPassword' == id) {
                $(".password").removeClass("error");
                $(".password").attr("type","password");
                $(".password").val("");
            } else {
                $(this).parent().find(".entry").val("");
                $(this).parent().find(".entry").removeClass("error");
            }
            $(".delete").remove();
        });
    }
</script>

</body>
</html>