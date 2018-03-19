<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/resources/page/common/path.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>



<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <link rel="icon" href="<%=path %>/resources/image/back/icon.ico" type="image/x-ico" />

    <link rel="stylesheet" type="text/css" href="<%=path %>/resources/css/back/manageCoefficient.css" />
    <link rel="stylesheet" type="text/css" href="<%=path %>/resources/css/back/public.css" />
    <link rel="stylesheet" type="text/css" href="<%=path %>/resources/css/back/simpleTips.css" />

    <title>系数管理</title>
</head>
<body>
<div id="header"></div>


<div class="content">
    <div id="menu"></div>

    <div class="contentRight">
        <div class="caption">
            <span class="title">系数管理</span>
        </div>

        <div class="top">
            <a class="add">新增系数</a>
            <form id="queryForm" action="<%=path %>/backerWeb/transactionCurrencyCoefficient/show.htm" method="post">
                <div class="askArea">
                    <p class="condition">添加时间：
                        从&nbsp;<input placeholder="请选择起始时间" class="askTime" id="start" name="startAddTime"
                                      value="${startAddTime}" onfocus="this.blur()"/>
                        到&nbsp;<input placeholder="请选择结束时间" class="askTime" id="end" name="endAddTime"
                                      value="${endAddTime}" onfocus="this.blur()"/>
                    </p>
                    <p class="condition">币种名称：
                        <select class="askSelect" id="currencyId" name="currencyName">
                            <option value="">全部</option>
                            <c:forEach items="${transactionCurrencyList}" var="item">
                                <option value="${item.currencyName}">${item.currencyName}</option>
                            </c:forEach>
                        </select>
                    </p>

                    <input type="hidden" id="queryPageNumber" name="pageNumber" value="${pageNumber}">
                    <input type="text" value="查&nbsp;询" class="ask" onfocus="this.blur()" onclick="queryForm()"/>
                </div>
            </form>
        </div>

        <div class="bottom">
            <table class="table" cellspacing="0" cellpadding="0">
                <tr class="tableTitle">
                    <td class="time">添加时间</td>
                    <td class="coin">币种</td>
                    <td class="coefficient">系数</td>
                    <td class="account">操作管理员</td>
                    <td class="ip">操作IP</td>
                    <td class="operate">操作</td>
                </tr>
                <c:forEach items="${currencyCoefficientList}" var="item">
                    <tr class="tableInfo">
                        <td class="time"><fmt:formatDate type="time" value="${item.addTime}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
                        <td class="coin">${item.currencyName}</td>
                        <td class="coefficient"><fmt:formatNumber type="number" value="${item.currencyCoefficient }" maxFractionDigits="4"/></td>
                        <td class="account">${item.backerAccount}</td>
                        <td class="ip">${item.ipAddress}</td>
                        <td class="operate">
                            <input type="text" value="删&nbsp;除" class="delete" onfocus="this.blur()" onclick="goDel('${item.orderNo}')"/>
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
        <div class="add_pop">
            <p class="popTitle">新增币种</p>
            <p class="popInput">
                <label class="popName">币种<span class="star">*</span></label>
                <select class="popSelected" id="currencyNameAd" name="currencyNameAd">
                    <option disabled selected>选择币种</option>
                    <c:forEach items="${transactionCurrencyList}" var="item">
                        <option value="${item.currencyName}">${item.currencyName}</option>
                    </c:forEach>
                </select>
            </p>
            <p class="popInput">
                <label class="popName">系数<span class="star">*</span></label>
                <input type="text" class="entry" placeholder="请输入系数" id="currencyCoefficientAd" name="currencyCoefficientAd"
                       maxlength="5" onkeyup="matchUtil(this, 'double', 2)" onblur="matchUtil(this, 'double', 2)"/>
            </p>

            <div class="buttons">
                <input type="text" value="取&nbsp;消" class="cancel" onfocus="this.blur()" />
                <input type="text" value="确&nbsp;定" class="yes" onfocus="this.blur()" onclick="add()" />
            </div>
        </div>

        <div class="delete_pop">
            <p class="popTitle">删除操作</p>
            <p class="popTips"><img src="<%=path %>/resources/image/back/tips.png" class="tipsImg" />确定删除？</p>

            <input type="hidden" id="delOrderNo" name="delOrderNo">
            <div class="buttons">
                <input type="text" value="取&nbsp;消" class="cancel" onfocus="this.blur()" />
                <input type="text" value="确&nbsp;定" class="yes" onfocus="this.blur()" onclick="del()" />
            </div>
        </div>
    </div>
</div>


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

    var popObj;
    $(function(){
        $(".add").click(function(){
            $(".mask").fadeIn();
            $(".add_pop").fadeIn();
            popObj = ".add_pop"
        });
        $(".delete").click(function(){

        });
        $(".cancel").click(function(){
            $(".mask").fadeOut("fast");
            $(popObj).fadeOut("fast");
        });
        $(".yes").click(function(){
        });
    });

    //查询
    function queryForm() {
        $("#queryPageNumber").val("0");
        $("#queryForm").submit();
    }
</script>

<script type="text/javascript">
    window.onload = function() {
        var code = '${code}';
        var message = '${message}';
        if (code != 1 && message != "") {
            openTips(message);
            return ;
        }

        $("#currencyId option").each(function(){
            if($(this).val()=='${currencyName}'){
                $(this).attr('selected',true);
            }
        });
    }


    //去删除页面
    function goDel(orderNo){
        $("#delOrderNo").val(orderNo);

        $(".mask").fadeIn();
        $(".delete_pop").fadeIn();
        popObj = ".delete_pop"
    }

    //新增
    var addBoo = false
    function add() {
        if (addBoo) {
            return ;
        } else {
            addBoo = true;
        }

        var currencyNameAd = document.getElementById("currencyNameAd").value;
        var currencyCoefficientAd = document.getElementById("currencyCoefficientAd").value;

        if (currencyNameAd == null || currencyNameAd == "") {
            addBoo = false;
            openTips("请输入币种名称");
            return;
        }
        if (currencyCoefficientAd == null || currencyCoefficientAd == "" || parseFloat(currencyCoefficientAd) < 0
            || parseFloat(currencyCoefficientAd) == null || parseFloat(currencyCoefficientAd) == "") {
            addBoo = false;
            openTips("请确认币种系数大于0");
            return;
        }

        $.ajax({
            url: '<%=path %>' + "/backerWeb/transactionCurrencyCoefficient/add.htm",
            data:{
                currencyNameAd : currencyNameAd,
                currencyCoefficientAd : currencyCoefficientAd
            },//参数
            dataType: "json",
            type: 'POST',
            async: true, //默认异步调用 (false：同步)
            success: function (resultData) {
                var code = resultData.code;
                var message = resultData.message;
                if (message != "") {
                    openTips(message);
                }
                if (code == 1){
                    $(".mask").fadeOut("fast");
                    $(popObj).fadeOut("fast");
                    setTimeout(function (){queryForm();}, 1000);
                }

                addBoo = false;
            },

            error: function () {
                addBoo = false;
                openTips("数据加载出错，请稍候重试");
            }
        });
    }

    //删除
    delBoo = false;
    function del() {
        if (delBoo) {
            return ;
        } else {
            delBoo = true;
        }

        var orderNo = $("#delOrderNo").val();
        if (orderNo == null || orderNo == ""){
            delBoo =false;
            openTips("订单号获取错误");
            return;
        }

        $.ajax({
            url: '<%=path %>' + "/backerWeb/transactionCurrencyCoefficient/del.htm",
            data: {
                orderNo : orderNo
            },//参数
            dataType: "json",
            type: 'POST',
            async: true, //默认异步调用 (false：同步)
            success: function (resultData) {
                var code = resultData.code;
                var message = resultData.message;
                if (message != "") {
                    openTips(message);
                }
                if (code == 1){
                    setTimeout(function (){queryForm();}, 1000);
                }

                delBoo = false;
            },

            error: function () {
                delBoo = false;
                openTips("数据加载出错，请稍候重试");
            }
        });

    }


    var mapMatch = {};
    mapMatch['double'] = true;
    function matchUtil(o, str, nu) {
        mapMatch[str] === true ? matchDouble(o, nu) : o.value = o.value.replace(mapMatch[str], '');
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