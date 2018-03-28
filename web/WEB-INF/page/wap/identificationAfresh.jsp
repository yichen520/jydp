<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <meta http-equiv="Cache-Control" content="no-cache" />

    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/wap/common.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/wap/simpleTips_wap.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/wap/examine.css">

    <title>实名认证-审核</title>
</head>
<body>
<!-- 头部导航 -->
<header>
    <p>实名认证</p>
    <c:if test="${identification.identificationStatus == 3}">
        <span class="change" onclick="resubmit()">重新提交</span>
    </c:if>
</header>
<!-- 内容区域 -->
<div class="wrapper">
    <div class="list-box">
        <p>审核状态</p>
        <c:if test="${identification.identificationStatus == 1}">
            <span class="smz" >审核中</span>
        </c:if>
        <c:if test="${identification.identificationStatus == 3}">
            <span class="jzsm" style="display:block">审核拒绝</span>
        </c:if>
    </div>
    <div class="mark-list">
        <p>审核备注</p>
        <p class="remark">备注信息</p>
        <div class="clear">${identification.remark}</div>
    </div>
    <div class="list-box">
        <p>账号</p>
        <p class="text" id="userAccount">${identification.userAccount}</p>
    </div>
    <div class="list-box">
        <p>姓名</p>
        <p class="text">${identification.userName}</p>
    </div>
    <div class="list-box">
        <p>证件类型</p>

        <c:if test="${identification.userCertType == 1}">
            <p class="text">身份证</p>
        </c:if>
        <c:if test="${identification.userCertType == 2}">
            <p class="text">护照</p>
        </c:if>
    </div>
    <div class="list-box">
        <p>证件号码</p>
        <p class="text">${identification.userCertNo}</p>
    </div>
    <div class="idcard-box">
        <p class="title">证件照</p>
        <div class="file">
            <div id="blah" class="autor">
                <img src="${identificationImageList[0].imageUrlFormat}">
            </div>
            <div id="blahtwo" class="blahtwo"><img src="${identificationImageList[1].imageUrlFormat}"></div>
        </div>
    </div>
</div>
<!-- loading图 -->
<div id="loading">
    <i></i>
</div>
</body>

<script src="${pageContext.request.contextPath}/resources/js/wap/jquery-2.1.4.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/wap/common.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/wap/zepto.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/wap/aut.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/wap/simpleTips_wap.js"></script>

<script>
    function resubmit() {
        var userAccount = $("#userAccount").html();
        // 取得要提交页面的URL
        var action = "${pageContext.request.contextPath}/userWap/identificationController/showAdd";
        var form = $("<form></form>");
        form.attr('action',action);
        form.attr('method','post');
        input1 = $("<input type='hidden' name='userAccount' />");
        input1.attr('value',userAccount);
        form.append(input1);
        form.appendTo("body");
        form.css('display','none');
        form.submit();
    }
</script>
</html>