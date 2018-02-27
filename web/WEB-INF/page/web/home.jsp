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
                <a href="#" class="swiper-slide"><img src="images/test/test_heng1.jpg" /></a>
                <a href="#" class="swiper-slide"><img src="images/test/test500.jpg" /></a>
                <a href="#" class="swiper-slide"><img src="images/test/test_heng.jpg" /></a>
                <a href="#" class="swiper-slide"><img src="images/test/test_shu.jpg" /></a>
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
        <tr class="coinInfo">
            <td class="coin">
                <img src="images/test/test_300.jpg" />
                <span>盛源链(MUC/USD)</span>
            </td>
            <td class="new">17.024</td>
            <td class="money">100.0000</td>
            <td class="money">100.0000</td>
            <td class="money">199</td>
            <td class="uplift in">+17%</td>
            <td class="operate"><a href="#">去交易</a></td>
        </tr>
        <tr class="coinInfo">
            <td class="coin">
                <img src="images/test/test_300.jpg" />
                <span>盛源链(MUC/USD)</span>
            </td>
            <td class="new">17.024</td>
            <td class="money">100.0000</td>
            <td class="money">100.0000</td>
            <td class="money">199</td>
            <td class="uplift minus">-17%</td>
            <td class="operate"><a href="#">去交易</a></td>
        </tr>
    </table>

    <div class="bottom">
        <div class="notice">
            <span class="bTitle">
                <img src="<%=path %>/resources/image/web/notice.png" />系统公告
                <a href="#" class="more">查看更多</a>
            </span>
            <ul class="list">
                <li class="listInfo">
                    <a href="#" class="link">
                        <span class="noticeTitle">【<span>公告</span>】XXXXXXXXXXX</span>
                        <span class="time">2016-06-06</span>
                    </a>
                </li>
                <li class="listInfo">
                    <a href="#" class="link">
                        <span class="noticeTitle">【<span>公告</span>】XXXXXXXXXXX</span>
                        <span class="time">2016-06-06</span>
                    </a>
                </li>
                <li class="listInfo">
                    <a href="#" class="link">
                        <span class="noticeTitle">【<span>公告</span>】XXXXXXXXXXX</span>
                        <span class="time">2016-06-06</span>
                    </a>
                </li>
                <li class="listInfo">
                    <a href="#" class="link">
                        <span class="noticeTitle">【<span>公告</span>】XXXXXXXXXXX</span>
                        <span class="time">2016-06-06</span>
                    </a>
                </li>
                <li class="listInfo">
                    <a href="#" class="link">
                        <span class="noticeTitle">【<span>公告</span>】XXXXXXXXXXX</span>
                        <span class="time">2016-06-06</span>
                    </a>
                </li>
            </ul>
        </div>

        <div class="hot">
            <span class="bTitle">
                <img src="<%=path %>/resources/image/web/hot.png" />热门话题
                <a href="#" class="more">查看更多</a>
            </span>
            <ul class="list">
                <li class="listInfo">
                    <a href="#" class="link">
                        <span class="noticeTitle">【<span>热门</span>】XXXXXXXXXXX</span>
                        <span class="time">2016-06-06</span>
                    </a>
                </li>
                <li class="listInfo">
                    <a href="#" class="link">
                        <span class="noticeTitle">【<span>热门</span>】XXXXXXXXXXX</span>
                        <span class="time">2016-06-06</span>
                    </a>
                </li>
                <li class="listInfo">
                    <a href="#" class="link">
                        <span class="noticeTitle">【<span>热门</span>】XXXXXXXXXXX</span>
                        <span class="time">2016-06-06</span>
                    </a>
                </li>
                <li class="listInfo">
                    <a href="#" class="link">
                        <span class="noticeTitle">【<span>热门</span>】XXXXXXXXXXX</span>
                        <span class="time">2016-06-06</span>
                    </a>
                </li>
                <li class="listInfo">
                    <a href="#" class="link">
                        <span class="noticeTitle">【<span>热门</span>】XXXXXXXXXXX</span>
                        <span class="time">2016-06-06</span>
                    </a>
                </li>
            </ul>
        </div>
    </div>

    <div class="cooperation">
        <p class="cTitle">合作商家</p>
        <p class="company"><img src="images/test/test_300.jpg" /><span>盛临九洲</span></p>
        <p class="company"><img src="images/test/test_300.jpg" /><span>盛临九洲</span></p>
        <p class="company"><img src="images/test/test_300.jpg" /><span>盛临九洲</span></p>
        <p class="company"><img src="images/test/test_300.jpg" /><span>盛临九洲</span></p>
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
</script>

</body>
</html>