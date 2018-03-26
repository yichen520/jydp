<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <link rel="icon" href="<%=path%>/resources/image/back/icon.ico" type="image/x-ico" />

    <link rel="stylesheet" type="text/css" href="<%=path %>/resources/css/back/homeAd.css" />
    <link rel="stylesheet" type="text/css" href="<%=path %>/resources/css/back/public.css" />
    <link rel="stylesheet" type="text/css" href="<%=path %>/resources/css/back/simpleTips.css" />

    <title>首页广告</title>
</head>
<body>
<div id="header"></div>


<div class="content">
    <div id="menu"></div>

    <div class="contentRight">
        <div class="caption">
            <span class="title">首页广告</span>
        </div>
        <c:if test="${backer_rolePower['111002'] == 111002}">
            <p class="add">新增广告</p>
        </c:if>
        <div class="bottom">
            <table class="table" cellspacing="0" cellpadding="0">
                <tr class="tableTitle">
                    <td class="location">位置</td>
                    <td class="time">添加时间</td>
                    <td class="adTitle">标题</td>
                    <td class="pic">封面图</td>
                    <td class="link">web链接</td>
                    <td class="link">wap链接</td>
                    <td class="operate">操作</td>
                </tr>
                <c:forEach items="${homeAdList}" var="homeAd" >
                    <tr class="tableInfo">
                        <td class="location">${homeAd.rankNumber}</td>
                        <td class="time"><fmt:formatDate type="time" value="${homeAd.addTime }" pattern="yyyy-MM-dd HH:mm:ss"></fmt:formatDate></td>
                        <td class="adTitle">${homeAd.adsTitle}</td>
                        <td class="pic"><img src="${homeAd.adsImageUrlFormat}" /></td>
                        <td class="link"><a target="_blank" href="${homeAd.webLinkUrl}">${homeAd.webLinkUrl}</a></td>
                        <td class="link"><a target="_blank" href="${homeAd.wapLinkUrl}">${homeAd.wapLinkUrl}</a></td>
                        <td class="operate">
                            <c:if test="${backer_rolePower['111003'] == 111003}">
                                <c:if test="${homeAd.rankNumber != 1}">
                                    <input type="text" value="上&nbsp;移" class="adUp" onfocus="this.blur()" onclick="upMove('${homeAd.id}')"/>
                                </c:if>
                            </c:if>
                            <c:if test="${backer_rolePower['111004'] == 111004}">
                                <c:if test="${homeAd.rankNumber != maxRankNumber}">
                                    <input type="text" value="下&nbsp;移" class="adDown" onfocus="this.blur()" onclick="downMove('${homeAd.id}')"/>
                                </c:if>
                            <c:if test="${backer_rolePower['111007'] == 111007}">
                                <c:if test="${homeAd.rankNumber != 1}">
                                    <input type="text" value="置&nbsp;顶" class="toTop" onfocus="this.blur()" onclick="topNotice(${homeAd.id });"/>
                                </c:if>
                            </c:if>
                            </c:if>
                            <c:if test="${backer_rolePower['111005'] == 111005}">
                                <input type="text" value="修&nbsp;改" class="change" onfocus="this.blur()" onclick="openModify('${homeAd.id}', '${homeAd.adsTitle}','${homeAd.webLinkUrl}', '${homeAd.wapLinkUrl}' )"/>
                            </c:if>
                            <c:if test="${backer_rolePower['111006'] == 111006}">
                                <input type="text" value="删&nbsp;除" class="delete" onfocus="this.blur()" onclick="openDelete('${homeAd.id}')"/>
                            </c:if>
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </div>
    </div>
</div>


<div id="footer"></div>


<div class="mask">
    <div class="mask_content">
        <form action="<%=path%>/backerWeb/backerAdsHomepages/deleteHomeAd.htm" method="post">
            <div class="delete_pop">
                <p class="popTitle">删除操作</p>
                <p class="popTips"><img src="<%=path%>/resources/image/back/tips.png" class="tipsImg" />确定删除？</p>

                <input type="hidden" name="id" id="deleteId"/>
                <div class="buttons">
                    <input type="text" value="取&nbsp;消" class="cancel" onfocus="this.blur()" />
                    <input type="text" value="确&nbsp;定" class="yes" onfocus="this.blur()" onclick="deleteCheck()"/>
                </div>
            </div>
        </form>

        <div class="add_pop">
            <p class="popTitle">新增广告</p>
            <form id="addForm" action="<%=path%>/backerWeb/backerAdsHomepages/addHomeAd.htm" enctype="multipart/form-data" method="post">
                <p class="popInput">
                    <label class="popName">广告图片<span class="star">*</span></label>

                    <span class="popPic">
                        <input type="text" id="changead_t1"  class="choosePic" placeholder="请选择文件" onfocus="this.blur()" />
                        <input type="text"  onclick="document.getElementById('changead_a1').click();"  value="选择文件" class="choose_button" onfocus="this.blur();" />
                        <input type="file" class="file" name="adsImageUrl" id="changead_a1" onchange="checkFileImageTwo(this, 'changead_t1');"/>
                    </span>
                </p>
                <p class="popInput">
                    <label class="popName">标题名称<span class="star">*</span></label>
                    <input type="text" class="entry" placeholder="广告标题名称，2~16个字符" name="adsTitle" id="addAdsTitle" maxlength="16"/>
                </p>
                <p class="popInput">
                    <label class="popName">web链接地址</label>
                    <input type="text" class="entry" placeholder="链接地址，非必填" name="webLinkUrl" id="addWebLinkUrl" maxlength="500"/>
                </p>
                <p class="popInput">
                    <label class="popName">wap链接地址</label>
                    <input type="text" class="entry" placeholder="链接地址，非必填" name="wapLinkUrl" id="addWapLinkUrl" maxlength="500"/>
                </p>

                <div class="buttons">
                    <input type="text" value="取&nbsp;消" class="cancel" onfocus="this.blur()" />
                    <input type="text" value="确&nbsp;定" class="yes" onfocus="this.blur()" onclick="addCheck()"/>
                </div>
            </form>
        </div>

        <div class="change_pop">
            <p class="popTitle">修改广告</p>
            <form id="modifyForm" action="<%=path%>/backerWeb/backerAdsHomepages/modifyHomeAd.htm" enctype="multipart/form-data" method="post">
                <p class="popInput">
                    <label class="popName">广告图片</label>

                    <span class="popPic">
                        <input type="text" id="changead_t2"  class="choosePic" placeholder="该项不修改时，请勿上传" onfocus="this.blur()" />
                        <input type="text"  onclick="document.getElementById('changead_a2').click();"  value="选择文件" class="choose_button" onfocus="this.blur();" />
                        <input type="file" class="file" name="adsImageUrl" id="changead_a2" onchange="checkFileImageTwo(this, 'changead_t2');" />
                    </span>
                </p>
                <p class="popInput">
                    <label class="popName">标题名称<span class="star">*</span></label>
                    <input type="text" class="entry" placeholder="广告标题名称，2~16个字符" name="modifyAdsTitle" id="modifyAdsTitle" maxlength="16"/>
                </p>
                <p class="popInput">
                    <label class="popName">web链接地址</label>
                    <input type="text" class="entry" placeholder="链接地址，非必填" name="modifyWebLinkUrl" id="modifyWebLinkUrl" maxlength="500"/>
                </p>
                <p class="popInput">
                    <label class="popName">wap链接地址</label>
                    <input type="text" class="entry" placeholder="链接地址，非必填" name="modifyWapLinkUrl" id="modifyWapLinkUrl" maxlength="500"/>
                </p>
                <input type="hidden" name="modifyId" id="modifyId" />
                <div class="buttons">
                    <input type="text" value="取&nbsp;消" class="cancel" onfocus="this.blur()" />
                    <input type="text" value="确&nbsp;定" class="yes" onfocus="this.blur()" onclick="modifyCheck()" />
                </div>
            </form>
        </div>
    </div>
</div>
<script type="text/javascript" src="http://libs.baidu.com/jquery/2.1.4/jquery.min.js"></script>
<script type="text/javascript" src="<%=path %>/resources/js/simpleTips.js"></script>
<script type="text/javascript" src="<%=path %>/resources/js/loadPageBack.js"></script>

<script type="text/javascript">

    window.onload = function() {
        var code = '${code}';
        var message = '${message}';
        if (code != 1 && message != "") {
            openTips(message);
            return false;
        }
    }

    var popObj;
    $(function(){
        $(".delete").click(function(){
            /*$(".mask").fadeIn();
            $(".delete_pop").fadeIn();
            popObj = ".delete_pop"*/
        });
        $(".add").click(function(){
            $(".mask").fadeIn();
            $(".add_pop").fadeIn();
            popObj = ".add_pop"
        });
        $(".change").click(function(){
            /*$(".mask").fadeIn();
            $(".change_pop").fadeIn();
            popObj = ".change_pop"*/
        });
        $(".cancel").click(function(){
            $(".mask").fadeOut("fast");
            $(popObj).fadeOut("fast");
        });
        /*$(".yes").click(function(){
            $(".mask").fadeOut("fast");
            $(popObj).fadeOut("fast");
        });*/
    });

    //删除
    var openDeleteBoo = false;
    function openDelete(id) {
        document.getElementById("deleteId").value = id;

        $(".mask").show();
        $(".delete_pop").fadeIn();
        popObj = ".delete_pop"
    }

    function deleteCheck(){
        var deleteId = document.getElementById("deleteId").value;

        if(openDeleteBoo){
            openTips("正在删除，请稍后！");
            return;
        }else{
            openDeleteBoo = true;
        }
        $.ajax({
            url: '<%=path %>' + "/backerWeb/backerAdsHomepages/deleteHomeAd.htm",
            data: {
                deleteId : deleteId,
            },//参数
            dataType: "json",
            type: 'POST',
            async: true, //默认异步调用 (false：同步)
            success: function (resultData) {
                var code = resultData.code;
                var message = resultData.message;
                if (code != 1 && message != "") {
                    openTips(message);
                    openDeleteBoo = false;
                    return;
                }

                window.location.href = "<%=path%>" + "/backerWeb/backerAdsHomepages/show.htm";
            },

            error: function () {
                openDeleteBoo = false;
                openTips("数据加载出错，请稍候重试");
            }
        });
    }
    //新增
    var addCheckBoo = false;
    function addCheck() {
        var adsTitle = document.getElementById("addAdsTitle").value;
        var webLinkUrl = document.getElementById("addWebLinkUrl").value;
        var wapLinkUrl = document.getElementById("addWapLinkUrl").value;
        var adsImageUrlStr = document.getElementById("changead_t1").value;
        var adsImageUrl = document.getElementById("changead_a1").files[0];

        if (adsTitle == null || adsTitle == "") {
            return openTips("标题不能为空");
        }

        if (adsImageUrlStr == null || adsImageUrlStr == '') {
            return openTips("请上传封面图");
        }

        if (adsTitle.length < 2 || adsTitle.length > 16) {
            return openTips("广告标题为2~16个字符");
        }

        if (webLinkUrl.length > 500) {
            return openTips("web链接地址不能超过500个字符");
        }

        if (wapLinkUrl.length > 500) {
            return openTips("wap链接地址不能超过500个字符");
        }

        var formData = new FormData();
        formData.append("adsImageUrl", adsImageUrl);
        formData.append("adsTitle", adsTitle);
        formData.append("webLinkUrl", webLinkUrl);
        formData.append("wapLinkUrl", wapLinkUrl);

        if(addCheckBoo){
            openTips("正在保存，请稍后！");
            return;
        }else{
            addCheckBoo = true;
        }

        $.ajax({
            url: '<%=path %>' + "/backerWeb/backerAdsHomepages/addHomeAd.htm",
            data:formData,//参数
            dataType: "json",
            type: 'POST',
            async: true, //默认异步调用 (false：同步)
            processData : false,
            contentType : false,
            success: function (resultData) {
                var code = resultData.code;
                var message = resultData.message;
                if (code != 1 && message != "") {
                    addCheckBoo = false;
                    openTips(message);
                    return;
                }

                window.location.href = "<%=path%>" + "/backerWeb/backerAdsHomepages/show.htm";
            },

            error: function () {
                addCheckBoo = false;
                openTips("数据加载出错，请稍候重试");
            }
        });
    }

        //修改
        var openModifyBoo = false;
        function openModify(id, adsTitle, webLinkUrl, wapLinkUrl) {
            document.getElementById("modifyId").value = id;
            document.getElementById("modifyAdsTitle").value = adsTitle;
            document.getElementById("modifyWebLinkUrl").value = webLinkUrl;
            document.getElementById("modifyWapLinkUrl").value = wapLinkUrl;

            $(".mask").show();
            $(".change_pop").fadeIn();
            popObj = ".change_pop"
        }

        function modifyCheck() {
            var modifyAdsTitle = document.getElementById("modifyAdsTitle").value;
            var modifyWebLinkUrl = document.getElementById("modifyWebLinkUrl").value;
            var modifyWapLinkUrl = document.getElementById("modifyWapLinkUrl").value;
            var modifyId = document.getElementById("modifyId").value;
            var adsImageUrl = document.getElementById("changead_a2").files[0];

            if(modifyAdsTitle == null || modifyAdsTitle == ""){
                return openTips("标题不能为空");
            }

            if(modifyAdsTitle.length < 2 || modifyAdsTitle.length > 16){
                return openTips("广告标题为2~16个字符");
            }

            if (modifyWebLinkUrl.length > 500) {
                return openTips("web链接地址不能超过500个字符");
            }

            if (modifyWapLinkUrl.length > 500) {
                return openTips("wap链接地址不能超过500个字符");
            }

            var formData = new FormData();
            formData.append("adsImageUrl", adsImageUrl);
            formData.append("modifyAdsTitle", modifyAdsTitle);
            formData.append("modifyId", modifyId);
            formData.append("modifyWebLinkUrl", modifyWebLinkUrl);
            formData.append("modifyWapLinkUrl", modifyWapLinkUrl);

            if(openModifyBoo){
                openTips("正在修改，请稍后！");
                return;
            }else{
                openModifyBoo = true;
            }

            $.ajax({
                url: '<%=path %>' + "/backerWeb/backerAdsHomepages/modifyHomeAd.htm",
                data:formData,//参数
                dataType: "json",
                type: 'POST',
                async: true, //默认异步调用 (false：同步)
                processData : false,
                contentType : false,
                success: function (resultData) {
                    var code = resultData.code;
                    var message = resultData.message;
                    if (code != 1 && message != "") {
                        openModifyBoo = false;
                        openTips(message);
                        return;
                    }

                    window.location.href = "<%=path%>" + "/backerWeb/backerAdsHomepages/show.htm";
                },

                error: function () {
                    openModifyBoo = false;
                    openTips("数据加载出错，请稍候重试");
                }
            });

        }


    //上移
    var upMoveBoo = false;
    function upMove(id) {

        if(upMoveBoo){
            openTips("正在上移，请稍后！");
            return;
        }else{
            upMoveBoo = true;
        }

        $.ajax({
            url: '<%=path %>' + "/backerWeb/backerAdsHomepages/upMoveHomeAd.htm",
            data: {
                id : id,
            },//参数
            dataType: "json",
            type: 'POST',
            async: true, //默认异步调用 (false：同步)
            success: function (resultData) {
                var code = resultData.code;
                var message = resultData.message;
                if (code != 1 && message != "") {
                    upMoveBoo = false;
                    openTips(message);
                    return;
                }

                window.location.href = "<%=path%>" + "/backerWeb/backerAdsHomepages/show.htm";
            },

            error: function () {
                upMoveBoo = false;
                openTips("数据加载出错，请稍候重试");
            }
        });

    }

    //下移
    var downMoveBoo = false;
    function downMove(id) {

        if(downMoveBoo){
            openTips("正在上移，请稍后！");
            return;
        }else{
            downMoveBoo = true;
        }

        $.ajax({
            url: '<%=path %>' + "/backerWeb/backerAdsHomepages/downMoveHomeAd.htm",
            data: {
                id : id,
            },//参数
            dataType: "json",
            type: 'POST',
            async: true, //默认异步调用 (false：同步)
            success: function (resultData) {
                var code = resultData.code;
                var message = resultData.message;
                if (code != 1 && message != "") {
                    downMoveBoo = false;
                    openTips(message);
                    return;
                }

                window.location.href = "<%=path%>" + "/backerWeb/backerAdsHomepages/show.htm";
            },

            error: function () {
                downMoveBoo = false;
                openTips("数据加载出错，请稍候重试");
            }
        });

    }

    //置顶
    var topNoticeBoo = false;
    function topNotice(id) {
        if(topNoticeBoo){
            openTips("正在置顶，请稍后！");
            return;
        }else{
            topNoticeBoo = true;
        }

        $.ajax({
            url: '<%=path %>' + "/backerWeb/backerAdsHomepages/top.htm",
            data: {
                id : id,
            },//参数
            dataType: "json",
            type: 'POST',
            async: true, //默认异步调用 (false：同步)
            success: function (resultData) {
                var code = resultData.code;
                var message = resultData.message;
                if (code != 1 && message != "") {
                    topNoticeBoo = false;
                    openTips(message);
                    return;
                }

                window.location.href = "<%=path%>" + "/backerWeb/backerAdsHomepages/show.htm";
            },

            error: function () {
                topNoticeBoo = false;
                openTips("数据加载出错，请稍候重试");
            }
        });

    }

    //判断图片格式
    function checkFileImageTwo(target, id){
        var flag = false;
        flag = checkFileImage(target);
        document.getElementById(id).value = target.value;
        if(flag == true){
            document.getElementById(id).value = target.value;
        }
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
</script>

</body>
</html>
