$().ready(function () {
    validateRule();

    /** 小类回显 */
    var typeSelectTxt = $("#type1 :selected").attr("mtype");
    $("#type2").append('<option value="">请选择产品小类</option>');
    if (typeSelectTxt) {
        var typeList = typeSelectTxt.split(",");
        if (typeList) {
            var typeValue = $("#type2Value").val();
            for (var i = 0; i < typeList.length; i++) {
                var type = typeList[i].trim();
                if (type) {
                    var isSelect = typeValue == type ? "selected" : "";
                    if (isSelect) {
                        $("#type2").append('<option value="' + type + '" selected="' + isSelect + '">' + type + '</option>');
                    } else {
                        $("#type2").append('<option value="' + type + '">' + type + '</option>');
                    }

                }
            }
        }
    }

    /** 大类/小类 二级联动 */
    $("#type1").change(function () {
        var mtype = $("#type1 :selected").attr("mtype");
        $("#type2").html('<option value="">请选择产品小类</option>');
        if (mtype) {
            var mtypeList = mtype.split(",");
            if (mtypeList) {
                for (var i = 0; i < mtypeList.length; i++) {
                    var type2 = mtypeList[i].trim();
                    if (type2) {
                        $("#type2").append('<option value="' + type2 + '">' + type2 + '</option>');
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
    var paramTxt = $('#signupForm').serialize();
    var statusTxt = paramTxt.indexOf('status') >=0 ? '启用' : '失效';
    layer.confirm('产品状态为' + statusTxt + '，是否确认保存修改内容？<br/>如点击《是》，小程序及网站产品页将同步更新！', {
        btn: ['是', '否'] //按钮
    }, function () {
        $.ajax({
            cache: true,
            type: "POST",
            url: "/operation/newProduct/update",
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