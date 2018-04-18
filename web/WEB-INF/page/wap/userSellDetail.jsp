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
    <title>确认出售</title>
</head>
<body>

<!-- 头部导航 -->
<header>
    <img src="<%=path %>/resources/image/wap/back.png" class="backimg" onclick="back()"/>
    <p>确认出售</p>
</header>

<main>
    <div class="base-info">
        <div><span>经销商名称</span><span>${userName}</span></div>
        <div><span>商家手机</span><span>${phoneNumber}</span></div>
        <div><span>商家姓名</span><span>${userName}</span></div>
    </div>

    <div class="receipt-method" style="display: none" id="alipay">
        <div class="item"><span>支付宝账号</span><span id="alipayPaymentAccount">${paymentAccount}</span></div>
        <div class="qr-bar">
            <img class="qr-code" src="${imageUrlFormat}">
        </div>
    </div>

    <div class="receipt-method" style="display: none" id="wechat">
        <div class="item"><span>微信账号</span><span id="wechatPaymentAccount">${paymentAccount}</span></div>
        <div class="qr-bar">
            <img class="qr-code" src="${imageUrlFormat}">
        </div>
    </div>

    <div class="receipt-method" style="display: none" id="bank">
        <div class="item"><span>银行卡号</span><span id="paymentAccount">${paymentAccount}</span></div>
        <div class="item"><span>收款银行</span><span id="bankName">${bankName}</span></div>
        <div class="item"><span>收款支行</span><span id="bankBranch">${bankBranch}</span></div>
        <div class="item"><span>银行预留姓名</span><span id="paymentName">${paymentName}</span></div>
        <div class="item"><span>银行预留电话</span><span id="paymentPhone">${paymentPhone}</span></div>
    </div>

    <div class="settle-accounts">
        <div class="item-quantity"><span>出售数量</span><span><fmt:formatNumber type="number" value="${sellNum}" groupingUsed="FALSE" maxFractionDigits="4"/></span></div>
        <div class="item-price">
            <span>获得金额</span>
            <div>
                <span class="symbol">¥ </span>
                <span><fmt:formatNumber type="number" value="${sellNum * otcTransactionPendOrder.pendingRatio}" groupingUsed="FALSE" maxFractionDigits="2"/></span>
            </div>
        </div>
    </div>

    <div class="tips">
        <img src="<%=path %>/resources/image/wap/tip-blue.png">
        <p>提示：请及时保存转账信息。若支付完成商家在24小时内未即时转账，请咨询客服中心处理</p>
    </div>
</main>

<form id="backForm" action="<%=path%>/userWap/otcTradeCenter/backSell.htm" method="post">
    <input name="otcPendingOrderNo"value="${otcTransactionPendOrder.otcPendingOrderNo}" type="hidden">
    <input name="imageUrl" value="${imageUrl}" type="hidden">
</form>

<p class="button" onclick="sell()">确 定</p>

<input type="hidden" id="imageUrl" name="imageUrl" value="${imageUrl}"/>
<input id="sellNum" name="sellNum" type="hidden" value="${sellNum}"/>
<input id="paymentType" name="paymentType" type="hidden" value="${paymentType}"/>
<input id="otcPendingOrderNo" name="otcPendingOrderNo" type="hidden" value="${otcTransactionPendOrder.otcPendingOrderNo}"/>

<div id="loading">
    <i></i>
</div>

<div class="show-qr-bar">
    <img class="show-qr" src="" alt="">
</div>

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
    function sell() {
        var sellNum = document.getElementById("sellNum").value;
        var paymentType = document.getElementById("paymentType").value;
        var otcPendingOrderNo = document.getElementById("otcPendingOrderNo").value;
        var imageUrl =  document.getElementById("imageUrl").value;

        var paymentAccount = document.getElementById("paymentAccount").val;
        var bankName = document.getElementById("bankName").val;
        var bankBranch = document.getElementById("bankBranch").val;
        var paymentName = document.getElementById("paymentName").val;
        var paymentPhone = document.getElementById("paymentPhone").val;

        var alipayPaymentAccount = document.getElementById("alipayPaymentAccount").innerHTML;

        var wechatPaymentAccount = document.getElementById("wechatPaymentAccount").innerHTML;

        if (sellNum == null || sellNum == "") {
            return openTips("数量不能为空");
        }

        if (paymentType == null || paymentType == '') {
            return openTips("支付方式有误");
        }

        if (otcPendingOrderNo == null || otcPendingOrderNo == '') {
            return openTips("该订单不存在");
        }

        var formData = new FormData();
        formData.append("sellNum", sellNum);
        formData.append("paymentType", paymentType);
        formData.append("otcPendingOrderNo", otcPendingOrderNo);
        formData.append("imageUrl", imageUrl);

        formData.append("paymentAccount", paymentAccount);
        formData.append("bankName", bankName);
        formData.append("bankBranch", bankBranch);
        formData.append("paymentName", paymentName);
        formData.append("paymentPhone", paymentPhone);

        formData.append("alipayPaymentAccount", alipayPaymentAccount);
        formData.append("wechatPaymentAccount", wechatPaymentAccount);

        if(addCheckBoo){
            openTips("正在下单，请稍后！");
            return;
        }else{
            addCheckBoo = true;
        }

        $.ajax({
            url: '<%=path %>' + "/userWap/otcTradeCenter/sell.htm",
            data:formData,//参数
            dataType: "json",
            type: 'POST',
            async: true, //默认异步调用 (false：同步)
            processData : false,
            contentType : false,
            success: function (resultData) {
                var code = resultData.code;
                var message = resultData.message;
                if (code != "1") {
                    addCheckBoo = false;
                    openTips(message);
                    return;
                }
                if (code == "1") {
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

    function  back() {
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

