<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
</head>

<body>
<div class="menu">
    <span class="logo">
        <a href="<%=path %>/userWeb/homePage/show" class="m_logo"><img src="<%=path %>/resources/image/web/trade_logo.png" /></a>交易大盘
    </span>

    <ul class="nav">
        <li class="navInfo"><a id="webHome" href="<%=path %>/userWeb/homePage/show">首页</a></li>
        <li class="navInfo"><a id="tradeCenter" href="javascript:void(0)" onclick="toTradeCenter(1)" class="tradeCenter">交易中心
                                <img src="<%=path %>/resources/image/web/tradeCoin.png" class="tradeArrow" />
                            </a></li>
        <li class="navInfo"><a href="#">我要充值</a></li>
        <li class="navInfo"><a id="message" href="<%=path %>/userWeb/userMessage/show.htm">个人中心</a></li>
    </ul>

    <ul class="coinLink">
    </ul>
    <form id="tradeCenterForm" action="<%=path %>/userWeb/tradeCenter/show" method="post">
        <input id="menuCurrencyId" name="currencyId" type="hidden"/>
    </form>
</div>
<script type="text/javascript">
    $(function(){
        $(".tradeCenter").mouseover(function(){
            $(".coinLink").fadeIn();
        });
        $(".coinLink").mouseleave(function(){
            $(".coinLink").fadeOut();
        });
        $(".link_trade").click(function () {
            $(".coinLink").fadeOut();
        });

        $.ajax({
            url: '<%=path %>/userWeb/homePage/getAllCurrency',
            type:'post',
            dataType:'json',
            async:true,
            success:function (result) {
                if (result.code == 1) {
                   var data =  result.data;
                   var transactionCurrencyList = data.transactionCurrencyList;
                   if (transactionCurrencyList != null) {
                       $(".coinLink").empty();
                       $.each(transactionCurrencyList,function (index,currency) {
                           $(".coinLink").append(
                                           '<li class="link_trade">'+
                                               '<a href="javascript:void(0)" onclick="toTradeCenter('+currency.currencyId+')">'+
                                               '<img src="'+currency.currencyImgUrl+'" class="tradeIcon" />'+
                                               '<span class="menu_coin">'+currency.currencyName+'('+currency.currencyShortName+')'+'</span>'+
                                               '</a>'+
                                           '</li>'
                           );
                       })
                   }
                }
            }
        });
    });

    function showPersonalMenu() {
        var menuObj = null;
        //获取当前url路径
        var curUrl = window.location.href;
        //首页
        if (curUrl.indexOf("homePage/show") > 0) {
            menuObj = $("#webHome");
        }
        //个人中心
        if (curUrl.indexOf("/userWeb/userMessage/show.htm") > 0) {
            menuObj = $("#message");
        }

        if (curUrl.indexOf("/userWeb/tradeCenter/show") > 0) {
            menuObj = $("#tradeCenter");
        }

        if(menuObj != null){
            menuObj.addClass("nav_pitch");
            clearInterval(showPersonalMenuId);
        }
    }
    var showPersonalMenuId = setInterval(showPersonalMenu, 20);

    //跳转至交易中心
    function toTradeCenter(currencyId) {
        $("#menuCurrencyId").val(currencyId);
        $("#tradeCenterForm").submit();
    }
</script>
</body>
</html>