var prefix = "/liumo/lmOrder"
$(function() {
	load();
});

function load() {
	$('#exampleTable').bootstrapTable({
		method : 'get', // 服务器数据的请求方式 get or post
		url : prefix + "/list", // 服务器数据的加载地址
		// showRefresh : true,
		// showToggle : true,
		// showColumns : true,
		iconSize : 'outline',
		toolbar : '#exampleToolbar',
		striped : true, // 设置为true会有隔行变色效果
		dataType : "json", // 服务器返回的数据类型
		pagination : true, // 设置为true会在底部显示分页条
		// queryParamsType : "limit",
		// //设置为limit则会发送符合RESTFull格式的参数
		singleSelect : false, // 设置为true将禁止多选
		// contentType : "application/x-www-form-urlencoded",
		// //发送到服务器的数据编码类型
		pageSize : 10, // 如果设置了分页，每页数据条数
		pageNumber : 1, // 如果设置了分布，首页页码
		// search : true, // 是否显示搜索框
		showColumns : false, // 是否显示内容下拉框（选择显示的列）
		sidePagination : "server", // 设置在哪里进行分页，可选值为"client" 或者 "server"
		queryParams : function(params) {
			return {
				// 说明：传入后台的参数包括offset开始索引，limit步长，sort排序列，order：desc或者,以及所有列的键值对
				limit : params.limit,
				offset : params.offset,
				state : $('#state').val(),
				orderType : $('#orderType').val(),
				username : $('#username').val(),
				mobile : $('#mobile').val(),
				id : $('#id').val()
			};
		},
		// //请求服务器数据时，你可以通过重写参数的方式添加一些额外的参数，例如 toolbar 中的参数 如果
		// queryParamsType = 'limit' ,返回参数必须包含
		// limit, offset, search, sort, order 否则, 需要包含:
		// pageSize, pageNumber, searchText, sortName,
		// sortOrder.
		// 返回false将会终止请求
		columns : [ {
			checkbox : true
		}, {
			field : 'id',
			title : '订单编号'
		}, {
			field : 'mobile',
			title : '手机号'
		}, {
			field : 'username',
			title : '借款人姓名'
		}, {
			field : 'certType',
			title : '证件类型',
			formatter : function(value, row, index) {
				if (value === '01') {
					return '身份证';
				}
				return value;
			}
		}, {
			field : 'certNo',
			title : '证件号码'
		}, {
			field : 'orderType',
			title : '订单类型',
			formatter : function(value, row, index) {
				if (value === 'A') {
					return '银行抵押贷款';
				}
				if (value === 'B') {
					return '银行信用贷款';
				}
				if (value === 'C') {
					return '银行按揭贷款';
				}
				return value;
			}
		}, {
			field : 'orderAmt',
			title : '订单金额'
		}, {
			field : 'state',
			title : '状态',
			formatter : function(value, row, index) {
				if (value === '01') {
					return '在线咨询';
				}
				if (value === '02') {
					return '门店申请';
				}
				if (value === '03') {
					return '匹配最优银行';
				}
				if (value === '04') {
					return '银行审批放款';
				}
				if (value === '05') {
					return '已放款';
				}
				if (value === '06') {
					return '失败';
				}
				return value;
			}
		}, {
			field : 'remark',
			title : '备注'
		}, {
			field : 'orderDate01',
			title : '在线咨询时间'
		}, {
			field : 'orderDate02',
			title : '门店申请时间'
		}, {
			field : 'orderDate03',
			title : '匹配最优银行时间'
		}, {
			field : 'orderDate04',
			title : '银行审批放款时间'
		}, {
			field : 'orderDate05',
			title : '已放款时间'
		}, {
			field : 'orderDate06',
			title : '失败时间'
		}, {
			title : '操作',
			field : 'id',
			align : 'center',
			formatter : function(value, row, index) {
				var e = '<a class="btn btn-primary btn-sm ' + s_edit_h + '" href="#" mce_href="#" title="编辑" onclick="edit(\'' + row.id + '\')"><i class="fa fa-edit"></i></a> ';
				return e;
			}
		} ]
	});
}
function reLoad(type) {
	if (type == 1) {
		var opt = {
			query : {
				offset : 0
			}
		};
		$('#exampleTable').bootstrapTable('refresh', opt);
	} else {
		$('#exampleTable').bootstrapTable('refresh');
	}
}
function add() {
	layer.open({
		type : 2,
		title : '增加',
		maxmin : true,
		shadeClose : false, // 点击遮罩关闭层
		area : [ '800px', '520px' ],
		content : prefix + '/add' // iframe的url
	});
}
function edit(id) {
	layer.open({
		type : 2,
		title : '编辑',
		maxmin : true,
		shadeClose : false, // 点击遮罩关闭层
		area : [ '800px', '520px' ],
		content : prefix + '/edit/' + id // iframe的url
	});
}
function remove(id) {
	layer.confirm('确定要删除选中的记录？', {
		btn : [ '确定', '取消' ]
	}, function() {
		$.ajax({
			url : prefix + "/remove",
			type : "post",
			data : {
				'id' : id
			},
			success : function(r) {
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
		btn : [ '确定', '取消' ]
	// 按钮
	}, function() {
		var ids = new Array();
		// 遍历所有选择的行数据，取每条数据对应的ID
		$.each(rows, function(i, row) {
			ids[i] = row['id'];
		});
		$.ajax({
			type : 'POST',
			data : {
				"ids" : ids
			},
			url : prefix + '/batchRemove',
			success : function(r) {
				if (r.code == 0) {
					layer.msg(r.msg);
					reLoad();
				} else {
					layer.msg(r.msg);
				}
			}
		});
	}, function() {

	});
}