<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link type="text/css" rel="stylesheet" href="../style/reset.css">
<link type="text/css" rel="stylesheet" href="../style/main.css">
<title>Insert title here</title>
</head>
<body>
	<div class="headerBar">
		<div class="topBar">
			<div class="comWidth">
					<div class="leftArea">
						<a href="#">欢迎访问大白网上书店</a>
					</div>
					<div class="rightArea">
						<c:choose>
						<c:when test="${user==null }">
							<a href="javascript:window.open('${pageContext.request.contextPath }/client/userlogin.jsp','_top')">登陆</a>
							<a href="javascript:window.open('${pageContext.request.contextPath }/client/register.jsp','_top')">注册</a>
						</c:when>
						<c:otherwise>
						<a href="javascript:window.open('${pageContext.request.contextPath }/client/BuyServlet?method=showCart','_top')">购物车</a>
							<a href="javascript:window.open('${pageContext.request.contextPath }/client/OrderServlet?method=getuserOrder','_top')">我的订单</a>
							<a href="${pageContext.request.contextPath }/client/UserServlet?method=nologin">注销登陆</a>
						</c:otherwise>
						</c:choose>
					</div>
			</div>
		</div>
		<div class="logoBar">
			<div class="comWidth">
				<div class="logo fl">
					<a href="#"><img src="../images/logo.jpg" width=50px height=60px></a>
				</div>
				<div class="research_box fl">
					<input type="text" class="search_text">
					<input type="button" value="搜 索" class="Search">
				</div>
				<div class="fabu">
					<span class="fabuzhiwei"><img src="../images/icn_add_user.png"><a href="#">发布职位</a></span>
					<span class="subscribe"><img src="../images\icn_edit.png"><a href="#">登记简历</a></span>
				</div>
			</div>
		</div>
		<div class="navBox">
			<div class="comWidth">
				<div class="jobClass fl">
					<h3>全部职位分类</h3>
				</div>
				<ul class="nav ">
					<c:forEach items="${applicationScope.categories }" var="category" varStatus="categorynum">
						<c:if test="${categorynum.count<7}">
							<li><a href="${pageContext.request.contextPath }/client/IndexServlet?method=getBookList&categoryId=${category.id}" target="main">${category.name }</a></li>
						</c:if>
					</c:forEach>
				</ul>
			</div>
		</div>
	</div>
</body>
</html>