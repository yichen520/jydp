
$(function () {
        // 首页轮播图
    banner();
    notice();
    function banner() {  
        var mySwiper = new Swiper('.banner', {
            autoplay: 3000,//可选选项，自动滑动
            speed: 3000,
            pagination: '.swiper-pagination',
        })
    };
    //公告
    function notice() {  
		setInterval("noticeUp('.noticeContent .noticebox','-0.5rem',500)", 2500);   
    };
    
});
function noticeUp(obj,top,time) {
    $(obj).animate({
    marginTop: top
    }, time, function () {
    $(this).css({marginTop:"0"}).find(".noticebox").appendTo(this);
    })
}