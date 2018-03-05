<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
</head>

<body>
<div class="menu">
    <span class="logo">
        <a href="<%=path %>/userWeb/homePage/show" class="m_logo"><img src="<%=path %>/resources/image/web/trade_logo.png" /></a>交易大盘
    </span>

    <ul class="nav">
        <li class="navInfo"><a id="webHome" href="<%=path %>/userWeb/homePage/show">首页</a></li>
        <li class="navInfo"><a href="#">交易中心</a></li>
        <li class="navInfo"><a href="#">我要充值</a></li>
        <li class="navInfo"><a id="message" href="<%=path %>/userWeb/userMessage/show.htm">个人中心</a></li>
    </ul>
</div>
<script type="text/javascript">
    function showPersonalMenu() {
        var menuObj = null;
        //获取当前url路径
        var curUrl = window.location.href;
        //首页
        if (curUrl.indexOf("homePage/show") > 0) {
            menuObj = $("#webHome");
        }
        //个人中心
        if (curUrl.indexOf("/userWeb/userMessage/show.htm") > 0) {
            menuObj = $("#message");
        }

        if(menuObj != null){
            menuObj.addClass("nav_pitch");
            clearInterval(showPersonalMenuId);
        }
    }
    var showPersonalMenuId = setInterval(showPersonalMenu, 20);
</script>

</body>
</html>