<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/resources/page/common/path.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <link rel="icon" href="<%=path %>/resources/image/back/icon.ico" type="image/x-ico" />

    <link rel="stylesheet" type="text/css" href="<%=path %>/resources/css/web/personal.css" />
    <link rel="stylesheet" type="text/css" href="<%=path %>/resources/css/web/public.css" />
    <link rel="stylesheet" type="text/css" href="<%=path %>/resources/css/web/simpleTips.css" />

    <title>个人信息</title>
</head>
<body>
<div id="header"></div>
<div id="menu"></div>


<div class="content">
    <div id="personalMenu"></div>

    <div class="contentRight">
        <div class="top">
            <div class="title">个人信息</div>

            <div class="accountInfo">
                <p class="info">
                    <span class="infoTitle">总资产</span>
                    <span class="allMoney"><fmt:formatNumber type="number" value="${userBalanceSum }" maxFractionDigits="2"/></span>
                </p>
                <p class="info">
                    <span class="infoTitle">可用资产</span>
                    <span class="money"><fmt:formatNumber type="number" value="${userMessage.userBalance }" maxFractionDigits="2"/></span>
                </p>
                <p class="info">
                    <span class="infoTitle">冻结资产</span>
                    <span class="money"><fmt:formatNumber type="number" value="${userMessage.userBalanceLock }" maxFractionDigits="2"/></span>
                </p>
            </div>

            <table class="accountTable" cellspacing="0" cellpadding="0">
                <tr class="coinTitle">
                    <td class="coin">币种</td>
                    <td class="amount">可用数量</td>
                    <td class="amount">冻结数量</td>
                    <td class="amount">币种总资产</td>
                    <td class="operate">操作</td>
                </tr>
                <c:forEach items="${userCurrencyList }" var="userCurrency">
                    <tr class="coinInfo">
                        <td class="coin">盛源链(MUC/USD)</td>
                        <td class="amount">${userCurrency.currencyNumber }</td>
                        <td class="amount">${userCurrency.currencyNumberLock }</td>
                        <td class="amount">${userCurrency.currencyNumberSum }</td>
                        <td><a href="#" class="link">去交易</a></td>
                    </tr>
                </c:forEach>
            </table>
        </div>

        <div class="safety">
            <div class="title">安全中心</div>

            <ul class="safetyList">
                <li class="safetyInfo">
                    <img src="<%=path %>/resources/image/web/pass.png" class="pass" id="payPass"/>
                    <img src="<%=path %>/resources/image/web/error.png" class="error" id="payError" />
                    <span class="safetyTitle">支付密码</span>
                    <span class="state">已设置</span>
                    <span class="explain">为保证账号安全，建议设置与登录密码不同的密码组合</span>
                    <input type="text" value="修&nbsp;改" class="changePay" onfocus="this.blur()" />
                </li>
                <li class="safetyInfo">
                    <img src="<%=path %>/resources/image/web/pass.png" class="pass" />
                    <img src="<%=path %>/resources/image/web/error.png" class="error" style="display: none" />
                    <span class="safetyTitle">登录密码</span>
                    <span class="state">已设置</span>
                    <span class="explain">为保证账号安全，建议设置为字母和数字的组合</span>
                    <input type="text" value="修&nbsp;改" class="changePassword" onfocus="this.blur()" />
                </li>
                <li class="safetyInfo">
                    <img src="<%=path %>/resources/image/web/pass.png" class="pass"  />
                    <img src="<%=path %>/resources/image/web/error.png" class="error" style="display: none"/>
                    <span class="safetyTitle">绑定手机</span>
                    <span class="state">${userMessage.phoneNumber }</span>
                    <span class="explain">可以通过该手机号找回密码</span>
                    <input type="text" value="修&nbsp;改" class="changePhone" onfocus="this.blur()" />
                </li>
                <li class="listTips">提示：为保证您的账户安全，初始支付密码为“123456”，请及时修改您的支付密码方可进行交易操作</li>
            </ul>
        </div>
    </div>
</div>


<div id="helpFooter"></div>
<div id="footer"></div>


<div class="mask">
    <div class="mask_content">
        <div class="changePay_pop">
            <p class="popTitle">修改支付密码</p>
            <p class="popChoose">
                <span class="password pick">通过原密码修改</span>
                <span class="tel">通过手机修改</span>
            </p>

            <div class="password_pop">
                <p class="popInput">
                    <label class="popName">原密码<span class="star">*</span>：</label>
                    <input type="password" class="entry" placeholder="原登录密码" />
                </p>
                <p class="popInput">
                    <label class="popName">新密码<span class="star">*</span>：</label>
                    <input type="password" class="entry" placeholder="字母、数字，6~16个字符" />
                </p>
                <p class="popInput">
                    <label class="popName">重复密码<span class="star">*</span>：</label>
                    <input type="password" class="entry" placeholder="再次输入新密码" />
                </p>
                <p class="tips">提示：初始支付密码为“123456”，修改初始密码后方可交易</p>
            </div>

            <div class="tel_pop">
                <p class="popInput">
                    <label class="popName">新密码<span class="star">*</span>：</label>
                    <input type="password" class="entry" placeholder="字母、数字，6~16个字符" />
                </p>
                <p class="popInput">
                    <label class="popName">重复密码<span class="star">*</span>：</label>
                    <input type="password" class="entry" placeholder="再次输入新密码" />
                </p>
                <p class="popInput">
                    <label class="popName">手机号：</label>
                    <span class="popTel"><span>+${userMessage.phoneAreaCode }</span><span>${userMessage.phoneNumber }</span></span>
                </p>
                <p class="popInput">
                    <label class="popName">手机验证码<span class="star">*</span>：</label>
                    <span class="popCode">
                        <input type="text" class="code" placeholder="6位短信验证码" />
                        <input type="text" id="passwordBtn" class="message" value="获取验证码" onfocus="this.blur()" />
                    </span>
                </p>
                <p class="tips">提示：初始支付密码为“123456”，修改初始密码后方可交易</p>
            </div>

            <div class="buttons">
                <input type="text" value="取&nbsp;消" class="cancel" onfocus="this.blur()" />
                <input type="text" value="确&nbsp;定" class="yes" onfocus="this.blur()" onclick="openTip()" />
            </div>
        </div>

        <div class="changePassword_pop">
            <p class="popTitle">修改密码</p>
            <p class="popInput">
                <label class="popName">原密码<span class="star">*</span>：</label>
                <input type="password" class="entry" placeholder="原登录密码" />
            </p>
            <p class="popInput">
                <label class="popName">新密码<span class="star">*</span>：</label>
                <input type="password" class="entry" placeholder="字母、数字，6~16个字符" />
            </p>
            <p class="popInput">
                <label class="popName">重复密码<span class="star">*</span>：</label>
                <input type="password" class="entry" placeholder="再次输入新密码" />
            </p>

            <div class="buttons">
                <input type="text" value="取&nbsp;消" class="cancel" onfocus="this.blur()" />
                <input type="text" value="确&nbsp;定" class="yes" onfocus="this.blur()" onclick="openTip()" />
            </div>
        </div>

        <div class="changePhone_pop">
            <p class="popTitle">绑定手机</p>
            <p class="popInput">
                <label class="popName">手机号<span class="star">*</span>：</label>
                <span class="popCode">
                    <select class="areTel">
                        <option selected>中国&nbsp;+86</option>
                    </select>
                    <input type="text" class="telNumber" placeholder="您的11位手机号" />
                </span>
            </p>
            <p class="popInput">
                <label class="popName">手机验证码<span class="star">*</span>：</label>
                <span class="popCode">
                    <input type="text" class="code" placeholder="6位短信验证码" />
                    <input type="text" id="phoneBtn" class="message" value="获取验证码" onfocus="this.blur()" />
                </span>
            </p>
            <p class="popInput">
                <label class="popName">登录密码<span class="star">*</span>：</label>
                <input type="password" class="entry" placeholder="您的登录密码" />
            </p>

            <div class="buttons">
                <input type="text" value="取&nbsp;消" class="cancel" onfocus="this.blur()" />
                <input type="text" value="确&nbsp;定" class="yes" onfocus="this.blur()" onclick="openTip()" />
            </div>
        </div>
    </div>
</div>

<script type="text/javascript" src="http://libs.baidu.com/jquery/2.1.4/jquery.min.js"></script>
<script type="text/javascript" src="js/public.js"></script>
<script type="text/javascript" src="<%=path %>/resources/js/simpleTips.js"></script>
<script type="text/javascript">
    window.onload = function() {
        var code = '${code}';
        var message = '${message}';
        var payPassword = '${userMessage.payPassword }';
        var payPass = document.getElementById("payPass");
        var payError = document.getElementById("payError");
        if(payPassword != null){
            payError.style.visibility ="visible";
            payPass.style.visibility = "hidden";
        } else {
            payError.style.visibility ="visible";
            payPass.style.visibility = "hidden";

        }
        if (code != 1 && message != "") {
            openTips(message);
            return false;
        }
    }
</script>
<script type="text/javascript">
    var popObj;
    $(function(){
        $(".changePay").click(function(){
            $(".mask").fadeIn();
            $(".changePay_pop").fadeIn();
            popObj = ".changePay_pop"
        });
        $(".changePassword").click(function(){
            $(".mask").fadeIn();
            $(".changePassword_pop").fadeIn();
            popObj = ".changePassword_pop"
        });
        $(".changePhone").click(function(){
            $(".mask").fadeIn();
            $(".changePhone_pop").fadeIn();
            popObj = ".changePhone_pop"
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

    $(function(){
        $(".popChoose span").click(function(){
            $(".popChoose span").removeClass("pick");
            $(this).addClass("pick");
        });
        $(".password").click(function(){
            $(".password_pop").show();
            $(".tel_pop").hide();
        });
        $(".tel").click(function(){
            $(".password_pop").hide();
            $(".tel_pop").show();
        })
    });

    var wait=60;
    function time(o) {
        if (wait == 0) {
            o.removeAttribute("disabled");
            o.value="获取验证码";
            wait = 60;
        } else {
            o.setAttribute("disabled", true);
            o.value="重新发送(" + wait + ")";
            wait--;
            setTimeout(function() {
                    time(o)
                },
                1000)
        }
    }
    document.getElementById("phoneBtn").onclick=function(){time(this);};
    document.getElementById("passwordBtn").onclick=function(){time(this);};

    function openTip()
    {
        openTips("阿萨德芳");
    }
</script>

</body>
</html>
