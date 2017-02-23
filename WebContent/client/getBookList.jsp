<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style type="text/css">
	tr {
		padding-top:20px;
	}
</style>
<script type="text/javascript" src="../jquery-1.4.2.min.js"></script>
</head>
<body>
	<center>
		<c:forEach items="${bks }" var="bk" >
		<table style="float:left;padding-left:150px">
			<tr>
				<td><a href="${pageContext.request.contextPath }/manager/BookServlet?method=getBookById&bookId=${bk.id}"><img width="150" height="100" src="${pageContext.request.contextPath }/image/${bk.image}"></a></td>
			</tr>
			<tr><td><a href="${pageContext.request.contextPath }/manager/BookServlet?method=getBookById&bookId=${bk.id}">${bk.name }</a></td></tr>
			<tr>
				<td>${bk.author }</td>
			</tr>
			<tr>
				<td>售价：${bk.price }</td>
			</tr>
			
			<tr>   
				<td><form action="${pageContext.request.contextPath }/client/BuyServlet" method="post">
					<input style="width:10px" type="text" name="quantity" value="1">
					<input type="hidden" name="bookId" value="${bk.id }">
					<input type="hidden" name="method" value="addCart">
					<input type="image" src="${pageContext.request.contextPath }/images/car_1.gif" onclick="true">
					</form>
				</td>
			</tr>
			</table>
		</c:forEach>
		<div style="clear:both">
		<br/>共有${bookpage.totalrecord }条记录
		<c:if test="${fn:length(bks)!=0 }">
		<a href="${pageContext.request.contextPath }/client/IndexServlet?method=getBookList">首页</a>
		<a href="${pageContext.request.contextPath }/client/IndexServlet?method=getBookList&pagenum=${bookpage.pagenum-1}"><</a>
		<c:forEach var="page" begin="${bookpage.startpage }" end="${bookpage.endpage }">
		<a href="${pageContext.request.contextPath }/client/IndexServlet?method=getBookList&pagenum=${page }">[${page }]</a>
		</c:forEach>
		<a href="${pageContext.request.contextPath }/client/IndexServlet?method=getBookList&pagenum=${bookpage.pagenum+1}">></a>
		<a href="${pageContext.request.contextPath }/client/IndexServlet?method=getBookList&pagenum=${bookpage.endpage}">末页</a>
		</div>
		</c:if>
	</center>
	
</body>
</html>