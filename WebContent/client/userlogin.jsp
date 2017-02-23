<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<form action="${pageContext.request.contextPath }/client/UserServlet" method="post">
	<input type="hidden" name="method" value="userLogin">
	<table>
		<tr>
			<td>用户名:</td>
			<td><input type="text" name="Username" value="${user.username }"> </td>
		</tr>
		<tr>
			<td>密&nbsp;码</td>
			<td><input type="password" name="Password"></td>
		</tr>
		<tr>
			<td></td>
			<td align="right"><input type="submit" value="登陆"></td>
		</tr>
	</table>
	</form>
</body>
</html>