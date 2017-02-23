<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript" src="../jquery-1.4.2.min.js"></script>
</head>
<body>
	<table border="1">
		<tr>
			<td>用户名</td>
			<td>订单号</td>
			<td>订单时间</td>
			<td>总价</td>
			<td>发货状态</td>
			<td></td>
		</tr>
		<tr><td rowspan="${fn:length(orders) }">${user.username }</td></tr>
			<c:forEach items="${orders }" var="order">
			<tr>
				<td>${order.id }</td>
				<td>${order.ordertime }</td>
				<td>${order.price }</td>
				<c:choose>
				<c:when test="${order.state==1 }">
					<td>未发货</td>
				</c:when>
				<c:otherwise>
					<td>已发货</td>
				</c:otherwise>
				</c:choose>
				<td><a href="#">查看详情</a></td>
			</tr>
			</c:forEach>
	</table>  
	<script type="text/javascript">
		var temp =  ${orderGson };
		var s = eval(temp);
		var len = s.length;
		var i = 0;
		var parentNode = $("tr").eq(2);
		for(i=0;i<len;i++){
			var childNode = parentNode.find("td").eq(4).find("a").first();
			(function(i){
				var tempitem = s[i];
				childNode.bind("click",function(){
				var orderitem = JSON.stringify(tempitem);
				$.ajax({
					type:"post",
					url:"OrderServlet",
					data:{"order":orderitem,"method":"getOrderitem"},
					success:function(data){
						window.location.href="getOrderitem.jsp";
					},
				})
				});
			})(i)
			parentNode = parentNode.next();
		}
	</script>
</body>
</html>