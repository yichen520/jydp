<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/resources/page/common/path.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <link rel="icon" href="<%=path %>/resources/image/back/icon.ico" type="image/x-ico" />

    <link rel="stylesheet" type="text/css" href="<%=path %>/resources/css/back/backForm.css" />
    <link rel="stylesheet" type="text/css" href="<%=path %>/resources/css/back/public.css" />
    <link rel="stylesheet" type="text/css" href="<%=path %>/resources/css/back/simpleTips.css" />

    <title>后台做单</title>
</head>
<body>
<div id="header"></div>


<div class="content">
    <div id="menu"></div>

    <div class="contentRight">
        <div class="caption">
            <span class="title">后台做单</span>
        </div>

        <div class="top">
            <c:if test="${backer_rolePower['151002'] == 151002}">
                <a href="<%=path %>/backerWeb/transactionMakeOrder/goAdd.htm" class="add">新增交易</a>
            </c:if>

            <form id="queryForm" action="<%=path %>/backerWeb/transactionMakeOrder/show.htm" method="post">
                <div class="askArea">
                    <p class="condition">
                        批次号：<input type="text" class="askInput" id="orderNo" name="orderNo" value="${orderNo}"
                                   maxlength="18" onkeyup="value=value.replace(/[^0-9]/g,'')"/>
                    </p>
                    <p class="condition">币种：
                        <select class="askSelect" id="currencyId" name="currencyId">
                            <option value="">全部</option>
                            <c:forEach items="${transactionCurrencyList}" var="item">
                                <option value="${item.currencyId}">${item.currencyName}</option>
                            </c:forEach>
                        </select>
                    </p>
                    <p class="condition">执行状态：
                        <select class="askSelect" id="executeStatus" name="executeStatus">
                            <option value="0">全部</option>
                            <option value="1">待执行</option>
                            <option value="2">执行中</option>
                            <option value="3">执行成功</option>
                            <option value="4">执行失败</option>
                        </select>
                    </p>
                    <p class="condition">
                        执行时间：
                        从&nbsp;<input placeholder="请选择起始时间" class="askTime" id="start"  name="startExecuteTime"
                                      value="${startExecuteTime}" onfocus="this.blur()"/>
                        到&nbsp;<input placeholder="请选择结束时间" class="askTime" id="end" name="endExecuteTime"
                                      value="${endExecuteTime}" onfocus="this.blur()"/>
                    </p>

                    <input type="hidden" id="queryPageNumber" name="pageNumber" value="${pageNumber}">
                    <input type="text" value="查&nbsp;询" class="ask" onfocus="this.blur()" onclick="queryForm()"/>
                </div>
            </form>
        </div>

        <div class="bottom">
            <table class="table" cellpadding="0" cellspacing="0">
                <tr class="tableTitle">
                    <td class="number">批次号</td>
                    <td class="coin">币种</td>
                    <td class="amount">成交单价</td>
                    <td class="amount">成交数量</td>
                    <td class="time">执行时间</td>
                    <td class="state">执行状态</td>
                    <td class="account">操作管理员账号</td>
                    <td class="ip">操作IP</td>
                    <td class="time">生成时间</td>
                    <td class="operate">操作</td>
                </tr>
                <c:forEach items="${transactionMakeOrderList}" var="item">
                    <tr class="tableInfo">
                        <td class="number">${item.orderNo}</td>
                        <td class="coin">${item.currencyName}</td>
                        <td class="amount"><fmt:formatNumber type="number" value="${item.currencyPrice}" maxFractionDigits="6"/> XT</td>
                        <td class="amount"><fmt:formatNumber type="number" value="${item.currencyNumber}" maxFractionDigits="6"/></td>
                        <td class="time"><fmt:formatDate type="time" value="${item.executeTime}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
                        <c:if test="${item.executeStatus == 1}">
                            <td class="state">待执行</td>
                        </c:if>
                        <c:if test="${item.executeStatus == 2}">
                            <td class="state">执行中</td>
                        </c:if>
                        <c:if test="${item.executeStatus == 3}">
                            <td class="state">执行成功</td>
                        </c:if>
                        <c:if test="${item.executeStatus == 4}">
                            <td class="state">执行失败</td>
                        </c:if>
                        <td class="account">${item.backerAccount}</td>
                        <td class="ip">${item.ipAddress}</td>
                        <td class="time"><fmt:formatDate type="time" value="${item.addTime}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
                        <td class="operate">
                            <c:if test="${backer_rolePower['151004'] == 151004}">
                                <a href="<%=path %>/backerWeb/transactionMakeOrder/showDetail/${item.orderNo}"  class="details" target="_blank">查看详情</a>
                            </c:if>
                            <c:if test="${item.executeStatus != 2}">
                                <c:if test="${backer_rolePower['151003'] == 151003}">
                                    <input type="text" value="删&nbsp;除" class="delete" onfocus="this.blur()" onclick="goDel('${item.orderNo}')"/>
                                </c:if>
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
        <div class="delete_pop">
            <p class="popTitle">删除操作</p>
            <p class="popTips"><img src="<%=path %>/resources/image/back/tips.png" class="tipsImg" />确定删除？</p>

            <input type="hidden" id="deOrderNo" name="deOrderNo">

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
    });
    //日期控件

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

    var popObj;
    $(function(){
        $(".delete").click(function(){

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

    function openTip()
    {
        openTips("阿萨德芳");
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
            if($(this).val()=='${currencyId}'){
                $(this).attr('selected',true);
            }
        });
        $("#executeStatus option").each(function(){
            if($(this).val()=='${executeStatus}'){
                $(this).attr('selected',true);
            }
        });
    }

    //查询
    function queryForm() {
        $("#queryPageNumber").val("0");
        $("#queryForm").submit();
    }

    //去删除页面
    function goDel(orderNo) {

        $("#deOrderNo").val(orderNo);

        $(".mask").fadeIn();
        $(".delete_pop").fadeIn();
        popObj = ".delete_pop"
    }

    //删除
    delBoo = false;
    function del() {
        if (delBoo) {
            return ;
        } else {
            delBoo = true;
        }

        var orderNo = $("#deOrderNo").val();
        if (orderNo == null || orderNo == ""){
            delBoo =false;
            openTips("订单号获取错误");
            return;
        }

        $.ajax({
            url: '<%=path %>' + "/backerWeb/transactionMakeOrder/del.htm",
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
</script>
</body>
</html>