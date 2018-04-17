<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
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
    <link rel="stylesheet" href="<%=path %>/resources/css/wap/recharge.css">

    <title>充币成功记录</title>

</head>
<body>

<div id="contactDiv">
    <header style="border-bottom:0">
        <img src="<%=path %>/resources/image/wap/back.png"
             onclick="window.location.href='<%=path %>/userWap/userInfo/myRecord/show.htm'" class="backimg"/>
        <p>充币成功记录</p>
    </header>

    <div class="wrapper">
        <div id="tableList"></div>
        <p id="seeMore" class="more" onclick="seeMore()">查看更多</p>
    </div>
    <input type="hidden" id="pageNumber" name="pageNumber" value="${pageNumber}">
</div>

<!-- loading图 -->
<div id="loading">
    <i></i>
</div>

</body>
<script type="text/javascript" src="http://libs.baidu.com/jquery/2.1.4/jquery.min.js"></script>
<script type="text/javascript" src="https://cdn.bootcss.com/handlebars.js/4.0.11/handlebars.min.js"></script>
<script type="text/javascript" src="<%=path %>/resources/js/wap/simpleTips_wap.js"></script>
<script type="text/javascript" src="<%=path %>/resources/js/wap/common.js"></script>
<script type="text/javascript" src="<%=path %>/resources/js/wap/zepto.min.js"></script>

<script id="contentId" type="text/template">
    {{#each userRechargeCoinRecordList}}
    <div class="main">
        <p class="titleBox">
            <span class="name">{{currencyName}}</span>
        </p>
        <div class="content">
            <p class="num">{{{numberFormat currencyNumber 8}}}</p>
            <p class="serialTitle">订单流水号</p>
            <p class="timeTitle">订单时间</p>
            <p class="serial">{{walletOrderNo}}</p>
            <p class="time">{{{timeFormat orderTime}}}</p>
            <p class="clear"></p>
        </div>
        {{#if remark}}
        <div class="footer">
            <p class="remark">{{remark}}</p>
            <p class="clear"></p>
        </div>
        {{else}}
        {{/if}}
    </div>
    {{/each}}
</script>
<script>
    var pageNumber = $("#pageNumber").val();

    //对位数进行控制
    Handlebars.registerHelper("numberFormat", function (num, maxFractionDigits) {
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

    //时间转换
    Handlebars.registerHelper("timeFormat", function (addTime) {
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

    $.ajax({
        url: '<%=path %>' + "/userWap/wapRechargeCoinRecord/getRechargeCoinRecordList",
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

            //预编译模板
            var template = Handlebars.compile($("#contentId").html());

            //输入 匹配数据到模板
            $('#tableList').html(template(resultData));

            if (resultData.totalPageNumber <= Number(pageNumber) + 1) {
                $("#seeMore").hide();
            } else {
                $("#seeMore").show();
            }
        },
        error: function () {
            openTips("请求数据异常");
        }
    });

    function seeMore() {
        pageNumber = Number(pageNumber) + 1;
        $.ajax({
            url: '<%=path %>' + "/userWap/wapRechargeCoinRecord/getRechargeCoinRecordList",
            type: 'post',
            dataType: "json",
            async: true,
            data: {
                pageNumber: pageNumber
            },
            success: function (result) {
                var code = result.code;
                var message = result.message;
                if (code != 1 && message != "") {
                    openTips(message);
                    return;
                }

                if (result.userRechargeCoinRecordList != null) {
                    var template = Handlebars.compile($("#contentId").html());
                    $('#tableList').append(template(result));
                }

                //查看更多限制
                if (result.totalPageNumber <= Number(pageNumber) + 1) {
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