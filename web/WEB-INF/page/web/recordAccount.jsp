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
    <link rel="stylesheet" type="text/css" href="<%=path %>/resources/css/web/record_account.css" />
    <link rel="stylesheet" type="text/css" href="<%=path %>/resources/css/web/public.css" />
    <link rel="stylesheet" type="text/css" href="<%=path %>/resources/css/web/simpleTips.css" />
    <title>账户记录</title>
</head>
<body>
<header id="header"></header>
<div id="menu"></div>


<div class="content">
    <div id="personalMenu"></div>

    <div class="contentRight">
        <div class="title">账户记录</div>

        <div class="main">
            <table class="table" cellpadding="0" cellspacing="0">
                <tr class="tableTitle">
                    <td class="time">记录时间</td>
                    <td class="coin">币种</td>
                    <td class="source">记录来源</td>
                    <td class="amount">币种资产</td>
                    <td class="amount">冻结资产</td>
                    <td class="mark">备注</td>
                </tr>

                <c:forEach items="${accountRecordList}" var="accountRecord">
                    <tr class="tableInfo">
                        <td class="time"><fmt:formatDate type="time" value="${accountRecord.addTime}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
                        <td class="coin">${accountRecord.currencyName}</td>
                        <td class="source">${accountRecord.fromType}</td>
                        <c:if test="${accountRecord.balanceNumber >= 0}">
                            <td class="amount in">
                                <c:if test="${accountRecord.balanceNumber > 0}">+</c:if><fmt:formatNumber type="number" value="${accountRecord.balanceNumber}" maxFractionDigits="4"/>
                            </td>
                        </c:if>
                        <c:if test="${accountRecord.balanceNumber < 0}">
                            <td class="amount pay">
                                <fmt:formatNumber type="number" value="${accountRecord.balanceNumber}" maxFractionDigits="4"/>
                            </td>
                        </c:if>
                        <c:if test="${accountRecord.frozenNumber >= 0}">
                            <td class="amount in">
                                <c:if test="${accountRecord.frozenNumber > 0}">+</c:if><fmt:formatNumber type="number" value="${accountRecord.frozenNumber}" maxFractionDigits="4"/>
                            </td>
                        </c:if>
                        <c:if test="${accountRecord.frozenNumber < 0}">
                            <td class="amount pay">
                                <fmt:formatNumber type="number" value="${accountRecord.frozenNumber}" maxFractionDigits="4"/>
                            </td>
                        </c:if>
                        <td class="mark">${accountRecord.remark}</td>
                    </tr>
                </c:forEach>
            </table>

            <form id="queryForm" action="<%=path %>/userWeb/accountRecord/show.htm" method="post">
                <input type="hidden" id="queryPageNumber" name="pageNumber">
            </form>

            <jsp:include page="/resources/page/common/paging.jsp"></jsp:include>
        </div>
    </div>
</div>
<div id="helpFooter"></div>
<div id="footer"></div>
</body>
</html>