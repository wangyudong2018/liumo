$().ready(function() {
	validateRule();
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
		url : "/liumo/lmProduct/edit",
		data : $('#signupForm').serialize(),// 你的formid
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
			content : {
				required : true
			},
			agreemt : {
				required : true
			},
			people : {
				required : true
			},
			sort : {
				required : true,
				number : true
			}
		},
		messages : {
			title : {
				required : icon + "请输入产品名称"
			},
			content : {
				required : icon + "请输入产品介绍"
			},
			agreemt : {
				required : icon + "请输入居间服务和费率"
			},
			people : {
				required : icon + "请输入适合人群"
			},
			sort : {
				required : icon + "请输入排序",
				number : icon + "请输入数字"
			}
		}
	});
}