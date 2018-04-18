<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/resources/page/common/path.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <meta http-equiv="Cache-Control" content="no-cache"/>

    <link rel="stylesheet" href="<%=path %>/resources/css/wap/common.css">
    <link rel="stylesheet" href="<%=path %>/resources/css/wap/simpleTips_wap.css">
    <link rel="stylesheet" href="<%=path %>/resources/css/wap/offline-transaction-list.css">

    <title>场外交易</title>
</head>
<body>

<!-- 头部导航 -->
<header>
    <p>场外交易</p>
</header>

<!-- 卡片列表 -->
<main>
<div class="list-bar" id="table">
    <c:forEach items="${otcTransactionPendOrderList}" var="pendOrder">
        <div class="item">
            <div class="item-header">
                <div class="left-content">
                    <div>${pendOrder.currencyName}</div>
                    <c:if test="${pendOrder.orderType == 1}">
                        <div class="buy">购买</div>
                    </c:if>
                    <c:if test="${pendOrder.orderType == 2}">
                        <div class="sell">出售</div>
                    </c:if>
                </div>
                <a class="right-content" onclick="toTransaction('${pendOrder.otcPendingOrderNo}', '${pendOrder.userId}')">去交易</a>
            </div>
            <div class="item-content">
                <div class="label"><span>经销商名称</span><span>比例</span></div>
                <div class="text"><span>${pendOrder.dealerName}</span><span>${pendOrder.pendingRatio}CNY</span></div>
            </div>
            <div class="item-content">
                <div class="label"> <span>交易限额</span><span>地区</span></div>
                <div class="text"><span><fmt:formatNumber type="number" value="${pendOrder.minNumber }" groupingUsed="FALSE" maxFractionDigits="2"/>~<fmt:formatNumber type="number" value="${pendOrder.maxNumber }" groupingUsed="FALSE" maxFractionDigits="2"/></span><span>${pendOrder.area}</span></div>
            </div>
        </div>
    </c:forEach>

</div>

<div class="see-more" id="more" onclick="more()">查看更多</div>
</main>

<form id="transactionForm" action="<%=path %>/userWap/otcTradeCenter/transaction.htm" method="post">
    <input id="otcPendingOrderNo" name="otcPendingOrderNo" type="hidden">
    <input id="userId" name="userId" type="hidden">
</form>

<div id="loading">
    <i></i>
</div>

</body>
<footer>
    <a href="<%=path %>/userWap/homePage/show" class="home">
        <img src="<%=path %>/resources/image/wap/home-nochose.png"  class="home-icon"/>
        <p>首页</p>
    </a>
    <a class="deal open">
        <img src="<%=path %>/resources/image/wap/deal-nochose.png"  class="deal-icon"/>
        <p>交易</p>
    </a>

    <a class="offline-transaction" href="<%=path %>/userWap/otcTradeCenter/show">
        <img src="<%=path %>/resources/image/wap/offline-transaction-chose.png" class="deal-icon"/>
        <p class="chose">场外交易</p>
    </a>

    <a href="<%=path %>/userWap/userInfo/show.htm" class="mine">
        <img src="<%=path %>/resources/image/wap/mine-nochose.png"  class="mine-icon"/>
        <p>我的</p>
    </a>
</footer>
<script type="text/javascript" src="<%=path %>/resources/js/wap/common.js"></script>
<script type="text/javascript" src="<%=path %>/resources/js/wap/zepto.min.js"></script>
<script type="text/javascript" src="<%=path %>/resources/js/wap/simpleTips_wap.js"></script>
<script type="text/javascript" src="http://libs.baidu.com/jquery/2.1.4/jquery.min.js"></script>


<script type="text/javascript">
    var pageNumber = 0;
    var totalPageNumber = 0;
    window.onload = function() {
        pageNumber = '<%=request.getAttribute("pageNumber")%>';
        totalPageNumber = '<%=request.getAttribute("totalPageNumber")%>';

        if (pageNumber >= totalPageNumber - 1) {
            $("#more").text("");
        }
    }

    function more(){
        var pagenumber =  ++pageNumber;
        if(pageNumber >= totalPageNumber)
        {
            $("#more").text("");
            return;
        }

        $.ajax({
            url: '<%=path %>' + "/userWap/otcTradeCenter/showMore",
            data:{
                pageNumber : pagenumber,
            },//参数
            dataType: "json",
            type: 'POST',
            async: true, //默认异步调用 (false：同步)
            success: function (resultData) {
                var code = resultData.code;
                var message = resultData.message;
                if (code != 1 && message != "") {
                    openTips(message);
                    return;
                }
                var result = resultData.data;
                pageNumber = result.pageNumber;
                totalPageNumber = result.totalPageNumber;

                var otcTransactionPendOrderList = result.otcTransactionPendOrderList;
                if(otcTransactionPendOrderList != null && otcTransactionPendOrderList.length > 0){
                    for(var i = 0; i < otcTransactionPendOrderList.length; i++){
                        var otcTransaction = otcTransactionPendOrderList[i];
                        var orderType = otcTransaction.orderType;
                        var buy ="";
                        if(orderType == 1){
                            var buy = '<div class="buy">购买</div>';
                        }else{
                            var buy = '<div class="sell">出售</div>';
                        }
                        var newChild= '<div class="item">' +
                            '<div class="item-header">' +
                            '<div class="left-content">' +
                            '<div>' + otcTransaction.currencyName + '</div>' +
                            buy +
                            '</div>' +
                            '<a class="right-content" onclick="toTransaction(\''+ otcTransaction.otcPendingOrderNo + '\', \''+ otcTransaction.userId + '\')">去交易</a>' +
                            '</div>' +
                            '<div class="item-content">' +
                            '<div class="label"><span>经销商名称</span><span>比例</span></div>' +
                        '<div class="text"><span>' + otcTransaction.dealerName + '</span><span>' + otcTransaction.pendingRatio + 'CNY</span></div>' +
                        '</div>' +
                        '<div class="item-content">' +
                            '<div class="label"> <span>交易限额</span><span>地区</span></div>' +
                        '<div class="text"><span>' + otcTransaction.minNumber  + '~' + otcTransaction.maxNumber + '</span><span>'+ otcTransaction.area +'</span></div>' +
                        '</div>' +
                        '</div>'

                        $("#table").append(newChild);
                    }
                }

                if(pageNumber >= totalPageNumber -1)
                {
                    $("#more").text("");
                }
            },
            error: function () {
                openTips("数据加载出错，请稍候重试");
            }
        });
    }

    function toTransaction(otcPendingOrderNo, userId){
        $('#otcPendingOrderNo').val(otcPendingOrderNo);
        $('#userId').val(userId);
        $('#transactionForm').submit();
    }

</script>
</html>

