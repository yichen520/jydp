<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/resources/page/common/path.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <link rel="icon" href="images/icon.ico" type="image/x-ico" />
    <link rel="icon" href="<%=path %>/resources/image/web/icon.ico" type="image/x-ico" />
    <link rel="stylesheet" type="text/css" href="<%=path %>/resources/css/web/tradeOutSeller.css" />
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
        <div class="title">场外交易记录</div>

        <div class="main">
            <div class="askArea">
                <p class="condition">申请时间：
                    从&nbsp;<input placeholder="请选择起始时间" class="askTime" onfocus="this.blur()" />
                    到&nbsp;<input placeholder="请选择结束时间" class="askTime" onfocus="this.blur()" />
                </p>
                <p class="condition">用户账号：<input type="text" class="askInput" /></p>
                <p class="condition">币种：
                    <select class="askSelect">
                        <option>全部</option>
                        <option>比特币</option>
                        <option>XT</option>
                    </select>
                </p>
                <p class="condition">类型：
                    <select class="askSelect">
                        <option>全部</option>
                        <option>购买</option>
                        <option>出售</option>
                    </select>
                </p>
                <p class="condition">交易状态：
                    <select class="askSelect">
                        <option>全部</option>
                        <option>待完成</option>
                        <option>待确认</option>
                        <option>已完成</option>
                    </select>
                </p>
                <p class="condition">转账方式：
                    <select class="askSelect">
                        <option>全部</option>
                        <option>银行卡转账</option>
                        <option>微信转账</option>
                        <option>支付宝转账</option>
                    </select>
                </p>

                <input type="text" value="查&nbsp;询" class="ask" onfocus="this.blur()" />
            </div>

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
                            <p>金额：<span>¥<fmt:formatNumber type="number" value="${userDeal.currencyTotalPrice}" maxFractionDigits="6"/></span></p>
                            <c:if test="${userDeal.dealType == 1}">
                                <p>类型：<span class="buy">购买</span></p>
                            </c:if>
                            <c:if test="${userDeal.dealType == 2}">
                                <p>类型：<span class="buy">出售</span></p>
                            </c:if>
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
                                <c:if test="${userDeal.dealStatus == 2}">
                                    <span class="wait">已确认</span>
                                </c:if>
                                <c:if test="${userDeal.dealStatus == 3}">
                                    <span class="wait">已完成</span>
                                </c:if>
                            </p>
                            <p>完成时间：<span>${userDeal.updateTime}</span></p>
                        </td>
                        <td class="operate">
                            <c:if test="${userDeal.dealStatus == 1}">
                                <input type="text" value="确认收款" class="confirm_money" onfocus="this.blur()" />
                            </c:if>
                        </td>
                    </tr>
                </c:forEach>
            </table>

            <jsp:include page="/resources/page/common/paging.jsp"></jsp:include>

            <form id="queryForm" action="<%=path %>/userWeb/dealerOtcDealRecord/show.htm" method="post">
                <input type="hidden" id="queryPageNumber" name="pageNumber">
            </form>
        </div>
    </div>
</div>



<div id="helpFooter"></div>
<div id="footer"></div>


<div class="mask">
    <div class="mask_content">
        <div class="coin_pop">
            <p class="popTitle">确认收货</p>
            <p class="popTips"><img src="images/tips.png" class="tipsImg" />确认已收到商品？</p>

            <div class="buttons">
                <input type="text" value="取&nbsp;消" class="cancel" onfocus="this.blur()" />
                <input type="text" value="确&nbsp;定" class="yes" onfocus="this.blur()" onclick="openTip()" />
            </div>
        </div>

        <div class="money_pop">
            <p class="popTitle">确认收款</p>
            <p class="popTips"><img src="images/tips.png" class="tipsImg" />确认已收到货款？</p>

            <div class="buttons">
                <input type="text" value="取&nbsp;消" class="cancel" onfocus="this.blur()" />
                <input type="text" value="确&nbsp;定" class="yes" onfocus="this.blur()" onclick="openTip()" />
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
        $(".confirm_coin").click(function(){
            $(".mask").fadeIn();
            $(".coin_pop").fadeIn();
            popObj = ".coin_pop"
        });
        $(".confirm_money").click(function(){
            $(".mask").fadeIn();
            $(".money_pop").fadeIn();
            popObj = ".money_pop"
        });
        $(".cancel").click(function(){
            $(".mask").fadeOut("fast");
            $(popObj).fadeOut("fast");
        });
        $(".yes").click(function(){
            $(".mask").fadeOut("fast");
            $(popObj).fadeOut("fast");
        });
    });

    function openTip()
    {
        openTips("阿萨德芳");
    }
</script>

</body>
</html>