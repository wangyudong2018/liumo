var prefix = "/manager/oloan"
var type = $("#type").val();
var oloanv = type != 1;
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
                        type: type,
                        loanMemberPhone: $("#loanMemberPhone").val(),
                        city: $("#city").val(),
                        commitTime: $("#commitTime").val(),
                        column3: $("#column3").val(),
                        operateTime: $("#operateTime").val(),
                        orderStatus: $("#orderStatus").val(),
                        column1: $("#column1").val()

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
                        field: 'orderSn',
                        title: '订单编号'
                    },
                    {
                        field: 'column1',
                        title: '邀请码'
                    },
                    {
                        field: 'city',
                        title: '业务区域'
                    },
                    {
                        field: 'loanMemberPhone',
                        title: '注册手机号码'
                    },
                    {
                        field: 'createTime',
                        title: '订单提交时间',
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
                        field: 'operatorTime',
                        title: '订单处理时间',//商务受理时间
                        visible: oloanv,
                        formatter: function (data, data_type, row) {
                            var date = new Date(parseInt(data));
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
                        field: 'orderStatusDesc',
                        title: '订单状态',
                        visible: oloanv
                    },
                    {
                        field: 'operator',
                        title: '操作人员',
                        visible: oloanv
                    },

                    {
                        title: '操作',
                        field: 'id',
                        align: 'center',
                        formatter: function (value, row, index) {
                            var e = '<a class="btn btn-primary btn-sm" href="#" mce_href="#" title="处理" onclick="edit(\''
                                + row.id
                                + '\')"><i class="fa fa-edit"></i></a> ';
                            var d = '<a class="btn btn-primary btn-sm" href="#" title="查看"  mce_href="#" onclick="edit(\''
                                + row.id
                                + '\')"><i class="fa fa-calendar-o"></i></a> ';
                            if(row.orderStatus == 1){
                                return e;
                            }else{
                                return d;
                            }

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

function edit(id) {
    layer.open({
        type: 2,
        title: '处理',
        maxmin: true,
        shadeClose: false, // 点击遮罩关闭层
        area: ['800px', '520px'],
        content: prefix + '/edit/' + id // iframe的url
    });
}