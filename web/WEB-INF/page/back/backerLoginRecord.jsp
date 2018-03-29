<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/resources/page/common/path.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <link rel="icon" href="<%=path %>/resources/image/back/icon.ico" type="image/x-ico" />

    <link rel="stylesheet" type="text/css" href="<%=path %>/resources/css/back/manageLogin.css" />
    <link rel="stylesheet" type="text/css" href="<%=path %>/resources/css/back/public.css" />
    <link rel="stylesheet" type="text/css" href="<%=path %>/resources/css/back/simpleTips.css" />

    <title>管理员登录记录</title>
</head>
<body>
<div id="header"></div>


<div class="content">
    <div id="menu"></div>

    <div class="contentRight">
        <div class="caption">
            <span class="title">管理员登录记录</span>
        </div>

        <div class="top">
            <form id="queryForm" action="<%=path %>/backerWeb/backerOperationalRecord/show.htm" method="post">
                <div class="askArea">
                    <p class="condition">
                        登录时间：
                        从&nbsp;<input placeholder="请选择起始时间" class="askTime" name="startLoginTime"
                                      value="${startLoginTime }" onfocus="this.blur()"/>
                        到&nbsp;<input placeholder="请选择结束时间" class="askTime"name="endLoginTime"
                                      value="${endLoginTime }" onfocus="this.blur()"/>
                    </p>
                    <p class="condition">登录账号：<input type="text" class="askInput" id="backerAccount" name="backerAccount" value="${backerAccount}"
                                                     maxlength="16" onkeyup="matchUtil(this, 'ENumber')" onblur="matchUtil(this, 'ENumber')"/></p>
                    <input type="hidden" id="queryPageNumber" name="pageNumber" value="${pageNumber}">
                    <c:if test="${backer_rolePower['131201'] == 131201}">
                        <input type="text" value="查&nbsp;询" class="ask" onfocus="this.blur()" onclick="queryForm()"/>
                    </c:if>
                </div>
            </form>
        </div>

        <div class="bottom">
            <table class="table" cellspacing="0" cellpadding="0">
                <tr class="tableTitle">
                    <td class="time">登录时间</td>
                    <td class="account">登录管理员帐号</td>
                    <td class="ip">登录时IP地址</td>
                </tr>

                <c:forEach items="${backerSessionList}" var="backerSession">
                    <tr class="tableInfo">
                        <td class="time"><fmt:formatDate type="time" value="${backerSession.loginTime }" pattern="yyyy-MM-dd HH:mm:ss"></fmt:formatDate></td>
                        <td class="account">${backerSession.backerAccount }</td>
                        <td class="ip">${backerSession.ipAddress }</td>
                    </tr>
                </c:forEach>
            </table>

            <jsp:include page="/resources/page/common/paging.jsp"></jsp:include>
        </div>
    </div>
</div>


<div id="footer"></div>

<script type="text/javascript" src="http://libs.baidu.com/jquery/2.1.4/jquery.min.js"></script>
<script type="text/javascript" src="<%=path %>/resources/js/laydate.js"></script>
<script type="text/javascript" src="<%=path %>/resources/js/loadPageBack.js"></script>
<script type="text/javascript" src="<%=path %>/resources/js/simpleTips.js"></script>

<script type="text/javascript">
    lay('.askTime').each(function(){
        laydate.render({
            elem: this,
            trigger: 'click',
            type:'datetime',
            theme: '#69c0ff'
        });
    });//日期控件



    //查询
    function queryForm() {
        $("#queryPageNumber").val("0");
        $("#queryForm").submit();
    }

    var mapMatch = {};
    mapMatch['ENumber'] = /[^\a-\z\A-\Z\d]/g;
    mapMatch['number'] = /[^\d]/g;
    mapMatch['double'] = true;
    function matchUtil(o, str) {
        mapMatch[str] === true ? matchDouble(o, 4) : o.value = o.value.replace(mapMatch[str], '');
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
