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
    <link rel="stylesheet" href="<%=path %>/resources/css/wap/dealer-management.css">
    <title>经销商管理</title>
</head>
<body>

<!-- 头部导航 -->
<header>
    <img src="<%=path %>/resources/image/wap/back.png" class="backimg" onclick="back()"/>
    <p>经销商管理</p>
</header>

<main>
    <div id="table">
    <c:forEach items="${otcTransactionPendOrderList}" var="otcTransactionPendOrder">
        <div class="item-bar">
            <div class="item-header">
                <div>
                    <div class="item-header-label">${otcTransactionPendOrder.currencyName}</div>
                    <c:if test="${otcTransactionPendOrder.orderType == 1}">
                        <div class="buy">购买</div>
                    </c:if>
                    <c:if test="${otcTransactionPendOrder.orderType == 2}">
                        <div class="sell">出售</div>
                    </c:if>
                </div>
                <div class="aa" onclick="deleteAds('${otcTransactionPendOrder.otcPendingOrderNo}')">删除</div>
            </div>
            <div class="item-foot">
                <div><span>地区</span><span>${otcTransactionPendOrder.area}</span></div>
                <div><span>比例(CNY)</span><span><fmt:formatNumber type="number" value="${otcTransactionPendOrder.pendingRatio}" groupingUsed="FALSE" maxFractionDigits="2"/></span></div>
                <div><span>交易限额(CNY)</span><span><fmt:formatNumber type="number" value="${otcTransactionPendOrder.minNumber}" groupingUsed="FALSE" maxFractionDigits="2"/>~<fmt:formatNumber type="number" value="${otcTransactionPendOrder.maxNumber}" groupingUsed="FALSE" maxFractionDigits="2"/></span></div>
            </div>
        </div>
    </c:forEach>
    </div>
    <p class="see-more" id="more" onclick="more()">查看更多</p>
</main>

<p class="button" onclick="initiateAds()">发起广告</p>
</body>

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
    };

    function more() {
        var pagenumber =  ++pageNumber;
        if(pageNumber >= totalPageNumber)
        {
            $("#more").text("");
            return;
        }

        $.ajax({
            url: '<%=path %>' + "/userWap/dealerManagment/showMore",
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
                    setTimeout("login()", 1000);
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
                        var orderTypeStr = "";
                        if(orderType ==1){
                            orderTypeStr = '<div class="buy">购买</div>';
                        }
                        if(orderType ==2){
                            orderTypeStr = '<div class="sell">出售</div>';
                        }

                        var newChild= '<div class="item-bar">'+
                            '<div class="item-header">'+
                            '<div>'+
                            '<div class="item-header-label">' +otcTransaction.currencyName +'</div>'+
                            orderTypeStr +
                            '</div>'+
                            '<div class="aa" onclick="deleteAds(\'' +otcTransaction.otcPendingOrderNo +'\')">删除</div>' +
                            '</div>'+
                            '<div class="item-foot">' +
                            '<div><span>地区</span><span>' +otcTransaction.area +'</span></div>'+
                        '<div><span>比例(CNY)</span><span>' + otcTransaction.pendingRatio +'</span></div>'+
                        '<div><span>交易限额(CNY)</span><span>'+otcTransaction.minNumber +'~'+ otcTransaction.maxNumber +'</span></div>' +
                        '</div>'+
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

    var deleteBoo = false;
    function deleteAds(otcPendingOrderNo) {
        var otcPendingOrderNo = otcPendingOrderNo;
        if (otcPendingOrderNo == null || otcPendingOrderNo == "") {
            return openTips("该记录不存在");
        }

        if(deleteBoo){
            openTips("正在删除，请稍后！");
            return;
        }else{
            deleteBoo = true;
        }

        $.ajax({
            url: '<%=path %>' + "/userWap/dealerManagment/deleteOtcTransactionPendOrder.htm",
            data: {
                otcPendingOrderNo : otcPendingOrderNo,

            },//参数
            dataType: "json",
            type: 'POST',
            async: true, //默认异步调用 (false：同步)
            success: function (resultData) {
                var code = resultData.code;
                var message = resultData.message;
                if (code != "1" && message != "") {
                    deleteBoo = false;
                    openTips(message);
                    return
                }
                if(code == "1" && message != ""){
                    openTips(message);
                    setTimeout("queryForm()", 1000);
                }

            },
            error: function () {
                deleteBoo = false;
                openTips("数据加载出错，请稍候重试");
            }
        });
    }

    function queryForm() {
        window.location.href = "<%=path%>" + "/userWap/dealerManagment/show.htm";
    }
    function initiateAds(){
        window.location.href = "<%=path%>" + "/userWap/dealerManagment/openInitiateAds.htm";
    }
    function back() {
            window.location.href = "<%=path%>"+"/userWap/userInfo/show.htm";
    }
</script>
</html>

