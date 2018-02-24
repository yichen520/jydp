<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/resources/page/common/path.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <link rel="icon" href="<%=path %>/resources/image/back/icon.ico" type="image/x-ico" />

    <link rel="stylesheet" type="text/css" href="<%=path %>/resources/css/back/entrustRecord.css" />
    <link rel="stylesheet" type="text/css" href="<%=path %>/resources/css/back/public.css" />
    <link rel="stylesheet" type="text/css" href="<%=path %>/resources/css/back/simpleTips.css" />
    <link rel="stylesheet" type="text/css" href="<%=path %>/resources/js/need/laydate.css" />
    <link rel="stylesheet" type="text/css" href="<%=path %>/resources/js/skins/danlan/laydate.css" />

    <title>挂单记录</title>
</head>
<body>
<div id="header"></div>

<div class="content">
    <div id="menu"></div>

    <div class="contentRight">
        <div class="caption">
            <span class="title">挂单记录</span>
        </div>

        <div class="top">
            <form id="queryForm" action="<%=path %>/backerWeb/backerTransactionPendOrder/show.htm" method="post">
                <div class="askArea">
                    <p class="condition">用户账号：
                        <input type="text" class="askInput" id="userAccount" name="userAccount" value="${userAccount}" />
                    </p>
                    <p class="condition">币种：
                        <select class="askSelect" id="currencyId" name="currencyId">
                            <option value="0">全部</option>
                            <c:forEach items="${transactionCurrencyList}" var="currency">
                                <option value="${currency.currencyId}">${currency.currencyName}</option>
                            </c:forEach>
                        </select>
                    </p>
                    <p class="condition">交易类型：
                        <select class="askSelect" id="paymentType" name="paymentType">
                            <option value="0">全部</option>
                            <option value="1">买入</option>
                            <option value="2">卖出</option>
                        </select>
                    </p>
                    <p class="condition">交易状态：
                        <select class="askSelect" id="pendingStatus" name="pendingStatus">
                            <option value="0">全部</option>
                            <option value="1">未成交</option>
                            <option value="2">部分成交</option>
                            <option value="3">全部成交</option>
                            <option value="4">部分撤销</option>
                            <option value="5">全部撤销</option>
                        </select>
                    </p>
                    <p class="condition">
                        挂单时间：
                        从&nbsp;<input placeholder="请选择起始时间" class="startTime" id="startOrder" value="${startAddTime }"
                                       name="startAddTime" onClick="laydate({istime: true,format:'YYYY-MM-DD hh:mm:ss'})" />
                        到&nbsp;<input placeholder="请选择结束时间" class="endTime" id="endOrder" value="${endAddTime }"
                                      name="endAddTime" onClick="laydate({istime: true,format: 'YYYY-MM-DD hh:mm:ss'})" />
                    </p>
                    <p class="condition">
                        完成时间：
                        从&nbsp;<input placeholder="请选择起始时间" class="startTime" id="start" value="${startFinishTime }"
                                      name="startFinishTime" onClick="laydate({istime: true,format:'YYYY-MM-DD hh:mm:ss'})" />
                        到&nbsp;<input placeholder="请选择结束时间" class="endTime" id="end" value="${endFinishTime }"
                                      name="endFinishTime" onClick="laydate({istime: true,format: 'YYYY-MM-DD hh:mm:ss'})" />
                    </p>

                    <input type="hidden" id="queryPageNumber" name="pageNumber" value="${pageNumber}">
                    <input type="text" value="查&nbsp;询" class="ask" onfocus="this.blur()" onclick="queryForm()"/>
                </div>
            </form>
        </div>

        <div class="bottom">
            <table class="table" cellspacing="0" cellpadding="0">
                <tr class="tableTitle">
                    <td class="time">挂单时间</td>
                    <td class="account">用户账号</td>
                    <td class="coin">币种</td>
                    <td class="amount">挂单数量</td>
                    <td class="amount">挂单总价</td>
                    <td class="amount">剩余数量</td>
                    <td class="type">交易类型</td>
                    <td class="state">交易状态</td>
                    <td class="time">完成时间</td>
                    <td class="operate">操作</td>
                </tr>
                <c:forEach items="${transactionPendOrderRecord}" var="pend">
                    <tr class="tableInfo">
                        <td class="time"><fmt:formatDate type="time" value="${pend.addTime}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
                        <td class="account">${pend.userAccount}</td>
                        <td class="coin">${pend.currencyName}</td>
                        <td class="amount"><fmt:formatNumber type="number" value="${pend.pendingNumber }" maxFractionDigits="4"/></td>
                        <td class="amount"><fmt:formatNumber type="number" value="${pend.pendingNumber * pend.pendingPrice }" maxFractionDigits="6"/></td>
                        <td class="amount"><fmt:formatNumber type="number" value="${pend.pendingNumber - pend.dealNumber }" maxFractionDigits="4"/></td>
                        <c:if test="${pend.paymentType == 1}">
                            <td class="type">买入</td>
                        </c:if>
                        <c:if test="${pend.paymentType == 2}">
                            <td class="type">卖出</td>
                        </c:if>
                        <c:if test="${pend.pendingStatus == 1}">
                            <td class="state">未成交</td>
                        </c:if>
                        <c:if test="${pend.pendingStatus == 2}">
                            <td class="state">部分成交</td>
                        </c:if>
                        <c:if test="${pend.pendingStatus == 3}">
                            <td class="state">已成交</td>
                        </c:if>
                        <c:if test="${pend.pendingStatus == 4}">
                            <td class="state">部分撤销</td>
                        </c:if>
                        <c:if test="${pend.pendingStatus == 5}">
                            <td class="state">全部撤销</td>
                        </c:if>

                        <td class="time">
                            <c:if test="${pend.pendingStatus != 1}">
                                <fmt:formatDate type="time" value="${pend.endTime}" pattern="yyyy-MM-dd HH:mm:ss" />
                            </c:if>
                        </td>
                        <td class="operate">
                            <c:if test="${pend.pendingStatus == 1 || pend.pendingStatus == 2}">
                                <input type="text" class="revoke" value="撤销挂单" onfocus="this.blur()" onclick="revokeHandle(${pend.pendingOrderNo});"/>
                            </c:if>
                        </td>
                    </tr>
                </c:forEach>
            </table>

            <jsp:include page="/resources/page/common/paging.jsp"></jsp:include>
        </div>
    </div>
</div>

<div id="footer"></div>

<div class="mask">
    <div class="mask_content">
        <div class="revoke_pop">
            <p class="popTitle">撤销挂单</p>
            <p class="popTips"><img src="<%=path %>/resources/image/back/tips.png" class="tipsImg" />确定撤销该笔挂单？</p>

            <div class="buttons">
                <input type="text" value="取&nbsp;消" class="cancel" onfocus="this.blur()" />
                <input type="text" value="确&nbsp;定" class="yes" onfocus="this.blur()" onclick="revoke()" />
            </div>
        </div>
    </div>
</div>

<script type="text/javascript" src="http://libs.baidu.com/jquery/2.1.4/jquery.min.js"></script>
<script type="text/javascript" src="<%=path %>/resources/js/loadPageBack.js"></script>
<script type="text/javascript" src="<%=path %>/resources/js/simpleTips.js"></script>
<script type="text/javascript" src="<%=path %>/resources/js/laydate.js"></script>

<script type="text/javascript">
    window.onload = function() {
        var code = '${code}';
        var message = '${message}';
        if (code != 1 && message != "") {
            openTips(message);
            return false;
        }
        $("#pendingStatus option").each(function(){
            if($(this).val()=='${pendingStatus}'){
                $(this).attr('selected',true);
            }
        });
        $("#paymentType option").each(function(){
            if($(this).val()=='${paymentType}'){
                $(this).attr('selected',true);
            }
        });
        $("#currencyId option").each(function(){
            if($(this).val()=='${currencyId}'){
                $(this).attr('selected',true);
            }
        });
    }

    function queryForm() {
        $("#queryPageNumber").val("0");
        $("#queryForm").submit();
    }

    var pendingOrderNo = null;
    function revokeHandle(OrderNo) {
        pendingOrderNo = OrderNo;
    }
    //撤销
    var resultBoo = false;
    function revoke() {
        if(resultBoo){
            return false;
        }else{
            resultBoo = true;
        }

        $.ajax({
            url: '<%=path%>' + "/backerWeb/backerTransactionPendOrder/revoke.htm", //方法路径URL
            data:{
                pendingOrderNo : pendingOrderNo
            },//参数
            dataType: 'json',
            type: 'POST',
            async: true, //默认异步调用 (false：同步)
            success: function (result) {
                if(result.code == 1) {
                    document.getElementById("queryPageNumber").value = ${pageNumber };
                    $("#queryForm").submit();
                } else {
                    alert(result.message);
                }
                resultBoo = false;
            }, error: function () {
                resultBoo = false;
                alert("导出失败,请重新刷新页面后重试");
            }
        });
    }
</script>

<script type="text/javascript">
    !function(){
        laydate.skin('danlan');//切换皮肤，请查看skins下面皮肤库
    }();

    var start = {
        elem: '#start',
        format: 'YYYY-MM-DD hh:mm:ss',
        istime: true,
        istoday: false
    };

    var end = {
        elem: '#end',
        format: 'YYYY-MM-DD hh:mm:ss',
        istime: true,
        istoday: false
    };
    var startOrder = {
        elem: '#startOrder',
        format: 'YYYY-MM-DD hh:mm:ss',
        istime: true,
        istoday: false
    };

    var endOrder = {
        elem: '#endOrder',
        format: 'YYYY-MM-DD hh:mm:ss',
        istime: true,
        istoday: false
    };
    laydate(start);
    laydate(end);
    laydate(startOrder);
    laydate(endOrder);//日期控件

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

</body>
</html>