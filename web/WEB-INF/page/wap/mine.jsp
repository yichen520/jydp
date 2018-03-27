<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <meta http-equiv="Cache-Control" content="no-cache" />

    <link rel="stylesheet" href="<%=path%>/resources/css/wap/common.css">
    <link rel="stylesheet" href="<%=path%>/resources/css/wap/mine.css">

    <title>我的</title>
</head>
<body>
    <!-- 内容区域 -->
    <div id="wrapper">
        <c:if test="${code==4}">
            <!-- 未登录状态 -->
            <div class="noLogin">
                <div class="title">
                    <img src="<%=path%>/resources/image/wap/mine-user.png" />
                    <p style="text-decoration: underline"><a href="<%=path%>/wapLogin">您还未登录，请先登录</a></p>
                    <div class="clear"></div>
                </div>
            </div>
        </c:if>

        <c:if test="${code==2}">
            <!-- 未登录状态 -->
            <div class="noLogin">
                <div class="title">
                    <img src="<%=path%>/resources/image/wap/mine-user.png" />
                    <p>${message}</p>
                    <div class="clear"></div>
                </div>
            </div>
        </c:if>

        <c:if test="${code==1}">
            <!-- 登录状态 -->
            <div class="login">
                <div class="title">
                    <img src="<%=path%>/resources/image/wap/mine-user.png" />
                    <p>${userInfo.userAccount}</p>
                    <div class="clear"></div>
                </div>
                <c:if test="${code==1}">
                    <div class="userBox">
                        <div class="usertitle">
                            <p>账户总资产（$）</p>
                            <p><fmt:formatNumber type="number" maxFractionDigits="6" value="${userInfo.userBalance+userInfo.userBalanceLock}"></fmt:formatNumber></p>
                        </div>
                        <div class="usercontent">
                            <div class="canuserBox">
                                <p>可用资产（$）</p>
                                <p><fmt:formatNumber type="number" maxFractionDigits="6" value="${userInfo.userBalance}"></fmt:formatNumber></p>
                            </div>
                            <div class="unuserBox">
                                <p>冻结资产（$）</p>
                                <p><fmt:formatNumber type="number" maxFractionDigits="6" value="${userInfo.userBalanceLock}"></fmt:formatNumber></p>
                            </div>
                            <div class="recharge">充值</div>
                        </div>
                    </div>
                    </div>
                </c:if>
        </c:if>
        <!-- 列表 -->
        <div class="mine-list">
            <ul>
                <li id="userCenter" style="cursor: pointer;">
                    <img src="<%=path%>/resources/image/wap/userIcon.png" class="icon"/>
                    <p>个人中心
                        <img src="<%=path%>/resources/image/wap/nextIcon.png" class="next" />
                    </p>
                    <div class="clear"></div>
                </li>
                <li id="currencyAssets" style="cursor: pointer;">
                    <img src="<%=path%>/resources/image/wap/moneyIcon.png" class="icon"/>
                    <p>币种资产
                        <img src="<%=path%>/resources/image/wap/nextIcon.png" class="next" />
                    </p>
                    <div class="clear"></div>
                </li>
                <li>
                    <img src="<%=path%>/resources/image/wap/myrecordIco.png" class="icon"/>
                    <p>我的记录
                        <img src="<%=path%>/resources/image/wap/nextIcon.png" class="next" />
                    </p>
                    <div class="clear"></div>
                </li>
                <li>
                    <img src="<%=path%>/resources/image/wap//cashIcon.png" class="icon"/>
                    <p>立即提现
                        <img src="<%=path%>/resources/image/wap/nextIcon.png" class="next" />
                    </p>
                    <div class="clear"></div>
                </li>
                <li>
                    <img src="<%=path%>/resources/image/wap/noticeIcon.png" class="icon"/>
                    <p>系统公告
                        <img src="<%=path%>/resources/image/wap/nextIcon.png" class="next" />
                    </p>
                    <div class="clear"></div>
                </li>
                <li>
                    <img src="<%=path%>/resources/image/wap/helpIcon.png" class="icon"/>
                    <p>帮助中心
                        <img src="<%=path%>/resources/image/wap/nextIcon.png" class="next" />
                    </p>
                    <div class="clear"></div>
                </li>
                <li>
                    <img src="<%=path%>/resources/image/wap/serviceIcon.png" class="icon"/>
                    <p>联系客服
                        <img src="<%=path%>/resources/image/wap/nextIcon.png" class="next" />
                    </p>
                    <div class="clear"></div>
                </li>
            </ul>
        </div>
    </div>
    <!-- 底部tabBar -->
    <footer>
        <a href="<%=path%>/userWap/homePage/show" class="home">
            <img src="<%=path%>/resources/image/wap/home-nochose.png"  class="home-icon"/>
            <p>首页</p>
        </a>
        <a  href="<%=path%>/userWap/userMine/show.htm" class="deal">
            <img src="<%=path%>/resources/image/wap/deal-nochose.png"  class="deal-icon"/>
            <p>交易</p>
        </a>
        <a href="#" class="mine">
            <img src="<%=path%>/resources/image/wap/mine-chose.png"  class="mine-icon"/>
            <p class="chose">我的</p>
        </a>
    </footer>
</body>

<script src="<%=path%>/resources/js/wap/common.js"></script>
<script src="<%=path%>/resources/js/wap/zepto.min.js"></script>
<script src="<%=path%>/resources/js/wap/jquery-2.1.4.min.js"></script>


<script type="text/javascript">

    $("#userCenter").click(function () {
        window.location.href="/userWap/userInfo/userCenter/show.htm";
    });

    $("#currencyAssets").click(function () {
        window.location.href="/userWap/userInfo/currencyAssets/show.htm";
    })

</script>
</html>