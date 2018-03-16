<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/resources/page/common/path.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <link rel="icon" href="<%=path %>/resources/image/back/icon.ico" type="image/x-ico" />

    <link rel="stylesheet" type="text/css" href="<%=path %>/resources/css/back/account_authentication.css" />
    <link rel="stylesheet" type="text/css" href="<%=path %>/resources/css/back/public.css" />
    <link rel="stylesheet" type="text/css" href="<%=path %>/resources/css/back/simpleTips.css" />

    <title>实名认证</title>
</head>
<body>
<header id="header"></header>


<div class="content">
    <div id="menu"></div>


    <div class="contentRight">
        <div class="caption">实名认证</div>

        <div class="top">
            <div class="askArea">
                <form id="queryForm" action="<%=path %>/backerWeb/backerIdentification/show.htm" method="post">
                    <p class="condition">提交时间：
                        从&nbsp;<input placeholder="请选择起始时间" class="askTime" id="start" name="startTime" value="${startTime }" onfocus="this.blur()"/>
                        到&nbsp;<input placeholder="请选择结束时间" class="askTime" id="end" name="endTime" value="${endTime }" onfocus="this.blur()"/>
                    </p>
                    <p class="condition">用户账号：<input type="text" class="askInput" id="userAccount" name="userAccount" value="${userAccount }"
                                                     maxlength="16" onkeyup="matchUtil(this, 'ENumber')" onblur="matchUtil(this, 'ENumber')"/></p>
                    <p class="condition">手机号：
                        <select class="askSelect" id="phoneAreaCode" name="phoneAreaCode">
                            <option value="">选择区号</option>
                            <c:forEach items="${phoneAreaMap}" var="phoneArea">
                                <option value="${phoneArea.key }">${phoneArea.key }</option>
                            </c:forEach>
                        </select>
                        <input type="text" class="askInput" id="userPhone" name="userPhone" value="${userPhone }"
                            maxlength="11" onkeyup="matchUtil(this, 'number')" onblur="matchUtil(this, 'number')"/>
                    </p>
                    <p class="condition">证件类型：
                        <select class="askSelect" id="userCertType" name="userCertType">
                            <option value="0">全部</option>
                            <option value="1">身份证</option>
                            <option value="2">护照</option>
                        </select>
                    </p>
                    <p class="condition">审核状态：
                        <select class="askSelect" id="identificationStatus" name="identificationStatus">
                            <option value="0">全部</option>
                            <option value="1">待审核</option>
                            <option value="2">审核通过</option>
                            <option value="3">审核拒绝</option>
                        </select>
                    </p>

                    <input type="hidden" id="queryPageNumber" name="pageNumber" value="${pageNumber}">
                    <c:if test="${backer_rolePower['141001'] == 141001}">
                        <input type="text" value="查&nbsp;询" class="ask" onfocus="this.blur()" onclick="queryForm()"/>
                    </c:if>
                </form>
            </div>
        </div>

        <div class="bottom">
            <table class="table" cellspacing="0" cellpadding="0">
                <tr class="tableTitle">
                    <td class="time">提交时间</td>
                    <td class="account">用户账号</td>
                    <td class="tel">手机号</td>
                    <td class="type">证件类型</td>
                    <td class="identity">证件号</td>
                    <td class="state">审核状态</td>
                    <td class="mark">审核备注</td>
                    <td class="operate">操作</td>
                </tr>
                <c:forEach items="${userIdentificationList}" var="item">
                    <tr class="tableInfo">
                        <td class="time"><fmt:formatDate type="time" value="${item.addTime}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
                        <td class="account">${item.userAccount}</td>
                        <td class="tel">${item.phoneAreaCode}&nbsp;${item.userPhone}</td>
                        <c:if test="${item.userCertType == 1}">
                            <td class="type">身份证</td>
                        </c:if>
                        <c:if test="${item.userCertType == 2}">
                            <td class="type">护照</td>
                        </c:if>
                        <td class="identity">${item.userCertNo}</td>
                        <c:if test="${item.identificationStatus == 1}">
                            <td class="state">待审核</td>
                        </c:if>
                        <c:if test="${item.identificationStatus == 2}">
                            <td class="state">审核通过</td>
                        </c:if>
                        <c:if test="${item.identificationStatus == 3}">
                            <td class="state">审核拒绝</td>
                        </c:if>
                        <td class="mark">${item.remark}</td>
                        <c:if test="${backer_rolePower['141002'] == 141002}">
                            <td class="operate"><a href="<%=path %>/backerWeb/backerIdentification/detail.htm/${item.id}" target="_blank" class="details">查看详情</a></td>
                        </c:if>
                    </tr>
                </c:forEach>
            </table>

            <jsp:include page="/resources/page/common/paging.jsp"></jsp:include>
        </div>
    </div>
</div>

<form id="detailForm" action="<%=path %>/backerWeb/backerIdentification/detail.htm" method="post">
    <input type="hidden" id="detailId" name="id">

    <input type="hidden" id="pageNumberDetail" name="pageNumber">
    <input type="hidden" id="startTimeDetail" name="startTime">
    <input type="hidden" id="endTimeDetail" name="endTime">
    <input type="hidden" id="userAccountDetail" name="userAccount">
    <input type="hidden" id="userPhoneDetail" name="userPhone">
    <input type="hidden" id="phoneAreaCodeDetail" name="phoneAreaCode">
    <input type="hidden" id="identificationStatusDetail" name="identificationStatus">
    <input type="hidden" id="userCertTypeDetail" name="userCertType">
</form>


<div id="footer"></div>


<script type="text/javascript" src="http://libs.baidu.com/jquery/2.1.4/jquery.min.js"></script>
<script type="text/javascript" src="<%=path %>/resources/js/loadPageBack.js"></script>
<script type="text/javascript" src="<%=path %>/resources/js/simpleTips.js"></script>
<script type="text/javascript" src="<%=path %>/resources/js/laydate.js"></script>

<script type="text/javascript">
    window.onload = function() {
        var code = '${code}';
        var message = '${message}';
        if (code != 1 && message != "") {
            openTips(message);
            return false;
        }
        $("#identificationStatus option").each(function(){
            if($(this).val()=='${identificationStatus}'){
                $(this).attr('selected',true);
            }
        });
        $("#phoneAreaCode option").each(function(){
            if($(this).val()=='${phoneAreaCode}'){
                $(this).attr('selected',true);
            }
        });
        $("#userCertType option").each(function(){
            if($(this).val()=='${userCertType}'){
                $(this).attr('selected',true);
            }
        });
    }

    function queryForm() {
        $("#queryPageNumber").val("0");
        $("#queryForm").submit();
    }

    function showDetail(id) {
        $("#pageNumberDetail").val($("#queryPageNumber").val());
        $("#startTimeDetail").val($("#start").val());
        $("#endTimeDetail").val($("#end").val());
        $("#userAccountDetail").val($("#userAccount").val());
        $("#userPhoneDetail").val($("#userPhone").val());
        $("#phoneAreaCodeDetail").val($("#phoneAreaCode").val());
        $("#identificationStatusDetail").val($("#identificationStatus").val());
        $("#userCertTypeDetail").val($("#userCertType").val());

        $("#detailId").val(id);
        $("#detailForm").submit();
    }

    var mapMatch = {};
    mapMatch['ENumber'] = /[^\a-\z\A-\Z\d]/g;
    mapMatch['number'] = /[^\d]/g;
    mapMatch['double'] = true;
    function matchUtil(o, str) {
        mapMatch[str] === true ? matchDouble(o, 2) : o.value = o.value.replace(mapMatch[str], '');
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

    lay('.askTime').each(function(){
        laydate.render({
            elem: this,
            trigger: 'click',
            type:'datetime',
            theme: '#69c0ff'
        });
    });//日期控件
</script>
</body>
</html>