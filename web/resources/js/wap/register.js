$(function () {
    show();
    btnCode();
    // 弹出选择手机
    function show() {
        var bgHeight = $(document).height();
        $('.choseNumber').on('click',function() {
            $('.chosePhone').css("height",bgHeight +"px");
            $('.chosePhone').show();
        });
        $('.search').on('click','p',function() {
            $('.chosePhone').css("height","0");
            $('.chosePhone').hide(); 
        });
        $('.searchList').on('click','li',function() {
            var cityNum = $(this).find('.cityNum').text();
            $('.chosePhone').css("height","0");
            $('.chosePhone').hide(); 
            $('.num').text(cityNum);
        });
        
    };
    var Repeatedclicks = true;
    //获取验证码60s重新发送 异步发送短信
    function btnCode() {
        $('.userCode').on('click','p',function () {
            if(!Repeatedclicks){
                return;
            }
            var area =  $("#area").html();
            var phone = $("#phoneNumber").val();
            var userAccount = $("#userAccount").val();
            if(!userAccount){
                return openTips("请输入您的账号");
            }
            if (userAccount.length < 6 || userAccount.length > 16) {
                return openTips( "账号长度在6~16个字符之间");
            }
            var commonReg = /^[A-Za-z0-9]{6,16}$/;
            if (!commonReg.test(userAccount)) {
                return openTips( "账号格式不正确");
            }

            var regPos = /^\d+(\.\d+)?$/; //非负浮点数
            var phoneReg = /^1([358][0-9]|4[579]|66|7[0135678]|9[89])[0-9]{8}$/;
            if (!phone) {
                return openTips("请输入您的手机号");
            }

            if(!regPos.test(phone) || phone.length > 11 || phone.length < 6){
                return openTips("请输入正确手机号");
            }

            if (area == "+86" && !phoneReg.test(phone)) {
                return openTips("请输入正确手机号");
            }
            var pageContext = $("#pageContext").val();
            $.ajax({
                url: pageContext+"/userWap/forgetPassword/phoneNumberCheck",
                type:'post',
                dataType:'json',
                async:true,
                data:{
                    userAccount : userAccount,
                    phoneNumber : phone,
                    phoneAreaCode : area
                },
                success:function(result){
                    if(result.code == 1){
                        sendCode(area+phone);
                    } else {
                        return openTips(result.message);
                    }
                },
                error:function() {
                    return openTips("服务器错误");
                }
            });
            clickButton(this);
            Repeatedclicks = false;

        });
    }
    function sendCode(areaPhone) {

        var pageContext = $("#pageContext").val();
        $.ajax({
            url: pageContext+"/sendCode/sendPhoneCode",
            type:'post',
            dataType:'json',
            async:true,
            data:{
                phoneNumber : areaPhone
            },
            success:function(result){
                openTips(result.message);
            },
            error:function() {
                return openTips("服务器错误");
            }
        });
    }

    function clickButton(name) {
        var obj = $(name);
        obj.attr("disabled", "disabled");/*按钮倒计时*/
        var time = 60;
        var set = setInterval(function () {
            obj.text("("+--time + ")s后重新获取");
            if(time < 0 ){
                clearInterval(set);
                Repeatedclicks = true;
                obj.attr("disabled", false).text("获取验证码");/*倒计时*/
            }
        }, 1000);/*等待时间*/
    };
    var flag = 1;
    $(".footer").on('click',function(){
        if(flag == 1){
            $(".checkboxBox").css("background","url(./images/check-yes.png) no-repeat");
            $(".checkboxBox").css("background-size","cover");
            flag = 0;
        } else{
            $(".checkboxBox").css("background","url(./images/check-no.png) no-repeat");
            $(".checkboxBox").css("background-size","cover");
            flag = 1;
        }
    })
});