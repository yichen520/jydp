<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/resources/page/common/path.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <link rel="icon" href="<%=path %>/resources/image/back/icon.ico" type="image/x-ico" />

    <link rel="stylesheet" type="text/css" href="<%=path %>/resources/css/back/cooperation.css" />
    <link rel="stylesheet" type="text/css" href="<%=path %>/resources/css/back/public.css" />
    <link rel="stylesheet" type="text/css" href="<%=path %>/resources/css/back/simpleTips.css" />

    <title>合作企业</title>
</head>
<body>
<div id="header"></div>


<div class="content">
    <div id="menu"></div>

    <div class="contentRight">
        <div class="caption">
            <span class="title">合作企业</span>
        </div>
        <c:if test="${backer_rolePower['112002'] == 112002}">
            <p class="add">新增合作企业</p>
        </c:if>

        <form id="queryForm" action="<%=path %>/backerWeb/backerBusinessesPartner/show.htm" method="post">
            <input type="hidden" id="queryPageNumber" name="pageNumber" value="${pageNumber}">
        </form>
        <div class="bottom">
            <table class="table" cellspacing="0" cellpadding="0">
                <tr class="tableTitle">
                    <td class="time">添加时间</td>
                    <td class="name">企业名称</td>
                    <td class="pic">企业图片</td>
                    <td class="link">web链接</td>
                    <td class="link">wap链接</td>
                    <td class="operate">操作</td>
                </tr>
                <c:forEach items="${systemBusinessesPartnerList}" var="item" varStatus="status">
                    <tr class="tableInfo">
                        <td class="time"><fmt:formatDate value="${item.addTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                        <td class="name">${item.businessesName}</td>
                        <td class="pic"><img src="${item.businessesImageUrlFormat}" /></td>
                        <td class="link"><a target="_blank" href="${item.webLinkUrl}">${item.webLinkUrl}</a></td>
                        <td class="link"><a target="_blank" href="${item.wapLinkUrl}">${item.wapLinkUrl}</a></td>
                        <td class="operate">
                            <c:if test="${status.count != 1 || pageNumber != 0}">
                                <c:if test="${backer_rolePower['112003'] == 112003}">
                                    <input type="text" value="置&nbsp; 顶" class="toTop" onfocus="this.blur()" onclick="topPartner('${item.id}')"/>
                                </c:if>
                            </c:if>
                            <c:if test="${backer_rolePower['112004'] == 112004}">
                                <input type="text" value="修&nbsp;改" class="change" onfocus="this.blur()" onclick="openModify('${item.id}', '${item.businessesName}', '${item.webLinkUrl}', '${item.wapLinkUrl}')"/>
                            </c:if>
                            <c:if test="${backer_rolePower['112005'] == 112005}">
                                <input type="text" value="删&nbsp;除" class="delete" onfocus="this.blur()" onclick="openDelete('${item.id}')"/>
                            </c:if>
                        </td>
                    </tr>
                </c:forEach>
            </table>

            <jsp:include page="/resources/page/common/paging.jsp"></jsp:include>
        </div>
    </div>
</div>

<div id="footer"></div>

<div class="mask">
    <div class="mask_content">
        <div class="delete_pop">
            <p class="popTitle">删除操作</p>
            <p class="popTips"><img src="<%=path%>/resources/image/back/tips.png" class="tipsImg" />确定删除？</p>

            <div class="buttons">
                <input type="text" value="取&nbsp;消" class="cancel" onfocus="this.blur()" />
                <input type="hidden" id="deleteId" />
                <input type="text" value="确&nbsp;定" class="yes" onfocus="this.blur()" onclick="deleteCheck()" />
            </div>
        </div>

        <div class="add_pop">
            <p class="popTitle">新增合作企业</p>
            <p class="popInput">
                <label class="popName">企业图片<span class="star">*</span></label>

                <span class="popPic">
                    <input type="text" id="changead_t1"  class="choosePic" placeholder="请选择文件" onfocus="this.blur()" />
                    <input type="text"  onclick="document.getElementById('changead_a1').click();"  value="选择文件" class="choose_button" onfocus="this.blur();" />
                    <input type="file" class="file" name="adsImageUrl" id="changead_a1" onchange="document.getElementById('changead_t1').value = this.value;" />
                </span>
            </p>
            <p class="popInput">
                <label class="popName">企业名称<span class="star">*</span></label>
                <input type="text" class="entry" placeholder="企业名称，2~16个字符" id="addBusinessesName" maxlength="16">
            </p>
            <p class="popInput">
                <label class="popName">web链接地址</label>
                <input type="text" class="entry" placeholder="链接地址，非必填" id="addWebLinkUrl" maxlength="500"/>
            </p>
            <p class="popInput">
                <label class="popName">wap链接地址</label>
                <input type="text" class="entry" placeholder="链接地址，非必填" id="addWapLinkUrl" maxlength="500"/>
            </p>

            <div class="buttons">
                <input type="text" value="取&nbsp;消" class="cancel" onfocus="this.blur()" />
                <input type="text" value="确&nbsp;定" class="yes" onfocus="this.blur()" onclick="addCheck()" />
            </div>
        </div>

        <div class="change_pop">
            <p class="popTitle">修改合作企业</p>
            <p class="popInput">
                <label class="popName">企业图片</label>

                <span class="popPic">
                    <input type="text" id="changead_t2"  class="choosePic" placeholder="该项不修改时，请勿上传" onfocus="this.blur()" />
                    <input type="text"  onclick="document.getElementById('changead_a2').click();"  value="选择文件" class="choose_button" onfocus="this.blur();" />
                    <input type="file" class="file" id="changead_a2" onchange="document.getElementById('changead_t2').value = this.value;" />
                </span>
            </p>
            <p class="popInput">
                <label class="popName">企业名称<span class="star">*</span></label>
                <input type="text" class="entry" placeholder="企业名称，2~16个字符" id="modifyBusinessesName" maxlength="16"/>
            </p>
            <p class="popInput">
                <label class="popName">web链接地址</label>
                <input type="text" class="entry" placeholder="链接地址，非必填" id="modifyWebLinkUrl" maxlength="500"/>
            </p>
            <p class="popInput">
                <label class="popName">wap链接地址</label>
                <input type="text" class="entry" placeholder="链接地址，非必填" id="modifyWapLinkUrl" maxlength="500"/>
            </p>

            <input type="hidden" name="modifyId" id="modifyId" />
            <div class="buttons">
                <input type="text" value="取&nbsp;消" class="cancel" onfocus="this.blur()" />
                <input type="text" value="确&nbsp;定" class="yes" onfocus="this.blur()" onclick="modifyCheck()" />
            </div>
        </div>
    </div>
</div>

<script type="text/javascript" src="http://libs.baidu.com/jquery/2.1.4/jquery.min.js"></script>
<script type="text/javascript" src="<%=path %>/resources/js/loadPageBack.js"></script>
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

    function openDelete(id) {
        $("#deleteId").val(id);
    }

    //删除
    var deleteBoo = false;
    function deleteCheck() {
        if (deleteBoo) {
            return false;
        } else {
            deleteBoo = true;
        }

        var deleteId = $("#deleteId").val();

        $.ajax({
            url: '<%=path %>' + "/backerWeb/backerBusinessesPartner/delete.htm",
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
                    deleteBoo = false;
                    openTips(message);
                    return;
                }
                $("#queryForm").submit();
            },

            error: function () {
                deleteBoo = false;
                openTips("数据加载出错，请稍候重试");
            }
        });
    }

    //新增
    var addBoo = false;
    function addCheck() {
        if (addBoo) {
            return false;
        } else {
            addBoo = true;
        }
        var businessesPartnerImageUrlStr = $("#changead_t1").val();
        var businessesPartnerImageUrl = document.getElementById("changead_a1").files[0];
        var businessesName = $("#addBusinessesName").val();
        var webLinkUrl = $("#addWebLinkUrl").val();
        var wapLinkUrl = $("#addWapLinkUrl").val();

        if (businessesPartnerImageUrlStr == null || businessesPartnerImageUrlStr == '') {
            addBoo = false;
            return openTips("请上传封面图");
        }
        if (businessesName == null || businessesName == "") {
            addBoo = false;
            return openTips("企业名称不能为空");
        }
        if (businessesName.length < 2 || businessesName.length > 16) {
            addBoo = false;
            return openTips("企业名称为2~16个字符");
        }
        if (webLinkUrl.length > 500) {
            addBoo = false;
            return openTips("web链接地址不能超过500个字符");
        }
        if (wapLinkUrl.length > 500) {
            addBoo = false;
            return openTips("wap链接地址不能超过500个字符");
        }

        var formData = new FormData();
        formData.append("businessesPartnerImageUrl", businessesPartnerImageUrl);
        formData.append("addBusinessesName", businessesName);
        formData.append("addWebLinkUrl", webLinkUrl);
        formData.append("addWapLinkUrl", wapLinkUrl);

        $.ajax({
            url: '<%=path %>' + "/backerWeb/backerBusinessesPartner/add.htm",
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
                    addBoo = false;
                    openTips(message);
                    return;
                }
                $("#queryForm").submit();
            },

            error: function () {
                addBoo = false;
                openTips("数据加载出错，请稍候重试");
            }
        });
    }

    function openModify(id, businessesName, webLinkUrl, wapLinkUrl) {
        $("#modifyId").val(id);
        $("#modifyBusinessesName").val(businessesName);
        $("#modifyWebLinkUrl").val(webLinkUrl);
        $("#modifyWapLinkUrl").val(wapLinkUrl);
    }

    //修改
    var modifyBoo = false;
    function modifyCheck() {
        if (modifyBoo) {
            return false;
        } else {
            modifyBoo = true;
        }

        var businessesName = $("#modifyBusinessesName").val();
        var webLinkUrl = $("#modifyWebLinkUrl").val();
        var wapLinkUrl = $("#modifyWapLinkUrl").val();

        if(businessesName == null || businessesName == ""){
            modifyBoo = false;
            return openTips("企业名称不能为空");
        }
        if(businessesName.length < 2 || businessesName.length > 16){
            modifyBoo = false;
            return openTips("企业名称为2~16个字符");

        }
        if (webLinkUrl.length > 500) {
            modifyBoo = false;
            return openTips("web链接地址不能超过500个字符");
        }
        if (wapLinkUrl.length > 500) {
            modifyBoo = false;
            return openTips("wap链接地址不能超过500个字符");
        }

        var updateId = $("#modifyId").val();
        var businessesPartnerImageUrl = document.getElementById("changead_a2").files[0];

        var formData = new FormData();
        formData.append("businessesPartnerImageUrl", businessesPartnerImageUrl);
        formData.append("updateId", updateId);
        formData.append("updateBusinessesName", businessesName);
        formData.append("updateWebLinkUrl", webLinkUrl);
        formData.append("updateWapLinkUrl", wapLinkUrl);

        $.ajax({
            url: '<%=path %>' + "/backerWeb/backerBusinessesPartner/update.htm",
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
                    modifyBoo = false;
                    openTips(message);
                    return;
                }

                $("#queryForm").submit();
            },

            error: function () {
                modifyBoo = false;
                openTips("数据加载出错，请稍候重试");
            }
        });
    }

    //置顶
    var topBoo = false;
    function topPartner(id) {
        if (topBoo) {
            return false;
        } else {
            topBoo = true;
        }

        $.ajax({
            url: '<%=path %>' + "/backerWeb/backerBusinessesPartner/top.htm",
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
                    topBoo = false;
                    openTips(message);
                    return;
                }

                $("#queryForm").submit();
            },

            error: function () {
                topBoo = false;
                openTips("数据加载出错，请稍候重试");
            }
        });

    }
</script>

<script type="text/javascript">
    var popObj;
    $(function(){
        $(".delete").click(function(){
            $(".mask").fadeIn();
            $(".delete_pop").fadeIn();
            popObj = ".delete_pop"
        });
        $(".add").click(function(){
            $(".mask").fadeIn();
            $(".add_pop").fadeIn();
            popObj = ".add_pop"
        });
        $(".change").click(function(){
            $(".mask").fadeIn();
            $(".change_pop").fadeIn();
            popObj = ".change_pop"
        });
        $(".cancel").click(function(){
            $(".mask").fadeOut("fast");
            $(popObj).fadeOut("fast");
        });
//        $(".yes").click(function(){
//            $(".mask").fadeOut("fast");
//            $(popObj).fadeOut("fast");
//        });
    });
</script>

</body>
</html>