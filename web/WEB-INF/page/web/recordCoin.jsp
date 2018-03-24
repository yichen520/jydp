<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <link rel="icon" href="images/icon.ico" type="image/x-ico" />

    <link rel="stylesheet" type="text/css" href="css/record_coin.css" />
    <link rel="stylesheet" type="text/css" href="css/public.css" />
    <link rel="stylesheet" type="text/css" href="css/simpleTips.css" />

    <title>币种提出记录</title>
</head>
<body>
<header id="header"></header>
<div id="menu"></div>


<div class="content">
    <div id="personalMenu"></div>

    <div class="contentRight">
        <div class="title">币种提出记录</div>

        <div class="main">
            <table class="table" cellpadding="0" cellspacing="0">
                <tr class="tableTitle">
                    <td class="time">申请时间</td>
                    <td class="time">申请流水号</td>
                    <td class="coin">币种</td>
                    <td class="amount">数量</td>
                    <td class="state">审核状态</td>
                    <td class="time">完成时间</td>
                    <td class="mark">备注</td>
                    <td class="operate">操作</td>
                </tr>
                <tr class="tableInfo">
                    <td class="time">2016-06-06&nbsp;06:06:06</td>
                    <td class="time">123456789123456</td>
                    <td class="coin">盛源链</td>
                    <td class="amount">123.0000</td>
                    <td class="state wait">待审核</td>
                    <td class="time">2016-06-06&nbsp;06:06:06</td>
                    <td class="mark"></td>
                    <td class="operate">
                        <input type="text" value="撤&nbsp;回" class="recall" onfocus="this.blur()" />
                    </td>
                </tr>
                <tr class="tableInfo">
                    <td class="time">2016-06-06&nbsp;06:06:06</td>
                    <td class="time">123456789123456</td>
                    <td class="coin">盛源链</td>
                    <td class="amount">123.0000</td>
                    <td class="state pass">审核通过</td>
                    <td class="time">2016-06-06&nbsp;06:06:06</td>
                    <td class="mark">备注备注备注备注备注</td>
                    <td class="operate"></td>
                </tr>
                <tr class="tableInfo">
                    <td class="time">2016-06-06&nbsp;06:06:06</td>
                    <td class="time">123456789123456</td>
                    <td class="coin">盛源链</td>
                    <td class="amount">123.0000</td>
                    <td class="state refuse">审核拒绝</td>
                    <td class="time">2016-06-06&nbsp;06:06:06</td>
                    <td class="mark">备注备注备注备注备注</td>
                    <td class="operate"></td>
                </tr>
                <tr class="tableInfo">
                    <td class="time">2016-06-06&nbsp;06:06:06</td>
                    <td class="time">123456789123456</td>
                    <td class="coin">盛源链</td>
                    <td class="amount">123.0000</td>
                    <td class="state">已撤回</td>
                    <td class="time">2016-06-06&nbsp;06:06:06</td>
                    <td class="mark">备注备注备注备注备注</td>
                    <td class="operate"></td>
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


<div class="mask">
    <div class="mask_content">
        <div class="recall_pop">
            <p class="popTitle">撤回操作</p>
            <p class="popTips"><img src="images/tips.png" class="tipsImg" />确定撤回该笔申请？</p>

            <div class="buttons">
                <input type="text" value="取&nbsp;消" class="cancel" onfocus="this.blur()" />
                <input type="text" value="确&nbsp;定" class="yes" onfocus="this.blur()" onclick="openTip()" />
            </div>
        </div>
    </div>
</div>

<script type="text/javascript" src="js/jquery-2.1.4.min.js"></script>
<script type="text/javascript" src="js/public.js"></script>
<script type="text/javascript" src="js/simpleTips.js"></script>

<script type="text/javascript">
    var popObj;
    $(function(){
        $(".recall").click(function(){
            $(".mask").fadeIn();
            $(".recall_pop").fadeIn();
            popObj = ".recall_pop"
        });
        $(".cancel").click(function(){
            $(".mask").fadeOut("fast");
            $(popObj).fadeOut("fast");
        });
        $(".yes").click(function(){
            $(".mask").fadeOut("fast");
            $(popObj).fadeOut("fast");
        });
    });

    function openTip()
    {
        openTips("阿萨德芳");
    }
</script>

</body>
</html>