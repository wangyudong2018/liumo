var prefix = "/query/member";
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
                        type: $('#type').val(),
                        id: $("#id").val(),
                        name:$("#name").val(),
                        idcard: $("#idcard").val(),
                        phone: $('#phone').val(),
                        source : $("#source").val(),
                        searchTime: $("#searchTime").val()
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
                        field:'id',
                        title: '客户编号'
                    },
                    {
                        field: 'name',
                        title: '客户名称'
                    },
                    {
                        field: 'idcard',
                        title: '客户身份证号'
                    },
                    {
                        field: 'phone',
                        title: '手机号码'
                    },
                    {
                        field: 'source',
                        title: '获客方式'
                    },
                    {
                        field: 'createTime',
                        title: '创建时间',
                        formatter: function (data, data_type, row) {
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
                            var e = '<a class="btn btn-primary btn-sm" href="#" mce_href="#" title="基本信息" onclick="showBasic(\''
                                + row.id
                                + '\')"><i class="fa fa-search"></i></a> ';
                            var d = '<a class="btn btn-success btn-sm" href="/query/loan?memberId=' + row.id + '&mtype=2" title="借款记录"  mce_href="#"><i class="fa fa-bars"></i></a> ';
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
function showBasic(id) {
    layer.open({
        type: 2,
        title: '基本信息',
        maxmin: true,
        shadeClose: false, // 点击遮罩关闭层
        area: ['800px', '520px'],
        content: prefix + '/showBasic/' + id + '?type=' + $("#type").val() // iframe的url
    });
}

function show(id) {
    layer.open({
        type: 2,
        title: '基本信息',
        maxmin: true,
        shadeClose: false, // 点击遮罩关闭层
        area: ['800px', '520px'],
        content: prefix + '/showOrder/' + id // iframe的url
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