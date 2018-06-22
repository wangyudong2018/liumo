$().ready(function () {
    validateRule();

    /* 城市/区域 二级联动 */
    $("#city").change(function () {
        var areaTxt = $("#city :selected").attr("cityarea");
        $("#area").html('<option value="" selected="selected">请选择所在区域</option>');
        if(areaTxt){
            var areaList = areaTxt.split(",");
            if(areaList){
                for(var i=0;i<areaList.length;i++){
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
        save();
    }
});

function save() {
    layer.confirm('是否确认保存修改内容？<br/>如点击《是》，小程序地址将更新！', {
        btn: ['是', '否'] //按钮
    }, function () {
        $.ajax({
            cache: true,
            type: "POST",
            url: "/operation/adress/save",
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
            type: {
                required: true
            },
            city: {
                required: true
            },
            phone: {
                required: true,
                checkPhone:true

            },
            detailAdress: {
                required: true,
                minlength:5
            }
        },
        messages: {
            type: {
                required: icon + "请选择地址分类"
            },
            city: {
                required: icon + "请选择所在城市"
            },
            phone:{
                required: icon + "请输入联系电话"
            },
            detailAdress: {
                required: icon + "请输入详细地址",
                minlength:icon + "详细地址信息过短"
            }
        }
    });

    jQuery.validator.addMethod("checkPhone", function(value, element) {
        var length = value.length;
        var mobile = /^(1\d{10})$/;

        var phone = /(^(\d{3,4}-)?\d{6,8}$)|(^(\d{3,4}-)?\d{6,8}(-\d{1,5})?$)|(\d{11})/;

        return this.optional(element) || (mobile.test(value) || phone.test(value));
    }, $.validator.format("请输入正确的联系电话"));

}