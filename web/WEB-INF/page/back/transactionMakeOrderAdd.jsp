<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/resources/page/common/path.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <link rel="icon" href="<%=path %>/resources/image/back/icon.ico" type="image/x-ico" />

    <link rel="stylesheet" type="text/css" href="<%=path %>/resources/css/back/backForm_add.css" />
    <link rel="stylesheet" type="text/css" href="<%=path %>/resources/css/back/public.css" />
    <link rel="stylesheet" type="text/css" href="<%=path %>/resources/css/back/simpleTips.css" />

    <title>新增交易</title>
</head>
<body>
<div id="header"></div>


<div class="content">
    <div id="menu"></div>

    <div class="contentRight">
        <div class="caption">
            <span class="title">新增交易</span>
        </div>

        <div class="bottom">
            <div class="addInfo">
                <p class="popInput">
                    <span class="popName">执行日期<span class="star">*</span>：</span>
                    <input type="text" class="entry askTime" placeholder="选择执行日期"
                           id="addExecuteTime" name="addExecuteTime" onfocus="this.blur()"/>
                </p>
                <p class="popInput">
                    <span class="popName">币种<span class="star">*</span>：</span>
                    <select class="popSelected" id="addCurrencyName" name="addCurrencyName">
                        <option disabled selected value="">请选择币种</option>
                        <c:forEach items="${transactionCurrencyList}" var="item">
                            <option value="${item.currencyName}">${item.currencyName}</option>
                        </c:forEach>
                    </select>
                </p>
                <p class="popInput">
                    <span class="popName">执行指令<span class="star">*</span>：</span>
                    <textarea class="txt" placeholder="执行指令格式为：小时:分钟,单价,总量(逗号是英文模式下的逗号，每条指令一行,回车换行)" id="textAr" name="textAr"></textarea>
                </p>

                <div class="editor"></div>

                <div class="operate">
                    <a href="<%=path %>/backerWeb/transactionMakeOrder/show.htm" class="back">返&nbsp;回</a>
                    <input type="text" value="提&nbsp;交" class="submit" onfocus="this.blur()" onclick="add()"/>
                </div>
            </div>
        </div>
    </div>
</div>


<div id="footer"></div>


<script type="text/javascript" src="http://libs.baidu.com/jquery/2.1.4/jquery.min.js"></script>
<script type="text/javascript" src="<%=path %>/resources/js/loadPageBack.js"></script>
<script type="text/javascript" src="<%=path %>/resources/js/simpleTips.js"></script>
<script type="text/javascript" src="<%=path %>/resources/js/laydate.js"></script>

<script type="text/javascript">
    lay('.askTime').each(function(){
        laydate.render({
            elem: this,
            trigger: 'click',
            type:'date',
            theme: '#69c0ff'
        });
    });
    //日期控件

    //新增
    var addBoo = false;
    function add(){
        if (addBoo){
            return ;
        } else {
            addBoo = true;
        }

        var curName = $("#addCurrencyName").val();
        var excTime = $("#addExecuteTime").val();
        var textAr = $("#textAr").val();

        if (excTime == null || excTime == ""){
            addBoo = false;
            openTips("请选择日期");
            return;
        }
        if (curName == "" || curName == null) {
            addBoo = false;
            openTips("请选择一个币种");
            return;
        }
        if (textAr == "" || textAr == null) {
            addBoo = false;
            openTips("请输入正确格式的执行命令");
            return;
        }

        textAr = textAr.replace(/[\r\n]/g,"$$$");
        textAr = textAr.replace(/[ ]/g,"");


        $.ajax({
            url: '<%=path %>' + "/backerWeb/transactionMakeOrder/addOrder.htm",
            data: {
                addCurrencyName : curName,
                textAr : textAr,
                addExecuteTime : excTime
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
                    setTimeout(function (){window.location.href = '<%=path%>/backerWeb/transactionMakeOrder/show.htm'}, 1000);
                }

                addBoo = false;
            },

            error: function () {
                addBoo = false;
                openTips("数据加载出错，请稍候重试");
            }
        });
    }

</script>

</body>
</html>