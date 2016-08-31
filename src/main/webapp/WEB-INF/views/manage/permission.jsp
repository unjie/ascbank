<!--<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> <c:set var="__CLASS__" value="permission" scope="request"/> <c:set var="URIBody" value="urpBody.jsp" scope="request"/>-->
<jsp:include page="../debris/Head.jsp"/>
<jsp:include page="Body.jsp"/>
<jsp:include page="urpJS.jsp"/>
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
					field: 'name',
					title: 'name',
					sortable: true,
					editable: true,
					footerFormatter: totalNameFormatter,
					align: 'center'
				}, {
					title: 'permission',
					field: 'permission',
					sortable: true,
					editable: true,
					align: 'center'
				}, {
					title: 'entityIds',
					field: 'entityIds',
					sortable: true,
					editable: true,
					align: 'center'
				}, {
					title: 'description',
					field: 'description',
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
<jsp:include page="../debris/Foot.jsp"/>