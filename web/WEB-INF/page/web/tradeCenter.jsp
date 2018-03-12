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
            <span class="priceNow" id="nowPrice"><fmt:formatNumber type="number" value="${standardParameter.nowPrice}" maxFractionDigits="8"/></span>

            <p class="increase">
                <c:if test="${standardParameter.todayRange >= 0}">
                    <span class="number rise" id="todayRangeRise">+<fmt:formatNumber type="number" value="${standardParameter.todayRange }" groupingUsed="FALSE" maxFractionDigits="6"/>%</span>
                </c:if>
                <c:if test="${standardParameter.todayRange < 0}">
                    <span class="number fall" id="todayRangeRise"><fmt:formatNumber type="number" value="${standardParameter.todayRange }" groupingUsed="FALSE" maxFractionDigits="6"/>%</span>
                </c:if>
                <span class="infoName">今日涨跌</span>
            </p>
            <p class="info">
                <span class="number rise" id="todayMax"><fmt:formatNumber type="number" value="${standardParameter.todayMax }" groupingUsed="FALSE" maxFractionDigits="6"/></span>
                <span class="infoName" >今日最高价</span>
            </p>
            <p class="info">
                <span class="number fall" id="todayMin"><fmt:formatNumber type="number" value="${standardParameter.todayMin }" groupingUsed="FALSE" maxFractionDigits="6"/></span>
                <span class="infoName">今日最低价</span>
            </p>
            <p class="info">
                <span class="number price" id="buyOne"><fmt:formatNumber type="number" value="${standardParameter.buyOne }" groupingUsed="FALSE" maxFractionDigits="6"/></span>
                <span class="infoName">买一价</span>
            </p>
            <p class="info">
                <span class="number price" id="sellOne"><fmt:formatNumber type="number" value="${standardParameter.sellOne }" groupingUsed="FALSE" maxFractionDigits="6"/></span>
                <span class="infoName">卖一价</span>
            </p>
            <p class="info">
                <span class="number price" id="dayTurnove"><fmt:formatNumber type="number" value="${standardParameter.dayTurnove }" groupingUsed="FALSE" maxFractionDigits="4"/></span>
                <span class="infoName">今日成交量</span>
            </p>
        </div>
    </div>

    <div class="left">
        <div class="charts">蜡烛图</div>

        <p class="promt">
            <span class="promtText"> 重要提示：今日最高价格：<span id="hintTodayMax">$<fmt:formatNumber type="number" value="${standardParameter.todayMax }" groupingUsed="FALSE" maxFractionDigits="6"/></span>，
                今日最低价格：<span id="hintTodayMin">$<fmt:formatNumber type="number" value="${standardParameter.todayMin }" groupingUsed="FALSE" maxFractionDigits="6"/></span></span>
            <span  class="promptTime">交易时间：08:00:00-07:59:00</span>
        </p>


        <div class="tradeArea">
            <div class="buy">
                <p class="buyTitle">买入</p>

                <p class="buyInput">
                    <label class="tradeName">可用美金：</label>
                    <span class="buyAmount rise" id="usableUserBalance">$<fmt:formatNumber type="number" value="${userDealCapitalMessage.userBalance }" groupingUsed="FALSE" maxFractionDigits="6"/></span>
                </p>
                <p class="serviceInput">
                    <label class="tradeName">最佳买价：</label>
                    <span class="service" id="optimumBuy" >$<fmt:formatNumber type="number" value="${standardParameter.sellOne }" groupingUsed="FALSE" maxFractionDigits="6"/></span>
                </p>
                <p class="buyInput">
                    <label class="tradeName">买入价格：</label>
                    <input type="text" class="entry" placeholder="请输入单个币买入价" id="buyPrice" name="buyPrice"
                           onkeyup="matchUtil(this, 'double', 2)" onblur="matchUtil(this, 'double', 2)" maxlength="9"/>
                </p>
                <p class="buyInput">
                    <label class="tradeName">买入数量：</label>
                    <input type="text" class="entry" placeholder="请输入您要买入的数量" id="buyNum" name="buyNum"
                           onkeyup="matchUtil(this, 'double', 4)" onblur="matchUtil(this, 'double', 4)" maxlength="11"/>
                    <span class="max">当前最大可买：<span id="buyMax">0</span></span>
                </p>
                <p class="phoneInput">
                    <label class="tradeName">支付密码：</label>
                    <span class="passwordSetting">
                        <input type="password" class="tradePassword" placeholder="您的支付密码" id="buyPwd" name="buyPwd" maxlength="16"
                               onkeyup="value=value.replace(/[^a-zA-Z0-9]/g,'')" onblur="value=value.replace(/[^a-zA-Z0-9]/g,'')"/>
                        <img src="<%=path %>/resources/image/web/setting.png" class="setting" />
                    </span>
                </p>
                <p class="buyInput">
                    <label class="tradeName">合计：</label>
                    <span class="all" id="buyTotal">$0</span>
                </p>
                <p class="serviceInput">
                    <label class="tradeName">手续费：</label>
                    <span class="service">${transactionCurrency.buyFee }%</span>
                    <input type="hidden" id="buyFee" value="${transactionCurrency.buyFee }">
                </p>

                <input type="text" class="buyBtn" value="买&nbsp;入" onfocus="this.blur()" onclick="buy();"/>
            </div>

            <div class="sell">
                <p class="sellTitle">卖出</p>

                <p class="buyInput">
                    <label class="tradeName">可用数量：</label>
                    <span class="sellAmount fall" id="usableCurrencyNumber"><fmt:formatNumber type="number"
                           value="${userDealCapitalMessage.currencyNumber }" groupingUsed="FALSE" maxFractionDigits="4"/></span>
                </p>
                <p class="serviceInput">
                    <label class="tradeName">最佳卖价：</label>
                    <span class="service" id="optimumSell">$<fmt:formatNumber type="number" value="${standardParameter.buyOne }" groupingUsed="FALSE" maxFractionDigits="6"/></span>
                </p>
                <p class="buyInput">
                    <label class="tradeName">卖出价格：</label>
                    <input type="text" class="entry" placeholder="请输入单个币的卖出价" id="sellPrice" name="sellPrice"
                           onkeyup="matchUtil(this, 'double', 2)" onblur="matchUtil(this, 'double', 2)" maxlength=9/>
                </p>
                <p class="buyInput">
                    <label class="tradeName">卖出数量：</label>
                    <input type="text" class="entry" placeholder="请输入您要卖出的该币种数量" id="sellNum" name="sellNum"
                           onkeyup="matchUtil(this, 'double', 4)" onblur="matchUtil(this, 'double', 4)" maxlength="11"/>
                    <span class="max">最大可获得：$<span id="sellMax">0</span></span>
                </p>
                <p class="phoneInput">
                    <label class="tradeName">支付密码：</label>
                    <span class="passwordSetting">
                        <input type="password" class="tradePassword" placeholder="您的支付密码" id="sellPwd" name="sellPwd" maxlength="16"
                           onkeyup="value=value.replace(/[^a-zA-Z0-9]/g,'')" onblur="value=value.replace(/[^a-zA-Z0-9]/g,'')"/>
                        <img src="<%=path %>/resources/image/web/setting.png" class="setting" />
                    </span>
                </p>
                <p class="buyInput">
                    <label class="tradeName">合计：</label>
                    <span class="all" id="sellTotal">$0</span>
                </p>
                <p class="serviceInput">
                    <label class="tradeName">手续费：</label>
                    <span class="service">${transactionCurrency.sellFee }%</span>
                </p>

                <input type="text" class="sellBtn" value="卖&nbsp;出" onfocus="this.blur()" onclick="sell();"/>
            </div>
        </div>
    </div>

    <div class="right">
        <ul class="account">
            <li class="accountList">
                <span class="listName">可用${transactionCurrency.currencyName }</span>
                <span class="listMoney rise" id="currencyNumberShow"><fmt:formatNumber type="number" value="${userDealCapitalMessage.currencyNumber }" groupingUsed="FALSE" maxFractionDigits="4"/></span>
                <input type="hidden" id="currencyNumber" value="<fmt:formatNumber type="number" value="${userDealCapitalMessage.currencyNumber }" groupingUsed="FALSE" maxFractionDigits="4"/>">
            </li>
            <li class="accountList">
                <span class="listName" >冻结${transactionCurrency.currencyName }</span>
                <span class="listMoney fall" id="currencyNumberLockShow"><fmt:formatNumber type="number" value="${userDealCapitalMessage.currencyNumberLock }" groupingUsed="FALSE" maxFractionDigits="4"/></span>
            </li>
            <li class="accountList">
                <span class="listName">可用美金</span>
                <span class="listMoney rise" id="userBalanceShow">$<fmt:formatNumber type="number" value="${userDealCapitalMessage.userBalance }" groupingUsed="FALSE" maxFractionDigits="6"/></span>
                <input type="hidden" id="userBalance" value="<fmt:formatNumber type="number" value="${userDealCapitalMessage.userBalance }" groupingUsed="FALSE" maxFractionDigits="6"/>">
            </li>
            <li class="accountList">
                <span class="listName">冻结美金</span>
                <span class="listMoney fall" id="userBalanceLockShow">$<fmt:formatNumber type="number" value="${userDealCapitalMessage.userBalanceLock }" groupingUsed="FALSE" maxFractionDigits="6"/></span>
            </li>
            <li class="accountList">
                <span class="listName">账户总资产</span>
                <span class="listMoney fall" id="currencyNumberSumShow">$<fmt:formatNumber type="number" value="${userDealCapitalMessage.currencyNumberSum }" groupingUsed="FALSE" maxFractionDigits="6"/></span>
            </li>
        </ul>

        <div class="entrust">
            <p class="entrustTop">
                <span class="dealPrice" id="nowPriceShow">最新成交价：$<fmt:formatNumber type="number" value="${standardParameter.nowPrice}" groupingUsed="FALSE" maxFractionDigits="6"/></span>
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
                        <ul class="sellRecord fall" id="orderSellReId">
                            <c:forEach items="${transactionPendOrderSellList}" var="item" varStatus="status">
                                <li class="recordInfo">
                                    <span class="rangeType">卖${fn:length(transactionPendOrderSellList) - status.index}</span>
                                    <span class="rangePrice"><fmt:formatNumber type="number" value="${item.pendingPrice}" maxFractionDigits="2" groupingUsed="FALSE"/></span>
                                    <span class="rangeNum"><fmt:formatNumber type="number" value="${item.restNumber}" maxFractionDigits="4" groupingUsed="FALSE"/></span>
                                    <span class="rangeAmount"><fmt:formatNumber type="number" value="${item.sumPrice}" maxFractionDigits="6" groupingUsed="FALSE"/></span>
                                </li>
                            </c:forEach>
                        </ul>

                        <ul class="buyRecord rise" id="orderBuyReId">
                            <c:forEach items="${transactionPendOrderBuyList}" var="item" varStatus="status">
                                <li class="recordInfo">
                                    <span class="rangeType">买${status.count}</span>
                                    <span class="rangePrice"><fmt:formatNumber type="number" value="${item.pendingPrice}" maxFractionDigits="2" groupingUsed="FALSE"/></span>
                                    <span class="rangeNum"><fmt:formatNumber type="number" value="${item.restNumber}" maxFractionDigits="4" groupingUsed="FALSE"/></span>
                                    <span class="rangeAmount"><fmt:formatNumber type="number" value="${item.sumPrice}" maxFractionDigits="6" groupingUsed="FALSE"/></span>
                                </li>
                            </c:forEach>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
    </div>


    <div class="myEntrust" id="tableId" style="display:none;">
        <p class="myTitle">
            我的委托记录<img src="<%=path %>/resources/image/web/entrust.png" />
            <a href="<%=path%>/userWeb/transactionPendOrderController/show.htm" class="more">查看更多</a>
        </p>
        <table class="table" cellspacing="0 " cellpadding="0" >
            <tr class="tableTitle">
                <td class="time">委托时间</td>
                <td class="type">类型</td>
                <td class="amount">委托价格</td>
                <td class="amount">委托数量</td>
                <td class="amount">委托总价</td>
                <td class="amount">已成交数量</td>
                <td class="operate">操作</td>
            </tr>
            <tbody id="entrustRecord">
            <c:forEach items="${transactionPendOrderList}" var="item">
                <tr class="tableInfo">
                    <td class="time"><fmt:formatDate type="time" value="${item.addTime}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
                    <c:if test="${item.paymentType == 1}">
                        <td class="type rise">买入</td>
                    </c:if>
                    <c:if test="${item.paymentType == 2}">
                        <td class="type fall">卖出</td>
                    </c:if>
                    <td class="amount">$<fmt:formatNumber type="number" value="${item.pendingPrice}" maxFractionDigits="2" groupingUsed="FALSE"/></td>
                    <td class="amount"><fmt:formatNumber type="number" value="${item.pendingNumber}" maxFractionDigits="4" groupingUsed="FALSE"/></td>
                    <td class="amount rise">$<fmt:formatNumber type="number" value="${item.countPrice}" maxFractionDigits="6" groupingUsed="FALSE"/></td>
                    <td class="amount"><fmt:formatNumber type="number" value="${item.dealNumber}" maxFractionDigits="4" groupingUsed="FALSE"/></td>
                    <td class="operate"><input type="text" readonly="readonly" value="撤&nbsp;销" class="revoke" onclick="cancle('${item.pendingOrderNo}')" /></td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>


    <div class="myDeal">
        <p class="myTitle">
            最近成交记录<img src="<%=path %>/resources/image/web/deal.png" />
            <a href="<%=path %>/userWeb/dealRecord/show.htm" class="more">我的成交记录</a>
        </p>

        <p class="tableTitle">
            <span class="dealTime">成交时间</span>
            <span class="type">类型</span>
            <span class="dealAmount">成交价格</span>
            <span class="dealAmount">成交数量</span>
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
                        <td class="dealAmount">$<fmt:formatNumber type="number" value="${item.transactionPrice}" maxFractionDigits="2" groupingUsed="FALSE"/></td>
                        <td class="dealAmount"><fmt:formatNumber type="number" value="${item.currencyNumber}" maxFractionDigits="4" groupingUsed="FALSE"/></td>
                    </tr>
                </c:forEach>
            </table>
        </div>


        <input type="hidden" id="cucyId" name="cucyId" value="${transactionCurrency.currencyId}">
    </div>
</div>


<div id="helpFooter"></div>
<div id="footer"></div>

<div class="mask">
    <div class="mask_content">
        <div class="buyConfirm">
            <p class="popTitle">买入提示</p>
            <p class="popInput">
                <label class="popName">买入价格：</label>
                <span class="popInfo" id="buyPriceTips"></span>
                <input type="hidden" id="buyPriceConfirm" />
            </p>
            <p class="popInput">
                <label class="popName">买入数量：</label>
                <span class="popInfo" id="buyNumTips" ></span>
                <input type="hidden" id="buyNumConfirm" />
            </p>
            <p class="popInput">
                <label class="popName">合计：</label>
                <span class="popInfo" id="buySumTips" ></span>
            </p>
            <p class="popInput">
                <label class="popName">手续费：</label>
                <span class="popInfo" >${transactionCurrency.buyFee }%</span>
            </p>

            <div class="buttons">
                <input type="hidden" id="buyPwdConfirm" />
                <input type="text" value="取&nbsp;消" class="cancel" onfocus="this.blur()" />
                <input type="text" value="确&nbsp;定" class="yes" onfocus="this.blur()" onclick="buyHandle()" />
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
                <span class="popInfo">${transactionCurrency.sellFee }%</span>
            </p>

            <div class="buttons">
                <input type="hidden" id="sellPwdConfirm" />
                <input type="text" value="取&nbsp;消" class="cancel" onfocus="this.blur()" />
                <input type="text" value="确&nbsp;定" class="yes" onfocus="this.blur()" onclick="sellHandle()" />
            </div>
        </div>

        <div class="password_pop">
            <p class="popTitle">记住密码提示</p>
            <p class="popTips">
                <label><input type="radio" class="choose" name="password" onclick="isPwd(2);">每次登录只输入一次交易密码</label>
                <label><input type="radio" class="choose" name="password" onclick="isPwd(1);" checked>每笔交易都输入交易密码</label>
            </p>
            <p class="popInput">
                <label class="popName">支付密码<span class="star">*</span>：</label>
                <input type="password" class="entry" placeholder="您的支付密码" id="rememberPwd" name="rememberPwd" maxlength="16"
                       onkeyup="value=value.replace(/[^a-zA-Z0-9]/g,'')" onblur="value=value.replace(/[^a-zA-Z0-9]/g,'')"/>
            </p>

            <div class="buttons">
                <input type="hidden" id="payPasswordStatus" name="payPasswordStatus" value="1"/>
                <input type="text" value="取&nbsp;消" class="cancel" onfocus="this.blur()" />
                <input type="text" value="确&nbsp;定" class="yes" onfocus="this.blur()" onclick="updatePayPwd();" />
            </div>
        </div>

        <div class="revoke_pop">
            <p class="popTitle">撤销委托</p>
            <p class="popTips"><img src="<%=path %>/resources/image/web/tips.png" class="tipsImg" />确定撤销该委托内容？</p>

            <div class="buttons">
                <input type="hidden" id="pendOrderNoCancle" name="pendOrderNoCancle">
                <input type="text" value="取&nbsp;消" class="cancel" onfocus="this.blur()" />
                <input type="text" value="确&nbsp;定" class="yes" onfocus="this.blur()" onclick="goCancle()" />
            </div>
        </div>
    </div>
</div>

<script type="text/javascript" src="http://libs.baidu.com/jquery/2.1.4/jquery.min.js"></script>
<script type="text/javascript" src="<%=path %>/resources/js/loadPageWeb.js"></script>
<script type="text/javascript" src="<%=path %>/resources/js/simpleTips.js"></script>
<script type="text/javascript">
    window.onload = function() {
        count();
        var code = '${code}';
        var message = '${message}';
        var transactionPendOrderList = '${transactionPendOrderList}';
        var userSession = '${userSession}';
        if(transactionPendOrderList != null && transactionPendOrderList.length > 0 && userSession != null
             && transactionPendOrderList != "" && transactionPendOrderList != "[]"){
            //$("#tableId").style.display="inline";
            $("#tableId").css("display","inline-block");
        }

        if (code != 1 && message != "") {
            openTips(message);
            return false;
        }

    }

    //买入
    var resultBoo = false;
    function buyHandle() {
        if(resultBoo){
            return false;
        }else{
            resultBoo = true;
        }

        var buyPrice = $("#buyPriceConfirm").val();
        var buyNum = $("#buyNumConfirm").val();
        var buyPwd = $("#buyPwdConfirm").val();
        var currencyId = $("#cucyId").val();

        document.getElementById("buyPrice").value = "";
        document.getElementById("buyNum").value = "";
        document.getElementById("buyPwd").value = "";
        $("#buyMax").html("0" );
        $("#buyTotal").html("$0");

        $(".mask").fadeOut("fast");
        $(popObj).fadeOut("fast");

        $.ajax({
            url: '<%=path%>' + "/userWeb/tradeCenter/buy.htm", //方法路径URL
            data:{
                buyPrice : buyPrice,
                buyNum : buyNum,
                buyPwd : buyPwd,
                currencyId : currencyId
            },//参数
            dataType: 'json',
            type: 'POST',
            async: true, //默认异步调用 (false：同步)
            success: function (result) {
                openTips(result.message);
                if(result.code == 1){
                    entrust();
                }
                resultBoo = false;
            }, error: function () {
                resultBoo = false;
                openTips("挂单失败,请重新刷新页面后重试");
            }
        });
    }

    //卖出
    function sellHandle() {
        if(resultBoo){
            return false;
        }else{
            resultBoo = true;
        }

        var sellPrice = $("#sellPriceConfirm").val();
        var sellNum = $("#sellNumConfirm").val();
        var sellPwd = $("#sellPwdConfirm").val();
        var currencyId = $("#cucyId").val();

        $(".mask").fadeOut("fast");
        $(popObj).fadeOut("fast");

        $.ajax({
            url: '<%=path%>' + "/userWeb/tradeCenter/sell.htm", //方法路径URL
            data:{
                sellPrice : sellPrice,
                sellNum : sellNum,
                sellPwd : sellPwd,
                currencyId : currencyId
            },//参数
            dataType: 'json',
            type: 'POST',
            async: true, //默认异步调用 (false：同步)
            success: function (result) {
                openTips(result.message);
                if(result.code == 1){
                    entrust();
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
        var f = 0;
        var buyPrice = $("#buyPrice").val();
        var buyNum = $("#buyNum").val();
        var buyFee = $("#buyFee").val();

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
            if(buyPrice > 0){
                buyFee = buyFee * 100;
                var number = buyFee * (buyPrice * 100);
                buyPrice = ((buyPrice * 1000000) + (number)) / 1000000
                var total = userBalance / buyPrice;
                var tota = mulMaxNumber(total);

                $("#buyMax").html(Math.floor(tota * 1000000) / 1000000);
            }else{
                $("#buyMax").html("0");
            }

        } else {
            $("#buyMax").html("0" );
            $("#buyTotal").html("$0");
        }


        //卖出
        var s = 0;
        var z = 0;
        var sellPrice = $("#sellPrice").val();
        var sellNum = $("#sellNum").val();
        if (sellPrice != null && sellPrice != "") {
            sellPrice = sellPrice.toString();
            try{s+=sellPrice.split(".")[1].length}catch(e){}
            try{z+=sellPrice.split(".")[1].length}catch(e){}
            if (sellNum != null && sellNum != "") {
                sellNum = sellNum.toString();
                try{s+=sellNum.split(".")[1].length}catch(e){}
                var number = parseFloat((Number(sellPrice.replace(".",""))*Number(sellNum.replace(".",""))/Math.pow(10,s)).toFixed(8));
                number = mulMaxNumber(number);
                $("#sellTotal").html("$" + number);
            }

            var currencyNumber = $("#currencyNumber").val();
            if(currencyNumber != null && currencyNumber != ""){
                currencyNumber = currencyNumber.toString();
                try{z+=currencyNumber.split(".")[1].length}catch(e){}
                var number = parseFloat((Number(sellPrice.replace(".",""))*Number(currencyNumber.replace(".",""))/Math.pow(10,z)).toFixed(8));
                number = mulMaxNumber(number);
                $("#sellMax").html(number);
            }
        } else {
            $("#sellMax").html("0");
            $("#sellTotal").html("$0");
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

    var popObj;
    $(function(){
        $(".buyBtn").click(function(){
            var buyPrice = $("#buyPrice").val();
            var buyNum = $("#buyNum").val();
            var buyTotal = $("#buyTotal").html();
            var buyPwd = $("#buyPwd").val();
            var currencyId = $("#cucyId").val();

            $("#buyPriceConfirm").val(buyPrice);
            $("#buyNumConfirm").val(buyNum);
            $("#buyPwdConfirm").val(buyPwd);

            document.getElementById("buyPrice").value = "";
            document.getElementById("buyNum").value = "";
            document.getElementById("buyPwd").value = "";
            $("#buyMax").html("0");
            $("#buyTotal").html("$0");

            var user = '${userSession}';
            if (user == null || user == "") {
                openTips("请先登录再操作");
                return;
            }

            if(buyPrice == null || buyPrice == ""){
                openTips("价格不能为空");
                return;
            }

            if(buyPrice <= 0){
                openTips("价格不能小于等于0");
                resultBoo = false;
                return;
            }

            if(buyNum == null || buyNum == ""){
                openTips("数量不能为空");
                return;
            }

            if(buyNum <= 0){
                openTips("数量不能小于等于0");
                return;
            }

            if((buyPwd == null || buyPwd == "") && parseInt('${userSession.isPwd}') == 1){
                openTips("交易密码不能为空");
                return;
            }

            if(currencyId == null || currencyId == ""){
                openTips("参数获取错误，请刷新页面重试");
                return;
            }

            $("#buyPriceTips").html("$" + buyPrice );
            $("#buyNumTips").html(buyNum );
            $("#buySumTips").html(buyTotal );

            $(".mask").fadeIn();
            $(".buyConfirm").fadeIn();
            popObj = ".buyConfirm"
        });
        $(".sellBtn").click(function(){
            var sellPrice = $("#sellPrice").val();
            var sellNum = $("#sellNum").val();
            var sellTotal = $("#sellTotal").html();
            var sellPwd = $("#sellPwd").val();
            var currencyId = $("#cucyId").val();

            $("#sellPriceConfirm").val(sellPrice);
            $("#sellNumConfirm").val(sellNum);
            $("#sellPwdConfirm").val(sellPwd);

            document.getElementById("sellPrice").value = "";
            document.getElementById("sellNum").value = "";
            document.getElementById("sellPwd").value = "";
            $("#sellMax").html("0");
            $("#sellTotal").html("$0");

            var user = '${userSession}';
            if (user == null || user == "") {
                openTips("请先登录再操作");
                return;
            }

            if(sellPrice == null || sellPrice == ""){
                openTips("价格不能为空");
                return;
            }

            if(sellPrice <= 0){
                openTips("价格不能小于等于0");
                return;
            }

            if(sellNum == null || sellNum == ""){
                openTips("数量不能为空");
                return;
            }

            var userIsPwd = '${userSession.isPwd}';
            if((sellPwd == null || sellPwd == "") && userIsPwd == 1){
                openTips("交易密码不能为空");
                return;
            }

            if(currencyId == null || currencyId == ""){
                openTips("参数获取错误，请刷新页面重试");
                return;
            }

            $("#sellPriceTips").html("$" + sellPrice );
            $("#sellNumTips").html(sellNum );
            $("#sellSumTips").html(sellTotal );

            $(".mask").fadeIn();
            $(".sellConfirm").fadeIn();
            popObj = ".sellConfirm"
        });
        $(".setting").click(function(){
            $(".mask").fadeIn();
            $(".password_pop").fadeIn();
            popObj = ".password_pop"
        });
        $(".revoke").click(function(){
            // $(".mask").fadeIn();
            // $(".revoke_pop").fadeIn();
            // popObj = ".revoke_pop"
        });
        $(".cancel").click(function(){
            $(".mask").fadeOut("fast");
            $(popObj).fadeOut("fast");
        });
        $(".yes").click(function(){
            // $(".mask").fadeOut("fast");
            // $(popObj).fadeOut("fast");
        });
    });

    var start = 5;
    var step = -1;
    function count()
    {
        document.getElementById("countNum").innerHTML = start;
        if (start <= 0) {
            var user = '${userSession}';
            if (user != null && user != "") {
                //登录后进行的操作
                //刷新委托记录
                entrust();
                //获取交易相关价格（用户资金信息）
                userMessage();
            }
            //刷新成交记录
            reDeal();
            //获取交易相关价格（基准信息）
            gainDealPrice();

            //刷新挂单记录
            rePend();
        }

        start += step;
        if(start < 0)
        start = 5;
        setTimeout("count()",1000);

    }

    //更改交易密码状态
    function isPwd(type) {
        $("#payPasswordStatus").val(type);
    }

    var PayPwdBoo = false;
    function updatePayPwd() {
        if (PayPwdBoo) {
            return;
        } else {
            PayPwdBoo = true;
        }

        var rememberPwd = $("#rememberPwd").val();
        var payPasswordStatus = parseInt($("#payPasswordStatus").val());

        if(payPasswordStatus != 1 && payPasswordStatus != 2){
            openTips("参数错误，请刷新页面重试");
            PayPwdBoo = false;
            return;
        }

        if(rememberPwd == null || rememberPwd == ""){
            openTips("交易密码不能为空");
            PayPwdBoo = false;
            return;
        }

        $.ajax({
            url: '<%=path%>' + "/userWeb/tradeCenter/rememberPwd.htm", //方法路径URL
            data:{
                rememberPwd : rememberPwd,
                payPasswordStatus : payPasswordStatus
            },//参数
            dataType: 'json',
            type: 'POST',
            async: true, //默认异步调用 (false：同步)
            success: function (result) {
                resultBoo = false;
                $(".mask").fadeOut("fast");
                $(popObj).fadeOut("fast");
                openTips(result.message);
                setTimeout("location.reload()",1000 );
            }, error: function () {
                resultBoo = false;
                $(".mask").fadeOut("fast");
                $(popObj).fadeOut("fast");
                openTips("修改失败,请重新刷新页面后重试");
            }
        });

    }

    /** 挂单记录 */
    var pendBoo = false;
    function rePend() {
        if (pendBoo) {
            return;
        } else {
            pendBoo = true;
        }

        var currencyId = $("#cucyId").val();
        if (currencyId == null || currencyId == "") {
            pendBoo = false;
            openTips("参数获取错误，请刷新页面重试");
            return;
        }

        $.ajax({
            url: '<%=path%>' + "/userWeb/tradeCenter/pend", //方法路径URL
            data:{
                currencyId : currencyId
            },//参数
            dataType: 'json',
            type: 'POST',
            async: true, //默认异步调用 (false：同步)
            success: function (result) {
                if(result.code != 1 && result.message != null) {
                    pendBoo = false;
                    openTips(result.message);
                    if(result.code == 5){
                        setTimeout("returnWebHome()",2000 );
                    }
                    return;
                }
                var data = result.data;
                var orderSellList = data.transactionPendOrderSellList; //卖出
                var orderBuyList = data.transactionPendOrderBuyList;  //买入
                //卖出挂单
                var newChildSell= "";
                $("#orderSellReId").empty() ;
                for (var i=0;i<=orderSellList.length-1;i++) {
                    var orderSell = orderSellList[i];

                    var pendingPrice = Math.floor(orderSell.pendingPrice * 100) / 100;  //单价
                    var pendingNumber = Math.floor(orderSell.restNumber * 100000) / 100000;  //数量
                    var sumPrice = Math.floor(orderSell.sumPrice * 1000000) / 1000000;  //总额

                    newChildSell += '<li class="recordInfo">' +
                                        '<span class="rangeType">卖' + (orderSellList.length-i) + '</span>' +
                                        '<span class="rangePrice">' + pendingPrice + '</span>' +
                                        '<span class="rangeNum">' + pendingNumber + '</span>' +
                                        '<span class="rangeAmount">' + sumPrice + '</span>' +
                                    '</li>';
                }
                $("#orderSellReId").html(newChildSell);

                //买入挂单
                var newChildBuy= "";
                $("#orderBuyReId").empty() ;
                for (var i=0;i<=orderBuyList.length-1;i++) {
                    var orderBuy = orderBuyList[i];

                    var pendingPrice = Math.floor(orderBuy.pendingPrice * 100) / 100;  //单价
                    var pendingNumber = Math.floor(orderBuy.restNumber * 100000) / 100000;  //数量
                    var sumPrice = Math.floor(orderBuy.sumPrice * 1000000) / 1000000;  //总额

                    newChildBuy += '<li class="recordInfo">' +
                                        '<span class="rangeType">买' + (i+1) + '</span>' +
                                        '<span class="rangePrice">' + pendingPrice + '</span>' +
                                        '<span class="rangeNum">' + pendingNumber + '</span>' +
                                        '<span class="rangeAmount">' + sumPrice + '</span>' +
                                    '</li>';
                }
                $("#orderBuyReId").html(newChildBuy);
                pendBoo = false;
            }, error: function () {
                pendBoo = false;
                openTips("获取失败,请重新刷新页面后重试");
            }
        });

    }


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
            openTips("参数获取错误，请刷新页面重试")
            return;
        }

        $.ajax({
            url: '<%=path%>' + "/userWeb/tradeCenter/deal", //方法路径URL
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
                    if(result.code == 5){
                        setTimeout("returnWebHome()",2000 );
                    }

                    return;
                }
                var data = result.data;
                var dealList = data.dealList;
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
                    var transactionPrice = Math.floor(deal.transactionPrice * 1000) / 1000;
                    var currencyNumber = Math.floor(deal.currencyNumber * 100000) / 100000;
                    var currencyTotalPrice = Math.floor(deal.currencyTotalPrice * 1000000) / 1000000;

                    newChild += "<tr class='tableInfo'>" +
                                    "<td class='dealTime'>"+ addTime +"</td>" +
                                    "<td class='type " + type + "'>" + paymentType + "</td>" +
                                    "<td class='dealAmount'>" + "$"+ transactionPrice + "</td>" +
                                    "<td class='dealAmount'>" + currencyNumber +"</td>" +
                                    "<td class='dealAmount rise'>$" + currencyTotalPrice + "</td>" +
                                "</tr>";

                }
                document.getElementById("dealOrder").innerHTML = newChild;
                dealBoo = false;
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

    function cancle(pendOrderNo) {
        if (pendOrderNo == "" || pendOrderNo == null) {
            openTips("单号错误");
            return;
        }

        $("#pendOrderNoCancle").val(pendOrderNo);

        $(".mask").fadeIn();
        $(".revoke_pop").fadeIn();
        popObj = ".revoke_pop"
    }

    var calMoreBoo = false;
    function goCancle() {
        if (calMoreBoo) {
            return;
        } else {
            calMoreBoo = true;
        }

        var pendOrderNo = $("#pendOrderNoCancle").val();

        $(".mask").fadeOut("fast");
        $(popObj).fadeOut("fast");

        $.ajax({
            url: '<%=path %>' + "/userWeb/transactionPendOrderController/revoke.htm",
            data: {
                pendingOrderNo: pendOrderNo
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

                entrust();
                calMoreBoo = false;

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

        var cucyId = $("#cucyId").val();
        if (cucyId == null || cucyId == "") {
            entrustBoo = false;
            openTips("参数获取错误，请刷新页面重试")
            return;
        }

        $.ajax({
            url: '<%=path%>' + "/userWeb/tradeCenter/entrust.htm", //方法路径URL
            data: {
                currencyId: cucyId

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
                    $("#tableId").css("display","inline-block");
                    var newChild = "";

                    for (var i = 0; i <= dealList.length - 1; i++) {
                        var deal = dealList[i];
                        var addTime = formatDateTime(deal.addTime);
                        var paymentType = "";
                        var type = "";
                        if (deal.paymentType == 1) {
                            paymentType = "买入";
                            type = "rise";
                        }
                        if (deal.paymentType == 2) {
                            paymentType = "卖出";
                            type = "fall";
                        }
                        var pendingPrice = deal.pendingPrice;
                        var pendingNumber = deal.pendingNumber ;
                        var currencyTotalPrice = deal.countPrice;
                        var pendingOrderNo = deal.pendingOrderNo;
                        var dealNumber = deal.dealNumber;
                        var goCancle = "cancle('"+ pendingOrderNo + "')";
                        newChild += "<tr class='tableInfo'>" +
                            "<td class='time'>" + addTime + "</td>" +
                            "<td class='type " + type + "'>" + paymentType + "</td>" +
                            "<td class='amount'>" + "$" + pendingPrice + "</td>" +
                            "<td class='amount'>" + pendingNumber + "</td>" +
                            "<td class='amount rise'>" + "$" + currencyTotalPrice+ "</td>" +
                            "<td class='amount'>"+ dealNumber +"</td>" +
                            "<td class='operate'><input type='text' readonly='readonly' value='撤&nbsp;销' class='revoke' onclick="+ goCancle + "></td>" +
                            "</tr>";
                    }
                    document.getElementById("entrustRecord").innerHTML = newChild;
                }else{
                    //$("#tableId").style.display="none";
                    $("#tableId").css("display","none");
                }
                entrustBoo = false;
            }, error: function () {
                entrustBoo = false;
                openTips("获取失败,请重新刷新页面后重试");
            }
        });
    }

    /**获取交易相关价格（基准信息）*/
    var gainDealPriceBoo = false;
    function gainDealPrice(){
        if (gainDealPriceBoo) {
            return;
        } else {
            gainDealPriceBoo = true;
        }

        var currencyId = $("#cucyId").val();

        $.ajax({
            url: '<%=path %>' + "/userWeb/tradeCenter/gainDealPrice",
            data: {
                currencyId : currencyId
            },//参数
            dataType: "json",
            type: 'POST',
            async: true, //默认异步调用 (false：同步)
            success: function (resultData) {
                gainDealPriceBoo = false;
                var code = resultData.code;
                var message = resultData.message;
                if (code != 1 && message != "") {
                    openTips(message);
                    return;
                }

                var data = resultData.data;
                var standardParameter = data.standardParameter;
                if(standardParameter != null){
                    $("#nowPrice").html(standardParameter.nowPrice);
                    $("#nowPriceShow").html("最新成交价：$" + standardParameter.nowPrice);
                    if(standardParameter.todayRange >= 0){
                        $("#todayRangeRise").html("+" + standardParameter.todayRange + "%");
                        $("#todayRangeRise").removeClass("number fall");
                        $("#todayRangeRise").addClass("number rise");
                    } else{
                        $("#todayRangeRise").html(standardParameter.todayRange  + "%");
                        $("#todayRangeRise").removeClass("number rise");
                        $("#todayRangeRise").addClass("number fall");
                    }
                    $("#todayMax").html(standardParameter.todayMax);
                    $("#hintTodayMax").html("$" + standardParameter.todayMax);
                    $("#todayMin").html(standardParameter.todayMin);
                    $("#hintTodayMin").html("$" + standardParameter.todayMin);
                    $("#buyOne").html(standardParameter.buyOne);
                    $("#optimumSell").html("$" + standardParameter.buyOne);
                    $("#sellOne").html(standardParameter.sellOne);
                    $("#optimumBuy").html("$" + standardParameter.sellOne);
                    $("#dayTurnove").html(standardParameter.dayTurnove);
                }
            },

            error: function () {
                gainDealPriceBoo = false;
                openTips("数据加载出错，请稍候重试");
            }
        });

    }

    /**获取交易相关价格（用户资金信息）*/
    var userMessageBoo = false;
    function userMessage(){
        if (userMessageBoo) {
            return;
        } else {
            userMessageBoo = true;
        }

        var currencyId = $("#cucyId").val();
        $.ajax({
            url: '<%=path %>' + "/userWeb/tradeCenter/userMessage",
            data: {
                currencyId : currencyId
            },//参数
            dataType: "json",
            type: 'POST',
            async: true, //默认异步调用 (false：同步)
            success: function (resultData) {
                userMessageBoo = false;
                var code = resultData.code;
                var message = resultData.message;
                if (code != 1 && message != "") {
                    openTips(message);
                    return;
                }

                var data = resultData.data;
                var userDealCapitalMessage = data.userDealCapitalMessage;
                if(userDealCapitalMessage != null){
                    $("#currencyNumberShow").html(userDealCapitalMessage.currencyNumber);
                    $("#currencyNumber").val(userDealCapitalMessage.currencyNumber);
                    $("#usableCurrencyNumber").html(userDealCapitalMessage.currencyNumber);
                    $("#currencyNumberLockShow").html(userDealCapitalMessage.currencyNumberLock);
                    $("#userBalanceShow").html("$" + userDealCapitalMessage.userBalance);
                    $("#userBalance").val(userDealCapitalMessage.userBalance);
                    $("#usableUserBalance").html("$" + userDealCapitalMessage.userBalance);
                    $("#userBalanceLockShow").html("$" + userDealCapitalMessage.userBalanceLock);
                    $("#currencyNumberSumShow").html("$" + userDealCapitalMessage.currencyNumberSum);
                }
            },

            error: function () {
                userMessageBoo = false;
                openTips("数据加载出错，请稍候重试");
            }
        });
    }

    //返回首页
    function returnWebHome(){
        window.location.href = "<%=path%>" + "/userWeb/homePage/show";
    }
</script>
</body>
</html>