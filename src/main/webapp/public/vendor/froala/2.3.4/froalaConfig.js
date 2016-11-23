angular.module('app', [ 'froala' ]).value('froalaConfig', {
	toolbarInline : false,
	codeMirror : false,
	codeViewKeepActiveButtons : [ "fullscreen" ],
	codeBeautifierOptions : {
		end_with_newline : true,
		indent_inner_html : true,
		extra_liners : "['p', 'h1', 'h2', 'h3', 'h4', 'h5', 'h6', 'blockquote', 'pre', 'ul', 'ol', 'table', 'dl']",
		brace_style : 'expand',
		indent_char : '\t',
		indent_size : 1,
		wrap_line_length : 0
	},
	codeMirrorOptions : {
		indentWithTabs : true,
		lineNumbers : true,
		lineWrapping : true,
		mode : 'text/html',
		tabMode : 'indent',
		tabSize : 2
	},
	//enter : $.FroalaEditor.ENTER_P,
	placeholderText : 'Enter Text Here',
	imageManagerDeleteMethod : 'DELETE',
	imageManagerDeleteParams : {
		items : '{{path}}'
	},
	imageManagerDeleteURL : './file/remove',
	imageManagerLoadMethod : 'POST',
	imageManagerLoadParams : {
		'path' : './images'
	},
	imageManagerLoadURL : './file/reads',
	imageManagerPageSize : 10,
	// imageManagerPreloader: './public/images/loader.gif',
	immediateAngularModelUpdate : false,
	imageManagerScrollOffset : 10,
	toolbarButtons : [ 'fullscreen', 'bold', 'italic', 'underline', 'strikeThrough', 'subscript', 'superscript', 'fontFamily', 'fontSize', '|', 'color', 'emoticons', 'inlineStyle', 'paragraphStyle', '|', 'paragraphFormat', 'align', 'formatOL', 'formatUL', 'outdent', 'indent', 'quote', 'insertHR', '-', 'insertLink', 'insertImage', 'insertVideo', 'insertFile', 'insertTable', 'undo', 'redo', 'clearFormatting', 'selectAll', 'html' ],

	inlineStyles : {
		'Big Red' : 'font-size: 20px; color: red;',
		'Small Blue' : 'font-size: 14px; color: blue;',
		'Width 50% center' : 'width:50%; margin: auto;'
	},
	tableStyles : {
		table : 'Bootstrap Table'
	},
	paragraphStyles : {
		'bg-light' : 'Light ',
		'bg-danger' : 'Danger',
		'bg-warning' : 'Warning ',
		'bg-dark'  : 'dark ',
		'bg-info'  : 'Info ',
		'bg-success'  : 'Success ',
		'bg-primary'  : 'Primary ',
		'col-lg-12' : 'col-lg-12',
		'col-lg-6' : 'col-lg-6',
		'col-lg-4' : 'col-lg-4',
		'col-lg-3' : 'col-lg-3',
		'col-lg-2-4' : 'col-lg-2-4',
		'col-lg-2' : 'col-lg-2',
		'col-xs-6' : 'col-xs-6',
		'col-sm-6'  : 'col-sm-6',
		'col-md-6' : 'col-md-6'
	},

});