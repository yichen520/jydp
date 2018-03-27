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
            let cityNum = $(this).find('.cityNum').text();
            $('.chosePhone').css("height","0");
            $('.chosePhone').hide(); 
            $('.num').text(cityNum);
        });
        
    };
    
    //获取验证码60s重新发送
    function btnCode() {
        $('.code').on('click',function () {
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
});