<%@ page pageEncoding="UTF-8" %>
<%@include file="/resources/page/common/path.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <meta http-equiv="Cache-Control" content="no-cache"/>
    <link rel="stylesheet" href="<%=path %>/resources/css/wap/common.css">
    <link rel="stylesheet" href="<%=path %>/resources/css/wap/simpleTips_wap.css">
    <link rel="stylesheet" href="<%=path %>/resources/css/wap/entrust.css">
    <title>委托记录</title>
</head>
<body>
<!-- 头部导航 -->
<header>
    <img src="<%=path %>/resources/image/wap/back.png" class="back"/>
    <p>委托记录</p>
    <input type="hidden" id="pageNumber" name="pageNumber" value="${pageNumber}">
</header>
<div class="wrapper">
    <div id="tableList"></div>
    <div class="more" id="seeMore" onclick="seeMore()">查看更多</div>
</div>
<div class="bg">
    <div class="showBox">
        <div class="showBoxTitle">撤销委托</div>
        <div class="showBoxContent">是否撤销该委托？</div>
        <div class="showBoxButton">
            <div class="cancelShow">取消</div>
            <div class="okay" onclick="sendAjax()">确定</div>
        </div>
    </div>
</div>
<!-- loading图 -->
<div id="loading">
    <i></i>
</div>
<input type="hidden" id="webPath" value="<%=path %>">
</body>
<script src="<%=path %>/resources/js/wap/common.js"></script>
<script src="<%=path %>/resources/js/wap/zepto.min.js"></script>
<script src="<%=path %>/resources/js/wap/jquery-2.1.4.min.js"></script>
<%--<script src="<%=path %>/resources/js/wap/entrust.js"></script>--%>
<script src="<%=path %>/resources/js/wap/simpleTips_wap.js"></script>
<script src="<%=path %>/resources/js/wap/handlebars-v4.0.11.js"></script>

<script type="text/template" id="pendOrder">
    {{#each transactionPendOrderRecordList}}
    <div class="content">
        <div class="header">
            <p class="date">委托时间：{{addTimeConvert addTime}}</p>
            <p class="state">{{pendingStatusConvert pendingStatus}}</p>
            <div class="clear"></div>
        </div>
        <div class="nav">
            <div class="navTop">
                <p class="name">{{currencyName}}</p>
                <p class="{{paymentTypeClassConvert paymentType}}">{{paymentTypeConvert paymentType}}</p>
            </div>
            <div class="navLeft">
                <ul>
                    <li>委托数量：<span>{{formatNumber pendingNumber 4}}</span></li>
                    <li>委托单价：<span>{{formatNumber pendingPrice 6}}</span></li>
                    <li>委托总价：<span>{{formatNumber totalPrice 6}}</span></li>
                </ul>
            </div>
            <div class="navRight">
                <ul>
                    <li>成交数量：<span>{{formatNumber dealNumber 4}}</span></li>
                    <li>剩余数量：<span class="special">{{formatNumber remainNum 4}}</span></li>
                </ul>
            </div>
        </div>
        <div class="bottom">
            {{{show pendingStatus pendingOrderNo}}}
            <input class="findClass" type="hidden" id="pendingOrderNo" value="{{pendingOrderNo}}">
        </div>
    </div>
    {{/each}}
</script>

<script type="text/javascript">
    var webPath = $("#webPath").val();
    var pageNumber = $("#pageNumber").val();

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

    //收支类型转换
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
        return "未知类型";
    });

    //挂单状态转换
    Handlebars.registerHelper("pendingStatusConvert", function (status) {
        if (status == undefined || status == null || status == "" || isNaN(status)) {
            return "未知类型";
        }
        if (status == 1) {
            return "未成交";
        }
        if (status == 2) {
            return "部分成交";
        }
        if (status == 3) {
            return "全部成交";
        }
        if (status == 4) {
            return "部分撤销";
        }
        if (status == 5) {
            return "全部撤销";
        }
        return "未知类型";
    });

    //剩余数量
    Handlebars.registerHelper("pendingRemainNumberConvert", function (pendingNumber, dealNumber,maxFractionDigits) {
        var pendingRemainNumber = pendingNumber - dealNumber;
        if (isNaN(pendingRemainNumber) || isNaN(maxFractionDigits)) {
            openTips("参数类型错误");
            return false;
        }
        pendingRemainNumber = pendingRemainNumber.toString();
        maxFractionDigits = parseInt(maxFractionDigits);
        if (pendingRemainNumber.indexOf(".") === -1) {
            return pendingRemainNumber;
        }
        var numField = pendingRemainNumber.split(".");
        var integerDigits = numField[0];
        var fractionDigits = numField[1];

        if (fractionDigits.length <= maxFractionDigits) {
            return pendingRemainNumber;
        }
        fractionDigits = fractionDigits.substring(0, maxFractionDigits);
        var pendingRemainNumberStr = integerDigits + "." + fractionDigits;
        return pendingRemainNumberStr;
    });

    //委托总价
    Handlebars.registerHelper("pendingTotalPriceConvert", function (pendingNumber, pendingPrice,maxFractionDigits) {
        var pendingTotalPrice = pendingNumber * pendingPrice;
        if (isNaN(pendingTotalPrice) || isNaN(maxFractionDigits)) {
            openTips("参数类型错误");
            return false;
        }
        pendingTotalPrice = pendingTotalPrice.toString();
        maxFractionDigits = parseInt(maxFractionDigits);
        if (pendingTotalPrice.indexOf(".") === -1) {
            return pendingTotalPrice;
        }
        var numField = pendingTotalPrice.split(".");
        var integerDigits = numField[0];
        var fractionDigits = numField[1];

        if (fractionDigits.length <= maxFractionDigits) {
            return pendingTotalPrice;
        }
        fractionDigits = fractionDigits.substring(0, maxFractionDigits);
        var pendingTotalPriceStr = integerDigits + "." + fractionDigits;
        return pendingTotalPriceStr;
    });

    //时间转换
    Handlebars.registerHelper("addTimeConvert", function (addTime) {
        var date = new Date(addTime);
        var year = date.getFullYear();
        var month = date.getMonth() + 1;
        if (month < 10) {
            month = '0' + month;
        }
        var day = date.getDate();
        if (day < 10) {
            day = '0' + day;
        }
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

    //买入卖出 区分显示
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

    //底部显示转换
    Handlebars.registerHelper("show", function (status) {
        var out = "";
        if (status == 1 || status == 2) {
            out = out + "<div class='cancel'>" + "撤销申请" + "</div>"
        }
        if (status != 1) {
            out = out + "<div class='see'>" + "查看详情" + "</div>";
        }
        return out;
    });


    $("#seeMore").hide();
    $.ajax({
        url: webPath + "/userWap/wapTransactionPendOrderController/getTransactionPendOrderList",
        data: {
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

            var template = Handlebars.compile($("#pendOrder").html())
            $('#tableList').html(template(resultData.data));

            if (resultData.data.totalPageNumber <= Number(pageNumber) + 1) {
                $("#seeMore").hide();
            }else{
                $("#seeMore").show();
            }


            $(".see").each(function () {
                $(this).bind("click", seeDetails);
            });
            $(".cancel").each(function () {
                $(this).bind("click", applicationCanceled);
            });
        },
        error: function () {
            openTips("请求数据异常");
        }
    });

    //返回跳转
    $('.back').on('click', function () {
        window.location.href = webPath + "/userWap/wapTransactionPendOrderController/showMyRecord";
    });

    //查看详情页面跳转
    function seeDetails() {
        //直接通过id去取的值会是同一个值
        var pendingOrderNo = $(this).parent().children('.findClass').val();
        if (pendingOrderNo == null || !pendingOrderNo) {
            openTips("参数错误")
        }
        window.location.href = webPath + "/userWap/wapDealRecord/show/" + pendingOrderNo;
    }

    //撤销申请
    var pendingOrderNoCancel;
    var clickFlag = false
    function applicationCanceled() {
        pendingOrderNoCancel = $(this).parent().children('.findClass').val();
        var bgHeight = $(document).height();
        $('.bg').css("height", bgHeight + "px");
        $('.bg').fadeIn();

        $('.cancelShow').on('click', function () {
            $('.bg').css("height", "0");
            $('.bg').fadeOut();
            clickFlag = false;
        });
    }
    function cancleOrder() {
        console.log('cancleOrder');
        if (pendingOrderNoCancel == "" || pendingOrderNoCancel == null) {
            openTips("挂单号错误");
            return;
        }
        $.ajax({
            url: webPath + "/userWap/wapTransactionPendOrderController/revoke.htm",
            data: {
                pendingOrderNo: pendingOrderNoCancel
            },
            dataType: "json",
            type: 'POST',
            async: true,
            success: function (resultData) {
                var code = resultData.code;
                var message = resultData.message;
                openTips(message);
                if (code != 1 && message != "") {
                    calMoreBoo = false;
                    return;
                }
                setTimeout(function () {
                    window.location.href = webPath + "/userWap/wapTransactionPendOrderController/show.htm"
                },1000);
            },
            error: function () {
                openTips("数据加载出错，请稍候重试");
            }
        });
    }
    var timer = null;
    function sendAjax() {
        $('.bg').css("height", "0");
        $('.bg').fadeOut();
        clearTimeout(timer);
        timer = setTimeout(function(){
            cancleOrder();
        },500)
    }

    function seeMore() {
        pageNumber = Number(pageNumber) + 1;
        $.ajax({
            url: webPath + "/userWap/wapTransactionPendOrderController/getTransactionPendOrderList",
            data: {
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

                var transactionPendOrderRecordList = resultData.data.transactionPendOrderRecordList;
                if (transactionPendOrderRecordList != null) {
                    //专配数据
                    var template = Handlebars.compile($("#pendOrder").html());
                    $('#tableList').append(template(resultData.data));

                    //绑定事件
                    $(".see").each(function () {
                        $(this).bind("click", seeDetails);
                    });
                    $(".cancel").each(function () {
                        $(this).bind("click", applicationCanceled);
                    });
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