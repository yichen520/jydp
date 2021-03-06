<%@ page pageEncoding="UTF-8" %>
<%@include file="/resources/page/common/path.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <meta http-equiv="Cache-Control" content="no-cache" />

    <link rel="stylesheet" href="<%=path %>/resources/css/wap/common.css">
    <link rel="stylesheet" href="<%=path %>/resources/css/wap/simpleTips_wap.css">
    <link rel="stylesheet" href="<%=path %>/resources/css/wap/deatil.css">
    <title>帮助详情</title>
</head>
<body>
    <!-- 头部导航 -->
    <header>
        <img src="<%=path %>/resources/image/wap/back.png" class="backimg"/>
        <%--<p>帮助详情</p>--%>
        <p>${helpTitle}详情</p>
    </header>
    <!-- 内容区域 -->
    <div class="content">
        <p>${systemHelpDO.content}</p>
    </div>
    <!-- loading图 -->
    <div id="loading">
        <i></i>
    </div>
    <input type="hidden" id="requestSource" value="${requestSource}">
</body>

<script type="text/javascript" src="<%=path %>/resources/js/wap/common.js"></script>
<script type="text/javascript" src="<%=path %>/resources/js/wap/zepto.min.js"></script>
<script type="text/javascript" src="<%=path %>/resources/js/wap/jquery-2.1.4.min.js"></script>
<%--<script type="text/javascript" src="<%=path %>/resources/js/wap/notice.js"></script>--%>
<script type="text/javascript" src="<%=path %>/resources/js/wap/simpleTips_wap.js"></script>


<script type="text/javascript">

    //返回到请求的页面
    $('.backimg').on('click', function () {
        window.history.go(-1);
    });
</script>


</html>