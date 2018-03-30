<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@include file="/resources/page/common/path.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <meta http-equiv="Cache-Control" content="no-cache"/>

    <link rel="stylesheet" href="<%=path %>/resources/css/wap/common.css">
    <link rel="stylesheet" href="<%=path %>/resources/css/wap/simpleTips_wap.css">
    <link rel="stylesheet" href="<%=path %>/resources/css/wap/serviece.css">

    <title>联系客服</title>
</head>
<body>
<div id="contactDiv">
    <script id="contactService" type="ext/x-handlebars-template">
        <!-- 头部导航 -->
        <header>
                <img src="{{webAppPath}}/resources/image/wap/back.png"  onclick="window.location.href='{{webAppPath}}/userWap/userInfo/show.htm'" class="back"/>
            <p>联系客服</p>
        </header>
        <!-- 内容 -->
        <div class="content">
            {{#each userFeedbackList}}
            <div class="list">
                <div class="titleBox">
                    <p class="title">{{feedbackTitle}}</p>
                    <p class="dcl">{{ContactTypeFormat handleStatus}}</p>
                </div>
                <div class="listContent">
                    <p class="main">{{feedbackContent}}</p>
                    <p class="date">{{ContactTimeFormat addTime}}</p>
                </div>
                {{{showHandlerContent handleContent}}}
            </div>
            {{/each}}
            <input type="hidden" id="webAppPath" value="{{webAppPath}}">
            <input type="hidden" id="pageNumber" value="{{pageNumber}}">
            <input type="hidden" id="totalNumber" value="{{totalNumber}}">
            <input type="hidden" id="totalPageNumber" value="{{totalPageNumber}}">
        </div>
        <div class="more">查看更多</div>
    </script>
</div>
<div class="submit">提 交</div>
<!-- 提交问题弹窗 -->
<div class="bg">
    <div class="showBox">
        <div class="showTitle">提交问题</div>
        <div class="showContent">
            <div class="contentTitle">
                <p>标题</p>
                <input id="feedbackTitle" type="text" placeholder="问题标题，2～16个字符" maxlength="16" minlength="2"/>
                <div class="clear"></div>
            </div>
            <div class="contentBox">
                <p>内容</p>
                <textarea id="feedbackContent" placeholder="问题内容" maxlength="500"></textarea>
                <div class="clear"></div>
            </div>
        </div>
        <div class="showFooter">
            <div class="cancel">取消</div>
            <div class="okay">确认</div>
        </div>
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
<script src="<%=path %>/resources/js/wap/handlebars-v4.0.11.js"></script>
<script src="<%=path %>/resources/js/wap/service.js"></script>

</html>