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
            <div class="title">个人信息 <c:if test="${userSession.isDealer == 2}"><span class="identity">经销商</span>  </c:if></div>

            <div class="accountInfo">
                <p class="info">
                    <span class="infoTitle">总资产</span>
                    <span class="allMoney"><fmt:formatNumber type="number" value="${userBalanceSum }" maxFractionDigits="2"/> XT</span>
                </p>
                <p class="info">
                    <span class="infoTitle">可用资产</span>
                    <span class="money"><fmt:formatNumber type="number" value="${userMessage.userBalance }" maxFractionDigits="2"/> XT</span>
                </p>
                <p class="info">
                    <span class="infoTitle">冻结资产</span>
                    <span class="money"><fmt:formatNumber type="number" value="${userMessage.userBalanceLock }" maxFractionDigits="2"/> XT</span>
                </p>
            </div>

            <c:if test="${!empty userCurrencyList}">
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
                            <td class="amount"><fmt:formatNumber type="number" value="${userCurrency.currencyNumber }" groupingUsed="FALSE" maxFractionDigits="4"/></td>
                            <td class="amount"><fmt:formatNumber type="number" value="${userCurrency.currencyNumberLock }" groupingUsed="FALSE" maxFractionDigits="4"/></td>
                            <td class="amount"><fmt:formatNumber type="number" value="${userCurrency.currencyNumberSum }" groupingUsed="FALSE" maxFractionDigits="6"/></td>
                            <td><a href="<%=path %>/userWeb/tradeCenter/show/${userCurrency.currencyId }" class="link">去交易</a></td>
                        </tr>
                    </c:forEach>
                </table>
            </c:if>
        </div>

        <c:if test="${userSession.isDealer == 2}">

        <input type="text" class="add_adv" value="+ 发起广告" onfocus="this.blur()" />
        <div class="sellerInfo">
            <div class="title">我的广告</div>
       <c:if test="${!empty otcTransactionPendOrderList}">
            <table class="advTable" cellspacing="0" cellpadding="0">
                <tr class="coinTitle">
                    <td class="coin">币种</td>
                    <td class="type">类型</td>
                    <td class="area">地区</td>
                    <td class="proportion">比例</td>
                    <td class="limit">交易限额</td>
                    <td class="operate">操作</td>
                </tr>
                <c:forEach items="${otcTransactionPendOrderList }" var="otcPendOrder">
                <tr class="coinInfo" id="${otcPendOrder.otcPendingOrderNo}">
                    <td class="coin">${otcPendOrder.currencyName }</td>
                    <td class="type"><c:if test="${otcPendOrder.orderType == '1' }">用户购买</c:if><c:if test="${otcPendOrder.orderType == '2' }">用户出售</c:if></td>
                    <td class="area">${otcPendOrder.area }</td>
                    <td class="proportion">1:<fmt:formatNumber type="number" value="${otcPendOrder.pendingRatio }" groupingUsed="FALSE" maxFractionDigits="4"/></td>
                    <td class="limit"><span><fmt:formatNumber type="number" value="${otcPendOrder.minNumber }" groupingUsed="FALSE" maxFractionDigits="2"/></span>~<span><fmt:formatNumber type="number" value="${otcPendOrder.maxNumber }" groupingUsed="FALSE" maxFractionDigits="2"/></span>CNY</td>
                    <td class="operate">
                        <input type="text" value="删&nbsp;除" class="delete" onfocus="this.blur()" onclick="openOrderDelete('${otcPendOrder.otcPendingOrderNo}')"/>
                    </td>
                </tr>
                </c:forEach>
            </table>
        </c:if>
        </div>
        </c:if>
        <div class="safety">
            <div class="title">安全中心</div>

            <ul class="safetyList">
                <li class="safetyInfo">
                    <img src="<%=path %>/resources/image/web/pass.png" class="pass" id="payPass"/>
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
                <li class="listTips">提示：为保证您的账户安全，登录密码与支付密码设置不相同的密码</li>
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
        <div class="add_pop">
            <p class="popTitle">发起广告</p>

            <p class="popInput">
                <label class="popName">币种<span class="star">*</span>：</label>
                <select class="popSelected" id="currencyId" name="currencyId">
                    <option value="0" disabled selected>选择币种</option>
                    <option value="999">XT</option>
                </select>
            </p>
            <p class="popInput">
                <label class="popName">类型<span class="star">*</span>：</label>
                <select class="popSelected" onchange="changeOrderType(this.value)" id="orderType" name="orderType">
                    <option value="0" disabled selected>选择类型</option>
                    <option value="2">用户出售</option>
                    <option value="1">用户购买</option>
                </select>
            </p>
            <p class="popInput">
                <label class="popName">地区<span class="star">*</span>：</label>
                <select class="popSelected" id="area" name="area">
                    <option value="0" disabled selected>选择地区</option>
                    <option value="1">中国(CN)</option>
                </select>
            </p>
            <p class="popInput">
                <label class="popName">比例<span class="star">*</span>：</label>
                <input type="text" id="pendingRatio" maxlength="10"  onkeyup="matchUtil(this, 'double', 2)" onblur="matchUtil(this, 'double', 2)" name="pendingRatio" class="entry" placeholder="交易比例" />
                <span class="remind">交易比例为：XT:兑换的货币单位；若比例为1:100，则填100。</span>
            </p>
            <p class="popInput">
                <label class="popName">交易限额<span class="star">*</span>：</label>
                <input type="text"  id="minNumber" maxlength="9"  onkeyup="matchUtil(this, 'double', 2)" onblur="matchUtil(this, 'double', 2)"  name="minNumber" class="lowLimit" placeholder="最低限额,单位:CNY" />&nbsp;～
                <input type="text"  id="maxNumber" maxlength="9" onkeyup="matchUtil(this, 'double', 2)" onblur="matchUtil(this, 'double', 2)"  name="maxNumber" class="highLimit" placeholder="最高限额,单位:CNY" />
            </p>
            <p class="choose">
                <label class="popName">支付方式<span class="star">*</span>：</label>
                <span class="chooseType">
                    <label class="typeInfo"><input type="checkbox" id="bankBox" class="box" />银行卡转账</label>
                    <label class="typeInfo"><input type="checkbox" id="alipayBox" class="box" />支付宝转账</label>
                    <label class="typeInfo"><input type="checkbox" id="wechatBox" class="box" />微信转账</label>
                </span>
            </p>

            <div class="buttons">
                <input type="text" value="取&nbsp;消" class="cancel" onfocus="this.blur()" />
                <input type="text" value="确&nbsp;定" class="confirm" id="addOrderConfirmId" onfocus="this.blur()" />
            </div>
        </div>

        <div class="pay_pop">
            <p class="popTitle">收款信息</p>

            <div class="overflow">
                <div class="card" id="cardDiv" style="display:none;">
                    <p class="popSecond">银行卡转账</p>

                    <p class="popInput">
                        <label class="popName">银行卡号<span class="star">*</span>：</label>
                        <input type="text" id="bankAccount" name="bankAccount" maxlength="19" onkeyup="value=value.replace(/[^\d]/g,'')" onblur="value=value.replace(/[^\d]/g,'')"
                               class="entry" placeholder="您的银行卡号" />
                    </p>
                    <p class="popInput">
                        <label class="popName">银行名称<span class="star">*</span>：</label>
                        <input type="text" id="bankName" name="bankName" onkeyup="value=value.replace(/[^a-zA-Z0-9\u4E00-\u9FA5]/g,'')" onblur="value=value.replace(/[^a-zA-Z0-9\u4E00-\u9FA5]/g,'')" maxlength="15"  class="entry" placeholder="该银行卡的银行名称" />
                    </p>
                    <p class="popInput">
                        <label class="popName">支行名称<span class="star">*</span>：</label>
                        <input type="text" id="bankBranch" name="bankBranch" onkeyup="value=value.replace(/[^a-zA-Z0-9\u4E00-\u9FA5]/g,'')" onblur="value=value.replace(/[^a-zA-Z0-9\u4E00-\u9FA5]/g,'')" maxlength="50" class="entry" placeholder="该卡的支行名称" />
                    </p>
                    <p class="popInput">
                        <label class="popName">预留姓名<span class="star">*</span>：</label>
                        <input type="text" id="paymentName" name="paymentName" onkeyup="value=value.replace(/[^a-zA-Z0-9\u4E00-\u9FA5]/g,'')" onblur="value=value.replace(/[^a-zA-Z0-9\u4E00-\u9FA5]/g,'')" maxlength="30" class="entry" placeholder="该银行卡的银行预留姓名" />
                    </p>
                    <p class="popInput">
                        <label class="popName">预留电话<span class="star">*</span>：</label>
                        <input type="text" id="paymentPhone" name="paymentPhone" maxlength="11" onkeyup="matchUtil(this, 'number')" onblur="matchUtil(this, 'number')"
                               onkeyup="value=value.replace(/[^\d]/g,'')" onblur="value=value.replace(/[^\d]/g,'')"
                               class="entry" placeholder="该银行卡的银行预留电话" />
                    </p>
                </div>

                <div class="alipay" id="alipayDiv" style="display:none;">
                    <p class="popSecond">支付宝转账</p>

                    <p class="popInput">
                        <label class="popName">支付宝账号<span class="star">*</span>：</label>
                        <input type="text" id="alipayAccount" onkeyup="value=value.replace(/[^a-zA-Z0-9\.@_-]/g,'')" onblur="value=value.replace(/[^a-zA-Z0-9\.@_-]/g,'')" maxlength="30" name="alipayAccount" class="entry" placeholder="您的支付宝账号" />
                    </p>
                    <p class="popInput">
                        <label class="popName">收款码<span class="star">*</span>：</label>
                        <span class="pic">
                      <input type="text" id="changead_t1"  class="choosePic" placeholder="请选择二维码图片" onfocus="this.blur()" />
                    <input type="text"  onclick="document.getElementById('changead_a1').click();"  value="选择文件" class="choose_button" onfocus="this.blur();" />
                    <input type="file" class="file" name="adsImageUrl" id="changead_a1" onchange="checkFileImageTwo(this, 'changead_t1');" />
                        </span>
                    </p>
                </div>

                <div class="wechat" id="wechatDiv" style="display:none;">
                    <p class="popSecond">微信转账</p>

                    <p class="popInput">
                        <label class="popName">微信账号<span class="star">*</span>：</label>
                        <input type="text" id="wechatAccount" onkeyup="value=value.replace(/[^a-zA-Z0-9\_-]/g,'')" onblur="value=value.replace(/[^a-zA-Z0-9\_-]/g,'')" maxlength="30" name="wechatAccount" class="entry" placeholder="您的微信账号" />
                    </p>
                    <p class="popInput">
                        <label class="popName">收款码<span class="star">*</span>：</label>
                        <span class="pic">
                            <input type="text" id="changead_t2"  class="choosePic" placeholder="请选择二维码图片" onfocus="this.blur()" />
                            <input type="text"  onclick="document.getElementById('changead_a2').click();"  value="选择文件" class="choose_button" onfocus="this.blur();" />
                            <input type="file" class="file" id="changead_a2" onchange="checkFileImageTwo(this, 'changead_t2');" />
                        </span>
                    </p>
                </div>
            </div>

            <div class="buttons">
                <input type="text" value="返&nbsp;回" class="back" onfocus="this.blur()" />
                <input type="text" value="确&nbsp;定" class="yes" id="addOrderId"  onclick="addOrder()"  />
            </div>
        </div>

        <div class="delete_pop">
            <p class="popTitle">删除操作</p>
            <p class="popTips"><img src="<%=path %>/resources/image/web/tips.png" class="tipsImg" />确定删除？</p>

            <div class="buttons">
                <input type="text" value="取&nbsp;消" class="cancel" onfocus="this.blur()" />
                <input type="hidden" id="deleteOrderId" />
                <input type="text" value="确&nbsp;定" class="yes" onfocus="this.blur()"  onclick="deleteOrder()"  />
            </div>
        </div>
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
                               onkeyup="value=value.replace(/[^\d]/g,'')" onblur="value=value.replace(/[^\d]/g,'')"/>
                        <input type="text" id="passwordBtn" class="message" value="获取验证码" onfocus="this.blur()" />
                    </span>
                </p>
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

            <p class="popInput">
                <label class="popName">手机号：</label>
                <span class="popTel"><span id="showAreaCode2">${userMessage.phoneAreaCode }</span><span id="showPhone2">${userMessage.phoneNumber }</span></span>
            </p>
            <p class="popInput">
                <label class="popName">手机验证码<span class="star">*</span>：</label>
                <span class="popCode">
                        <input type="text" class="code" placeholder="6位短信验证码" id="pasVerifyCode" maxlength="6"
                               onkeyup="value=value.replace(/[^\d]/g,'')" onblur="value=value.replace(/[^\d]/g,'')"/>
                        <input type="text" id="changeBtn" class="message" value="获取验证码" onfocus="this.blur()" />
                    </span>
            </p>
            <div class="buttons">
                <input type="text" value="取&nbsp;消" class="cancel" onfocus="this.blur()" />
                <input type="text" value="确&nbsp;定" class="yes" onfocus="this.blur()" onclick="updateLogPassword()" />
            </div>
        </div>

        <div class="changePhone_pop">
            <p class="popTitle">修改手机</p>
            <p class="popInput">
                <label class="popName">原手机号：</label>
                <span class="popTel"><span id="showAreaCode3">${userMessage.phoneAreaCode }</span><span id="showPhone3">${userMessage.phoneNumber }</span></span>
            </p>
            <p class="popInput">
                <label class="popName">手机验证码<span class="star">*</span>：</label>
                <span class="popCode">
                    <input type="text" class="code" placeholder="原手机接收6位短信验证码" id="verifyCode" maxlength="6"
                           onkeyup="value=value.replace(/[^\d]/g,'')" onblur="value=value.replace(/[^\d]/g,'')"/>
                    <input type="text" id="changeTel_btn" class="message" value="获取验证码" onfocus="this.blur()" />
                </span>
            </p>
            <p class="popInput">
                <label class="popName">新手机号<span class="star">*</span>：</label>
                <span class="popCode">
                    <span class="select">
                        <span class="selectCont" id="areaCode">+86</span>
                        <img src="<%=path %>/resources/image/web/area.png" alt=""/>
                        <span class="selectUl">
                            <c:forEach items="${phoneAreaMap}" var="phoneArea">
                               <span class="selectLi">
                                   <span class="selectName">${phoneArea.value }</span>
                                   <span class="selectNumber">${phoneArea.key }</span>
                               </span>
                            </c:forEach>
                        </span>
                    </span>
                    <input type="text" class="telNumber" placeholder="请输入您的手机号" maxlength="11" id="bindingMobile"
                           onkeyup="value=value.replace(/[^\d]/g,'')" onblur="value=value.replace(/[^\d]/g,'')"/>
                </span>
            </p>
            <p class="popInput">
                <label class="popName">手机验证码<span class="star">*</span>：</label>
                <span class="popCode">
                    <input type="text" class="code" placeholder="新手机接收6位短信验证码" maxlength="6" id="newVerifyCode"
                           onkeyup="value=value.replace(/[^\d]/g,'')" onblur="value=value.replace(/[^\d]/g,'')"/>
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
        if (code != 1 && message != "") {
            openTips(message);
            return false;
        }
    }
    if($("#orderType").val() == 1){
            $(".choose").css("display" , "inline-block");
    }else{
        $(".choose").hide();
    }
        if(document.getElementById("bankBox").checked){
            $("#cardDiv").show()
        }else{
            $("#cardDiv").hide()
        }
        if(document.getElementById("alipayBox").checked){
            $("#alipayDiv").show()
        }else{
            $("#alipayDiv").hide()
        }
        if(document.getElementById("wechatBox").checked){
            $("#wechatDiv").show()
        }else{
            $("#wechatDiv").hide()
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

            passwordPop = encode64(passwordPop);
            newPasswordPop = encode64(newPasswordPop);
            repPasswordPop = encode64(repPasswordPop);

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
                    openTips(result.message);
                    if(result.code == 1) {
                        $("#payPass").attr('src',"<%=path %>" + "/resources/image/web/pass.png");
                        $(".mask").fadeOut("fast");
                        $(".changePay_pop").fadeOut("fast");
                        $("#passwordPop").val("");
                        $("#newPasswordPop").val("");
                        $("#repPasswordPop").val("");

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

            newPasswordTel = encode64(newPasswordTel);
            repPasswordTel = encode64(repPasswordTel);
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
                        $("#payPass").attr('src',"<%=path %>" + "/resources/image/web/pass.png");
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
    //修改登录密码
    function updateLogPassword() {
        var password = $("#password").val();
        var newPassword = $("#newPassword").val();
        var repPassword = $("#repPassword").val();
        var pasVerifyCode = $("#pasVerifyCode").val();

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

        if(pasVerifyCode == ""){
            openTips("请输入验证码");
            return;
        }

        if(pasVerifyCode.length != 6){
            openTips("验证码为6位");
            return;
        }

        if(updateLogPasswordBoo){
            openTips("系统正在更新登录密码，请稍后");
            return;
        } else {
            updateLogPasswordBoo = true;
        }

        password = encode64(password);
        newPassword = encode64(newPassword);
        repPassword = encode64(repPassword);

        $.ajax({
            url: '<%=path %>' + "/userWeb/userMessage/updateLogPassword.htm",
            type:'post',
            dataType:'json',
            async:true,
            data:{
                password : password,
                newPassword : newPassword,
                repetitionPassword : repPassword,
                pasVerifyCode : pasVerifyCode
            },
            success:function(result){
                openTips(result.message);
                updateLogPasswordBoo = false;
                if(result.code == 1) {
                    $(".mask").fadeOut("fast");
                    $(".changePassword_pop").fadeOut("fast");
                    $("#password").val("");
                    $("#newPassword").val("");
                    $("#repPassword").val("");
                    $("#pasVerifyCode").val("");
                    setTimeout("webLogin()",1000 );
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
        var newVerifyCode = $("#newVerifyCode").val();
        var areaCode = $("#areaCode").html();
        var phonePassword = $("#phonePassword").val();

        if(bindingMobile == ""){
            openTips("请输入手机号");
            return;
        }

        if(areaCode == "+86"){
            if(!regPos.test(bindingMobile) || bindingMobile.length > 11 || bindingMobile.length <= 10){
                openTips("请输入正确手机号");
                return;
            }
        } else {
            if(!regPos.test(bindingMobile) || (bindingMobile.length + areaCode.length) > 14 || bindingMobile.length <= 5 || bindingMobile.length > 11){
                openTips("请输入正确手机号");
                return;
            }
        }

        if(verifyCode == ""){
            openTips("请输入原手机验证码");
            return;
        }

        if(verifyCode.length < 6){
            openTips("原手机验证码为六位");
            return;
        }

        if(newVerifyCode == ""){
            openTips("请输入新手机验证码");
            return;
        }

        if(newVerifyCode.length < 6){
            openTips("新手机验证码为六位");
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

        phonePassword = encode64(phonePassword);

        $.ajax({
            url: '<%=path %>' + "/userWeb/userMessage/updatePasswordByPhone.htm",
            type:'post',
            dataType:'json',
            async:true,
            data:{
                phone : bindingMobile,
                areaCode : areaCode,
                validateCode : verifyCode,
                newVerifyCode : newVerifyCode,
                password : phonePassword
            },
            success:function(result){
                updatePhoneNumberBoo = false;
                if(result.code == 1) {
                    openTips(result.message);
                    var phone = bindingMobile.substring(0,3) + "***" + bindingMobile.substring(bindingMobile.length - 3);
                    $("#showPhone").text(phone);
                    $("#showMobilePhone").text(phone);
                    $("#showAreaCode").text(areaCode);
                    $("#showPhone2").text(phone);
                    $("#showAreaCode2").text(areaCode);
                    $("#showPhone3").text(phone);
                    $("#showAreaCode3").text(areaCode);
                    $(".mask").fadeOut("fast");
                    $(".changePhone_pop").fadeOut("fast");
                    $("#bindingMobile").val("");
                    $("#verifyCode").val("");
                    $("#areaCode").val("+86");
                    $("#phonePassword").val("");
                    $("#newVerifyCode").val("");
                } else {
                    openTips(result.message);
                }
            }, error:function(){
                updatePhoneNumberBoo = false;
                openTips("系统错误！");
            }
        });
    }

    //返回登录页
    function webLogin(){
        window.location.href = "<%=path%>" + "/userWeb/userLogin/loginOut.htm";
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
        $(".add_adv").click(function(){
            $(".mask").fadeIn();
            $(".add_pop").fadeIn();
            popObj = ".add_pop"
        });
        $(".confirm").click(function(){
            $("#addOrderConfirmId").attr("disabled",true);
            var orderType = $("#orderType").val();//判断如果是  出售单  直接执行提交方法
            var currencyId = $("#currencyId").val();//币种id
            var orderType = $("#orderType").val();//购买 1  出售 2  针对用户而言  经销商相反
            var area = $("#area").val();//挂单比例
            var pendingRatio = $("#pendingRatio").val();//挂单比例
            var minNumber = $("#minNumber").val();//最小限额
            var maxNumber = $("#maxNumber").val();//最大限额
            if(currencyId == null){
                $("#addOrderConfirmId").attr("disabled",false);
                openTips("请选择币种");
                return;
            }
            if(orderType == null){
                $("#addOrderConfirmId").attr("disabled",false);
                openTips("请选择类型");
                return;
            }
            if(area == null){
                $("#addOrderConfirmId").attr("disabled",false);
                openTips("请选择地区");
                return;
            }
            if(pendingRatio == ""){
                $("#addOrderConfirmId").attr("disabled",false);
                openTips("请输入挂单比例");
                return;
            }
            if(pendingRatio <= 0){
                $("#addOrderConfirmId").attr("disabled",false);
                openTips("挂单比例要大于0");
                return;
            }
            if(minNumber == ""){
                $("#addOrderConfirmId").attr("disabled",false);
                openTips("请输入最低限额");
                return;
            }
            if(minNumber <= 0){
                $("#addOrderConfirmId").attr("disabled",false);
                openTips("最低限额要大于0");
                return;
            }
            if(maxNumber == ""){
                $("#addOrderConfirmId").attr("disabled",false);
                openTips("请输入最高限额");
                return;
            }
            if(maxNumber <= 0){
                $("#addOrderConfirmId").attr("disabled",false);
                openTips("最高限额要大于0");
                return;
            }
            if(Number(pendingRatio) > Number(999999.99)){
                $("#addOrderConfirmId").attr("disabled",false);
                openTips("挂单比例要小于一百万");
                return;
            }
            if(Number(maxNumber) > Number(999999.99)){
                $("#addOrderConfirmId").attr("disabled",false);
                openTips("最高限额要小于一百万");
                return;
            }
            if(Number(maxNumber) <= Number(minNumber)){
                $("#addOrderConfirmId").attr("disabled",false);
                openTips("最高限额要大于最低限额");
                return;
            }
            if(accMul(accMul(pendingRatio,0.0001),10000) > accMul(maxNumber,10000)){
                $("#addOrderConfirmId").attr("disabled",false);
                openTips("最高限额过小");
                return;
            }
            if(orderType == 2){
             addOrder();
            }else{
                    if(document.getElementById("bankBox").checked==false&&document.getElementById("alipayBox").checked==false&&document.getElementById("wechatBox").checked==false){
                        $("#addOrderConfirmId").attr("disabled",false);
                        openTips("请勾选支付方式");
                        return;
                    }
            $(".mask").fadeIn();
            $(".add_pop").hide();
            $(".pay_pop").fadeIn();
            popObj = ".pay_pop"
            }
        });
        $(".back").click(function(){
            $("#addOrderConfirmId").attr("disabled",false);
            $(".mask").fadeIn();
            $(".add_pop").fadeIn();
            $(".pay_pop").hide();
            popObj = ".add_pop"
        });
        $(".delete").click(function(){
            $(".mask").fadeIn();
            $(".delete_pop").fadeIn();
            popObj = ".delete_pop"
        });
        $(".cancel").click(function(){
            $(".mask").fadeOut("fast");
            $(popObj).fadeOut("fast");

       //清空数据


            $("#currencyId").val(0);//币种id
            $("#orderType").val(0);//购买 1  出售 2  针对用户而言  经销商相反
            $("#area").val(0);//挂单比例
            $("#pendingRatio").val('');//挂单比例
            $("#minNumber").val('');//最小限额
            $("#maxNumber").val('');//最大限额
            $("#bankAccount").val('');//收款银行账号
            $("#alipayAccount").val('');//支付宝账号
            $("#wechatAccount").val('');//微信账号
            $("#bankName").val('');//收款银行
            $("#bankBranch").val('');//收款支行
            $("#paymentName").val('');//收款人姓名
            $("#paymentPhone").val('');//收款人手机号
            $("#changead_t1").val('');
            $("#changead_t2").val('');
            $(".choose").hide();
            document.getElementById("bankBox").checked = false;
            document.getElementById("alipayBox").checked = false;
            document.getElementById("wechatBox").checked = false;
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

    $(function(){
        $('.select').click(function(){
            $('.selectUl').addClass('selected');
        });
        $('.selectLi').click(function(e){
            e = e || window.event;
            if (e.stopPropagation) {
                e.stopPropagation();
            } else {
                e.cancelBubble = true;
            }
            $('.selectUl').removeClass('selected');
            $('.selectCont').html( $(this).children('.selectNumber').html());
        });
        $('.select').mouseleave(function(){
            $('.selectUl').removeClass('selected');
        });
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
                var areaCode = $("#areaCode").html();
                if(bindingMobile == ""){
                    openTips("请输入手机号");
                    return;
                }
                if(areaCode == "+86"){
                    if(!regPos.test(bindingMobile) || bindingMobile.length > 11 || bindingMobile.length <= 10){
                        openTips("请输入正确手机号");
                        return;
                    }
                } else {
                    if(!regPos.test(bindingMobile) || (bindingMobile.length + areaCode.length) > 14 || bindingMobile.length <= 5 || bindingMobile.length > 11){
                        openTips("请输入正确手机号");
                        return;
                    }
                }
                bindingMobile = areaCode + bindingMobile;
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

    function countDown(o, times) {
        var timer=null;
        o.setAttribute("disabled", true);
        o.value="重新发送(" + times + ")";

        timer=setInterval(function(){
            if(times > 1){
                times--;
                o.value="重新发送(" + times + ")";
                if( times == 59){

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
            }else{
                o.removeAttribute("disabled");
                o.value="获取验证码";
                clearInterval(timer);
            }

        },1000);
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

    document.getElementById("changeBtn").onclick=function(){countDown(this, 60);};
    document.getElementById("changeTel_btn").onclick=function(){countDown(this, 60);};
    document.getElementById("phoneBtn").onclick=function(){time(this);};
    document.getElementById("passwordBtn").onclick=function(){countDown(this, 60);};

    function openOrderDelete(otcPendingOrderNo) {
        $("#deleteOrderId").val(otcPendingOrderNo);
    }

    var deleteOrderBoo = false;
    //删除订单
    function deleteOrder() {
        if (deleteOrderBoo) {
            return;
        } else {
            deleteOrderBoo = true;
        }
        var deleteOrderId = $("#deleteOrderId").val();
        $.ajax({
            url: '<%=path %>' + "/userWeb/userMessage/deleteOtcTransactionPendOrder.htm",
            data: {
                otcPendingOrderNo : deleteOrderId,
            },//参数
            dataType: "json",
            type: 'POST',
            async: true, //默认异步调用 (false：同步)
            success: function (resultData) {
                deleteOrderBoo = false;
                var code = resultData.code;
                var message = resultData.message;
                if (code == -1) {
                    openTipsLoad(message);
                    return;
                }else if (code != 1 && message != "") {
                    deleteOrderBoo = false;
                    openTips(message);
                    return;
                }else{
                    $("#"+deleteOrderId).remove();
                    openTips(message);
                    $(".delete_pop").hide();
                    $(".mask").hide();
                }

            },

            error: function () {
                deleteOrderBoo = false;
                openTips("数据加载出错，请稍候重试");
            }
        });
    }

    function changeOrderType(valueNum){
        if(valueNum == 1){
            $(".choose").css("display" , "inline-block");
        }
        if(valueNum ==2){
            $(".choose").hide();
        }
    }

    var addOrderBoo = false;
//添加订单
    function addOrder() {
        if (addOrderBoo) {
            return;
        } else {
            addOrderBoo = true;
        }

        $("#addOrderId").attr("disabled",true);

        var currencyId = $("#currencyId").val();//币种id
        var orderType = $("#orderType").val();//购买 1  出售 2  针对用户而言  经销商相反
        var area = $("#area").val();//挂单比例
        var pendingRatio = $("#pendingRatio").val();//挂单比例
        var minNumber = $("#minNumber").val();//最小限额
        var maxNumber = $("#maxNumber").val();//最大限额
        var bankAccount = $("#bankAccount").val();//收款银行账号
        var alipayAccount = $("#alipayAccount").val();//支付宝账号
        var wechatAccount = $("#wechatAccount").val();//微信账号
        var bankName = $("#bankName").val();//收款银行
        var bankBranch = $("#bankBranch").val();//收款支行
        var paymentName = $("#paymentName").val();//收款人姓名
        var paymentPhone = $("#paymentPhone").val();//收款人手机号
        var alipayImageUrl = document.getElementById("changead_a1").files[0];
        var wechatImageUrl = document.getElementById("changead_a2").files[0];//微信图片
        if(currencyId == null){
            $("#addOrderConfirmId").attr("disabled",false);
            $("#addOrderId").attr("disabled",false);
            openTips("请选择币种");
            return;
        }
        if(orderType == null){
            $("#addOrderConfirmId").attr("disabled",false);
            $("#addOrderId").attr("disabled",false);
            addOrderBoo =false;
            openTips("请选择类型");
            return;
        }
        if(area == null){
            $("#addOrderConfirmId").attr("disabled",false);
            $("#addOrderId").attr("disabled",false);
            addOrderBoo =false;
            openTips("请选择地区");
            return;
        }
        if(pendingRatio == ""){
            $("#addOrderConfirmId").attr("disabled",false);
            $("#addOrderId").attr("disabled",false);
            addOrderBoo =false;
            openTips("请输入挂单比例");
            return;
        }
        if(pendingRatio <= 0){
            $("#addOrderConfirmId").attr("disabled",false);
            $("#addOrderId").attr("disabled",false);
            addOrderBoo =false;
            openTips("挂单比例要大于0");
            return;
        }
        if(minNumber == ""){
            $("#addOrderConfirmId").attr("disabled",false);
            $("#addOrderId").attr("disabled",false);
            addOrderBoo =false;
            openTips("请输入最低限额");
            return;
        }
        if(minNumber < 0){
            $("#addOrderConfirmId").attr("disabled",false);
            $("#addOrderId").attr("disabled",false);
            addOrderBoo =false;
            openTips("最低限额不能小于0");
            return;
        }
        if(maxNumber == ""){
            $("#addOrderConfirmId").attr("disabled",false);
            $("#addOrderId").attr("disabled",false);
            addOrderBoo =false;
            openTips("请输入最高限额");
            return;
        }
        if(maxNumber <= 0){
            $("#addOrderConfirmId").attr("disabled",false);
            $("#addOrderId").attr("disabled",false);
            addOrderBoo =false;
            openTips("最高限额要大于0");
            return;
        }
        if(Number(maxNumber) <= Number(minNumber)){
            $("#addOrderConfirmId").attr("disabled",false);
            $("#addOrderId").attr("disabled",false);
            addOrderBoo =false;
            openTips("最高限额要大于最低限额");
            return;
        }
        //若为购买类型 需验证付款方式
        if(orderType == 1){
        // 选种的付款方式做进一步校验
        //银行
        if(document.getElementById("bankBox").checked){
            if(bankAccount == ""){
                $("#addOrderConfirmId").attr("disabled",false);
                $("#addOrderId").attr("disabled",false);
                addOrderBoo =false;
                openTips("请输入银行卡号");
                return;
            }
            if(bankAccount.length < 16 || bankAccount.length > 19){
                $("#addOrderConfirmId").attr("disabled",false);
                $("#addOrderId").attr("disabled",false);
                addOrderBoo =false;
                openTips("银行卡号位数不正确");
                return;
            }
            if(bankName == ""){
                $("#addOrderConfirmId").attr("disabled",false);
                $("#addOrderId").attr("disabled",false);
                addOrderBoo =false;
                openTips("请输入银行名称");
                return;
            }
            if(bankBranch == ""){
                $("#addOrderConfirmId").attr("disabled",false);
                $("#addOrderId").attr("disabled",false);
                addOrderBoo =false;
                openTips("请输入银行支行");
                return;
            }
            if(paymentName == ""){
                $("#addOrderConfirmId").attr("disabled",false);
                $("#addOrderId").attr("disabled",false);
                addOrderBoo =false;
                openTips("请输入银行卡预留姓名");
                return;
            }
            if(paymentPhone == ""){
                $("#addOrderConfirmId").attr("disabled",false);
                $("#addOrderId").attr("disabled",false);
                addOrderBoo =false;
                openTips("请输入银行卡预留手机号");
                return;
            }
            if(paymentPhone.length != 11){
                $("#addOrderConfirmId").attr("disabled",false);
                $("#addOrderId").attr("disabled",false);
                addOrderBoo =false;
                openTips("请输入11位手机号");
                return;
            }
        }
        //支付宝
        if(document.getElementById("alipayBox").checked){
            if(alipayAccount == ""){
                $("#addOrderConfirmId").attr("disabled",false);
                $("#addOrderId").attr("disabled",false);
                addOrderBoo =false;
                openTips("请输入支付宝账户");
                return;
            }
            var alipayImageUrlStr = $("#changead_t1").val();
            if (alipayImageUrlStr == null || alipayImageUrlStr == '') {
                $("#addOrderConfirmId").attr("disabled",false);
                $("#addOrderId").attr("disabled",false);
                addOrderBoo =false;
                return openTips("请上传支付宝收款码图片");
            }
        }
        //微信
        if(document.getElementById("wechatBox").checked){
            $("#addOrderConfirmId").attr("disabled",false);
            $("#addOrderId").attr("disabled",false);
            if(wechatAccount == ""){
                addOrderBoo =false;
                openTips("请输入微信账户");
                return;
            }
            var wechatImageUrlStr = $("#changead_t2").val();
            if (wechatImageUrlStr == null || wechatImageUrlStr == '') {
                $("#addOrderConfirmId").attr("disabled",false);
                $("#addOrderId").attr("disabled",false);
                addOrderBoo =false;
                return openTips("请上传微信收款码图片");
            }
        }
        }
        var formData = new FormData();
        formData.append("currencyId", currencyId);
        formData.append("orderType", orderType);
        formData.append("pendingRatio", pendingRatio);
        formData.append("minNumber", minNumber);
        formData.append("maxNumber", maxNumber);
        formData.append("bankAccount", bankAccount);
        formData.append("alipayAccount", alipayAccount);
        formData.append("wechatAccount", wechatAccount);
        formData.append("bankName", bankName);
        formData.append("bankBranch", bankBranch);
        formData.append("paymentName", paymentName);
        formData.append("paymentPhone", paymentPhone);
        formData.append("alipayImageUrl", alipayImageUrl);
        formData.append("wechatImageUrl", wechatImageUrl);
        $.ajax({
            url: '<%=path %>' + "/userWeb/userMessage/otcReleaseOrder.htm",
            data: formData,//参数
            dataType: "json",
            type: 'POST',
            async: true, //默认异步调用 (false：同步)
            processData : false,
            contentType : false,
            success: function (resultData) {
                addOrderBoo =false;
                var code = resultData.code;
                var message = resultData.message;
                if (code == -1) {
                    $("#addOrderConfirmId").attr("disabled",false);
                    openTipsLoad(message);
                    return;
                }else if (code != 1 && message != "") {
                    $("#addOrderConfirmId").attr("disabled",false);
                    addOrderBoo = false;
                    openTips(message);
                    return;
                }else{
                    location.reload(true)
                }

            },

            error: function () {
                addOrderBoo =false;
                openTips("数据加载出错，请稍候重试");
            }
        });
    }
    //判断图片格式
    function checkFileImageTwo(target, id){
        var flag = false;
        flag = checkFileImage(target);
        document.getElementById(id).value = target.value;
        if(flag == true){
            document.getElementById(id).value = target.value;
        }
    }
    //判断图片格式
    function checkFileImage(target) {
        var fileSize = 0;
        var filetypes = [".jpg", ".jpeg", ".png", ".JPG", ".JPEG", ".PNG"];
        var filepath = target.value;
        var filemaxsize = 1024 * 3;//3M
        if (filepath) {
            var isnext = false;
            var fileend = filepath.substring(filepath.lastIndexOf("."));
            if (filetypes && filetypes.length > 0) {
                for (var i = 0; i < filetypes.length; i++) {
                    if (filetypes[i] == fileend) {
                        isnext = true;
                        break;
                    }
                }
            }
            if (!isnext) {
                openTips("图片格式必须是jpeg,jpg,png中的一种！");
                target.value = "";
                return false;
            }
        } else {
            return false;
        }
        if (!target.files) {
            var filePath = target.value;
            var fileSystem = new ActiveXObject("Scripting.FileSystemObject");
            if (!fileSystem.FileExists(filePath)) {
                openTips("图片不存在，请重新输入！");
                return false;
            }
            var file = fileSystem.GetFile(filePath);
            fileSize = file.Size;
        } else {
            fileSize = target.files[0].size;
        }

        var size = fileSize / 1024;
        if (size > filemaxsize) {
            openTips("图片大小不能大于" + filemaxsize / 1024 + "M！");
            target.value = "";
            return false;
        }
        if (size <= 0) {
            openTips("图片大小不能为0M！");
            target.value = "";
            return false;
        }

        return true;
    }

    $("#bankBox").click(function(){
        if(document.getElementById("bankBox").checked){
            $("#cardDiv").show()
        }else{
            $("#bankAccount").val('');//收款银行账号
            $("#bankName").val('');//收款银行
            $("#bankBranch").val('');//收款支行
            $("#paymentName").val('');//收款人姓名
            $("#paymentPhone").val('');//收款人手机号
            $("#cardDiv").hide()
        }
    });
    $("#alipayBox").click(function(){
        if(document.getElementById("alipayBox").checked){
            $("#alipayDiv").show()
        }else{
            $("#alipayAccount").val('');//支付宝账号
            $("#changead_t1").val('');
            $("#alipayDiv").hide()
        }
    });
    $("#wechatBox").click(function(){
        if(document.getElementById("wechatBox").checked){
            $("#wechatDiv").show()
        }else{
            $("#wechatAccount").val('');//微信账号
            $("#changead_t2").val('');
            $("#wechatDiv").hide()
        }
    });

    var mapMatch = {};
    mapMatch['number'] = /[^\d]/g;
    mapMatch['ENumber'] = /[^\a-\z\A-\Z\d]/g;
    mapMatch['double'] = true;
    mapMatch['phone'] = /[^\d]/g;
    mapMatch['email'] = /([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\.[a-zA-Z0-9_-]{2,3}){1,2})$/;
    function matchUtil(o, str, nu) {
        if(mapMatch[str] === true){
            matchDouble(o, nu);
        }else {
            o.value = o.value.replace(mapMatch[str], '');
        }
    }
    function matchDouble(o, num){
        var matchStr = /^((?:0\.\d*[1-9]|(?!0)\d+(?:\.\d*[1-9])?)|0)-(?:0\.\d*[1-9]|(?!0)\d+(?:\.\d*[1-9])?)$/;
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

    var mapMatch = {};
    mapMatch['number'] = /[^\d]/g;
    mapMatch['ENumber'] = /[^\a-\z\A-\Z\d]/g;
    mapMatch['double'] = true;
    mapMatch['phone'] = /[^\d]/g;
    mapMatch['email'] = /([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\.[a-zA-Z0-9_-]{2,3}){1,2})$/;
    function matchUtil(o, str, nu) {
        if(mapMatch[str] === true){
            matchDouble(o, nu);
        }else {
            o.value = o.value.replace(mapMatch[str], '');
        }
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
    function openTipsLoad(showText)
    {
        var bgHeight = $(document).height()
        $(".tipsMask").remove();
        var str = "<div class='tipsMask'><div class='tips_content'><div class='empty_pop'><p class='tipsMaskText'></p>"+
            "<div class='Button'><input type='text' class='tipsYes' value='确&nbsp;&nbsp;定' onfocus='this.blur()'/>"+
            "</div></div></div></div>";
        $("body").append(str);
        $(".tipsMaskText").html("<span>" + showText + "</span>");
        $(".tipsMask").css("display","block");
        $(".tipsYes").click(function(){
            location.reload(true)
        })
    }

    function accMul(arg1,arg2){
        var m=0,s1=arg1.toString(),s2=arg2.toString();
        try{m+=s1.split(".")[1].length}catch(e){}
        try{m+=s2.split(".")[1].length}catch(e){}
        return Number(s1.replace(".",""))*Number(s2.replace(".",""))/Math.pow(10,m)
    }
</script>

</body>
</html>
