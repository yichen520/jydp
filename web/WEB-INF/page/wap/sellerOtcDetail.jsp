<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/resources/page/common/path.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <meta http-equiv="Cache-Control" content="no-cache"/>

    <link rel="stylesheet" href="<%=path %>/resources/css/wap/offline-transaction-detail-dealer.css">
    <link rel="stylesheet" href="<%=path %>/resources/css/wap/common.css">
    <link rel="stylesheet" href="<%=path %>/resources/css/wap/simpleTips_wap.css">
    <title>订单详情</title>
</head>
<body>

<!-- 头部导航 -->
<header>
    <img src="<%=path %>/resources/image/wap/back.png" class="backimg" onclick="back()"/>
    <p>订单详情-经销商</p>
</header>

<article>
    <c:if test="${otcTransactionUserDeal.dealStatus == 2}">
        <img src="<%=path %>/resources/image/wap/wait-completed.png">
        <div>待确认</div>
    </c:if>
    <c:if test="${otcTransactionUserDeal.dealStatus == 1 || otcTransactionUserDeal.dealStatus == 3}">
        <img src="<%=path %>/resources/image/wap/wait-completed.png">
        <div>待完成</div>
    </c:if>
    <c:if test="${otcTransactionUserDeal.dealStatus == 4}">
        <img src="<%=path %>/resources/image/wap/completed.png">
        <div>已完成</div>
    </c:if>
</article>

<main>
    <div class="item-order">
        <div class="item-header">
            交易流水号：${otcTransactionUserDeal.otcOrderNo}
        </div>
        <div class="item-title">
            ${otcTransactionUserDeal.currencyName}
        </div>
        <div class="item-inner">
            <div>数量</div>
            <div><fmt:formatNumber type="number" value="${otcTransactionUserDeal.currencyNumber}" maxFractionDigits="4"/></div>
        </div>
        <div class="item-inner">
            <div>金额</div>
            <div>¥<fmt:formatNumber type="number" value="${otcTransactionUserDeal.currencyTotalPrice}" maxFractionDigits="2"/></div>
        </div>
        <div class="item-inner">
            <div>类型</div>
            <!--按照情况区分 -->
            <c:if test="${otcTransactionUserDeal.dealType == 1}" >
                <div class="buy">购买</div>
            </c:if>
            <c:if test="${otcTransactionUserDeal.dealType == 2}" >
                <div class="sell">出售</div>
            </c:if>
        </div>
        <div class="item-inner">
            <div>地区</div>
            <div>${otcTransactionUserDeal.area}</div>
        </div>
    </div>

    <c:if test="${otcTransactionUserDeal.dealType == 1}">
        <c:if test="${otcTransactionUserDeal.paymentType == 1}">
            <!--微信、支付宝支付 -->
            <div class="item-dealer-info-qr">
                <div class="item-title">
                    用户信息
                </div>
                <div class="item-inner">
                    <div>用户账号</div>
                    <div>${otcTransactionUserDeal.userAccount}</div>
                </div>
                <div class="item-inner">
                    <div>转账方式</div>
                    <div>银行卡转账</div>
                </div>
            </div>
        </c:if>
    </c:if>

    <c:if test="${otcTransactionUserDeal.dealType == 1}">
        <c:if test="${otcTransactionUserDeal.paymentType == 2}">
        <!--微信、支付宝支付 -->
        <div class="item-dealer-info-qr">
            <div class="item-title">
                用户信息
            </div>
            <div class="item-inner">
                <div>用户账号</div>
                <div>${otcTransactionUserDeal.userAccount}</div>
            </div>
            <div class="item-inner">
                <div>转账方式</div>
                <div>支付宝转账</div>
            </div>
        </div>
        </c:if>
    </c:if>
    <c:if test="${otcTransactionUserDeal.dealType == 1}">
        <c:if test="${otcTransactionUserDeal.paymentType == 3}">
            <!--微信、支付宝支付 -->
            <div class="item-dealer-info-qr">
                <div class="item-title">
                    用户信息
                </div>
                <div class="item-inner">
                    <div>用户账号</div>
                    <div>${otcTransactionUserDeal.userAccount}</div>
                </div>
                <div class="item-inner">
                    <div>转账方式</div>
                    <div>微信转账</div>
                </div>
            </div>
        </c:if>
    </c:if>

    <c:if test="${otcTransactionUserDeal.dealType == 1}">
        <c:if test="${otcTransactionUserDeal.paymentType == 1}">
        <div class="item-user-info">
            <div class="item-title">
                我的信息
            </div>
            <div class="item-inner">
                <div>银行账号</div>
                <div>${otcTransactionUserDeal.paymentAccount}</div>
            </div>
        </div>
        </c:if>
    </c:if>
    <c:if test="${otcTransactionUserDeal.dealType == 1}">
        <c:if test="${otcTransactionUserDeal.paymentType == 2}">
            <div class="item-user-info">
                <div class="item-title">
                    我的信息
                </div>
                <div class="item-inner">
                    <div>支付宝账号</div>
                    <div>${otcTransactionUserDeal.paymentAccount}</div>
                </div>
            </div>
        </c:if>
    </c:if>
    <c:if test="${otcTransactionUserDeal.dealType == 1}">
        <c:if test="${otcTransactionUserDeal.paymentType == 3}">
            <div class="item-user-info">
                <div class="item-title">
                    我的信息
                </div>
                <div class="item-inner">
                    <div>微信账号</div>
                    <div>${otcTransactionUserDeal.paymentAccount}</div>
                </div>
            </div>
        </c:if>
    </c:if>

<c:if test="${otcTransactionUserDeal.dealType == 2}">
    <c:if test="${otcTransactionUserDeal.paymentType == 1}">
    <div class="item-user-info">
        <div class="item-title">
            用户信息
        </div>
        <div class="item-inner">
            <div>用户账号</div>
            <div>${otcTransactionUserDeal.userAccount}</div>
        </div>
        <div class="item-inner">
            <div>用户手机号</div>
            <div>${otcTransactionUserDeal.userPhone}</div>
        </div>
        <div class="item-inner">
            <div>转账方式</div>
            <div>银行转账</div>
        </div>
        <div class="item-inner">
            <div>银行卡号</div>
            <div>${otcTransactionUserDeal.paymentAccount}</div>
        </div>
        <div class="item-inner">
            <div>银行信息</div>
            <div>${otcTransactionUserDeal.bankName}</div>
        </div>
        <div class="item-inner">
            <div>银行预留姓名</div>
            <div>${otcTransactionUserDeal.paymentName}</div>
        </div>
        <div class="item-inner">
            <div>银行预留手机</div>
            <div>${otcTransactionUserDeal.paymentPhone}</div>
        </div>
    </div>
    </c:if>
</c:if>

<c:if test="${otcTransactionUserDeal.dealType == 2}">
    <c:if test="${otcTransactionUserDeal.paymentType == 2}">
    <div class="item-dealer-info">
        <div class="item-title">
            用户信息
        </div>
        <div class="item-inner">
            <div>用户账号</div>
            <div>${otcTransactionUserDeal.userAccount}</div>
        </div>
        <div class="item-inner">
            <div>用户手机号</div>
            <div>${otcTransactionUserDeal.userPhone}</div>
        </div>
        <div class="item-inner">
            <div>转账方式</div>
            <div>支付宝转账</div>
        </div>
        <div class="item-inner">
            <div>支付宝账号</div>
            <div>${otcTransactionUserDeal.paymentAccount}</div>
        </div>
        <div class="item-ing-inner">
            <img class="qr-code" src="${otcTransactionUserDeal.paymentImage}">
        </div>
    </div>
    </c:if>
</c:if>
    <c:if test="${otcTransactionUserDeal.dealType == 2}">
        <c:if test="${otcTransactionUserDeal.paymentType == 3}">
            <div class="item-dealer-info">
                <div class="item-title">
                    用户信息
                </div>
                <div class="item-inner">
                    <div>用户账号</div>
                    <div>${otcTransactionUserDeal.userAccount}</div>
                </div>
                <div class="item-inner">
                    <div>用户手机号</div>
                    <div>${otcTransactionUserDeal.userPhone}</div>
                </div>
                <div class="item-inner">
                    <div>转账方式</div>
                    <div>微信转账</div>
                </div>
                <div class="item-inner">
                    <div>微信账号</div>
                    <div>${otcTransactionUserDeal.paymentAccount}</div>
                </div>
                <div class="item-ing-inner">
                    <img class="qr-code" src="${otcTransactionUserDeal.paymentImage}">
                </div>
            </div>
        </c:if>
    </c:if>

    <div class="item-time">
        <div class="item-inner">
            <div>申请时间</div>
            <div><fmt:formatDate type="time" value="${otcTransactionUserDeal.addTime}" pattern="yyyy-MM-dd HH:mm:ss" /></div>
        </div>
        <div class="item-inner">
            <div>完成时间</div>
            <div><fmt:formatDate type="time" value="${otcTransactionUserDeal.updateTime}" pattern="yyyy-MM-dd HH:mm:ss" /></div>
        </div>
    </div>
    <c:if test="${otcTransactionUserDeal.dealType == 1}">
        <c:if test="${otcTransactionUserDeal.dealStatus == 1 || otcTransactionUserDeal.dealStatus == 2}">
            <p class="button" onclick="confirmMoney('${otcTransactionUserDeal.otcOrderNo}')">确认收款</p>
        </c:if>
    </c:if>
    <c:if test="${otcTransactionUserDeal.dealType == 2}">
        <c:if test="${otcTransactionUserDeal.dealStatus == 1 || otcTransactionUserDeal.dealStatus == 2}">
        <p class="button" onclick="confirmReceipt('${otcTransactionUserDeal.otcOrderNo}')">确认收货</p>
        </c:if>
    </c:if>
</main>

<div class="show-qr-bar">
    <img class="show-qr" src="" alt="">
</div>

</body>

<script type="text/javascript" src="<%=path %>/resources/js/wap/common.js"></script>
<script type="text/javascript" src="<%=path %>/resources/js/wap/zepto.min.js"></script>
<script type="text/javascript" src="<%=path %>/resources/js/wap/simpleTips_wap.js"></script>
<script type="text/javascript" src="http://libs.baidu.com/jquery/2.1.4/jquery.min.js"></script>
<script type="text/javascript">

    $('.show-qr-bar').hide()
    $('.qr-code').on('click', function (e) {
        $('.show-qr-bar').show()
        $('.show-qr')[0].src = this.src
    })
    $('.show-qr-bar').on('click', function (e) {
        $('.show-qr-bar').hide()
    })

    //正在收货
    var receiptBoo = false;
    function confirmReceipt(otcOrderNo) {
        var otcOrderNo = otcOrderNo;
        if(otcOrderNo == null || otcOrderNo == null){
            options("该订单不存在，请刷新页面");
            return;
        }
        if(receiptBoo){
            openTips("正在确认，请稍后！");
            return;
        }else{
            receiptBoo = true;
        }
        $.ajax({
            url: '<%=path %>' + "/userWap/dealerOtcRecord/confirmTakeCoin.htm",
            data: {
                otcOrderNo : otcOrderNo,

            },//参数
            dataType: "json",
            type: 'POST',
            async: true, //默认异步调用 (false：同步)
            success: function (resultData) {
                var code = resultData.code;
                var message = resultData.message;
                if (code != 1 && message != "") {
                    receiptBoo = false;
                    openTips(message);
                    return;
                }

                window.location.href = "<%=path%>" + "/userWap/userOtcDealRecord/show.htm";
            },

            error: function () {
                receiptBoo = false;
                openTips("数据加载出错，请稍候重试");
            }
        });
    }

    //正在收款
    var addCheckBoo = false;
    function confirmMoney(otcOrderNo) {
        var otcOrderNo = otcOrderNo;
        if(otcOrderNo == null || otcOrderNo == null){
            options("该订单不存在，请刷新页面");
            return;
        }
        if(addCheckBoo){
            openTips("正在确认，请稍后！");
            return;
        }else{
            addCheckBoo = true;
        }
        $.ajax({
            url: '<%=path %>' + "/userWap/dealerOtcRecord/confirmTakeMoney.htm",
            data: {
                otcOrderNo : otcOrderNo,

            },//参数
            dataType: "json",
            type: 'POST',
            async: true, //默认异步调用 (false：同步)
            success: function (resultData) {
                var code = resultData.code;
                var message = resultData.message;
                if (code != 1 && message != "") {
                    addCheckBoo = false;
                    openTips(message);
                    return;
                }

                window.location.href = "<%=path%>" + "/userWap/dealerOtcRecord/show.htm";
            },

            error: function () {
                addCheckBoo = false;
                openTips("数据加载出错，请稍候重试");
            }
        });
    }

function back() {
    window.location.href = "<%=path%>" + "/userWap/dealerOtcRecord/show.htm";
}


</script>
</html>

