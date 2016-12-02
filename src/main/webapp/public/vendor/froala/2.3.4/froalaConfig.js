angular.module('app', [ 'froala' ]).value('froalaConfig', {
	// 配置信息
	language:'zh_cn',
	//回车 以新建div
	//a.FE.ENTER_P = 0,
   // a.FE.ENTER_DIV = 1,
   // a.FE.ENTER_BR = 2,
	 enter :  1,
	 editorClass: 'panel',
	//初始
	placeholderText : 'Enter Text Here',
	
	imageUploadMethod: 'POST',
	imageUploadURL:'./file/upload',
	imageUploadParams:{destination:'./images'	},
	imageUploadParam:'files[0]',
	
	fileUploadURL:'./file/upload',
	fileUploadParams:{destination:('./'+new Date().toDateString())	},
	fileUploadParam:'files[0]',
	
	fileManagerLoadMethod : 'POST',
	fileManagerLoadParams : {'path' : './'},
	fileManagerLoadURL : './file/reads',
	
	
	imageManagerDeleteMethod : 'DELETE',
	//imageManagerDeleteParams : {		items : '{{path}}'	},
	imageManagerDeleteURL : './file/remove',
	imageManagerLoadMethod : 'POST',
	imageManagerLoadParams : {'path' : './images'},
	imageManagerLoadURL : './file/reads',
	imageManagerPageSize : 10,
	// imageManagerPreloader: './public/images/loader.gif',
	immediateAngularModelUpdate : false,
	imageManagerScrollOffset : 10,

	inlineStyles : {
		'Big Red' : 'font-size: 20px; color: red;',
		'Small Blue' : 'font-size: 14px; color: blue;',
		'Width 50% center' : 'width:50%; margin: auto;'
	},
	tableStyles : {
		'table' : 'Bootstrap Table',
		'table-condensed': 'Bootstap condensed-table',
		'table-bordered' : 'Bootstap bordered-table',
		'table-striped' : 'Bootstap zebra-striped',
		'table-responsive' : 'table-responsive',
		
	},
	paragraphStyles : {
		'label' : 'Label',
		'bg-light' : 'Light ',
		'bg-danger' : 'Danger',
		'bg-warning' : 'Warning ',
		'bg-dark' : 'dark ',
		'bg-info' : 'Info ',
		'bg-success' : 'Success ',
		'bg-primary' : 'Primary ',
		'well' : 'Well',
		'col-lg-12' : '(1)col-lg-12',
		'col-lg-10' : '(10/12)col-lg-10',
		'col-lg-8' : '(8/12)col-lg-8',
		'col-lg-6' : '(1/2)col-lg-6',
		'col-lg-4' : '(1/3)col-lg-4',
		'col-lg-3' : '(1/4)col-lg-3',
		'col-lg-2-4' : '(1/5)col-lg-2-4',
		'col-lg-2' : '(1/6)col-lg-2',
		'col-xs-6' : '(1/2)col-xs-6',
		'col-sm-6' : '(1/2)col-sm-6',
		'col-md-6' : '(1/2)col-md-6',
	},
	quickInsertButtons:['image', 'table', 'ul', 'ol', 'hr'],
	quickInsertTags:['p', 'div', 'h1', 'h2', 'h3', 'h4', 'h5', 'h6', 'pre', 'blockquote']

});