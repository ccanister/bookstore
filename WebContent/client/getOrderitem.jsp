<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"  import="com.dabai.vo.*,com.dabai.factory.BookFactory,java.util.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<table border="1">
		<tr>
			<td>书名</td>
			<td>图片</td>
			<td>数量</td>
			<td>总价</td>
		</tr>
		<c:forEach items="${orderitems }" var="orderitem">
		<c:set var="orderitem" value="${orderitem }" scope="request"/>
		<%
			orderItem orderitem = (orderItem)request.getAttribute("orderitem");
			int bookId = orderitem.getBookId();
			book bk = BookFactory.getInstance().findById(bookId);
			request.setAttribute("bk",bk);
		%>
		<tr>
			<td>${bk.name }</td>
			<td><img src="${pageContext.request.contextPath }/image/${bk.image}" style="width:150px;height:100px;"></td>
			<td>${orderitem.quantity }</td>
			<td>${orderitem.prices }</td>
		</tr>
		</c:forEach>
	</table>
</body>
</html>