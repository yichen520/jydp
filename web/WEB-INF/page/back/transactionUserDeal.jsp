<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/resources/page/common/path.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <link rel="icon" href="<%=path %>/resources/image/back/icon.ico" type="image/x-ico" />

    <link rel="stylesheet" type="text/css" href="<%=path %>/resources/css/back/tradeRecord.css" />
    <link rel="stylesheet" type="text/css" href="<%=path %>/resources/css/back/public.css" />
    <link rel="stylesheet" type="text/css" href="<%=path %>/resources/css/back/simpleTips.css" />

    <title>成交记录</title>
</head>
<body>
<div id="header"></div>


<div class="content">
    <div id="menu"></div>

    <div class="contentRight">
        <div class="caption">
            <span class="title">成交记录</span>
        </div>

        <div class="top">
            <form id="queryForm" action="<%=path %>/backerWeb/transactionUserDeal/show.htm" method="post">
                <div class="askArea">
                    <p class="condition">用户账号：<input type="text" class="askInput" id="userAccount" name="userAccount"
                                                     maxlength="16" onkeyup="value=value.replace(/[^a-zA-Z0-9]/g,'')" value="${userAccount}" /></p>
                    <p class="condition">币种：
                        <select class="askSelect" id="currencyId" name="currencyName">
                            <option value="">全部</option>
                            <c:forEach items="${transactionCurrencyList}" var="item">
                                <option value="${item.currencyName}">${item.currencyName}</option>
                            </c:forEach>
                        </select>
                    </p>
                    <p class="condition">交易类型：
                        <select class="askSelect" id="paymentType" name="paymentType">
                            <option value="0">全部</option>
                            <option value="1">买入</option>
                            <option value="2">卖出</option>
                            <option value="3">撤回</option>
                        </select>
                    </p>
                    <p class="condition">
                        挂单时间：
                        从&nbsp;<input placeholder="请选择起始时间" class="askTime" id="startOrder" name="startPendTime"
                                      value="${startPendTime}" onfocus="this.blur()"/>
                        到&nbsp;<input placeholder="请选择结束时间" class="askTime" id="endOrder" name="endPendTime"
                                      value="${endPendTime}" onfocus="this.blur()" />
                    </p>
                    <p class="condition">
                        完成时间：
                        从&nbsp;<input placeholder="请选择起始时间" class="askTime" id="start" name="startAddTime"
                                      value="${startAddTime}" onfocus="this.blur()"/>
                        到&nbsp;<input placeholder="请选择结束时间" class="askTime" id="end" name="endAddTime"
                                      value="${endAddTime}" onfocus="this.blur()"/>
                    </p>

                    <input type="hidden" id="queryPageNumber" name="pageNumber" value="${pageNumber}">
                    <input type="text" value="查&nbsp;询" class="ask" onfocus="this.blur()" onclick="queryForm()"/>
                </div>
            </form>
        </div>

        <div class="bottom">
            <table class="table" cellspacing="0" cellpadding="0">
                <tr class="tableTitle">
                    <td class="order">交易订单号</td>
                    <td class="time">挂单时间</td>
                    <td class="account">用户账号</td>
                    <td class="coin">币种</td>
                    <td class="amount">交易数量</td>
                    <td class="amount">交易总价</td>
                    <td class="type">交易类型</td>
                    <td class="time">完成时间</td>
                </tr>
                <c:forEach items="${transactionUserDealList}" var="item">
                    <tr class="tableInfo">
                        <td class="order">${item.orderNo}</td>
                        <td class="time"><fmt:formatDate type="time" value="${item.pendTime}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
                        <td class="account">${item.userAccount}</td>
                        <td class="coin">${item.currencyName}</td>
                        <td class="amount"><fmt:formatNumber type="number" value="${item.currencyNumber }" maxFractionDigits="6"/></td>
                        <td class="amount">
                            <c:if test="${item.paymentType == 1 && item.paymentType != 3}">
                                <p>实际：$<fmt:formatNumber type="number" value="${item.feeNumber * item.currencyTotalPrice + item.currencyTotalPrice}" maxFractionDigits="6"/></p>
                            </c:if>
                            <c:if test="${item.paymentType == 2 && item.paymentType != 3}">
                                <p>实际：$<fmt:formatNumber type="number" value="${item.currencyTotalPrice - item.feeNumber * item.currencyTotalPrice }" maxFractionDigits="6"/></p>
                            </c:if>
                            <p>总计：$<fmt:formatNumber type="number" value="${item.currencyTotalPrice }" maxFractionDigits="6"/></p>
                            <c:if test="${item.paymentType != 3}">
                                <p>手续费：$<fmt:formatNumber type="number" value="${item.feeNumber * item.currencyTotalPrice }" maxFractionDigits="8"/></p>
                            </c:if>
                        </td>
                        <c:if test="${item.paymentType == 1}">
                            <td class="type">买入</td>
                        </c:if>
                        <c:if test="${item.paymentType == 2}">
                            <td class="type">卖出</td>
                        </c:if>
                        <c:if test="${item.paymentType == 3}">
                            <td class="type">撤回</td>
                        </c:if>
                        <td class="time"><fmt:formatDate type="time" value="${item.addTime}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
                    </tr>
                </c:forEach>
            </table>

            <jsp:include page="/resources/page/common/paging.jsp"></jsp:include>
        </div>
    </div>
</div>


<div id="footer"></div>

<script type="text/javascript" src="http://libs.baidu.com/jquery/2.1.4/jquery.min.js"></script>
<script type="text/javascript" src="<%=path %>/resources/js/loadPageBack.js"></script>
<script type="text/javascript" src="<%=path %>/resources/js/simpleTips.js"></script>
<script type="text/javascript" src="<%=path %>/resources/js/laydate.js"></script>

<script type="text/javascript">
    lay('.askTime').each(function(){
        laydate.render({
            elem: this,
            trigger: 'click',
            type:'datetime',
            theme: '#69c0ff'
        });
    });//日期控件

    $(function(){
        $(".revoke").click(function(){
            $(".mask").fadeIn();
            $(".revoke_pop").fadeIn();
        });
        $(".cancel").click(function(){
            $(".mask").fadeOut("fast");
        });
        $(".yes").click(function(){
            $(".mask").fadeOut("fast");
        });
    });
</script>

<script type="text/javascript">
    window.onload = function() {
        var code = '${code}';
        var message = '${message}';
        if (code != 1 && message != "") {
            openTips(message);
            return ;
        }

        $("#currencyId option").each(function(){
            if($(this).val()=='${currencyName}'){
                $(this).attr('selected',true);
            }
        });
        $("#paymentType option").each(function(){
            if($(this).val()=='${paymentType}'){
                $(this).attr('selected',true);
            }
        });

    }

    //查询
    function queryForm() {
        $("#queryForm").submit();
    }
</script>
</body>
</html>