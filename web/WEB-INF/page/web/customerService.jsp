<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/resources/page/common/path.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <link rel="icon" href="<%=path %>/resources/image/web/icon.ico" type="image/x-ico" />

    <link rel="stylesheet" type="text/css" href="<%=path %>/resources/css/web/service.css" />
    <link rel="stylesheet" type="text/css" href="<%=path %>/resources/css/web/public.css" />
    <link rel="stylesheet" type="text/css" href="<%=path %>/resources/css/web/simpleTips.css" />

    <title>联系客服</title>
</head>
<body>
<header id="header"></header>
<div id="menu"></div>


<div class="content">
    <div id="personalMenu"></div>

    <div class="contentRight">
        <div class="title">
            联系客服
            <input type="text" value="意见反馈" class="add" onfocus="this.blur()" />
        </div>

        <div class="main">
            <div class="project">
                <c:forEach items="${userFeedbackList}" var="item">
                    <div class="service">
                        <p class="serviceTitle">${item.feedbackTitle}</p>
                        <c:if test="${item.handleStatus == 1}">
                            <p class="state wait">待处理</p>
                        </c:if>
                        <c:if test="${item.handleStatus == 2}">
                            <p class="state handle">处理中</p>
                        </c:if>
                        <c:if test="${item.handleStatus == 3}">
                            <p class="state finish">已处理</p>
                        </c:if>
                        <p class="info">${item.feedbackContent}</p>
                        <p class="time"><fmt:formatDate value="${item.addTime}" pattern="yyyy-MM-dd HH:mm:ss"/></p>
                        <p class="reply">${item.handleContent}</p>
                    </div>
                </c:forEach>
            </div>

            <jsp:include page="/resources/page/common/paging.jsp"></jsp:include>

            <form id="queryForm" action="<%=path %>/userWeb/webCustomerService/show" method="post">
                <input type="hidden" id="queryPageNumber" name="pageNumber">
            </form>
        </div>
    </div>
</div>


<div id="helpFooter"></div>
<div id="footer"></div>


<div class="mask">
    <div class="mask_content">
        <div class="add_pop">
            <p class="popTitle">意见反馈</p>
            <p class="popInput">
                <label class="popName">反馈标题<span class="star">*</span>：</label>
                <input type="text" class="entry" placeholder="2~16个字符的反馈标题" id="feedbackTitle" maxlength="16"/>
            </p>
            <p class="popInput">
                <label class="popName" style="line-height: 20px;vertical-align: top;">反馈内容<span class="star">*</span>：</label>
                <textarea class="txt" placeholder="反馈内容，最多200个字符" id="feedbackContent" maxlength="200"></textarea>
            </p>

            <div class="buttons">
                <input type="text" value="取&nbsp;消" class="cancel" onfocus="this.blur()" />
                <input type="text" value="确&nbsp;定" class="yes" onfocus="this.blur()" onclick="feedback()" />
            </div>
        </div>
    </div>
</div>

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

    var feedbackBoo = false;
    function feedback() {
        if (feedbackBoo) {
            return false;
        } else {
            feedbackBoo = true;
        }

        var feedbackTitle = $("#feedbackTitle").val();
        var feedbackContent = $("#feedbackContent").val();

        if (!feedbackTitle || feedbackTitle == '') {
            feedbackBoo = false;
            return openTips("反馈标题不能为空")
        }
        if (feedbackTitle.length < 2 || feedbackTitle.length > 16) {
            feedbackBoo = false;
            return openTips("反馈标题为2~16个字符")
        }
        if (!feedbackContent || feedbackContent == '') {
            feedbackBoo = false;
            return openTips("反馈内容不能为空")
        }
        if (feedbackContent.length > 200) {
            feedbackBoo = false;
            return openTips("反馈内容不能超过200个字符")
        }

        $.ajax({
            url: '<%=path %>' + "/userWeb/webCustomerService/feedback",
            type:'post',
            dataType:'json',
            async:true,
            data:{
                feedbackTitle : feedbackTitle,
                feedbackContent : feedbackContent
            },
            success:function (result) {
                if (result.code == 1) {
                    $("#queryForm").submit();
                } else {
                    openTips(result.message);
                }
                feedbackBoo = false;
            },
            error:function () {
                feedbackBoo = false;
                openTips("数据加载出错，请稍候重试");
            }
        });
    }

    $(function(){
        $(".service").height($(".service").height())
    });

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
            $(".mask").fadeOut("fast");
            $(popObj).fadeOut("fast");
        });
    });

</script>

</body>
</html>