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
                    <span class="allMoney">$<fmt:formatNumber type="number" value="${userBalanceSum }" maxFractionDigits="2"/></span>
                </p>
                <p class="info">
                    <span class="infoTitle">可用资产</span>
                    <span class="money">$<fmt:formatNumber type="number" value="${userMessage.userBalance }" maxFractionDigits="2"/></span>
                </p>
                <p class="info">
                    <span class="infoTitle">冻结资产</span>
                    <span class="money">$<fmt:formatNumber type="number" value="${userMessage.userBalanceLock }" maxFractionDigits="2"/></span>
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
                        <td class="coin">${userCurrency.currencyName }</td>
                        <td class="amount">${userCurrency.currencyNumber }</td>
                        <td class="amount">${userCurrency.currencyNumberLock }</td>
                        <td class="amount">${userCurrency.currencyNumberSum }</td>
                        <td><a href="javascript:;" class="link" onclick="dealSkip('${userCurrency.currencyId }')" >去交易</a></td>
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
                    <span class="state" id="showMobilePhone">${userMessage.phoneNumber }</span>
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

<form id="tradeCenters" action="<%=path %>/userWeb/tradeCenter/show" method="post">
    <input type="hidden" id="currencyIds" name="currencyId">
</form>

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
                    <input type="password" class="entry" placeholder="原支付密码" maxlength="16" id="passwordPop"
                           onkeyup="value=value.replace(/[^a-zA-Z0-9]/g,'')" onblur="value=value.replace(/[^a-zA-Z0-9]/g,'')"/>
                </p>
                <p class="popInput">
                    <label class="popName">新密码<span class="star">*</span>：</label>
                    <input type="password" class="entry" placeholder="字母、数字，6~16个字符" maxlength="16" id="newPasswordPop"
                           onkeyup="value=value.replace(/[^a-zA-Z0-9]/g,'')" onblur="value=value.replace(/[^a-zA-Z0-9]/g,'')" />
                </p>
                <p class="popInput">
                    <label class="popName">重复密码<span class="star">*</span>：</label>
                    <input type="password" class="entry" placeholder="再次输入新密码" maxlength="16" id="repPasswordPop"
                           onkeyup="value=value.replace(/[^a-zA-Z0-9]/g,'')" onblur="value=value.replace(/[^a-zA-Z0-9]/g,'')" />
                </p>
                <p class="tips">提示：初始支付密码为“123456”，修改初始密码后方可交易</p>
            </div>

            <div class="tel_pop">
                <p class="popInput">
                    <label class="popName">新密码<span class="star">*</span>：</label>
                    <input type="password" class="entry" placeholder="字母、数字，6~16个字符" maxlength="16" id="newPasswordTel"
                           onkeyup="value=value.replace(/[^a-zA-Z0-9]/g,'')" onblur="value=value.replace(/[^a-zA-Z0-9]/g,'')" />
                </p>
                <p class="popInput">
                    <label class="popName">重复密码<span class="star">*</span>：</label>
                    <input type="password" class="entry" placeholder="再次输入新密码" maxlength="16" id="repPasswordTel"
                           onkeyup="value=value.replace(/[^a-zA-Z0-9]/g,'')" onblur="value=value.replace(/[^a-zA-Z0-9]/g,'')" />
                </p>
                <p class="popInput">
                    <label class="popName">手机号：</label>
                    <span class="popTel"><span id="showAreaCode">${userMessage.phoneAreaCode }</span><span id="showPhone">${userMessage.phoneNumber }</span></span>
                </p>
                <p class="popInput">
                    <label class="popName">手机验证码<span class="star">*</span>：</label>
                    <span class="popCode">
                        <input type="text" class="code" placeholder="6位短信验证码" maxlength="6" id="validateCodeTel"
                               onkeyup="value=value.replace(/[^a-zA-Z0-9]/g,'')" onblur="value=value.replace(/[^a-zA-Z0-9]/g,'')"/>
                        <input type="text" id="passwordBtn" class="message" value="获取验证码" onfocus="this.blur()" onclick="payNoteVerify()"  />
                    </span>
                </p>
                <p class="tips">提示：初始支付密码为“123456”，修改初始密码后方可交易</p>
            </div>

            <div class="buttons">
                <input type="text" value="取&nbsp;消" class="cancel" onfocus="this.blur()" />
                <input type="text" value="确&nbsp;定" class="yes" onfocus="this.blur()" onclick="updatePayPassword()" />
            </div>
        </div>

        <div class="changePassword_pop">
            <p class="popTitle">修改密码</p>
            <p class="popInput">
                <label class="popName">原密码<span class="star">*</span>：</label>
                <input type="password" class="entry" placeholder="原登录密码" maxlength="16" id="password"
                       onkeyup="value=value.replace(/[^a-zA-Z0-9]/g,'')" onblur="value=value.replace(/[^a-zA-Z0-9]/g,'')"/>
            </p>
            <p class="popInput">
                <label class="popName">新密码<span class="star">*</span>：</label>
                <input type="password" class="entry" placeholder="字母、数字，6~16个字符" maxlength="16" id="newPassword"
                       onkeyup="value=value.replace(/[^a-zA-Z0-9]/g,'')" onblur="value=value.replace(/[^a-zA-Z0-9]/g,'')"/>
            </p>
            <p class="popInput">
                <label class="popName">重复密码<span class="star">*</span>：</label>
                <input type="password" class="entry" placeholder="再次输入新密码" maxlength="16" id="repPassword"
                       onkeyup="value=value.replace(/[^a-zA-Z0-9]/g,'')" onblur="value=value.replace(/[^a-zA-Z0-9]/g,'')"/>
            </p>

            <div class="buttons">
                <input type="text" value="取&nbsp;消" class="cancel" onfocus="this.blur()" />
                <input type="text" value="确&nbsp;定" class="yes" onfocus="this.blur()" onclick="updateLogPassword()" />
            </div>
        </div>

        <div class="changePhone_pop">
            <p class="popTitle">绑定手机</p>
            <p class="popInput">
                <label class="popName">手机号<span class="star">*</span>：</label>
                <span class="popCode">
                    <select class="areTel" id="areaCode" >
                        <c:forEach items="${phoneAreaMap}" var="phoneArea">
                            <option value="${phoneArea.key }">${phoneArea.value }&nbsp;${phoneArea.key }</option>
                        </c:forEach>
                    </select>
                    <input type="text" class="telNumber" placeholder="您的11位手机号" maxlength="11" id="bindingMobile"
                           onkeyup="value=value.replace(/[^\d]/g,'')" onblur="value=value.replace(/[^\d]/g,'')"/>
                </span>
            </p>
            <p class="popInput">
                <label class="popName">手机验证码<span class="star">*</span>：</label>
                <span class="popCode">
                    <input type="text" class="code" placeholder="6位短信验证码" maxlength="6" id="verifyCode"
                           onkeyup="value=value.replace(/[^a-zA-Z0-9]/g,'')" onblur="value=value.replace(/[^a-zA-Z0-9]/g,'')"/>
                    <input type="text" style="width: 0;height: 0;border: none"/>
                    <input type="text" id="phoneBtn" class="message" value="获取验证码" onfocus="this.blur()" />
                </span>
            </p>
            <p class="popInput">
                <label class="popName">登录密码<span class="star">*</span>：</label>

                <input type="text" class="entry" placeholder="您的登录密码" maxlength="16" id="phonePassword"
                       onkeyup="value=value.replace(/[^a-zA-Z0-9]/g,'')" onblur="value=value.replace(/[^a-zA-Z0-9]/g,'')"/>
            </p>

            <div class="buttons">
                <input type="text" value="取&nbsp;消" class="cancel" onfocus="this.blur()" />
                <input type="text" value="确&nbsp;定" class="yes" onfocus="this.blur()" onclick="updatePhoneNumber()" />
            </div>
        </div>
    </div>
</div>

<script type="text/javascript" src="http://libs.baidu.com/jquery/2.1.4/jquery.min.js"></script>
<script type="text/javascript" src="<%=path %>/resources/js/loadPageWeb.js"></script>
<script type="text/javascript" src="<%=path %>/resources/js/simpleTips.js"></script>
<script type="text/javascript">
    window.onload = function() {
        $('#phonePassword').attr("type", "password");
        var code = '${code}';
        var message = '${message}';
        var payPassword = '${userMessage.payPassword }';
        var payPass = document.getElementById("payPass");
        var payError = document.getElementById("payError");
        if(payPassword == 1){
            payError.style.display ="inline";
            payPass.style.display = "none";
        } else {
            payPass.style.display ="inline";
            payError.style.display = "none";
        }
        if (code != 1 && message != "") {
            openTips(message);
            return false;
        }
    }

    var updatePayPasswordBoo = false;
    //更新支付密码
    function updatePayPassword() {
        var tel_pop =$('.tel_pop').css('display');

        if(tel_pop == 'none'){
            var passwordPop = $("#passwordPop").val();
            var newPasswordPop = $("#newPasswordPop").val();
            var repPasswordPop = $("#repPasswordPop").val();

            if(passwordPop == ""){
                openTips("请输入原密码");
                return;
            }

            if(passwordPop.length < 6){
                openTips("原密码长度不足！");
                return;
            }
            
            if(newPasswordPop == ""){
                openTips("请输入新密码");
                return;
            }

            if(newPasswordPop.length < 6){
                openTips("新密码长度不足！");
                return;
            }
            
            if(newPasswordPop == "123456"){
                openTips("不可设为初始密码");
                return;
            }

            if(repPasswordPop == ""){
                openTips("请您输入确认密码");
                return;
            }

            if(newPasswordPop != repPasswordPop){
                openTips("两次密码不一致");
                return;
            }

            if(updatePayPasswordBoo){
                openTips("系统正在更新支付密码，请稍后");
                return;
            } else {
                updatePayPasswordBoo = true;
            }

            $.ajax({
                url: '<%=path %>' + "/userWeb/userMessage/updatePayPasswordByPassword.htm",
                type:'post',
                dataType:'json',
                async:true,
                data:{
                    password : passwordPop,
                    newPassword : newPasswordPop,
                    repetitionPassword : repPasswordPop
                },
                success:function(result){
                    updatePayPasswordBoo = false;
                    if(result.code == 1) {
                        var payPass = document.getElementById("payPass");
                        var payError = document.getElementById("payError");
                        payPass.style.display ="inline";
                        payError.style.display = "none";
                        $(".mask").fadeOut("fast");
                        $(".changePay_pop").fadeOut("fast");
                        $("#passwordPop").val("");
                        $("#newPasswordPop").val("");
                        $("#repPasswordPop").val("");

                    } else {
                        openTips(result.message);
                    }
                }, error:function(){
                    updatePayPasswordBoo = false;
                    openTips("系统错误！");
                }
            });

        } else {
            var newPasswordTel = $("#newPasswordTel").val();
            var repPasswordTel = $("#repPasswordTel").val();
            var validateCodeTel = $("#validateCodeTel").val();

            if(newPasswordTel == ""){
                openTips("请输入新密码");
                return;
            }

            if(newPasswordTel.length <6){
                openTips("新密码长度不足！");
                return;
            }
            
            if(newPasswordTel == "123456"){
                openTips("不可设为初始密码");
                return;
            }

            if(repPasswordTel == ""){
                openTips("请您输入确认密码");
                return;
            }

            if(validateCodeTel == ""){
                openTips("请输入验证码");
                return;
            }

            if(validateCodeTel.length != 6){
                openTips("验证码为6位");
                return;
            }
            
            if(newPasswordTel != repPasswordTel){
                openTips("两次密码不一致");
                return;
            }

            if(updatePayPasswordBoo){
                openTips("系统正在更新支付密码，请稍后");
                return;
            } else {
                updatePayPasswordBoo = true;
            }
            $.ajax({
                url: '<%=path %>' + "/userWeb/userMessage/updatePhoneByPassword.htm",
                type:'post',
                dataType:'json',
                async:true,
                data:{
                    validateCode : validateCodeTel,
                    newPassword : newPasswordTel,
                    repetitionPassword : repPasswordTel
                },
                success:function(result){
                    updatePayPasswordBoo = false;
                    if(result.code == 1) {
                        openTips(result.message);
                        var payPass = document.getElementById("payPass");
                        var payError = document.getElementById("payError");
                        payPass.style.display ="inline";
                        payError.style.display = "none";
                        $(".mask").fadeOut("fast");
                        $(".changePay_pop").fadeOut("fast");
                        $("#newPasswordTel").val("");
                        $("#repPasswordTel").val("");
                        $("#validateCodeTel").val("");
                    } else {
                        openTips(result.message);
                    }
                }, error:function(){
                    updatePayPasswordBoo = false;
                    openTips("系统错误！");
                }
            });
        }
    }

    var updateLogPasswordBoo = false;
    //修改登陆密码
    function updateLogPassword() {
        var password = $("#password").val();
        var newPassword = $("#newPassword").val();
        var repPassword = $("#repPassword").val();

        if(password == ""){
            openTips("请输入原密码");
            return;
        }

        if(password.length < 6){
            openTips("原密码长度不足！");
            return;
        }
        
        if(newPassword == ""){
            openTips("请输入新密码");
            return;
        }

        if(newPassword.length < 6){
            openTips("新密码长度不足！");
            return;
        }
        
        if(repPassword == ""){
            openTips("请您输入确认密码");
            return;
        }

        if(newPassword != repPassword){
            openTips("两次密码不一致");
            return;
        }

        if(updateLogPasswordBoo){
            openTips("系统正在更新登录密码，请稍后");
            return;
        } else {
            updateLogPasswordBoo = true;
        }

        $.ajax({
            url: '<%=path %>' + "/userWeb/userMessage/updateLogPassword.htm",
            type:'post',
            dataType:'json',
            async:true,
            data:{
                password : password,
                newPassword : newPassword,
                repetitionPassword : repPassword
            },
            success:function(result){
                updateLogPasswordBoo = false;
                if(result.code == 1) {
                    $(".mask").fadeOut("fast");
                    $(".changePassword_pop").fadeOut("fast");
                    $("#password").val("");
                    $("#newPassword").val("");
                    $("#repPassword").val("");

                    window.location.href = "<%=path%>" + "/userWeb/userLogin/show";
                } else {
                    openTips(result.message);
                }
            }, error:function(){
                updateLogPasswordBoo = false;
                openTips("系统错误！");
            }
        });

    }

    var updatePhoneNumberBoo = false;
    //修改绑定手机号
    function updatePhoneNumber() {
        var regPos = /^\d+(\.\d+)?$/; //非负浮点数
        var bindingMobile = $("#bindingMobile").val();
        var verifyCode = $("#verifyCode").val();
        var areaCode = $("#areaCode").val();
        var phonePassword = $("#phonePassword").val();
        if(bindingMobile == ""){
            openTips("请输入手机号");
            return;
        }

        if(!regPos.test(bindingMobile) || bindingMobile.length > 11 || bindingMobile.length < 5){
            openTips("请输入正确手机号");
            return;
        }
        
        if(verifyCode == ""){
            openTips("请输入验证码");
            return;
        }

        if(verifyCode.length < 6){
            openTips("验证码为六位");
            return;
        }
        
        if(phonePassword == ""){
            openTips("请输入登录密码");
            return;
        }

        if(phonePassword.length < 6){
            openTips("登录密码长度不足！");
            return;
        }
        
        if(updatePhoneNumberBoo){
            openTips("系统正在更新绑定手机号，请稍后");
            return;
        } else {
            updatePhoneNumberBoo = true;
        }

        $.ajax({
            url: '<%=path %>' + "/userWeb/userMessage/updatePasswordByPhone.htm",
            type:'post',
            dataType:'json',
            async:true,
            data:{
                phone : bindingMobile,
                areaCode : areaCode,
                validateCode : verifyCode,
                password : phonePassword
            },
            success:function(result){
                updatePhoneNumberBoo = false;
                if(result.code == 1) {
                    openTips(result.message);
                    var phone = bindingMobile.substring(0,3) + "***" + bindingMobile.substring(bindingMobile.length - 3);
                    $("#showPhone").text(phone);
                    $("#showMobilePhone ").text(phone);
                    $("#showAreaCode").text(areaCode);
                    $(".mask").fadeOut("fast");
                    $(".changePhone_pop").fadeOut("fast");
                    $("#bindingMobile").val("");
                    $("#verifyCode").val("");
                    $("#areaCode").val("+86");
                    $("#phonePassword").val("");
                } else {
                    openTips(result.message);
                }
            }, error:function(){
                updatePhoneNumberBoo = false;
                openTips("系统错误！");
            }
        });
    }

    //去交易
    function dealSkip (currencyId){
        $("#currencyIds").val(currencyId);
        $("#tradeCenters").submit();
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
            // $(".mask").fadeOut("fast");
            // $(popObj).fadeOut("fast");
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
            if( wait == 60){
                var regPos = /^\d+(\.\d+)?$/; //非负浮点数
                var bindingMobile = $("#bindingMobile").val();
                var areaCode = $("#areaCode").val();
                if(bindingMobile == ""){
                    openTips("请输入手机号");
                    return;
                }

                if(!regPos.test(bindingMobile) || bindingMobile.length > 11 || bindingMobile.length < 5){
                    openTips("请输入正确手机号");
                    return;
                }
                //bindingMobile = areaCode + bindingMobile;
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

    var payWait=60;
    function payTime(o) {
        if (payWait == 0) {
            o.removeAttribute("disabled");
            o.value="获取验证码";
            payWait = 60;
        } else {
            if( payWait == 60){
                $.ajax({
                    url: '<%=path %>' + "/userWeb/userMessage/payNoteVerify.htm",
                    type:'post',
                    dataType:'json',
                    async:true,
                    data:{
                    },
                    success:function(result){
                        openTips(result.message);
                    }, error:function(){
                        openTips("系统错误！");
                    }
                });
            }
            o.setAttribute("disabled", true);
            o.value="重新发送(" + payWait + ")";
            payWait--;
            setTimeout(function() {
                    payTime(o)
                },
                1000)
        }
    }
    document.getElementById("phoneBtn").onclick=function(){time(this);};
    document.getElementById("passwordBtn").onclick=function(){payTime(this);};
</script>

</body>
</html>
