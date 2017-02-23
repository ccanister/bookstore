<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="com.dabai.vo.*,com.dabai.factory.BookFactory,java.util.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head> 
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>购物车</title>
<script type="text/javascript" src="../jquery-1.4.2.min.js"></script>
</head>
<body>
	<!-- <form action="${pageContext.request.contextPath }/client/OrderServlet" method="post">  -->
	<input type="hidden" name="method" value="insertOrder" />
	<table>
		<%
			
		%>
		<c:forEach items="${carts.cartItems }" var="cart" >
		<c:set var="cart" value="${cart }" scope="request"/>
		<% 
			cartItem cartitem = (cartItem)request.getAttribute("cart");
			int id = cartitem.getBookId();
			book bk = BookFactory.getInstance().findById(id);
			request.setAttribute("bk",bk);
			request.setAttribute("cartitem",cartitem);
		%>
		<tr>
			<td rowspan="3"><input type="checkbox" name="book" checked="checked" value="${bk.id }"></td>
			<td rowspan="3"><img src="${pageContext.request.contextPath }/image/${bk.image}" style="width:100px;height:50px;"></td>
			<td>书名:${bk.name }</td>
		</tr>
		<tr>
			<td name="quantity">数量: <input class="quantity" type="text" name="quantities" value="${cartitem.quantity }"></td>
		</tr>
		<tr>
			<td name="price" >价格:<input class="price" type="text" name="quantities" value="${cartitem.prices }" disabled="true"></td>
			
		</tr>
		</c:forEach>
		<tr>
			<td>总价:<input type="text" width="1"id="Allprice" name="totalprice" value="${carts.prices }" disabled="true"></td>
		</tr>
		<tr>
			<td><input id="submit" type="submit" value="提交订单"></td>
		</tr>
	</table>
	
	<script type="text/javascript">
	function test(e){
		var element = document.getElementById(e);
		element.style.display = element.style.display=='block'?'none':'block';
	}
		var s = <%=session.getAttribute("cartitems")%>;
		var cartitems = eval(s);
		var len = cartitems.length;
		var sum = ${carts.prices };
		var prices = new Array();
		var i;
		for(i=0;i<len;i++){
			prices[i] = cartitems[i].prices;
		}
		var checkboxs = document.getElementsByName("book");
		var priceTd = document.getElementById("Allprice");
		var copyitems = JSON.parse(JSON.stringify(cartitems));
		for(i=0;i<len;i++){
			(function(i){
				checkboxs[i].addEventListener('change',function(){
					if(this.checked == true){
						var sum2 = window.sum;
						sum = sum2+window.prices[i];
						priceTd.value = sum;
						copyitems[i] = cartitems[i];
						console.log(copyitems);
					}else{
						var sum2 = window.sum;
						sum = sum2-window.prices[i];
						priceTd.value = sum;
						delete copyitems[i];
						console.log(copyitems);
					}
					},false)
			})(i)
		}
		
		$(function(){
			var index;var oldQuan;
			$(".quantity").focus(function(){
				var quanNode = $(":focus");
				var parentNode = quanNode.parent().parent();
				index = $("tr").index(parentNode);
				oldQuan = $("tr").eq(index).find("td").first().find("input").first().val();
			}).blur(function(){
				var pos = Math.floor(index / 3);
				var newQuan = $("tr").eq(index).find("td").first().find("input").first().val();
				var price = cartitems[pos].prices / cartitems[pos].quantity;
				var priceNode = $("tr").eq(index+1).find("td").first().find("input").first();
				var totalNode = $("#Allprice");
				var redPrice = price * (newQuan-oldQuan);
				var oldPrice = Number(priceNode.val());var oldTotalPrice=Number(totalNode.val());
				var newPrice = oldPrice + redPrice;var newTotalPrice = oldTotalPrice+ redPrice;	
				priceNode.val(newPrice);
				totalNode.val(newTotalPrice);
				cartitems[pos].quantity = newQuan;cartitems[pos].prices = newPrice;
			})
			
			
		})
		$("#submit").bind("click",function(){
			var cartState = ${cartState };
			console.log(cartState);
			var json = JSON.stringify(copyitems);
			$.ajax({
				type:"post",
				url:"OrderServlet",
				data:{"books":json,"method":"insertOrder"},
				success:function(data){
					alert("提交订单成功");
					if(cartState==1) {
						window.location.href="cartList.jsp";
					} else if(cartState==0) {
						alert("购物车为空！");
						window.location.href="../index.jsp";
					}
				},
				error: function() { 
					alert("fail");
				},
			})
		});
	</script>
</body>
</html>