$().ready(function () {
    validateRule();
    $("#summary").val($("#summaryInput").val());
    $("#content").val($("#contentInput").val());
    $("#remark").val($("#remarkInput").val());
    $("#category").val($("#categoryInput").val());
    $("#openType").val($("#openTypeInput").val());
    $("#sysType").val($("#sysTypeInput").val());
    $("#sTime").val(dateFormatUtil($("#sTimeInput").val()));
    $("#eTime").val(dateFormatUtil($("#eTimeInput").val()));
    $("#commonUrl").attr("src", $("#commonImgInput").val());
    $("#wxUrl").attr("src", $("#wxImgInput").val());
    $("#iosUrl").attr("src", $("#iosImgInput").val());
    $("#androidUrl").attr("src", $("#androidImgInput").val());
});

$.validator.setDefaults({
    submitHandler: function () {
        update();
    }
});

function update() {
    $.ajax({
        cache: true,
        type: "POST",
        url: "/system/sysAdShare/update",
        data: $('#signupForm').serialize(),// 你的formid
        async: false,
        error: function (request) {
            parent.layer.alert("Connection error");
        },
        success: function (data) {
            if (data.code == 0) {
                parent.layer.msg("操作成功");
                parent.reLoad();
                var index = parent.layer.getFrameIndex(window.name); // 获取窗口索引
                parent.layer.close(index);

            } else {
                parent.layer.alert(data.msg)
            }

        }
    });

}

function validateRule() {
    var icon = "<i class='fa fa-times-circle'></i> ";
    $("#signupForm").validate({
        rules: {
            name: {
                required: true
            }
        },
        messages: {
            name: {
                required: icon + "请输入名字"
            }
        }
    })
}

/*
           时间格式化工具
           把Long类型的yyyy-MM-dd日期还原yyyy-MM-dd格式日期
       */
function dateFormatUtil(longTypeDate) {
    var dateTypeDate = "";
    var date = new Date();
    date.setTime(longTypeDate);
    dateTypeDate += date.getFullYear();   //年
    dateTypeDate += "-" + getMonth(date); //月
    dateTypeDate += "-" + getDay(date);   //日
    return dateTypeDate;
}

//返回 01-12 的月份值
function getMonth(date) {
    var month = "";
    month = date.getMonth() + 1; //getMonth()得到的月份是0-11
    if (month < 10) {
        month = "0" + month;
    }
    return month;
}

//返回01-30的日期
function getDay(date) {
    var day = "";
    day = date.getDate();
    if (day < 10) {
        day = "0" + day;
    }
    return day;
}