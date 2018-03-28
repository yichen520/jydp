<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@include file="/resources/page/common/path.jsp"%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <link rel="icon" href="<%=path %>/resources/image/back/icon.ico" type="image/x-ico" />

    <link rel="stylesheet" type="text/css" href="<%=path %>/resources/css/back/accountRole_change.css" />
    <link rel="stylesheet" type="text/css" href="<%=path %>/resources/css/back/public.css" />
    <link rel="stylesheet" type="text/css" href="<%=path %>/resources/css/back/simpleTips.css" />

    <title>修改角色</title>
</head>
<body>
<div id="header"></div>


<div class="content">
    <div id="menu"></div>

    <div class="contentRight">
        <div class="caption">
            <span class="title">修改角色</span>
        </div>

        <div class="bottom">
            <div class="addInfo">
                <form id="modifyForm" action="<%=path %>/backerWeb/backerRole/roleModify.htm" method="post">
                    <p class="role">
                        <label class="name">角色名称<span class="star">*</span>：</label>
                        <input type="text" class="nameInput" placeholder="2~10个字符" maxLength="10"
                               id="modify_roleName" name="modify_roleName" value="${role.roleName }"
                        onkeyup="value=value.replace(/[^a-zA-Z0-9\_\u4e00-\u9fa5]/,'')" onblur="value=value.replace(/[^a-zA-Z0-9\_\u4e00-\u9fa5]/,'')"/>
                    </p>
                    <input type="hidden" name="modify_roleId" value="${role.roleId }">
                    <input type="hidden" id="modify_powerJson" name="modify_powerJson">
                    <input type="hidden" id="modify_mainPowerJson" name="modify_mainPowerJson">
                </form>

                <div class="role">
                    <label class="name">角色权限<span class="star">*</span>：</label>
                    <ul class="roleInfo">
                        <c:forEach items="${rolePowerMapVOList }" var="rolePowerMapVO">
                            <li class="infoOne">
                                <p class="infoTitle">
                                    <label class="choose">
                                        <input type="checkbox" class="check" name="roleBox" id="${rolePowerMapVO.powerId }"
                                            value="${rolePowerMapVO.powerId }"
                                            onchange="roleHandle('${rolePowerMapVO.powerId }', 1, '${rolePowerMapVO.powerId + 1}')" />${rolePowerMapVO.powerName }
                                    </label>
                                    <img src="<%=path %>/resources/image/back/down.png" class="info_down" />
                                    <img src="<%=path %>/resources/image/back/up.png" class="info_up" />
                                </p>

                                <ul class="infoTwo">
                                    <c:forEach items="${rolePowerMapVO.powerPowerMapList }" var="powerPowerList">
                                        <c:if test="${powerPowerList.powerId == rolePowerMapVO.powerId + 1}">
                                            <label class="box">
                                                <input type="checkbox" class="check" name="roleBox"
                                                    id="${powerPowerList.powerId }" value="${powerPowerList.powerId }"
                                                    onchange="roleHandle('${powerPowerList.powerId }', 2, '${rolePowerMapVO.powerId + 1}')" />${powerPowerList.powerName }
                                            </label>
                                        </c:if>
                                        <c:if test="${powerPowerList.powerId != rolePowerMapVO.powerId + 1}">
                                            <label class="box">
                                                <input type="checkbox" class="check" name="roleBox"
                                                    id="${powerPowerList.powerId }" value="${powerPowerList.powerId }"
                                                    onchange="roleHandle('${powerPowerList.powerId }', 3, '${rolePowerMapVO.powerId + 1}')" />${powerPowerList.powerName }
                                            </label>
                                        </c:if>
                                    </c:forEach>
                                </ul>
                            </li>
                        </c:forEach>
                    </ul>
                </div>

                <div class="operate">
                    <a href="<%=path %>/backerWeb/backerRole/show.htm" class="back">返&nbsp;回</a>
                    <input type="text" value="提&nbsp;交" class="submit" onfocus="this.blur()" onclick="roleSubmit()"/>
                </div>
            </div>
        </div>
    </div>
</div>


<script type="text/javascript" src="http://libs.baidu.com/jquery/2.1.4/jquery.min.js"></script>
<script type="text/javascript" src="<%=path %>/resources/js/loadPageBack.js"></script>
<script type="text/javascript" src="<%=path %>/resources/js/simpleTips.js"></script>

<script type="text/javascript">
    $(function() {
        $(".infoTitle").not(".choose").click(function () {
            !$(this).parent().find(".infoTwo").toggle();
            if($(this).parent().find(".infoTwo").css("display")=="block"){
                $(this).css("background-color","#e4f4fc");
                $(this).find(".info_up").show();
                $(this).find(".info_down").hide();
            }else
            {
                $(this).css("background-color","#f5f5f5");
                $(this).parent().find(".info_down").show();
                $(this).parent().find(".info_up").hide();
            }
        })
    });
</script>
<script type="text/javascript">
    window.onload = function() {
        var code = '${code}';
        var message = '${message}';
        if (code != 1 && message != "") {
            return openTips(message);
        }

        showPower();
    }

    //禁用enter事件
    $(document).keydown(function(event){
        switch(event.keyCode){
            case 13:return false;
        }
    });

    function showPower() {
        var powerList = eval(${role.powerJson});
        Object.keys(powerList).map(function handle(num){
            var obj = document.getElementById(powerList[num]);
            if (!!obj) {
                obj.checked = true;
            }
        });
    }

    function roleSubmit() {
        var powerIdList = "";
        var mainPowerList = "";
        var inputList = document.getElementsByName("roleBox");
        Object.keys(inputList).map(function executeHandle(num) {
            var itemInput = inputList[num];
            if (itemInput.type == "checkbox" && itemInput.checked) {
                var powerId = parseInt(itemInput.value);
                var mainPowerId = parseInt(powerId/10000)*10000;
                powerIdList += powerId + ":" + powerId + ",";
                mainPowerList += mainPowerId + ":" + mainPowerId + ",";
            }
        });
        //二级，三级权限
        var powerLength = powerIdList.length;
        if (powerLength > 0) {
            powerIdList = powerIdList.substring(0, powerLength - 1);
        }
        var powerJson = "{" + powerIdList + "}";
        //一级权限
        var mainPowerLength = mainPowerList.length;
        if (mainPowerLength > 0) {
            mainPowerList = mainPowerList.substring(0, mainPowerLength - 1);
        }
        var mainPowerJson = "{" + mainPowerList + "}";

        var modify_roleName = document.getElementById("modify_roleName").value;
        if (!modify_roleName || modify_roleName.length<2 || modify_roleName.length>10) {
            return openTips("请输入角色名称，要求2到10位");
        }
        if (isEmpty(powerJson)) {
            return openTips("请选择角色权限");
        }

        $("#modify_powerJson").val(powerJson);
        $("#modify_mainPowerJson").val(mainPowerJson);
        $("#modifyForm").submit();
    }
</script>

<script type="text/javascript">
    //点击选择checkbox
    function roleHandle(powerId, markType, tePowerId) {
        var selected;
        document.getElementById(powerId).checked ? selected = true : selected = false;

        var inputList = document.getElementsByName("roleBox");
        var MarkObj = {
            inputList : inputList,
            selected : selected,
            powerId : powerId,
            tePowerId : tePowerId,
            itemInput : null,
            inputValue : 0,
            tempValue : 0,
            tempPowerValue : 0
        };

        var markMap = {};
        markMap[1] = "mapHandle(MarkObj, executeOne);";
        markMap[2] = "mapHandle(MarkObj, executeTwo);";
        markMap[3] = "mapHandle(MarkObj, executeThree);";
        eval(markMap[markType]);
    }

    function mapHandle(MarkObj, fun) {
        Object.keys(MarkObj.inputList).map(function executeHandle(num) {
            MarkObj.itemInput = MarkObj.inputList[num];
            MarkObj.inputValue = MarkObj.inputList[num].value;
            MarkObj.tempValue = parseInt(MarkObj.inputValue/100)*100;
            MarkObj.tempPowerValue = parseInt(MarkObj.powerId/100)*100;
            fun(MarkObj);
        });
    }

    function executeOne(obj) {
        obj.tempValue == obj.powerId ? (obj.selected ? obj.itemInput.checked = true : obj.itemInput.checked = false) : false;
    }
    function executeTwo(obj) {
        obj.selected ? (obj.inputValue == obj.tempPowerValue ? obj.itemInput.checked = true : true) :
            (obj.tempValue == obj.tempPowerValue ? obj.itemInput.checked = false : true);
    }
    function executeThree(obj) {
        obj.tempPowerValue == obj.inputValue || obj.inputValue == obj.tePowerId ? obj.itemInput.checked = true : false;
    }
</script>

<script type="text/javascript">
    //字符串判空，为空返回true，非空返回false
    var map = {};
    map[null] = true;
    map[""] = true;
    map[" "] = true;
    map["null"] = true;
    map["NULL"] = true;
    map["undefined"] = true;
    map[undefined] = true;
    map["{}"] = true;
    function isEmpty(validateStr) {
        return map[validateStr] ? true : false;
    }
</script>
</body>
</html>
