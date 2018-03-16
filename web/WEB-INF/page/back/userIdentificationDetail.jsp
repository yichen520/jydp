<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/resources/page/common/path.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <link rel="icon" href="<%=path %>/resources/image/back/icon.ico" type="image/x-ico" />

    <link rel="stylesheet" type="text/css" href="<%=path %>/resources/css/back/authentication_details.css" />
    <link rel="stylesheet" type="text/css" href="<%=path %>/resources/css/back/public.css" />
    <link rel="stylesheet" type="text/css" href="<%=path %>/resources/css/back/simpleTips.css" />

    <title>实名认证详情</title>
</head>
<body>
<header id="header"></header>


<div class="content">
    <div id="menu"></div>


    <div class="contentRight">
        <div class="caption">
            <span class="title">实名认证详情</span>
        </div>

        <div class="bottom">
            <div class="authenticationInfo">
                <p class="role">
                    <label class="name">用户账号：</label>
                    <span class="info">${userIdentification.userAccount}</span>
                </p>
                <p class="role">
                    <label class="name">姓名：</label>
                    <span class="info">${userIdentification.userName}</span>
                </p>
                <p class="role">
                    <label class="name">证件类型：</label>
                    <c:if test="${userIdentification.userCertType == 1}">
                        <span class="info">身份证</span>
                    </c:if>
                    <c:if test="${userIdentification.userCertType == 2}">
                        <span class="info">护照</span>
                    </c:if>
                </p>
                <p class="role">
                    <label class="name">证件号：</label>
                    <span class="info">${userIdentification.userCertNo}</span>
                </p>
                <p class="role">
                    <label class="name">证件照：</label>
                    <span class="info">
                        <c:forEach items="${userIdentificationImageList}" var="item">
                            <img src="${item.imageUrlFormat}" class="img" />
                        </c:forEach>
                    </span>
                </p>
                <p class="role">
                    <label class="name">审核状态：</label>
                    <c:if test="${userIdentification.identificationStatus == 1}">
                        <span class="info">待审核</span>
                    </c:if>
                    <c:if test="${userIdentification.identificationStatus == 2}">
                        <span class="info">审核通过</span>
                    </c:if>
                    <c:if test="${userIdentification.identificationStatus == 3}">
                        <span class="info">审核拒绝</span>
                    </c:if>
                </p>
                <p class="role">
                    <label class="name">审核备注：</label>
                    <span class="info">${userIdentification.remark}</span>
                </p>
            </div>

            <div class="operate">
                <a href="<%=path %>/backerWeb/backerIdentification/show.htm" class="back">返&nbsp;回</a>
                <c:if test="${userIdentification.identificationStatus == 1}">
                    <c:if test="${backer_rolePower['141003'] == 141003}">
                        <input type="text" value="审核通过" class="pass" onfocus="this.blur()" />
                    </c:if>
                    <c:if test="${backer_rolePower['141004'] == 141004}">
                        <input type="text" value="审核拒绝" class="refuse" onfocus="this.blur()" />
                    </c:if>
                </c:if>
            </div>

            <div class="bigPic_pop">
                <p class="popTitle">
                    详情图
                    <span class="close">×</span>
                </p>
                <div class="over"><img src="" id="popImg" /></div>

                <div class="rotate">
                    <input type="text" value="旋&nbsp;转" id="right" onfocus="this.blur()" />
                </div>
            </div>
        </div>
    </div>
</div>

<form id="backForm" action="<%=path %>/backerWeb/backerIdentification/show.htm" method="post">
    <input type="hidden" value="${pageNumber}" name="pageNumber">
    <input type="hidden" value="${startTime}" name="startTime">
    <input type="hidden" value="${endTime}" name="endTime">
    <input type="hidden" value="${userAccount}" name="userAccount">
    <input type="hidden" value="${userPhone}" name="userPhone">
    <input type="hidden" value="${phoneAreaCode}" name="phoneAreaCode">
    <input type="hidden" value="${identificationStatus}" name="identificationStatus">
    <input type="hidden" value="${userCertType}" name="userCertType">
</form>


<div id="footer"></div>


<div class="mask">
    <div class="mask_content">
        <div class="pass_pop">
            <p class="popTitle">审核通过</p>
            <p class="popTips"><img src="<%=path%>/resources/image/back/tips.png" class="tipsImg" />确定认证通过？</p>
            <p class="popInput">
                <label class="popName" style="line-height: 20px">备注：</label>
                <textarea class="txt" id="passRemark" placeholder="备注，非必填，最多100个字符" maxlength="100"></textarea>
            </p>

            <div class="buttons">
                <input type="text" value="取&nbsp;消" class="cancel" onfocus="this.blur()" />
                <input type="text" value="确&nbsp;定" class="yes" onfocus="this.blur()" onclick="passSubmit()" />
            </div>
        </div>

        <div class="refuse_pop">
            <p class="popTitle">审核拒绝</p>
            <p class="popTips"><img src="<%=path%>/resources/image/back/tips.png" class="tipsImg" />确定认证拒绝？</p>
            <p class="popInput">
                <label class="popName" style="line-height: 20px">备注：</label>
                <textarea class="txt" id="refuseRemark" placeholder="备注，非必填，最多100个字符" maxlength="100"></textarea>
            </p>

            <div class="buttons">
                <input type="text" value="取&nbsp;消" class="cancel" onfocus="this.blur()" />
                <input type="text" value="确&nbsp;定" class="yes" onfocus="this.blur()" onclick="refuseSubmit()" />
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

    function goBack() {
        $("#backForm").submit();
    }

    var identiId = '${userIdentification.id}';

    var passBoo = false;
    function passSubmit() {
        if(passBoo){
            return false;
        }else{
            passBoo = true;
        }

        var passRemark = $("#passRemark").val();
        if (passRemark.length > 100) {
            return openTips("审核备注过长！")
        }
        $.ajax({
            url: '<%=path %>' + "/backerWeb/backerIdentification/pass.htm",
            type:'post',
            dataType:'json',
            async:true,
            data:{
                id : identiId,
                remark : passRemark
            },
            success:function(result){
                passBoo = false;
                if(result.code == 1) {
                    $(".mask").fadeOut("fast");
                    $(popObj).fadeOut("fast");
                    openTips(result.message)
                    setTimeout(function (){$("#backForm").submit();}, 1000);
                } else {
                    openTips(result.message);
                }
            }, error:function(){
                passBoo = false;
                openTips("服务器异常，请稍后再试！");
            }
        });
    }

    var refuseBoo = false;
    function refuseSubmit() {
        if(refuseBoo){
            return false;
        }else{
            refuseBoo = true;
        }

        var refuseRemark = $("#refuseRemark").val();
        if (refuseRemark.length > 100) {
            return openTips("审核备注过长！")
        }
        $.ajax({
            url: '<%=path %>' + "/backerWeb/backerIdentification/refuse.htm",
            type:'post',
            dataType:'json',
            async:true,
            data:{
                id : identiId,
                remark : refuseRemark
            },
            success:function(result){
                refuseBoo = false;
                if(result.code == 1) {
                    $(".mask").fadeOut("fast");
                    $(popObj).fadeOut("fast");
                    openTips(result.message)
                    setTimeout(function (){$("#backForm").submit();}, 1000);
                } else {
                    openTips(result.message);
                }
            }, error:function(){
                refuseBoo = false;
                openTips("服务器异常，请稍后再试！");
            }
        });

    }

    var popObj;
    $(function(){
        $(".img").click(function(){
            $(".bigPic_pop").fadeIn();
            $(".bigPic_pop img").attr("src",this.src);
            popObj = ".bigPic_pop"
        });
        $(".pass").click(function(){
            $(".mask").fadeIn();
            $(".pass_pop").fadeIn();
            popObj = ".pass_pop"
        });
        $(".refuse").click(function(){
            $(".mask").fadeIn();
            $(".refuse_pop").fadeIn();
            popObj = ".refuse_pop"
        });
        $(".cancel").click(function(){
            $(".mask").fadeOut("fast");
            $(popObj).fadeOut("fast");
        });
        $(".close").click(function(){
            $(".bigPic_pop").fadeOut("fast");
        });
        /*$(".yes").click(function(){
            $(".mask").fadeOut("fast");
            $(popObj).fadeOut("fast");
        });*/
    });

    $(function(){
        var deg = 0;
        $("#right").click(function(){
            deg += 90;
            $("#popImg").css("transform","rotate(" + deg + "deg)");
        })
    });
</script>
</body>
</html>