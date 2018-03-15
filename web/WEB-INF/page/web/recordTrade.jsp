<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@include file="/resources/page/common/path.jsp"%>
<script type="text/javascript" src="http://libs.baidu.com/jquery/2.1.4/jquery.min.js"></script>
<script type="text/javascript" src="<%=path %>/resources/js/loadPageWeb.js"></script>
<script type="text/javascript" src="<%=path %>/resources/js/simpleTips.js"></script>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <link rel="icon" href="<%=path %>/resources/image/web/icon.ico" type="image/x-ico" />
    <link rel="stylesheet" type="text/css" href="<%=path %>/resources/css/web/recordTrade.css" />
    <link rel="stylesheet" type="text/css" href="<%=path %>/resources/css/web/public.css" />
    <link rel="stylesheet" type="text/css" href="<%=path %>/resources/css/web/simpleTips.css" />
    <title>成交记录</title>
</head>
<body>
<header id="header"></header>
<div id="menu"></div>


<div class="content">
    <div id="personalMenu"></div>


    <div class="contentRight">
        <div class="title">成交记录</div>
        <div class="main">
            <table class="table" cellpadding="0" cellspacing="0">
                <tr class="tableTitle">
                    <td class="order">交易订单号</td>
                    <td class="coin">币种</td>
                    <td class="type">类型</td>
                    <td class="amount">数量</td>
                    <td class="deal">成交详情</td>
                    <td class="time">完成时间</td>
                </tr>
                <c:forEach items="${dealRecordList}" var="dealRecord">
                <tr class="tableInfo">
                    <td class="order">${dealRecord.orderNo}</td>
                    <td class="coin">${dealRecord.currencyName}</td>
                    <c:if test="${dealRecord.paymentType == 1}">
                        <td class="type in">买入</td>
                    </c:if>
                    <c:if test="${dealRecord.paymentType == 2}">
                        <td class="type pay">卖出</td>
                    </c:if>
                    <c:if test="${dealRecord.paymentType == 3}">
                        <td class="type back">撤销</td>
                    </c:if>
                    <td class="amount">
                        <p>数量：<fmt:formatNumber type="number" value="${dealRecord.currencyNumber}" maxFractionDigits="4"/></p>
                        <p>单价：$<fmt:formatNumber type="number" value="${dealRecord.transactionPrice}" maxFractionDigits="2"/></p>
                    </td>
                    <td class="deal">
                        <c:if test="${dealRecord.paymentType != 3}">
                            <p>成交总额：$<fmt:formatNumber type="number" value="${dealRecord.currencyTotalPrice}" maxFractionDigits="6"/></p>
                            <p>手续费：$<fmt:formatNumber type="number" value="${dealRecord.feeNumber * dealRecord.currencyTotalPrice}" maxFractionDigits="8"/></p>
                        </c:if>
                        <c:if test="${dealRecord.paymentType == 1}">
                            <p>实际支出：$<fmt:formatNumber type="number" value="${dealRecord.feeNumber * dealRecord.currencyTotalPrice + dealRecord.currencyTotalPrice}" maxFractionDigits="6"/></p>
                        </c:if>
                        <c:if test="${dealRecord.paymentType == 2}">
                            <p>实际到账：$<fmt:formatNumber type="number" value="${dealRecord.currencyTotalPrice - dealRecord.feeNumber * dealRecord.currencyTotalPrice }" maxFractionDigits="6"/></p>
                        </c:if>
                    </td>
                    <td class="time"><fmt:formatDate type="time" value="${dealRecord.addTime}" pattern="yyyy-MM-dd HH:mm:ss"></fmt:formatDate></td>
                </tr>
                </c:forEach>
            </table>

            <jsp:include page="/resources/page/common/paging.jsp"></jsp:include>

            <form id="queryForm" action="<%=path %>/userWeb/dealRecord/show.htm" method="post">
                <input type="hidden" id="queryPageNumber" name="pageNumber">
                <input type="hidden" id="pendingOrderNo" name="pendingOrderNo" value="${pendingOrderNo}">
            </form>
        </div>
    </div>
</div>
<div id="helpFooter"></div>
<div id="footer"></div>
</body>
</html>