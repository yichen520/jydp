<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/resources/page/common/path.jsp"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <link rel="icon" href="<%=path %>/resources/image/web/icon.ico" type="image/x-ico" />

    <link rel="stylesheet" type="text/css" href="<%=path %>/resources/css/web/hotDetail.css" />
    <link rel="stylesheet" type="text/css" href="<%=path %>/resources/css/web/public.css" />
    <link rel="stylesheet" type="text/css" href="<%=path %>/resources/css/web/simpleTips.css" />

    <title>话题详情</title>
</head>
<body>
<header id="header"></header>
<div id="menu"></div>

<div class="content">
    <p class="noticeTitle">
        <span class="nTitle"><span>【热门话题】</span>${systemHot.noticeTitle}</span>
        <span class="time"><fmt:formatDate value="${systemHot.addTime}" pattern="yyyy-MM-dd HH:mm:ss"/></span>
    </p>
    <div class="editor">
        ${systemHot.content }
    </div>
</div>


<div id="helpFooter"></div>
<div id="footer"></div>

<script type="text/javascript" src="http://libs.baidu.com/jquery/2.1.4/jquery.min.js"></script>
<script type="text/javascript" src="<%=path %>/resources/js/loadPageWeb.js"></script>
<script type="text/javascript" src="<%=path %>/resources/js/simpleTips.js"></script>

</body>
</html>