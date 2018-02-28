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
    <link rel="stylesheet" type="text/css" href="<%=path %>/resources/js/need/laydate.css" />
    <link rel="stylesheet" type="text/css" href="<%=path %>/resources/js/skins/danlan/laydate.css" />

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
            <div class="topOperate">
                <c:if test="${backer_rolePower['103002'] == 103002}">
                    <p class="add addTrade">新增交易</p>
                </c:if>
                <c:if test="${backer_rolePower['103003'] == 103003}">
                    <p class="add" onclick="dowland()">下载交易模板</p>
                </c:if>
                <c:if test="${backer_rolePower['103004'] == 103004}">
                    <p class="add">导入交易数据</p>
                </c:if>
                <input type="file" class="file" id="file" name="file" onchange="addMore()"/>
            </div>

            <form id="queryForm" action="<%=path %>/backerWeb/transactionMakeOrder/show.htm" method="post">
                <div class="askArea">
                    <p class="condition">
                        生成时间：
                        从&nbsp;<input placeholder="请选择起始时间" class="startTime" id="startOrder" name="startAddTime"
                                      value="${startAddTime}"
                                      onClick="laydate({istime: true,format:'YYYY-MM-DD hh:mm:ss'})" />
                        到&nbsp;<input placeholder="请选择结束时间" class="endTime" id="endOrder" name="endAddTime"
                                      value="${endAddTime}"
                                      onClick="laydate({istime: true,format: 'YYYY-MM-DD hh:mm:ss'})" />
                    </p>
                    <p class="condition">币种：
                        <select class="askSelect" id="currencyId" name="currencyName">
                            <option value="">全部</option>
                            <c:forEach items="${transactionCurrencyList}" var="item">
                                <option value="${item.currencyName}">${item.currencyName}</option>
                            </c:forEach>
                        </select>
                    </p>
                    <p class="condition">类型：
                        <select class="askSelect" id="paymentType" name="paymentType">
                            <option value="0">全部</option>
                            <option value="1">买入</option>
                            <option value="2">卖出</option>
                        </select>
                    </p>
                    <p class="condition">执行状态：
                        <select class="askSelect" id="executeStatus" name="executeStatus">
                            <option value="0">全部</option>
                            <option value="1">待执行</option>
                            <option value="2">执行中</option>
                            <option value="3">执行完成</option>
                            <option value="4">执行失败</option>
                            <option value="5">已撤回</option>
                        </select>
                    </p>
                    <p class="condition">管理员账号：<input type="text" class="askInput" id="backAccount" name="backAccount" maxlength="16" onkeyup="value=value.replace(/[^a-zA-Z0-9]/g,'')" value="${backAccount}"/></p>
                    <p class="condition">
                        执行时间：
                        从&nbsp;<input placeholder="请选择起始时间" class="startTime" id="start"  name="startExecuteTime"
                                      value="${startExecuteTime}" onfocus="this.blur()"
                                      onClick="laydate({istime: true,format:'YYYY-MM-DD hh:mm:ss'})" />
                        到&nbsp;<input placeholder="请选择结束时间" class="endTime" id="end" name="endExecuteTime"
                                      value="${endExecuteTime}" onfocus="this.blur()"
                                      onClick="laydate({istime: true,format: 'YYYY-MM-DD hh:mm:ss'})" />
                    </p>

                    <input type="hidden" id="queryPageNumber" name="pageNumber" value="${pageNumber}">
                    <input type="text" value="查&nbsp;询" class="ask" onfocus="this.blur()" onclick="queryForm()"/>
                </div>
            </form>
        </div>

        <div class="batchOperate">
            <c:if test="${backer_rolePower['103005'] == 103005}">
                <input type="text" class="implement" value="立即执行" onfocus="this.blur()" onclick="goExcMore()"/>
            </c:if>
            <c:if test="${backer_rolePower['103006'] == 103006}">
                <input type="text" class="recall" value="撤回" onfocus="this.blur()" onclick="goCalMore()"/>
            </c:if>
        </div>

        <div class="bottom">
            <table class="table" cellpadding="0" cellspacing="0">
                <tr class="tableTitle">
                    <td class="time">生成时间</td>
                    <td class="coin">币种</td>
                    <td class="type">类型</td>
                    <td class="amount">成交数量</td>
                    <td class="amount">成交单价</td>
                    <td class="amount">成交总价</td>
                    <td class="time">执行时间</td>
                    <td class="state">执行状态</td>
                    <td class="account">操作管理员账号</td>
                    <td class="ip">操作IP</td>
                    <td class="operate">操作</td>
                    <td class="check">
                        <label class="controlAll">
                            <input type="checkbox" id="controlAll" name="controlAll" onclick="selectAll()"/>
                            <span>全选</span>
                        </label>
                    </td>
                </tr>
                <c:forEach items="${transactionMakeOrderList}" var="item">
                    <tr class="tableInfo">
                        <td class="time"><fmt:formatDate type="time" value="${item.addTime}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
                        <td class="coin">${item.currencyName}</td>
                        <c:if test="${item.paymentType == 1}">
                            <td class="type">买入</td>
                        </c:if>
                        <c:if test="${item.paymentType == 2}">
                            <td class="type">卖出</td>
                        </c:if>
                        <td class="amount"><fmt:formatNumber type="number" value="${item.currencyNumber}" maxFractionDigits="6"/></td>
                        <td class="amount">$<fmt:formatNumber type="number" value="${item.transactionPrice}" maxFractionDigits="6"/></td>
                        <td class="amount">$<fmt:formatNumber type="number" value="${item.currencyTotalPrice}" maxFractionDigits="6"/></td>
                        <td class="time"><fmt:formatDate type="time" value="${item.executeTime}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
                        <c:if test="${item.executeStatus == 1}">
                            <td class="state">待执行</td>
                        </c:if>
                        <c:if test="${item.executeStatus == 2}">
                            <td class="state">执行中</td>
                        </c:if>
                        <c:if test="${item.executeStatus == 3}">
                            <td class="state">执行完成</td>
                        </c:if>
                        <c:if test="${item.executeStatus == 4}">
                            <td class="state">执行失败</td>
                        </c:if>
                        <c:if test="${item.executeStatus == 5}">
                            <td class="state">已撤回</td>
                        </c:if>
                        <td class="account">${item.backerAccount}</td>
                        <td class="ip">${item.ipAddress}</td>
                        <td class="operate">
                            <c:if test="${item.executeStatus == 1}">
                                <c:if test="${backer_rolePower['103005'] == 103005}">
                                    <input type="text" value="立即执行" class="tImplement" onfocus="this.blur()" onclick="goExc('${item.orderNo}')"/>
                                </c:if>
                                <c:if test="${backer_rolePower['103006'] == 103006}">
                                    <input type="text" value="撤&nbsp;回" class="tRecall" onfocus="this.blur()" onclick="goCal('${item.orderNo}')"/>
                                </c:if>
                            </c:if>
                        </td>
                        <td class="check">
                            <label class="choose_two"><input type="checkbox" class="checkbox"  name="selected" value="${item.orderNo}"/></label>
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
        <div class="addTrade_pop">
            <p class="popTitle">新增交易记录</p>
            <p class="popInput">
                <label class="popName">执行时间<span class="star">*</span></label>
                <input placeholder="请选择执行时间" class="entry" id="addExecuteTime" name="addExecuteTime"
                       onfocus="this.blur()"
                       onClick="laydate({istime: true,format:'YYYY-MM-DD hh:mm:ss'})" />
            </p>
            <p class="popInput">
                <label class="popName">币种<span class="star">*</span></label>
                <select class="popSelected" id="addCurrencyName" name="addCurrencyName">
                    <option disabled selected value="">请选择币种</option>
                    <c:forEach items="${transactionCurrencyList}" var="item">
                        <option value="${item.currencyName}">${item.currencyName}</option>
                    </c:forEach>
                </select>
            </p>
            <p class="popInput">
                <label class="popName">类型<span class="star">*</span></label>
                <select class="popSelected" id="addPaymentType" name="addPaymentType">
                    <option disabled selected value="">请选择类型</option>
                    <option value="1">买入</option>
                    <option value="2">卖出</option>
                </select>
            </p>
            <p class="popInput">
                <label class="popName">成交数量<span class="star">*</span></label>
                <input type="text" class="entry" placeholder="成交币种的数量"
                       onkeyup="matchUtil(this, 'double')" onblur="matchUtil(this, 'double')"
                       id="addCurrencyNumber" name="addCurrencyNumber"/>
            </p>
            <p class="popInput">
                <label class="popName">成交单价<span class="star">*</span></label>
                <input type="text" class="entry" placeholder="成交币种的单价，单位：美元"
                       onkeyup="matchUtil(this, 'double')" onblur="matchUtil(this, 'double')"
                       id="addCurrencyPrice" name="addCurrencyPrice"/>
            </p>
            <p class="popInput">
                <label class="popName">成交总价</label>
                <span class="popAccount" id="addTotalPrice"></span>
            </p>

            <div class="buttons">
                <input type="text" value="取&nbsp;消" class="cancel" onfocus="this.blur()" />
                <input type="text" value="确&nbsp;定" class="yes" onfocus="this.blur()" onclick="addSubmit()" />
            </div>
        </div>

        <div class="implement_pop">
            <p class="popTitle">立即执行</p>
            <p class="popTips"><img src="<%=path %>/resources/image/back/tips.png" class="tipsImg" />确定立即执行多笔成交？</p>

            <div class="buttons">
                <input type="text" value="取&nbsp;消" class="cancel" onfocus="this.blur()" />
                <input type="text" value="确&nbsp;定" class="yes" onfocus="this.blur()" onclick="execute()" />
            </div>
        </div>

        <div class="recall_pop">
            <p class="popTitle">撤回操作</p>
            <p class="popTips"><img src="<%=path %>/resources/image/back/tips.png" class="tipsImg" />确定撤回多笔成交？</p>

            <div class="buttons">
                <input type="text" value="取&nbsp;消" class="cancel" onfocus="this.blur()" />
                <input type="text" value="确&nbsp;定" class="yes" onfocus="this.blur()" onclick="cancle()" />
            </div>
        </div>

        <div class="tImplement_pop">
            <p class="popTitle">立即执行</p>
            <p class="popTips"><img src="<%=path %>/resources/image/back/tips.png" class="tipsImg" />确定立即执行该笔成交？</p>
            
            <input type="hidden" id="excOrderNo" name="excOrderNo">
            <div class="buttons">
                <input type="text" value="取&nbsp;消" class="cancel" onfocus="this.blur()" />
                <input type="text" value="确&nbsp;定" class="yes" onfocus="this.blur()" onclick="execute()" />
            </div>
        </div>

        <div class="tRecall_pop">
            <p class="popTitle">撤回操作</p>
            <p class="popTips"><img src="<%=path %>/resources/image/back/tips.png" class="tipsImg" />确定撤回该笔成交？</p>

            <input type="hidden" id="calOrderNo" name="calOrderNo">
            <div class="buttons">
                <input type="text" value="取&nbsp;消" class="cancel" onfocus="this.blur()" />
                <input type="text" value="确&nbsp;定" class="yes" onfocus="this.blur()" onclick="cancle()" />
            </div>
        </div>
    </div>
</div>

<script type="text/javascript" src="http://libs.baidu.com/jquery/2.1.4/jquery.min.js"></script>
<script type="text/javascript" src="<%=path %>/resources/js/loadPageBack.js"></script>
<script type="text/javascript" src="<%=path %>/resources/js/simpleTips.js"></script>
<script type="text/javascript" src="<%=path %>/resources/js/laydate.js"></script>

<script type="text/javascript">
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
    var startOrder = {
        elem: '#startOrder',
        format: 'YYYY-MM-DD hh:mm:ss',
        istime: true,
        istoday: false
    };

    var endOrder = {
        elem: '#endOrder',
        format: 'YYYY-MM-DD hh:mm:ss',
        istime: true,
        istoday: false
    };
    laydate(start);
    laydate(end);
    laydate(startOrder);
    laydate(endOrder);//日期控件

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
        $(".addTrade").click(function(){
            $(".mask").fadeIn();
            $(".addTrade_pop").fadeIn();
            popObj = ".addTrade_pop"
        });
        $(".implement").click(function(){

        });
        $(".recall").click(function(){

        });
        $(".tImplement").click(function(){
            
        });
        $(".tRecall").click(function(){
            
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
        $("#paymentType option").each(function(){
            if($(this).val()=='${paymentType}'){
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

    //动态计算总价
    function mul() {
        var number = parseFloat($("#addCurrencyNumber").val());
        var price = parseFloat($("#addCurrencyPrice").val());
        if (number > 0) {
            if (price > 0) {
                var total = Math.floor(number * price * 1000000) / 1000000;
                $("#addTotalPrice").html(total);
                return;
            }
        }
        $("#addTotalPrice").html("");
    }

    //添加记录
    var addBoo = false;
    function addSubmit() {
        if (addBoo) {
            return;
        } else {
            addBoo = true;
        }
        var number = parseFloat($("#addCurrencyNumber").val());
        var price = parseFloat($("#addCurrencyPrice").val());
        var curName = $("#addCurrencyName").val();
        var payType = $("#addPaymentType").val();
        var excTime = $("#addExecuteTime").val();

        if (!number > 0 || !price > 0) {
            addBoo = false;
            openTips("请验证数量和单价是否合理");
            return;
        }

        if (curName == "") {
            addBoo = false;
            openTips("请选择一个币种");
            return;
        }
        if (payType == "") {
            addBoo = false;
            openTips("请选择一个收支类型");
            return;
        }

        $.ajax({
            url: '<%=path %>' + "/backerWeb/transactionMakeOrder/addOrder.htm",
            data: {
                addCurrencyName : curName,
                addPaymentType : payType,
                addCurrencyNumber : number,
                addCurrencyPrice : price,
                addExecuteTime : excTime
            },//参数
            dataType: "json",
            type: 'POST',
            async: true, //默认异步调用 (false：同步)
            success: function (resultData) {
                var code = resultData.code;
                var message = resultData.message;
                if (code != 1 && message != "") {
                    addBoo = false;
                    openTips(message);
                    return;
                }

                $("#queryForm").submit();
            },

            error: function () {
                openTips("数据加载出错，请稍候重试");
            }
        });
    }

    //下载模板
    var dowBoo = false;
    function dowland() {
        if (dowBoo) {
            return;
        } else {
            dowBoo = true;
        }

        $.ajax({
            url: '<%=path %>' + "/backerWeb/transactionMakeOrder/dowland.htm",
            data: {
            },//参数
            dataType: "json",
            type: 'POST',
            async: true, //默认异步调用 (false：同步)
            success: function (resultData) {
                var code = resultData.code;
                var message = resultData.message;
                var data = resultData.data;
                if (code != 1 && message != "") {
                    dowBoo = false;
                    openTips(message);
                    return;
                }
                window.location.href = '<%=path%>' + data.downLoadUrl;
            },

            error: function () {
                openTips("数据加载出错，请稍候重试");
            }
        });
    }

    //打开立即执行页面
    function goExc(orderNo){
        
        document.getElementById("excOrderNo").value = orderNo;

        $(".mask").fadeIn();
        $(".tImplement_pop").fadeIn();
        popObj = ".tImplement_pop"
    }

    //打开撤回页面
    function goCal(orderNo) {

        document.getElementById("calOrderNo").value = orderNo;

        $(".mask").fadeIn();
        $(".tRecall_pop").fadeIn();
        popObj = ".tRecall_pop"
    }

    //打开多个执行页面
    function goExcMore(){
        document.getElementById("excOrderNo").value = selectMore();

        $(".mask").fadeIn();
        $(".implement_pop").fadeIn();
        popObj = ".implement_pop"
    }

    //打开多个撤回页面
    function goCalMore() {
        document.getElementById("calOrderNo").value = selectMore();

        $(".mask").fadeIn();
        $(".recall_pop").fadeIn();
        popObj = ".recall_pop"
    }

    //立即执行
    var excBoo = false;
    function execute() {
        if (excBoo) {
            return;
        } else {
            excBoo = true;
        }

        var orderNo = $("#excOrderNo").val();
        if (orderNo == "" || orderNo == null) {
            excBoo =false;
            openTips("订单号不能为空");
            return;
        }

        $.ajax({
            url: '<%=path %>' + "/backerWeb/transactionMakeOrder/execute.htm",
            data: {
                orderNo : orderNo
            },//参数
            dataType: "json",
            type: 'POST',
            async: true, //默认异步调用 (false：同步)
            success: function (resultData) {
                var code = resultData.code;
                var message = resultData.message;
                if (code != 1 && message != "") {
                    excBoo = false;
                    openTips(message);
                    return;
                }

                $("#queryForm").submit();
            },

            error: function () {
                openTips("数据加载出错，请稍候重试");
            }
        });
    }

    //撤回
    var calBoo = false;
    function cancle() {
        if (calBoo) {
            return;
        } else {
            calBoo = true;
        }

        var orderNo = $("#calOrderNo").val();
        if (orderNo == "" || orderNo == null) {
            calBoo =false;
            openTips("订单号不能为空");
            return;
        }

        $.ajax({
            url: '<%=path %>' + "/backerWeb/transactionMakeOrder/cancle.htm",
            data: {
                orderNo : orderNo
            },//参数
            dataType: "json",
            type: 'POST',
            async: true, //默认异步调用 (false：同步)
            success: function (resultData) {
                var code = resultData.code;
                var message = resultData.message;
                if (code != 1 && message != "") {
                    calBoo = false;
                    openTips(message);
                    return;
                }

                $("#queryForm").submit();
            },

            error: function () {
                openTips("数据加载出错，请稍候重试");
            }
        });
    }

    //所有选中
    function selectMore() {
        var checklist = document.getElementsByName("selected");
        var takeNoListStr = "";
        var sizeLength = checklist.length;
        for(var i = 0; i < sizeLength; i++) {
            if (checklist[i].checked) {
                takeNoListStr += "," + checklist[i].value;
            }
        }
        return takeNoListStr;
    }

    //导入
    var addMoreBoo = false;
    function addMore() {
        if (addMoreBoo) {
            return;
        } else {
            addMoreBoo = true;
        }

        var formData = new FormData();
        var date = $("#file")[0].files[0];
        formData.append("myfile", date);

        $.ajax({
            url: '<%=path %>' + "/backerWeb/transactionMakeOrder/importOrder.htm",
            data: formData,//参数
            type: 'POST',
            contentType: false,
            processData: false,
            async: true, //默认异步调用 (false：同步)
            success: function (resultData) {
                var code = resultData.code;
                var message = resultData.message;
                if (code != 1 && message != "") {
                    addMoreBoo = false;
                    openTips(message);
                    return;
                }

                $("#queryForm").submit();
            },

            error: function () {
                openTips("数据加载出错，请稍候重试");
            }
        });

    }

    var mapMatch = {};
    mapMatch['number'] = /[^\d]/g;
    mapMatch['ENumber'] = /[^\a-\z\A-\Z\d]/g;
    mapMatch['double'] = true;
    mapMatch['phone'] = /[^\d]/g;
    mapMatch['email'] = /([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\.[a-zA-Z0-9_-]{2,3}){1,2})$/;
    function matchUtil(o, str) {
        mapMatch[str] === true ? matchDouble(o, 6) : o.value = o.value.replace(mapMatch[str], '');
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
        mul();
    }

</script>
</body>
</html>