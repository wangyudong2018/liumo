$().ready(function () {
    validateRule();

    /* 大类/小类 二级联动 */
    $("#type1").change(function () {
        var mtypeTxt = $("#type1 :selected").attr("mtype")
        $("#type2").html('<option value="" selected="selected">请选择产品小类</option>');
        if (mtypeTxt) {
            var mtypeList = mtypeTxt.split(",");
            if (mtypeList) {
                for (var i = 0; i < mtypeList.length; i++) {
                    var mtype = mtypeList[i].trim();
                    if (mtype) {
                        $("#type2").append('<option value="' + mtype + '">' + mtype + '</option>');
                    }
                }
            }
        }
    });
});

$.validator.setDefaults({
    submitHandler: function () {
        save();
    }
});

function save() {
    var paramTxt = $('#signupForm').serialize();
    var statusTxt = paramTxt.indexOf('status') >=0 ? '启用' : '失效';
    layer.confirm('产品状态为' + statusTxt + '，是否确认保存修改内容？<br/>如点击《是》，小程序及网站产品页将同步更新！', {
        btn: ['是', '否'] //按钮
    }, function () {
        $.ajax({
            cache: true,
            type: "POST",
            url: "/operation/newProduct/save",
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
            },
            type1: {
                required: true
            },
            type2: {
                required: true
            }
        },
        messages: {
            name: {
                required: icon + "请输入产品名称"
            },
            type1: {
                required: icon + "请选择产品大类"
            },
            type2: {
                required: icon + "请选择产品小类"
            }
        }
    })
}