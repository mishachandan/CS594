<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
     <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Home</title>
</head>
<body>
<form action="homeservlet" method="post">
<table>
<tr><td>Enter Project</td><td><input type="text" name="project"> </td></tr>
<tr>
<td>Enter webservice</td><td><input type="text" name="wbservice"></td>
</tr>
<tr><td colspan="2"><input type="submit"></td></tr>
</table>
</form>
</body>
</html>