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
            盛源交易所
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
                    <span class="select">
                    <span class="selectCont">${selectedArea}</span>
                    <img src="<%=path %>/resources/image/web/area.png" alt=""/>
                    <span class="selectUl">
                        <c:forEach items="${phoneAreaMap}" var="phoneArea">
                           <span class="selectLi">
                               <span class="selectName">${phoneArea.value }</span>
                               <span class="selectNumber">${phoneArea.key }</span>
                           </span>
                        </c:forEach>
                    </span>
                </span>
                <input type="hidden" id="phoneAreaCode" name="phoneAreaCode" value="${selectedArea}"/>
                <input type="text" class="phone" id="phone" name="phoneNumber" placeholder="绑定的手机号码" maxLength="11"
                       onkeyup="value=value.replace(/[^\d]/g,'')" onblur="value=value.replace(/[^\d]/g,'')"/>
               </span>
            </p>

            <p class="phoneInput">
                <label class="popName">短信验证码<span class="star">*</span></label>

                    <span class="popCode">
                        <input type="text" class="code" id="validateCode" name="validateCode" placeholder="6位短信验证码" maxLength="6"
                               onkeyup="value=value.replace(/[^\d]/g,'')" onblur="value=value.replace(/[^\d]/g,'')" />
                        <input type="text" value="获取验证码" onfocus="this.blur()" class="message" id="btn" autocomplete="off"/>
                    </span>
            </p>

            <p class="phoneInput">
                <label class="popName">新密码<span class="star">*</span></label>
                <input type="password" class="password entry" id="password" name="password" placeholder="新密码，6~16个字符，字母和数字" maxLength="16"
                       onkeyup="value=value.replace(/[^\a-\z\A-\Z\d]/g,'')" onblur="value=value.replace(/[^\a-\z\A-\Z\d]/g,'')"/>
            </p>

            <p class="phoneInput">
                <label class="popName">重复密码<span class="star">*</span></label>
                <input type="password" class="password entry" id="repeatPassword"  placeholder="再次输入新密码" maxLength="16"
                       onkeyup="value=value.replace(/[^\a-\z\A-\Z\d]/g,'')" onblur="value=value.replace(/[^\a-\z\A-\Z\d]/g,'')"/>
            </p>
            <input type="text" value="提&nbsp;交" class="submit" onfocus="this.blur()" onclick="forgetPwd()"/>
        </div>
    </form>
</div>
<input type="hidden" id="chinaArea" value="${selectedArea}">

<div class="forgetFoot">盛临九洲版权所有</div>

<script type="text/javascript">
    $(function(){
        $('.select').click(function(){
            $('.selectUl').addClass('selected');
        });
        $('.selectLi').click(function(e){
            e = e || window.event;
            if (e.stopPropagation) {
                e.stopPropagation();
            } else {
                e.cancelBubble = true;
            }
            $('.selectUl').removeClass('selected');
            $('.selectCont').html( $(this).children('.selectNumber').html());
            $('#phoneAreaCode').val($('.selectCont').html());
        });
        $('.select').mouseleave(function(){
            $('.selectUl').removeClass('selected');
        });
    });


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

        var chinaArea = '${selectedArea}';
        var area =  $(".selectCont").html();
        var phone = $("#phone").val();
        var regPos = /^\d+(\.\d+)?$/; //非负浮点数
        var phoneReg = /^1([358][0-9]|4[579]|66|7[0135678]|9[89])[0-9]{8}$/;
        if (!phone) {
            phoneBoo = false;
            return openTips("请输入您的手机号");
        }

        if(!regPos.test(phone) || phone.length > 11 || phone.length < 6){
            phoneBoo = false;
            return openTips("请输入正确手机号");
        }

        if (area == chinaArea && !phoneReg.test(phone)) {
            phoneBoo = false;
            return openTips("请输入正确手机号");
        }

        time(this);
        $.ajax({
            url: '<%=path %>' + "/sendCode/sendPhoneCode",
            type:'post',
            dataType:'json',
            async:true,
            data:{
                phoneNumber : area+phone
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

        var commonReg = /^[A-Za-z0-9]{6,16}$/;
        var phoneReg = /^1([358][0-9]|4[579]|66|7[0135678]|9[89])[0-9]{8}$/;
        var chinaArea = '${selectedArea}';
        var area =  $(".selectCont").html();

        if ($("#userAccount").hasClass("error")) {
            return;
        } else if (!userAccount) {
            changeValue(userAccountEle, "请输入账号，字母、数字，6~16个字符");
            return;
        } else if (userAccount.length < 6 || userAccount.length > 16) {
            changeValue(userAccountEle, "账号长度在6~16个字符之间");
            return;
        } else if (!commonReg.test(userAccount)) {
            changeValue(userAccountEle, "账号格式不正确");
            return;
        }

        if ($("#phone").hasClass("error")) {
            return;
        } else if (!phone) {
            changeValue(phoneEle, "请输入您的手机号");
            return;
        } else if (area != chinaArea && (phone.length < 6 || phone.length > 11)) {
            changeValue(phoneEle, "请输入正确手机号");
            return;
        } else if (area == chinaArea && !phoneReg.test(phone)) {
            changeValue(phoneEle, "请输入正确手机号");
            return;
        }

        if (!validateCode || validateCode.length!=6) {
            changeValue(validateCodeEle, "请输入6位短信验证码");
            return;
        }

        if ($("#password").hasClass("error")) {
            return;
        } else if (!password) {
            changeValue(passwordEle, "请输入登录密码，字母、数字，6~16个字符");
            return;
        } else if (password.length < 6 || password.length > 16) {
            changeValue(passwordEle, "密码长度在6~16个字符之间");
            return;
        } else if (!commonReg.test(password)) {
            changeValue(passwordEle, "登录密码格式不正确");
            return;
        }

        if ($("#repeatPassword").hasClass("error")) {
            return;
        } else if (!repeatPassword) {
            changeValue(repeatPasswordEle, "请输入重复密码，字母、数字，6~16个字符");
            return;
        } else if (repeatPassword.length < 6 || repeatPassword.length > 16) {
            changeValue(repeatPasswordEle, "密码长度在6~16个字符之间");
            return;
        } else if (!commonReg.test(repeatPassword)) {
            changeValue(repeatPasswordEle, "重复密码格式不正确");
            return;
        }
        if (repeatPassword != password) {
            changeValue(repeatPasswordEle, "两次密码不匹配");
            changeValue(passwordEle, "两次密码不匹配");
            return;
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
                    setTimeout(function () {
                        window.location.href = "<%=path%>" + "/userWeb/userLogin/show";
                    },3000);
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

    $("#userAccount").focus(function(){
        if ($(this).hasClass("error")){
            $(this).removeClass("error");
            $(this).val('');
            $(this).parent().children("img").remove();
        }
    });

    $("#password").focus(function(){
        if ($(this).hasClass("error")){
            $(this).removeClass("error");
            $(this).val('');
            $(this).parent().children("img").remove();
            $(this).attr("type","password");

            if ($("#repeatPassword").hasClass("error")){
                $("#repeatPassword").removeClass("error");
                $("#repeatPassword").val('');
                $("#repeatPassword").parent().children("img").remove();
                $("#repeatPassword").attr("type","password");
            }
        }
    });

    $("#repeatPassword").focus(function(){
        if ($(this).hasClass("error")){
            $(this).removeClass("error");
            $(this).val('');
            $(this).parent().children("img").remove();
            $(this).attr("type","password");
        }
    });

    $("#phone").focus(function(){
        if ($(this).hasClass("error")){
            $(this).removeClass("error");
            $(this).val('');
            $(this).parent().children("img").remove();
        }
    });

    $("#validateCode").focus(function(){
        if ($(this).hasClass("error")){
            $(this).removeClass("error");
            $(this).val('');
            $(this).parent().children("img").remove();
        }
    });

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
        } else if ('phone' == id) {
            str = "<img class='delete' id='delete_phone' src='<%=path %>/resources/image/web/register.png' />";
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
            } else if('delete_phone' == id){
                $(this).parent().find(".phone").val("");
                $(this).parent().find(".phone").removeClass("error");
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