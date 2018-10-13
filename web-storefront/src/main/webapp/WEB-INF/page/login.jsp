<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Login</title>
</head>
<body>
<a href="/regist">Regist</a> &nbsp;
<c:if test="${not empty SPRING_SECURITY_LAST_EXCEPTION}">
    Login failure: ${SPRING_SECURITY_LAST_EXCEPTION.message}
</c:if>
<c:if test="${not empty FLASH_AUTHENTICATION_MESSAGE}">
    Login failure: ${FLASH_AUTHENTICATION_MESSAGE}
</c:if>
<br/>
    <form action="/login" name="login" method="post">
        Login:
        <br/>
        username: <input type="text" name="username">
        <br/>
        password: <input type="password" name="password">
        <br>
        <input type="submit" name="submit" value="submit"/>
    </form>
</body>
</html>