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
    <link rel="stylesheet" href="<%=path %>/resources/css/wap/myRecord.css">
    <title>帮助中心</title>
</head>
<body>
    <!-- 头部导航 -->
    <header>
        <img src="<%=path %>/resources/image/wap/back.png" class="backimg"/>
        <p>帮助中心</p>
    </header>
    <!-- 内容区域 -->
    <div class="wrapper">   
        <div class="content helpCenter" id="101014" onclick="showDetail(101014)">
        <p>公司简介</p>
        <img src="<%=path %>/resources/image/wap/nextIcon.png" />
    </div>
        <div class="content helpCenter" id="101013" onclick="showDetail(101013)">
            <p>联系我们</p>
            <img src="<%=path %>/resources/image/wap/nextIcon.png" />
        </div>
        <div class="content helpCenter" id="101015" onclick="showDetail(101015)">
            <p>充值流程</p>
            <img src="<%=path %>/resources/image/wap/nextIcon.png" />
        </div>
        <div class="content helpCenter" id="101016"onclick="showDetail(101016)">
            <p>注册指南</p>
            <img src="<%=path %>/resources/image/wap/nextIcon.png" />
        </div>
        <div class="content helpCenter" id="101017" onclick="showDetail(101017)">
            <p>交易指南</p>
            <img src="<%=path %>/resources/image/wap/nextIcon.png" />
        </div>
        <div class="content helpCenter" id="101010" onclick="showDetail(101010)">
            <p>注册协议</p>
            <img src="<%=path %>/resources/image/wap/nextIcon.png" />
        </div>
    </div>
    <!-- loading图 -->
    <div id="loading">
        <i></i>
    </div>
</body>

<script type="text/javascript" src="<%=path %>/resources/js/wap/common.js"></script>
<script type="text/javascript" src="<%=path %>/resources/js/wap/zepto.min.js"></script>
<script type="text/javascript" src="<%=path %>/resources/js/wap/jquery-2.1.4.min.js"></script>
<%--<script type="text/javascript" src="<%=path %>/resources/js/wap/myRecord.js"></script>--%>
<script type="text/javascript" src="<%=path %>/resources/js/wap/simpleTips_wap.js"></script>

<script type="text/javascript">
    //跳转至我的页面
    $('.backimg').on('click',function(){
        window.location.href = "<%=path %>/userWap/userMine/show.htm"
    });
    //跳转
    function showDetail(helpId) {
        window.location.href="<%=path %>/userWap/wapHelpCenter/show/"+helpId;
    }


</script>



</html>