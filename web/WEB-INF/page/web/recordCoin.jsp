<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@include file="/resources/page/common/path.jsp"%>
<script type="text/javascript" src="http://libs.baidu.com/jquery/2.1.4/jquery.min.js"></script>
<script type="text/javascript" src="<%=path %>/resources/js/loadPageWeb.js"></script>
<script type="text/javascript" src="<%=path %>/resources/js/simpleTips.js"></script>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <link rel="icon" href="<%=path %>/resources/image/web/icon.ico" type="image/x-ico" />
    <link rel="stylesheet" type="text/css" href="<%=path %>/resources/css/web/recordCoin.css" />
    <link rel="stylesheet" type="text/css" href="<%=path %>/resources/css/web/public.css" />
    <link rel="stylesheet" type="text/css" href="<%=path %>/resources/css/web/simpleTips.css" />
    <title>币种提出记录</title>
</head>
<body>
<header id="header"></header>
<div id="menu"></div>


<div class="content">
    <div id="personalMenu"></div>

    <div class="contentRight">
        <div class="title">币种提出记录</div>

        <div class="main">
            <table class="table" cellpadding="0" cellspacing="0">
                <tr class="tableTitle">
                    <td class="time">申请时间</td>
                    <td class="time">申请流水号</td>
                    <td class="coin">币种</td>
                    <td class="amount">数量</td>
                    <td class="state">状态</td>
                    <td class="time">完成时间</td>
                    <td class="mark">备注</td>
                    <td class="operate">操作</td>
                </tr>
             <c:forEach items="${coinOutRecordList}" var="coinOutRecord">
                <tr class="tableInfo">
                    <td class="time"><fmt:formatDate type="time" value="${coinOutRecord.addTime}" pattern="yyyy-MM-dd HH:mm:ss"></fmt:formatDate></td>
                    <td class="time">${coinOutRecord.coinRecordNo}</td>
                    <td class="coin">${coinOutRecord.currencyName}</td>
                    <td class="amount"><fmt:formatNumber type="number" value="${coinOutRecord.currencyNumber}" maxFractionDigits="2"></fmt:formatNumber></td>
                    <td class="state">
                        <c:if test="${coinOutRecord.handleStatus == 1}">
                            <p>审核状态：待审核</p>
                        </c:if>
                        <c:if test="${coinOutRecord.handleStatus == 2}">
                            <p>审核状态：审核通过</p>
                        </c:if>
                        <c:if test="${coinOutRecord.handleStatus == 3}">
                            <p>审核状态：审核拒绝</p>
                        </c:if>
                        <c:if test="${coinOutRecord.handleStatus == 4}">
                            <p>审核状态：已撤回</p>
                        </c:if>
                        <c:if test="${coinOutRecord.handleStatus == 2 and coinOutRecord.sendStatus == 1}">
                            <p>转出状态：待转出</p>
                        </c:if>
                        <c:if test="${coinOutRecord.handleStatus == 2 and coinOutRecord.sendStatus == 2}">
                            <p>转出状态：转出中</p>
                        </c:if>
                        <c:if test="${coinOutRecord.handleStatus == 2 and coinOutRecord.sendStatus == 3}">
                            <p>转出状态：转出成功</p>
                        </c:if>
                        <c:if test="${coinOutRecord.handleStatus == 2 and coinOutRecord.sendStatus == 4}">
                            <p>转出状态：转出失败</p>
                        </c:if>
                    </td>
                    <td class="time"><fmt:formatDate type="time" value="${coinOutRecord.finishTime}" pattern="yyyy-MM-dd HH:mm:ss"></fmt:formatDate></td>
                    <td class="mark">${coinOutRecord.remark}</td>
                    <td class="operate">
                        <c:if test="${coinOutRecord.handleStatus == 1}">
                            <input type="text" value="撤&nbsp;回" class="recall" onfocus="this.blur()" onclick="showDialog('${coinOutRecord.coinRecordNo}')"/>
                        </c:if>
                    </td>
                </tr>
             </c:forEach>
            </table>

            <jsp:include page="/resources/page/common/paging.jsp"></jsp:include>

            <form id="queryForm" action="<%=path %>/userWeb/jydpUserCoinOutRecord/show.htm" method="post">
                <input type="hidden" id="queryPageNumber" name="pageNumber" value="${pageNumber}">
            </form>
            <input type="hidden" id="recallRecordNo">
        </div>
    </div>
</div>


<div id="helpFooter"></div>
<div id="footer"></div>


<div class="mask">
    <div class="mask_content">
        <div class="recall_pop">
            <p class="popTitle">撤回操作</p>
            <p class="popTips"><img src="<%=path %>/resources/image/web/tips.png" class="tipsImg" />确定撤回该笔申请？</p>

            <div class="buttons">
                <input type="text" value="取&nbsp;消" class="cancel" onfocus="this.blur()" />
                <input type="text" value="确&nbsp;定" class="yes" onfocus="this.blur()" onclick="withdraw()" />
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
    var popObj;
    $(function(){
        $(".cancel").click(function(){
            $(".mask").fadeOut("fast");
            $(popObj).fadeOut("fast");
        });
        $(".yes").click(function(){
            $(".mask").fadeOut("fast");
            $(popObj).fadeOut("fast");
        });
    });

    function showDialog(coinRecordNo) {
        $(".mask").fadeIn();
        $(".recall_pop").fadeIn();
        popObj = ".recall_pop";
        $("#recallRecordNo").val(coinRecordNo);
    }

    var withdrawBoo = false;
    //撤销操作
    function withdraw() {

        if(withdrawBoo){
            openTips("正在撤销，请稍后！");
            return;
        }else{
            withdrawBoo = true;
        }

        var coinRecordNo = $("#recallRecordNo").val();
        $.ajax({
            url: '<%=path %>/userWeb/jydpUserCoinOutRecord/withdrawCoinOutRecord.htm',
            type:'post',
            data:{
                coinRecordNo:coinRecordNo
            },
            dataType:'json',
            success:function (result) {
                if (result.code == 1) {
                  $("#queryForm").submit();
                } else {
                    openTips(result.message);
                    setTimeout(function () {
                        $("#queryForm").submit();
                    }, 1000);
                }
                withdrawBoo = false;
            },
            error:function () {
                withdrawBoo = false;
                openTips("数据加载出错，请稍候重试");
            }
        });
    }

</script>
</body>
</html>