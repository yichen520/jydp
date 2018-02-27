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
<div class="headerTop">
    <p class="h_side">
        <span class="h_info">您好，<span>ASDFGHJKLASDFGHJ</span>&nbsp;&nbsp;&nbsp;欢迎登录交易大盘</span>
        <a href="#" class="out"><img src="<%=path %>/resources/image/web/out.png" />退出登录</a>

        <a  href="#" class="h_login">登录</a>
        <a  href="#" class="h_register">注册</a>
    </p>
</div>
</body>
</html>
