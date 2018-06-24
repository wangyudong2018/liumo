$().ready(function() {
	validateRule();
});

$.validator.setDefaults({
	submitHandler : function() {
		save();
	}
});
function save() {
	$.ajax({
		cache : true,
		type : "POST",
		url : "/liumo/lmRelease/save",
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
			brief : {
				required : true
			},
			title : {
				required : true
			},
			content : {
				required : true
			},
			logo : {
				required : true
			},
			sort : {
				required : true,
				number : true
			}
		},
		messages : {
			brief : {
				required : icon + "请输入发布简介"
			},
			title : {
				required : icon + "请输入发布标题"
			},
			content : {
				required : icon + "请输入发布内容"
			},
			logo : {
				required : icon + "请选择Logo"
			},
			sort : {
				required : icon + "请输入排序",
				number : icon + "请输入数字"
			}
		}
	});
}