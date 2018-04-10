$(function () {
    // 首页轮播图
    banner();
    //dom加载完loading图消失
    $("#loading").fadeOut();
    open();
    function banner() {
        var mySwiper = new Swiper('.banner', {
            autoplay: 1500,//可选选项，自动滑动
            speed: 200,
            autoplayDisableOnInteraction : false,
            pagination: '.swiper-pagination',
        })
    };
    //公告
    setInterval('AutoScroll(".notice")', 2500)
    // 弹出货币选择
    function open() {
        var bgHeight = $(document).height();
        $('.open').on('click', function () {
            $.get(path + "/userWap/tradeCenter/currencyInfo", function (result) {
                if (result.code != 0) {
                    openTips(result.message)
                    return;
                }
                var myTemplate = Handlebars.compile($("#table-template").html());
                $('#currencyList').html(myTemplate(result.transactionUserDealList));
            })

            $('.closeAnthoer').css("height", bgHeight + "px");
            $('.choseBz').css("height", bgHeight + "px");
            $('.choseBzBox').css("height", '100%');
            $('.choseBzBox').show();
            $('.choseBzBox').animate({left: '0'}, "500");
            setTimeout(function () {
                $('#wrapper').hide();
                $('footer').hide();
            }, 450);
        });
        $('.closeBox').on('click', function () {
            $('#wrapper').show();
            $('footer').show();
            setTimeout(function () {
                $('.closeAnthoer').css("height", "0");
                $('.choseBz').css("height", "0");
                $('.choseBzBox').css("height", "0");
            }, 450);
            $('.choseBzBox').animate({left: '-100%'}, "500");
        });

    }

});


function AutoScroll(obj) {
    $(obj).find("ul:first").animate({
            marginTop: "-0.48rem"
        },
        600,
        function () {
            $(this).css({
                marginTop: "0px"
            }).find("li:first").appendTo(this);
        });
}