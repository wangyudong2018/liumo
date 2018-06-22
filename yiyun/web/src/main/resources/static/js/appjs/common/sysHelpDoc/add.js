$().ready(function() {
	validateRule();
});

$.validator.setDefaults({
    submitHandler: function () {
        var h_type = $("#type").val();
        var h_description = $("#description").val();
        var h_title = $("#title").val();
        var h_body = $("#body").val();
        if (h_type == ""||h_description == ""||h_title == ""&&h_body == "") {
            layer.msg("类型与标题内容不允许为空");
            return;
        } else {
            save();
        }
    }
});
function save() {
	$.ajax({
		cache : true,
		type : "POST",
		url : "/common/sysHelpDoc/save",
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
			name : {
				required : true
			}
		},
		messages : {
			name : {
				required : icon + "请输入姓名"
			}
		}
	})
}