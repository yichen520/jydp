<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/resources/page/common/path.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <link rel="icon" href="<%=path %>/resources/image/back/icon.ico" type="image/x-ico" />

    <link rel="stylesheet" type="text/css" href="<%=path %>/resources/css/back/manageCoin.css" />
    <link rel="stylesheet" type="text/css" href="<%=path %>/resources/css/back/public.css" />
    <link rel="stylesheet" type="text/css" href="<%=path %>/resources/css/back/simpleTips.css" />

    <title>增减用户可用币记录</title>
</head>
<body>
<div id="header"></div>


<div class="content">
    <div id="menu"></div>

    <div class="contentRight">
        <div class="caption">
            <span class="title">增减用户可用币记录</span>
        </div>

        <div class="top">
            <div class="askArea">
                <form id="queryForm" action="<%=path %>/backerWeb/backerAdministratorOperation/show.htm" method="post">
                    <p class="condition">
                        操作时间：
                        从&nbsp;<input placeholder="请选择起始时间" class="askTime" name="startAddTime" onfocus="this.blur()" value="${startAddTime }"/>
                        到&nbsp;<input placeholder="请选择结束时间" class="askTime" name="endAddTime" onfocus="this.blur()" value="${endAddTime }"/>
                    </p>
                    <p class="condition">用户账号：<input type="text" class="askInput" maxlength="16" name="userAccount" value="${userAccount }"
                                                     onkeyup="value=value.replace(/[^a-zA-Z0-9]/g,'')" onblur="value=value.replace(/[^a-zA-Z0-9]/g,'')"/></p>
                    <p class="condition">币种：
                        <select class="askSelect" id="currencyId" name="currencyId">
                            <option value="0">全部</option>
                            <c:forEach items="${--------}" var="item">
                                <option value="${item.currencyId}">${item.currencyName}</option>
                            </c:forEach>
                        </select>
                    </p>
                    <p class="condition">操作类型：
                        <select class="askSelect" id="typeHandle" name="typeHandle">
                            <option value="0">全部</option>
                            <option value="1">增加</option>
                            <option value="2">减少</option>
                        </select>
                    </p>
                    <p class="condition">操作管理员账号：<input type="text" class="askInput" maxlength="16" name="backerAccount" value="${backerAccount }"
                                                        onkeyup="value=value.replace(/[^a-zA-Z0-9]/g,'')" onblur="value=value.replace(/[^a-zA-Z0-9]/g,'')"/></p>

                    <input type="hidden" id="queryPageNumber" name="pageNumber" value="${pageNumber}">
                    <input type="text" value="查&nbsp;询" class="ask" onfocus="this.blur()" onclick="queryForm()"/>
                </form>
            </div>
        </div>

        <div class="bottom">
            <table class="table" cellspacing="0" cellpadding="0">
                <tr class="tableTitle">
                    <td class="time">操作时间</td>
                    <td class="account">用户账号</td>
                    <td class="coin">币种</td>
                    <td class="type">操作类型</td>
                    <td class="money">数量</td>
                    <td class="mark">备注</td>
                    <td class="account">操作管理员账号</td>
                    <td class="ip">操作时IP地址</td>
                </tr>
                <c:forEach items="${----------}" var="item">
                    <tr class="tableInfo">
                        <td class="time"><fmt:formatDate type="time" value="${item.addTime}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
                        <td class="account">${item.userAccount}</td>
                        <td class="coin">${item.currencyName}</td>
                        <c:if test="${item.typeHandle == 1}">
                            <td class="type">增加</td>
                        </c:if>
                        <c:if test="${item.typeHandle == 2}">
                            <td class="type">减少</td>
                        </c:if>
                        <td class="money"><fmt:formatNumber type="number" value="${item.---- }" maxFractionDigits="2"/></td>
                        <td class="mark">${item.remarks }</td>
                        <td class="account">${item.backerAccount }</td>
                        <td class="ip">${item.ipAddress }</td>
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
        $("#typeHandle").val('${typeHandle}');
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
</script>
</body>
</html>