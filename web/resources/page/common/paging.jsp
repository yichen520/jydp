<%@page pageEncoding="UTF-8"%>
<body>
    <div class="changePage">
        <p class="total" id="totalNumber">共${totalNumber}条</p>
        <p class="jump">
            <input type="text" id="pageGoNumber"  value="${pageNumber+1}"
                   autocomplete="off" maxLength="8" onkeyup="value=value.replace(/[^\d]/g,'')"/>
            <input type="text" value="跳&nbsp;转" class="jumpButton" onfocus="this.blur()" onclick="pageGo();"/>
        </p>
        <p class="page">
            <input type="text" class="first" value="首页" onfocus="this.blur()" onclick="pageFirst();"/>
            <input type="text" class="upPage" value="<上一页" onfocus="this.blur()" onclick="pagePre();"/>
            <span class="pageNumber"><span>${pageNumber+1}</span>/<span>${totalPageNumber}</span></span>
            <input type="text" class="downPage" value="下一页>" onfocus="this.blur()" onclick="pageNext();"/>
            <input type="text" class="end" value="尾页" onfocus="this.blur()" onclick="pageEnd();"/>
        </p>
    </div>
</body>
<script type="text/javascript">
    //上一页
    function pagePre() {
        var pageNumber = parseInt(${pageNumber });

        if(pageNumber > 0){
            pageNumber = pageNumber - 1;

            document.getElementById("queryPageNumber").value = pageNumber;
            $("#queryForm").submit();
        } else {
            openTips("当前已是第一页");
        }
    }

    //下一页
    function pageNext() {
        var pageNumber = parseInt(${pageNumber });
        var totalPageNumber = parseInt(${totalPageNumber });

        if(pageNumber < totalPageNumber - 1){
            pageNumber = pageNumber + 1;

            document.getElementById("queryPageNumber").value = pageNumber;
            $("#queryForm").submit();
        } else {
            openTips("当前已是最后一页");
        }
    }

    //跳转首页
    function pageFirst() {
        var pageNumber = parseInt(${pageNumber });

        if(pageNumber > 0){
            pageNumber = 0;

            document.getElementById("queryPageNumber").value = pageNumber;
            $("#queryForm").submit();
        } else {
            openTips("当前已是第一页");
        }
    }

    //跳转尾页
    function pageEnd() {
        var pageNumber = parseInt(${pageNumber });
        var totalPageNumber = parseInt(${totalPageNumber });

        if(pageNumber < totalPageNumber - 1){
            pageNumber = totalPageNumber - 1;

            document.getElementById("queryPageNumber").value = pageNumber;
            $("#queryForm").submit();
        }else{
            openTips("当前已是最后一页");
        }
    }

    //页面跳转
    function pageGo() {
        var pageNumber = ${pageNumber };
        var totalPageNumber = parseInt(${totalPageNumber });

        var pageGoNumber = document.getElementById("pageGoNumber").value;
        var pageGoNumberInt = parseInt(pageGoNumber);
        if(pageGoNumber == null || pageGoNumber == "" || isNaN(pageGoNumberInt) || !pageGoNumberInt > 0){
            pageGoNumberInt = 1;
        }

        if(pageGoNumberInt > totalPageNumber) {
            return openTips("当前最大" + totalPageNumber + "页");
        }

        pageNumber = pageGoNumberInt - 1;
        document.getElementById("queryPageNumber").value = pageNumber;
        $("#queryForm").submit();
    }
</script>