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

  $('.file-upload-alipay').on('change', function () {
    var obj = document.querySelector(".file-upload-alipay");
    if (!obj.files[0]) {
      return
    }
    var fileName = obj.files[0].name
    if (fileName.length > 20) {
      fileName = fileName.slice(0, 15) + '...' + fileName.slice(-5)
    }
    $('.file-name-alipay').text(fileName)
    $('.file-name-alipay').css('color', '#35394f')
  })

  $('.file-upload-wechat').on('change', function () {
    var obj = document.querySelector(".file-upload-wechat");
    if (!obj.files[0]) {
      return
    }
    var fileName = obj.files[0].name
    if (fileName.length > 20) {
      fileName = fileName.slice(0, 15) + '...' + fileName.slice(-5)
    }
    $('.file-name-wechat').text(fileName)
    $('.file-name-wechat').css('color', '#35394f')
  })

})