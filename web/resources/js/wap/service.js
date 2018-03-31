var ParamAndViewInit = {
    show: function () {
        var bgHeight = $(document).height();
        $('.submit').on('click',function() {
            $('.bg').fadeIn();
        });
        $('.cancel').on('click',function() {
            $("#feedbackTitle").val("");
            $("#feedbackContent").val("");
            $('.bg').fadeOut();
        });
    },
    backToMine: function () {
        var webAppPath = $("#webAppPath").val();
        window.location.href = webAppPath + "/userWap/userInfo/show.htm";
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
        if(s < 10){
            s = "0" + s;
        }
        return Y+M+D+h+m+s;
    },
    viewMore: function () {
        var bgHeight = $(document).height();
        $('.bg').css("height",bgHeight +"px");
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
                handleStatusStr = "<span style='color: #C9C9C9'>待处理</span>";
            }
            if(data[i].handleStatus == 2){
                handleStatusStr = "<span style='color: dodgerblue'>处理中</span>";
            }
            if(data[i].handleStatus == 3){
                handleStatusStr = "<span style='color: yellow'>已处理</span>";
            }
            var $p2 = $("<p/>").addClass("dcl").html(handleStatusStr);
            var $div1 = $("<div/>").addClass("titleBox").append($p1).append($p2);
            $div0.append($div1);
            var $p3 =  $("<p/>").addClass("main").html(data[i].feedbackContent);
            var $p4 =  $("<p/>").addClass("date").text(ParamAndViewInit.formatDate(data[i].addTime));
            var $div2 = $("<div/>").addClass("listContent").append($p3).append($p4);
            $div0.append($div2);
            if(data[i].handleContent != undefined && data[i].handleContent !=null && data[i].handleContent != ""){
                var $p5 =  $("<p/>").addClass("main").html(data[i].handleContent);
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
        if (feedbackTitleValue.length < 2 || feedbackTitleValue.length > 16) {
            openTips("反馈标题应该为2~16个字符");
            return;
        }
        if(feedbackContentValue ==undefined || feedbackContentValue == null || feedbackContentValue == ""){
            openTips("内容不能为空");
            return;
        }
        if (feedbackContentValue.length > 400) {
            openTips("反馈内容不能超过400个字符");
            return ;
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
                $('.bg').fadeOut();
                openTips(data.message);
                window.setTimeout(ParamAndViewInit.addFeedFinish,1000);
            },
            error: function () {
                openTips("服务器异常，请稍后再试！")
                return;
            }
        });
    }
}
$(function () {
    var bgHeight = $(document).height();
    //初始化事件
    ParamAndViewInit.show();
    $('.back').on('click',ParamAndViewInit.backToMine);
    var pageNum = $("#pageNumber").val();
    if(pageNum === undefined || pageNum === null || pageNum === ""){
        pageNum = 0;
    }
    Handlebars.registerHelper("contactTypeFormat", function(type){
        if(type == undefined || type == null || type == "" || isNaN(type)){
            return "未知类型";
        }
        if(type == 1){
            return "<span style='color: #C9C9C9'>待处理</span>";
        }
        if(type == 2){
            return "<span style='color: dodgerblue'>处理中</span>";
        }
        if(type == 3){
            return "<span style='color: yellow'>已处理</span>";
        }
        return "未知类型";
    });
    Handlebars.registerHelper("contactTimeFormat", function(timestamp){
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
        if(s < 10){
            s = "0" + s;
        }
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
            var bgHeight = $(document).height();
             $('.bg').css("height",bgHeight +"px");
            if(Number(data.totalPageNumber) == (Number(data.pageNumber) + 1) ){
                $(".more").unbind('click');
                $(".more").text("已显示全部记录");
            }
        },
        error: function () {
            openTips("服务器异常，请稍后再试！");
            return;
        }
    });
});