<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@include file="/resources/page/common/path.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <link rel="icon" href="<%=path %>/resources/image/web/icon.ico" type="image/x-ico"/>

    <link rel="stylesheet" type="text/css" href="<%=path %>/resources/css/web/record_coinIn.css"/>
    <link rel="stylesheet" type="text/css" href="<%=path %>/resources/css/web/public.css"/>
    <link rel="stylesheet" type="text/css" href="<%=path %>/resources/css/web/simpleTips.css"/>

    <title>充币成功记录</title>
</head>
<body>
<header id="header"></header>
<div id="menu"></div>


<div class="content">
    <div id="personalMenu"></div>

    <div class="contentRight">
        <div class="title">充币记录</div>

        <div class="main">
            <table class="table" cellpadding="0" cellspacing="0">
                <tr class="tableTitle">
                    <td class="time">订单时间</td>
                    <td class="time">订单流水号</td>
                    <td class="coin">币种</td>
                    <td class="amount">数量</td>
                    <td class="mark">备注</td>
                </tr>
                <c:forEach items="${userRechargeCoinRecordList}" var="item">
                    <tr class="tableInfo">
                        <td class="time"><fmt:formatDate type="time" value="${item.orderTime}"
                                                         pattern="yyyy-MM-dd HH:mm:ss"></fmt:formatDate></td>
                        <td class="time">${item.walletOrderNo}</td>
                        <td class="coin">${item.currencyName}</td>
                        <td class="amount"><fmt:formatNumber type="number" value="${item.currencyNumber}"
                                                             maxFractionDigits="8"></fmt:formatNumber></td>
                        <td class="mark">${item.remark}</td>
                    </tr>
                </c:forEach>
            </table>

            <jsp:include page="/resources/page/common/paging.jsp"></jsp:include>
        </div>
    </div>
</div>

<form id="queryForm" action="<%=path %>/userWeb/userRechargeCoinRecord/show.htm" method="post">
    <input type="hidden" id="queryPageNumber" name="pageNumber">
</form>

<div id="helpFooter"></div>
<div id="footer"></div>


<script type="text/javascript" src="http://libs.baidu.com/jquery/2.1.4/jquery.min.js"></script>
<script type="text/javascript" src="<%=path %>/resources/js/loadPageWeb.js"></script>
<script type="text/javascript" src="<%=path %>/resources/js/simpleTips.js"></script>

<script type="text/javascript">
    window.onload = function () {
        var code = '${code}';
        var message = '${message}';
        if (code != 1 && message != "") {
            openTips(message);
            return false;
        }
    }

    var popObj;
    $(function () {
        $(".recall").click(function () {
            $(".mask").fadeIn();
            $(".recall_pop").fadeIn();
            popObj = ".recall_pop"
        });
        $(".cancel").click(function () {
            $(".mask").fadeOut("fast");
            $(popObj).fadeOut("fast");
        });
        $(".yes").click(function () {
            $(".mask").fadeOut("fast");
            $(popObj).fadeOut("fast");
        });
    });
</script>
</body>
</html>