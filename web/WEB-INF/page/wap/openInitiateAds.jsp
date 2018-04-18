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
    <link rel="stylesheet" href="<%=path %>/resources/css/wap/initiate-advertise.css">
    <title>发起广告</title>
</head>
<body>

<!-- 头部导航 -->
<header>
    <img src="<%=path %>/resources/image/wap/back.png" class="backimg" onclick="back()"/>
    <p>发起广告</p>
</header>

<main>
    <div class="base-info">
        <div class="item-base border-b">
            <div><span>币种</span><span>*</span></div>
            <div>
                <!--<span>XT</span>-->
                <select id="currencyId" name="currencyId">
                    <option value="0" disabled selected>选择币种</option>
                    <option value="999">XT</option>
                </select>
                <img src="<%=path %>/resources/image/wap/nextIcon.png"></div>
        </div>
        <div class="item-base border-b">
            <div><span>类型</span><span>*</span></div>
            <div>
                <select onchange="changeOrderType(this.value)" id="orderType" name="orderType">
                    <option value="0" disabled selected>选择类型</option>
                    <option  value="1">用户购买</option>
                    <option  value="2">用户出售</option>
                </select>
                <img src="<%=path %>/resources/image/wap/nextIcon.png"></div>
        </div>
        <div class="item-base border-b">
            <div><span>地区</span><span>*</span></div>
            <div>
                <!--<span>XT</span>-->
                <select id="area" name="area">
                    <option value="0" disabled selected>选择地区</option>
                    <option value="1">中国(CN)</option>
                </select>
                <img src="<%=path %>/resources/image/wap/nextIcon.png"></div>
        </div>
    </div>
    <div class="order-info">
        <div class="order-item item-proportion border-b">
            <div><span>比例</span><span>*</span></div>
            <input type="text" placeholder="币种交易比例" id="pendingRatio" maxlength="10" onkeyup="matchUtil(this, 'double', 2)" onblur="matchUtil(this, 'double', 2)" name="pendingRatio">
        </div>
        <div class="order-item item-quota">
            <div><span>交易限额</span><span>*</span></div>
            <div>
                <input class="min-quota" type="text" placeholder="交易最小额度" id="minNumber" maxlength="9"  onkeyup="matchUtil(this, 'double', 2)" onblur="matchUtil(this, 'double', 2)"  name="minNumber">
                <div>~</div>
                <input class="max-quota" type="text" placeholder="交易最大额度" id="maxNumber" maxlength="9" onkeyup="matchUtil(this, 'double', 2)" onblur="matchUtil(this, 'double', 2)"  name="maxNumber">
            </div>
        </div>

        <div class="order-item item-receipt-method" style="display: none" id="choose">
            <%--<div><span>交易限额</span><span>*</span></div>--%>
            <div>
                <div class="item-check">
                    <img src="<%=path %>/resources/image/wap/bank.png">
                    <span>银行卡转账</span>
                    <input type="checkbox" id="bankBox" name="selected" value="1">
                </div>
                <div class="item-check">
                    <img src="<%=path %>/resources/image/wap/alipay.png">
                    <span>支付宝转账</span>
                    <input type="checkbox" id="alipayBox" name="selected" value="2">
                </div>
                <div class="item-check">
                    <img src="<%=path %>/resources/image/wap/wechat.png">
                    <span>微信转账</span>
                    <input type="checkbox" id="wechatBox" name="selected" value="3">
                </div>
            </div>
        </div>

    </div>
    <p class="button" onclick="confirm()">确 定</p>
</main>
<input id="selects" name="selects" value="" type="hidden"/>

<form id="jumpForm" action="<%=path %>/userWap/dealerManagment/otcReceipt.htm" method="post">
    <input id="currencyIds" name="currencyIds" value="" type="hidden"/>
    <input id="orderTypes" name="orderTypes" value="" type="hidden"/>
    <input id="areas" name="areas" value="" type="hidden"/>
    <input id="pendingRatios" name="pendingRatios" value="" type="hidden"/>
    <input id="minNumbers" name="minNumbers" value="" type="hidden"/>
    <input id="maxNumbers" name="maxNumbers" value="" type="hidden"/>
    <input id="selectList" name="selectList" value="" type="hidden"/>
</form>

</body>

<script type="text/javascript" src="<%=path %>/resources/js/wap/common.js"></script>
<script type="text/javascript" src="<%=path %>/resources/js/wap/zepto.min.js"></script>
<script type="text/javascript" src="<%=path %>/resources/js/wap/simpleTips_wap.js"></script>
<script type="text/javascript" src="http://libs.baidu.com/jquery/2.1.4/jquery.min.js"></script>
<script type="text/javascript">
    function changeOrderType(valueNum){
        if(valueNum == 1){
            $("#choose").css("display" , "inline-block");
        }
        if(valueNum ==2){
            $("#choose").hide();
        }
    }

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
    var addCheckBoo = false;
    function confirm() {
        var currencyId = $("#currencyId").val();
        var orderType = $("#orderType").val();
        var area = $("#area").val();
        var pendingRatio = $("#pendingRatio").val();
        var minNumber = $("#minNumber").val();
        var maxNumber = $("#maxNumber").val();

        if(currencyId == null || currencyId == 0){
            openTips("请选择币种");
            return;
        }
        if(orderType == null || orderType == 0){
            openTips("请选择类型");
            return;
        }
        if(area == null || area == 0){
            openTips("请选择地区");
            return;
        }
        if(pendingRatio == ""){
            openTips("请输入挂单比例");
            return;
        }
        if(pendingRatio <= 0){
            openTips("挂单比例要大于0");
            return;
        }
        if(minNumber == ""){
            openTips("请输入最低限额");
            return;
        }
        if(minNumber <= 0){
            openTips("最低限额要大于0");
            return;
        }
        if(maxNumber == ""){
            openTips("请输入最高限额");
            return;
        }
        if(maxNumber <= 0){
            openTips("最高限额要大于0");
            return;
        }
        if(Number(pendingRatio) > Number(999999.99)){
            openTips("挂单比例要小于一百万");
            return;
        }
        if(Number(maxNumber) > Number(999999.99)){
            openTips("最高限额要小于一百万");
            return;
        }
        if(Number(maxNumber) <= Number(minNumber)){
            openTips("最高限额要大于最低限额");
            return;
        }
        if(accMul(accMul(pendingRatio,0.0001),10000) > accMul(maxNumber,10000)){
            openTips("最高限额过小");
            return;
        }

        if(orderType ==1){
            var sselect = [];
            $('body').find(':checkbox').each(function () {
                if ($(this).is(":checked")) {
                    var checkVal = $(this).val();
                    sselect.push(checkVal);
                }
            });

            if(sselect.length < 1 || sselect == "" || sselect == null){
                openTips("请选择收款方式！");
                return;
            }
            var selects = $('#selects').val(sselect);
        }

        var selectsList = $('#selects').val();

            if(addCheckBoo){
                openTips("正在保存，请稍后！");
                return;
            }else{
                addCheckBoo = true;
            }

            $.ajax({
                url: '<%=path %>' + "/userWap/dealerManagment/initiateAds.htm",
                data: {
                    currencyId : currencyId,
                    orderType : orderType,
                    pendingRatio : pendingRatio,
                    minNumber : minNumber,
                    maxNumber : maxNumber,
                    area : area,
                    selectsList : selectsList,
                },//参数
                dataType: "json",
                type: 'POST',
                async: true, //默认异步调用 (false：同步)
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

                    if (code == 1 && message == "跳转收款信息") {
                        addCheckBoo = false;
                        var result = resultData.data;
                        var otcOrder = result.otcOrderVO;
                        var selectsList = result.selectsList;

                        $("#currencyIds").val(otcOrder.currencyId);
                        $("#orderTypes").val(otcOrder.orderType);
                        $("#areas").val(otcOrder.area);
                        $("#pendingRatios").val(otcOrder.pendingRatio);
                        $("#minNumbers").val(otcOrder.minNumber);
                        $("#maxNumbers").val(otcOrder.maxNumber);
                        $("#selectList").val(selectsList);
                        $("#jumpForm").submit();
                    }
                    if (code == 1 && message == "发布成功") {
                        addCheckBoo = false;
                        openTips(message);
                        setTimeout(setTimeout('window.location.href = \"<%=path%>\" + \"/userWap/dealerManagment/show\"', 1000));
                        return;
                    }
                    if (code != 1 && message != "") {
                        addCheckBoo = false;
                        openTips(message);
                        return;
                    }
                },
                error: function () {
                    addCheckBoo = false;
                    openTips("数据加载出错，请稍候重试");
                }
            });
    }

    function accMul(arg1,arg2){
        var m=0,s1=arg1.toString(),s2=arg2.toString();
        try{m+=s1.split(".")[1].length}catch(e){}
        try{m+=s2.split(".")[1].length}catch(e){}
        return Number(s1.replace(".",""))*Number(s2.replace(".",""))/Math.pow(10,m)
    }
    function back() {
        window.location.href = "<%=path%>" + "/userWap/dealerManagment/show";
    }
</script>
</html>

