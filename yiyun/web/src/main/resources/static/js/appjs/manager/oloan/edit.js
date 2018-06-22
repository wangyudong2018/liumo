$().ready(function () {
    validateRule();

    /** 商务岗设置用户名 */
    $("#column3").change(function () {
        $("#customer").val($("#column3 :selected").text());
    })
});

$.validator.setDefaults({
    submitHandler: function () {
        operate();
    }
    
});

function operate(type) {
    var title = '是否确认《受理失败》？';

    if (type == 1) {//受理成功
        var expectAmount = $("#expectAmount").val();
        if (!expectAmount) {
            layer.alert("评估值金额不能为空");
            return;
        }
        if (parseInt(expectAmount) <= 0) {
            layer.alert("评估值金额必须大于0");
            return;
        }
        var commerce = $("#column4").val();
        if (!commerce) {
            layer.alert("必须选择一个商务人员");
            return;
        }
        $("#orderStatus").val(2);//受理成功
        $("#orderStep").val(2);//商务岗
        $("#customer").val($("#column4 :selected").text());//商务受理人
        title = "是否确认受理 ? 评估值" + expectAmount + "万元<br/>" + "下一步提交至商务经理 : " + $("#column4 :selected").text();

    } else if (type == 2) {
        $("#orderStatus").val(11);//受理失败
    }else if(type == 3){
        $("#customer").val($("#column4 :selected").text());//商务受理人
        title = '是否确认《保存》？'
    }

    layer.confirm(title, {
        btn: ['确定', '取消']
    }, function () {
        $.ajax({
            cache: true,
            type: "POST",
            url: "/manager/oloan/update",
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

function showImg(type) {
    var id = $("#id").val();
    location.href = "/manager/oloan/showImg?id=" + id + "&type=" + type;
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