<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/resources/page/common/path.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <link rel="icon" href="<%=path %>/resources/image/web/icon.ico" type="image/x-ico" />
    <link rel="stylesheet" type="text/css" href="<%=path %>/resources/css/web/register.css" />
    <link rel="stylesheet" type="text/css" href="<%=path %>/resources/css/web/simpleTips.css" />
    <script type="text/javascript" src="http://libs.baidu.com/jquery/2.1.4/jquery.min.js"></script>
    <script type="text/javascript" src="<%=path %>/resources/js/simpleTips.js"></script>
    <title>注册</title>
</head>
<body>
<div class="fHeader">
    <div class="headerInfo">
        <a href="<%=path %>/userWeb/homePage/show">
            <img src="<%=path %>/resources/image/web/trade_logo.png" class="logo" />
            盛源交易所
        </a>
    </div>
</div>

<div class="content">
    <p class="registerTitle">
        <span class="titleR">注册</span>
        <span class="link">已有账号？<a href="<%=path %>/userWeb/userLogin/show">直接登录</a></span>
    </p>

    <form id="registerForm" method="post">
        <div class="register">
            <p class="registerInput">
                <label class="popName">账号<span class="star">*</span></label>
                <input type="text" class="entry" id="userAccount" name="userAccount" placeholder="字母、数字，6~16个字符"
                       onpaste="return false" oncontextmenu="return false" oncopy="return false" oncut="return false"
                       maxLength="16" onkeyup="value=value.replace(/[^\a-\z\A-\Z\d]/g,'')" onblur="validateUser(this)"/>
                <span class="warmTips">该项一经注册不可更改，请谨慎填写</span>
            </p>

            <p class="registerInput">
                <label class="popName">登录密码<span class="star">*</span></label>
                <input type="password" class="password entry" id="password" placeholder="字母、数字，6~16个字符" maxLength="16"
                       onpaste="return false" oncontextmenu="return false" oncopy="return false" oncut="return false"
                       onkeyup="value=value.replace(/[^\a-\z\A-\Z\d]/g,'')" onblur="value=value.replace(/[^\a-\z\A-\Z\d]/g,'')"/>
                <span class="warmTips">该项为登录账号时所用密码</span>
                <input type="hidden" id="encodePwd" name="password"/>
            </p>
            <p class="registerInput">
                <label class="popName">重复密码<span class="star">*</span></label>
                <input type="password" class="password entry" id="repeatPassword" placeholder="请再次输入登录密码" maxLength="16"
                       onpaste="return false" oncontextmenu="return false" oncopy="return false" oncut="return false"
                       onkeyup="value=value.replace(/[^\a-\z\A-\Z\d]/g,'')" onblur="value=value.replace(/[^\a-\z\A-\Z\d]/g,'')"/>
            </p>
            <p class="registerInput">
                <label class="popName">支付密码<span class="star">*</span></label>
                <input type="password" class="password entry" id="payPassword" placeholder="字母、数字，6~16个字符" maxlength="16"
                       onpaste="return false" oncontextmenu="return false" oncopy="return false" oncut="return false"
                       onkeyup="value=value.replace(/[^\a-\z\A-\Z\d]/g,'')" onblur="value=value.replace(/[^\a-\z\A-\Z\d]/g,'')"/>
                <span class="warmTips">该项为交易时所用密码</span>
                <input type="hidden" id="encodePayPwd" name="payPassword"/>
            </p>
            <p class="registerInput">
                <label class="popName">重复密码<span class="star">*</span></label>
                <input type="password" class="password entry" id="repeatPayPassword" placeholder="请再次输入支付密码" maxLength="16"
                       onpaste="return false" oncontextmenu="return false" oncopy="return false" oncut="return false"
                       onkeyup="value=value.replace(/[^\a-\z\A-\Z\d]/g,'')" onblur="value=value.replace(/[^\a-\z\A-\Z\d]/g,'')"/>
            </p>
            <p class="registerInput">
                <label class="popName">手机号码<span class="star">*</span></label>
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
                <input type="text" class="phone" id="phone" name="phoneNumber" placeholder="绑定的手机号" maxLength="11"
                       onkeyup="value=value.replace(/[^\d]/g,'')" onblur="value=value.replace(/[^\d]/g,'')"/>
               </span>
            </p>
            <p class="registerInput">
                <label class="popName">短信验证码<span class="star">*</span></label>

                <span class="popCode">
                    <input type="text" class="code" id="validateCode" name="validateCode" placeholder="6位短信验证码" maxLength="6"
                           onkeyup="value=value.replace(/[^\d]/g,'')" onblur="value=value.replace(/[^\d]/g,'')"/>
                    <input type="text" value="获取验证码" onfocus="this.blur()" class="message" id="btn" autocomplete="off" />
                </span>
            </p>

            <p class="user">
                <span>
                    <img src="<%=path %>/resources/image/web/click.png" class="check" style="display: none " />
                    <img src="<%=path %>/resources/image/web/clicked.png" class="checked"  />
                    已阅读并接受
                </span>
                <a href="<%=path %>/userWeb/userRegister/registerAgree" class="agreement">《用户注册协议》</a>
            </p>

            <input type="text" value="注&nbsp;册" class="submit" onfocus="this.blur()" onclick="register()"/>
        </div>
    </form>
</div>
<form id="identificationForm" action="<%=path %>/userWeb/identificationController/show" method="post">
    <input type="hidden" id="userId" name="userId"/>
    <input type="hidden" id="userAccountIde" name="userAccount"/>
</form>
<div class="forgetFoot">盛源交易所版权所有</div>

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

    //验证用户账号
    var validateUserBoo = false;
    function validateUser(o) {

        var userAccount = o.value;
        var reg = /^[A-Za-z0-9]{6,16}$/;
        var userAccountEle = $("#userAccount");
        if (!reg.test(userAccount)) {
            changeValue(userAccountEle, "请输入账号，字母、数字，6~16个字符");
            return;
        }

        if (validateUserBoo) {
            return;
        } else {
            validateUserBoo = true;
        }

        var account = o.value;
        if (!account || account.length<6 || account.length>16) {
            validateUserBoo = false;
            return;
        }

        $.ajax({
            url: '<%=path %>' + "/userWeb/userRegister/validateAccount",
            type:'post',
            dataType:'json',
            async:true,
            data:{
                userAccount : account
            },
            success:function(result){
                if (result.code != 1) {
                    changeValue(o, result.message);
                }
                validateUserBoo = false;
            },
            error:function(){
                validateUserBoo = false;
            }
        });
    }

    var wait=60;
    var waitBoo = false;
    function time(o) {
        if (wait == 0) {
            o.removeAttribute("disabled");
            o.value="获取验证码";
            wait = 60;
            waitBoo = false;
        } else {
            o.setAttribute("disabled", true);
            o.value="重新发送(" + wait + ")";
            wait--;
            waitBoo = true;
            setTimeout(function() {
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
                phoneNumber :area+phone
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

    var registerBoo = false;
    var redCheck = true;
    //用户注册
    function register() {
        var userAccountEle = $("#userAccount");
        var passwordEle = $("#password");
        var repeatPasswordEle = $("#repeatPassword");
        var payPasswordEle = $("#payPassword");
        var repeatPayPasswordEle = $("#repeatPayPassword");
        var phoneEle = $("#phone");
        var validateCodeEle = $("#validateCode");

        var userAccount = userAccountEle.val();
        var password = passwordEle.val();
        var repeatPassword = repeatPasswordEle.val();
        var payPassword = payPasswordEle.val();
        var repeatPayPassword = repeatPayPasswordEle.val();
        var phone = phoneEle.val();
        var validateCode = validateCodeEle.val();

        var commonReg = /^[A-Za-z0-9]{6,16}$/;
        var phoneReg = /^1([358][0-9]|4[579]|66|7[0135678]|9[89])[0-9]{8}$/;
        var chinaArea = '${selectedArea}';
        var area =  $(".selectCont").html();

        if (userAccountEle.hasClass("error")) {
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

        if (passwordEle.hasClass("error")) {
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

        if (repeatPasswordEle.hasClass("error")) {
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

        if (payPasswordEle.hasClass("error")) {
            return;
        } else if (!payPassword) {
            changeValue(payPasswordEle,"请输入支付密码，字母、数字，6~16个字符");
            return;
        } else if (payPassword.length < 6 || payPassword.length > 16) {
            changeValue(payPasswordEle, "密码长度在6~16个字符之间");
            return;
        } else if (payPassword == password) {
            changeValue(payPasswordEle, "支付密码不能与登录密码一样");
            return;
        } else if (!commonReg.test(payPassword)) {
            changeValue(payPasswordEle,"支付密码格式不正确");
            return;
        }

        if (repeatPayPasswordEle.hasClass("error")) {
            return;
        } else if (!repeatPayPassword) {
            changeValue(repeatPayPasswordEle, "请输入重复密码，字母、数字，6~16个字符");
            return;
        } else if (repeatPayPassword.length < 6 || repeatPayPassword.length > 16) {
            changeValue(repeatPayPasswordEle, "密码长度在6~16个字符之间");
            return;
        } else if (!commonReg.test(repeatPayPassword)) {
            changeValue(repeatPayPasswordEle, "重复密码格式不正确");
            return;
        }

        if (repeatPayPassword != payPassword) {
            changeValue(repeatPayPasswordEle, "两次密码不匹配");
            changeValue(payPasswordEle, "两次密码不匹配");
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

        if (!redCheck) {
            openTips("请勾选《用户注册协议》");
            return;
        }

        if(registerBoo){
            openTips("正在注册，请稍后！");
            return;
        }else{
            registerBoo = true;
        }

        var pwd = encode64($("#password").val());
        var payPwd = encode64($("#payPassword").val());
        $("#encodePwd").val(pwd);
        $("#encodePayPwd").val(payPwd);

        var formData = new FormData(document.getElementById("registerForm"));
        $.ajax({
            url: '<%=path %>/userWeb/userRegister/register',
            type:'post',
            data:formData,
            dataType:'json',
            processData:false,
            contentType:false,
            success:function (result) {
                if (result.code == 1) {
                    openTips(result.message);
                    setTimeout(function () {
                        var obj = result.data;
                        var userId =obj.userId;
                        var userAccount = obj.userAccount;
                        $("#userId").val(userId);
                        $("#userAccountIde").val(userAccount);
                        $("#identificationForm").submit();
                    },3000)
                } else {
                    openTips(result.message);
                }
                registerBoo = false;
            },
            error:function () {
                registerBoo = false;
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

    $("#payPassword").focus(function(){
        if ($(this).hasClass("error")){
            $(this).removeClass("error");
            $(this).val('');
            $(this).parent().children("img").remove();
            $(this).attr("type","password");

            if ($("#repeatPayPassword").hasClass("error")){
                $("#repeatPayPassword").removeClass("error");
                $("#repeatPayPassword").val('');
                $("#repeatPayPassword").parent().children("img").remove();
                $("#repeatPayPassword").attr("type","password");
            }
        }
    });

    $("#repeatPayPassword").focus(function(){
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
        } else if ('payPassword' == id || 'repeatPayPassword' == id) {
            str = "<img class='delete' id='delete_payPassword' src='<%=path %>/resources/image/web/register.png' />";
        } else if ('phone' == id) {
            str = "<img class='delete' id='delete_phone' src='<%=path %>/resources/image/web/register.png' />";
        }

        $(o).parent().append(str);

        $(".delete").bind("click",function(){
            var id = $(this).attr("id");
            if ('delete_code' == id) {
                $(this).parent().find(".code").val("");
                $(this).parent().find(".code").removeClass("error");
            } else if ('delete_password' == id) {
                $("#password").removeClass("error");
                $("#password").val('');
                $("#password").parent().children("img").remove();
                $("#password").attr("type","password");
                $("#repeatPassword").removeClass("error");
                $("#repeatPassword").val('');
                $("#repeatPassword").parent().children("img").remove();
                $("#repeatPassword").attr("type","password");
            }  else if ('delete_payPassword' == id || 'repeatPayPassword' == id) {
                $("#payPassword").removeClass("error");
                $("#payPassword").val('');
                $("#payPassword").parent().children("img").remove();
                $("#payPassword").attr("type","password");
                $("#repeatPayPassword").removeClass("error");
                $("#repeatPayPassword").val('');
                $("#repeatPayPassword").parent().children("img").remove();
                $("#repeatPayPassword").attr("type","password");
            } else if('delete_phone' == id){
                $(this).parent().find(".phone").val("");
                $(this).parent().find(".phone").removeClass("error");
            } else{
                $(this).parent().find(".entry").val("");
                $(this).parent().find(".entry").removeClass("error");
            }
            $(".delete").remove();
        });
    }

    $(function(){
        $(".user span").click(function () {
            if($(".check").css("display") == "inline"){
                redCheck = true;
                $(".check").hide();
                $(".checked").show();
            }else if($(".check").css("display") == "none"){
                redCheck = false;
                $(".checked").hide();
                $(".check").show();
            }
        });
    });

</script>
</body>
</html>