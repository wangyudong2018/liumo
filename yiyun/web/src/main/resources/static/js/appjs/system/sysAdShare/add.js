$().ready(function () {
    validateRule();
});

$.validator.setDefaults({
    submitHandler: function () {
        var commmonImg = $("#commonImgInput").val();
        if (commmonImg == null || commmonImg == "" || commmonImg == undefined) {
            layer.msg("请选择通用图片！");
            return;
        } else {
            save();
        }
    }
});

function save() {

    $.ajax({
        cache: true,
        type: "POST",
        url: "/system/sysAdShare/save",
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
            commonImgInput: {
                required: true
            }
        },
        messages: {

            commonImgInput: {
                required: icon + "请选择通用图片"
            }
        }
    })
}

