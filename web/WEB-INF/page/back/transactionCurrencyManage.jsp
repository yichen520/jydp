<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/resources/page/common/path.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">

    <link rel="icon" href="<%=path %>/resources/image/back/icon.ico" type="image/x-ico" />

    <link rel="stylesheet" type="text/css" href="<%=path %>/resources/css/back/coinManage.css" />
    <link rel="stylesheet" type="text/css" href="<%=path %>/resources/css/back/public.css" />
    <link rel="stylesheet" type="text/css" href="<%=path %>/resources/css/back/simpleTips.css" />

    <title>币种管理</title>
</head>
<body>
<div id="header"></div>


<div class="content">
    <div id="menu"></div>

    <div class="contentRight">
        <div class="caption">
            <span class="title">币种管理</span>
        </div>

        <div class="top">
            <c:if test="${backer_rolePower['104002'] == 104002}">
                <p class="add">新增币种</p>
            </c:if>

            <div class="askArea">
                <p class="condition">币种名称：
                    <select class="askSelect" id="currencyId" name="currencyName">
                        <option value="">全部</option>
                        <c:forEach items="${transactionCurrencyList}" var="item">
                            <option value="${item.currencyName}">${item.currencyName}</option>
                        </c:forEach>
                    </select>
                </p>
                <p class="condition">交易状态：
                    <select class="askSelect">
                        <option>全部</option>
                        <option>正常</option>
                        <option>涨停</option>
                        <option>跌停</option>
                        <option>停牌</option>
                    </select>
                </p>
                <p class="condition">上线状态：
                    <select class="askSelect">
                        <option>全部</option>
                        <option>待上线</option>
                        <option>上线中</option>
                        <option>已下线</option>
                        <option>禁用</option>
                    </select>
                </p>
                <p class="condition">管理员账号：<input type="text" class="askInput" /></p>
                <p class="condition">
                    添加时间：
                    从&nbsp;<input placeholder="请选择起始时间" class="askTime" />
                    到&nbsp;<input placeholder="请选择结束时间" class="askTime"/>
                </p>
                <p class="condition">
                    上线时间：
                    从&nbsp;<input placeholder="请选择起始时间" class="askTime" />
                    到&nbsp;<input placeholder="请选择结束时间" class="askTime" />
                </p>

                <input type="text" value="查&nbsp;询" class="ask" onfocus="this.blur()" />
                <input type="text" value="导&nbsp;出" class="educe" onfocus="this.blur()" />
            </div>
        </div>

        <div class="bottom">
            <table class="table" cellspacing="0" cellpadding="0">
                <tr class="tableTitle">
                    <td class="time">添加时间</td>
                    <td class="coin">币种信息</td>
                    <td class="coinLogo">币种徽标</td>
                    <td class="service">手续费</td>
                    <td class="range">涨跌幅度</td>
                    <td class="state">交易状态</td>
                    <td class="time">上线时间</td>
                    <td class="state">上线状态</td>
                    <td class="account">管理员信息</td>
                    <td class="operate">操作</td>
                </tr>
                <tr class="tableInfo">
                    <td class="time">2016-06-06&nbsp;06:06:06</td>
                    <td class="coin">
                        <p>名称：盛源链</p>
                        <p>英文标识：MUC</p>
                        <p>交易开始时间：06:06:06</p>
                        <p>交易结束时间：06:06:06</p>
                    </td>
                    <td class="coinLogo"><img src="images/test/test_300.jpg" /></td>
                    <td class="service">
                        <p>买入：10%</p>
                        <p>卖出：10%</p>
                    </td>
                    <td class="range">
                        <p>涨停：10%</p>
                        <p>跌停：10%</p>
                    </td>
                    <td class="state">正常</td>
                    <td class="time">2016-06-06&nbsp;06:06:06</td>
                    <td class="state">上线中</td>
                    <td class="account">
                        <p>管理员账号：ASDFGHJKLASDFGHJ</p>
                        <p>IP:192.365.3.321</p>
                    </td>
                    <td class="operate">
                        <input type="text" value="禁&nbsp;用" class="stop" onfocus="this.blur()" />
                        <input type="text" value="下&nbsp;线" class="offLine" onfocus="this.blur()" />
                        <input type="text" value="修&nbsp;改" class="change" onfocus="this.blur()" />
                    </td>
                </tr>
                <tr class="tableInfo">
                    <td class="time">2016-06-06&nbsp;06:06:06</td>
                    <td class="coin">
                        <p>名称：盛源链</p>
                        <p>英文标识：MUC</p>
                        <p>交易开始时间：06:06:06</p>
                        <p>交易结束时间：06:06:06</p>
                    </td>
                    <td class="coinLogo"><img src="images/test/test_300.jpg" /></td>
                    <td class="service">
                        <p>买入：10%</p>
                        <p>卖出：10%</p>
                    </td>
                    <td class="range">
                        <p>涨停：10%</p>
                        <p>跌停：10%</p>
                    </td>
                    <td class="state">停牌</td>
                    <td class="time">2016-06-06&nbsp;06:06:06</td>
                    <td class="state">禁用</td>
                    <td class="account">
                        <p>管理员账号：ASDFGHJKLASDFGHJ</p>
                        <p>IP:192.365.3.321</p>
                    </td>
                    <td class="operate">
                        <input type="text" value="启&nbsp;用" class="start" onfocus="this.blur()" />
                        <input type="text" value="下&nbsp;线" class="offLine" onfocus="this.blur()" />
                        <input type="text" value="修&nbsp;改" class="change" onfocus="this.blur()" />
                    </td>
                </tr>
                <tr class="tableInfo">
                    <td class="time">2016-06-06&nbsp;06:06:06</td>
                    <td class="coin">
                        <p>名称：盛源链</p>
                        <p>英文标识：MUC</p>
                        <p>交易开始时间：06:06:06</p>
                        <p>交易结束时间：06:06:06</p>
                    </td>
                    <td class="coinLogo"><img src="images/test/test_300.jpg" /></td>
                    <td class="service">
                        <p>买入：10%</p>
                        <p>卖出：10%</p>
                    </td>
                    <td class="range">
                        <p>涨停：10%</p>
                        <p>跌停：10%</p>
                    </td>
                    <td class="state"></td>
                    <td class="time">2016-06-06&nbsp;06:06:06</td>
                    <td class="state">已下线</td>
                    <td class="account">
                        <p>管理员账号：ASDFGHJKLASDFGHJ</p>
                        <p>IP:192.365.3.321</p>
                    </td>
                    <td class="operate">
                        <input type="text" value="上&nbsp;线" class="online" onfocus="this.blur()" />
                        <input type="text" value="修&nbsp;改" class="change" onfocus="this.blur()" />
                    </td>
                </tr>
                <tr class="tableInfo">
                    <td class="time">2016-06-06&nbsp;06:06:06</td>
                    <td class="coin">
                        <p>名称：盛源链</p>
                        <p>英文标识：MUC</p>
                        <p>交易开始时间：06:06:06</p>
                        <p>交易结束时间：06:06:06</p>
                    </td>
                    <td class="coinLogo"><img src="images/test/test_300.jpg" /></td>
                    <td class="service">
                        <p>买入：10%</p>
                        <p>卖出：10%</p>
                    </td>
                    <td class="range">
                        <p>涨停：10%</p>
                        <p>跌停：10%</p>
                    </td>
                    <td class="state">正常</td>
                    <td class="time">2016-06-06&nbsp;06:06:06</td>
                    <td class="state">待上线</td>
                    <td class="account">
                        <p>管理员账号：ASDFGHJKLASDFGHJ</p>
                        <p>IP:192.365.3.321</p>
                    </td>
                    <td class="operate">
                        <input type="text" value="上&nbsp;线" class="online" onfocus="this.blur()" />
                        <input type="text" value="下&nbsp;线" class="offLine" onfocus="this.blur()" />
                        <input type="text" value="修&nbsp;改" class="change" onfocus="this.blur()" />
                    </td>
                </tr>
                <tr class="tableInfo">
                    <td class="time">2016-06-06&nbsp;06:06:06</td>
                    <td class="coin">
                        <p>名称：盛源链</p>
                        <p>英文标识：MUC</p>
                        <p>交易开始时间：06:06:06</p>
                        <p>交易结束时间：06:06:06</p>
                    </td>
                    <td class="coinLogo"><img src="images/test/test_300.jpg" /></td>
                    <td class="service">
                        <p>买入：10%</p>
                        <p>卖出：10%</p>
                    </td>
                    <td class="range">
                        <p>涨停：10%</p>
                        <p>跌停：10%</p>
                    </td>
                    <td class="state">涨停</td>
                    <td class="time">2016-06-06&nbsp;06:06:06</td>
                    <td class="state">上线中</td>
                    <td class="account">
                        <p>管理员账号：ASDFGHJKLASDFGHJ</p>
                        <p>IP:192.365.3.321</p>
                    </td>
                    <td class="operate">
                        <input type="text" value="禁&nbsp;用" class="stop" onfocus="this.blur()" />
                        <input type="text" value="下&nbsp;线" class="offLine" onfocus="this.blur()" />
                        <input type="text" value="修&nbsp;改" class="change" onfocus="this.blur()" />
                    </td>
                </tr>
                <tr class="tableInfo">
                    <td class="time">2016-06-06&nbsp;06:06:06</td>
                    <td class="coin">
                        <p>名称：盛源链</p>
                        <p>英文标识：MUC</p>
                        <p>交易开始时间：06:06:06</p>
                        <p>交易结束时间：06:06:06</p>
                    </td>
                    <td class="coinLogo"><img src="images/test/test_300.jpg" /></td>
                    <td class="service">
                        <p>买入：10%</p>
                        <p>卖出：10%</p>
                    </td>
                    <td class="range">
                        <p>涨停：10%</p>
                        <p>跌停：10%</p>
                    </td>
                    <td class="state">跌停</td>
                    <td class="time">2016-06-06&nbsp;06:06:06</td>
                    <td class="state">上线中</td>
                    <td class="account">
                        <p>管理员账号：ASDFGHJKLASDFGHJ</p>
                        <p>IP:192.365.3.321</p>
                    </td>
                    <td class="operate">
                        <input type="text" value="禁&nbsp;用" class="stop" onfocus="this.blur()" />
                        <input type="text" value="下&nbsp;线" class="offLine" onfocus="this.blur()" />
                        <input type="text" value="修&nbsp;改" class="change" onfocus="this.blur()" />
                    </td>
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


<div id="footer"></div>


<div class="mask">
    <div class="mask_content">
        <div class="start_pop">
            <p class="popTitle">启用操作</p>
            <p class="popTips"><img src="images/tips.png" class="tipsImg" />确定启用该币种？</p>

            <div class="buttons">
                <input type="text" value="取&nbsp;消" class="cancel" onfocus="this.blur()" />
                <input type="text" value="确&nbsp;定" class="yes" onfocus="this.blur()" onclick="openTip()" />
            </div>
        </div>

        <div class="stop_pop">
            <p class="popTitle">禁用操作</p>
            <p class="popTips"><img src="images/tips.png" class="tipsImg" />确定禁用该币种？</p>

            <div class="buttons">
                <input type="text" value="取&nbsp;消" class="cancel" onfocus="this.blur()" />
                <input type="text" value="确&nbsp;定" class="yes" onfocus="this.blur()" onclick="openTip()" />
            </div>
        </div>

        <div class="online_pop">
            <p class="popTitle">上线操作</p>
            <p class="popTips"><img src="images/tips.png" class="tipsImg" />确定立即上线该币种？</p>

            <div class="buttons">
                <input type="text" value="取&nbsp;消" class="cancel" onfocus="this.blur()" />
                <input type="text" value="确&nbsp;定" class="yes" onfocus="this.blur()" onclick="openTip()" />
            </div>
        </div>

        <div class="offLine_pop">
            <p class="popTitle">下线操作</p>
            <p class="popTips"><img src="images/tips.png" class="tipsImg" />确定下线该币种？</p>

            <div class="buttons">
                <input type="text" value="取&nbsp;消" class="cancel" onfocus="this.blur()" />
                <input type="text" value="确&nbsp;定" class="yes" onfocus="this.blur()" onclick="openTip()" />
            </div>
        </div>

        <div class="add_pop">
            <p class="popTitle">新增币种</p>
            <div class="popMain">
                <p class="popInput">
                    <label class="popName">币种名称<span class="star">*</span></label>
                    <input type="text" class="entry" placeholder="币种名称" />
                </p>
                <p class="popInput">
                    <label class="popName">英文标识<span class="star">*</span></label>
                    <input type="text" class="entry" placeholder="币种的英文缩写标识" />
                </p>
                <p class="popInput">
                    <label class="popName">币种徽标<span class="star">*</span></label>

                    <span class="popPic">
                        <input type="text" id="changead_t1"  class="choosePic" placeholder="请选择文件" onfocus="this.blur()" />
                        <input type="text"  onclick="document.getElementById('changead_a1').click();"  value="选择文件" class="choose_button" onfocus="this.blur();" />
                        <input type="file" class="file" id="changead_a1" onchange="document.getElementById('changead_t1').value = this.value;" />
                    </span>
                </p>
                <p class="popInput">
                    <label class="popName">交易开始时间<span class="star">*</span></label>
                    <input placeholder="请选择交易开始时间" class="entry tradeTime" />
                </p>
                <p class="popInput">
                    <label class="popName">交易结束时间<span class="star">*</span></label>
                    <input placeholder="请选择交易结束时间" class="entry tradeTime" />
                </p>
                <p class="popInput">
                    <label class="popName">买入手续费<span class="star">*</span></label>
                    <input type="text" class=" percentage" placeholder="买入手续费，无手续费则填“0”" />%
                </p>
                <p class="popInput">
                    <label class="popName">买入手续费<span class="star">*</span></label>
                    <input type="text" class=" percentage" placeholder="买入手续费，无手续费则填“0”" />%
                </p>
                <p class="popInput">
                    <label class="popName">卖出手续费<span class="star">*</span></label>
                    <input type="text" class="percentage" placeholder="卖出手续费，无手续费则填“0”" />%
                </p>
                <p class="popInput">
                    <label class="popName">涨停幅度<span class="star">*</span></label>
                    <input type="text" class="percentage" placeholder="涨停幅度，无限制则填“0”" />%
                </p>
                <p class="popInput">
                    <label class="popName">跌停幅度<span class="star">*</span></label>
                    <input type="text" class="percentage" placeholder="跌停幅度，无限制则填“0”" />%
                </p>
                <p class="popInput">
                    <label class="popName">上线时间<span class="star">*</span></label>
                    <label class="immediately">
                        <input type="radio" name="radioBtn" class="radioBtn" />立即上线
                    </label>
                    <label class="timeManage">
                        <input type="radio" name="radioBtn" class="radioBtn" />
                        <input placeholder="请选择上线时间" class="askTime chooseTime" />
                    </label>

                </p>
            </div>

            <div class="buttons">
                <input type="text" value="取&nbsp;消" class="cancel" onfocus="this.blur()" />
                <input type="text" value="确&nbsp;定" class="yes" onfocus="this.blur()" onclick="openTip()" />
            </div>
        </div>

        <div class="change_pop">
            <p class="popTitle">修改币种</p>
            <div class="popMain">
                <p class="popInput">
                    <label class="popName">币种名称<span class="star">*</span></label>
                    <input type="text" class="entry" placeholder="币种名称" />
                </p>
                <p class="popInput">
                    <label class="popName">英文标识<span class="star">*</span></label>
                    <input type="text" class="entry" placeholder="币种的英文缩写标识" />
                </p>
                <p class="popInput">
                    <label class="popName">币种徽标</label>

                    <span class="popPic">
                        <input type="text" id="changead_t2"  class="choosePic" placeholder="该项不修改时，请勿上传" onfocus="this.blur()" />
                        <input type="text"  onclick="document.getElementById('changead_a2').click();"  value="选择文件" class="choose_button" onfocus="this.blur();" />
                        <input type="file" class="file" id="changead_a2" onchange="document.getElementById('changead_t2').value = this.value;" />
                    </span>
                </p>
                <p class="popInput">
                    <label class="popName">交易开始时间<span class="star">*</span></label>
                    <input placeholder="请选择交易开始时间" class="entry tradeTime" />
                </p>
                <p class="popInput">
                    <label class="popName">交易结束时间<span class="star">*</span></label>
                    <input placeholder="请选择交易结束时间" class="entry tradeTime" />
                </p>
                <p class="popInput">
                    <label class="popName">买入手续费<span class="star">*</span></label>
                    <input type="text" class=" percentage" placeholder="买入手续费，无手续费则填“0”" />%
                </p>
                <p class="popInput">
                    <label class="popName">卖出手续费<span class="star">*</span></label>
                    <input type="text" class="percentage" placeholder="卖出手续费，无手续费则填“0”" />%
                </p>
                <p class="popInput">
                    <label class="popName">涨停幅度<span class="star">*</span></label>
                    <input type="text" class="percentage" placeholder="涨停幅度，无限制则填“0”" />%
                </p>
                <p class="popInput">
                    <label class="popName">跌停幅度<span class="star">*</span></label>
                    <input type="text" class="percentage" placeholder="跌停幅度，无限制则填“0”" />%
                </p>
                <p class="popInput">
                    <label class="popName">上线时间<span class="star">*</span></label>
                    <input placeholder="请选择上线时间" class="askTime entry" id="c_onlineTime" />
                </p>
            </div>

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
<script type="text/javascript" src="js/laydate.js"></script>

<script type="text/javascript">
    lay('.askTime').each(function(){
        laydate.render({
            elem: this,
            trigger: 'click',
            type:'datetime',
            theme: '#69c0ff'
        });
    });
    lay('.tradeTime').each(function(){
        laydate.render({
            elem: this,
            trigger: 'click',
            type:'time',
            theme: '#69c0ff'
        });
    });//日期控件

    var popObj;
    $(function(){
        $(".stop").click(function(){
            $(".mask").fadeIn();
            $(".stop_pop").fadeIn();
            popObj = ".stop_pop"
        });
        $(".start").click(function(){
            $(".mask").fadeIn();
            $(".start_pop").fadeIn();
            popObj = ".start_pop"
        });
        $(".online").click(function(){
            $(".mask").fadeIn();
            $(".online_pop").fadeIn();
            popObj = ".online_pop"
        });
        $(".offLine").click(function(){
            $(".mask").fadeIn();
            $(".offLine_pop").fadeIn();
            popObj = ".offLine_pop"
        });
        $(".add").click(function(){
            $(".mask").fadeIn();
            $(".add_pop").fadeIn();
            popObj = ".add_pop"
        });
        $(".change").click(function(){
            $(".mask").fadeIn();
            $(".change_pop").fadeIn();
            popObj = ".change_pop"
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

</script>

</body>
</html>