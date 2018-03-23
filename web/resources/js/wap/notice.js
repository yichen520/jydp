$(function () {
    $('.back').on('click',function(){
        window.location.href = "mine.html"
    });
    $('.backimg').on('click',function(){
        window.location.href = "notice.html"
    });
    $('.content').on('click','li',function(){
        window.location.href = "noticeDeatil.html"
    });
});