<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/resources/page/common/path.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


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
                    <input type="text" class="entry" placeholder="请输入单个币买入价" id="buyPrice" name="buyPrice"/>
                </p>
                <p class="buyInput">
                    <label class="tradeName">买入数量：</label>
                    <input type="text" class="entry" placeholder="请输入您要买入的数量" id="buyNum" name="buyNum"/>
                    <span class="max">当前最大可买：<span>123.000</span></span>
                </p>
                <p class="phoneInput">
                    <label class="tradeName">支付密码：</label>
                    <input type="password" class="entry" placeholder="您的支付密码" id="buyPwd" name="buyPwd"/>
                </p>
                <p class="buyInput">
                    <label class="tradeName">合计：</label>
                    <span class="all">$123.00000</span>
                </p>
                <p class="serviceInput">
                    <label class="tradeName">手续费：</label>
                    <span class="service">${transactionCurrency.buyFee }</span>
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
                    <label class="tradeName">买入价格：</label>
                    <input type="text" class="entry" placeholder="请输入单个盛源链买入价" id="sellPrice" name="sellPrice" />
                </p>
                <p class="buyInput">
                    <label class="tradeName">买入数量：</label>
                    <input type="text" class="entry" placeholder="请输入您要买入的该币种数量" id="sellNum" name="sellNum"/>
                    <span class="max">当前最大可买：<span>123.000</span></span>
                </p>
                <p class="phoneInput">
                    <label class="tradeName">支付密码：</label>
                    <input type="password" class="entry" placeholder="您的支付密码" id="sellPwd" name="sellPwd"/>
                </p>
                <p class="buyInput">
                    <label class="tradeName">合计：</label>
                    <span class="all">$123.00000</span>
                </p>
                <p class="serviceInput">
                    <label class="tradeName">手续费：</label>
                    <span class="service">${transactionCurrency.sellFee }</span>
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
            </li>
            <li class="accountList">
                <span class="listName">冻结${transactionCurrency.currencyName }</span>
                <span class="listMoney fall">${userDealCapitalMessage.currencyNumberLock }</span>
            </li>
            <li class="accountList">
                <span class="listName">可用美金</span>
                <span class="listMoney rise">$${userDealCapitalMessage.userBalance }</span>
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
                            <li class="recordInfo">
                                <span class="rangeType">卖3</span>
                                <span class="rangePrice">90.0000</span>
                                <span class="rangeNum">0.98</span>
                                <span class="rangeAmount">123.000000</span>
                            </li>
                            <li class="recordInfo">
                                <span class="rangeType">卖3</span>
                                <span class="rangePrice">90.0000</span>
                                <span class="rangeNum">0.98</span>
                                <span class="rangeAmount">123.000000</span>
                            </li> <li class="recordInfo">
                            <span class="rangeType">卖3</span>
                            <span class="rangePrice">90.0000</span>
                            <span class="rangeNum">0.98</span>
                            <span class="rangeAmount">123.000000</span>
                        </li> <li class="recordInfo">
                            <span class="rangeType">卖3</span>
                            <span class="rangePrice">90.0000</span>
                            <span class="rangeNum">0.98</span>
                            <span class="rangeAmount">123.000000</span>
                        </li> <li class="recordInfo">
                            <span class="rangeType">卖3</span>
                            <span class="rangePrice">90.0000</span>
                            <span class="rangeNum">0.98</span>
                            <span class="rangeAmount">123.000000</span>
                        </li>
                            <li class="recordInfo">
                                <span class="rangeType">卖2</span>
                                <span class="rangePrice">90.0000</span>
                                <span class="rangeNum">0.98</span>
                                <span class="rangeAmount">123.000000</span>
                            </li>
                            <li class="recordInfo">
                                <span class="rangeType">卖1</span>
                                <span class="rangePrice">90.0000</span>
                                <span class="rangeNum">0.98</span>
                                <span class="rangeAmount">123.000000</span>
                            </li>
                        </ul>

                        <ul class="buyRecord rise">
                            <li class="recordInfo">
                                <span class="rangeType">买1</span>
                                <span class="rangePrice">90.0000</span>
                                <span class="rangeNum">0.98</span>
                                <span class="rangeAmount">123.000000</span>
                            </li>
                            <li class="recordInfo">
                                <span class="rangeType">买2</span>
                                <span class="rangePrice">90.0000</span>
                                <span class="rangeNum">0.98</span>
                                <span class="rangeAmount">123.000000</span>
                            </li>
                            <li class="recordInfo">
                                <span class="rangeType">买3</span>
                                <span class="rangePrice">90.0000</span>
                                <span class="rangeNum">0.98</span>
                                <span class="rangeAmount">123.000000</span>
                            </li>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <div class="myEntrust">
        <p class="myTitle">
            我的委托记录<img src="<%=path %>/resources/image/web/entrust.png" />
            <a href="#" class="more">查看更多</a>
        </p>
        <table class="table" cellspacing="0 " cellpadding="0">
            <tr class="tableTitle">
                <td class="time">委托时间</td>
                <td class="type">类型</td>
                <td class="amount">委托价格</td>
                <td class="amount">委托数量</td>
                <td class="amount">委托总价</td>
                <td class="operate">操作</td>
            </tr>
            <tr class="tableInfo">
                <td class="time">2016-06-06&nbsp;06:06:05</td>
                <td class="type rise">买入</td>
                <td class="amount">$12.0000</td>
                <td class="amount">0.95</td>
                <td class="amount rise">$11.40000000</td>
                <td class="operate"><input type="text" value="撤&nbsp;销" class="revoke" onfocus="this.blur()" /></td>
            </tr>
            <tr class="tableInfo">
                <td class="time">2016-06-06&nbsp;06:06:05</td>
                <td class="type fall">卖出</td>
                <td class="amount">$12.0000</td>
                <td class="amount">0.95</td>
                <td class="amount fall">$11.40000000</td>
                <td class="operate"><input type="text" value="撤&nbsp;销" class="revoke" onfocus="this.blur()" /></td>
            </tr>
            <tr class="tableInfo">
                <td class="time">2016-06-06&nbsp;06:06:05</td>
                <td class="type rise">买入</td>
                <td class="amount">$12.0000</td>
                <td class="amount">0.95</td>
                <td class="amount rise">$11.40000000</td>
                <td class="operate"><input type="text" value="撤&nbsp;销" class="revoke" onfocus="this.blur()" /></td>
            </tr>
            <tr class="tableInfo">
                <td class="time">2016-06-06&nbsp;06:06:05</td>
                <td class="type rise">买入</td>
                <td class="amount">$12.0000</td>
                <td class="amount">0.95</td>
                <td class="amount rise">$11.40000000</td>
                <td class="operate"><input type="text" value="撤&nbsp;销" class="revoke" onfocus="this.blur()" /></td>
            </tr>
            <tr class="tableInfo">
                <td class="time">2016-06-06&nbsp;06:06:05</td>
                <td class="type fall">卖出</td>
                <td class="amount">$12.0000</td>
                <td class="amount">0.95</td>
                <td class="amount fall">$11.40000000</td>
                <td class="operate"><input type="text" value="撤&nbsp;销" class="revoke" onfocus="this.blur()" /></td>
            </tr>
        </table>
    </div>

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
            <table class="table" cellspacing="0 " cellpadding="0">
                <tr class="tableInfo">
                    <td class="dealTime">2016-06-06&nbsp;06:06:05</td>
                    <td class="type rise">买入</td>
                    <td class="dealAmount">$12.0000</td>
                    <td class="dealAmount">0.95</td>
                    <td class="dealAmount rise">$11.40000000</td>
                </tr>
                <tr class="tableInfo">
                    <td class="dealTime">2016-06-06&nbsp;06:06:05</td>
                    <td class="type fall">卖出</td>
                    <td class="dealAmount">$12.0000</td>
                    <td class="dealAmount">0.95</td>
                    <td class="dealAmount fall">$11.40000000</td>
                </tr>
                <tr class="tableInfo">
                    <td class="dealTime">2016-06-06&nbsp;06:06:05</td>
                    <td class="type rise">买入</td>
                    <td class="dealAmount">$12.0000</td>
                    <td class="dealAmount">0.95</td>
                    <td class="dealAmount rise">$11.40000000</td>
                </tr>
                <tr class="tableInfo">
                    <td class="dealTime">2016-06-06&nbsp;06:06:05</td>
                    <td class="type rise">买入</td>
                    <td class="dealAmount">$12.0000</td>
                    <td class="dealAmount">0.95</td>
                    <td class="dealAmount rise">$11.40000000</td>
                </tr>
                <tr class="tableInfo">
                    <td class="dealTime">2016-06-06&nbsp;06:06:05</td>
                    <td class="type fall">卖出</td>
                    <td class="dealAmount">$12.0000</td>
                    <td class="dealAmount">0.95</td>
                    <td class="dealAmount fall">$11.40000000</td>
                </tr>
                <tr class="tableInfo">
                    <td class="dealTime">2016-06-06&nbsp;06:06:05</td>
                    <td class="type fall">卖出</td>
                    <td class="dealAmount">$12.0000</td>
                    <td class="dealAmount">0.95</td>
                    <td class="dealAmount fall">$11.40000000</td>
                </tr>
                <tr class="tableInfo">
                    <td class="dealTime">2016-06-06&nbsp;06:06:05</td>
                    <td class="type fall">卖出</td>
                    <td class="dealAmount">$12.0000</td>
                    <td class="dealAmount">0.95</td>
                    <td class="dealAmount fall">$11.40000000</td>
                </tr>
                <tr class="tableInfo">
                    <td class="dealTime">2016-06-06&nbsp;06:06:05</td>
                    <td class="type fall">卖出</td>
                    <td class="dealAmount">$12.0000</td>
                    <td class="dealAmount">0.95</td>
                    <td class="dealAmount fall">$11.40000000</td>
                </tr>
                <tr class="tableInfo">
                    <td class="dealTime">2016-06-06&nbsp;06:06:05</td>
                    <td class="type fall">卖出</td>
                    <td class="dealAmount">$12.0000</td>
                    <td class="dealAmount">0.95</td>
                    <td class="dealAmount fall">$11.40000000</td>
                </tr>
                <tr class="tableInfo">
                    <td class="dealTime">2016-06-06&nbsp;06:06:05</td>
                    <td class="type fall">卖出</td>
                    <td class="dealAmount">$12.0000</td>
                    <td class="dealAmount">0.95</td>
                    <td class="dealAmount fall">$11.40000000</td>
                </tr>
            </table>
        </div>
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
        start += step;
        if(start < 0)
            start = 5;
        setTimeout("count()",1000);
    }
    window.onload = count;
</script>

</body>
</html>