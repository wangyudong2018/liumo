//获取验证码接口
var wait = 60;

function time(o) {
    if (wait == 0) {
        o.removeAttribute("disabled");
        o.value = "免费获取验证码";
        wait = 60;
    } else {
        o.setAttribute("disabled", true);
        o.value = "重新发送(" + wait + ")";
        wait--;
        setTimeout(function () {
            time(o)
        }, 1000)
    }
}

function yzm_ajax() {
    //time($("#btn"))
    var reg = /^0?1[3|5|7|8][0-9]\d{8}$/;
    var tel = $("#tel").val();
    if (tel == "") {
        layer.msg('请输入手机号！', {time: 2000});
        return false;
    } else {
        if (!reg.test(tel)) {
            layer.msg('手机号输入不正确！', {time: 2000});
            return false;
        } else {
            time(document.getElementById("btn"));
            $.post("/wechat/appMember/sendSms", {phone: $("#tel").val()}, function (resp) {
                var data = resp;
                if (data.flag == 1) {
                    layer.msg(data.message, {time: 2000});
                } else {
                    layer.msg(data.message, {time: 2000});
                }
            });
        }

    }
}

//获取验证码接口
$(function () {
    $("#tijiao").click(function () {   //提交校验
        var tel = $("#tel").val();
        if (tel == "") {
            layer.msg('请输入您的手机号！', {time: 2000});
            return false;
        } else {
            var reg = /^0?1[3|5|7|8][0-9]\d{8}$/;
            if (!reg.test(tel)) {
                layer.msg('手机号输入不正确！', {time: 2000});
                return false;
            } else {
                var tel_yzm = $(".tel_yzm").val();
                if (tel_yzm == "") {
                    layer.msg('请输入验证码！', {time: 2000});
                    return false;
                } else {
                    //提交ajax
                    $.post("/wechat/appMember/login", {
                        phone: $("#tel").val(),
                        msgInputCode: $(".tel_yzm").val(),
                        redirectUrl: $("#redirectUrl").val()
                    }, function (resp) {
                        var data = resp;
                        if (data.flag == 1) {
                            //alert(data.message);
                            localStorage.setItem("phone", $("#tel").val());
                            if (data.DATA.redirectUrl) {
                                location.href = data.DATA.redirectUrl + "?phone=" + $("#tel").val()
                            } else {
                                location.href = "/wechat/appMember/toOrderList?phone=" + $("#tel").val()
                            }
                        } else {
                            layer.msg(data.message, {time: 2000});
                        }
                    });
                    return false;
                }
            }
        }
    });
    $("#tel").keyup(function () {
        $(this).css({"border": "1px solid #ff8902"});
        if ($(this).val() == "") {
            $(".bg").css({"background": "#b4b4b4"})
        } else {
            $(".bg").css({"background": "#ff8902"})
        }
    });
    $("#tel").change(function () {
        $(this).css({"border": "1px solid #f2f2f2"})
    });
    $(".tel_yzm").keyup(function () {
        $(this).css({"border": "1px solid #ff8902"});
    });
    $(".tel_yzm").change(function () {
        $(this).css({"border": "1px solid #f2f2f2"})
    })
});