<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/resources/page/common/path.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <link rel="icon" href="<%=path %>/resources/image/web/icon.ico" type="image/x-ico" />

    <link rel="stylesheet" type="text/css" href="<%=path %>/resources/css/web/coinOut.css" />
    <link rel="stylesheet" type="text/css" href="<%=path %>/resources/css/web/public.css" />
    <link rel="stylesheet" type="text/css" href="<%=path %>/resources/css/web/simpleTips.css" />

    <title>币种提币</title>
</head>
<body>
<header id="header"></header>
<div id="menu"></div>


<div class="content">
    <div id="personalMenu"></div>

    <div class="contentRight">
        <div class="title">币种提币</div>

        <div class="main">
            <p class="coinInput">
                <label class="popName">当前可提币数量</label>
                <c:if test="${!empty userCoinConfigList}">
                    <span class="amount" id="coinNumber"><fmt:formatNumber type="number" value="${userCoinConfigList[0].currencyNumber}" maxFractionDigits="4" groupingUsed="false"/></span>
                </c:if>
                <c:if test="${empty userCoinConfigList}">
                    <span class="amount" id="coinNumber">0</span>
                </c:if>
            </p>
            <p class="coinInput">
                <label class="popName">选择币种<span class="star">*</span></label>
                <select class="coinSelect" id="coinCurrencyId" onchange="chooseNumber()" autocomplete="off">
                    <c:forEach items="${userCoinConfigList}" var="item">
                        <option value="${item.currencyId}">${item.currencyName}</option>
                    </c:forEach>
                </select>
            </p>
            <p class="coinInput">
                <label class="popName">提币数量<span class="star">*</span></label>
                <input type="text" class="entry" placeholder="您要提币的数量"  id="number" autocomplete="off"
                       onkeyup="matchUtil(this, 'double', 2)" onblur="matchUtil(this, 'double', 2)" maxlength="11"/>
                <c:if test="${!empty userCoinConfigList}">
                    <span class="tips" id="tip">提示：当前币种最低提币<fmt:formatNumber type="number" value="${userCoinConfigList[0].minCurrencyNumber}" maxFractionDigits="2" groupingUsed="false"/>个，
                            超过<fmt:formatNumber type="number" value="${userCoinConfigList[0].freeCurrencyNumber}" maxFractionDigits="2" groupingUsed="false"/>个需人工审核</span>
                </c:if>
                <c:if test="${empty userCoinConfigList}">
                    <span class="tips" id="tip">提示：当前币种最低提币0个，超过0个需人工审核</span>
                </c:if>
                <input type="hidden" id="minNumber">
            </p>
            <p class="coinInput">
                <label class="popName">短信验证码<span class="star">*</span></label>
                <span class="popCode">
                    <input type="text" class="code" placeholder="6位短信验证码" id="validateCode" maxlength="6" autocomplete="off"
                           onkeyup="value=value.replace(/[^\d]/g,'')" onblur="value=value.replace(/[^\d]/g,'')"/>
                    <input type="text" id="message" class="message" value="获取验证码"
                           onfocus="this.blur()" />
                </span>
                <span class="tips">提示：系统将向手机号 ${phoneAreaCode} ${phoneNumberEn}发送一条短信验证码</span>
            </p>
            <p class="coinInput">
                <label class="popName">交易密码<span class="star">*</span></label>
                <input type="password" class="entry" placeholder="您的交易密码"  id="buypwd" maxlength="16" autocomplete="off"
                       onkeyup="value=value.replace(/[^a-zA-Z0-9]/g,'')" onblur="value=value.replace(/[^a-zA-Z0-9]/g,'')"/>
            </p>

            <input type="text" value="立即提币" class="btn" onfocus="this.blur()" onclick="mentionCoin()"/>
        </div>
    </div>
</div>


<div id="helpFooter"></div>
<div id="footer"></div>

<script type="text/javascript" src="http://libs.baidu.com/jquery/2.1.4/jquery.min.js"></script>
<script type="text/javascript" src="<%=path %>/resources/js/loadPageWeb.js"></script>
<script type="text/javascript" src="<%=path %>/resources/js/simpleTips.js"></script>

<script type="text/javascript">
    window.onload = function() {
        //提币最小数量
        var minNumber = '${userCoinConfigList[0].minCurrencyNumber}';
        minNumber = String(minNumber).replace(/^(.*\..{2}).*$/,"$1");
        minNumber = Number(minNumber);
        $("#minNumber").val(minNumber);

        var code = '${code}';
        var message = '${message}';
        if (code != 1 && message != "") {
            openTips(message);
            return false;
        }
    }

    function chooseNumber() {
        var currencyId = document.getElementById("coinCurrencyId").value;

        if (currencyId == null || currencyId == '') {
            return ;
        }
        $.ajax({
            url: '<%=path %>' + "/userWeb/userCoinWithdrawal/coinConfig.htm",
            type:'post',
            dataType:'json',
            async:true,
            data:{
                currencyId : currencyId
            },
            success:function (result) {
                if (result.code == 1 ) {
                    var data = result.data;
                    var userCoinConfig = data.userCoinConfig;

                    var reg = /^(.*\..{2}).*$/;
                    //币种数量
                    var coinNumber = userCoinConfig.currencyNumber;
                    coinNumber = String(coinNumber).replace(/^(.*\..{4}).*$/,"$1");
                    coinNumber = Number(coinNumber);
                    //提币最小数量
                    var minNumber = userCoinConfig.minCurrencyNumber;
                    minNumber = String(minNumber).replace(reg,"$1");
                    minNumber = Number(minNumber);
                    //免审数量
                    var freeNumber = userCoinConfig.freeCurrencyNumber;
                    freeNumber = String(freeNumber).replace(reg,"$1");
                    freeNumber = Number(freeNumber);

                    document.getElementById("coinNumber").innerHTML = coinNumber;
                    document.getElementById("tip").innerHTML = '提示：当前币种最低提币' + minNumber + '个，超过' + freeNumber + '个需人工审核';
                    $("#minNumber").val(minNumber);
                } else {
                    openTips(result.message);
                }
            },
            error:function () {
                openTips("数据加载出错，请稍候重试");
            }
        });
    }

    var wait=60;
    function time(o) {
        if (wait == 0) {
            o.removeAttribute("disabled");
            o.value="获取验证码";
            wait = 60;
        } else {
            if( wait == 60){
                bindingMobile = '${phoneAreaCode}' + '${phoneNumber}';
                $.ajax({
                    url: '<%=path %>' + "/sendCode/sendPhoneCode",
                    type:'post',
                    dataType:'json',
                    async:true,
                    data:{
                        phoneNumber : bindingMobile
                    },
                    success:function(result){
                        openTips(result.message);
                    }, error:function(){
                        openTips("系统错误！");
                    }
                });
            }
            o.setAttribute("disabled", true);
            o.value="重新发送(" + wait + ")";
            wait--;
            setTimeout(function() {
                    time(o)
                },
                1000)
        }
    }

    document.getElementById("message").onclick=function(){time(this);};

    //币种提币
    var coinBoo = false;
    function mentionCoin() {
        if (coinBoo) {
            return false;
        } else {
            coinBoo = true;
        }

        var coinNumber = $("#coinNumber").html();
        coinNumber = parseFloat(coinNumber);
        var minNumber =$("#minNumber").val();
        minNumber = parseFloat(minNumber);
        var currencyId = $("#coinCurrencyId").val();
        var number = $("#number").val();
        number = parseFloat(number);
       var validateCode = $("#validateCode").val();
        var buyPwd = $("#buypwd").val();


        if (!currencyId ) {
            coinBoo = false;
            return openTips("币种信息错误");
        }
        if (!number) {
            coinBoo = false;
            return openTips("请输入提币数量");
        }
        if (number <= 0) {
            coinBoo = false;
            return openTips("提币数量必须大于0");
        }
        if (number > coinNumber) {
            coinBoo = false;
            return openTips("币种数量不足");
        }
        if (number < minNumber) {
            coinBoo = false;
            return openTips("提币数量不能小于最低提币数量");
        }
        if (!validateCode) {
            coinBoo = false;
            return openTips("请输入验证码!");
        }
        if (validateCode.length != 6) {
            coinBoo = false;
            return openTips("验证码为6位数字!");
        }
        if (!buyPwd) {
            coinBoo = false;
            return openTips("请输入交易密码!");
        }
        buyPwd = encode64(buyPwd);

        $.ajax({
            url: '<%=path %>' + "/userWeb/userCoinWithdrawal/mentionCoin.htm",
            type:'post',
            dataType:'json',
            async:true,
            data:{
                currencyId : currencyId,
                number : number,
                validateCode : validateCode,
                buyPwd : buyPwd
            },
            success:function (result) {
                if (result.code == 1) {
                    openTips("操作成功!");
                    setTimeout(function () {
                        window.location.href='<%=path %>' + "/userWeb/userCoinWithdrawal/show.htm";
                    }, 1000);
                } else {
                    openTips(result.message);
                }
                coinBoo = false;
            },
            error:function () {
                coinBoo = false;
                openTips("数据加载出错，请稍候重试");
            }
        });
    }

    // base64加密开始
    var keyStr = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=";

    function encode64(input) {
        var output = "";
        var chr1, chr2, chr3 = "";
        var enc1, enc2, enc3, enc4 = "";
        var i = 0;
        do {
            chr1 = input.charCodeAt(i++);
            chr2 = input.charCodeAt(i++);
            chr3 = input.charCodeAt(i++);
            enc1 = chr1 >> 2;
            enc2 = ((chr1 & 3) << 4) | (chr2 >> 4);
            enc3 = ((chr2 & 15) << 2) | (chr3 >> 6);
            enc4 = chr3 & 63;
            if (isNaN(chr2)) {
                enc3 = enc4 = 64;
            } else if (isNaN(chr3)) {
                enc4 = 64;
            }
            output = output + keyStr.charAt(enc1) + keyStr.charAt(enc2)
                + keyStr.charAt(enc3) + keyStr.charAt(enc4);
            chr1 = chr2 = chr3 = "";
            enc1 = enc2 = enc3 = enc4 = "";
        } while (i < input.length);

        return output;
    }
    // base64加密结束

    var mapMatch = {};
    mapMatch['number'] = /[^\d]/g;
    mapMatch['ENumber'] = /[^\a-\z\A-\Z\d]/g;
    mapMatch['double'] = true;
    mapMatch['phone'] = /[^\d]/g;
    mapMatch['email'] = /([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\.[a-zA-Z0-9_-]{2,3}){1,2})$/;
    function matchUtil(o, str, nu) {
        mapMatch[str] === true ? matchDouble(o, nu) : o.value = o.value.replace(mapMatch[str], '');
    }

    function matchDouble(o, num){
        var matchStr = /^-?\d+\.?\d{0,num}$/;
        if(!matchStr.test(o.value)){
            if(isNaN(o.value)){
                o.value = '';
            }else{
                var n = o.value.indexOf('.');
                var m = n + num + 1;
                if(n > -1 && o.value.length > m){
                    o.value = o.value.substring(0, m);
                }
            }
        }
    }
</script>
</body>
</html>