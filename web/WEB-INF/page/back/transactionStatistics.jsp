<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@include file="/resources/page/common/path.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <link rel="icon" href="<%=path %>/resources/image/back/icon.ico" type="image/x-ico"/>

    <link rel="stylesheet" type="text/css" href="<%=path %>/resources/css/back/statistics.css"/>
    <link rel="stylesheet" type="text/css" href="<%=path %>/resources/css/back/public.css"/>
    <link rel="stylesheet" type="text/css" href="<%=path %>/resources/css/back/simpleTips.css"/>

    <title>当日统计</title>
</head>
<body>
<div id="header"></div>


<div class="content">
    <div id="menu"></div>

    <div class="contentRight">
        <div class="caption">
            <span class="title">当日统计</span>
        </div>

        <div class="top">
            <div class="askArea">
                <form id="queryForm" action="<%=path %>/backerWeb/transactionStatistics/show.htm" method="post">
                    <p class="condition">统计日期：
                        从&nbsp;<input placeholder="请选择起始时间" class="askTime" id="startTime" name="startTime"
                                      value="${startTime }" onfocus="this.blur()"/>
                        到&nbsp;<input placeholder="请选择结束时间" class="askTime" id="end" name="endTime" value="${endTime }"
                                      onfocus="this.blur()"/>
                    </p>
                    <p class="condition">币种名称：
                        <select class="askSelect" id="currencyId" name="currencyId">
                            <option value="0">全部</option>
                            <c:forEach items="${currencyList}" var="item">
                                <option value="${item.currencyId}">${item.currencyName}</option>
                            </c:forEach>
                        </select>
                    </p>

                    <input type="hidden" id="queryPageNumber" name="pageNumber" value="${pageNumber}">
                    <input type="text" value="查&nbsp;询" class="ask" onfocus="this.blur()" onclick="queryForm()"/>
                </form>
            </div>
        </div>

        <div class="bottom">
            <table class="table" cellspacing="0" cellpadding="0">
                <tr class="tableTitle">
                    <td class="time">日期</td>
                    <td class="coin">币种</td>
                    <td class="amount">当日成交总量</td>
                    <td class="amount">当日成交总金额</td>
                    <td class="coefficient">当日系数</td>
                </tr>
                <c:forEach items="${statisticsList}" var="item">
                    <tr class="tableInfo">
                        <td class="time"><fmt:formatDate type="time" value="${item.statisticsDate}"
                                                         pattern="yyyy-MM-dd"/></td>
                        <td class="coin">${item.currencyName}</td>
                        <td class="amount"><fmt:formatNumber type="number" value="${item.transactionTotalNumber }"
                                                             maxFractionDigits="4"/></td>
                        <td class="amount"><fmt:formatNumber type="number" value="${item.transactionTotalPrice }"
                                                              maxFractionDigits="6"/> XT</td>
                        <td class="coefficient"><fmt:formatNumber type="number" value="${item.currencyCoefficient }"
                                                                  maxFractionDigits="2"/></td>
                    </tr>
                </c:forEach>
            </table>

            <jsp:include page="/resources/page/common/paging.jsp"></jsp:include>
        </div>
    </div>
</div>


<div id="footer"></div>


<script type="text/javascript" src="http://libs.baidu.com/jquery/2.1.4/jquery.min.js"></script>
<script type="text/javascript" src="<%=path %>/resources/js/loadPageBack.js"></script>
<script type="text/javascript" src="<%=path %>/resources/js/simpleTips.js"></script>
<script type="text/javascript" src="<%=path %>/resources/js/laydate.js"></script>

<script type="text/javascript">
    window.onload = function () {
        var code = '${code}';
        var message = '${message}';
        if (code != 1 && message != "") {
            openTips(message);
            return;
        }

        $("#currencyId option").each(function () {
            if ($(this).val() == '${currencyId}') {
                $(this).attr('selected', true);
            }
        });
    }

    function queryForm() {
        $("#queryPageNumber").val("0");
        $("#queryForm").submit();
    }

    lay('.askTime').each(function () {
        laydate.render({
            elem: this,
            trigger: 'click',
            type: 'datetime',
            theme: '#69c0ff'
        });
    });//日期控件
</script>
</body>
</html>