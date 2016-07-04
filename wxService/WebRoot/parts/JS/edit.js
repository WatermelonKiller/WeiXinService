var editor1;
KindEditor.ready(function(K) {
	 editor1 = K.create('textarea[name="content"]', {
		uploadJson : '<%=path%>/kindeditor/fileupload',
		fileManagerJson : '<%=path%>/kindeditor/filemanager',
		allowFileManager : true,
		afterBlur: function(){this.sync();},
		items:[
		        'source', '|', 'undo', 'redo', '|', 'preview', 'print', 'template', 'cut', 'copy', 'paste',
		        'plainpaste', 'wordpaste', '|', 'justifyleft', 'justifycenter', 'justifyright',
		        'justifyfull', 'insertorderedlist', 'insertunorderedlist', 'indent', 'outdent', 'subscript',
		        'superscript', 'clearhtml', 'quickformat', 'selectall', '|', 'fullscreen', '/',
		        'formatblock', 'fontname', 'fontsize', '|', 'forecolor', 'hilitecolor', 'bold',
		        'italic', 'underline', 'strikethrough', 'lineheight', 'removeformat', '|', 'image',
		         'insertfile', 'table', 'hr', 'emoticons', 'baidumap', 'pagebreak',
		        'anchor', 'link', 'unlink', '|', 'about'
		]
	});
});