<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/resources/page/common/path.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <link rel="icon" href="<%=path %>/resources/image/back/icon.ico" type="image/x-ico" />

    <link rel="stylesheet" type="text/css" href="<%=path %>/resources/css/back/userAccount.css" />
    <link rel="stylesheet" type="text/css" href="<%=path %>/resources/css/back/public.css" />
    <link rel="stylesheet" type="text/css" href="<%=path %>/resources/css/back/simpleTips.css" />
    <link rel="stylesheet" type="text/css" href="<%=path %>/resources/js/need/laydate.css" />
    <link rel="stylesheet" type="text/css" href="<%=path %>/resources/js/skins/danlan/laydate.css" />

    <title>用户账号</title>
</head>
<body>
<header id="header"></header>


<div class="content">
    <div id="menu"></div>

    <div class="contentRight">
        <div class="caption">用户账号</div>

        <div class="top">
            <div class="askArea">
                <form id="queryForm" action="<%=path %>/backerWeb/backerUserAccount/show.htm" method="post">
                    <p class="condition">用户账号：
                        <input type="text" class="askInput" id="userAccount" name="userAccount" value="${userAccount }"
                               maxlength="16" onkeyup="matchUtil(this, 'ENumber')" onblur="matchUtil(this, 'ENumber')"/></p>
                    <p class="condition">手机号：
                        <input type="text" class="askInput" id="phoneNumber" name="phoneNumber" value="${phoneNumber }"
                               maxlength="32" onkeyup="matchUtil(this, 'number')" onblur="matchUtil(this, 'number')"/></p>
                    <p class="condition">账号状态：
                        <select class="askSelect" id="accountStatus" name="accountStatus">
                            <option value="0">全部</option>
                            <option value="1">启用</option>
                            <option value="2">禁用</option>
                        </select>
                    </p>
                    <p class="condition">注册时间：
                        从&nbsp;<input placeholder="请选择起始时间" class="startTime" id="start" name="startTime" value="${startTime }"
                                      onfocus="this.blur()" onClick="laydate({istime: true,format:'YYYY-MM-DD hh:mm:ss'})" />
                        到&nbsp;<input placeholder="请选择结束时间" class="endTime" id="end" name="endTime" value="${endTime }"
                                      onfocus="this.blur()" onClick="laydate({istime: true,format: 'YYYY-MM-DD hh:mm:ss'})" />
                    </p>

                    <input type="hidden" id="queryPageNumber" name="pageNumber" value="${pageNumber}">
                    <input type="text" value="查&nbsp;询" class="ask" onfocus="this.blur()" onclick="queryForm()"/>
                    <input type="text" value="导&nbsp;出" class="educe" onfocus="this.blur()" onclick="exportData()"/>
                </form>
            </div>
        </div>

        <div class="bottom">
            <table class="table" cellpadding="0" cellspacing="0">
                <tr class="tableTitle">
                    <td class="time">注册时间</td>
                    <td class="account">用户账号</td>
                    <td class="money">账户可用余额</td>
                    <td class="money">冻结金额</td>
                    <td class="money">账户总金额</td>
                    <td class="state">账号状态</td>
                    <td class="operate">操作</td>
                </tr>
                <c:forEach items="${userList}" var="user">
                    <tr class="tableInfo">
                        <td class="time"><fmt:formatDate type="time" value="${user.addTime}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
                        <td class="account">${user.userAccount}</td>
                        <td class="money">$<fmt:formatNumber type="number" value="${user.userBalance }" maxFractionDigits="2"/></td>
                        <td class="money">$<fmt:formatNumber type="number" value="${user.userBalanceLock }" maxFractionDigits="2"/></td>
                        <td class="money">$<fmt:formatNumber type="number" value="${user.userBalance + user.userBalanceLock}" maxFractionDigits="2"/></td>
                        <c:if test="${user.accountStatus == 1}">
                            <td class="state">启用</td>
                        </c:if>
                        <c:if test="${user.accountStatus == 2}">
                            <td class="state">禁用</td>
                        </c:if>
                        <td class="operate">
                            <c:if test="${user.accountStatus == 1}">
                                <input type="text" class="start" value="启&nbsp;用" onfocus="this.blur()" />
                            </c:if>
                            <c:if test="${user.accountStatus == 2}">
                                <input type="text" class="stop" value="禁&nbsp;用" onfocus="this.blur()" />
                            </c:if>
                            <a href="#" target="_blank" class="details">账户明细</a>
                            <input type="text" class="addMoney" value="增加账户余额" onfocus="this.blur()" />
                            <input type="text" class="minusMoney" value="减少账户余额" onfocus="this.blur()" />
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
        <div class="start_pop">
            <p class="popTitle">启用操作</p>
            <p class="popTips"><img src="<%=path %>/resources/image/back/tips.png" class="tipsImg" />确定启用该账号？</p>

            <div class="buttons">
                <input type="text" value="取&nbsp;消" class="cancel" onfocus="this.blur()" />
                <input type="text" value="确&nbsp;定" class="yes" onfocus="this.blur()" onclick="openTip()" />
            </div>
        </div>

        <div class="stop_pop">
            <p class="popTitle">禁用操作</p>
            <p class="popTips"><img src="<%=path %>/resources/image/back/tips.png" class="tipsImg" />确定禁用该账号？</p>

            <div class="buttons">
                <input type="text" value="取&nbsp;消" class="cancel" onfocus="this.blur()" />
                <input type="text" value="确&nbsp;定" class="yes" onfocus="this.blur()" onclick="openTip()" />
            </div>
        </div>

        <div class="addMoney_pop">
            <p class="popTitle">增加账户余额</p>
            <p class="popInput">
                <label class="popName">用户账号</label>
                <span class="popAccount">ASDFGHJKLASDFGHJ</span>
            </p>
            <p class="popInput">
                <label class="popName">增加账户余额<span class="star">*</span></label>
                <input type="text" class="entry" placeholder="要增加的金额" />
            </p>
            <p class="popInput">
                <label class="popName" style="line-height: 20px">备注内容</label>
                <textarea class="txt" placeholder="备注内容，非必填"></textarea>
            </p>

            <div class="buttons">
                <input type="text" value="取&nbsp;消" class="cancel" onfocus="this.blur()" />
                <input type="text" value="确&nbsp;定" class="yes" onfocus="this.blur()" onclick="openTip()" />
            </div>
        </div>

        <div class="minusMoney_pop">
            <p class="popTitle">减少账户余额</p>
            <p class="popInput">
                <label class="popName">用户账号</label>
                <span class="popAccount">ASDFGHJKLASDFGHJ</span>
            </p>
            <p class="popInput">
                <label class="popName">减少账户余额<span class="star">*</span></label>
                <input type="text" class="entry" placeholder="要减少的金额" />
            </p>
            <p class="popInput">
                <label class="popName" style="line-height: 20px">备注内容</label>
                <textarea class="txt" placeholder="备注内容，非必填"></textarea>
            </p>

            <div class="buttons">
                <input type="text" value="取&nbsp;消" class="cancel" onfocus="this.blur()" />
                <input type="text" value="确&nbsp;定" class="yes" onfocus="this.blur()" onclick="openTip()" />
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
        $("#accountStatus option").each(function(){
            if($(this).val()=='${accountStatus}'){
                $(this).attr('selected',true);
            }
        });
    }

    function queryForm() {
        $("#queryPageNumber").val("0");
        $("#queryForm").submit();
    }

    //导出数据
    var exportDataBoo = false;
    function exportData() {
        if(exportDataBoo){
            return false;
        }else{
            exportDataBoo = true;
        }

        var pageNumber = $("#pageNumber").val();
        var startTime = $("#startTime").val();
        var endTime = $("#endTime").val();
        var userAccount = $("#userAccount").val();
        var phoneNumber = $("#phoneNumber").val();
        var accountStatus = $("#accountStatus").val();

        $.ajax({
            url: '<%=path %>' + "/backerWeb/backerUserAccount/exportData.htm",
            type:'post',
            dataType:'json',
            async:true,
            data:{
                pageNumber : pageNumber,
                startTime : startTime,
                endTime : endTime,
                userAccount : userAccount,
                phoneNumber : phoneNumber,
                accountStatus : accountStatus
            },
            success:function(result){
                if(result.code == 1) {
                    window.location.href = '<%=path%>' + result.message;
                } else {
                    openTips(result.message);
                }
                exportDataBoo = false;
            }, error:function(){
                exportDataBoo = false;
                openTips("导出失败,请重新刷新页面后重试");
            }
        });
    }

    var mapMatch = {};
    mapMatch['ENumber'] = /[^\a-\z\A-\Z\d]/g;
    mapMatch['number'] = /[^\d]/g;
    mapMatch['double'] = true;
    function matchUtil(o, str) {
        mapMatch[str] === true ? matchDouble(o, 4) : o.value = o.value.replace(mapMatch[str], '');
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


    var popObj;
    $(function(){
        $(".stop").click(function(){
            $(".mask").fadeIn();
            $(".stop_pop").fadeIn();
            popObj = ".stop_pop"
        });
        $(".start").click(function(){
            $(".mask").fadeIn();
            $(".start_pop").fadeIn();
            popObj = ".start_pop"
        });
        $(".addMoney").click(function(){
            $(".mask").fadeIn();
            $(".addMoney_pop").fadeIn();
            popObj = ".addMoney_pop"
        });
        $(".minusMoney").click(function(){
            $(".mask").fadeIn();
            $(".minusMoney_pop").fadeIn();
            popObj = ".minusMoney_pop"
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