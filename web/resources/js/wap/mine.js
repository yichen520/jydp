$(function () {
    open()
    function open() {
        var bgHeight = $(document).height();
        $('.open').on('click', function () {
            $.get(path+"/userWap/tradeCenter/currencyInfo",function (result) {
                if (result.code!=0) {
                    openTips(result.message)
                    return;
                }
                var myTemplate = Handlebars.compile($("#table-template").html());
                $('#currencyList').html(myTemplate(result.transactionUserDealList));
            })
            setTimeout(function () {
                $('#wrapper').hide();
            }, 450);
            $('.closeAnthoer').css("height", bgHeight + "px");
            $('.choseBz').css("height", bgHeight + "px");
            $('.choseBzBox').css("height", '100%');
            $('.choseBzBox').show();
            $('.choseBzBox').animate({left: '0'}, "500");
        });
        $('.closeBox').on('click', function () {
            $('#wrapper').show();
            setTimeout(function () {
                $('.closeAnthoer').css("height", "0");
                $('.choseBz').css("height", "0");
                $('.choseBzBox').css("height", "0");
            }, 450);
            $('.choseBzBox').animate({left: '-100%'}, "500");
        });
    }
});