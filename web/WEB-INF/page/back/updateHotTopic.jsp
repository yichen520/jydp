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

    <link rel="stylesheet" type="text/css" href="<%=path %>/resources/css/back/hotAdd.css" />
    <link rel="stylesheet" type="text/css" href="<%=path %>/resources/css/back/public.css" />
    <link rel="stylesheet" type="text/css" href="<%=path %>/resources/css/back/simpleTips.css" />

    <title>修改话题</title>
</head>
<body>
<div id="header"></div>


<div class="content">
    <div id="menu"></div>

    <div class="contentRight">
        <div class="caption">
            <span class="title">修改话题</span>
        </div>

        <div class="bottom">
            <form id="modifyForm" action="<%=path%>/backerWeb/hotTopic/updateHotTopic.htm" enctype="multipart/form-data" method="post">
            <div class="addInfo">
                <p class="condition">
                    话题类型<span class="star">*</span>：<input type="text" class="askInput" placeholder="话题类型，2~16个字符" value="${systemHotDO.noticeType }" maxlength="16" id="noticeType" name="noticeType"/>
                </p>
                <p class="condition">
                    话题标题<span class="star">*</span>：<input type="text" class="askInput" placeholder="话题标题，2~32个字符" value="${systemHotDO.noticeTitle }" maxlength="32" id="noticeTitle" name="noticeTitle"/>
                </p>
                <p class="condition">
                    封面图：
                    <span class="pic">
                        <input type="text" id="changead_t1"  class="choosePic" placeholder="请选择文件" onfocus="this.blur()" />
                        <input type="text"  onclick="document.getElementById('changead_a1').click();"  value="选择文件" class="choose_button" onfocus="this.blur();" />
                        <input type="file" class="file" name="noticeUrl" id="changead_a1" onchange="document.getElementById('changead_t1').value = this.value;" />
                    </span>
                </p>

                <div class="editor">
                    <script id="editor" type="text/plain">${systemHotDO.content }</script>
                </div>

                <input type="hidden" id="id" name="id" value="${systemHotDO.id }"/>
                <input type="hidden" id="content" name="content"/>
                <div class="operate">
                    <a href="javascript:;" class="back"onclick="backList()">返&nbsp;回</a>
                    <input type="text" value="提&nbsp;交" class="submit" onfocus="this.blur()" onclick="modifyHandle()"/>
                </div>
            </div>
            </form>
        </div>
    </div>
</div>

<form id="openListForm" action="<%=path %>/backerWeb/hotTopic/show.htm" method="post">
    <input type="hidden" id="open_pageNumber" name="pageNumber" value="${pageNumber }">
    <input type="hidden" id="open_noticeType" name="noticeType" value="${noticeType }">
    <input type="hidden" id="open_noticeTitle" name="noticeTitle" value="${noticeTitle }">
</form>
<script type="text/javascript" src="http://libs.baidu.com/jquery/2.1.4/jquery.min.js"></script>
<script type="text/javascript" src="<%=path %>/resources/js/loadPageBack.js"></script>
<script type="text/javascript" src="<%=path %>/resources/js/simpleTips.js"></script>

<div id="footer"></div>

<script type="text/javascript">
    //确定
    function modifyHandle() {
        var noticeType = document.getElementById("noticeType").value;
        var noticeTitle = document.getElementById("noticeTitle").value;
        var content =  UE.getEditor('editor').getContent();
        var noticeUrlStr = document.getElementById("changead_t1").value;
        var noticeUrl = document.getElementById("changead_a1").files[0];
        var id = document.getElementById("id").value;
        if(noticeType == null || noticeType == ""){
            return openTips("类型不能为空");
        }

        if(noticeTitle == null || noticeTitle == ""){
            return openTips("标题不能为空");
        }

        if(content == null || content == ""){
            return openTips("内容不能为空");
        }

        if(noticeType.length < 2 || noticeType.length > 16){
            return openTips("请输入公告类型，2~16个字符");
        }

        if(noticeTitle.length < 2 || noticeTitle.length > 32){
            return openTips("请输入公告标题，2~32个字符");
        }

        document.getElementById("content").value = content;

        var formData = new FormData();
        formData.append("noticeType", noticeType);
        formData.append("noticeTitle", noticeTitle);
        formData.append("content", content);
        formData.append("noticeUrl", noticeUrl);
        formData.append("id", id);

        $.ajax({
            url: '<%=path %>' + "/backerWeb/hotTopic/updateHotTopic.htm",
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
                    openTips(message);
                    return;
                }

                $("#openListForm").submit();
            },

            error: function () {
                openTips("数据加载出错，请稍候重试");
            }
        });

    }

    function backList(){

        $("#openListForm").submit();

    }
</script>

<!--建议手动加在语言，避免在ie下有时因为加载语言失败导致编辑器加载失败-->
<!--这里加载的语言文件会覆盖你在配置项目里添加的语言类型，比如你在配置项目里配置的是英文，这里加载的中文，那最后就是中文-->
<script type="text/javascript" charset="utf-8" src="<%=path%>/resources/js/ueditor/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="<%=path%>/resources/js/ueditor/jquery-1.8.3.js"></script>
<script type="text/javascript" charset="utf-8" src="<%=path%>/resources/js/ueditor/ueditor.all.min.js"> </script>
<script type="text/javascript" charset="utf-8" src="<%=path %>/resources/js/ueditor/lang/zh-cn/zh-cn.js"></script>
<script type="text/javascript">
    //实例化编辑器
    //建议使用工厂方法getEditor创建和引用编辑器实例，如果在某个闭包下引用该编辑器，直接调用UE.getEditor('editor')就能拿到相关的实例
    var ue = UE.getEditor('editor');

    function isFocus(e){
        alert(UE.getEditor('editor').isFocus());
        UE.dom.domUtils.preventDefault(e)
    }
    function setblur(e){
        UE.getEditor('editor').blur();
        UE.dom.domUtils.preventDefault(e)
    }
    function insertHtml() {
        var value = prompt('插入html代码', '');
        UE.getEditor('editor').execCommand('insertHtml', value)
    }
    function createEditor() {
        enableBtn();
        UE.getEditor('editor');
    }
    function getAllHtml() {
        alert(UE.getEditor('editor').getAllHtml())
    }
    function getPlainTxt() {
        var arr = [];
        arr.push("使用editor.getPlainTxt()方法可以获得编辑器的带格式的纯文本内容");
        arr.push("内容为：");
        arr.push(UE.getEditor('editor').getPlainTxt());
        alert(arr.join('\n'))
    }
    function setContent(isAppendTo) {
        var arr = [];
        arr.push("使用editor.setContent('欢迎使用ueditor')方法可以设置编辑器的内容");
        UE.getEditor('editor').setContent('欢迎使用ueditor', isAppendTo);
        alert(arr.join("\n"));
    }
    function setDisabled() {
        UE.getEditor('editor').setDisabled('fullscreen');
        disableBtn("enable");
    }
    function setEnabled() {
        UE.getEditor('editor').setEnabled();
        enableBtn();
    }
    function getText() {
        //当你点击按钮时编辑区域已经失去了焦点，如果直接用getText将不会得到内容，所以要在选回来，然后取得内容
        var range = UE.getEditor('editor').selection.getRange();
        range.select();
        var txt = UE.getEditor('editor').selection.getText();
        alert(txt)
    }
    function getContentTxt() {
        var arr = [];
        arr.push("使用editor.getContentTxt()方法可以获得编辑器的纯文本内容");
        arr.push("编辑器的纯文本内容为：");
        arr.push(UE.getEditor('editor').getContentTxt());
        alert(arr.join("\n"));
    }
    function hasContent() {
        var arr = [];
        arr.push("使用editor.hasContents()方法判断编辑器里是否有内容");
        arr.push("判断结果为：");
        arr.push(UE.getEditor('editor').hasContents());
        alert(arr.join("\n"));
    }
    function setFocus() {
        UE.getEditor('editor').focus();
    }
    function deleteEditor() {
        disableBtn();
        UE.getEditor('editor').destroy();
    }
    function disableBtn(str) {
        var div = document.getElementById('btns');
        var btns = UE.dom.domUtils.getElementsByTagName(div, "button");
        for (var i = 0, btn; btn = btns[i++];) {
            if (btn.id == str) {
                UE.dom.domUtils.removeAttributes(btn, ["disabled"]);
            } else {
                btn.setAttribute("disabled", "true");
            }
        }
    }
    function enableBtn() {
        var div = document.getElementById('btns');
        var btns = UE.dom.domUtils.getElementsByTagName(div, "button");
        for (var i = 0, btn; btn = btns[i++];) {
            UE.dom.domUtils.removeAttributes(btn, ["disabled"]);
        }
    }
    function getLocalData () {
        alert(UE.getEditor('editor').execCommand( "getlocaldata" ));
    }
    function clearLocalData () {
        UE.getEditor('editor').execCommand( "clearlocaldata" );
        alert("已清空草稿箱")
    }
</script>
</body>
</html>
