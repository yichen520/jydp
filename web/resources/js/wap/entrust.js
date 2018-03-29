$(function () {
    $('.back').on('click',function(){
        window.location.href = "myRecord.html"
    });
    $('.see').on('click',function(){
        window.location.href = "volume.html"
    });
    show();
    function show() {
        var bgHeight = $(document).height();
        $('.cancel').on('click',function() {
            $('.bg').css("height",bgHeight +"px");
            $('.showBox').css("display","block");
            $('.showBox').animate({opacity:'1'},"1000");
        });
        $('.okay').on('click',function() {
            $('.bg').css("height","0");
            $('.showBox').animate({opacity:'0'},"100");
            setTimeout(function(){
                    $('.showBox').css('display','none');
            },100)
        });
        $('.cancelShow').on('click',function() {
            $('.bg').css("height","0");
            $('.showBox').animate({opacity:'0'},"100");
            setTimeout(function(){
                    $('.showBox').css('display','none');
            },100)
        });
        
    };
});