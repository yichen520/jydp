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
                        <select class="askSelect" id="phoneAreaCode" name="phoneAreaCode">
                            <option value="">选择区号</option>
                            <c:forEach items="${phoneAreaMap}" var="phoneArea">
                                <option value="${phoneArea.key }">${phoneArea.key }</option>
                            </c:forEach>
                        </select>
                        <input type="text" class="askInput" id="phoneNumber" name="phoneNumber" value="${phoneNumber }"
                               maxlength="11" onkeyup="matchUtil(this, 'number')" onblur="matchUtil(this, 'number')"/>
                    </p>
                    <p class="condition">审核状态：
                        <select class="askSelect" id="authenticationStatus" name="authenticationStatus">
                            <option value="0">全部</option>
                            <option value="1">待审核</option>
                            <option value="2">审核通过</option>
                            <option value="3">审核拒绝</option>
                            <option value="4">未提交</option>
                        </select>
                    </p>
                    <p class="condition">账号状态：
                        <select class="askSelect" id="accountStatus" name="accountStatus">
                            <option value="">全部</option>
                            <option value="1">启用</option>
                            <option value="2">禁用</option>
                        </select>
                    </p>
                    <p class="condition">注册时间：
                        从&nbsp;<input placeholder="请选择起始时间" class="askTime" id="start" name="startTime" value="${startTime }" onfocus="this.blur()"/>
                        到&nbsp;<input placeholder="请选择结束时间" class="askTime" id="end" name="endTime" value="${endTime }" onfocus="this.blur()"/>
                    </p>

                    <input type="hidden" id="queryPageNumber" name="pageNumber" value="${pageNumber}">
                    <c:if test="${backer_rolePower['141101'] == 141101}">
                        <input type="text" value="查&nbsp;询" class="ask" onfocus="this.blur()" onclick="queryForm()"/>
                    </c:if>
                    <c:if test="${backer_rolePower['141102'] == 141102}">
                        <input type="text" value="导&nbsp;出" class="educe" onfocus="this.blur()" onclick="exportData()"/>
                    </c:if>
                </form>
            </div>
        </div>

        <div class="bottom">
            <table class="table" cellpadding="0" cellspacing="0">
                <tr class="tableTitle">
                    <td class="time">注册时间</td>
                    <td class="account">用户信息</td>
                    <td class="money">账户可用余额</td>
                    <td class="money">冻结金额</td>
                    <td class="money">账户总金额</td>
                    <td class="state">审核状态</td>
                    <td class="state">账号状态</td>
                    <td class="operate">操作</td>
                </tr>
                <c:forEach items="${userList}" var="user">
                    <tr class="tableInfo">
                        <td class="time"><fmt:formatDate type="time" value="${user.addTime}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
                        <td class="account">
                            <p>账号：${user.userAccount}</p>
                            <p>手机：${user.phoneAreaCode}&nbsp;${user.phoneNumber}</p>
                        </td>
                        <td class="money">$<fmt:formatNumber type="number" value="${user.userBalance }" maxFractionDigits="8"/></td>
                        <td class="money">$<fmt:formatNumber type="number" value="${user.userBalanceLock }" maxFractionDigits="8"/></td>
                        <td class="money">$<fmt:formatNumber type="number" value="${user.userBalance + user.userBalanceLock}" maxFractionDigits="8"/></td>
                        <c:if test="${user.authenticationStatus == 1}">
                            <td class="state">待审核</td>
                        </c:if>
                        <c:if test="${user.authenticationStatus == 2}">
                            <td class="state">审核通过</td>
                        </c:if>
                        <c:if test="${user.authenticationStatus == 3}">
                            <td class="state">审核拒绝</td>
                        </c:if>
                        <c:if test="${user.authenticationStatus == 4}">
                            <td class="state">未提交</td>
                        </c:if>

                        <c:if test="${user.accountStatus == 1}">
                            <td class="state">启用</td>
                        </c:if>
                        <c:if test="${user.accountStatus == 2}">
                            <td class="state">禁用</td>
                        </c:if>
                        <td class="operate">
                            <c:if test="${backer_rolePower['141105'] == 141105}">
                                <c:if test="${user.accountStatus == 2}">
                                    <input type="text" class="start" value="启&nbsp;用" onfocus="this.blur()" onclick="unlock(${user.userId })"/>
                                </c:if>
                            </c:if>

                            <c:if test="${backer_rolePower['141104'] == 141104}">
                                <c:if test="${user.accountStatus == 1}">
                                    <input type="text" class="stop" value="禁&nbsp;用" onfocus="this.blur()" onclick="lock(${user.userId })"/>
                                </c:if>
                            </c:if>

                            <c:if test="${backer_rolePower['141103'] == 141103}">
                                <a href="#" onclick="showDetail('${user.userId}')" class="details">账户明细</a>
                            </c:if>
                            <c:if test="${backer_rolePower['141106'] == 141106}">
                                <input type="text" class="addMoney" value="增加可用余额" onfocus="this.blur()" onclick="addAmount('${user.userId }', '${user.userAccount }')"/>
                            </c:if>
                            <c:if test="${backer_rolePower['141107'] == 141107}">
                                <input type="text" class="minusMoney" value="减少可用余额" onfocus="this.blur()" onclick="reduceAmount('${user.userId }', '${user.userAccount }', '${user.userBalance }')"/>
                            </c:if>
                            <c:if test="${backer_rolePower['141108'] == 141108}">
                                <input type="text" class="addFrozen" value="增加冻结余额" onfocus="this.blur()" onclick="addLockAmount('${user.userId }', '${user.userAccount }')"/>
                            </c:if>
                            <c:if test="${backer_rolePower['141109'] == 141109}">
                                <input type="text" class="minusFrozen" value="减少冻结余额" onfocus="this.blur()" onclick="reduceLockAmount('${user.userId }', '${user.userAccount }', '${user.userBalanceLock }')"/>
                            </c:if>
                        </td>
                    </tr>
                </c:forEach>
            </table>

            <jsp:include page="/resources/page/common/paging.jsp"></jsp:include>
        </div>
    </div>
</div>

<form id="detailForm" action="<%=path %>/backerWeb/backerUserAccountDetail/showDetail.htm" target="_blank" method="post">
    <input type="hidden" id="detailUserId" name="userId">
</form>

<div id="footer"></div>


<div class="mask">
    <div class="mask_content">
        <div class="start_pop">
            <p class="popTitle">启用操作</p>
            <p class="popTips"><img src="<%=path %>/resources/image/back/tips.png" class="tipsImg" />确定启用该账号？</p>

            <div class="buttons">
                <input type="hidden" id="unlockId" />
                <input type="text" value="取&nbsp;消" class="cancel" onfocus="this.blur()" />
                <input type="text" value="确&nbsp;定" class="yes" onfocus="this.blur()" onclick="unlockSubmit()" />
            </div>
        </div>

        <div class="stop_pop">
            <p class="popTitle">禁用操作</p>
            <p class="popTips"><img src="<%=path %>/resources/image/back/tips.png" class="tipsImg" />确定禁用该账号？</p>

            <div class="buttons">
                <input type="hidden" id="lockId" />
                <input type="text" value="取&nbsp;消" class="cancel" onfocus="this.blur()" />
                <input type="text" value="确&nbsp;定" class="yes" onfocus="this.blur()" onclick="lockSubmit()" />
            </div>
        </div>

        <div class="addMoney_pop">
            <p class="popTitle">增加可用余额</p>
            <p class="popInput">
                <label class="popName">用户账号</label>
                <span class="popAccount" id="addAccount"></span>
            </p>
            <p class="popInput">
                <label class="popName">增加账户余额<span class="star">*</span></label>
                <input type="text" id="addBalanceNumber" class="entry" placeholder="要增加的金额"
                       maxLength="9"  onkeyup="matchUtil(this, 'double')" onblur="matchUtil(this, 'double')"/>
            </p>
            <p class="popInput">
                <label class="popName" style="line-height: 20px">备注内容</label>
                <textarea class="txt" maxLength="100" id="addMark" placeholder="备注内容，非必填"></textarea>
            </p>

            <input type="hidden" id="addId" />
            <div class="buttons">
                <input type="text" value="取&nbsp;消" class="cancel" onfocus="this.blur()" />
                <input type="text" value="确&nbsp;定" class="yes" onfocus="this.blur()" onclick="addAmountSubmit()" />
            </div>
        </div>

        <div class="minusMoney_pop">
            <p class="popTitle">减少可用余额</p>
            <p class="popInput">
                <label class="popName">用户账号</label>
                <span class="popAccount" id="reduceAccount"></span>
            </p>
            <p class="popInput">
                <label class="popName">减少可用余额<span class="star">*</span></label>
                <input type="text" id="reduceBalanceNumber" class="entry" placeholder="要减少的金额"
                       maxLength="9"  onkeyup="matchUtil(this, 'double')" onblur="matchUtil(this, 'double')"/>
            </p>
            <p class="popInput">
                <label class="popName" style="line-height: 20px">备注内容</label>
                <textarea class="txt" maxLength="100"  id="reduceMark" placeholder="备注内容，非必填"></textarea>
            </p>

            <input type="hidden" id="reduceId" />
            <input type="hidden" id="reduceUserBalance" />
            <div class="buttons">
                <input type="text" value="取&nbsp;消" class="cancel" onfocus="this.blur()" />
                <input type="text" value="确&nbsp;定" class="yes" onfocus="this.blur()" onclick="reduceAmountSubmit()" />
            </div>
        </div>


        <div class="addFrozen_pop">
            <p class="popTitle">增加冻结余额</p>
            <p class="popInput">
                <label class="popName">用户账号</label>
                <span class="popAccount"  id="addLockAccount"></span>
            </p>
            <p class="popInput">
                <label class="popName">增加冻结余额<span class="star">*</span></label>
                <input type="text" id="addLockBalanceNumber" class="entry" placeholder="要增加的金额"
                       maxLength="9"  onkeyup="matchUtil(this, 'double')" onblur="matchUtil(this, 'double')"/>
            </p>
            <p class="popInput">
                <label class="popName" style="line-height: 20px">备注内容</label>
                <textarea class="txt" maxLength="100" id="addLockMark" placeholder="备注内容，非必填"></textarea>
            </p>

            <input type="hidden" id="addLockId" />
            <div class="buttons">
                <input type="text" value="取&nbsp;消" class="cancel" onfocus="this.blur()" />
                <input type="text" value="确&nbsp;定" class="yes" onfocus="this.blur()" onclick="addLockAmountSubmit()" />
            </div>
        </div>

        <div class="minusFrozen_pop">
            <p class="popTitle">减少冻结余额</p>
            <p class="popInput">
                <label class="popName">用户账号</label>
                <span class="popAccount" id="reduceLockAccount"></span>
            </p>
            <p class="popInput">
                <label class="popName">减少冻结余额<span class="star">*</span></label>
                <input type="text" id="reduceLockBalanceNumber" class="entry" placeholder="要减少的金额"
                       maxLength="9"  onkeyup="matchUtil(this, 'double')" onblur="matchUtil(this, 'double')"/>
            </p>
            <p class="popInput">
                <label class="popName" style="line-height: 20px">备注内容</label>
                <textarea class="txt" maxLength="100"  id="reduceLockMark" placeholder="备注内容，非必填"></textarea>
            </p>

            <input type="hidden" id="reduceLockId" />
            <input type="hidden" id="reduceLockUserBalance" />
            <div class="buttons">
                <input type="text" value="取&nbsp;消" class="cancel" onfocus="this.blur()" />
                <input type="text" value="确&nbsp;定" class="yes" onfocus="this.blur()" onclick="reduceLockAmountSubmit()" />
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
        $("#phoneAreaCode option").each(function(){
            if($(this).val()=='${phoneAreaCode}'){
                $(this).attr('selected',true);
            }
        });
        $("#authenticationStatus option").each(function(){
            if($(this).val()=='${authenticationStatus}'){
                $(this).attr('selected',true);
            }
        });
    }

    function queryForm() {
        $("#queryPageNumber").val("0");
        $("#queryForm").submit();
    }

    function showDetail(userId) {
        $("#detailUserId").val(userId);
        $("#detailForm").submit();
    }

    //启用
    function unlock(userId) {
        $(".mask").fadeIn();
        $(".start_pop").fadeIn();
        popObj = ".start_pop"

        $("#unlockId").val(userId);
    }

    //禁用
    function lock(userId) {
        $(".mask").fadeIn();
        $(".stop_pop").fadeIn();
        popObj = ".stop_pop"

        $("#lockId").val(userId);
    }

    //启用
    var unlockBoo = false;
    function unlockSubmit() {
        if(unlockBoo){
            return false;
        }else{
            unlockBoo = true;
        }

        var userId = $("#unlockId").val();
        $.ajax({
            url: '<%=path %>' + "/backerWeb/backerUserAccount/unlock.htm",
            type:'post',
            dataType:'json',
            async:true,
            data:{
                userId : userId
            },
            success:function(result){
                unlockBoo = false;
                if(result.code == 1) {
                    $(".mask").fadeOut("fast");
                    $(popObj).fadeOut("fast");
                    openTips(result.message)
                    setTimeout(function (){$("#queryForm").submit();}, 1000);
                } else {
                    openTips(result.message);
                }
            }, error:function(){
                unlockBoo = false;
                openTips("服务器异常，请稍后再试！");
            }
        });
    }

    //禁用
    var lockBoo = false;
    function lockSubmit() {
        if(lockBoo){
            return false;
        }else{
            lockBoo = true;
        }

        var userId = $("#lockId").val();
        $.ajax({
            url: '<%=path %>' + "/backerWeb/backerUserAccount/lock.htm",
            type:'post',
            dataType:'json',
            async:true,
            data:{
                userId : userId
            },
            success:function(result){
                lockBoo = false;
                if(result.code == 1) {
                    $(".mask").fadeOut("fast");
                    $(popObj).fadeOut("fast");
                    openTips(result.message)
                    setTimeout(function (){$("#queryForm").submit();}, 1000);
                } else {
                    openTips(result.message);
                }
            }, error:function(){
                lockBoo = false;
                openTips("服务器异常，请稍后再试！");
            }
        });
    }

    //增加账户金额
    function addAmount(userId, userAccount) {
        $(".mask").fadeIn();
        $(".addMoney_pop").fadeIn();
        popObj = ".addMoney_pop"

        $("#addId").val(userId);
        $("#addAccount").html(userAccount);
    }

    //减少账户金额
    function reduceAmount(userId, userAccount, userBalance) {
        $(".mask").fadeIn();
        $(".minusMoney_pop").fadeIn();
        popObj = ".minusMoney_pop"

        $("#reduceId").val(userId);
        $("#reduceAccount").html(userAccount);
        $("#reduceUserBalance").val(userBalance);
    }

    //增加账户金额
    var addAmountBoo = false;
    function addAmountSubmit() {
        if(addAmountBoo){
            return false;
        }else{
            addAmountBoo = true;
        }

        var addId = $("#addId").val();
        var addAccount = $("#addAccount").html();
        var addBalanceNumberStr = $("#addBalanceNumber").val();
        var addBalanceNumber = parseFloat(addBalanceNumberStr);
        var addMark = $("#addMark").val();

        $("#addBalanceNumber").val("");
        $("#addMark").val("");
        if (addBalanceNumberStr == null || addBalanceNumberStr == "" || addBalanceNumber <= 0) {
            addAmountBoo = false;
            return openTips("请输入要增加的金额");
        }
        $.ajax({
            url: '<%=path %>' + "/backerWeb/backerUserAccount/addAmount.htm",
            type:'post',
            dataType:'json',
            async:true,
            data:{
                userId : addId,
                userAccount : addAccount,
                balanceNumber : addBalanceNumber,
                remark : addMark
            },
            success:function(result){
                addAmountBoo = false;
                if(result.code == 1) {
                    $(".mask").fadeOut("fast");
                    $(popObj).fadeOut("fast");
                    openTips(result.message)
                    setTimeout(function (){$("#queryForm").submit();}, 1000);
                } else {
                    openTips(result.message);
                }
            }, error:function(){
                addAmountBoo = false;
                openTips("服务器异常，请稍后再试！");
            }
        });
    }

    //减少账户金额
    var reduceAmountBoo = false;
    function reduceAmountSubmit() {
        if(reduceAmountBoo){
            return false;
        }else{
            reduceAmountBoo = true;
        }

        var reduceId = $("#reduceId").val();
        var reduceAccount = $("#reduceAccount").html();
        var reduceBalanceNumberStr = $("#reduceBalanceNumber").val();
        var reduceBalanceNumber = parseFloat(reduceBalanceNumberStr);
        var reduceMark = $("#reduceMark").val();
        var userBalance = parseFloat($("#reduceUserBalance").val());

        $("#reduceBalanceNumber").val("");
        $("#reduceMark").val("");
        if (reduceBalanceNumberStr == null || reduceBalanceNumberStr == "" || reduceBalanceNumber <= 0) {
            reduceAmountBoo = false;
            return openTips("请输入要减少的金额");
        }
        if (userBalance < reduceBalanceNumber) {
            reduceAmountBoo = false;
            return openTips("您输入的金额大于该账户的可用余额");
        }
        $.ajax({
            url: '<%=path %>' + "/backerWeb/backerUserAccount/reduceAmount.htm",
            type:'post',
            dataType:'json',
            async:true,
            data:{
                userId : reduceId,
                userAccount : reduceAccount,
                balanceNumber : reduceBalanceNumber,
                remark : reduceMark
            },
            success:function(result){
                reduceAmountBoo = false;
                if(result.code == 1) {
                    $(".mask").fadeOut("fast");
                    $(popObj).fadeOut("fast");
                    openTips(result.message)
                    setTimeout(function (){$("#queryForm").submit();}, 1000);
                } else {
                    openTips(result.message);
                }
            }, error:function(){
                reduceAmountBoo = false;
                openTips("服务器异常，请稍后再试！");
            }
        });
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
        var phoneAreaCode = $("#phoneAreaCode").val();
        var phoneNumber = $("#phoneNumber").val();
        var authenticationStatus = $("#authenticationStatus").val();
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
                phoneAreaCode : phoneAreaCode,
                phoneNumber : phoneNumber,
                authenticationStatus : authenticationStatus,
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

    //增加账户冻结金额
    function addLockAmount(userId, userBalanceLock) {
        $(".mask").fadeIn();
        $(".addFrozen_pop").fadeIn();
        popObj = ".addFrozen_pop"

        $("#addLockId").val(userId);
        $("#addLockAccount").html(userBalanceLock);
    }

    //减少账户冻结金额
    function reduceLockAmount(userId, userAccount, userBalanceLock) {
        $(".mask").fadeIn();
        $(".minusFrozen_pop").fadeIn();
        popObj = ".minusFrozen_pop"

        $("#reduceLockId").val(userId);
        $("#reduceLockAccount").html(userAccount);
        $("#reduceLockUserBalance").val(userBalanceLock);
    }

    //增加账户冻结金额
    var addLockAmountBoo = false;
    function addLockAmountSubmit() {
        if(addLockAmountBoo){
            return false;
        }else{
            addLockAmountBoo = true;
        }

        var addLockId = $("#addLockId").val();
        var addLockAccount = $("#addLockAccount").html();
        var addLockBalanceNumberStr = $("#addLockBalanceNumber").val();
        var addLockBalanceNumber = parseFloat(addLockBalanceNumberStr);
        var addLockMark = $("#addLockMark").val();

        $("#addLockBalanceNumber").val("");
        $("#addLockMark").val("");
        if (addLockBalanceNumberStr == null || addLockBalanceNumberStr == "" || addLockBalanceNumber <= 0) {
            addLockAmountBoo = false;
            return openTips("请输入要增加的冻结金额");
        }
        $.ajax({
            url: '<%=path %>' + "/backerWeb/backerUserAccount/addLockAmount.htm",
            type:'post',
            dataType:'json',
            async:true,
            data:{
                userId : addLockId,
                userAccount : addLockAccount,
                balanceNumber : addLockBalanceNumber,
                remark : addLockMark
            },
            success:function(result){
                addLockAmountBoo = false;
                if(result.code == 1) {
                    $(".mask").fadeOut("fast");
                    $(popObj).fadeOut("fast");
                    openTips(result.message)
                    setTimeout(function (){$("#queryForm").submit();}, 1000);
                } else {
                    openTips(result.message);
                }
            }, error:function(){
                addLockAmountBoo = false;
                openTips("服务器异常，请稍后再试！");
            }
        });
    }

    //减少账户冻结金额
    var reduceLockAmountBoo = false;
    function reduceLockAmountSubmit() {
        if(reduceLockAmountBoo){
            return false;
        }else{
            reduceLockAmountBoo = true;
        }

        var reduceLockId = $("#reduceLockId").val();
        var reduceLockAccount = $("#reduceLockAccount").html();
        var reduceLockBalanceNumberStr = $("#reduceLockBalanceNumber").val();
        var reduceLockBalanceNumber = parseFloat(reduceLockBalanceNumberStr);
        var reduceLockMark = $("#reduceLockMark").val();
        var userBalance = parseFloat($("#reduceLockUserBalance").val());

        $("#reduceLockBalanceNumber").val("");
        $("#reduceLockMark").val("");
        if (reduceLockBalanceNumberStr == null || reduceLockBalanceNumberStr == "" || reduceLockBalanceNumber <= 0) {
            reduceLockAmountBoo = false;
            return openTips("请输入要减少的冻结金额");
        }
        if (userBalance < reduceLockBalanceNumber) {
            reduceLockAmountBoo = false;
            return openTips("您输入的金额大于该账户剩余的冻结金额");
        }
        $.ajax({
            url: '<%=path %>' + "/backerWeb/backerUserAccount/reduceLockAmount.htm",
            type:'post',
            dataType:'json',
            async:true,
            data:{
                userId : reduceLockId,
                userAccount : reduceLockAccount,
                balanceNumber : reduceLockBalanceNumber,
                remark : reduceLockMark
            },
            success:function(result){
                reduceLockAmountBoo = false;
                if(result.code == 1) {
                    $(".mask").fadeOut("fast");
                    $(popObj).fadeOut("fast");
                    openTips(result.message)
                    setTimeout(function (){$("#queryForm").submit();}, 1000);
                } else {
                    openTips(result.message);
                }
            }, error:function(){
                reduceLockAmountBoo = false;
                openTips("服务器异常，请稍后再试！");
            }
        });
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

    lay('.askTime').each(function(){
        laydate.render({
            elem: this,
            trigger: 'click',
            type:'datetime',
            theme: '#69c0ff'
        });
    });//日期控件

    var popObj;
    $(function(){
        /*$(".stop").click(function(){
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
        $(".addFrozen").click(function(){
            $(".mask").fadeIn();
            $(".addFrozen_pop").fadeIn();
            popObj = ".addFrozen_pop"
        });
        $(".minusFrozen").click(function(){
            $(".mask").fadeIn();
            $(".minusFrozen_pop").fadeIn();
            popObj = ".minusFrozen_pop"
        });*/
        $(".cancel").click(function(){
            $(".mask").fadeOut("fast");
            $(popObj).fadeOut("fast");
        });
        /*$(".yes").click(function(){
            $(".mask").fadeOut("fast");
            $(popObj).fadeOut("fast");
        });*/
    });
</script>
</body>
</html>