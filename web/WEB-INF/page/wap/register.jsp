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
<header>
    <img src="<%=path %>/resources/image/wap/back.png" class="backimg"/>
    <p>注册</p>
</header>
<form id="registerForm" method="post">
    <div class="registerBox">
        <div class="title">注册</div>
        <div class="registerContent">
            <div class="userName">
                <input type="text" id="userAccount" name="userAccount" placeholder="登录账号为字母、数字，6～16个字符 "
                       onpaste="return false" oncontextmenu="return false" oncopy="return false" oncut="return false"
                       maxLength="16" onkeyup="value=value.replace(/[^\a-\z\A-\Z\d]/g,'')" onblur="validateUser(this)"/>
            </div>
            <div class="userPhone">
                <div class="choseNumber" onclick="getPhoneArea()">
                    <p class="num" id="chosePhoneNum">${selectedArea}</p>
                    <img src="<%=path %>/resources/image/wap/iconDown.png"/>
                </div>

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
        <div class="confirm" onclick="register()">注册</div>
        <div class="footer">
            <div class="checkboxBox">
                <input type="checkbox" checked="checked"/>
            </div>
            <p>已阅读并接受<span onclick="readUserAgree()">《用户注册协议》</span></p>
        </div>
    </div>
</form>

<!-- 选择手机号弹窗 -->
<div class="chosePhone" id="chosePhone"></div>
<script type="text/template" id="abcd">
    <div class="search">
        <img src="<%=path %>/resources/image/wap/searchIcon.png"/>
        <input type="type" placeholder="请选择国家或区号" id="country" onkeyup="showSearch()"/>
        <p onclick="backHome()">取消</p>
    </div>
    <div class="searchList" id="searchList">
        {{{phoneArea jsonObject}}}
    </div>
</script>

<form id="identificationForm" action="<%=path %>/userWap/identificationController/showAdd" method="post">
    <input type="hidden" id="userId" name="userId"/>
    <input type="hidden" id="userAccountIde" name="userAccount"/>
</form>
</body>

<script src="<%=path %>/resources/js/wap/common.js"></script>
<script src="<%=path %>/resources/js/wap/zepto.min.js"></script>
<script src="<%=path %>/resources/js/wap/jquery-2.1.4.min.js"></script>
<script src="<%=path %>/resources/js/wap/simpleTips_wap.js"></script>
<script src="<%=path %>/resources/js/wap/handlebars-v4.0.11.js"></script>
<script type="text/javascript">
    var jsonObject;
    $.ajax({
        url: '<%=path %>' + "/userWap/userRegister/getPhoneArea",
        type: 'post',
        dataType: 'json',
        async: true,
        data: {},
        success: function (result) {
            if (result.code != 1) {
                openTips(result.message);
                validateUserBoo = false;
                return;
            }

            //获取的数据存储
            jsonObject = result.data.jsonObject;

            var template = Handlebars.compile($("#abcd").html())
            $('#chosePhone').html(template(result.data));

            $("li[name='phoneName']").each(function () {
                $(this).bind("click", search);
            });
        },
        error: function () {
            validateUserBoo = false;
            openTips("服务异常")
        }
    });

    Handlebars.registerHelper("phoneArea", function (jsonObject123) {
        var jsonObjectString = eval('(' + jsonObject123 + ')');
        var out = "<ul id='ultest'>";
        for (var prop in jsonObjectString) {
            out = out + "<li name='phoneName'>";
            out = out + "<p class='city' id='city'>" + jsonObjectString[prop] + "</p>";
            out = out + "<p class='cityNum' id='cityNum'>" + prop + "</p>";
            out = out + "<p class='clear'>" + "</p>";
            out = out + "</li>";
        }
        out = out + "</ul>";
        return out;
    });


    //获取手机区号并赋值
    function search() {
        var cityNum = $(this).children("p:eq(1)").text();
        $('.chosePhone').css("height", "0");
        $('.chosePhone').hide();
        $('.num').text(cityNum);
    }

    //搜索时动态显示区号
    function showSearch() {
        var value = $("#country").val();
        if (!value) {
            $("#ultest li").each(function () {
                $(this).show();
            })
            return;
        }
        $("#ultest li").each(function () {
            if ($(this).children("p:eq(0)").text() == value || $(this).children("p:eq(1)").text() == value) {
                $(this).show()
            } else {
                $(this).hide();
            }
        })
    }

    //跳转显示选择区号页
    function getPhoneArea() {
        var bgHeight = $(document).height();
        $('.chosePhone').css("height", bgHeight + "px");
        $('.chosePhone').show();
    }
    
    //返回
    function backHome() {
        $('.chosePhone').css("height", "0");
        $('.chosePhone').hide();
    }

    function readUserAgree() {
        window.location.href="<%=path %>/userWap/wapHelpCenter/show/101010?type=1";
    }
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
    var getMesCodeFlag = false;
    function getMesCode(obj) {
        if(getMesCodeFlag || waitBoo){
            return false;
        }

        //号码检验
        var phoneNumber = $('#phoneNumber').val();
        var area = $('#chosePhoneNum').text();
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
                openTips(message);
                if (code != 1 ) {
                    return;
                }
                getMesCodeFlag = true;
            },
            error: function () {
                getMesCodeFlag = false;
                openTips("服务器错误");
            }
        });
    }

    //时间倒计时
    var wait = 60;
    var waitBoo = false;
    function time(obj) {
        if (wait == 0) {
            $(obj).attr("disabled", false);
            $(obj).text("获取验证码");
            wait = 60;
            waitBoo = false;
        } else {
            $(obj).attr("disabled", true);
            $(obj).text("(" + wait + ")s重新发送");
            wait--;
            waitBoo = true;
             setTimeout(function() {
                time(obj)
            },
            1000)
        }
    }

    function register() {
        //取值
        var userAccount = $("#userAccount").val();
        var password = $("#password").val();
        var repeatPassword = $("#repeatPassword").val();
        var payPassword = $("#payPassword").val();
        var repeatPayPassword = $("#repeatPayPassword").val();
        var phoneArea = $("#chosePhoneNum").text();
        var phone = $("#phoneNumber").val();
        var validateCode = $("#validateCode").val();
        var commonReg = /^[A-Za-z0-9]{6,16}$/;
        var phoneReg = /^1([358][0-9]|4[579]|66|7[0135678]|9[89])[0-9]{8}$/;

        //检验
        if (!userAccount) {
            openTips("请输入6-16位的字母、数字账号");
            return;
        } else if (userAccount.length < 6 || userAccount.length > 16) {
            openTips("账号长度为6~16个字符")
            return;
        } else if (!commonReg.test(userAccount)) {
            openTips("账号格式不正确");
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

        if (!getMesCodeFlag) {
            openTips("请先获取短信验证码");
            return;
        }

        if (!validateCode || validateCode.length != 6) {
            openTips("请输入6位短信验证码");
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
        }
        if (!repeatPassword) {
            openTips("请输入重复登录密码");
            return;
        } else if (repeatPassword.length < 6 || repeatPassword.length > 16) {
            openTips("重复登录密码长度应为6-16位字符");
            return;
        } else if (!commonReg.test(repeatPassword)) {
            openTips("重复登录密码格式不正确");
            return;
        }else if (password != repeatPassword) {
            openTips("登录密码和重复登录密码不一致");
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
        }

        if (!repeatPayPassword) {
            openTips("请输入6-16位的字母、数字重复支付密码");
            return;
        } else if (repeatPayPassword.length < 6 || repeatPayPassword.length > 16) {
            openTips("重复支付密码的长度应为6-16位字符");
            return;
        } else if (!commonReg.test(repeatPayPassword)) {
            openTips("重复支付密码格式不正确");
            return;
        } else if (payPassword != repeatPayPassword) {
            openTips("支付密码和重复支付密码不一致");
            return;
        }

        //注册协议
        if (!checked) {
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


   $("#payPassword").blur(function () {
       var password = $("#password").val();
       var payPassword = $("#payPassword").val();
       if(!password){
           openTips("请先输入登录密码");
           return;
       }
       if(!payPassword){
           openTips("请输入支付密码");
           return;
       }
       if(password==payPassword){
           openTips("支付密码和登录密码不能一致");
           return;
       }
   })

    //复选框加载图片的处理
    var checked = true;
    var flag = 1;
    $(".footer").on('click', function () {
        if (flag == 1) {
            $(".checkboxBox").css("background", "url(../../resources/image/wap/check-no.png) no-repeat");
            $(".checkboxBox").css("background-size", "cover");
            flag = 0;
            checked = false;
        } else {
            $(".checkboxBox").css("background", "url(../../resources/image/wap/check-yes.png) no-repeat");
            $(".checkboxBox").css("background-size", "cover");
            flag = 1;
            checked = true;
        }
    })


    $('.backimg').on('click',function () {
        location.href = "javascript:history.back(-1)"
    });
</script>
</html>