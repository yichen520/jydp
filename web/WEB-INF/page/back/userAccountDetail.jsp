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
        <div class="caption">账户明细</div>

        <div class="bottom">
            <p class="account">当前用户账号：${userAccount}</p>

            <table class="table" cellspacing="0" cellpadding="0">
                <tr class="tableTitle">
                    <td class="coin">币种名称</td>
                    <td class="amount">可用数量</td>
                    <td class="amount">冻结数量</td>
                    <td class="amount">总数量</td>
                    <td class="operate">操作</td>
                </tr>
                <c:if test="${userCurrencyNumList != null and !empty userCurrencyNumList}">
                    <C:forEach items="${userCurrencyNumList}" var="item">
                        <tr class="tableInfo">
                            <td class="coin">${item.currencyName}</td>
                            <td class="amount"><fmt:formatNumber type="number" value="${item.currencyNumber }" maxFractionDigits="8"/></td>
                            <td class="amount"><fmt:formatNumber type="number" value="${item.currencyNumberLock }" maxFractionDigits="8"/></td>
                            <td class="amount"><fmt:formatNumber type="number" value="${item.currencyNumber + item.currencyNumberLock }" maxFractionDigits="8"/></td>
                            <td class="operate">
                                <c:if test="${backer_rolePower['141110'] == 141110}">
                                    <input type="text" class="addMoney" value="增加可用数量" onfocus="this.blur()" onclick="addCurrencyNumber('${item.currencyId}', '${item.currencyName}')" />
                                </c:if>
                                <c:if test="${backer_rolePower['141111'] == 141111}">
                                    <input type="text" class="minusMoney" value="减少可用数量" onfocus="this.blur()" onclick="reduceCurrencyNumber('${item.currencyId}', '${item.currencyName}', '${item.currencyNumber }')" />
                                </c:if>
                                <c:if test="${backer_rolePower['141112'] == 141112}">
                                    <input type="text" class="addFrozen" value="增加冻结数量" onfocus="this.blur()" onclick="addLockCurrencyNumber('${item.currencyId}', '${item.currencyName}')" />
                                </c:if>
                                <c:if test="${backer_rolePower['141113'] == 141113}">
                                    <input type="text" class="minusFrozen" value="减少冻结数量" onfocus="this.blur()" onclick="reduceLockCurrencyNumber('${item.currencyId}', '${item.currencyName}', '${item.currencyNumberLock }')" />
                                </c:if>
                            </td>
                        </tr>
                    </C:forEach>
                </c:if>
            </table>
        </div>
    </div>
</div>

<form id="detailForm" action="<%=path %>/backerWeb/backerUserAccountDetail/showDetail.htm" method="post">
    <input type="hidden" id="detailUserId" name="userId" value="${userId}">
</form>


<div id="footer"></div>


<div class="mask">
    <div class="mask_content">
        <div class="addMoney_pop">
            <p class="popTitle">增加可用数量</p>
            <p class="popInput">
                <label class="popName">增加可用数量<span class="star">*</span></label>
                <input type="text" id="addCurrencyNumber" class="entry" placeholder="要增加的数量"
                       maxLength="9"  onkeyup="matchUtil(this, 'double')" onblur="matchUtil(this, 'double')"/>
            </p>
            <p class="popInput">
                <label class="popName" style="line-height: 20px">备注内容</label>
                <textarea class="txt" maxLength="100" id="addMark" placeholder="备注内容，非必填"></textarea>
            </p>

            <input type="hidden" id="addCurrencyId" />
            <input type="hidden" id="addCurrencyName" />
            <div class="buttons">
                <input type="text" value="取&nbsp;消" class="cancel" onfocus="this.blur()" />
                <input type="text" value="确&nbsp;定" class="yes" onfocus="this.blur()" onclick="addCurrencyNumberSubmit()" />
            </div>
        </div>

        <div class="minusMoney_pop">
            <p class="popTitle">减少可用数量</p>
            <p class="popInput">
                <label class="popName">减少可用数量<span class="star">*</span></label>
                <input type="text" id="reduceCurrencyNumber" class="entry" placeholder="要减少的数量"
                       maxLength="9"  onkeyup="matchUtil(this, 'double')" onblur="matchUtil(this, 'double')"/>
            </p>
            <p class="popInput">
                <label class="popName" style="line-height: 20px">备注内容</label>
                <textarea class="txt" maxLength="100" id="reduceMark" placeholder="备注内容，非必填"></textarea>
            </p>

            <input type="hidden" id="reduceCurrencyId" />
            <input type="hidden" id="reduceCurrencyName" />
            <input type="hidden" id="reduceUserCurrency" />
            <div class="buttons">
                <input type="text" value="取&nbsp;消" class="cancel" onfocus="this.blur()" />
                <input type="text" value="确&nbsp;定" class="yes" onfocus="this.blur()" onclick="reduceCurrencyNumberSubmit()" />
            </div>
        </div>

        <div class="addFrozen_pop">
            <p class="popTitle">增加冻结数量</p>
            <p class="popInput">
                <label class="popName">增加冻结数量<span class="star">*</span></label>
                <input type="text" id="addLockCurrencyNumber" class="entry" placeholder="要增加的数量"
                       maxLength="9"  onkeyup="matchUtil(this, 'double')" onblur="matchUtil(this, 'double')"/>
            </p>
            <p class="popInput">
                <label class="popName" style="line-height: 20px">备注内容</label>
                <textarea class="txt" maxLength="100" id="addLockMark" placeholder="备注内容，非必填"></textarea>
            </p>

            <input type="hidden" id="addLockCurrencyId" />
            <input type="hidden" id="addLockCurrencyName" />
            <div class="buttons">
                <input type="text" value="取&nbsp;消" class="cancel" onfocus="this.blur()" />
                <input type="text" value="确&nbsp;定" class="yes" onfocus="this.blur()" onclick="addLockCurrencyNumberSubmit()" />
            </div>
        </div>

        <div class="minusFrozen_pop">
            <p class="popTitle">减少冻结数量</p>
            <p class="popInput">
                <label class="popName">减少冻结数量<span class="star">*</span></label>
                <input type="text" id="reduceLockCurrencyNumber" class="entry" placeholder="要减少的数量"
                       maxLength="9"  onkeyup="matchUtil(this, 'double')" onblur="matchUtil(this, 'double')"/>
            </p>
            <p class="popInput">
                <label class="popName" style="line-height: 20px">备注内容</label>
                <textarea class="txt" maxLength="100" id="reduceLockMark" placeholder="备注内容，非必填"></textarea>
            </p>

            <input type="hidden" id="reduceLockCurrencyId" />
            <input type="hidden" id="reduceLockCurrencyName" />
            <input type="hidden" id="reduceLockUserCurrency" />
            <div class="buttons">
                <input type="text" value="取&nbsp;消" class="cancel" onfocus="this.blur()" />
                <input type="text" value="确&nbsp;定" class="yes" onfocus="this.blur()" onclick="reduceLockCurrencyNumberSubmit()" />
            </div>
        </div>
    </div>
</div>


<script type="text/javascript" src="http://libs.baidu.com/jquery/2.1.4/jquery.min.js"></script>
<script type="text/javascript" src="<%=path %>/resources/js/loadPageBack.js"></script>
<script type="text/javascript" src="<%=path %>/resources/js/simpleTips.js"></script>

<script type="text/javascript">
    window.onload = function() {
        var code = '${code}';
        var message = '${message}';
        if (code != 1 && message != "") {
            openTips(message);
            return false;
        }
    }

    //增加用户账户币数量
    function addCurrencyNumber(currencyId, currencyName) {
        $(".mask").fadeIn();
        $(".addMoney_pop").fadeIn();
        popObj = ".addMoney_pop";

        $("#addCurrencyId").val(currencyId);
        $("#addCurrencyName").val(currencyName);
    }

    //减少用户账户币数量
    function reduceCurrencyNumber(currencyId, currencyName, currencyNumber) {
        $(".mask").fadeIn();
        $(".minusMoney_pop").fadeIn();
        popObj = ".minusMoney_pop";

        $("#reduceCurrencyId").val(currencyId);
        $("#reduceCurrencyName").val(currencyName);
        $("#reduceUserCurrency").val(currencyNumber);
    }

    //增加用户账户币数量
    var addCurrencyNumberBoo = false;
    function addCurrencyNumberSubmit() {
        if(addCurrencyNumberBoo){
            return false;
        }else{
            addCurrencyNumberBoo = true;
        }

        var addId = '${userId}';
        var addAccount = '${userAccount}';
        var addCurrencyNumberStr = $("#addCurrencyNumber").val();
        var addCurrencyNumber = parseFloat(addCurrencyNumberStr);
        var addMark = $("#addMark").val();
        var currencyId = $("#addCurrencyId").val();
        var currencyName = $("#addCurrencyName").val();

        $("#addCurrencyNumber").val("");
        $("#addMark").val("");
        if (addCurrencyNumberStr == null || addCurrencyNumberStr == "" || addCurrencyNumber <= 0) {
            addCurrencyNumberBoo = false;
            return openTips("请输入要增加的数量");
        }
        $.ajax({
            url: '<%=path %>' + "/backerWeb/backerUserAccountDetail/addCurrencyNumber.htm",
            type:'post',
            dataType:'json',
            async:true,
            data:{
                userId : addId,
                userAccount : addAccount,
                currencyNumber : addCurrencyNumber,
                currencyId : currencyId,
                currencyName : currencyName,
                remark : addMark
            },
            success:function(result){
                addCurrencyNumberBoo = false;
                if(result.code == 1) {
                    $(".mask").fadeOut("fast");
                    $(popObj).fadeOut("fast");
                    openTips(result.message)
                    setTimeout(function (){$("#detailForm").submit();}, 1000);
                } else {
                    openTips(result.message);
                }
            }, error:function(){
                addCurrencyNumberBoo = false;
                openTips("服务器异常，请稍后再试！");
            }
        });
    }

    //减少用户账户币数量
    var reduceCurrencyNumberBoo = false;
    function reduceCurrencyNumberSubmit() {
        if(reduceCurrencyNumberBoo){
            return false;
        }else{
            reduceCurrencyNumberBoo = true;
        }

        var reduceId = '${userId}';
        var reduceAccount = '${userAccount}';
        var reduceCurrencyNumberStr = $("#reduceCurrencyNumber").val();
        var reduceCurrencyNumber = parseFloat(reduceCurrencyNumberStr);
        var reduceMark = $("#reduceMark").val();
        var currencyId = $("#reduceCurrencyId").val();
        var currencyName = $("#reduceCurrencyName").val();
        var userCurrency = parseFloat($("#reduceUserCurrency").val());

        $("#reduceCurrencyNumber").val("");
        $("#reduceMark").val("");
        if (reduceCurrencyNumberStr == null || reduceCurrencyNumberStr == "" || reduceCurrencyNumber <= 0) {
            reduceCurrencyNumberBoo = false;
            return openTips("请输入要减少的数量");
        }
        if (userCurrency < reduceCurrencyNumber) {
            reduceCurrencyNumberBoo = false;
            return openTips("您输入的金额大于[" + currencyName + "]的可用数量");
        }
        $.ajax({
            url: '<%=path %>' + "/backerWeb/backerUserAccountDetail/reduceCurrencyNumber.htm",
            type:'post',
            dataType:'json',
            async:true,
            data:{
                userId : reduceId,
                userAccount : reduceAccount,
                currencyNumber : reduceCurrencyNumber,
                currencyId : currencyId,
                currencyName : currencyName,
                remark : reduceMark
            },
            success:function(result){
                reduceCurrencyNumberBoo = false;
                if(result.code == 1) {
                    $(".mask").fadeOut("fast");
                    $(popObj).fadeOut("fast");
                    openTips(result.message)
                    setTimeout(function (){$("#detailForm").submit();}, 1000);
                } else {
                    openTips(result.message);
                }
            }, error:function(){
                reduceCurrencyNumberBoo = false;
                openTips("服务器异常，请稍后再试！");
            }
        });
    }


    //增加用户账户冻结币数量
    function addLockCurrencyNumber(currencyId, currencyName) {
        $(".mask").fadeIn();
        $(".addFrozen_pop").fadeIn();
        popObj = ".addFrozen_pop"

        $("#addLockCurrencyId").val(currencyId);
        $("#addLockCurrencyName").val(currencyName);
    }

    //减少用户账户冻结币数量
    function reduceLockCurrencyNumber(currencyId, currencyName, currencyNumber) {
        $(".mask").fadeIn();
        $(".minusFrozen_pop").fadeIn();
        popObj = ".minusFrozen_pop"

        $("#reduceLockCurrencyId").val(currencyId);
        $("#reduceLockCurrencyName").val(currencyName);
        $("#reduceLockUserCurrency").val(currencyNumber);
    }

    //增加用户账户冻结币数量
    var addLockCurrencyNumberBoo = false;
    function addLockCurrencyNumberSubmit() {
        if(addLockCurrencyNumberBoo){
            return false;
        }else{
            addLockCurrencyNumberBoo = true;
        }

        var addId = '${userId}';
        var addAccount = '${userAccount}';
        var addCurrencyNumberStr = $("#addLockCurrencyNumber").val();
        var addCurrencyNumber = parseFloat(addCurrencyNumberStr);
        var addMark = $("#addLockMark").val();
        var currencyId = $("#addLockCurrencyId").val();
        var currencyName = $("#addLockCurrencyName").val();

        $("#addLockCurrencyNumber").val("");
        $("#addLockMark").val("");
        if (addCurrencyNumberStr == null || addCurrencyNumberStr == "" || addCurrencyNumber <= 0) {
            addLockCurrencyNumberBoo = false;
            return openTips("请输入要增加的数量");
        }
        $.ajax({
            url: '<%=path %>' + "/backerWeb/backerUserAccountDetail/addLockCurrencyNumber.htm",
            type:'post',
            dataType:'json',
            async:true,
            data:{
                userId : addId,
                userAccount : addAccount,
                currencyNumber : addCurrencyNumber,
                currencyId : currencyId,
                currencyName : currencyName,
                remark : addMark
            },
            success:function(result){
                addLockCurrencyNumberBoo = false;
                if(result.code == 1) {
                    $(".mask").fadeOut("fast");
                    $(popObj).fadeOut("fast");
                    openTips(result.message)
                    setTimeout(function (){$("#detailForm").submit();}, 1000);
                } else {
                    openTips(result.message);
                }
            }, error:function(){
                addLockCurrencyNumberBoo = false;
                openTips("服务器异常，请稍后再试！");
            }
        });
    }

    //减少用户账户冻结币数量
    var reduceLockCurrencyNumberBoo = false;
    function reduceLockCurrencyNumberSubmit() {
        if(reduceLockCurrencyNumberBoo){
            return false;
        }else{
            reduceLockCurrencyNumberBoo = true;
        }

        var reduceId = '${userId}';
        var reduceAccount = '${userAccount}';
        var reduceCurrencyNumberStr = $("#reduceLockCurrencyNumber").val();
        var reduceCurrencyNumber = parseFloat(reduceCurrencyNumberStr);
        var reduceMark = $("#reduceLockMark").val();
        var currencyId = $("#reduceLockCurrencyId").val();
        var currencyName = $("#reduceLockCurrencyName").val();
        var userCurrency = parseFloat($("#reduceLockUserCurrency").val());

        $("#reduceLockCurrencyNumber").val("");
        $("#reduceLockMark").val("");
        if (reduceCurrencyNumberStr == null || reduceCurrencyNumberStr == "" || reduceCurrencyNumber <= 0) {
            reduceLockCurrencyNumberBoo = false;
            return openTips("请输入要减少的数量");
        }
        if (userCurrency < reduceCurrencyNumber) {
            reduceLockCurrencyNumberBoo = false;
            return openTips("您输入的金额大于[" + currencyName + "]的冻结数量");
        }
        $.ajax({
            url: '<%=path %>' + "/backerWeb/backerUserAccountDetail/reduceLockCurrencyNumber.htm",
            type:'post',
            dataType:'json',
            async:true,
            data:{
                userId : reduceId,
                userAccount : reduceAccount,
                currencyNumber : reduceCurrencyNumber,
                currencyId : currencyId,
                currencyName : currencyName,
                remark : reduceMark
            },
            success:function(result){
                reduceLockCurrencyNumberBoo = false;
                if(result.code == 1) {
                    $(".mask").fadeOut("fast");
                    $(popObj).fadeOut("fast");
                    openTips(result.message)
                    setTimeout(function (){$("#detailForm").submit();}, 1000);
                } else {
                    openTips(result.message);
                }
            }, error:function(){
                reduceLockCurrencyNumberBoo = false;
                openTips("服务器异常，请稍后再试！");
            }
        });
    }


    var mapMatch = {};
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

    var popObj;
    $(function(){
        /*$(".addMoney").click(function(){
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