<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/resources/page/common/path.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <link rel="icon" href="<%=path %>/resources/image/back/icon.ico" type="image/x-ico" />

    <link rel="stylesheet" type="text/css" href="<%=path %>/resources/css/back/opinion.css" />
    <link rel="stylesheet" type="text/css" href="<%=path %>/resources/css/back/public.css" />
    <link rel="stylesheet" type="text/css" href="<%=path %>/resources/css/back/simpleTips.css" />
    <link rel="stylesheet" type="text/css" href="<%=path %>/resources/js/need/laydate.css" />
    <link rel="stylesheet" type="text/css" href="<%=path %>/resources/js/skins/danlan/laydate.css" />

    <title>意见反馈</title>
</head>
<body>
<div id="header"></div>


<div class="content">
    <div id="menu"></div>

    <div class="contentRight">
        <div class="caption">
            <span class="title">意见反馈</span>
        </div>

        <div class="top">
            <form id="queryForm" action="<%=path %>/backerWeb/backerFeedback/show.htm" method="post">
                <div class="askArea">
                    <p class="condition">反馈人账号：
                        <input type="text" class="askInput" id="userAccount" name="userAccount" value="${userAccount}"
                               maxlength="16" onkeyup="matchUtil(this, 'ENumber')" onblur="matchUtil(this, 'ENumber')"/></p>
                    <p class="condition">反馈标题：
                        <input type="text" class="askInput" id="feedbackTitle" name="feedbackTitle" value="${feedbackTitle}"
                               maxlength="32" /></p>
                    <p class="condition">处理状态：
                        <select class="askSelect" id="handleStatus" name="handleStatus">
                            <option value="0">全部</option>
                            <option value="1">待处理</option>
                            <option value="2">处理中</option>
                            <option value="3">已处理</option>
                        </select>
                    </p>
                    <p class="condition">
                        反馈时间：
                        从&nbsp;<input placeholder="请选择起始时间" class="startTime" id="start" name="startTime" value="${startTime}"
                                      onClick="laydate({istime: true,format:'YYYY-MM-DD hh:mm:ss'})" onfocus="this.blur()"/>
                        到&nbsp;<input placeholder="请选择结束时间" class="endTime" id="end" name="endTime" value="${endTime}"
                                      onClick="laydate({istime: true,format: 'YYYY-MM-DD hh:mm:ss'})" onfocus="this.blur()"/>
                    </p>

                    <input type="hidden" id="queryPageNumber" name="pageNumber" value="${pageNumber}">
                    <c:if test="${backer_rolePower['116001'] == 116001}">
                        <input type="text" value="查&nbsp;询" class="ask" onfocus="this.blur()" onclick="queryForm()"/>
                    </c:if>
                </div>
            </form>
        </div>

        <div class="bottom">
            <table class="table" cellspacing="0" cellpadding="0">
                <tr class="tableTitle">
                    <td class="time">反馈时间</td>
                    <td class="account">反馈人账号</td>
                    <td class="oTitle">反馈标题</td>
                    <td class="opinion">反馈内容</td>
                    <td class="state">处理状态</td>
                    <td class="opinion">回复内容</td>
                    <td class="operate">操作</td>
                </tr>
                <c:forEach items="${feedbackList}" var="item">
                    <tr class="tableInfo">
                        <td class="time"><fmt:formatDate value="${item.addTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                        <td class="account">${item.userAccount}</td>
                        <td class="oTitle">${item.feedbackTitle}</td>
                        <td class="opinion">${item.feedbackContent}</td>
                        <c:if test="${item.handleStatus == 1}">
                            <td class="state">待处理</td>
                        </c:if>
                        <c:if test="${item.handleStatus == 2}">
                            <td class="state">处理中</td>
                        </c:if>
                        <c:if test="${item.handleStatus == 3}">
                            <td class="state">已处理</td>
                        </c:if>
                        <td class="opinion">${item.handleContent}</td>
                        <td class="operate">
                            <c:if test="${item.handleStatus != 3}">
                                <c:if test="${backer_rolePower['116002'] == 116002}">
                                    <input type="text" value="回&nbsp;复" class="reply" onfocus="this.blur()" onclick="reply('${item.id}','${item.handleStatus}')"/>
                                </c:if>
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
        <div class="reply_pop">
            <p class="popTitle">回复操作</p>
            <p class="popInput">
                <label class="popName">处理状态<span class="star">*</span></label>
                <select class="popSelected" id="reply_handleStatus">
                    <option disabled selected>选择处理状态</option>
                    <option value="1">待处理</option>
                    <option value="2">处理中</option>
                    <option value="3">已处理</option>
                </select>
            </p>
            <p class="popInput">
                <label class="popName" style="line-height: 20px">回复内容</label>
                <textarea class="txt" id="reply_handleContent" placeholder="回复内容，非必填，最多100个字符" maxlength="100"></textarea>
            </p>

            <div class="buttons">
                <input type="text" value="取&nbsp;消" class="cancel" onfocus="this.blur()" />
                <input type="hidden" id="reply_id">
                <input type="text" value="确&nbsp;定" class="yes" onfocus="this.blur()" onclick="replyHandle()" />
            </div>
        </div>
    </div>
</div>

<script type="text/javascript" src="http://libs.baidu.com/jquery/2.1.4/jquery.min.js"></script>
<script type="text/javascript" src="<%=path %>/resources/js/loadPageBack.js"></script>
<script type="text/javascript" src="<%=path %>/resources/js/simpleTips.js"></script>
<script type="text/javascript" src="<%=path %>/resources/js/laydate.js"></script>

<script type="text/javascript">
    window.onload = function() {
        $("#handleStatus").val('${handleStatus}');

        var code = '${code}';
        var message = '${message}';
        if (code != 1 && message != "") {
            openTips(message);
            return false;
        }
    }

    function queryForm() {
        $("#queryPageNumber").val("0");
        $("#queryForm").submit();
    }

    function reply(id, handleStatus) {
        $("#reply_handleStatus").val(handleStatus);
        $("#reply_id").val(id);
    }

    //回复操作
    var replyHandleBoo = false;
    function replyHandle() {
        if (replyHandleBoo) {
            return false;
        } else {
            replyHandleBoo = true;
        }

        var id = $("#reply_id").val();
        var handleStatus = $("#reply_handleStatus").val();
        var handleContent = $("#reply_handleContent").val();

        if (!id) {
            replyHandleBoo = false;
            return openTips("未选中反馈内容");
        }

        if (!handleStatus || handleStatus == 0) {
            replyHandleBoo = false;
            return openTips("未选择处理状态");
        }

        $.ajax({
            url: '<%=path %>' + "/backerWeb/backerFeedback/reply.htm",
            type:'post',
            dataType:'json',
            async:true,
            data:{
                replyId : id,
                replyHandleStatus : handleStatus,
                replyHandleContent : handleContent
            },
            success:function (result) {
                if (result.code == 1) {
                    $("#queryForm").submit();
                } else {
                    openTips(result.message);
                }
                replyHandleBoo = false;
            },
            error:function () {
                replyHandleBoo = false;
                openTips("回复错误!");
            }
        });
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

    !function(){
        laydate.skin('danlan');//切换皮肤，请查看skins下面皮肤库
    }();

    var start = {
        elem: '#start',
        format: 'YYYY-MM-DD hh:mm:ss',
        istime: true,
        istoday: false
    };

    var end = {
        elem: '#end',
        format: 'YYYY-MM-DD hh:mm:ss',
        istime: true,
        istoday: false
    };
    laydate(start);
    laydate(end);//日期控件

    $(function(){
        var popObj;
        $(function(){
            $(".reply").click(function(){
                $(".mask").fadeIn();
                $(".reply_pop").fadeIn();
                popObj = ".reply_pop"
            });
            $(".cancel").click(function(){
                $(".mask").fadeOut("fast");
                $(popObj).fadeOut("fast");
            });
            $(".yes").click(function(){
                $(".mask").fadeOut("fast");
                $(popObj).fadeOut("fast");
            });
        })
    });

    function openTip()
    {
        openTips("阿萨德芳");
    }
</script>

</body>
</html>