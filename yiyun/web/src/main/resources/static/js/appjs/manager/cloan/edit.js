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
    var title = '是否确认更新？ <br/>更新信息将推送至合伙人小程序上并短信通知合伙人';

    if (type == 1) {//待补充资料
        var remark = $("#remark").val();
        if (!remark) {
            layer.alert("请填写待补充材料");
            return;
        }
        $("#orderStatus").val(3);
    } else if (type == 2) { //服务进行中
        var remark = $("#remark").val();
        if (remark || remark.trim()) {
            layer.alert("当前尚有待补充材料");
            return;
        }
        var name = $("#name").val();
        var idcard = $("#idcard").val();
        var wechatPhone = $("#wechatPhone").val();
        if (!name || name=='' || !idcard || idcard=='' || !wechatPhone || wechatPhone == '') {
            layer.alert("请填写客户名称, 客户身份证号 , 客户手机号");
            return;
        }

        if (idcard.indexOf("*")<0 && !(/(^\d{15}$)|(^\d{17}([0-9]|X)$)/.test(idcard.toUpperCase())) ){
            layer.alert("客户身份证格式不正确");
            return;
        }
        if (wechatPhone.indexOf("*")<0 && !/^(1\d{10})$/.test(wechatPhone)) {
            layer.alert("客户手机号格式不正确");
            return;
        }

        $("#orderStatus").val(5);//
    } else if (type == 3) {//放款成功
        var remark = $("#remark").val();
        if (remark || remark.trim()) {
            layer.alert("当前尚有待补充材料");
            return;
        }
        var name = $("#name").val();
        var idcard = $("#idcard").val();
        var realAmount = $("#realAmount").val();
        var loanLimit = $("#loanLimit").val();
        var loanBeginTimeStr = $("#loanBeginTimeStr").val();
        var loanEndTimeStr = $("#loanEndTimeStr").val();
        var wechatPhone = $("#wechatPhone").val();
        if (!name || !idcard || !wechatPhone || !realAmount || !loanLimit || !loanBeginTimeStr || !loanEndTimeStr) {
            layer.alert("放款成功必须填写客户名称, 客户身份证号, 批复金额, 借款期限, 借款起止日期");
            return;
        }
        if (idcard.indexOf("*")<0 && !(/(^\d{15}$)|(^\d{17}([0-9]|X)$)/.test(idcard.toUpperCase())) ){
            layer.alert("客户身份证格式不正确");
            return;
        }
        if (wechatPhone.indexOf("*")<0 && !/^(1\d{10})$/.test(wechatPhone)) {
            layer.alert("客户手机号格式不正确");
            return;
        }

        title = '是否确认放款成功？<br/>点击《是》将此信息将推送至合伙人小程序上并短信通知合伙人';
        $("#orderStatus").val(6);//
    } else if (type == 4) {//放款失败
        var name = $("#name").val();
        var idcard = $("#idcard").val();
        var wechatPhone = $("#wechatPhone").val();
        if (!name || name=='' || !idcard || idcard=='' || !wechatPhone || wechatPhone == '') {
            layer.alert("请填写客户名称, 客户身份证号 , 客户手机号");
            return;
        }

        if (idcard.indexOf("*")<0 && !(/(^\d{15}$)|(^\d{17}([0-9]|X)$)/.test(idcard.toUpperCase())) ){
            layer.alert("客户身份证格式不正确");
            return;
        }
        if (wechatPhone.indexOf("*")<0 && !/^(1\d{10})$/.test(wechatPhone)) {
            layer.alert("客户手机号格式不正确");
            return;
        }
        title = '是否确人放款失败？ <br/>点击《是》将此信息将推送至合伙人小程序上并短信通知合伙人';
        $("#orderStatus").val(7);//
    } else if (type == 5) {//保存
    }

    layer.confirm(title, {
        btn: ['确定', '取消']
    }, function () {
        $.ajax({
            cache: true,
            type: "POST",
            url: "/manager/cloan/update",
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

function sendMsg() {
    var id = $("#id").val();
    layer.open({
        type: 2,
        title: '发送短信',
        maxmin: true,
        shadeClose: false, // 点击遮罩关闭层
        area: ['600px', '420px'],
        content: '/manager/cloan/sendMsg/' + id // iframe的url
    });
}

function showLog() {
    var id = $("#id").val();
    layer.open({
        type: 2,
        title: '发送记录',
        maxmin: true,
        shadeClose: false, // 点击遮罩关闭层
        area: ['700px', '500px'],
        content: '/manager/cloan/sendLog/' + id // iframe的url
    });
}


function showImg(type) {
    var id = $("#id").val();
    location.href = "/manager/cloan/showImg?id=" + id + "&type=" + type;
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