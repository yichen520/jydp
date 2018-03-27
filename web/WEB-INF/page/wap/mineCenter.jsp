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
    <link rel="stylesheet" href="<%=path%>/resources/css/wap/myRecord.css">
    
    <title>个人中心</title>
</head>
<body>
    <!-- 头部导航 -->
    <header>
        <img src="<%=path%>/resources/image/wap/back.png" class="backimg"/>
        <p>个人中心</p>
    </header>
    <!-- 内容区域 -->
    <div class="wrapper">   
        <a href="<%=path%>/userWap/userInfo/modifyPayPassword/show.htm"><div class="content">
            <p>修改支付密码</p>
            <img src="<%=path%>/resources/image/wap/nextIcon.png" />
        </div></a>
        <a href="<%=path%>/userWap/userInfo/modifyPassword/show.htm"><div class="content volume">
            <p>修改密码</p>
            <img src="<%=path%>/resources/image/wap/nextIcon.png" />
        </div></a>
        <a href="<%=path%>/userWap/userInfo/modifyPhone/show.htm"><div class="content">
            <p>修改手机号</p>
            <img src="<%=path%>/resources/image/wap/nextIcon.png" />
        </div></a>
        <div class="content">
            <p>银行卡绑定</p>
            <img src="<%=path%>/resources/image/wap/nextIcon.png" />
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
<script type="text/javascript" src="<%=path%>/resources/js/wap/myRecord.js"></script>
<script type="text/javascript" src="<%=path%>/resources/js/wap/simpleTips_wap.js"></script>

<script type="text/javascript">

</script>

</html>