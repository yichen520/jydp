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

            <form id="queryForm" action="<%=path %>/backerWeb/transactionCurrency/show.htm" method="post">
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
                        <select class="askSelect" id="paymentType" name="paymentType">
                            <option value="0">全部</option>
                            <option value="1">正常</option>
                            <option value="2">停牌</option>
                        </select>
                    </p>
                    <p class="condition">上线状态：
                        <select class="askSelect" id="upStatus" name="upStatus">
                            <option value="0">全部</option>
                            <option value="1">待上线</option>
                            <option value="2">上线中</option>
                            <option value="3">停牌</option>
                            <option value="4">已下线</option>
                        </select>
                    </p>
                    <p class="condition">
                        管理员账号：<input type="text" class="askInput" id="backerAccount" name="backerAccount"
                                     maxlength="16" onkeyup="value=value.replace(/[^a-zA-Z0-9]/g,'')" value="${backAccount}"/></p>
                    <p class="condition">
                        添加时间：
                        从&nbsp;<input placeholder="请选择起始时间" class="askTime" id="startAddTime" name="startAddTime"
                                      value="${startAddTime}" onfocus="this.blur()"/>
                        到&nbsp;<input placeholder="请选择结束时间" class="askTime" id="endAddTime" name="endAddTime"
                                      value="${endAddTime}" onfocus="this.blur()"/>
                    </p>
                    <p class="condition">
                        上线时间：
                        从&nbsp;<input placeholder="请选择起始时间" class="askTime" id="startUpTime" name="startUpTime"
                                      value="${startUpTime}" onfocus="this.blur()"/>
                        到&nbsp;<input placeholder="请选择结束时间" class="askTime" id="endUpTime" name="endUpTime"
                                      value="${endUpTime}" onfocus="this.blur()"/>
                    </p>

                    <input type="hidden" id="queryPageNumber" name="pageNumber" value="${pageNumber}">
                    <input type="text" value="查&nbsp;询" class="ask" onfocus="this.blur()" onclick="queryForm()"/>
                    <c:if test="${backer_rolePower['104004'] == 104004}">
                        <input type="text" value="导&nbsp;出" class="educe" onfocus="this.blur()" onclick="dowland()"/>
                    </c:if>
                </div>
            </form>
        </div>

        <div class="bottom">
            <table class="table" cellspacing="0" cellpadding="0">
                <tr class="tableTitle">
                    <td class="time">添加时间</td>
                    <td class="coin">币种信息</td>
                    <td class="coinLogo">币种徽标</td>
                    <td class="service">手续费</td>
                    <td class="state">交易状态</td>
                    <td class="time">上线时间</td>
                    <td class="state">上线状态</td>
                    <td class="account">管理员信息</td>
                    <td class="operate">操作</td>
                </tr>
                <c:forEach items="${transactionCurrencyVOList}" var="item">
                    <tr class="tableInfo">
                        <td class="time"><fmt:formatDate type="time" value="${item.addTime}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
                        <td class="coin">
                            <p>名称：${item.currencyName}</p>
                            <p>英文标识：${item.currencyShortName}</p>
                            <p>上市指导价：$<fmt:formatNumber type="number" value="${item.guidancePrice}" maxFractionDigits="6"/></p>
                        </td>
                        <td class="coinLogo"><img src="${item.currencyImgUrl}" /></td>
                        <td class="service">
                            <p>买入：<fmt:formatNumber type="number" value="${item.buyFee * 100}" maxFractionDigits="6"/>%</p>
                            <p>卖出：<fmt:formatNumber type="number" value="${item.sellFee * 100}" maxFractionDigits="6"/>%</p>
                        </td>
                        <c:if test="${item.paymentType == 1}">
                            <td class="state">正常</td>
                        </c:if>
                        <c:if test="${item.paymentType == 2}">
                            <td class="state">停牌</td>
                        </c:if>
                        <td class="time"><fmt:formatDate type="time" value="${item.upTime}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
                        <c:if test="${item.upStatus == 1}">
                            <td class="state">待上线</td>
                        </c:if>
                        <c:if test="${item.upStatus == 2}">
                            <td class="state">上线中</td>
                        </c:if>
                        <c:if test="${item.upStatus == 3}">
                            <td class="state">停牌</td>
                        </c:if>
                        <c:if test="${item.upStatus == 4}">
                            <td class="state">已下线</td>
                        </c:if>
                        <td class="account">
                            <p>管理员账号：${item.backerAccount}</p>
                            <p>IP:${item.ipAddress}</p>
                        </td>
                        <td class="operate">
                            <p>
                                <c:if test="${item.rankNumber != 1}">
                                    <input type="text" value="上&nbsp;移" class="adUp" onfocus="this.blur()" onclick="upMove('${item.currencyId}')"/>
                                </c:if>
                                <c:if test="${item.rankNumber != totalCurrNumber}">
                                    <input type="text" value="下&nbsp;移" class="adDown" onfocus="this.blur()" onclick="downMove('${item.currencyId}')"/>
                                </c:if>
                                <c:if test="${item.rankNumber != 1}">
                                    <input type="text" value="置&nbsp; 顶" class="toTop" onfocus="this.blur()" onclick="topMove('${item.currencyId}')"/>
                                </c:if>
                            </p>
                            <c:if test="${item.paymentType != 2}">
                                <input type="text" value="停&nbsp;牌" class="stop" onfocus="this.blur()" onclick="goPayType('${item.currencyId}', 2)"/>
                            </c:if>
                            <c:if test="${item.upStatus == 1 || item.upStatus == 4}">
                                <input type="text" value="上&nbsp;线" class="online" onfocus="this.blur()" onclick="goEditUpStatus('${item.currencyId}', 2)"/>
                            </c:if>
                            <c:if test="${item.upStatus != 4}">
                                <input type="text" value="下&nbsp;线" class="offLine" onfocus="this.blur()" onclick="goEditUpStatus('${item.currencyId}', 4)"/>
                            </c:if>
                            <c:if test="${item.paymentType == 2 && item.upStatus == 3}">
                                <input type="text" value="复&nbsp;牌" class="start" onfocus="this.blur()" onclick="goPayType('${item.currencyId}', 1)"/>
                            </c:if>
                                <input type="text" value="修&nbsp;改" class="change" onfocus="this.blur()"
                                   onclick="goUpdate('${item.currencyId}', '${item.currencyName}','${item.currencyShortName}', '${item.buyFee}', '${item.sellFee}', '${item.upTimeStr}', '${item.upStatus}', '${item.guidancePrice}', '${item.reCode}')"/>
                        </td>
                    </tr>
                </c:forEach>
            </table>

            <jsp:include page="/resources/page/common/paging.jsp"></jsp:include>
        </div>
    </div>
</div>


<div id="footer"></div>


<div class="mask">
    <div class="mask_content">
        <div class="start_pop">
            <p class="popTitle">复牌操作</p>
            <p class="popTips"><img src="<%=path %>/resources/image/back/tips.png" class="tipsImg" />确定复牌该币种？</p>

            <div class="buttons">
                <input type="text" value="取&nbsp;消" class="cancel" onfocus="this.blur()" />
                <input type="text" value="确&nbsp;定" class="yes" onfocus="this.blur()" onclick="editPayType()" />
            </div>
        </div>

        <div class="stop_pop">
            <p class="popTitle">停牌操作</p>
            <p class="popTips"><img src="<%=path %>/resources/image/back/tips.png" class="tipsImg" />确定停牌该币种？</p>

            <div class="buttons">
                <input type="text" value="取&nbsp;消" class="cancel" onfocus="this.blur()" />
                <input type="text" value="确&nbsp;定" class="yes" onfocus="this.blur()" onclick="editPayType()" />
            </div>
        </div>

        <div class="online_pop">
            <p class="popTitle">上线操作</p>
            <p class="popTips"><img src="<%=path %>/resources/image/back/tips.png" class="tipsImg" />确定立即上线该币种？</p>

            <div class="buttons">
                <input type="text" value="取&nbsp;消" class="cancel" onfocus="this.blur()" />
                <input type="text" value="确&nbsp;定" class="yes" onfocus="this.blur()" onclick="editUpStatus()" />
            </div>
        </div>

        <div class="offLine_pop">
            <p class="popTitle">下线操作</p>
            <p class="popTips"><img src="<%=path %>/resources/image/back/tips.png" class="tipsImg" />确定下线该币种？</p>

            <div class="buttons">
                <input type="text" value="取&nbsp;消" class="cancel" onfocus="this.blur()" />
                <input type="text" value="确&nbsp;定" class="yes" onfocus="this.blur()" onclick="editUpStatus()" />
            </div>
        </div>

        <div class="add_pop">
            <p class="popTitle">新增币种</p>
            <div class="popMain">
                <p class="popInput">
                    <label class="popName">币种名称<span class="star">*</span></label>
                    <input type="text" class="entry" placeholder="币种名称" id="currencyNameAd" name="currencyNameAd"
                           maxlength="10"/>
                </p>
                <p class="popInput">
                    <label class="popName">英文标识<span class="star">*</span></label>
                    <input type="text" class="entry" placeholder="币种的英文缩写标识" id="currencyShortNameAd" name="currencyShortNameAd"
                           maxlength="10" onkeyup="matchUtil(this, 'ENumber', 6)" onblur="matchUtil(this, 'ENumber', 6)"/>
                </p>
                <p class="popInput">
                    <label class="popName">上市指导价<span class="star">*</span></label>
                    <input type="text" class="entry" placeholder="币种的上市指导价格，单位：$"
                           id="guidAd" name="guidAd" maxlength="10"
                           onkeyup="matchUtil(this, 'double', 2)" onblur="matchUtil(this, 'double', 2)"/>
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
                    <label class="popName">买入手续费<span class="star">*</span></label>
                    <input type="text" class=" percentage" placeholder="买入手续费，无手续费则填“0”"
                           id="buyFeeAd" name="buyFeeAd" maxlength="18"
                           onkeyup="matchUtil(this, 'double', 1)" onblur="matchUtil(this, 'double', 1)"/>%
                </p>
                <p class="popInput">
                    <label class="popName">卖出手续费<span class="star">*</span></label>
                    <input type="text" class="percentage" placeholder="卖出手续费，无手续费则填“0”"
                           id="sellFeeAd" name="sellFeeAd"maxlength="18"
                           onkeyup="matchUtil(this, 'double', 1)" onblur="matchUtil(this, 'double', 1)"/>%
                </p>
                <p class="popInput">
                    <label class="popName">上线时间<span class="star">*</span></label>
                    <label class="immediately">
                        <input type="radio" name="radioBtn" class="radioBtn" value="1"/>立即上线
                    </label>
                    <label class="timeManage">
                        <input type="radio" name="radioBtn" class="radioBtn" value="2"/>
                        <input placeholder="请选择上线时间" class="askTime chooseTime" id="upTimeAd" name="upTimeAd"/>
                    </label>

                </p>
            </div>

            <div class="buttons">
                <input type="text" value="取&nbsp;消" class="cancel" onfocus="this.blur()" />
                <input type="text" value="确&nbsp;定" class="yes" onfocus="this.blur()" onclick="add()" />
            </div>
        </div>

        <div class="change_pop">
            <p class="popTitle">修改币种</p>
            <div class="popMain">
                <p class="popInput">
                    <label class="popName">币种名称<span class="star">*</span></label>
                    <input type="text" class="entry" placeholder="币种名称" id="currencyNameUp" name="currencyNameUp"
                           maxlength="10"/>
                </p>
                <p class="popInput">
                    <label class="popName">英文标识<span class="star">*</span></label>
                    <input type="text" class="entry" placeholder="币种的英文缩写标识" id="currencyShortNameUp" name="currencyShortNameUp"
                           maxlength="10" onkeyup="matchUtil(this, 'ENumber', 6)" onblur="matchUtil(this, 'ENumber', 6)"/>
                </p>
                <p class="popInput" id="inGuid">
                    <label class="popName">上市指导价<span class="star">*</span></label>
                    <input type="text" class="entry" placeholder="币种的上市指导价格，单位：$" id="guidUp" name="guidUp"
                           maxlength="10" onkeyup="matchUtil(this, 'double', 2)" onblur="matchUtil(this, 'double', 2)"/>
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
                    <label class="popName">买入手续费<span class="star">*</span></label>
                    <input type="text" class=" percentage" placeholder="买入手续费，无手续费则填“0”" id="buyFeeUp" name="buyFeeUp"
                           maxlength="18" onkeyup="matchUtil(this, 'double', 1)" onblur="matchUtil(this, 'double', 1)"/>%
                </p>
                <p class="popInput">
                    <label class="popName">卖出手续费<span class="star">*</span></label>
                    <input type="text" class="percentage" placeholder="卖出手续费，无手续费则填“0”" id="sellFeeUp" name="sellFeeUp"
                           maxlength="18" onkeyup="matchUtil(this, 'double', 1)" onblur="matchUtil(this, 'double', 1)"/>%
                </p>
                <p class="popInput" id="inTime">
                    <label class="popName">上线时间<span class="star">*</span></label>
                    <input placeholder="请选择上线时间" class="askTime entry" id="c_onlineTime"/>
                </p>
            </div>

            <div class="buttons">
                <input type="text" value="取&nbsp;消" class="cancel" onfocus="this.blur()" />
                <input type="text" value="确&nbsp;定" class="yes" onfocus="this.blur()" onclick="update()" />
            </div>
        </div>
    </div>
</div>


<script type="text/javascript" src="http://libs.baidu.com/jquery/2.1.4/jquery.min.js"></script>
<script type="text/javascript" src="<%=path %>/resources/js/loadPageBack.js"></script>
<script type="text/javascript" src="<%=path %>/resources/js/simpleTips.js"></script>
<script type="text/javascript" src="<%=path %>/resources/js/laydate.js"></script>

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
        $(".add").click(function(){
            $(".mask").fadeIn();
            $(".add_pop").fadeIn();
            popObj = ".add_pop"
        });
        $(".cancel").click(function(){
            $(".mask").fadeOut("fast");
            $(popObj).fadeOut("fast");
        });
        $(".yes").click(function(){

        });
    });

</script>
<script type="text/javascript">
    window.onload = function() {
        var code = '${code}';
        var message = '${message}';
        if (code != 1 && message != "") {
            openTips(message);
            return ;
        }

        $("#currencyId option").each(function(){
            if($(this).val()=='${currencyName}'){
                $(this).attr('selected',true);
            }
        });
        $("#paymentType option").each(function(){
            if($(this).val()=='${paymentType}'){
                $(this).attr('selected',true);
            }
        });
        $("#upStatus option").each(function(){
            if($(this).val()=='${upStatus}'){
                $(this).attr('selected',true);
            }
        });

    }

    //查询
    function queryForm() {
        $("#queryPageNumber").val("0");
        $("#queryForm").submit();
    }
</script>

<script type="text/javascript">
    var statusS = 0;
    var typeS = 0;
    var currencyId = 0;

    //去修改上线状态
    function goEditUpStatus(currId, status) {
        statusS = status;
        currencyId = currId;

        if (status == 2) {
            $(".mask").fadeIn();
            $(".online_pop").fadeIn();
            popObj = ".online_pop"
        } else if (status == 4) {
            $(".mask").fadeIn();
            $(".offLine_pop").fadeIn();
            popObj = ".offLine_pop"
        }
    }

    //修改上线状态
    edUpBoo = false;
    function editUpStatus() {
       if (edUpBoo) {
           return ;
       } else {
           edUpBoo = true;
       }

        currencyId = parseInt(currencyId);
        statusS = parseInt(statusS);

       if (currencyId == 0 || statusS == 0) {
           edUpBoo = false;
           $(".mask").fadeOut("fast");
           $(popObj).fadeOut("fast");

           openTips("币种信息获取错误");
           return;
       }

        $.ajax({
            url: '<%=path %>' + "/backerWeb/transactionCurrency/editUpType.htm",
            data: {
                currencyId : currencyId,
                upStatus : statusS
            },//参数
            type: 'POST',
            async: true, //默认异步调用 (false：同步)
            success: function (resultData) {
                var code = resultData.code;
                var message = resultData.message;
                if (message != "") {
                    openTips(message);
                }
                if (code == 1){
                    $(".mask").fadeOut("fast");
                    $(popObj).fadeOut("fast");
                    setTimeout(function (){queryForm();}, 1000);
                }
                edUpBoo = false;
            },

            error: function () {
                edUpBoo = false;
                openTips("数据加载出错，请稍候重试");
            }
        });
    }

    //去停复牌页面
    function goPayType(currency, type){
        typeS = type;
        currencyId = currency;

        if (type == 2) {
            $(".mask").fadeIn();
            $(".stop_pop").fadeIn();
            popObj = ".stop_pop"
        } else if (type == 1) {
            $(".mask").fadeIn();
            $(".start_pop").fadeIn();
            popObj = ".start_pop"
        }
    }

    //停复牌
    edPayBoo = false;
    function editPayType() {
        if (edPayBoo) {
            return ;
        } else {
            edPayBoo = true;
        }

        currencyId = parseInt(currencyId);
        typeS = parseInt(typeS);

        if (currencyId == null || typeS == null || currencyId == 0 || typeS == 0) {
            edPayBoo = false;
            $(".mask").fadeOut("fast");
            $(popObj).fadeOut("fast");
            openTips("币种信息获取错误");
            return;
        }

        $.ajax({
            url: '<%=path %>' + "/backerWeb/transactionCurrency/editPayType.htm",
            data: {
                currencyId : currencyId,
                paymentType : typeS
            },//参数
            type: 'POST',
            async: true, //默认异步调用 (false：同步)
            success: function (resultData) {
                var code = resultData.code;
                var message = resultData.message;
                if (message != "") {
                    openTips(message);
                }
                if (code == 1){
                    $(".mask").fadeOut("fast");
                    $(popObj).fadeOut("fast");
                    setTimeout(function (){queryForm();}, 1000);
                }
                edPayBoo = false;
            },

            error: function () {
                edPayBoo = false;
                openTips("数据加载出错，请稍候重试");
            }
        });
    }

    //新增
    var addBoo = false
    function add() {
        if (addBoo) {
            return ;
        } else {
            addBoo = true;
        }

        var currencyNameAd = document.getElementById("currencyNameAd").value;
        var currencyShortNameAd = document.getElementById("currencyShortNameAd").value;
        var buyFeeAd = document.getElementById("buyFeeAd").value;
        var sellFeeAd = document.getElementById("sellFeeAd").value;
        var guidAd = document.getElementById("guidAd").value;
        var changead_t1 = document.getElementById("changead_t1").value;
        var adsImageUrl = document.getElementById("changead_a1").files[0];
        var status = $('input:radio:checked').val();

        if (currencyNameAd == null || currencyNameAd == "") {
            addBoo = false;
            openTips("请输入币种名称");
            return;
        }
        if (currencyShortNameAd == null || currencyShortNameAd == "") {
            addBoo = false;
            openTips("请输入英文标识");
            return;
        }
        if (guidAd == null || guidAd == "" || parseFloat(guidAd) <= 0
            || parseFloat(guidAd) == null || parseFloat(guidAd) == "") {
            addBoo = false;
            openTips("请确认上市指导价大于0");
            return;
        }
        if (changead_t1 == null || changead_t1 == '') {
            addBoo = false;
            openTips("请上传徽标");
            return;
        }
        if (buyFeeAd == null || buyFeeAd == "") {
            addBoo = false;
            openTips("请输入买入手续费");
            return;
        }
        if (sellFeeAd == null || sellFeeAd == "") {
            addBoo = false;
            openTips("请输入卖出手续费");
            return;
        }
        if (status == null || status == "") {
            addBoo = false;
            openTips("请选择执行方式");
            return;
        }
        var staNum = parseInt(status);
        if (staNum == 1) {
            var upTimeAd = null;
        }
        if (staNum == 2) {
            var upTimeAd = document.getElementById("upTimeAd").value;
            if (upTimeAd == null || upTimeAd == "") {
                addBoo = false;
                openTips("请选择正确的时间");
                return;
            }
        }

        var formData = new FormData();
        formData.append("currencyNameAd", currencyNameAd);
        formData.append("currencyShortNameAd", currencyShortNameAd);
        formData.append("buyFeeAd", buyFeeAd);
        formData.append("sellFeeAd", sellFeeAd);
        formData.append("guidAd", guidAd);
        formData.append("adsImageUrl", adsImageUrl);
        formData.append("status", staNum);
        if (staNum == 2) {
            formData.append("upTimeAd", upTimeAd);
        }

        $.ajax({
            url: '<%=path %>' + "/backerWeb/transactionCurrency/add.htm",
            data:formData,//参数
            dataType: "json",
            type: 'POST',
            async: true, //默认异步调用 (false：同步)
            processData : false,
            contentType : false,
            success: function (resultData) {
                var code = resultData.code;
                var message = resultData.message;
                if (message != "") {
                    openTips(message);
                }
                if (code == 1){
                    $(".mask").fadeOut("fast");
                    $(popObj).fadeOut("fast");
                    setTimeout(function (){queryForm();}, 1000);
                }

                addBoo = false;
            },

            error: function () {
                addBoo = false;
                openTips("数据加载出错，请稍候重试");
            }
        });
    }

    //去修改页面
    function goUpdate(currencId, currencyName, currencyShortName, buyFee, sellFee, upTime, upStatus, price, reCode){

        currencyId = currencId;

        document.getElementById("currencyNameUp").value = currencyName;
        document.getElementById("currencyShortNameUp").value = currencyShortName;
        document.getElementById("buyFeeUp").value = buyFee * 100;
        document.getElementById("sellFeeUp").value = sellFee * 100;
        document.getElementById("c_onlineTime").value = upTime;
        document.getElementById("guidUp").value = price;

        if (upStatus == 2) {
            $("#inTime").hide();
        } else {
            $("#inTime").show();
        }
        if (reCode == 1 && upStatus != 2) {
            $("#inGuid").show();
        } else {
            $("#inGuid").hide();
        }

        $(".mask").fadeIn();
        $(".change_pop").fadeIn();
        popObj = ".change_pop"
    }

    //修改
    var updateBoo = false
    function update() {
        if (updateBoo) {
            return ;
        } else {
            updateBoo = true;
        }

        var currencyNameUp = document.getElementById("currencyNameUp").value;
        var currencyShortNameUp = document.getElementById("currencyShortNameUp").value;
        var buyFeeUp = document.getElementById("buyFeeUp").value;
        var sellFeeUp = document.getElementById("sellFeeUp").value;
        var guidUp = document.getElementById("guidUp").value;
        var changead_t2 = document.getElementById("changead_t2").value;

        var upTimeUp = document.getElementById("c_onlineTime").value;

        if (currencyId == 0 || currencyId == null || currencyId == "") {
            updateBoo = false;
            openTips("币种参数获取错误");
            return;
        }
        if (currencyNameUp == null || currencyNameUp == "") {
            updateBoo = false;
            openTips("请输入币种名称");
            return;
        }
        if (currencyShortNameUp == null || currencyShortNameUp == "") {
            updateBoo = false;
            openTips("请输入英文标识");
            return;
        }
        if (buyFeeUp == null || buyFeeUp == "") {
            updateBoo = false;
            openTips("请输入买入手续费");
            return;
        }
        if (sellFeeUp == null || sellFeeUp == "") {
            updateBoo = false;
            openTips("请输入卖出手续费");
            return;
        }
        if (guidUp == null || guidUp == "") {
            updateBoo = false;
            openTips("请确认上市指导价");
            return;
        }
        if (upTimeUp == null || upTimeUp == "") {
            updateBoo = false;
            openTips("请选择正确的时间");
            return;
        }

        var formData = new FormData();
        if (changead_t2 != null && changead_t2 != '') {
            var adsImageUrl = document.getElementById("changead_a2").files[0];
        }
        formData.append("currencyNameUp", currencyNameUp);
        formData.append("currencyId", currencyId);
        formData.append("currencyShortNameUp", currencyShortNameUp);
        formData.append("buyFeeUp", buyFeeUp);
        formData.append("sellFeeUp", sellFeeUp);
        formData.append("guidUp", guidUp);
        formData.append("imgUrl", adsImageUrl);
        formData.append("upTimeUp", upTimeUp);

        $.ajax({
            url: '<%=path %>' + "/backerWeb/transactionCurrency/update.htm",
            data:formData,//参数
            dataType: "json",
            type: 'POST',
            async: true, //默认异步调用 (false：同步)
            processData : false,
            contentType : false,
            success: function (resultData) {
                var code = resultData.code;
                var message = resultData.message;
                if (message != "") {
                    openTips(message);
                }
                if (code == 1){
                    $(".mask").fadeOut("fast");
                    $(popObj).fadeOut("fast");
                    setTimeout(function (){queryForm();}, 1000);
                }

                updateBoo = false;
            },

            error: function () {
                updateBoo = false;
                openTips("数据加载出错，请稍候重试");
            }
        });
    }
    
    //导出
    var extBoo = false;
    function dowland(){
        if (extBoo) {
            return ;
        } else {
            extBoo = true;
        }

        var currencyName = $("#currencyId").val();
        var pageNumber = $("#pageNumber").val();
        var paymentType = $("#paymentType").val();
        var backerAccount = $("#backerAccount").val();
        var upStatus = $("#upStatus").val();
        var startAddTime = $("#startAddTime").val();
        var endAddTime = $("#endAddTime").val();
        var startUpTime = $("#startUpTime").val();
        var endUpTime = $("#endUpTime").val();

        $.ajax({
            url: '<%=path %>' + "/backerWeb/transactionCurrency/export.htm",
            data:{
                currencyName : currencyName,
                pageNumber : pageNumber,
                paymentType : paymentType,
                backerAccount : backerAccount,
                upStatus : upStatus,
                startAddTime : startAddTime,
                endAddTime : endAddTime,
                startUpTime : startUpTime,
                endUpTime : endUpTime
            },//参数
            dataType: "json",
            type: 'POST',
            async: true, //默认异步调用 (false：同步)
            success: function (resultData) {
                var code = resultData.code;
                var message = resultData.message;
                extBoo = false;
                if (code != 1 && message != "") {
                    openTips(message);
                    return;
                }

                window.location.href = '<%=path%>' + message;
            },

            error: function () {
                extBoo = false;
                openTips("数据加载出错，请稍候重试");
            }
        });
    }

    //上移
    var upBoo = false;
    function upMove(id) {
        if (upBoo) {
            return;
        } else {
            upBoo = true;
        }

        if (id == null || parseInt(id) <= 0) {
            upBoo = false;
            openTips("获取参数错误");
            return;
        }

        $.ajax({
            url: '<%=path %>' + "/backerWeb/transactionCurrency/up.htm",
            data:{
                currencyId : id
            },//参数
            dataType: "json",
            type: 'POST',
            async: true, //默认异步调用 (false：同步)
            success: function (resultData) {
                var code = resultData.code;
                var message = resultData.message;
                if (message != "") {
                    openTips(message);
                }
                if (code == 1){
                    setTimeout(function (){queryForm();}, 1000);
                }

                upBoo = false;
            },

            error: function () {
                upBoo = false;
                openTips("数据加载出错，请稍候重试");
            }
        });
    }

    //下移
    var downBoo = false;
    function downMove(id) {
        if (downBoo) {
            return;
        } else {
            downBoo = true;
        }

        if (id == null || parseInt(id) <= 0) {
            downBoo = false;
            openTips("获取参数错误");
            return;
        }

        $.ajax({
            url: '<%=path %>' + "/backerWeb/transactionCurrency/down.htm",
            data:{
                currencyId : id
            },//参数
            dataType: "json",
            type: 'POST',
            async: true, //默认异步调用 (false：同步)
            success: function (resultData) {
                var code = resultData.code;
                var message = resultData.message;
                if (message != "") {
                    openTips(message);
                }
                if (code == 1){
                    setTimeout(function (){queryForm();}, 1000);
                }

                downBoo = false;
            },

            error: function () {
                downBoo = false;
                openTips("数据加载出错，请稍候重试");
            }
        });
    }

    //置顶
    var topBoo = false;
    function topMove(id) {
        if (topBoo) {
            return;
        } else {
            topBoo = true;
        }

        if (id == null || parseInt(id) <= 0) {
            topBoo = false;
            openTips("获取参数错误");
            return;
        }

        $.ajax({
            url: '<%=path %>' + "/backerWeb/transactionCurrency/top.htm",
            data:{
                currencyId : id
            },//参数
            dataType: "json",
            type: 'POST',
            async: true, //默认异步调用 (false：同步)
            success: function (resultData) {
                var code = resultData.code;
                var message = resultData.message;
                if (message != "") {
                    openTips(message);
                }
                if (code == 1){
                    setTimeout(function (){queryForm();}, 1000);
                }

                topBoo = false;
            },

            error: function () {
                topBoo = false;
                openTips("数据加载出错，请稍候重试");
            }
        });
    }


    var mapMatch = {};
    mapMatch['number'] = /[^\d]/g;
    mapMatch['ENumber'] = /[^\a-\z\A-\Z]/g;
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