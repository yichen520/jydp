<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/resources/page/common/path.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <meta http-equiv="Cache-Control" content="no-cache" />

    <link rel="stylesheet" href="<%=path %>/resources/css/wap/common.css">
    <link rel="stylesheet" href="<%=path %>/resources/css/wap/simpleTips_wap.css">
    <link rel="stylesheet" href="<%=path %>/resources/css/wap/notice.css">
    
    <title>系统公告</title>
</head>
<body>
    <!-- 头部导航 -->
    <header>
        <img src="<%=path %>/resources/image/wap/back.png" onclick="javascript:window.history.back(-1);" class="back"/>
        <p>系统公告</p>
    </header>
    <!-- 内容区域 -->
    <div class="content">
        <div hidden="hidden" id="queryPageNumber" name="pageNumber"></div>
        <ul>
        </ul>
    </div>
    <div class="more" onclick="pageNext()">查看更多</div>
    <!-- loading图 -->
    <div id="loading">
        <i></i>
    </div>
</body>
<script id="template" type="text/x-handlebars-template">
    {{#each this}}
    <a href="<%=path %>/userWap/wapSystemNotice/showNoticeDetail/{{id}}" class="notice" >
        <li>
            {{#compare noticeUrl 1}}
                <img src="{{noticeUrlFormat}}" class="noticeImg" />
                {{else}}
                <img src="<%=path%>/upload/image/notic_hotTopic.jpg" class="noticeImg" />
            {{/compare}}
            <div class="noticeInfo list-box">
                <p class="noticeTitle title">【<span>公告</span>】{{noticeTitle}}</p>
                <p class="time date">{{addTimeConvert addTime}}</p>
            </div>
            <div class="clear"></div>
        </li>
    </a>
    {{/each}}
</script>
<script src="<%=path %>/resources/js/wap/common.js"></script>
<script src="<%=path %>/resources/js/wap/zepto.min.js"></script>
<script src="<%=path %>/resources/js/wap/jquery-2.1.4.min.js"></script>
<script src="<%=path %>/resources/js/wap/notice.js"></script>
<script src="<%=path %>/resources/js/wap/simpleTips_wap.js"></script>
<script src="<%=path %>/resources/js/wap/handlebars-v4.0.11.js"></script>
<script type="text/javascript">
    //时间转换
    Handlebars.registerHelper("addTimeConvert", function (addTime) {
        var date = new Date(addTime);
        var year = date.getFullYear();
        var month = date.getMonth() + 1;
        month=month < 10 ? ('0' + month) : month;
        var day = date.getDate();
        day=day < 10 ? ('0' + day) : day;
        var hours = date.getHours();
        hours=hours < 10 ? ('0' + hours) : hours;
        var minutes = date.getMinutes();
        minutes=minutes < 10 ? ('0' + minutes) : minutes;
        var seconds = date.getSeconds();
        seconds=seconds < 10 ? ('0' + seconds) : seconds;
        return year + "-" + month + "-" + day + " " + hours + ":" + minutes + ":" + seconds;
    });
    //if比较
    Handlebars.registerHelper("compare",function(x1,x2,options){
        if(x1!=x2){
            //满足条件执行
            return options.fn(this);
        }else{
            //不满足执行{{else}}部分
            return options.inverse(this);
        }
    });


    //公告数据填充
    var systemNoticeListData = ${requestScope.systemNoticeList};
    var noticefunc = Handlebars.compile($('#template').html());
    $('.content ul').html(noticefunc(systemNoticeListData));

    var totalPageNumber = parseInt(${requestScope.totalPageNumber});
    if (totalPageNumber == 0 ||totalPageNumber == 1){
        $(".more").remove();
    }
    //更多
    function pageNext() {
        var pageNumber = $("#queryPageNumber").html();
        var totalPageNumber = parseInt(${requestScope.totalPageNumber});
        if(pageNumber < totalPageNumber - 1){
            pageNumber = pageNumber + 1;
            $.ajax({
                url: '<%=path %>/userWap/wapSystemNotice/showMoreNotice',
                type: 'post',
                dataType: 'json',
                data:{pageNumber:pageNumber,},//参数
                success: function (result) {
                    if (result.code == 1) {
                            var noticeList = result.systemNoticeList;
                            if (noticeList != null) {
                                var transactionfunc = Handlebars.compile($('#template').html());
                                $('.content ul').append(transactionfunc(noticeList));
                                $('#queryPageNumber').html(result.pageNumber);
                                if (noticeList.length < 10){
                                    $(".more").remove();
                                }
                            }
                        }
                    }
            });
        } else {
            $(".more").remove();
        }
    }

</script>

</html>