var prefix = "/liumo/lmUserHis"
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
				username : $('#username').val(),
				hisType : $('#hisType').val(),
				certSign : $('#certSign').val(),
				mobile : $('#mobile').val(),
				sort : 'last_update_time',
				order : 'desc'
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
			field : 'hisType',
			title : '变更类型',
			formatter : function(value, row, index) {
				if (value === '00') {
					return '用户注册';
				}
				if (value === '01') {
					return '实名认证';
				}
				if (value === '02') {
					return '手机号';
				}
				if (value === '03') {
					return '用户密码';
				}
				return value;
			}
		}, {
			field : 'mobile',
			title : '手机号'
		}, {
			field : 'username',
			title : '姓名'
		}, {
			field : 'certType',
			title : '证件类型',
			formatter : function(value, row, index) {
				return value === '01' ? '身份证' : '其他';
			}
		}, {
			field : 'certNo',
			title : '证件号码'
		}, {
			field : 'certSign',
			title : '实名认证标志',
			formatter : function(value, row, index) {
				return value === '0' ? '否' : '是';
			}
		}, {
			field : 'certRemark',
			title : '实名认证审核评语'
		}, {
			field : 'certPositive',
			title : '证件正面',
			formatter : function(value, row, index) {
				return '<a href="#" onclick="img(\'' + value + '\');">查看图片</a>';
			}
		}, {
			field : 'certReverse',
			title : '证件反面',
			formatter : function(value, row, index) {
				return '<a href="#" onclick="img(\'' + value + '\');">查看图片</a>';
			}
		}, {
			field : 'certHand',
			title : '手持证件照',
			formatter : function(value, row, index) {
				return '<a href="#" onclick="img(\'' + value + '\');">查看图片</a>';
			}
		}, {
			field : 'certDate',
			title : '实名认证通过时间'
		}, {
			field : 'createTime',
			title : '创建时间'
		}, {
			field : 'usrUpdateTime',
			title : '用户更新时间'
		}, {
			field : 'oprUpdateTime',
			title : '操作员更新时间'
		}, {
			field : 'lastUpdateTime',
			title : '最后更新时间'
		} ]
	});
}
function img(id) {
	layer.open({
		type : 2,
		title : '展示',
		maxmin : true,
		shadeClose : false, // 点击遮罩关闭层
		area : [ '800px', '520px' ],
		content : prefix + '/img?id=' + id // iframe的url
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