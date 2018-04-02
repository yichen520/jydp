/**
 * Created by ·Summer· on 2017/8/20.
 */
function openTips(showText)
{
    var bgHeight = $(document).height()
    $(".tipsMask").remove();
    var str = "<div class='tipsMask'><div class='tips_content'><div class='empty_pop'><p class='tipsMaskText'></p>"+
        "<div class='Button'><input type='text' class='tipsYes' value='确&nbsp;&nbsp;定' onfocus='this.blur()'/>"+
        "</div></div></div></div>";
    $("body").append(str);
    $(".tipsMaskText").html("<span>" + showText + "</span>");
    $(".tipsMask").css("display","block");
    $(".tipsYes").click(function(){
        $(".tipsMask").hide();
    })
}

