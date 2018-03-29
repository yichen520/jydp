$(function () {
    show();
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
        var phone="";
        var areaCode="";
        var myreg=/^[1][3,4,5,7,8][0-9]{9}$/;
        if ($(this).attr("id")=="oldPhoneCode") {
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
    });

});