<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/resources/page/common/path.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <link rel="icon" href="<%=path %>/resources/image/web/icon.ico" type="image/x-ico" />

    <link rel="stylesheet" type="text/css" href="<%=path %>/resources/css/web/record_entrust.css" />
    <link rel="stylesheet" type="text/css" href="<%=path %>/resources/css/web/public.css" />
    <link rel="stylesheet" type="text/css" href="<%=path %>/resources/css/web/simpleTips.css" />

    <title>委托记录</title>
</head>
<body>
<header id="header"></header>
<div id="menu"></div>


<div class="content">
    <div id="personalMenu"></div>

    <div class="contentRight">
        <div class="title">委托记录</div>

        <div class="main">
            <table class="table" cellpadding="0" cellspacing="0">
                <tr class="tableTitle">
                    <td class="time">委托时间</td>
                    <td class="coin">币种</td>
                    <td class="type">类型</td>
                    <td class="amount">委托详情</td>
                    <td class="amount">成交详情</td>
                    <td class="state">状态</td>
                    <td class="operate">操作</td>
                </tr>
                <c:forEach items="${transactionPendOrderRecord}" var="item">
                    <tr class="tableInfo">
                        <td class="time"><fmt:formatDate type="time" value="${item.addTime}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
                        <td class="coin">${item.currencyName}</td>
                        <c:if test="${item.paymentType == 1}">
                            <td class="type in">买入</td>
                        </c:if>
                        <c:if test="${item.paymentType == 2}">
                            <td class="type pay">卖出</td>
                        </c:if>
                        <td class="amount">
                            <p>委托数量：<fmt:formatNumber type="number" value="${item.pendingNumber}" maxFractionDigits="4"/></p>
                            <p>委托单价：<fmt:formatNumber type="number" value="${item.pendingPrice }" maxFractionDigits="6"/> XT</p>
                            <p>委托总价：<fmt:formatNumber type="number" value="${item.pendingNumber * item.pendingPrice }" maxFractionDigits="6"/> XT</p>
                        </td>
                        <td class="amount">
                            <p>已成交数量：<fmt:formatNumber type="number" value="${item.dealNumber }" maxFractionDigits="4"/></p>
                            <p>剩余数量：<fmt:formatNumber type="number" value="${item.pendingNumber - item.dealNumber }" maxFractionDigits="4"/></p>
                        </td>
                        <c:if test="${item.pendingStatus == 1}">
                            <td class="state">未成交</td>
                        </c:if>
                        <c:if test="${item.pendingStatus == 2}">
                            <td class="state">部分成交</td>
                        </c:if>
                        <c:if test="${item.pendingStatus == 3}">
                            <td class="state">已成交</td>
                        </c:if>
                        <c:if test="${item.pendingStatus == 4}">
                            <td class="state">部分撤销</td>
                        </c:if>
                        <c:if test="${item.pendingStatus == 5}">
                            <td class="state">全部撤销</td>
                        </c:if>
                        <td class="operate">
                            <c:if test="${item.pendingStatus < 3}">
                                <input type="text" value="撤销委托" class="revoke" onfocus="this.blur()" onclick="goCancle('${item.pendingOrderNo}')"/>&nbsp;
                            </c:if>
                            <c:if test="${item.pendingStatus != 1}">
                                <a class="details" onclick="detailsHandle('${item.pendingOrderNo}')">查看详情</a>
                            </c:if>
                        </td>
                    </tr>
                </c:forEach>
            </table>

            <jsp:include page="/resources/page/common/paging.jsp"></jsp:include>

            <form id="queryForm" action="<%=path %>/userWeb/transactionPendOrderController/show.htm" method="post">
                <input type="hidden" id="queryPageNumber" name="queryPageNumber">
            </form>

            <form id="detailsForm" action="<%=path %>/userWeb/dealRecord/show.htm" method="post">
                <input type="hidden" id="detailsOrderNo" name="pendingOrderNo">
            </form>
        </div>
    </div>
</div>


<div id="helpFooter"></div>
<div id="footer"></div>


<div class="mask">
    <div class="mask_content">
        <div class="revoke_pop">
            <p class="popTitle">撤销委托</p>
            <p class="popTips"><img src="<%=path %>/resources/image/web/tips.png" class="tipsImg" />确定撤销该委托内容？</p>

            <input type="hidden" id="cancleOrderNo" name="cancleOrderNo">
            <div class="buttons">
                <input type="text" value="取&nbsp;消" class="cancel" onfocus="this.blur()" />
                <input type="text" value="确&nbsp;定" class="yes" onfocus="this.blur()" onclick="cancleOrder()" />
            </div>
        </div>
    </div>
</div>

<script type="text/javascript" src="http://libs.baidu.com/jquery/2.1.4/jquery.min.js"></script>
<script type="text/javascript" src="<%=path %>/resources/js/loadPageWeb.js"></script>
<script type="text/javascript" src="<%=path %>/resources/js/simpleTips.js"></script>

<script type="text/javascript">
    var popObj;
    $(function(){
        $(".revoke").click(function(){

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

</script>
<script type="text/javascript">
    window.onload = function() {
        var code = '${code}';
        var message = '${message}';
        if (code != 1 && message != "") {
            openTips(message);
            return;
        }
    }

    //查看详情
    function detailsHandle(pendOrderNo) {
        document.getElementById("detailsOrderNo").value = pendOrderNo;
        $("#detailsForm").submit();
    }

    //撤回
    function goCancle(pendOrderNo) {
        document.getElementById("cancleOrderNo").value = pendOrderNo;

        $(".mask").fadeIn();
        $(".revoke_pop").fadeIn();
        popObj = ".revoke_pop"
    }

    //撤回
    var calMoreBoo = false;
    function cancleOrder() {
        if (calMoreBoo) {
            return;
        } else {
            calMoreBoo = true;
        }

        var cancleOrderNo = $("#cancleOrderNo").val();
        if (cancleOrderNo == "" || cancleOrderNo == null) {
            calMoreBoo =false;
            openTips("单号错误");
            return;
        }

        $.ajax({
            url: '<%=path %>' + "/userWeb/transactionPendOrderController/revoke.htm",
            data: {
                pendingOrderNo : cancleOrderNo
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
                document.getElementById("queryPageNumber").value = ${pageNumber };
                $("#queryForm").submit();
            },

            error: function () {
                openTips("数据加载出错，请稍候重试");
            }
        });
    }
</script>
</body>
</html>