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
        <a href="#">
            <img src="<%=path %>/resources/image/web/trade_logo.png" class="logo"/>
            交易大盘
        </a>
    </div>
</div>

<div class="content">
    <p class="forgetTitle">
        <span>忘记密码</span>
    </p>

    <div class="forget">
        <p class="phoneInput">
            <label class="popName">登录账号<span class="star">*</span></label>
            <input type="text" class="entry" placeholder="登录账号"/>
        </p>

        <p class="phoneInput">
            <label class="popName">手机号<span class="star">*</span></label>
            <span class="popCode">
                <select class="select">
                    <option>中国&nbsp;+86</option>
                </select>
            <input type="text" class="phone" placeholder="11位绑定的手机号码" />
           </span>
        </p>

        <p class="phoneInput">
            <label class="popName">短信验证码<span class="star">*</span></label>

                <span class="popCode">
                    <input type="text" class="code" placeholder="6位短信验证码"/>
                    <input type="text" value="获取验证码" onfocus="this.blur()" class="message" id="btn" autocomplete="off"/>
                </span>
        </p>

        <p class="phoneInput">
            <label class="popName">新密码<span class="star">*</span></label>
            <input type="password" class="entry" placeholder="新密码，6~16个字符，字母和数字"/>
        </p>

        <p class="phoneInput">
            <label class="popName">重复密码<span class="star">*</span></label>
            <input type="password" class="entry" placeholder="再次输入新密码"/>
        </p>
        <input type="text" value="提&nbsp;交" class="submit" onfocus="this.blur()"/>
    </div>
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
    function time(o) {
        if (wait == 0) {
            o.removeAttribute("disabled");
            o.value = "获取验证码";
            wait = 60;
        } else {
            o.setAttribute("disabled", true);
            o.value = "重新发送(" + wait + ")";
            wait--;
            setTimeout(function () {
                        time(o)
                    },
                    1000)
        }
    }
    document.getElementById("btn").onclick = function () {
        time(this);
    };
</script>

</body>
</html>