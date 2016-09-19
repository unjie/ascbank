<%@ page contentType="text/html;charset=UTF-8"%><%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%><%if (request.getHeader("accept").indexOf("application/json") > -1|| (request.getHeader("X-Requested-With") != null&& request.getHeader("X-Requested-With").indexOf("XMLHttpRequest") > -1)) {%>{success:false,message:"{default.not.permissions}"}<%	} else {%>
<html>
<head>
<% response.setStatus(403); %>
<title>403 - 用户权限不足</title>
</head>
<body>
	<h1>用户权限不足</h1>
	<p>
		<a href="<c:url value="/"/>">返回首页</a>
	</p>
</body>
</html>
<%
	}
%>