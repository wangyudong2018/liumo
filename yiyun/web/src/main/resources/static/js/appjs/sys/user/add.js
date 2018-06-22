$().ready(function() {
	validateRule();
});

$.validator.setDefaults({
	submitHandler : function() {
		save();
	}
});
function getCheckedRoles() {
	var adIds = "";
	$("input:checkbox[name=role]:checked").each(function(i) {
		if (0 == i) {
			adIds = $(this).val();
		} else {
			adIds += ("," + $(this).val());
		}
	});
	return adIds;
}
function save() {
	$("#roleIds").val(getCheckedRoles());
	$.ajax({
		cache : true,
		type : "POST",
		url : "/sys/user/save",
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
			},
			username : {
				required : true,
				minlength : 2,
				remote : {
					url : "/sys/user/exit", // 后台处理程序
					type : "post", // 数据发送方式
					dataType : "json", // 接受数据格式
					data : { // 要传递的数据
						username : function() {
							return $("#username").val();
						}
					}
				}
			},
			password : {
				required : true,
				minlength : 6
			},
			confirm_password : {
				required : true,
				minlength : 6,
				equalTo : "#password"
			},
            mobile: {
                checkPhone:true

            },
			topic : {
				required : "#newsletter:checked",
				minlength : 2
			},
			agree : "required",
            investCode:{
                digits:true,
                range:[100000,999999]
            }
		},
		messages : {
			name : {
				required : icon + "请输入姓名"
			},
			username : {
				required : icon + "请输入您的用户名",
				minlength : icon + "用户名必须两个字符以上",
				remote : icon + "用户名已经存在"
			},
			password : {
				required : icon + "请输入您的密码",
				minlength : icon + "密码必须6个字符以上"
			},
			confirm_password : {
				required : icon + "请再次输入密码",
				minlength : icon + "密码必须6个字符以上",
				equalTo : icon + "两次输入的密码不一致"
			},
			email : icon + "请输入您的E-mail",
            investCode:{
                digits : icon + "邀请码格式为6位纯数字",
                range:icon + "邀请码格式为6位纯数字"
            }
		}
	});
    jQuery.validator.addMethod("checkPhone", function(value, element) {
        var length = value.length;
        var mobile = /^(1\d{10})$/;
        var phone = /(^(\d{3,4}-)?\d{6,8}$)|(^(\d{3,4}-)?\d{6,8}(-\d{1,5})?$)|(\d{11})/;

        return this.optional(element) || (mobile.test(value) || phone.test(value));
    }, $.validator.format("请输入正确的手机号"));
}

var openDept = function(){
	layer.open({
		type:2,
		title:"选择部门",
		area : [ '300px', '450px' ],
		content:"/system/sysDept/treeView"
	})
}
function loadDept( deptId,deptName){
	$("#deptId").val(deptId);
	$("#deptName").val(deptName);
}

function createInvestCode(){
	$.get('createInvestCode' ,function (code) {
        if(code){
            $("#investCode").val(code);
            validateRule();
        }else{
            layer.alert('邀请码获取失败');
        }
    })
}