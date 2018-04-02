$(function () {
    $('.volume').on('click',function(){
        window.location.href = "volume.jsp"
    });
    $('.entrust').on('click',function(){
        window.location.href = path+"/userWap/userInfo/record/entrust/show.htm"
    });
    $('.helpCenter').on('click',function(){
        window.location.href = "helpDetail.html"
    });
    $('.backimg').on('click',function(){
        window.location.href = path+"/userWap/userInfo/show.htm"
    });
});