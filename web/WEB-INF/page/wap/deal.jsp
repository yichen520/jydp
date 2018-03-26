<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/resources/page/common/path.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <meta http-equiv="Cache-Control" content="no-cache" />
    
    <link rel="stylesheet" type="text/css" href="<%=path %>/resources/css/wap/common.css">
    <link rel="stylesheet" href="<%=path %>/resources/css/wap/deal.css">
    <link rel="stylesheet" href="<%=path %>/resources/css/wap/simpleTips_wap.css">

    <title>盛源交易所</title>
</head>

<body>
<div id="header">
    <div>
        <input type="hidden" id="currencyIdStr" name="currencyIdStr" value="${currencyIdStr}"/>
        <input type="hidden" id="userSession" name="userSession" value="${userSession}"/>
    </div>
</div>
<!-- 头部导航 -->
<script type="text/x-handlebars-template" id="headerTemplate">
<header>
    <img src="{{webAppPath}}/resources/image/wap/header-open.png" class="open"/>
    <p>{{transactionCurrency.currencyName}}({{transactionCurrency.currencyShortName}}/USD)</p>
    <a href="{{webAppPath}}/userWap/userLogin/show">登录</a>
</header>
<div class="nav">
    <div class="nav-top">
        <div class="topLeft">{{formatNumber standardParameter.nowPrice 8}}</div>
        <div class="topRight">
                <img src="{{webAppPath}}/resources/image/wap/deal-hq.png" />
                <span>行情</span>
        </div>
        <div class="clear"></div>
    </div>
    <div class="nav-content">
                <div class="nav-content-list">
                    <p class="list-desc">最高 <span class="list-num">{{formatNumber standardParameter.todayMax 6}}</span></p>
                    <p class="list-desc">最低 <span class="list-num">{{formatNumber standardParameter.todayMin 6}}</span></p>
                </div>
                <div class="nav-content-list">
                    <p class="list-desc">买一价 <span class="list-num">{{formatNumber standardParameter.buyOne 6}}</span></p>
                    <p class="list-desc">卖一价 <span class="list-num">{{formatNumber standardParameter.sellOne 6}}</span></p>
                </div>
                <div class="nav-content-list">
                    <p class="list-desc">日成交额</p>
                    <p class="list-num">{{formatNumber standardParameter.dayTurnove 4}}万</p>
                </div>
    </div>
</div>

<!-- 内容 -->
<div id="wrapper">
    <!-- 当前价格 -->
    <div class="nowPrice">
        <div class="title">当前价格：\${{formatNumber standardParameter.nowPrice 8}}</div>
    </div>
    <!-- 买入卖入价格 -->
    <div class="tool">
        <div class="payLeft">
            <div class="leftTitle">
                <span>卖</span>
                <span>数量</span>
                <span>总额($)</span>
            </div>
            <div class="leftContent">
                {{#each transactionPendOrderSellList}}
                <p>
                    <span>卖{{eachWithIndexFromOne @index}}</span>
                    <span>{{formatNumber restNumber 4}}</span>
                    <span>{{formatNumber sumPrice 6}}</span>
                </p>
                {{/each}}
            </div>
        </div>
        <div class="payRight">
            <div class="rightTitle">
                <span>买</span>
                <span>数量</span>
                <span>总额($)</span>
            </div>
            <div class="rightContent">
                {{#each transactionPendOrderBuyList}}
                    <p>
                    <span>买{{eachWithIndexFromOne @index}}</span>
                    <span>{{formatNumber restNumber 4}}</span>
                    <span>{{formatNumber sumPrice 6}}</span>
                    </p>
                {{/each}}
            </div>
        </div>
        <div class="clear"></div>
    </div>
    <!-- 买入卖出委托记录 -->
    <div class="main">
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
    <input type="hidden" id="userIsPwd" value="{{userIdPwd}}"/>
    <input type="hidden" id="cucyId" name="cucyId" value="{{transactionCurrency.currencyId}}">
    <!-- 买入 -->
    <div class="buy">
        <div class="mainContent">
            <div class="mainContent-priceBuy">
                <span>单价</span><input id="buyPrice" name="buyPrice"  maxlength="9">
        </div>
        <div class="mainContent-numBuy">
            <span>数量</span><input  id="buyNum" name="buyNum" maxlength="11">
        </div>
            <input type="hidden" id="buyFee" value="{{transactionCurrency.buyFee}}">
            <input type="hidden" id="buyTotal">
            <p class="maxNum" id="buyMax">最大可买: 0</p>
            <div class="mainContent-passwordBuy">
                <span>交易密码</span><input type="password" id="buyPwd" name="buyPwd" maxlength="16" onkeyup="value=value.replace(/[^a-zA-Z0-9]/g,'')" onblur="value=value.replace(/[^a-zA-Z0-9]/g,'')" />
            </div>
        </div>
        <div class="mainButtonBuy">买入</div>
        <div class="mainMoney">
            <p class="mainMoney-title">
                <span>总资产</span>
                <span>冻结美金</span>
                <span>可用美金</span>
            </p>
            <p class="mainMoney-num">
                <span id="currencyNumberSumShow">{{formatNumber userDealCapitalMessage.currencyNumberSum 6}}</span>
                <span id="userBalanceLockShow">{{formatNumber userDealCapitalMessage.userBalanceLock 6}}</span>
                <span id="userBalance">{{formatNumber userDealCapitalMessage.userBalance 6}}</span>
            </p>
        </div>
    </div>
    <!-- 卖出 -->
    <div class="sell">
        <div class="mainContent">
            <div class="mainContent-priceSell">
                <span>单价</span><input type="number" id="sellPrice" name="sellPrice"  maxlength=9>
            </div>
            <div class="mainContent-numSell">
                <span>数量</span><input type="number" id="sellNum" name="sellNum"  maxlength="11">
            </div>
            <input type="hidden" id="sellFee" value="{{transactionCurrency.sellFee}}">
            <input type="hidden" id="currencyNumber" value="{{formatNumber userDealCapitalMessage.currencyNumber 4}}">
            <input type="hidden" id="sellTotal">
            <p class="maxNum" id="sellMax">最大可获得: 0</p>
            <div class="mainContent-passwordSell">
                <span>交易密码</span><input type="password" id="sellPwd" name="sellPwd" maxlength="16" onkeyup="value=value.replace(/[^a-zA-Z0-9]/g,'')" onblur="value=value.replace(/[^a-zA-Z0-9]/g,'')" />
            </div>
        </div>
        <div class="mainButtonSell">卖出</div>
            <div class="mainMoney">
                <p class="mainMoney-title">
                        <span>总资产</span>
                        <span>冻结数量</span>
                        <span>可用数量</span>
                </p>
                <p class="mainMoney-num">
                    <span>{{formatNumber userDealCapitalMessage.currencyNumberSum 6}}</span>
                    <span>{{formatNumber userDealCapitalMessage.userBalanceLock 6}}</span>
                    <span>{{formatNumber userDealCapitalMessage.userBalance 6}}</span>
                </p>
            </div>
        </div>
         <!-- 委托记录 -->
        <div class="entrust">
            <ul class="entrust-title">
                <li>类型</li>
                <li>单价($)</li>
                <li>数量</li>
                <li>已成交</li>
                <li>操作</li>
                <li class="clear"></li>
            </ul>
            {{#each transactionPendOrderList}}
            <ul class="entrust-content">
                <li>{{paymentTypeFormat paymentType}}</li>
                <li>{{formatNumber pendingPrice 2}}</li>
                <li>{{formatNumber pendingNumber 4}}</li>
                <li>{{formatNumber dealNumber 4}}</li>
                <li id="toCancleOrder">撤销<input type="hidden" value="{{pendingOrderNo}}"/></li>
                <li class="clear"></li>
            </ul>
            {{/each}}
        </div>
    </div>
</div>
<div id="revokeDiv" style="display: none;width: 200px;height: 200px;background-color: #C9C9C9;position: absolute">
    <p>撤销委托</p>
    <p><img src="{{webAppPath}}/resources/image/wap/tips.png"/>确定撤销该委托内容？</p>
    <div class="buttons">
        <input type="hidden" id="pendOrderNoCancle" name="pendOrderNoCancle">
        <input type="text" value="取&nbsp;消" class="cancel" onfocus="this.blur()" />
        <input type="text" id="cancleOrder" value="确&nbsp;定" class="yes" onfocus="this.blur()" />
    </div>
</div>

<!-- 底部tabBar -->
<footer>
    <a href="{{webAppPath}}/userWap/homePage/show" class="home">
            <img src="{{webAppPath}}/resources/image/wap/home-nochose.png"  class="home-icon"/>
            <p>首页</p>
    </a>
    <a class="deal open">
            <img src="{{webAppPath}}/resources/image/wap/deal-chose.png"  class="deal-icon"/>
            <p class="chose">交易</p>
    </a>
    <a href="{{webAppPath}}/userWap/userMine/show.htm" class="mine">
            <img src="{{webAppPath}}/resources/image/wap/mine-nochose.png"  class="mine-icon"/>
            <p>我的</p>
    </a>
</footer>

<!-- 选择币种 -->
<div class="choseBz">
    <div class="choseBzBox">
        <div class="closeAnthoer closeBox"></div>
        <div class="choseBzBox-title">
            <p>选择币种</p>
            <img src="{{webAppPath}}/resources/image/wap/header-close.png" class="closeBox"/>
            <div class="clear"></div>
        </div>
        <div class="choseBzBox-content">
            <ul>
                    {{#eachFortransactionCurrencyList transactionUserDealList standardParameter webAppPath}}
                    {{/eachFortransactionCurrencyList}}
            </ul>
        </div>
    </div>
</div>

<!--其他-->
<div class="mask">
    <div class="mask_content">
        <div class="buyConfirm">
            <p class="popTitle">买入提示</p>
            <p class="popInput">
                <label  class="popName" >买入价格：</label>
                <span  class="popInfo" id="buyPriceTips"></span>
                <input type="hidden" id="buyPriceConfirm" />
            </p>
            <p class="popInput">
                <label class="popName" >买入数量：</label>
                <span class="popInfo" id="buyNumTips" ></span>
                <input type="hidden" id="buyNumConfirm" />
            </p>
            <p class="popInput">
                <label class="popName" >合计：</label>
                <span  class="popInfo" id="buySumTips" ></span>
            </p>
            <p class="popInput">
                <label class="popName" for="buySpan">手续费：</label>
                <span class="popInfo"  id="buySpan">{{transactionCurrency.buyFee }}%</span>
            </p>

            <div class="buttons">
                <input type="hidden" id="buyPwdConfirm" />
                <input  type="text" value="取&nbsp;消" class="cancel" onfocus="this.blur()" />
                <input type="text" value="确&nbsp;定" class="yes" id="buyHandler" />
            </div>
        </div>

        <div class="sellConfirm">
            <p class="popTitle">卖出提示</p>
            <p class="popInput">
                <label class="popName">卖出价格：</label>
                <span class="popInfo" id="sellPriceTips"></span>
                <input type="hidden" id="sellPriceConfirm" />
            </p>
            <p class="popInput">
                <label class="popName">卖出数量：</label>
                <span class="popInfo" id="sellNumTips"></span>
                <input type="hidden" id="sellNumConfirm" />
            </p>
            <p class="popInput">
                <label class="popName">合计：</label>
                <span class="popInfo" id="sellSumTips"></span>
            </p>
            <p class="popInput">
                <label class="popName">手续费：</label>
                <span id="sellSpan">{{transactionCurrency.sellFee }}%</span>
            </p>

            <div class="buttons">
                <input type="hidden" id="sellPwdConfirm" />
                <input  type="text" value="取&nbsp;消" class="cancel" onfocus="this.blur()"/>
                <input type="text" value="确&nbsp;定" class="yes" onfocus="this.blur()" onclick="sellHandle()" />
            </div>
        </div>
    </div>
</div>

<!--其他-->
</script>
</div>
<script src="<%=path %>/resources/js/wap/common.js"></script>
<script src="<%=path %>/resources/js/wap/jquery-2.1.4.min.js"></script>
<script src="<%=path %>/resources/js/wap/handlebars-v4.0.11.js"></script>
<script src="<%=path %>/resources/js/wap/simpleTips_wap.js"></script>
<script src="<%=path %>/resources/js/wap/deal.js"></script>
</body>
</html>