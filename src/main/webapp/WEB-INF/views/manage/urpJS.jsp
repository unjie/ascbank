<script>
window.operateEvents = {
	'click .like': function(e, value, row, index) {
		user_submit(row, function(e) {
			if(e.success) {
				row.id = e.id;
			}
			alert(e.message);
		});
	},
	'click .remove': function(e, value, row, index) {
		var wm = $('#warningModal');
		wm.find(".infotext").html("Warning! you want to delete the (" + row.username + ") users? ");
		wm.modal('show');
		wm.find("button.subimt").on('click', function() {
			$.ajax({
				type: 'DELETE',
				url: url_prefix + "destroy/" + row.id,
				success: function() {
					if(e.success)
						$table.bootstrapTable('remove', {
							field: 'id',
							values: [row.id]
						});
					$(this).off('click');
					wm.modal('hide');
				}
			});
		});
	}
};
jQuery.prototype.serializeObject = function() {
	var obj = new Object();
	$.each(this.serializeArray(), function(
		index, param) {
		if(!(param.name in obj)) {
			obj[param.name] = param.value;
		}
	});
	return obj;
};
$.ajaxSetup({
	dataType: "json",
	contentType: 'application/json;charset=UTF-8',
	success: function(e) {
		alert(e.message);
	}
});

var $table = $('#table'),
	$remove = $('#remove'),
	$addplus = $('#add-plus'),
	selections = [],
	__ROOT = '${__ROOT__}',
	__CLASS = '${__CLASS__}',
	url_prefix = __ROOT + "/" + __CLASS + "/";
	

function initTable() {
	$table.bootstrapTable({
		'height': getHeight(),
		'columns': columns
	});
	// sometimes footer render error.
	setTimeout(function() {
		$table.bootstrapTable('resetView');
	}, 200);
	$table.on('check.bs.table uncheck.bs.table ' + 'check-all.bs.table uncheck-all.bs.table',
		function() {
			$remove.prop('disabled', !$table.bootstrapTable('getSelections').length);
			// save your data, here just save the current page
			selections = getIdSelections();
			// push or splice the selections if you want to save all data selections
		});
	$table.on('all.bs.table', function(e, name, args) {
		console.log(name, args);
	});
	$remove.click(function() {
		var ids = getIdSelections();
		$table.bootstrapTable('remove', {
			field: 'id',
			values: ids
		});
		$remove.prop('disabled', true);
	});
	$addplus.click(function() {
		var data = $.map($table.bootstrapTable('getSelections'),
			function(row) {
				var r = $.extend(true, {}, row);
				r.id = null;
				r.username = null;
				r.phone = null;
				r.email = null;
				return r;
			});
		$table.bootstrapTable('prepend', data);
	});

	$(window).resize(function() {
		$table.bootstrapTable('resetView', {
			height: getHeight()
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
	html.push('<form action="javascript:alert( \'success!\' );" method="POST" id=form_"' + index + '" onsubmit="javascript:user_submit_update(this);">');
	$.each(row, function(key, value) {
		html.push('<span style="float:left;width:30%;margin:5px;"><b style="float:left;width:25%">' + key + ':</b><input type="text" name="' + key + '" value="' + (value ? value : '') + '" id="k_' + key + '"></span>');
	});
	html.push('<span style="float:left;;width:30%;"><input type="submit"  value="提交" ></span>'); //onsubmit="javascript:user_submit_update(this);"
	html.push('</form>');

	return html.join('');
}



function user_submit_update(even) {
	var e = $(even),
		data = e.serializeObject();
		console.log(data);
		user_submit(data, func);
}
//updata or add
function user_submit(data, func) {
	$.ajax({
		'type': (!data.id) ? 'PUT' : 'PATCH',
		'url': url_prefix + ((!data.id) ? "create" : "update"),
		'data': JSON.stringify(data),
		'success': func
	});
}

function operateFormatter(value, row, index) {
	return [
		'<a class="like" href="javascript:void(0)" title=" add or update">',
		'<i class="glyphicon glyphicon-heart"></i>',
		'</a>  ',
		'<a class="remove" href="javascript:void(0)" title="Remove">',
		'<i class="glyphicon glyphicon-remove"></i>',
		'</a>'
	].join('');
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
	return $(window).height() - $('h1').outerHeight(true);
}

$(function() {
	var scripts = [
			location.search.substring(1) ||
			'//cdn.bootcss.com/bootstrap-table/1.11.0/bootstrap-table.min.js', //'assets/bootstrap-table/src/bootstrap-table.js',
			//'assets/bootstrap-table/src/extensions/export/bootstrap-table-export.js',
			'//cdn.bootcss.com/bootstrap-table/1.11.0/extensions/export/bootstrap-table-export.min.js',
			//'http://rawgit.com/hhurz/tableExport.jquery.plugin/master/tableExport.js',
			'//cdn.bootcss.com/TableExport/3.2.5/js/tableexport.min.js',
			'//cdn.bootcss.com/bootstrap-table/1.11.0/extensions/editable/bootstrap-table-editable.min.js',
			// 'http://rawgit.com/vitalets/x-editable/master/dist/bootstrap3-editable/js/bootstrap-editable.js'
			'//cdn.bootcss.com/x-editable/1.5.1/bootstrap3-editable/js/bootstrap-editable.min.js'
		],
		eachSeries = function(
			arr, iterator, callback) {
			callback = callback || function() {};
			if(!arr.length) {
				return callback();
			}
			var completed = 0;
			var iterate = function() {
				iterator(arr[completed],function(err) {
						if(err) {
							callback(err);
							callback = function() {};
						} else {
							completed += 1;
							if(completed >= arr.length) {
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
	var head = document.getElementsByTagName('head')[0];
	var script = document.createElement('script');
	script.src = url;
	var done = false;
	// Attach handlers for all browsers
	script.onload = script.onreadystatechange = function() {
		if(!done && (!this.readyState || this.readyState == 'loaded' || this.readyState == 'complete')) {
			done = true;
			if(callback)
				callback();
			script.onload = script.onreadystatechange = null;
		}
	};
	head.appendChild(script);
	// We handle everything using the script element injection
	return undefined;
}
</script>
<link href="//cdn.bootcss.com/bootstrap-table/1.11.0/bootstrap-table.min.css" rel="stylesheet">