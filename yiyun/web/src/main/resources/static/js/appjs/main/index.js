//获取验证码接口
var wait = 60;
var invcode = getUrlParam('invcode');

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
    if ($("#select1 .select_showbox").text() == "请选择所在区域") {
        layer.msg('请选择所在的区域！', {time: 2000});
        return false;
    } else {
        if ($("#select2 .select_showbox").text() == "请选择产品类别") {
            layer.msg('请选择产品类别！', {time: 2000});
            return false;
        } else {
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
                    $.post("/main/sendSms", {phone: tel}, function (resp) {
                        var data = resp;//$.parseJSON(resp);
                        if (data.flag == 1) {
                            layer.msg(data.message);
                            time(document.getElementById("btn"));
                        } else {
                            layer.alert(data.message);
                        }
                    });
                }
            }
        }
    }
}

//提交订单接口
$(function () {
    $("#tijiao").click(function () {   //提交校验
        if ($("#select1 .select_showbox").text() == "请选择所在区域") {
            layer.msg('请选择所在的区域！', {time: 2000});
            return false;
        } else {
            if ($("#select2 .select_showbox").text() == "请选择产品类别") {
                layer.msg('请选择产品类别！', {time: 2000});
                return false;
            } else {
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
                            var area = $("#select1 .select_showbox").text();
                            var productType = $("#select2 .select_showbox").text();
                            var smsCode = $(".tel_yzm").val();
                            $.post("/mLoan/commitLoan", {
                                city: area,
                                productType: productType,
                                loanMemberPhone: tel,
                                smsCode: smsCode,
                                column1 : invcode
                            }, function (resp) {
                                var data = resp;//$.parseJSON(resp);
                                if (data.flag == 1) {
                                    layer.msg(data.message);
                                    $(".tel_yzm").val("");
                                } else {
                                    layer.alert(data.message);
                                }
                            });
                            return false;
                        }
                    }
                }
            }
        }
    });

    $(function () {
        $(".foot_liji").click(function () {
            $(".fix_dis").animate({"right": "0px"})
        });
        $("#liji_tonghua").click(function () {
            if ($("#dibu_tel").val() == "") {
                layer.msg('请输入手机号！', {time: 2000});
                return false;
            } else {
                var reg = /^0?1[3|5|7|8][0-9]\d{8}$/;
                if (!reg.test($("#dibu_tel").val())) {
                    layer.msg('手机号输入不正确！', {time: 2000});
                    return false;
                } else {
                    $.post("/mLoan/commitLoanNoCode", {
                        loanMemberPhone: $("#dibu_tel").val(),
                        column1 : invcode
                    }, function (resp) {
                        var data = resp;//$.parseJSON(resp);
                        if (data.flag == 1) {
                            layer.msg(data.message);
                            $(".tel_yzm").val("");
                        } else {
                            layer.alert(data.message);
                        }
                        $("#dibu_tel").val("");
                        $(".fix_dis").animate({"right": "-380px"})
                    });
                    return false;
                }
            }
        });
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