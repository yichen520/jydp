<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/resources/page/common/path.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <link rel="icon" href="<%=path %>/resources/image/back/icon.ico" type="image/x-ico" />

    <link rel="stylesheet" type="text/css" href="<%=path %>/resources/css/back/accountBackstage.css" />
    <link rel="stylesheet" type="text/css" href="<%=path %>/resources/css/back/public.css" />
    <link rel="stylesheet" type="text/css" href="<%=path %>/resources/css/back/simpleTips.css" />

    <title>后台账号</title>
</head>
<body>
<div id="header"></div>


<div class="content">
    <div id="menu"></div>

    <div class="contentRight">
        <div class="caption">
            <span class="title">后台账号</span>
        </div>

        <c:if test="${backer_rolePower['131102'] == 131102}">
            <p class="add">新增后台账号</p>
        </c:if>

        <div class="bottom">
            <table class="table" cellspacing="0" cellpadding="0">
                <tr class="tableTitle">
                    <td class="time">添加时间</td>
                    <td class="account">用户名</td>
                    <td class="role">账号角色</td>
                    <td class="state">账号状态</td>
                    <td class="operate">操作</td>
                </tr>
                <c:forEach items="${backerList}" var="backer">
                    <tr class="tableInfo">
                        <td class="time"><fmt:formatDate value="${backer.addTime }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                        <td class="account">${backer.backerAccount }</td>
                        <td class="role">${backer.roleName }</td>
                        <c:if test="${backer.accountStatus == 1}">
                            <td class="state">启用</td>
                        </c:if>
                        <c:if test="${backer.accountStatus == 2}">
                            <td class="state">禁用</td>
                        </c:if>
                        <td class="operate">
                            <c:if test="${backer.accountStatus == 2 && backer_rolePower['131105'] == 131105}">
                                <input type="text" value="启&nbsp;用" class="start" onfocus="this.blur()" onclick="enable('${backer.backerId}');"/>
                            </c:if>
                            <c:if test="${backer.accountStatus == 1 && backer_rolePower['131106'] == 131106}">
                                <input type="text" value="禁&nbsp;用" class="stop" onfocus="this.blur()" onclick="disable('${backer.backerId}');"/>
                            </c:if>
                            <c:if test="${backer_rolePower['131103'] == 131103}">
                                <input type="text" value="修改角色" class="changeRole" onfocus="this.blur()"
                                       onclick="updateRole('${backer.backerId}','${backer.backerAccount}','${backer.roleId}');"/>
                            </c:if>
                            <c:if test="${back_backerId != backer.backerId && backer_rolePower['131107'] == 131107}">
                                <input type="text" value="重置密码" class="reset" onfocus="this.blur()" onclick="resetPassword('${backer.backerId}');"/>
                            </c:if>
                            <c:if test="${back_backerId == backer.backerId}">
                                <input type="text" value="修改密码" class="changePassword" onfocus="this.blur()" />
                            </c:if>
                            <c:if test="${backer_rolePower['131104'] == 131104}">
                                <input type="text" value="删&nbsp;除" class="delete" onfocus="this.blur()" onclick="deleteBacker('${backer.backerId}');"/>
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
        <div class="start_pop">
            <p class="popTitle">启用操作</p>
            <p class="popTips"><img src="<%=path %>/resources/image/back/tips.png" class="tipsImg" />确定启用该账号？</p>

            <div class="buttons">
                <input type="text" value="取&nbsp;消" class="cancel" onfocus="this.blur()" />
                <input type="text" value="确&nbsp;定" class="yes" onfocus="this.blur()" onclick="enableConfirm()" />
            </div>
        </div>
        <input type="hidden" id="enbaleBackerId" name="enbaleBackerId">

        <div class="stop_pop">
            <p class="popTitle">禁用操作</p>
            <p class="popTips"><img src="<%=path %>/resources/image/back/tips.png" class="tipsImg" />确定禁用该账号？</p>

            <div class="buttons">
                <input type="text" value="取&nbsp;消" class="cancel" onfocus="this.blur()" />
                <input type="text" value="确&nbsp;定" class="yes" onfocus="this.blur()" onclick="disableConfirm()" />
            </div>
        </div>
        <input type="hidden" id="disableBackerId" name="disableBackerId">

        <div class="delete_pop">
            <p class="popTitle">删除操作</p>
            <p class="popTips"><img src="<%=path %>/resources/image/back/tips.png" class="tipsImg" />确定删除该账号？</p>

            <div class="buttons">
                <input type="text" value="取&nbsp;消" class="cancel" onfocus="this.blur()" />
                <input type="text" value="确&nbsp;定" class="yes" onfocus="this.blur()" onclick="deleteConfirm()" />
            </div>
        </div>
        <input type="hidden" id="deleteBackerId" name="deleteBackerId">

        <div class="reset_pop">
            <p class="popTitle">重置密码</p>
            <p class="popTips"><img src="<%=path %>/resources/image/back/tips.png" class="tipsImg" />确定重置密码？重置后的密码为123456</p>

            <div class="buttons">
                <input type="text" value="取&nbsp;消" class="cancel" onfocus="this.blur()" />
                <input type="text" value="确&nbsp;定" class="yes" onfocus="this.blur()" onclick="resetConfirm()" />
            </div>
        </div>
        <input type="hidden" id="resetPasswordBackerId" name="resetPasswordBackerId">

        <div class="add_pop">
            <p class="popTitle">新增后台账号</p>
            <p class="popInput">
                <label class="popName">用户账号<span class="star">*</span></label>
                <input type="text" class="entry" placeholder="用户账号，6~16个字符，字母或数字" maxlength="16"
                    onkeyup="value=value.replace(/[^a-zA-Z0-9]/g,'')" onblur="validateUser(this);"
                       id="addBackerAccount" name="addBackerAccount"/>
            </p>
            <p class="popInput">
                <label class="popName">登录密码<span class="star">*</span></label>
                <input type="password" class="entry" placeholder="登录密码，6~16个字符，字母或数字" maxlength="16"
                    onkeyup="value=value.replace(/[^a-zA-Z0-9]/g,'')" onblur="value=value.replace(/[^a-zA-Z0-9]/g,'')"
                    id="addBackerPassword" name="addBackerPassword"/>
            </p>
            <p class="popInput">
                <label class="popName">重复密码<span class="star">*</span></label>
                <input type="password" class="entry" placeholder="再次输入登录密码" maxlength="16"
                       onkeyup="value=value.replace(/[^a-zA-Z0-9]/g,'')" onblur="value=value.replace(/[^a-zA-Z0-9]/g,'')"
                    id="addBackerRepeatPassword" name="addBackerRepeatPassword"/>
            </p>
            <p class="popInput">
                <label class="popName">账号角色<span class="star">*</span></label>
                <select class="popSelected" id="addRoleId" name="addRoleId">
                    <option disabled selected>选择账号角色</option>
                    <c:forEach items="${roleList}" var="role">
                        <option value="${role.roleId}">${role.roleName}</option>
                    </c:forEach>
                </select>
            </p>

            <div class="buttons">
                <input type="text" value="取&nbsp;消" class="cancel" onfocus="this.blur()" />
                <input type="text" value="确&nbsp;定" class="yes" onfocus="this.blur()" onclick="addBackerSubmit()" />
            </div>
        </div>

        <div class="changeRole_pop">
            <p class="popTitle">修改角色</p>
            <p class="popInput">
                <label class="popName">用户名<span class="star">*</span></label>
                <span class="popAccount" id="account_span"></span>
            </p>
            <p class="popInput">
                <label class="popName">账号角色<span class="star">*</span></label>
                <select class="popSelected" id="updateRoleId" name="updateRoleId">
                    <option disabled selected>选择账号角色</option>
                    <c:forEach items="${roleList}" var="role">
                        <option value="${role.roleId}">${role.roleName}</option>
                    </c:forEach>
                </select>
            </p>

            <div class="buttons">
                <input type="text" value="取&nbsp;消" class="cancel" onfocus="this.blur()" />
                <input type="text" value="确&nbsp;定" class="yes" onfocus="this.blur()" onclick="updateRoleSubmit()" />
            </div>
        </div>
        <input type="hidden" id="updateRoleBackerId" name="updateRoleBackerId">

        <div class="changePassword_pop">
            <p class="popTitle">修改密码</p>
            <p class="popInput">
                <label class="popName">旧密码<span class="star">*</span></label>
                <input type="password" class="entry" placeholder="旧登录密码，6~16个字符，字母或数字" maxlength="16"
                       onkeyup="value=value.replace(/[^a-zA-Z0-9]/g,'')" onblur="value=value.replace(/[^a-zA-Z0-9]/g,'')"
                       id="updateBackerOldPassword" name="updateBackerOldPassword"/>
            </p>
            <p class="popInput">
                <label class="popName">登录密码<span class="star">*</span></label>
                <input type="password" class="entry" placeholder="新登录密码，6~16个字符，字母或数字" maxlength="16"
                       onkeyup="value=value.replace(/[^a-zA-Z0-9]/g,'')" onblur="value=value.replace(/[^a-zA-Z0-9]/g,'')"
                       id="updateBackerNewPassword" name="updateBackerNewPassword"/>
            </p>
            <p class="popInput">
                <label class="popName">重复密码<span class="star">*</span></label>
                <input type="password" class="entry" placeholder="再次输入新登录密码" maxlength="16"
                       onkeyup="value=value.replace(/[^a-zA-Z0-9]/g,'')" onblur="value=value.replace(/[^a-zA-Z0-9]/g,'')"
                    id="updateBackerRepeatPassword" name="updateBackerRepeatPassword"/>
            </p>

            <div class="buttons">
                <input type="text" value="取&nbsp;消" class="cancel" onfocus="this.blur()" />
                <input type="text" value="确&nbsp;定" class="yes" onfocus="this.blur()" onclick="updatePasswordSubmit()" />
            </div>
        </div>
        <input type="hidden" id="updatePasswordBackerId" name="updatePasswordBackerId">
    </div>
</div>

<form id="queryForm" action="<%=path%>/backerWeb/backerAccount/show.htm">
    <input type="hidden" id="queryPageNumber" name="pageNumber" value="${pageNumber }">
</form>

<script type="text/javascript" src="http://libs.baidu.com/jquery/2.1.4/jquery.min.js"></script>
<script type="text/javascript" src="<%=path %>/resources/js/loadPageBack.js"></script>
<script type="text/javascript" src="<%=path %>/resources/js/simpleTips.js"></script>
<script type="text/javascript">
    window.onload = function() {
        var code = '${code}';
        var message = '${message}';
        if (code != 1 && message != "") {
            openTips(message);
            return false;
        }
    }

    function enable(backerId) {
        $("#enbaleBackerId").val(backerId);
    }

    var enableConfirmBoo = false;
    //启用确定
    function enableConfirm() {
        if(enableConfirmBoo){
            openTips("系统繁忙请稍后");
            return;
        } else {
            enableConfirmBoo = true;
        }

        var enbaleBackerId = $("#enbaleBackerId").val();
        $.ajax({
            url: '<%=path %>' + "/backerWeb/backerAccount/startUp.htm",
            type:'post',
            dataType:'json',
            async:true,
            data:{
                enbaleBackerId : enbaleBackerId
            },
            success:function(result){
                openTips(result.message);
                enableConfirmBoo = false;
                if(result.code == 1) {
                    setTimeout("refresh()",1000 );
                }
            }, error:function(){
                enableConfirmBoo = false;
                openTips("系统错误！");
            }
        });
    }

    function disable(backerId) {
        $("#disableBackerId").val(backerId);
    }

    var disableConfirmBoo = false;
    //禁用确定
    function disableConfirm() {
        if(disableConfirmBoo){
            openTips("系统繁忙请稍后");
            return;
        } else {
            disableConfirmBoo = true;
        }
        var disableBackerId = $("#disableBackerId").val();
        $.ajax({
            url: '<%=path %>' + "/backerWeb/backerAccount/forbidden.htm",
            type:'post',
            dataType:'json',
            async:true,
            data:{
                disableBackerId : disableBackerId
            },
            success:function(result){
                openTips(result.message);
                disableConfirmBoo = false;
                if(result.code == 1) {
                   setTimeout("refresh()",1000 );
                }
            }, error:function(){
                disableConfirmBoo = false;
                openTips("系统错误！");
            }
        });
    }

    function resetPassword(backerId) {
        $("#resetPasswordBackerId").val(backerId);
    }

    var resetConfirmBoo = false;
    //重置确定
    function resetConfirm() {
        if(resetConfirmBoo){
            openTips("系统繁忙请稍后");
            return;
        } else {
            resetConfirmBoo = true;
        }

        var resetPasswordBackerId = $("#resetPasswordBackerId").val();
        $.ajax({
            url: '<%=path %>' + "/backerWeb/backerAccount/resetPassword.htm",
            type:'post',
            dataType:'json',
            async:true,
            data:{
                resetPasswordBackerId : resetPasswordBackerId
            },
            success:function(result){
                openTips(result.message);
                resetConfirmBoo = false;
                if(result.code == 1) {
                   setTimeout("refresh()",1000 );
                }
            }, error:function(){
                resetConfirmBoo = false;
                openTips("系统错误！");
            }
        });
    }

    function deleteBacker(backerId) {
        $("#deleteBackerId").val(backerId);
    }

    var deleteConfirmBoo = false;
    //删除确定
    function deleteConfirm() {
        if(deleteConfirmBoo){
            openTips("系统繁忙请稍后");
            return;
        } else {
            deleteConfirmBoo = true;
        }

        var deleteBackerId = $("#deleteBackerId").val();
        $.ajax({
            url: '<%=path %>' + "/backerWeb/backerAccount/delete.htm",
            type:'post',
            dataType:'json',
            async:true,
            data:{
                deleteBackerId : deleteBackerId
            },
            success:function(result){
                openTips(result.message);
                deleteConfirmBoo = false;
                if(result.code == 1) {
                   setTimeout("refresh()",1000 );
                }
            }, error:function(){
                deleteConfirmBoo = false;
                openTips("系统错误！");
            }
        });
    }

    function updateRole(backerId, backerAccount, roleId) {
        $("#updateRoleBackerId").val(backerId);
        $("#account_span").text(backerAccount);
        $("#updateRoleId").val(roleId);
    }

    var updateRoleSubmitBoo = false;
    //修改角色
    function updateRoleSubmit() {
        var updateRoleId = $("#updateRoleId").val();
        var updateRoleBackerId = $("#updateRoleBackerId").val();
        if (!updateRoleId) {
            return openTips("请选择角色");
        }

        if(updateRoleSubmitBoo){
            openTips("系统繁忙请稍后");
            return;
        } else {
            updateRoleSubmitBoo = true;
        }

        $.ajax({
            url: '<%=path %>' + "/backerWeb/backerAccount/updateRole.htm",
            type:'post',
            dataType:'json',
            async:true,
            data:{
                updateRoleId : updateRoleId,
                updateRoleBackerId : updateRoleBackerId
            },
            success:function(result){
                openTips(result.message);
                updateRoleSubmitBoo = false;
                if(result.code == 1) {
                   setTimeout("refresh()",1000 );
                }
            }, error:function(){
                updateRoleSubmitBoo = false;
                openTips("系统错误！");
            }
        });
    }

    var updatePasswordSubmitBoo = false;
    //修改密码
    function updatePasswordSubmit() {
        var oldPassword = $("#updateBackerOldPassword").val();
        var newPassword = $("#updateBackerNewPassword").val();
        var newRepeatPassword = $("#updateBackerRepeatPassword").val();
        if (!oldPassword || oldPassword.length < 6 || oldPassword.length > 16) {
            return openTips("请输入旧登录密码，6~16个字符，字母或数字");
        }
        if (!newPassword || newPassword.length < 6 || newPassword.length > 16) {
            return openTips("请输入新登录密码，6~16个字符，字母或数字");
        }
        if (!newRepeatPassword || newRepeatPassword.length < 6 || newRepeatPassword.length > 16) {
            return openTips("请再次输入新登录密码");
        }
        if (newPassword != newRepeatPassword) {
            return openTips("两次密码不匹配");
        }

        if(updatePasswordSubmitBoo){
            openTips("系统繁忙请稍后");
            return;
        } else {
            updatePasswordSubmitBoo = true;
        }

        $.ajax({
            url: '<%=path %>' + "/backerWeb/backerAccount/updatePassword.htm",
            type:'post',
            dataType:'json',
            async:true,
            data:{
                updateBackerOldPassword : oldPassword,
                updateBackerNewPassword : newPassword,
                updateBackerRepeatPassword : newRepeatPassword
            },
            success:function(result){
                openTips(result.message);
                updatePasswordSubmitBoo = false;
                if(result.code == 1) {
                    setTimeout("backLogin()",1000 );
                }
            }, error:function(){
                updatePasswordSubmitBoo = false;
                openTips("系统错误！");
            }
        });
    }


    var addBackerSubmitBoo = false;
    //新增账号
    function addBackerSubmit() {
        var reg = /^[0-9a-zA-Z]{6,16}$/;
        var addBackerAccount = $("#addBackerAccount").val();
        var addRoleId = $("#addRoleId").val();
        var addBackerPassword = $("#addBackerPassword").val();
        var addBackerRepeatPassword = $("#addBackerRepeatPassword").val();
        if (!addBackerAccount || addBackerAccount.length < 6 || addBackerAccount.length > 16) {
            return openTips("用户账号必须是6-16位的数字或字母");
        }
        if (!validateAccount) {
            return openTips("请重新输入用户账号");
        }
        if (!addRoleId) {
            return openTips("请选择账号角色");
        }
        if (!addBackerPassword || addBackerPassword.length < 6 || addBackerPassword.length > 16) {
            return openTips("登录密码必须是6-16位的数字或字母");
        }
        if (!addBackerRepeatPassword || addBackerRepeatPassword.length < 6 || addBackerRepeatPassword.length > 16) {
            return openTips("重复密码必须是6-16位的数字或字母");
        }
        if (addBackerPassword != addBackerRepeatPassword) {
            return openTips("两次密码不一致");
        }

        if(addBackerSubmitBoo){
            openTips("系统繁忙请稍后");
            return;
        } else {
            addBackerSubmitBoo = true;
        }

        $.ajax({
            url: '<%=path %>' + "/backerWeb/backerAccount/insert.htm",
            type:'post',
            dataType:'json',
            async:true,
            data:{
                addBackerAccount : addBackerAccount,
                addRoleId : addRoleId,
                addBackerPassword : addBackerPassword,
                addBackerRepeatPassword : addBackerRepeatPassword
            },
            success:function(result){
                openTips(result.message);
                addBackerSubmitBoo = false;
                if(result.code == 1) {
                   setTimeout("refresh()",1000 );
                }
            }, error:function(){
                addBackerSubmitBoo = false;
                openTips("系统错误！");
            }
        });
    }

    //验证用户账号
    var validateAccount = true;
    var validateUserBoo = false;
    function validateUser(o) {
        if (validateUserBoo) {
            return;
        } else {
            validateUserBoo = true;
        }

        var backerAccount = o.value;
        if (!backerAccount || backerAccount.length<6 || backerAccount.length>16) {
            validateAccount = false;
            validateUserBoo = false;
            return openTips("用户账号必须是6-16位的数字或字母");
        }

        $.ajax({
            url: '<%=path %>' + "/backerWeb/backerAccount/validateAccount",
            type:'post',
            dataType:'json',
            async:true,
            data:{
                backerAccount : backerAccount
            },
            success:function(result){
                validateUserBoo = false;
                if (parseInt(result.code) != 1) {
                    validateAccount = false;
                    return openTips(result.message);
                }
                validateAccount = true;
            },
            error:function(){
                validateUserBoo = false;
            }
        });
    }

    //页面刷新
    function refresh() {
        $("#queryForm").submit();
    }

    //登陆页跳转
    function backLogin() {
        window.location.href = "<%=path%>" + "/backLogin";
    }
</script>

<script type="text/javascript">
    var popObj;
    $(function(){
        $(".stop").click(function(){
            $(".mask").fadeIn();
            $(".stop_pop").fadeIn();
            popObj = ".stop_pop"
        });
        $(".start").click(function(){
            $(".mask").fadeIn();
            $(".start_pop").fadeIn();
            popObj = ".start_pop"
        });
        $(".reset").click(function(){
            $(".mask").fadeIn();
            $(".reset_pop").fadeIn();
            popObj = ".reset_pop"
        });
        $(".delete").click(function(){
            $(".mask").fadeIn();
            $(".delete_pop").fadeIn();
            popObj = ".delete_pop"
        });
        $(".add").click(function(){
            $(".mask").fadeIn();
            $(".add_pop").fadeIn();
            popObj = ".add_pop"
        });
        $(".changeRole").click(function(){
            $(".mask").fadeIn();
            $(".changeRole_pop").fadeIn();
            popObj = ".changeRole_pop"
        });
        $(".changePassword").click(function(){
            $(".mask").fadeIn();
            $(".changePassword_pop").fadeIn();
            popObj = ".changePassword_pop"
        });
        $(".cancel").click(function(){
            $(".mask").fadeOut("fast");
            $(popObj).fadeOut("fast");
        });
        /*$(".yes").click(function(){
            $(".mask").fadeOut("fast");
            $(popObj).fadeOut("fast");
        });*/
    });

    function openTip()
    {
        openTips("阿萨德芳");
    }
</script>

</body>
</html>
