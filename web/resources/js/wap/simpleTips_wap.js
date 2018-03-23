/**
 * Created by ·Summer· on 2016/12/21.
 */
function openTips(showText)
{
    var bgHeight = $(document).height();
    $(".tipsMask").remove();
    var str = "<div class='tipsMask'><div class='tips_content'><div class='empty_pop'><p class='tipsMaskText'></p>"+
        "<div class='ButtonTwo'><input type='text' class='tipsYes' value='确&nbsp;&nbsp;定' onfocus='this.blur()'/>"+
        "</div></div></div></div>";
    $("body").append(str);
    $(".tipsMaskText").html("<span>" + showText + "</span>");
    $('.tipsMask').css("height",bgHeight +"px");
    $(".tipsMask").css("display","block").delay(2500).hide(300);
    $(".tipsYes").click(function(){
        $(".tipsMask").hide();
    })
}

