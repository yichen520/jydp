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
<div class="header">
    <p class="h_title">
        <img src="<%=path %>/resources/image/back/logo_trade.png" class="logo" />
        <span class="logo_title">交易大盘后台管理系统</span>
    </p>
    <p class="h_side">
        <span class="user">您好，${backerSession.backerAccount }</span>
        <a href="<%=path %>/backerWeb/backerLogin/loginOut.htm" class="out"><img src="<%=path %>/resources/image/back/out.png" />退出登录</a>
    </p>
</div>
</body>
</html>
