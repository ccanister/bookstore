<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script type="text/javascript" src="../jquery-1.4.2.min.js"></script>
<style type="text/css">
	.menu{
		display:none;
	}
	ul {
		padding	: 20px 0px 10px 30px;
	}
	li {
		padding-left:20px;
		padding-top:20px;
		list-style:none;	
	}
	a {
		padding-top:20px;
		text-decoration:none;
		color:grey;
	}
</style>
<script type="text/javaScript">
	function test(e){
		var element = document.getElementById(e);
		element.style.display = element.style.display=='block'?'none':'block';
	}
</script>
</head>
<body>
	<ul>
	<a href="#" onclick="test('menu1')">分类管理
		<div id="menu1" class="menu">
			<li><a href="${pageContext.request.contextPath }/manager/addCategory.jsp" target="right_2">添加分类</a></li>
			<li><a href="${pageContext.request.contextPath }/manager/CategoryServlet?method=getCategoryList" target="right_2">分类列表</a></li>
		</div>	
	</a>
	</ul>
	<ul>
	<a href="#" onclick="test('menu2')">书籍管理
		<div id="menu2" class="menu">
			<li><a href="${pageContext.request.contextPath }/manager/addBook.jsp" target="right_2">添加书籍</a></li>
			<li><a href="${pageContext.request.contextPath }/manager/BookServlet?method=getBookList" target="right_2">书籍列表</a></li>
		</div>	
	</a>
	</ul>
	<ul>
	<a href="#" onclick="test('menu3')">订单管理
		<div id="menu3" class="menu">
			<li><a href="${pageContext.request.contextPath }/client/OrderServlet?method=getOrdersManager&state=2" target="right_2">已发货订单</a></li>
			<li><a href="${pageContext.request.contextPath }/client/OrderServlet?method=getOrdersManager&state=1" target="right_2">未发货订单</a></li>
		</div>	
	</a>
	</ul>
	<ul>
	<a href="#" onclick="test('menu4')">评论管理
		<div id="menu4" class="menu">
			<li><a href="#">添加评论</a></li>
			<li><a href="#">评论列表</a></li>
		</div>	
	</a>
	</ul>
</body>
</html>