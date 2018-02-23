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
        <p class="add">新增广告</p>

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
                    <input type="submit" value="确&nbsp;定" class="yes" onfocus="this.blur()" />
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
                        <input type="file" class="file" name="adsImageUrl" id="changead_a1" onchange="document.getElementById('changead_t1').value = this.value;" />
                    </span>
                </p>
                <p class="popInput">
                    <label class="popName">标题名称<span class="star">*</span></label>
                    <input type="text" class="entry" placeholder="广告标题名称，2~16个字符" name="adsTitle" id="addAdsTitle" maxlength="16"/>
                </p>
                <p class="popInput">
                    <label class="popName">web链接地址</label>
                    <input type="text" class="entry" placeholder="链接地址，非必填" name="webLinkUrl" id="addWebLinkUrl"/>
                </p>
                <p class="popInput">
                    <label class="popName">wap链接地址</label>
                    <input type="text" class="entry" placeholder="链接地址，非必填" name="wapLinkUrl" id="addWapLinkUrl"/>
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
                        <input type="file" class="file" name="adsImageUrl" id="changead_a2" onchange="document.getElementById('changead_t2').value = this.value;" />
                    </span>
                </p>
                <p class="popInput">
                    <label class="popName">标题名称<span class="star">*</span></label>
                    <input type="text" class="entry" placeholder="广告标题名称，2~16个字符" name="modifyAdsTitle" id="modifyAdsTitle" maxlength="16"/>
                </p>
                <p class="popInput">
                    <label class="popName">web链接地址</label>
                    <input type="text" class="entry" placeholder="链接地址，非必填" name="modifyWebLinkUrl" id="modifyWebLinkUrl"/>
                </p>
                <p class="popInput">
                    <label class="popName">wap链接地址</label>
                    <input type="text" class="entry" placeholder="链接地址，非必填" name="modifyWapLinkUrl" id="modifyWapLinkUrl"/>
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

<script type="text/javascript" src=http://libs.baidu.com/jquery/2.1.4/jquery.min.js"></script>
<script type="text/javascript" src="<%=path %>/resources/js/public.js"></script>
<script type="text/javascript" src="<%=path %>/resources/js/simpleTips.js"></script>

<script type="text/javascript">
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

</script>
<script type="text/javascript">
    window.onload = function() {
        var code = '${code}';
        var message = '${message}';
        if (code != 1 && message != "") {
            openTips(message);
            return false;
        }
    }

    //删除
    function openDelete(id) {
        document.getElementById("deleteId").value = id;

        $(".mask").show();
        $(".delete_pop").fadeIn();
        popObj = ".delete_pop"
    }
    //新增
    function addCheck() {
        var adsTitle = document.getElementById("addAdsTitle").value;
        var webLinkUrl = document.getElementById("addWebLinkUrl").value;
        var wapLinkUrl = document.getElementById("addWapLinkUrl").value;
        var adsImageUrl = document.getElementById("changead_t1").value;

        if(adsTitle == null || adsTitle == ""){
            return openTips("标题不能为空");
        }

        if(adsImageUrl == null || adsImageUrl == '')
        {
            return openTips("请上传封面图");
        }

        if(adsTitle.length < 2 || adsTitle.length > 16){
            return openTips("广告标题为2~16个字符");
        }

        $.ajax({
            url: '<%=path %>' + "/backerWeb/backerAdsHomepages/addHomeAd.htm",
            data:{
                adsTitle : adsTitle,
                webLinkUrl : webLinkUrl,
                wapLinkUrl : wapLinkUrl,
                adsImageUrl : adsImageUrl,

            },//参数
            dataType: "json",
            type: 'POST',
            async: true, //默认异步调用 (false：同步)
            success: function (resultData) {
                var code = resultData.code;
                var message = resultData.message;
                if (code != 1 && message != "") {
                    openTips(message);
                    return;
                }

                window.location.href = "<%=path%>" + "/backerWeb/backerAdsHomepages/show.htm";
            },

            error: function () {
                openTips("数据加载出错，请稍候重试");
            }
        });

        //修改
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
            var adsTitle = document.getElementById("modifyAdsTitle").value;

            if(adsTitle == null || adsTitle == ""){
                return openTips("标题不能为空");
            }

            if(adsTitle.length < 2 || adsTitle.length > 16){
                return openTips("广告标题为2~16个字符");
            }

            $("#modifyForm").submit();
        }
    }

    //上移
    function upMove(id) {
        url = "<%=path%>/backerWeb/backerAdsHomepages/upMoveHomeAd.htm?id=" + id;
        window.location.href = url;
    }

    //下移
    function downMove(id) {
        url = "<%=path%>/backerWeb/backerAdsHomepages/downMoveHomeAd.htm?id=" + id;
        window.location.href = url;
    }
</script>

</body>
</html>
