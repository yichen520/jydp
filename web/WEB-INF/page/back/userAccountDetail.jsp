<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/resources/page/common/path.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="C" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <link rel="icon" href="<%=path %>/resources/image/back/icon.ico" type="image/x-ico" />

    <link rel="stylesheet" type="text/css" href="<%=path %>/resources/css/back/userDetails.css" />
    <link rel="stylesheet" type="text/css" href="<%=path %>/resources/css/back/public.css" />
    <link rel="stylesheet" type="text/css" href="<%=path %>/resources/css/back/simpleTips.css" />
    <link rel="stylesheet" type="text/css" href="<%=path %>/resources/js/need/laydate.css" />
    <link rel="stylesheet" type="text/css" href="<%=path %>/resources/js/skins/danlan/laydate.css" />

    <title>账户明细</title>
</head>
<body>
<header id="header"></header>


<div class="content">
    <div id="menu"></div>

    <div class="contentRight">
        <div class="caption">用户账号</div>

        <div class="bottom">
            <p class="account">当前用户账号：${userAccount}</p>

            <table class="table" cellspacing="0" cellpadding="0">
                <tr class="tableTitle">
                    <td class="coin">币种名称</td>
                    <td class="amount">可用数量</td>
                    <td class="amount">冻结数量</td>
                    <td class="amount">总数量</td>
                </tr>
                <c:if test="${userCurrencyNumList != null and !empty userCurrencyNumList}">
                    <C:forEach items="${userCurrencyNumList}" var="item">
                        <tr class="tableInfo">
                            <td class="coin">${item.currencyName}</td>
                            <td class="amount"><fmt:formatNumber type="number" value="${item.currencyNumber }" maxFractionDigits="8"/></td>
                            <td class="amount"><fmt:formatNumber type="number" value="${item.currencyNumberLock }" maxFractionDigits="8"/></td>
                            <td class="amount"><fmt:formatNumber type="number" value="${item.currencyNumber + item.currencyNumberLock }" maxFractionDigits="8"/></td>
                        </tr>
                    </C:forEach>
                </c:if>
            </table>
        </div>
    </div>
</div>


<div id="footer"></div>


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
        $("#accountStatus option").each(function(){
            if($(this).val()=='${accountStatus}'){
                $(this).attr('selected',true);
            }
        });
    }

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
    laydate(start);
    laydate(end);//日期控件
</script>
</body>
</html>