<!-- <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%><c:set var="__CLASS__" value="user" scope="request"/><c:set var="URIBody" value="urpBody.jsp" scope="request"/> -->
<jsp:include page="../debris/Head.jsp"/>
<jsp:include page="Body.jsp"/>
<jsp:include page="urpJS.jsp"/>

<span ng-app="">
	<b>{{__CLASS__}}</b>
</span>

<script   >
<jsp:include page="user.JS.jsp"/>
</script>
<jsp:include page="../debris/Foot.jsp"/>
