<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <meta http-equiv="Cache-Control" content="no-cache" />

    <link rel="stylesheet" href="css/common.css">
    <link rel="stylesheet" href="css/register.css">

    <title>注册</title>
</head>
<body>
    <div class="back">
        <a href="login.html"><img src="./images/back.png" /></a>
    </div>
    <div class="registerBox">
        <div class="title">注册</div>
        <div class="registerContent">
            <div class="userName">
                <input type="text" placeholder="登录帐号为字母、数字，6～16个字符"/>
            </div>
            <div class="userPhone">
                <select>
                    <option value="+86">+86</option>
                    <option value="+86">+16</option>
                    <option value="+86">+05</option>
                </select>
                <input type="number" placeholder="您的手机号"/>
            </div>
            <div class="userCode">
                <input type="number" placeholder="请输入6位短信验证码"/>
                <p>获取验证码</p>
            </div>
            <div class="userPassword">
                <input type="password" placeholder="登录密码为字母、数字，6～16个字符"/>
            </div>
            <div class="userPasswordTwo">
                <input type="password" placeholder="请再次输入密码"/>
            </div>
        </div>
        <div class="confirm">注册</div>
        <div class="footer">
            <input type="checkbox" />
            <p>已阅读并接受<span>《用户注册协议》</span></p>
        </div>
    </div>
</body>

<script src="js/common.js"></script>
<script src="js/zepto.min.js"></script>
<script src="js/jquery-2.1.4.min.js"></script>
</html>