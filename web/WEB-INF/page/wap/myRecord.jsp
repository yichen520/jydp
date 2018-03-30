<%@ page pageEncoding="UTF-8" %>
<%@include file="/resources/page/common/path.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <meta http-equiv="Cache-Control" content="no-cache" />
    <link rel="stylesheet" href="<%=path %>/resources/css/wap/common.css">
    <link rel="stylesheet" href="<%=path %>/resources/css/wap/simpleTips_wap.css">
    <link rel="stylesheet" href="<%=path %>/resources/css/wap/myRecord.css">
    <title>我的记录</title>
</head>
<body>
<!-- 头部导航 -->
<header>
    <img src="<%=path %>/resources/image/wap/back.png" class="backimg" id="back"/>
    <p>我的记录</p>
</header>
<!-- 内容区域 -->
<div class="wrapper">
    <div class="content entrust" id="entrust">
        <p>委托记录</p>
        <img src="<%=path %>/resources/image/wap/nextIcon.png"/>
    </div>
    <div class="content volume" id="volume">
        <p>成交记录</p>
        <img src="<%=path %>/resources/image/wap/nextIcon.png"/>
    </div>
<%--    <div class="content">
        <p>充值记录</p>
        <img src="<%=path %>/resources/image/wap/nextIcon.png"/>
    </div>
    <div class="content">
        <p>提现记录</p>
        <img src="<%=path %>/resources/image/wap/nextIcon.png"/>
    </div>--%>
    <div class="content currency" id="currency">
        <p>币种提现记录</p>
        <img src="<%=path %>/resources/image/wap/nextIcon.png"/>
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
<script src="<%=path %>/resources/js/wap/simpleTips_wap.js"></script>


<script type="text/javascript">
    //委托记录
    $("#entrust").click(function () {
        window.location.href = "<%=path%>"+"/userWap/wapTransactionPendOrderController/show.htm"
    })
    //成交记录
    $("#volume").click(function () {
        window.location.href = "<%=path %>"+"/userWap/wapDealRecord/show.htm";
    })
    //币种提现记录
    $("#currency").click(function () {
        window.location.href = "<%=path %>"+"/userWap/presentRecord/show.htm";
    })
    //返回
    $("#back").click(function () {
        window.location.href = "<%=path%>"+"/userWap/userInfo/show.htm";
    })
</script>
</html>