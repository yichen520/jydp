<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/resources/page/common/path.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <link rel="icon" href="<%=path %>/resources/image/back/icon.ico" type="image/x-ico" />

    <link rel="stylesheet" type="text/css" href="<%=path %>/resources/css/back/manageMoney.css" />
    <link rel="stylesheet" type="text/css" href="<%=path %>/resources/css/back/public.css" />
    <link rel="stylesheet" type="text/css" href="<%=path %>/resources/css/back/simpleTips.css" />
    <link rel="stylesheet" type="text/css" href="<%=path %>/resources/js/need/laydate.css" />
    <link rel="stylesheet" type="text/css" href="<%=path %>/resources/js/skins/danlan/laydate.css" />

    <title>增减用户余额记录</title>
</head>
<body>
<div id="header"></div>


<div class="content">
    <div id="menu"></div>

    <div class="contentRight">
        <div class="caption">
            <span class="title">增减用户余额记录</span>
        </div>

        <div class="top">
            <div class="askArea">
                <form id="queryForm" action="<%=path %>/backerWeb/backerAdministratorOperation/show.htm" method="post">
                    <p class="condition">
                        操作时间：
                        从&nbsp;<input placeholder="请选择起始时间" name="startAddTime" class="startTime" id="startOrder"  onfocus="this.blur()" value="${startAddTime }"
                                      onClick="laydate({istime: true,format:'YYYY-MM-DD hh:mm:ss'})" />
                        到&nbsp;<input placeholder="请选择结束时间" name="endAddTime" class="endTime" id="endOrder"  onfocus="this.blur()" value="${endAddTime }"
                                      onClick="laydate({istime: true,format: 'YYYY-MM-DD hh:mm:ss'})" />
                    </p>
                    <p class="condition">用户账号：<input type="text" class="askInput" maxlength="16" name="userAccount" value="${userAccount }"
                                                     onkeyup="value=value.replace(/[^a-zA-Z0-9]/g,'')" onblur="value=value.replace(/[^a-zA-Z0-9]/g,'')"/></p>
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
                    <input type="test" value="查&nbsp;询" class="ask" onfocus="this.blur()" onclick="queryForm()"/>
                </form>
            </div>
        </div>

        <div class="bottom">
            <table class="table" cellspacing="0" cellpadding="0">
                <tr class="tableTitle">
                    <td class="time">操作时间</td>
                    <td class="account">用户账号</td>
                    <td class="type">操作类型</td>
                    <td class="money">账户金额</td>
                    <td class="mark">备注</td>
                    <td class="account">操作管理员账号</td>
                    <td class="ip">操作时IP地址</td>
                </tr>
                <c:forEach items="${backerHandleUserRecordBalanceList}" var="backerHandle">
                    <tr class="tableInfo">
                        <td class="time"><fmt:formatDate type="time" value="${backerHandle.addTime}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
                        <td class="account">${backerHandle.userAccount}</td>
                        <c:if test="${backerHandle.typeHandle == 1}">
                            <td class="type">增加</td>
                        </c:if>
                        <c:if test="${backerHandle.typeHandle == 2}">
                            <td class="type">减少</td>
                        </c:if>
                        <td class="money">$<fmt:formatNumber type="number" value="${backerHandle.userBalance }" maxFractionDigits="2"/></td>
                        <td class="mark">${backerHandle.remarks }</td>
                        <td class="account">${backerHandle.backerAccount }</td>
                        <td class="ip">${backerHandle.ipAddress }</td>
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
    !function(){
        laydate.skin('danlan');//切换皮肤，请查看skins下面皮肤库
    }();

    var start = {
        elem: '#startOrder',
        format: 'YYYY-MM-DD hh:mm:ss',
        istime: true,
        istoday: false
    };

    var end = {
        elem: '#endOrder',
        format: 'YYYY-MM-DD hh:mm:ss',
        istime: true,
        istoday: false
    };
    laydate(start);
    laydate(end);//日期控件
</script>

<script type="text/javascript">

    window.onload = function() {
        var code = '${code}';
        var message = '${message}';
        $("#typeHandle").val('${typeHandle}');
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
