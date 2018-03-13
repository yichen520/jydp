<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/resources/page/common/path.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <link rel="icon" href="<%=path %>/resources/image/back/icon.ico" type="image/x-ico" />

    <link rel="stylesheet" type="text/css" href="<%=path %>/resources/css/back/backForm_details.css" />
    <link rel="stylesheet" type="text/css" href="<%=path %>/resources/css/back/public.css" />
    <link rel="stylesheet" type="text/css" href="<%=path %>/resources/css/back/simpleTips.css" />

    <title>后台做单详情</title>
</head>
<body>
<div id="header"></div>


<div class="content">
    <div id="menu"></div>

    <div class="contentRight">
        <div class="caption">
            <span class="title">后台做单详情</span>
        </div>

        <div class="bottom">
            <p class="account">
                <span>当前批次号：${order.orderNo}</span>，
                <span>共：${count}笔</span>，
                <span>当前币种：${order.currencyName}</span>
            </p>

            <table class="table" cellpadding="0" cellspacing="0">
                <tr class="tableTitle">
                    <td class="number">序号</td>
                    <td class="type">类型</td>
                    <td class="amount">成交单价</td>
                    <td class="amount">成交数量</td>
                    <td class="time">执行时间</td>
                </tr>
                <c:forEach items="${resultList}" var="item" varStatus="index">
                    <tr class="tableInfo">
                        <td class="number">${index.index + 1}</td>
                        <c:if test="${item.paymentType == 1}">
                            <td class="type">买入</td>
                        </c:if>
                        <c:if test="${item.paymentType == 2}">
                            <td class="type">卖出</td>
                        </c:if>
                        <td class="amount">$<fmt:formatNumber type="number" value="${item.transactionPrice}" maxFractionDigits="6"/></td>
                        <td class="amount"><fmt:formatNumber type="number" value="${item.currencyNumber}" maxFractionDigits="6"/></td>
                        <td class="time"><fmt:formatDate type="time" value="${item.addTime}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
                    </tr>
                </c:forEach>
            </table>
        </div>
    </div>
</div>

<div id="footer"></div>

<script type="text/javascript" src="http://libs.baidu.com/jquery/2.1.4/jquery.min.js"></script>
<script type="text/javascript" src="<%=path %>/resources/js/loadPageBack.js"></script>
<script type="text/javascript" src="<%=path %>/resources/js/simpleTips.js"></script>

</body>
</html>
