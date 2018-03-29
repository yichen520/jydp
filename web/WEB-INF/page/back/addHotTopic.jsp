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

    <title>新增话题</title>
</head>
<body>
<div id="header"></div>


<div class="content">
    <div id="menu"></div>

    <div class="contentRight">
        <div class="caption">
            <span class="title">新增话题</span>
        </div>

        <div class="bottom">
            <form id="addForm" action="<%=path%>/backerWeb/hotTopic/addHotTopic.htm" enctype="multipart/form-data" method="post">
                <div class="addInfo">
                    <p class="condition">
                        话题类型<span class="star">*</span>：<input type="text" class="askInput" placeholder="话题类型，2~16个字符" maxlength="16" id="noticeType" name="noticeType"/>
                    </p>
                    <p class="condition">
                        话题标题<span class="star">*</span>：<input type="text" class="askInput" placeholder="话题标题，2~32个字符" maxlength="32" id="noticeTitle" name="noticeTitle"/>
                    </p>
                    <p class="condition">
                        封面图：
                        <span class="pic">
                            <input type="text" id="changead_t1"  class="choosePic" placeholder="请选择文件" onfocus="this.blur()" />
                            <input type="text"  onclick="document.getElementById('changead_a1').click();"  value="选择文件" class="choose_button" onfocus="this.blur();" />
                            <input type="file" class="file" name="noticeUrl" id="changead_a1" onchange="checkFileImageTwo(this, 'changead_t1');" />
                        </span>
                    </p>

                    <div class="editor">
                        <script id="editor" type="text/plain"></script>
                    </div>

                    <input type="hidden" id="content" name="content"/>

                    <div class="operate">
                        <a href="<%=path%>/backerWeb/hotTopic/show.htm" class="back">返&nbsp;回</a>
                        <input type="text" value="提&nbsp;交" class="submit" onfocus="this.blur()" onclick="addHandle()"/>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>

<script type="text/javascript" src="http://libs.baidu.com/jquery/2.1.4/jquery.min.js"></script>
<script type="text/javascript" src="<%=path %>/resources/js/loadPageBack.js"></script>
<script type="text/javascript" src="<%=path %>/resources/js/simpleTips.js"></script>
<div id="footer"></div>

<script type="text/javascript">
    var exportDataBoo = false;
    //确定
    function addHandle() {
        var noticeType = document.getElementById("noticeType").value;
        var noticeTitle = document.getElementById("noticeTitle").value;
        var content =  UE.getEditor('editor').getContent();
        var noticeUrlStr = document.getElementById("changead_t1").value;
        var noticeUrl = document.getElementById("changead_a1").files[0];
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
            return openTips("请输入话题类型，2~16个字符");
        }

        if(noticeTitle.length < 2 || noticeTitle.length > 32){
            return openTips("请输入话题标题，2~32个字符");
        }

        document.getElementById("content").value = content;

        var formData = new FormData();
        formData.append("noticeType", noticeType);
        formData.append("noticeTitle", noticeTitle);
        formData.append("content", content);
        formData.append("noticeUrl", noticeUrl);

        if(exportDataBoo){
            openTips("正在保存，请稍后！");
            return;
        }else{
            exportDataBoo = true;
        }
        $.ajax({
            url: '<%=path %>' + "/backerWeb/hotTopic/addHotTopic.htm",
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
                    exportDataBoo = false;
                    return;
                }

                window.location.href = "<%=path%>" + "/backerWeb/hotTopic/show.htm";
            },

            error: function () {
                exportDataBoo = false;
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
