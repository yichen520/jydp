<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <meta http-equiv="Cache-Control" content="no-cache" />

    <link rel="stylesheet" href="<%=path%>/resources/css/wap/common.css">
    <link rel="stylesheet" href="<%=path%>/resources/css/wap/simpleTips_wap.css">
    <link rel="stylesheet" href="<%=path%>/resources/css/wap/revise.css">

    <title>修改支付密码</title>
</head>
<body>
    <header>
        <img src="<%=path%>/resources/image/wap/back.png" class="back"/>
        <p>修改支付密码</p>
    </header>

    <div class="registerBox">
        <div class="tab">
            <p class="tabChose">原密码修改</p>
            <p>通过手机号修改</p>
            <div class="clear"></div>
        </div>
        <div class="con">
        <!-- 原密码修改 -->
            <div class="registerContent" style="display:block">
                <div class="userPasswordTwo">
                    <input type="password" placeholder="原密码" maxlength="16" id="oldPwdPyPwd" onkeyup="formatPwd(this)"/>
                </div>
                <div class="userPassword">
                    <input  style="width:100%" type="password" placeholder="新密码为字母、数字，6～16个字符" maxlength="16" id="newPwdByPwd" onkeyup="formatPwd(this)"/>
                </div>
                <div class="userPassword">
                        <input type="password" placeholder="重复密码" maxlength="16" id="confirmPwdByPwd" onkeyup="formatPwd(this)"/>
                    </div>
            </div>
            <!-- 通过手机号修改 -->
            <div class="registerContent" style="display:none">
                <div class="userPassword">
                    <input style="width:100%" type="password" placeholder="新密码为字母、数字，6～16个字符" maxlength="16" id="newPwdByPhone" onkeyup="formatPwd(this)"/>
                </div>
                <div class="userPassword">
                        <input type="password" placeholder="重复密码" maxlength="16" id="confirmPwdByPhone" onkeyup="formatPwd(this)"/>
                    </div>
                <div class="userPhone">
                    <p class="num"><span id="areaCode">${phoneAreaCode}</span>&nbsp;<input type="hidden" value="${phoneNumber}" id="phoneNumber"/><span id="phoneNumberText"></span></p>
                </div>
                <div class="userCode">
                    <input type="number" placeholder="请输入6位短信验证码" oninput="if(value.length>6)value=value.slice(0,6)" id="codeByPhone"/>
                    <input class="code" value="获取验证码" readonly="readonly"/>
                </div>  
            </div>
        </div>
        <div class="confirm">提 交</div>
    </div>
    <!-- loading图 -->
    <div id="loading">
        <i></i>
    </div>
</body>

<script type="text/javascript" src="<%=path%>/resources/js/wap/common.js"></script>
<script type="text/javascript" src="<%=path%>/resources/js/wap/zepto.min.js"></script>
<script type="text/javascript" src="<%=path%>/resources/js/wap/jquery-2.1.4.min.js"></script>
<script type="text/javascript" src="<%=path%>/resources/js/wap/reviseZf.js"></script>
<script type="text/javascript" src="<%=path%>/resources/js/wap/simpleTips_wap.js"></script>

<script>

    var path = "<%=path%>"
    
    $(function () {
        var phone="<%=request.getAttribute("phoneNumber")%>";
        if (phone.length==6) {
            phone=phone.substring(0,3)+"***";
        }
        if (phone.length>6) {
            phone=phone.substring(0,3)+"****"+phone.substring(7)
        }
        $("#phoneNumberText").text(phone);
    })

    $(".back").click(function () {
        window.location.href="<%=path%>/userWap/userInfo/userCenter/show.htm";
    })
    
    function formatPwd(obj) {
        var matchStr = /[^\a-\z\A-\Z\d]/g;
        var value = $(obj).val();
        if (matchStr.test(value)) {
            $(obj).get(0).value=$(obj).get(0).value.replace(/[^\a-\z\A-\Z\d]/g,'');
        }
    }

    $(".confirm").click(function () {
        var oldPwd="";
        var newPwd="";
        var confirmPwd="";
        var code="";
        var regx = /^[A-Za-z0-9]*$/;
        if($(".registerContent").eq(0).css("display")=="block") {
            //原密码修改
            oldPwd=$("#oldPwdPyPwd").val();
            newPwd=$("#newPwdByPwd").val();
            confirmPwd=$("#confirmPwdByPwd").val();
            if(oldPwd=="") {
                openTips("请输入密码");
                return;
            }
            if (oldPwd.length<6 || oldPwd.length>16) {
                openTips("密码长度错误");
                return;
            }
            if (newPwd=="") {
                openTips("请输入新密码");
                return;
            }
            if (newPwd.length<6 || newPwd.length>16) {
                openTips("新密码长度错误");
                return;
            }
            if (!regx.test(newPwd)) {
                openTips("新密码格式不正确");
                return;
            }
            if(confirmPwd=="") {
                openTips("请输入确认密码");
                return;
            }
            if(!regx.test(confirmPwd)) {
                openTips("确认密码格式不正确");
                return;
            }
            if (confirmPwd.length<6 || confirmPwd.length>16) {
                openTips("确认密码长度错误");
                return;
            }
            if (newPwd!=confirmPwd) {
                openTips("两次输入密码不一致");
                return;
            }
            $.ajax({
                url: "<%=path%>/userWap/userInfo/payPassword/modifyByPwd",
                type:'post',
                dataType:'json',
                async:true,
                contentType: "application/json",
                data: JSON.stringify({
                    oldPassword: oldPwd,
                    newPassword: newPwd,
                    confirmPassword: confirmPwd
                }),
                success:function(result){
                    openTips(result.message);
                    if (result.code==1) {
                        setTimeout("skipToUserInfoHtml()",1000);
                    }
                },
                error:function(){
                    return openTips("服务器错误");
                }
            });
        } else {
            //通过手机号修改
            newPwd=$("#newPwdByPhone").val();
            confirmPwd=$("#confirmPwdByPhone").val();
            code=$("#codeByPhone").val().replace(/\ +/g,"");
            if (newPwd=="") {
                openTips("请输入新密码");
                return;
            }
            if (!regx.test(newPwd)) {
                openTips("新密码格式不正确");
                return;
            }
            if (newPwd.length<6 || newPwd.length>16) {
                openTips("新密码长度错误");
                return;
            }
            if (confirmPwd=="") {
                openTips("请输入确认密码");
                return;
            }
            if(!regx.test(confirmPwd)) {
                openTips("确认密码格式不正确");
                return;
            }
            if (confirmPwd.length<6 || confirmPwd.length>16) {
                openTips("确认密码长度错误");
                return;
            }
            if (newPwd!=confirmPwd) {
                openTips("两次输入密码不一致");
                return;
            }

            if (code=="" && newPwd!="" && confirmPwd !="") {
                openTips("验证码错误");
                return;
            }
            if (code.length !=6) {
                openTips("验证码长度错误")
                return;
            }
            $.ajax({
                url: "<%=path%>/userWap/userInfo/payPassword/modifyByPhone",
                type:'post',
                dataType:'json',
                async:true,
                contentType: "application/json",
                data: JSON.stringify({
                    newPassword: newPwd,
                    confirmPassword: confirmPwd,
                    validCode:code
                }),
                success:function(result){
                    openTips(result.message);
                    if (result.code==1) {
                        setTimeout("skipToUserInfoHtml()",1000);
                    }
                },
                error:function(){
                    return openTips("服务器错误");
                }
            });
        }
    })
    function skipToUserInfoHtml() {
        window.location.href="<%=path%>/userWap/userInfo/show.htm";
    }
</script>

</html>