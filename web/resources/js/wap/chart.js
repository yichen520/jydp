var ChartParamsAndInit = {
    registerHandler: function () {
        //方法注册函数加载 数字 分隔位
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
        Handlebars.registerHelper("paymentTypeFormat", function (type, index) {
            if (type == undefined || type == null || type == "" || isNaN(type)) {
                return "未知类型";
            }
            if (type == 1) {
                return "<span class='red'>买入</span>";
            }
            if (type == 2) {
                return "<span class='green'>卖出</span>";
            }
            return "未知类型";
        });
        Handlebars.registerHelper("isLogin", function (userSession, webAppPath) {
            if (userSession == undefined || userSession == null || userSession == "") {
                return "<a href='" + webAppPath + "/userWap/userLogin/show'>登录</a>";
            } else {
                return "";
            }
        });
        Handlebars.registerHelper("timeFormat", function (timestamp) {
            var date = new Date(timestamp);//10位需要乘以1000
            var Y = date.getFullYear() + "-";
            if (date.getMonth() + 1 < 10) {
                M = '0' + (date.getMonth() + 1);
            } else {
                M = date.getMonth() + 1;
            }
            M = M + "-";
            var D = date.getDate() + ' ';
            var h = date.getHours() + ':';
            var m = date.getMinutes() + ':';
            var s = date.getSeconds();
            return Y + M + D + h + m + s;
        });
    },
    formatDate: function (timestamp) {
        var date = new Date(timestamp);//10位需要乘以1000
        var Y = date.getFullYear() + "-";
        if (date.getMonth() + 1 < 10) {
            M = '0' + (date.getMonth() + 1);
        } else {
            M = date.getMonth() + 1;
        }
        M = M + "-";
        var D = date.getDate() + ' ';
        var h = date.getHours() + ':';
        var m = date.getMinutes() + ':';
        var s = date.getSeconds();
        return Y + M + D + h + m + s;
    },
    currencyInfo: function () {
        var webAppPath = $("#webAppPath").val();
        $.ajax({
            url: "currencyInfo",
            dataType: "json",
            type: 'POST',
            async: true, //默认异步调用 (false：同步)
            success: function (data) {
                if (data.code != 0) {
                    return;
                }
                var transactionUserDealList = data.transactionUserDealList;
                if (transactionUserDealList != undefined && transactionUserDealList != null && transactionUserDealList != "") {
                    var $currencyListUl = $("#currencyListUl");
                    $currencyListUl.empty();
                    var out = "";
                    for (var i = 0; i < transactionUserDealList.length; i++) {
                        myHref = "<a href='" + "toChartPage?currencyId=" + transactionUserDealList[i].currencyId + "'>";
                        out = out + '<li>' +
                            '<p>' + myHref + transactionUserDealList[i].currencyName + "(" + transactionUserDealList[i].currencyShortName + ")" + "</a></p>"
                            + "<p class='zhang'>" + transactionUserDealList[i].latestPrice + "</p>"
                            + "<p class='zhang'>" + transactionUserDealList[i].change + "%</p>"
                            + "</li>";
                    }
                    $currencyListUl.html(out);
                }
            },
            error: function () {
                return;
            }
        });
    },
    open: function () {
        var bgHeight = $(document).height();
        $('.open').on('click', function () {
            $('.closeAnthoer').css("height", bgHeight + "px");
            $('.choseBz').css("height", bgHeight + "px");
            $('.choseBzBox').css("height", bgHeight + "px");
            $('.choseBzBox').show();
            $('.choseBzBox').animate({left: '0'}, "500");
            //加载数据
            ChartParamsAndInit.currencyInfo();
        });
        $('.closeBox').on('click', function () {
            setTimeout(function () {
                $('.closeAnthoer').css("height", "0");
                $('.choseBz').css("height", "0");
                $('.choseBzBox').css("height", "0");
            }, 450);
            $('.choseBzBox').animate({left: '-82%'}, "500");
        });
    },
    formatNumber: function (num, maxFractionDigits) {
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
    },
    dealInfo: function () {
        var currencyId = $("#currencyId").val();
        if (undefined == currencyId || currencyId == null || currencyId == "") {
            return;
        }
        $.ajax({
            url: "gainDealPrice",
            data: {
                currencyId: currencyId
            },//参数
            dataType: "json",
            type: 'POST',
            async: true, //默认异步调用 (false：同步)
            success: function (data) {
                if (data.code != 0) {
                    return;
                }
                var standardParameter = data.standardParameter;
                if (standardParameter != null) {
                    $(".topLeft").text(ChartParamsAndInit.formatNumber(standardParameter.nowPrice, 8));
                    $("#todayMaxSpan").text(ChartParamsAndInit.formatNumber(standardParameter.todayMax, 6));
                    $("#todayMinSpan").text(ChartParamsAndInit.formatNumber(standardParameter.todayMin, 6));
                    $("#buyOneSpan").text(ChartParamsAndInit.formatNumber(standardParameter.buyOne, 6));
                    $("#sellOneOne").text(ChartParamsAndInit.formatNumber(standardParameter.sellOne, 6));
                    $("#dayTurnoveOne").text(ChartParamsAndInit.formatNumber(standardParameter.dayTurnove, 4)+"万");
                }
            },
            error: function () {
                return;
            }
        });
    },
    deal: function () {
        var currencyId = $("#currencyId").val();
        if (undefined == currencyId || currencyId == null || currencyId == "") {
            return;
        }
        $.ajax({
            url: "deal", //方法路径URL
            data: {
                currencyId: currencyId
            },
            dataType: 'json',
            type: 'POST',
            async: true, //默认异步调用 (false：同步)
            success: function (data) {
                if (data.code != 0) {
                    return;
                }
                var dealList = data.dealList;
                $("#dealDiv").empty();
                var newChildSell = "";
                for (var i = 0; i <= dealList.length - 1; i++) {
                    var info = dealList[i].paymentType == 1 ? "<span class='red'>买入</span>" : "<span class='green'>卖出</span>"
                    newChildSell += '<ul class="entrust-content">' +
                        ' <li>' + info + '</li>' +
                        '<li>' + ChartParamsAndInit.formatNumber(dealList[i].transactionPrice, 2) + '</li>' +
                        '<li>' + ChartParamsAndInit.formatNumber(dealList[i].currencyNumber, 4) + '</li>' +
                        '<li>' + ChartParamsAndInit.formatDate(dealList[i].addTime) + '</li>' +
                        '</ul>';
                }
                $("#dealDiv").html(newChildSell);
            },
            error: function () {
                return;
            }
        });
    },
    reloadData: function () {
        var currencyId = $("#currencyId").val();
        if (undefined == currencyId || currencyId == null || currencyId == "") {
            return;
        }
        window.setInterval(ChartParamsAndInit.dealInfo, 1000);
        window.setInterval(ChartParamsAndInit.deal, 1000);
    },
    openChart: function () {
        Highcharts.setOptions({
            global: {useUTC: false},
            lang: {
                months: ['一月', '二月', '三月', '四月', '五月', '六月', '七月', '八月', '九月', '十月', '十一月', '十二月'],
                weekdays: ['星期日', '星期一', '星期二', '星期三', '星期四', '星期五', '星期六']
            }
        });
        $(".wrapper ul").on('click', 'li', function () {
            $('.choose').removeClass('choose');
            $(this).addClass('choose');
            ChartParamsAndInit.gainMatch($(this).index());
        });
    },
    gainMatch: function (i) {
        var gainHash = ['5m', '15m', '30m', '1h', '4h', '1d', '1w'];
        ChartParamsAndInit.gainGraphData(gainHash[i], 7);
    },
    gainGraphData: function (time) {
        var currencyId = $("#currencyId").val();
        clearTimeout(t);
        var currencyName = $("#currencyName").val();
        var webAppPath = $("#webAppPath").val();
        Highcharts.setOptions({
            global: {useUTC: false},
            lang: {
                months: ['一月', '二月', '三月', '四月', '五月', '六月', '七月', '八月', '九月', '十月', '十一月', '十二月'],
                weekdays: ['星期日', '星期一', '星期二', '星期三', '星期四', '星期五', '星期六']
            }
        });
        $.ajax({
            url: "gainGraphData", //方法路径URL
            data: {
                currencyId: currencyId,
                node: time
            },//参数
            dataType: 'json',
            type: 'POST',
            async: true, //默认异步调用 (false：同步)
            success: function (data) {
                if (data.code != 0) {
                    return;
                }
                data = data.transactionGraphList;
                var ohlc = [], res = [], volome = [], dataLength = data.length;
                var i = 0;
                if (time == "5m") {
                    newTime = "5m";
                    groupingUnits = [[
                        'minute',                         // unit name
                        [5]                             // allowed multiples
                    ]]
                }
                if (time == "15m") {
                    newTime = "15m";
                    groupingUnits = [[
                        'minute',                         // unit name
                        [15]                             // allowed multiples
                    ]]
                }
                if (time == "30m") {
                    newTime = "30m";
                    groupingUnits = [[
                        'minute',                         // unit name
                        [30]                             // allowed multiples
                    ]]
                }
                if (time == "1h") {
                    newTime = "1h";
                    groupingUnits = [[
                        'hour',                         // unit name
                        [1]                             // allowed multiples
                    ]]
                }
                if (time == "4h") {
                    newTime = "4h";
                    groupingUnits = [[
                        'hour',                         // unit name
                        [4]                             // allowed multiples
                    ]]
                }
                if (time == "1d") {
                    newTime = "1d";
                    groupingUnits = [[
                        'day',                         // unit name
                        [1]                             // allowed multiples
                    ]]
                }
                if (time == "1w") {
                    newTime = "1w";
                    groupingUnits = [[
                        'week',                         // unit name
                        [1]                             // allowed multiples
                    ]]
                }
                for (i; i < dataLength; i += 1) {
                    ohlc.push([
                        data[i].dealDate, // 时间节点
                        data[i].openPrice, // 开盘价
                        data[i].maxPrice, // 最高价
                        data[i].minPrice, // 最低价
                        data[i].closPrice // 收盘价
                    ]);
             /*       res.push([
                        data[i].dealDate, // 时间节点
                        data[i].countPrice // 成交量
                    ]);*/
                }
                // 使用框架
                $('#chart').highcharts('StockChart', {
                    chart: {
                        events: {
                            // 第一次加载的时候触发事件
                            load: function () {
                                $('.wrapper ul').css('display', 'block');
                            }
                        }
                    },
                    exporting: {
                        enabled: false //用来设置是否显示‘打印’,'导出'等功能按钮，不设置时默认为显示
                    },
                    credits: {
                        text: '',
                        href: ''
                    },

                    plotOptions: {
                        column: {
                            pointWidth: 5
                        }
                    },
                    tooltip: {
                        split: false,
                        xDateFormat: '%A %Y-%m-%d %H:%M:%S',  //  显示星期
                        shared: true,
                        valueDecimals: 4 // 小数点后几位
                    },
                    xAxis: {
                        crosshair: {
                            width: 1,
                            color: '#cccccc'
                        }
                    },
                    navigator: {
                        enabled: false
                    },
                    scrollbar: {
                        enabled: false
                    },
                    rangeSelector : {
                        enabled: false
                    },
                    yAxis: [{
                        allowDecimals: 'false',
                        crosshair: {
                            width: 1,
                            color: '#cccccc'
                        },
                        floor: 0,  //  最低数据大于0，
                        labels: {
                            format: '{value}'
                        },
                        opposite: false,
                        top: '12%',
                        height: '88%',
                        resize: {
                            enabled: true
                        },
                        lineWidth: 2
                    }],
                    series : [
                        {
                            name : '123',
                            type: 'candlestick',
                            color: 'green',
                            lineColor: 'green',
                            upColor: 'red',
                            upLineColor: 'red',
                            data : ohlc,

                            navigatorOptions: {
                                color: Highcharts.getOptions().colors[0]
                            },
                            dataGrouping: {
                                units: groupingUnits
                            },

                        }
                    ]
                });
            },
            error: function () {
                return;
            }
        });
        t = setTimeout("ChartParamsAndInit.gainGraphData(newTime)", 1000 * 5 * 60);
    }
}

$().ready(function () {
    t = "";
    //方法注册
    ChartParamsAndInit.registerHandler();
    var currencyIdStr = $("#currencyIdStr").val()
    if (currencyIdStr === undefined || isNaN(currencyIdStr)) {
        currencyIdStr = "";
    }
    $.ajax({
        url: 'getChartPageInfo',
        type: 'POST',
        dataType: 'json',
        async: true,
        data: {
            "currencyIdStr": currencyIdStr
        },
        success: function (data) {
            if (data.code != 0) {
                openTips(data.message);
            }

            //加载currencyNameTemplate
            var headerTemplate = $("#headerTemplate").html();
            var headerComplile = Handlebars.compile(headerTemplate);
            var headerHtml = headerComplile(data);
            $("#header").html(headerHtml);

            //数据准备
            //参数给与
            ChartParamsAndInit.openChart();
            ChartParamsAndInit.open();
            ChartParamsAndInit.reloadData();
            ChartParamsAndInit.gainGraphData("5m", 7);
        }
    });
});