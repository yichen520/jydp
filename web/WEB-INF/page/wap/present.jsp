<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/resources/page/common/path.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <meta http-equiv="Cache-Control" content="no-cache" />

    <link rel="stylesheet" href="<%=path %>/resources/css/wap/common.css">
    <link rel="stylesheet" href="<%=path %>/resources/css/wap/simpleTips_wap.css">
    <link rel="stylesheet" href="<%=path %>/resources/css/wap/present.css">

    <title>币种提现记录</title>
</head>
<body>
    <header style="border-bottom:0">
        <img src="<%=path %>/resources/image/wap/back.png" class="backimg"/>
        <p>币种提现记录</p>
    </header>
    <!-- 内容区域 -->
    <input type="hidden" id="queryPageNumber" name="pageNumber" value="${pageNumber}">
    <div class="wrapper">
    </div>
    <c:if test="${pageNumber < totalPageNumber - 1}">
        <p class="more" onclick="pageNext()">查看更多</p>
    </c:if>
    <!-- 撤销弹窗 -->
    <div class="bg">
        <div class="showBox">
            <div class="showBoxTitle">撤回记录</div>
            <div class="showBoxContent">是否撤销该委托？</div>
            <div class="showBoxButton">
                <div class="cancelShow">取消</div>
                <div class="okay" onclick="withdraw()">确定</div>
            </div>
        </div>
    </div>
    <!-- loading图 -->
    <div id="loading">
        <i></i>
    </div>
    <input type="hidden" id="webPath" value="<%=path %>">
</body>
<script id="template" type="text/x-handlebars-template">
    {{#each this}}
    <div class="main">
        <p class="titleBox">
            <input type="hidden" id="recallRecordNo">
            <span class="name">{{currencyName}}</span>
            <span class="state">{{handleShow handleStatus}}</span>
        </p>
        <div class="content">
            <p class="num">{{formatNumber currencyNumber 2}}</p>
            <p class="timeTitle">申请时间</p>
            <p class="serialTitle">申请流水号</p>
            <p class="time">{{addTimeConvert addTime}}</p>
            <p class="serial">{{coinRecordNo}}</p>
            <p class="clear"></p>
        </div>
            <p class="money-state">{{#sendStatusShow handleStatus}}转出状态：
                <span>{{sendShow sendStatus}}</span>
                {{/sendStatusShow}}
            </p>
        <div class="footer">
            <p class="remark">{{remark}}</p>
            {{#withdrawShow handleStatus}}
                <p class="withdraw" onclick="showDialog('{{coinRecordNo}}')">撤回</p>
            {{/withdrawShow}}
            <p class="clear"></p>
        </div>
    </div>
    {{/each}}
</script>
<script src="<%=path %>/resources/js/wap/common.js"></script>
<script src="<%=path %>/resources/js/wap/zepto.min.js"></script>
<script src="<%=path %>/resources/js/wap/jquery-2.1.4.min.js"></script>
<script src="<%=path %>/resources/js/wap/simpleTips_wap.js"></script>
<script src="<%=path %>/resources/js/wap/handlebars-v4.0.11.js"></script>
<script src="<%=path %>/resources/js/wap/present.js"></script>
<script type="text/javascript">
    var webPath = $("#webPath").val();
    //时间转换
    Handlebars.registerHelper("addTimeConvert", function (addTime) {
        var date = new Date(addTime);
        var year = date.getFullYear();
        var month = date.getMonth() + 1;
        month=month < 10 ? ('0' + month) : month;
        var day = date.getDate();
        day=day < 10 ? ('0' + day) : day;
        var hours = date.getHours();
        hours=hours < 10 ? ('0' + hours) : hours;
        var minutes = date.getMinutes();
        minutes=minutes < 10 ? ('0' + minutes) : minutes;
        var seconds = date.getSeconds();
        seconds=seconds < 10 ? ('0' + seconds) : seconds;
        return year + "-" + month + "-" + day + " " + hours + ":" + minutes + ":" + seconds;
    });

    //函数加载
    Handlebars.registerHelper('formatNumber', function (num, maxFractionDigits, options) {
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

    //处理状态显示
    Handlebars.registerHelper("handleShow", function (handleStatus) {
        switch (handleStatus) {
            case 1: return "待审核";break;
            case 2: return "审核通过";break;
            case 3: return "审核拒绝";break;
            case 4: return "已撤回";
        }
    });

    //撤回按钮显示
    Handlebars.registerHelper("withdrawShow", function (handleStatus,options) {
        if (handleStatus == 1) {
            //满足条件执行
            return options.fn(this);
        }
    });

    //转出状态字段显示
    Handlebars.registerHelper("sendStatusShow", function (handleStatus,options) {
       if (handleStatus == 2) {
           //满足条件执行
           return options.fn(this);
       }
    });

    //转出状态显示
    Handlebars.registerHelper("sendShow", function (sendStatus) {
        switch (sendStatus) {
            case 1: return "待转出";break;
            case 2: return "转出中";break;
            case 3: return "转出成功";break;
            case 4: return "转出失败";
        }
    });

    //提币记录数据填充
    var presentListData = ${requestScope.coinOutRecordList};
    var presentfunc = Handlebars.compile($('#template').html());
    $('.wrapper').html(presentfunc(presentListData));

    //更多
    function pageNext() {
        var pageNumber = parseInt($("#queryPageNumber").val());
        var totalPageNumber = parseInt(${requestScope.totalPageNumber});
        if(pageNumber < totalPageNumber - 1){
            pageNumber = pageNumber + 1;
            $.ajax({
                url: '<%=path %>/userWap/presentRecord/showMorePresent.htm',
                type: 'post',
                dataType: 'json',
                data:{pageNumber:pageNumber},//参数
                success: function (result) {
                    if (result.code == 1) {
                        var presentList = result.coinOutRecordList;
                        if (presentList != null && presentList.length > 0) {
                            var transactionfunc = Handlebars.compile($('#template').html());
                            $('.wrapper').append(transactionfunc(presentList));
                            $('#queryPageNumber').val(result.pageNumber);
                            if (result.pageNumber >= result.totalPageNumber - 1) {
                                $(".more").remove();
                            }
                        }
                    }
                }
            });
        } else {
            $(".more").remove();
            openTips("已全部加载完成");
        }
    }

    var withdrawBoo = false;
    //撤销操作
    function withdraw() {

        if (withdrawBoo) {
            openTips("正在撤销，请稍后！");
            return;
        } else {
            withdrawBoo = true;
        }

        var coinRecordNo = $("#recallRecordNo").val();
        $.ajax({
            url: '<%=path %>/userWap/presentRecord/withdrawCoinOutRecord.htm',
            type: 'post',
            data: {
                coinRecordNo: coinRecordNo
            },
            dataType: 'json',
            success: function (result) {
                if (result.code == 1) {
                    window.location.href = "<%=path %>/userWap/presentRecord/show.htm";
                } else {
                    openTips(result.message);
                    setTimeout(function () {
                        window.location.href = "<%=path %>/userWap/presentRecord/show.htm";
                    }, 1000);
                }
                withdrawBoo = false;
            },
            error: function () {
                withdrawBoo = false;
                openTips("数据加载出错，请稍候重试");
            }
        });
    }
</script>
</html>