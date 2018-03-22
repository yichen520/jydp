<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <meta http-equiv="Cache-Control" content="no-cache" />

    <link rel="stylesheet" href="css/common.css">
    <link rel="stylesheet" href="css/mine.css">

    <title>我的</title>
</head>
<body>
    <!-- 内容区域 -->
    <div id="wrapper">
        <!-- 未登录状态 -->
        <div class="noLogin">
            <div class="title">
                <img src="images/mine-user.png" />
                <p>您还未登录，请先登录</p>
                <div class="clear"></div>
            </div>
        </div>
        <!-- 登录状态 -->
        <div class="login">
            <div class="title">
                <img src="images/mine-user.png" />
                <p>Your name</p>
                <div class="clear"></div>
            </div>
            <div class="userBox">
                <div class="usertitle">
                    <p>账户总资产（$）</p>
                    <p>12345.00</p>
                </div>
                <div class="usercontent">
                    <div class="canuserBox">
                        <p>可用资产（$）</p>
                        <p>12345.00</p>
                    </div>
                    <div class="unuserBox">
                        <p>冻结资产（$）</p>
                        <p>12345.00</p>
                    </div>
                    <div class="recharge">充值</div>
                </div>
            </div>
        </div>
        <!-- 列表 -->
        <div class="mine-list">
            <ul>
                <li>
                    <img src="./images/userIcon.png" class="icon"/>
                    <p>个人中心
                        <img src="./images/nextIcon.png" class="next" />
                    </p>
                    <div class="clear"></div>
                </li>
                <li>
                    <img src="./images/moneyIcon.png" class="icon"/>
                    <p>币种资产
                        <img src="./images/nextIcon.png" class="next" />
                    </p>
                    <div class="clear"></div>
                </li>
                <li>
                    <img src="./images/myrecordIco.png" class="icon"/>
                    <p>我的记录
                        <img src="./images/nextIcon.png" class="next" />
                    </p>
                    <div class="clear"></div>
                </li>
                <li>
                    <img src="./images/cashIcon.png" class="icon"/>
                    <p>立即提现
                        <img src="./images/nextIcon.png" class="next" />
                    </p>
                    <div class="clear"></div>
                </li>
                <li>
                    <img src="./images/noticeIcon.png" class="icon"/>
                    <p>系统公告
                        <img src="./images/nextIcon.png" class="next" />
                    </p>
                    <div class="clear"></div>
                </li>
                <li>
                    <img src="./images/helpIcon.png" class="icon"/>
                    <p>帮助中心
                        <img src="./images/nextIcon.png" class="next" />
                    </p>
                    <div class="clear"></div>
                </li>
                <li>
                    <img src="./images/serviceIcon.png" class="icon"/>
                    <p>联系客服
                        <img src="./images/nextIcon.png" class="next" />
                    </p>
                    <div class="clear"></div>
                </li>
            </ul>
        </div>
    </div>
    <!-- 底部tabBar -->
    <footer>
        <a href="index.html" class="home">
            <img src="images/home-nochose.png"  class="home-icon"/>
            <p>首页</p>
        </a>
        <a  href="deal.html" class="deal">
            <img src="images/deal-nochose.png"  class="deal-icon"/>
            <p>交易</p>
        </a>
        <a href="#" class="mine">
            <img src="images/mine-chose.png"  class="mine-icon"/>
            <p class="chose">我的</p>
        </a>
    </footer>
</body>

<script src="js/common.js"></script>
<script src="js/zepto.min.js"></script>
<script src="js/jquery-2.1.4.min.js"></script>
</html>