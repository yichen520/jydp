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

    <link rel="stylesheet" href="<%=path %>/resources/css/wap/offline-transaction-record.css">
    <link rel="stylesheet" href="<%=path %>/resources/css/wap/common.css">
    <link rel="stylesheet" href="<%=path %>/resources/css/wap/simpleTips_wap.css">

    <title>场外交易记录</title>
</head>
<body>

<!-- 头部导航 -->
<header>
    <img src="<%=path %>/resources/image/wap/back.png" class="backimg" onclick="back()"/>
    <p>场外交易记录</p>
</header>

<main>
    <div id="table">
    <c:forEach items="${otcTransactionUserDealList}" var="otcTransactionUserDealList">
    <div class="item">
            <div class="item-header">
                交易流水号：${otcTransactionUserDealList.otcOrderNo}
            </div>
            <div class="item-title">
                <div>${otcTransactionUserDealList.currencyName}</div>
                <c:if test="${otcTransactionUserDealList.dealStatus == 1}">
                    <div class="wait-completed">待完成</div>
                </c:if>
                <c:if test="${otcTransactionUserDealList.dealStatus == 2 || otcTransactionUserDealList.dealStatus == 3}">
                    <div class="wait-completed">待确认</div>
                </c:if>
                <c:if test="${otcTransactionUserDealList.dealStatus == 4}">
                    <div class="completed">已完成</div>
                </c:if>
            </div>
            <div class="item-label">
                <div>数量</div>
                <div>金额</div>
            </div>
            <div class="item-text">
                <div><fmt:formatNumber type="number" value="${otcTransactionUserDealList.currencyNumber }" groupingUsed="FALSE" maxFractionDigits="4"/></div>
                <div>¥<fmt:formatNumber type="number" value="${otcTransactionUserDealList.currencyTotalPrice }" groupingUsed="FALSE" maxFractionDigits="2"/></div>
            </div>
            <div class="item-label">
                <div>类型</div>
                <div>地区</div>
            </div>
            <div class="item-text sell">
                <c:if test="${otcTransactionUserDealList.dealType == 2}">
                    <div class="item-type sell">出售</div>
                </c:if>
                <c:if test="${otcTransactionUserDealList.dealType == 1}">
                    <div class="item-type buy">购买</div>
                </c:if>
                <div>${otcTransactionUserDealList.area}</div>
            </div>
            <div class="item-label">
                <div>申请时间</div>
                <div>完成时间</div>
            </div>
            <div class="item-time">
                <div><fmt:formatDate type="time" value="${otcTransactionUserDealList.addTime}" pattern="yyyy-MM-dd HH:mm:ss" /></div>
                <div><fmt:formatDate type="time" value="${otcTransactionUserDealList.updateTime}" pattern="yyyy-MM-dd HH:mm:ss" /></div>
            </div>
            <div class="item-foot">

                <div class="jump-to-detail" onclick="lookDetail('${otcTransactionUserDealList.otcOrderNo}')">查看详情</div>
                <c:if test="${otcTransactionUserDealList.dealType == 2}">
                    <c:if test="${otcTransactionUserDealList.dealStatus == 1 || otcTransactionUserDealList.dealStatus == 3}">
                        <div class="confirm-receipt" onclick="confirmReceipt('${otcTransactionUserDealList.otcOrderNo}')">确认收款</div>
                    </c:if>
                </c:if>
                <c:if test="${otcTransactionUserDealList.dealType == 1}">
                    <c:if test="${otcTransactionUserDealList.dealStatus == 1 || otcTransactionUserDealList.dealStatus == 3}">
                        <div class="confirm-receipt" onclick="confirmReceipt('${otcTransactionUserDealList.otcOrderNo}')">确认收货</div>
                    </c:if>
                </c:if>
            </div>
    </div>
    </c:forEach>
    </div>
    <div class="see-more" onclick="more()" id="more">查看更多</div>
</main>

<form id="lookDetailForm" action="<%=path %>/userWap/userOtcDealRecord/userOtcDetail.htm" method="post">
    <input id="otcOrderNo" name="otcOrderNo" type="hidden"/>
</form>

<div id="loading">
    <i></i>
</div>

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
                url: '<%=path %>' + "/userWap/userOtcDealRecord/showMore",
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

                    var otcTransactionUserDealList = result.otcTransactionUserDealList;
                    if(otcTransactionUserDealList != null && otcTransactionUserDealList.length > 0){
                        for(var i = 0; i < otcTransactionUserDealList.length; i++){
                            var otcTransaction = otcTransactionUserDealList[i];
                            var dealStatus = otcTransaction.dealStatus;
                            var dealType = otcTransaction.dealType;
                            var updateTime = otcTransaction.updateTime;
                            var addTime = formatDateTime(otcTransaction.addTime);
                            if(updateTime != "" && updateTime != null){
                                updateTime = formatDateTime(otcTransaction.updateTime);
                            }else{
                                updateTime ="";
                            }
                            var dealTypeStr = "";
                            var dealStatusStr = "";
                            var result="";
                            if(dealStatus ==1){
                                dealStatusStr = '<div class="wait-completed">待完成</div>';
                            }
                            if(dealStatus ==2){
                                dealStatusStr = '<div class="wait-completed">待确认</div>';
                            }
                            if(dealStatus ==3){
                                dealStatusStr = '<div class="completed">已完成</div>';
                            }

                            if(dealType == 1){
                                dealTypeStr = '<div class="item-type buy">购买</div>';
                            }
                            if(dealType == 2){
                                dealTypeStr = '<div class="item-type sell">出售</div>';
                            }

                            if(dealStatus ==1 || dealStatus ==3){
                                if(dealType ==2){
                                    result = '<div class="confirm-receipt" onclick="confirmReceipt(\'' +otcTransaction.otcOrderNo+'\')">确认收款</div>';
                                }
                            }
                            if(dealStatus ==1 || dealStatus ==3){
                                if(dealType ==1){
                                    result = '<div class="confirm-receipt" onclick="confirmReceipt(\'' +otcTransaction.otcOrderNo+'\')">确认收货</div>';
                                }
                            }

                            var newChild= '<div class="item">'+
                                '<div class="item-header">' +
                                '交易流水号：'+ otcTransaction.otcOrderNo +
                        '</div>' +
                            '<div class="item-title">' +
                                '<div>' + otcTransaction.currencyName +'</div>' +
                                dealStatusStr +
                                '</div>'+
                                '<div class="item-label">' +
                                '<div>数量</div>' +
                                '<div>金额</div>'+
                                '</div>' +
                                '<div class="item-text">'+
                                '<div>' + otcTransaction.currencyNumber +'</div>' +
                                '<div>¥'+ otcTransaction.currencyTotalPrice +'</div>'+
                                '</div>'+
                                '<div class="item-label">'+
                                '<div>类型</div>'+
                                '<div>地区</div>'+
                                '</div>'+
                                '<div class="item-text sell">' +
                                dealTypeStr +
                                '<div>' +otcTransaction.area+'</div>' +
                                '</div>'+
                                '<div class="item-label">'+
                                '<div>申请时间</div>' +
                                '<div>完成时间</div>' +
                                '</div>'+
                                '<div class="item-time">'+
                                '<div>' + addTime +'</div>' +
                                '<div>' + updateTime +'</div>'+
                                '</div>'+
                                '<div class="item-foot">'+

                                '<div class="jump-to-detail" onclick="lookDetail(\''+otcTransaction.otcOrderNo +'\')">查看详情</div>'+
                                result +
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
    function login() {
        window.location.href = "<%=path%>" + "/userWap/userLogin/show";
    }

    var addCheckBoo = false;
   function confirmReceipt(otcOrderNo) {
        var otcOrderNo = otcOrderNo;
        if(otcOrderNo == null || otcOrderNo == null){
            options("该订单不存在，请刷新页面");
            return;
        }
       if(addCheckBoo){
           openTips("正在确认，请稍后！");
           return;
       }else{
           addCheckBoo = true;
       }
       $.ajax({
           url: '<%=path %>' + "/userWap/userOtcDealRecord/userConfirm.htm",
           data: {
               otcOrderNo : otcOrderNo,

           },//参数
           dataType: "json",
           type: 'POST',
           async: true, //默认异步调用 (false：同步)
           success: function (resultData) {
               var code = resultData.code;
               var message = resultData.message;
               if (code != 1 && message != "") {
                   addCheckBoo = false;
                   openTips(message);
                   return;
               }

               window.location.href = "<%=path%>" + "/userWap/userOtcDealRecord/show.htm";
           },

           error: function () {
               addCheckBoo = false;
               openTips("数据加载出错，请稍候重试");
           }
       });
   }

   //查看详情
    function lookDetail(otcOrderNo) {
        $('#otcOrderNo').val(otcOrderNo);
        $('#lookDetailForm').submit();
    }

    //处理时间
    function formatDateTime(inputTime) {
        var date = new Date(inputTime);
        var y = date.getFullYear();
        var m = date.getMonth() + 1;
        m = m < 10 ? ('0' + m) : m;
        var d = date.getDate();
        d = d < 10 ? ('0' + d) : d;
        var h = date.getHours();
        h = h < 10 ? ('0' + h) : h;
        var minute = date.getMinutes();
        var second = date.getSeconds();
        minute = minute < 10 ? ('0' + minute) : minute;
        second = second < 10 ? ('0' + second) : second;
        return y + '-' + m + '-' + d+' '+h+':'+minute+':'+second;
    };

   function back() {
       window.location.href = "<%=path%>" + "/userWap/dealerOtcRecord/showMyRecord";
   }
</script>
</html>

