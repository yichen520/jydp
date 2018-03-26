$(function () {
    $('.back').on('click',function(){
        window.location.href = "mine.html"
    });
    $('.backimg').on('click',function(){
        window.location.href = "hot.html"
    });
    $('.content').on('click','li',function(){
        window.location.href = "hotDeatil.html"
    });
});