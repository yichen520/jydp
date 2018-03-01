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
        <a href="#" class="m_logo"><img src="<%=path %>/resources/image/web/trade_logo.png" /></a>交易大盘
    </span>

    <ul class="nav">
        <li class="navInfo"><a href="<%=path %>/userWeb/homePage/show" class="nav_pitch">首页</a></li>
        <li class="navInfo"><a href="#">交易中心</a></li>
        <li class="navInfo"><a href="#">我要充值</a></li>
        <li class="navInfo"><a href="<%=path %>/userWeb/userMessage/show.htm">个人中心</a></li>
    </ul>
</div>
</body>
</html>