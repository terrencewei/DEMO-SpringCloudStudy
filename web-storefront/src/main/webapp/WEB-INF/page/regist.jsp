<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Regist</title>
</head>
<body>
<a href="/login">SignIn</a> &nbsp;<br/>
${errorMessage}
<br/>
    <form action="/doRegist" name="regist" method="post">
        Regist:
        <br/>
        username: <input type="text" name="username">
        <br/>
        password: <input type="password" name="password">
        <br>
        <input type="submit" value="submit"/>
    </form>
</body>
</html>