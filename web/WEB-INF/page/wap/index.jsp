<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/resources/page/common/path.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <meta http-equiv="Cache-Control" content="no-cache" />

    <link rel="stylesheet" href="<%=path %>/resources/css/wap/common.css">
    <link rel="stylesheet" href="<%=path %>/resources/css/wap/index.css">
    <link rel="stylesheet" href="<%=path %>/resources/css/wap/swiper-3.4.2.min.css">

    <title>首页</title>
</head>
<body>
    <!-- 头部导航 -->
    <header>
        <img src="<%=path %>/resources/image/wap/home-logo.png" />
        <p>首页</p>
        <a href="login.jsp">登录</a>
    </header>
    <!-- 内容区域 -->
    <div id="wrapper">
        <!-- 广告轮播图 -->
        <div class="swiper-container banner">
            <div class="swiper-wrapper">

                <c:forEach items="${systemAdsHomepagesList}" var="homePageAds">
                    <c:if test="${homePageAds.wapLinkUrl != ''}">
                        <a target="_blank" href="${homePageAds.wapLinkUrl}" class="swiper-slide"><img src="${homePageAds.adsImageUrlFormat}" class="swiper-slide"  /></a>
                    </c:if>
                    <c:if test="${homePageAds.wapLinkUrl == ''}">
                        <a class="swiper-slide"><img src="${homePageAds.adsImageUrlFormat}" /></a>
                    </c:if>
                </c:forEach>
            </div>
            <div class="swiper-pagination"></div>
        </div>
        <!-- 公告 -->
        <div class="notice">
            <img src="<%=path %>/resources/image/wap/home-notice.png" />
            <ul class="noticeContent">
                <c:forEach items="${systemNoticeList}" var="systemNotice">
                    <a target="_blank" href="<%=path%>/userWeb/webSystemNotice/showNoticeDetail/${systemNotice.id}" class="link">
                    <li class="noticebox">
                        <p class="noticeTitle">【公告】${systemNotice.noticeTitle}</p>
                        <span><fmt:formatDate type="time" value="${systemNotice.addTime}" pattern="yyyy-MM-dd"></fmt:formatDate></span>
                    </li>
                </a>
                </c:forEach>
            </ul>
            <div class="more"><a href="notice.jsp">更多</a></div>
            <div class="clear"></div>
        </div>
        <!-- 列表 -->
        <div class="content-list">
            <c:forEach items="${transactionUserDealList}" var="transactionUserDeal">
            <ul>
                <li>
                    <img src="${transactionUserDeal.currencyImgUrl}" />
                    <div>
                        <p>${transactionUserDeal.currencyName}</p>
                        <c:if test="${transactionUserDeal.change >= 0 }">
                        <p class="red">$<fmt:formatNumber type="number" value="${transactionUserDeal.latestPrice}" groupingUsed="FALSE" maxFractionDigits="2"/></p>
                        </c:if>
                        <c:if test="${transactionUserDeal.change < 0 }">
                            <p class="green">$<fmt:formatNumber type="number" value="${transactionUserDeal.latestPrice}" groupingUsed="FALSE" maxFractionDigits="2"/></p>
                        </c:if>
                    </div>
                </li>
                <li>
                    <div>
                        <p>日成交量</p>
                        <c:if test="${transactionUserDeal.change >= 0 }">
                            <p class="red">$<fmt:formatNumber type="number" value="${transactionUserDeal.volume}" groupingUsed="FALSE" maxFractionDigits="4"/></p>
                        </c:if>
                        <c:if test="${transactionUserDeal.change < 0 }">
                            <p class="green">$<fmt:formatNumber type="number" value="${transactionUserDeal.volume}" groupingUsed="FALSE" maxFractionDigits="4"/></p>
                        </c:if>
                    </div>
                </li>
                <li>
                    <div>
                        <p>日涨跌</p>
                        <p>
                            <c:if test="${transactionUserDeal.change >= 0 }">
                            <span class="red">
                                <c:if test="${transactionUserDeal.change > 0 }">+</c:if><fmt:formatNumber type="number" value="${transactionUserDeal.change}" groupingUsed="FALSE" maxFractionDigits="2"/>%
                            </span>
                            </c:if>
                            <c:if test="${transactionUserDeal.change < 0 }">
                                <span class="green"><fmt:formatNumber type="number" value="${transactionUserDeal.change}" groupingUsed="FALSE" maxFractionDigits="2"/>%</span>
                            </c:if>
                        </p>
                    </div>
                </li>
            </ul>
            </c:forEach>
        </div>
        <div class="str"></div>
        <!-- 合作商家 -->
        <div class="seller">
            <div class="sellerTitle">
                <div class="border"></div>
                <p>合作商家</p>
                <div class="clear"></div>
            </div>
            <div class="sellerContent">
                <c:forEach items="${systemBusinessesPartnerList}" var="partner">
                    <c:if test="${partner.webLinkUrl != ''}">
                        <div class="iconBox">
                            <a target="_blank" href="${partner.webLinkUrl}" class="link">
                                <img src="${partner.businessesImageUrlFormat}" />
                            </a>
                            <p>${partner.businessesName}</p>
                        </div>
                    </c:if>
                </c:forEach>
           </div>
        </div>
    </div>
    <!-- 底部tabBar -->
    <footer>
        <a class="home">
            <img src="<%=path %>/resources/image/wap/home-chose.png"  class="home-icon"/>
            <p class="chose">首页</p>
        </a>
        <a  href="deal.html" class="deal">
            <img src="<%=path %>/resources/image/wap/deal-nochose.png"  class="deal-icon"/>
            <p>交易</p>
        </a>
        <a href="<%=path %>/userWap/userInfo/show.htm" class="mine">
            <img src="<%=path %>/resources/image/wap/mine-nochose.png"  class="mine-icon"/>
            <p>我的</p>
        </a>
    </footer>
</body>
<script src="<%=path %>/resources/js/wap/common.js"></script>
<script src="<%=path %>/resources/js/wap/zepto.min.js"></script>
<script src="<%=path %>/resources/js/wap/jquery-2.1.4.min.js"></script>
<script src="<%=path %>/resources/js/wap/swiper-3.4.2.min.js"></script>
<script src="<%=path %>/resources/js/wap/index.js"></script>

</html>