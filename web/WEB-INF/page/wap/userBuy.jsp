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

    <link rel="stylesheet" href="<%=path %>/resources/css/wap/offline-transaction-buy.css">
    <link rel="stylesheet" href="<%=path %>/resources/css/wap/common.css">
    <link rel="stylesheet" href="<%=path %>/resources/css/wap/simpleTips_wap.css">
    <title>购买</title>
</head>
<body>

<!-- 头部导航 -->
<header>
    <img src="<%=path %>/resources/image/wap/back.png" class="backimg" onclick="back()"/>
    <p>购买</p>
</header>

<main>
    <div class="order-info">
        <div class="order-item item-quantity">
            <div>购买数量</div>
            <input type="text" placeholder="您要购买的数量，单位：个" name="buyNum" id="buyNumber" maxlength="11"
                   onkeyup="matchUtil(this, 'double', 4)" onblur="matchUtil(this, 'double', 4)">
        </div>
        <div class="item-proportion">
            比例：1:<fmt:formatNumber type="number" value="${otcTransactionPendOrder.pendingRatio}" groupingUsed="FALSE" maxFractionDigits="2"/>
        </div>
        <div class="order-item item-price">
            <div>总价</div>
            <div><span>¥ </span><span id="buySum">0</span></div>
        </div>
        <div class="order-item item-receipt-method">
            <div>支付方式</div>
            <div>
                <span class="receipt-method-text">请选择支付方式</span>
                <img src="<%=path %>/resources/image/wap/nextIcon.png">
            </div>
        </div>
    </div>

    <p class="button" onclick="buy()">去支付</p>
</main>

<div class="select">
    <div class="select-header">
        选择收款方式
        <div>
            <img class="close-select" src="<%=path %>/resources/image/wap/close.png">
        </div>
    </div>
    <c:if test="${hasBank ==1}">
        <label class="select-item">
            <div class="select-label">
                <img src="<%=path %>/resources/image/wap/bank.png">
                <span>银行卡转账</span>
            </div>
            <input type="radio" class="choose" name="radio" value="bank"/>
        </label>
    </c:if>

    <c:if test="${hasAliPay ==1}">
        <label class="select-item">
            <div class="select-label">
                <img src="<%=path %>/resources/image/wap/alipay.png">
                <span>支付宝转账</span>
            </div>
            <input type="radio" class="choose" name="radio" value="alipay"/>
        </label>
    </c:if>
    <c:if test="${hasWeiXin ==1}">
        <label class="select-item">
            <div class="select-label">
                <img src="<%=path %>/resources/image/wap/wechat.png">
                <span>微信转账</span>
            </div>
            <input type="radio" class="choose" name="radio" value="wechat"/>
        </label>
    </c:if>
</div>

<form id="buyForm" action="<%=path %>/userWap/otcTradeCenter/userBuyDetail.htm" method="post">
    <input id="buyNums" name="buyNum" type="hidden"/>
    <input id="otcPendingOrderNo" name="otcPendingOrderNo" type="hidden" value="${otcTransactionPendOrder.otcPendingOrderNo}"/>
    <input id="paymentType" name="paymentType" type="hidden"/>
    <input id="userId" name="userId" type="hidden" value="${otcTransactionPendOrder.userId}"/>
</form>

<input id="minNumber" name="minNumber" value="${otcTransactionPendOrder.minNumber}" type="hidden"/>
<input id="maxNumber" name="maxNumber" value="${otcTransactionPendOrder.maxNumber}" type="hidden"/>
<div class="mask"></div>

</body>

<script type="text/javascript" src="<%=path %>/resources/js/wap/common.js"></script>
<script type="text/javascript" src="<%=path %>/resources/js/wap/zepto.min.js"></script>
<script type="text/javascript" src="<%=path %>/resources/js/wap/simpleTips_wap.js"></script>
<script type="text/javascript" src="http://libs.baidu.com/jquery/2.1.4/jquery.min.js"></script>
<script type="text/javascript">
    var pendingRatio = '${otcTransactionPendOrder.pendingRatio}';

    var mapMatch = {};
    mapMatch['number'] = /[^\d]/g;
    mapMatch['ENumber'] = /[^\a-\z\A-\Z\d]/g;
    mapMatch['double'] = true;
    mapMatch['phone'] = /[^\d]/g;
    mapMatch['email'] = /([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\.[a-zA-Z0-9_-]{2,3}){1,2})$/;
    function matchUtil(o, str, nu) {
        if(mapMatch[str] === true){
            matchDouble(o, nu);
        }else {
            o.value = o.value.replace(mapMatch[str], '');
        }
    }

    function matchDouble(o, num){
        var matchStr = /^-?\d+\.?\d{0,num}$/;
        if(!matchStr.test(o.value)){
            if(isNaN(o.value)){
                o.value = '';
            }else{
                var n = o.value.indexOf('.');
                var m = n + num + 1;
                if(n > -1 && o.value.length > m){
                    o.value = o.value.substring(0, m);
                }
            }
        }
        mul();
    }
    //动态计算总价
    function mul() {
        var m = 0;

        var buyNumber = $("#buyNumber").val();
        if(buyNumber == null || buyNumber == ""){
            $("#buySum").html("0");
        }

        //买入
        if (buyNumber != null && buyNumber != "" && pendingRatio != null && pendingRatio != "") {
            buyNumber = buyNumber * 10;
            var pendingRatios = pendingRatio * 10;
            buyNumber = buyNumber.toString();
            try{m+=buyNumber.split(".")[1].length}catch(e){}
            pendingRatios = pendingRatios.toString();
            try{m+=pendingRatios.split(".")[1].length}catch(e){}
            m += 2;
            var number = parseFloat((Number(buyNumber.replace(".",""))*Number(pendingRatios.replace(".",""))/Math.pow(10,m)).toFixed(8));
            number = mulMaxNumber(number);
            $("#buySum").html(number);
        }
    }

    //超大位数显示   返回字符串
    function mulMaxNumber(value) {
        value = "" + value;
        var mulArray = value.split("e+");
        if (mulArray == null) {
            return 0;
        }
        if (mulArray.length == 1) {
            return mulArray[0];
        }

        var decimal = new Number(mulArray[1]);
        var suffix = "";
        for (var i = 0; i < decimal; i++) {
            suffix += "0";
        }

        var pointArray = mulArray[0].split(".");
        if (pointArray == null) {
            return 0;
        }

        var prefix = "";
        var pointLength = pointArray.length;
        if (pointLength == 1) {
            prefix = "" + pointArray[0]
        }
        if (pointLength == 2) {
            prefix = "" + pointArray[0] + pointArray[1];
        }

        return prefix + suffix;
    }
</script>
<script type="text/javascript">
    $('.mask').hide();
    $('.choose').on('click', function () {
        $('html,body').removeClass('overflow'); //网页恢复滚动
        $('.mask').hide();
        $('.select').css('bottom', '-4.3rem');
        var map = {
            wechat: '微信转账',
            alipay: '支付宝转账',
            bank: '银行卡转账'
        };
        var checkValue = $("input[name='radio']:checked").val()
        $('.receipt-method-text').text(map[checkValue]);
    });

    $('.receipt-method-text').on('click', function () {
        $('html,body').addClass('overflow'); //使网页不可滚动
        $('.mask').show();
        $('.select').css('bottom', '0');
    });

    $('.mask').on('click', function () {
        $('html,body').removeClass('overflow'); //网页恢复滚动
        $('.mask').hide();
        $('.select').css('bottom', '-4.3rem');
    });

    $('.close-select').on('click', function () {
        $('html,body').removeClass('overflow'); //网页恢复滚动
        $('.mask').hide();
        $('.select').css('bottom', '-4.3rem');
    });

    function buy() {
        var num = $('#buyNumber').val();
        var maxNumber = parseFloat($('#maxNumber').val());
        var minNumber = parseFloat($('#minNumber').val());
        var paymentType = $('input[name="radio"]:checked').val();
        var buySum = parseFloat($('#buySum').text());

        if(num <= 0 || num == '' || num == null){
            openTips("请输入正确的数量");
            return;
        }
        if(paymentType <= 0 || paymentType == '' || paymentType == null){
            openTips("请选择支付方式");
            return;
        }
        if(buySum < minNumber){
            openTips("交易额度不能小于最小限额");
            return;
        }
        if(buySum > maxNumber){
            openTips("交易额度不能大于最大限额");
            return;
        }

        $('#buyNums').val(num);
        if(paymentType == "bank"){
            $('#paymentType').val(1);
        }
        if(paymentType == "alipay"){
            $('#paymentType').val(2);
        }
        if(paymentType == "wechat"){
            $('#paymentType').val(3);
        }

        $('#buyForm').submit();
    }

    function back() {
        window.location.href = "<%=path%>" + "/userWap/otcTradeCenter/show";
    }

</script>
</html>

