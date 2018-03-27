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
    
    //获取验证码60s重新发送 异步发送短信
    function btnCode() {
        $('.userCode').on('click','p',function () {
            var area =  $("#area").html();
            var phone = $("#phoneNumber").val();
            var regPos = /^\d+(\.\d+)?$/; //非负浮点数
            var phoneReg = /^1([358][0-9]|4[579]|66|7[0135678]|9[89])[0-9]{8}$/;
            if (!phone) {
                phoneBoo = false;
                return openTips("请输入您的手机号");
            }

            if(!regPos.test(phone) || phone.length > 11 || phone.length < 6){
                phoneBoo = false;
                return openTips("请输入正确手机号");
            }

            if (area == "+86" && !phoneReg.test(phone)) {
                phoneBoo = false;
                return openTips("请输入正确手机号");
            }
            $.ajax({
                url: "/jydp/sendCode/sendPhoneCode",
                type:'post',
                dataType:'json',
                async:true,
                data:{
                    phoneNumber : area+phone
                },
                success:function(result){
                    openTips(result.message);
                },
                error:function(){
                    return openTips("服务器错误");
                }
            });
            clickButton(this);
        });
    }
    function clickButton(obj) {
        var obj = $(obj);
        obj.attr("disabled", "disabled");/*按钮倒计时*/
        var time = 60;
        var set = setInterval(function () {
            obj.text(--time + "s后重新获取");
        }, 1000);/*等待时间*/
        setTimeout(function () {
            obj.attr("disabled", false).text("再次获取");/*倒计时*/
            clearInterval(set);
        }, 60000);
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