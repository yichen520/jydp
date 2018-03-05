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
                        管理员账号：<input type="text" class="askInput" id="backAccount" name="backAccount"
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
                        从&nbsp;<input placeholder="请选择起始时间" class="askTime" id="startTime" name="startTime"
                                      value="${startTime}" onfocus="this.blur()"/>
                        到&nbsp;<input placeholder="请选择结束时间" class="askTime" id="endTime" name="endTime"
                                      value="${endTime}" onfocus="this.blur()"/>
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
                    <td class="range">涨跌幅度</td>
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
                        </td>
                        <td class="coinLogo"><img src="${item.currencyImgUrl}" /></td>
                        <td class="service">
                            <p>买入：<fmt:formatNumber type="number" value="${item.buyFee * 100}" maxFractionDigits="6"/>%</p>
                            <p>卖出：<fmt:formatNumber type="number" value="${item.sellFee * 100}" maxFractionDigits="6"/>%</p>
                        </td>
                        <td class="range">
                            <p>涨停：<fmt:formatNumber type="number" value="${item.upRange * 100}" maxFractionDigits="6"/>%</p>
                            <p>跌停：<fmt:formatNumber type="number" value="${item.downRange * 100}" maxFractionDigits="6"/>%</p>
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
                            <input type="text" value="停&nbsp;牌" class="stop" onfocus="this.blur()" />
                            <input type="text" value="上&nbsp;线" class="online" onfocus="this.blur()" />
                            <input type="text" value="下&nbsp;线" class="offLine" onfocus="this.blur()" />
                            <input type="text" value="修&nbsp;改" class="change" onfocus="this.blur()" />
                            <input type="text" value="复&nbsp;牌" class="start" onfocus="this.blur()" />
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
            <p class="popTitle">启用操作</p>
            <p class="popTips"><img src="<%=path %>/resources/image/back/tips.png" class="tipsImg" />确定启用该币种？</p>

            <div class="buttons">
                <input type="text" value="取&nbsp;消" class="cancel" onfocus="this.blur()" />
                <input type="text" value="确&nbsp;定" class="yes" onfocus="this.blur()" onclick="openTip()" />
            </div>
        </div>

        <div class="stop_pop">
            <p class="popTitle">禁用操作</p>
            <p class="popTips"><img src="<%=path %>/resources/image/back/tips.png" class="tipsImg" />确定禁用该币种？</p>

            <div class="buttons">
                <input type="text" value="取&nbsp;消" class="cancel" onfocus="this.blur()" />
                <input type="text" value="确&nbsp;定" class="yes" onfocus="this.blur()" onclick="openTip()" />
            </div>
        </div>

        <div class="online_pop">
            <p class="popTitle">上线操作</p>
            <p class="popTips"><img src="<%=path %>/resources/image/back/tips.png" class="tipsImg" />确定立即上线该币种？</p>

            <div class="buttons">
                <input type="text" value="取&nbsp;消" class="cancel" onfocus="this.blur()" />
                <input type="text" value="确&nbsp;定" class="yes" onfocus="this.blur()" onclick="openTip()" />
            </div>
        </div>

        <div class="offLine_pop">
            <p class="popTitle">下线操作</p>
            <p class="popTips"><img src="<%=path %>/resources/image/back/tips.png" class="tipsImg" />确定下线该币种？</p>

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
</body>
</html>