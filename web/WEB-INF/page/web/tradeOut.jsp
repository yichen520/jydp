<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <link rel="icon" href="images/icon.ico" type="image/x-ico" />

    <link rel="stylesheet" type="text/css" href="css/tradeOut.css" />
    <link rel="stylesheet" type="text/css" href="css/public.css" />
    <link rel="stylesheet" type="text/css" href="css/simpleTips.css" />

    <title>场外交易</title>
</head>
<body>
<header id="header"></header>
<div id="menu"></div>


<div class="content">
    <div class="askArea">
        <p class="condition">币种：
            <select class="askSelect">
                <option>全部</option>
                <option>比特币</option>
                <option>XT</option>
            </select>
        </p>
        <p class="condition">类型：
            <select class="askSelect">
                <option>全部</option>
                <option>待完成</option>
                <option>待确认</option>
                <option>已完成</option>
            </select>
        </p>
        <p class="condition">地区：
            <select class="askSelect">
                <option>全部</option>
                <option>中国(CN)</option>
            </select>
        </p>

        <input type="text" value="查&nbsp;询" class="ask" onfocus="this.blur()" />
    </div>

    <div class="main">
        <table class="table" cellspacing="0" cellpadding="0">
            <tr class="tableTitle">
                <td class="name">经销商名称</td>
                <td class="coin">币种</td>
                <td class="area">地区</td>
                <td class="type">类型</td>
                <td class="limit">交易限额</td>
                <td class="proportion">购买比例</td>
                <td class="operate">操作</td>
            </tr>
            <tr class="tableInfo">
                <td class="name">浙江省经销商</td>
                <td class="coin">XT</td>
                <td class="area">中国(CN)</td>
                <td class="type buy">购买</td>
                <td class="limit"><span>11000</span>~<span>14000</span>CNY</td>
                <td class="proportion">1:100</td>
                <td class="operate"><input type="text" value="我要购买" class="tradeBuy" onfocus="this.blur()" /></td>
            </tr>
            <tr class="tableInfo">
                <td class="name">浙江省经销商</td>
                <td class="coin">XT</td>
                <td class="area">中国(CN)</td>
                <td class="type sale">出售</td>
                <td class="limit"><span>11000</span>~<span>14000</span>CNY</td>
                <td class="proportion">1:100</td>
                <td class="operate"><input type="text" value="我要出售" class="tradeSale" onfocus="this.blur()" /></td>
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


<div id="helpFooter"></div>
<div id="footer"></div>

<div class="mask">
    <div class="mask_content">
        <div class="buy_pop">
            <p class="popTitle">我要购买</p>

            <p class="popInput">
                <label class="popName">购买数量<span class="star">*</span>：</label>
                <input type="text" class="entry" placeholder="您要购买的数量，单位：个" />
                <span class="remind">交易比例为：<span>1：100</span>，<span>XT</span>：兑换货币</span>
            </p>
            <p class="popInput">
                <label class="popName">总价：</label>
                <span class="popMoney">¥1000.000</span>
            </p>
            <p class="popInput">
                <label class="popName">支付方式<span class="star">*</span>：</label>
                <select class="popSelected">
                    <option disabled selected>选择支付方式</option>
                    <option>银行卡转账</option>
                    <option>支付宝转账</option>
                    <option>微信转账</option>
                </select>
            </p>

            <div class="buttons">
                <input type="text" value="取&nbsp;消" class="cancel" onfocus="this.blur()" />
                <input type="text" value="去支付" class="pay" onfocus="this.blur()" />
            </div>
        </div>

        <div class="pay_pop">
            <p class="popTitle">银行卡转账</p>

            <div class="overflow">
                <div class="sellerInfo">
                    <p class="popInfo">
                        <label class="popName">商家名称：</label>
                        <span class="popWord">浙江省经销商</span>
                    </p>
                    <p class="popInfo">
                        <label class="popName">商家手机：</label>
                        <span class="popWord">1234567899</span>
                    </p>
                    <p class="popInfo">
                        <label class="popName">商家姓名：</label>
                        <span class="popWord">张三</span>
                    </p>
                </div>

                <div class="payInfo">
                    <p class="popInfo">
                        <label class="popName">商家银行卡号：</label>
                        <span class="popWord">1234567767687687868</span>
                    </p>
                    <p class="popInfo">
                        <label class="popName">收款银行：</label>
                        <span class="popWord">中国工商银行XXXXXXX支行</span>
                    </p>
                    <p class="popInfo">
                        <label class="popName">收款人姓名：</label>
                        <span class="popWord">张三</span>
                    </p>
                    <p class="popInfo">
                        <label class="popName">收款人手机号：</label>
                        <span class="popWord">12345678701</span>
                    </p>
                </div>

                <div class="moneyInfo">
                    <p class="popInfo">
                        <label class="popName">购买数量：</label>
                        <span class="popWord">1.00000</span>
                    </p>
                    <p class="popInfo">
                        <label class="popName">支付金额：</label>
                        <span class="popWord">¥1000.00000</span>
                    </p>
                </div>

                <div class="prompt">提示：请及时保存转账信息。若支付完成商家在24小时内未即时转账，请咨询客服中心处理。</div>
            </div>

            <div class="buttons">
                <input type="text" value="取&nbsp;消" class="cancel" onfocus="this.blur()" />
                <input type="text" value="确&nbsp;定" class="yes" onfocus="this.blur()" />
            </div>
        </div>

        <div class="pay_pop">
            <p class="popTitle">支付宝转账</p>

            <div class="overflow">
                <div class="sellerInfo">
                    <p class="popInfo">
                        <label class="popName">商家名称：</label>
                        <span class="popWord">浙江省经销商</span>
                    </p>
                    <p class="popInfo">
                        <label class="popName">商家手机：</label>
                        <span class="popWord">1234567899</span>
                    </p>
                    <p class="popInfo">
                        <label class="popName">商家姓名：</label>
                        <span class="popWord">张三</span>
                    </p>
                </div>

                <div class="payInfo">
                    <p class="popInfo">
                        <label class="popName">商家支付宝：</label>
                        <span class="popWord">1234567767687687868</span>
                    </p>
                    <p class="popInfo">
                        <label class="popName">二维码：</label>
                        <span class="popWord"><img src="images/test/test_300.jpg" class="code" /></span>
                    </p>
                </div>

                <div class="moneyInfo">
                    <p class="popInfo">
                        <label class="popName">购买数量：</label>
                        <span class="popWord">1.00000</span>
                    </p>
                    <p class="popInfo">
                        <label class="popName">支付金额：</label>
                        <span class="popWord">¥1000.00000</span>
                    </p>
                </div>

                <div class="prompt">提示：请及时保存转账信息。若支付完成商家在24小时内未即时转账，请咨询客服中心处理。</div>
            </div>


            <div class="buttons">
                <input type="text" value="取&nbsp;消" class="cancel" onfocus="this.blur()" />
                <input type="text" value="确&nbsp;定" class="yes" onfocus="this.blur()" />
            </div>
        </div>

        <div class="pay_pop">
            <p class="popTitle">微信转账</p>

            <div class="overflow">
                <div class="sellerInfo">
                    <p class="popInfo">
                        <label class="popName">商家名称：</label>
                        <span class="popWord">浙江省经销商</span>
                    </p>
                    <p class="popInfo">
                        <label class="popName">商家手机：</label>
                        <span class="popWord">1234567899</span>
                    </p>
                    <p class="popInfo">
                        <label class="popName">商家姓名：</label>
                        <span class="popWord">张三</span>
                    </p>
                </div>

                <div class="payInfo">
                    <p class="popInfo">
                        <label class="popName">商家微信：</label>
                        <span class="popWord">1234567767687687868</span>
                    </p>
                    <p class="popInfo">
                        <label class="popName">二维码：</label>
                        <span class="popWord"><img src="images/test/test_300.jpg" class="code" /></span>
                    </p>
                </div>

                <div class="moneyInfo">
                    <p class="popInfo">
                        <label class="popName">购买数量：</label>
                        <span class="popWord">1.00000</span>
                    </p>
                    <p class="popInfo">
                        <label class="popName">支付金额：</label>
                        <span class="popWord">¥1000.00000</span>
                    </p>
                </div>

                <div class="prompt">提示：请及时保存转账信息。若支付完成商家在24小时内未即时转账，请咨询客服中心处理。</div>
            </div>


            <div class="buttons">
                <input type="text" value="取&nbsp;消" class="cancel" onfocus="this.blur()" />
                <input type="text" value="确&nbsp;定" class="yes" onfocus="this.blur()" />
            </div>
        </div>

        <div class="sell_pop">
            <p class="popTitle">我要出售</p>

            <div class="overflow">
                <p class="popInput">
                    <label class="popName">经销商名称：</label>
                    <span class="popWord">浙江省经销商</span>
                </p>
                <p class="popInput">
                    <label class="popName">出售数量<span class="star">*</span>：</label>
                    <input type="text" class="entry" placeholder="您要出售的数量，单位：个" />
                    <span class="remind">交易比例为：<span>1：100</span>，<span>XT</span>：兑换货币</span>
                </p>
                <p class="popInput">
                    <label class="popName">总价：</label>
                    <span class="popMoney">¥1000.000</span>
                </p>

                <p class="popInput">
                    <label class="popName">收款方式<span class="star">*</span>：</label>
                    <select class="popSelected" onchange="change(this.value)">
                        <option disabled selected>选择收款方式</option>
                        <option value="0">银行卡转账</option>
                        <option value="1">支付宝转账</option>
                        <option value="2">微信转账</option>
                    </select>
                </p>

                <div class="card">
                    <p class="popInput">
                        <label class="popName">银行卡号<span class="star">*</span>：</label>
                        <input type="text" class="entry" placeholder="您的银行卡号" />
                    </p>
                    <p class="popInput">
                        <label class="popName">银行名称<span class="star">*</span>：</label>
                        <input type="text" class="entry" placeholder="该银行卡的银行名称" />
                    </p>
                    <p class="popInput">
                        <label class="popName">支行名称<span class="star">*</span>：</label>
                        <input type="text" class="entry" placeholder="该卡的支行名称" />
                    </p>
                    <p class="popInput">
                        <label class="popName">预留姓名<span class="star">*</span>：</label>
                        <input type="text" class="entry" placeholder="该银行卡的银行预留姓名" />
                    </p>
                    <p class="popInput">
                        <label class="popName">预留电话<span class="star">*</span>：</label>
                        <input type="text" class="entry" placeholder="该银行卡的银行预留电话" />
                    </p>
                </div>

                <div class="ali">
                    <p class="popInput">
                        <label class="popName">支付宝账号<span class="star">*</span>：</label>
                        <input type="text" class="entry" placeholder="您的支付宝账号" />
                    </p>
                    <p class="popInput">
                        <label class="popName">收款码<span class="star">*</span>：</label>
                        <span class="pic">
                        <input type="text" id="changead_t1"  class="choosePic" placeholder="请选择二维码图片" onfocus="this.blur()" />
                        <input type="text"  onclick="document.getElementById('changead_a1').click();"  value="选择文件" class="choose_button" onfocus="this.blur();" />
                        <input type="file" class="file" id="changead_a1" onchange="document.getElementById('changead_t1').value = this.value;" />
                    </span>
                    </p>
                </div>

                <div class="wechat">
                    <p class="popInput">
                        <label class="popName">微信账号<span class="star">*</span>：</label>
                        <input type="text" class="entry" placeholder="您的微信账号" />
                    </p>
                    <p class="popInput">
                        <label class="popName">收款码<span class="star">*</span>：</label>
                        <span class="pic">
                            <input type="text" id="changead_t2"  class="choosePic" placeholder="请选择二维码图片" onfocus="this.blur()" />
                            <input type="text"  onclick="document.getElementById('changead_a2').click();"  value="选择文件" class="choose_button" onfocus="this.blur();" />
                            <input type="file" class="file" id="changead_a2" onchange="document.getElementById('changead_t2').value = this.value;" />
                        </span>
                    </p>
                </div>
            </div>

            <div class="buttons">
                <input type="text" value="取&nbsp;消" class="cancel" onfocus="this.blur()" />
                <input type="text" value="确&nbsp;定" class="confirm" onfocus="this.blur()" />
            </div>
        </div>

        <div class="confirm_pop">
            <p class="popTitle">出售确认</p>

            <p class="popInfo">
                <label class="popName">经销商名称：</label>
                <span class="popWord">浙江省经销商</span>
            </p>
            <p class="popInfo">
                <label class="popName">出售数量：</label>
                <span class="popWord">1.0000</span>
            </p>
            <p class="popInfo">
                <label class="popName">总价：</label>
                <span class="popWord">¥12.000</span>
            </p>
            <p class="popInfo">
                <label class="popName">收款方式：</label>
                <span class="popWord">支付宝转账</span>
            </p>
            <p class="popInfo">
                <label class="popName"><span>支付宝</span>账号：</label>
                <span class="popWord">qweqeqwewqwq</span>
            </p>

            <div class="buttons">
                <input type="text" value="取&nbsp;消" class="cancel" onfocus="this.blur()" />
                <input type="text" value="确&nbsp;定" class="yes" onfocus="this.blur()" />
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
        $(".tradeBuy").click(function(){
            $(".mask").fadeIn();
            $(".buy_pop").fadeIn();
            popObj = ".buy_pop"
        });
        $(".pay").click(function(){
            $(".mask").fadeIn();
            $(".buy_pop").hide();
            $(".pay_pop").fadeIn();
            popObj = ".pay_pop"
        });
        $(".tradeSale").click(function(){
            $(".mask").fadeIn();
            $(".sell_pop").fadeIn();
            popObj = ".sell_pop"
        });
        $(".confirm").click(function(){
            $(".mask").fadeIn();
            $(".sell_pop").hide();
            $(".confirm_pop").fadeIn();
            popObj = ".confirm_pop"
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

    function change(valueNum)
    {
        if(valueNum == 0)
        {
            $(".card").css("display" , "inline-block");
            $(".ali").hide();
            $(".wechat").hide();
        }
        if(valueNum == 1)
        {
            $(".card").hide();
            $(".ali").css("display" , "inline-block");
            $(".wechat").hide();
        }
        if(valueNum == 2)
        {
            $(".card").hide();
            $(".ali").hide();
            $(".wechat").css("display" , "inline-block");
        }
    }

    function openTip()
    {
        openTips("阿萨德芳");
    }
</script>

</body>
</html>