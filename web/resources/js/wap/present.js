show();
function show() {
    var bgHeight = $(document).height();
    $('.withdraw').on('click',function() {
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
$('.backimg').on('click',function () {
    location.href = "javascript:history.back(-1)"
});