<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="cn">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<jsp:include page="../debris/jspVar.jsp" />
<title>AdminLTE 2 | Data Tables</title>
<!-- Tell the browser to be responsive to screen width -->
<meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
<!-- Bootstrap 3.3.6 -->
<link rel="stylesheet" href="${__STATIC__}/bootstrap/css/bootstrap.min.css">
<!-- Font Awesome -->
<link rel="stylesheet" href="//cdn.bootcss.com/font-awesome/4.5.0/css/font-awesome.min.css">
<!-- Ionicons -->
<link rel="stylesheet" href="//cdn.bootcss.com/ionicons/2.0.1/css/ionicons.min.css">
<!-- Dojo -->
<!--   
<link href="//cdn.bootcss.com/dojo/1.11.0-rc5/resources/dojo.css" rel="stylesheet">
 <script type="text/javascript"   src="//cdn.bootcss.com/dojo/1.11.0-rc5/dojo.js" data-dojo-config="isDebug: 1, async: 1, parseOnLoad: 1"></script>
  -->
<!-- Theme style -->
<link rel="stylesheet" href="${__STATIC__}/dist/css/AdminLTE.min.css">
<!-- AdminLTE Skins. Choose a skin from the css/skins
       folder instead of downloading all of them to reduce the load. -->
<link rel="stylesheet" href="${__STATIC__}/dist/css/skins/_all-skins.min.css">
<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
  <script src="//cdn.bootcss.com/html5shiv/r29/html5.min.js"></script>
  <script src="//cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
  <![endif]-->
<!-- jQuery 2.2.3 -->
<script src="${__STATIC__}/plugins/jQuery/jquery-2.2.3.min.js"></script>
</head>
<body class="hold-transition skin-blue sidebar-mini">
	<div class="wrapper">
		<jsp:include page="header.jsp" />
		<jsp:include page="leftMenu.jsp"></jsp:include>
		<!-- Content Wrapper. Contains page content -->
		<div class="content-wrapper">
			<!-- Content Header (Page header) -->
			<section class="content-header">
				<h1>
					Data Tables <small>advanced tables</small>
				</h1>
				<ol class="breadcrumb">
					<li><a href="#"><i class="fa fa-dashboard"></i> Home</a></li>
					<li><a href="#">Tables</a></li>
					<li class="active">Data tables</li>
				</ol>
			</section>
			<!-- Main content -->
			<section class="content">
				<div class="row">
					<div class="col-xs-12">
						<div class="box">
							<div class="box-header">
								<h3 class="box-title">Data Table With Full Features</h3>
							</div>
							<!-- /.box-header -->
							<div class="box-body">
								<div id="toolbar">
									<button id="remove" class="btn btn-danger" disabled>
										<i class="glyphicon glyphicon-remove"></i> Delete
									</button>
								</div>
								<!--   data-side-pagination="server" -->
								<table id="table" data-toolbar="#toolbar" data-search="true" data-response-handler="responseHandler" data-detail-view="true" data-detail-formatter="detailFormatter" data-show-refresh="true" data-show-toggle="true" data-show-columns="true" data-show-export="true" data-minimum-count-columns="2" data-show-pagination-switch="true" data-pagination="true" data-id-field="id" data-page-list="[10, 25, 50, 100, 200, 500, 1000, ALL]" data-show-footer="false" data-method="POST" data-url="${__ROOT__}/user/reads">
								</table>
							</div>
							<script>
								var $table = $('#table'), $remove = $('#remove'), selections = [],__ROOT='${__ROOT__}';
								function initTable() {
									$table.bootstrapTable({
												height : getHeight(),
												columns : [ 
														{
															field : 'state',
															checkbox : true,
															align : 'center',
															valign : 'middle'
														},
														{
															title : 'id',
															field : 'id',
															align : 'center',
															valign : 'middle',
															sortable : true,
															footerFormatter : totalTextFormatter
														},
														{
															field : 'username',
															title : 'username',
															sortable : true,
															editable : true,
															footerFormatter : totalNameFormatter,
															align : 'center'
														},
														{
															title : 'email',
															field : 'email',
															sortable : true,
															editable : {
																type : 'text',
																title : 'Email',
																validate : function(
																		value) {
																	value = $
																			.trim(value);
																	if (!value) {
																		return 'This field is required';
																	}
																	if (!/^(\w)+(\.\w+)*@(\w)+((\.\w+)+)$/
																			.test(value)) {
																		return 'This field not email format.'
																	}
																	var data = $table
																			.bootstrapTable('getData'), index = $(
																			this)
																			.parents(
																					'tr')
																			.data(
																					'index');
																	console
																			.log(data[index]);
																	return '';
																}
															},
															align : 'center'
														},
														{
															title : 'phone',
															field : 'phone',
															sortable : true,
															editable : true,
															align : 'center'
														},
														{
															title : 'realName',
															field : 'realName',
															sortable : true,
															editable : true,
															align : 'center'
														},
														{
															title : 'wechat',
															field : 'wechat',
															sortable : true,
															editable : true,
															align : 'center'
														},
														{
															title : 'wechatName',
															field : 'wechatName',
															sortable : true,
															editable : true,
															align : 'center'
														},
														{
															field : 'operate',
															title : 'Item Operate',
															align : 'center',
															events : operateEvents,
															formatter : operateFormatter
														} ] 
											});
									// sometimes footer render error.
									setTimeout(function() {
										$table.bootstrapTable('resetView');
									}, 200);
									$table.on('check.bs.table uncheck.bs.table '+ 'check-all.bs.table uncheck-all.bs.table',
													function() {
														$remove.prop('disabled',!$table.bootstrapTable('getSelections').length);
														// save your data, here just save the current page
														selections = getIdSelections();
														// push or splice the selections if you want to save all data selections
													});
									$table.on('all.bs.table', function(e, name,	args) {
										console.log(name, args);
									});
									$remove.click(function() {
										var ids = getIdSelections();
										$table.bootstrapTable('remove', {
											field : 'id',
											values : ids
										});
										$remove.prop('disabled', true);
									});
									$(window).resize(function() {
										$table.bootstrapTable('resetView', {
											height : getHeight()
										});
									});
								}

								function getIdSelections() {
									return $.map($table.bootstrapTable('getSelections'),
											function(row) {
												return row.id
											});
								}

								function responseHandler(res) {
									$.each(res.data, function(i, row) {
										row.state = $.inArray(row.id,
												selections) !== -1;
									});
									return res;
								}

								function detailFormatter(index, row) {
									var html = [];
									html.push('<form action="javascript:alert( \'success!\' );" method="POST" id=form_"'+ index+ '" onsubmit="javascript:user_submit_update(this);">');
									$.each(row,function(key, value) {
														html.push('<span style="float:left;width:30%;margin:5px;"><b style="float:left;width:25%">'+ key+ ':</b><input type="text" name="'+ key+ '" value="'+ (value ? value: '')+ '" id="k_'+ key+ '"></span>');
													});
									html.push('<span style="float:left;;width:30%;"><input type="submit"  value="提交" ></span>');//onsubmit="javascript:user_submit_update(this);"
									html.push('</form>');
									return html.join('');
								}

								jQuery.prototype.serializeObject = function() {
									var obj = new Object();
									$.each(this.serializeArray(), function(
											index, param) {
										if (!(param.name in obj)) {
											obj[param.name] = param.value;
										}
									});
									return obj;
								};
								$.ajaxSetup({type : 'PATCH',dataType : "json",contentType:'application/json;charset=UTF-8',success : function(e) {
									alert(e.message);
								}});
								
								function user_submit_update(even) {
									var e = $(even), data = e.serializeObject();
									console.log(data);
									$.ajax({
												url : __ROOT+"/user/update/",
												data :JSON.stringify(data)
											});
								}

								window.operateEvents = {
										'click .like' : function(e, value, row,	index) {
											$.ajax({
												url : __ROOT+"/user/update/",
												data :JSON.stringify(row)
											});
											alert('You click like action, row: '	+ JSON.stringify(row));
										},
										'click .remove' : function(e, value, row, index) {
											alert('remove '+row.username+' row');
											
											$.ajax({type:'DELETE',url:__ROOT+"/user/destroy/"+row.id});
											
											$table.bootstrapTable('remove', {	field : 'id',	values : [ row.id ]});
										}
									};
								function operateFormatter(value, row, index) {
									return [
											'<a class="like" href="javascript:void(0)" title="Like">',
											'<i class="glyphicon glyphicon-heart"></i>',
											'</a>  ',
											'<a class="remove" href="javascript:void(0)" title="Remove">',
											'<i class="glyphicon glyphicon-remove"></i>',
											'</a>' ].join('');
								}

								

								function totalTextFormatter(data) {
									return 'Total';
								}

								function totalNameFormatter(data) {
									return data.length;
								}

								function totalPriceFormatter(data) {
									var total = 0;
									$.each(data, function(i, row) {
										total += +(row.price.substring(1));
									});
									return '$' + total;
								}

								function getHeight() {
									return $(window).height()- $('h1').outerHeight(true);
								}

								$(function() {
									var scripts = [
											location.search.substring(1)
													|| '//cdn.bootcss.com/bootstrap-table/1.11.0/bootstrap-table.min.js',//'assets/bootstrap-table/src/bootstrap-table.js',
											//'assets/bootstrap-table/src/extensions/export/bootstrap-table-export.js',
											'//cdn.bootcss.com/bootstrap-table/1.11.0/extensions/export/bootstrap-table-export.min.js',
											//'http://rawgit.com/hhurz/tableExport.jquery.plugin/master/tableExport.js',
											'//cdn.bootcss.com/TableExport/3.2.5/js/tableexport.min.js',
											'//cdn.bootcss.com/bootstrap-table/1.11.0/extensions/editable/bootstrap-table-editable.min.js',
											// 'http://rawgit.com/vitalets/x-editable/master/dist/bootstrap3-editable/js/bootstrap-editable.js'
											'//cdn.bootcss.com/x-editable/1.5.1/bootstrap3-editable/js/bootstrap-editable.min.js' ], eachSeries = function(
											arr, iterator, callback) {
										callback = callback || function() {
										};
										if (!arr.length) {
											return callback();
										}
										var completed = 0;
										var iterate = function() {
											iterator(
													arr[completed],
													function(err) {
														if (err) {
															callback(err);
															callback = function() {
															};
														} else {
															completed += 1;
															if (completed >= arr.length) {
																callback(null);
															} else {
																iterate();
															}
														}
													});
										};
										iterate();
									};

									eachSeries(scripts, getScript, initTable);
								});

								function getScript(url, callback) {
									var head = document
											.getElementsByTagName('head')[0];
									var script = document
											.createElement('script');
									script.src = url;

									var done = false;
									// Attach handlers for all browsers
									script.onload = script.onreadystatechange = function() {
										if (!done&& (!this.readyState	|| this.readyState == 'loaded' || this.readyState == 'complete')) {
											done = true;
											if (callback)
												callback();
											script.onload = script.onreadystatechange = null;
										}
									};

									head.appendChild(script);

									// We handle everything using the script element injection
									return undefined;
								}
							</script>
						</div>
						<!-- /.box-body -->
					</div>
					<!-- /.box -->
				</div>
				<!-- /.col -->
		</div>
		<!-- /.row -->
		</section>
		<!-- /.content -->
	</div>
	<jsp:include page="footer.jsp" />
	<jsp:include page="controlSidebar.jsp" />
	</div>
	<!-- ./wrapper -->
	<!-- Bootstrap 3.3.6 -->
	<script src="${__STATIC__}/bootstrap/js/bootstrap.min.js"></script>
	<!-- <script src="//cdn.bootcss.com/dojo/1.11.0-rc5/dojo.js"></script> -->
	<!-- DataTables -->
	<%--
<link rel="stylesheet" href="${__STATIC__}/plugins/datatables/dataTables.bootstrap.css">
 <script src="${__STATIC__}/plugins/datatables/jquery.dataTables.min.js"></script>
<script src="${__STATIC__}/plugins/datatables/dataTables.bootstrap.min.js"></script>
 --%>
	<link href="//cdn.bootcss.com/bootstrap-table/1.11.0/bootstrap-table.min.css" rel="stylesheet">
	<script src="//cdn.bootcss.com/bootstrap-table/1.11.0/bootstrap-table.min.js"></script>
	<!-- SlimScroll -->
	<script src="${__STATIC__}/plugins/slimScroll/jquery.slimscroll.min.js"></script>
	<!-- FastClick -->
	<script src="${__STATIC__}/plugins/fastclick/fastclick.js"></script>
	<!-- AdminLTE App -->
	<script src="${__STATIC__}/dist/js/app.min.js"></script>
	<!-- AdminLTE for demo purposes -->
	<script src="${__STATIC__}/dist/js/demo.js"></script>
	<!-- page script -->
</body>
</html>
