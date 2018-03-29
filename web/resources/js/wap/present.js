show();
function show() {
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
function showDialog(coinRecordNo) {
    var bgHeight = $(document).height();
    $('.bg').css("height",bgHeight +"px");
    $('.showBox').css("display","block");
    $('.showBox').animate({opacity:'1'},"1000");
    $("#recallRecordNo").val(coinRecordNo);
    alert(coinRecordNo);
}
$('.backimg').on('click',function () {
    location.href = webPath + "/userWap/wapTransactionPendOrderController/showMyRecord";
});