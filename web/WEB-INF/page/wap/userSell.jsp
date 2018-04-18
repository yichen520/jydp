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

    <link rel="stylesheet" href="<%=path %>/resources/css/wap/offline-transaction-sell.css">
    <link rel="stylesheet" href="<%=path %>/resources/css/wap/common.css">
    <link rel="stylesheet" href="<%=path %>/resources/css/wap/simpleTips_wap.css">
    <title>出售</title>
</head>
<body>

<!-- 头部导航 -->
<header>
    <img src="<%=path %>/resources/image/wap/back.png" class="backimg" onclick="back()"/>
    <p>出售</p>
</header>

<main>
    <div class="order-info">
        <div class="order-item item-dealer">
            <div>经销商</div>
            <div>${otcTransactionPendOrder.dealerName}</div>
        </div>
        <div class="order-item item-quantity">
            <div>出售数量</div>
            <input type="text" placeholder="您要出售的数量，单位：个" id="sellNumber" maxlength="11"
                   onkeyup="matchUtil(this, 'double', 4)" onblur="matchUtil(this, 'double', 4)">
        </div>
        <div class="item-proportion">
            比例：1:${otcTransactionPendOrder.pendingRatio}
        </div>
        <div class="order-item item-price">
            <div>总价</div>
            <div><span>¥ </span><span id="sellNum">0</span></div>
        </div>
        <div class="order-item item-receipt-method">
            <div>收款方式</div>
            <div>
                <span class="receipt-method-text">请选择收款方式</span>
                <img src="<%=path %>/resources/image/wap/nextIcon.png">
            </div>
        </div>
    </div>
    <form id="sellForm" action="<%=path %>/userWap/otcTradeCenter/userSellDetail.htm" method="post" enctype="multipart/form-data">
        <div class="receipt" style="display: none" id="bank">
            <div class="receipt-item border-b">
                <div>银行卡号</div>
                <input type="text" placeholder="您的银行卡号" id="paymentAccount" name="paymentAccount" maxlength="19"
                       onkeyup="value=value.replace(/[^\d]/g,'')" onblur="value=value.replace(/[^\d]/g,'')">
            </div>
            <div class="receipt-item border-b">
                <div>银行名称</div>
                <input type="text" placeholder="该银行卡的银行名称" id="bankName" name="bankName" maxlength="15"
                       onkeyup="value=value.replace(/[^a-zA-Z0-9\u4E00-\u9FA5]/g,'')" onblur="value=value.replace(/[^a-zA-Z0-9\u4E00-\u9FA5]/g,'')">
            </div>
            <div class="receipt-item border-b">
                <div>支行名称</div>
                <input type="text" placeholder="该银行卡的银行支行名称" id="bankBranch" name="bankBranch" maxlength="50"
                       onkeyup="value=value.replace(/[^a-zA-Z0-9\u4E00-\u9FA5]/g,'')" onblur="value=value.replace(/[^a-zA-Z0-9\u4E00-\u9FA5]/g,'')">
            </div>
            <div class="receipt-item border-b">
                <div>银行预留姓名</div>
                <input type="text" placeholder="该银行卡的预留姓名" id="paymentName" name="paymentName" maxlength="30"
                       onkeyup="value=value.replace(/[^a-zA-Z0-9\u4E00-\u9FA5]/g,'')" onblur="value=value.replace(/[^a-zA-Z0-9\u4E00-\u9FA5]/g,'')">
            </div>
            <div class="receipt-item border-b">
                <div>银行预留电话</div>
                <input type="text" placeholder="该银行卡的预留电话" maxlength="11" id="paymentPhone" name="paymentPhone"
                       onkeyup="matchUtil(this, 'number')" onblur="matchUtil(this, 'number')">
            </div>
        </div>

        <div class="receipt" style="display: none" id="alipay">
            <div class="receipt-item border-b">
                <div>支付宝账号</div>
                <input type="text" placeholder="您的支付宝账号" id="alipayPaymentAccount" name="alipayPaymentAccount" maxlength="30"
                       onkeyup="value=value.replace(/[^a-zA-Z0-9\.@_-]/g,'')" onblur="value=value.replace(/[^a-zA-Z0-9\.@_-]/g,'')">
            </div>
            <div class="receipt-item border-b qr-code">
                <div>二维码</div>
                <div class="upload-bar">
                    <input type="file" class="file-upload-alipay" accept="image/gif,image/jpeg,image/jpg,image/png,image/svg" id="alipayPaymentImage" name="alipayPaymentImage">
                    <span  class="file-name-alipay">请选择文件</span>
                </div>
            </div>
        </div>

        <div class="receipt" style="display: none" id="wechat">
            <div class="receipt-item border-b">
                <div>微信账号</div>
                <input type="text" placeholder="您的微信账号" id="wechatPaymentAccount" name="wechatPaymentAccount" maxlength="30"
                       onkeyup="value=value.replace(/[^a-zA-Z0-9\.@_-]/g,'')" onblur="value=value.replace(/[^a-zA-Z0-9\.@_-]/g,'')">
            </div>
            <div class="receipt-item border-b qr-code">
                <div>二维码</div>
                <div class="upload-bar">
                    <input type="file" class="file-upload-wechat" accept="image/gif,image/jpeg,image/jpg,image/png,image/svg" id="wechatPaymentImage" name="wechatPaymentImage">
                    <span class="file-name-wechat">请选择文件</span>
                </div>
            </div>
            <input id="otcPendingOrderNo" name="otcPendingOrderNo" type="hidden" value="${otcTransactionPendOrder.otcPendingOrderNo}"/>
            <input id="paymentType" name="paymentType" type="hidden"/>
            <input id="sellNumbers" name="sellNum" type="hidden"/>
            <input id="url" name="url" type="hidden"/>
        </div>
    </form>
    <p class="button" onclick="sell()">确 定</p>
</main>

<input id="minNumber" name="minNumber" value="${otcTransactionPendOrder.minNumber}" type="hidden"/>
<input id="maxNumber" name="maxNumber" value="${otcTransactionPendOrder.maxNumber}" type="hidden"/>
<div id="loading">
    <i></i>
</div>

<div class="select">
    <div class="select-header">
        选择收款方式
        <div>
            <img class="close-select" src="<%=path %>/resources/image/wap/close.png">
        </div>
    </div>

    <label class="select-item">
        <div class="select-label">
            <img src="<%=path %>/resources/image/wap/bank.png">
            <span>银行卡转账</span>
        </div>
        <input type="radio" class="choose" name="radio" value="bank" onclick="banks()"/>
    </label>

    <label class="select-item">
        <div class="select-label">
            <img src="<%=path %>/resources/image/wap/alipay.png">
            <span>支付宝转账</span>
        </div>
        <input type="radio" class="choose" name="radio" value="alipay" onclick="alipays()"/>
    </label>

    <label class="select-item">
        <div class="select-label">
            <img src="<%=path %>/resources/image/wap/wechat.png">
            <span>微信转账</span>
        </div>
        <input type="radio" class="choose" name="radio" value="wechat" onclick="wechats()"/>
    </label>

</div>

<div class="mask"></div>


</body>


<script type="text/javascript" src="<%=path %>/resources/js/wap/common.js"></script>
<script type="text/javascript" src="<%=path %>/resources/js/wap/zepto.min.js"></script>
<script type="text/javascript" src="<%=path %>/resources/js/wap/simpleTips_wap.js"></script>
<script type="text/javascript" src="http://libs.baidu.com/jquery/2.1.4/jquery.min.js"></script>
<script type="text/javascript" src="<%=path %>/resources/js/wap/offline-transaction-sell.js"></script>
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

        var sellNumber = $("#sellNumber").val();
        if(sellNumber == null || sellNumber == ""){
            $("#sellNum").html("0");
        }

        //买入
        if (sellNumber != null && sellNumber != "" && pendingRatio != null && pendingRatio != "") {
            sellNumber = sellNumber * 10;
            var pendingRatios = pendingRatio * 10;
            sellNumber = sellNumber.toString();
            try{m+=sellNumber.split(".")[1].length}catch(e){}
            pendingRatios = pendingRatios.toString();
            try{m+=pendingRatios.split(".")[1].length}catch(e){}
            m += 2;
            var number = parseFloat((Number(sellNumber.replace(".",""))*Number(pendingRatios.replace(".",""))/Math.pow(10,m)).toFixed(8));
            number = mulMaxNumber(number);
            $("#sellNum").html(number);
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

    function banks() {
    $("#bank").css("display","block");
    $("#alipay").css("display","none");
    $("#wechat").css("display","none");
    }

    function alipays() {
        $("#alipay").css("display","block");
        $("#bank").css("display","none");
        $("#wechat").css("display","none");

    }
    function wechats() {
        $("#wechat").css("display","block");
        $("#bank").css("display","none");
        $("#alipay").css("display","none");

    }

    function sell() {
        var sellNumber = $("#sellNumber").val();
        $("#sellNumbers").val(sellNumber);
        var maxNumber = parseFloat($('#maxNumber').val());
        var minNumber = parseFloat($('#minNumber').val());
        var sellNum = parseFloat($('#sellNum').text());

        var chooseValue = $("input[name='radio']:checked").val();

        if(sellNumber == "" || sellNumber ==0 || sellNumber == null){
            openTips("请输入数量");
            return;
        }
        if(sellNum < minNumber){
            openTips("交易额度不能小于最小限额");
            return;
        }
        if(sellNum > maxNumber){
            openTips("交易额度不能大于最大限额");
            return;
        }
        if(chooseValue != "bank" && chooseValue != "alipay" && chooseValue != "wechat"){
            openTips("请选择收款方式");
            return;
        }

        if(chooseValue == "bank"){
            $("#paymentType").val(1);
            var paymentAccount = $('#paymentAccount').val();
            var bankName = $('#bankName').val();
            var bankBranch = $('#bankBranch').val();
            var paymentName = $('#paymentName').val();
            var paymentPhone = $('#paymentPhone').val();

            if(paymentAccount == null || paymentAccount == ''){
                openTips("请输入银行卡号");
                return;
            }
            if(paymentAccount.length < 16 || paymentAccount.length >19){
                openTips("银行卡号在16-19位之间");
                return;
            }
            if(bankName == null || bankName == ''){
                openTips("请输入银行名称");
                return;
            }
            if(bankBranch == null || bankBranch == ''){
                openTips("请输入支行名称");
                return;
            }
            if(paymentName == null || paymentName == ''){
                openTips("请输入银行预留姓名");
                return;
            }
            if(paymentPhone == null || paymentPhone == ''){
                openTips("请输入银行预留电话");
                return;
            }
            if(paymentPhone.length != 11){
                openTips("请输入11位银行预留电话");
                return;
            }
        }
        if(chooseValue == "alipay"){
            $("#paymentType").val(2);
            var alipayPaymentAccount = $("#alipayPaymentAccount").val();
            var alipayPaymentImage = document.getElementById("alipayPaymentImage").files[0];
            if (alipayPaymentAccount == null || alipayPaymentAccount == '') {
                return openTips("请输入支付宝账户");
            }
            if (alipayPaymentImage == null || alipayPaymentImage == '') {
                return openTips("请上传二维码");
            }

        }
        if(chooseValue == "wechat"){
            $("#paymentType").val(3);
            var wechatPaymentAccount = $("#wechatPaymentAccount").val();
            var wechatPaymentImage = document.getElementById("wechatPaymentImage").files[0];
            if (wechatPaymentAccount == null || wechatPaymentAccount == '') {
                return openTips("请输入微信账户");
            }
            if (wechatPaymentImage == null || wechatPaymentImage == '') {
                return openTips("请上传二维码");
            }
        }

        $('#sellForm').submit();
    }

    $('.file-upload-wechat').on('change', function (e) {
        console.log(e);
        var obj = document.querySelector(".file-upload-wechat");
        console.log(obj);
        if (!obj.files[0]) {
            return
        }
        var fileName = obj.files[0].name
        if (fileName.length > 20) {
            fileName = fileName.slice(0, 15) + '...' + fileName.slice(-5)
        }
        $('.file-name-wechat').text(fileName)
        $('.file-name-wechat').css('color', '#35394f')
    })

    function back() {
        window.location.href = "<%=path%>" + "/userWap/otcTradeCenter/show";
    }

</script>
</html>

