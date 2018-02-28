<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
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
        <c:choose>
            <c:when test="${userSession.userAccount != null}">
                <span class="h_info">您好，<span>${userSession.userAccount}</span>&nbsp;&nbsp;&nbsp;欢迎登录交易大盘</span>
                <a href="<%=path %>/userWeb/userLogin/loginOut.htm" class="out"><img src="<%=path %>/resources/image/web/out.png" />退出登录</a>
            </c:when>
            <c:otherwise>
                <a  href="<%=path %>/userWeb/userLogin/show" class="h_login">登录</a>
                <a  href="<%=path %>/userWeb/userRegister/show" class="h_register">注册</a>
            </c:otherwise>
        </c:choose>
    </p>
</div>
</body>
</html>
