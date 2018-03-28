$(function () {
    btnCode();
//获取验证码60s重新发送
    function btnCode() {
        $('.userCode').on('click','p',function () {
            clickButton(this);
        });
    }
    function clickButton(obj) {
        var obj = $(obj);
        var phone=obj.text().replace(/\ +/g,"");
        $.ajax({
            url: "/jydp/sendCode/sendPhoneCode",
            type:'post',
            dataType:'json',
            async:true,
            data:{
                phoneNumber : phone
            },
            success:function(result){
                //openTips(result.message);
            },
            error:function(){
                return openTips("服务器错误");
            }
        });
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
    tabChage();
    function tabChage() {
        $('.tab').on('click','p',function () {
            var i = $(this).index();
            $(this).addClass('tabChose').siblings().removeClass('tabChose');
            $('.con .registerContent').eq(i).show().siblings().hide();
        });
    };
});
