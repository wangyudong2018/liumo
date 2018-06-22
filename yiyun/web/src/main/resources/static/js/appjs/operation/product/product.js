var prefix = "/operation/product";
$(function () {
    load();
});

function load() {
    $('#exampleTable')
        .bootstrapTable(
            {
                method: 'get', // 服务器数据的请求方式 get or post
                url: prefix + "/list", // 服务器数据的加载地址
                //	showRefresh : true,
                //	showToggle : true,
                //	showColumns : true,
                iconSize: 'outline',
                toolbar: '#exampleToolbar',
                striped: true, // 设置为true会有隔行变色效果
                dataType: "json", // 服务器返回的数据类型
                pagination: true, // 设置为true会在底部显示分页条
                // queryParamsType : "limit",
                // //设置为limit则会发送符合RESTFull格式的参数
                singleSelect: false, // 设置为true将禁止多选
                // contentType : "application/x-www-form-urlencoded",
                // //发送到服务器的数据编码类型
                pageSize: 10, // 如果设置了分页，每页数据条数
                pageNumber: 1, // 如果设置了分布，首页页码
                //search : true, // 是否显示搜索框
                showColumns: false, // 是否显示内容下拉框（选择显示的列）
                sidePagination: "server", // 设置在哪里进行分页，可选值为"client" 或者 "server"
                queryParams: function (params) {
                    return {
                        //说明：传入后台的参数包括offset开始索引，limit步长，sort排序列，order：desc或者,以及所有列的键值对
                        limit: params.limit,
                        offset: params.offset,
                        name:$('#name').val(),
                        // username:$('#searchName').val()
                    };
                },
                // //请求服务器数据时，你可以通过重写参数的方式添加一些额外的参数，例如 toolbar 中的参数 如果
                // queryParamsType = 'limit' ,返回参数必须包含
                // limit, offset, search, sort, order 否则, 需要包含:
                // pageSize, pageNumber, searchText, sortName,
                // sortOrder.
                // 返回false将会终止请求
                columns: [
                    {
                        checkbox: true
                    },
                    /*{
                        field: 'id',
                        title: '主键'
                    },*/
                    {
                        field: 'type',
                        title: '类别',
                        formatter: function (data, type, row) {
                            if(data==1){
                                return "信贷";
                            }else if(data==2){
                                return "房贷";
                            }
                            return data;
                        }
                    },
                  /*  {
                        field: 'productStatus',
                        title: '状态'
                    },*/
                    {
                        field: 'name',
                        title: '产品名'
                    },
                    {
                        field: 'logo',
                        title: '图片',
                        formatter: function (value, row, index) {
                            var html = '<img src="' + value + '" style="width: 50px;height: 50px;">';
                            return html;
                        }
                    },
                    /*{
                        field: 'introduce',
                        title: '介绍'
                    },*/
                    {
                        field: 'monthRateLower',
                        title: '月利率(%)',
                        formatter: function (value, row, index) {
                            return row.monthRateLower + "-" + row.monthRateUpper;
                        }
                    },
                    /*{
                        field: 'monthRateUpper',
                        title: '月利上限'
                    },*/
                    {
                        field: 'loanTime',
                        title: '放款时间(天)'
                    },
                    {
                        field: 'loanLimitLower',
                        title: '贷款期限(月)',
                        formatter: function (value, row, index) {
                            return row.loanLimitLower + "-" + row.loanLimitUpper;
                        }
                    },
                    /*{
                        field: 'loanLimitUpper',
                        title: '贷款期限上限'
                    },*/
                    {
                        field: 'loanAmountLower',
                        title: '放款金额(万元)',
                        formatter: function (value, row, index) {
                            return row.loanAmountLower + "-" + row.loanAmountUpper;
                        }
                    },
                    /*{
                        field: 'loanAmountUpper',
                        title: '放款金额上限'
                    },*/
                    /*{
                        field: 'client',
                        title: '使用身份'
                    },*/
                    {
                        field: 'repayMethod',
                        title: '还款方式'
                    },
                    {
                        field: 'isMortgageAgain',
                        title: '房产二押'
                    },
                    /*{
                        field: 'isEarlyRepay',
                        title: '提前还款'
                    },*/
                    {
                        field: 'serverceCharge',
                        title: '一次性服务费'
                    },
                    {
                        field: 'houseTimeLimit',
                        title: '房龄要求',
                        formatter: function (value, row, index) {
                            return row.houseTimeLimit && row.houseTimeLimit.length >= 20 ? row.houseTimeLimit.substring(0, 20) + " . . ." : row.houseTimeLimit;
                        }
                    },
                    {
                        field: 'houseValueLimit',
                        title: '房产估值要求',
                        formatter: function (value, row, index) {
                            return row.houseValueLimit && row.houseValueLimit.length >= 20 ? row.houseValueLimit.substring(0, 20) + " . . ." : row.houseValueLimit;
                        }
                    },
                    /*{
                        field: 'houseArea',
                        title: '房产坐落区域'
                    },*/
                    /*{
                        field: 'pawnType',
                        title: '抵押物类型'
                    },*/
                    /*{
                        field: 'operatorTime',
                        title: '经营时间'
                    },*/
                    /*{
                        field: 'needData',
                        title: '所需材料'
                    },*/
                    {
                        field: 'createTime',
                        title: '创建时间',
                        formatter: function (data, type, row) {
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
                    },
                    {
                        title: '操作',
                        field: 'id',
                        align: 'center',
                        formatter: function (value, row, index) {
                            var e = '<a class="btn btn-primary btn-sm ' + s_edit_h + '" href="#" mce_href="#" title="编辑" onclick="edit(\''
                                + row.id
                                + '\')"><i class="fa fa-edit"></i></a> ';
                            var d = '<a class="btn btn-warning btn-sm ' + s_remove_h + '" href="#" title="删除"  mce_href="#" onclick="remove(\''
                                + row.id
                                + '\')"><i class="fa fa-remove"></i></a> ';
                            var f = '<a class="btn btn-success btn-sm" href="#" title="备用"  mce_href="#" onclick="resetPwd(\''
                                + row.id
                                + '\')"><i class="fa fa-key"></i></a> ';
                            return e + d;
                        }
                    }]
            });
}

function reLoad(type) {
    if (type == 1) {
        var opt = {
            query: {
                offset: 0
            }
        };
        $('#exampleTable').bootstrapTable('refresh', opt);
    } else {
        $('#exampleTable').bootstrapTable('refresh');
    }
}

function add() {
    layer.open({
        type: 2,
        title: '增加',
        maxmin: true,
        shadeClose: false, // 点击遮罩关闭层
        area: ['800px', '520px'],
        content: prefix + '/add' // iframe的url
    });
}

function edit(id) {
    layer.open({
        type: 2,
        title: '编辑',
        maxmin: true,
        shadeClose: false, // 点击遮罩关闭层
        area: ['800px', '520px'],
        content: prefix + '/edit/' + id // iframe的url
    });
}

function remove(id) {
    layer.confirm('确定要删除选中的记录？', {
        btn: ['确定', '取消']
    }, function () {
        $.ajax({
            url: prefix + "/remove",
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

function resetPwd(id) {
}

function batchRemove() {
    var rows = $('#exampleTable').bootstrapTable('getSelections'); // 返回所有选择的行，当没有选择的记录时，返回一个空数组
    if (rows.length == 0) {
        layer.msg("请选择要删除的数据");
        return;
    }
    layer.confirm("确认要删除选中的'" + rows.length + "'条数据吗?", {
        btn: ['确定', '取消']
        // 按钮
    }, function () {
        var ids = [];
        // 遍历所有选择的行数据，取每条数据对应的ID
        $.each(rows, function (i, row) {
            ids[i] = row['id'];
        });
        $.ajax({
            type: 'POST',
            data: {
                "ids": ids
            },
            url: prefix + '/batchRemove',
            success: function (r) {
                if (r.code == 0) {
                    layer.msg(r.msg);
                    reLoad();
                } else {
                    layer.msg(r.msg);
                }
            }
        });
    }, function () {

    });
}