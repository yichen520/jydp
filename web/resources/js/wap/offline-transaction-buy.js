$(function () {

  $('.mask').hide();

  $('.choose').on('click', function () {
    $('html,body').removeClass('overflow'); //网页恢复滚动
    $('.mask').hide();
    $('.select').css('bottom', '-4.3rem');
    var map = {
      wechat: '微信转账',
      alipay: '支付宝转账',
      bank: '银行卡转账'
    };
    var checkValue = $("input[name='radio']:checked").val()
    $('.receipt-method-text').text(map[checkValue]);
  });

  $('.receipt-method-text').on('click', function () {
    $('html,body').addClass('overflow'); //使网页不可滚动
    $('.mask').show();
    $('.select').css('bottom', '0');
  });

  $('.mask').on('click', function () {
    $('html,body').removeClass('overflow'); //网页恢复滚动
    $('.mask').hide();
    $('.select').css('bottom', '-4.3rem');
  });

  $('.close-select').on('click', function () {
    $('html,body').removeClass('overflow'); //网页恢复滚动
    $('.mask').hide();
    $('.select').css('bottom', '-4.3rem');
  });

})