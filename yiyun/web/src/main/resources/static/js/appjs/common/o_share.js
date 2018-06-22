//订单状态
function getOrderStatus(i) {
    var str;
    switch (i) {
        case -2:
            str = "系统取消";
            break;
        case -1:
            str = "用户取消";
            break;
        case 0:
            str = "新申请";
            break;
        case 1:
            str = "待审核";
            break;
        case 2:
            str = "审核失败";
            break;
        case 3:
            str = "补充资料";
            break;
        case 4:
            str = "待支付";
            break;
        case 5:
            str = "待确认";
            break;
        case 6:
            str = "待放款";
            break;
        case 7:
            str = "还款中";
            break;
        case 8:
            str = "逾期";
            break;
        case 9:
            str = "结清";
            break;
        case 10:
            str = "逾期结清";
            break;
        case 11:
            str = "提前结清";
            break;
    }
    return str;
}

//格式化日期
function getDate(data) {
    var date = new Date(parseInt(data));
    if (data == "" || data == "-" || data == undefined) {
        return "-";
    } else {
        return date.getFullYear() + "-"
            + (date.getMonth() + 1)
            + "-" + date.getDate()
            + " " + date.getHours()
            + ":" + date.getMinutes()
            + ":" + date.getSeconds();
    }
}

//订单详情
function ordernfo(orderSn) {
    $("#paramOrderSn").val(orderSn);
    fm.action = '/order/getOrderDetailInfo';
    fm.submit();
}

//借款跟踪
function checkOpinion(orderSn) {
    layer.open({
        type: 2,
        title: '订单跟踪',
        maxmin: true,
        shadeClose: false, // 点击遮罩关闭层
        area: ['800px', '420px'],
        content: '/order/orderFollow/add/' + orderSn // iframe的url
    });
}

//增加黑名单
function addBlack(id) {
    layer.confirm('是否确认拉入黑名单？', {
            btn: ['确定', '取消']
        },
        function () {
            $.ajax({
                url: "/yiyun/mMember/toBlackList",
                type: "post",
                data: {
                    'id': id
                },
                success: function (r) {
                    debugger
                    layer.msg(r.msg);
                    window.reLoad();
                }
            });
        })
}

//放款
function loanOperation(orderSn) {
    layer.confirm('是否确认放款？', {
        btn: ['确定', '取消']
    }, function () {
        $.ajax({
            url: "/order/loanMoneyBySn",
            type: "post",
            data: {
                'orderSn': orderSn
            },
            success: function (r) {
                layer.msg(r.message);
            }
        });
    })
}

//放款确认
function loanConfirm(orderSn) {
    layer.confirm('是否确认放款？', {
        btn: ['确定', '取消']
    }, function () {
        $.ajax({
            url: "/order/loanConfirm",
            type: "post",
            data: {
                'orderSn': orderSn
            },
            success: function (r) {
                parent.layer.msg(r.message);
                layer.closeAll();
                reLoad();
            }
        });
    })
}

//任务流转
function orderCommit(orderSn) {
    layer.confirm('确定要转至客服？', {
        btn: ['确定', '取消']
    }, function () {
        $.ajax({
            url: "/oTask/toPhoneCheck",
            type: "post",
            data: {
                'orderSn': orderSn,
                'roleCode': "CHECK"
            },
            success: function (r) {
                layer.msg(r.message);
                setTimeout(function () {
                    window.location.href = '/oTask/1';
                }, 0)

            }
        });
    });

}

//拉入黑名单
function black(id) {
    layer.confirm('确定将该用户拉入黑名单？', {
        btn: ['确定', '取消']
    }, function () {
        $.ajax({
            url: "/system/users/joinBlack",
            type: "post",
            data: {
                'id': id
            },
            success: function (r) {
                if (r.code == 0) {
                    layer.msg(r.msg);
                    reLoad();
                } else {
                    layer.msg(r.msg);
                }
            }
        });
    })
}

//查看用户详情
function userDetail(id, type, orderSn) {
    $("#id").val(id);
    $("#typee").val(type);
    $("#paramOrderSn").val(orderSn);
    fm.action = '/system/users/detail/' + id;
    fm.submit();
}

//冲账
function accountsRecord(id) {
    layer.open({
        type: 2,
        title: '冲账',
        maxmin: true,
        shadeClose: false, // 点击遮罩关闭层
        area: ['800px', '420px'],
        content: '/order/accountsRecord/repaymentPlan/' + id// iframe的url
    });

}

//查看分期计划详情（还款计划表）
function stagesPlan(planId) {
    $("#planId").val(planId);
    fm.action = '/order/repaymentRecord/stagesPlan/' + planId;
    fm.submit();
}

//代扣
function chargeBacks(periodId) {
    layer.open({
        type: 2,
        title: '代扣',
        maxmin: true,
        shadeClose: false, // 点击遮罩关闭层
        area: ['800px', '420px'],
        content: '/order/repaymentRecord/chargeBacks/' + periodId// iframe的url
    });
}

//订单审核
function orderCheck(orderSn, phone, userId) {
    layer.open({
        type: 2,
        title: '订单审核',
        maxmin: true,
        shadeClose: false, // 点击遮罩关闭层
        area: ['800px', '500px'],
        content: '/order/checkOrder/' + orderSn + '/' + phone + '/' + userId // iframe的url
    });
}

//提醒反馈跳转
function orderRemind(orderSn, periodId, type) {
    layer.open({
        type: 2,
        title: '还款跟踪',
        maxmin: true,
        shadeClose: false, // 点击遮罩关闭层
        area: ['800px', '420px'],
        content: '/order/oOrderRemind/feedbackRemind/' + orderSn + '/' + periodId + '/' + type
    });
}

//线下还款
function lowerRepayment(orderSn, periodId) {
    layer.open({
        type: 2,
        title: '线下还款',
        maxmin: true,
        shadeClose: false, // 点击遮罩关闭层
        area: ['800px', '500px'],
        content: '/order/repaymentRecord/lowerRepayment/' + orderSn + '/' + periodId
    });
}


//新申请订单取消确认编辑原因
function cancelOrder(id) {
    layer.confirm('确定要取消选此订单？', {
        btn: ['确定', '取消']
    }, function (id) {
        layer.open({
            type: 2,
            title: '编辑',
            maxmin: true,
            shadeClose: false, // 点击遮罩关闭层
            area: ['800px', '520px'],
            content: '/order/cancelOrder/' + id // iframe的url
        });
    })
}

//退款
function refund(id) {
    layer.open({
        type: 2,
        title: '退款',
        maxmin: true,
        shadeClose: false, // 点击遮罩关闭层
        area: ['800px', '500px'],
        content: '/order/refund/' + id
    });
}

//收支对账
function mReconciliation(responseTimeBegin, responseTimeEnd) {
    $("#responseTimeBegin").val(responseTimeBegin);
    $("#responseTimeEnd").val(responseTimeEnd);
    fm.action = '/mReconciliation/detail';
    fm.submit();
}

/** 获取请求地址中的参数 */
function getUrlParam(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
    var r = window.location.search.substr(1).match(reg);  //匹配目标参数
    if (r != null) return decodeURI(r[2]);
    return null; //返回参数值
}

/** 清除所有查询参数 */
function pmclear() {
    console.log("清除所有内容");
    $(".pmclear").val("");
}