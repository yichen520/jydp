<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/resources/page/common/path.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <link rel="icon" href="<%=path %>/resources/image/web/icon.ico" type="image/x-ico" />

    <link rel="stylesheet" type="text/css" href="<%=path %>/resources/css/web/tradeOut_record.css" />
    <link rel="stylesheet" type="text/css" href="<%=path %>/resources/css/web/public.css" />
    <link rel="stylesheet" type="text/css" href="<%=path %>/resources/css/web/simpleTips.css" />

    <title>场外交易记录</title>
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
                <form id="queryForm" action="<%=path %>/userWeb/userDealRecord/show.htm" method="post">
                    <p class="condition">申请时间：
                        从&nbsp;<input placeholder="请选择起始时间" class="askTime" onfocus="this.blur()" value="${startAddTime }" name="startAddTime" id="startAddTime"/>
                        到&nbsp;<input placeholder="请选择结束时间" class="askTime" onfocus="this.blur()" value="${endAddTime }" name="endAddTime" id="endAddTime" />
                    </p>
                    <p class="condition">经销商名称：<input type="text" class="askInput" name="dealerName" id="dealerName" value="${dealerName }"  /></p>
                    <p class="condition">币种：
                        <select class="askSelect" name="currencyId" id="currencyId" >
                            <option value="0" >全部</option>
                            <option value="999">XT</option>
                        </select>
                    </p>
                    <p class="condition">类型：
                        <select class="askSelect" name="dealType" id="dealType">
                            <option value="0">全部</option>
                            <option value="1">购买</option>
                            <option value="2">出售</option>
                        </select>
                    </p>
                    <p class="condition">交易状态：
                        <select class="askSelect" name="dealStatus" id="dealStatus">
                            <option value="0">全部</option>
                            <option value="1">待完成</option>
                            <option value="2">待确认</option>
                            <option value="3">已完成</option>
                        </select>
                    </p>

                    <input type="text" value="查&nbsp;询" class="ask" onfocus="this.blur()" onclick="queryForm()" />
                </form>
            </div>

            <table class="table" cellpadding="0" cellspacing="0">
                <tr class="tableTitle">
                    <td class="apply">申请信息</td>
                    <td class="seller">经销商信息</td>
                    <td class="my">我的信息</td>
                    <td class="state">交易状态</td>
                    <td class="operate">操作</td>
                </tr>
                <c:forEach items="${otcTransactionUserDealList}" var="item">
                    <c:if test="${item.dealType == 1}">
                        <tr class="tableInfo">
                            <td class="apply">
                                <p>流水号：<span>${item.otcOrderNo}</span></p>
                                <p>币种：<span>${item.currencyName}</span></p>
                                <p>数量：<span><fmt:formatNumber type="number" value="${item.currencyNumber }" maxFractionDigits="4"/></span></p>
                                <p>金额：<span>¥<fmt:formatNumber type="number" value="${item.currencyTotalPrice }" maxFractionDigits="2"/></span></p>
                                <p>类型：<span class="buy">购买</span></p>
                                <p>地区：<span>${item.area }</span></p>
                                <p>申请时间：<span><fmt:formatDate type="time" value="${item.addTime}" pattern="yyyy-MM-dd HH:mm:ss" /></span></p>
                            </td>
                            <td class="seller">
                                <p>经销商名称：<span>${item.dealerName }</span></p>
                                <p>经销商电话：<span>${item.phoneNumber }</span></p>
                                <c:if test="${item.paymentType == 1}">
                                    <p>银行卡号：<span>${item.paymentAccount }</span></p>
                                    <p>银行：<span>${item.bankName } ${item.bankBranch } </span></p>
                                    <p>银行预留电话：<span>${item.paymentPhone }</span></p>
                                    <p>收款人：<span>${item.paymentName }</span></p>

                                </c:if>
                                <c:if test="${item.paymentType == 2}">
                                    <p>支付宝账号：<span>${item.paymentAccount }</span></p>
                                    <p><img src="${item.paymentImage }" class="code" /></p>
                                </c:if>
                                <c:if test="${item.paymentType == 3}">
                                    <p>微信账号：<span>${item.paymentAccount }</span></p>
                                    <p><img src="${item.paymentImage }" class="code" /></p>
                                </c:if>
                            </td>
                            <td class="my"></td>
                            <td class="state">
                            <c:if test="${item.dealStatus == 1}">
                                <p>状态：<span class="wait">待完成</span></p>
                                <p>完成时间：<span></span></p>
                            </c:if>
                            <c:if test="${item.dealStatus == 2}">
                                <p>状态：<span class="wait_confirm">待确认</span></p>
                                <p>完成时间：<span></span></p>
                            </c:if>
                            <c:if test="${item.dealStatus == 3}">
                                <p>状态：<span class="finish">已完成</span></p>
                                <p>完成时间：<span><fmt:formatDate type="time" value="${item.updateTime}" pattern="yyyy-MM-dd HH:mm:ss" /></span></p>
                            </c:if>
                            </td>
                            <td class="operate">
                            <c:if test="${item.dealStatus != 3}">
                                <input type="text" value="确认收货" class="confirm_coin" onfocus="this.blur()" onclick="affirmGet('${item.otcOrderNo}')"/>
                            </c:if>
                            </td>
                        </tr>
                    </c:if>
                    <c:if test="${item.dealType == 2}">
                        <tr class="tableInfo">
                            <td class="apply">
                                <p>流水号：<span>${item.otcOrderNo}</span></p>
                                <p>币种：<span>${item.currencyName}</span></p>
                                <p>数量：<span><fmt:formatNumber type="number" value="${item.currencyNumber }" maxFractionDigits="4"/></span></p>
                                <p>金额：<span>¥<fmt:formatNumber type="number" value="${item.currencyTotalPrice }" maxFractionDigits="2"/></span></p>
                                <p>类型：<span class="sale">出售</span></p>
                                <p>地区：<span>${item.area }</span></p>
                                <p>申请时间：<span><fmt:formatDate type="time" value="${item.addTime}" pattern="yyyy-MM-dd HH:mm:ss" /></span></p>
                            </td>
                            <td class="seller">
                                <p>经销商名称：<span>${item.dealerName}</span></p>
                                <p>经销商电话：<span>${item.phoneNumber}</span></p>
                            </td>
                            <td class="my">
                                <c:if test="${item.paymentType == 1}">
                                    <p>银行卡号：<span>${item.paymentAccount}</span></p>
                                    <p>银行：<span>${item.bankName}</span></p>
                                    <p>收款人：${item.paymentName}</p>
                                </c:if>
                                <c:if test="${item.paymentType == 2}">
                                    <p>支付宝账号；<span>${item.paymentAccount}</span></p>
                                </c:if>
                                <c:if test="${item.paymentType == 3}">
                                    <p>微信账号；<span>${item.paymentAccount}</span></p>
                                </c:if>
                            </td>
                            <td class="state">
                                <c:if test="${item.dealStatus == 1}">
                                    <p>状态：<span class="wait">待完成</span></p>
                                    <p>完成时间：<span></span></p>
                                </c:if>
                                <c:if test="${item.dealStatus == 2}">
                                    <p>状态：<span class="wait_confirm">待确认</span></p>
                                    <p>完成时间：<span></span></p>
                                </c:if>
                                <c:if test="${item.dealStatus == 3}">
                                    <p>状态：<span class="finish">已完成</span></p>
                                    <p>完成时间：<span><fmt:formatDate type="time" value="${item.updateTime}" pattern="yyyy-MM-dd HH:mm:ss" /></span></p>
                                </c:if>
                            </td>
                            <td class="operate">
                                <c:if test="${item.dealStatus != 3}">
                                    <input type="text" value="确认收款" class="confirm_money" onfocus="this.blur()" onclick="affirmGet('${item.otcOrderNo}')"/>
                                </c:if>
                            </td>
                        </tr>
                    </c:if>
                </c:forEach>
            </table>
            <jsp:include page="/resources/page/common/paging.jsp"></jsp:include>
        </div>
    </div>
</div>



<div id="helpFooter"></div>
<div id="footer"></div>


<div class="mask">
    <div class="mask_content">
        <div class="coin_pop">
            <p class="popTitle">确认收货</p>
            <p class="popTips"><img src="<%=path %>/resources/image/web/tips.png" class="tipsImg" />确认已收到商品？</p>
            <div class="buttons">
                <input type="text" value="取&nbsp;消" class="cancel" onfocus="this.blur()" />
                <input type="text" value="确&nbsp;定" class="yes" onfocus="this.blur()" onclick="sureGet()" />
            </div>
        </div>

        <div class="money_pop">
            <p class="popTitle">确认收款</p>
            <p class="popTips"><img src="<%=path %>/resources/image/web/tips.png" class="tipsImg" />确认已收到货款？</p>
            <div class="buttons">
                <input type="text" value="取&nbsp;消" class="cancel" onfocus="this.blur()" />
                <input type="text" value="确&nbsp;定" class="yes" onfocus="this.blur()" onclick="sureGet()" />
            </div>
        </div>
    </div>
</div>

<input type="hidden" value="" id="affirmGetId"/>

<script type="text/javascript" src="<%=path %>/resources/js/laydate.js"></script>
<script type="text/javascript" src="http://libs.baidu.com/jquery/2.1.4/jquery.min.js"></script>
<script type="text/javascript" src="<%=path %>/resources/js/loadPageWeb.js"></script>
<script type="text/javascript" src="<%=path %>/resources/js/simpleTips.js"></script>

<script type="text/javascript">
    window.onload = function() {
        var code = '${code}';
        var message = '${message}';
        $("#currencyId").val('${currencyId}');
        $("#dealType").val('${dealType}');
        $("#dealStatus").val('${dealStatus}');
        if (code != 1 && message != "") {
            openTips(message);
            return false;
        }
    }

    //确认收货订单号存入
    function affirmGet(otcOrderNo) {
        $("#affirmGetId").val(otcOrderNo);
    }

    //确认收货
    var sureGetBoo = false;
    function sureGet() {
        if (sureGetBoo) {
            return;
        } else {
            sureGetBoo = true;
        }

        var otcOrderNo = $("#affirmGetId").val();
        $.ajax({
            url: '<%=path %>' + "/userWeb/userDealRecord/userConfirm.htm",
            data: {
                otcOrderNo : otcOrderNo
            },//参数
            dataType: "json",
            type: 'POST',
            async: true, //默认异步调用 (false：同步)
            success: function (resultData) {
                sureGetBoo = false;
                var code = resultData.code;
                var message = resultData.message;
                if (code != 1 && message != "") {
                    openTips(message);
                    return;
                }
                $("#queryForm").submit();
            },

            error: function () {
            userMessageBoo = false;
            //openTips("数据加载出错，请稍候重试");
            }
        });
    }


    function queryForm() {
        $("#queryPageNumber").val("0");
        $("#queryForm").submit();
    }
</script>

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

</script>

</body>
</html>
