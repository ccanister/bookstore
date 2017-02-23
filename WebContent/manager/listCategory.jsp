<%@ page language="java" contentType="text/html; charset=UTF-8"
	import="java.util.*,com.dabai.vo.*"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style type="text/css">
	table { 
		  margin:60px 0px 0px 100px;
		  border: 1px solid #98bf21; 
		  font-family: arial; 
		  font-size:16px; 
	} 
	td{border:solid #add9c0; border-width:0px 1px 1px 0px; padding:10px 0px;}
	.query {
		padding:50px 0px 0px 100px;
	}
</style>
</head>
<body>
	<div class="query">
		<form action="CategoryServlet" method="post">
			<input type="hidden" name="method" value="getCategoryList">
			查询条件：<input type="text" name="key">
			<input type="submit" value="查询">
		</form>
	</div>
	<table cellpadding="10px">
		<tr>
			<td>名称</td>
			<td>描述</td>
		</tr>
		<c:forEach items="${cats }" var="cat">
			<tr>
				<td>${cat.name }</td>
				<td>${cat.description }</td>
				<c:choose>
					<c:when test=""></c:when>
				</c:choose>
			</tr>
		</c:forEach>
	</table>
	<div style="padding-left:150px">
	当前一共有${categorypages.totalpage }页,${categorypages.totalrecord }条记录<br>
	[<a href="<%=request.getContextPath()%>/manager/CategoryServlet?method=getCategoryList&&pagenum=1">首页</a>]
	<c:forEach var="pagenum" begin="${categorypages.startpage }" end="${categorypages.endpage }">
		[<a href="<%=request.getContextPath()%>/manager/CategoryServlet?method=getCategoryList&&pagenum=${pagenum }">${pagenum }</a>]
	</c:forEach>
	[<a href="<%=request.getContextPath()%>/manager/CategoryServlet?method=getCategoryList&&pagenum=${categorypages.totalpage }">尾页</a>]
	</div>
</body>
</html>