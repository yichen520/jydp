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
    <link rel="stylesheet" type="text/css" href="<%=path %>/resources/css/web/public.css" />
    <link rel="stylesheet" type="text/css" href="<%=path %>/resources/css/web/simpleTips.css" />

    <title>实名认证</title>
</head>
<body>

<div class="fHeader">
    <div class="headerInfo">
        <a href="<%=path%>/userWeb/homePage/show">
            <img src="<%=path %>/resources/image/web/trade_logo.png" class="logo"/>
            交易大盘
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

        <p class="phoneInput">
            <label class="popName">姓名<span class="star">*</span></label>
            <input type="text" id="userName" class="entry" placeholder="您的身份证姓名" maxlength="8"
                   onkeyup="value=value.replace(/[^\u4E00-\u9FA5]/g,'')" onblur="value=value.replace(/[^\u4E00-\u9FA5]/g,'')"/>
        </p>

        <p class="phoneInput">
            <label class="popName">证件号<span class="star">*</span></label>
            <input type="text" id="userCertNo" class="entry" placeholder="您的身份证号码" maxlength="18"/>
        </p>

        <p class="phoneInput">
            <label class="popName" style="line-height: 14px">证件照<span class="star">*</span></label>
            <span class="img">
                 <span class="picBox" id="imageAdd">
                     <img src="<%=path %>/resources/image/web/real.png" class="picMin"/>
                     <input type="file" name="imageList" value="" class="file" onchange="fileOnchange(this)"/>
                 </span>
            </span>
        </p>

        <p class="phoneInput phoneInput_mark">
            <span class="mark">提示：请上传您的证件正反面及手持证件照，否则可能会影响您的账号审核通过率</span>
        </p>
        <input type="text" value="提&nbsp;交" class="submit" onfocus="this.blur()" onclick="add();"/>
    </div>
</div>
<form id="queryForm" method="post" action="<%=path%>/userWeb/identificationController/show">
    <input type="hidden" id="userId" name="userId" value="${userId}">
    <input type="hidden" id="userAccount" name="userAccount" value="${userAccount}">
</form>

<div class="forgetFoot">盛临九洲版权所有</div>

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

    //多图片上传
    function fileOnchange(target) {
        var check = checkFileImage(target);
        if(!check){
            return;
        }

        var fileNum = $('.pic').length;

        var objUrl = getObjectURL(target.files[0]);
        console.log(objUrl);
        if (objUrl) {
            var dele = '<span class="picBox_max" id="imageDel_'+ fileNum +'">' +
                            '<img src=' + objUrl + ' class="pic"/>' +
                            '<span class="delete">' +
                                '<img src="<%=path %>/resources/image/web/delete.png"/>' +
                            '</span>' +
                        '</span>';
            $(target).parent().before(dele);
        }

        $("#imageAdd").append("<input type='file' id='imageAdd_"+ fileNum +"' class='file' name='imageList' onchange='fileOnchange(this)'/>");

        //删除图片
        $(function () {
            $(".picBox_max .delete").click(function () {
                var id=$(this).parent().attr("id");
                var idNum = id.substr(9, id.length)
                $("#imageAdd_"+idNum).remove();

                $(this).parent().remove();
                limit(id);
            });
        });
        limit(fileNum);
    }

    function limit(num) {
        if (num == 8) {
            $('.file').hide();
            $('.picBox').hide();
        } else {
            $('.file').show();
            $('.picBox').show();
        }
    }

    //新增实名认证
    var addBoo = false;
    function add() {
        if(addBoo){
            return false;
        }else{
            addBoo = true;
        }

        var userAccount = '${userAccount}';
        var userId = '${userId}';
        var userName = $("#userName").val();
        var userCertNo = $("#userCertNo").val();
        var imageList = document.getElementsByName("imageList");

        //$("#userName").val("");
        //$("#userCertNo").val("");
        if (userName == null || userName == "") {
            addBoo = false;
            return openTips("请输入您的姓名");
        }
        if (userCertNo == null || userCertNo == "") {
            addBoo = false;
            return openTips("请输入您的身份证号码");
        }
        if (!IdentityCodeValid(userCertNo)) {
            addBoo = false;
            return ;
        }
        if (imageList.length < 4) {
            addBoo = false;
            return openTips("请至少上传三张证件照");
        }
        if (imageList.length > 10) {
            addBoo = false;
            return openTips("最多上传9张证件照");
        }

        var formData = new FormData();
        formData.append("userAccount", userAccount);
        formData.append("userId", userId);
        formData.append("userName", userName);
        formData.append("userCertNo", userCertNo);
        for (var i=0; i<imageList.length-1;i++) {
            formData.append("image_"+i, imageList[i].files[0]);
        }
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
                    $("#queryForm").submit();
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
            var fileend = filepath.substring(filepath.indexOf("."));
            if (filetypes && filetypes.length > 0) {
                for (var i = 0; i < filetypes.length; i++) {
                    if (filetypes[i] == fileend) {
                        isnext = true;
                        break;
                    }
                }
            }
            if (!isnext) {
                openTips("图片格式必须是,jpeg,jpg,png中的一种！");
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