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
<ul class="menu">
    <li class="levelOne" id="li_140000">
        <p class="menuTitle">
            账号管理
            <img src="<%=path %>/resources/image/back/down.png" class="menu_down" />
            <img src="<%=path %>/resources/image/back/up.png" class="menu_up" />
        </p>

        <ul class="levelTwo">
            <li class="menuInfo" id="li_141000"><a id="a_141000" href="#">实名认证</a></li>
            <li class="menuInfo" id="li_141100"><a id="a_141100" href="<%=path%>/backerWeb/backerUserAccount/show.htm">用户账号</a></li>
        </ul>
    </li>

    <li class="levelOne" id="li_110000">
        <p class="menuTitle">
            运营中心
            <img src="<%=path %>/resources/image/back/down.png" class="menu_down" />
            <img src="<%=path %>/resources/image/back/up.png" class="menu_up" />
        </p>

        <ul class="levelTwo">
            <li class="menuInfo" id="li_111000"><a id="a_111000" href="<%=path %>/backerWeb/backerAdsHomepages/show.htm">首页广告</a></li>
            <li class="menuInfo"><a href="#">合作伙伴</a></li>
            <li class="menuInfo" id="li_113000"><a id="a_113000" href="<%=path %>/backerWeb/backerNotice/show.htm">用户公告管理</a></li>
            <li class="menuInfo" id="li_114000"><a id="a_114000" href="<%=path %>/backerWeb/hotTopic/show.htm">热门话题管理</a></li>
            <li class="menuInfo"><a href="#">用户帮助中心</a></li>
            <li class="menuInfo"><a href="#">意见反馈</a></li>
        </ul>
    </li>

    <li class="levelOne">
        <p class="menuTitle">
            充值管理
            <img src="<%=path %>/resources/image/back/down.png" class="menu_down" />
            <img src="<%=path %>/resources/image/back/up.png" class="menu_up" />
        </p>

        <ul class="levelTwo">
            <li class="menuInfo"><a href="#">充值记录</a></li>
        </ul>
    </li>

    <li class="levelOne" id="li_100000">
        <p class="menuTitle">
            交易记录
            <img src="<%=path %>/resources/image/back/down.png" class="menu_down" />
            <img src="<%=path %>/resources/image/back/up.png" class="menu_up" />
        </p>

        <ul class="levelTwo">
            <li class="menuInfo" id="li_101000"><a href="<%=path%>/backerWeb/backerTransactionPendOrder/show.htm">挂单记录</a></li>
            <li class="menuInfo" id="li_102000"><a href="#">交易记录</a></li>
            <li class="menuInfo" id="li_103000"><a href="#">后台挂单</a></li>
        </ul>
    </li>

    <li class="levelOne">
        <p class="menuTitle">
            提现管理
            <img src="<%=path %>/resources/image/back/down.png" class="menu_down" />
            <img src="<%=path %>/resources/image/back/up.png" class="menu_up" />
        </p>

        <ul class="levelTwo">
            <li class="menuInfo"><a href="#">提现记录</a></li>
        </ul>
    </li>

    <li class="levelOne"  id="li_130000" >
        <p class="menuTitle">
            后台账号管理
            <img src="<%=path %>/resources/image/back/down.png" class="menu_down" />
            <img src="<%=path %>/resources/image/back/up.png" class="menu_up" />
        </p>

        <ul class="levelTwo">
            <li class="menuInfo" id="li_131000"><a id="a_131000" href="<%=path %>/backerWeb/backerRole/show.htm">账号角色</a></li>
            <li class="menuInfo" id="li_131100"><a id="a_131100" href="<%=path %>/backerWeb/backerAccount/show.htm">后台账号</a></li>
        </ul>
    </li>


    <li class="levelOne"  id="li_120000">
        <p class="menuTitle">
            管理员操作记录
            <img src="<%=path %>/resources/image/back/down.png" class="menu_down" />
            <img src="<%=path %>/resources/image/back/up.png" class="menu_up" />
        </p>

        <ul class="levelTwo">
            <li class="menuInfo" id="li_121000"><a id="a_121000" href="<%=path%>/backerWeb/backerAdministratorOperation/show.htm">用户余额操作记录</a></li>
        </ul>
    </li>
</ul>

<script type="text/javascript">
    $(function() {
        $(".menuTitle").click(function () {
            if($(this).parent().find(".levelTwo").css("display")=="block"){
                $(this).find(".menu_up").css("display","block");
                $(this).parent().find(".levelTwo").slideUp();
            }else{
                $(".levelTwo").hide();
                $(this).parent().find(".levelTwo").slideDown();
            }

            if($(this).find(".menu_up").css("display")=="block"){
                $(this).find(".menu_up").hide();
                $(this).find(".menu_down").css("display","block");
            }else{
                $(".menu_up").hide();
                $(".menu_down").show();
                $(this).find(".menu_up").css("display","block");
                $(this).find(".menu_down").hide();
            }
        })
    });
    $(function(){
        $(".menuInfo a").click(function(){
            $(".levelOne").find("p").removeClass("titleChoose");
            $(this).parent().parent().parent().find(".menuTitle").addClass("titleChoose")
        })
    });
    $(function(){
        var height = $("body").height();
        $("#menu").css('minHeight',height - 142);
    });
</script>

<script type="text/javascript">
	function menuSelect(rolePower) {
		//一级模块
		var onePower = parseInt(rolePower/10000)*10000;
        var oneLevel = document.getElementById("li_"+onePower);
        if(oneLevel != null && oneLevel != "") {
            oneLevel.style.display = "inline-block";
        }
        //二级功能
        var twoPower = parseInt(rolePower/100)*100;
        var twoLevel = document.getElementById("li_"+twoPower);
        if(twoLevel != null && twoLevel != "") {
            twoLevel.style.display = "inline-block";
        }
	}

    function showMenu(){
        $('.levelOne').hide();
        $('.menuInfo').hide();
        //显示有权限的模块
        var rolePowerList = JSON.parse('${backer_rolePower}');
        Object.keys(rolePowerList).map(menuSelect);
        //显示当前模块
        var pagePowerId = ${backer_pagePowerId};
        if(pagePowerId > 0){
            var menuPower = parseInt(pagePowerId/10000)*10000;
            var curMenuLi = document.getElementById("li_"+menuPower);

            if(curMenuLi != null && curMenuLi != ""){
                $('#li_'+menuPower).find('.menu_down').hide();
                $('#li_'+menuPower).find('.menu_up').show();
                $("#li_"+menuPower).find(".levelTwo").show();
                $("#li_"+menuPower).find(".menuTitle").addClass("titleChoose");

            }

            //给当前模块添加选中标记
            var curMenuA = document.getElementById("a_" + pagePowerId);
            if(curMenuA != null && curMenuA != ""){
                curMenuA.setAttribute("class", "personal_pitch");
            }
            curMenuA.p
        }
    }

    var t1 = setTimeout(showMenu(), 200);
    clearTimeout(t1);
</script>
</body>
</html>
