<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/resources/page/common/path.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>


<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <link rel="icon" href="<%=path %>/resources/image/web/icon.ico" type="image/x-ico" />

    <link rel="stylesheet" type="text/css" href="<%=path %>/resources/css/web/tradeCenter.css" />
    <link rel="stylesheet" type="text/css" href="<%=path %>/resources/css/web/public.css" />
    <link rel="stylesheet" type="text/css" href="<%=path %>/resources/css/web/simpleTips.css" />

    <title>交易大盘</title>
</head>
<body>
<header id="header"></header>
<div id="menu"></div>


<div class="content">
    <div class="top">
        <p class="coin">
            <img src="${transactionCurrency.currencyImgUrl }" class="coinLogo" /><br>
            <span class="coinName">${transactionCurrency.currencyName }(${transactionCurrency.currencyShortName }/USD)</span>
        </p>

        <div class="coinInfo">
            <span class="priceNow">${standardParameter.nowPrice}</span>

            <p class="increase">
                <c:if test="${standardParameter.todayRange >= 0}">
                    <span class="number rise">+${standardParameter.todayRange }%</span>
                </c:if>
                <c:if test="${standardParameter.todayRange < 0}">
                    <span class="number fall">${standardParameter.todayRange }%</span>
                </c:if>
                <span class="infoName">今日涨跌</span>
            </p>
            <p class="info">
                <span class="number rise">${standardParameter.todayMax }</span>
                <span class="infoName">今日最高价</span>
            </p>
            <p class="info">
                <span class="number fall">${standardParameter.todayMin }</span>
                <span class="infoName">今日最低价</span>
            </p>
            <p class="info">
                <span class="number price">${standardParameter.buyOne }</span>
                <span class="infoName">买一价</span>
            </p>
            <p class="info">
                <span class="number price">${standardParameter.sellOne }</span>
                <span class="infoName">卖一价</span>
            </p>
            <p class="info">
                <span class="number price">${standardParameter.dayTurnove }</span>
                <span class="infoName">24小时成交量</span>
            </p>
        </div>
    </div>

    <div class="left">
        <div class="charts">蜡烛图</div>

        <div class="tradeArea">
            <div class="buy">
                <p class="buyTitle">买入</p>

                <p class="buyInput">
                    <label class="tradeName">可用美金：</label>
                    <span class="buyAmount rise">$${userDealCapitalMessage.userBalance }</span>
                </p>
                <p class="buyInput">
                    <label class="tradeName">买入价格：</label>
                    <input type="text" class="entry" placeholder="请输入单个币买入价" id="buyPrice" name="buyPrice"
                           onkeyup="matchUtil(this, 'double', 2)" onblur="matchUtil(this, 'double', 2)" maxlength="18"/>
                </p>
                <p class="buyInput">
                    <label class="tradeName">买入数量：</label>
                    <input type="text" class="entry" placeholder="请输入您要买入的数量" id="buyNum" name="buyNum"
                           onkeyup="matchUtil(this, 'double', 6)" onblur="matchUtil(this, 'double', 6)" maxlength="18"/>
                    <span class="max">当前最大可买：<span id="buyMax">0</span></span>
                </p>
                <p class="phoneInput">
                    <label class="tradeName">支付密码：</label>
                    <input type="password" class="entry" placeholder="您的支付密码" id="buyPwd" name="buyPwd" maxlength="16"
                           onkeyup="value=value.replace(/[^a-zA-Z0-9]/g,'')" onblur="value=value.replace(/[^a-zA-Z0-9]/g,'')"/>
                </p>
                <p class="buyInput">
                    <label class="tradeName">合计：</label>
                    <span class="all" id="buyTotal">0$</span>
                </p>
                <p class="serviceInput">
                    <label class="tradeName">手续费：</label>
                    <span class="service">${transactionCurrency.buyFee }%</span>
                </p>

                <input type="text" class="buyBtn" value="买&nbsp;入" onfocus="this.blur()" onclick="buyHandle();"/>
            </div>

            <div class="sell">
                <p class="sellTitle">卖出</p>

                <p class="buyInput">
                    <label class="tradeName">可用数量：</label>
                    <span class="sellAmount fall">${userDealCapitalMessage.currencyNumber }</span>
                </p>
                <p class="buyInput">
                    <label class="tradeName">卖出价格：</label>
                    <input type="text" class="entry" placeholder="请输入单个币的买入价" id="sellPrice" name="sellPrice"
                           onkeyup="matchUtil(this, 'double', 2)" onblur="matchUtil(this, 'double', 2)" maxlength="18"/>
                </p>
                <p class="buyInput">
                    <label class="tradeName">卖出数量：</label>
                    <input type="text" class="entry" placeholder="请输入您要买入的该币种数量" id="sellNum" name="sellNum"
                           onkeyup="matchUtil(this, 'double', 6)" onblur="matchUtil(this, 'double', 6)" maxlength="18"/>
                    <span class="max">当前最大可卖：<span id="sellMax">0</span></span>
                </p>
                <p class="phoneInput">
                    <label class="tradeName">支付密码：</label>
                    <input type="password" class="entry" placeholder="您的支付密码" id="sellPwd" name="sellPwd" maxlength="16"
                           onkeyup="value=value.replace(/[^a-zA-Z0-9]/g,'')" onblur="value=value.replace(/[^a-zA-Z0-9]/g,'')"/>
                </p>
                <p class="buyInput">
                    <label class="tradeName">合计：</label>
                    <span class="all" id="sellTotal">0$</span>
                </p>
                <p class="serviceInput">
                    <label class="tradeName">手续费：</label>
                    <span class="service">${transactionCurrency.sellFee }%</span>
                </p>

                <input type="text" class="sellBtn" value="卖&nbsp;出" onfocus="this.blur()" onclick="sellHandle();"/>
            </div>
        </div>
    </div>

    <div class="right">
        <ul class="account">
            <li class="accountList">
                <span class="listName">可用${transactionCurrency.currencyName }</span>
                <span class="listMoney rise">${userDealCapitalMessage.currencyNumber }</span>
                <input type="hidden" id="currencyNumber" value="${userDealCapitalMessage.currencyNumber }">
            </li>
            <li class="accountList">
                <span class="listName">冻结${transactionCurrency.currencyName }</span>
                <span class="listMoney fall">${userDealCapitalMessage.currencyNumberLock }</span>
            </li>
            <li class="accountList">
                <span class="listName">可用美金</span>
                <span class="listMoney rise">$${userDealCapitalMessage.userBalance }</span>
                <input type="hidden" id="userBalance" value="${userDealCapitalMessage.userBalance }">
            </li>
            <li class="accountList">
                <span class="listName">冻结美金</span>
                <span class="listMoney fall">$${userDealCapitalMessage.userBalanceLock }</span>
            </li>
            <li class="accountList">
                <span class="listName">账户总资产</span>
                <span class="listMoney fall">$${userDealCapitalMessage.currencyNumberSum }</span>
            </li>
        </ul>

        <div class="entrust">
            <p class="entrustTop">
                <span class="dealPrice">最新成交价：$${standardParameter.nowPrice}</span>
                <span class="count"><span id="countNum"></span>s后刷新</span>
            </p>

            <div class="record">
                <p class="recordChoose">
                    <span class="allRecord chooseStyle">查看全部</span>
                    <span class="onlyBuy">只看买入</span>
                    <span class="onlySell">只看卖出</span>
                </p>

                <div class="entrustRange">
                    <p class="rangeName">
                        <span class="rangeType">买/卖</span>
                        <span class="rangePrice">价格</span>
                        <span class="rangeNum">数量</span>
                        <span class="rangeAmount">总额</span>
                    </p>

                    <div class="scroll">
                        <ul class="sellRecord fall">
                            <c:forEach items="${transactionPendOrderSellList}" var="item" varStatus="status">
                                <li class="recordInfo">
                                    <span class="rangeType">卖${fn:length(transactionPendOrderSellList) - status.index}</span>
                                    <span class="rangePrice"><fmt:formatNumber type="number" value="${item.pendingPrice}" maxFractionDigits="4"/></span>
                                    <span class="rangeNum"><fmt:formatNumber type="number" value="${item.restNumber}" maxFractionDigits="2"/></span>
                                    <span class="rangeAmount"><fmt:formatNumber type="number" value="${item.sumPrice}" maxFractionDigits="6"/></span>
                                </li>
                            </c:forEach>
                        </ul>

                        <ul class="buyRecord rise">
                            <c:forEach items="${transactionPendOrderBuyList}" var="item" varStatus="status">
                                <li class="recordInfo">
                                    <span class="rangeType">买${status.count}</span>
                                    <span class="rangePrice"><fmt:formatNumber type="number" value="${item.pendingPrice}" maxFractionDigits="4"/></span>
                                    <span class="rangeNum"><fmt:formatNumber type="number" value="${item.restNumber}" maxFractionDigits="2"/></span>
                                    <span class="rangeAmount"><fmt:formatNumber type="number" value="${item.sumPrice}" maxFractionDigits="6"/></span>
                                </li>
                            </c:forEach>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <c:if test="${userSession != null}">
        <div class="myEntrust">
            <p class="myTitle">
                我的委托记录<img src="<%=path %>/resources/image/web/entrust.png" />
                <a href="<%=path%>/userWeb/transactionPendOrderController/show.htm" class="more">查看更多</a>
            </p>
            <table class="table" cellspacing="0 " cellpadding="0" id="entrustRecord">
                <tr class="tableTitle">
                    <td class="time">委托时间</td>
                    <td class="type">类型</td>
                    <td class="amount">委托价格</td>
                    <td class="amount">委托数量</td>
                    <td class="amount">委托总价</td>
                    <td class="operate">操作</td>
                </tr>
                <c:forEach items="${transactionPendOrderList}" var="item">
                    <tr class="tableInfo">
                        <td class="time"><fmt:formatDate type="time" value="${item.addTime}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
                        <c:if test="${item.paymentType == 1}">
                            <td class="type rise">买入</td>
                        </c:if>
                        <c:if test="${item.paymentType == 2}">
                            <td class="type fall">卖出</td>
                        </c:if>
                        <td class="amount">$<fmt:formatNumber type="number" value="${item.pendingPrice}" maxFractionDigits="4"/></td>
                        <td class="amount"><fmt:formatNumber type="number" value="${item.pendingNumber}" maxFractionDigits="2"/></td>
                        <td class="amount rise">$<fmt:formatNumber type="number" value="${item.pendingPrice * item.pendingNumber}" maxFractionDigits="6"/></td>
                        <td class="operate"><input type="text" value="撤&nbsp;销" class="revoke" onclick="goCancle('${item.pendingOrderNo}')" /></td>
                    </tr>
                </c:forEach>
            </table>
        </div>
    </c:if>

    <div class="myDeal">
        <p class="myTitle">
            最近成交记录<img src="<%=path %>/resources/image/web/deal.png" />
            <a href="#" class="more">我的成交记录</a>
        </p>

        <p class="tableTitle">
            <span class="dealTime">成交时间</span>
            <span class="type">类型</span>
            <span class="dealAmount">委托价格</span>
            <span class="dealAmount">委托数量</span>
            <span class="dealAmount">委托总价</span>
        </p>

        <div class="tableScroll">
            <table class="table" cellspacing="0 " cellpadding="0" id="dealOrder">
                <c:forEach items="${dealList}" var="item">
                    <tr class="tableInfo">
                        <td class="dealTime"><fmt:formatDate type="time" value="${item.addTime}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
                        <c:if test="${item.paymentType == 1}">
                            <td class="type rise">买入</td>
                        </c:if>
                        <c:if test="${item.paymentType == 2}">
                            <td class="type fall">卖出</td>
                        </c:if>
                        <td class="dealAmount">$<fmt:formatNumber type="number" value="${item.transactionPrice}" maxFractionDigits="4"/></td>
                        <td class="dealAmount"><fmt:formatNumber type="number" value="${item.currencyNumber}" maxFractionDigits="2"/></td>
                        <td class="dealAmount rise">$<fmt:formatNumber type="number" value="${item.currencyTotalPrice}" maxFractionDigits="6"/></td>
                    </tr>
                </c:forEach>
            </table>
        </div>


        <input type="hidden" id="cucyId" name="cucyId" value="${transactionCurrency.currencyId}">
    </div>
</div>


<div id="helpFooter"></div>
<div id="footer"></div>

<script type="text/javascript" src="http://libs.baidu.com/jquery/2.1.4/jquery.min.js"></script>
<script type="text/javascript" src="<%=path %>/resources/js/loadPageWeb.js"></script>
<script type="text/javascript" src="<%=path %>/resources/js/simpleTips.js"></script>
<script type="text/javascript">
    window.onload = function() {
        var code = '${code}';
        var message = '${message}';
        if (code != 1 && message != "") {
            openTips(message);
            return false;
        }

    }

    var resultBoo = false;
    function buyHandle() {
        if(resultBoo){
            return false;
        }else{
            resultBoo = true;
        }

        var buyPrice = $("#buyPrice").val();
        var buyNum = $("#buyNum").val();
        var buyPwd = $("#buyPwd").val();

        $.ajax({
            url: '<%=path%>' + "/userWeb/tradeCenter/buy.htm", //方法路径URL
            data:{
                buyPrice : buyPrice,
                buyNum : buyNum,
                buyPwd : buyPwd,
                currencyId : 1
            },//参数
            dataType: 'json',
            type: 'POST',
            async: true, //默认异步调用 (false：同步)
            success: function (result) {
                if(result.code == 1) {
                    openTips(result.message);
                } else {
                    openTips(result.message);
                }
                resultBoo = false;
            }, error: function () {
                resultBoo = false;
                openTips("挂单失败,请重新刷新页面后重试");
            }
        });
    }

    function sellHandle() {
        if(resultBoo){
            return false;
        }else{
            resultBoo = true;
        }

        var sellPrice = $("#sellPrice").val();
        var sellNum = $("#sellNum").val();
        var sellPwd = $("#sellPwd").val();

        $.ajax({
            url: '<%=path%>' + "/userWeb/tradeCenter/sell.htm", //方法路径URL
            data:{
                sellPrice : sellPrice,
                sellNum : sellNum,
                sellPwd : sellPwd,
                currencyId : 1
            },//参数
            dataType: 'json',
            type: 'POST',
            async: true, //默认异步调用 (false：同步)
            success: function (result) {
                if(result.code == 1) {
                    openTips(result.message);
                } else {
                    openTips(result.message);
                }
                resultBoo = false;
            }, error: function () {
                resultBoo = false;
                openTips("挂单失败,请重新刷新页面后重试");
            }
        });
    }

    var mapMatch = {};
    mapMatch['number'] = /[^\d]/g;
    mapMatch['ENumber'] = /[^\a-\z\A-\Z\d]/g;
    mapMatch['double'] = true;
    mapMatch['phone'] = /[^\d]/g;
    mapMatch['email'] = /([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\.[a-zA-Z0-9_-]{2,3}){1,2})$/;
    function matchUtil(o, str, nu) {
        mapMatch[str] === true ? matchDouble(o, nu) : o.value = o.value.replace(mapMatch[str], '');
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
        var buyPrice = $("#buyPrice").val();
        var buyNum = $("#buyNum").val();

        //买入
        if (buyPrice != null && buyPrice != "") {
            buyPrice = buyPrice.toString();
            try{m+=buyPrice.split(".")[1].length}catch(e){}
            if (buyNum != null && buyNum != "") {
                buyNum = buyNum.toString();
                try{m+=buyNum.split(".")[1].length}catch(e){}
                var number = parseFloat((Number(buyPrice.replace(".",""))*Number(buyNum.replace(".",""))/Math.pow(10,m)).toFixed(8));
                number = mulMaxNumber(number);
                $("#buyTotal").html("$" + number);
            }

            var userBalance = parseFloat($("#userBalance").val());
            var total = userBalance / buyPrice;
            var tota = mulMaxNumber(total);
            $("#buyMax").html(tota);
        } else {
        $("#buyMax").html("");
            $("#buyTotal").html("");
        }


        //卖出
        var s = 0;
        var sellPrice = $("#sellPrice").val();
        var sellNum = $("#sellNum").val();
        if (sellPrice != null && sellPrice != "") {
            sellPrice = sellPrice.toString();
            try{s+=sellPrice.split(".")[1].length}catch(e){}
            if (sellNum != null && sellNum != "") {
                sellNum = sellNum.toString();
                try{s+=sellNum.split(".")[1].length}catch(e){}
                var number = parseFloat((Number(sellPrice.replace(".",""))*Number(sellNum.replace(".",""))/Math.pow(10,s)).toFixed(8));
                number = mulMaxNumber(number);
                $("#sellTotal").html(number);
            }

            var currencyNumber = $("#currencyNumber").val();
            if(currencyNumber != null && currencyNumber != ""){
                currencyNumber = currencyNumber.toString();
                try{s+=currencyNumber.split(".")[1].length}catch(e){}
                var number = parseFloat((Number(sellPrice.replace(".",""))*Number(currencyNumber.replace(".",""))/Math.pow(10,s)).toFixed(8));
                number = mulMaxNumber(number);
                $("#sellMax").html(number);
            }
        } else {
            $("#sellMax").html("");
            $("#sellTotal").html("");
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
</script>
<script type="text/javascript">
    $(function(){
        $(".recordChoose span").click(function(){
            $(".recordChoose span").removeClass("chooseStyle");
            $(this).addClass("chooseStyle")
        })
    });

    $(function(){
        $(".allRecord").click(function(){
            $(".sellRecord").show();
            $(".buyRecord").show();
        });
        $(".onlyBuy").click(function(){
            $(".sellRecord").hide();
            $(".buyRecord").show();
        });
        $(".onlySell").click(function(){
            $(".sellRecord").show();
            $(".buyRecord").hide();
        })
    });

    var start = 5;
    var step = -1;
    function count()
    {
        document.getElementById("countNum").innerHTML = start;
        if (start <= 0) {
            //刷新成交记录
            reDeal();
            //刷新委托记录
            entrust();
        }

        start += step;
        if(start < 0)
        start = 5;
        setTimeout("count()",1000);



    }
    window.onload = count;


    /** 刷新成交记录 */
    var dealBoo = false;
    function reDeal() {
        if (dealBoo) {
            return;
        } else {
            dealBoo = true;
        }

        var currencyId = $("#cucyId").val();
        if (currencyId == null || currencyId == "") {
            dealBoo = false;
            //openTips("参数获取错误，请刷新页面重试")
            return;
        }

        $.ajax({
            url: '<%=path%>' + "/userWeb/tradeCenter/deal.htm", //方法路径URL
            data:{
                currencyId : currencyId
            },//参数
            dataType: 'json',
            type: 'POST',
            async: true, //默认异步调用 (false：同步)
            success: function (result) {
                if(result.code != 1 && result.message != null) {
                    dealBoo = false;
                    openTips(result.message);
                    return;
                }
                var data = result.data;
                var dealList = data.dealList;
                if (dealList != null && dealList.length > 0) {
                    var newChild= "";

                    for (var i=0;i<=dealList.length-1;i++) {
                        var deal = dealList[i];
                        var addTime = formatDateTime(deal.addTime);
                        var paymentType = "";
                        var type = ""
                        if (deal.paymentType == 1) {
                            paymentType = "买入";
                            type = "rise";
                        }
                        if (deal.paymentType == 2) {
                            paymentType = "卖出";
                            type = "fall";
                        }
                        var transactionPrice = Math.floor(deal.transactionPrice * 10000) / 10000;
                        var currencyNumber = Math.floor(deal.currencyNumber * 100) / 100;
                        var currencyTotalPrice = Math.floor(deal.currencyTotalPrice * 1000000) / 1000000;

                        newChild += "<tr class='tableInfo'>" +
                                        "<td class='dealTime'>"+ addTime +"</td>" +
                                        "<td class='type " + type + "'>" + paymentType + "</td>" +
                                        "<td class='dealAmount'>$" + transactionPrice + "</td>" +
                                        "<td class='dealAmount'>" + currencyNumber +"</td>" +
                                        "<td class='dealAmount rise'>" + currencyTotalPrice + "</td>" +
                                    "</tr>";

                    }
                    document.getElementById("dealOrder").innerHTML = newChild;
                    dealBoo = false;
                }


            }, error: function () {
                dealBoo = false;
                openTips("获取失败,请重新刷新页面后重试");
            }
        });

    }






</script>
<script type="text/javascript">
    //处理时间
    function formatDateTime(inputTime) {
        var date = new Date(inputTime);
        var y = date.getFullYear();
        var m = date.getMonth() + 1;
        m = m < 10 ? ('0' + m) : m;
        var d = date.getDate();
        d = d < 10 ? ('0' + d) : d;
        var h = date.getHours();
        h = h < 10 ? ('0' + h) : h;
        var minute = date.getMinutes();
        var second = date.getSeconds();
        minute = minute < 10 ? ('0' + minute) : minute;
        second = second < 10 ? ('0' + second) : second;
        return y + '-' + m + '-' + d+' '+h+':'+minute+':'+second;
    };
</script>

<script type="text/javascript">

    var calMoreBoo = false;
    function goCancle(pendOrderNo){
        if (calMoreBoo) {
            return;
        } else {
            calMoreBoo = true;
        }

        if (pendOrderNo == "" || pendOrderNo == null) {
            calMoreBoo =false;
            openTips("单号错误");
            return;
        }

        $.ajax({
            url: '<%=path %>' + "/userWeb/transactionPendOrderController/revoke.htm",
            data: {
                pendingOrderNo : pendOrderNo
            },//参数
            dataType: "json",
            type: 'POST',
            async: true, //默认异步调用 (false：同步)
            success: function (resultData) {
                var code = resultData.code;
                var message = resultData.message;
                if (code != 1 && message != "") {
                    calMoreBoo = false;
                    openTips(message);
                    return;
                }
                calMoreBoo = false
                entrust();

            },

            error: function () {
                calMoreBoo = false;
                openTips("数据加载出错，请稍候重试");
            }
        });

    }

    /** 刷新委托记录 */
    var entrustBoo = false;
    function entrust() {
        if (entrustBoo) {
            return;
        } else {
            entrustBoo = true;
        }

        var currencyId = $("#cucyId").val();
        if (currencyId == null || currencyId == "") {
            entrustBoo = false;
            openTips("参数获取错误，请刷新页面重试")
            return;
        }

        $.ajax({
            url: '<%=path%>' + "/userWeb/tradeCenter/entrust.htm", //方法路径URL
            data: {
                currencyId: currencyId

            },//参数
            dataType: 'json',
            type: 'POST',
            async: true, //默认异步调用 (false：同步)
            success: function (result) {
                if (result.code != 1 && result.message != null) {
                    entrustBoo = false;
                    openTips(result.message);
                    return;
                }
                var data = result.data;
                var dealList = data.transactionPendOrderList;
                if (dealList != null && dealList.length > 0) {
                    var newChild = "";

                    for (var i = 0; i <= dealList.length - 1; i++) {
                        var deal = dealList[i];
                        var addTime = formatDateTime(deal.addTime);
                        var paymentType = "";
                        var type = ""
                        if (deal.paymentType == 1) {
                            paymentType = "买入";
                            type = "rise";
                        }
                        if (deal.paymentType == 2) {
                            paymentType = "卖出";
                            type = "fall";
                        }
                        var pendingPrice = Math.floor(deal.pendingPrice * 10000) / 10000;
                        var pendingNumber = Math.floor(deal.pendingNumber * 100) / 100;
                        var currencyTotalPrice = Math.floor((pendingPrice * pendingNumber )* 1000000) / 1000000;

                        newChild += "<tr class='tableInfo'>" +
                            "<td class='time'>" + addTime + "</td>" +
                            "<td class='type " + type + "'>" + paymentType + "</td>" +
                            "<td class='amount'>" + pendingPrice + "</td>" +
                            "<td class='amount'>" + pendingNumber + "</td>" +
                            "<td class='amount rise'>" + currencyTotalPrice+ "</td>" +
                            "<td class='operate'><input type='text' value='撤&nbsp;销' class='revoke' onclick='goCancle('"+ ${deal.pendingOrderNo} + "')'/></td>" +
                            "</tr>";
                    }

                    document.getElementById("entrustRecord").innerHTML = newChild;
                    entrustBoo = false;
                }


            }, error: function () {
                entrustBoo = false;
                openTips("获取失败,请重新刷新页面后重试");
            }
        });
    }



</script>
</body>
</html>