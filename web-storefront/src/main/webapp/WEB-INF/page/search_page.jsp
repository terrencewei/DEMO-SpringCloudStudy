<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Product Search</title>
</head>
<body>
<%@ include file="./header.jsp" %>
<br/>
Product Search: <br/>
<form action="search" method="get">
    id:<input type="text" name="productId"/> <br/>
    name:<input type="text" name="name"/> <br/>
    <input type="submit" value="search"/>
</form>
</body>
</html>