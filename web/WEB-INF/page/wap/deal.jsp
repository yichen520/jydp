<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <meta http-equiv="Cache-Control" content="no-cache" />
    
    <link rel="stylesheet" href="css/common.css">
    <link rel="stylesheet" href="css/deal.css">

    <title>交易</title>  
</head>

<body>

<!-- 头部导航 -->
<header>
    <img src="images/header-open.png" class="open"/>
    <p>盛源链（MUC/USD）</p>
    <a>登录</a>
</header>
<div class="nav">
    <div class="nav-top">
        <div class="topLeft">9399.5</div>
        <div class="topRight">
                <img src="images/deal-hq.png" />
                <span>行情</span>
        </div>
        <div class="clear"></div>
    </div>
    <div class="nav-content">
        <div class="nav-content-list">
            <p class="list-desc">最高 <span class="list-num">93</span></p>
            <p class="list-desc">最低 <span class="list-num">93</span></p>
        </div>
        <div class="nav-content-list">
            <p class="list-desc">最高 <span class="list-num">93</span></p>
            <p class="list-desc">最低 <span class="list-num">93</span></p>
        </div>
        <div class="nav-content-list">
            <p class="list-desc">日成交额</p>
            <p class="list-num">3.49万</p>
        </div>
        <div class="nav-content-list">
            <p class="list-desc">日成交额</p>
            <p class="list-num">3.49万</p>
        </div>
    </div>
</div>

<!-- 内容 -->
<div id="wrapper">
    <!-- 当前价格 -->
    <div class="nowPrice">
        <div class="title">当前价格：$0.039</div>
    </div>
    <!-- 买入卖入价格 -->
    <div class="tool">
        <div class="payLeft">
            <div class="leftTitle">
                <span>卖</span>
                <span>数量</span>
                <span>总额($)</span>
            </div>
            <div class="leftContent">
                <p>
                    <span>卖5</span>
                    <span>0.98</span>
                    <span>132.000</span>
                </p>
                <p>
                    <span>卖4</span>
                    <span>0.98</span>
                    <span>132.000</span>
                </p> 
                <p>
                    <span>卖3</span>
                    <span>0.98</span>
                    <span>132.000</span>
                </p>
                <p>
                    <span>卖2</span>
                    <span>0.98</span>
                    <span>132.000</span>
                </p>
                <p>
                    <span>卖1</span>
                    <span>0.98</span>
                    <span>132.000</span>
                </p>
            </div>
        </div>
        <div class="payRight">
            <div class="rightTitle">
                <span>买</span>
                <span>数量</span>
                <span>总额($)</span>    
            </div>
            <div class="rightContent"> 
                <p>
                    <span>买5</span>
                    <span>0.98</span>
                    <span>132.000</span>
                </p>
                <p>
                    <span>买4</span>
                    <span>0.98</span>
                    <span>132.000</span>
                </p>
                <p>
                    <span>买3</span>
                    <span>0.98</span>
                    <span>132.000</span>
                </p>
                <p>
                    <span>买2</span>
                    <span>0.98</span>
                    <span>132.000</span>
                </p>
                <p>
                    <span>买1</span>
                    <span>0.98</span>
                    <span>132.000</span>
                </p>
            </div>
        </div>
        <div class="clear"></div>
    </div>
    <!-- 买入卖出委托记录 -->
    <div class="main">
        <ul class="mainTitle">
            <li>
                <p>买入</p>
                <div class="borderBuy"></div>
            </li>
            <li>
                <p>卖出</p>
                <div class="borderSell"></div>
            </li>
            <li>
                <p>委托记录</p>
                <div class="borderEntrust"></div>
            </li>
            <div class="clear"></div>
        </ul>
    </div>
    <div class="tab">   
    <!-- 买入 -->
    <div class="buy">
        <div class="mainContent">
            <div class="mainContent-priceBuy">
                <span>单价</span><input type="number" >
            </div>
            <div class="mainContent-numBuy">
                <span>数量</span><input type="number">
            </div>
            <p class="maxNum">最大：123.000</p>
            <div class="mainContent-passwordBuy">
                <span>交易密码</span><input type="password">
            </div>
        </div>
        <div class="mainButtonBuy">买入</div>
        <div class="mainMoney">
            <p class="mainMoney-title">
                <span>总资产</span>
                <span>冻结美金</span>
                <span>可用美金</span>
            </p>

            <p class="mainMoney-num">
                <span>$123.000</span>
                <span>$123.000</span>
                <span>$123.000</span>
            </p>
        </div>
    </div>
    <!-- 卖出 -->
    <div class="sell">
        <div class="mainContent">
            <div class="mainContent-priceSell">
                <span>单价</span><input type="number" >
            </div>
            <div class="mainContent-numSell">
                <span>数量</span><input type="number" >
            </div>
            <p class="maxNum">最大：123.000</p>
            <div class="mainContent-passwordSell">
                <span>交易密码</span><input type="password" >
            </div>
        </div>
        <div class="mainButtonSell">卖出</div>
            <div class="mainMoney">
                <p class="mainMoney-title">
                        <span>总资产</span>
                        <span>冻结数量</span>
                        <span>可用数量</span>
                </p>
                <p class="mainMoney-num">
                    <span>$123.000</span>
                    <span>123.000</span>
                    <span>123.000</span>
                </p>
            </div>
        </div>
         <!-- 委托记录 -->
        <div class="entrust">
            <ul class="entrust-title">
                <li>类型</li>
                <li>单价($)</li>
                <li>数量</li>
                <li>已成交</li>
                <li>操作</li>
                <li class="clear"></li>
            </ul>
            <ul class="entrust-content">
                <li>买入</li>
                <li>123.00</li>
                <li>123.00</li>
                <li>123.00</li>
                <li class="cancel">撤销</li>
                <li class="clear"></li>
            </ul>
            <ul class="entrust-content">
                <li>卖出</li>
                <li>123.00</li>
                <li>123.00</li>
                <li>123.00</li>
                <li class="cancel">撤销</li>
                <li class="clear"></li>
            </ul>
            <ul class="entrust-content">
                <li>买入</li>
                <li>123.00</li>
                <li>123.00</li>
                <li>123.00</li>
                <li class="cancel">撤销</li>
                <li class="clear"></li>
            </ul>
            <ul class="entrust-content">
                <li>卖出</li>
                <li>123.00</li>
                <li>123.00</li>
                <li>123.00</li>
                <li class="cancel">撤销</li>
                <li class="clear"></li>
            </ul>
        </div>
        </div>
</div>


<!-- 底部tabBar -->
<footer>
    <a href="index.html" class="home">
            <img src="images/home-nochose.png"  class="home-icon"/>
            <p>首页</p>
    </a>
    <a class="deal open">
            <img src="images/deal-chose.png"  class="deal-icon"/>
            <p class="chose">交易</p>
    </a>
    <a href="mine.html" class="mine">
            <img src="images/mine-nochose.png"  class="mine-icon"/>
            <p>我的</p>
    </a>
</footer>

<!-- 选择币种 -->
<div class="choseBz">
    <div class="choseBzBox">
        <div class="closeAnthoer closeBox"></div>
        <div class="choseBzBox-title">
            <p>选择币种</p>
            <img src="images/header-close.png" class="closeBox"/>
            <div class="clear"></div>
        </div>
        <div class="choseBzBox-content">
            <ul>
                <li>
                    <p>盛源链</p>
                    <p class="zhang">92.0000</p>
                    <p class="zhang">+10%</p>
                </li>
                <li>
                    <p>盛源链</p>
                    <p class="die">92.0000</p>
                    <p class="die">-10%</p>
                </li>
                <li>
                    <p>盛源链</p>
                    <p class="die">92.0000</p>
                    <p class="die">-10%</p>
                </li>
                <li>
                    <p>盛源链</p>
                    <p class="zhang">92.0000</p>
                    <p class="zhang">+10%</p>
                </li>
                <li>
                    <p>盛源链</p>
                    <p class="die">92.0000</p>
                    <p class="die">+10%</p>
                </li>
                <li>
                    <p>盛源链</p>
                    <p class="zhang">92.0000</p>
                    <p class="zhang">+10%</p>
                </li>
                <li>
                    <p>盛源链</p>
                    <p class="die">92.0000</p>
                    <p class="die">+10%</p>
                </li>
            </ul>
        </div>
    </div>
</div>

<script src="js/common.js"></script>
<script src="js/jquery-2.1.4.min.js"></script>
<script src="js/deal.js"></script>
</body>
</html>