<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/resources/page/common/path.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <link rel="icon" href="<%=path %>/resources/image/web/icon.ico" type="image/x-ico" />

    <link rel="stylesheet" type="text/css" href="<%=path %>/resources/css/web/notice.css" />
    <link rel="stylesheet" type="text/css" href="<%=path %>/resources/css/web/public.css" />
    <link rel="stylesheet" type="text/css" href="<%=path %>/resources/css/web/simpleTips.css" />

    <title>热门话题</title>
</head>
<body>
<header id="header"></header>
<div id="menu"></div>


<div class="content">
    <div id="personalMenu"></div>

    <div class="contentRight">
        <div class="title">热门话题</div>

        <div class="main">
            <div class="list">
                <c:forEach items="${systemHotList}" var="item">
                    <a href="#" class="notice" onclick="showDetail('${item.id}')">
                        <img src="${item.noticeUrlFormat}" class="noticeImg" />
                        <span class="noticeInfo">
                            <span class="noticeTitle">【<span>话题</span>】${item.noticeTitle}</span>
                            <span class="time"><fmt:formatDate value="${item.addTime}" pattern="yyyy-MM-dd HH:mm:ss"/></span>
                        </span>
                    </a>
                </c:forEach>
            </div>

            <jsp:include page="/resources/page/common/paging.jsp"></jsp:include>

            <form id="queryForm" action="<%=path %>/userWeb/webSystemHot/show" method="post">
                <input type="hidden" id="queryPageNumber" name="pageNumber">
            </form>

            <form id="detailForm" action="<%=path %>/userWeb/webSystemHot/showHotDetail" method="post">
                <input type="hidden" id="hotId" name="hotId">
                <input type="hidden" id="detailPageNumber" name="pageNumber">
            </form>
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
        var code = ${code};
        var message = '${message}';
        if (code != 1 && message != "") {
            openTips(message);
            return false;
        }
    }

    //详情
    function showDetail(id) {
        $("#hotId").val(id);
        $("#detailPageNumber").val('${pageNumber}');
        $("#detailForm").submit();
    }
</script>

</body>
</html>