<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style type="text/css">
	textarea {
		width:200px;
		height:100px;
	}
	
	table {
		padding-left:60px;
		padding-top:60px;
	}
</style>
<script type="text/javascript">

</script>
</head>
<body>
	<form action="CategoryServlet" method="Post"> 
		<input type="hidden" name="method" value="addCategory">
		<table>
		<tr><td>名称：</td><td><input type="text" name="Name" style="width:200px;"></td></tr>
		<tr><td>描述：</td><td><textarea name="Description"></textarea></td></tr>
		<tr><td>是否在首页</td><td><input type="checkbox" name="iscommond" value="check"></td></tr>
		<tr><td><input type="submit" value="提交"></td>
			<td align="right"><input type="reset" value="重置"></td></tr>
		</table>
	</form>
</body>
</html>