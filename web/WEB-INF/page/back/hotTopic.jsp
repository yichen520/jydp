<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <link rel="icon" href="<%=path%>/resources/image/back/icon.ico" type="image/x-ico" />

    <link rel="stylesheet" type="text/css" href="<%=path %>/resources/css/back/hot.css" />
    <link rel="stylesheet" type="text/css" href="<%=path %>/resources/css/back/public.css" />
    <link rel="stylesheet" type="text/css" href="<%=path %>/resources/css/back/simpleTips.css" />

    <title>热门话题管理</title>
</head>
<body>
<div id="header"></div>


<div class="content">
    <div id="menu"></div>

    <div class="contentRight">
        <div class="caption">
            <span class="title">热门话题管理</span>
        </div>

        <div class="top">
            <c:if test="${backer_rolePower['114002'] == 114002}">
                <a href="<%=path%>/backerWeb/hotTopic/openAddHotTopic.htm" class="add">新增热门</a>
            </c:if>
            <form id="queryForm" action="<%=path%>/backerWeb/hotTopic/show.htm">
                <div class="askArea">
                    <p class="condition">话题类型：<input type="text" class="askInput" name="noticeType" value="${noticeType }" maxLength="8"/></p>
                    <p class="condition">话题标题：<input type="text" class="askInput"  name="noticeTitle" value="${noticeTitle }" maxLength="64"/></p>
                    <input type="hidden" id="query_pageNumber" name=query_pageNumber value="${pageNumber }">
                    <c:if test="${backer_rolePower['114001'] == 114001}">
                        <input type="text" value="查&nbsp;询" class="ask" onfocus="this.blur()" onclick="queryForm()"/>
                    </c:if>
                </div>
            </form>
        </div>

        <div class="bottom">
            <table class="table" cellpadding="0" cellspacing="0">
                <tr class="tableTitle">
                    <td class="time">添加时间</td>
                    <td class="pic">话题图片</td>
                    <td class="type">话题类型</td>
                    <td class="nTitle">话题标题</td>
                    <td class="operate">操作</td>
                </tr>
                <c:forEach items="${systemHotList}" var="systemHot" varStatus="status">
                    <tr class="tableInfo">
                        <td class="time"><fmt:formatDate type="time" value="${systemHot.addTime }" pattern="yyyy-MM-dd HH:mm:ss"></fmt:formatDate></td>
                        <td class="pic"><img src="${systemHot.noticeUrlFormat }" /></td>
                        <td class="type">${systemHot.noticeType }</td>
                        <td class="nTitle">${systemHot.noticeTitle }</td>
                        <td class="operate">
                            <c:if test="${status.count != 1 || pageNumber != 0}">
                                <c:if test="${backer_rolePower['114003'] == 114003}">
                                    <input type="text" value="置&nbsp; 顶" class="toTop" onfocus="this.blur()" onclick="topNotice(${systemHot.id });"/>
                                </c:if>
                            </c:if>
                            <c:if test="${backer_rolePower['114004'] == 114004}">
                                <a href="<%=path%>/backerWeb/hotTopic/openUpdateHotTopic.htm?id=${systemHot.id }" class="change">修&nbsp;改</a>
                            </c:if>
                            <c:if test="${backer_rolePower['114005'] == 114005}">
                                <a href="<%=path%>/backerWeb/hotTopic/hotTopicDetails.htm?id=${systemHot.id }" class="details">详&nbsp;情</a>
                            </c:if>
                            <c:if test="${backer_rolePower['114006'] == 114006}">
                                <input type="text" value="删&nbsp;除" class="delete" onfocus="this.blur()" onclick="deleteHandle(${systemHot.id });"/>
                            </c:if>
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
        <form action="<%=path%>/backerWeb/hotTopic/deleteHotTopic.htm" method="post">
            <div class="delete_pop">
                <p class="popTitle">删除操作</p>
                <p class="popTips"><img src="<%=path%>/resources/image/back/tips.png" class="tipsImg" />确定删除？</p>
                <input type="hidden" name="id" id="deleteId"/>
                <div class="buttons">
                    <input type="text" value="取&nbsp;消" class="cancel" onfocus="this.blur()" />
                    <input type="text" value="确&nbsp;定" class="yes" onfocus="this.blur()" onclick="deleteNotice()"/>
                </div>
            </div>
        </form>
    </div>
</div>

<script type="text/javascript" src="http://libs.baidu.com/jquery/2.1.4/jquery.min.js"></script>
<script type="text/javascript" src="<%=path %>/resources/js/loadPageBack.js"></script>
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

    var popObj;
    $(function(){
        $(".delete").click(function(){
            /*$(".mask").fadeIn();
            $(".delete_pop").fadeIn();
            popObj = ".delete_pop"*/
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

    //查询
    function queryForm() {
        $("#queryPageNumber").val("0");
        $("#queryForm").submit();
    }

    //删除
    function deleteHandle(id) {
        document.getElementById("deleteId").value = id;

        $(".mask").show();
        $(".delete_pop").fadeIn();
        popObj = ".delete_pop"
    }

    function deleteNotice(){
        var deleteId = document.getElementById("deleteId").value;

        $.ajax({
            url: '<%=path %>' + "/backerWeb/hotTopic/deleteHotTopic.htm",
            data: {
                deleteId : deleteId,
            },//参数
            dataType: "json",
            type: 'POST',
            async: true, //默认异步调用 (false：同步)
            success: function (resultData) {
                var code = resultData.code;
                var message = resultData.message;
                if (code != 1 && message != "") {
                    openTips(message);
                    return;
                }

                $("#queryForm").submit();
            },

            error: function () {
                openTips("数据加载出错，请稍候重试");
            }
        });

    }

    //置顶
    function topNotice(id) {
        $.ajax({
            url: '<%=path %>' + "/backerWeb/hotTopic/top.htm",
            data: {
                id : id,
            },//参数
            dataType: "json",
            type: 'POST',
            async: true, //默认异步调用 (false：同步)
            success: function (resultData) {
                var code = resultData.code;
                var message = resultData.message;
                if (code != 1 && message != "") {
                    openTips(message);
                    return;
                }

                $("#queryForm").submit();
            },

            error: function () {
                openTips("数据加载出错，请稍候重试");
            }
        });

    }
</script>

</body>
</html>
