<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <meta http-equiv="Cache-Control" content="no-cache" />

    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/wap/common.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/wap/simpleTips_wap.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/wap/forget.css">

    <title>忘记密码</title>
</head>
<body>
    <header>
        <img src="${pageContext.request.contextPath}/resources/image/wap/back.png" class="backimg" id="backImg"/>
        <p>忘记密码</p>
    </header>
    <div class="registerBox">
        <div class="registerContent">
            <form id="forgetForm" method="post">
            <div class="userName">
                <input type="text" placeholder="您的登录账号" id="userAccount" name="userAccount" maxlength="16"
                       onpaste="return false" oncontextmenu="return false" oncopy="return false" oncut="return false"
                       onkeyup="checkoutValue(this)" onblur="checkoutValue(this)"/>
            </div>
            <div class="userPhone">
                <div class="choseNumber">
                    <p class="num" id="area">+86</p>
                    <img src="${pageContext.request.contextPath}/resources/image/wap/iconDown.png" />
                </div>
                <input type="text" placeholder="您注册的手机号" id="phoneNumber" name="phoneNumber" maxlength="11"
                       onkeyup="checkoutNumber(this)" onblur="checkoutNumber(this)" />
            </div>
            <div class="userCode">
                <input type="text" id="validateCode" name="validateCode" placeholder="请输入6位短信验证码" maxlength="6"
                       onkeyup="checkoutNumber(this)" onblur="checkoutNumber(this)"/>
                <p onclick="getValidateCode()">获取验证码</p>
            </div>
            <div class="userPassword">
                <input type="password" id="password" placeholder="新密码为字母、数字，6～16个字符" maxlength="16" autocomplete="new-password"
                       onkeyup="checkoutValue(this)" onblur="checkoutValue(this)"/>
                <input type="hidden" id="encodePwd" name="password"/>
            </div>
            <div class="userPasswordTwo">
                <input type="password" id="repeatPassword" placeholder="请再次输入新密码" maxlength="16" autocomplete="new-password"
                       onkeyup="checkoutValue(this)" onblur="checkoutValue(this)"/>
            </div>
                <input type="hidden" name="phoneAreaCode" id="phoneAreaCode">
            </form>
        </div>
        <div class="confirm" onclick="forgetPwd()" >提 交</div>
        <input id="pageContext" type="hidden" value="${pageContext.request.contextPath}" />
    </div>
    <!-- 选择手机号弹窗 -->
    <div class="chosePhone">
        <div class="search">
            <img src="${pageContext.request.contextPath}/resources/image/wap/searchIcon.png" />
            <input type="text" placeholder="请选择国家或区号" id="country" oninput="showSearch()" onkeyup="checkoutCountry(this)"/>
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

<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/wap/jquery-2.1.4.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/wap/common.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/wap/zepto.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/wap/register.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/wap/checkout.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/wap/handlebars-v4.0.11.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/wap/simpleTips_wap.js"></script>
<!-- 定义handlebars模板 -->
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
    window.onload = function() {
        var code = '${code}';
        var message = '${message}';

        if(code != 1 && message != "") {
            openTips(message);
            return false;
        }
    }
    

    $(function () {
        $("#backImg").click(function () {
            window.location.replace("${pageContext.request.contextPath}/userWap/userLogin/show");
        })
    })

    var b = false;
    function getValidateCode() {
        b = true;
    }

    function forgetPwd()
    {
        var userAccount = $("#userAccount").val();
        var phoneNumber = $("#phoneNumber").val();
        var validateCode = $("#validateCode").val();
        var password = $("#password").val();
        var repeatPassword = $("#repeatPassword").val();

        var commonReg = /^[A-Za-z0-9]{6,16}$/;
        var phoneReg = /^1([358][0-9]|4[579]|66|7[0135678]|9[89])[0-9]{8}$/;
        var chinaArea = '+86';
        var area =  $("#area").html();

        if(!userAccount){
            return openTips("请输入账号 ");
        }
        if (userAccount.length < 6 || userAccount.length > 16) {
            return openTips( "账号长度在6~16个字符之间");
        }
        if (!commonReg.test(userAccount)) {
            return openTips( "账号格式不正确");
        }

        if (!phoneNumber) {
            return openTips( "请输入您的手机号");
        }
        if ((phoneNumber.length < 6 || phoneNumber.length > 11)) {
            return openTips( "请输入正确手机号");
        }
        if (area == chinaArea && !phoneReg.test(phoneNumber)) {
            return openTips( "请输入正确手机号");
        }

        if(!b){
            return openTips("请获取验证码");
        }

        if (!validateCode || validateCode.length!=6) {
            return openTips( "请输入6位短信验证码");
        }

        if (!password) {
            return openTips( "请输入密码,字母、数字，6~16个字符 ");
        }

        if (password.length < 6 || password.length > 16) {
            return openTips( "密码长度在6~16个字符之间");
        }
        if (!commonReg.test(password)) {
            return openTips( "密码格式不正确");
        }

        if (!repeatPassword) {
            return openTips( "请输入重复密码 ");
        }
        if (repeatPassword.length < 6 || repeatPassword.length > 16) {
            return openTips( "重复密码长度在6~16个字符之间");
        }
        if (!commonReg.test(repeatPassword)) {
            return openTips( "重复密码格式不正确");
        }
        if (repeatPassword != password) {
            return openTips("两次密码不匹配");
        }

        $("#phoneAreaCode").val(area);
        var pwd = encode64($("#password").val());
        $("#encodePwd").val(pwd);
        var formData = new FormData(document.getElementById("forgetForm"));
        var url = "${pageContext.request.contextPath}/userWap/forgetPassword/forgetPassword";
        $.ajax({
            url: url,
            type:'post',
            data:formData,
            dataType:'json',
            processData:false,
            contentType:false,
            success:function (result) {
                if (result.code == 1) {
                    openTips(result.message);
                    setTimeout(function () {
                        window.location.href = "${pageContext.request.contextPath}" + "/userWap/userLogin/show";
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


    $('.choseNumber').on('click',function() {
        getphoneArea();
    });
    var list = {};
    function getphoneArea(){
        var url = "${pageContext.request.contextPath}/userWap/forgetPassword/phoneArea";
        $.get(url,function(result){

            var myData = result.data.phoneAreaMap;
            list.phoneAreaMap = [];
            var i = 0;
            for(var key in myData){
                var obj = {"cityNum":key,"city":myData[key]};
                list.phoneAreaMap[i++] = obj;
            }
            injection(list);

        });
    }
    
    function injection(arr) {
        var compileTemplate = $("#getPhoneArea").html();
        var compileComplile = Handlebars.compile(compileTemplate);
        var headerHtml = compileComplile(arr);
        $("#phoneAreaContainer").html(headerHtml);
    }

    //搜索时动态显示区号
    function showSearch() {

        var value = $("#country").val();
        var arr = {};
        arr.phoneAreaMap = [];
        for(var i=0;i<list.phoneAreaMap.length;i++){
            //如果字符串中不包含目标字符会返回-1
            if(list.phoneAreaMap[i].cityNum.indexOf(value)>=0||list.phoneAreaMap[i].city.indexOf(value)>=0){
                arr.phoneAreaMap.push(list.phoneAreaMap[i]);
            }
        }
        injection(arr);
    }



</script>

</html>