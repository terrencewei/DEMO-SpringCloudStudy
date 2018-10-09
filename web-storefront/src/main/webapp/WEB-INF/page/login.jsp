<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Login</title>
</head>
<body>
<a href="/regist">Regist</a> &nbsp;
${errorMessage}
<br/>
    <form action="/doLogin" name="login">
        Login:
        <br/>
        username: <input type="text" name="username">
        <br/>
        password: <input type="password" name="password">
        <br>
        <input type="submit" name="submit"/>
    </form>
</body>
</html>