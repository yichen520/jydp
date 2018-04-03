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
        <div class="registerContent">
            <div class="oldPhone">
                <p>原手机号：<span id="oldAreaCode">${phoneAreaCode}</span>&nbsp;<input type="hidden" value="${phoneNumber}" id="oldPhone"/><span id="oldPhoneText"></span></p>
            </div>
            <div class="oldPhoneCode">
                <input type="number" placeholder="请输入6位短信验证码" oninput="if(value.length>6)value=value.slice(0,6)" class="oldCode" id="oldValidCode"/>
                <input class="code" id="oldPhoneCode" value="获取验证码" readonly="readonly"/>
            </div>
            <div class="newPhone">
                <div class="choseNumber">
                    <p class="num" id="newAreaCode">+86</p>
                    <img src="<%=path%>/resources/image/wap/iconDown.png" />
                </div>
                <input type="tel" placeholder="您的新手机号" oninput="if(value.length>11)value=value.slice(0,11)" id="newPhone"/>
            </div>
            <div class="newPhoneCode">
                <input type="number" placeholder="请输入6位短信验证码" oninput="if(value.length>6)value=value.slice(0,6)" class="oldCode" id="newValidCode"/>
                <input class="code" id="newPhoneCode" value="获取验证码" readonly="readonly"/>
            </div>
            <div class="userPassword">
                <input type="password" placeholder="登录密码" maxlength="16" id="password" onkeyup="formatPwd(this)"/>
            </div>
        </div>
        <div class="confirm">提 交</div>
    </div>
    <!-- 选择手机号弹窗 -->
    <div class="chosePhone">
        <div class="search">
            <img src="<%=path%>/resources/image/wap/searchIcon.png" />
            <input type="type" placeholder="请选择国家或区号" id="searchAreaCode" oninput="showSearch()"/>
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

    var path = "<%=path%>"

    //搜索时动态显示区号
    function showSearch() {
        var value = $("#searchAreaCode").val();
        if (!value) {
            $("#phoneAreaContainer li").each(function () {
                $(this).show();
            })
            return;
        }
        $("#phoneAreaContainer li").each(function () {
            var textKey = $(this).children("p:eq(0)").text();
            var textkeyValue = $(this).children("p:eq(1)").text();
            //如果字符串中不包含目标字符会返回-1
            if(textKey.indexOf(value)>=0 || textkeyValue.indexOf(value)>=0 ){
                $(this).show()
            } else {
                $(this).hide();
            }
        })
    }

    $(function () {
        var phone="<%=request.getAttribute("phoneNumber")%>";
        if (phone.length==6) {
            phone=phone.substring(0,3)+"***";
        }
        if (phone.length>6) {
            phone=phone.substring(0,3)+"****"+phone.substring(7)
        }
        $("#oldPhoneText").text(phone);
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
    })
    $(".confirm").click(function () {
        var password=$("#password").val();
        var validCode=$("#oldValidCode").val();
        var newValidCode=$("#newValidCode").val();
        var areaCode=$("#newAreaCode").text();
        var phone=$("#newPhone").val();
        if (validCode=="") {
            openTips("请输入原手机验证码");
            return;
        }
        if (phone=="") {
            openTips("请输入新手机号");
            return;
        }
        if (newValidCode=="") {
            openTips("请输入新手机验证码");
            return;
        }
        if (password=="") {
            openTips("请输入登陆密码");
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