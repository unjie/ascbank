<!-- <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%><c:set var="__CLASS__" value="menu" scope="request"/><c:set var="URIBody" value="urpBody.jsp" scope="request"/> -->
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
					field: 'parentId',
					title: 'parentId',
					sortable: true,
					editable: true,
					//footerFormatter: totalNameFormatter,
					align: 'center'
				}, {
					title: 'alias',
					field: 'alias',
					editable: true,
					sortable: true,
					align: 'center',
		
				}, {
					title: 'title',
					field: 'title',
					editable: true,
					sortable: true,
					align: 'center',
		
				}, {
					title: 'edittime',
					field: 'edittime',
					editable: true,
					sortable: true,
					align: 'center',
		
				}, {
					title: 'author',
					field: 'author',
					editable: true,
					sortable: true,
					align: 'center',
		
				}, {
					title: 'description',
					field: 'description',
					editable: true,
					sortable: true,
					align: 'center',
		
				}, {
					title: 'isNavigation',
					field: 'isNavigation',
					editable: true,
					sortable: true,
					align: 'center',
		
				}, {
					title: 'isPublish',
					field: 'isPublish',
					editable: true,
					sortable: true,
					align: 'center',
		
				}, {
					title: 'keyword',
					field: 'keyword',
					editable: true,
					sortable: true,
					align: 'center',
		
				}, {
					title: 'stem',
					field: 'stem',
					editable: true,
					sortable: true,
					align: 'center',
		
				}, {
					title: 'style',
					field: 'style',
					editable: true,
					sortable: true,
					align: 'center',
		
				}, {
					title: 'thumb',
					field: 'thumb',
					editable: true,
					sortable: true,
					align: 'center',
		
				}, {
					title: 'url',
					field: 'url',
					editable: true,
					sortable: true,
					align: 'center',
		
				}, {
					title: 'clicks',
					field: 'clicks',
					editable: true,
					sortable: true,
					align: 'center',
		
				}, {
					title: 'sort',
					field: 'sort',
					editable: true,
					sortable: true,
					align: 'center',
		
				},{
					field: 'operate',
					title: 'Item Operate',
					align: 'center',
					events: operateEvents,
					formatter: operateFormatter
				}];
</script>
<jsp:include page="../debris/Foot.jsp"/>
