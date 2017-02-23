<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="com.dabai.factory.CategoryFactory,com.dabai.vo.category"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>

	<form action="<%=request.getContextPath()%>/manager/BookServlet" method="post">
		<input type="hidden" name="method" value="getBookList">
		关键字查询：<input type="text" name="keyWord">
		<input type="submit" value="查询">
	</form>
	<table border="2">
		<tr>
			<td>书名</td>
			<td>作者</td>
			<td>价格</td>
			<td>描述</td>
			<td>类别</td>
			<td colspan="2" align="center">操作</td>
		</tr>
		<c:forEach items="${bks }" var="bk">
		<c:set var="id" value="${bk.category_id }" scope="request"/>
		
		<%
			int id = Integer.parseInt(request.getAttribute("id").toString());
			category cat = CategoryFactory.getInstance().findById(id);
			request.setAttribute("categoryName",cat.getName());
		%>
			<tr>
				<td>${bk.name }</td>
				<td>${bk.author }</td>
				<td>${bk.price }</td>
				<td>${bk.description }</td>
				<td>${categoryName }</td>
				<td><a href="<%=request.getContextPath()%>/manager/BookServlet?method=detBook&&bookId=${bk.id}">删除</a></td>
				<td><a href="<%=request.getContextPath()%>/manager/BookServlet?method=modBook&&bookId=${bk.id}">修改</a></td>
			</tr>
		</c:forEach>
	</table>
	当前一共有${bookpages.totalpage }页,${bookpages.totalrecord }条记录<br>
	[<a href="<%=request.getContextPath()%>/manager/BookServlet?method=getBookList&&pagenum=1">首页</a>]
	<c:forEach var="pagenum" begin="${bookpages.startpage }" end="${bookpages.endpage }">
		[<a href="<%=request.getContextPath()%>/manager/BookServlet?method=getBookList&&pagenum=${pagenum }">${pagenum }</a>]
	</c:forEach>
	[<a href="<%=request.getContextPath()%>/manager/BookServlet?method=getBookList&&pagenum=${bookpages.totalpage }">尾页</a>]
</body>
</html>