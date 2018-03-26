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
    <link rel="stylesheet" href="<%=path %>/resources/css/wap/notice.css">
    
    <title>系统公告</title>
</head>
<body>
    <!-- 头部导航 -->
    <header>
        <img src="<%=path %>/resources/image/wap/back.png" class="back"/>
        <p>系统公告</p>
    </header>
    <!-- 内容区域 -->
    <div class="content">
        <ul>
            <c:forEach items="${systemNoticeList}" var="item">
                <a href="<%=path %>/userWap/wapSystemNotice/showNoticeDetail/${item.id}" class="notice" >
                <li>

                    <c:if test="${item.noticeUrl != '1'}">
                        <img src="${item.noticeUrlFormat}" class="noticeImg" />
                    </c:if>
                    <c:if test="${item.noticeUrl == '1'}">
                        <img src="<%=path%>/upload/image/notic_hotTopic.jpg" class="noticeImg" />
                    </c:if>
                    <div class="noticeInfo list-box">
                            <p class="noticeTitle title">【<span>公告</span>】${item.noticeTitle}</p>
                            <p class="time date"><fmt:formatDate value="${item.addTime}" pattern="yyyy-MM-dd HH:mm:ss"/></p>

                    </div>
                    <div class="clear"></div>
                </li>
                </a>
            </c:forEach>
        </ul>
    </div>
    <!-- loading图 -->
    <div id="loading">
        <i></i>
    </div>
</body>

<script src="<%=path %>/resources/js/wap/common.js"></script>
<script src="<%=path %>/resources/js/wap/zepto.min.js"></script>
<script src="<%=path %>/resources/js/wap/jquery-2.1.4.min.js"></script>
<script src="<%=path %>/resources/js/wap/notice.js"></script>
<script src="<%=path %>/resources/js/wap/simpleTips_wap.js"></script>
</html>