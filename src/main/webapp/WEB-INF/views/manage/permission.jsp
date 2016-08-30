<!--<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> <c:set var="__CLASS__" value="permission" scope="request"/> -->
<jsp:include page="Head.jsp"/>
<jsp:include page="urpBody.jsp"/>
<script>
columns = [{
					field: 'state',
					checkbox: true,
					align: 'center',
					valign: 'middle'
				}, {
					title: 'id',
					field: 'id',
					align: 'center',
					valign: 'middle',
					sortable: true,
					footerFormatter: totalTextFormatter
				}, {
					field: 'username',
					title: 'username',
					sortable: true,
					editable: true,
					footerFormatter: totalNameFormatter,
					align: 'center'
				}, {
					title: 'email',
					field: 'email',
					sortable: true,
					align: 'center',
					editable: {
						type: 'text',
						title: 'Email',
						validate: function(value) {
							value = $.trim(value);
							if(!value) {
								return 'This field is required';
							}
							if(!/^(\w)+(\.\w+)*@(\w)+((\.\w+)+)$/
								.test(value)) {
								return 'This field not email format.'
							}
							var data = $table.bootstrapTable('getData'),
								index = $(this).parents('tr').data('index');
							console.log(data[index]);
							return '';
						}
					}
				}, {
					title: 'phone',
					field: 'phone',
					sortable: true,
					editable: true,
					align: 'center'
				}, {
					title: 'realName',
					field: 'realName',
					sortable: true,
					editable: true,
					align: 'center'
				}, {
					title: 'wechat',
					field: 'wechat',
					sortable: true,
					editable: true,
					align: 'center'
				}, {
					title: 'wechatName',
					field: 'wechatName',
					sortable: true,
					editable: true,
					align: 'center'
				}, {
					field: 'operate',
					title: 'Item Operate',
					align: 'center',
					events: operateEvents,
					formatter: operateFormatter
				}];
</script>
<jsp:include page="urpJS.jsp"/>
<jsp:include page="Foot.jsp"/>