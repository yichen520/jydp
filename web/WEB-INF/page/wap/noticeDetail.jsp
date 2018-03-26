<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/resources/page/common/path.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <meta http-equiv="Cache-Control" content="no-cache" />

    <link rel="stylesheet" href="<%=path %>/resources/css/wap/common.css">
    <link rel="stylesheet" href="<%=path %>/resources/css/wap/simpleTips_wap.css">
    <link rel="stylesheet" href="<%=path %>/resources/css/wap/detail.css">
    
    <title>公告详情</title>
</head>
<body>
    <!-- 头部导航 -->
    <header>
        <img src="<%=path %>/resources/image/wap/back.png" class="backimg"/>
        <p>公告详情</p>
    </header>
    <!-- 内容区域 -->
    <div class="content">
        <p class="noticeTitle title">
            <span class="nTitle title"><span>【公告】</span>${systemNotice.noticeTitle}</span>
            <span class="time date"><fmt:formatDate value="${systemNotice.addTime}" pattern="yyyy-MM-dd HH:mm:ss"/></span>
        </p>
        <div class="editor">
            ${systemNotice.content }
        </div>
    </div>
</body>

<script src="<%=path %>/resources/js/wap/common.js"></script>
<script src="<%=path %>/resources/js/wap/zepto.min.js"></script>
<script src="<%=path %>/resources/js/wap/jquery-2.1.4.min.js"></script>
<script src="<%=path %>/resources/js/wap/notice.js"></script>
<script src="<%=path %>/resources/js/wap/simpleTips_wap.js"></script>

</html>