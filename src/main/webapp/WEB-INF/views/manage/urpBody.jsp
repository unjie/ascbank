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
									<button id="add-plus" class="btn btn-default">
										<i class="glyphicon glyphicon-plus"></i>Add
									</button>
								</div>
								<!--   data-side-pagination="server" -->
								<table id="table" data-toolbar="#toolbar" data-search="true" data-response-handler="responseHandler" data-detail-view="true" data-detail-formatter="detailFormatter" data-show-refresh="true" data-show-toggle="true" data-show-columns="true" data-show-export="true" data-minimum-count-columns="1" data-show-pagination-switch="true" data-pagination="true" data-id-field="id" data-page-list="[10, 25, 50, 100, 200, 500, 1000, ALL]" data-show-footer="false" data-method="get" data-url="${__ROOT__}/${__CLASS__}/reads">
								</table>
							</div>
							<!-- /.box-body -->
							<!-- Modal -->
							<div class="modal fade modal-warning" id="warningModal">
								<div class="modal-dialog">
									<div class="modal-content">
										<div class="modal-header">
											<button type="button" class="close" data-dismiss="modal" aria-label="Close">
												<span aria-hidden="true">&times;</span>
											</button>
											<h4 class="modal-title">Warning Modal</h4>
										</div>
										<div class="modal-body">
											<p class="infotext">.....</p>
										</div>
										<div class="modal-footer">
											<button type="button" class="btn btn-outline pull-left" data-dismiss="modal">Close</button>
											<button type="button" class="btn btn-outline subimt">Yse,I'm sure</button>
										</div>
									</div>
								</div>
							</div>
							<!-- Button trigger modal -->
						</div>
						<!-- /.box -->
					</div>
					<!-- /.col -->
				</div>
				<!-- /.row -->
			</section>
			<!-- /.content -->