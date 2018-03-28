<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <meta http-equiv="Cache-Control" content="no-cache"/>

    <link rel="stylesheet" href="<%=path%>/resources/css/wap/common.css">
    <link rel="stylesheet" href="<%=path%>/resources/css/wap/simpleTips_wap.css">
    <link rel="stylesheet" href="<%=path%>/resources/css/wap/assets.css">

    <title>币种资产</title>
</head>
<body>
<!-- 头部导航 -->
<header>
    <img src="<%=path%>/resources/image/wap/back.png" class="back"/>
    <p>币种资产</p>
</header>
<div class="wrapper"></div>
<!-- 撤销弹窗 -->
<div class="bg">
    <div class="showBox">
        <div class="showBoxTitle">撤销委托</div>
        <div class="showBoxContent">是否撤销该委托？</div>
        <div class="showBoxButton">
            <div class="cancelShow">取消</div>
            <div class="okay">确定</div>
        </div>
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
<script type="text/javascript" src="<%=path%>/resources/js/wap/entrust.js"></script>
<script type="text/javascript" src="<%=path %>/resources/js/wap/handlebars-v4.0.11.js"></script>
<script type="text/javascript" src="<%=path%>/resources/js/wap/simpleTips_wap.js"></script>

<script id="table-template" type="text/x-handlebars-template">
    {{#each userCurrencyAssets}}
    <div class="content">
        <div class="header">
            <p class="date">{{currencyName}}</p>
        </div>
        <div class="nav">
            <div class="navLeft">
                <ul>
                    <li>币种总资产：<span>{{totalCurrencyAssets}}</span></li>
                    <li>可用数量：<span>{{currencyNumber}}</span></li>
                    <li>冻结数量：<span>{{currencyNumberLock}}</span></li>
                </ul>
            </div>
        </div>
        <div class="bottom">
            <div class="see">去交易</div>
        </div>
    </div>
    {{/each}}
</script>

<script type="text/javascript">
    $(function () {
        $.ajax({
            url: "<%=path%>/userWap/userInfo/currencyAssets",
            dataType: "json",
            type: 'GET',
            async: true, //默认异步调用 (false：同步)
            success: function (data) {
                if (data.code != 1) {
                    openTips(data.message);
                    return;
                }
                var myTemplate = Handlebars.compile($("#table-template").html());
                $('.wrapper').html(myTemplate(data.data));
            },
            error: function () {
                openTips("数据加载出错，请稍候重试");
            }
        });
    })
</script>
</html>