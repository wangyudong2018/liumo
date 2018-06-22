$().ready(function () {
    validateRule();

    /** 区域回显 */
    var areaSelectTxt = $("#city :selected").attr("cityarea");
    $("#area").append('<option value="">请选择所在区域</option>');
    if (areaSelectTxt) {
        var areaList = areaSelectTxt.split(",");
        if (areaList) {
            var areaValue = $("#areaValue").val();
            for (var i = 0; i < areaList.length; i++) {
                var area = areaList[i].trim();
                if (area) {
                    var isSelect = areaValue == area ? "selected" : "";
                    if (isSelect) {
                        $("#area").append('<option value="' + area + '" selected="' + isSelect + '">' + area + '</option>');
                    }else{
                        $("#area").append('<option value="' + area + '">' + area + '</option>');
                    }

                }
            }
        }
    }


    /** 城市/区域 二级联动 */
    $("#city").change(function () {
        var areaTxt = $("#city :selected").attr("cityarea");
        $("#area").html('<option value="">请选择所在区域</option>');
        if (areaTxt) {
            var areaList = areaTxt.split(",");
            if (areaList) {
                for (var i = 0; i < areaList.length; i++) {
                    var area = areaList[i].trim();
                    if (area) {
                        $("#area").append('<option value="' + area + '">' + area + '</option>');
                    }
                }
            }
        }
    });
});

$.validator.setDefaults({
    submitHandler: function () {
        update();
    }
});

function update() {
    layer.confirm('是否确认保存修改内容？<br/>如点击《是》，小程序及网站产品页将同步更新！', {
        btn: ['是', '否'] //按钮
    }, function () {
        $.ajax({
            cache: true,
            type: "POST",
            url: "/operation/adress/update",
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