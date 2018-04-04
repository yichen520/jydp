var ParamsAndInit = {
    registerHandler : function () {
        //方法注册函数加载 数字 分隔位
        Handlebars.registerHelper('formatNumber', function (num, maxFractionDigits, options) {
            if (isNaN(num) || isNaN(maxFractionDigits)) {
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
                myHref = "<a href='" + webAppPath + "/userWap/tradeCenter/show/"+ transactionUserDealList[i].currencyId + ">";
                out = out + '<li>' +
                    '<p>' + myHref + transactionUserDealList[i].currencyName + "(" + transactionUserDealList[i].currencyShortName + ")" + "</a></p>"
                    + "<p class='zhang'>" + transactionUserDealList[i].latestPrice + "</p>"
                    + "<p class='zhang'>" + transactionUserDealList[i].change + "%</p>"
                    + "</li>";
            }
            out = out + '</ul>';
            return out;
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
            if(userSession == undefined || userSession == null || userSession == ""){
                return "<a href='" + webAppPath + "/userWap/userLogin/show'>登录</a>";
            }else {
                return "";
            }
        });
        Handlebars.registerHelper("eachForTransactionPendOrderSellList", function (transactionPendOrderSellList) {
            var newChildSell = "";
            for(var i = transactionPendOrderSellList.length - 1; i >= 0; i--){
                newChildSell += '<span class="list-content">' +
                    '<span>卖' + (i + 1) + '</span>' +
                    '<span>' + ParamsAndInit.formatNumber(transactionPendOrderSellList[i].pendingPrice, 2) + '</span>' +
                    '<span>' + ParamsAndInit.formatNumber(transactionPendOrderSellList[i].restNumber, 4) + '</span>' +
                    '<span>' + ParamsAndInit.formatNumber(transactionPendOrderSellList[i].sumPrice, 6) + '</span>' +
                    '</span>';
            }
            return newChildSell;
        });
        Handlebars.registerHelper('formatNumberWithWan', function (num, maxFractionDigits, options) {
            if (isNaN(num) || isNaN(maxFractionDigits)) {
                return false;
            }
            num = num.toString();
            maxFractionDigits = parseInt(maxFractionDigits);
            if (num.indexOf(".") === -1) {
              /*  if("0" == num){
                    return num;
                }else{
                    return num +"万";
                }*/
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
         /*   if("0" == numStr){
                return numStr;
            }else{
                return numStr +"万";
            }*/
        });
    },
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
        var webAppPath = $("#webAppPath").val();
        var bgHeight = $(document).height();
        $('.open').on('click', function () {
            $.get( path + "/userWap/tradeCenter/currencyInfo",function (result) {
                if (result.code!=0) {
                    openTips(result.message)
                    return;
                }


                $('.closeAnthoer').css("height", bgHeight + "px");
                $('.choseBz').css("height", bgHeight + "px");
                $('.choseBzBox').css("height", '100%');
                $('.choseBzBox').show();
                $('.choseBzBox').animate({left: '0'}, "500");
                setTimeout(function () {
                    $('#wrapper').hide();
                    $('footer').hide();
                    //$('.choseBz').hide();
                }, 450);

                var webpath = $("#webAppPath").val();
                var myTemplate = Handlebars.compile($("#table-template").html());
                $('#currencyList').html(myTemplate(result.transactionUserDealList));

                $('.choseBzBox-content ul').on('click', 'li', function () {
                    var currencyId=$(this).find("p").eq(0).text();
                    window.location.href= webpath + "/userWap/tradeCenter/show/" + currencyId
                })
            })



            //加载数据
        });
        $('.closeBox').on('click', function () {
            $('#wrapper').show();
            $('footer').show();
            setTimeout(function () {
                $('.closeAnthoer').css("height", "0");
                $('.choseBz').css("height", "0");
                $('.choseBzBox').css("height", "0");
            }, 450);
            $('.choseBzBox').animate({left: '-100%'}, "500");
        });
    },
    seeting:function () {
        var bgHeight = $(document).height();
        $('.cin').css("height",bgHeight +"px");
        $('.setting').on('click',function() {
            $('.cin').fadeIn();
            //回显
            var payPasswordStatus = $("#payPasswordStatus").val();
            if( payPasswordStatus !=undefined && payPasswordStatus!= "" && !isNaN(payPasswordStatus) ){
                    $("input[name='remember']").each(function () {
                        if(payPasswordStatus == $(this).val()){
                            $(this).get(0).checked=true;
                        }
                    });
            }

        });
        $('.cancelSetting').on('click',function() {
            $('.cin').fadeOut();
            $("input[name='remember']").each(function () {
                $(this).attr("checked",false)
            });
        });
    },
    matchPassword:function () {
        var matchStr = /[^a-zA-Z0-9]/g;
        var value = $(this).val();
        if (matchStr.test(value)) {
            $(this).get(0).value=$(this).get(0).value.replace(/[^a-zA-Z0-9]/g,'');
        }
    },
    matchUtil: function (e) {

        var matchStr = /^-?\d+\.?\d{0,num}$/;
        //格式化显示值
        var value = $(this).val();
        if (!matchStr.test(value)) {
            if (isNaN(value)) {
                $(this).val('');
            } else {
                var a = value.indexOf('.');
                var b = a + e.data.num + 1;
                if (a > -1 && value.length > b) {
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
            try {
                m += buyPrice.split(".")[1].length
            } catch (e) {
            }
            if (buyNum != null && buyNum != "") {
                buyNum = buyNum.toString();
                try {
                    //拿到价格小数点后的数字
                    m += buyNum.split(".")[1].length
                } catch (e) {
                }
                //价格 * 数量 除以 (10 * 小数点的位数的8位浮点数)
                var number = parseFloat((Number(buyPrice.replace(".", "")) * Number(buyNum.replace(".", "")) / Math.pow(10, m)).toFixed(8));
                //设置购买总价格
                $("#buyTotal").val(number);
            }
            //可用美金
            var userBalance = parseFloat($("#userBalance").text());
            if (buyPrice > 0) {
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
            } else {
                //如果价格小于0就直接返回0
                $("#buyMax").html("最大可买: 0");
            }
        } else {
            //价格不正确直接返回
            $("#buyMax").html("最大可买: 0");
            $("#buyTotal").val("0");
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
            try {
                //获取小数点后长度
                s += sellPrice.split(".")[1].length;
            } catch (e) {
            }
            try {
                //获取小数点后长度 这个z的作用在哪里
                z += sellPrice.split(".")[1].length;
            } catch (e) {
            }
            //数量不等于空
            if (sellNum != null && sellNum != "") {
                //获取数量
                sellNum = sellNum.toString();
                try {
                    //获取数量小数点后长度 加上 价格小数点后长度
                    s += sellNum.split(".")[1].length
                } catch (e) {
                }
                //保证小数位为8位
                var number = parseFloat((Number(sellPrice.replace(".", "")) * Number(sellNum.replace(".", "")) / Math.pow(10, s)).toFixed(8));
                //格式化
                number = ParamsAndInit.mulMaxNumber(number);
                $("#sellTotal").val(number);
            }

            //可用币
            var currencyNumber = $("#currencyNumber").val();
            if (currencyNumber != null && currencyNumber != "") {
                currencyNumber = currencyNumber.toString();
                try {
                    //出售价格小数点后位 + 出售数量小数点后尾数
                    z += currencyNumber.split(".")[1].length
                } catch (e) {
                }
                var number = parseFloat((Number(sellPrice.replace(".", "")) * Number(currencyNumber.replace(".", "")) / Math.pow(10, z)).toFixed(8));
                //格式化
                number = ParamsAndInit.mulMaxNumber(number);
                $("#sellMax").html("最大可获得: "  + number + " XT");
            }
        } else {
            //价格不正确
            $("#sellMax").html("最大可获得: " + "0 XT");
            $("#sellTotal").val("0");
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
    mask:function () {
        var bgHeight = $(document).height();
        $('.mask').css("height",bgHeight +"px");
        $('.maskSell').css("height",bgHeight +"px");

        $('.cancelmask').on('click',function() {
            $('.mask').fadeOut();
        });

        $('.cancelmaskSell').on('click',function() {
            $('.maskSell').fadeOut();
        });
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
        $("#buyMax").html("最大可买: 0");
        $buyTotal.val("0");

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
        $("#buyPriceTips").html(buyPrice + " XT");
        $("#buyNumTips").html(buyNum);
        $("#buySumTips").html(buyTotal + " XT");
        $('.mask').fadeIn();
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
        $("#sellMax").html("最大可获得: 0 XT");
        $sellTotal.val("0");

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
        $("#sellPriceTips").html(sellPrice + " XT");
        $("#sellNumTips").html(sellNum);
        $("#sellSumTips").html(sellTotal + " XT");
        $('.maskSell').fadeIn();
    },
    sellHandle: function () {
        $('.maskSell').hide();
        var sellPrice = $("#sellPriceConfirm").val();
        var sellNum = $("#sellNumConfirm").val();
        var sellPwd = $("#sellPwdConfirm").val();
        var currencyId = $("#cucyId").val();
        var webAppPath = $("#webAppPath").val();
        document.getElementById("buyPrice").value = "";
        document.getElementById("buyNum").value = "";
        document.getElementById("buyPwd").value = "";
        $("#sellMax").html("最大可获得: " + "0 XT");
        $("#sellTotal").val("0");
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
                if (data.code != 1) {
                    openTips(data.message);
                    return;
                }
                openTips(data.message);
                var isPwd = data.userIsPwd;
                $("#userIsPwd").val(isPwd);
                //去刷新委托
                ParamsAndInit.entrust();
            },
            error: function () {
                openTips("挂单失败,请重新刷新页面后重试");
                return;
            }
        });
    },
    buyHandle: function () {
        $('.mask').hide();
        var buyPrice = $("#buyPriceConfirm").val();
        var buyNum = $("#buyNumConfirm").val();
        var buyPwd = $("#buyPwdConfirm").val();
        var currencyId = $("#cucyId").val();
        var webAppPath = $("#webAppPath").val();

        document.getElementById("buyPrice").value = "";
        document.getElementById("buyNum").value = "";
        document.getElementById("buyPwd").value = "";
        $("#buyMax").html("最大可买: 0");
        $("#buyTotal").val("0");
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
                    return;
                }
                openTips(data.message);
                //去刷新委托
                ParamsAndInit.entrust();
                var isPwd = data.userIsPwd;
                $("#userIsPwd").val(isPwd);
            }, error: function () {
                openTips("挂单失败,请重新刷新页面后重试");
                return;
            }
        });
    },
    toCancel: function () {
        var bgHeight = $(document).height();
        $('.bg').css("height",bgHeight +"px");
        $('.showBox').css("display","block");
        $('.showBox').animate({opacity:'1'},"1000");

        var orderNum = $(this).children("input:hidden").val();
        $("#pendOrderNoCancle").val(orderNum);
    },
    cancleOrder: function () {
        $('.bg').css("height","0");
        $('.showBox').animate({opacity:'0'},"100");
        setTimeout(function(){
            $('.showBox').css('display','none');
        },100)
        var pendOrderNo = $("#pendOrderNoCancle").val();
        var webAppPath = $("#webAppPath").val();
        var currencyId = $("#cucyId").val();
        if (pendOrderNo === undefined || pendOrderNo === null || pendOrderNo == "") {
            openTips("单号错误");
            return;
        }
        $.ajax({
            url: webAppPath + "/userWap/wapTransactionPendOrderController/revokeForDeal.htm",
            data: {
                //参数
                pendingOrderNo: pendOrderNo
            },
            dataType: "json",
            type: 'POST',
            async: true, //默认异步调用 (false：同步)
            success: function (data) {
                if (data.code != "0") {
                    openTips(data.message);
                    return;
                }
                //如果cancel成功
                openTips(data.message);
                //去重新加载数据
                ParamsAndInit.entrust();
            },
            error: function () {
                openTips("数据加载出错，请稍候重试");
            }
        });
    },
    entrust: function () {
        //获取currency数据
        var currencyIdStr = $("#currencyId").val();
        if (undefined == currencyIdStr || currencyIdStr == null || currencyIdStr == "") {
            openTips("页面数据错误，请重新刷新页面");
            return;
        }
        var userSession = $("#userSession").val();
        if (userSession == undefined || userSession == null || userSession == "") {
            return;
        }

        $.ajax({
            url: path + '/userWap/tradeCenter/entrust.htm',
            type: 'POST',
            dataType: 'json',
            async: true,
            data: {
                "currencyIdStr": currencyIdStr
            },
            success: function (data) {
                if (data.code != 0) {
                    return;
                }
                //删除元素 新增委托数量
                $(".entrust .entrust-content").empty();
                data = data.transactionPendOrderList;
                var str = "";
                for (i in data) {
                    var paymentType =  data[i].paymentType == 1 ? "<span class='red'>买入</span>" : "<span class='green'>卖出</span>"
                    str += "<li>"
                            + "<p>" + paymentType +"</p>"
                            + "<p>" + ParamsAndInit.formatNumber(data[i].pendingPrice, 2) + "</p>"
                            + "<p>" + ParamsAndInit.formatNumber(data[i].pendingNumber, 4) +"</p>"
                            +"<p>" +  ParamsAndInit.formatNumber(data[i].dealNumber, 4) + "</p>"
                            +"<p class='toCancleOrder'>撤销"  + "<input type='hidden' value='"+ data[i].pendingOrderNo +"'/>"  + "</p>"
                            +"<p class='clear'></p>"+"</li>";
                }
                $("#entrustUL").html(str);
                $(".toCancleOrder").each(function () {
                    $(this).bind('click', ParamsAndInit.toCancel);
                });
            },
            error: function () {
                return;
            }
        });
    },
    //用户相关信息
    userInfo: function () {
        var userSession = $("#userSession").val();
        var currencyId = $("#currencyId").val();
        var userSession = $("#userSession").val();
        if (userSession == undefined || userSession == null || userSession == "") {
            return;
        }
        if (undefined == currencyId || currencyId == null || currencyId == "") {
            return;
        }
        $.ajax({
            url: path + "/userWap/tradeCenter/userInfo.htm",
            data: {
                currencyId : currencyId
            },//参数
            dataType: "json",
            type: 'POST',
            async: true, //默认异步调用 (false：同步)
            success: function (data) {
                if (data.code != 0) {
                    return;
                }
                var userDealCapitalMessage = data.userDealCapitalMessage;
                if(userDealCapitalMessage != null){
                    $("#currencyNumberShow").html(ParamsAndInit.formatNumber(userDealCapitalMessage.currencyNumber, 6));
                    $("#currencyNumber").val(ParamsAndInit.formatNumber(userDealCapitalMessage.currencyNumber, 4));
                    $("#currencyNumberLockShow").html(ParamsAndInit.formatNumber(userDealCapitalMessage.currencyNumberLock, 6));

                    $("#userBalance").html(ParamsAndInit.formatNumber(userDealCapitalMessage.userBalance, 6));
                    $("#userBalanceLockShow").html(ParamsAndInit.formatNumber(userDealCapitalMessage.userBalanceLock, 6));
                    $("#currencyNumberSumShow").html(ParamsAndInit.formatNumber(userDealCapitalMessage.currencyNumberSum, 6));
                }
            },
            error: function () {
            }
        });
    },
    pendOrder: function () {
        var currencyId = $("#currencyId").val();
        if (undefined == currencyId || currencyId == null || currencyId == "") {
            return;
        }
        $.ajax({
            url: path + "/userWap/tradeCenter/pend", //方法路径URL
            data:{
                currencyId : currencyId
            },
            dataType: 'json',
            type: 'POST',
            async: true, //默认异步调用 (false：同步)
            success: function (data) {
                if (data.code != 0) {
                    return;
                }
                var orderSellList = data.transactionPendOrderSellList; //卖出
                var orderBuyList = data.transactionPendOrderBuyList;  //买入

                $(".leftContent").empty();
                var newChildSell = "";
                for(var i = orderSellList.length - 1; i >= 0; i--){
                    newChildSell += '<span class="list-content">' +
                            '<span>卖' + (i + 1) + '</span>' +
                            '<span>' + ParamsAndInit.formatNumber(orderSellList[i].pendingPrice, 2) + '</span>' +
                            '<span>' + ParamsAndInit.formatNumber(orderSellList[i].restNumber, 4) + '</span>' +
                            '<span>' + ParamsAndInit.formatNumber(orderSellList[i].sumPrice, 6) + '</span>' +
                            '</span>';
                }
                $(".leftContent").html(newChildSell);

                $(".rightContent").empty();
                var newChildBuy= "";
                for (var i=0; i <= orderBuyList.length - 1; i++) {
                    newChildBuy += '<span class="list-content">' +
                        '<span>买' + (i + 1) + '</span>' +
                        '<span>' + ParamsAndInit.formatNumber(orderBuyList[i].pendingPrice, 2) + '</span>' +
                        '<span>' + ParamsAndInit.formatNumber(orderBuyList[i].restNumber, 4) + '</span>' +
                        '<span>' + ParamsAndInit.formatNumber(orderBuyList[i].sumPrice, 6) + '</span>' +
                        '</span>';
                }
                $(".rightContent").html(newChildBuy);
            },
            error: function () {
                return ;
            }
        });
    },
    dealInfo: function () {
        var currencyId = $("#currencyId").val();
        if (undefined == currencyId || currencyId == null || currencyId == "") {
            return;
        }
        $.ajax({
            url:path + "/userWap/tradeCenter/gainDealPrice",
            data: {
                currencyId : currencyId
            },//参数
            dataType: "json",
            type: 'POST',
            async: true, //默认异步调用 (false：同步)
            success:function (data) {
                if (data.code != 0) {
                    return;
                }
                var standardParameter = data.standardParameter;
                if(standardParameter != null){
                    $(".topLeft").text(ParamsAndInit.formatNumber(standardParameter.nowPrice, 8));
                    $("#todayMaxSpan").text(ParamsAndInit.formatNumber(standardParameter.todayMax, 6));
                    $("#todayMinSpan").text(ParamsAndInit.formatNumber(standardParameter.todayMin, 6));
                    $("#buyOneSpan").text(ParamsAndInit.formatNumber(standardParameter.buyOne, 6));
                    $("#sellOneOne").text(ParamsAndInit.formatNumber(standardParameter.sellOne, 6));
                    var dayTurnove =  ParamsAndInit.formatNumber(standardParameter.dayTurnove, 4);
                    if(dayTurnove == 0){
                        $("#dayTurnoveOne").text(dayTurnove);
                    }else {
                        $("#dayTurnoveOne").text(dayTurnove);
                    }

                    $("#nowPriceDiv").text("当前价格："+ParamsAndInit.formatNumber(standardParameter.nowPrice, 8)+" XT");
                }
            },
            error: function () {
                return;
            }
        });
    },
    reloadData:function () {
        var currencyId = $("#currencyId").val();
        if (undefined == currencyId || currencyId == null || currencyId == "") {
            return;
        }
        var userSession = $("#userSession").val();
        if(userSession != undefined && userSession != null && userSession != ""){
                 window.setInterval(ParamsAndInit.userInfo, 5000);
                 window.setInterval(ParamsAndInit.entrust, 5000);
        }
        window.setInterval(ParamsAndInit.pendOrder, 5000);
        window.setInterval(ParamsAndInit.dealInfo, 5000);
    },
    updatePayPwd :function () {
        var payPasswordStatus = $("input[name='remember']:checked").val();
        var rememberPwd = $("#rememberPwd").val();

        var user = $("#userSession").val();
        if (user == undefined || user == null || user == "") {
            openTips("请先登录再操作");
            return;
        }
        if(payPasswordStatus != 1 && payPasswordStatus != 2){
            openTips("参数错误，请刷新页面重试");
            return;
        }
        if(rememberPwd == undefined|| rememberPwd == null || rememberPwd == "" ){
            openTips("交易密码不能为空");
            return;
        }

        $.ajax({
            url: path + "/userWap/tradeCenter/rememberPwd.htm", //方法路径URL
            data:{
                rememberPwd : rememberPwd,
                payPasswordStatus : payPasswordStatus
            },
            dataType: 'json',
            type: 'POST',
            async: true, //默认异步调用 (false：同步)
            success: function (data) {
                if (data.code != 0) {
                    openTips(data.message);
                    return;
                }
                var isPwd = data.userIsPwd;
                $("#userIsPwd").val(isPwd);
                $("#payPasswordStatus").val(payPasswordStatus);
                $("#rememberPwd").val("");
                if(payPasswordStatus == 1){
                    $("#bMaxNum").text("当前设置: 每笔交易都输入密码");
                    $("#sMaxNum").text("当前设置: 每笔交易都输入密码");
                }
                if(payPasswordStatus == 2){
                    $("#bMaxNum").text("当前设置: 每次登录只输入一次密码");
                    $("#sMaxNum").text("当前设置: 每次登录只输入一次密码");
                }
                $('.cin').fadeOut();
                openTips(data.message);
                $("input[name='remember']").each(function () {
                    $(this).attr("checked",false)
                });
            }, error: function () {
                $('.cin').fadeOut();
                openTips("修改失败,请重新刷新页面后重试");
                $("input[name='remember']").each(function () {
                    $(this).attr("checked",false)
                });
                return ;
            }
        });
    },
    formatNumber: function (num, maxFractionDigits) {
        if (isNaN(num) || isNaN(maxFractionDigits)) {
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
    toChartPage:function () {
        var currencyId = $("#currencyId").val();
        var webAppPath = $("#webAppPath").val();
        if (currencyId == undefined || currencyId == null || currencyId == "") {
            openTips("页面数据错误，请刷新页面！");
            return;
        }
        window.location.href = webAppPath + '/userWap/tradeCenter/toChartPage/' + currencyId;
    },
    footInit: function () {
        var h=$(window).height();
        $("input").focus(function(){
            $("header").css({"position":"relative","top":0});
            $("section").css({"position":"relative","top":'0'});
            $("#wrapper").css({"position":"relative","top":'0'});
            $("footer").hide();
        });
        $("input").blur(function(){
            $("header").css("position","fixed");
            $("section").css({"position":"fixed","top":'0.92rem'});

            $("#wrapper").css({"position":"relative","top":'2.3rem'});
            $('footer').show();
        });
        //进入页面的时候判断
        var payPasswordStatus = $("#payPasswordStatus").val();
        if(payPasswordStatus == 2){
            $("#bMaxNum").text("当前设置: 每次登录只输入一次密码");
            $("#sMaxNum").text("当前设置: 每次登录只输入一次密码");
        }
        $('.cancelShow').on('click',function() {
            $('.bg').css("height","0");
            $('.showBox').animate({opacity:'0'},"100");
            setTimeout(function(){
                $('.showBox').css('display','none');
            },100)
        });
    }
};
$().ready(function () {
    //方法注册
    ParamsAndInit.registerHandler();
    var currencyIdStr = $("#currencyIdStr").val()
    if (currencyIdStr === undefined || isNaN(currencyIdStr)) {
        currencyIdStr = "";
    }
    $.ajax({
        url: path + '/userWap/tradeCenter/getWapTradeCenterInfo',
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
            $("#buyPrice").bind("keyup", {num: 2}, ParamsAndInit.matchUtil);
            $("#buyPrice").unbind("blur");
            $("#buyPrice").bind("blur", {num: 2}, ParamsAndInit.matchUtil);
            $("#buyNum").unbind("keyup");
            $("#buyNum").bind("keyup", {num: 4}, ParamsAndInit.matchUtil);
            $("#buyNum").unbind("blur");
            $("#buyNum").bind("blur", {num: 4}, ParamsAndInit.matchUtil);
            $("#sellPrice").unbind("keyup");
            $("#sellPrice").bind("keyup", {num: 2}, ParamsAndInit.matchUtil);
            $("#sellPrice").unbind("blur");
            $("#sellPrice").bind("blur", {num: 2}, ParamsAndInit.matchUtil);
            $("#sellNum").unbind("blur");
            $("#sellNum").bind("blur", {num: 4}, ParamsAndInit.matchUtil);
            $("#sellNum").unbind("keyup");
            $("#sellNum").bind("keyup", {num: 4}, ParamsAndInit.matchUtil);
            $("#buyPwd").bind("keyup",ParamsAndInit.matchPassword);
            $("#buyPwd").bind("blur",ParamsAndInit.matchPassword);
            $("#sellPwd").bind("keyup",ParamsAndInit.matchPassword);
            $("#sellPwd").bind("blur",ParamsAndInit.matchPassword);
            $(".mainButtonBuy").bind('click', ParamsAndInit.toBuy);
            $(".mainButtonSell").bind('click', ParamsAndInit.toSell);
            $(".toCancleOrder").each(function () {
                $(this).bind('click', ParamsAndInit.toCancel);
            });
            $("#cancleOrder").bind('click', ParamsAndInit.cancleOrder);
            $(".cancelShow").bind('click', ParamsAndInit.cancleOpt);
            $("#buyHandler").unbind('click');
            $("#buyHandler").bind('click', ParamsAndInit.buyHandle);
            $("#sellHandler").unbind('click');
            $("#sellHandler").bind('click', ParamsAndInit.sellHandle);
            $(".okaySetting").bind('click', ParamsAndInit.updatePayPwd);
            $(".topRight").bind('click', ParamsAndInit.toChartPage);
            ParamsAndInit.reloadData();
            ParamsAndInit.seeting();
            ParamsAndInit.mask();
            ParamsAndInit.footInit();
        },
        error: function () {
            openTips("服务器异常，请稍后再试！")
        }
    });
});