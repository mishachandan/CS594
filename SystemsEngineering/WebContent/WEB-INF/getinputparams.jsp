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
<form action="getinputs" method="post">
		<table>
		<tr><td>Number of Times</td><td><input type="text" name="times"> </td> </tr>
		<c:forEach items="${allinputs}" var="input">
			<tr>
				<td>${input.name}</td><td><input type="text" name="${input.name}"></td>
			</tr>
			</c:forEach>
			<tr><td colspan="2"><input type="submit"></td></tr>
		</table>
	</form>
</body>
</html>