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
		url : "/liumo/lmRecruit/edit",
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
			jobTitle : {
				required : true
			},
			jobNature : {
				required : true
			},
			salaryRange : {
				required : true
			},
			hiring : {
				required : true
			},
			jobDuty : {
				required : true
			},
			jobQuality : {
				required : true
			},
			sort : {
				required : true,
				number : true
			}
		},
		messages : {
			jobTitle : {
				required : icon + "请输入职位名称"
			},
			jobNature : {
				required : icon + "请输入工作性质"
			},
			salaryRange : {
				required : icon + "请输入薪资范围"
			},
			hiring : {
				required : icon + "请输入招聘人数"
			},
			jobDuty : {
				required : icon + "请输入工作职责"
			},
			jobQuality : {
				required : icon + "请输入任职资格"
			},
			sort : {
				required : icon + "请输入排序",
				number : icon + "请输入数字"
			}
		}
	});
}