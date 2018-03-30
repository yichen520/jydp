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


    var waitOld =60;
    var waitNew =60;
    function timeOld(o) {
        if (waitOld == 0) {
            o.removeAttribute("disabled");
            o.value="获取验证码";
            waitOld = 60;
        } else {
            o.setAttribute("disabled", true);
            o.value="重新发送(" + waitOld + ")";
            waitOld--;
            setTimeout(function() {
                    timeOld(o)
                },
                1000)
        }
    }
    function timeNew(o) {
        if (waitNew == 0) {
            o.removeAttribute("disabled");
            o.value="获取验证码";
            waitNew = 60;
        } else {
            o.setAttribute("disabled", true);
            o.value="重新发送(" + waitNew + ")";
            waitNew--;
            setTimeout(function() {
                    timeNew(o)
                },
                1000)
        }
    }

    $('.code').on('click',function(){
        var phone="";
        var areaCode="";
        var myreg=/^[1][3,4,5,7,8][0-9]{9}$/;
        if ($(this).attr("id")=="oldPhoneCode") {
            areaCode=$("#oldAreaCode").text()
            phone=$("#oldPhone").text();
            timeOld(this);
        } else {
            areaCode=$("#newAreaCode").text()
            phone=$("#newPhone").val();
            if ($("#newAreaCode").text()!='+86') {
                if (phone.length<6 || phone.length>11) {
                    openTips("手机号格式不正确");
                    return;
                }
            } else {
                if (!myreg.test(phone)) {
                    openTips("手机号格式不正确");
                    return;
                }
            }
            timeNew(this);
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