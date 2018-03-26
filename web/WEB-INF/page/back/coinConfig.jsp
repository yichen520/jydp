<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/resources/page/common/path.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <link rel="icon" href="<%=path %>/resources/image/back/icon.ico" type="image/x-ico" />

    <link rel="stylesheet" type="text/css" href="<%=path %>/resources/css/back/coinOut_manage.css" />
    <link rel="stylesheet" type="text/css" href="<%=path %>/resources/css/back/public.css" />
    <link rel="stylesheet" type="text/css" href="<%=path %>/resources/css/back/simpleTips.css" />

    <title>币种转出管理</title>
</head>
<body>
<div id="header"></div>


<div class="content">
    <div id="menu"></div>

    <div class="contentRight">
        <div class="caption">
            <span class="title">币种转出管理</span>
        </div>

        <div class="top">
            <c:if test="${backer_rolePower['161002'] == 161002}">
                <p class="add">新增币种转出</p>
            </c:if>
            <form id="queryForm" action="<%=path %>/backerWeb/backerCoinConfig/show.htm" method="post">
                <div class="askArea">
                    <p class="condition">
                        添加时间：
                        从&nbsp;<input placeholder="请选择起始时间" id="start" name="startAddTime" class="askTime" value="${addTime }" onfocus="this.blur()"/>
                        到&nbsp;<input placeholder="请选择结束时间" id="end" name="endAddTime" class="askTime" value="${endTime }" onfocus="this.blur()"/>
                    </p>
                    <p class="condition">管理员账号：<input type="text" class="askInput" value="${backerAccount}" id="backerAccount" name="backerAccount"
                                                      onkeyup="matchUtil(this, 'ENumber')" onblur="matchUtil(this, 'ENumber')" maxlength="16"/></p>
                    <p class="condition">币种：
                        <select class="askSelect" id="currencyName" name="currencyName">
                            <option value ="">全部</option>
                            <c:forEach items="${transactionCurrencyList}" var="item">
                                <option value="${item.currencyName}">${item.currencyName}</option>
                            </c:forEach>
                        </select>
                    </p>
                <c:if test="${backer_rolePower['161001'] == 161001}">
                    <input type="text" value="查&nbsp;询" class="ask" onfocus="this.blur()" onclick="queryForm()"/>
                </c:if>
                </div>
            </form>
        </div>

        <div class="bottom">
            <table class="table" cellspacing="0" cellpadding="0">
                <tr class="tableTitle">
                    <td class="time">添加时间</td>
                    <td class="coin">币种</td>
                    <td class="amount">免审转出数量</td>
                    <td class="amount">最低转出数量</td>
                    <td class="account">管理员账号</td>
                    <td class="ip">操作时IP</td>
                    <td class="operate">操作</td>
                </tr>

                <c:forEach items="${jydpCoinConfigList}" var="jydpCoinConfig">
                    <tr class="tableInfo">
                        <td class="time"><fmt:formatDate type="time" value="${jydpCoinConfig.addTime }" pattern="yyyy-MM-dd HH:mm:ss"></fmt:formatDate></td>
                        <td class="coin">${jydpCoinConfig.currencyName}</td>
                        <td class="amount">${jydpCoinConfig.freeCurrencyNumber}</td>
                        <td class="amount">${jydpCoinConfig.minCurrencyNumber}</td>
                        <td class="account">${jydpCoinConfig.backerAccount}</td>
                        <td class="ip">${jydpCoinConfig.ipAddress}</td>
                        <td class="operate">
                            <c:if test="${backer_rolePower['161003'] == 161003}">
                                <input type="text" value="删&nbsp;除" class="delete" onfocus="this.blur()" onclick="deleteCoin('${jydpCoinConfig.recordNo}')"/>
                            </c:if>
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </div>
    </div>
</div>


<div id="footer"></div>


<div class="mask">
    <div class="mask_content">
        <div class="delete_pop">
            <p class="popTitle">删除操作</p>
            <p class="popTips"><img src="<%=path%>/resources/image/back/tips.png" class="tipsImg" />确定删除？</p>

            <div class="buttons">
                <input type="hidden" name="deleteRecordNo" id="deleteRecordNo"/>
                <input type="text" value="取&nbsp;消" class="cancel" onfocus="this.blur()" />
                <input type="text" value="确&nbsp;定" class="yes" onfocus="this.blur()" onclick="deleteRecordNo()" />
            </div>
        </div>

        <div class="add_pop">
            <p class="popTitle">新增币种转出</p>
            <p class="popInput">
                <label class="popName">币种<span class="star">*</span></label>
                <select class="popSelected" id="addCurrencyName" name="addCurrencyName">
                    <option disabled selected value="">选择币种</option>
                    <c:forEach items="${transactionCurrencyList}" var="item">
                        <option value="${item.currencyName}">${item.currencyName}</option>
                    </c:forEach>
                </select>
            </p>
            <p class="popInput">
                <label class="popName">免审数量<span class="star">*</span></label>
                <input type="text" class="entry" placeholder="币种转出的免审数量，无则填“0”" id="addFreeCurrencyNumber" name="addFreeCurrencyNumber"
                       onkeyup="matchUtil(this, 'double', 4)" onblur="matchUtil(this, 'double', 4)" maxlength="18"/>
            </p>
            <p class="popInput">
                <label class="popName">最低数量<span class="star">*</span></label>
                <input type="text" class="entry" placeholder="币种转出的最低数量，无则填“0”" id="addMinCurrencyNumber" name="addMinCurrencyNumber"
                       onkeyup="matchUtil(this, 'double', 4)" onblur="matchUtil(this, 'double', 4)" maxlength="18"/>
            </p>

            <div class="buttons">
                <input type="text" value="取&nbsp;消" class="cancel" onfocus="this.blur()" />
                <input type="text" value="确&nbsp;定" class="yes" onfocus="this.blur()" onclick="addCoin()"/>
            </div>
        </div>
    </div>
</div>



<script type="text/javascript" src="<%=path %>/resources/js/laydate.js"></script>
<script type="text/javascript" src="http://libs.baidu.com/jquery/2.1.4/jquery.min.js"></script>
<script type="text/javascript" src="<%=path %>/resources/js/simpleTips.js"></script>
<script type="text/javascript" src="<%=path %>/resources/js/loadPageBack.js"></script>


<script type="text/javascript">
    window.onload = function() {
        var code = '${code}';
        var message = '${message}';
        $("#currencyName").val('${currencyName}');

        if (code != 1 && message != "") {
            openTips(message);
            return false;
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
        $(".delete").click(function(){
            // $(".mask").fadeIn();
            // $(".delete_pop").fadeIn();
            // popObj = ".delete_pop"
        });
        $(".add").click(function(){
            $(".mask").fadeIn();
            $(".add_pop").fadeIn();
            popObj = ".add_pop"
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

    var mapMatch = {};
    mapMatch['number'] = /[^\d]/g;
    mapMatch['ENumber'] = /[^\a-\z\A-\Z\d]/g;
    mapMatch['double'] = true;
    mapMatch['phone'] = /[^\d]/g;
    mapMatch['email'] = /([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\.[a-zA-Z0-9_-]{2,3}){1,2})$/;
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


    function queryForm() {
        $("#queryForm").submit();
    }


    //删除
    function deleteCoin(recordNo) {
        document.getElementById("deleteRecordNo").value = recordNo;

        $(".mask").show();
        $(".delete_pop").fadeIn();
        popObj = ".delete_pop"
    }

    var deleteNoticeBoo = false;
    function deleteRecordNo(){
        var deleteRecordNo = document.getElementById("deleteRecordNo").value;

        if(deleteNoticeBoo){
            openTips("正在删除，请稍后！");
            return;
        }else{
            deleteNoticeBoo = true;
        }
        $.ajax({
            url: '<%=path %>' + "/backerWeb/backerCoinConfig/deleteCoinConfig.htm",
            data: {
                recordNo : deleteRecordNo,
            },//参数
            dataType: "json",
            type: 'POST',
            async: true, //默认异步调用 (false：同步)
            success: function (resultData) {
                var code = resultData.code;
                var message = resultData.message;
                if (code != 1 && message != "") {
                    openTips(message);
                    deleteNoticeBoo = false;
                    return;
                }

                $("#queryForm").submit();
            },

            error: function () {
                deleteNoticeBoo = false;
                openTips("数据加载出错，请稍候重试");
            }
        });

    }

    //新增
    var addCheckBoo = false;
    function addCoin() {
        var addCurrencyName = document.getElementById("addCurrencyName").value;
        var addFreeCurrencyNumber = document.getElementById("addFreeCurrencyNumber").value;
        var addMinCurrencyNumber = document.getElementById("addMinCurrencyNumber").value;

        if(addCurrencyName == "" || addCurrencyName == null){
            return openTips("请选择币种");
        }
        if(addFreeCurrencyNumber == "" || addFreeCurrencyNumber == null){
            return openTips("请填写免审数量");
        }
        if(addFreeCurrencyNumber  < 0){
            return openTips("免审数量必须大于等于0");
        }

        if(addMinCurrencyNumber == "" || addMinCurrencyNumber == null){
            return openTips("请填写最低数量");
        }
        if(addMinCurrencyNumber  < 0){
            return openTips("最低数量必须大于等于0");
        }

        if(addCheckBoo){
            openTips("正在保存，请稍后！");
            return;
        }else{
            addCheckBoo = true;
        }

        $.ajax({
            url: '<%=path%>' + "/backerWeb/backerCoinConfig/addCoinConfig.htm", //方法路径URL
            data:{
                currencyName : addCurrencyName,
                freeCurrencyNumber : addFreeCurrencyNumber,
                minCurrencyNumber : addMinCurrencyNumber
            },//参数
            dataType: 'json',
            type: 'POST',
            async: true, //默认异步调用 (false：同步)
            success: function (result) {
                var code = result.code;
                var message = result.message;
                if (code != 1 && message != "") {
                    openTips(message);
                    addCheckBoo = false;
                    return;
                }
                addCheckBoo = false;
                $("#queryForm").submit();
            }, error: function () {
                dealBoo = false;
                openTips("数据加载出错，请稍候重试");
            }
        });

    }
</script>

</body>
</html>
