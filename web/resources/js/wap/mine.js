$(function () {
    open()
    function open() {
        var bgHeight = $(document).height();
        $('.open').on('click',function(){
            $('.closeAnthoer').css("height",bgHeight +"px");
            $('.choseBz').css("height",bgHeight +"px");
            $('.choseBzBox').css("height",bgHeight +"px");
            $('.choseBzBox').show();
            $('.choseBzBox').animate({left:'0'},"500");
        });
         $('.closeBox').on('click',function(){
            setTimeout(function(){
                $('.closeAnthoer').css("height","0");
                $('.choseBz').css("height","0");
                $('.choseBzBox').css("height","0");
            },450);
            $('.choseBzBox').animate({left:'-82%'},"500");
    });  
    //选择货币进入交易页面
    choseHb();
    function choseHb() {  
        $('.choseBzBox-content ul').on('click','li',function(){
            window.location.href = "deal.html"
        })
    };
    }
});