<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/resources/page/common/path.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <link rel="icon" href="<%=path %>/resources/image/web/icon.ico" type="image/x-ico" />
    <link rel="icon" href="<%=path %>/resources/image/web/icon.ico" type="image/x-ico" />
    <link rel="stylesheet" type="text/css" href="<%=path %>/resources/css/web/tradeOut_record.css" />
    <link rel="stylesheet" type="text/css" href="<%=path %>/resources/css/web/public.css" />
    <link rel="stylesheet" type="text/css" href="<%=path %>/resources/css/web/simpleTips.css" />
    <script type="text/javascript" src="http://libs.baidu.com/jquery/2.1.4/jquery.min.js"></script>
    <script type="text/javascript" src="<%=path %>/resources/js/loadPageWeb.js"></script>
    <script type="text/javascript" src="<%=path %>/resources/js/simpleTips.js"></script>
    <script type="text/javascript" src="<%=path %>/resources/js/laydate.js"></script>
    <title>场外交易记录-经销商</title>
</head>
<body>
<header id="header"></header>
<div id="menu"></div>


<div class="content">
    <div id="personalMenu"></div>

    <div class="contentRight">
        <div class="title">场外交易记录-经销商</div>

        <div class="main">
            <form id="queryForm" action="<%=path %>/userWeb/dealerOtcDealRecord/show.htm" method="post">
            <div class="askArea">
                <p class="condition">申请时间：
                    从&nbsp;<input placeholder="请选择起始时间" name="startAddTime" class="askTime" onfocus="this.blur()" value="${queryParams.startAddTime}" />
                    到&nbsp;<input placeholder="请选择结束时间" name="endAddTime" class="askTime" onfocus="this.blur()" value="${queryParams.endAddTime}"/>
                </p>
                <p class="condition">用户账号：<input type="text" name="userAccount" class="askInput" maxlength="16" value="${queryParams.userAccount}"
                                                 onkeyup="matchUtil(this, 'ENumber')" onblur="matchUtil(this, 'ENumber')"/></p>
                <p class="condition">币种：
                    <select class="askSelect" id="currencyId" name="currencyId">
                        <option value="0">全部</option>
                        <option value="999">XT</option>
                        <%--<c:forEach items="${transactionCurrencyList}" var="currency">--%>
                            <%--<option value="${currency.currencyId}">${currency.currencyName}</option>--%>
                        <%--</c:forEach>--%>
                    </select>
                </p>
                <p class="condition">类型：
                    <select class="askSelect" id="dealType" name="dealType">
                        <option value="0">全部</option>
                        <option value="1">购买</option>
                        <option value="2">出售</option>
                    </select>
                </p>
                <p class="condition">交易状态：
                    <select class="askSelect" id="dealStatus" name="dealStatus">
                        <option value="0">全部</option>
                        <option value="1">待完成</option>
                        <option value="2">待确认</option>
                        <option value="4">已完成</option>
                    </select>
                </p>
                <p class="condition">转账方式：
                    <select class="askSelect" id="paymentType" name="paymentType">
                        <option value="0">全部</option>
                        <option value="1">银行卡转账</option>
                        <option value="3">微信转账</option>
                        <option value="2">支付宝转账</option>
                    </select>
                </p>
                <input type="hidden" id="queryPageNumber" name="pageNumber">
                <input type="submit" value="查&nbsp;询" class="ask" onfocus="this.blur()" />
            </div>
            </form>

            <table class="table" cellpadding="0" cellspacing="0">
                <tr class="tableTitle">
                    <td class="apply">申请信息</td>
                    <td class="userInfo">用户信息</td>
                    <td class="userInfo">我的信息</td>
                    <td class="state">交易状态</td>
                    <td class="operate">操作</td>
                </tr>

                <c:forEach items="${otcTransactionUserDealList}" var="userDeal">
                    <tr class="tableInfo">
                        <td class="apply">
                            <p>流水号：<span>${userDeal.otcOrderNo}</span></p>
                            <p>币种：<span>${userDeal.currencyName}</span></p>
                            <p>数量：<span><fmt:formatNumber type="number" value="${userDeal.currencyNumber}" maxFractionDigits="4"/></span></p>
                            <p>金额：<span>¥<fmt:formatNumber type="number" value="${userDeal.currencyTotalPrice}" maxFractionDigits="2"/></span></p>
                            <p>类型：
                                <c:if test="${userDeal.dealType == 1}">
                                    <span class="buy">购买</span>
                                </c:if>
                                <c:if test="${userDeal.dealType == 2}">
                                    <span class="sale">出售</span>
                                </c:if>
                            </p>
                            <p>地区：<span>${userDeal.area}</span></p>
                            <p>申请时间：<span><fmt:formatDate type="time" value="${userDeal.addTime}" pattern="yyyy-MM-dd HH:mm:ss"></fmt:formatDate></span></p>
                        </td>
                        <td class="seller">
                            <p>用户账号：<span>${userDeal.userAccount}</span></p>
                            <p>用户手机号：<span>${userDeal.userPhone}</span></p>
                            <p>转账方式：
                                <c:if test="${userDeal.paymentType == 1}">
                                    <span>银行卡转账</span></p>
                                    <c:if test="${userDeal.dealType == 2}">
                                        <p>银行卡号：<span>${userDeal.paymentAccount}</span></p>
                                        <p>银行：<span>${userDeal.bankName}${userDeal.bankBranch}</span></p>
                                        <p>银行预留电话：<span>${userDeal.paymentPhone}</span></p>
                                        <p>收款人：<span>${userDeal.paymentName}</span></p>
                                    </c:if>
                                </c:if>
                                <c:if test="${userDeal.paymentType == 2}">
                                    <span>支付宝转账</span>
                                    <c:if test="${userDeal.dealType == 2}">
                                        <p><img src="${userDeal.paymentImage}" class="code" /></p>
                                    </c:if>
                                </c:if>
                                <c:if test="${userDeal.paymentType == 3}">
                                    <span>微信转账</span>
                                    <c:if test="${userDeal.dealType == 2}">
                                        <p><img src="${userDeal.paymentImage}" class="code" /></p>
                                    </c:if>
                                </c:if>
                        </td>
                        <td class="my">
                            <c:if test="${userDeal.paymentType == 1 and userDeal.dealType == 1}">
                                <p>银行账号：${userDeal.paymentAccount}</p>
                            </c:if>
                            <c:if test="${userDeal.paymentType == 2 and userDeal.dealType == 1}">
                                <p>支付宝账号：${userDeal.paymentAccount}</p>
                            </c:if>
                            <c:if test="${userDeal.paymentType == 3 and userDeal.dealType == 1}">
                                <p>微信账号：${userDeal.paymentAccount}</p>
                            </c:if>
                        </td>
                        <td class="state">
                            <p>状态：
                                <c:if test="${userDeal.dealStatus == 1}">
                                    <span class="wait">待完成</span>
                                </c:if>
                                <c:if test="${userDeal.dealStatus == 2 || userDeal.dealStatus == 3}">
                                    <span class="wait_confirm">待确认</span>
                                </c:if>
                                <c:if test="${userDeal.dealStatus == 4}">
                                    <span class="finish">已完成</span>
                                </c:if>
                            </p>
                            <p>完成时间：<span><c:if test="${userDeal.dealStatus == 4}">
                                                <fmt:formatDate type="time" value="${userDeal.updateTime}" pattern="yyyy-MM-dd HH:mm:ss"></fmt:formatDate>
                                              </c:if>
                                        </span></p>
                        </td>
                        <td class="operate">
                            <c:if test="${userDeal.dealType == 1 and (userDeal.dealStatus == 1 or userDeal.dealStatus == 2)}">
                                <input type="text" value="确认收款" class="confirm_money" onfocus="this.blur()" onclick="showMoney('${userDeal.otcOrderNo}')"/>
                            </c:if>
                            <c:if test="${userDeal.dealType == 2 and (userDeal.dealStatus == 1 or userDeal.dealStatus == 2)}">
                                <input type="text" value="确认收货" class="confirm_coin" onfocus="this.blur()" onclick="showCoin('${userDeal.otcOrderNo}')"/>
                            </c:if>
                        </td>
                    </tr>
                </c:forEach>
            </table>

            <jsp:include page="/resources/page/common/paging.jsp"></jsp:include>
        </div>
    </div>
</div>
<input type="hidden" id="otcOrderNo">
<div id="helpFooter"></div>
<div id="footer"></div>


<div class="mask">
    <div class="mask_content">
        <div class="coin_pop">
            <p class="popTitle">确认收货</p>
            <p class="popTips"><img src="<%=path %>/resources/image/web/tips.png" class="tipsImg" />确认已收到商品？</p>

            <div class="buttons">
                <input type="text" value="取&nbsp;消" class="cancel" onfocus="this.blur()" />
                <input type="text" value="确&nbsp;定" class="yes" onfocus="this.blur()" onclick="confirmTakeCoin()" />
            </div>
        </div>

        <div class="money_pop">
            <p class="popTitle">确认收款</p>
            <p class="popTips"><img src="<%=path %>/resources/image/web/tips.png" class="tipsImg" />确认已收到货款？</p>

            <div class="buttons">
                <input type="text" value="取&nbsp;消" class="cancel" onfocus="this.blur()" />
                <input type="text" value="确&nbsp;定" class="yes" onfocus="this.blur()" onclick="confirmTakeMoney()" />
            </div>
        </div>
    </div>
</div>



<script type="text/javascript">

    lay('.askTime').each(function(){
        laydate.render({
            elem: this,
            trigger: 'click',
            type:'datetime',
            theme: '#e83a33'
        });
    });//日期控件

    var popObj;
    $(function(){
        $(".cancel").click(function(){
            $(".mask").fadeOut("fast");
            $(popObj).fadeOut("fast");
        });
        $(".yes").click(function(){
            $(".mask").fadeOut("fast");
            $(popObj).fadeOut("fast");
        });
    });

    var mapMatch = {};
    mapMatch['ENumber'] = /[^\a-\z\A-\Z\d]/g;
    mapMatch['number'] = /[^\d]/g;
    mapMatch['double'] = true;
    function matchUtil(o, str) {
        mapMatch[str] === true ? matchDouble(o, 2) : o.value = o.value.replace(mapMatch[str], '');
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
    }
    //查询数据回显
    window.onload = function() {
        $("#currencyId").val('${queryParams.currencyId}');
        $("#dealType").val('${queryParams.dealType}');
        $("#dealStatus").val('${queryParams.dealStatus}');
        $("#paymentType").val('${queryParams.paymentType}');
    }

    //展示确认收款弹框
    function showMoney(otcOrderNo) {
        $(".mask").fadeIn();
        $(".money_pop").fadeIn();
        $('#otcOrderNo').val(otcOrderNo);
        popObj = ".money_pop";
    }

    //展示确认收货弹框
    function showCoin(otcOrderNo) {
        $(".mask").fadeIn();
        $(".coin_pop").fadeIn();
        $('#otcOrderNo').val(otcOrderNo);
        popObj = ".coin_pop";
    }

    //确认收货
    var takeCoinBoo = false;
    function confirmTakeCoin() {

        if(takeCoinBoo){
            openTips("正在执行，请稍后！");
            return;
        }else{
            takeCoinBoo = true;
        }

        var otcOrderNo = $('#otcOrderNo').val();
        $.ajax({
            url: '<%=path %>/userWeb/dealerOtcDealRecord/confirmTakeCoin.htm',
            type:'post',
            data:{otcOrderNo:otcOrderNo},
            dataType:'json',
            success:function (result) {
                if (result.code == 1) {
                    $("#queryForm").submit();
                } else {
                    openTips(result.message);
                }
                takeCoinBoo = false;
            },
            error:function () {
                takeCoinBoo = false;
                openTips("数据加载出错，请稍候重试");
            }
        });
    }

    //确认收款
    var takeMoneyBoo = false;
    function confirmTakeMoney() {

        if(takeMoneyBoo){
            openTips("正在执行，请稍后！");
            return;
        }else{
            takeMoneyBoo = true;
        }

        var otcOrderNo = $('#otcOrderNo').val();
        $.ajax({
            url: '<%=path %>/userWeb/dealerOtcDealRecord/confirmTakeMoney.htm',
            type:'post',
            data:{otcOrderNo:otcOrderNo},
            dataType:'json',
            success:function (result) {
                if (result.code == 1) {
                    $("#queryForm").submit();
                } else {
                    openTips(result.message);
                }
                takeMoneyBoo = false;
            },
            error:function () {
                takeMoneyBoo = false;
                openTips("数据加载出错，请稍候重试");
            }
        });
    }
</script>
</body>
</html>