$(function () {
    show();
    btnCode();
    // 弹出选择手机
    function show() {
        var bgHeight = $(document).height();
        $('.choseNumber').on('click',function() {
            $('.chosePhone').css("height",bgHeight +"px");
            $.get("/jydp/userWap/forgetPassword/phoneArea",function(result){
                var list = {};
                var myData = result.data.phoneAreaMap;
                list.phoneAreaMap = [];
                var i = 0;
                for(var key in myData){
                    var obj = {"cityNum":key,"city":myData[key]};
                    list.phoneAreaMap[i++] = obj;
                }
                var compileTemplate = $("#getPhoneArea").html();
                var compileComplile = Handlebars.compile(compileTemplate);
                var headerHtml = compileComplile(list);
                $("#phoneAreaContainer").html(headerHtml);
            });
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
        var phone="";
        var areaCode="";
        var myreg=/^[1][3,4,5,7,8][0-9]{9}$/;
        if (obj.attr("id")=="oldPhoneCode") {
            areaCode=$("#oldAreaCode").text()
            phone=$("#oldPhone").text();
        } else {
            areaCode=$("#newAreaCode").text()
            phone=$("#newPhone").val();
        }
        if (!myreg.test(phone)) {
            openTips("手机号格式不正确");
            return;
        }
        $.ajax({
            url: "/jydp/sendCode/sendPhoneCode",
            type:'post',
            dataType:'json',
            async:true,
            data:{
                phoneNumber : areaCode+phone
            },
            success:function(result){
                openTips(result.message);
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
});