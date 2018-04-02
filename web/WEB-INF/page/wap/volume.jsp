<%@ page pageEncoding="UTF-8" %>
<%@include file="/resources/page/common/path.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <meta http-equiv="Cache-Control" content="no-cache"/>
    <link rel="stylesheet" href="<%=path %>/resources/css/wap/common.css">
    <link rel="stylesheet" href="<%=path %>/resources/css/wap/simpleTips_wap.css">
    <link rel="stylesheet" href="<%=path %>/resources/css/wap/volume.css">
    <title>成交记录</title>
</head>
<body>

<!-- 头部导航 -->
<header>
    <img src="<%=path %>/resources/image/wap/back.png" class="back"/>
    <p>成交记录</p>
</header>
<!-- 内容区域 -->
<div class="wrapper">
    <div id="tableList"></div>
    <!-- 查看更多 -->
    <div class="more" id="seeMore" onclick="seeMore()">查看更多</div>

    <input type="hidden" id="wapPath" value="<%=path %>">
    <input type="hidden" id="pendingOrderNo" name="pendingOrderNo" value="${pendingOrderNo}">
    <input type="hidden" id="pageNumber" name="pageNumber" value="${pageNumber}">
</div>
<!-- 撤销弹窗 -->
<div class="bg">
    <div class="showBox">
        <div class="showBoxTitle">撤销委托</div>
        <div class="showBoxContent">是否撤销该委托？</div>
        <div class="showBoxButton">
            <div class="cancelShow">取消</div>
            <div class="okay">确定</div>
        </div>
    </div>
</div>
<!-- loading图 -->
<div id="loading">
    <i></i>
</div>
</body>

<script type="text/javascript" src="<%=path %>/resources/js/wap/common.js"></script>
<script type="text/javascript" src="<%=path %>/resources/js/wap/zepto.min.js"></script>
<script type="text/javascript" src="<%=path %>/resources/js/wap/jquery-2.1.4.min.js"></script>
<script type="text/javascript" src="<%=path %>/resources/js/wap/simpleTips_wap.js"></script>
<script src="<%=path %>/resources/js/wap/handlebars-v4.0.11.js"></script>

<script type="text/template" id="accountRecord">
    {{#each dealRecordList}}
    <div class="content">
        <div class="header">
            <p class="date">交易订单号：{{orderNo}}</p>
        </div>
        <div class="nav">
            <div class="navTop">
                <p class="name">{{currencyName}}</p>
                <p class="{{paymentTypeClassConvert paymentType}}">{{paymentTypeConvert paymentType}}</p>
            </div>
            <div class="navLeft">
                <ul>
                    <li>数量：<span>{{formatNumber currencyNumber 4}}</span></li>
                    <li>单价：<span>{{formatNumber transactionPrice 2}}</span></li>
                    <li>总价：<span>{{formatNumber currencyTotalPrice 6}}</span></li>
                    <li>手续费：<span>{{feesConvert feeForWap 8}}</span></li>
                    {{#compare actualPrice 0 }}
                        <li>{{formatPaymentType paymentType}}：<span>{{actualArrivalConvert actualPriceForWap 6}}</span></li>
                    {{/compare}}
                    <li>完成时间：<span>{{addTimeConvert addTime}}</span></li>
                </ul>
            </div>
        </div>
    </div>
    {{/each}}
</script>

<script type="text/javascript">
    var wapPath = $("#wapPath").val();
    var pendingOrderNo = $("#pendingOrderNo").val();
    var pageNumber = $("#pageNumber").val();

    //加载页面数据
    $.ajax({
        url: wapPath + "/userWap/wapDealRecord/getAccountRecord.htm",
        data: {
            pendingOrderNo: pendingOrderNo,
            pageNumber: pageNumber
        },
        dataType: "json",
        type: 'POST',
        async: true,
        success: function (resultData) {
            var code = resultData.code;
            var message = resultData.message;
            if (code != 1 && message != "") {
                openTips(message);
                return;
            }

            var template = Handlebars.compile($("#accountRecord").html())
            $('#tableList').html(template(resultData.data));

            //对查看更多做控制
            if (resultData.data.totalPageNumber <= Number(pageNumber) + 1) {
                $("#seeMore").hide();
            }
        },
        error: function () {
            openTips("请求数据异常");
        }
    });

    //if比较
    Handlebars.registerHelper("compare", function (x1, x2, options) {
        if (x1 > x2) {
            //满足条件执行
            return options.fn(this);
        } else {
            //不满足执行{{else}}部分
            return options.inverse(this);
        }
    });

    //对位数进行控制
    Handlebars.registerHelper("formatNumber", function (num,maxFractionDigits) {
        if (isNaN(num) || isNaN(maxFractionDigits)) {
            openTips("参数类型错误");
            return false;
        }
        num = num.toString();
        maxFractionDigits = parseInt(maxFractionDigits);
        if (num.indexOf(".") === -1) {
            return num;
        }
        var numField = num.split(".");
        var integerDigits = numField[0];
        var fractionDigits = numField[1];

        if (fractionDigits.length <= maxFractionDigits) {
            return num;
        }
        fractionDigits = fractionDigits.substring(0, maxFractionDigits);
        var numStr = integerDigits + "." + fractionDigits;
        return numStr;
    });


    //收支类型
    Handlebars.registerHelper("paymentTypeConvert", function (type) {
        if (type == undefined || type == null || type == "" || isNaN(type)) {
            return "未知类型";
        }
        if (type == 1) {
            return "买入";
        }
        if (type == 2) {
            return "卖出";
        }
        if (type == 3) {
            return "撤销";
        }
        return "未知类型";
    });

    //实际支出买入显示类型
    Handlebars.registerHelper("formatPaymentType", function (type) {
        if (type == undefined || type == null || type == "" || isNaN(type)) {
            return "未知类型";
        }
        if (type == 1) {
            return "实际支出";
        }
        if (type == 2) {
            return "实际到账";
        }
        if (type == 3){
            return "成交总价";
        }
        return "未知类型";
    });

    //手续费
    Handlebars.registerHelper("feesConvert", function (fee, maxFractionDigits) {
        if (isNaN(maxFractionDigits)) {
            openTips("参数类型错误");
            return false;
        }
        fee = fee.toString();
        maxFractionDigits = parseInt(maxFractionDigits);
        if (fee.indexOf(".") === -1) {
            return fee;
        }
        var numField = fee.split(".");
        var integerDigits = numField[0];
        var fractionDigits = numField[1];

        if (fractionDigits.length <= maxFractionDigits) {
            return fee;
        }
        fractionDigits = fractionDigits.substring(0, maxFractionDigits);
        var feePriceStr = integerDigits + "." + fractionDigits;
        return feePriceStr;
    });

    //实际到账
    Handlebars.registerHelper("actualArrivalConvert", function (actualPrice,maxFractionDigits) {

        if (isNaN(actualPrice) || isNaN(maxFractionDigits)) {
            openTips("参数类型错误");
            return false;
        }
        actualPrice = actualPrice.toString();
        maxFractionDigits = parseInt(maxFractionDigits);
        if (actualPrice.indexOf(".") == -1) {
            return actualPrice;
        }
        var numField = actualPrice.split(".");
        var integerDigits = numField[0];
        var fractionDigits = numField[1];

        if (fractionDigits.length <= maxFractionDigits) {
            return actualPrice;
        }
        fractionDigits = fractionDigits.substring(0, maxFractionDigits);
        var feePriceStr = integerDigits + "." + fractionDigits;
        return feePriceStr;
    });

    //买入 卖出 撤销的样式显示
    Handlebars.registerHelper("paymentTypeClassConvert", function (paymentType) {
        if (paymentType == undefined || paymentType == null || paymentType == "" || isNaN(paymentType)) {
            return "未知类型";
        }
        if (paymentType == 1) {
            return "mr";
        }
        if (paymentType == 2) {
            return "mc";
        }
        if (paymentType == 3) {
            return "cx";
        }
        return "未知类型";
    });

    //时间转换
    Handlebars.registerHelper("addTimeConvert", function (addTime) {
        var date = new Date(addTime);
        var year = date.getFullYear();
        var month = date.getMonth() + 1;
        var day = date.getDate();
        var hours = date.getHours();
        if (hours < 10) {
            hours = '0' + hours;
        }
        var minutes = date.getMinutes();
        if (minutes < 10) {
            minutes = '0' + minutes;
        }
        var seconds = date.getSeconds();
        if (seconds < 10) {
            seconds = '0' + seconds;
        }
        return year + "-" + month + "-" + day + " " + hours + ":" + minutes + ":" + seconds;
    });

    //返回
    $('.back').on('click', function () {
        //根据跳转源返回到对应的页面
        if (pendingOrderNo == null || pendingOrderNo == "") {
            window.location.href = wapPath + "/userWap/wapTransactionPendOrderController/showMyRecord";
        } else {
            window.location.href = wapPath + "/userWap/wapTransactionPendOrderController/show.htm";
        }
    });

    //查看更多
    function seeMore() {
        pageNumber = Number(pageNumber) + 1;
        $.ajax({
            url: wapPath + "/userWap/wapDealRecord/getAccountRecord.htm",
            data: {
                pendingOrderNo: pendingOrderNo,
                pageNumber: pageNumber
            },
            dataType: "json",
            type: 'POST',
            async: true,
            success: function (resultData) {
                var code = resultData.code;
                var message = resultData.message;
                if (code != 1 && message != "") {
                    openTips(message);
                    return;
                }
                var dealRecordList = resultData.data.dealRecordList;
                if (dealRecordList != null) {
                    //装配数据
                    var template = Handlebars.compile($("#accountRecord").html())
                    $('#tableList').append(template(resultData.data));
                }
                //查看更多限制
                if (resultData.data.totalPageNumber <= Number(pageNumber) + 1) {
                    $("#seeMore").hide(1);
                }
            },
            error: function () {
                openTips("请求数据异常");
            }
        });
    }
</script>
</html>