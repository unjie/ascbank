<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>

	<sf:form modelAttribute="user" class="contact" id="contact-form" name="contact-form"  action="user/login" method="post"  lang="utf-8"  >

					
					<sf:errors path="*" />
					<sf:input path="username" id="name" placeholder="Your Name" class="contact__field" />
					<sf:password path="password" id="password" placeholder="Your Password" class="contact__field " />
					<%-- 
					<span>
						<a onclick="javascript:reloadValidateCode();" style="position: absolute; float: left; margin-left: 202px" href="javascript:return false"> 
							<img height="38" style="height: 38px;margin:1px;border: none; -webkit-border-radius: 23px;  -moz-border-radius: 23px; border-radius: 23px; box-shadow: none;" id="validateCodeImg" src="${__ROOT__}captcha.jpg" /><i class=""></i>
						</a> 
						<sf:input path="captcha" placeholder="Captcha" class="contact__field " />
					</span>
					 --%>
					<div class="checkbox">  
						<input id="check1" type="checkbox" name="rememberMe"  value="check1" checked="checked">  
						<label for="check1">remember me</label>    
					</div> 
					<a class="login__callback" href="#">Forgot password?</a> 			
				    <button class="btn btn--decorated btn-warning login__btn" type="submit">Login</button>
	</sf:form>
</body>
</html>