$(function () {
    //买入 卖出 委托管理tab切换
    tabChange();
    open();
    function tabChange() {  
        $(".mainTitle li:eq(1)").click(function () {
            $('.borderSell').show();
            $('.borderBuy').hide();
            $('.borderEntrust').hide();
            $('.sell').fadeIn();
            $('.buy').hide();
            $('.entrust').hide();
        });
        $(".mainTitle li:eq(2)").click(function () {
            $('.borderSell').hide();
            $('.borderBuy').hide();
            $('.borderEntrust').show();
            $('.sell').hide();
            $('.buy').hide();
            $('.entrust').fadeIn();
        });
        $(".mainTitle li:eq(0)").click(function () {
            $('.borderSell').hide();
            $('.borderBuy').show();
            $('.borderEntrust').hide();
            $('.sell').hide();
            $('.buy').fadeIn();
            $('.entrust').hide();
        });    
    };
    //展开货币选择
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
                   
    }
var h=$(window).height();
    $(window).resize(function() {
        if($(window).height()<h){
            $('footer').hide();
        }
        if($(window).height()>=h){
            $('footer').show();
        }
    });
});