<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/resources/page/common/path.jsp"%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <link rel="icon" href="<%=path %>/resources/image/back/icon.ico" type="image/x-ico" />

    <link rel="stylesheet" type="text/css" href="<%=path %>/resources/css/back/public.css" />
    <link rel="stylesheet" type="text/css" href="<%=path %>/resources/css/back/simpleTips.css" />

    <title>首页</title>
</head>
<body>
<header id="header"></header>


<div class="content">
    <div id="menu"></div>

    <div class="contentRight" style="color: #333333;font-size: 20px;">欢迎登录交易大盘后台管理系统</div>
</div>


<div id="footer"></div>

<script type="text/javascript" src="http://libs.baidu.com/jquery/2.1.4/jquery.min.js"></script>
<script type="text/javascript" src="<%=path %>/resources/js/loadPageBack.js"></script>
<script type="text/javascript" src="<%=path %>/resources/js/simpleTips.js"></script>
<script type="text/javascript">
    window.onload = function() {
        var code = '${code}';
        var message = '${message}';

        if(parseInt(code) != 1 && message != "") {
            openTips(message);
            return false;
        }
    }
</script>
</body>
</html>
