// 验证字母+数字
function checkoutValue(object) {
    var matchStr = /[^\a-\z\A-\Z\d]/g;
    var value = object.value;
    if (matchStr.test(value)) {
        object.value = object.value.replace(matchStr,'');
    }
}
//验证数字
function checkoutNumber(object) {
    var matchStr = /[^\d]/g;
    var value = object.value;
    if (matchStr.test(value)) {
        object.value = object.value.replace(matchStr,'');
    }
}
//验证用户姓名
function checkoutUserName(object) {
    var matchStr = /[%`~!@#$^&*()=|{}':;",_+\-\\\[\].<>/?！￥…（）—【】《》；：‘’”“。，、？1234567890]/g;
    var value = object.value;
    if (matchStr.test(value)) {
        object.value = object.value.replace(matchStr,'');
    }
}
//验证特殊匹配
function checkoutCountry(object) {
    var matchStr = /[%`~!@#$^&*()=|{}':;",_\-\\\[\].<>/?！￥…（）—【】《》；：‘’”“。，、？]/g;
    var value = object.value;
    if (matchStr.test(value)) {
        object.value = object.value.replace(matchStr,'');
    }
}