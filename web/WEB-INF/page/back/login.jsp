<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <link rel="icon" href="<%=path %>/resources/image/back/icon.ico" type="image/x-ico" />

    <link rel="stylesheet" type="text/css" href="<%=path %>/resources/css/back/login.css" />
<%--
    <link rel="stylesheet" type="text/css" href="<%=path %>/resources/css/back/public.css" />
--%>
    <link rel="stylesheet" type="text/css" href="<%=path %>/resources/css/back/simpleTips.css" />

    <title>登录</title>
</head>

<body>
<div class="loginBg">
    <div class="content">
        <img src="<%=path %>/resources/image/back/login_trade.png" class="login_logo" />
		
		<div class="login_form">
			<form id="loginForm" action="<%=path %>/backerWeb/backerLogin/login" method="post">
	            <p class="loginTitle">管理员登录</p>
	            <p class="loginInfo">
	                <img src="<%=path %>/resources/image/back/user.png" class="loginImg" />
	                <input type="text" class="loginInput" placeholder="管理员登录用户名" id="backerAccount" name="backerAccount" value="${backerAccount }" 
	                	onpaste="return false" oncontextmenu="return false" oncopy="return false" oncut="return false"
	                	maxLength="16" onkeyup="value=value.replace(/[^\a-\z\A-\Z\d]/g,'')" onblur="value=value.replace(/[^\a-\z\A-\Z\d]/g,'')"/>
	            </p>
	            <p class="loginInfo">
	                <img src="<%=path %>/resources/image/back/password.png" class="loginImg" />
	                <input type="password" class="loginInput" placeholder="管理员登录密码" id="password" name="password" 
	                 	onpaste="return false" oncontextmenu="return false" oncopy="return false" oncut="return false"
	                	maxLength="16" onkeyup="value=value.replace(/[^\a-\z\A-\Z\d]/g,'')" onblur="value=value.replace(/[^\a-\z\A-\Z\d]/g,'')"/>
	            </p>
	            <p class="checkCode">
	                <input type="text" id="validateCode" name="validateCode" class="loginCheck" placeholder="右侧图片验证码" 
	                	maxLength="4" onkeyup="value=value.replace(/[^\a-\z\A-\Z\d]/g,'')" onblur="value=value.replace(/[^\a-\z\A-\Z\d]/g,'')"
	                	onkeypress="keypressHandle(event);"/>
	                <img src="<%=path%>/kaptcha/getKaptchaImage" id="kaptchaImage" class="codeImg" />
	            </p>

	            <input type="text" value="登&nbsp;录" class="loginBtn" onfocus="this.blur()" onclick="loginSubmit();"/>
			</form>
		</div>
		<div class="loginFooter">盛临九洲版权所有</div>
    </div>
</div>

<div id="footer"></div>

<script type="text/javascript" src="http://libs.baidu.com/jquery/2.1.4/jquery.min.js"></script>
<%--<script type="text/javascript" src="<%=path %>/resources/js/loadPageBack.js"></script>--%>
<script type="text/javascript" src="<%=path %>/resources/js/simpleTips.js"></script>

<script type="text/javascript">
	//生成验证码  
	$(function() {
		$('#kaptchaImage').click(function () {
			$(this).hide().attr('src', '<%=path%>/kaptcha/getKaptchaImage?' + Math.floor(Math.random()*100) ).fadeIn();
			event.cancelBubble=true;
		});
	});
	
    //回车执行
	function keypressHandle(event) {
        if (event.keyCode == "13") {
            loginSubmit();
        }
    }
</script>

<script type="text/javascript">
	window.onload = function() {
        var code = '${code}';
        var message = '${message}';
        
        if(code != 1 && message != "") {
            openTips(message);
            return false;
        }
    }

	//验证登陆
	function loginSubmit() {
		var backerAccount = $("#backerAccount").val();
		var password = $("#password").val();
		var validateCode = $("#validateCode").val();
		
		if (isEmpty(backerAccount) || backerAccount.length<6 || backerAccount.length>16) {
			openTips("请输入账号，6~16个字符");
            return;
		}
		if (isEmpty(password) || password.length<6 || password.length>16) {
			openTips("请输入密码，6~16个字符");
            return;
		}
		if (isEmpty(validateCode) || validateCode.length != 4) {
            openTips("请输入验证码");
            return;
        }
		
		$("#loginForm").submit();
	}
</script>

<script type="text/javascript">
	//字符串判空，为空返回true，非空返回false
	function isEmpty(validateStr) {
		var map = {};
		map[null] = true;
		map[""] = true;
		map[" "] = true;
		map["null"] = true;
		map["NULL"] = true;
		map["undefined"] = true;
		map[undefined] = true;
		return map[validateStr] ? true : false;
	}
</script>
</body>
</html>
