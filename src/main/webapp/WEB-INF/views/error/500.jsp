<%@ page contentType="text/html;charset=UTF-8" isErrorPage="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="org.slf4j.Logger,org.slf4j.LoggerFactory"%>
<%@taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%	
	//设置返回码200，避免浏览器自带的错误页面
	response.setStatus(500);
	response.addHeader("Error-Json", "{success:false,message:'" + exception.getMessage() + "',script:''}");
	//记录日志
	Logger logger = LoggerFactory.getLogger("500.jsp");
	logger.error(exception.getMessage(), exception);
	
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>

<title>500 - 系统内部错误</title>
</head>
<body>
	<h2>500 - 系统发生内部错误.</h2>
	<font color="red">${ERROR}</font>
	<% out.print(exception.getMessage()); %>
</body>
</html>
