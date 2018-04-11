<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/resources/page/common/path.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <link rel="icon" href="images/icon.ico" type="image/x-ico" />
    <link rel="icon" href="<%=path %>/resources/image/web/icon.ico" type="image/x-ico" />
    <link rel="stylesheet" type="text/css" href="<%=path %>/resources/css/web/tradeOutSeller.css" />
    <link rel="stylesheet" type="text/css" href="<%=path %>/resources/css/web/public.css" />
    <link rel="stylesheet" type="text/css" href="<%=path %>/resources/css/web/simpleTips.css" />
    <script type="text/javascript" src="http://libs.baidu.com/jquery/2.1.4/jquery.min.js"></script>
    <script type="text/javascript" src="<%=path %>/resources/js/loadPageWeb.js"></script>
    <script type="text/javascript" src="<%=path %>/resources/js/simpleTips.js"></script>
    <script type="text/javascript" src="<%=path %>/resources/js/laydate.js"></script>
    <title>场外交易记录-经销商</title>
</head>
<body>
<header id="header"></header>
<div id="menu"></div>


<div class="content">
    <div id="personalMenu"></div>

    <div class="contentRight">
        <div class="title">场外交易记录</div>

        <div class="main">
            <div class="askArea">
                <p class="condition">申请时间：
                    从&nbsp;<input placeholder="请选择起始时间" class="askTime" onfocus="this.blur()" />
                    到&nbsp;<input placeholder="请选择结束时间" class="askTime" onfocus="this.blur()" />
                </p>
                <p class="condition">用户账号：<input type="text" class="askInput" /></p>
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
                        <option>购买</option>
                        <option>出售</option>
                    </select>
                </p>
                <p class="condition">交易状态：
                    <select class="askSelect">
                        <option>全部</option>
                        <option>待完成</option>
                        <option>待确认</option>
                        <option>已完成</option>
                    </select>
                </p>
                <p class="condition">转账方式：
                    <select class="askSelect">
                        <option>全部</option>
                        <option>银行卡转账</option>
                        <option>微信转账</option>
                        <option>支付宝转账</option>
                    </select>
                </p>

                <input type="text" value="查&nbsp;询" class="ask" onfocus="this.blur()" />
            </div>

            <table class="table" cellpadding="0" cellspacing="0">
                <tr class="tableTitle">
                    <td class="apply">申请信息</td>
                    <td class="userInfo">用户信息</td>
                    <td class="userInfo">我的信息</td>
                    <td class="state">交易状态</td>
                    <td class="operate">操作</td>
                </tr>
                <tr class="tableInfo">
                    <c:forEach items="${otcTransactionUserDealList}" var="userDeal">
                        <td class="apply">
                            <p>流水号：<span>${userDeal.otcOrderNo}</span></p>
                            <p>币种：<span>${userDeal.currencyName}</span></p>
                            <p>数量：<span>${userDeal.currencyNumber}</span></p>
                            <p>金额：<span>¥${userDeal.currencyTotalPrice}</span></p>
                            <p>类型：<span class="buy">${userDeal.}</span></p>
                            <p>地区：<span>${userDeal.}</span></p>
                            <p>申请时间：<span>${userDeal.}</span></p>
                        </td>
                    </c:forEach>

                    <td class="apply">
                        <p>流水号：<span>12345678901</span></p>
                        <p>币种：<span>盛源链</span></p>
                        <p>数量：<span>1.000</span></p>
                        <p>金额：<span>¥10000.0000</span></p>
                        <p>类型：<span class="buy">购买</span></p>
                        <p>地区：<span>中国(CN)</span></p>
                        <p>申请时间：<span>2016-06-06&nbsp;06:06:06</span></p>
                    </td>
                    <td class="seller">
                        <p>用户账号：<span>ASDFGHJKLASDFGHJ</span></p>
                        <p>用户手机号：<span>12345678901</span></p>
                        <p>转账方式：<span>支付宝转账</span></p>
                    </td>
                    <td class="my">
                        <p>支付宝账号：1234****1234</p>
                    </td>
                    <td class="state">
                        <p>状态：<span class="wait">待完成</span></p>
                        <p>完成时间：<span></span></p>
                    </td>
                    <td class="operate">
                        <input type="text" value="确认收款" class="confirm_money" onfocus="this.blur()" />
                    </td>
                </tr>
                <tr class="tableInfo">
                    <td class="apply">
                        <p>流水号：<span>12345678901</span></p>
                        <p>币种：<span>盛源链</span></p>
                        <p>数量：<span>1.000</span></p>
                        <p>金额：<span>¥10000.0000</span></p>
                        <p>类型：<span class="sale">出售</span></p>
                        <p>地区：<span>中国(CN)</span></p>
                        <p>申请时间：<span>2016-06-06&nbsp;06:06:06</span></p>
                    </td>
                    <td class="seller">
                        <p>用户账号：<span>ASDFGHJKLASDFGHJ</span></p>
                        <p>用户手机号：<span>12345678901</span></p>
                        <p>转账方式：<span>支付宝转账</span></p>
                        <p><img src="images/test/test_300.jpg" class="code" /></p>
                    </td>
                    <td class="my"></td>
                    <td class="state">
                        <p>状态：<span class="wait_confirm">待确认</span></p>
                        <p>完成时间：<span></span></p>
                    </td>
                    <td class="operate">
                        <input type="text" value="确认收货" class="confirm_coin" onfocus="this.blur()" />
                    </td>
                </tr>
                <tr class="tableInfo">
                    <td class="apply">
                        <p>流水号：<span>12345678901</span></p>
                        <p>币种：<span>盛源链</span></p>
                        <p>数量：<span>1.000</span></p>
                        <p>金额：<span>¥10000.0000</span></p>
                        <p>类型：<span class="buy">购买</span></p>
                        <p>地区：<span>中国(CN)</span></p>
                        <p>申请时间：<span>2016-06-06&nbsp;06:06:06</span></p>
                    </td>
                    <td class="seller">
                        <p>用户账号：<span>ASDFGHJKLASDFGHJ</span></p>
                        <p>用户手机号：<span>12345678901</span></p>
                        <p>转账方式：<span>微信转账</span></p>
                    </td>
                    <td class="my">  <p>微信账号：1234****1234</p></td>
                    <td class="state">
                        <p>状态：<span class="finish">已完成</span></p>
                        <p>完成时间：<span>2016-06-06&nbsp;06:06:06</span></p>
                    </td>
                    <td class="operate"></td>
                </tr>
                <tr class="tableInfo">
                    <td class="apply">
                        <p>流水号：<span>12345678901</span></p>
                        <p>币种：<span>盛源链</span></p>
                        <p>数量：<span>1.000</span></p>
                        <p>金额：<span>¥10000.0000</span></p>
                        <p>类型：<span class="sale">出售</span></p>
                        <p>地区：<span>中国(CN)</span></p>
                        <p>申请时间：<span>2016-06-06&nbsp;06:06:06</span></p>
                    </td>
                    <td class="seller">
                        <p>用户账号：<span>ASDFGHJKLASDFGHJ</span></p>
                        <p>用户手机号：<span>12345678900</span></p>
                        <p>转账方式：<span>银行卡转账</span></p>
                        <p>银行卡号：<span>12341231231</span></p>
                        <p>银行：<span>中国工商银行浙江省XXXXXX支行</span></p>
                        <p>银行预留电话：<span>12345678900</span></p>
                        <p>收款人：<span>张三</span></p>
                    </td>
                    <td class="my"></td>
                    <td class="state">
                        <p>状态：<span class="finish">已完成</span></p>
                        <p>完成时间：<span>2016-06-06&nbsp;06:06:06</span></p>
                    </td>
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
        <div class="coin_pop">
            <p class="popTitle">确认收货</p>
            <p class="popTips"><img src="images/tips.png" class="tipsImg" />确认已收到商品？</p>

            <div class="buttons">
                <input type="text" value="取&nbsp;消" class="cancel" onfocus="this.blur()" />
                <input type="text" value="确&nbsp;定" class="yes" onfocus="this.blur()" onclick="openTip()" />
            </div>
        </div>

        <div class="money_pop">
            <p class="popTitle">确认收款</p>
            <p class="popTips"><img src="images/tips.png" class="tipsImg" />确认已收到货款？</p>

            <div class="buttons">
                <input type="text" value="取&nbsp;消" class="cancel" onfocus="this.blur()" />
                <input type="text" value="确&nbsp;定" class="yes" onfocus="this.blur()" onclick="openTip()" />
            </div>
        </div>
    </div>
</div>



<script type="text/javascript">
    lay('.askTime').each(function(){
        laydate.render({
            elem: this,
            trigger: 'click',
            type:'datetime',
            theme: '#e83a33'
        });
    });//日期控件

    var popObj;
    $(function(){
        $(".confirm_coin").click(function(){
            $(".mask").fadeIn();
            $(".coin_pop").fadeIn();
            popObj = ".coin_pop"
        });
        $(".confirm_money").click(function(){
            $(".mask").fadeIn();
            $(".money_pop").fadeIn();
            popObj = ".money_pop"
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