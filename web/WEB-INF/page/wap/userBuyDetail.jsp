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
    <link rel="stylesheet" href="<%=path %>/resources/css/wap/offline-transaction-sell-confirm.css">

    <title>确认购买</title>
</head>
<body>

<!-- 头部导航 -->
<header>
    <img src="<%=path %>/resources/image/wap/back.png" class="backimg" onclick="back()"/>
    <p>确认购买</p>
</header>

<main>

    <div class="base-info">
        <div><span>经销商名称</span><span>${dealerName}</span></div>
        <div><span>商家手机</span><span>${phoneNumber}</span></div>
        <div><span>商家姓名</span><span>${userName}</span></div>
    </div>

    <div class="receipt-method" style="display: none" id="alipay">
        <div class="item"><span>支付宝账号</span><span>${userPaymentType.paymentAccount}</span></div>
        <div class="qr-bar">
            <img class="qr-code" src="${userPaymentType.paymentImageFormat}" alt="支付二维码">
        </div>
    </div>

    <div class="receipt-method" style="display: none" id="wechat">
        <div class="item"><span>微信账号</span><span>${userPaymentType.paymentAccount}</span></div>
        <div class="qr-bar">
            <img class="qr-code" src="${userPaymentType.paymentImageFormat}" alt="支付二维码">
        </div>
    </div>

    <div class="receipt-method" style="display: none" id="bank">
        <div class="item"><span>银行卡号</span><span>${userPaymentType.paymentAccount}</span></div>
        <div class="item"><span>收款银行</span><span>${userPaymentType.bankName}</span></div>
        <div class="item"><span>收款支行</span><span>${userPaymentType.bankBranch}</span></div>
        <div class="item"><span>银行预留姓名</span><span>${userPaymentType.paymentName}</span></div>
        <div class="item"><span>银行预留电话</span><span>${userPaymentType.paymentPhone}</span></div>
    </div>

    <div class="settle-accounts">
        <div class="item-quantity"><span>购买数量</span><span><fmt:formatNumber type="number" value="${buyNum}" groupingUsed="FALSE" maxFractionDigits="4"/></span></div>
        <div class="item-price">
            <span>支付金额</span>
            <div>
                <span class="symbol">¥ </span>
                <span><fmt:formatNumber type="number" value="${buyNum * otcTransactionPendOrder.pendingRatio}" groupingUsed="FALSE" maxFractionDigits="4"/></span>
            </div>
        </div>
    </div>

    <div class="tips">
        <img src="<%=path %>/resources/image/wap/tip-blue.png">
        <p>提示：请及时保存转账信息。若支付完成商家在24小时内未即时转账，请咨询客服中心处理</p>
    </div>
</main>

<p class="button" onclick="buy()">确 定</p>

<form id="backForm" action="<%=path %>/userWap/otcTradeCenter/transaction.htm" method="post">
    <input name="otcPendingOrderNo" type="hidden" value="${otcTransactionPendOrder.otcPendingOrderNo}"/>
    <input name="userId" type="hidden" value="${otcTransactionPendOrder.userId}"/>
</form>


<div id="loading">
    <i></i>
</div>

<div class="show-qr-bar">
    <img class="show-qr" src="">
</div>
    <input id="buyNum" name="buyNum" type="hidden" value="<fmt:formatNumber type="number" value="${buyNum}" groupingUsed="FALSE" maxFractionDigits="4"/>"/>
    <input id="paymentType" name="paymentType" type="hidden" value="${paymentType}"/>
    <input id="otcPendingOrderNo" name="otcPendingOrderNo" type="hidden" value="${otcTransactionPendOrder.otcPendingOrderNo}"/>
</body>

<script type="text/javascript" src="<%=path %>/resources/js/wap/common.js"></script>
<script type="text/javascript" src="<%=path %>/resources/js/wap/zepto.min.js"></script>
<script type="text/javascript" src="<%=path %>/resources/js/wap/simpleTips_wap.js"></script>
<script type="text/javascript" src="http://libs.baidu.com/jquery/2.1.4/jquery.min.js"></script>
<script type="text/javascript">
    window.onload = function() {
        var paymentType = '${paymentType}';
        if(paymentType ==1){
            $("#bank").css("display","block");
        }

        if(paymentType ==2){
            $("#alipay").css("display","block");
        }
        if(paymentType ==3){
            $("#wechat").css("display","block");
        }

    };

    var addCheckBoo = false;
    function buy() {
        var buyNum = document.getElementById("buyNum").value;
        var paymentType = document.getElementById("paymentType").value;
        var otcPendingOrderNo = document.getElementById("otcPendingOrderNo").value;

        if (buyNum == null || buyNum == "") {
            return openTips("数量不能为空");
        }

        if (paymentType == null || paymentType == '') {
            return openTips("支付方式有误");
        }

        if (otcPendingOrderNo == null || otcPendingOrderNo == '') {
            return openTips("该订单不存在");
        }

        if(addCheckBoo){
            openTips("正在下单，请稍后！");
            return;
        }else{
            addCheckBoo = true;
        }

        $.ajax({
            url: '<%=path %>' + "/userWap/otcTradeCenter/buy.htm",
            data: {
                buyNum : buyNum,
                paymentType : paymentType,
                otcPendingOrderNo : otcPendingOrderNo,
            },//参数
            dataType: "json",
            type: 'POST',
            async: true, //默认异步调用 (false：同步)
            success: function (resultData) {
                var code = resultData.code;
                var message = resultData.message;
                if (code == "4" && message=="未登录") {
                    addCheckBoo = false;
                    openTips(message);
                    setTimeout('window.location.href = \"<%=path%>\" + \"/userWap/userLogin/show\"', 1000);
                    return;
                }
                if (code == "2" && message=="该广告已被删除") {
                    addCheckBoo = false;
                    openTips(message);
                    setTimeout('window.location.href = \"<%=path%>\" + \"/userWap/otcTradeCenter/show\"', 1000);
                    return;
                }
                if (code == "1" && message=="下单成功") {
                    addCheckBoo = false;
                    openTips(message);
                    setTimeout('window.location.href = \"<%=path%>\" + \"/userWap/otcTradeCenter/show.htm\"', 1000);
                }
            },
            error: function () {
                addCheckBoo = false;
                openTips("数据加载出错，请稍候重试");
            }
        });
    }

    function back() {
        $('#backForm').submit();
    }

</script>
<script type="text/javascript">
    $('.show-qr-bar').hide()
    $('.qr-code').on('click', function (e) {
        $('.show-qr-bar').show()
        $('.show-qr')[0].src = this.src
    })
    $('.show-qr-bar').on('click', function (e) {
        $('.show-qr-bar').hide()
    })

</script>
</html>

