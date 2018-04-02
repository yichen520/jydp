$(function () {
//获取验证码60s重新发送
        var wait=60;
        function time(o) {
            if (wait == 0) {
                o.removeAttribute("disabled");
                o.value="获取验证码";
                wait = 60;
            } else {
                o.setAttribute("disabled", true);
                o.value="重新发送(" + wait + ")";
                wait--;
                setTimeout(function() {
                        time(o)
                    },
                    1000)
            }
        }
        $('.code').on('click',function(){
            time(this);
            var phone=$("#areaCode").text()+$("#phoneNumber").val();
            $.ajax({
                url: "/jydp/sendCode/sendPhoneCode",
                type:'post',
                dataType:'json',
                async:true,
                data:{
                    phoneNumber : phone
                },
                success:function(result){
                    openTips(result.message);
                },
                error:function(){
                    return openTips("服务器错误");
                }
            });
        });


    tabChage();
    function tabChage() {
        $('.tab').on('click','p',function () {
            var i = $(this).index();
            $(this).addClass('tabChose').siblings().removeClass('tabChose');
            $('.con .registerContent').eq(i).show().siblings().hide();
        });
    };
});
