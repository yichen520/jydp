<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/resources/page/common/path.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <link rel="icon" href="<%=path %>/resources/image/web/icon.ico" type="image/x-ico" />

    <link rel="stylesheet" type="text/css" href="<%=path %>/resources/css/web/authentication_audit.css" />
    <link rel="stylesheet" type="text/css" href="<%=path %>/resources/css/web/simpleTips.css" />

    <title>实名认证</title>
</head>
<body>
<div class="fHeader">
    <div class="headerInfo">
        <a href="<%=path%>/userWeb/homePage/show">
            <img src="<%=path %>/resources/image/web/trade_logo.png" class="logo"/>
            盛源交易所
        </a>
    </div>
</div>

<div class="content">
    <p class="auditTitle">
        <span class="titleR">实名认证</span>
    </p>

    <div class="audit">
        <span class="auth">
        <c:if test="${identification.identificationStatus == 1}">
            <img src="<%=path %>/resources/image/web/audit.png" class="state"/>
        </c:if>
        <c:if test="${identification.identificationStatus == 2}">
            <img src="<%=path %>/resources/image/web/auditThrough.png" class="state"/>
        </c:if>
        <c:if test="${identification.identificationStatus == 3}">
            <img src="<%=path %>/resources/image/web/auditRefused.png" class="state"/>
        </c:if>
        </span>
        <p class="phoneInput">
            <label class="popName">账号</label>
            <span class="number">${identification.userAccount}</span>
        </p>
        <p class="phoneInput">
            <label class="popName">姓名</label>
            <span class="number">${identification.userName}</span>
        </p>
        <p class="phoneInput">
            <label class="popName">证件类型</label>
            <c:if test="${identification.userCertType == 1}">
                <span class="number">身份证</span>
            </c:if>
            <c:if test="${identification.userCertType == 2}">
                <span class="number">护照</span>
            </c:if>
        </p>
        <p class="phoneInput">
            <label class="popName">证件号</label>
            <span class="number">${identification.userCertNo}</span>
        </p>
        <p class="phoneInput">
            <label class="popName">证件照</label>
            <span class="auditImg">
                <c:forEach items="${identificationImageList}" var="item">
                    <img src="${item.imageUrlFormat}" alt=""/>
                </c:forEach>
            </span>
        </p>
        <p class="phoneInput">
            <label class="popName">审核备注</label>
            <span class="mark">${identification.remark}</span>
        </p>
        <c:if test="${identification.identificationStatus == 3}">
            <input type="text" value="重新认证" class="submit" onfocus="this.blur()" onclick="showAdd()"/>
        </c:if>
    </div>
</div>
<form id="showForm" action="<%=path %>/userWeb/identificationController/showAdd" method="post">
    <input type="hidden" id="userId" name="userId">
    <input type="hidden" id="userAccount" name="userAccount">
</form>


<div class="forgetFoot">盛源交易所版权所有</div>

<script type="text/javascript" src="http://libs.baidu.com/jquery/2.1.4/jquery.min.js"></script>
<script type="text/javascript" src="<%=path %>/resources/js/loadPageWeb.js"></script>
<script type="text/javascript" src="<%=path %>/resources/js/simpleTips.js"></script>

<script type="text/javascript">
    window.onload = function() {
        var code = '${code}';
        var message = '${message}';
        if (code != 1 && message != "") {
            openTips(message);
            return false;
        }
    }

    function showAdd() {
        var userAccount = '${userAccount}';
        var userId = '${userId}';
        $("#userId").val(userId);
        $("#userAccount").val(userAccount);
        $("#showForm").submit();
    }

    $(function () {
        $(".pChoose").click(function () {
            $(".phone").show();
            $(".artificial").hide();
        });
        $(".aChoose").click(function () {
            $(".phone").hide();
            $(".artificial").show();
        })
    });

    $(function () {
        $(".mode span").click(function () {
            $(".mode span").removeClass("chooseState");
            $(this).addClass("chooseState");
        })
    });
</script>
</body>
</html>