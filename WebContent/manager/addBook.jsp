<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="com.dabai.vo.token" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javaScript">
	
</script>
</head>
<body>
	
	<form action="${pageContext.request.contextPath }/manager/BookServlet" method="Post"  enctype="multipart/form-data">
	<table>
	<%
		token tk = token.getInstance();
		String token = tk.getToken(request);
	%>
		<input type="hidden" name="Token" value="${token }" />
		<tr>
			<td>书名：</td>
			<td><input type="text" name="Name" value="${bk.name}"></td>
		</tr>
		<tr>
			<td>作者：</td>
			<td><input type="text" name="Author" value="${bk.author}"></td>
		</tr>
		<tr>
			<td>图片：</td>
			<td><input type="file" name="File"></td> 
			<c:if test="${bk.image!=null }">
			<td>
				<a href="${pageContext.request.contextPath }/image/${bk.image}">查看图片</a>
			</td>
			</c:if>
		</tr>
		<tr>
			<td>价格：</td>
			<td><input type="text" name="Price" value="${bk.price}"></td>
		</tr>
		<tr>
			<td>描述：</td>
			<td><input type="text" name="Description" value="${bk.description}"></td>
		</tr>
		<tr>
			<td>类别：</td>
			<td>
			<select name="Category_id">
				<c:forEach items="${categories }" var="c">
					<c:choose>
						<c:when test="${c.name==bk.category_id }">
							<option value=${c.id } selected="selected">${bk.category_id }</option>
						</c:when>
						<c:otherwise>
							<option value=${c.id }>${c.name }</option>
						</c:otherwise>
					</c:choose>
				</c:forEach>
			</select>
			</td>
		</tr>
		<tr>
			<td><input type="submit" value="提交s" ></td> 
			<td><input type="reset" value="重置"></td> 
		</tr>
	</table>
	</form>
</body>
</html>