<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/resources/page/common/path.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <link rel="icon" href="<%=path %>/resources/image/web/icon.ico" type="image/x-ico" />
    <link rel="stylesheet" type="text/css" href="<%=path %>/resources/css/web/tradeOut.css" />
    <link rel="stylesheet" type="text/css" href="<%=path %>/resources/css/web/public.css" />
    <link rel="stylesheet" type="text/css" href="<%=path %>/resources/css/web/simpleTips.css" />
    <script type="text/javascript" src="http://libs.baidu.com/jquery/2.1.4/jquery.min.js"></script>
    <script type="text/javascript" src="<%=path %>/resources/js/loadPageWeb.js"></script>
    <script type="text/javascript" src="<%=path %>/resources/js/simpleTips.js"></script>
    <title>场外交易</title>
</head>
<body>
<header id="header"></header>
<div id="menu"></div>


<div class="content">
    <div class="askArea">
        <form id="queryForm" action="<%=path %>/userWeb/otcTradeCenter/show" method="post">
            <p class="condition">币种：
                <select class="askSelect" id="currencyId" name="currencyId">
                    <option value="999">XT</option>
                </select>
            </p>
            <p class="condition">类型：
                <select class="askSelect" id="orderType" name="orderType">
                    <option value="0">全部</option>
                    <option value="1">购买</option>
                    <option value="2">出售</option>
                </select>
            </p>
            <p class="condition">地区：
                <select class="askSelect" id="area" name="area">
                    <option value="中国(CN)">中国(CN)</option>
                </select>
            </p>
            <input type="hidden" id="queryPageNumber" name="pageNumber" value="${pageNumber}">
            <input type="text" value="查&nbsp;询" class="ask" onfocus="this.blur()" onclick="queryForm()" />
        </form>
    </div>

    <div class="main">
        <table class="table" cellspacing="0" cellpadding="0">
            <tr class="tableTitle">
                <td class="name">经销商名称</td>
                <td class="coin">币种</td>
                <td class="area">地区</td>
                <td class="type">类型</td>
                <td class="limit">交易限额</td>
                <td class="proportion">购买比例</td>
                <td class="operate">操作</td>
            </tr>
            <c:forEach items="${otcTransactionPendOrderList}" var="pendOrder">
                <tr class="tableInfo">
                    <td class="name">${pendOrder.dealerName}</td>
                    <td class="coin">${pendOrder.currencyName}</td>
                    <td class="area">${pendOrder.area}</td>
                    <c:if test="${pendOrder.orderType == 1}">
                        <td class="type buy">购买</td>
                    </c:if>
                    <c:if test="${pendOrder.orderType == 2}">
                        <td class="type sale">出售</td>
                    </c:if>
                    <td class="limit"><span><fmt:formatNumber type="number" value="${pendOrder.minNumber }" groupingUsed="FALSE" maxFractionDigits="2"/>
                            </span>~<span><fmt:formatNumber type="number" value="${pendOrder.maxNumber }" groupingUsed="FALSE" maxFractionDigits="2"/></span>CNY</td>
                    <td class="proportion">1:<fmt:formatNumber type="number" value="${pendOrder.pendingRatio }" groupingUsed="FALSE" maxFractionDigits="4"/></td>
                    <td class="operate">
                        <c:if test="${pendOrder.orderType == 1}">
                            <input type="text" value="我要购买" class="tradeBuy" onfocus="this.blur()"
                                   onclick="buy('${pendOrder.otcPendingOrderNo}','${pendOrder.userId}','${pendOrder.pendingRatio}',
                                           '${pendOrder.minNumber}','${pendOrder.maxNumber}');"/>
                        </c:if>
                        <c:if test="${pendOrder.orderType == 2}">
                            <input type="text" value="我要出售" class="tradeSale" onfocus="this.blur()"
                                    onclick="sell('${pendOrder.otcPendingOrderNo}','${pendOrder.dealerName}','${pendOrder.pendingRatio}',
                                            '${pendOrder.minNumber}','${pendOrder.maxNumber}');"/>
                        </c:if>
                    </td>
                </tr>
            </c:forEach>
        </table>

        <jsp:include page="/resources/page/common/paging.jsp"></jsp:include>

    </div>
</div>


<div id="helpFooter"></div>
<div id="footer"></div>

<div class="mask">
    <div class="mask_content">
        <div class="buy_pop">
            <p class="popTitle">我要购买</p>

            <p class="popInput">
                <label class="popName">购买数量<span class="star">*</span>：</label>
                <input type="text" class="entry" placeholder="您要购买的数量，单位：个" id="buyNumber"
                       onkeyup="matchUtil(this, 'double', 4)" onblur="matchUtil(this, 'double', 4)" maxlength="11"/>
                <input type="hidden" name="pendingRatio" id="pendingRatio" value="0" />
                <input type="hidden" name="orderNo" id="orderNo" value="0" />
                <span class="remind">交易比例为：<span id="ratio"></span>，<span>XT</span>：兑换货币</span>
            </p>
            <p class="popInput">
                <label class="popName">总价：</label>
                <span class="popMoney" id="buySum">¥0</span>
                <input type="hidden" id="buyMin">
                <input type="hidden" id="buyMax">
            </p>
            <p class="popInput">
                <label class="popName">支付方式<span class="star">*</span>：</label>
                <select class="popSelected" id="paymentType" name="paymentType">
                    <option value="0" disabled selected>选择支付方式</option>
                    <option value="1">银行卡转账</option>
                    <option value="2">支付宝转账</option>
                    <option value="3">微信转账</option>
                </select>
            </p>

            <div class="buttons">
                <input type="text" value="取&nbsp;消" class="cancel" onfocus="this.blur()" />
                <input type="text" value="去支付" class="pay" onfocus="this.blur()" onclick="toPay();"/>
            </div>
        </div>

        <div class="pay_pop" id="bankPay">
            <p class="popTitle">银行卡转账</p>

            <div class="overflow">
                <div class="sellerInfo">
                    <p class="popInfo">
                        <label class="popName">商家名称：</label>
                        <span class="popWord" id="dealerName"></span>
                    </p>
                    <p class="popInfo">
                        <label class="popName">商家手机：</label>
                        <span class="popWord" id="userPhone"></span>
                    </p>
                    <p class="popInfo">
                        <label class="popName">商家姓名：</label>
                        <span class="popWord" id="userName"></span>
                    </p>
                </div>

                <div class="payInfo">
                    <p class="popInfo">
                        <label class="popName">商家银行卡号：</label>
                        <span class="popWord" id="bankCard"></span>
                    </p>
                    <p class="popInfo">
                        <label class="popName">收款银行：</label>
                        <span class="popWord" id="bankName"></span>
                    </p>
                    <p class="popInfo">
                        <label class="popName">收款人姓名：</label>
                        <span class="popWord" id="paymentName"></span>
                    </p>
                    <p class="popInfo">
                        <label class="popName">收款人手机号：</label>
                        <span class="popWord" id="paymentPhone"></span>
                    </p>
                </div>

                <div class="moneyInfo">
                    <p class="popInfo">
                        <label class="popName">购买数量：</label>
                        <span class="popWord" id="buyNumFinal"></span>
                    </p>
                    <p class="popInfo">
                        <label class="popName">支付金额：</label>
                        <span class="popWord" id="buySumFinal"></span>
                    </p>
                </div>

                <div class="prompt">提示：请及时保存转账信息。若支付完成商家在24小时内未即时转账，请咨询客服中心处理。</div>
            </div>

            <div class="buttons">
                <input type="hidden" name="orderNo" id="buyOrderNo" value="0" />
                <input type="text" value="取&nbsp;消" class="cancel" onfocus="this.blur()" />
                <input type="text" value="确&nbsp;定" class="yes" onfocus="this.blur()" onclick="buyFinal(1);"/>
            </div>
        </div>

        <div class="pay_pop" id="aliPay">
            <p class="popTitle">支付宝转账</p>

            <div class="overflow">
                <div class="sellerInfo">
                    <p class="popInfo">
                        <label class="popName">商家名称：</label>
                        <span class="popWord" id="aliDealerName"></span>
                    </p>
                    <p class="popInfo">
                        <label class="popName">商家手机：</label>
                        <span class="popWord" id="aliUserPhone"></span>
                    </p>
                    <p class="popInfo">
                        <label class="popName">商家姓名：</label>
                        <span class="popWord" id="aliUserName"></span>
                    </p>
                </div>

                <div class="payInfo">
                    <p class="popInfo">
                        <label class="popName">商家支付宝：</label>
                        <span class="popWord" id="aliAccount"></span>
                    </p>
                    <p class="popInfo">
                        <label class="popName">二维码：</label>
                        <span class="popWord" id="aliImage"></span>
                    </p>
                </div>

                <div class="moneyInfo">
                    <p class="popInfo">
                        <label class="popName">购买数量：</label>
                        <span class="popWord" id="aliNum"></span>
                    </p>
                    <p class="popInfo">
                        <label class="popName">支付金额：</label>
                        <span class="popWord" id="aliSum"></span>
                    </p>
                </div>

                <div class="prompt">提示：请及时保存转账信息。若支付完成商家在24小时内未即时转账，请咨询客服中心处理。</div>
            </div>


            <div class="buttons">
                <input type="text" value="取&nbsp;消" class="cancel" onfocus="this.blur()" />
                <input type="text" value="确&nbsp;定" class="yes" onfocus="this.blur()" onclick="buyFinal(2);"/>
            </div>
        </div>

        <div class="pay_pop" id="weiXinPay">
            <p class="popTitle">微信转账</p>

            <div class="overflow">
                <div class="sellerInfo">
                    <p class="popInfo">
                        <label class="popName">商家名称：</label>
                        <span class="popWord" id="wxDealerName"></span>
                    </p>
                    <p class="popInfo">
                        <label class="popName">商家手机：</label>
                        <span class="popWord" id="wxUserPhone"></span>
                    </p>
                    <p class="popInfo">
                        <label class="popName">商家姓名：</label>
                        <span class="popWord" id="wxUserName"></span>
                    </p>
                </div>

                <div class="payInfo">
                    <p class="popInfo">
                        <label class="popName">商家微信：</label>
                        <span class="popWord" id="wxAccount"></span>
                    </p>
                    <p class="popInfo">
                        <label class="popName">二维码：</label>
                        <span class="popWord" id="wxImage"></span>
                    </p>
                </div>

                <div class="moneyInfo">
                    <p class="popInfo">
                        <label class="popName">购买数量：</label>
                        <span class="popWord" id="wxNum"></span>
                    </p>
                    <p class="popInfo">
                        <label class="popName">支付金额：</label>
                        <span class="popWord" id="wxSum"></span>
                    </p>
                </div>

                <div class="prompt">提示：请及时保存转账信息。若支付完成商家在24小时内未即时转账，请咨询客服中心处理。</div>
            </div>


            <div class="buttons">
                <input type="text" value="取&nbsp;消" class="cancel" onfocus="this.blur()" />
                <input type="text" value="确&nbsp;定" class="yes" onfocus="this.blur()" onclick="buyFinal(3);"/>
            </div>
        </div>

        <div class="sell_pop">
            <p class="popTitle">我要出售</p>

            <div class="overflow">
                <p class="popInput">
                    <label class="popName">经销商名称：</label>
                    <span class="popWord" id="sellDealerName"></span>
                </p>
                <p class="popInput">
                    <label class="popName">出售数量<span class="star">*</span>：</label>
                    <input type="hidden" name="pendingRatio" id="sellRatio" value="0" />
                    <input type="hidden" name="orderNo" id="sellOrderNo" value="0" />
                    <input type="text" class="entry" placeholder="您要出售的数量，单位：个" id="sellNumber"
                           onkeyup="matchUtil(this, 'double', 4)" onblur="matchUtil(this, 'double', 4)" maxlength="11"/>
                    <span class="remind">交易比例为：<span id="ratioSell"></span>，<span>XT</span>：兑换货币</span>
                </p>
                <p class="popInput">
                    <label class="popName">总价：</label>
                    <span class="popMoney" id="sellSum">¥0</span>
                    <input type="hidden" id="sellMin">
                    <input type="hidden" id="sellMax">
                </p>

                <p class="popInput">
                    <label class="popName">收款方式<span class="star">*</span>：</label>
                    <select class="popSelected" onchange="change(this.value)" id="sellPayType">
                        <option value="0" disabled selected>选择收款方式</option>
                        <option value="1">银行卡转账</option>
                        <option value="2">支付宝转账</option>
                        <option value="3">微信转账</option>
                    </select>
                </p>

                <div class="card">
                    <p class="popInput">
                        <label class="popName">银行卡号<span class="star">*</span>：</label>
                        <input type="text" class="entry" placeholder="您的银行卡号" id="sellBankCard" maxlength="19"
                               onkeyup="value=value.replace(/[^\d]/g,'')" onblur="value=value.replace(/[^\d]/g,'')"/>
                    </p>
                    <p class="popInput">
                        <label class="popName">银行名称<span class="star">*</span>：</label>
                        <input type="text" class="entry" placeholder="该银行卡的银行名称" id="sellBankName" maxlength="15"
                               onkeyup="value=value.replace(/[^a-zA-Z0-9\u4E00-\u9FA5]/g,'')" onblur="value=value.replace(/[^a-zA-Z0-9\u4E00-\u9FA5]/g,'')"/>
                    </p>
                    <p class="popInput">
                        <label class="popName">支行名称<span class="star">*</span>：</label>
                        <input type="text" class="entry" placeholder="该卡的支行名称" id="sellBankBranch" maxlength="50"
                               onkeyup="value=value.replace(/[^a-zA-Z0-9\u4E00-\u9FA5]/g,'')" onblur="value=value.replace(/[^a-zA-Z0-9\u4E00-\u9FA5]/g,'')"/>
                    </p>
                    <p class="popInput">
                        <label class="popName">预留姓名<span class="star">*</span>：</label>
                        <input type="text" class="entry" placeholder="该银行卡的银行预留姓名" id="sellPaymentName" maxlength="30"
                               onkeyup="value=value.replace(/[^a-zA-Z0-9\u4E00-\u9FA5]/g,'')" onblur="value=value.replace(/[^a-zA-Z0-9\u4E00-\u9FA5]/g,'')"/>
                    </p>
                    <p class="popInput">
                        <label class="popName">预留电话<span class="star">*</span>：</label>
                        <input type="text" class="entry" placeholder="该银行卡的银行预留电话" id="sellPaymentPhone"
                               maxlength="11" onkeyup="matchUtil(this, 'number')" onblur="matchUtil(this, 'number')"/>
                    </p>
                </div>

                <div class="ali">
                    <p class="popInput">
                        <label class="popName">支付宝账号<span class="star">*</span>：</label>
                        <input type="text" class="entry" placeholder="您的支付宝账号" id="sellAliAccount" maxlength="30"/>
                    </p>
                    <p class="popInput">
                        <label class="popName">收款码<span class="star">*</span>：</label>
                        <span class="pic">
                        <input type="text" id="changead_t1"  class="choosePic" placeholder="请选择二维码图片" onfocus="this.blur()" />
                        <input type="text"  onclick="document.getElementById('changead_a1').click();"  value="选择文件" class="choose_button" onfocus="this.blur();" />
                        <input type="file" class="file" id="changead_a1" onchange="checkFileImageTwo(this, 'changead_t1');" />
                    </span>
                    </p>
                </div>

                <div class="wechat">
                    <p class="popInput">
                        <label class="popName">微信账号<span class="star">*</span>：</label>
                        <input type="text" class="entry" placeholder="您的微信账号" id="sellWxAccount" maxlength="30"
                               onkeyup="value=value.replace(/[^a-zA-Z0-9]/g,'')" onblur="value=value.replace(/[^a-zA-Z0-9]/g,'')"/>
                    </p>
                    <p class="popInput">
                        <label class="popName">收款码<span class="star">*</span>：</label>
                        <span class="pic">
                            <input type="text" id="changead_t2"  class="choosePic" placeholder="请选择二维码图片" onfocus="this.blur()" />
                            <input type="text"  onclick="document.getElementById('changead_a2').click();"  value="选择文件" class="choose_button" onfocus="this.blur();" />
                            <input type="file" class="file" id="changead_a2" onchange="checkFileImageTwo(this, 'changead_t2');" />
                        </span>
                    </p>
                </div>
            </div>

            <div class="buttons">
                <input type="text" value="取&nbsp;消" class="cancel" onfocus="this.blur()" />
                <input type="text" value="确&nbsp;定" class="confirm" onfocus="this.blur()" onclick="toSell();"/>
            </div>
        </div>

        <div class="confirm_pop" id="bankConfirm">
            <p class="popTitle">出售确认</p>

            <p class="popInfo">
                <label class="popName">经销商名称：</label>
                <span class="popWord" id="bankConfirmDealer"></span>
            </p>
            <p class="popInfo">
                <label class="popName">出售数量：</label>
                <span class="popWord" id="bankConfirmNum"></span>
            </p>
            <p class="popInfo">
                <label class="popName">总价：</label>
                <span class="popWord" id="bankConfirmSum"></span>
            </p>
            <p class="popInfo">
                <label class="popName">收款方式：</label>
                <span class="popWord" >银行转账</span>
            </p>
            <p class="popInfo" >
                <label class="popName">银行卡号：</label>
                <span class="popWord" id="bankConfirmBankCard"></span>
            </p>
            <p class="popInfo" >
                <label class="popName">银行名称：</label>
                <span class="popWord" id="bankConfirmBankName"></span>
            </p>
            <p class="popInfo" >
                <label class="popName">支行名称：</label>
                <span class="popWord" id="bankConfirmBankBranch"></span>
            </p>
            <p class="popInfo" >
                <label class="popName">预留姓名：</label>
                <span class="popWord" id="bankConfirmPaymentName"></span>
            </p>
            <p class="popInfo" >
                <label class="popName">预留电话：</label>
                <span class="popWord" id="bankConfirmPaymentPhone"></span>
            </p>

            <div class="buttons">
                <input type="text" value="取&nbsp;消" class="cancel" onfocus="this.blur()" />
                <input type="text" value="确&nbsp;定" class="yes" onfocus="this.blur()" onclick="sellFinal(1);"/>
            </div>
        </div>

        <div class="confirm_pop" id="aliConfirm">
            <p class="popTitle">出售确认</p>

            <p class="popInfo">
                <label class="popName">经销商名称：</label>
                <span class="popWord" id="aliConfirmDealer"></span>
            </p>
            <p class="popInfo">
                <label class="popName">出售数量：</label>
                <span class="popWord" id="aliConfirmNum"></span>
            </p>
            <p class="popInfo">
                <label class="popName">总价：</label>
                <span class="popWord" id="aliConfirmSum"></span>
            </p>
            <p class="popInfo">
                <label class="popName">收款方式：</label>
                <span class="popWord">支付宝转账</span>
            </p>
            <p class="popInfo" >
                <label class="popName"><span>支付宝</span>账号：</label>
                <span class="popWord" id="aliConfirmAccount"></span>
            </p>

            <div class="buttons">
                <input type="text" value="取&nbsp;消" class="cancel" onfocus="this.blur()" />
                <input type="text" value="确&nbsp;定" class="yes" onfocus="this.blur()" onclick="sellFinal(2);"/>
            </div>
        </div>

        <div class="confirm_pop" id="wxConfirm">
            <p class="popTitle">出售确认</p>

            <p class="popInfo">
                <label class="popName">经销商名称：</label>
                <span class="popWord" id="wxConfirmDealer"></span>
            </p>
            <p class="popInfo">
                <label class="popName">出售数量：</label>
                <span class="popWord" id="wxConfirmNum"></span>
            </p>
            <p class="popInfo">
                <label class="popName">总价：</label>
                <span class="popWord" id="wxConfirmSum"></span>
            </p>
            <p class="popInfo">
                <label class="popName">收款方式：</label>
                <span class="popWord">微信转账</span>
            </p>
            <p class="popInfo" >
                <label class="popName"><span>微信</span>账号：</label>
                <span class="popWord" id="wxConfirmAccount"></span>
            </p>

            <div class="buttons">
                <input type="text" value="取&nbsp;消" class="cancel" onfocus="this.blur()" />
                <input type="text" value="确&nbsp;定" class="yes" onfocus="this.blur()" onclick="sellFinal(3);"/>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
    var popObj;
    $(function(){
        $(".tradeBuy").click(function(){
            // $(".mask").fadeIn();
            // $(".buy_pop").fadeIn();
            // popObj = ".buy_pop"
        });
        $(".pay").click(function(){
            // $(".mask").fadeIn();
            // $(".buy_pop").hide();
            // $("#bankPay").fadeIn();
            // popObj = ".pay_pop"
        });
        $(".tradeSale").click(function(){
            // $(".mask").fadeIn();
            // $(".sell_pop").fadeIn();
            // popObj = ".sell_pop"
        });
        $(".confirm").click(function(){
            // $(".mask").fadeIn();
            // $(".sell_pop").hide();
            // $(".confirm_pop").fadeIn();
            // popObj = ".confirm_pop"
        });
        $(".cancel").click(function(){
            $(".mask").fadeOut("fast");
            $(popObj).fadeOut("fast");

            qingkong();
        });
        $(".yes").click(function(){
            $(".mask").fadeOut("fast");
            $(popObj).fadeOut("fast");
        });
    });

    function change(valueNum)
    {
        if(valueNum == 1)
        {
            $(".card").css("display" , "inline-block");
            $(".ali").hide();
            $(".wechat").hide();
        }
        if(valueNum == 2)
        {
            $(".card").hide();
            $(".ali").css("display" , "inline-block");
            $(".wechat").hide();
        }
        if(valueNum == 3)
        {
            $(".card").hide();
            $(".ali").hide();
            $(".wechat").css("display" , "inline-block");
        }
    }

    function qingkong() {
        $("#buyNumber").val("");
        $("#sellNumber").val("");
        $("#buySum").html("¥0");
        $("#sellSum").html("¥0");
        $("#sellNumber").val("");
        $("#sellSum").html("¥0");
        $("#sellBankCard").val("");
        $("#sellBankName").val("");
        $("#sellBankBranch").val("");
        $("#sellPaymentName").val("");
        $("#sellPaymentPhone").val("");
        $("#sellAliAccount").val("");
        $("#changead_t1").val("");
        $("#sellWxAccount").val("");
        $("#changead_t2").val("");

        $("#paymentType").val(0);
        $("#sellPayType").val(0);
        $(".card").hide();
        $(".ali").hide();
        $(".wechat").hide();
    }
</script>

<script type="text/javascript">
    window.onload = function() {
        var code = '${code}';
        var message = '${message}';
        if (code != 1 && message != "") {
            openTips(message);
            return false;
        }
        $("#currencyId option").each(function(){
            if($(this).val()=='${currencyId}'){
                $(this).attr('selected',true);
            }
        });
        $("#orderType option").each(function(){
            if($(this).val()=='${orderType}'){
                $(this).attr('selected',true);
            }
        });
        $("#area option").each(function(){
            if($(this).val()=='${area}'){
                $(this).attr('selected',true);
            }
        });
    }

    function queryForm() {
        $("#queryPageNumber").val("0");
        $("#queryForm").submit();
    }

    var buyBoo = false;
    function buy(otcPendingOrderNo, userId, pendingRatio, min, max) {
        if (buyBoo) {
            return;
        } else {
            buyBoo = true;
        }

        var user = '${userSession}';
        if (user == null || user == "") {
            openTips("请先登录再操作");
            buyBoo = false;
            return;
        }

        $("#buyMin").val(parseFloat(min));
        $("#buyMax").val(parseFloat(max));
        $("#pendingRatio").val(pendingRatio);
        $("#orderNo").val(otcPendingOrderNo);
        var ratio = "1:" + parseFloat(pendingRatio);
        $("#ratio").html(ratio);

        $.ajax({
            url: '<%=path%>' + "/userWeb/otcTradeCenter/getPayType.htm", //方法路径URL
            data:{
                otcPendingOrderNo : otcPendingOrderNo,
                userId : userId
            },//参数
            dataType: 'json',
            type: 'POST',
            async: true, //默认异步调用 (false：同步)
            success: function (result) {
                buyBoo = false;
                $("#paymentType option").each(function(){
                    $(this).show();
                });

                var data = result.data;
                var hasBank = data.hasBank;
                var hasWeiXin = data.hasWeiXin;
                var hasAliPay = data.hasAliPay;
                if(hasBank == 0){
                    $("#paymentType option").each(function(){
                        if($(this).val()==1){
                            $(this).hide();
                        }
                    });
                }
                if(hasWeiXin == 0){
                    $("#paymentType option").each(function(){
                        if($(this).val()==2){
                            $(this).hide();
                        }
                    });
                }
                if(hasAliPay == 0){
                    $("#paymentType option").each(function(){
                        if($(this).val()==3){
                            $(this).hide();
                        }
                    });
                }

                $(".mask").fadeIn();
                $(".buy_pop").fadeIn();
                popObj = ".buy_pop"
            }, error: function () {
                buyBoo = false;
                openTips("请重新刷新页面后重试");
            }
        });
    }

    var toPayBoo = false;
    function toPay() {
        if (toPayBoo) {
            return;
        } else {
            toPayBoo = true;
        }

        var buyNumber = $("#buyNumber").val();
        var buySum = $("#buySum").html();
        var orderNo = $("#orderNo").val();
        var paymentType = $("#paymentType").val();


        if(buyNumber <= 0 || buyNumber == '' || buyNumber == null){
            toPayBoo = false;
            openTips("请输入正确的数量");
            return;
        }

        if(paymentType <= 0 || paymentType == '' || paymentType == null){
            toPayBoo = false;
            openTips("请选择支付方式");
            return;
        }

        var min = parseInt($("#buyMin").val());
        var max = parseInt($("#buyMax").val());

        var buySumNum = parseInt(buySum.substring(1));
        if(buySumNum < min){
            toPayBoo = false;
            openTips("交易额度不能小于最小限额");
            return;
        }
        if(buySumNum > max){
            toPayBoo = false;
            openTips("交易额度不能大于最大限额");
            return;
        }

        $("#buyOrderNo").html(orderNo);

        $.ajax({
            url: '<%=path%>' + "/userWeb/otcTradeCenter/getPayDetails.htm", //方法路径URL
            data:{
                otcPendingOrderNo : orderNo,
                paymentType : paymentType,
            },//参数
            dataType: 'json',
            type: 'POST',
            async: true, //默认异步调用 (false：同步)
            success: function (result) {
                toPayBoo = false;

                var data = result.data;
                var dealerName = data.dealerName;
                var userName = data.userName;
                var userPhone = data.userPhone;
                var userPaymentType = data.userPaymentType;

                if(paymentType == 1){
                    $("#buyNumFinal").html(buyNumber);
                    $("#buySumFinal").html(buySum);
                    $("#dealerName").html(dealerName);
                    $("#userName").html(userName);
                    $("#userPhone").html(userPhone);
                    $("#bankCard").html(userPaymentType.paymentAccount);
                    $("#bankName").html(userPaymentType.bankName + userPaymentType.bankBranch);
                    $("#paymentName").html(userPaymentType.paymentName);
                    $("#paymentPhone").html(userPaymentType.paymentPhone);
                    $(".mask").fadeIn();
                    $(".buy_pop").hide();
                    $("#bankPay").fadeIn();
                    popObj = ".pay_pop"
                } else if(paymentType == 2){
                    $("#aliNum").html(buyNumber);
                    $("#aliSum").html(buySum);
                    $("#aliDealerName").html(dealerName);
                    $("#aliUserName").html(userName);
                    $("#aliUserPhone").html(userPhone);
                    $("#aliAccount").html(userPaymentType.paymentAccount);
                    var img = "<img src='" + userPaymentType.paymentImageFormat + "' class='code' />";
                    $("#aliImage").html(img);
                    $(".mask").fadeIn();
                    $(".buy_pop").hide();
                    $("#aliPay").fadeIn();
                    popObj = ".pay_pop"
                } else if(paymentType == 3){
                    $("#wxNum").html(buyNumber);
                    $("#wxSum").html(buySum);
                    $("#wxDealerName").html(dealerName);
                    $("#wxUserName").html(userName);
                    $("#wxUserPhone").html(userPhone);
                    $("#wxAccount").html(userPaymentType.paymentAccount);
                    var img = "<img src='" + userPaymentType.paymentImageFormat + "' class='code' />";
                    $("#wxImage").html(img);
                    $(".mask").fadeIn();
                    $(".buy_pop").hide();
                    $("#weiXinPay").fadeIn();
                    popObj = ".pay_pop"
                }

            }, error: function () {
                toPayBoo = false;
                openTips("请重新刷新页面后重试");
            }
        });

    }

    var buyFinalBoo = false;
    function buyFinal(payType) {
        if (buyFinalBoo) {
            return;
        } else {
            buyFinalBoo = true;
        }

        var buyNumber = $("#buyNumber").val();
        var orderNo = $("#orderNo").val();

        $.ajax({
            url: '<%=path%>' + "/userWeb/otcTradeCenter/buy.htm", //方法路径URL
            data:{
                otcPendingOrderNo : orderNo,
                paymentType : payType,
                buyNum : buyNumber
            },//参数
            dataType: 'json',
            type: 'POST',
            async: true, //默认异步调用 (false：同步)
            success: function (result) {
                buyFinalBoo = false;
                var message = result.message;
                $(".mask").fadeOut("fast");
                $(popObj).fadeOut("fast");
                qingkong();
                openTips(message);

            }, error: function () {
                buyFinalBoo = false;
                qingkong();
                openTips("请重新刷新页面后重试");
            }
        });
    }

    function sell(otcPendingOrderNo, dealerName, pendingRatio, min, max) {
        var user = '${userSession}';
        if (user == null || user == "") {
            openTips("请先登录再操作");
            return;
        }

        $("#sellMin").val(parseFloat(min));
        $("#sellMax").val(parseFloat(max));
        $("#sellDealerName").html(dealerName);
        $("#sellRatio").val(pendingRatio);
        $("#sellOrderNo").val(otcPendingOrderNo);
        var ratio = "1:" + parseFloat(pendingRatio);
        $("#ratioSell").html(ratio);
        $(".mask").fadeIn();
        $(".sell_pop").fadeIn();
        popObj = ".sell_pop"
    }

    function toSell() {
        var sellPayType = $("#sellPayType").val();

        if(sellPayType == 0 || sellPayType == null || sellPayType == ''){
            openTips("请选择支付方式");
            return;
        }

        var dealer = $("#sellDealerName").html();
        var num = $("#sellNumber").val();
        var sum = $("#sellSum").html();

        if(num <= 0 || num == null || num == ''){
            openTips("请输入正确的数量");
            return;
        }

        var min = parseInt($("#sellMin").val());
        var max = parseInt($("#sellMax").val());

        var sellSumNum = parseInt(sum.substring(1));
        if(sellSumNum < min){
            toPayBoo = false;
            openTips("交易额度不能小于最小限额");
            return;
        }
        if(sellSumNum > max){
            toPayBoo = false;
            openTips("交易额度不能大于最大限额");
            return;
        }


        if(sellPayType == 1){
            var sellBankCard = $("#sellBankCard").val();
            var sellBankName = $("#sellBankName").val();
            var sellBankBranch = $("#sellBankBranch").val();
            var sellPaymentName = $("#sellPaymentName").val();
            var sellPaymentPhone = $("#sellPaymentPhone").val();

            if(sellBankCard == null || sellBankCard == ''){
                openTips("请输入银行卡号");
                return;
            }
            if(sellBankCard.length < 16 || sellBankCard.length >19){
                openTips("银行卡号在16-19位之间");
                return;
            }
            if(sellBankName == null || sellBankName == ''){
                openTips("请输入银行名称");
                return;
            }
            if(sellBankBranch == null || sellBankBranch == ''){
                openTips("请输入支行名称");
                return;
            }
            if(sellPaymentName == null || sellPaymentName == ''){
                openTips("请输入银行预留姓名");
                return;
            }
            if(sellPaymentPhone == null || sellPaymentPhone == ''){
                openTips("请输入银行预留电话");
                return;
            }
            if(sellPaymentPhone.length != 11){
                openTips("请输入11位银行预留电话");
                return;
            }

            $("#bankConfirmDealer").html(dealer);
            $("#bankConfirmNum").html(num);
            $("#bankConfirmSum").html(sum);

            $("#bankConfirmBankCard").html(sellBankCard);
            $("#bankConfirmBankName").html(sellBankName);
            $("#bankConfirmBankBranch").html(sellBankBranch);
            $("#bankConfirmPaymentName").html(sellPaymentName);
            $("#bankConfirmPaymentPhone").html(sellPaymentPhone);

            $(".mask").fadeIn();
            $(".sell_pop").hide();
            $("#bankConfirm").fadeIn();
            popObj = ".confirm_pop"

        }else if(sellPayType == 2){
            var sellAliAccount = $("#sellAliAccount").val();
            var qrCode = document.getElementById("changead_a1").files[0];

            if (sellAliAccount == null || sellAliAccount == '') {
                return openTips("请输入支付宝账户");
            }
            if (qrCode == null || qrCode == '') {
                return openTips("请上传二维码");
            }

            $("#aliConfirmDealer").html(dealer);
            $("#aliConfirmNum").html(num);
            $("#aliConfirmSum").html(sum);

            $("#aliConfirmAccount").html(sellAliAccount);

            $(".mask").fadeIn();
            $(".sell_pop").hide();
            $("#aliConfirm").fadeIn();
            popObj = ".confirm_pop"

        }else if(sellPayType == 3){
            var sellWxAccount = $("#sellWxAccount").val();
            var qrCode = document.getElementById("changead_a2").files[0];

            if (sellWxAccount == null || sellWxAccount == '') {
                return openTips("请输入微信账户");
            }
            if (qrCode == null || qrCode == '') {
                return openTips("请上传二维码");
            }

            $("#wxConfirmDealer").html(dealer);
            $("#wxConfirmNum").html(num);
            $("#wxConfirmSum").html(sum);

            $("#wxConfirmAccount").html(sellWxAccount);

            $(".mask").fadeIn();
            $(".sell_pop").hide();
            $("#wxConfirm").fadeIn();
            popObj = ".confirm_pop"
        }
    }

    var sellFinalBoo = false;
    function sellFinal(payType) {
        if (sellFinalBoo) {
            return;
        } else {
            sellFinalBoo = true;
        }

        var num = $("#sellNumber").val();
        var orderNo = $("#sellOrderNo").val();

        var paymentAccount = "";
        var paymentImage = "";
        if(payType == 1){
            paymentAccount = $("#sellBankCard").val();
        }else if(payType == 2){
            paymentAccount = $("#sellAliAccount").val();
            paymentImage = document.getElementById("changead_a1").files[0];
        }else if(payType == 3){
            paymentAccount = $("#sellWxAccount").val();
            paymentImage = document.getElementById("changead_a2").files[0];
        }
        var bankName = $("#sellBankName").val();
        var bankBranch = $("#sellBankBranch").val();
        var paymentName = $("#sellPaymentName").val();
        var paymentPhone = $("#sellPaymentPhone").val();

        var formData = new FormData();
        formData.append("otcPendingOrderNo", orderNo);
        formData.append("paymentType", payType);
        formData.append("sellNum", num);
        formData.append("paymentAccount", paymentAccount);
        formData.append("paymentImage", paymentImage);
        formData.append("bankName", bankName);
        formData.append("bankBranch", bankBranch);
        formData.append("paymentName", paymentName);
        formData.append("paymentPhone", paymentPhone);

        $.ajax({
            url: '<%=path%>' + "/userWeb/otcTradeCenter/sell.htm", //方法路径URL
            data:formData,//参数
            dataType: 'json',
            type: 'POST',
            async: true, //默认异步调用 (false：同步)
            processData : false,
            contentType : false,
            success: function (result) {
                sellFinalBoo = false;
                var message = result.message;
                $(".mask").fadeOut("fast");
                $(popObj).fadeOut("fast");
                qingkong();
                openTips(message);

            }, error: function () {
                sellFinalBoo = false;
                qingkong();
                openTips("请重新刷新页面后重试");
            }
        });

    }
</script>

<script type="text/javascript">
    var mapMatch = {};
    mapMatch['number'] = /[^\d]/g;
    mapMatch['ENumber'] = /[^\a-\z\A-\Z\d]/g;
    mapMatch['double'] = true;
    mapMatch['phone'] = /[^\d]/g;
    mapMatch['email'] = /([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\.[a-zA-Z0-9_-]{2,3}){1,2})$/;
    function matchUtil(o, str, nu) {
        if(mapMatch[str] === true){
            matchDouble(o, nu);
        }else {
            o.value = o.value.replace(mapMatch[str], '');
        }
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

        var buyNumber = $("#buyNumber").val();
        if(buyNumber == null || buyNumber == ""){
            $("#buySum").html("¥0");
        }
        var pendingRatio = $("#pendingRatio").val();

        //买入
        if (buyNumber != null && buyNumber != "" && pendingRatio != null && pendingRatio != "") {
            buyNumber = buyNumber.toString();
            try{m+=buyNumber.split(".")[1].length}catch(e){}
            pendingRatio = pendingRatio.toString();
            try{m+=pendingRatio.split(".")[1].length}catch(e){}
            var number = parseFloat((Number(buyNumber.replace(".",""))*Number(pendingRatio.replace(".",""))/Math.pow(10,m)).toFixed(8));
            number = mulMaxNumber(number);
            $("#buySum").html("¥" + number);
        }

        var sellNumber = $("#sellNumber").val();
        if(sellNumber == null || sellNumber == ""){
            $("#sellSum").html("¥0");
        }
        var sellRatio = $("#sellRatio").val();

        //卖出
        if (sellNumber != null && sellNumber != "" && sellRatio != null && sellRatio != "") {
            sellNumber = sellNumber.toString();
            try{f+=sellNumber.split(".")[1].length}catch(e){}
            sellRatio = sellRatio.toString();
            try{f+=sellRatio.split(".")[1].length}catch(e){}
            var number = parseFloat((Number(sellNumber.replace(".",""))*Number(sellRatio.replace(".",""))/Math.pow(10,f)).toFixed(8));
            number = mulMaxNumber(number);
            $("#sellSum").html("¥" + number);
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

    function checkFileImageTwo(target, id){
        var flag = false;
        flag = checkFileImage(target);
        document.getElementById(id).value = target.value;
        if(flag == true){
            document.getElementById(id).value = target.value;
        }
    }

    //判断图片格式
    function checkFileImage(target) {
        var fileSize = 0;
        var filetypes = [".jpg", ".jpeg", ".png", ".JPG", ".JPEG", ".PNG"];
        var filepath = target.value;
        var filemaxsize = 1024 * 3;//10M
        if (filepath) {
            var isnext = false;
            var fileend = filepath.substring(filepath.lastIndexOf("."));
            if (filetypes && filetypes.length > 0) {
                for (var i = 0; i < filetypes.length; i++) {
                    if (filetypes[i] == fileend) {
                        isnext = true;
                        break;
                    }
                }
            }
            if (!isnext) {
                openTips("图片格式必须是jpeg,jpg,png中的一种！");
                target.value = "";
                return false;
            }
        } else {
            return false;
        }
        if (!target.files) {
            var filePath = target.value;
            var fileSystem = new ActiveXObject("Scripting.FileSystemObject");
            if (!fileSystem.FileExists(filePath)) {
                openTips("图片不存在，请重新输入！");
                return false;
            }
            var file = fileSystem.GetFile(filePath);
            fileSize = file.Size;
        } else {
            fileSize = target.files[0].size;
        }

        var size = fileSize / 1024;
        if (size > filemaxsize) {
            openTips("图片大小不能大于" + filemaxsize / 1024 + "M！");
            target.value = "";
            return false;
        }
        if (size <= 0) {
            openTips("图片大小不能为0M！");
            target.value = "";
            return false;
        }

        return true;
    }

</script>

</body>
</html>