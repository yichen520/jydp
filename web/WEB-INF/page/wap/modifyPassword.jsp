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
                    <input type="password" placeholder="原登录密码" maxlength="16" />
                </div>
                <div class="userPassword">
                    <input  style="width:100%" type="password" placeholder="新密码为字母、数字，6～16个字符" maxlength="16"/>
                </div>
                <div class="userPassword">
                        <input type="password" placeholder="再次输入新密码" maxlength="16"/>
                    </div>
                <div class="userPhone">
                    <p class="num">+86 136****9006</p> 
                </div>
                <div class="userCode">
                    <input type="number" placeholder="请输入6位短信验证码" maxlength="6"/>
                    <p class="code">获取验证码</p>
                </div>  
            </div>
        </div>
        <div class="confirm" onclick="openTip()" >提 交</div>
    </div>
    <!-- 选择手机号弹窗 -->
    <div class="chosePhone">
        <div class="search">
            <img src="<%=path%>/resources/image/wap/searchIcon.png" />
            <input type="text" placeholder="请选择国家或区号"/>
            <p>取消</p>
        </div>
        <div class="searchList">
            <ul>
                <li>
                    <p class="city">阿尔及利亚</p>
                    <p class="cityNum">+35</p>
                    <p class="clear"></p>
                </li>
                <li>
                    <p class="city">安哥拉</p>
                    <p class="cityNum">+23</p>
                    <p class="clear"></p>
                </li>
                <li>
                    <p class="city">阿根廷</p>
                    <p class="cityNum">+54</p>
                    <p class="clear"></p>
                </li>
                <li>
                    <p class="city">亚美尼亚</p>
                    <p class="cityNum">+375</p>
                    <p class="clear"></p>
                </li>
                <li>
                    <p class="city">安道尔共和国</p>
                    <p class="cityNum">+376</p>
                    <p class="clear"></p>
                </li>
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
<script type="text/javascript" src="<%=path%>/resources/js/wap/reviseZf.js"></script>
<script type="text/javascript" src="<%=path%>/resources/js/wap/simpleTips_wap.js"></script>

<script>
    function openTip()
    {
        openTips("阿萨德芳");
    }
    $(".back").click(function () {
        window.location.href="/userWap/userInfo/userCenter/show.htm";
    })
</script>

</html>