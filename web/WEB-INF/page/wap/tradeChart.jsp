<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/resources/page/common/path.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=0">
    <meta http-equiv="Cache-Control" content="no-cache" />
    
    <link rel="stylesheet" href="<%=path %>/resources/css/wap/common.css">
    <link rel="stylesheet" href="<%=path %>/resources/css/wap/simpleTips_wap.css">
    <link rel="stylesheet" href="<%=path %>/resources/css/wap/chart.css">

    <title>交易</title>  
</head>

<body>
<div id="header">
    <div>
        <input type="hidden" id="currencyIdStr" name="currencyIdStr" value="${currencyIdStr}"/>
    </div>
</div>
<script type="text/x-handlebars-template" id="headerTemplate">
<!-- 头部导航 -->
<header>
    <div>
        <input type="hidden" id="currencyId"  value="{{currencyId}}"/>
        <input type="hidden" id="userSession" name="userSession" value="{{userSession}}"/>
        <input type="hidden" id="currencyName"  value="{{transactionCurrency.currencyName}}"/>
    </div>
    <img src="{{webAppPath}}/resources/image/wap/header-open.png" class="open"/>
    <p>{{transactionCurrency.currencyName}}({{transactionCurrency.currencyShortName}}/USD)</p>
    {{{isLogin userSession webAppPath}}}
</header>
<div class="nav">
    
    <div class="nav-content">
        <div class="topLeft">{{formatNumber standardParameter.nowPrice 8}}</div>
            <div class="nav-content-list">
                <p class="list-desc">最高 <span class="list-num" id="todayMaxSpan">{{formatNumber standardParameter.todayMax 6}}</span></p>
                <p class="list-desc">最低 <span class="list-num" id="todayMinSpan">{{formatNumber standardParameter.todayMin 6}}</span></p>
            </div>
            <div class="nav-content-list">
                <p class="list-desc right">买一价 <span class="list-num" id="buyOneSpan">{{formatNumber standardParameter.buyOne 6}}</span></p>
                <p class="list-desc right">卖一价 <span class="list-num"  id="sellOneOne">{{formatNumber standardParameter.sellOne 6}}</span></p>
            </div>
            <div class="nav-content-list">
                <p class="list-desc">日成交额</p>
                <p class="list-num" id="dayTurnoveOne">{{formatNumber standardParameter.dayTurnove 4}}万</p>
            </div>
    </div>
</div>
<!-- 内容 -->
<div id="wrapper">
    <div class="wrapper" style="height:5rem;width:100%">
        <ul class="wrapper-tab">
            <li class="choose">5分钟</li>
            <li>15分钟</li>
            <li>30分钟</li>
            <li>1小时</li>
            <li>4小时</li>
            <li>1天</li>
            <li>1周</li>
            <li class='goto-trade'>
                <a href='{{webAppPath}}/userWap/tradeCenter/show?currencyIdStr={{currencyId}}'>
                    <img src="{{webAppPath}}/resources/image/wap/trade.png">
                    <span>交易</span>
                </a>
            </li>
        </ul>
        <div id="chart" class="chart" style='height: 4.8rem'></div>
    </div>
    <div class="main">
        <ul class="mainTitle">
            <li>
                <p>最近成交记录</p>
            </li>
            <div class="clear"></div>
        </ul>
    </div>
    <div class="tab">
        <div class="entrust" style="display:block">
            <ul class="entrust-title">
                <li>类型</li>
                <li>单价($)</li>
                <li>数量</li>
                <li>成交时间</li>
                <li class="clear"></li>
            </ul>
            <div id="dealDiv">
                {{#each dealList}}
                    <ul class="entrust-content">
                        <li>{{{paymentTypeFormat paymentType}}}</li>
                        <li>{{formatNumber transactionPrice 2}}</li>
                        <li>{{formatNumber currencyNumber 4}}</li>
                        <li>{{timeFormat addTime}}</li>
                        <li class="clear"></li>
                    </ul>
                {{/each}}
            </div>
        </div>
        </div>
</div>
<!-- 底部tabBar -->
<footer>
    <a href="{{webAppPath}}/userWap/homePage/show" class="home">
            <img src="{{webAppPath}}/resources/image/wap/home-nochose.png"  class="home-icon"/>
            <p>首页</p>
    </a>
    <a class="deal open">
            <img src="{{webAppPath}}/resources/image/wap/deal-chose.png"  class="deal-icon"/>
            <p class="chose">交易</p>
    </a>
    <a href="{{webAppPath}}/userWap/userInfo/show.htm" class="mine">
            <img src="{{webAppPath}}/resources/image/wap/mine-nochose.png"  class="mine-icon"/>
            <p>我的</p>
    </a>
</footer>
<!-- 选择币种 -->
<div class="choseBz">
    <div class="choseBzBox">

        <div class="choseBzBox-title">
            <p>选择币种</p>
            <img src="{{webAppPath}}/resources/image/wap/header-close.png" class="closeBox"/>
            <div class="clear"></div>
        </div>
        <div id="currencyListUl">
         <ul  class="choseBzBox-content">
        </ul>
        </div>
    </div>
    <div id="closeAnthoer" class="closeAnthoer closeBox"></div>


</div>
<!-- loading图 -->
<div id="loading">
    <i></i>
</div>
</script>
<script type="text/javascript" src="<%=path %>/resources/js/wap/common.js"></script>
<script type="text/javascript" src="<%=path %>/resources/js/wap/jquery-2.1.4.min.js"></script>
<script src="<%=path %>/resources/js/wap/handlebars-v4.0.11.js"></script>
<script type="text/javascript" src="<%=path %>/resources/js/wap/chart.js"></script>
<script type="text/javascript" src="<%=path %>/resources/js/wap/simpleTips_wap.js"></script>
<script type="text/javascript" src="<%=path %>/resources/js/wap/highstock.js"></script>
<%--<script src="https://img.hcharts.cn/highcharts-plugins/highcharts-zh_CN.js"></script>--%>
<script src="https://img.hcharts.cn/highcharts/themes/dark-unica.js"></script>

</body>
</html>