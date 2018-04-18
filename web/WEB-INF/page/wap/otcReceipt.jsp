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
    <link rel="stylesheet" href="<%=path %>/resources/css/wap/receipt-method.css">

    <title>收款信息</title>
</head>
<body>

<!-- 头部导航 -->
<header>
    <img src="<%=path %>/resources/image/wap/back.png" class="backimg" onclick="back()"/>
    <p>收款信息</p>
</header>

<main>
    <c:forEach items="${selectList}" var="selectList">
        <c:if test="${selectList == '1'}">
            <div class="receipt">
                <div class="receipt-header">
                    银行卡转账信息
                </div>
                <div class="receipt-item border-b">
                    <div>银行卡号<span>*</span></div>
                    <input type="text" placeholder="您的银行卡号" id="sellBankCard" maxlength="19"
                           onkeyup="value=value.replace(/[^\d]/g,'')" onblur="value=value.replace(/[^\d]/g,'')">
                </div>
                <div class="receipt-item border-b">
                    <div>银行名称<span>*</span></div>
                    <input type="text" placeholder="该银行卡的银行名称" maxlength="15" id="sellBankName"
                           onkeyup="value=value.replace(/[^a-zA-Z0-9\u4E00-\u9FA5]/g,'')" onblur="value=value.replace(/[^a-zA-Z0-9\u4E00-\u9FA5]/g,'')">
                </div>
                <div class="receipt-item border-b">
                    <div>支行名称<span>*</span></div>
                    <input type="text" placeholder="该银行卡的银行支行名称" maxlength="50" id="sellBankBranch"
                           onkeyup="value=value.replace(/[^a-zA-Z0-9\u4E00-\u9FA5]/g,'')" onblur="value=value.replace(/[^a-zA-Z0-9\u4E00-\u9FA5]/g,'')">
                </div>
                <div class="receipt-item border-b">
                    <div>银行预留姓名<span>*</span></div>
                    <input type="text" placeholder="该银行卡的预留姓名" maxlength="30" id="sellPaymentName"
                           onkeyup="value=value.replace(/[^a-zA-Z0-9\u4E00-\u9FA5]/g,'')" onblur="value=value.replace(/[^a-zA-Z0-9\u4E00-\u9FA5]/g,'')">
                </div>
                <div class="receipt-item border-b">
                    <div>银行预留电话<span>*</span></div>
                    <input type="text" placeholder="该银行卡的预留电话" id="sellPaymentPhone"
                           maxlength="11" onkeyup="matchUtil(this, 'number')" onblur="matchUtil(this, 'number')">
                </div>
            </div>
            <input id="hasBank" name="hasBank" value="${selectList}" type="hidden"/>
        </c:if>
        <c:if test="${selectList == '2'}">
            <div class="receipt">
                <div class="receipt-header">
                    支付宝转账信息
                </div>
                <div class="receipt-item border-b">
                    <div>支付宝账号<span>*</span></div>
                    <input type="text" placeholder="您的支付宝账号" id="sellAliAccount" maxlength="30"
                           onkeyup="value=value.replace(/[^a-zA-Z0-9\.@_-]/g,'')" onblur="value=value.replace(/[^a-zA-Z0-9\.@_-]/g,'')">
                </div>
                <div class="receipt-item border-b qr-code">
                    <div>二维码<span>*</span></div>
                    <div class="upload-bar">
                        <input type="file" class="file-upload-alipay" accept="image/gif,image/jpeg,image/jpg,image/png,image/svg" id="changead_t1">
                        <span  class="file-name-alipay">请选择支付宝二维码</span>
                    </div>
                </div>
            </div>
            <input id="hasAlipy" name="hasAlipy" value="${selectList}" type="hidden"/>
        </c:if>
        <c:if test="${selectList == '3'}">
        <div class="receipt">
            <div class="receipt-header">
                微信转账信息
            </div>
            <div class="receipt-item border-b">
                <div>微信账号<span>*</span></div>
                <input type="text" placeholder="您的微信账号" id="sellWxAccount" maxlength="30"
                       onkeyup="value=value.replace(/[^a-zA-Z0-9\_-]/g,'')" onblur="value=value.replace(/[^a-zA-Z0-9\_-]/g,'')">
            </div>
            <div class="receipt-item border-b qr-code">
                <div>二维码<span>*</span></div>
                <div class="upload-bar">
                    <input type="file" class="file-upload-wechat" accept="image/gif,image/jpeg,image/jpg,image/png,image/svg" id="changead_t2">
                    <span class="file-name-wechat">请选择微信二维码</span>
                </div>
            </div>
        </div>
            <input id="hasWx" name="hasWx" value="${selectList}" type="hidden"/>
        </c:if>
    </c:forEach>
    <p class="button" onclick="otcAdsSubmit()">确 定</p>
</main>

<input id="currencyId" name="currencyId" value="${currencyId}" type="hidden"/>
<input id="orderType" name="orderType" value="${orderType}" type="hidden"/>
<input id="area" name="area" value="${area}" type="hidden"/>
<input id="pendingRatio" name="pendingRatio" value="${pendingRatio}" type="hidden"/>
<input id="minNumber" name="minNumber" value="${minNumber}" type="hidden"/>
<input id="maxNumber" name="maxNumber" value="${maxNumber}" type="hidden"/>
<input id="selectList" name="selectList" value="${selectList}" type="hidden"/>
</body>

<script type="text/javascript" src="<%=path %>/resources/js/wap/common.js"></script>
<script type="text/javascript" src="<%=path %>/resources/js/wap/zepto.min.js"></script>
<script type="text/javascript" src="<%=path %>/resources/js/wap/simpleTips_wap.js"></script>
<script type="text/javascript" src="http://libs.baidu.com/jquery/2.1.4/jquery.min.js"></script>
<script type="text/javascript">
var sellFinalBoo = false;
function otcAdsSubmit() {
    var hasBank = $("#hasBank").val();
    var hasAlipy = $("#hasAlipy").val();
    var hasWx = $("#hasWx").val();
    var formData = new FormData();
    if(hasBank =="1"){
        var sellBankCard = $("#sellBankCard").val();
        var sellBankName = $("#sellBankName").val();
        var sellBankBranch = $("#sellBankBranch").val();
        var sellPaymentName = $("#sellPaymentName").val();
        var sellPaymentPhone = $("#sellPaymentPhone").val();
        if(sellBankCard == null || sellBankCard == ''){
            openTips("请输入银行卡号");
            return;
        }
        if(sellBankCard.length < 16 || sellBankCard.length >19){
            openTips("银行卡号在16-19位之间");
            return;
        }
        if(sellBankName == null || sellBankName == ''){
            openTips("请输入银行名称");
            return;
        }
        if(sellBankBranch == null || sellBankBranch == ''){
            openTips("请输入支行名称");
            return;
        }
        if(sellPaymentName == null || sellPaymentName == ''){
            openTips("请输入银行预留姓名");
            return;
        }
        if(sellPaymentPhone == null || sellPaymentPhone == ''){
            openTips("请输入银行预留电话");
            return;
        }
        if(sellPaymentPhone.length != 11){
            openTips("请输入11位银行预留电话");
            return;
        }

        formData.append("bankAccount", sellBankCard);
        formData.append("bankName", sellBankName);
        formData.append("bankBranch", sellBankBranch);
        formData.append("paymentName", sellPaymentName);
        formData.append("paymentPhone", sellPaymentPhone);
    }
    if(hasAlipy =="2"){
        var sellAliAccount = $("#sellAliAccount").val();
        var qrCode = document.getElementById("changead_t1").files[0];

        if (sellAliAccount == null || sellAliAccount == '') {
            return openTips("请输入支付宝账户");
        }
        if (qrCode == null || qrCode == '') {
            return openTips("请上传二维码");
        }
        formData.append("alipayAccount", sellAliAccount);
        formData.append("alipayImageUrl", qrCode);
    }
    if(hasWx =="3"){
        var sellWxAccount = $("#sellWxAccount").val();
        var qrCode = document.getElementById("changead_t2").files[0];

        if (sellWxAccount == null || sellWxAccount == '') {
            return openTips("请输入微信账户");
        }
        if (qrCode == null || qrCode == '') {
            return openTips("请上传二维码");
        }
        formData.append("wechatAccount", sellWxAccount);
        formData.append("wechatImageUrl", qrCode);
    }
    var currencyId = $("#currencyId").val();
    var orderType = $("#orderType").val();
    var area = $("#area").val();
    var pendingRatio = $("#pendingRatio").val();
    var minNumber = $("#minNumber").val();
    var maxNumber = $("#maxNumber").val();

    if (sellFinalBoo) {
        return;
    } else {
        sellFinalBoo = true;
    }

    formData.append("currencyId", currencyId);
    formData.append("orderType", orderType);
    formData.append("pendingRatio", pendingRatio);
    formData.append("minNumber", minNumber);
    formData.append("maxNumber", maxNumber);

    $.ajax({
        url: '<%=path%>' + "/userWap/dealerManagment/otcRelease.htm",
        data:formData,//参数
        dataType: "json",
        type: 'POST',
        async: true, //默认异步调用 (false：同步)
        processData : false,
        contentType : false,
        success: function (resultData) {
            var code = resultData.code;
            var message = resultData.message;
            if (code == 1 && message == "未登录！") {
                addCheckBoo = false;
                openTips(message);
                setTimeout(setTimeout('window.location.href = \"<%=path%>\" + \"/userWap/userLogin/show\"', 1000));
                return;
            }
            if (code == 3 && message == "当前用户不是经销商") {
                addCheckBoo = false;
                openTips(message);
                setTimeout(setTimeout('window.location.href = \"<%=path%>\" + \"/userWap/otcTradeCenter/show\"', 1000));
                return;
            }
            if (code == 3 && message == "参数错误") {
                addCheckBoo = false;
                openTips(message);
                setTimeout(setTimeout('window.location.href = \"<%=path%>\" + \"/userWap/dealerManagment/show\"', 1000));
                return;
            }
            if (code == 1 && message == "发布成功") {
                addCheckBoo = false;
                openTips(message);
                setTimeout(setTimeout('window.location.href = \"<%=path%>\" + \"/userWap/dealerManagment/show\"', 1000));
                return;
            }
            if (code != 1 && message != "") {
                sellFinalBoo  = false;
                openTips(message);
                return;
            }
        },
        error: function () {
            sellFinalBoo  = false;
            openTips("数据加载出错，请稍候重试");
        }
    });
}
</script>

<script type="text/javascript">
    $('.file-upload-alipay').on('change', function () {
        var obj = document.querySelector(".file-upload-alipay");
        if (!obj.files[0]) {
            return
        }
        var fileName = obj.files[0].name
        if (fileName.length > 20) {
            fileName = fileName.slice(0, 15) + '...' + fileName.slice(-5)
        }
        $('.file-name-alipay').text(fileName)
        $('.file-name-alipay').css('color', '#35394f')
    })

    $('.file-upload-wechat').on('change', function () {
        var obj = document.querySelector(".file-upload-wechat");
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
    }

    function back() {
        window.location.href = "<%=path%>" + "/userWap/dealerManagment/openInitiateAds.htm";
    }
</script>
</html>

