<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
</head>

<body>
<div class="helpFooter">
    <div class="help">
        <span class="helpTitle">关于我们</span>

        <ul class="helpMenu">
            <li class="helpInfo"><a href="javascript:;" onclick="showHelpDetail(this)" id="101014">公司简介</a></li>
            <li class="helpInfo"><a href="javascript:;" onclick="showHelpDetail(this)" id="101013">联系我们</a></li>
            <li class="helpInfo"><a href="javascript:;" onclick="showHelpDetail(this)" id="101015">充值流程</a></li>
        </ul>
    </div>

    <div class="help">
        <span class="helpTitle">新手指南</span>

        <ul class="helpMenu">
            <li class="helpInfo"><a href="javascript:;" onclick="showHelpDetail(this)" id="101016">注册指南</a></li>
            <li class="helpInfo"><a href="javascript:;" onclick="showHelpDetail(this)" id="101017">交易指南</a></li>
            <li class="helpInfo"><a href="javascript:;" onclick="showHelpDetail(this)" id="101010">注册协议</a></li>
        </ul>
    </div>

    <span class="f_logo"><img src="<%=path %>/resources/image/web/footer_logo.png" />交易大盘</span>
</div>

<form id="helpForm" action="<%=path %>/userWeb/webHelpCenter/show" method="post">
    <input type="hidden" id="helpId" name="helpId">
</form>

<script type="text/javascript">
    function showHelpDetail(obj) {
        if (obj.id == null || obj.id == "") {
            return false;
        }
        $("#helpId").val(obj.id);
        $("#helpForm").submit();
    }
</script>

</body>
</html>