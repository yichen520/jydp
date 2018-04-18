<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ page import="com.jydp.entity.DO.system.SystemNoticeDO" %>
<%@include file="/resources/page/common/path.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html  style="font-size: 50px">
<head>
    <meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <meta http-equiv="Cache-Control" content="no-cache"/>

    <link rel="stylesheet" href="<%=path %>/resources/css/wap/common.css">
    <link rel="stylesheet" href="<%=path %>/resources/css/wap/index.css">
    <link rel="stylesheet" href="<%=path %>/resources/css/wap/swiper-3.4.2.min.css">
    <title>首页</title>
</head>
<body>
<!-- 头部导航 -->
<header>
    <img src="<%=path %>/resources/image/wap/home-logo.png"/>
    <p>首页</p>
    <c:choose>
        <c:when test="${userSession.userAccount == null}">
            <a href="<%=path %>/userWap/userLogin/show">登录</a>
        </c:when>
        <c:otherwise>
        </c:otherwise>
    </c:choose>
</header>
<!-- 内容区域 -->
<div id="wrapper">
    <!-- 广告轮播图 -->
    <div class="top">
        <div class="swiper-container banner">
            <div class="swiper-wrapper"></div>
            <div class="swiper-pagination"></div>
        </div>
    </div>

    <!-- 公告 -->
    <div class="notice">
        <img src="<%=path %>/resources/image/wap/home-notice.png"/>
        <ul class="noticeContent">
        </ul>
        <div class="more"><a href="<%=path %>/userWap/wapSystemNotice/show">更多</a></div>
        <div class="clear"></div>
    </div>

    <!-- 列表 -->
    <div class="content-list"></div>
    <div class="str"></div>
    <!-- 合作商家 -->
    <div class="seller">
        <div class="sellerTitle">
            <div class="border"></div>
            <p>合作伙伴</p>
            <div class="clear"></div>
        </div>
        <div class="sellerContent"></div>
    </div>
</div>
<!-- loading图 -->
<div id="loading">
    <i></i>
</div>

<!-- 选择币种 -->
    <div class="choseBzBox">
        <div class="choseBzBox-title">
            <p>选择币种</p>
            <img src="<%=path%>/resources/image/wap/header-close.png" class="closeBox"/>
            <div class="clear"></div>
        </div>
        <div class="choseBzBox-content">
            <ul id="currencyList">
            </ul>
        </div>
    </div>

<!-- 底部tabBar -->
<footer>
    <a class="home" href="#">
        <img src="<%=path %>/resources/image/wap/home-chose.png" class="home-icon"/>
        <p class="chose">首页</p>
    </a>
    <a  class="deal open">
        <img src="<%=path %>/resources/image/wap/deal-nochose.png" class="deal-icon"/>
        <p>交易</p>
    </a>
    <a class="offline-transaction" href="<%=path %>/userWap/otcTradeCenter/show">
        <img src="<%=path %>/resources/image/wap/offline-transaction-nochose.png" class="deal-icon"/>
        <p>场外交易</p>
    </a>
    <a href="<%=path %>/userWap/userInfo/show.htm" class="mine">
        <img src="<%=path %>/resources/image/wap/mine-nochose.png" class="mine-icon"/>
        <p>我的</p>
    </a>
</footer>
</body>
<script id="template" type="text/x-handlebars-template">
    {{#each this}}
    <a href="<%=path%>/userWap/tradeCenter/show/{{currencyId}}">
        <ul>
            <li>
                <img src="{{currencyImgUrl}}"/>
                <div>
                    <p class="list-name">{{currencyName}}</p>
                    {{#compare change 0}}
                    <p class="red">{{latestPrice}} </p>
                    {{else}}
                    <p class="green">{{latestPrice}} </p>
                    {{/compare}}
                </div>
            </li>
            <li>
                <div>
                    <p>日成交量</p>
                    <p> {{volume}}</p>
                </div>
            </li>
            <li>
                <div>
                    <p>日涨跌</p>
                    <p>
                        {{#compare change 0}}
                        {{#if change}}
                        <span class="red"> + </span>
                        {{/if}}
                        <span class="red"> {{change}}%</span>
                        {{else}}
                        <span class="green"> {{change}}%</span>
                        {{/compare}}
                    </p>
                </div>
            </li>
        </ul>
    </a>
    {{/each}}
</script>

<script id="sellerTemplate" type="text/x-handlebars-template">
    {{#each this}}
        {{#if webLinkUrl}}
        <div class="iconBox">
            <a target="_blank" href="{{webLinkUrl}}" class="link">
                <img src="{{businessesImageUrlFormat}}"/>
            </a>
            <p>{{businessesName}}</p>
        </div>
        {{else}}
        <div class="iconBox">
            <a target="_blank"  class="link">
                <img src="{{businessesImageUrlFormat}}"/>
            </a>
            <p>{{businessesName}}</p>
        </div>
        {{/if}}
    {{/each}}
</script>

<script id="swiperTemplate" type="text/x-handlebars-template">
    {{#each this}}
    {{#if wapLinkUrl}}
    <a target="_blank" href="{{wapLinkUrl}}" class="swiper-slide" style="display: block;background: url({{adsImageUrlFormat}});background-size:cover !important;"  >
    </a>
    {{else}}
    <a class="swiper-slide" style="display: block;background: url({{adsImageUrlFormat}});background-size:cover !important;">
    </a>
    {{/if}}
    {{/each}}
</script>

<script id="noticeTemplate" type="text/x-handlebars-template">
    {{#each this}}
    <li class="noticebox">
        <a target="_blank" href="<%=path%>/userWap/wapSystemNotice/showNoticeDetail/{{id}}" class="link">
            <p class="noticeTitle">【公告】{{{noticeTitle}}}</p>
            <span>{{addTimeDateConvert addTime}}</span>
        </a>
    </li>
    {{/each}}
</script>

<script id="table-template" type="text/x-handlebars-template">
    {{#each this}}
    <li>
        <p style="display: none;">{{currencyId}}</p>
        <p>{{currencyName}}({{currencyShortName}})</p>

        {{#compare change 0}}
        <p class="red">{{latestPrice}}</p>
        {{else}}
        <p class="green">{{latestPrice}}</p>
        {{/compare}}

        {{#compare change 0}}
        <p class="red">{{change}}%</p>
        {{else}}
        <p class="green">{{change}}%</p>
        {{/compare}}
    </li>
    {{/each}}
</script>
<script src="<%=path %>/resources/js/wap/common.js"></script>
<script src="<%=path %>/resources/js/wap/zepto.min.js"></script>
<script src="<%=path %>/resources/js/wap/jquery-2.1.4.min.js"></script>
<script src="<%=path %>/resources/js/wap/swiper-3.4.2.min.js"></script>
<script src="<%=path %>/resources/js/wap/index.js"></script>
<script src="<%=path %>/resources/js/wap/handlebars-v4.0.11.js"></script>

<script type="text/javascript">
    var path = "<%=path%>"

    //日期转换
    Handlebars.registerHelper("addTimeDateConvert", function (addTime) {
        var date = new Date(addTime);
        var year = date.getFullYear();
        var month = date.getMonth() + 1;
        month=month < 10 ? ('0' + month) : month;
        var day = date.getDate();
        day=day < 10 ? ('0' + day) : day;
        return year + "-" + month + "-" + day;
    });

    //if比较
    Handlebars.registerHelper("compare", function (x1, x2, options) {

        if (x1 >= x2) {
            //满足条件执行
            return options.fn(this);
        } else {
            //不满足执行{{else}}部分
            return options.inverse(this);
        }
    });

    //保留小数点后几位
    Handlebars.registerHelper("toDecimal", function (num, index) {
        var result = num.substring(0, num.indexOf(".") + index + 1);
        return result;
    });

    $(document).ready(function () {
        //循环执行，每隔10秒钟执行一次 10000
        var t1 = window.setInterval(refreshMarket, 10000);
    });

    var systemAdsHomepagesListData = ${requestScope.systemAdsHomepagesList};
    var systemAdsHomepagesfunc = Handlebars.compile($('#swiperTemplate').html());
    $('.swiper-wrapper').html(systemAdsHomepagesfunc(systemAdsHomepagesListData));

    //公告数据填充
    var systemNoticeListData = ${requestScope.systemNoticeList};
    var noticefunc = Handlebars.compile($('#noticeTemplate').html());
    $('.noticeContent').html(noticefunc(systemNoticeListData));
    //交易数据填充
    var transactionUserDealListData = ${requestScope.transactionUserDealList};
    var transactionfunc = Handlebars.compile($('#template').html());
    $('.content-list').html(transactionfunc(transactionUserDealListData));

    //合作伙伴数据填充
    var systemBusinessesPartnerListData = ${requestScope.systemBusinessesPartnerList};
    var systemBusinessesfunc = Handlebars.compile($('#sellerTemplate').html());
    $('.sellerContent').html(systemBusinessesfunc(systemBusinessesPartnerListData));

    //刷新币种行情
    function refreshMarket() {
        $.ajax({
            url: '<%=path %>/userWeb/homePage/getCurrencyMarket',
            type: 'post',
            dataType: 'json',
            success: function (result) {
                if (result.code == 1) {
                    var currencyMarket = result.data;
                    if (currencyMarket != null) {
                        var marketList = currencyMarket.transactionUserDealList;
                        if (marketList != null) {
                            $(".content-list ul").remove();
                            var transactionfunc = Handlebars.compile($('#template').html());
                            $('.content-list').html(transactionfunc(marketList));
                        }
                    }
                }
            }
        });
    }

    $('.choseBzBox-content ul').on('click', 'li', function () {
        var currencyId=$(this).find("p").eq(0).text();
        window.location.href="<%=path%>/userWap/tradeCenter/show/"+currencyId
    })
</script>
</html>