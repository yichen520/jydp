<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/resources/page/common/path.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <link rel="icon" href="<%=path %>/resources/image/web/icon.ico" type="image/x-ico" />

    <link rel="stylesheet" type="text/css" href="<%=path %>/resources/css/web/authentication.css" />
    <link rel="stylesheet" type="text/css" href="<%=path %>/resources/css/web/simpleTips.css" />

    <title>实名认证</title>
</head>
<body>

<div class="fHeader">
    <div class="headerInfo">
        <a href="<%=path%>/userWeb/homePage/show">
            <img src="<%=path %>/resources/image/web/trade_logo.png" class="logo"/>
            盛源交易所
        </a>
    </div>
</div>

<div class="content">
    <p class="auditTitle">
        <span class="titleR">实名认证</span>
    </p>

    <div class="audit">
        <p class="phoneInput">
            <label class="popName">账号</label>
            <span class="number">${userAccount}</span>
        </p>

        <p class="phoneInput phoneInputName">
            <label class="popName">姓名<span class="star">*</span></label>
            <input type="text" id="userName" class="entry" placeholder="您的证件姓名" maxlength="16"
                   onkeyup="matchUtil(this, 'rightful')" onblur="matchUtil(this, 'rightful')"/>
        </p>

        <p class="phoneInput">
            <label class="popName">证件号<span class="star">*</span></label>
            <span class="popCode">
                <select class="select" id="userCertType">
                    <option value="1">身份证</option>
                    <option value="2">护照</option>
                </select>
                <input type="text" id="userCertNo" class="phone" placeholder="您的证件号码" maxlength="18"
                       onkeyup="matchUtil(this, 'ENumber')" onblur="matchUtil(this, 'ENumber')"/>
            </span>
        </p>

        <p class="phoneInput">
            <label class="popName" style="line-height: 14px">证件照<span class="star">*</span></label>
            <span class="img">
                <span class="picBox">
                    <input type="file" id="frontImg" value="" class="file"/><img src="<%=path %>/resources/image/web/real.png" class="pic"/>
                    <span>证件正面照</span>
                </span>
                <span class="picBox">
                    <input type="file" id="backImg" value="" class="file"/><img src="<%=path %>/resources/image/web/real.png" class="pic"/>
                    <span>证件反面照</span>
                </span>
            </span>
        </p>

        <p class="phoneInput phoneInput_mark">
            <span class="mark">提示：请上传您的证件正反面，否则可能会影响您的账号审核通过率</span>
        </p>
        <input type="text" value="提&nbsp;交" class="submit" onfocus="this.blur()" onclick="add();"/>
    </div>
</div>
<form id="queryForm" method="post" action="<%=path%>/userWeb/identificationController/show">
    <input type="hidden" id="userId" name="userId" value="${userId}">
    <input type="hidden" id="userAccount" name="userAccount" value="${userAccount}">
</form>

<div class="forgetFoot">盛源交易所版权所有</div>

<script type="text/javascript" src="http://libs.baidu.com/jquery/2.1.4/jquery.min.js"></script>
<script type="text/javascript" src="<%=path %>/resources/js/loadPageWeb.js"></script>
<script type="text/javascript" src="<%=path %>/resources/js/simpleTips.js"></script>

<script type="text/javascript">
    window.onload = function() {
        var code = '${code}';
        var message = '${message}';
        if (code != 1 && message != "") {
            openTips(message);
            return false;
        }

    }

    var formData = new FormData();
    $(".file").change(function () {
        if(this.files[0] == undefined){
            return;
        }
        var check = checkFileImage(this);
        if(!check){
            return;
        }

        //图片压缩
        var fileObj = this.files[0];
        var fileId = this.id;

        var objUrl = getObjectURL(fileObj);
        if (objUrl) {
            $(this).parent().find("img").attr("src", objUrl)
        }

        if (fileId == "frontImg" && formData.has("frontImg")) {
            formData.delete("frontImg");
        }
        if (fileId == "backImg" && formData.has("backImg")) {
            formData.delete("backImg");
        }

        //大于400K，进行压缩上传
        if (fileObj.size / 1024 > 400) {
            var quality = 0.5;
            if (fileObj.size / 1024 < 5 * 1024) {
                //5M以内
                quality = 0.7;
            }
            if (fileObj.size / 1024 <= 10 * 1024 && fileObj.size / 1024 >= 7 * 1024) {
                //7M - 10M
                quality = 0.3;
            }

            photoCompress(fileObj, {
                quality: quality
            }, function (base64Codes) {
                var file = convertBase64UrlToBlob(base64Codes);
                if (fileId == "frontImg") {
                    formData.append("frontImg", file, "file_frontImg.jpg"); // 文件对象
                } else {
                    formData.append("backImg", file, "file_backImg.jpg"); // 文件对象
                }
            });
        } else { //小于等于400k 原图上传
            if (fileId == "frontImg") {
                formData.append("frontImg", $('#frontImg')[0].files[0]);
            } else {
                formData.append("backImg", $('#backImg')[0].files[0]);
            }
        }
    });

    /*
        三个参数
        file：一个是文件(类型是图片格式)，
        w：一个是文件压缩的后宽度，宽度越小，字节越小
        objDiv：一个是容器或者回调函数
        photoCompress()
     */
    function photoCompress(file, w, objDiv) {
        var ready = new FileReader();
        /*开始读取指定的Blob对象或File对象中的内容. 当读取操作完成时,
        readyState属性的值会成为DONE,如果设置了onloadend事件处理程序,则调用之.
        同时,result属性中将包含一个data: URL格式的字符串以表示所读取文件的内容.*/
        ready.readAsDataURL(file);
        ready.onload = function () {
            var re = this.result;
            canvasDataURL(re, w, objDiv)
        }
    }

    function canvasDataURL(path, obj, callback) {
        var img = new Image();
        img.src = path;
        img.onload = function () {
            var that = this;
            // 图像质量
            var quality = 0.7;  // 默认图片质量为0.7
            if (obj.quality && obj.quality <= 1 && obj.quality > 0) {
                quality = obj.quality;
            }
            // 默认按比例压缩
            var w = that.width * quality;
            var h = that.height * quality;
            //生成canvas
            var canvas = document.createElement('canvas');
            var ctx = canvas.getContext('2d');
            // 创建属性节点
            var anw = document.createAttribute("width");
            anw.nodeValue = w;
            var anh = document.createAttribute("height");
            anh.nodeValue = h;
            canvas.setAttributeNode(anw);
            canvas.setAttributeNode(anh);
            ctx.drawImage(that, 0, 0, w, h);
            // quality值越小，所绘制出的图像越模糊
            var base64 = canvas.toDataURL('image/jpeg', quality);
            // 回调函数返回base64的值
            callback(base64);
        }
    }

    /**
     * 将以base64的图片url数据转换为Blob
     * @param urlData
     *            用url方式表示的base64图片数据
     */
    function convertBase64UrlToBlob(urlData) {
        var arr = urlData.split(','), mime = arr[0].match(/:(.*?);/)[1],
            bstr = atob(arr[1]), n = bstr.length, u8arr = new Uint8Array(n);
        while (n--) {
            u8arr[n] = bstr.charCodeAt(n);
        }
        return new Blob([u8arr], {type: mime});
    }

    //新增实名认证
    var addBoo = false;
    function add() {
        if(addBoo){
            openTips("图片上传中......");
            return false;
        }else{
            addBoo = true;
        }

        var userAccount = '${userAccount}';
        var userId = '${userId}';
        var userName = $.trim($("#userName").val());
        var userCertType = $("#userCertType").val();
        var userCertNo = $("#userCertNo").val();
        var frontImg = $("#frontImg").val();
        var backImg = $("#backImg").val();

        if (userName == null || userName == "" || userName.length > 16) {
            addBoo = false;
            return openTips("请输入您的姓名");
        }
        if (userName.length <= 1) {
            addBoo = false;
            return openTips("您的姓名过短");
        }
        if (userCertNo == null || userCertNo == "" || userCertNo.length > 18) {
            addBoo = false;
            return openTips("请输入您的证件号");
        }
        var regEx = /\s+/g;
        userName = userName.replace(regEx, ' ');
        //证件类型是身份证
        if (userCertType == 1) {
            var reg = /^[\u4e00-\u9fa5]+(·[\u4e00-\u9fa5]+)*$/.test(userName);
            if (!userName || !reg) {
                addBoo = false;
                return openTips("请输入正确的身份证姓名！");
            }
            if (!IdentityCodeValid(userCertNo)) {
                addBoo = false;
                return ;
            }
        }

        //证件类型是护照
        if (userCertType == 2) {
            if (userCertNo.length < 6) {
                addBoo = false;
                return openTips("护照号长度必须大于6位");
            }
        }
        $("#userName").val(userName);

        var frontImg2 = formData.get("frontImg");
        var backImg2 = formData.get("backImg");
        if (frontImg2 == null || frontImg2 == "") {
            addBoo = false;
            return openTips("请上传您的证件正面照");
        }
        if (backImg2 == null || backImg2 == "") {
            addBoo = false;
            return openTips("请上传您的证件反面照");
        }

        formData.append("userAccount", userAccount);
        formData.append("userId", userId);
        formData.append("userName", userName);
        formData.append("userCertType", userCertType);
        formData.append("userCertNo", userCertNo);

        $.ajax({
            url: '<%=path %>' + "/userWeb/identificationController/add",
            type:'post',
            dataType:'json',
            async:true,
            processData : false,
            contentType : false,
            data:formData,
            success:function(result){
                addBoo = false;
                if(result.code == 1) {
                    openTips(result.message)
                    setTimeout(function (){$("#queryForm").submit();}, 1000);
                } else {
                    openTips(result.message);
                }
            }, error:function(){
                addBoo = false;
                openTips("服务器异常，请稍后再试！");
            }
        });
    }

    //判断图片格式
    function checkFileImage(target) {
        var fileSize = 0;
        var filetypes = [".jpg", ".jpeg", ".png", ".JPG", ".JPEG", ".PNG"];
        var filepath = target.value;
        var filemaxsize = 1024 * 10;//10M
        if (filepath) {
            var isnext = false;
            var fileend = filepath.substring(filepath.lastIndexOf("."));
            if (filetypes && filetypes.length > 0) {
                for (var i = 0; i < filetypes.length; i++) {
                    if (filetypes[i] == fileend) {
                        isnext = true;
                        break;
                    }
                }
            }
            if (!isnext) {
                openTips("图片格式必须是jpeg,jpg,png中的一种！");
                target.value = "";
                return false;
            }
        } else {
            return false;
        }
        if (!target.files) {
            var filePath = target.value;
            var fileSystem = new ActiveXObject("Scripting.FileSystemObject");
            if (!fileSystem.FileExists(filePath)) {
                openTips("图片不存在，请重新输入！");
                return false;
            }
            var file = fileSystem.GetFile(filePath);
            fileSize = file.Size;
        } else {
            fileSize = target.files[0].size;
        }

        var size = fileSize / 1024;
        if (size > filemaxsize) {
            openTips("图片大小不能大于" + filemaxsize / 1024 + "M！");
            target.value = "";
            return false;
        }
        if (size <= 0) {
            openTips("图片大小不能为0M！");
            target.value = "";
            return false;
        }

        return true;
    }

    //身份证校验
    function IdentityCodeValid(code) {
        var city={11:"北京",12:"天津",13:"河北",14:"山西",15:"内蒙古",21:"辽宁",22:"吉林",23:"黑龙江 ",31:"上海",32:"江苏",
            33:"浙江",34:"安徽",35:"福建",36:"江西",37:"山东",41:"河南",42:"湖北 ",43:"湖南",44:"广东",45:"广西",46:"海南",
            50:"重庆",51:"四川",52:"贵州",53:"云南",54:"西藏 ",61:"陕西",62:"甘肃",63:"青海",64:"宁夏",65:"新疆",71:"台湾",
            81:"香港",82:"澳门",91:"国外"};
        var tip = "";
        var pass= true;
        if(!code || !/^\d{6}(19|20)?\d{2}(0[1-9]|1[0-2])(0[1-9]|[1-2][0-9]|3[0-1])\d{3}(\d|X)$/i.test(code)){
            tip = "身份证号码有误";
            pass = false;
        } else if (!city[code.substr(0,2)]) {
            tip = "身份证地址编码错误";
            pass = false;
        } else {
            //18位身份证需要验证最后一位校验位
            if(code.length == 18){
                code = code.split('');
                //∑(ai×Wi)(mod 11)
                //加权因子
                var factor = [ 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2 ];
                //校验位
                var parity = [ 1, 0, 'X', 9, 8, 7, 6, 5, 4, 3, 2 ];
                var sum = 0;
                var ai = 0;
                var wi = 0;
                for (var i = 0; i < 17; i++) {
                    ai = code[i];
                    wi = factor[i];
                    sum += ai * wi;
                }
                var last = parity[sum % 11];
                if (code[17] == 'x') {
                    code[17] = 'X';
                }
                if (last != code[17]) {
                    tip = "身份证号码错误";
                    pass =false;
                }
            }
        }
        if(!pass) openTips(tip);
        return pass;
    }

    var mapMatch = {};
    mapMatch['ENumber'] = /[^\a-\z\A-\Z\d]/g;
    mapMatch['rightful'] = /[%`~!@#$^&*()=|{}':;",_+\-\\\[\].<>/?！￥…（）—【】《》；：‘’”“。，、？1234567890]/g;
    function matchUtil(o, str) {
        o.value = o.value.replace(mapMatch[str], '');
    }
</script>
<script type="text/javascript">
    function getObjectURL(file) {
        var url = null;
        if (window.createObjectURL != undefined) { // basic
            url = window.createObjectURL(file);
        } else if (window.URL != undefined) { // mozilla(firefox)
            url = window.URL.createObjectURL(file);
        } else if (window.webkitURL != undefined) { // webkit or chrome
            url = window.webkitURL.createObjectURL(file);
        }
        return url;
    }

    $(function () {
        $(".pChoose").click(function () {
            $(".phone").show();
            $(".artificial").hide();
        });
        $(".aChoose").click(function () {
            $(".phone").hide();
            $(".artificial").show();
        })
    });

    $(function () {
        $(".mode span").click(function () {
            $(".mode span").removeClass("chooseState");
            $(this).addClass("chooseState");
        })
    });
</script>

</body>
</html>