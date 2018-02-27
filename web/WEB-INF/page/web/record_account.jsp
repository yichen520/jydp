<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <link rel="icon" href="images/icon.ico" type="image/x-ico" />

    <link rel="stylesheet" type="text/css" href="css/record_account.css" />
    <link rel="stylesheet" type="text/css" href="css/public.css" />
    <link rel="stylesheet" type="text/css" href="css/simpleTips.css" />

    <title>账户记录</title>
</head>
<body>
<header id="header"></header>
<div id="menu"></div>


<div class="content">
    <div id="personalMenu"></div>

    <div class="contentRight">
        <div class="title">账户记录</div>

        <div class="main">
            <table class="table" cellpadding="0" cellspacing="0">
                <tr class="tableTitle">
                    <td class="time">记录时间</td>
                    <td class="coin">币种</td>
                    <td class="source">记录来源</td>
                    <td class="amount">币种资产</td>
                    <td class="amount">冻结资产</td>
                    <td class="mark">备注</td>
                </tr>
                <tr class="tableInfo">
                    <td class="time">2016-06-06&nbsp;06:06:06</td>
                    <td class="coin">盛源链</td>
                    <td class="source">挂单卖出</td>
                    <td class="amount pay">-10.0000</td>
                    <td class="amount in">+10.0000</td>
                    <td class="mark">备注备注备注备注备注备注备注</td>
                </tr>
                <tr class="tableInfo">
                    <td class="time">2016-06-06&nbsp;06:06:06</td>
                    <td class="coin">盛源链</td>
                    <td class="source">成交卖出</td>
                    <td class="amount pay">-10.0000</td>
                    <td class="amount"></td>
                    <td class="mark">备注备注备注备注备注备注备注</td>
                </tr>
                <tr class="tableInfo">
                    <td class="time">2016-06-06&nbsp;06:06:06</td>
                    <td class="coin">盛源链</td>
                    <td class="source">成交买入</td>
                    <td class="amount in">+10.0000</td>
                    <td class="amount"></td>
                    <td class="mark">备注备注备注备注备注备注备注</td>
                </tr>
            </table>

            <div class="changePage">
                <p class="total">共21条</p>
                <p class="jump">
                    <input type="text" />
                    <input type="text" value="跳&nbsp;转" class="jumpButton" onfocus="this.blur()" />
                </p>
                <p class="page">
                    <input type="text" class="first" value="首页" onfocus="this.blur()" />
                    <input type="text" class="upPage" value="<上一页" onfocus="this.blur()" />
                    <span class="pageNumber"><span>1</span>/<span>3</span></span>
                    <input type="text" class="downPage" value="下一页>" onfocus="this.blur()" />
                    <input type="text" class="end" value="尾页" onfocus="this.blur()" />
                </p>
            </div>
        </div>
    </div>
</div>


<div id="helpFooter"></div>
<div id="footer"></div>

<script type="text/javascript" src="js/jquery-2.1.4.min.js"></script>
<script type="text/javascript" src="js/public.js"></script>
<script type="text/javascript" src="js/simpleTips.js"></script>

</body>
</html>