<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<style type="text/css">
	.center {
		padding:150px 100px 0px 300px;
	}
</style>


</head>
<body>
	<div class="center">
		<form action="<%=request.getContextPath() %>/admin/adminServlet" method="post">
		<input type="hidden" name="method" value="findLogin">
	 	用户名:<input type="text" name="Name"><br>
	 	密&nbsp;码:<input type="password" name="Password"><br>
	 	<input type="submit" value="提交">
	 	<input type="reset" value="重置">
	 	</form>
 	</div>
</body>
</html>