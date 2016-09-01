<!-- <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%><c:set var="__CLASS__" value="article" scope="request"/><c:set var="URIBody" value="urpBody.jsp" scope="request"/> -->
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
	field: 'menuId',
	title: 'menuId',
	sortable: true,
	editable: true,
	//footerFormatter: totalNameFormatter,
	align: 'center'
}, {
	title: 'uploadtime',
	field: 'uploadtime',
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
