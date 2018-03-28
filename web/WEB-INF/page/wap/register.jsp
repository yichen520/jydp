<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@include file="/resources/page/common/path.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <meta http-equiv="Cache-Control" content="no-cache"/>
    <link rel="stylesheet" href="<%=path %>/resources/css/wap/common.css">
    <link rel="stylesheet" href="<%=path %>/resources/css/wap/simpleTips_wap.css">
    <link rel="stylesheet" href="<%=path %>/resources/css/wap/register.css">
    <title>注册</title>
</head>
<body>
<div class="back">
    <a href="<%=path %>/userWap/userLogin/show"><img src="<%=path %>/resources/image/wap/back.png"/></a>
</div>
<form id="registerForm" method="post">
    <div class="registerBox">
        <div class="title">注册</div>
        <div class="registerContent">
            <div class="userName">
                <input type="text" id="userAccount" name="userAccount" placeholder="登录帐号为字母、数字，6～16个字符 "
                       onpaste="return false" oncontextmenu="return false" oncopy="return false" oncut="return false"
                       maxLength="16" onkeyup="value=value.replace(/[^\a-\z\A-\Z\d]/g,'')" onblur="validateUser(this)"/>
            </div>
            <div class="userPhone">
                <span>
                    <select id="selectContext">
                        <c:forEach items="${phoneAreaMap }" var="phoneArea">
                            <option value="${phoneArea.key }">${phoneArea.key }</option>
                        </c:forEach>
                     </select>
                </span>
                <input type="text" id="phoneNumber" name="phoneNumber" placeholder="您的手机号" maxlength="11"
                       onkeyup="value=value.replace(/[^\d]/g,'')" onblur="value=value.replace(/[^\d]/g,'')"/>
            </div>
            <div class="userCode">
                <input type="text" id="validateCode" name="validateCode" placeholder="请输入6位短信验证码" maxlength="6"
                       onkeyup="value=value.replace(/[^\d]/g,'')" onblur="value=value.replace(/[^\d]/g,'')"/>
                <p id="mesCodeBtn" onclick="getMesCode(this)">获取验证码</p>
            </div>
            <div class="userPassword">
                <input type="password" id="password" name="password" placeholder="登录密码为字母、数字，6～16个字符" maxLength="16"
                       onpaste="return false" oncontextmenu="return false" oncopy="return false" oncut="return false"
                       onkeyup="value=value.replace(/[^\a-\z\A-\Z\d]/g,'')"
                       onblur="value=value.replace(/[^\a-\z\A-\Z\d]/g,'')"/>
            </div>
            <div class="userPasswordTwo">
                <input type="password" id="repeatPassword" name="repeatPassword" placeholder="请再次输入密码" maxLength="16"
                       onpaste="return false" oncontextmenu="return false" oncopy="return false" oncut="return false"
                       onkeyup="value=value.replace(/[^\a-\z\A-\Z\d]/g,'')"
                       onblur="value=value.replace(/[^\a-\z\A-\Z\d]/g,'')"/>
            </div>
            <div class="userPassword">
                <input type="password" id="payPassword" name="payPassword" placeholder="支付密码为字母、数字，6～16个字符"
                       maxLength="16"
                       onpaste="return false" oncontextmenu="return false" oncopy="return false" oncut="return false"
                       onkeyup="value=value.replace(/[^\a-\z\A-\Z\d]/g,'')"
                       onblur="value=value.replace(/[^\a-\z\A-\Z\d]/g,'')"/>
            </div>
            <div class="userPasswordTwo">
                <input type="password" id="repeatPayPassword" name="repeatPayPassword" placeholder="请再次输入支付密码"
                       maxLength="16"
                       onpaste="return false" oncontextmenu="return false" oncopy="return false" oncut="return false"
                       onkeyup="value=value.replace(/[^\a-\z\A-\Z\d]/g,'')"
                       onblur="value=value.replace(/[^\a-\z\A-\Z\d]/g,'')"/>
            </div>
        </div>
        <div class="footer">
            <input type="checkbox" name="readCheck" id="readCheck"/>
            <p>已阅读并接受<span>《用户注册协议》</span></p>
        </div>
        <div class="confirm" onclick="register()">注册</div>
    </div>
</form>

<form id="identificationForm" action="<%=path %>/userWap/WapIdentificationController/show" method="post">
    <input type="hidden" id="userId" name="userId"/>
    <input type="hidden" id="userAccountIde" name="userAccount"/>
</form>
</body>

<script src="<%=path %>/resources/js/wap/common.js"></script>
<script src="<%=path %>/resources/js/wap/zepto.min.js"></script>
<script src="<%=path %>/resources/js/wap/jquery-2.1.4.min.js"></script>
<script src="<%=path %>/resources/js/wap/simpleTips_wap.js"></script>

<script type="text/javascript">
    //验证用户账号
    var validateUserBoo = false;

    function validateUser(obj) {
        var userAccount = obj.value;
        var reg = /^[A-Za-z0-9]{6,16}$/;
        if (!reg.test(userAccount)) {
            openTips("输入6-16位数字和字母账号");
        }
        var account = obj.value;
        if (account.length < 6 || account.length > 16) {
            validateUserBoo = false;
            openTips("输入6-16数字和字母账号");
            return;
        }

        $.ajax({
            url: '<%=path %>' + "/userWap/userRegister/validateAccount",
            type: 'post',
            dataType: 'json',
            async: true,
            data: {
                userAccount: account
            },
            success: function (result) {
                if (result.code != 1) {
                    openTips(result.message);
                    validateUserBoo = false;
                    return;
                }
                validateUserBoo = true;
            },
            error: function () {
                validateUserBoo = false;
                openTips("服务异常")
            }
        });
    }

    //获取短信验证码
    var getMesCodeFalg = false;

    function getMesCode(obj) {
        //号码检验
        var phoneNumber = $('#phoneNumber').val();
        var area = $("#selectContext").val();
        var regPos = /^\d+(\.\d+)?$/; //非负浮点数
        var phoneReg = /^1([358][0-9]|4[579]|66|7[0135678]|9[89])[0-9]{8}$/;
        if (!phoneNumber || phoneNumber == "" || phoneNumber == null) {
            openTips("请先输入您的手机号")
            return;
        }
        if (!regPos.test(phoneNumber) || phoneNumber.length > 11 || phoneNumber.length < 6) {
            return openTips("请输入正确手机号");
        }
        if (!phoneReg.test(phoneNumber)) {
            return openTips("请输入正确手机号");
        }

        time(obj);
        $.ajax({
            url: '<%=path %>' + "/sendCode/sendPhoneCode",
            data: {
                phoneNumber: area + phoneNumber
            },
            dataType: "json",
            type: 'POST',
            async: true,
            success: function (resultData) {
                var code = resultData.code;
                var message = resultData.message;
                if (code != 1 && message != "") {
                    openTips(message);
                    return;
                }
                getMesCodeFalg = true;
            },
            error: function () {
                openTips("短信发送失败");
            }
        });
    }

    //时间倒计时
    var wait = 60;

    function time(obj) {
        if (wait == 0) {
            $(obj).attr("disabled", false);
            $(obj).text("获取验证码");
            wait = 60;
            return false;
        } else {
            $(obj).attr("disabled", true);
            $(obj).text("(" + wait + ")s重新发送");
            wait--;
        }
        setTimeout(function () {
            time(obj)
        }, 1000)
    }

    function register() {
        //取值
        var userAccount = $("#userAccount").val();
        var password = $("#password").val();
        var repeatPassword = $("#repeatPassword").val();
        var payPassword = $("#payPassword").val();
        var repeatPayPassword = $("#repeatPayPassword").val();
        var phoneArea = $("#selectContext").val();
        var phone = $("#phoneNumber").val();
        var validateCode = $("#validateCode").val();

        var commonReg = /^[A-Za-z0-9]{6,16}$/;
        var phoneReg = /^1([358][0-9]|4[579]|66|7[0135678]|9[89])[0-9]{8}$/;

        //检验
        if (!userAccount) {
            openTips("请输入6-16位的字母、数字账户");
            return;
        } else if (userAccount.length < 6 || userAccount.length > 16) {
            openTips("账号长度为6~16个字符")
            return;
        } else if (!commonReg.test(userAccount)) {
            openTips("账户格式不正确");
            return;
        }

        if (!password) {
            openTips("请输入6-16位的字母、数字登录密码");
            return;
        } else if (password.length < 6 || password.length > 16) {
            openTips("登录密码长度应为6-16位字符");
            return;
        } else if (!commonReg.test(password)) {
            openTips("登录密码格式不正确");
            return;
        } else if (password != repeatPassword) {
            openTips("登录密码和确认登录密码不一致");
            return;
        }

        if (!payPassword) {
            openTips("请输入6-16位的字母、数字支付密码");
            return;
        } else if (payPassword.length < 6 || payPassword.length > 16) {
            openTips("支付密码的长度应为6-16位字符");
            return;
        } else if (!commonReg.test(payPassword)) {
            openTips("支付密码格式不正确");
            return;
        } else if (payPassword != repeatPayPassword) {
            openTips("支付密码和确认支付密码不一致");
            return;
        }

        if (!phone) {
            openTips("请输入您的手机号");
            return;
        } else if (phone.length < 6 || phone.length > 11) {
            openTips("请输入正确手机号");
            return;
        } else if (!phoneReg.test(phone)) {
            openTips("请输入正确手机号");
            return;
        }

        if (!validateCode || validateCode.length != 6) {
            openTips("请输入6位短信验证码");
            return;
        }
        if (!getMesCodeFalg) {
            openTips("请先获取短信验证码");
            return;
        }
        //注册协议
        var readChecked = document.getElementById("readCheck").checked;
        if (!readChecked) {
            openTips("请阅读用户注册协议");
            return;
        }

        //发送至后台
        $.ajax({
            url: '<%=path %>/userWap/userRegister/register',
            type: 'post',
            data: {
                userAccount: userAccount,
                password: password,
                repeatPassword: repeatPassword,
                payPassword: payPassword,
                repeatPayPassword: repeatPayPassword,
                phoneAreaCode: phoneArea,
                phoneNumber: phone,
                validateCode: validateCode,
            },
            dataType: 'json',
            async: true,
            success: function (result) {

                if (result.code == 1) {
                    openTips(result.message);
                    //注册成功跳转到实名认证页面
                    setTimeout(function () {
                        var obj = result.data;
                        var userId = obj.userId;
                        var userAccount = obj.userAccount;

                        $("#userId").val(userId);
                        $("#userAccountIde").val(userAccount);
                        $("#identificationForm").submit();
                    }, 3000)
                } else {
                    openTips(result.message);
                }
                registerBoo = false;
            },
            error: function () {
                registerBoo = false;
                openTips("数据加载出错，请稍候重试");
            }
        });
    }
</script>
</html>