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
    <link rel="stylesheet" href="<%=path %>/resources/css/wap/present.css">

    <title>币种提现记录</title>
</head>
<body>
    <header style="border-bottom:0">
        <img src="./images/back.png" class="backimg"/>
        <p>币种提现记录</p>
    </header>
    <!-- 内容区域 -->
    <div class="wrapper">
        <div hidden="hidden" id="queryPageNumber" name="pageNumber"></div>

        <div class="main">
            <p class="titleBox">
                <span class="name">盛源链</span>
                <span class="state">审核通过</span>
            </p>
            <div class="content">
                <p class="num">12345.00</p>
                <p class="timeTitle">申请时间</p>
                <p class="serialTitle">申请流水号</p>
                <p class="time">2016-06-06 13:00:02</p>
                <p class="serial">123456789012347</p>
                <p class="clear"></p>
            </div>
            <p class="money-state">转出状态：
                <span>转出成功</span>
                <span style="display:none">转出中</span>
                <span style="display:none">转出失败</span>
            </p>
            <div class="footer">
                <p class="remark" >备注内容备注备注内容备注备注内容备注</p>
                <p class="withdraw" style="display:none">撤回</p>
                <p class="clear"></p>
            </div>
        </div>
        <div class="main">
            <p class="titleBox">
                <span class="name">盛源链</span>
                <span class="state">审核拒绝</span>
            </p>
            <div class="content">
                <p class="num">12345.00</p>
                <p class="timeTitle">申请时间</p>
                <p class="serialTitle">申请流水号</p>
                <p class="time">2016-06-06 13:00:02</p>
                <p class="serial">123456789012347</p>
                <p class="clear"></p>
            </div>
            <p class="money-state">转出状态：
                <span>转出失败</span>
                <span style="display:none">转出成功</span>
                <span style="display:none">转出中</span>
            </p>
            <div class="footer">
                <p class="remark">备注内容备注备注内容备注备注内容备注</p>
                <p class="withdraw" style="display:none">撤回</p>
                <p class="clear"></p>
            </div>
        </div>
    </div>
    <p class="more">查看更多</p>
    <!-- 撤销弹窗 -->
    <div class="bg">
        <div class="showBox">
            <div class="showBoxTitle">撤回记录</div>
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
<script id="template" type="text/x-handlebars-template">
    {{#each this}}
    <div class="main">
        <p class="titleBox">
            <span class="name">盛源链</span>
            <span class="state">待审核</span>
        </p>
        <div class="content">
            <p class="num">12345.00</p>
            <p class="timeTitle">申请时间</p>
            <p class="serialTitle">申请流水号</p>
            <p class="time">2016-06-06 13:00:02</p>
            <p class="serial">123456789012347</p>
            <p class="clear"></p>
        </div>
        <p class="money-state">转出状态：
            <span>转出中</span>
            <span style="display:none">转出成功</span>
            <span style="display:none">转出失败</span>
        </p>
        <div class="footer">
            <p class="remark" style="display:none">备注内容备注备注内容备注备注内容备注</p>
            <p class="withdraw">撤回</p>
            <p class="clear"></p>
        </div>
    </div>
    {{/each}}
</script>
<script src="<%=path %>/resources/js/wap/common.js"></script>
<script src="<%=path %>/resources/js/wap/zepto.min.js"></script>
<script src="<%=path %>/resources/js/wap/jquery-2.1.4.min.js"></script>
<script src="<%=path %>/resources/js/wap/present.js"></script>
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
</script>
</html>