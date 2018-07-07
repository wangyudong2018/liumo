$().ready(function() {
	validateRule();
	$('#category').on('change', function() {
		if (this.value === '01') {
			$('#outChainDiv').hide();
		} else {
			$('#outChainDiv').show();
		}
	}).trigger('change');

	var content = new Simditor({
		textarea : $('#content'),
		placeholder : '新闻内容',
		defaultImage : '/img/a1.jpg',
		params : {},
		upload : {
			url : '/liumo/lmFile/save',
			params : null,
			fileKey : 'file',
			connectionCount : 10,
			leaveConfirm : '上传正在进行中，你确定要离开这个页面吗？'
		},
		tabIndent : true,
		toolbar : [ 'title', 'bold', 'italic', 'underline', 'strikethrough', 'fontScale', 'color', 'ol', 'ul', 'blockquote', 'code', 'table', 'link', 'image', 'hr', 'indent', 'outdent', 'alignment' ],
		toolbarFloat : true,
		toolbarFloatOffset : 0,
		toolbarHidden : false,
		pasteImage : false,
		cleanPaste : false,
		imageButton : [ 'upload', 'external' ],
		allowedTags : [ 'br', 'span', 'a', 'img', 'b', 'strong', 'i', 'strike', 'u', 'font', 'p', 'ul', 'ol', 'li', 'blockquote', 'pre', 'code', 'h1', 'h2', 'h3', 'h4', 'hr' ],
		codeLanguages : [ {
			name : 'Bash',
			value : 'bash'
		}, {
			name : 'C++',
			value : 'c++'
		}, {
			name : 'C#',
			value : 'cs'
		}, {
			name : 'CSS',
			value : 'css'
		}, {
			name : 'Erlang',
			value : 'erlang'
		}, {
			name : 'Less',
			value : 'less'
		}, {
			name : 'Sass',
			value : 'sass'
		}, {
			name : 'Diff',
			value : 'diff'
		}, {
			name : 'CoffeeScript',
			value : 'coffeescript'
		}, {
			name : 'HTML,XML',
			value : 'html'
		}, {
			name : 'JSON',
			value : 'json'
		}, {
			name : 'Java',
			value : 'java'
		}, {
			name : 'JavaScript',
			value : 'js'
		}, {
			name : 'Markdown',
			value : 'markdown'
		}, {
			name : 'Objective C',
			value : 'oc'
		}, {
			name : 'PHP',
			value : 'php'
		}, {
			name : 'Perl',
			value : 'parl'
		}, {
			name : 'Python',
			value : 'python'
		}, {
			name : 'Ruby',
			value : 'ruby'
		}, {
			name : 'SQL',
			value : 'sql'
		} ]
	});
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