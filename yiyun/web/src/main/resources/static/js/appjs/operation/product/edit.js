$().ready(function () {
    validateRule();
    $("#type").val($("#typeInput").val());
    if ($("#isMortgageAgainInput").val() == $("#isMortgageAgainYes").val()) {
        $("#isMortgageAgainYes").attr("checked", true);
    } else {
        $("#isMortgageAgainNo").attr("checked", true);
    }
    if ($("#isEarlyRepayInput").val() == $("#isEarlyRepayYes").val()) {
        $("#isEarlyRepayYes").attr("checked", true);
    } else {
        $("#isEarlyRepayNo").attr("checked", true);
    }
    $("#introduce").val($("#introduceInput").val());
    $("#repayMethod").val($("#repayMethodInput").val());
    $("#client").val($("#clientInput").val());
    $("#houseTimeLimit").val($("#houseTimeLimitInput").val());
    $("#houseValueLimit").val($("#houseValueLimitInput").val());
    $("#pawnType").val($("#pawnTypeInput").val());
    $("#houseArea").val($("#houseAreaInput").val());
    $("#needData").val($("#needDataInput").val());

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
        url: "/yiyun/operation/update",
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
                // required: icon + "请输入名字"
            }
        }
    })
}