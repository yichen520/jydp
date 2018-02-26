<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <link rel="icon" href="<%=path%>/resources/image/back/icon.ico" type="image/x-ico" />

    <link rel="stylesheet" type="text/css" href="<%=path %>/resources/css/back/noticeDetails.css" />
    <link rel="stylesheet" type="text/css" href="<%=path %>/resources/css/back/public.css" />
    <link rel="stylesheet" type="text/css" href="<%=path %>/resources/css/back/simpleTips.css" />

    <title>公告详情</title>
</head>
<body>
<div id="header"></div>


<div class="content">
    <div id="menu"></div>

    <div class="contentRight">
        <div class="caption">
            <span class="title">公告详情</span>
        </div>

        <div class="notice">
            <p class="noticeTitle">
                <span class="nTitle">【<span>${systemNoticeDO.noticeType }</span>】${systemNoticeDO.noticeTitle }</span>
                <span class="time"><fmt:formatDate type="time" value="${systemNotice.addTime }" pattern="yyyy-MM-dd HH:mm:ss"></fmt:formatDate></span>
            </p>

            <div class="editor">${systemNoticeDO.content }</div>

            <div class="operate">
                <a href="<%=path%>/backerWeb/backerNotice/show.htm" class="back">返&nbsp;回</a>
            </div>
        </div>
    </div>
</div>


<div id="footer"></div>

<script type="text/javascript" src="http://libs.baidu.com/jquery/2.1.4/jquery.min.js"></script>
<script type="text/javascript" src="<%=path %>/resources/js/loadPageBack.js"></script>
<script type="text/javascript" src="<%=path %>/resources/js/simpleTips.js"></script>

</body>
</html>
