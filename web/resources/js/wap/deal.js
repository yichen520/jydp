var ParamsAndInit = {
    tabChange: function () {
        $(".mainTitle li:eq(1)").click(function () {
            $('.borderSell').show();
            $('.borderBuy').hide();
            $('.borderEntrust').hide();
            $('.sell').fadeIn();
            $('.buy').hide();
            $('.entrust').hide();
        });
        $(".mainTitle li:eq(2)").click(function () {
            $('.borderSell').hide();
            $('.borderBuy').hide();
            $('.borderEntrust').show();
            $('.entrust').fadeIn();
            $('.sell').hide();
            $('.buy').hide();
        });
        $(".mainTitle li:eq(0)").click(function () {
            $('.borderSell').hide();
            $('.borderBuy').show();
            $('.borderEntrust').hide();
            $('.sell').hide();
            $('.buy').fadeIn();
            $('.entrust').hide();
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
    cancleOpt: function () {
        $(".mask").fadeOut();
        $(".buyConfirm").fadeOut();
        $(".sellConfirm").fadeOut();
        $(".mask_order").fadeOut();
        $(".orderConfirm").fadeOut();
    },
    matchUtil: function (e) {
        var matchStr = /^-?\d+\.?\d{0,num}$/;
        //格式化显示值
        var value = $(this).val();
        if(!matchStr.test(value)){
            if(isNaN(value)){
                $(this).val('');
            }else{
                var a = value.indexOf('.');
                var b = a + e.data.num + 1;
                if(a > -1 && value.length > b){
                    $(this).val(value.substring(0, b));
                }
            }
        }
        //计算
        var m = 0;
        var f = 0;
        var buyPrice = $("#buyPrice").val();
        var buyNum = $("#buyNum").val();
        var buyFee = $("#buyFee").val();
        //买入
        if (buyPrice != null && buyPrice != "") {
            buyPrice = buyPrice.toString();
            try{m += buyPrice.split(".")[1].length}catch(e){}
            if (buyNum != null && buyNum != "") {
                buyNum = buyNum.toString();
                try{
                    //拿到价格小数点后的数字
                    m += buyNum.split(".")[1].length
                }catch(e){
                }
                //价格 * 数量 除以 (10 * 小数点的位数的8位浮点数)
                var number = parseFloat((Number(buyPrice.replace(".","")) * Number(buyNum.replace(".","")) / Math.pow(10,m)).toFixed(8));
                //设置购买总价格
                $("#buyTotal").val("$" + number);
            }
            //可用美金
            var userBalance = parseFloat($("#userBalance").text());
            if(buyPrice > 0){
                //如果单价大于0，购买的手续费用乘以100
                buyFee = buyFee * 100;
                //购买的手续费 * （单价 * 100）
                var number = buyFee * (buyPrice * 100);
                //购买价格 * 1000000 + number / 100 00 00 求出每个单价(包含手续费的单价)
                buyPrice = ((buyPrice * 1000000) + (number)) / 1000000
                //或得可以购买的个数
                var totalCanBuy = userBalance / buyPrice;
                //格式化可买的个数 返回字符串
                var tota = ParamsAndInit.mulMaxNumber(totalCanBuy);
                $("#buyMax").html("最大可买: " + Math.floor(tota * 1000000) / 1000000);
            }else{
                //如果价格小于0就直接返回0
                $("#buyMax").html("最大可买: " + "0");
            }
        } else {
            //价格不正确直接返回
            $("#buyMax").html("最大可买: " + "0" );
            $("#buyTotal").val("$0");
        }
        //卖出
        var s = 0;
        var z = 0;
        //获取售出价格
        var sellPrice = $("#sellPrice").val();
        //售出数量
        var sellNum = $("#sellNum").val();
        if (sellPrice != null && sellPrice != "") {
            sellPrice = sellPrice.toString();
            try{
                //获取小数点后长度
                s += sellPrice.split(".")[1].length;
            }catch(e){
            }
            try{
                //获取小数点后长度 这个z的作用在哪里
                z += sellPrice.split(".")[1].length;
            }catch(e){
            }
            //数量不等于空
            if (sellNum != null && sellNum != "") {
                //获取数量
                sellNum = sellNum.toString();
                try{
                    //获取数量小数点后长度 加上 价格小数点后长度
                    s += sellNum.split(".")[1].length
                }catch(e){
                }
                //保证小数位为8位
                var number = parseFloat((Number(sellPrice.replace(".", "")) * Number(sellNum.replace(".", "")) / Math.pow(10, s)).toFixed(8));
                //格式化
                number = ParamsAndInit.mulMaxNumber(number);
                $("#sellTotal").val("$" + number);
            }

            //可用币
            var currencyNumber = $("#currencyNumber").val();
            if(currencyNumber != null && currencyNumber != ""){
                currencyNumber = currencyNumber.toString();
                try{
                    //出售价格小数点后位 + 出售数量小数点后尾数
                    z += currencyNumber.split(".")[1].length
                }catch(e){
                }
                var number = parseFloat((Number(sellPrice.replace(".", "")) * Number(currencyNumber.replace(".", "")) / Math.pow(10, z)).toFixed(8));
                //格式化
                number = ParamsAndInit.mulMaxNumber(number);
                $("#sellMax").html("最大可获得: " + "$" + number);
            }
        } else {
            //价格不正确
            $("#sellMax").html("最大可获得: "+"$0");
            $("#sellTotal").val("$0");
        }
    },
    mulMaxNumber: function (value) {
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
    },
    toBuy: function () {
        var buyPrice = $("#buyPrice").val();
        var buyNum = $("#buyNum").val();
        var $buyTotal = $("#buyTotal");
        var buyTotal = $buyTotal.val();
        var buyPwd = $("#buyPwd").val();
        var isPwd = $("#userIsPwd").val();

        $("#buyPriceConfirm").val(buyPrice);
        $("#buyNumConfirm").val(buyNum);
        $("#buyPwdConfirm").val(buyPwd);

        $("#buyPrice").val("");
        $("#buyNum").val("");
        $("#buyPwd").val("");
        $("#buyMax").html("最大可买: " + "0");
        $buyTotal.val("$0");

        var user = $("#userSession").val();
        if (user == undefined || user == null || user == "") {
            openTips("请先登录再操作");
            return;
        }
        if (buyPrice == null || buyPrice == "") {
            openTips("价格不能为空");
            return;
        }
        if (buyPrice <= 0) {
            openTips("价格不能小于等于0");
            return;
        }
        if (buyNum == null || buyNum == "") {
            openTips("数量不能为空");
            return;
        }
        if (buyNum <= 0) {
            openTips("数量不能小于等于0");
            return;
        }
        if ((buyPwd == null || buyPwd == "") && isPwd == 1) {
            openTips("交易密码不能为空");
            return;
        }
        if (buyPwd.length < 6 && isPwd == 1) {
            openTips("交易密码不能小于六位");
            return;
        }
        if (isNaN(buyPrice) || isNaN(buyNum)) {
            openTips("交易价格和购买数量必须是数字");
        }
        $("#buyPriceTips").html("$" + buyPrice);
        $("#buyNumTips").html(buyNum);
        $("#buySumTips").html(buyTotal);
        $(".mask").fadeIn();
        $(".buyConfirm").fadeIn();
    },
    toSell: function () {
        var sellPrice = $("#sellPrice").val();
        var sellNum = $("#sellNum").val();
        var $sellTotal = $("#sellTotal");
        var sellTotal = $sellTotal.val();
        var sellPwd = $("#sellPwd").val();
        var isPwd = $("#userIsPwd").val();

        $("#sellPriceConfirm").val(sellPrice);
        $("#sellNumConfirm").val(sellNum);
        $("#sellPwdConfirm").val(sellPwd);

        $("#sellPrice").val("");
        $("#sellNum").val("");
        $("#sellPwd").val("");
        $("#sellMax").html("最大可获得: " + "$" + "0");
        $sellTotal.val("$0");

        var user = $("#userSession").val();
        if (user == undefined || user == null || user == "") {
            openTips("请先登录再操作");
            return;
        }
        if (sellPrice == null || sellPrice == "") {
            openTips("价格不能为空");
            return;
        }
        if (sellPrice <= 0) {
            openTips("价格不能小于等于0");
            return;
        }
        if (sellNum == null || sellNum == "") {
            openTips("数量不能为空");
            return;
        }
        if (sellNum <= 0) {
            openTips("数量不能小于等于0");
            return;
        }
        if ((sellPwd == null || sellPwd == "") && isPwd == 1) {
            openTips("交易密码不能为空");
            return;
        }
        if (sellPwd.length < 6 && isPwd == 1) {
            openTips("交易密码不能小于六位");
            return;
        }
        if (isNaN(sellNum) || isNaN(sellPrice)) {
            openTips("出售数量和价格必须都是数字");
            retrurn;
        }
        $("#sellPriceTips").html("$" + sellPrice);
        $("#sellNumTips").html(sellNum);
        $("#sellSumTips").html(sellTotal);
        $(".mask").fadeIn();
        $(".sellConfirm").fadeIn();
    },
    sellHandle: function () {
        var sellPrice = $("#sellPriceConfirm").val();
        var sellNum = $("#sellNumConfirm").val();
        var sellPwd = $("#sellPwdConfirm").val();
        var currencyId = $("#cucyId").val();
        var webAppPath = $("#webAppPath").val();
        document.getElementById("buyPrice").value = "";
        document.getElementById("buyNum").value = "";
        document.getElementById("buyPwd").value = "";
        $("#sellMax").html("最大可获得: " + "$0");
        $("#sellTotal").val("$0");
        $.ajax({
            url: webAppPath + "/userWap/tradeCenter/sell.htm", //方法路径URL
            data: {
                sellPrice: sellPrice,
                sellNum: sellNum,
                sellPwd: sellPwd,
                currencyId: currencyId
            },//参数
            dataType: 'json',
            type: 'POST',
            async: true, //默认异步调用 (false：同步)
            success: function (data) {
                if (data.code != 0) {
                    $(".mask").fadeOut();
                    $(".sellConfirm").fadeOut();
                    openTips(data.message);
                    return;
                }
                window.location.href = webAppPath + "/userWap/tradeCenter/show?currencyIdStr=" + currencyId;
            },
            error: function () {
                $(".mask").fadeOut();
                $(".sellConfirm").fadeOut();
                openTips("挂单失败,请重新刷新页面后重试");
                return ;
            }
        });
    },
    buyHandle: function () {
        var buyPrice = $("#buyPriceConfirm").val();
        var buyNum = $("#buyNumConfirm").val();
        var buyPwd = $("#buyPwdConfirm").val();
        var currencyId = $("#cucyId").val();
        var webAppPath = $("#webAppPath").val();

        document.getElementById("buyPrice").value = "";
        document.getElementById("buyNum").value = "";
        document.getElementById("buyPwd").value = "";
        $("#buyMax").html("最大可买: " + "0");
        $("#buyTotal").val("$0");
        $.ajax({
            url: webAppPath + "/userWap/tradeCenter/buy.htm", //方法路径URL
            data: {
                buyPrice: buyPrice,
                buyNum: buyNum,
                buyPwd: buyPwd,
                currencyId: currencyId
            },//参数
            dataType: 'json',
            type: 'POST',
            async: true, //默认异步调用 (false：同步)
            success: function (data) {
                if (data.code != "1") {
                    openTips(data.message);
                    $(".mask").fadeOut();
                    $(".buyConfirm").fadeOut();
                    return ;
                }
                window.location.href = webAppPath + "/userWap/tradeCenter/show?currencyIdStr=" + currencyId;
            }, error: function () {
                $(".mask").fadeOut();
                $(".buyConfirm").fadeOut();
                openTips("挂单失败,请重新刷新页面后重试");
                return;
            }
        });
    },
    toCancel: function () {
        var orderNum = $(this).children("input:hidden").val();
        $("#pendOrderNoCancle").val(orderNum);
        $(".mask_order").fadeIn();
        $(".orderConfirm").fadeIn();
    },
    cancleOrder: function () {
        var pendOrderNo = $("#pendOrderNoCancle").val();
        var webAppPath = $("#webAppPath").val();
        var currencyId = $("#cucyId").val();
        if (pendOrderNo === undefined || pendOrderNo === null || pendOrderNo == "") {
            $(".mask_order").fadeOut();
            $(".orderConfirm").fadeOut();
            openTips("单号错误");
            return;
        }
        $.ajax({
            url: webAppPath + "/userWap/transactionPendOrder/revoke.htm",
            data: {
                pendingOrderNo: pendOrderNo
            },//参数
            dataType: "json",
            type: 'POST',
            async: true, //默认异步调用 (false：同步)
            success: function (data) {
                if (data.code != "1") {
                    openTips(data.message);
                    $(".mask_order").fadeOut();
                    $(".orderConfirm").fadeOut();
                    return;
                }
                window.location.href = webAppPath + "/userWap/tradeCenter/show?currencyIdStr=" + currencyId;
            },
            error: function () {
                $(".mask_order").fadeOut();
                $(".orderConfirm").fadeOut();
                openTips("数据加载出错，请稍候重试");
            }
        });
    }
};
$().ready(function () {
    var currencyIdStr = $("#currencyIdStr").val()
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

    Handlebars.registerHelper("eachWithIndexFromOne", function (index) {
        return index + 1;
    });
    Handlebars.registerHelper("eachFortransactionCurrencyList", function (transactionUserDealList, standardParameter, webAppPath, options) {
        var out = "<ul>";
        for (var i = 0; i < transactionUserDealList.length; i++) {
            myHref = "<a href='" + webAppPath + "/userWap/tradeCenter/show?currencyIdStr=" + transactionUserDealList[i].currencyId + "'>";
            out = out + '<li>' +
                '<p>' + myHref + transactionUserDealList[i].currencyName + "(" + transactionUserDealList[i].currencyShortName + ")" + "</a></p>"
                + "<p class='zhang'>" + transactionUserDealList[i].latestPrice + "</p>"
                + "<p class='zhang'>" + transactionUserDealList[i].change + "%</p>"
                + "</li>";
        }
        out = out + '</ul>';
        return out;
    });
    Handlebars.registerHelper("paymentTypeFormat", function(type ,index){
        if(type == undefined || type == null || type == "" || isNaN(type)){
            return "未知类型";
        }
        if(type == 1){
            return "买入";
        }
        if(type == 2){
            return "卖出";
        }
        return "未知类型";
    });
    if (currencyIdStr === undefined || currencyIdStr === "") {
        openTips("该币种信息无法加载，请联系管理员");
        return;
    }
    $.ajax({
        url: 'getWapTradeCenterInfo',
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

            //加载完成后去绑定事件
            ParamsAndInit.tabChange();
            ParamsAndInit.open();
            $("#buyPrice").unbind("keyup");
            $("#buyPrice").bind("keyup", {num : 2}, ParamsAndInit.matchUtil);
            $("#buyNum").unbind("blur");
            $("#buyNum").bind("blur", {num : 4}, ParamsAndInit.matchUtil);
            $("#sellPrice").unbind("keyup");
            $("#sellPrice").bind("keyup", {num : 2}, ParamsAndInit.matchUtil);
            $("#sellNum").unbind("blur");
            $("#sellNum").bind("blur", {num : 4}, ParamsAndInit.matchUtil);
            $(".mainButtonBuy").bind('click', ParamsAndInit.toBuy);
            $(".mainButtonSell").bind('click', ParamsAndInit.toSell);
            $(".toCancleOrder").bind('click', ParamsAndInit.toCancel);
            $("#cancleOrder").each(function () {
                $(this).bind('click', ParamsAndInit.cancleOrder);
            });
            $(".cancel").bind('click',ParamsAndInit.cancleOpt);
            $("#buyHandler").unbind('click');
            $("#buyHandler").bind('click',ParamsAndInit.buyHandle);
            $("#sellHandler").unbind('click');
            $("#sellHandler").bind('click',ParamsAndInit.buyHandle);
        },
        error: function () {
            openTips("服务器异常，请稍后再试！")
        }
    });
});