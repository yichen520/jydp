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
    
    <title>话题详情</title>
</head>
<body>
    <!-- 头部导航 -->
    <header>
        <img src="<%=path %>/resources/image/wap/back.png" class="backimg" onclick="javascript:window.history.back(-1);"/>
        <p>话题详情</p>
    </header>
    <!-- 内容区域 -->
    <div class="content">
        <p class="noticeTitle title">
            <span class="nTitle title"><span>【公告】</span>{{noticeTitle}}</span>
            <span class="time date"> {{addTimeConvert addTime}}</span>
        </p>
        <div class="editor">
            {{{content}}}
        </div>
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
<script src="<%=path %>/resources/js/wap/handlebars-v4.0.11.js"></script>
<script type="text/javascript">
    //时间转换
    Handlebars.registerHelper("addTimeConvert", function (addTime) {
        var date = new Date(addTime);
        var year = date.getFullYear();
        var month = date.getMonth() + 1;
        var day = date.getDate();
        var hours = date.getHours();
        hours=hours < 10 ? ('0' + hours) : hours;
        var minutes = date.getMinutes();
        minutes=minutes < 10 ? ('0' + minutes) : minutes;
        var seconds = date.getSeconds();
        seconds=seconds < 10 ? ('0' + seconds) : seconds;
        return year + "-" + month + "-" + day + " " + hours + ":" + minutes + ":" + seconds;
    });

    //公告数据填充
    var systemHotData = ${requestScope.systemHot};
    console.log(systemHotData);
    var noticefunc = Handlebars.compile($('.content').html());
    $('.content').html(noticefunc(systemHotData));

</script>

</html>