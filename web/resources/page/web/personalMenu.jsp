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
<ul class="personalMenu">
    <li class="levelOne">
        <p class="menuTitle">
            账户信息管理
            <img src="<%=path %>/resources/image/web/down.png" class="menu_down" />
            <img src="<%=path %>/resources/image/web/up.png" class="menu_up" />
        </p>

        <ul class="levelTwo">
            <li class="menuInfo"><a id="userMessage" href="<%=path %>/userWeb/userMessage/show.htm" class="personal_pitch">个人信息</a></li>
            <li class="menuInfo"><a href="#">银行卡绑定</a></li>
        </ul>
    </li>

    <li class="levelOne">
        <p class="menuTitle">
            财务中心
            <img src="<%=path %>/resources/image/web/down.png" class="menu_down" />
            <img src="<%=path %>/resources/image/web/up.png" class="menu_up" />
        </p>

        <ul class="levelTwo">
            <li class="menuInfo"><a href="#">我要充值</a></li>
            <li class="menuInfo"><a href="#">立即提现</a></li>
        </ul>
    </li>

    <li class="levelOne">
        <p class="menuTitle">
            我的记录
            <img src="<%=path %>/resources/image/web/down.png" class="menu_down" />
            <img src="<%=path %>/resources/image/web/up.png" class="menu_up" />
        </p>

        <ul class="levelTwo">
            <li class="menuInfo"><a href="#">账户记录</a></li>
            <li class="menuInfo"><a href="<%=path%>/userWeb/transactionPendOrderController/show.htm">委托记录</a></li>
            <li class="menuInfo"><a href="<%=path %>/userWeb/dealRecord/show.htm">成交记录</a></li>
            <li class="menuInfo"><a href="#">充值记录</a></li>
            <li class="menuInfo"><a href="#">提现记录</a></li>
        </ul>
    </li>

    <li class="levelOne">
        <p class="menuTitle">
            我要帮助
            <img src="<%=path %>/resources/image/web/down.png" class="menu_down" />
            <img src="<%=path %>/resources/image/web/up.png" class="menu_up" />
        </p>

        <ul class="levelTwo">
            <li class="menuInfo"><a id="webSystemNotice" href="<%=path %>/userWeb/webSystemNotice/show">系统公告</a></li>
            <li class="menuInfo"><a id="webSystemHot" href="<%=path %>/userWeb/webSystemHot/show">热门话题</a></li>
            <li class="menuInfo"><a id="webHelpCenter" href="<%=path %>/userWeb/webHelpCenter/show">帮助中心</a></li>
            <li class="menuInfo"><a id="webCustomerService" href="<%=path %>/userWeb/webCustomerService/show">联系客服</a></li>
        </ul>
    </li>
</ul>

<script type="text/javascript">
    $(function() {
        $(".menuTitle").click(function () {
            $(this).parent().find(".levelTwo").toggle();
            if($(this).parent().find(".levelTwo").css("display")=="block"){
                $(this).find(".menu_up").show();
                $(this).find(".menu_down").hide();
            }else
            {
                $(this).parent().find(".menu_down").show();
                $(this).parent().find(".menu_up").hide();
            }
        })
    });
</script>
<script type="text/javascript">
    function showPersonalMenu() {
        //获取当前url路径
        var curUrl = window.location.href;

        //最终返回记录截取controller最开头的路径
        var subUrl;
        //查询userWeb字符所在位置
        var index = curUrl.indexOf("/userWeb/");
        //截取掉userWeb之前的所有字符
        subUrl = curUrl.substring(index + 9);
        //查询“/”所在位置
        index = subUrl.indexOf("/");
        //截取掉“/”之后的所有路径
        subUrl = subUrl.slice(0,index);
        //alert(subUrl);
        var menuObj = document.getElementById(subUrl);
        console.log(menuObj);
        if(menuObj != null){
            $("#" + subUrl).parent().parent().parent().find(".levelTwo").show();
            $("#" + subUrl).parent().parent().parent().find(".menuTitle").find(".menu_up").show();
            $("#" + subUrl).parent().parent().parent().find(".menuTitle").find(".menu_down").hide();
            $("#" + subUrl).addClass("personal_pitch");
            $("#" + subUrl).parent().parent().parent().find(".menuTitle").addClass("titleChoose");
            clearInterval(showPersonalMenuId);
            console.log('jhgfsahgdjhgdkjsg');
        }
    }
    var showPersonalMenuId = setInterval(showPersonalMenu, 50);
</script>
</body>
</html>