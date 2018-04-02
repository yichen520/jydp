<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@include file="/resources/page/common/path.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <meta http-equiv="Cache-Control" content="no-cache"/>

    <link rel="stylesheet" type="text/css" href="<%=path %>/resources/css/wap/common.css">
    <link rel="stylesheet" href="<%=path %>/resources/css/wap/deal.css">
    <link rel="stylesheet" href="<%=path %>/resources/css/wap/simpleTips_wap.css">

    <title>盛源交易所</title>
</head>

<body>
<div id="header">
    <div>
        <input type="hidden" id="currencyIdStr" name="currencyIdStr" value="${currencyIdStr}"/>
    </div>
</div>
<!-- 头部导航 -->
<script type="text/x-handlebars-template" id="headerTemplate">
    <header>
        <div>
            <input type="hidden" id="currencyId" value="{{currencyId}}"/>
            <input type="hidden" id="userSession" name="userSession" value="{{userSession}}"/>
        </div>
        <img src="{{webAppPath}}/resources/image/wap/header-open.png" class="open"/>
        <p>{{transactionCurrency.currencyName}}({{transactionCurrency.currencyShortName}}/XT)</p>
        {{{isLogin userSession webAppPath}}}
    </header>
    <section class="nav">
        <div class="nav-content">
            <p class="topLeft flex">{{formatNumber standardParameter.nowPrice 8}}</p>
            <p class="nav-content-list flex">
                <span class="list-desc">最高 <span class="list-num"  id="todayMaxSpan">{{formatNumber standardParameter.todayMax 6}}</span></span>
                <span class="list-desc">最低 <span class="list-num"  id="todayMinSpan">{{formatNumber standardParameter.todayMin 6}}</span></span>
            </p>
            <p class="nav-content-list flex">
                <span class="list-desc right">买一 <span class="list-num" id="buyOneSpan">{{formatNumber standardParameter.buyOne 6}}</span></span>
                <span class="list-desc right">卖一 <span class="list-num"  id="sellOneOne">{{formatNumber standardParameter.sellOne 6}}</span></span>
            </p>
            <p class="nav-content-list flex">
                <span class="list-desc txtRight">日成交额</span>
                <span class="list-num" style="display:block" id="dayTurnoveOne">{{formatNumberWithWan standardParameter.dayTurnove 4}}</span>
            </p>
        </div>
    </section>
    <div id="wrapper">
        <div class="nowPrice">
            <p class="title" id="nowPriceDiv">当前价格：{{formatNumber standardParameter.nowPrice 8}} XT</p>
                <p class="topRight">
                    <img src="{{webAppPath}}/resources/image/wap/deal-hq.png" />
                    <span>行情</span>
                </p>
            <p class="clear"></p>
        </div>
        <div class="tool">
            <div class="payLeft">
                <div class="leftTitle">
                    <span>卖/买</span>
                    <span>价格(XT)</span>
                    <span>数量</span>
                    <span>总额(XT)</span>
                </div>
                <p class="leftContent">
                    {{{eachForTransactionPendOrderSellList transactionPendOrderSellList}}}

                </p>
            </div>
            <div class="payRight">
                <p class="rightContent">
                {{#each transactionPendOrderBuyList}}
                    <span class="list-content">
                        <span>买{{eachWithIndexFromOne @index}}</span>
                        <span>{{formatNumber pendingPrice 2}}</span>
                        <span>{{formatNumber restNumber 4}}</span>
                        <span>{{formatNumber sumPrice 6}}</span>
                    </span>
                {{/each}}
                </p>
            </div>
        </div>
        <!-- 买入和卖出操作 -->
        <div class="main" >
            <ul class="mainTitle">
                <li>
                    <p>买入</p>
                    <div class="borderBuy"></div>
                </li>
                <li>
                    <p>卖出</p>
                    <div class="borderSell"></div>
                </li>
                <li>
                    <p>委托记录</p>
                    <div class="borderEntrust"></div>
                </li>
                <div class="clear"></div>
            </ul>
        </div>
        <div class="tab">
            <input type="hidden" id="webAppPath" value="{{webAppPath}}"/>
            <input type="hidden" id="cucyId" name="cucyId" value="{{transactionCurrency.currencyId}}">
            <!-- 买入 -->
            <div class="buy">
                <div class="mainContent">
                    <div class="mainContent-priceBuy">
                        <span class="name">单价</span><input type="text" id="buyPrice" name="buyPrice" maxlength="9">
                    </div>
                    <div class="mainContent-numBuy">
                        <span class="name">数量</span><input type="text" id="buyNum" name="buyNum" maxlength="11" autocomplete="new-password">
                    </div>
                    <input type="hidden" id="buyFee" value="{{transactionCurrency.buyFee}}">
                    <input type="hidden" id="buyTotal">
                    <p class="maxNum" id="buyMax">最大可买: 0 XT</p>
                    <div class="mainContent-passwordBuy">
                        <span class="name">交易密码</span><input type="password" id="buyPwd" name="buyPwd" maxlength="16"  autocomplete="new-password"/>
                        <span class="setting">设置</span>
                    </div>
                    <p class="maxNumTwo" id="bMaxNum">当前设置: 每笔交易都输入密码</p>
                </div>
                <div class="mainButtonBuy">买入</div>
                <div class="mainMoney">
                    <p class="mainMoney-title">
                        <span>总资产</span>
                        <span>冻结 XT</span>
                        <span>可用 XT</span>
                    </p>
                    <p class="mainMoney-num">
                        <span id="currencyNumberSumShow">{{formatNumber userDealCapitalMessage.currencyNumberSum 6}} XT</span>
                        <span id="userBalanceLockShow">{{formatNumber userDealCapitalMessage.userBalanceLock 6}} XT</span>
                        <span id="userBalance">{{formatNumber userDealCapitalMessage.userBalance 6}} XT</span></span>
                    </p>
                </div>
            </div>
            <!-- 卖出 -->
            <div class="sell">
                <div class="mainContent">
                    <div class="mainContent-priceSell">
                        <span class="name">单价</span><input type="text" id="sellPrice" name="sellPrice" maxlength="9">
                    </div>
                    <div class="mainContent-numSell">
                        <span class="name">数量</span><input type="text" id="sellNum" name="sellNum" maxlength="11" autocomplete="new-password">
                    </div>
                    <input type="hidden" id="sellFee" value="{{transactionCurrency.sellFee}}">
                    <input type="hidden" id="currencyNumber"
                           value="{{formatNumber userDealCapitalMessage.currencyNumber 4}}">
                    <input type="hidden" id="sellTotal">
                    <p class="maxNum" id="sellMax">最大可获得: 0 XT</p>
                    <div class="mainContent-passwordSell">
                        <span class="name">交易密码</span>
                        <input type="password" id="sellPwd" name="sellPwd"  maxlength="16"  autocomplete="new-password"/>
                        <span class="setting">设置</span>
                    </div>
                    <p class="maxNumTwo" id="sMaxNum">当前设置: 每笔交易都输入密码</p>
                </div>
                <div class="mainButtonSell">卖出</div>
                <div class="mainMoney">
                    <p class="mainMoney-title">
                        <span>冻结数量</span>
                        <span>可用数量</span>
                    </p>
                    <p class="mainMoney-num">
                        <span id="currencyNumberLockShow">{{formatNumber userDealCapitalMessage.currencyNumberLock 6}}</span>
                        <span id="currencyNumberShow">{{formatNumber userDealCapitalMessage.currencyNumber 6}}</span>
                    </p>
                </div>
            </div>
            <!-- 委托记录 -->
            <div class="entrust">
                <ul class="entrust-title">
                    <li>类型</li>
                    <li>单价(XT)</li>
                    <li>数量</li>
                    <li>已成交</li>
                    <li>操作</li>
                    <li class="clear"></li>
                </ul>
                <ul class="entrust-content" id="entrustUL">
                        {{#each transactionPendOrderList}}
                        <li>
                            <p >{{{paymentTypeFormat paymentType}}}</p>
                            <p>{{formatNumber pendingPrice 2}}</p>
                            <p>{{formatNumber pendingNumber 4}}</p>
                            <p>{{formatNumber dealNumber 4}}</p>
                            <p class="toCancleOrder">撤销<input type="hidden" value="{{pendingOrderNo}}"/></p>
                            <p class="clear"></p>
                        </li>
                        {{/each}}
                </ul>
                </ul>
            </div>
        </div>
    </div>
    <!-- 撤销弹窗  -->
    <div class="bg">
        <div class="showBox">
            <div class="showBoxTitle">撤销委托</div>
            <div class="showBoxContent">是否撤销该委托？</div>
            <div class="showBoxButton">
                <input type="hidden" id="pendOrderNoCancle" name="pendOrderNoCancle">
                <div class="cancelShow cancel">取消</div>
                <div class="okay" id="cancleOrder">确定</div>
            </div>
        </div>
    </div>

    <!-- 底部tabBar -->
    <footer>
        <a href="{{webAppPath}}/userWap/homePage/show" class="home">
            <img src="{{webAppPath}}/resources/image/wap/home-nochose.png" class="home-icon"/>
            <p>首页</p>
        </a>
        <a class="deal open">
            <img src="{{webAppPath}}/resources/image/wap/deal-chose.png" class="deal-icon"/>
            <p class="chose">交易</p>
        </a>
        <a href="{{webAppPath}}/userWap/userInfo/show.htm" class="mine">
            <img src="{{webAppPath}}/resources/image/wap/mine-nochose.png" class="mine-icon"/>
            <p>我的</p>
        </a>
    </footer>

    <!-- 选择币种 -->
    <div class="choseBz">
        <div class="choseBzBox">

            <div class="choseBzBox-title">
                <p>选择币种</p>
                <img src="{{webAppPath}}/resources/image/wap/header-close.png" class="closeBox"/>
                <div class="clear"></div>
            </div>
            <div class="choseBzBox-content">
                  <ul id="currencyList">
                  </ul>
            </div>
        </div>
        <div class="closeAnthoer closeBox"></div>
    </div>

    <!--买入 卖出提示-->
    <div class="mask">
        <div class="maskBox">
            <div class="maskBoxTitle">买入提示</div>
            <div class="maskBoxContent">
                <p>
                    <span>买入价格：</span>
                    <span id="buyPriceTips"></span>
                    <input type="hidden" id="buyPriceConfirm"/>
                </p>
                <p>
                    <span>买入数量：</span>
                    <span id="buyNumTips"></span>
                    <input type="hidden" id="buyNumConfirm"/>
                </p>
                <p>
                    <span>合计:</span>
                    <span id="buySumTips"></span>
                </p>
                <p>
                    <span>手续费:</span>
                    <span id="buySpan">{{transactionCurrency.buyFee }}%</span>
                </p>
            </div>
            <div class="maskBoxButton">
                <input type="hidden" id="buyPwdConfirm" />
                <div class="cancelmask">取消</div>
                <div class="okaymask" id="buyHandler">确定</div>

            </div>
        </div>
    </div>
    </div>

    <div class="maskSell">
        <div class="maskSellBox">
            <div class="maskSellBoxTitle">卖出提示</div>
            <div class="maskSellBoxContent">
                <p>
                    <span>卖出价格：</span>
                    <span id="sellPriceTips"></span>
                    <span id="sellPriceConfirm"></span>
                </p>
                <p>
                    <span>卖出数量：</span>
                    <span id="sellNumTips"></span>
                    <input type="hidden" id="sellNumConfirm"/>
                </p>
                <p>
                    <span>合计:</span>
                    <span id="sellSumTips"></span>
                </p>
                <p>
                    <span>手续费:</span>
                    <span>{{transactionCurrency.sellFee }}%</span>
                </p>
            </div>
            <div class="maskSellBoxButton">
                <input type="hidden" id="sellPwdConfirm" />
                <div class="cancelmaskSell">取消</div>
                <div class="okaymaskSell" id="sellHandler">确定</div>
            </div>
        </div>
    </div>
    </div>
    <!--其他-->
    <!-- 密码设置弹窗 -->
    <div>
     <input type="hidden" id="payPasswordStatus" name="payPasswordStatus" value="{{payPasswordStatus}}"/>
     <input type="hidden" id="userIsPwd" name="userIsPwd" value="{{userIsPwd}}"/>
    </div>
    <div class="cin">
        <div class="settingBox">
            <div class="settingTitle">记住密码提示</div>
            <div class="settingContent">
                <label style="position: relative">
                    <input type="radio" name="remember" value="2" class="choose" style="position: absolute;top: 0.24rem;left:0.05rem"/>
                    <span style="position: absolute;left: 0.6rem">每次登录只输入一次密码</span>
                </label>
                <label style="position: relative;margin-top: 0.3rem;margin-bottom: 0.6rem">
                    <input type="radio" name="remember" value="1" class="choose"  style="position: absolute;top: 0.26rem;left:0.05rem"/>
                    <span style="position: absolute;left: 0.6rem">每笔交易都输入密码</span>
                </label>
                <p >
                    交易密码<input type="password" class="pas" id="rememberPwd" maxlength="16" autocomplete="new-password"
                               onkeyup="value=value.replace(/[^a-zA-Z0-9]/g,'')"
                               onblur="value=value.replace(/[^a-zA-Z0-9]/g,'')"/>
                </p>
            </div>
            <div class="settingButton">
                <div class="cancelSetting">取消</div>
                <div class="okaySetting">确定</div>
            </div>
        </div>
    </div>
</script>
<script id="table-template" type="text/x-handlebars-template">
    {{#each this}}
    <li>
        <p style="display: none;">{{currencyId}}</p>
        <p>{{currencyName}}({{currencyShortName}})</p>
        {{#compare change 0}}
        <p style="color: red">{{latestPrice}}</p>
        {{else}}
        <p style="color: green">{{latestPrice}}</p>
        {{/compare}}
        {{#compare change 0}}
        <p style="color: red">{{change}}%</p>
        {{else}}
        <p style="color: green">{{change}}%</p>
        {{/compare}}
    </li>
    {{/each}}
</script>
</div>
<script src="<%=path %>/resources/js/wap/common.js"></script>
<script src="<%=path %>/resources/js/wap/jquery-2.1.4.min.js"></script>
<script src="<%=path %>/resources/js/wap/handlebars-v4.0.11.js"></script>
<script src="<%=path %>/resources/js/wap/simpleTips_wap.js"></script>
<script src="<%=path %>/resources/js/wap/deal.js"></script>
<script type="text/javascript">

    var path = "<%=path%>"

    //if比较
    Handlebars.registerHelper("compare", function (x1, x2, options) {
        if (x1 >= x2) {
            //满足条件执行
            return options.fn(this);
        } else {
            //不满足执行{{else}}部分
            return options.inverse(this);
        }
    });
</script>
</body>
</html>