var ParamAndViewInit = {
    show: function () {
        var bgHeight = $(document).height();
        $('.submit').on('click',function() {
            $('.bg').fadeIn();
        });
        $('.cancel').on('click',function() {
            $('.bg').fadeOut();
            $("#feedbackTitle").val("");
            $("#feedbackContent").val("");
        });
    },
    backToMine: function () {
        window.location.href = ParamAndViewInit.webPath  + "/userWap/userInfo/show.htm";
    },
    formatDate: function(timestamp){
        var date = new Date(timestamp);
        var y = date.getFullYear();
        var m = date.getMonth() + 1;
        m = m < 10 ? ('0' + m) : m;
        var d = date.getDate();
        d = d < 10 ? ('0' + d) : d;
        var h = date.getHours();
        h = h < 10 ? ('0' + h) : h;
        var minute = date.getMinutes();
        var second = date.getSeconds();
        minute = minute < 10 ? ('0' + minute) : minute;
        second = second < 10 ? ('0' + second) : second;
        return y + '-' + m + '-' + d+' '+h+':'+minute+':'+second;
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
                handleStatusStr = "<p class='dcl'>待处理</p>";
            }
            if(data[i].handleStatus == 2){
                handleStatusStr = "<p class='clz'>处理中</p>";
            }
            if(data[i].handleStatus == 3){
                handleStatusStr = "<p class='ycl'>已处理</p>";
            }
            var $p2 = $("<p/>").html(handleStatusStr);
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
            var bgHeight = $(document).height();
            $('.bg').css("height",bgHeight +"px");
        }
    },
    addFeedFinish: function () {
        window.location.href = "show";
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
            return "<p class='dcl'>待处理</p>";
        }
        if(type == 2){
            return "<p class='clz'>处理中</p>";
        }
        if(type == 3){
            return "<p class='ycl'>已处理</p>";
        }
        return "未知类型";
    });
    Handlebars.registerHelper("contactTimeFormat", function(timestamp){
        var date = new Date(timestamp);
        var y = date.getFullYear();
        var m = date.getMonth() + 1;
        m = m < 10 ? ('0' + m) : m;
        var d = date.getDate();
        d = d < 10 ? ('0' + d) : d;
        var h = date.getHours();
        h = h < 10 ? ('0' + h) : h;
        var minute = date.getMinutes();
        var second = date.getSeconds();
        minute = minute < 10 ? ('0' + minute) : minute;
        second = second < 10 ? ('0' + second) : second;
        return y + '-' + m + '-' + d+' '+h+':'+minute+':'+second;
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