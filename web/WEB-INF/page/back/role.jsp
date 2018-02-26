<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@include file="/resources/page/common/path.jsp"%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <link rel="icon" href="<%=path %>/resources/image/back/icon.ico" type="image/x-ico" />

    <link rel="stylesheet" type="text/css" href="<%=path %>/resources/css/back/accountRole.css" />
    <link rel="stylesheet" type="text/css" href="<%=path %>/resources/css/back/public.css" />
    <link rel="stylesheet" type="text/css" href="<%=path %>/resources/css/back/simpleTips.css" />

    <title>账号角色</title>
</head>
<body>
<div id="header"></div>


<div class="content">
    <div id="menu"></div>

    <div class="contentRight">
        <div class="caption">
            <span class="title">账号角色</span>
        </div>

        <c:if test="${backer_rolePower['131002'] == 131002}">
            <a href="<%=path %>/backerWeb/backerRole/roleAddPage.htm" class="add">新增角色</a>
        </c:if>
        <div class="bottom">
            <table class="table" cellspacing="0" cellpadding="0">
                <tr class="tableTitle">
                    <td class="name">角色名称</td>
                    <td class="role">角色权限</td>
                    <td class="operate">操作</td>
                </tr>
                <c:forEach items="${backerRoleList}" var="backerRole">
                    <tr class="tableInfo">
                        <td class="name">${backerRole.roleName}</td>
                        <td class="role">${backerRole.roleData}</td>
                        <td class="operate">
                            <c:if test="${backer_rolePower['131003'] == 131003}">
                                <a onclick="updateRole('${backerRole.roleId}');" href="#" class="change">修改角色</a>
                            </c:if>
                            <c:if test="${backer_rolePower['131004'] == 131004}">
                                <input type="text" value="删&nbsp;除" class="delete" onfocus="this.blur()" onclick="deletePower('${backerRole.roleId}');"/>
                            </c:if>
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </div>
    </div>
</div>


<div id="footer"></div>

<form id="updateForm" action="<%=path %>/backerWeb/backerRole/roleModifyPage.htm" method="post">
    <input type="hidden" id="modify_roleId" name="modify_roleId">
</form>

<div class="mask">
    <div class="mask_content">
        <form action="<%=path %>/backerWeb/backerRole/roleDelete.htm" method="post">
            <div class="delete_pop">
                <p class="popTitle">删除操作</p>
                <p class="popTips"><img src="<%=path %>/resources/image/back/tips.png" class="tipsImg" />确定删除？</p>

                <div class="buttons">
                    <input type="text" value="取&nbsp;消" class="cancel" onfocus="this.blur()" />
                    <input type="text" value="确&nbsp;定" class="yes" onfocus="this.blur()" onclick="deleteConfirm()" />
                </div>
            </div>
            <input type="hidden" id="delete_roleId" name="delete_roleId">
        </form>
    </div>
</div>

<script type="text/javascript" src="http://libs.baidu.com/jquery/2.1.4/jquery.min.js"></script>
<script type="text/javascript" src="<%=path %>/resources/js/loadPageBack.js"></script>
<script type="text/javascript" src="<%=path %>/resources/js/simpleTips.js"></script>

<script type="text/javascript">
    $(function(){
        $(".delete").click(function(){
            $(".mask").fadeIn();
            $(".delete_pop").fadeIn();
        });
        $(".cancel").click(function(){
            $(".mask").fadeOut("fast");
        });
        $(".yes").click(function(){
            $(".mask").fadeOut("fast");
        });
    })

</script>
<script type="text/javascript">
    window.onload = function() {
        var message = '${message}';
        if(message != "") {
            return openTips(message);
        }
    }

    function deletePower(roleId){
        $("#delete_roleId").val(roleId);

        $(".mask").fadeIn();
        $(".delete_pop").fadeIn();
    }

    var exportDataBoo = false;
    function deleteConfirm(){
        if(exportDataBoo){
            return;
        }else{
            exportDataBoo = true;
        }
        var delete_roleId = $("#delete_roleId").val();

        $.ajax({
            url: '<%=path %>' + "/backerWeb/backerRole/roleDelete.htm",
            type:'post',
            dataType:'json',
            async:true,
            data:{
                delete_roleId : delete_roleId
            },
            success:function(result){
                if(result.code == 1) {
                    window.location.href = "<%=path %>/backerWeb/backerRole/show.htm";
                } else {
                    openTips(result.message);
                }
                exportDataBoo = false;
            }, error:function(){
                exportDataBoo = false;
                openTips("系统错误！");
            }
        });
    }

    function updateRole(roleId) {
        $("#modify_roleId").val(roleId);
        $("#updateForm").submit();
    }
</script>
</body>
</html>
