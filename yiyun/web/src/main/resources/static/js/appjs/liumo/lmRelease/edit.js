$().ready(function() {
	validateRule();
	$('#category').on('change', function() {
		if (this.value === '01') {
			$('#outChainDiv').hide();
		} else {
			$('#outChainDiv').show();
		}
	}).trigger('change');
});

$.validator.setDefaults({
	submitHandler : function() {
		edit();
	}
});
function edit() {
	$.ajax({
		cache : true,
		type : "POST",
		url : "/liumo/lmRelease/edit",
		cache : false,
		contentType : false,
		processData : false,
		data : new FormData($('#signupForm')[0]),
		async : false,
		error : function(request) {
			parent.layer.alert("Connection error");
		},
		success : function(data) {
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

}
function validateRule() {
	var icon = "<i class='fa fa-times-circle'></i> ";
	$("#signupForm").validate({
		rules : {
			title : {
				required : true
			},
			brief : {
				required : true
			},
			sort : {
				required : true,
				number : true
			}
		},
		messages : {
			title : {
				required : icon + "请输入发布标题"
			},
			brief : {
				required : icon + "请输入发布简介"
			},
			sort : {
				required : icon + "请输入排序",
				number : icon + "请输入数字"
			}
		}
	});
}