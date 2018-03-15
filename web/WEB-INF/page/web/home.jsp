<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/resources/page/common/path.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <link rel="icon" href="<%=path %>/resources/image/web/icon.ico" type="image/x-ico" />
    <link rel="stylesheet" type="text/css" href="<%=path %>/resources/css/web/home.css" />
    <link rel="stylesheet" type="text/css" href="<%=path %>/resources/css/web/public.css" />
    <link rel="stylesheet" type="text/css" href="<%=path %>/resources/css/web/swiper.min.css" />
    <link rel="stylesheet" type="text/css" href="<%=path %>/resources/css/web/simpleTips.css" />
    <script type="text/javascript" src="http://libs.baidu.com/jquery/2.1.4/jquery.min.js"></script>
    <script type="text/javascript" src="<%=path %>/resources/js/loadPageWeb.js"></script>
    <script type="text/javascript" src="<%=path %>/resources/js/swiper.min.js"></script>
    <script type="text/javascript" src="<%=path %>/resources/js/simpleTips.js"></script>
    <title>首页</title>
</head>
<body>
<header id="header"></header>
<div id="menu"></div>

<div class="content">
    <div class="top">
        <!-------轮播图----------->
        <div class="swiper-container">
            <div class="swiper-wrapper">
                <c:forEach items="${systemAdsHomepagesList}" var="homePageAds">
                    <c:if test="${homePageAds.webLinkUrl != ''}">
                        <a target="_blank" href="${homePageAds.webLinkUrl}" class="swiper-slide"><img src="${homePageAds.adsImageUrlFormat}" /></a>
                    </c:if>
                    <c:if test="${homePageAds.webLinkUrl == ''}">
                        <a class="swiper-slide"><img src="${homePageAds.adsImageUrlFormat}" /></a>
                    </c:if>
                </c:forEach>
            </div>
            <div class="swiper-pagination"></div>
        </div>
    </div>

    <table class="coinTable" cellpadding="0" cellspacing="0">
        <tr class="coinTitle">
            <td class="coin">交易币种</td>
            <td class="new">最新成交价</td>
            <td class="money">买一价</td>
            <td class="money">卖一价</td>
            <td class="money">今日成交量</td>
            <td class="uplift">今日涨跌</td>
            <td class="operate">操作</td>
        </tr>
        <c:forEach items="${transactionUserDealList}" var="transactionUserDeal">
            <tr class="coinInfo">
                <td class="coin"><img src="${transactionUserDeal.currencyImgUrl}" /><span>${transactionUserDeal.currencyName}(${transactionUserDeal.currencyShortName}/USD)</span></td>
                <td class="new"><fmt:formatNumber type="number" value="${transactionUserDeal.latestPrice}" groupingUsed="FALSE" maxFractionDigits="2"/></td>
                <td class="money"><fmt:formatNumber type="number" value="${transactionUserDeal.buyOnePrice}" groupingUsed="FALSE" maxFractionDigits="2"/></td>
                <td class="money"><fmt:formatNumber type="number" value="${transactionUserDeal.sellOnePrice}" groupingUsed="FALSE" maxFractionDigits="2"/></td>
                <td class="money"><fmt:formatNumber type="number" value="${transactionUserDeal.volume}" groupingUsed="FALSE" maxFractionDigits="4"/></td>
                <c:if test="${transactionUserDeal.change >= 0 }">
                    <td class="uplift in">
                        <c:if test="${transactionUserDeal.change > 0 }">+</c:if><fmt:formatNumber type="number" value="${transactionUserDeal.change}" groupingUsed="FALSE" maxFractionDigits="2"/>%
                    </td>
                </c:if>
                <c:if test="${transactionUserDeal.change < 0 }">
                    <td class="uplift minus"><fmt:formatNumber type="number" value="${transactionUserDeal.change}" groupingUsed="FALSE" maxFractionDigits="2"/>%</td>
                </c:if>
                <td class="operate"><a href="<%=path%>/userWeb/tradeCenter/show/${transactionUserDeal.currencyId}">去交易</a></td>
            </tr>
        </c:forEach>
    </table>

    <div class="bottom">
        <div class="notice">
            <span class="bTitle">
                <img src="<%=path %>/resources/image/web/notice.png" />系统公告
                <a href="<%=path %>/userWeb/webSystemNotice/show" class="more">查看更多</a>
            </span>
            <ul class="list">
                <c:forEach items="${systemNoticeList}" var="systemNotice">
                    <li class="listInfo">
                        <a href="<%=path%>/userWeb/webSystemNotice/showNoticeDetail/${systemNotice.id}" class="link">
                            <span class="noticeTitle">【<span>公告</span>】${systemNotice.noticeTitle}</span>
                            <span class="time"><fmt:formatDate type="time" value="${systemNotice.addTime}" pattern="yyyy-MM-dd"></fmt:formatDate></span>
                        </a>
                    </li>
                </c:forEach>
            </ul>
        </div>

        <div class="hot">
            <span class="bTitle">
                <img src="<%=path %>/resources/image/web/hot.png" />热门话题
                <a href="<%=path %>/userWeb/webSystemHot/show" class="more">查看更多</a>
            </span>
            <ul class="list">
                <c:forEach items="${systemHotList}" var="hotTopic">
                    <li class="listInfo">
                        <a href="<%=path%>/userWeb/webSystemHot/showHotDetail/${hotTopic.id}" class="link">
                            <span class="noticeTitle">【<span>热门</span>】${hotTopic.noticeTitle}</span>
                            <span class="time"><fmt:formatDate type="time" value="${hotTopic.addTime}" pattern="yyyy-MM-dd"></fmt:formatDate></span>
                        </a>
                    </li>
                </c:forEach>
            </ul>
        </div>
    </div>

    <div class="cooperation">
        <p class="cTitle">合作伙伴</p>
        <c:forEach items="${systemBusinessesPartnerList}" var="partner">
            <c:if test="${partner.webLinkUrl != ''}">
                <a target="_blank" href="${partner.webLinkUrl}" class="link">
                    <p class="company"><img src="${partner.businessesImageUrlFormat}" /><span>${partner.businessesName}</span></p>
                </a>
            </c:if>
            <c:if test="${partner.webLinkUrl == ''}">
                <a class="link">
                    <p class="company" style="cursor:default">
                        <img src="${partner.businessesImageUrlFormat}" /><span>${partner.businessesName}</span>
                    </p>
                </a>
            </c:if>
        </c:forEach>
    </div>
</div>

<div id="helpFooter"></div>
<div id="footer"></div>

<script type="text/javascript">
    var swiper = new Swiper('.top .swiper-container', {
        pagination: '.swiper-pagination',
        nextButton: '.swiper-button-next',
        prevButton: '.swiper-button-prev',
        paginationClickable: true,
        spaceBetween: 30,
        centeredSlides: true,
        autoplay: 4000,
        autoplayDisableOnInteraction: false
    });

    $(document).ready(function(){
        //循环执行，每隔10秒钟执行一次 10000
        var t1=window.setInterval(refreshMarket, 10000);
    });

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
                        $(".coinInfo").remove();
                        if (marketList != null) {
                            for (var i = marketList.length-1; i >=0; i--) {
                                var transactionUserDeal = marketList[i];

                                //涨幅样式显示
                                var classStyle = "";
                                var symbol = "";
                                if (transactionUserDeal.change >= 0) {
                                    classStyle = "in";
                                    if (transactionUserDeal.change > 0){
                                        symbol = "+"
                                    }
                                } else if (transactionUserDeal.change < 0) {
                                    classStyle = "minus";
                                }
                                $(".coinTitle").after(
                                    '<tr class="coinInfo">' +
                                    '<td class="coin">' +
                                        '<img src="' + transactionUserDeal.currencyImgUrl + '"/>' +
                                        '<span>' + transactionUserDeal.currencyName + '(' + transactionUserDeal.currencyShortName + '/USD)</span>' +
                                    '</td>' +
                                    '<td class="new">' + parseFloat(transactionUserDeal.latestPrice).toString() + '</td>' +
                                    '<td class="money">' + parseFloat(transactionUserDeal.buyOnePrice).toString() + '</td>' +
                                    '<td class="money">' + parseFloat(transactionUserDeal.sellOnePrice).toString() + '</td>' +
                                    '<td class="money">' + parseFloat(transactionUserDeal.volume).toString() + '</td>' +
                                    '<td class="uplift '+classStyle+'">' + symbol + parseFloat(transactionUserDeal.change).toString() + '%</td>' +
                                    '<td class="operate"><a href="<%=path%>/userWeb/tradeCenter/show/'+transactionUserDeal.currencyId+'">去交易</a></td>' +
                                    '</tr>');
                            }
                        }
                    }
                }
            }
        });
    }

</script>

</body>
</html>