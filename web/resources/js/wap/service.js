var ParamAndViewInit = {
    show: function () {
        var bgHeight = $(document).height();
        $('.submit').on('click',function() {
            $('.bg').css("height",bgHeight +"px");
            $('.showBox').css("display","block");
            $('.showBox').animate({opacity:'1'},"1000");
        });
        $('.okay').on('click',function() {
            $('.bg').css("height","0");
            $('.showBox').animate({opacity:'0'},"100");
            setTimeout(function(){
                $('.showBox').css('display','none');
            },100)
        });
        $('.cancel').on('click',function() {
            $('.bg').css("height","0");
            $('.showBox').animate({opacity:'0'},"100");
            setTimeout(function(){
                $('.showBox').css('display','none');
            },100)
        });
    },
    backToMine: function () {
        var webAppPath = $("#webAppPath").val();
        window.location.href = webAppPath + "/userWap/userMine/show.htm";
    },
    formatDate: function(timestamp){
        var date = new Date(timestamp);//10位需要乘以1000
        var Y  = date.getFullYear() + "-";
        if(date.getMonth() + 1 < 10){
            M = '0' + (date.getMonth() + 1);
        }else{
            M = date.getMonth() + 1;
        }
        M = M + "-";
        var D = date.getDate() + ' ';
        var h = date.getHours() + ':';
        var m = date.getMinutes() + ':';
        var s = date.getSeconds();
        return Y+M+D+h+m+s;
    },
    viewMore: function () {
        //拿到已有的list
        var $listUserFeedbackList  = $("div[class='list']");
        var pageNum = $("#pageNumber").val();
        if(pageNum === undefined || pageNum === null || pageNum === ""){
            pageNum = 0;
        }else{
            pageNum = Number(pageNum)
            pageNum  += 1;
        }
        //获取新的list
        $.ajax({
            url: "getServiceInfo",
            type: "POST",
            dataType: "json",
            async: true,
            data: {
                pageNumber : pageNum
            },
            success: function (data) {
                if (data.code != 0) {
                    openTips(data.message);
                    return ;
                }
                //成功后去拼接
                ParamAndViewInit.appendUserFeedbackList(data.userFeedbackList);
                $("#pageNumber").val(data.pageNumber);
                $("#totalNumber").val(data.totalNumber);
                $("#totalPageNumber").val(data.totalPageNumber);


                if(Number(data.totalPageNumber) == (Number(data.pageNumber) + 1) ){
                    $(".more").unbind('click');
                    $(".more").text("已显示全部记录");
                }
            },
            error: function () {
                openTips("服务器异常，请稍后再试！")
                return;
            }
        });
    },
    appendUserFeedbackList: function (data) {
        var $divContent = $(".content");
        for(i in data){
            var $div0 = $("<div/>").addClass("list");
            var $p1 =  $("<p/>").addClass("title").text(data[i].feedbackTitle);
            var handleStatusStr = "";
            if(data[i].handleStatus == 1){
                handleStatusStr = "待处理";
            }
            if(data[i].handleStatus == 2){
                handleStatusStr = "处理中";
            }
            if(data[i].handleStatus == 3){
                handleStatusStr = "已处理";
            }
            var $p2 = $("<p/>").addClass("dcl").text(handleStatusStr);
            var $div1 = $("<div/>").addClass("titleBox").append($p1).append($p2);
            $div0.append($div1);
            var $p3 =  $("<p/>").addClass("main").text(data[i].feedbackContent);
            var $p4 =  $("<p/>").addClass("date").text(ParamAndViewInit.formatDate(data[i].addTime));
            var $div2 = $("<div/>").addClass("listContent").append($p3).append($p4);
            $div0.append($div2);
            if(data[i].handleContent != undefined && data[i].handleContent !=null && data[i].handleContent != ""){
                var $p5 =  $("<p/>").addClass("main").text(data[i].handleContent);
                var $div3 = $("<div/>").addClass("listContent").append($p5);
                $div0.append($div3);
            }
            $divContent.append($div0);
        }
    },
    addFeedFinish: function () {
        var webAppPath = $("#webAppPath").val();
        window.location.href = webAppPath + "/userWap/wapCustomerService/show";
    },
    addFeedback: function () {
        var feedbackTitleValue = $("#feedbackTitle").val();
        var feedbackContentValue = $("#feedbackContent").val();
        if(feedbackTitleValue ==undefined || feedbackTitleValue == null || feedbackTitleValue == ""){
            openTips("标题不能为空");
            return;
        }
        if(feedbackContentValue ==undefined || feedbackContentValue == null || feedbackContentValue == ""){
            openTips("内容不能为空");
            return;
        }
        $.ajax({
            url: "feedback.htm",
            type: "POST",
            dataType: "json",
            async: true,
            data: {
                feedbackTitle : feedbackTitleValue,
                feedbackContent : feedbackContentValue
            },
            success: function (data) {
                if (data.code != 0) {
                    openTips(data.message);
                    return;
                }
                openTips(data.message);
                window.setTimeout(ParamAndViewInit.addFeedFinish,2000);
            },
            error: function () {
                openTips("服务器异常，请稍后再试！")
                return;
            }
        });
    }
}
$(function () {
    //初始化事件
    ParamAndViewInit.show();
    $('.back').on('click',ParamAndViewInit.backToMine);
    var pageNum = $("#pageNumber").val();
    if(pageNum === undefined || pageNum === null || pageNum === ""){
        pageNum = 0;
    }
    Handlebars.registerHelper("ContactTypeFormat", function(type){
        if(type == undefined || type == null || type == "" || isNaN(type)){
            return "未知类型";
        }
        if(type == 1){
            return "待处理";
        }
        if(type == 2){
            return "处理中";
        }
        if(type == 3){
            return "已处理";
        }
        return "未知类型";
    });
    Handlebars.registerHelper("ContactTimeFormat", function(timestamp){
        var date = new Date(timestamp);//10位需要乘以1000
        var Y  = date.getFullYear() + "-";
        if(date.getMonth() + 1 < 10){
            M = '0' + (date.getMonth() + 1);
        }else{
            M = date.getMonth() + 1;
        }
        M = M + "-";
        var D = date.getDate() + ' ';
        var h = date.getHours() + ':';
        var m = date.getMinutes() + ':';
        var s = date.getSeconds();
        return Y+M+D+h+m+s;
    });
    Handlebars.registerHelper("showHandlerContent", function(handleContent){
        if(handleContent == undefined || handleContent == null || handleContent == ""){
            return "";
        }
        return "<div class='listContent'><p class='main'>" + handleContent + "</p></div>";
    });
    $.ajax({
        url: "getServiceInfo",
        type: "POST",
        dataType: "json",
        async: true,
        data: {
            pageNumber : pageNum
        },
        success: function (data) {
            if (data.code != 0) {
                openTips(data.message);
                returnl;
            }
            //加载数据
            var contactTemplate = $("#contactService").html();
            var contactComplile = Handlebars.compile(contactTemplate);
            var contactHtml = contactComplile(data);
            $("#contactDiv").html(contactHtml);
            $('.more').bind('click',ParamAndViewInit.viewMore);
            $('.okay').bind('click',ParamAndViewInit.addFeedback);
        },
        error: function () {
            openTips("服务器异常，请稍后再试！");
            return;
        }
    });
});