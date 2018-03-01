<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/resources/page/common/path.jsp"%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <link rel="icon" href="<%=path %>/resources/image/web/icon.ico" type="image/x-ico" />

    <link rel="stylesheet" type="text/css" href="<%=path %>/resources/css/web/helpCenter.css" />
    <link rel="stylesheet" type="text/css" href="<%=path %>/resources/css/web/public.css" />
    <link rel="stylesheet" type="text/css" href="<%=path %>/resources/css/web/simpleTips.css" />

    <title>帮助中心</title>
</head>
<body>
<header id="header"></header>
<div id="menu"></div>


<div class="content">
    <ul class="helpCenter">
        <li class="levelOne">
            <p class="menuTitle">关于我们</p>

            <ul class="levelTwo">
                <li class="menuInfo"><a href="javascript:;" onclick="showHelpDetail(this)" id="101014">公司简介</a></li>
                <li class="menuInfo"><a href="javascript:;" onclick="showHelpDetail(this)" id="101013">联系我们</a></li>
                <li class="menuInfo"><a href="javascript:;" onclick="showHelpDetail(this)" id="101015">充值流程</a></li>
            </ul>
        </li>

        <li class="levelOne">
            <p class="menuTitle">新手指南</p>

            <ul class="levelTwo">
                <li class="menuInfo"><a href="javascript:;" onclick="showHelpDetail(this)" id="101016">注册指南</a></li>
                <li class="menuInfo"><a href="javascript:;" onclick="showHelpDetail(this)" id="101017">交易指南</a></li>
                <li class="menuInfo"><a href="javascript:;" onclick="showHelpDetail(this)" id="101010">注册协议</a></li>
            </ul>
        </li>
    </ul>

    <div class="contentRight">
        ${systemHelpDO.content}
    </div>
</div>

<form id="helpForm" action="<%=path %>/userWeb/webHelpCenter/show" method="post">
    <input type="hidden" id="helpId" name="helpId">
</form>

<div id="helpFooter"></div>
<div id="footer"></div>

<script type="text/javascript" src="http://libs.baidu.com/jquery/2.1.4/jquery.min.js"></script>
<script type="text/javascript" src="<%=path %>/resources/js/loadPageWeb.js"></script>
<script type="text/javascript" src="<%=path %>/resources/js/simpleTips.js"></script>

<script type="text/javascript">
    window.onload = function() {
        var code = '${code}';
        var message = '${message}';
        if (code != 1 && message != "") {
            openTips(message);
            return false;
        }

        var helpId = '${helpId}';
        var menuObj = document.getElementById(helpId);

        if (menuObj != null) {
            menuObj.setAttribute("class", "personal_pitch");
        }
    }

    function showHelpDetail(obj) {
        $("#helpId").val(obj.id);
        $("#helpForm").submit();
    }
</script>

</body>
</html>