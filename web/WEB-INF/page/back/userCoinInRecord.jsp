<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/resources/page/common/path.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <link rel="icon" href="<%=path %>/resources/image/back/icon.ico" type="image/x-ico" />

    <link rel="stylesheet" type="text/css" href="<%=path %>/resources/css/back/coinIn_record.css" />
    <link rel="stylesheet" type="text/css" href="<%=path %>/resources/css/back/public.css" />
    <link rel="stylesheet" type="text/css" href="<%=path %>/resources/css/back/simpleTips.css" />

    <title>用户充币成功记录</title>
</head>
<body>
<div id="header"></div>


<div class="content">
    <div id="menu"></div>

    <div class="contentRight">
        <div class="caption">
            <span class="title">用户充币成功记录</span>
        </div>

        <div class="top">
            <form id="queryForm" action="<%=path %>/backerWeb/backerUserCoinInRecord/show.htm" method="post">
                <div class="askArea">
                    <p class="condition">用户账号：
                        <input type="text" class="askInput" value="${userAccount}" name="userAccount"
                               maxlength="16" onkeyup="matchUtil(this, 'ENumber')" onblur="matchUtil(this, 'ENumber')"/>
                    <p class="condition">订单号：
                        <input type="text" class="askInput" value="${orderNo}" name="orderNo"
                               maxlength="20" onkeyup="value=value.replace(/[^0-9]/g,'')"/>
                    <p class="condition">钱包订单号：
                        <input type="text" class="askInput" value="${walletOrderNo}" name="walletOrderNo"
                               maxlength="20" onkeyup="value=value.replace(/[^0-9]/g,'')"/>
                    <p class="condition">币种：
                        <select class="askSelect" id="currencyId" name="currencyId">
                            <option value="0">全部</option>
                            <c:forEach items="${transactionCurrencyList}" var="item">
                                <option value="${item.currencyId}">${item.currencyName}</option>
                            </c:forEach>
                        </select>
                    </p>
                    <p class="condition">
                        订单时间：
                        从&nbsp;<input placeholder="请选择起始时间" class="askTime" value="${startTime}" name="startTime" onfocus="this.blur()"/>
                        到&nbsp;<input placeholder="请选择结束时间" class="askTime" value="${endTime}" name="endTime" onfocus="this.blur()"/>
                    </p>

                    <input type="text" value="查&nbsp;询" class="ask" onfocus="this.blur()" onclick="queryForm()"/>
                </div>
            </form>
        </div>

        <div class="bottom">
            <table class="table" cellpadding="0" cellspacing="0">
                <tr class="tableTitle">
                    <td class="time">订单时间</td>
                    <td class="number">流水号</td>
                    <td class="account">用户账号</td>
                    <td class="coin">币种</td>
                    <td class="amount">转入数量</td>
                    <td class="account">转出账号</td>
                    <td class="mark">备注</td>
                </tr>
                <c:forEach items="${sylToJydpChainList}" var="item">
                    <tr class="tableInfo">
                        <td class="time"><fmt:formatDate type="time" value="${item.orderTime}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
                        <td class="number">
                            <p>订单号：${item.orderNo}</p>
                            <p>钱包订单号：${item.walletOrderNo}</p>
                        </td>
                        <td class="account">${item.userAccount}</td>
                        <td class="coin">${item.currencyName}</td>
                        <td class="amount"><fmt:formatNumber type="number" value="${item.currencyNumber }" maxFractionDigits="2"/></td>
                        <td class="account">${item.walletUserAccount}</td>
                        <td class="mark">${item.remark}</td>
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
</script>

<script type="text/javascript">
    window.onload = function() {
        var code = '${code}';
        var message = '${message}';
        $("#currencyId").val('${currencyId}');
        if (code != 1 && message != "") {
            openTips(message);
            return false;
        }
    }

    function queryForm() {
        $("#queryPageNumber").val("0");
        $("#queryForm").submit();
    }

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
</script>
</body>
</html>
