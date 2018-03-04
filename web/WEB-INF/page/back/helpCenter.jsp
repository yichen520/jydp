<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/resources/page/common/path.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <link rel="icon" href="<%=path %>/resources/image/back/icon.ico" type="image/x-ico" />

    <link rel="stylesheet" type="text/css" href="<%=path %>/resources/css/back/helpCenter.css" />
    <link rel="stylesheet" type="text/css" href="<%=path %>/resources/css/back/public.css" />
    <link rel="stylesheet" type="text/css" href="<%=path %>/resources/css/back/simpleTips.css" />

    <title>用户帮助中心</title>
</head>
<body>
<div id="header"></div>


<div class="content">
    <div id="menu"></div>

    <div class="contentRight">
        <div class="caption">
            <span class="title">用户帮助中心</span>
        </div>

        <ul class="helpMenu">
            <c:forEach items="${helpTitleList }" var="item">
                <c:if test="${item.key == helpId}">
                    <li class="helpInfo chooseState" value="${item.key }">${item.value }</li>
                </c:if>
                <c:if test="${item.key != helpId}">
                    <li class="helpInfo" value="${item.key }">${item.value }</li>
                </c:if>
            </c:forEach>
        </ul>

        <div class="editor">
            <div class="editorInfo">
                <script id="editor" type="text/plain">${systemHelp.content }</script>
            </div>

            <c:if test="${backer_rolePower['115002'] == 115002}">
                <input type="text" value="提&nbsp;交" class="submit" onfocus="this.blur()" onclick="submitHelp()" />
            </c:if>
        </div>
    </div>
</div>


<div id="footer"></div>

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

    $(function(){
        $(".helpInfo").click(function(){
            $(".helpMenu li").removeClass("chooseState");
            $(this).addClass(".chooseState")
            var helpId = this.value;
            window.location.href = '<%=path%>' + "/backerWeb/helpCenter/show.htm?helpId=" + helpId;
        })
    });

    //提交
    var helpBoo = false;
    function submitHelp() {
        if (helpBoo) {
            return false;
        } else {
            helpBoo = true;
        }

        var content = UE.getEditor('editor').getContent();
        var helpId = '${helpId}';

        if (!content) {
            helpBoo = false;
            return openTips("内容不能为空");
        }

        if (!helpId) {
            helpBoo = false;
            return openTips("记录ID错误")
        }

        $.ajax({
            url: '<%=path %>' + "/backerWeb/helpCenter/submitHelp.htm",
            type:'post',
            dataType:'json',
            async:true,
            data:{
                helpId : helpId,
                submit_content : content
            },
            success:function (result) {
                if (result.code == 1) {
                    window.location.href = '<%=path%>' + "/backerWeb/helpCenter/show.htm?helpId="+helpId;
                } else {
                    openTips(result.message);
                }
                helpBoo = false;
            },
            error:function () {
                helpBoo = false;
                openTips("数据加载出错，请稍候重试")
            }
        });
    }

    $(function(){
        $(".helpInfo").click(function(){
            $(".helpMenu li").removeClass("chooseState");
            $(this).addClass(".chooseState")
        })
    });
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