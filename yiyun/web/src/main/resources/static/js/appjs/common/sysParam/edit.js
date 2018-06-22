$().ready(function() {
	validateRule();
});

$.validator.setDefaults({
	submitHandler : function() {
		update();
	}
});
function update() {
	$.ajax({
		cache : true,
		type : "POST",
		url : "/common/sysParam/update",
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
            type : {
                required : true
            },
        	name : {
                required : true
            },
            code : {
                required : true
            },
            value : {
                required : true
            },
            sort : {
                number : true
            }

        },
        messages : {
            name : {
                required : icon + "请输入参数名"
            },
            type : {
                required : icon + "请输入类型代码"
            },
            code : {
                required : icon + "请输入参数代码"
            },
            value : {
                required : icon + "请输入参数值"
            },
            sort : {
                number : icon + "必须是数字"
            }
        }
	})
}