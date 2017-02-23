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
			<td>总价</td>
			<td>成交时间</td>
			<td>发货状态</td>
			<c:if test="${state==1 }">
			<td>操作</td>
			</c:if>
			<td>地址</td>
			<td></td>
		</tr>
		<c:forEach items="${noordermans }" var="orderman">
			<c:set value="${fn:length(orderman.orderslist)+1 }" var="size" />
			<td rowspan="${size }">${orderman.user.username }</td>
			<c:forEach items="${orderman.orderslist }" var="order">
				<c:if test="${order.state == state }">
				<tr>
					<td>${order.id }</td>
					<td>${order.price }</td>
					<td>${order.ordertime }</td>
					<td>未发货</td>
					<td><a href="${pageContext.request.contextPath }/client/OrderServlet?method=changeState&id=${order.id }">发货</a></td>
					<td>${orderman.user.address }</td>
					<td><a id="inf" href="#">详情</a></td>
				</tr>
		</c:if>
			</c:forEach>
		</c:forEach>
	</table>
	<script type="text/javascript">
		var temp =  ${noordermanGson };
		var s = eval(temp);
		var len = s[0].orderslist.length;
		var i = 0;
		console.log(len);
		for(i=0;i<len;i++){
			(function(i){
				var pos = i+2;
				var parentNode = $("tr").eq(pos);
				var childNode = parentNode.find("td").eq(6).find("a").first();
				console.log(childNode.html());
				var tempitem = s[0].orderslist[i];
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
				parentNode = parentNode.next();
			})(i)
		}
	</script>
</body>
</html>