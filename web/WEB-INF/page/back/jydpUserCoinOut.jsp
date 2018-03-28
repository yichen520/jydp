<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/resources/page/common/path.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <link rel="icon" href="<%=path %>/resources/image/back/icon.ico" type="image/x-ico" />

    <link rel="stylesheet" type="text/css" href="<%=path %>/resources/css/back/coinExamine.css" />
    <link rel="stylesheet" type="text/css" href="<%=path %>/resources/css/back/public.css" />
    <link rel="stylesheet" type="text/css" href="<%=path %>/resources/css/back/simpleTips.css" />

    <title>币种转出审核</title>
</head>
<body>
<div id="header"></div>


<div class="content">
    <div id="menu"></div>

    <div class="contentRight">
        <div class="caption">
            <span class="title">币种转出审核</span>
        </div>

        <div class="top">
            <form id="queryForm" action="<%=path %>/backerWeb/backerUserCoinOut/show.htm" method="post">
                <div class="askArea">
                    <p class="condition">申请流水号：<input type="text" class="askInput" id="coinRecordNo" name="coinRecordNo" value="${coinRecordNo}"
                                                      maxlength="15" onkeyup="matchUtil(this, 'ENumber')" onblur="matchUtil(this, 'ENumber')"/></p>
                    <p class="condition">申请账号：<input type="text" class="askInput" id="userAccount" name="userAccount" value="${userAccount}"
                                                     maxlength="16" onkeyup="matchUtil(this, 'ENumber')" onblur="matchUtil(this, 'ENumber')"/></p>
                    <p class="condition">转入账号：<input type="text" class="askInput" id="walletAccount" name="walletAccount" value="${walletAccount}"
                                                     maxlength="16" onkeyup="matchUtil(this, 'ENumber')" onblur="matchUtil(this, 'ENumber')"/></p>
                    <p class="condition">币种：
                        <select class="askSelect" id="currencyId" name="currencyId">
                            <option value="0">全部</option>
                            <c:forEach items="${transactionCurrencyList}" var="item">
                                <option value="${item.currencyId}">${item.currencyName}</option>
                            </c:forEach>
                        </select>
                    </p>
                    <p class="condition">审核状态：
                        <select class="askSelect" id="handleStatus" name="handleStatus">
                            <option value="0">全部</option>
                            <option value="1">待审核</option>
                            <option value="2">审核通过</option>
                            <option value="3">审核拒绝</option>
                            <option value="4">已撤回</option>
                        </select>
                    </p>
                    <p class="condition">
                        申请时间：
                        从&nbsp;<input placeholder="请选择起始时间" class="askTime" name="startAddTime"
                                      value="${startAddTime }" onfocus="this.blur()"/>
                        到&nbsp;<input placeholder="请选择结束时间" class="askTime" name="endAddTime"
                                      value="${endAddTime }" onfocus="this.blur()"/>
                    </p>
                    <p class="condition">
                        完成时间：
                        从&nbsp;<input placeholder="请选择起始时间" class="askTime" name="startFinishTime"
                                      value="${startFinishTime }" onfocus="this.blur()"/>
                        到&nbsp;<input placeholder="请选择结束时间" class="askTime" name="endFinishTime"
                                      value="${endFinishTime }" onfocus="this.blur()"/>
                    </p>
                    <input type="hidden" id="queryPageNumber" name="pageNumber" value="${pageNumber}">
                    <c:if test="${backer_rolePower['162001'] == 162001}">
                        <input type="text" value="查&nbsp;询" class="ask" onfocus="this.blur()" onclick="queryForm()"/>
                    </c:if>
                </div>
            </form>
        </div>

        <div class="topOperate">
            <c:if test="${backer_rolePower['162003'] == 162003}">
                <input type="text" value="审核拒绝" class="refuse" onfocus="this.blur()" onclick="selectMoreReject()"/>
            </c:if>
            <c:if test="${backer_rolePower['162002'] == 162002}">
                <input type="text" value="审核通过" class="pass" onfocus="this.blur()" onclick="selectMoreThrough()"/>
            </c:if>
        </div>

        <div class="bottom">
            <table class="table" cellspacing="0" cellpadding="0">
                <tr class="tableTitle">
                    <td class="time">申请时间</td>
                    <td class="number">申请流水号</td>
                    <td class="account">申请账号</td>
                    <td class="coin">币种</td>
                    <td class="amount">申请数量</td>
                    <td class="account">转入账号</td>
                    <td class="time">完成时间</td>
                    <td class="state">审核状态</td>
                    <td class="mark">备注</td>
                    <td class="check">
                        <label class="controlAll">
                            <input type="checkbox" id="controlAll" name="controlAll" onclick="selectAll()" value=""/>
                            <span>全选</span>
                        </label>
                    </td>
                </tr>

                <c:forEach items="${jydpUserCoinOutRecordList}" var="jydpUserCoinOutRecord">
                    <tr class="tableInfo">
                        <td class="time"><fmt:formatDate type="time" value="${jydpUserCoinOutRecord.addTime }" pattern="yyyy-MM-dd HH:mm:ss"></fmt:formatDate></td>
                        <td class="number">${jydpUserCoinOutRecord.coinRecordNo}</td>
                        <td class="account">${jydpUserCoinOutRecord.userAccount}</td>
                        <td class="coin">${jydpUserCoinOutRecord.currencyName}</td>
                        <td class="amount"><fmt:formatNumber type="number" value="${jydpUserCoinOutRecord.currencyNumber}" groupingUsed="FALSE" maxFractionDigits="2"/></td>
                        <td class="account">${jydpUserCoinOutRecord.walletAccount}</td>
                        <td class="time"><fmt:formatDate type="time" value="${jydpUserCoinOutRecord.finishTime}" pattern="yyyy-MM-dd HH:mm:ss"></fmt:formatDate></td>
                        <c:if test="${jydpUserCoinOutRecord.handleStatus == '1'}">
                            <td class="state">待审核</td>
                        </c:if>
                        <c:if test="${jydpUserCoinOutRecord.handleStatus == '2'}">
                            <td class="state">审核通过</td>
                        </c:if>
                        <c:if test="${jydpUserCoinOutRecord.handleStatus == '3'}">
                            <td class="state">审核拒绝</td>
                        </c:if>
                        <c:if test="${jydpUserCoinOutRecord.handleStatus == '4'}">
                            <td class="state">已撤回</td>
                        </c:if>
                        <td class="mark">${jydpUserCoinOutRecord.remark}</td>
                        <td class="check">
                            <c:if test="${jydpUserCoinOutRecord.handleStatus == '1'}">
                                <label class="choose_two"><input type="checkbox" class="checkbox" name="selected" value="${jydpUserCoinOutRecord.coinRecordNo}"/></label>
                            </c:if>
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
        <div class="pass_pop">
            <p class="popTitle">审核通过</p>
            <p class="popInput">
                <label class="popName" style="line-height: 20px">备注</label>
                <textarea class="txt" placeholder="回复内容，非必填，最多100个字符" id="passRemark" name="passRemark" maxlength="100"></textarea>
            </p>

            <div class="buttons">
                <input type="hidden" name="passCoinRecordNoList" id="passCoinRecordNoList"/>
                <input type="text" value="取&nbsp;消" class="cancel" onfocus="this.blur()" />
                <input type="text" value="确&nbsp;定" class="yes" onfocus="this.blur()" onclick="auditPassed()"/>
            </div>
        </div>

        <div class="refuse_pop">
            <p class="popTitle">审核拒绝</p>
            <p class="popInput">
                <label class="popName" style="line-height: 20px">备注</label>
                <textarea class="txt" placeholder="回复内容，非必填，最多100个字符" id="refuseRemark" name="refuseRemark" maxlength="100"></textarea>
            </p>

            <div class="buttons">
                <input type="hidden" name="refuseCoinRecordNoList" id="refuseCoinRecordNoList"/>
                <input type="text" value="取&nbsp;消" class="cancel" onfocus="this.blur()" />
                <input type="text" value="确&nbsp;定" class="yes" onfocus="this.blur()" onclick="auditRefuse()"/>
            </div>
        </div>
    </div>
</div>


<script type="text/javascript" src="<%=path %>/resources/js/laydate.js"></script>
<script type="text/javascript" src="http://libs.baidu.com/jquery/2.1.4/jquery.min.js"></script>
<script type="text/javascript" src="<%=path %>/resources/js/loadPageBack.js"></script>
<script type="text/javascript" src="<%=path %>/resources/js/simpleTips.js"></script>

<script type="text/javascript">

    window.onload = function() {
        var code = '${code}';
        var message = '${message}';
        $("#currencyId").val('${currencyId}');
        $("#handleStatus").val('${handleStatus}');
        if (code != 1 && message != "") {
            openTips(message);
            return false;
        }
    }
    //查询
    function queryForm() {
        $("#queryPageNumber").val("0");
        $("#queryForm").submit();
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
        $(".pass").click(function(){
            /*$(".mask").fadeIn();
            $(".pass_pop").fadeIn();
            popObj = ".pass_pop"*/
        });
        $(".refuse").click(function(){
            /*$(".mask").fadeIn();
            $(".refuse_pop").fadeIn();
            popObj = ".refuse_pop"*/
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

    function selectAll(){
        var checklist = document.getElementsByName ("selected");
        if(document.getElementById("controlAll").checked)
        {
            for(var i=0;i<checklist.length;i++)
            {
                checklist[i].checked = 1;
            }
        }else{
            for(var j=0;j<checklist.length;j++)
            {
                checklist[j].checked = 0;
            }
        }
    }

    function selectMoreThrough(){
        var coinRecordNo = [];
        $('body').find(':checkbox').each(function(){
            if ($(this).is(":checked")) {
                var checkVal = $(this).val();
                coinRecordNo.push(checkVal);

            } });

        if(coinRecordNo.length < 1 || coinRecordNo == "" || coinRecordNo == null){
            openTips("请选择审核的记录！");
            return;
        }

        $("#passCoinRecordNoList").val(coinRecordNo);

        $(".mask").fadeIn();
        $(".pass_pop").fadeIn();
        popObj = ".pass_pop";

    }

    var auditPassedBoo = false;
    function auditPassed(){
        var coinRecordNo = $("#passCoinRecordNoList").val();
        var passRemark = $("#passRemark").val();

        if(auditPassedBoo){
            openTips("正在审核，请稍后！");
            return;
        }else{
            auditPassedBoo = true;
        }

        $.ajax({
            url: '<%=path %>' + "/backerWeb/backerUserCoinOut/auditPassed.htm",
            data: {
                coinRecordNo : coinRecordNo,
                passRemark : passRemark
            },//参数
            dataType: "json",
            type: 'POST',
            async: true, //默认异步调用 (false：同步)
            success: function (resultData) {
                var code = resultData.code;
                var message = resultData.message;
                if (code != 1 && message != "") {
                    auditPassedBoo = false;
                    openTips(message);
                    setTimeout("queryForm()", 1000);
                    return;
                }

                $("#queryForm").submit();
            },
            error: function () {
                auditPassedBoo = false;
                openTips("数据加载出错，请稍候重试");
            }
        });
    }


    function selectMoreReject(){
        var coinRecordNo = [];
        $('body').find(':checkbox').each(function(){
            if ($(this).is(":checked")) {
                var checkVal = $(this).val();
                coinRecordNo.push(checkVal);
            } });

        if(coinRecordNo.length < 1 || coinRecordNo == "" || coinRecordNo == null){
            openTips("请选择审核的记录！");
            return;
        }

        $("#refuseCoinRecordNoList").val(coinRecordNo);

        $(".mask").fadeIn();
        $(".refuse_pop").fadeIn();
        popObj = ".refuse_pop";
    }

    var auditRefuseBoo = false;
    function auditRefuse(){

        var coinRecordNo = $("#refuseCoinRecordNoList").val();
        var refuseRemark = $("#refuseRemark").val();

        if(auditRefuseBoo){
            openTips("正在审核，请稍后！");
            return;
        }else{
            auditRefuseBoo = true;
        }

        $.ajax({
            url: '<%=path %>' + "/backerWeb/backerUserCoinOut/auditRefuse.htm",
            data: {
                coinRecordNo : coinRecordNo,
                refuseRemark : refuseRemark
            },//参数
            dataType: "json",
            type: 'POST',
            async: true, //默认异步调用 (false：同步)
            success: function (resultData) {
                var code = resultData.code;
                var message = resultData.message;
                if (code != 1 && message != "") {
                    auditRefuseBoo = false;
                    openTips(message);
                    setTimeout("queryForm()", 1000);
                    return;
                }

                $("#queryForm").submit();
            },
            error: function () {
                auditRefuseBoo = false;
                openTips("数据加载出错，请稍候重试");
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
</script>

</body>
</html>
