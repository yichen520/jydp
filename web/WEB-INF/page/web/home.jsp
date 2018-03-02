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
                <c:forEach items="${systemAdsHomepagesDOList}" var="homePageAds">
                    <a href="${homePageAds.webLinkUrl}" class="swiper-slide"><img src="${homePageAds.adsImageUrlFormat}" /></a>
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
            <td class="money">24小时成交量</td>
            <td class="uplift">24小时涨跌</td>
            <td class="operate">操作</td>
        </tr>
        <c:forEach items="${transactionUserDealDTOList}" var="transactionUserDeal">
            <tr class="coinInfo">
                <td class="coin">
                    <img src="${transactionUserDeal.currencyImgUrl}" />
                    <span>${transactionUserDeal.currencyName}(${transactionUserDeal.currencyShortName}/USD)</span>
                </td>
                <td class="new">${transactionUserDeal.latestPrice}</td>
                <td class="money">${transactionUserDeal.buyOnePrice}</td>
                <td class="money">${transactionUserDeal.sellOnePrice}</td>
                <td class="money">${transactionUserDeal.volume}</td>
                <td class="uplift in">${transactionUserDeal.change}%</td>
                <td class="operate"><a href="#">去交易</a></td>
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
                <c:forEach items="${systemNoticeDOList}" var="systemNotice">
                    <li class="listInfo">
                        <a href="<%=path %>/userWeb/webSystemNotice/showNoticeDetail?noticeId=${systemNotice.id}" class="link">
                            <span class="noticeTitle">【<span>公告</span>】${systemNotice.noticeTitle}</span>
                            <span class="time"><fmt:formatDate type="time" value="${systemNotice.addTime}" pattern="yyyy-MM-dd HH:mm:ss"></fmt:formatDate></span>
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
                <c:forEach items="${systemHotDOList}" var="hotTopic">
                    <li class="listInfo">
                        <a href="<%=path %>/userWeb/webSystemHot/showHotDetail?hotId=${hotTopic.id}" class="link">
                            <span class="noticeTitle">【<span>热门</span>】${hotTopic.noticeTitle}</span>
                            <span class="time"><fmt:formatDate type="time" value="${hotTopic.addTime}" pattern="yyyy-MM-dd HH:mm:ss"></fmt:formatDate></span>
                        </a>
                    </li>
                </c:forEach>
            </ul>
        </div>
    </div>

    <div class="cooperation">
        <p class="cTitle">合作商家</p>
        <c:forEach items="${systemBusinessesPartnerDOList}" var="partner">
            <a href="${partner.webLinkUrl}" class="link">
              <p class="company"><img src="${partner.businessesImageUrlFormat}" /><span>${partner.businessesName}</span></p>
            </a>
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
                    if (currencyMarket != null){
                        var marketList = currencyMarket.transactionUserDealDTOList;
                        $(".coinInfo").remove();
                        for (var i = marketList.length-1; i >=0; i--) {
                            var transactionUserDeal = marketList[i];
                            $(".coinTitle").after(
                                '<tr class="coinInfo">' +
                                '<td class="coin">' +
                                '<img src="' + transactionUserDeal.currencyImgUrl + '"/>' +
                                '<span>' + transactionUserDeal.currencyName + '(' + transactionUserDeal.currencyShortName + '/USD)</span>' +
                                '</td>' +
                                '<td class="new">' + transactionUserDeal.latestPrice + '</td>' +
                                '<td class="money">' + transactionUserDeal.buyOnePrice + '</td>' +
                                '<td class="money">' + transactionUserDeal.sellOnePrice + '</td>' +
                                '<td class="money">' + transactionUserDeal.volume + '</td>' +
                                '<td class="uplift in">' + transactionUserDeal.change + '%</td>' +
                                '<td class="operate"><a href="#">去交易</a></td>' +
                                '</tr>');
                        }

                    }

                }
            }
        });
    }
</script>

</body>
</html>